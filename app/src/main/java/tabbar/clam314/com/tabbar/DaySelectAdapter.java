package tabbar.clam314.com.tabbar;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by clam314 on 2017/5/9
 */

public class DaySelectAdapter extends RecyclerView.Adapter<DaySelectAdapter.DayHolder> {
    private List<String> tvDayList;
    private ViewPager mViewPage;
    private RecyclerView mRecyclerView;

    public DaySelectAdapter(List<String> tvDayList, ViewPager viewPager) {
        this.tvDayList = tvDayList;
        mViewPage = viewPager;
        mViewPage.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setScrollPosition(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private boolean setIndicatorPositionFromTabPosition(int position) {
        if(mRecyclerView.findViewHolderForAdapterPosition(position) == null) {
            mRecyclerView.scrollToPosition(position);
            return false;
        }else {
            return true;
        }
    }

    private void setScrollPosition(int position, float positionOffset) {
        if(position >= 0 && position < mRecyclerView.getLayoutManager().getItemCount()) {
            if(setIndicatorPositionFromTabPosition(position))
            mRecyclerView.scrollBy(calculateScrollXForTab(position, positionOffset), 0);
        }
    }

    private int calculateScrollXForTab(int position, float positionOffset) {
        if(mRecyclerView != null) {
            RecyclerView.LayoutManager lm = mRecyclerView.getLayoutManager();
            View selectedChild = mRecyclerView.findViewHolderForAdapterPosition(position).itemView;
            View nextChild = position + 1 < lm.getItemCount()?mRecyclerView.findViewHolderForAdapterPosition(position).itemView:null;
            int selectedWidth = selectedChild != null?selectedChild.getWidth():0;
            int nextWidth = nextChild != null?nextChild.getWidth():0;
            int selectedLeft = selectedChild != null?selectedChild.getLeft():0;
            return selectedLeft + (int)((float)(selectedWidth + nextWidth) * positionOffset * 0.5F) + selectedWidth / 2 - lm.getWidth() / 2;
        } else {
            return 0;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return tvDayList != null ? tvDayList.size() : 0;
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DayHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final DayHolder holder, int position) {
        if(position == tvDayList.size()-1){
            holder.divide.setVisibility(View.GONE);
        }else {
            holder.divide.setVisibility(View.VISIBLE);
        }
        if(mViewPage.getCurrentItem() == position){
            holder.tab.setVisibility(View.VISIBLE);
        }else {
            holder.tab.setVisibility(View.GONE);
        }
        holder.tvDay.setText(tvDayList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = holder.getAdapterPosition();
                if(mViewPage.getAdapter() != null && mViewPage.getAdapter().getCount() > p){
                    mViewPage.setCurrentItem(p);
                }
            }
        });
    }

    static class DayHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tab_select)
        View tab;
        @BindView(R.id.tab_divide)
        View divide;
        @BindView(R.id.tv_day)
        TextView tvDay;

        DayHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
