package com.example.abgomsale.iot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class HistoryActivity extends Activity {

    public ArrayAdapter adapter;
    public ArrayList list1 = new ArrayList();
    String TAG = "HistoryActivity";
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        list = (ListView) findViewById(R.id.list1);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list1);


        for(int i = 0; i < CategoryDetails.sensorResult.size(); i++) {
            Log.d(TAG, CategoryDetails.sensorResult.get(i).toString());
            String result[] = CategoryDetails.sensorResult.get(i).split(" ");
            list1.add(result[0] + "         " + result[1] + "℉          "+ result[2] + "                 " + result[3]);


        }
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /*LoadList loadList = new LoadList();
        loadList.execute("Start");*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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

        return super.onOptionsItemSelected(item);
    }



    public class LoadList extends AsyncTask<String, String, String> {

        String TAG = "LoadList";
        private ProgressDialog mProgDialog;

        @Override
        protected void onPreExecute() {
            mProgDialog = ProgressDialog.show(HistoryActivity.this, "", "Loading the history, Please wait....", true);
        }

        @Override
        protected String doInBackground(String... params) {
            ///Add the rules to the category

            for(int i = 0; i < CategoryDetails.sensorResult.size(); i++) {
                Log.d(TAG,CategoryDetails.sensorResult.get(i).toString());
                list1.add(CategoryDetails.sensorResult.get(i));
            }


            return null;

        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            if (mProgDialog.isShowing()) {
                mProgDialog.dismiss();
            }
                adapter.notifyDataSetChanged();


        }
    }
}
