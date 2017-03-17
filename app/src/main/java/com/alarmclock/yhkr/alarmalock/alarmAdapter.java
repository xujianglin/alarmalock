package com.alarmclock.yhkr.alarmalock;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class alarmAdapter extends BaseAdapter {
    List<AlarmAlock> list=new ArrayList<AlarmAlock>();
    private Context mcontext;

    public alarmAdapter(List<AlarmAlock> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            View view =inflater.inflate(R.layout.alarm_item,null);
            holder=new ViewHolder();
            holder.alarm_content=(TextView) convertView.findViewById(R.id.alarm_content);
            holder.alarm_state=(TextView) convertView.findViewById(R.id.alarm_state);
            holder.alarm_time=(TextView) convertView.findViewById(R.id.alarm_time);
            convertView.setTag(holder);
        }else{
           holder=(ViewHolder) convertView.getTag();
        }
        holder.alarm_time.setText(list.get(position).getAlarmTime());
        holder.alarm_content.setText(list.get(position).getAlarmContent());
        if(!list.get(position).getAlarmState().equals("闹钟完成")){
            holder.alarm_state.setTextColor(Color.RED);
        }else{
            holder.alarm_state.setTextColor(Color.GREEN);
        }
        holder.alarm_state.setText(list.get(position).getAlarmState());
        return convertView;
    }
   class ViewHolder{
         TextView alarm_time;
       TextView alarm_content;
       TextView alarm_state;
   };
}
