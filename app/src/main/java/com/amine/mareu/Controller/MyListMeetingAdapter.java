package com.amine.mareu.Controller;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.R;

import java.util.List;

import butterknife.ButterKnife;

public class MyListMeetingAdapter extends RecyclerView.Adapter<MyListMeetingAdapter.MyListMeetingHolder> {

    private final List<Meeting> mMeetingList;

    public MyListMeetingAdapter(List<Meeting> items) {
        this.mMeetingList = items;
    }

    @Override
    public MyListMeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.meeting_item, parent, false);
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
        holder.mListitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), ShowMeetingActivity.class);
                //intent.putExtra("meeting", (Parcelable) mMeetingList);
                //v.getContext().startActivity(intent);
                System.out.println("OKAY");

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

    public class MyListMeetingHolder extends RecyclerView.ViewHolder {

        private TextView mDate;
        private TextView mLocation;
        private TextView mSubject;
        private TextView mParticipant;
        private ImageView mImageView;
        private ConstraintLayout mListitem;

        MyListMeetingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mLocation = (TextView) itemView.findViewById(R.id.location);
            mSubject = (TextView) itemView.findViewById(R.id.subject);
            mParticipant = (TextView) itemView.findViewById(R.id.participation);
            mImageView = (ImageView) itemView.findViewById(R.id.delete);
            mListitem = (ConstraintLayout) itemView.findViewById(R.id.superItem);
        }

        void show(Meeting meeting) {
            mDate.setText(String.valueOf(meeting.getDate()));
            mSubject.setText(meeting.getSubject());
            mLocation.setText(meeting.getLocation());
            mParticipant.setText(meeting.getParticipants());

        }
    }
}