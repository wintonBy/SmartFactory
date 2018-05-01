package com.sf.smartfactory.view;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.sf.smartfactory.constant.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/18 19:29
 * @package: com.sf.smartfactory.view
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class LineChartManager  {
    private LineChart lineChart;
    private YAxis leftAxis;   //左边Y轴
    private YAxis rightAxis;  //右边Y轴
    private XAxis xAxis;      //X轴
    private List<String> mTimes;

    public LineChartManager(LineChart lineChart) {
        this.lineChart = lineChart;
        leftAxis = lineChart.getAxisLeft();
        rightAxis = lineChart.getAxisRight();
        xAxis = lineChart.getXAxis();
        initLineChart();
    }
    /**
     * 初始化LineChart
     */
    private void initLineChart() {
        lineChart.setDrawGridBackground(false);
        //显示边界
        lineChart.setDrawBorders(false);

        //设置动画效果
        lineChart.animateY(1000, Easing.EasingOption.Linear);
        lineChart.animateX(1000, Easing.EasingOption.Linear);
        lineChart.setTouchEnabled(false);

        //折线图例 标签 设置
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //设置图描述
        Description description = lineChart.getDescription();
        description.setText("");
        //XY轴的设置
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int id = (int) value;
                long time =  TimeUtils.getMillis(mTimes.get(id), Constant.SERVER_SDF,0,0);
                String strTime = TimeUtils.getString(time,Constant.XAXIS_SDF,0,0);
                return strTime;
            }
        });

        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setEnabled(false);
        lineChart.setNoDataText("暂无数据");
    }


    public void setXAxis(float max, float min, int labelCount){
        if (max < min) {
            return;
        }
        xAxis.setAxisMinimum(min);
        xAxis.setAxisMaximum(max);
        xAxis.setLabelCount(labelCount);
        int step =(int) (max - min)/labelCount;
        xAxis.setGranularity(step);
        lineChart.invalidate();

    }

    public void showLineChart(List<ILineDataSet> dataSets){
        if(ObjectUtils.isEmpty(dataSets)){
            return;
        }
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    public void setTimes(List<String> mTimes) {
        this.mTimes = mTimes;
    }
}
