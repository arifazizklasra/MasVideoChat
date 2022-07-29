package com.rifcode.masvideochat.View.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.rifcode.masvideochat.Models.DataFire;
import com.rifcode.masvideochat.R;

import java.util.ArrayList;
import java.util.List;

public class SearchRandomActivity extends AppCompatActivity {

    private CountryCodePicker ccpCountrySearch;
    private SharedPreferences prefscheckOption;
    public static String strcheckOption;
    private SharedPreferences prefscheckMatchWith,prefs_country_selected,prefs_country_code_selected;
    private String strMatchWith,get_region_preferences,get_country_selected,get_country_code_selected;
    private SharedPreferences prefs_region_preferences;
    private TextView tvNamegenderSearch;
    private TextView tvCountrySearch;
    private LinearLayout llcountryshowSearch;
    private ImageView imgvGlobalSearch;
    private DataFire dataFire;
    private boolean isRequest=false;
    private ImageView imgvExitSearch;
    private SharedPreferences.Editor edit_checkMatchWith;
    private LinearLayout adView;
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_random);
        dataFire=new DataFire();
        widgets();
        AudienceNetworkAds.initialize(this);

        loadNativeAd();

        prefscheckOption = getSharedPreferences("checkOption", Context.MODE_PRIVATE);
        strcheckOption = prefscheckOption.getString("checkOption", null);

        // match with
        edit_checkMatchWith = getSharedPreferences("match_with", MODE_PRIVATE).edit();
        prefscheckMatchWith = getSharedPreferences("match_with", Context.MODE_PRIVATE);
        strMatchWith = prefscheckMatchWith.getString("match_with", null);

        // region preferences
        prefs_region_preferences = getSharedPreferences("region_preferences", Context.MODE_PRIVATE);
        get_region_preferences = prefs_region_preferences.getString("region_preferences", null);

        prefs_country_selected = getSharedPreferences("country", Context.MODE_PRIVATE);
        get_country_selected = prefs_country_selected.getString("country", null);

        // code country
        prefs_country_code_selected = getSharedPreferences("country_code", Context.MODE_PRIVATE);
        get_country_code_selected = prefs_country_code_selected.getString("country_code", null);


            if (strMatchWith.equals("guy")) {
                tvNamegenderSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_common_male_selected, 0, 0, 0);
                tvNamegenderSearch.setText(R.string.str_guys);
            } else if (strMatchWith.equals("girl")) {
                tvNamegenderSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_common_female_selected, 0, 0, 0);
                tvNamegenderSearch.setText(R.string.str_girls);
            } else {
                tvNamegenderSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_common_female_selected, 0, R.drawable.icon_common_male_selected, 0);
                tvNamegenderSearch.setText(R.string.string_both);
            }


        if(get_region_preferences.equals("select_country")) {
            tvCountrySearch.setText(get_country_selected);
            ccpCountrySearch.setCountryForNameCode(get_country_code_selected);
        }
        else if(get_region_preferences.equals("nearby")) {
            ccpCountrySearch.setAutoDetectedCountry(true);
            dataFire.getDbRefUsers().child(dataFire.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tvCountrySearch.setText(String.valueOf(dataSnapshot.child("city").getValue())+", "+
                            String.valueOf(dataSnapshot.child("country").getValue()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            ccpCountrySearch.setVisibility(View.GONE);
            imgvGlobalSearch.setVisibility(View.VISIBLE);
            tvCountrySearch.setText(R.string.global);
        }


        imgvExitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataFire.getDbRefSearch().child(dataFire.getUserID()).removeValue();
                finish();
            }
        });

    }

    private void widgets() {

        ccpCountrySearch = findViewById(R.id.ccpCountrySearch);
        ccpCountrySearch.setCcpClickable(false);

        tvNamegenderSearch=findViewById(R.id.tvNameSexSearch);
        tvCountrySearch=findViewById(R.id.tvCountrySearch);
        llcountryshowSearch=findViewById(R.id.llcountryshowSearch);
        imgvGlobalSearch=findViewById(R.id.imgvGlobalSearch);
        imgvExitSearch=findViewById(R.id.imgvExitSearch);

    }

    private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(this, getString(R.string.NativeAds_Facebook));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e("TAG", "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e("TAG", "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d("TAG", "Native ad is loaded and ready to be displayed!");
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d("TAG", "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d("TAG", "Native ad impression logged!");
            }
        };


        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());

        showNativeAdWithDelay();
    }


    private void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container_search);
        LayoutInflater inflater = LayoutInflater.from(SearchRandomActivity.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(SearchRandomActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }
    @Override
    protected void onStart() {
        super.onStart();

        isRequest=false;
        setupCall();
        getSearchResult(strMatchWith,get_country_selected,strcheckOption);


        dataFire.getDbRefSearch().child(dataFire.getUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("status_request_call") && dataSnapshot.hasChild("request_iduser")
                        && dataSnapshot.child("status_request_call").getValue().equals("send")) {

                    isRequest=true;
                    String request_iduser = dataSnapshot.child("request_iduser").getValue().toString();
                    Intent requestintent = new Intent(SearchRandomActivity.this, RequestCallActivity.class);
                    requestintent.putExtra("userIDvisited", request_iduser);
                    startActivity(requestintent);
                    Animatoo.animateSlideLeft(SearchRandomActivity.this);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void setupCall(){

        dataFire.getDbRefUsers().child(dataFire.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataFire.getDbRefSearch().child(dataFire.getUserID()).child("gender")
                        .setValue(String.valueOf(dataSnapshot.child("gender").getValue()));
                dataFire.getDbRefSearch().child(dataFire.getUserID()).child("cityMeet")
                        .setValue(String.valueOf(dataSnapshot.child("city").getValue()));
                dataFire.getDbRefSearch().child(dataFire.getUserID()).child("country")
                        .setValue(String.valueOf(dataSnapshot.child("country").getValue())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataFire.getDbRefSearch().child(dataFire.getUserID()).child("type_call")
                                .setValue(strcheckOption);

                        dataFire.getDbRefSearch().child(dataFire.getUserID()).child("countryMeet")
                                .setValue(get_country_selected);

                        dataFire.getDbRefSearch().child(dataFire.getUserID()).child("genderMeet")
                                .setValue(strMatchWith);

                        dataFire.getDbRefSearch().child(dataFire.getUserID()).child("time")
                                .setValue(-1*(System.currentTimeMillis()/1000));
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });


    }


    private void getSearchResult(final String genderSelected,final String countryselected,final String typeCallSelected){


            if (genderSelected.equals("both")) {
                dataFire.getDbRefSearch().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull final DataSnapshot dataSnapshotSearch, @Nullable String s) {

                        if(get_region_preferences.equals("global")) {

                            dataFire.getDbRefUsers().child(dataFire.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String mygender = String.valueOf(dataSnapshot.child("gender").getValue());
                                    if (!dataSnapshotSearch.getKey().equals(dataFire.getUserID())
                                            && dataSnapshotSearch.hasChild("gender")
                                            && dataSnapshotSearch.hasChild("genderMeet")
                                    ) {
                                        if ((dataSnapshotSearch.child("genderMeet").getValue().equals(mygender)
                                                || dataSnapshotSearch.child("genderMeet").getValue().equals("both"))
                                                && !dataSnapshotSearch.hasChild("room_id") && dataSnapshotSearch.child("type_call").getValue().equals(typeCallSelected)
                                        ) {


                                            isRequest=true;
                                            Intent requestintent = new Intent(SearchRandomActivity.this, RequestCallActivity.class);
                                            requestintent.putExtra("userIDvisited", dataSnapshotSearch.getKey());
                                            startActivity(requestintent);
                                            Animatoo.animateSlideLeft(SearchRandomActivity.this);
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("status_request_call").setValue("send");
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("request_iduser").setValue(dataFire.getUserID());
                                            String pushid=dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).push().getKey();
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("room_id").setValue(pushid);
                                            dataFire.getDbRefSearch().child(dataFire.getUserID()).child("room_id").setValue(pushid);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else if(get_region_preferences.equals("nearby")){

                            dataFire.getDbRefUsers().child(dataFire.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String mygender = String.valueOf(dataSnapshot.child("gender").getValue());
                                    String mycity = String.valueOf(dataSnapshot.child("city").getValue());

                                    if (!dataSnapshotSearch.getKey().equals(dataFire.getUserID())
                                            && dataSnapshotSearch.hasChild("gender")
                                            && dataSnapshotSearch.hasChild("genderMeet")
                                    ) {
                                        if ((dataSnapshotSearch.child("genderMeet").getValue().equals(mygender)
                                                || dataSnapshotSearch.child("genderMeet").getValue().equals("both"))
                                                && dataSnapshotSearch.child("cityMeet").getValue().equals(mycity)
                                                && !dataSnapshotSearch.hasChild("room_id") && dataSnapshotSearch.child("type_call").getValue().equals(typeCallSelected)
                                        ) {

                                            isRequest=true;
                                            Intent requestintent = new Intent(SearchRandomActivity.this, RequestCallActivity.class);
                                            requestintent.putExtra("userIDvisited", dataSnapshotSearch.getKey());
                                            startActivity(requestintent);
                                            Animatoo.animateSlideLeft(SearchRandomActivity.this);
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("status_request_call").setValue("send");
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("request_iduser").setValue(dataFire.getUserID());
                                            String pushid=dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).push().getKey();
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("room_id").setValue(pushid);
                                            dataFire.getDbRefSearch().child(dataFire.getUserID()).child("room_id").setValue(pushid);

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else if(get_region_preferences.equals("select_country")){

                            dataFire.getDbRefUsers().child(dataFire.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String mygender = String.valueOf(dataSnapshot.child("gender").getValue());

                                    if (!dataSnapshotSearch.getKey().equals(dataFire.getUserID())
                                            && dataSnapshotSearch.hasChild("gender")
                                            && dataSnapshotSearch.hasChild("genderMeet")

                                    ) {
                                        if ((dataSnapshotSearch.child("genderMeet").getValue().equals(mygender)
                                                || dataSnapshotSearch.child("genderMeet").getValue().equals("both"))
                                                && dataSnapshotSearch.child("country").getValue().equals(countryselected)
                                                && !dataSnapshotSearch.hasChild("room_id") && dataSnapshotSearch.child("type_call").getValue().equals(typeCallSelected)
                                        ) {

                                            isRequest=true;
                                            Intent requestintent = new Intent(SearchRandomActivity.this, RequestCallActivity.class);
                                            requestintent.putExtra("userIDvisited", dataSnapshotSearch.getKey());
                                            startActivity(requestintent);
                                            Animatoo.animateSlideLeft(SearchRandomActivity.this);
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("status_request_call").setValue("send");
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("request_iduser").setValue(dataFire.getUserID());
                                            String pushid=dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).push().getKey();
                                            dataFire.getDbRefSearch().child(dataSnapshotSearch.getKey()).child("room_id").setValue(pushid);
                                            dataFire.getDbRefSearch().child(dataFire.getUserID()).child("room_id").setValue(pushid);

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

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
            else {
                dataFire.getDbRefSearch().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {

                        if(get_region_preferences.equals("global")) {

                            if (!dataSnapshot.getKey().equals(dataFire.getUserID()) && dataSnapshot.hasChild("gender")
                                    && dataSnapshot.hasChild("genderMeet")
                            ) {

                                dataFire.getDbRefUsers().child(dataFire.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshotmyuser) {

                                        String mygender = String.valueOf(dataSnapshotmyuser.child("gender").getValue());

                                        if (dataSnapshot.child("gender").getValue().equals(genderSelected)
                                                && (dataSnapshot.child("genderMeet").getValue().equals(mygender)
                                                || dataSnapshot.child("genderMeet").getValue().equals("both"))
                                                && !dataSnapshot.hasChild("room_id")
                                                && dataSnapshot.child("type_call").getValue().equals(typeCallSelected)
                                        ) {

                                            isRequest=true;
                                            Intent requestintent = new Intent(SearchRandomActivity.this, RequestCallActivity.class);
                                            requestintent.putExtra("userIDvisited", dataSnapshot.getKey());
                                            startActivity(requestintent);
                                            Animatoo.animateSlideLeft(SearchRandomActivity.this);
                                            dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("status_request_call").setValue("send");
                                            dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("request_iduser").setValue(dataFire.getUserID());
                                            String pushid=dataFire.getDbRefSearch().child(dataSnapshot.getKey()).push().getKey();
                                            dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("room_id").setValue(pushid);
                                            dataFire.getDbRefSearch().child(dataFire.getUserID()).child("room_id").setValue(pushid);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                        else if(get_region_preferences.equals("nearby")){

                            if (!dataSnapshot.getKey().equals(dataFire.getUserID())
                                    && dataSnapshot.hasChild("gender") && dataSnapshot.hasChild("genderMeet")

                            ) {
                                dataFire.getDbRefUsers().child(dataFire.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshotmyuser) {

                                        String mygender = String.valueOf(dataSnapshotmyuser.child("gender").getValue());
                                        String mycity = String.valueOf(dataSnapshotmyuser.child("city").getValue());

                                        try {

                                            if (dataSnapshot.child("gender").getValue().equals(genderSelected)
                                                    && (dataSnapshot.child("genderMeet").getValue().equals(mygender)
                                                    || dataSnapshot.child("genderMeet").getValue().equals("both"))
                                                    && dataSnapshot.child("cityMeet").getValue().equals(mycity)
                                                    && !dataSnapshot.hasChild("room_id")
                                                    && dataSnapshot.child("type_call").getValue().equals(typeCallSelected)
                                            ) {

                                                isRequest=true;
                                                Intent requestintent = new Intent(SearchRandomActivity.this, RequestCallActivity.class);
                                                requestintent.putExtra("userIDvisited", dataSnapshot.getKey());
                                                startActivity(requestintent);
                                                Animatoo.animateSlideLeft(SearchRandomActivity.this);
                                                dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("status_request_call").setValue("send");
                                                dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("request_iduser").setValue(dataFire.getUserID());
                                                String pushid=dataFire.getDbRefSearch().child(dataSnapshot.getKey()).push().getKey();
                                                dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("room_id").setValue(pushid);
                                                dataFire.getDbRefSearch().child(dataFire.getUserID()).child("room_id").setValue(pushid);
                                            }
                                        } catch (Exception e) {
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        }
                        else if(get_region_preferences.equals("select_country")){

                            if (!dataSnapshot.getKey().equals(dataFire.getUserID())
                                    && dataSnapshot.hasChild("gender") && dataSnapshot.hasChild("genderMeet")

                            ) {

                                dataFire.getDbRefUsers().child(dataFire.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshotmyuser) {

                                        String mygender = String.valueOf(dataSnapshotmyuser.child("gender").getValue());

                                        try {
                                            if (dataSnapshot.child("gender").getValue().equals(genderSelected)
                                                    && (dataSnapshot.child("genderMeet").getValue().equals(mygender)
                                                    || dataSnapshot.child("genderMeet").getValue().equals("both"))
                                                    && dataSnapshot.child("country").getValue().equals(countryselected)
                                                    && !dataSnapshot.hasChild("room_id")
                                                    && dataSnapshot.child("type_call").getValue().equals(typeCallSelected)
                                            ) {
                                                isRequest=true;
                                                Intent requestintent = new Intent(SearchRandomActivity.this, RequestCallActivity.class);
                                                requestintent.putExtra("userIDvisited", dataSnapshot.getKey());
                                                startActivity(requestintent);
                                                Animatoo.animateSlideLeft(SearchRandomActivity.this);
                                                dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("status_request_call").setValue("send");
                                                dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("request_iduser").setValue(dataFire.getUserID());
                                                String pushid=dataFire.getDbRefSearch().child(dataSnapshot.getKey()).push().getKey();
                                                dataFire.getDbRefSearch().child(dataSnapshot.getKey()).child("room_id").setValue(pushid);
                                                dataFire.getDbRefSearch().child(dataFire.getUserID()).child("room_id").setValue(pushid);
                                            }
                                        } catch (Exception e) {
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });

                            }
                        }

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

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dataFire.getDbRefSearch().child(dataFire.getUserID()).removeValue();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isRequest!=true)
            dataFire.getDbRefSearch().child(dataFire.getUserID()).removeValue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isRequest!=true)
            dataFire.getDbRefSearch().child(dataFire.getUserID()).removeValue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isRequest!=true)
            dataFire.getDbRefSearch().child(dataFire.getUserID()).removeValue();
    }

    private void showNativeAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Check if nativeAd has been loaded successfully
                if(nativeAd == null || !nativeAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if(nativeAd.isAdInvalidated()) {
                    return;
                }
                inflateAd(nativeAd); // Inflate NativeAd into a container, same as in previous code examples
            }
        }, 1000 * 60 * 15); // Show the ad after 15 minutes
    }

}
