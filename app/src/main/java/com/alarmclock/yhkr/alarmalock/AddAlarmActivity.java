package com.alarmclock.yhkr.alarmalock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alarmclock.yhkr.alarmalock.webService.MD5Util;
import com.alarmclock.yhkr.alarmalock.webService.WebServiceConfig;
import com.alarmclock.yhkr.alarmalock.webService.WebServiceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class AddAlarmActivity extends Activity {
    private List<AlarmAlock> mData;
    private alarmAdapter adapter;
    private ListView alarm_listview;
    private Button add_alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        alarm_listview= (ListView) findViewById(R.id.alarm_listview);
        add_alarm= (Button) findViewById(R.id.add);
        mData=new ArrayList<AlarmAlock>();
        QueryAlarm();


        add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddAlarmActivity.this,MainActivity.class));
            }
        });

        alarm_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                new AlertDialog.Builder(AddAlarmActivity.this).setTitle("操作选项").setItems(new CharSequence[]{"删除", /*"修改"*/}, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                deleteAlarm(position);
                                break;
                           /* case 1:
                                alter(position);
                                break;*/

                            default:
                                break;
                        }

                    }
                }).setNegativeButton("取消", null).show();

                return true;
            }
        });

    }

    private void QueryAlarm() {
        Map<String,Object> mapParams=new HashMap<String,Object>();

        mapParams.put("username", "qinshi");
        mapParams.put("password", MD5Util.encrypt("123456")+"");
        mapParams.put("token", "LTAIEhAH00ppRBQZ");
        mapParams.put("operate", "get");
        mapParams.put("function", "alarmclock");
        mapParams.put("time", "");




        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace,WebServiceConfig.queryMethodName , mapParams,
                new WebServiceUtils.Response() {
                    @Override
                    public void onSuccess(JSONObject result) {

                        try {
                            String code=result.getString("status");
                            Log.i("YUAN","成功:"+code);
                            if (code.equals("failed"))
                            {
                                String errorMsg=result.getString("info");
                                Log.i("YUAN",errorMsg);
                            }
                            else
                            {
                                mData.clear();
                                JSONArray jsonarray=result.getJSONArray("data");
                                for (int i=0;i<jsonarray.length();i++){
                                    JSONObject json=jsonarray.getJSONObject(i);
                                    String ClockId=json.getString("ClockId");
                                    String ClockTime=json.getString("ClockTime");
                                    String Interval=json.getString("Interval");
                                    String Status=json.getString("Status");
                                    String Repeat=json.getString("Repeat");
                                    String minute=ClockTime.substring(0,2);
                                    String second=ClockTime.substring(3,5);

                                   AlarmAlock alarm_clock=new  AlarmAlock (ClockTime,Status,Repeat);
                                    alarm_clock.setAlarmId(ClockId);

                                    mData.add(alarm_clock);


                                }
                                adapter=new alarmAdapter(mData,AddAlarmActivity.this);
                                alarm_listview.setAdapter(adapter);





                                Log.i("YUAN","成功");
                                //String Version=result.getJSONArray("data").getJSONObject(0).getString("Version");

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

    private void deleteAlarm(int position){
        //删除方法

        String ClockId=mData.get(position).getAlarmId();
        Log.d("YUAN","闹钟ID"+ClockId);
        DeleteAlarmClock(ClockId);
        //saveAlarmList();

    }

    private  void DeleteAlarmClock(String ClockId )  {
        // 查询版本信息


        Map<String,Object> mapParams=new HashMap<String,Object>();
        mapParams.put("username", "qinshi");
        mapParams.put("password", MD5Util.encrypt("123456"));
        mapParams.put("token", "LTAIEhAH00ppRBQZ");
        mapParams.put("clockid",ClockId);





        WebServiceUtils.call(WebServiceConfig.endPoint,
                WebServiceConfig.nameSpace,WebServiceConfig.DeleteAlarmClockRecordMethodName, mapParams,
                new WebServiceUtils.Response() {
                    @Override
                    public void onSuccess(JSONObject result) {

                        try {
                            String code=result.getString("status");
                            Log.i("YUAN","成功:"+code);
                            if (code.equals("failed"))
                            {
                                String errorMsg=result.getString("info");
                                Log.i("YUAN",errorMsg);
                            }
                            else
                            {
                                adapter=new alarmAdapter(mData,AddAlarmActivity.this);
                                alarm_listview.setAdapter(adapter);
                                Toast.makeText(AddAlarmActivity.this,"删除闹钟成功",Toast.LENGTH_SHORT).show();
                                Log.i("YUAN","删除闹钟成功:");
                                QueryAlarm();




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
