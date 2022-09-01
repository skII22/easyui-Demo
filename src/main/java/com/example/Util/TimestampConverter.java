package com.example.Util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
@Component
public class TimestampConverter implements Converter<Timestamp>{

    @Override
    public Class<Timestamp> supportJavaTypeKey() {
        return Timestamp.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<Timestamp> convertToExcelData(Timestamp timestamp, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp));
    }

    @Override
    public Timestamp convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Date timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cellData.getStringValue());
        return null;
    }
}
