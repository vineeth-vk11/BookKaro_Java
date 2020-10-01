package com.example.bookkaro.HouseholdHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookkaro.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class HouseholdFragment extends Fragment {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private HouseholdServicesFragment servicesFragment;
    private HouseholdPackagesFragment packagesFragment;
    private HouseholdCustomerReviewsFragment customerReviewsFragment;

    String serviceId;
    String serviceName;
    String serviceIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_household, container, false);

        Bundle bundle = getArguments();
        serviceId = bundle.getString("serviceId");
        serviceName = bundle.getString("serviceName");
        serviceIcon = bundle.getString("serviceIcon");

        toolbar = view.findViewById(R.id.toolbar);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.view_pager);

        servicesFragment = new HouseholdServicesFragment();
        packagesFragment = new HouseholdPackagesFragment();
        customerReviewsFragment = new HouseholdCustomerReviewsFragment();

        Bundle bundle1 = new Bundle();
        bundle1.putString("serviceId",serviceId);
        bundle1.putString("serviceName",serviceName);
        bundle1.putString("serviceIcon",serviceIcon);

        servicesFragment.setArguments(bundle1);

        toolbar.setTitle("Services");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        viewPagerAdapter.addFragment(servicesFragment,"Services");
        viewPagerAdapter.addFragment(customerReviewsFragment,"Customer Reviews");

        viewPager.setAdapter(viewPagerAdapter);

        return view;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitle.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}