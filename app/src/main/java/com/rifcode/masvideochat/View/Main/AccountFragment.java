package com.rifcode.masvideochat.View.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rifcode.masvideochat.Models.DataFire;
import com.rifcode.masvideochat.R;
import com.rifcode.masvideochat.View.Profile.EditProfileActivity;
import com.rifcode.masvideochat.View.Profile.PreviewProfileActivity;
import com.rifcode.masvideochat.View.Profile.SettingsActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private View view;
    private DataFire dataFire;
    private ImageView imgvMe_top_setting,imgvMeEditProfile;
    private CircleImageView imgvMeAvatar;
    private TextView tvMeName,tvMePreviewProfile;
    private TemplateView template;
    private AdLoader adLoaderMeduim;
    private Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        dataFire=new DataFire();
        wedgets();
        clickOnWedgets();

        template  =view.findViewById(R.id.my_template_profile);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // native ads admob :
                adLoaderMeduim  = new AdLoader.Builder(getActivity(), getString(R.string.NativeAdmobID))
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                NativeTemplateStyle styles = new
                                        NativeTemplateStyle.Builder().build();
                                template.setStyles(styles);
                                template.setNativeAd(nativeAd);
                            }
                        })
                        .build();
                adLoaderMeduim.loadAd(new AdRequest.Builder().build());

            }
        }, 1000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDataUser();

        // native ads admob :

    }

    private void clickOnWedgets() {
        imgvMe_top_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        imgvMeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        imgvMeEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        tvMePreviewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PreviewProfileActivity.class));
            }
        });
    }

    private void getDataUser() {
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = String.valueOf(dataSnapshot.child("username").getValue());
                final String picurl = String.valueOf(dataSnapshot.child("photoProfile").getValue());
                String age = String.valueOf(dataSnapshot.child("age").getValue());
                String gender = String.valueOf(dataSnapshot.child("gender").getValue());

                tvMeName.setText(name+", "+age);
                if(gender.equals("guy"))
                    tvMeName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_common_male_selected, 0);
                else
                    tvMeName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_common_female_selected, 0);

                Picasso.get().load(picurl).networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.icon_register_select_header).into(imgvMeAvatar, new Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(picurl).placeholder(R.drawable.icon_register_select_header)
                                .into(imgvMeAvatar);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void wedgets() {
        imgvMe_top_setting=view.findViewById(R.id.imgvMe_top_setting);
        imgvMeAvatar=view.findViewById(R.id.imgvMeAvatar);
        imgvMeEditProfile=view.findViewById(R.id.imgvMeEditProfile);
        tvMeName=view.findViewById(R.id.tvMeName);
        tvMePreviewProfile=view.findViewById(R.id.tvMePreviewProfile);
    }
}
