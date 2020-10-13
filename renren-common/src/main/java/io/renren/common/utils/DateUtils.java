/**
 * Copyright 2018 人人开源 http://www.renren.io
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.renren.common.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
    /** 时间格式(yyyy-MM-dd) */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期 0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[] {beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    /**
     * ISO日期转换为UTC日期
     *
     * @param dateString ISO日期,String格式
     * @return UTC日期
     * @author zjr
     * @date 2018-11-19 14:54:47
     */
    public static XMLGregorianCalendar xmlToDate(String dateString) {
        Date date = null;
        XMLGregorianCalendar gc = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            date = sdf.parse(dateString);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return gc;
    }

    /**
     * ISO日期转换为UTC日期
     *
     * @param date ISO日期,Date格式
     * @return UTC日期
     * @author zjr
     * @date 2018-11-19 14:54:47
     */
    public static XMLGregorianCalendar xmlToDate(Date date) {
        XMLGregorianCalendar gc = null;
        try {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return gc;
    }

    /**
     * UTC日期转换为ISO日期
     *
     * @param gc UTC日期
     * @return ISO日期
     * @author zjr
     * @date 2018-11-19 14:54:47
     */
    public static Date dateToXML(XMLGregorianCalendar gc) {
        GregorianCalendar ca = gc.toGregorianCalendar();
        return ca.getTime();
    }

    /**
     * 获取现在时间减45天
     *
     * @return 返回时间格式 yyyy-mm-dd HH:mm:ss
     * @author zjr
     * @date 2018-11-19 14:54:47
     */
    public static XMLGregorianCalendar getTheDateNow45DaysShort() {
        Date currentTime = new Date();
        currentTime = addDateDays(currentTime, -45);
        return xmlToDate(currentTime);
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间格式 yyyy-mm-dd HH:mm:ss
     * @author zjr
     * @date 2018-11-19 14:54:47
     */
    public static XMLGregorianCalendar getNowDateShort() {
        Date currentTime = new Date();
        return xmlToDate(currentTime);
    }
    /**
     * 获取现在时间减10天
     *
     * @return 返回时间格式 yyyy-mm-dd HH:mm:ss
     * @author zjr
     * @date 2018-11-19 14:54:47
     */
    public static XMLGregorianCalendar getTheDateNow10DaysShort() {
        Date currentTime = new Date();
        currentTime = addDateDays(currentTime, -1);
        return xmlToDate(currentTime);
    }
    /**
     * 获取现在时间减1月
     *
     * @return 返回时间格式 yyyy-mm-dd HH:mm:ss
     * @author zjr
     * @date 2018-11-19 14:54:47
     */
    public static Date getTheDateNow1MonthsShort() {
        Date currentTime = new Date();
        currentTime = addDateMonths(currentTime, -2);
        return currentTime;
    }
    /**
     * 获取现在时间减10天
     *
     * @return 返回时间格式 yyyy-mm-dd HH:mm:ss
     * @author zjr
     * @date 2018-11-19 14:54:47
     */
    public static XMLGregorianCalendar getTheDateNowOneYearShort() {
        Date currentTime = new Date();
        currentTime = addDateMonths(currentTime, -5);
        return xmlToDate(currentTime);
    }

    /**
     * 将Date类转换为XMLGregorianCalendar
     *
     * @param date
     * @return
     */
    public static XMLGregorianCalendar dateToXmlDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
        }
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(Calendar.YEAR));
        // 由于Calendar.MONTH取值范围为0~11,需要加1
        dateType.setMonth(cal.get(Calendar.MONTH) + 1);
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
        dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateType.setMinute(cal.get(Calendar.MINUTE));
        dateType.setSecond(cal.get(Calendar.SECOND));
        return dateType;
    }

    /**
     * 将XMLGregorianCalendar转换为Date
     *
     * @param cal
     * @return
     */
    public static Date xmlDate2Date(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

}
