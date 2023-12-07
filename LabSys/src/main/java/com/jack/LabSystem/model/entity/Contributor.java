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
 * @since 2023-11-11 20:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("contributor")
public class Contributor implements Serializable {

    @TableField("Recordid")
    private Integer recordid;

 @TableField("Outcomeid")
private Integer outcomeid;

 @TableField("Researcherid")
private Integer researcherid;

@TableField("ranks")
private Integer ranks;


        }