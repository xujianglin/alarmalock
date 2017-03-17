package com.alarmclock.yhkr.alarmalock.webService;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/12/14.
 */

public class Backstage {

    public static void requestLogin(final String userName, final String password) {
        // 参数集合
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("username", userName + "");
        //mapParams.put("password", MD5Util.encrypt(password + ""));
        mapParams.put("password", password + "");
        mapParams.put("token", "LTAIEhAH00ppRBQZ");

        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace, WebServiceConfig.loginMethodName, mapParams,
                new WebServiceUtils.Response() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String code = result.getString("status");
                            if (code.equals("error")) {
                                String errorMsg = result.getString("info");
                                Log.i("login", errorMsg);
                            } else {
                                Log.i("YUAN","登陆成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        //dlg.dismiss();
                        Log.i("login", "failed");
                    }
                });
    }

    public  void requestRegister(final String userName, final String password) {
        // 参数集合
        //是否注册成功
        final Map<String,Object> mapParams=new HashMap<String,Object>();
        mapParams.put("username", userName + "");
        //mapParams.put("password", MD5Util.encrypt(password + ""));
        mapParams.put("password", password+"");
        mapParams.put("token", "LTAIEhAH00ppRBQZ");

        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace, WebServiceConfig.registerMethodName, mapParams,
                new WebServiceUtils.Response() {

                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String code=result.getString("status");
                            if (code.equals("failed"))
                            {
                                String errorMsg=result.getString("info");
                                Log.i("register",errorMsg);

                            }
                            else
                            {

                                Log.i("YUAN","注册成功");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        //dlg.dismiss();
                        Log.i("login","failed");
                    }
                });

    }

    public static  void requestChangePwd(final String userName, final String password, final String newpassword) {
        // 参数集合
        Map<String,Object> mapParams=new HashMap<String,Object>();
        mapParams.put("username", userName + "");
        //mapParams.put("password", MD5Util.encrypt(password + ""));
        mapParams.put("password", password+"");
        mapParams.put("newpassword", newpassword+"");
        mapParams.put("token", "LTAIEhAH00ppRBQZ");

        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace, WebServiceConfig.changePwdMethodName, mapParams,
                new WebServiceUtils.Response() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String code=result.getString("status");
                            if (code.equals("failed"))
                            {
                                String errorMsg=result.getString("info");
                                Log.i("changePwd",errorMsg);
                            }
                            else
                            {
                                Log.i("YUAN","修改密码成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        //dlg.dismiss();
                        Log.i("login","failed");
                    }
                });
    }

    public static void requestAddUserInfo(String username, String password, String realname, int age, double height, double weight,
                                          String address, String bloodtype, double bloodpressure, double bloodsugar, String diseasehistory,
                                          String medicalreq, String deviceid) {
        // 参数集合
        Map<String,Object> mapParams=new HashMap<String,Object>();
        mapParams.put("username", username);
        mapParams.put("password",MD5Util.encrypt(password));
        mapParams.put("realname", realname);
        mapParams.put("age",age);
        mapParams.put("height",height);//数据类型不对，更新失败
        mapParams.put("weight",weight);
        mapParams.put("address",address);
        mapParams.put("bloodtype",bloodtype);
        mapParams.put("bloodpressure",bloodpressure);
        mapParams.put("bloodsugar",bloodsugar);
        mapParams.put("diseasehistory",diseasehistory);
        mapParams.put("medicalreq",medicalreq);
        mapParams.put("deviceid",deviceid);
        mapParams.put("token", "LTAIEhAH00ppRBQZ");

        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace, WebServiceConfig.addUserInfoMethodName, mapParams,
                new WebServiceUtils.Response() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String code=result.getString("status");
                            if (code.equals("failed"))
                            {
                                String errorMsg=result.getString("info");
                                Log.i("addUserInfo",errorMsg);
                            }
                            else
                            {
                                Log.i("YUAN","添加用户信息成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        //dlg.dismiss();
                        Log.i("addUserInfo","failed");
                    }
                });
    }

    private static void requestQuery(final String userName, final String password, final String operate, final String function, final String time) {
        // 参数集合
        Map<String,Object> mapParams=new HashMap<String,Object>();
        mapParams.put("username", userName);
        mapParams.put("password", password);
        mapParams.put("operate", operate);
        mapParams.put("function", function);
        mapParams.put("time", time);
        mapParams.put("token", "LTAIEhAH00ppRBQZ");

        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace, WebServiceConfig.queryMethodName, mapParams,
                new WebServiceUtils.Response() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String code=result.getString("status");
                            if (code.equals("failed"))
                            {
                                String errorMsg=result.getString("info");
                                Log.i("query",errorMsg);
                            }
                            else
                            {
                                Log.i("YUAN","查询用户信息成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        //dlg.dismiss();
                        Log.i("query","failed");
                    }
                });
    }

    private static void requestUploadImage(final String userName, final String password, final String imagePath){
        byte[] imagedata= new byte[0];
        try {
            imagedata = image2Bytes(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 参数集合
        Map<String,Object> mapParams=new HashMap<String,Object>();
        mapParams.put("username", userName);
        mapParams.put("password", password);
        mapParams.put("imagedata", imagedata);
        mapParams.put("token", "LTAIEhAH00ppRBQZ");

        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace, WebServiceConfig.uploadImageMethodName, mapParams,
                new WebServiceUtils.Response() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String code=result.getString("status");
                            if (code.equals("failed"))
                            {
                                String errorMsg=result.getString("info");
                                Log.i("uploadimage",errorMsg);
                            }
                            else
                            {

                                Log.i("YUAN","图片上传成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        Log.i("uploadimage","failed");
                    }
                });
    }

    private static byte[] image2Bytes(String imgSrc) throws Exception
    {
        FileInputStream fin = new FileInputStream(new File(imgSrc));
        //图片需要限制大小
        byte[] bytes  = new byte[fin.available()];
        //将文件内容写入字节数组
        fin.read(bytes);
        fin.close();
        return bytes;

    }


    private static void requestimg() {
        // 参数集合
        List<String> list=new ArrayList<String>();
        list.add("http://xiaoyujiankan.oss-cn-shanghai.aliyuncs.com/img/qinshi/1482997868802/20161229155040.jpg");
        list.add("http://xiaoyujiankan.oss-cn-shanghai.aliyuncs.com/img/qinshi/1482997871149/20161229155053.jpg");
        list.add("http://xiaoyujiankan.oss-cn-shanghai.aliyuncs.com/img/qinshi/1482997865345/20161229155026.jpg");
        Map<String,Object> mapParams=new HashMap<String,Object>();
        mapParams.put("username", "qinshi");
        mapParams.put("password", "123456");
        mapParams.put("des","喻华科病历描述测试数据");
        mapParams.put("imagelist",list);
        mapParams.put("token", "LTAIEhAH00ppRBQZ");

        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace, WebServiceConfig.queryMethodName, mapParams,
                new WebServiceUtils.Response() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String code=result.getString("status");
                            if (code.equals("failed"))
                            {
                                String errorMsg=result.getString("info");
                                Log.i("YUAN",errorMsg);
                            }
                            else
                            {
                                Log.i("YUAN","病历上传成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        //dlg.dismiss();
                        Log.i("query","failed");
                    }
                });
    }
}
