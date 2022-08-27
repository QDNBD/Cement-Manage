package com.example.servlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetTime {

    /**
     * 当月
     * @return
     */
    public String getNowTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 上个月
     * @return
     */
    public String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 下个月
     * @return
     */
    public String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }

    /**
     * 获取任意时间的下一个月
     * 描述:<描述函数实现的功能>.
     * @param repeatDate
     * @return
     */
    public String getPreMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String[] strings = repeatDate.split("-");
        int year = Integer.parseInt(strings[0]);
        String monthsString = strings[1];
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year,month,Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取任意时间的上一个月
     * 描述:<描述函数实现的功能>.
     * @param repeatDate
     * @return
     */
    public String getLastMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String[] strings = repeatDate.split("-");
        int year = Integer.parseInt(strings[0]);
        String monthsString = strings[1];
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year,month-2,Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }
}
