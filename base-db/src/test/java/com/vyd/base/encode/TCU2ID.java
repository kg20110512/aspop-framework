package com.vyd.base.encode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCU2ID {
	
	private static final Logger logger = LoggerFactory.getLogger(TCU2ID.class);
	
	public List<TCU> readFile2List(String filePath) {
		FileReader reader = null;
		BufferedReader br = null;
		List<TCU> list = new ArrayList<>();
		try {
			reader = new FileReader(filePath);
			br = new BufferedReader(reader);
			String str = StringUtils.EMPTY;
			while ((str=br.readLine()) != null) {
				String[] strs = str.split(",");
				TCU tcu = new TCU();
				tcu.setId(Integer.valueOf(strs[0]));
				tcu.setEcode(strs[1]);
				list.add(tcu);
			}
		} catch (FileNotFoundException e) {
			logger.error("文件【{}】未找到",filePath,e);
		} catch (IOException e) {
			logger.error("读取文件【{}】出现异常",filePath,e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	
	public static void main(String[] args) {
		List<TCU> list = new TCU2ID().readFile2List("/Users/sundonghe/Documents/aspopMCY.txt");
	}
}