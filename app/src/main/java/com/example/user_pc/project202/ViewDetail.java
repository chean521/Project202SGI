package com.example.user_pc.project202;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by User-PC on 12/2/2017.
 */

public class ViewDetail extends AppCompatActivity{

    private String Act_PID;
    private Button view_btn;
    private Button close_btn;
    private TextView txt_pid;
    private TextView txt_name;
    private TextView txt_desc;
    private TextView txt_create;
    private TextView txt_stat;
    private TextView txt_end;
    private TextView txt_start;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        Bundle extras = getIntent().getExtras();
        if(extras == null)
            finish();
        else
            Act_PID = extras.getString("activity_id");

        setTitle("View Activity (ID: " + Act_PID + ")");

        txt_pid = (TextView) findViewById(R.id.view_act_pid);
        txt_name = (TextView) findViewById(R.id.view_act_ttl);
        txt_desc = (TextView) findViewById(R.id.view_act_desc);
        txt_create = (TextView) findViewById(R.id.view_act_create_date);
        txt_end = (TextView) findViewById(R.id.view_act_end_date);
        txt_start = (TextView) findViewById(R.id.view_act_start_date);
        txt_stat = (TextView) findViewById(R.id.view_act_stat);
        view_btn = (Button) findViewById(R.id.view_act_view_cand);
        close_btn = (Button) findViewById(R.id.view_act_exit_view);

        this.SetListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
        GetActivityData();
    }


    private void SetListener(){
        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewButtonClicked(view);
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseButtonClicked(view);
            }
        });
    }

    private void GetActivityData(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd);

            PreparedStatement ps = con.prepareStatement("call GetActivity_Result(?);");

            ps.setInt(1, Integer.parseInt(Act_PID));

            ResultSet rs = ps.executeQuery();

            if(rs != null) {
                while (rs.next()) {
                    SimpleDateFormat sdt = new SimpleDateFormat("MM/dd/yyyy");
                    txt_pid.setText(String.valueOf(rs.getInt("act_id")));
                    txt_name.setText(rs.getString("act_ttl"));
                    txt_desc.setText(rs.getString("act_desc"));
                    txt_create.setText(sdt.format(rs.getDate("act_create")));

                    Date start = rs.getDate("act_start");
                    Date end = rs.getDate("act_end");
                    Date now = new Date();
                    String stat = "";

                    if (now.before(start)) {
                        stat = "Haven't Started";
                        txt_stat.setTextColor(Color.parseColor("#fff600"));
                    }
                    else if (now.after(end)) {
                        stat = "Ended";
                        txt_stat.setTextColor(Color.parseColor("#ff0000"));
                    }
                    else {
                        stat = "In progress";
                        txt_stat.setTextColor(Color.parseColor("#00ff19"));
                    }

                    txt_stat.setText(stat);
                    txt_start.setText(sdt.format(rs.getDate("act_start")));
                    txt_end.setText(sdt.format(rs.getDate("act_end")));
                }
            }
            else
            {
                Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
                finish();
            }

            ps.close();
            con.close();
        }
        catch(Exception e){
            Toast.makeText(this, "Unable to Retrieve DataQ", Toast.LENGTH_SHORT).show();
            System.out.println("Error" + e.toString());
            finish();
        }
    }


    private void ViewButtonClicked(View v){
        Intent i = new Intent(this, ViewCand.class);
        i.putExtra("activity_id", Act_PID);
        startActivity(i);
    }

    private void CloseButtonClicked(View v){
        finish();
    }


}







