package com.rifcode.masvideochat.View.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.messaging.FirebaseMessaging;
import com.rifcode.masvideochat.Models.DataFire;
import com.rifcode.masvideochat.R;
import com.rifcode.masvideochat.View.Main.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private CircleImageView imgvRegisterUser;
    private TextView tvRegister_name,tvRegister_age;
    private RadioButton rb_register_male,rb_register_female;
    private TextView btn_register_confirm;
    private DataFire dataFire;
    private String photoUser;
    private SharedPreferences prefs;
    private ProgressBar pbRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dataFire=new DataFire();
        prefs = getSharedPreferences("photoUser", MODE_PRIVATE);
        photoUser = prefs.getString("photoUser", null);

        // images reviews
        String puchKey = dataFire.getdbRefImagesReviews().push().getKey();
        HashMap<String, String> imagesReviewsMap = new HashMap<>();
        imagesReviewsMap.put("userID", dataFire.getUserID());
        imagesReviewsMap.put("image", photoUser);
        imagesReviewsMap.put("type", "photo_profile");
        dataFire.getdbRefImagesReviews().child(puchKey).setValue(imagesReviewsMap);
        dataFire.getdbRefImagesReviews().child(puchKey).child("time").setValue(-1*(System.currentTimeMillis()/1000));

        wedgets();
        getData();
        btn_register_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbRegister.setVisibility(View.VISIBLE);
                signUpDetails();


            }
        });

    }

    private void signUpDetails(){

        HashMap<String, String> userMap = new HashMap<>();

        //info user
        userMap.put("username", tvRegister_name.getText().toString());
        userMap.put("age",tvRegister_age.getText().toString());
        userMap.put("gender",InfoRegisterActivity.gender);
        if(InfoRegisterActivity.gender.equals("guy"))
            userMap.put("genderMeet","girl");
        else
            userMap.put("genderMeet","guy");

        userMap.put("about", "---");
        userMap.put("online","false");
        userMap.put("photoProfile", photoUser);
        userMap.put("discovery", "false");
        userMap.put("job", "---");
        userMap.put("school", "---");
        userMap.put("birthday",
                String.valueOf(InfoRegisterActivity.day)+"/"
                        +String.valueOf(InfoRegisterActivity.month)+"/"
                        +String.valueOf(InfoRegisterActivity.year));
        ////---------------- for notification one to one ------------------//

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        //handle token error
                        return;
                    }

                    String deviceTokenID  = task.getResult();
                    userMap.put("device_token", deviceTokenID);
                });

        //
        if(WelcomeActivity.typeAccount!=null) {
            if (WelcomeActivity.typeAccount.equals("phone")) {
                userMap.put("numberPhone", PhoneActivity.phonenumber);
                userMap.put("codeCountryPhone", PhoneActivity.codecountry);
                userMap.put("type_account", "phone");

            } else {
                userMap.put("type_account", "facebook");
                userMap.put("numberPhone", "---");
                userMap.put("codeCountryPhone", "---");
                userMap.put("email", InfoRegisterActivity.emailfb);
            }
        }
        //gps use
        userMap.put("country", "---");
        userMap.put("country_code", "---");
        userMap.put("city", "---");

        //options meet for matches
        userMap.put("country_meet", "my_country");
        userMap.put("max_age_meet", "99");
        userMap.put("min_age_meet", "18");

        //app options
        userMap.put("language_app", Locale.getDefault().getLanguage());
        userMap.put("state_app", "start");
        userMap.put("rateApp","false");
        dataFire.getDbRefUsers().child(dataFire.getUserID()).setValue(userMap);


        //5 images user vide

        //0
        //images user
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("0")
                .child("thumb_picture").setValue(photoUser);
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("0")
                .child("Time").setValue(-1*(System.currentTimeMillis()/1000));


        //1
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("1")
                .child("thumb_picture").setValue("none");
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("1")
                .child("Time").setValue(-1*(System.currentTimeMillis()/1000));

        //2
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("2")
                .child("thumb_picture").setValue("none");
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("2")
                .child("Time").setValue(-1*(System.currentTimeMillis()/1000));

        //3
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("3")
                .child("thumb_picture").setValue("none");
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("3")
                .child("Time").setValue(-1*(System.currentTimeMillis()/1000));

        //4
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("4")
                .child("thumb_picture").setValue("none");
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("4")
                .child("Time").setValue(-1*(System.currentTimeMillis()/1000));

        //5
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("5")
                .child("thumb_picture").setValue("none");
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("images").child("5")
                .child("Time").setValue(-1*(System.currentTimeMillis()/1000));


        //number user signup
        dataFire.getDbRefUsers().child(dataFire.getUserID())
                .child("number")
                .setValue(-1*(System.currentTimeMillis()/1000)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pbRegister.setVisibility(View.GONE);

                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                Toast.makeText(RegisterActivity.this, getString(R.string.auth_success), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getData(){

        tvRegister_age.setText(InfoRegisterActivity.age);
        tvRegister_name.setText(InfoRegisterActivity.name);

        if(InfoRegisterActivity.gender.equals("guy")){
            rb_register_male.setChecked(true);
        }else{
            rb_register_female.setChecked(true);
        }

        Picasso.get().load(photoUser).networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.icon_register_select_header).into(imgvRegisterUser, new Callback() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onError(Exception e) {
                Picasso.get().load(photoUser).placeholder(R.drawable.icon_register_select_header)
                        .into(imgvRegisterUser);
            }
        });

    }

    private void wedgets() {
        rb_register_female=findViewById(R.id.rb_register_female);
        rb_register_male=findViewById(R.id.rb_register_male);
        tvRegister_name=findViewById(R.id.tvRegister_name);
        tvRegister_age=findViewById(R.id.tvRegister_age);
        imgvRegisterUser=findViewById(R.id.imgvRegisterUser);
        pbRegister=findViewById(R.id.pbRegister);
        btn_register_confirm=findViewById(R.id.btn_register_confirm);
    }



}
