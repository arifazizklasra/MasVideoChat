package com.rifcode.masvideochat.View.Login;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jkb.vcedittext.VerificationCodeEditText;

import com.rifcode.masvideochat.Models.DataFire;
import com.rifcode.masvideochat.R;
import com.rifcode.masvideochat.View.Main.MainActivity;

public class VerifyPhoneActivity extends AppCompatActivity {

    private String mAuthVerificationId;
    private VerificationCodeEditText codeview;
    private TextView tv_verify_code,tv_resend_code;
    private String userID;
    private DataFire dataFire;
    private ImageView imgv_back_left_verifyphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        dataFire=new DataFire();
        wedgets();

        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        codeview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==6){
                    tv_verify_code.setAlpha(1);
                }else{
                    tv_verify_code.setAlpha(0.5f);
                }
            }
        });

        imgv_back_left_verifyphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backwelcome = new Intent(VerifyPhoneActivity.this,PhoneActivity.class);
                startActivity(backwelcome);
                Animatoo.animateSlideRight(VerifyPhoneActivity.this);
            }
        });

        tv_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_verify_code.getAlpha()==1){
                    tv_resend_code.setVisibility(View.VISIBLE);
                    tv_verify_code.setAlpha(0.5f);
                    codeview.setEnabled(false);
                    counterTimeSendCode();
                    if(!TextUtils.isEmpty(codeview.getText().toString())){
                        String code = codeview.getText().toString();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, code);
                        signInWithPhoneAuthCredential(credential);
                    }else{
                        Toast.makeText(VerifyPhoneActivity.this, R.string.enter_verifi_code, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


        tv_resend_code.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_verify_code.getAlpha()==1){
                    tv_verify_code.setAlpha(0.5f);
                    tv_resend_code.setAlpha(0.5f);
                    codeview.setEnabled(false);
                    counterTimeSendCode();
                    if(!TextUtils.isEmpty(codeview.getText().toString())){
                        String code = codeview.getText().toString();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, code);
                        signInWithPhoneAuthCredential(credential);
                    }else{
                        Toast.makeText(VerifyPhoneActivity.this, R.string.enter_verifi_code, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void counterTimeSendCode(){
        new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                tv_resend_code.setText(getResources().getString(R.string.login_input_resent)+" - "+String.valueOf(millisUntilFinished / 1000));
                //here you can have your logic to set text to edittext
            }
            public void onFinish() {
                tv_resend_code.setText(getResources().getString(R.string.login_input_resent));
                tv_resend_code.setAlpha(1);
                tv_verify_code.setAlpha(1);
                codeview.setEnabled(true);
            }

        }.start();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        dataFire.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            userID=user.getUid();
                            dataFire.getDbRefUsers().child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild("username")){

                                        Toast.makeText(VerifyPhoneActivity.this, getString(R.string.auth_success), Toast.LENGTH_SHORT).show();
                                        Intent continueintent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
                                        int mPendingIntentId = 123456;
                                        PendingIntent mPendingIntent = PendingIntent.getActivity(VerifyPhoneActivity.this, mPendingIntentId, continueintent, PendingIntent.FLAG_MUTABLE);
                                        AlarmManager mgr = (AlarmManager)VerifyPhoneActivity.this.getSystemService(Context.ALARM_SERVICE);
                                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                                        System.exit(0);

                                    }else {
                                        Intent continueintent = new Intent(VerifyPhoneActivity.this, InfoRegisterActivity.class);
                                        startActivity(continueintent);
                                        Animatoo.animateSlideLeft(VerifyPhoneActivity.this);
                                    }
                                }
                                @Override
                                public void onCancelled(@androidx.annotation.NonNull DatabaseError databaseError) {
                                }
                            });

                        } else {
                            if (task.getException() instanceof
                                FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyPhoneActivity.this, R.string.vercodenovalid, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }



    private void wedgets() {
        codeview=findViewById(R.id.codeview);
        tv_verify_code=findViewById(R.id.tv_verify_code);
        tv_resend_code=findViewById(R.id.tv_resend_code);
        imgv_back_left_verifyphone=findViewById(R.id.imgv_back_left_verifyphone);

    }
}
