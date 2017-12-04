package com.example.user_pc.project202;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by User-PC on 12/2/2017.
 */

public class ViewCandAdapter extends RecyclerView.Adapter<ViewCandAdapter.ViewCandHolder>{

    private List<Candidates> Data;
    private Context ctx;
    public int total_vote;

    public ViewCandAdapter(List<Candidates> Data, Context ctx, int total_vote){
        this.Data = Data;
        this.ctx = ctx;
        this.total_vote = total_vote;
    }

    @Override
    public ViewCandAdapter.ViewCandHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.candidate_details_row, parent, false);
        ViewCandAdapter.ViewCandHolder vh = new ViewCandAdapter.ViewCandHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewCandHolder holder, final int position){
        Candidates cand = Data.get(position);
        holder.bind_Candidate(cand, total_vote);
    }

    @Override
    public int getItemCount(){
        return Data.size();
    }

    public class ViewCandHolder extends RecyclerView.ViewHolder{

        private TextView txt_ttl;
        private TextView txt_desc;
        private ProgressBar progress;

        public View Layout;


        public ViewCandHolder(View v){
            super(v);
            Layout = v;
            txt_ttl = (TextView) v.findViewById(R.id.cand_details_ttl_bar);
            txt_desc = (TextView) v.findViewById(R.id.cand_details_desc_bar);
            progress = (ProgressBar) v.findViewById(R.id.cand_details_percent);
        }

        public void bind_Candidate(final Candidates Cand, int totals){
            if(Cand.getCand_ID() == "null"){
                txt_ttl.setText("Oops!");
                txt_desc.setText("No voters voted, please wait some time to let voters vote.");
                txt_ttl.setTextColor(Color.parseColor("#ff0000"));
                txt_desc.setTextColor(Color.parseColor("#ff0000"));
                progress.setVisibility(View.INVISIBLE);
            }
            else {
                txt_ttl.setText(Config.capitalize(Cand.getCand_ID()) + " - " + Cand.getCand_Name());
                int no_of_votes = Cand.getNo_of_votes();
                double divide = ((double) no_of_votes * (double) 100) / (double) totals;
                DecimalFormat df = new DecimalFormat(".##");
                txt_desc.setText("Total Voted: " + String.valueOf(no_of_votes) + " - Overall Percentage: " + df.format(divide) + " %");
                int prog = (int) divide;
                progress.setProgress(prog);
            }
        }
    }


}
