package com.amine.mareu.Controller;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.MeetingItemBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


public class MyListMeetingAdapter extends RecyclerView.Adapter<MyListMeetingAdapter.MyListMeetingHolder> implements Filterable {

    private MeetingItemBinding binding;
    private MeetingApiService mApiService;
    private List<Meeting> mMeetingList;
    private List<Meeting> mMeetingListAll;

    private MyListMeetingAdapter mAdapter;

    private String strMeetDat;
    private SimpleDateFormat createDate;

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
            @RequiresApi(api = Build.VERSION_CODES.N)
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        // Run on Background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Meeting> filteredMeeting = new ArrayList<Meeting>();

            if (constraint == null || constraint.length() == 0) {
                filteredMeeting.addAll(mMeetingListAll);
            } else {
                for (Meeting meeting : mMeetingListAll) {
                    if (meeting.getRoom().toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredMeeting.add(meeting);
                        Log.d("getFilter/ifFound", "FOUND THIS " + meeting.getRoom() + meeting.getId());
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredMeeting;
            return filterResults;
        }

        //Run on a UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMeetingList.clear();
            mMeetingList.addAll((Collection<? extends Meeting>) results.values);
            mAdapter = new MyListMeetingAdapter(mMeetingList, mContext);
        }
    };

    public class MyListMeetingHolder extends RecyclerView.ViewHolder {

        private MeetingItemBinding mBinding;

        MyListMeetingHolder(MeetingItemBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        void updateElement(Meeting meeting) {
            createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            strMeetDat = createDate.format(meeting.getDateBegin());
            strMeetDat = strMeetDat.substring(11, 16);

            binding.text.setText(String.valueOf(
                    meeting.getRoom().getRoom() + " - "
                            + strMeetDat + " - "
                            + meeting.getSubject()));
            binding.participation.setText(meeting.getParticipants());
            binding.icone.setColorFilter(Color.parseColor(meeting.getRoom().getColor()));
        }
    }
}