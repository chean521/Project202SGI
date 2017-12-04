package com.example.user_pc.project202;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by User-PC on 12/3/2017.
 */

public class StudentLogin extends AppCompatActivity{

    private Button btn_login;
    private Button btn_back;
    private EditText textfield_stdID;
    private EditText textfield_pwd;
    private SessionManager Session;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.student_login);

        setTitle("Student Login");

        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_back = (Button) findViewById(R.id.btn_back);
        textfield_stdID = (EditText) findViewById(R.id.txt_stdID);
        textfield_pwd = (EditText) findViewById(R.id.txtPassword);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButtonClicked(view);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackBtnClicked(view);
            }
        });
    }

    private void LoginButtonClicked(View vt){
        String Std_id = textfield_stdID.getText().toString();
        String pwd = textfield_pwd.getText().toString();

        if(Std_id.trim().length() > 0 && pwd.trim().length() > 0) {

            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd);

                PreparedStatement ps = con.prepareStatement("select count(*) as 'no_acc', roles, fullname from user where studentID=? AND password=?;");
                ps.setString(1, Std_id);
                ps.setString(2, pwd);

                ResultSet rs = ps.executeQuery();
                int result = 0;
                String roles = null;
                String fullname = null;

                while(rs.next()){
                    result = rs.getInt("no_acc");
                    roles = rs.getString("roles");
                    fullname = rs.getString("fullname");
                }

                ps.close();
                con.close();

                if(result > 0){
                    Session = new SessionManager(getApplicationContext());
                    Session.CreateSession(Std_id, roles, fullname);
                    Config.AlertMessage("Login Success.", this, Config.DIALOG_ALERT, true);
                }
                else{
                    Config.AlertMessage("Invalid Combination of user name and password.", this, Config.DIALOG_ALERT, false);
                }
            }
            catch(Exception e){
                System.out.println(e.toString());
                Config.AlertMessage("Error while processing request.", this, Config.DIALOG_ALERT, false);
            }
        }
        else{
            Config.AlertMessage("Please fill in the Student ID and Password Correctly.", this, Config.DIALOG_ALERT, false);
        }
    }

    private void BackBtnClicked(View vt){
        finish();
    }



}
