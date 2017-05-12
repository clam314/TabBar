package tabbar.clam314.com.tabbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.vp_shop)
    ViewPager vpShop;
    @BindView(R.id.rv_day)
    RecyclerView rvDay;

    private List<Date> week;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日", Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initWeek();
        init();
    }

    private void init(){
        vpShop.setAdapter(new TabAdapter(createFragmentList(),getSupportFragmentManager()));
        rvDay.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        rvDay.setAdapter(new DaySelectAdapter(createDayList(),vpShop));
    }

    private List<Fragment> createFragmentList(){
        List<Fragment> list = new ArrayList<>();
        for (Date d : week){
            list.add(PageFragment.newInstance());
        }
        return list;
    }

    private List<String> createDayList(){
        List<String> tvDay = new ArrayList<>();
        for(int i = 0; i<week.size(); i++){
            if(i== 0){
                tvDay.add("今天");
            }else if(i == 1){
                tvDay.add("明天");
            }else {
                tvDay.add(dateFormat.format(week.get(i)));
            }
        }
        return tvDay;
    }

    private void initWeek(){
        week = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        week.add(calendar.getTime());
        for(int i = 1; i < 7; i++){
            calendar.add(Calendar.DAY_OF_MONTH,1);
            week.add(calendar.getTime());
        }
    }
}
