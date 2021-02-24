package com.amine.mareu.Controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;
import com.amine.mareu.R;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.MeetingItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MyListMeetingAdapter extends RecyclerView.Adapter<MyListMeetingAdapter.MyListMeetingHolder> {

    private MeetingItemBinding binding;
    private MeetingApiService mApiService;
    private List<Meeting> mMeetingList;
    private List<Room> mRoomList;

    private String strMeetDat;
    private SimpleDateFormat createDate;

    public MyListMeetingAdapter(List<Meeting> items) {
        this.mMeetingList = items;
    }

    @Override
    public MyListMeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = MeetingItemBinding.inflate(inflater, parent, false);
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
                notifyDataSetChanged(); /* Permet de remettre les éléments en place quand un diparait */
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
            setUpColorRoom(meeting);
            createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            strMeetDat = createDate.format(meeting.getDateBegin());
            strMeetDat = strMeetDat.substring(11, 16);


            binding.text.setText(String.valueOf(
                    meeting.getRoom() + " - "
                            + strMeetDat + " - "
                            + meeting.getSubject()));

            binding.participation.setText(meeting.getParticipants());
        }

        public void setUpColorRoom(Meeting meeting) {
            if (meeting.getRoom().equals(mRoomList.get(0))) {
                binding.icone.setColorFilter(R.color.purple_500);
            }
            if (meeting.getRoom().equals(mRoomList.get(1))) {
                binding.icone.setColorFilter(R.color.blueblue);
            }
            if (meeting.getRoom().equals(mRoomList.get(2))) {
                binding.icone.setColorFilter(R.color.teal_700);
            }

        }
    }
}