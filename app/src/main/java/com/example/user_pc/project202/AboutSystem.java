package com.example.user_pc.project202;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User-PC on 12/2/2017.
 */

public class AboutSystem extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.about_system);

        try{
            PackageInfo pinfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String Ver_Name = pinfo.versionName;
            int Ver_Code = pinfo.versionCode;

            TextView build = (TextView) findViewById(R.id.about_build_number);
            TextView vers = (TextView) findViewById(R.id.about_version);

            build.setText("Build No: " + String.valueOf(Ver_Code));
            vers.setText("Version: " + String.valueOf(Ver_Name));
        }
        catch(Exception e){
            Toast.makeText(this, "Unable get version info.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
