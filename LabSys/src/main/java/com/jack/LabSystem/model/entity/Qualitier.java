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
 * @since 2023-11-12 10:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qualitier")
public class Qualitier implements Serializable {


 @TableId("Qualitierid")
private Integer qualitierid;

@TableField("qualitiername")
private String qualitiername;

@TableField("address")
private String address;

@TableField("Leaderid")
private Integer leaderid;


        }