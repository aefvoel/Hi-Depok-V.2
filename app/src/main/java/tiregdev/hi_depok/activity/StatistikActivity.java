package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import net.alhazmy13.wordcloud.WordCloud;
import net.alhazmy13.wordcloud.WordCloudView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import tiregdev.hi_depok.R;

public class StatistikActivity extends AppCompatActivity {

    private List<WordCloud> list;
    private String wordCloud;
    private HashMap<String, ArrayList<String>> getMessageStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadChart();
    }

    private void loadChart(){

        getMessageStats = (HashMap<String, ArrayList<String>>) getIntent().getSerializableExtra("STATISTIK_HASHMAP");

        List<String> messageCount = getMessageStats.get("ARRAY_MESSAGES");
        List<String> analysisCount = getMessageStats.get("ARRAY_ANALYSIS");
        List<String> scoreCount = getMessageStats.get("ARRAY_SCORES");
        List<String> monthCount = getMessageStats.get("ARRAY_MONTH");
        List<String> usersCount = getMessageStats.get("ARRAY_USERS");

        wordCloud = removeDup(TextUtils.join(" ", messageCount));
        HashMap<String, Integer> sentiment = new HashMap<>();

        Set<String> unique = new HashSet<>(analysisCount);
        for (String key : unique) {
            sentiment.put(key, Collections.frequency(analysisCount, key));
        }


        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        int pos, net, neg;
        if(sentiment.get("positif")==null){
            pos = 0;
        }else{
            pos = sentiment.get("positif");
        }

        if(sentiment.get("netral")==null){
            net = 0;
        }else{
            net = sentiment.get("netral");
        }

        if(sentiment.get("negatif")==null){
            neg = 0;
        }else{
            neg = sentiment.get("negatif");
        }

        try{
            yvalues.add(new PieEntry((pos*100) / analysisCount.size(), "Positif"));
            yvalues.add(new PieEntry((net*100) / analysisCount.size(), "Netral"));
            yvalues.add(new PieEntry((neg*100) / analysisCount.size(), "Negatif"));
        }catch (ArithmeticException e){
            yvalues.add(new PieEntry((pos*100), "Positif"));
            yvalues.add(new PieEntry((net*100), "Netral"));
            yvalues.add(new PieEntry((neg*100), "Negatif"));
        }


        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setDrawHoleEnabled(false);


        pieChart.getDescription().setText("Sentiment Analysis Result");
        pieChart.setData(data);
        pieChart.animateXY(1400, 1400);
        pieChart.invalidate();


        BarChart barChart = findViewById(R.id.barchart);

        final ArrayList<String> labels = new ArrayList<String>();
        String[] listOfMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        HashMap<String, Integer> mont = new HashMap<>();
        ArrayList<Integer> values = new ArrayList<>();
        Set<String> mo = new HashSet<>(monthCount);
        List<BarEntry> entries = new ArrayList<>();
        for (String key : mo) {
            mont.put(key, Collections.frequency(monthCount, key));
        }

        for(int i = 0;i<listOfMonth.length;i++){
            if(mont.containsKey(listOfMonth[i])){
                labels.add(listOfMonth[i]);
                values.add(mont.get(listOfMonth[i]));
            }else {
                labels.add(listOfMonth[i]);
                values.add(0);
            }

        }

        for(int i=0; i < listOfMonth.length; i++){
            entries.add(new BarEntry(i, values.get(i)));
        }

        BarDataSet set = new BarDataSet(entries, "");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(set);
        barData.setValueFormatter(new LargeValueFormatter());
        barData.setBarWidth(0.9f);
        barChart.getDescription().setText("Total Pesan / Bulan");
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int) value);
            }
        });
        barChart.getXAxis().setGranularity(1f);
        barChart.getLegend().setEnabled(false);
        barChart.animateXY(1400, 1400);
        barChart.invalidate();

        generateRandomText();
        WordCloudView wordCloud = (WordCloudView) findViewById(R.id.wordCloud);
        wordCloud.setSize(300,300);
        wordCloud.setColors(ColorTemplate.MATERIAL_COLORS);
        wordCloud.setDataSet(list);
        wordCloud.notifyDataSetChanged();


        ArrayList<Integer> valueUsers = new ArrayList<>();
        HashSet<String> users = new HashSet<>();
        HashMap<String, Integer> usr = new HashMap<>();
        users.addAll(usersCount);
        for (String key : users) {
            usr.put(key, Collections.frequency(usersCount, key));
        }

        usersCount.clear();
        usersCount.addAll(users);
        for(int i = 0;i<usersCount.size();i++){
            if(usr.containsKey(usersCount.get(i))){
                valueUsers.add(usr.get(usersCount.get(i)));
            }else {
                valueUsers.add(0);
            }
        }

        BarChart hBarChart = findViewById(R.id.horbarchart);
        List<BarEntry> hEntries = new ArrayList<>();
        for(int i=0; i < usersCount.size(); i++){
            hEntries.add(new BarEntry(i, valueUsers.get(i)));
        }

        BarDataSet hSet = new BarDataSet(hEntries, "");
        hSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData hBarData = new BarData(hSet);
        hBarData.setValueFormatter(new LargeValueFormatter());
        hBarData.setBarWidth(0.9f);
        hBarChart.getDescription().setEnabled(false);
        hBarChart.setData(hBarData);
        hBarChart.setFitBars(true);
        hBarChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return usersCount.get((int) value);
            }
        });
        hBarChart.getXAxis().setGranularity(1f);
        hBarChart.getAxisRight().setEnabled(false);
        hBarChart.getAxisLeft().setEnabled(false);
        hBarChart.getXAxis().setDrawGridLines(false);
        hBarChart.getAxisLeft().setAxisMaximum(messageCount.size());
        hBarChart.getAxisLeft().setAxisMinimum(0);
        hBarChart.getLegend().setEnabled(false);
        hBarChart.setDrawGridBackground(false);
        hBarChart.animateXY(1400, 1400);
        hBarChart.invalidate();


    }
    private void generateRandomText() {
        String[] data = wordCloud.split(" ");
        list = new ArrayList<>();
        Random random = new Random();
        for (String s : data) {
            list.add(new WordCloud(s, random.nextInt(200)));
        }
    }

    public String removeDup(String s) {
        return new LinkedHashSet<String>(Arrays.asList(s.split(" "))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", " ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                StatistikActivity.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
