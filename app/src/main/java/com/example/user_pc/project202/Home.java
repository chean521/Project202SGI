package com.example.user_pc.project202;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Home extends AppCompatActivity {

    private Button btn_view;
    private Button btn_exit;
    private Button btn_vote;
    private Button btn_about;
    private Button btn_logStat;
    private TextView txt_ttl;

    private SessionManager Session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Session = new SessionManager(getApplicationContext());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_view = (Button) findViewById(R.id.btn_link_1);
        btn_exit = (Button) findViewById(R.id.btn_link_2);
        btn_vote = (Button) findViewById(R.id.btn_link_3);
        btn_about = (Button) findViewById(R.id.btn_link_4);
        btn_logStat = (Button) findViewById(R.id.btn_link_5);
        txt_ttl = (TextView) findViewById(R.id.name_bar);

        CheckSession();

        btn_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ViewBtnClicked(v);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ExitBtnClicked(v);
            }
        });

        btn_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoteButtonClicked(view);
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutButtonClicked(view);
            }
        });

        btn_logStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogButtonClicked(view);
            }
        });

        RefreshLoginStat();
    }

    private void ViewBtnClicked(View vt){
        Intent i = new Intent(Home.this, ViewAct.class);
        startActivity(i);
    }

    private void ExitBtnClicked(View vt){
        finish();
    }

    private void VoteButtonClicked(View vt){
        if(Session.isLoggedIn() == true){
            Intent i = new Intent(this, VoteList.class);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Please login to continue!", Toast.LENGTH_SHORT).show();
        }
    }

    private void AboutButtonClicked(View vt){
        Intent i = new Intent(this, AboutSystem.class);
        startActivity(i);
    }

    private void LogButtonClicked(View v){
        if(Session.isLoggedIn() == true){
            Session.LogOut();
            Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(this, StudentLogin.class);
            startActivity(i);
        }
    }

    private synchronized void CheckSession(){
        if(Session.isLoggedIn() == true){
            btn_logStat.setText(R.string.menu_logout);
            final List<String> Users = Session.getUserDetails();
            txt_ttl.post(new Runnable() {
                @Override
                public void run() {
                    txt_ttl.setText("Welcome, " + Users.get(2));
                }
            });

        }
        else{
            btn_logStat.setText(R.string.menu_login);
            final List<String> Users = Session.getUserDetails();
            txt_ttl.post(new Runnable() {
                @Override
                public void run() {
                    txt_ttl.setText("");
                }
            });
        }
    }

    private void RefreshLoginStat(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(500);

                        Context ctx = Home.this;
                        if(((Activity) ctx).isFinishing()){
                            break;
                        }

                        CheckSession();

                    } catch (Exception e) {
                        System.out.println(e.toString());
                        break;
                    }
                }
            }
        });

        thread.start();
    }


}
