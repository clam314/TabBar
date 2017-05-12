package tabbar.clam314.com.tabbar;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by clam314 on 2016/3/1
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list;


    public TabAdapter(List<Fragment> list, FragmentManager fragmentManager){
        super(fragmentManager);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
}
