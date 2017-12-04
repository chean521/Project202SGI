package com.example.user_pc.project202;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

/**
 * Created by User-PC on 11/30/2017.
 */

public class Activity_Lab {
    private List<Activity> activities;


    public Activity_Lab() {
        activities = new ArrayList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd);

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from voteactivity order by creation_date desc;");

            while(rs.next()){
                Activity act = new Activity();
                act.setPid(rs.getString("activity_id"));
                act.setAct_ttl(rs.getString("activity_title"));
                act.setDesc(rs.getString("description"));
                act.setCreate_date(rs.getDate("creation_date"));
                act.setStart_date(rs.getDate("start_date"));
                act.setEnd_date(rs.getDate("end_date"));
                activities.add(act);
            }

            rs.close();
            con.close();

        }
        catch(Exception e){
            System.out.println(e.toString());
            activities = null;
        }

    }

    public List<Activity> getActivity(){return activities;}

}
