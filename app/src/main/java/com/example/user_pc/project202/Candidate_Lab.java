package com.example.user_pc.project202;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 12/2/2017.
 */

public class Candidate_Lab {

    private List<Candidates> Candidate;
    private int TotalVote = 0;

    public Candidate_Lab(int Pid) {
        Candidate = new ArrayList<>();
        int i=0;

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd);

            PreparedStatement ps = con.prepareStatement("call GetNoResult_All(?);");
            ps.setInt(1, Pid);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Candidates cd = new Candidates();
                cd.setCand_ID(rs.getString("candidate_id"));
                cd.setCand_Name(rs.getString("fullname"));
                cd.setNo_of_votes(rs.getInt("no_vote"));
                TotalVote += rs.getInt("no_vote");
                Candidate.add(cd);
                i++;
            }

            if(i==0){
                Candidates cd = new Candidates();
                cd.setNo_of_votes(0);
                cd.setCand_Name("null");
                cd.setCand_ID("null");
                Candidate.add(cd);
            }

            ps.close();
            con.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e.toString());
            Candidate = null;
        }
    }

    public List<Candidates> getCandidates() {
        return Candidate;
    }

    public int getTotalVote() {
        return TotalVote;
    }
}
