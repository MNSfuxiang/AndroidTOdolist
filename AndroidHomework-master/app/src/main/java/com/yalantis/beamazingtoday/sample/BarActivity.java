package com.yalantis.beamazingtoday.sample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BarActivity extends AppCompatActivity {

    private BarChart mBarChart;
    private BarData mBarData;
    private PieData mPieData;
    private PieChart pc;

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initBarData();
//        setData();
        initPieData();

        initBarChart();
        initPieChart();
    }

    private void initView() {
        mBarChart = findViewById(R.id.bar_chart);
        pc = (PieChart) findViewById(R.id.pic_chart);
    }

    private void initBarData(){
        // 柱状图数据
        List<DailyGoal> goals = LitePal.findAll(DailyGoal.class);// 查询所有的数据
        ArrayList<BarEntry> yValues = new ArrayList<>();
        String date = null;
        int y1, y2; // y1是已经完成的目标，y2是还没有完成的目标
        int x = 0;
        for (DailyGoal goal: goals){
            x++;
            String[] hasDoneStrArray = goal.getHasDone().split("%%");
            String[] notDoneStrArray = goal.getNotDone().split("%%");
            y1 = hasDoneStrArray.length;
            y2 = notDoneStrArray.length;
            if (y1 == 1 && hasDoneStrArray[0].equals("")){
                y1 = 0;
            }
            if (y2 == 1 && notDoneStrArray[0].equals("")){
                y2 = 0;
            }
            yValues.add(new BarEntry(x, new float[]{y1, y2}));
        }
        // y 轴数据集
        BarDataSet barDataSet = new BarDataSet(yValues, "目标");
        // 设置 对应数据 颜色
        barDataSet.setColors(Color.rgb(104, 241, 175), Color.rgb(164, 228, 251));
        mBarData = new BarData(barDataSet);
    }

    private void initPieData() {
        int y1,y2;
        ArrayList<PieEntry> yValues_pie = new ArrayList<>();
        String date = null;
        // 获取饼图的数据
        date = getCurrentDate();
        List<DailyGoal> mGoals = getDailyGoals(date);
        for (DailyGoal goal: mGoals){
            String[] hasDoneStrArray = goal.getHasDone().split("%%");
            String[] notDoneStrArray = goal.getNotDone().split("%%");
            y1 = hasDoneStrArray.length;
            y2 = notDoneStrArray.length;

            if (y1 == 1 && hasDoneStrArray[0].equals("")){
                y1 = 0;
            }
            if (y2 == 1 && notDoneStrArray[0].equals("")){
                y2 = 0;
            }
            yValues_pie.add(new PieEntry(y1,"已完成"));
            yValues_pie.add(new PieEntry(y2,"未完成"));
        }


        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4A92FC"));
        colors.add(Color.parseColor("#ee6e55"));

        PieDataSet pieDataSet = new PieDataSet(yValues_pie, "");
        pieDataSet.setColors(colors);
        mPieData = new PieData(pieDataSet);
    }

    private void initBarChart() {
        mBarChart.setData(mBarData);
        mBarChart.setScaleEnabled(false);
        String descriptionStr_bar = "每天任务情况";
        Description description_bar = new Description();
        description_bar.setText(descriptionStr_bar);
        mBarChart.setDescription(description_bar);

        mBarChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        mBarChart.setDrawGridBackground(false);
        //背景阴影
        mBarChart.setDrawBarShadow(false);
        mBarChart.setHighlightFullBarEnabled(false);
        //显示边框
        mBarChart.setDrawBorders(true);
        //设置动画效果
        mBarChart.animateY(1000, Easing.Linear);
        mBarChart.animateX(1000, Easing.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        leftAxis = mBarChart.getAxisLeft();
        rightAxis = mBarChart.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = mBarChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }
    private void initPieChart(){
        pc.setData(mPieData);
        pc.setBackgroundColor(Color.WHITE);
        String descriptionStr_pie = getCurrentDate();
        Description description_pie = new Description();
        description_pie.setText(descriptionStr_pie);
        pc.setDescription(description_pie);

    }



    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return "" + year + "-" + month + "-" + day;
    }
    public List<DailyGoal> getDailyGoals(String date){
        List<DailyGoal> goals = LitePal.where("date=?",date).find(DailyGoal.class);
        return  goals;
    }


    public void setData() {

        List<DailyGoal> goals = LitePal.findAll(DailyGoal.class);// 查询所有的数据
        final List<String> xVals = new ArrayList<String>();

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        String date = null;
        int y1,y2;
        int i=0;
        for (DailyGoal goal: goals){
            i++;
            date= getCurrentDate();
            xVals.add(date);
            String[] hasDoneStrArray = goal.getHasDone().split("%%");
            String[] notDoneStrArray = goal.getNotDone().split("%%");
            y1 = hasDoneStrArray.length;
            y2 = notDoneStrArray.length;
            if (y1 == 1 && hasDoneStrArray[0].equals("")){
                y1 = 0;
            }
            if (y2 == 1 && notDoneStrArray[0].equals("")){
                y2 = 0;
            }
            yVals1.add(new BarEntry(y1,i));
            yVals2.add(new BarEntry(y2,i));
        }

        XAxis xAxis = mBarChart.getXAxis();


        BarDataSet set1 = new BarDataSet(yVals1, "已完成");
        set1.setColor(Color.rgb(104, 241, 175));
        BarDataSet set2 = new BarDataSet(yVals2, "未完成");
        set2.setColor(Color.rgb(164, 228, 251));


        int size=xVals.size();
        String[] array = (String[])xVals.toArray(new String[size]);
        set1.setStackLabels(array);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);


        mBarData = new BarData(dataSets);
    }
}
