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
 * @since 2023-11-12 10:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("officespace")
public class Officespace implements Serializable {


@TableId(value = "Spaceid",type = IdType.AUTO)
private Integer spaceid;

 @TableField("Spacename")
private String spacename;

 @TableField("Labid")
private Integer labid;

@TableField("area")
private Double area;

@TableField("address")
private String address;


        }