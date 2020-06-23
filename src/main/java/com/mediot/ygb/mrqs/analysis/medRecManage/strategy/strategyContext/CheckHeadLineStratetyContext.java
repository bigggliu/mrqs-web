package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.strategyContext;

import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.CheckHeadLineStrategy;
import lombok.Data;


@Data
public class CheckHeadLineStratetyContext {

    private CheckHeadLineStrategy checkHeadLineStrategy;

    public CheckHeadLineStratetyContext(CheckHeadLineStrategy checkHeadLineStrategy){
        this.checkHeadLineStrategy=checkHeadLineStrategy;
    }

    public void startCheckHDLine(){
        checkHeadLineStrategy.checkHeadline();
    }

}
