package com.sdzhking.shortcut;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class AliImageLoader extends BaseImageDownloader {
    private static boolean isgetToken = false;
//    private Context mcontext;
    // private String uri;

    public AliImageLoader(Context context) {
        super(context);
//        mcontext = context;
    }

    /*
    @Override
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        if(TextUtils.isEmpty(imageUri))
            return null;

        if(imageUri.startsWith(OSSManager.urlpre)){
            String object = imageUri.substring(OSSManager.urlpre.length());
            String xOssProcess = null;
            if(object.contains("?x-oss-process=")){
                int index = object.indexOf("?x-oss-process=");
                xOssProcess = object.substring(index + 15);
                object = object.substring(0,index);
            }
            OSSManager ossManager = OSSManager.getInstance(context);
            InputStream inputStream = ossManager.getFileSync(object, xOssProcess);
            if(inputStream !=null){
                return inputStream;
            }else {
                return super.getStreamFromNetwork(imageUri, extra);
            }
        }else {
            return super.getStreamFromNetwork(imageUri, extra);
        }
    }
    */

    @Override
    public InputStream getStream(String imageUri, Object extra) throws IOException {
        if(imageUri !=null){
//            String name = Thread.currentThread().getName();
//            Log.w("AliImageLoader", "getStreamFromOtherSource  pid ="+ name);

            boolean isOssFile =true;
//            if(imageUri.startsWith("avatar/")){
//                isOssFile =true;
//            }else if(imageUri.startsWith("group_avatar/")){
//                isOssFile =true;
//            }else if(imageUri.startsWith("task/")){
//                isOssFile =true;
//            }else if(imageUri.startsWith("workgroup_notice/")){
//                isOssFile =true;
//            }else if(imageUri.startsWith("userlog/")){
//                isOssFile =true;
//            }else if(imageUri.startsWith("approve/")){
//                isOssFile =true;
//            }else if(imageUri.startsWith("workreport/")){
//                isOssFile =true;
//            }else if(imageUri.startsWith("note/")){
//                isOssFile =true;
//            }
            if(imageUri.length() <5){
                isOssFile =false;
            }else if(imageUri.startsWith("/")){
                isOssFile =false;
            }else if(imageUri.startsWith("file:")){
                isOssFile =false;
            }else if(imageUri.startsWith("assets:") || imageUri.startsWith("drawable:") ){
                isOssFile =false;
            }else if(imageUri.startsWith("http:")){
                isOssFile =false;
            }else if(imageUri.startsWith("www")){
                isOssFile =false;
            }else if(imageUri.startsWith("https:")){
                isOssFile =false;
            }

            if(isOssFile){
                return getStreamFromOtherSource(imageUri, extra);
            }
        }
        return super.getStream(imageUri, extra);
    }

    @Override
    protected InputStream getStreamFromOtherSource(String imageUri, Object extra) throws IOException {
        if("null".equals(imageUri)){
            return null;
        }

        String object = imageUri;
        String xOssProcess = null;

        if(object.contains("?x-oss-process=")){
            int index = object.indexOf("?x-oss-process=");
            xOssProcess = object.substring(index + 15);
            object = object.substring(0,index);
        }
//        OSSManager ossManager = OSSManager.getInstance(context);
//        InputStream inputStream = ossManager.getFileSync(object, xOssProcess);

        return null;
    }

}