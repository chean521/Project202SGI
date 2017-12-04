package com.example.user_pc.project202;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 12/4/2017.
 */

public class Panel_Lab {

    private List<Panels> Candidate_Panel;

    public Panel_Lab(String Activity_ID){

        Candidate_Panel = new ArrayList<Panels>();

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            try(Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd)) {

                try(CallableStatement stmt = con.prepareCall("CALL GetCandDetails_Vote(?);")){

                    stmt.setInt(1, Integer.parseInt(Activity_ID));

                    boolean result = stmt.execute();

                    int rs_count = 0;

                    do {

                        if(result){
                            ResultSet rs = stmt.getResultSet();
                            rs_count++;

                            while(rs.next()){
                                Panels pn = new Panels();
                                pn.setCandidate_ID(rs.getString("cand_id"));
                                pn.setCandidate_Name(rs.getString("fullname"));
                                pn.setCandidate_Prog(rs.getString("programme"));
                                pn.setExplanation(rs.getString("cand_desc"));
                                Candidate_Panel.add(pn);
                            }

                            rs.close();
                        }

                        result = stmt.getMoreResults();
                    }
                    while(result);

                }

            }

        }
        catch(Exception e){
            System.out.println("Error: " + e.toString());
            Candidate_Panel = null;
        }
    }

    public List<Panels> getCandidate_Panel() {
        return Candidate_Panel;
    }
}
