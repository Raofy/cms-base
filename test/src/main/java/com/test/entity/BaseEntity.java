package com.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * Created by Rao on 2023/9/1 17:26
 */
@Data
public class BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;
}
