package com.amine.mareu.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.R;

import java.util.List;

public class MyListMeetingAdapter extends RecyclerView.Adapter<MyListMeetingAdapter.MyListMeetingHolder> {

    List<Meeting> mMeetingList;

    public MyListMeetingAdapter(List<Meeting> mMeetingList) {this.mMeetingList = mMeetingList;}

    @Override
    public MyListMeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meeting_item,parent,false);
        return new MyListMeetingHolder(view);
    }

    @Override
    public void onBindViewHolder(MyListMeetingHolder holder, int position) {
        holder.show(mMeetingList.get(position));
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetingList.remove(mMeetingList.get(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

    class MyListMeetingHolder extends RecyclerView.ViewHolder {

        private TextView mDate;
        private TextView mLocation ;
        private TextView mSubject ;
        private TextView mParticipant ;
        private ImageView mImageView ;

        MyListMeetingHolder(View itemView){
            super(itemView);
            mDate = (TextView)itemView.findViewById(R.id.date);
            mLocation = (TextView)itemView.findViewById(R.id.location);
            mSubject = (TextView)itemView.findViewById(R.id.subject);
            mParticipant = (TextView)itemView.findViewById(R.id.participation);
            mImageView = (ImageView)itemView.findViewById(R.id.delete);
        }

        void show(Meeting meeting){
            mDate.setText(String.valueOf(meeting.getDate()));
            mSubject.setText(meeting.getSubject());
            mLocation.setText(meeting.getLocation());
            mParticipant.setText(meeting.getParticipants());

        }
    }

}
