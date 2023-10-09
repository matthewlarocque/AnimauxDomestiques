package com.izaguirre.mascotas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.izaguirre.mascotas.R;

import java.util.ArrayList;

public class CanelAdapter extends BaseAdapter {

    private ArrayList<NeufGrilles> channelList;
    private LayoutInflater layoutInflater;

    public CanelAdapter(ArrayList<NeufGrilles> list, Context context){
        channelList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return channelList.size();
    }

    @Override
    public Object getItem(int position) {
        return channelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_item,null);
            holder = new ViewHolder();
            holder.imgChannel = convertView.findViewById(R.id.neuf_img);
            holder.decChannel = convertView.findViewById(R.id.neuf_dec);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        //设置图标和文字
        NeufGrilles channel = channelList.get(position);
        if(channel != null){
            holder.decChannel.setText(channel.getDec());
            holder.imgChannel.setImageResource(channel.getImgId());
        }
        return convertView;
    }

    class ViewHolder{
        ImageView imgChannel;
        TextView decChannel;
    }
}