package com.example.readinglmao.Slider;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.readinglmao.ui.fragment.UserReadingFragment;

public class SliderPagerAdapter extends FragmentStateAdapter {

    private int chapterId;  // Store the chapterId to pass to fragments

    public SliderPagerAdapter(@NonNull FragmentActivity fragmentActivity, int chapterId) {
        super(fragmentActivity);
        this.chapterId = chapterId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        UserReadingFragment fragment = new UserReadingFragment();

        // Pass the chapterId to the fragment
        Bundle bundle = new Bundle();
        bundle.putInt("chapterId", chapterId); // Pass the chapterId to the fragment
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 1;  // You can change this if you have multiple pages (e.g., based on the number of chapters)
    }
}
