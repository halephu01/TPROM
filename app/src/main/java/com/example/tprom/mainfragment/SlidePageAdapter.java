package com.example.tprom.mainfragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tprom.group.GroupFragment;
import com.example.tprom.group.groupdetails.GroupDetailsFragment;
import com.example.tprom.notification.NotificationFragment;

public class SlidePageAdapter extends FragmentStateAdapter {
    public SlidePageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 1:
                return new CalendarFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new GroupFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
