package com.amine.mareu.Controller;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.databinding.MeetingItemBinding;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyListMeetingAdapter extends RecyclerView.Adapter<MyListMeetingAdapter.MyListMeetingHolder> {

  private MeetingItemBinding binding;
  private final List<Meeting> mMeetingList;

  public MyListMeetingAdapter(List<Meeting> items) {
    this.mMeetingList = items;
  }

  @NonNull
  @Override
  public MyListMeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    binding = MeetingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new MyListMeetingHolder(binding);
  }

  @Override
  public void onBindViewHolder(MyListMeetingHolder holder, int position) {
    holder.updateElement(mMeetingList.get(position));
    holder.mBinding.delete.setOnClickListener((View v) -> {
      // TODO La supression ne fonctionne pas - Trouver une solution pour envoyer l'information à MainActivity pour suppriumer le Meeting
      notifyItemRemoved(position);
      notifyItemRangeChanged(position, mMeetingList.size());
      notifyDataSetChanged();
    });
    holder.mBinding.superItem.setOnClickListener((View v) -> {
      //Intent intent = new Intent(v.getContext(), ShowMeetingActivity.class);
      //intent.putExtra("meeting", (Parcelable) mMeetingList);
      //v.getContext().startActivity(intent);
    });
  }

  @Override
  public int getItemCount() {
    return mMeetingList.size();
  }

  public class MyListMeetingHolder extends RecyclerView.ViewHolder {

    private final MeetingItemBinding mBinding;

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