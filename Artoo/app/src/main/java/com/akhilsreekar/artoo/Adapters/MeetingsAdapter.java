package com.akhilsreekar.artoo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhilsreekar.artoo.MainActivity;
import com.akhilsreekar.artoo.Models.MeetingDetail;
import com.akhilsreekar.artoo.R;

import java.util.List;

/**
 * Created by akhil on 24-12-2017.
 */

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.ViewHolder>{

    List<MeetingDetail> meetingDetails;
    Context context;

    public MeetingsAdapter(List<MeetingDetail> meetingDetails,Context context){
        this.meetingDetails = meetingDetails;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MeetingDetail meetingDetail = meetingDetails.get(position);

        holder.meetingWith.setText(meetingDetail.getWhomToMeet());
        holder.description.setText(meetingDetail.getDescription());
        holder.address.setText(meetingDetail.getAddress());
        holder.date.setText(MainActivity.displayableDateFormat.format(meetingDetail.getMeetingTime()));
        holder.time.setText(MainActivity.displayableTimeFormat.format(meetingDetail.getMeetingTime()));
    }

    @Override
    public int getItemCount() {
        return meetingDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView meetingWith;
        TextView description;
        TextView address;
        TextView date;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            meetingWith = itemView.findViewById(R.id.meeting_with);
            description = itemView.findViewById(R.id.description);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
