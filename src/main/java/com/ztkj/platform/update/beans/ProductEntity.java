package com.ztkj.platform.update.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 09:27
 * @Description: 产品的实体类
 */
@Data
@TableName("product")
public class ProductEntity {
    /**
     * 主键，无意义
     */
    @TableId(type = IdType.AUTO)
    private Integer pkid;
    /**
     * 产品的包名
     */
    @TableField("productID")
    private String productID;
    /**
     *产品的版本号
     */
    @TableField("productVersionID")
    private String productVersionID;
    /**
     * 区分用的版本号
     */
    @TableField("VersionCode")
    private Integer VersionCode;
    /**
     * 产品的 文件路径
     */
    @TableField("productFileName")
    private String productFileName;
    /**
     * 产品的 文件大小
     */
    @TableField("productFileSize")
    private Long productFileSize;
    /**
     * 是否为最新版本
     */
    @TableField("isNew")
    private Integer isNew;
    /**
     * 更新方式
     */
    @TableField("updateMethod")
    private Integer updateMethod;
    /**
     * 更新说明
     */
    @TableField("updateDirections")
    private String updateDirections;

    @TableField(value = "updateDate",jdbcType = JdbcType.BIGINT)
    private Long TimeStamp;
}
