package com.jack.LabSystem.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
        import lombok.experimental.Accessors;
    
/**
 * <p>
 * 
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("project")
public class Project implements Serializable {


 @TableId("Projectid")
private Integer projectid;

@TableField("Leaderid")
private Integer leaderid;

@TableField("projectname")
private String projectname;

@TableField("content")
private String content;

@TableField("totalfund")
private Integer totalfund;

@TableField("startdate")
private LocalDate startdate;

@TableField("enddate")
private LocalDate enddate;


        }