package com.rfy.logger;

import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.recovery.ResilientFileOutputStream;
import ch.qos.logback.core.util.ContextUtil;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 自定义日志Appender
 * <p>
 * <p>
 * Created by Rao on 2023/8/29 15:29
 */
public class AdvanceRollingFileAppender<T> extends FileAppender<T> {
    private static final long DEFAULT_BUFFER_SIZE = 8192;
    // 单个文件最大大小10M（默认）
    private static final long DEFAULT_MAX_FILE_SIZE = 10 * 1024 * 1024;
    File currentlyActiveFile;

    // 追加或者截断文件，默认为true(追加)
    protected boolean append = true;
    // 当前写入日志的文件名
    protected String fileName = null;
    // 决定是否重命名已经存在的日志文件
    protected boolean prudent = false;
    // 文件夹名称
    private String dir;
    private FileSize bufferSize = new FileSize(DEFAULT_BUFFER_SIZE);
    private FileSize maxFileSize = new FileSize(DEFAULT_MAX_FILE_SIZE);

    @Override
    public boolean isAppend() {
        return append;
    }

    @Override
    public String getFile() {
        return fileName;
    }

    @Override
    public void start() {
        if (this.dir == null) {
            addError("日志目录不能为空");
            return;
        }
        this.setFile(null);
        if (!append) {
            addWarn("RollingFileAppend日志append必须是追加，默认为true");
            append = true;
        }
        if (isPrudent()) {
            if (rawFileProperty() != null) {
                addWarn("在prudent模式下，保证File属性为null");
                setFile(null);
            }
        }
        currentlyActiveFile = new File(getFile());
        addInfo("当前日志文件名：" + getFile());
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        Map<String, String> map = ContextUtil.getFilenameCollisionMap(context);
        if (map == null || getName() == null) {
            return;
        }
        map.remove(getName());
    }

    @Override
    public void setFile(String file) {
        if (file == null) {
            Date now = new Date();
            String subDir = this.getPresentTime(now, "yyyy-MM");
            String fileName = this.getPresentTime(now, "yyyy-MM-dd");
            String trueFileName = String.format("%s/%s/%s.log", this.dir, subDir, fileName);
            this.fileName = trueFileName;
        } else {
            this.fileName = file;
        }
    }

    @Override
    protected boolean checkForFileCollisionInPreviousFileAppenders() {
        boolean collisionDetected = false;
        if (fileName == null) {
            return false;
        }
        Map<String, String> map = (Map<String, String>) context.getObject(CoreConstants.FA_FILENAME_COLLISION_MAP);
        if (map == null) {
            return collisionDetected;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (fileName.equals(entry.getValue())) {
                addErrorForCollision("File", entry.getValue(), entry.getKey());
                collisionDetected = true;
            }
        }
        if (name != null) {
            map.put(getName(), fileName);
        }
        return collisionDetected;
    }

    @Override
    protected void addErrorForCollision(String optionName, String optionValue, String appenderName) {
        addError("'" + optionName + "' option has the same value \"" + optionValue + "\" as that given for appender [" + appenderName + "] defined earlier.");
    }

    /**
     * 设置和打开要输出日志的文件。指定的文件必须是可写的。
     * 如果已经打开了一个文件，则关闭前一个文件
     * 第一。不要直接使用此方法。配置FileAppender或其中之一它的子类，逐个设置它的属性，然后调用start()
     *
     * @param fileName 日志文件的路径。
     * @throws IOException
     */
    @Override
    public void openFile(String fileName) throws IOException {
        lock.lock();
        try {
            File file = new File(fileName);
            // 创建父目录（不存在就创建）
            boolean result = FileUtil.createMissingParentDirectories(file);
            if (!result) {
                addError(file.getAbsolutePath() + "父目录创建失败");
            }
            ResilientFileOutputStream fileOutputStream = new ResilientFileOutputStream(file, append, bufferSize.getSize());
            fileOutputStream.setContext(context);
            setOutputStream(fileOutputStream);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isPrudent() {
        return this.prudent;
    }

    @Override
    public void setPrudent(boolean prudent) {
        super.setPrudent(prudent);
    }

    @Override
    public void setAppend(boolean append) {
        super.setAppend(append);
    }

    @Override
    public void setBufferSize(FileSize bufferSize) {
        addInfo("设置的日志缓冲区大小[" + bufferSize.toString() + "]");
        this.bufferSize = bufferSize;
    }

    @Override
    protected void subAppend(T event) {
        synchronized (this) {
            // 1. 超过文件大小
            if (currentlyActiveFile.length() >= maxFileSize.getSize()) {
                this.rollover();
            }
            // 2. 过了一天
            if (!this.checkIsPresent()) {
                this.rollover();
            }
        }
        super.subAppend(event);
    }

    @Override
    protected void writeOut(T event) throws IOException {
        if (prudent) {
            safeWrite(event);
        } else {
            super.writeOut(event);
        }
    }

    private boolean checkIsPresent() {
        this.setFile(null);
        File file = new File(getFile());
        return file.exists();
    }

    private void safeWrite(T evnet) throws IOException {
        ResilientFileOutputStream resilientFileOutputStream = (ResilientFileOutputStream) getOutputStream();
        FileChannel fileChannel = resilientFileOutputStream.getChannel();
        if (fileChannel == null) {
            return;
        }
        // 清除当前任何中断
        boolean interrupted = Thread.interrupted();
        FileLock fileLock = null;
        try {
            fileLock = fileChannel.lock();
            long position = fileChannel.position();
            long size = fileChannel.size();
            if (position != size) {
                fileChannel.position(size);
            }
            super.writeOut(evnet);
        } catch (IOException e) {
            resilientFileOutputStream.postIOFailure(e);
        } finally {
            if (fileLock != null && fileLock.isValid()) {
                fileLock.release();
            }
        }
        //如果在中断状态启动，则重新中断(参见LoGBACK-875)
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }

    // 通过将大部分轮转工作委托给轮转策略来实现
    public void rollover() {
        lock.lock();
        try {
            this.closeOutputStream();
            attemptRollover();
            attemptOpenFile();
            ;
        } finally {
            lock.unlock();
        }
    }

    private void attemptOpenFile() {
        try {
            this.setFile(null);
            currentlyActiveFile = new File(getFile());
            this.openFile(getFile());
        } catch (IOException e) {
            addError("setFile(" + fileName + ", false) call failed.", e);
        }
    }

    private void attemptRollover() {
        File renameFile = getRenameFile();
        currentlyActiveFile.renameTo(renameFile);
    }

    private File getRenameFile() {
        Date now = new Date();
        String t1 = this.getPresentTime(now, "yyyy-MM");
        String t2 = this.getPresentTime(now, "yyyy-MM-dd");
        String t3 = this.getPresentTime(now, "hh:mm:ss");
        String trueFileName = String.format("%s/%s/%s-%s.log", this.dir, t1, t2, t3);
        return new File(trueFileName);
    }

    public String getDir() {
        return this.dir;
    }

    /**
     * 设置日志目录文件夹
     *
     * @param dir
     */
    public void setDir(String dir) {
        if (this.isAbsolute(dir)) {
            this.dir = dir;
        } else {
            String base = System.getProperty("user.dir");
            Path path = FileSystems.getDefault().getPath(base, dir);
            this.dir = path.toAbsolutePath().toString();
        }
    }

    // 是否绝对路径
    private boolean isAbsolute(String str) {
        Path path = FileSystems.getDefault().getPath(str);
        return path.isAbsolute();
    }

    private String getPresentTime(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public FileSize getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(FileSize maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
