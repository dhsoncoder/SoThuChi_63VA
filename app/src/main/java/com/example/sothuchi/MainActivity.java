package com.example.sothuchi;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    TextView calender;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.trangtaomoi) {
                selectedFragment = new AddFragment();

            } else if (itemId == R.id.tranglich) {
                selectedFragment = new CalendarFragment();

            } else if (itemId == R.id.trangbaocao) {
                selectedFragment = new BaocaoThangFragment();

            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, selectedFragment)
                        .commit();
            }
            return true;
        });
        // Set default selection
        // Set fragment mặc định khi khởi động
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new AddFragment())
                .commit();
//        TabLayout tabLayout = findViewById(R.id.tab_layout);
//        ViewPager2 viewPager = findViewById(R.id.view_pager);
//
//        // Set up the ViewPager with the sections adapter.
//        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
//        viewPager.setAdapter(adapter);
//
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            if (tab != null) {
//                tab.view.setBackgroundResource(R.drawable.tab_background_selector);
//            }
//        }
//
//        new TabLayoutMediator(tabLayout, viewPager,
//                new TabLayoutMediator.TabConfigurationStrategy() {
//                    @Override
//                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                        switch (position) {
//                            case 0:
//                                tab.setText("Tiền chi");
//                                break;
//                            case 1:
//                                tab.setText("Tiền thu");
//                                break;
//                        }
//                    }
//                }).attach();


    }



}