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
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.entities.message;
import android.view.ext.R;
import android.view.ext.SatelliteMenuActivity;
import android.view.tools.AsyncImageLoader1;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Declaration.
 *
 * @author jian.wang@t-systems.com
 */
public class GridAdapter extends BaseAdapter{
    private List<message> msg;
    private LayoutInflater layoutInflater;
    private AsyncImageLoader1 asyncImageLoader =new AsyncImageLoader1();
    private Context context;
    public GridAdapter(Context context,List<message> msg) {
        this.msg=msg;
        this.context=context;
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
        final message mes=msg.get(i);
        view=layoutInflater.inflate(R.layout.gridlayout,null);
        TextView textView=(TextView)view.findViewById(R.id.textView);
        ImageView imageView= (ImageView) view.findViewById(R.id.imageView);
        ImageView buy=(ImageView)view.findViewById(R.id.buy);
        final SatelliteMenuActivity satelliteMenuActivity= (SatelliteMenuActivity) context;
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                satelliteMenuActivity.buy(mes.getSaleurl());
            }
        });
        ImageView pingjia=(ImageView)view.findViewById(R.id.pingjia);
        pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                satelliteMenuActivity.pingjia();
            }
        });
        Drawable cachedImage = asyncImageLoader.loadDrawable(mes.getImgurl(),imageView, new AsyncImageLoader1.ImageCallback(){
                    public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl) {
                        imageView.setImageDrawable(imageDrawable);
                    }
                });
        if(cachedImage==null){
            imageView.setImageResource(R.drawable.loading);
        }
        else{
                imageView.setImageDrawable(cachedImage);
        }
                textView.setText(mes.getTitle());
        return view;
    }
}
