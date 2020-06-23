package com.mediot.ygb.mrqs.analysis.medRecManage.excelDataListener;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomStringConverter implements Converter{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Object convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        logger.info("该cell的值是:"+cellData.getStringValue());
        return cellData.getStringValue();
    }

    @Override
    public CellData convertToExcelData(Object o, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }
}
