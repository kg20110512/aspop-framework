package com.hczt.base.qiniu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.io.Files;

public class TCU2ID {
	
	public List<TCU> readFile2List(String filePath) {
		FileReader reader = null;
		BufferedReader br = null;
		List<TCU> list = new ArrayList<>();
		try {
			reader = new FileReader(filePath);
			br = new BufferedReader(reader);
			String str = "";
			while ((str=br.readLine()) != null) {
				String[] strs = str.split(",");
				TCU tcu = new TCU();
				tcu.setId(Integer.valueOf(strs[0]));
				tcu.setEcode(strs[1]);
				list.add(tcu);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
//			logger.error("文件【{}】未找到",filePath,e);
		} catch (IOException e) {
			e.printStackTrace();
//			logger.error("读取文件【{}】出现异常",filePath,e);
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
	
	
	public Map<String, Integer> readFile2Map(String filePath) {
		FileReader reader = null;
		BufferedReader br = null;
		Map<String, Integer> map = new HashMap<>();
		try {
			reader = new FileReader(filePath);
			br = new BufferedReader(reader);
			String str = "";
			while ((str=br.readLine()) != null) {
				String[] strs = str.split(",");
				map.put(strs[1], Integer.valueOf(strs[0]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
//			logger.error("文件【{}】未找到",filePath,e);
		} catch (IOException e) {
			e.printStackTrace();
//			logger.error("读取文件【{}】出现异常",filePath,e);
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
		
		return map;
	}
	
	public void changeFiles(String path, Map<String, Integer> map) {
		File file = new File(path);
		if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹:"+path+"是空的!");
                return;
            } else {
            	System.out.println("正在遍历文件夹："+path);
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        changeFiles(file2.getAbsolutePath(), map);
                    } else {
                    	changeCode2Id(path, file2, map);
                    }
                }
            }
        } else {
            System.out.println("文件:"+path+"不存在!");
        }
	}
	
	public void changeCode2Id(String parentPath, File file, Map<String, Integer> map) {
		String[] fileNames = file.getName().substring(0, file.getName().lastIndexOf(".")).split("_");
		String newFileName = "";
		for (int i = 0; i < fileNames.length; i++) {
			Integer id = map.get(fileNames[i]);
			if(id == null) {
				newFileName += fileNames[i];
			}else {
				newFileName += id.intValue();
			}
			if(i != fileNames.length - 1) {
				newFileName += "_";
			}
		}
		newFileName += file.getName().substring(file.getName().lastIndexOf("."));
		if(!newFileName.equals(file.getName())) {
			newFileName = parentPath + "/" + newFileName;
			File out = new File(newFileName); // 目标文件夹
			try {
				Files.copy(file, out);
				System.out.println("文件拷贝并重命名成功，源文件:"+file.getName()+";目标文件:"+out.getName());
			} catch (IOException e) {
				System.out.println("文件拷贝并重命名失败，源文件:"+file.getName());
				e.printStackTrace();
			}
		}else {
			System.out.println("文件无法重命名，源文件:"+file.getName());
		}
	}
	
	public static void main(String[] args) {
		List<TCU> list = new TCU2ID().readFile2List("/Users/sundonghe/Documents/aspopMCY.txt");
	}
}