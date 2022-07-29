package com.rifcode.masvideochat.View.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rifcode.masvideochat.Adapter.ArrayAdapterMatches;
import com.rifcode.masvideochat.Models.CardMatches;
import com.rifcode.masvideochat.Models.DataFire;
import com.rifcode.masvideochat.Models.Images;
import com.rifcode.masvideochat.R;
import com.rifcode.masvideochat.View.Chat.ChatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {


    private DataFire dataFire;
    private ImageView imgvRecentlySearchBack;
    private LinearLayoutManager mLayoutManagermax;
    private RecyclerView rcvRecentHorizontalmax;
    private ArrayAdapterMatches arrAdpMatches;
    private CardMatches item;
    private ArrayList<CardMatches> rowItemsMatches;
    private ArrayList<Images> arrayListImages;
    private TextView tv_send_common_msg;
    private String useridselected;
    private String TAG="activity_history";
    private AdView adViewBanner;
    private LinearLayout adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dataFire=new DataFire();
        widgets();
        rowItemsMatches = new ArrayList<CardMatches>();
        arrAdpMatches = new ArrayAdapterMatches(HistoryActivity.this, item,rowItemsMatches);
        rcvRecentHorizontalmax.setAdapter(arrAdpMatches);

        getRecentlySearchUsers();

        imgvRecentlySearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rcvRecentHorizontalmax.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int position = getCurrentItem();

                    try {
                            if (position >= 0) {
                                useridselected = rowItemsMatches.get(position).getUserId();
                            }

                    }catch(Exception e){
                        Log.d(TAG, "onScrollStateChanged: "+e.getMessage());
                    }

                }
            }
        });

        tv_send_common_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(useridselected==null){
                    useridselected =  rowItemsMatches.get(0).getUserId();
                }
                Intent chatiintent = new Intent(HistoryActivity.this,ChatActivity.class);
                chatiintent.putExtra("userIDvisited",useridselected);
                startActivity(chatiintent);
            }
        });
    }

    private int getCurrentItem(){
        return ((LinearLayoutManager)rcvRecentHorizontalmax.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private void setupAdapterCard(String keyid){

        dataFire.getDbRefUsers().child(keyid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotusers) {
                arrayListImages = new ArrayList<>();

                if (dataSnapshotusers.hasChild("username") && dataSnapshotusers.hasChild("photoProfile")) {

                    tv_send_common_msg.setVisibility(View.VISIBLE);
                    String userUsername = dataSnapshotusers.child("username").getValue().toString();
                    String school = dataSnapshotusers.child("school").getValue().toString();
                    String job = dataSnapshotusers.child("job").getValue().toString();
                    String userGender = dataSnapshotusers.child("gender").getValue().toString();
                    String userAge = dataSnapshotusers.child("age").getValue().toString();
                    String about = dataSnapshotusers.child("about").getValue().toString();

                    for (DataSnapshot serieSnapshot : dataSnapshotusers.child("images").getChildren()) {
                        Images images = serieSnapshot.getValue(Images.class);
                        Images img = new Images();
                        if(!images.getThumb_picture().equals("none")) {
                            img.setThumb_picture(images.getThumb_picture());
                            arrayListImages.add(img);
                        }

                    }

                    if(dataSnapshotusers.hasChild("city") && dataSnapshotusers.hasChild("country")){
                        String city = dataSnapshotusers.child("city").getValue().toString();
                        String country = dataSnapshotusers.child("country").getValue().toString();

                        item  = new CardMatches(dataSnapshotusers.getKey(),userUsername,userAge,userGender,job,school,arrayListImages,about,city,country);
                        rowItemsMatches.add(item);
                        arrAdpMatches.notifyDataSetChanged();
                    }else{
                        item  = new CardMatches(dataSnapshotusers.getKey(),userUsername,userAge,userGender,job,school,arrayListImages,about,"--- ","---");
                        rowItemsMatches.add(item);
                        arrAdpMatches.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void getRecentlySearchUsers(){
        dataFire.getDbHistory().child(dataFire.getUserID()).orderByChild("Time").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                setupAdapterCard(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void widgets() {

        rcvRecentHorizontalmax=findViewById(R.id.rcvRecentHorizontalmax);
        rcvRecentHorizontalmax.setHasFixedSize(true);
        mLayoutManagermax = new LinearLayoutManager(HistoryActivity.this,LinearLayoutManager.HORIZONTAL,false);
        rcvRecentHorizontalmax.setLayoutManager(mLayoutManagermax);

        imgvRecentlySearchBack=findViewById(R.id.imgvRecentlySearchBack);
        tv_send_common_msg=findViewById(R.id.tv_send_common_msg);
        adContainer = findViewById(R.id.banner_container);

    }



    @Override
    protected void onStart() {
        super.onStart();
        bannerFacebbok();
    }


    private void bannerFacebbok(){

        // banner ads facebook
        adViewBanner = new AdView(HistoryActivity.this, getString(R.string.Banner_ads_facebook), AdSize.BANNER_HEIGHT_50);

        // Add the ad view to your activity layout
        adContainer.addView(adViewBanner);
        adViewBanner.loadAd();
    }

}
