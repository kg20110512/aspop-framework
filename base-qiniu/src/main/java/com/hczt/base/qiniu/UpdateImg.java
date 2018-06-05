package com.hczt.base.qiniu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛：覆盖上传
 * 
 * @author sundonghe
 *
 */
public class UpdateImg {

	private static String ACCESS_KEY = "l3Gg0eTWFhsqALKyIygjszxlaEACula5JdIdSETi";
	
	private static String SECRET_KEY = "a_PNIWSausmTdlPPNSUrI5vd0BQdcQBIuHeS2cqm";
	
	// 获取授权对象
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	// 第一种方式: 指定具体的要上传的zone
	// 注：该具体指定的方式和以下自动识别的方式选择其一即可
	// 要上传的空间(bucket)的存储区域为华东时
	// Zone z = Zone.zone0();
	// 要上传的空间(bucket)的存储区域为华北时
	// Zone z = Zone.zone1();
	// 要上传的空间(bucket)的存储区域为华南时
	// Zone z = Zone.zone2();

	// 第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
	Zone zone = Zone.autoZone();

	Configuration config = new Configuration(zone);
	UploadManager uploadManager = new UploadManager(config);

	/**
	 * 获取凭证
	 * 
	 * @param bucketName
	 *            空间名称
	 * @return
	 */
	public String getUpToken(String bucketName, String key) {
		// insertOnly 如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1
		return auth.uploadToken(bucketName, key, 3600, new StringMap().put("insertOnly", 0));
	}

	/**
	 * 覆盖上传
	 * 
	 * @param path
	 *            上传文件路径
	 * @param bucketName
	 *            空间名
	 * @param key
	 *            文件名
	 */
	public void overrideUpload(String path, String bucketName, String key) {
		try {
			String token = getUpToken(bucketName, key);// 获取 token
			Response response = uploadManager.put(path, key, token);// 执行上传，通过token来识别
																	// 该上传是“覆盖上传”
			System.out.println(response.toString());
		} catch (QiniuException e) {
			System.out.println(e.response.statusCode);
			e.printStackTrace();
		}
	}

	public static List<File> getFileList(String strPath) {
		List<File> filelist  = new ArrayList<>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("png")) { // 判断文件名是否以.avi结尾
//                    String strFileName = files[i].getAbsolutePath();
//                    System.out.println("---" + strFileName);
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
        return filelist;
    }
	
	/**
	 * 主函数：测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 上传文件的路径，因为在Mac下，所以路径和windows下不同
		String filePath = "/Users/sundonghe/Desktop/new/";
		// 要上传的空间
		String bucketName = "images";
		
		List<File> fileList = getFileList(filePath);
		
//		fileList.forEach(file -> System.out.println(file.getAbsolutePath()));
		fileList.forEach(file -> new UpdateImg().overrideUpload(file.getAbsolutePath(), bucketName, "craftwork/WCY/"+file.getName()));
		
		// 上传到七牛后保存的文件名
//		String key = "admin_3.jpg";

//		new UpdateImg().overrideUpload(filePath, bucketName, key);
	}
}