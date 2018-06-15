package com.dd.sdk.netbean;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/15 15:57
 * @change
 * @class describe
 */

public class VideoPictureBean implements Serializable {
    /**
     * 参数的名称
     */
    private String mName;
    /**
     * 文件名
     */
    private String mFileName;
    /**
     * 文件的 mime，需要根据文档查询
     */
    private String mMime;

    /**
     * 水桶名
     * @return
     */
    private String bucketName;

    private String   file;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public String getMime() {
        return mMime;
    }

    public void setMime(String mime) {
        mMime = mime;
    }

    // 对图片进行二进制转换
    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            FileInputStream fis=new FileInputStream(file);
            byte[] buff=new byte[1024];
            int count=0;
            while((count=fis.read(buff))!=-1){
                bos.write(buff);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }
}
