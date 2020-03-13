package com.wmh.member.doentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户do
 */
@Data
@TableName(value = "user_info")
public class UserDo implements Serializable {
    private static final Integer DEFAULT_PID = 10;
    public static final String ID = "user_id";
    /**
     * userid
     */
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long id;
    /**
     * 手机号码
     */

    private String mobile;
    /**
     * 邮箱
     */

    private String email;
    /**
     * 密码
     */

    private String password;
    /**
     * 用户名称
     */

    private String userName;
    /**
     * 性别 0 男 1女
     */

    private long sex;
    /**
     * 年龄
     */

    private Long age;
    /**
     * 注册时间
     */

    private Date createTime;
    /**
     * 修改时间
     */

    private Date updateTime;
    /**
     * 账号是否可以用 1 正常 0冻结
     */

    private long isAvalible;
    /**
     * 用户头像
     */

    private String picImg;
    /**
     * 用户关联 QQ 开放ID
     */

    private String qqOpenId;
    /**
     * 用户关联 微信 开放ID
     */
    private String wxOpenId;

}
