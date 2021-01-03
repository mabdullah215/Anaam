package com.app.anaamapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.anaamapp.R;
import com.app.anaamapp.activities.ProfileSettings;
import com.app.anaamapp.adapters.DashboardAdapter;
import com.app.anaamapp.model.Dashboard;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DashboardFragment extends Fragment
{
    RecyclerView mRecyclerView;
    ArrayList<Dashboard>mList=new ArrayList<>();
    int [] fragmentList={R.id.navigation_home,-150,R.id.navigation_messages,R.id.navigation_notifications,0};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_dashboard, container, false);
        mRecyclerView=view.findViewById(R.id.data_list);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        setupList();
        DashboardAdapter adapter=new DashboardAdapter(getContext(),mList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DashboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                if(position==1)
                {
                    startActivity(new Intent(getContext(), ProfileSettings.class));
                }
                else if(position==4)
                {
                    logoutAlert();
                }
                else
                {
                    Navigation.findNavController(view).navigate(fragmentList[position]);
                }
            }
        });
        return view;
    }
    public void logoutAlert()
    {
        new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        FirebaseAuth.getInstance().signOut();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void setupList()
    {
        mList.clear();
        mList.add(new Dashboard("Explore Items","Explore items nearby your location",R.drawable.ic_search_dashboard,""));
        mList.add(new Dashboard("Your Profile","Manage your bidding and selling items",R.drawable.user_avatar,""));
        mList.add(new Dashboard("Messages","Manage your incoming messages",R.drawable.ic_chat,"2"));
        mList.add(new Dashboard("Notifications","Get Alerts for new offers around",R.drawable.ic_notification,"34"));
        mList.add(new Dashboard("Settings","Manage your app and other settings",R.drawable.ic_settings_icon,""));
    }
}