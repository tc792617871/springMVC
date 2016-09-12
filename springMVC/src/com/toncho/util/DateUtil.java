package com.toncho.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类，支持JDK8，兼容JDK6
 */
public final class DateUtil {

	public static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String SIMPLE_PATTERN = "yyyy-MM-dd";

	private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	private DateUtil() {
	}

	/**
	 * 通过秒数得到时间，精确到秒
	 *
	 * @param seconds
	 *            秒数
	 * @return 时间对象
	 */
	public static LocalDateTime secondsToDateTime(long seconds) {
		if (0 == seconds)
			return null;
		return LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.of("+8"));
	}

	/**
	 * 通过秒数得到时间，精确到日
	 *
	 * @param seconds
	 *            秒数
	 * @return 时间对象
	 */
	public static LocalDate secondsToDate(long seconds) {
		LocalDateTime localDateTime = secondsToDateTime(seconds);
		return null == localDateTime ? null : localDateTime.toLocalDate();
	}

	/**
	 * 格式化时间对象
	 *
	 * @param date
	 *            时间对象
	 * @param pattern
	 *            格式化正则
	 * @return 时间字符串
	 */
	public static String formateDate(Date date, String pattern) {
		String str = "";
		if (null != date) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			str = formatter.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("+8")));
		}
		return str;
	}

	/**
	 * 格式化时间对象，使用yyyy-MM-dd HH:mm:ss格式
	 *
	 * @param date
	 *            时间对象
	 * @return 时间字符串
	 */
	public static String formateDate(Date date) {
		return formateDate(date, FULL_PATTERN);
	}

	/**
	 * 时间格式字符串转换成时间对象
	 *
	 * @param dateStr
	 *            时间字符串
	 * @param pattern
	 *            格式化正则
	 * @return 时间对象
	 */
	public static Date toDateTime(String dateStr, String pattern) {
		Date date = null;
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 时间格式字符串转换成时间对象，使用yyyy-MM-dd HH:mm:ss格式
	 *
	 * @param dateStr
	 *            时间字符串
	 * @return 时间对象
	 */
	public static Date toDateTime(String dateStr) {
		return toDateTime(dateStr, FULL_PATTERN);
	}

	/**
	 * 获得指定日期前后的日期，返回日期型值
	 * 
	 * @param inDate
	 *            指定的日期
	 * @param days
	 *            加减天数
	 * @return Date
	 */
	public static Date getDateByAddDays(Date inDate, int days) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(inDate);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * 获得指定日期前后的日期，返回日期字符串型值
	 * 
	 * @param inDate
	 *            指定的日期
	 * @param days
	 *            加减天数
	 * @param _iType
	 *            日期格式化类型
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getDateByAddDays(Date inDate, int days, int _iType) {
		Date tempDate = getDateByAddDays(inDate, days);
		return date2String(tempDate, _iType);
	}

	public static String date2String(Date date, int _iType) {
		String strTemp = TIME_FORMAT.format(date);
		SimpleDateFormat formatter = null;
		switch (_iType) {
		case 0: // yyyymmdd
			strTemp = strTemp.substring(0, 8);
			break;
		case 4:// yyyy
			strTemp = strTemp.substring(0, 4);
			break;
		case 6: // yymmdd
			strTemp = strTemp.substring(2, 8);
			break;
		case 8: // yyyymmdd
			strTemp = strTemp.substring(0, 8);
			break;
		case 10: // yyyy-mm-dd
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			strTemp = formatter.format(date);
			break;
		case -6: // HHmmss
			strTemp = strTemp.substring(8);
			break;
		case -8: // HH:mm:ss
			formatter = new SimpleDateFormat("HH:mm:ss");
			strTemp = formatter.format(date);
			break;
		default:
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strTemp = formatter.format(date);
			break;
		}
		return strTemp;
	}

	/**
	 * 功能描述：返回小
	 *
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

}
