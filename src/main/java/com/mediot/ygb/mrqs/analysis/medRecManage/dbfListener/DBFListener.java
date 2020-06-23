package com.mediot.ygb.mrqs.analysis.medRecManage.dbfListener;

import com.linuxense.javadbf.DBFReader;

public interface DBFListener {

    public void rowProcessed(Object[] rowObjects, DBFReader reader, Integer currentNum, Boolean isTitle);

}
