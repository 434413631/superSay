/*
 * GridAdapter
 *
 * Version 1.0.0
 *
 * 2013-07-03
 *
 * Copyright (c) 2012, T-Systems.
 * All rights reserved.
 */
package android.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.entities.message;
import android.view.ext.R;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Declaration.
 *
 * @author jian.wang@t-systems.com
 */
public class GridAdapter extends BaseAdapter{
    private List<message> msg;
    private LayoutInflater layoutInflater;

    public GridAdapter(Context context,List<message> msg) {
        this.msg=msg;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return msg.size();
    }

    @Override
    public Object getItem(int i) {
        return msg.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=layoutInflater.inflate(R.layout.gridlayout,null);
        return view;
    }
}
