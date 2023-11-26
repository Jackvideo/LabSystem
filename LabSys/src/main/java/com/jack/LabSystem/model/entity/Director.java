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
import org.springframework.data.annotation.Id;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("director")
public class Director implements Serializable {


 @TableId(value = "Directorid",type = IdType.AUTO)
private Integer directorid;

 @TableField("Labid")
private Integer labid;

@TableField("startdate")
private LocalDate startdate;

@TableField("term")
private Integer term;


        }