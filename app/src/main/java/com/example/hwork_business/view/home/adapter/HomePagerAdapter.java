package com.example.hwork_business.view.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hwork_business.model.CHANNEL;
import com.example.hwork_business.view.discovery.DiscoveryFragment;
import com.example.hwork_business.view.friend.FriendFragment;
import com.example.hwork_business.view.mine.MineFragment;

/**
 * 首页ViewPager的adapter
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private CHANNEL[] mList = null;

    public HomePagerAdapter(FragmentManager fm, CHANNEL[] mList) {
        super(fm);
        this.mList = mList;
    }

    /**
     * 初始化对应的Fragment
     * @param i
     * @return
     */
    @Override
    public Fragment getItem(int i) {
        int type = mList[i].getValue();
        switch (type) {
            case CHANNEL.MINE_ID:
                return MineFragment.newInstance();
                case CHANNEL.DISCORY_ID:
                    return DiscoveryFragment.newInstance();
                    case CHANNEL.FRIEND_ID:
                        return FriendFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.length;
    }
}
