package com.example.user_pc.project202;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import java.util.Date;
import java.util.List;

/**
 * Created by User-PC on 11/30/2017.
 */

public class ViewActAdapter extends RecyclerView.Adapter<ViewActAdapter.ViewActHolder>{
    private List<Activity> Data;
    private ItemClickListener ClickListener;
    private Context ctx;

    public ViewActAdapter(List<Activity> Data, Context ctx){
        this.Data = Data;
        this.ctx = ctx;
    }

    public void SetClickListener(ItemClickListener clickListener){
        this.ClickListener = clickListener;
    }

    public class ViewActHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView Act_Stat;
        public TextView Act_Desc;
        public View layout;

        public ViewActHolder(View v){
            super(v);
            layout = v;
            Act_Stat = (TextView) v.findViewById(R.id.act_row_ttl_bar);
            Act_Desc = (TextView) v.findViewById(R.id.act_row_desc_bar);
            v.setOnClickListener(this);
        }

        public void bind_Activity(final Activity activity){
            Date today = new Date();
            String stat = "";

            if(today.after(activity.getEnd_date()) == true) {
                stat = "Ended";
                Act_Stat.setTextColor(Color.parseColor("#ff0000"));
            }
            else if(today.before(activity.getStart_date()) == true) {
                stat = "Haven't Started";
                Act_Stat.setTextColor(Color.parseColor("#fff600"));
            }
            else {
                stat = "In Progress";
                Act_Stat.setTextColor(Color.parseColor("#00ff19"));
            }

            Act_Stat.setText(stat);
            Act_Desc.setText("Activity ID: " + activity.getPid() + " - " + activity.getAct_ttl() + ", " + activity.getCreate_date());
        }

        @Override
        public void onClick(View v){
            if(ClickListener != null) ClickListener.onClick(v, getAdapterPosition());
        }
    }

    @Override
    public ViewActHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.available_activity_row, parent, false);
        ViewActHolder vh = new ViewActHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewActHolder holder, final int position){
        Activity act = Data.get(position);
        holder.bind_Activity(act);
    }

    @Override
    public int getItemCount(){
        return Data.size();
    }
}
