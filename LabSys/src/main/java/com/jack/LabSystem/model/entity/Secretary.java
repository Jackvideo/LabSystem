package com.jack.LabSystem.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-11-12 10:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("secretary")
public class Secretary implements Serializable {


 @TableId(value = "Secretaryid",type= IdType.AUTO)
private Integer secretaryid;

@TableField("hiretime")
private LocalDate hiretime;

@TableField("responsibility")
private String responsibility;


        }