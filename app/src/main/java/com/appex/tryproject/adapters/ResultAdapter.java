package com.appex.tryproject.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appex.tryproject.R;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("InflateParams")
public class ResultAdapter extends BaseAdapter {
    private class ViewHolder {
        TextView name;
        TextView category;
        TextView res;
    }

    private ViewHolder holder;
    ArrayList<HashMap<String, String>> Data = new ArrayList<HashMap<String, String>>();
    Context Cont;

    public ResultAdapter(Context context,
                         ArrayList<HashMap<String, String>> data) {
        Cont = context;
        Data = data;
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return Data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        HashMap<String, String> map = new HashMap<String, String>();
        final LayoutInflater mInflater = (LayoutInflater)
                Cont.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        map = Data.get(arg0);
        if (arg1 == null) {
            arg1 = mInflater.inflate(R.layout.res_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) arg1.findViewById(R.id.resultName);
            holder.category = (TextView) arg1.findViewById(R.id.resultCategory);
            holder.res = (TextView) arg1.findViewById(R.id.resultRes);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        holder.name.setText((String) map.get("Event"));
        holder.category.setText((String) map.get("Category"));
        holder.res.setText((String) map.get("Result"));
        return arg1;

    }
}