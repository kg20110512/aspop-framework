/*
 * RadomUtil.java
 *
 * Created Date: 2017年6月1日
 * 
 * Copyright (c) Yuandian Technologies Co., Ltd.
 *
 * This software is the confidential and proprietary information of
 * Yuandian Technologies Co., Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * Yuandian Technologies Co., Ltd.
 */

package com.vyd.base.utils;

import java.util.UUID;

/**
 * @author Alisa.Yang
 * @version <br>
 *          <p>
 *          随机数、字符串等工具类
 *          </p>
 */

public class RadomUtil {
	
	public static String NUMBER = "1234567890";
	public static String BASE = "abcdefghijklmnopqrstuvwxyz0123456789";
	
	public static String getRandomNumber(boolean numberFlag, int length) {
		
		String retStr = "";
		String strTable = numberFlag ? NUMBER : BASE;
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		
		return retStr;
	}
	
	public static String getRandomUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
