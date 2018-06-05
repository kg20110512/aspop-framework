package com.hczt.base.qiniu;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

public class FileMove {
	
	private static String ACCESS_KEY = "Ci_VFCYmKwFGWgbIEFxTsGNd69amAaXtwiZr9vIR";
	private static String SECRET_KEY = "XSQNWYzsdcaiE9r68WyQQj13t1RpjuauMR_vx2JG";
	
	public void fileMove() {
		// 获取授权对象
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		
		String bucket = "images";
		
		Configuration cfg = new Configuration(Zone.autoZone());
		
		BucketManager bucketManager = new BucketManager(auth, cfg);
		
		String prefix = "craftwork";
		
		int limit = 1000;
		
		String delimiter = "";
		
		//列举空间文件列表
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
		
		try {
			while (fileListIterator.hasNext()) {
			    //处理获取的file list结果
			    FileInfo[] items = fileListIterator.next();
			    for (FileInfo item : items) {
			    	bucketManager.move(bucket, item.key, bucket, "lsoms/"+item.key);
			    	System.out.println(item.key + "修改为:"+"lsoms/"+item.key+"完成");
			    }
			}
		} catch (Exception e) {
			System.out.println("发生异常");
		}
	}
	
	public static void main(String[] args) {
		new FileMove().fileMove();
	}
}
