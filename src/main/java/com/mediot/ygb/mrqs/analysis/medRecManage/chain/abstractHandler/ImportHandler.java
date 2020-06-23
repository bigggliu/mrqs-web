package com.mediot.ygb.mrqs.analysis.medRecManage.chain.abstractHandler;

import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanRequest;


public abstract class ImportHandler {

    private ImportHandler successer;

    public abstract  void handelReq(DataCleanRequest param);

    //获取当前角色的下个角色
    public ImportHandler getNextHandler(){
        return  successer;
    };

    //设置下一个处理角色
    public void setNextHandler(ImportHandler successer){
        this.successer=successer;
    }

}
