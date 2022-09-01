package com.example.Util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@Slf4j
public class DateConverter implements Converter<Date> {
    @Override
    public Class<Date> supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Calendar calendar = new GregorianCalendar(1900,0,-1);
        int i = cellData.getNumberValue().intValue();
        int intDay = Integer.parseInt(String.valueOf(i));
        Date dd = DateUtils.addDays(calendar.getTime(),intDay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        String d =new SimpleDateFormat("yyyy-MM-dd").format(simpleDateFormat.parse(String.valueOf(dd)));
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(d);
        return date;
    }


    @Override
    public WriteCellData convertToExcelData(Date date, ExcelContentProperty contentProperty,
                                                       GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
}
