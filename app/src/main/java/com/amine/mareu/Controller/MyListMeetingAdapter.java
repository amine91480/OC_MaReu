package com.amine.mareu.Controller;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.MeetingItemBinding;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MyListMeetingAdapter extends RecyclerView.Adapter<MyListMeetingAdapter.MyListMeetingHolder> {

    private MeetingItemBinding binding;
    private MeetingApiService mApiService;
    private List<Meeting> mMeetingList;
    private List<Meeting> mMeetingListAll;

    private Context mContext;

    public MyListMeetingAdapter(List<Meeting> items, Context context) {
        this.mMeetingList = items;
        this.mContext = context;
        mMeetingListAll = new ArrayList<>();
        mMeetingListAll.addAll(mMeetingList);
    }

    @Override
    public MyListMeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = MeetingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        mApiService = DI.getMeetingApiService();
        return new MyListMeetingHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyListMeetingHolder holder, int position) {
        holder.updateElement(mMeetingList.get(position));
        holder.mBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.deleteMeeting(mMeetingList.get(position)); /* A test pour savoir si sa marche !*/
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mMeetingList.size());
                /*notifyDataSetChanged(); *//* Permet de remettre les éléments en place quand un diparait */
            }
        });
        holder.mBinding.superItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), ShowMeetingActivity.class);
                //intent.putExtra("meeting", (Parcelable) mMeetingList);
                //v.getContext().startActivity(intent);
                mMeetingList.stream().forEach(element -> System.out.println(element.getRoom()));
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
            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
            String data = meeting.getRoom().getName() + " - " +
                    meeting.getDateBegin().format(formatTime) + " - " +
                    meeting.getSubject();
            binding.text.setText(data);
            binding.participation.setText(meeting.getParticipants());
            binding.icone.setColorFilter(Color.parseColor(meeting.getRoom().getColor()));
        }
    }
}