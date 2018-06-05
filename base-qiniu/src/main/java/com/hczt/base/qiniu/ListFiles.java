package com.hczt.base.qiniu;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

public class ListFiles {
	
	private static String ACCESS_KEY = "l3Gg0eTWFhsqALKyIygjszxlaEACula5JdIdSETi";
	private static String SECRET_KEY = "a_PNIWSausmTdlPPNSUrI5vd0BQdcQBIuHeS2cqm";
	
//	private static String ACCESS_KEY = "Ci_VFCYmKwFGWgbIEFxTsGNd69amAaXtwiZr9vIR";
//	private static String SECRET_KEY = "XSQNWYzsdcaiE9r68WyQQj13t1RpjuauMR_vx2JG";
	
	public void listFiles() {
		// 获取授权对象
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		
		String bucket = "images";
		
		Configuration cfg = new Configuration(Zone.autoZone());
		
		BucketManager bucketManager = new BucketManager(auth, cfg);
		
		String prefix = "craftwork/WJK";
		
		int limit = 1000;
		
		String delimiter = "";
		
		//列举空间文件列表
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
		while (fileListIterator.hasNext()) {
		    //处理获取的file list结果
		    FileInfo[] items = fileListIterator.next();
		    for (FileInfo item : items) {
		    	System.out.println(item.key);
		    }
		}
	}
	
	public static void main(String[] args) {
		new ListFiles().listFiles();
	}
}