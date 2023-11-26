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
 * @since 2023-11-11 20:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("subproject")
public class Subproject implements Serializable {


@TableField("Projectid")
private Integer projectid;

@TableField("Subprojectid")
private Integer subprojectid;

@TableField("Leaderid")
private Integer leaderid;

@TableField("deadline")
private LocalDate deadline;

@TableField("budget")
private Double budget;

@TableField("technicalindex")
private String technicalindex;


        }