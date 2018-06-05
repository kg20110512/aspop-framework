package com.hczt.base.qiniu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by tom on 17/7/18.
 */
@RestController
@RequestMapping("/qiniu")
public class QiniuController {

    @Resource
    private QiniuManage qiniuManage;

    /**
     * 获取上传凭证
     *
     * @return
     */
    @GetMapping("/upToken")
    public String upToken(String bucket, String key) {
        return qiniuManage.upToken(bucket, key);
    }

}
