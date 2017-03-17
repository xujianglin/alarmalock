package com.alarmclock.yhkr.alarmalock.webService;

/**
 * Created by dingl on 2016/12/10.
 */

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServiceUtils {
    // 访问的服务器是否由dotNet开发
    public static boolean isDotNet = true;
    // 线程池的大小
    private static int threadSize = 5;
    // 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
    private static ExecutorService threadPool = Executors.newFixedThreadPool(threadSize);
    // 连接响应标示
    public static final int SUCCESS_FLAG = 0;
    public static final int ERROR_FLAG = 1;

    /**
     * 调用WebService接口
     *
     * @param endPoint        WebService服务器地址
     * @param nameSpace       命名空间
     * @param methodName      WebService的调用方法名
     * @param mapParams       WebService的参数集合，可以为null
     * @param reponseCallBack 服务器响应接口
     */
    public static void call(final String endPoint,
                            final String nameSpace,
                            final String methodName,
                            Map<String, Object> mapParams,
                            final Response reponseCallBack) {
        // 1.创建HttpTransportSE对象，传递WebService服务器地址
        final MyHttpTransportSE transport = new MyHttpTransportSE(endPoint);
        transport.debug = true;
        // 2.创建SoapObject对象用于传递请求参数
        final SoapObject request = constructSoapObject(nameSpace, methodName,mapParams);

        // 3.实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = isDotNet; // 设置是否调用的是.Net开发的WebService
        envelope.bodyOut=request;
        envelope.setOutputSoapObject(request);

        // 4.用于子线程与主线程通信的Handler，网络请求成功时会在子线程发送一个消息，然后在主线程上接收
        final Handler responseHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 根据消息的arg1值判断调用哪个接口
                if (msg.arg1 == SUCCESS_FLAG) {
                    String jsonString = String.valueOf(msg.obj);
                    JSONObject obj;
                    try {
                        obj = new JSONObject(jsonString);
                        reponseCallBack.onSuccess(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    reponseCallBack.onError((Exception) msg.obj);
                }
            }

        };

        // 5.提交一个子线程到线程池并在此线种内调用WebService
        if (threadPool == null || threadPool.isShutdown())
            threadPool = Executors.newFixedThreadPool(threadSize);
        threadPool.submit(new Runnable() {

            @Override
            public void run() {
                //SoapObject result = null;
                String jsonString=null;
                try {
                    // 解决EOFException
                    System.setProperty("http.keepAlive", "false");
                    // 连接服务器
                    String soapAction=nameSpace+methodName;
                    transport.call(soapAction, envelope);
                    if (transport.getResponseString() != null || transport.getResponseString()!="") {
                        // 获取服务器响应返回的SoapObject
                        //result = (SoapObject) envelope.bodyIn;
                        jsonString=transport.getResponseString();
                        Log.i("YUAN","服务器响应"+jsonString);
                    }
                } catch (IOException e) {
                    // 当call方法的第一个参数为null时会有一定的概念抛IO异常
                    // 因此需要需要捕捉此异常后用命名空间加方法名作为参数重新连接
                    e.printStackTrace();
                    try {
                        /*
                        transport.call(nameSpace + methodName, envelope);
                        if (envelope.getResponse() != null) {
                            // 获取服务器响应返回的SoapObject
                            result = (SoapObject) envelope.bodyIn;
                        }*/
                        transport.call(nameSpace + methodName, envelope);
                        if (transport.getResponseString() != null || transport.getResponseString()!="") {
                            jsonString=transport.getResponseString();
                        }
                    } catch (Exception e1) {
                        // e1.printStackTrace();
                        responseHandler.sendMessage(responseHandler.obtainMessage(0, ERROR_FLAG, 0, e1));
                    }
                } catch (XmlPullParserException e) {
                    // e.printStackTrace();
                    responseHandler.sendMessage(responseHandler.obtainMessage(0, ERROR_FLAG, 0, e));
                } finally {
                    // 将获取的消息利用Handler发送到主线程
                    responseHandler.sendMessage(responseHandler.obtainMessage(0, SUCCESS_FLAG, 0, jsonString));
                }
            }
        });
    }

    /**
     * 设置线程池的大小
     *
     * @param threadSize
     */
    public static void setThreadSize(int threadSize) {
        WebServiceUtils.threadSize = threadSize;
        threadPool.shutdownNow();
        threadPool = Executors.newFixedThreadPool(WebServiceUtils.threadSize);
    }

    /*private static SoapObject constructSoapObject(String nameSpace,String methodName, Map<String, Object> params) {
        SoapObject request = new SoapObject(nameSpace, methodName);
        if (params != null && !params.isEmpty()) {
            for (Iterator<Map.Entry<String, Object>> it = params.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> e = it.next();
                if (e.getValue() instanceof byte[]) {
                    byte[] d = (byte[]) e.getValue();
                    String data = new String(Base64.encode(d));
                    request.addProperty(e.getKey(), data);
                    //request.addProperty(e.getKey(), new SoapPrimitive(
                            //SoapEnvelope.ENC, "base64Binary", data));
                } else {
                    request.addProperty(e.getKey().toString(), e.getValue());
                }
            }
        }
        return request;
    }*/

    private static SoapObject constructSoapObject(String nameSpace, String methodName, Map<String, Object> params) {
        SoapObject request = new SoapObject(nameSpace, methodName);
        if (params != null && !params.isEmpty()) {
            for (Iterator<Map.Entry<String, Object>> it = params.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> e = it.next();
                if (e.getValue() instanceof byte[]) {
                    byte[] d = (byte[]) e.getValue();
                    String data = new String(Base64.encode(d));
                    request.addProperty(e.getKey(), data);
                    //request.addProperty(e.getKey(), new SoapPrimitive(
                    //SoapEnvelope.ENC, "base64Binary", data));
                }else if(e.getValue() instanceof Map)
                {
                    SoapObject requestInner = new SoapObject(nameSpace, e.getKey());
                    //SoapObject requestInner = new SoapObject(nameSpace, methodName);
                    Map<Object, String> parasInner=(Map<Object, String>)e.getValue();
                    for (Iterator<Map.Entry<Object, String>> itInner = parasInner.entrySet()
                            .iterator(); itInner.hasNext();) {
                        Map.Entry<Object, String> eInner = itInner.next();
                        requestInner.addProperty(eInner.getValue().toString(), eInner.getKey().toString());
                    }
                    /*PropertyInfo pInfo = new PropertyInfo();
                    pInfo.setName(e.getKey().toString());
                    pInfo.setValue(requestInner);
                    request.addProperty(pInfo);*/
                    request.addSoapObject(requestInner);}
                else {
                    request.addProperty(e.getKey().toString(), e.getValue());
                }
            }
        }
        return request;
    }


    /**
     * 服务器响应接口，在响应后需要回调此接口
     */
    public interface Response {
        public void onSuccess(JSONObject result);

        public void onError(Exception e);
    }

    public static void callTB(final String endPoint,
                              final String nameSpace,
                              final String methodName,
                              Map<String, Object> mapParams,
                              final Response reponseCallBack) throws JSONException {
        // 1.创建HttpTransportSE对象，传递WebService服务器地址
        final MyHttpTransportSE transport = new MyHttpTransportSE(endPoint);
        transport.debug = true;
        // 2.创建SoapObject对象用于传递请求参数
        final SoapObject request = constructSoapObject(nameSpace, methodName, mapParams);

        // 3.实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = isDotNet; // 设置是否调用的是.Net开发的WebService
        envelope.bodyOut = request;
        envelope.setOutputSoapObject(request);
        //SoapObject result = null;
        String jsonString = null;
        try {
            // 解决EOFException
            System.setProperty("http.keepAlive", "false");
            // 连接服务器
            String soapAction = nameSpace + methodName;
            transport.call(soapAction, envelope);
            if (transport.getResponseString() != null || transport.getResponseString() != "") {
                // 获取服务器响应返回的SoapObject
                //result = (SoapObject) envelope.bodyIn;
                jsonString = transport.getResponseString();
                Log.i("YUAN", "服务器响应" + jsonString);
            }
        } catch (IOException e) {
            // 当call方法的第一个参数为null时会有一定的概念抛IO异常
            // 因此需要需要捕捉此异常后用命名空间加方法名作为参数重新连接
            e.printStackTrace();
            try {
                        /*
                        transport.call(nameSpace + methodName, envelope);
                        if (envelope.getResponse() != null) {
                            // 获取服务器响应返回的SoapObject
                            result = (SoapObject) envelope.bodyIn;
                        }*/
                transport.call(nameSpace + methodName, envelope);
                if (transport.getResponseString() != null || transport.getResponseString() != "") {
                    jsonString = transport.getResponseString();

                }
            } catch (Exception e1) {
                // e1.printStackTrace();
                reponseCallBack.onError(e1);
            }
        } catch (XmlPullParserException e) {
            // e.printStackTrace();
            reponseCallBack.onError(e);
        } finally {
            JSONObject obj = null;
            obj = new JSONObject(jsonString);
            reponseCallBack.onSuccess(obj);
        }
    }

}
