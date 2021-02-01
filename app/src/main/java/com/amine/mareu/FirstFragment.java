package com.amine.mareu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amine.mareu.Controller.MyListMeetingAdapter;
import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.ActivityMainBinding;
import com.amine.mareu.databinding.FragmentFirstBinding;

import java.util.List;


public class FirstFragment extends Fragment {

    //ViewBindind
    private FragmentFirstBinding binding;

    private List<Meeting> mMeetings;
    private MeetingApiService mApiService;
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        configureRecyclerView();
    }


    private void configureRecyclerView() {
        mApiService = DI.getMeetingApiService();
        mMeetings = mApiService.getMeetings();
        mRecyclerView.setAdapter(new MyListMeetingAdapter(mMeetings));
    }
}

    /*public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }*/
