package com.nyklundamade.pickulacka;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity {

    private ViewPager2 hlavniViewPager;
    private static final int POCET_FRAGMENTU = 5;
    private FragmentStateAdapter pagerAdapter;
    private BottomNavigationView dolniMenuBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView bannerAd = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);

        pagerAdapter = new ScreenSlidePagerAdapter(this);

        dolniMenuBottomNavigation = findViewById(R.id.bottomNavigationDolniMenu);
        dolniMenuBottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_vzdalenost) {
                hlavniViewPager.setCurrentItem(0);
            } else if (itemId == R.id.menu_plocha) {
                hlavniViewPager.setCurrentItem(1);
            } else if (itemId == R.id.menu_cas) {
                hlavniViewPager.setCurrentItem(2);
            } else if (itemId == R.id.menu_objem) {
                hlavniViewPager.setCurrentItem(3);
            } else if (itemId == R.id.menu_ostatni) {
                hlavniViewPager.setCurrentItem(4);
            }
            return true;
        });

        hlavniViewPager = findViewById(R.id.viewPagerHlavni);
        hlavniViewPager.setAdapter(pagerAdapter);
        hlavniViewPager.setPageTransformer(new ZoomOutPageTransformer());
        hlavniViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        dolniMenuBottomNavigation.getMenu().findItem(R.id.menu_vzdalenost).setChecked(true);
                        break;
                    case 1:
                        dolniMenuBottomNavigation.getMenu().findItem(R.id.menu_plocha).setChecked(true);
                        break;
                    case 2:
                        dolniMenuBottomNavigation.getMenu().findItem(R.id.menu_cas).setChecked(true);
                        break;
                    case 3:
                        dolniMenuBottomNavigation.getMenu().findItem(R.id.menu_objem).setChecked(true);
                        break;
                    case 4:
                        dolniMenuBottomNavigation.getMenu().findItem(R.id.menu_ostatni).setChecked(true);
                        break;
                }
            }
        });
    }

        private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: return new VzdalenostFragment();
                case 1: return new PlochaFragment();
                case 2: return new CasFragment();
                case 3: return new ObjemFragment();
                case 4: return new OstatniFragment();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return POCET_FRAGMENTU;
        }
    }
}