package com.nanrailgun.springbootoss.service;

import com.nanrailgun.springbootoss.config.QiNiuConfig;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nanrailgun.springbootoss.config.QiNiuConfig.*;

@Service
public class OssService {

    private static final String upToken;

    private static final UploadManager uploadManager=QiNiuConfig.getUploadManager();


    static {
        Auth auth = Auth.create(accessKey, secretKey);
        upToken = auth.uploadToken(bucket);
    }


    /**
     * 上传文件
     * @param multipartFile
     * @throws IOException
     */
    public boolean upload(MultipartFile multipartFile) throws IOException {
        byte[] bytes=multipartFile.getBytes();
        String name=multipartFile.getOriginalFilename();
        int i = name.lastIndexOf(".");
        String key=name.substring(0,i);

        if (!isImageExist(key)) return false;


        try {
            Response response = uploadManager.put(bytes, key, upToken);
            System.out.println(response.bodyString());
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                System.out.println("QiNiu_ex2");
            }
        }
        return true;
    }

    /**
     * 获取文件列表
     * @return
     */
    public List<FileInfo[]> getImageList(){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释

        String accessKey = "niBP4srpbhIhMZ8LOci0qxBfeFQneSLG__IDwfy-";
        String secretKey = "8nC1KwJ-UBY7KAdp3C09QaI0FZC4zLKB7g_gENRB";

        String bucket = "nanrailguntest1";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";


        List<FileInfo[]> list=new ArrayList<>();
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            list.add(items);
        }
        return list;
    }

    /**
     * 判断同名图片是否存在
     * @param key
     * @return
     */
    public boolean isImageExist(String key){

        List<FileInfo[]> list=getImageList();
        for (FileInfo[] fileInfos : list) {
            for (FileInfo fileInfo : fileInfos) {
                if (fileInfo.key.equals(key))
                    return false;
            }
        }
        return true;
    }

    /**
     * 获取key
     * @return
     */
    public List<Map<String,String>> getKeyMap(){
        List<Map<String,String>> keys=new ArrayList<>();
        String url="http://q76ujmt72.bkt.clouddn.com/";

        List<FileInfo[]> imageList = getImageList();
        for (FileInfo[] fileInfos : imageList) {
            for (FileInfo info : fileInfos) {
                Map<String,String> keyMap=new HashMap<>();
                keyMap.put("src",url+info.key);
                keyMap.put("key",info.key);
                keys.add(keyMap);
            }
        }
        return keys;
    }


    /**
     * 删除
     * @param image_name
     * @return
     */
    public boolean deleteKeys(String image_name){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.autoZone());
        //...其他参数参考类注释

        String accessKey = "niBP4srpbhIhMZ8LOci0qxBfeFQneSLG__IDwfy-";
        String secretKey = "8nC1KwJ-UBY7KAdp3C09QaI0FZC4zLKB7g_gENRB";

        String bucket = "nanrailguntest1";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        try {
            //单次批量请求的文件数量不得超过1000
            String[] keyList = new String[]{
                    image_name
            };
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                System.out.print(key + "\t");
                if (status.code == 200) {
                    //System.out.println("delete success");
                    return true;
                } else {
                    //System.out.println(status.data.error);
                    return false;
                }
            }
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
            return false;
        }
        return false;
    }

}
