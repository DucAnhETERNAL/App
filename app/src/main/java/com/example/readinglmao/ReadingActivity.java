package com.example.readinglmao;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.readinglmao.Slider.SliderPagerAdapter;

public class ReadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        // Get the chapterId passed from the adapter
        int chapterId = getIntent().getIntExtra("chapterId", -1);

        // Set up ViewPager2 and its adapter
        ViewPager2 viewPager = findViewById(R.id.viewPage);
        SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter(this, chapterId);
        viewPager.setAdapter(sliderPagerAdapter);
    }

}
