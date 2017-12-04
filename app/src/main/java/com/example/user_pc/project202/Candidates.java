package com.example.user_pc.project202;

/**
 * Created by User-PC on 12/2/2017.
 */

public class Candidates {
    private String Cand_ID;
    private String Cand_Name;
    private int no_of_votes;

    public Candidates() {
    }

    public String getCand_ID() {
        return Cand_ID;
    }

    public void setCand_ID(String cand_ID) {
        Cand_ID = cand_ID;
    }

    public String getCand_Name() {
        return Cand_Name;
    }

    public void setCand_Name(String cand_Name) {
        Cand_Name = cand_Name;
    }

    public int getNo_of_votes() {
        return no_of_votes;
    }

    public void setNo_of_votes(int no_of_votes) {
        this.no_of_votes = no_of_votes;
    }
}
