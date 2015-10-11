package com.example.abgomsale.iot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.sourceforge.jFuzzyLogic.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends Activity {

    TextView tv_response;
    static View.OnClickListener myOnClickListener;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<CategoryData> categories;
    private static String TAG = "MainActivity";
    private String temp;
    public static List<Integer> randomNumbers;
    //public static String temperature;
    //public static String ambient;
    //public static String blind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_grid);


        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        categories = new ArrayList<CategoryData>();
        for(int i = 0; i < CategoryDetails.cardHeading.length; i++) {
            categories.add(new CategoryData(
                    CategoryDetails.cardHeading[i],
                    0,
                    CategoryDetails.id_[i],
                    ""
            ));
        }

        adapter = new MyAdapter(categories);
        recyclerView.setAdapter(adapter);

        myOnClickListener = new MyOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        if(id == R.id.rules) {
            new SendJSONRequest().execute("getRules");
            Intent i = new Intent(MainActivity.this, ListDisplay.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }



	private class MyOnClickListener implements View.OnClickListener {

		private final Context context;

		private MyOnClickListener(Context context) {
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			new SendJSONRequest().execute();
		}

	}

    @Override
    public void onPause(){
        Intent service_intent = new Intent(MainActivity.this,SensorService.class);
        stopService(service_intent);
        super.onPause();

    }

    @Override
    public void onDestroy()
    {
        Intent service_intent = new Intent(MainActivity.this,SensorService.class);
        stopService(service_intent);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent service_intent = new Intent(MainActivity.this,SensorService.class);
        startService(service_intent);

    }

    public void getRandomNumber() {

        Random random = new Random();
        int count = 8;
        randomNumbers = new ArrayList<Integer>(count);
        for (int i = 0; i < count; i++) {
            int number;
            do {
                number = random.nextInt(CategoryDetails.myCardColors.size());
            } while (randomNumbers.contains(number));
            randomNumbers.add(number);
        }


    }

}
