package com.jack.LabSystem.model.entity;

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
 * @since 2023-11-11 20:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("contactrelation")
public class Contactrelation implements Serializable {

    @TableField("recordid")
    private Integer recordid;

 @TableField("departmentname")
private String departmentname;

 @TableField("departmentid")
private Integer departmentid;

 @TableField("Contactid")
private Integer contactid;


        }