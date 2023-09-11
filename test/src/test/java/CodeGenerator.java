import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 代码生成模板
 * <p> <a href="https://github.com/baomidou/generator">具体配置详情</a></p>
 *
 * Created by Rao on 2023/9/1 11:14
 */
public class CodeGenerator {
    static final String dbUrl = "jdbc:mysql://154.221.20.179:3306/base-cms?allowPublicKeyRetrieval=true&useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    static final String dbUser = "root";
    static final String dbPass = "9425361raoyy";
    static final String author = "Rao";
    static final String basePath = "E:\\Users\\13121\\Idea\\Intellij\\cms-java-base\\cms-java-base\\test";
    static final String javaPath = "/src/main/java";
    static final String resourcePath = "/src/main/resources";
    static final String baseEntity = "com.test.entity.BaseEntity";
    static final String packageParent = "com.test";
    static final String packagePath = "/com/test";
    static final String entityPath = "/entity";
    static final String servicePath = "/service";
    static final String serviceImplPath = "/service/impl";
    static final String controllerPath = "/controller";
    static final String mapperPath = "/mapper";
    static final String xml = "/mapper";

    public static void main(String[] args) {
        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(dbUrl, dbUser, dbPass);
        AutoGenerator autoGenerator = new AutoGenerator(builder.build());

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .author(author)
                .outputDir(basePath)
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy/MM/dd HH:mm")
                .build();
        autoGenerator.global(globalConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent(packageParent)
                .pathInfo(getPathInfo())
                .build();
        autoGenerator.packageInfo(packageConfig);

        // 生成代码模板配置
        TemplateConfig templateConfig = new TemplateConfig.Builder()
                // 自定义模板
                .entity("/mybatisplus/template/entity.java")
                // 官方模板
//                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .controller("/templates/controller.java")
                .xml("/templates/mapper.xml")
                .build();
        autoGenerator.template(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .addInclude(scanner("表名，多个英文逗号分割").split(","))
                // 实体类策略配置，1.驼峰命名；2.指定基类；3.
                .entityBuilder()
                .naming(NamingStrategy.underline_to_camel)
                .enableLombok()
                .superClass(baseEntity)
                .addSuperEntityColumns("id", "create_time", "update_time", "delete_time")
                // mapper层策略配置
                .mapperBuilder()
                .enableMapperAnnotation()
                // service层配置
                .serviceBuilder()
                // controller层策略配置，1.开启驼峰转连字符
                .controllerBuilder()
                .enableHyphenStyle()
                .build();
        autoGenerator.strategy(strategyConfig);
        autoGenerator.execute(new FreemarkerTemplateEngine());
    }


    /**
     * 读取控制台内容
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private static Map<OutputFile, String> getPathInfo() {
        HashMap<OutputFile, String> pathInfoMap = new HashMap<>();
        pathInfoMap.put(OutputFile.entity, basePath  + javaPath + packagePath + entityPath);
        pathInfoMap.put(OutputFile.mapper, basePath + javaPath + packagePath + mapperPath);
        pathInfoMap.put(OutputFile.xml, basePath + resourcePath + xml);
        pathInfoMap.put(OutputFile.service, basePath + javaPath + packagePath + servicePath);
        pathInfoMap.put(OutputFile.serviceImpl, basePath + javaPath + packagePath + serviceImplPath);
        pathInfoMap.put(OutputFile.controller, basePath + javaPath + packagePath + controllerPath);
        return pathInfoMap;
    }
}
