package ninja.cyplay.com.playretail_api.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ninja.cyplay.com.playretail_api.ui.fragment.CustomerOffersFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerPlaylistFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerProfileFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerSalesHistoryFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.CustomerWishlistFragment;

public class CustomerSlidingViewPagerAdapter extends FragmentPagerAdapter {

    private CharSequence titles[];

    public CustomerSlidingViewPagerAdapter(FragmentManager fm, CharSequence mTitles[]) {
        super(fm);
        this.titles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CustomerProfileFragment();
            case 1:
                return new CustomerPlaylistFragment();
            case 2:
                return new CustomerWishlistFragment();
            case 3:
                return new CustomerOffersFragment();
            case 4:
                return new CustomerSalesHistoryFragment();
        }

        return new Fragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles != null ? titles.length : 0;
    }
}