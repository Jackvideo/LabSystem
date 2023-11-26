package com.jack.LabSystem.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
        import lombok.experimental.Accessors;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

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
@TableName("outcome")
public class Outcome implements Serializable {


@TableField("Projectid")
private Integer projectid;

 @TableId( value = "Outcomeid",type= IdType.AUTO)
private Integer outcomeid;

@TableField("outcomename")
private String outcomename;

@TableField("achievedate")
private Date achievedate;

@TableField("type")
private String type;


        }