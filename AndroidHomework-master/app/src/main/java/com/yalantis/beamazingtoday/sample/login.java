package com.yalantis.beamazingtoday.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class login extends AppCompatActivity {
    ImageView loginbackup;
    ImageView editup,editdown;
    EditText username,password;
    Button loginbtn;
    TextView alarmtext;
    ImageView slogan;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbackup=(ImageView)findViewById(R.id.loginbackup);
        editup=(ImageView)findViewById(R.id.editbackup);
        editdown=(ImageView)findViewById(R.id.editbackdown);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        loginbtn=(Button)findViewById(R.id.login);
        alarmtext=(TextView)findViewById(R.id.alarm);
        slogan=(ImageView)findViewById(R.id.slogan);
        logo=(ImageView)findViewById(R.id.logo);
        slogan.setVisibility(View.INVISIBLE);
        Animation loginbackupmove= AnimationUtils.loadAnimation(login.this,R.anim.loginbackupmove);
        Animation editupscale=AnimationUtils.loadAnimation(login.this,R.anim.editbackupscale);
        Animation editdownscale=AnimationUtils.loadAnimation(login.this,R.anim.editbackdown);
        Animation editscale=AnimationUtils.loadAnimation(login.this,R.anim.editscale);
        Animation loginscale=AnimationUtils.loadAnimation(login.this,R.anim.loginscale);
        Animation logoscale=AnimationUtils.loadAnimation(login.this,R.anim.logoscale);
        loginbackup.startAnimation(loginbackupmove);
        editup.startAnimation(editupscale);
        editdown.startAnimation(editdownscale);
        username.startAnimation(editscale);
        password.startAnimation(editscale);
        loginbtn.startAnimation(loginscale);
        logo.startAnimation(logoscale);
        loginbtn.setOnClickListener(new myclick());
    }
    class myclick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(username.getText().toString().equals("123")){
                if(password.getText().toString().equals("123")){
                    final Animation lodinup2=AnimationUtils.loadAnimation(login.this,R.anim.loginbackupscale2);
                    Animation editdownbtn=AnimationUtils.loadAnimation(login.this,R.anim.editdownandbtn);
                    Animation editup2=AnimationUtils.loadAnimation(login.this,R.anim.editupandedit);
                    Animation sloganalpa=AnimationUtils.loadAnimation(login.this,R.anim.sloganmation);
                    Animation logoscale2=AnimationUtils.loadAnimation(login.this,R.anim.logoscale2);
                    loginbackup.startAnimation(lodinup2);
                    editup.startAnimation(editup2);
                    editdown.startAnimation(editdownbtn);
                    username.startAnimation(editup2);
                    password.startAnimation(editup2);
                    loginbtn.startAnimation(editdownbtn);
                    logo.startAnimation(logoscale2);
                    alarmtext.setText("");
                    alarmtext.setVisibility(View.INVISIBLE);
                    loginbackup.setVisibility(View.INVISIBLE);
                    editdown.setVisibility(View.INVISIBLE);
                    editup.setVisibility(View.INVISIBLE);
                    username.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    loginbtn.setVisibility(View.INVISIBLE);
                    slogan.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.INVISIBLE);
                    slogan.startAnimation(sloganalpa);
                    sloganalpa.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Intent intent=new Intent(login.this,ExampleActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.in,R.anim.out);
                            slogan.setVisibility(View.INVISIBLE);
                            alarmtext.setVisibility(View.VISIBLE);
                            loginbackup.setVisibility(View.VISIBLE);
                            editdown.setVisibility(View.VISIBLE);
                            editup.setVisibility(View.VISIBLE);
                            username.setVisibility(View.VISIBLE);
                            password.setVisibility(View.VISIBLE);
                            loginbtn.setVisibility(View.VISIBLE);
                            logo.setVisibility(View.VISIBLE);
                            Animation loginbackupmove= AnimationUtils.loadAnimation(login.this,R.anim.loginbackupmove);
                            Animation editupscale=AnimationUtils.loadAnimation(login.this,R.anim.editbackupscale);
                            Animation editdownscale=AnimationUtils.loadAnimation(login.this,R.anim.editbackdown);
                            Animation editscale=AnimationUtils.loadAnimation(login.this,R.anim.editscale);
                            Animation loginscale=AnimationUtils.loadAnimation(login.this,R.anim.loginscale);
                            Animation logoscale=AnimationUtils.loadAnimation(login.this,R.anim.logoscale);
                            loginbackup.startAnimation(loginbackupmove);
                            editup.startAnimation(editupscale);
                            editdown.startAnimation(editdownscale);
                            username.startAnimation(editscale);
                            password.startAnimation(editscale);
                            loginbtn.startAnimation(loginscale);
                            logo.startAnimation(logoscale);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }
            else {
                alarmtext.setVisibility(View.VISIBLE);
                alarmtext.setText("please input the currect username and password!");
            }
        }
    }
}
