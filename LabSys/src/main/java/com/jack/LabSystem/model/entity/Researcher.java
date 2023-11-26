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
 * @since 2023-11-11 20:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("researcher")
public class Researcher implements Serializable {


 @TableId(value = "Researcherid",type = IdType.AUTO)
private Integer researcherid;

@TableField("Labid")
private Integer labid;

@TableField("researchername")
private String researchername;

@TableField("gender")
private String gender;

@TableField("level")
private String level;

@TableField("age")
private Integer age;

@TableField("researcharea")
private String researcharea;


        }