package com.khome.kdaydin.sudroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedHashMap;

/**
 * Created by kdaydin on 23.2.2017.
 */

public class HashMapAdapter extends BaseAdapter {

    private LinkedHashMap<String,String> mData = new LinkedHashMap<String,String>();
    private String[] mKeys;

    public HashMapAdapter(LinkedHashMap<String,String> data){
        mData = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String key = mKeys[position];

        if (convertView==null){
            //convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.support_simple_spinner_dropdown_item,parent);
            LayoutInflater vi = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        ((TextView)convertView).setText(key);

        return convertView;
    }
}
