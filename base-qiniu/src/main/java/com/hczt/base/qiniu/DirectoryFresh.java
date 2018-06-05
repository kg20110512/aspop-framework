package com.hczt.base.qiniu;

import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.util.Auth;

/**
 * 刷新CDN缓存目录
 * @author sundonghe
 *
 */
public class DirectoryFresh {
private static String ACCESS_KEY = "l3Gg0eTWFhsqALKyIygjszxlaEACula5JdIdSETi";
	
	private static String SECRET_KEY = "a_PNIWSausmTdlPPNSUrI5vd0BQdcQBIuHeS2cqm";
	
	// 获取授权对象
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	
	CdnManager c = new CdnManager(auth);
	
	public void refreshDirectory(String[] dirs) {
		try {
			CdnResult.RefreshResult result = c.refreshDirs(dirs);
			System.out.println(result.code);
		} catch (QiniuException e) {
			System.err.println(e.response.toString());
		}
	}
	
	public static void main(String[] args) {
		String[] dirs = new String[] {
				"http://otzju8ivi.bkt.clouddn.com/craftwork/MCY/"
		};
		
		new DirectoryFresh().refreshDirectory(dirs);
	}
}