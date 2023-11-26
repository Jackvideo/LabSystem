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
 * @since 2023-11-12 10:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("record")
public class Record implements Serializable {


 @TableField("Researcherid")
private Integer researcherid;

 @TableField("Projectid")
private Integer projectid;

@TableField("participationdate")
private LocalDate participationdate;

@TableField("workload")
private String workload;

@TableField("allocatedfund")
private Double allocatedfund;


        }