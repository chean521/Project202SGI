package com.example.user_pc.project202;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by User-PC on 12/3/2017.
 */

public class Config {
    public static final String ConnectionString = "jdbc:mysql://192.168.0.147:3306/project205cde?useSSL=false&amp;allowMultiQueries=true";
    public static final String ConnectionID = "test_user1";
    public static final String ConnectionPwd = "Qy253AXuJA97";

    public static final int DIALOG_ALERT = 0;
    public static final int DIALOG_LOAD = 1;

    public static AlertDialog AlertMessage(String Message, final Context ctx, int CONFIG_MODE, final boolean addOns){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setMessage(Message);

        if(CONFIG_MODE == 0){
            dialog.setCancelable(true);
            dialog.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(addOns == true) {
                                dialog.cancel();
                                try{
                                    if(ctx instanceof Activity){
                                        ((Activity)ctx).finish();
                                    }
                                }
                                catch(Exception e){
                                    System.out.println(e.toString());
                                }
                            }
                            else{
                                dialog.cancel();
                            }
                        }
                    });
        }
        else{
            dialog.setCancelable(false);
        }

        AlertDialog alert = dialog.create();
        alert.show();

        if(CONFIG_MODE==0)
            return null;
        else
            return alert;
    }

    public static String capitalize(String s) {
        if (s.length() == 0) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
