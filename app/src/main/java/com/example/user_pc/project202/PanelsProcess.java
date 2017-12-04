package com.example.user_pc.project202;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by User-PC on 12/4/2017.
 */

public class PanelsProcess {

    private String Act_ID;
    private String Std_ID;
    private String Cand_ID;

    public PanelsProcess(String act_ID, String std_ID, String cand_ID) {
        Act_ID = act_ID;
        Std_ID = std_ID;
        Cand_ID = cand_ID;
    }

    public void ProcessVote(){

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            try(Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd)){
                try(PreparedStatement ps = con.prepareStatement("INSERT INTO votes(activity_id, studentID, candidate_id, choose_date) VALUES (?,?,?,now());")){

                    ps.setInt(1, Integer.parseInt(Act_ID));
                    ps.setString(2, Std_ID);
                    ps.setString(3, Cand_ID);

                    boolean result = ps.execute();

                }
            }

        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
