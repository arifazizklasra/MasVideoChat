package com.rifcode.masvideochat.View.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.rifcode.masvideochat.Models.DataFire;
import com.rifcode.masvideochat.R;
import com.rifcode.masvideochat.View.Login.PrivacyPolicyActivity;
import com.rifcode.masvideochat.View.Login.TermsOfServiceActivity;
import com.rifcode.masvideochat.View.Profile.Community.CommunityActivity;

public class AboutAppActivity extends AppCompatActivity {

    private ImageView imgvAboutAppBack;
    private DataFire dataFire;
    private RelativeLayout rl_about_privacy_policy,rl_about_terms_of_service,rl_about_community,rl_delete_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        dataFire=new DataFire();
        widgets();
        clickOnWidgets();
    }

    private void clickOnWidgets() {
        imgvAboutAppBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_about_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutAppActivity.this, PrivacyPolicyActivity.class));
            }
        });
        rl_about_terms_of_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutAppActivity.this, TermsOfServiceActivity.class));
            }
        });
        rl_about_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutAppActivity.this, CommunityActivity.class));
            }
        });

    }

    private void widgets() {
        imgvAboutAppBack=findViewById(R.id.imgvAboutAppBack);

        rl_about_privacy_policy=findViewById(R.id.rl_about_privacy_policy);
        rl_about_community=findViewById(R.id.rl_about_community);
        rl_about_terms_of_service=findViewById(R.id.rl_about_terms_of_service);
        rl_delete_account=findViewById(R.id.rl_delete_account);
    }

}
