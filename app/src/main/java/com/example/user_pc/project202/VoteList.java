package com.example.user_pc.project202;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * Created by User-PC on 12/3/2017.
 */

public class VoteList extends AppCompatActivity implements ItemClickListener{
    private RecyclerView recView;
    private VoteListAdapter adpt;
    private List<Votes> Act;
    private Button btn_back;
    private SessionManager Session;
    private Switch admin_allow;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vote_activity_select_recycler);
        setTitle("Choose Activity (Vote)");

        btn_back = (Button) findViewById(R.id.vote_available_back_btn);
        recView = (RecyclerView) findViewById(R.id.vote_available_recycler_view);
        admin_allow = (Switch) findViewById(R.id.allow_cand_vote_admin);
        Session = new SessionManager(getApplicationContext());

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(recView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider_grey));
        recView.addItemDecoration(divider);

        Vote_Lab ab = new Vote_Lab();
        if(ab.getAct()==null){
            Toast.makeText(this, "Unable get data, connection error!", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        Act = ab.getAct();

        adpt = new VoteListAdapter(Act, this);
        recView.setAdapter(adpt);
        adpt.SetClickListener(this);

        GetAdminSetting();
        CheckAdmin();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackButtonClicked(view);
            }
        });
        admin_allow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AllowCandSwitchChanged(compoundButton, b);
            }
        });
    }

    @Override
    public void onClick(View v, int pos){
        Votes vt = Act.get(pos);
        List<String> UserDetails = Session.getUserDetails();

        if(UserDetails.get(1).equals("admin") == true){
            Toast.makeText(this, "Admin cannot take part into vote!", Toast.LENGTH_SHORT).show();
        }
        else if(CheckCandidate(Integer.parseInt(vt.getPid())) == false){
            Toast.makeText(this, "Allow Candidate Vote Disabled.", Toast.LENGTH_SHORT).show();
        }
        else if(CheckVoted(vt.getPid(), UserDetails.get(0)) == true){
            Intent i = new Intent(this, PanelVote.class);
            i.putExtra("activity_id", vt.getPid());
            startActivity(i);
        }
        else{
            Toast.makeText(this, "You voted this event, permission rejected!", Toast.LENGTH_SHORT).show();
        }
    }

    public void BackButtonClicked(View v){
        finish();
    }

    private void AllowCandSwitchChanged(CompoundButton compoundButton, boolean b){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            try (Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd)) {

                try (PreparedStatement st = con.prepareStatement("UPDATE androidconfig SET Config_Value=? WHERE Config_ID=?")) {

                    st.setString(2, "allow_cand_vote");

                    if(admin_allow.isChecked() == true){
                        st.setString(1, "true");
                    }
                    else{
                        st.setString(1, "false");
                    }

                    st.executeUpdate();
                    GetAdminSetting();
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e.toString());
        }
    }

    private void CheckAdmin(){
        String roles = Session.getUserDetails().get(1);

        if(roles.equals("admin")){
            admin_allow.setEnabled(true);
        }
        else{
            admin_allow.setEnabled(false);
        }
    }

    private void GetAdminSetting(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            try(Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd)){

                try(Statement st = con.createStatement()){

                    ResultSet rs = st.executeQuery("select * from androidconfig where Config_ID='allow_cand_vote';");

                    String res = null;

                    while(rs.next()){
                        res = rs.getString("Config_Value");
                    }

                    if(res.equals("true")){
                        admin_allow.setChecked(true);
                    }
                    else{
                        admin_allow.setChecked(false);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    private boolean CheckCandidate(int Act_ID){

        if(admin_allow.isChecked() == true){
            return true;
        }
        else {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                try (Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd)) {

                    try (PreparedStatement ps = con.prepareStatement("select count(*) as 'counts' from candidate where cand_id=? AND activity_id=?;")) {

                        ps.setString(1, Session.getUserDetails().get(0));
                        ps.setInt(2, Act_ID);

                        ResultSet rs = ps.executeQuery();
                        int count = 0;

                        while (rs.next()) {
                            count = rs.getInt("counts");
                        }

                        if (count > 0)
                            return false;
                        else
                            return true;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.toString());
                Toast.makeText(this, "Unable process checking.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean CheckVoted(String Act_ID, String Std_ID){

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            int voted = 0;

            try(Connection con = DriverManager.getConnection(Config.ConnectionString, Config.ConnectionID, Config.ConnectionPwd)) {

                try(CallableStatement stmt = con.prepareCall("CALL GetVoteCountBySingle(?,?);")) {

                    stmt.setInt(1, Integer.parseInt(Act_ID));
                    stmt.setString(2, Std_ID);

                    boolean result = stmt.execute();

                    int resultSet = 0;

                    do{
                        if(result){
                            ResultSet rs = stmt.getResultSet();
                            resultSet++;

                            while(rs.next()){
                                voted = rs.getInt("result");
                            }

                            rs.close();
                        }

                        result = stmt.getMoreResults();
                    }
                    while(result);
                }

            }

            if(voted > 0)
                return false;
            else
                return true;
        }
        catch(Exception e){
            Toast.makeText(this, "Error processing verification.", Toast.LENGTH_SHORT).show();
            System.out.println("Error: " + e.toString());
            return false;
        }

    }
}
