/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils;

import io.renren.common.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应数据
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@ApiModel(value = "响应")
@Data
public class APPResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编码：0表示成功，其他值表示失败
     */
    @ApiModelProperty(value = "编码：0表示成功，其他值表示失败")
    private int errcode = 0;
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String errmsg = "操作成功";
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public APPResult<T> ok(T data) {
        this.setData(data);
        return this;
    }

    public boolean success(){
        return errcode == 0 ? true : false;
    }

    public APPResult<T> error() {
        this.errcode = ErrorCode.INTERNAL_SERVER_ERROR;
        this.errmsg = MessageUtils.getMessage(this.errcode);
        return this;
    }

    public APPResult<T> error(int code) {
        this.errcode = code;
        this.errmsg = MessageUtils.getMessage(this.errcode);
        return this;
    }

    public APPResult<T> error(int code, String msg) {
        this.errcode = code;
        this.errmsg = msg;
        return this;
    }

    public APPResult<T> error(String msg) {
        this.errcode = ErrorCode.INTERNAL_SERVER_ERROR;
        this.errmsg = msg;
        return this;
    }


}
