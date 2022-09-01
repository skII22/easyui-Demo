package com.example.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 身份证校验工具类
 */
public class IDCardUtil {

    /*
    校验规则：
        如果为15位，只能是15位数字；前两位满足省/直辖市的行政区划代码。
        如果为18位，允许为18位数字，如出现字母只能在最后一位，且仅能为“X”；
        18位中包含年月的字段满足日期的构成规则；前两位满足省/直辖市的行政区划代码；
        最后一位校验位满足身份证的校验规则（身份证校验规则见附录）。
        附录：身份证校验规则
            公民身份证号码校验公式为RESULT = ∑( A[i] * W[i] ) mod 11。
            其中,i表示号码字符从右至左包括校验码在内的位置序号;A[i]表示第I位置上的数字的数值;W[i]表示第i位置上的加权因子,其值如下:

            i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
            W[i] 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2

            RESULT 0 1 2 3 4 5 6 7 8 9 10
            校验码A[1] 1 0 X 9 8 7 6 5 4 3 2
     */
    public static boolean idCardValidate(String idCard) {
        String[] valCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String ai = "";
        String ai1 = "";
        String ai2 = "";

        // 号码的长度 15位或18位
        if (idCard.length() != 15 && idCard.length() != 18) {
            return false;
        }

        // 数字 除最后以为都为数字
        if (idCard.length() == 18) {
            ai = idCard.substring(0, 17);
        } else if (idCard.length() == 15) {
            ai = idCard.substring(0, 6) + "19" + idCard.substring(6, 15);
        }
        if (!isNumeric(ai)) {
            return false;
        }

        // 出生年月是否有效
        String strYear = ai.substring(6, 10); // 年份
        String strMonth = ai.substring(10, 12); // 月份
        String strDay = ai.substring(12, 14); // 月份
        if (!isDataFormat(strYear + "-" + strMonth + "-" + strDay)) {
            return false;
        }

        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            return false;
        }

        // 地区码是否有效
        Hashtable h = getAreaCode();
        if (h.get(ai.substring(0, 2)) == null) {
            return false;
        }

        // 判断最后一位的值
        int totalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            totalmulAiWi = totalmulAiWi + Integer.parseInt(String.valueOf(ai.charAt(i))) * Integer.parseInt(wi[i]);
        }
        int modValue = totalmulAiWi % 11;
        String strVerifyCode = valCodeArr[modValue];
        ai1 = ai + strVerifyCode.toUpperCase();
        ai2 = ai + strVerifyCode.toLowerCase();
        if (idCard.length() == 18) {
            if (!ai1.equals(idCard) && !ai2.equals(idCard)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        }

        return false;
    }

    private static boolean isDataFormat(String str) {
        boolean flag = false;
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    private static Hashtable getAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }



    /**
     * 方法描述： 根据身份证获取年龄，性别
     * 2表示女
     * 1表示男
     * @param idNum
     * @return
     * String[]
     * @author  husheng
     */
    public static String[] getAgeAndSexById(String idNum) {
        String age = "";
        String sex = "";
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getDefault());//获取系统当前时间
        int currentYear = calendar.get(Calendar.YEAR);
        if (idNum.matches("^\\d{15}$|^\\d{17}[\\dxX]$")) {
            if (idNum.length() == 18) {
                Pattern pattern = Pattern.compile("\\d{6}(\\d{4})\\d{6}(\\d{1})[\\dxX]{1}");
                Matcher matcher = pattern.matcher(idNum);
                if (matcher.matches()) {

                    age = String.valueOf(currentYear - Integer.parseInt(matcher.group(1)));
                    sex = "" + Integer.parseInt(matcher.group(2)) % 2;
                }
            } else if (idNum.length() == 15) {
                Pattern p = Pattern.compile("\\d{6}(\\d{2})\\d{5}(\\d{1})\\d{1}");
                Matcher m = p.matcher(idNum);
                if (m.matches()) {
                    int year = Integer.parseInt(m.group(1));
                    year = 2000 + year;
                    if (year > 2020) {
                        year = year - 100;
                    }
                    age = String.valueOf(currentYear - year);
                    sex = "" + Integer.parseInt(m.group(2)) % 2;
                }
            }
        }
        if ("0".equals(sex)) {
            sex = "2";
        }
        return new String[]{age, sex};
    }

    /**
     * 方法描述： 根据身份证获取性别
     * 2表示女
     * 1表示男
     * @param idNum
     * @return
     * String[]
     * @author  husheng
     */
    public static String getSexByIdCard(String idNum) {
        String sex = "";
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getDefault());//获取系统当前时间
        int currentYear = calendar.get(Calendar.YEAR);
        if (idNum.matches("^\\d{15}$|^\\d{17}[\\dxX]$")) {
            if (idNum.length() == 18) {
                Pattern pattern = Pattern.compile("\\d{6}(\\d{4})\\d{6}(\\d{1})[\\dxX]{1}");
                Matcher matcher = pattern.matcher(idNum);
                if (matcher.matches()) {
                    sex = "" + Integer.parseInt(matcher.group(2)) % 2;
                }
            } else if (idNum.length() == 15) {
                Pattern p = Pattern.compile("\\d{6}(\\d{2})\\d{5}(\\d{1})\\d{1}");
                Matcher m = p.matcher(idNum);
                if (m.matches()) {
                    int year = Integer.parseInt(m.group(1));
                    year = 2000 + year;
                    if (year > 2020) {
                        year = year - 100;
                    }
                    sex = "" + Integer.parseInt(m.group(2)) % 2;
                }
            }
        }
        if ("0".equals(sex)) {
            sex = "2";
        }
        return sex;
    }

    //从身份证中提出出生日期
    public static Date getBirthDay(String idCard) {
        String ai = "";
        Date revertDate = new Date();
        if (idCard.length() == 18) {
            ai = idCard.substring(0, 17);
        } else if (idCard.length() == 15) {
            ai = idCard.substring(0, 6) + "19" + idCard.substring(6, 15);
        }
        // 出生年月是否有效
        String strYear = ai.substring(6, 10); // 年份
        String strMonth = ai.substring(10, 12); // 月份
        String strDay = ai.substring(12, 14); // 月份
        String birthDay = strYear + "-" + strMonth + "-" + strDay;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            revertDate = sdf.parse(birthDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return revertDate;
    }
}

