package com.mediot.ygb.mrqs.org.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * T_ORG_RELATIONSHIP
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_ORG_RELATIONSHIP")
public class TOrgRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "REF_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long refId;

    private String pid;

    private String orgId;

    private String orgName;

    @TableField(exist = false)
    private String fqun;

}