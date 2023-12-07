package com.jack.LabSystem.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
    
/**
 * <p>
 * 
 * </p>
 *
 * @author Jackvideo
 * @since 2023-12-05 10:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pdrelation")
public class Pdrelation implements Serializable {

@TableId(value = "Recordid",type = IdType.AUTO)
private Integer recordid;

@TableField("projectid")
private Integer projectid;

@TableField("projectname")
private String projectname;

@TableField("partner")
private String partner;

@TableField("qualifier")
private String qualifier;

@TableField("principle")
private String principle;


        }