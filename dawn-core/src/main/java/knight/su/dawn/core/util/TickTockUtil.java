/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/
package knight.su.dawn.core.util;

import knight.su.dawn.core.enums.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;

/**
 * 
 *
 * Date: 2019年1月17日<br/>
 * 
 * @author sugengbin
 */
public class TickTockUtil {

	private Logger logger = LoggerFactory.getLogger(TickTockUtil.class);

	protected Date first = null;
	protected Date last = null;
	protected Date cur = null;
	/** 时间类型 -- 毫秒 **/
	public static final int TIME_TYPE_MILLISECOND = 0;
	/** 时间类型 -- 秒 **/
	public static final int TIME_TYPE_SECONDS = 1;
	/** 时间类型 -- 分钟 **/
	public static final int TIME_TYPE_MINUTES = 2;
	protected int timeType; // 时间类型
	protected LogLevel logLvl = LogLevel.INFO; // 默认log级别
	private boolean switchClose = false; // 关闭开关，默认不关闭

	/**
	 * 默认
	 */
	public TickTockUtil() {
		this("Clock start at: " + DateUtils.now(), TIME_TYPE_SECONDS);
	}

	/**
	 * 
	 * @param msg
	 * @param timeType
	 * @param switchClose
	 * @param type
	 */
	public TickTockUtil(String msg, int timeType, boolean switchClose, Class<?> type) {
		if (Objects.nonNull(logger) && Objects.nonNull(type)) {
			logger = LoggerFactory.getLogger(type);
		}
		this.switchClose = switchClose;
		last = DateUtils.now();
		cur = last;
		first = last;// 初始化第一次
		this.timeType = timeType;
		log(msg);
	}

	/**
	 * 
	 * @param msg
	 * @param timeType
	 */
	public TickTockUtil(String msg, int timeType, boolean switchClose) {
		this(msg, timeType, switchClose, null);
	}
	
	/**
	 * 
	 * @param msg
	 * @param timeType
	 */
	public TickTockUtil(String msg, int timeType) {
		this(msg, timeType, false);
	}
	
	/**
	 * 
	 * @param msg
	 * @param timeType
	 */
	public TickTockUtil(int timeType) {
		this("", timeType, false);
	}

	/**
	 * 计时方法
	 * 
	 * @param msg
	 */
	public void clock(String msg) {
		last = cur;
		cur = DateUtils.now();
		log(msg + ": " + getIntervalTime());
	}

	/**
	 * 计算时间间隔
	 * 
	 * @return
	 */
	protected String getIntervalTime() {
		String tmStr = "";
		switch (timeType) {
		case 0: // 毫秒
			tmStr = (cur.getTime() - last.getTime()) + "毫秒";
			break;
		case 1: // 秒
			tmStr = DateUtils.getDiffMillisecond(last, cur) + "秒";
			break;
		case 2: // 分钟
		default:
			tmStr = DateUtils.getDiffMins(last, cur) + "分钟";
			break;
		}
		return tmStr;
	}

	/**
	 * 设置log级别
	 * 
	 * @param logLvl
	 */
	public void setLogLvl(LogLevel logLvl) {
		this.logLvl = logLvl;
	}

	/**
	 * 输出日志
	 * 
	 * @param msg
	 */
	protected void log(String msg) {
		if (switchClose) { // 关闭Log
			return;
		}
		if (logLvl == LogLevel.INFO) {
			logger.info(msg);
		} else if (logLvl == LogLevel.DEBUG) {
			logger.debug(msg);
		} else if (logLvl == LogLevel.ERROR) {
			logger.error(msg);
		}
	}

}
