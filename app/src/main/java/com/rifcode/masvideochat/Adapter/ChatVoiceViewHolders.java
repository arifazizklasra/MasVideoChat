package com.rifcode.masvideochat.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rifcode.masvideochat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatVoiceViewHolders extends RecyclerView.ViewHolder{

    public TextView tvInMessageTextChat,tvOutMessageTextChat;
    public LinearLayout lyMeesageInText,lyMeesageOutText;
    public CircleImageView imgvProfilePicChatVoice;

    public ChatVoiceViewHolders(View itemView) {
        super(itemView);

        //textview
        tvInMessageTextChat=itemView.findViewById(R.id.tvInMessageTextChat);
        tvOutMessageTextChat=itemView.findViewById(R.id.tvOutMessageTextChat);

        //ly in
        lyMeesageInText=itemView.findViewById(R.id.lyMeesageInText);
        //ly out
        lyMeesageOutText=itemView.findViewById(R.id.lyMeesageOutText);
        imgvProfilePicChatVoice=itemView.findViewById(R.id.imgvProfilePicChatVoice);

    }

}
