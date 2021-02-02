package com.amine.mareu.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.databinding.MeetingItemBinding;

import java.util.List;


public class MyListMeetingAdapter extends RecyclerView.Adapter<MyListMeetingAdapter.MyListMeetingHolder> {

    private MeetingItemBinding binding;
    private List<Meeting> mMeetingList;

    public MyListMeetingAdapter(List<Meeting> items) {
        this.mMeetingList = items;
    }

    @Override
    public MyListMeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = MeetingItemBinding.inflate(inflater, parent, false);
        return new MyListMeetingHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyListMeetingHolder holder, int position) {
        holder.updateElement(mMeetingList.get(position));
        holder.mBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetingList.remove(mMeetingList.get(position));
                notifyDataSetChanged();
            }
        });
        holder.mBinding.superItem.setOnClickListener(new View.OnClickListener() {
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

        private MeetingItemBinding mBinding;

        MyListMeetingHolder(MeetingItemBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        void updateElement(Meeting meeting) {
            binding.text.setText(String.valueOf(
                    meeting.getLocation() + " - "
                            + meeting.getDate().
                            substring(meeting.getDate().length() - 10, meeting.getDate().length() - 5) + " - "
                            + meeting.getSubject()));

            binding.participation.setText(meeting.getParticipants());
        }
    }
}