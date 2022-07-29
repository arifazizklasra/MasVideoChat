package com.rifcode.masvideochat.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rifcode.masvideochat.R;

public class GiftViewHolders extends RecyclerView.ViewHolder{

    public TextView tv_gift_name;
    public ImageView iv_gift_icon;

    public GiftViewHolders(View itemView) {
        super(itemView);

        //textview
        tv_gift_name=itemView.findViewById(R.id.tv_gift_name);

        //imageview
        iv_gift_icon=itemView.findViewById(R.id.iv_gift_icon);

    }

}
