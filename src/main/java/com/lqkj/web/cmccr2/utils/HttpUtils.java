package com.lqkj.web.cmccr2.utils;


import okhttp3.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * http工具类
 */
public class HttpUtils {
    private static String baseUrl = "";

    private static OkHttpClient client;

    private static CacheControl cacheControl;

    private static HttpUtils httpUtils;
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static void init(String baseUrl) {
        HttpUtils.baseUrl = baseUrl;
    }

    public synchronized static HttpUtils getInstance() {
        if (client == null) {

            //设置缓存
            Cache cache = new Cache(new File(""), 1024 * 1024 * 50);

            //设置超时时间
            client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                    .cache(cache)
                    .connectTimeout(10,TimeUnit.SECONDS)
                    .build();
        }
        if (httpUtils == null) {
            httpUtils = new HttpUtils();

            /**
             * 缓存设置
             */
            cacheControl = new CacheControl.Builder()
                    .maxAge(30, TimeUnit.DAYS)
                    .build();
        }
        
        return httpUtils;
    }

    public OkHttpClient getClient() {
        return client;
    }





    /**
     * 结束所有链接
     */
    public void cancelAll(){
        if (client!=null){
            client.dispatcher().cancelAll();
        }
    }

    private static final int TIMEOUT_CONNECT = 5; //5秒
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7; //7天

    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        public Response intercept(Chain chain) throws IOException {
            //获取retrofit @headers里面的参数，参数可以自己定义，在本例我自己定义的是cache，跟@headers里面对应就可以了
            String cache = chain.request().header("cache");
            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            //如果cacheControl为空，就让他TIMEOUT_CONNECT秒的缓存，本例是5秒，方便观察。注意这里的cacheControl是服务器返回的
            if (cacheControl == null) {
                //如果cache没值，缓存时间为TIMEOUT_CONNECT，有的话就为cache的值
                if (cache == null || "".equals(cache)) {
                    cache = TIMEOUT_CONNECT + "";
                }
                if (!cache.equals("0")) {
                    originalResponse = originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + cache)
                            .build();
                } else {
                    originalResponse = originalResponse.newBuilder()
                            .header("Cache-Control", "no-cache")
                            .build();
                }
                return originalResponse;
            } else {
                return originalResponse;
            }
        }
    };

 
    public String uploadFile(String url,File file,String fileName,Map<String,String> para){
    	MultipartBody.Builder build = new MultipartBody.Builder()
    	.setType(MultipartBody.FORM)
    	.addFormDataPart("upload", fileName, RequestBody.create(MediaType.parse("application/octet-stream"),file));
    	for (String key : para.keySet()) {
			String val=para.get(key);
			build.addFormDataPart(key, val);
		}
    	
    	Request.Builder builder = new Request.Builder()
        .url(url)
        .post(build.build())
        .cacheControl(cacheControl);
    	
    	try {
			return client.newCall(builder.build()).execute().body().string();
		} catch (Exception e) {
			System.out.println("请求异常");
			e.printStackTrace();
		}
		return null;
    }
    
    public String postRequest(String url,Map<String,String> para){
    	MultipartBody.Builder build = new MultipartBody.Builder()
    	.setType(MultipartBody.FORM);
    	for (String key : para.keySet()) {
			String val=para.get(key);
			build.addFormDataPart(key, val);
		}
    	
    	Request.Builder builder = new Request.Builder()
        .url(url)
        .post(build.build())
        .cacheControl(cacheControl);
    	
    	try {
			return client.newCall(builder.build()).execute().body().string();
		} catch (Exception e) {
			System.out.println("请求异常");
			e.printStackTrace();
		}
		return null;
    }
    public Response getRequest(String url, int out_time,Map<String,String> header){

        Headers headers = Headers.of(header);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get()
                .headers(headers)
                .cacheControl(cacheControl);

        try {
            return client.newCall(builder.build()).execute();
        } catch (Exception e) {
            System.out.println("请求异常");
            e.printStackTrace();
        }
        return null;
    }


    public String postJson(String url,String json){
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody)
                .cacheControl(cacheControl);

        try {
            return client.newCall(builder.build()).execute().body().string();
        } catch (Exception e) {
            System.out.println("请求异常");
            e.printStackTrace();
        }
        return null;
    }


	public static void inputstreamtofile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void main(String[] args) {

        Map<String,String> para = new HashMap<String, String>();
        para.put("grant_type","client_credentials");
        para.put("client_id","vKCWVo1OIVCA7fUlGuwlNMve");
        para.put("client_secret","5440544069c55a7aa158ae4b386defac");
        String ret = HttpUtils.getInstance().postRequest("https://openapi.baidu.com/oauth/2.0/token",para);
        System.out.println(ret);
    }



}
