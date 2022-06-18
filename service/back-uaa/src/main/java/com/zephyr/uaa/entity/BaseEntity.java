package com.zephyr.uaa.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.ZonedDateTime;

public class BaseEntity {
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;
    @TableField(value = "CREATED_DATE", fill = FieldFill.INSERT)
    private ZonedDateTime createdDate;
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(value = "UPDATED_DATE", fill = FieldFill.INSERT_UPDATE)
    private ZonedDateTime updatedDate;
    @TableField(value = "UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
    @TableField("DELETED_DATE")
    private ZonedDateTime deletedDate;
    @TableField("DELETED_BY")
    private String deletedBy;
    @TableField("STATUS")
    private String status;
}
