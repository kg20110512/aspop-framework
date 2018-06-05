package com.hczt.base.qiniu;

import java.util.Map;

public class ChangeFileName {

	public static void main(String[] args) {

		TCU2ID tcu2ID = new TCU2ID();
		//List<TCU> list = tcu2ID.readFile2List("D:\\work\\2017\\task\\aspopMCY.txt");
		//System.out.println(list.size());
		
		Map<String, Integer> map = tcu2ID.readFile2Map("/Users/sundonghe/Downloads/aspopWJK.txt");
		//map.keySet().size();
		//遍历所有文件夹下的所有文件，每一个文件复制一份并且以 _ 分隔并将CODE重名为ID
		tcu2ID.changeFiles("/Users/sundonghe/Downloads/WOMAN_OBJ_F", map);
	}

}
