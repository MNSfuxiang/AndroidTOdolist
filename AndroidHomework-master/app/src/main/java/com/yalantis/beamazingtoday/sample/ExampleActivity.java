package com.yalantis.beamazingtoday.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.yalantis.beamazingtoday.interfaces.AnimationType;
import com.yalantis.beamazingtoday.interfaces.BatModel;
import com.yalantis.beamazingtoday.listeners.BatListener;
import com.yalantis.beamazingtoday.listeners.OnItemClickListener;
import com.yalantis.beamazingtoday.listeners.OnOutsideClickedListener;
import com.yalantis.beamazingtoday.ui.adapter.BatAdapter;
import com.yalantis.beamazingtoday.ui.animator.BatItemAnimator;
import com.yalantis.beamazingtoday.ui.callback.BatCallback;
import com.yalantis.beamazingtoday.ui.widget.BatRecyclerView;
import com.yalantis.beamazingtoday.util.TypefaceUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import   java.text.SimpleDateFormat;

/**
 * Created by maple on 12.06.19.
 */
public class ExampleActivity extends AppCompatActivity implements BatListener, OnItemClickListener, OnOutsideClickedListener {

    private BatRecyclerView mRecyclerView;
    private BatAdapter mAdapter;
    private List<BatModel> mGoals;
    private BatItemAnimator mAnimator;
    private TextView textTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        Log.w("life", "onCreate");


        setContentView(R.layout.activity_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ((TextView) findViewById(R.id.text_title)).setTypeface(TypefaceUtil.getAvenirTypeface(this));
        mRecyclerView = (BatRecyclerView) findViewById(R.id.bat_recycler_view);
        mAnimator = new BatItemAnimator();


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new BatCallback(this));
        itemTouchHelper.attachToRecyclerView(mRecyclerView.getView());
        mRecyclerView.getView().setItemAnimator(mAnimator);
        mRecyclerView.setAddItemListener(this);

        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.revertAnimation();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialGoals();//恢复数据

        // 获取当前的系统时间，设置为标题
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String  str = formatter.format(curDate);
        textTitle = (TextView)findViewById(R.id.text_title);
        textTitle.setText(str);
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeGoals();
    }


    // 新增目标
    @Override
    public void add(String string) {
        mGoals.add(0, new Goal(string));
        Log.w("add", "add: " + string);
        mAdapter.notify(AnimationType.ADD, 0);
    }

    @Override
    public void delete(int position) {
        mGoals.remove(position);
        mAdapter.notify(AnimationType.REMOVE, position);
    }

    @Override
    public void move(int from, int to) {
        if (from >= 0 && to >= 0) {
            mAnimator.setPosition(to);
            BatModel model = mGoals.get(from);
            mGoals.remove(model);
            mGoals.add(to, model);
            mAdapter.notify(AnimationType.MOVE, from, to);

            if (from == 0 || to == 0) {
                mRecyclerView.getView().scrollToPosition(Math.min(from, to));
            }
        }
    }

    @Override
    public void onClick(BatModel item, int position) {
        Log.w("click", "onClick: "+ item.isChecked());
        Toast.makeText(this, item.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOutsideClicked() {
        mRecyclerView.revertAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


        /*
         * pram1:组号 pram2:唯一的ID号 pram3:排序号 pram4:标题
         */
        menu.add(1, Menu.FIRST, Menu.FIRST, "数据统计");
        // 希望显示菜单就返回true
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 响应每个菜单项(通过菜单项的ID)
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(ExampleActivity.this,BarActivity.class);
                startActivity(intent);
                break;
        }
        //返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
        return true;
    }




    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return "" + year + "-" + month + "-" + day;
    }

    public static int getGapCount(String end_Date) { // 获取当前日期与设定的日期之间的天数
        Date startDate = null;
        Date endDate = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = sdf.parse("2019-6-12");
            endDate = sdf.parse(end_Date);

        }catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);


        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
    }

    public boolean isExistedInDatabase(String date){

        List<DailyGoal> goals =getDailyGoals(date);
        return goals.size() > 0;
    }

    public List<DailyGoal> getDailyGoals(String date){
        List<DailyGoal> goals = LitePal.where("date=?",date).find(DailyGoal.class);
        return  goals;
    }

    public void initialGoals(){
        // 给todolist添加这一天的目标
        mRecyclerView.getView().setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.getView().setAdapter(mAdapter = new BatAdapter(mGoals = new ArrayList<BatModel>(),
                this, mAnimator).setOnItemClickListener(this).setOnOutsideClickListener(this));

        String date = getCurrentDate();
        Log.w("in", "initalGoals: " + date );
        boolean isexisted = isExistedInDatabase(date);
        if (isexisted){ // 恢复数据
//            Log.w("initial", "initalGoals: 存在着表！"  );
            List<DailyGoal> goals = getDailyGoals(date);
            String str = null;

            for (DailyGoal goal: goals){
                String[] notDoneStrArray = goal.getNotDone().split("%%");
                //把还没做的目标重新显示在list中
                for (int i=0; i< notDoneStrArray.length;i++){
                    str = notDoneStrArray[i];
                    if (str.length() > 0)
                        mGoals.add(new Goal(str));
                }
                // 把完成的目标显示在list中
                String[] hasDoneStrArray = goal.getHasDone().split("%%");
                for (int i=0; i< hasDoneStrArray.length;i++){
                    str = hasDoneStrArray[i];
                    Goal target = new Goal(str);
                    target.setChecked(true);
                    if (target.getText().length() > 0)
                        mGoals.add(target);
                }


            }
        }
    }

    public void storeGoals(){
        // 退出应用的时候，把list中的goal写入数据库
        boolean isChecked = false;
        StringBuilder content_hasDone = new StringBuilder();
        StringBuilder content_notDone = new StringBuilder();
        String date = getCurrentDate();
        for(BatModel item: mGoals){
            isChecked = item.isChecked();
            if (isChecked){ // 如果被选中了，那么说明已经完成该任务，则存入hasDone
                content_hasDone.append(item.getText() + "%%");
            }
            else {
                content_notDone.append(item.getText() + "%%");
            }
        }
        DailyGoal goal = new DailyGoal();
        goal.setNotDone(content_notDone.toString());
        goal.setHasDone(content_hasDone.toString());
        goal.setDate(getCurrentDate());

        if (isExistedInDatabase(date)){//如果今天的目标已经在数据库中了，那么就直接修改
            LitePal.deleteAll(DailyGoal.class, "date=?" , date);
        }
        goal.save();
    }

}
