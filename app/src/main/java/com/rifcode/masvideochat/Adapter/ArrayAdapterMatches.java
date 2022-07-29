package com.rifcode.masvideochat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rifcode.masvideochat.Models.CardMatches;
import com.rifcode.masvideochat.R;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;


public class ArrayAdapterMatches extends RecyclerView.Adapter<ArrayAdapterMatches.ViewHolder> {


    private Context mContext;
    private ViewPagerImagesAdapter vpImagesAdapter;
    private CardMatches card_item;
    private ArrayList<CardMatches> cardMatches;



    public ArrayAdapterMatches(Context mContext, CardMatches card_item,ArrayList<CardMatches> mcardMatches) {
        this.mContext = mContext;
        this.card_item = card_item;
        this.cardMatches = mcardMatches;
    }

    @NonNull
    @Override
    public ArrayAdapterMatches.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe_card, parent, false);
        ArrayAdapterMatches.ViewHolder holder = new ArrayAdapterMatches.ViewHolder(view);
        return holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ArrayAdapterMatches.ViewHolder holder, final int position) {



        vpImagesAdapter = new ViewPagerImagesAdapter(mContext, cardMatches.get(position).getImages());
        holder.viewpager.setAdapter(vpImagesAdapter);
        holder.indicator_view.setViewPager(holder.viewpager);

        holder.tv_bar_name_age.setText(cardMatches.get(position).getUsername()+", "+cardMatches.get(position).getAge());
        holder.tv_pop_name_age.setText(cardMatches.get(position).getUsername()+", "+cardMatches.get(position).getAge());

        holder.tv_pop_education.setText(cardMatches.get(position).getSchool());
        holder.tv_pop_career.setText(cardMatches.get(position).getJob());
        holder.tv_pop_description.setText(cardMatches.get(position).getAbout());
        if(cardMatches.get(position).getCity()==null)
            holder.tv_pop_location.setVisibility(View.GONE);
        else
        holder.tv_pop_location.setText(cardMatches.get(position).getCity()+", "+cardMatches.get(position).getCountry());

        if(cardMatches.get(position).getGender().equals("guy")){
            holder.tv_bar_name_age.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_common_male_selected, 0);
            holder.tv_pop_name_age.setCompoundDrawablesWithIntrinsicBounds( R.drawable.icon_common_male_selected, 0,0, 0);

        }else{
            holder.tv_bar_name_age.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_common_female_selected, 0);
            holder.tv_pop_name_age.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_common_female_selected, 0, 0, 0);

        }

        holder.lyRightSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.viewpager.setCurrentItem(holder.viewpager.getCurrentItem() + 1);
            }
        });

        holder.lyLeftSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.viewpager.setCurrentItem(holder.viewpager.getCurrentItem() - 1);
            }
        });


        holder.imgvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.pop_card.setVisibility(View.VISIBLE);
                holder.rl_default_info_content.setVisibility(View.GONE);
            }
        });

        holder.iv_pop_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.pop_card.setVisibility(View.GONE);
                holder.rl_default_info_content.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cardMatches.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        LinePageIndicator indicator_view;
        ViewPager viewpager;
        TextView tv_bar_name_age,tv_pop_name_age,tv_pop_education,tv_pop_career,tv_pop_description,tv_pop_location;
        ImageView imgvInfo,iv_pop_arrow;
        CardView pop_card;
        RelativeLayout rl_default_info_content;
        LinearLayout lyLeftSwipe,lyRightSwipe;

        public ViewHolder(View itemView) {
            super(itemView);
            viewpager = itemView.findViewById(R.id.vpPreviewProfile);
            indicator_view = itemView.findViewById(R.id.indicator);
            tv_bar_name_age = itemView.findViewById(R.id.tv_bar_name_age);
            imgvInfo = itemView.findViewById(R.id.iv_info_icon);
            pop_card = itemView.findViewById(R.id.pop_card);
            iv_pop_arrow = itemView.findViewById(R.id.iv_pop_arrow);
            tv_pop_name_age = itemView.findViewById(R.id.tv_pop_name_age);
            tv_pop_education = itemView.findViewById(R.id.tv_pop_education);
            tv_pop_career = itemView.findViewById(R.id.tv_pop_career);
            tv_pop_description = itemView.findViewById(R.id.tv_pop_description);
            tv_pop_location = itemView.findViewById(R.id.tv_pop_location);
            rl_default_info_content = itemView.findViewById(R.id.rl_default_info_content);
            lyLeftSwipe = itemView.findViewById(R.id.lyLeftSwip);
            lyRightSwipe = itemView.findViewById(R.id.lyRightSwip);
        }


    }



}
