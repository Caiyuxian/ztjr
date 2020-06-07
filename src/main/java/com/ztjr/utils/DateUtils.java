package com.ztjr.utils;

import com.ztjr.model.BorrowCycle;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 计算还款时间
     */
    public static String getRepayDate(Timestamp time, BorrowCycle cycle){
    	if(time == null)
    		return null;
        String datetime = format.format(time);
        try{
            Date date = format.parse(datetime);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, cycle.getValue());
            return format.format(c.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * timestamp 时间转化成 1970-08-08 20：00：00格式
     * @param time timestamp
     * @return time
     */
    public static String format(Timestamp time) {
        if (time == null) {
            return "";
        }
        try{
        	return format.format(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    
}
