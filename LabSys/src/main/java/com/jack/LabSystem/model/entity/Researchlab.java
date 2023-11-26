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
 * @since 2023-11-11 18:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("researchlab")
public class Researchlab implements Serializable {

@TableId(value = "Labid", type = IdType.AUTO)
private Integer labid;

@TableField("labname")
private String labname;

@TableField("researcharea")
private String researcharea;


        }