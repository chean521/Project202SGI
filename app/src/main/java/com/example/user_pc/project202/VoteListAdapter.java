package com.example.user_pc.project202;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by User-PC on 12/3/2017.
 */

public class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.VoteListHolder>{

    private List<Votes> Data;
    private Context ctx;
    private ItemClickListener ClickListener;

    public VoteListAdapter(List<Votes> Data, Context ctx){
        this.Data = Data;
        this.ctx = ctx;
    }

    @Override
    public VoteListAdapter.VoteListHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.vote_activity_select_row, parent, false);
        VoteListHolder vh = new VoteListHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(VoteListHolder holder, final int position){
        Votes act = Data.get(position);
        holder.BindList(act);
    }

    @Override
    public int getItemCount(){ return Data.size(); }

    public void SetClickListener(ItemClickListener clickListener){
        this.ClickListener = clickListener;
    }

    public class VoteListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View layout;
        private TextView txt_ttl;
        private TextView txt_desc;

        public VoteListHolder(View v){
            super(v);
            this.layout = v;
            txt_ttl = (TextView) v.findViewById(R.id.vote_available_ttl);
            txt_desc = (TextView) v.findViewById(R.id.vote_available_desc);
            v.setOnClickListener(this);
        }

        public void BindList(final Votes act){
            if(act.getAct_ttl().equals("no_data") == true){
                txt_ttl.setText("Warning!");
                txt_desc.setText("No available event happen.");
                txt_ttl.setTextColor(Color.RED);
                txt_desc.setTextColor(Color.RED);
            }
            else{
                SimpleDateFormat sdt = new SimpleDateFormat("MM/dd/yyyy");
                txt_ttl.setText("#" + String.valueOf(act.getPid()) + " - " + act.getAct_ttl());
                txt_desc.setText("Create Date: " + sdt.format(act.getCreate_date()) + ", Start Date: " + sdt.format(act.getStart_date()) + ", End Date: " + sdt.format(act.getEnd_date()));
            }
        }

        @Override
        public void onClick(View v){
            if(ClickListener != null) ClickListener.onClick(v, getAdapterPosition());
        }

    }


}
