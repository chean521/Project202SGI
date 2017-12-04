package com.example.user_pc.project202;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-PC on 12/3/2017.
 */

public class Vote_Lab {

    private List<Votes> Activities;

    public Vote_Lab(){
        Activities = new ArrayList<Votes>();

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            try(Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd)) {
                String sql = "CALL GetActCountAndDetails();";
                CallableStatement multi_stat = con.prepareCall(sql);

                boolean result = multi_stat.execute();
                boolean retrieve_completed = false;
                int rs_count = 0;

                do{
                    if(result){
                        ResultSet rs = multi_stat.getResultSet();
                        rs_count++;
                        while(rs.next()){
                            switch(rs_count){
                                case 1:
                                    if(rs.getInt("rows") == 0){
                                        retrieve_completed = false;
                                        Votes act = new Votes();
                                        act.setAct_ttl("no_data");
                                        Activities.add(act);
                                    }
                                    else{
                                        retrieve_completed = true;
                                    }
                                    break;

                                case 2:
                                    Votes act = new Votes();
                                    act.setPid(String.valueOf(rs.getInt("activity_id")));
                                    act.setAct_ttl(rs.getString("activity_title"));
                                    act.setDesc(rs.getString("description"));
                                    act.setCreate_date(rs.getDate("creation_date"));
                                    act.setStart_date(rs.getDate("start_date"));
                                    act.setEnd_date(rs.getDate("end_date"));
                                    Activities.add(act);
                                    retrieve_completed = true;
                                    break;
                            }

                            if(retrieve_completed == false)
                                break;
                        }

                        rs.close();
                    }

                    if(retrieve_completed == false)
                        break;

                    result = multi_stat.getMoreResults();
                }
                while(result);

                multi_stat.close();
            }

        }
        catch(Exception e){
            System.out.println(e.toString());
            Activities = null;
        }
    }

    public List<Votes> getAct() {
        return Activities;
    }
}
