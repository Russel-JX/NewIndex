package com;

import org.apache.log4j.Logger;

public class Log4jTest {

	private static Logger logger = Logger.getLogger(Log4jTest.class);
	/**
	 * 日志测试
	 */
	public static void main(String[] args) {
		logger.info("log4j的info级别日志测试3....");
		logger.error("log4j的error级别日志测试....");
		logger.debug("log4j的error级别日志测试...");
		
		System.out.println(logger.getLoggerRepository());
		System.out.println(logger.getName());

	}

}
