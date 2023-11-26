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
 * @since 2023-11-12 10:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("contact")
public class Contact implements Serializable {


 @TableId("Contactid")
private Integer contactid;

@TableField("workphone")
private String workphone;

@TableField("mobilephone")
private String mobilephone;

@TableField("email")
private String email;


        }