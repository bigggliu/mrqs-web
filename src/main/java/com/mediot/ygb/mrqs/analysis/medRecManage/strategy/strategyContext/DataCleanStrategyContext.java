package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.strategyContext;

import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.DataCleanStrategy;


public class DataCleanStrategyContext {

    private DataCleanStrategy dcs;

    public DataCleanStrategyContext(DataCleanStrategy dcs) {
        this.dcs = dcs;
    }

    public void cleanData() {
        this.dcs.startDataClean();
    }
}
