/*
 * Copyright 2013 weibopay.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.template.enums;


/**
 * <p>可用状态枚举</p>
 * @author zhangjiewen
 * @version $Id: StatusKind.java, v 0.1 2013-3-1 下午3:48:54 zhangjiewen Exp $
 */
public enum StatusKind {
    //在用：USING；停用：NOT_USING
    USING("USING","可用"),
    NOT_USING("NOT_USING","不可用");
    
    private String code;
    private String desc;
    
    private StatusKind(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
    
    public static StatusKind getStatusByCode(String code){
        if(code == null){
            return null;
        }
        for(StatusKind status : StatusKind.values()){
            if(status.getCode().equals(code)){
                return status;
            }
        }
        return null;
    }
}
