package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.strategyContext;

import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.ParseFileStrategy;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;

import java.io.File;
import java.util.List;

public class StrategyContext {

    private ParseFileStrategy pfsStg;

    public StrategyContext(ParseFileStrategy pfsStg) {
        this.pfsStg = pfsStg;
    }

    public void excuteData() {
        this.pfsStg.parseFile2Date();
    }
}
