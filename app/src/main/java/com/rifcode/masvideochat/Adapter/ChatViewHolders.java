package com.rifcode.masvideochat.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rifcode.masvideochat.R;

public class ChatViewHolders extends RecyclerView.ViewHolder{

    public TextView tvInMessageTextChat,tvOutMessageTextChat;
    public LinearLayout lyMeesageInText,lyMeesageOutText;

    public ChatViewHolders(View itemView) {
        super(itemView);

        //textview
        tvInMessageTextChat=itemView.findViewById(R.id.tvInMessageTextChat);
        tvOutMessageTextChat=itemView.findViewById(R.id.tvOutMessageTextChat);

        //ly in
        lyMeesageInText=itemView.findViewById(R.id.lyMeesageInText);
        //ly out
        lyMeesageOutText=itemView.findViewById(R.id.lyMeesageOutText);

    }

}
