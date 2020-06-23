package com.mediot.ygb.mrqs.common.util;

import lombok.Getter;

@Getter
public class ResultPageUtil extends ResultUtil {

    private Integer total;

    private Integer pageSize;

    private Integer pageNum;

    // 构造方法
    public static ResultPageUtil build() {
        return new ResultPageUtil();
    }

    public ResultPageUtil total(Integer total) {
        this.total = total;
        return this;
    }

    public ResultPageUtil pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public ResultPageUtil pageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

}
