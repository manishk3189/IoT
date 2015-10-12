package com.example.abgomsale.iot;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDisplay extends ListActivity {
    ProgressDialog dialog;

    private static String TAG = "ListDisplay";
    public ArrayList list = new ArrayList();
    private static ArrayList<CategoryData> categories;
    public static int itemDeletePosition;

    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    public ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_display);


        /** Reference to the delete button of the layout main.xml */
        Button btnDel = (Button) findViewById(R.id.btnDel);


        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, list);
        LoadList loadList = new LoadList();
        loadList.execute("Start");


        /** Defining a click event listener for the button "Delete" */
        OnClickListener listenerDel = new OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemCount = getListView().getCount();
                if (itemCount > 1) {


                    /** Getting the checked items from the listview */
                    SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                    Log.d(
                            TAG, checkedItemPositions.toString());
                    itemDeletePosition = checkedItemPositions.keyAt(0);

                    //int itemCount = getListView().getCount();

                    for (int i = itemCount - 1; i >= 0; i--) {
                        if (checkedItemPositions.get(i)) {
                            adapter.remove(list.get(i));
                        }
                    }
                    checkedItemPositions.clear();
                    adapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Rule deleted Successfully!!", Toast.LENGTH_SHORT).show();
                    RefreshRules refreshRules = new RefreshRules();
                    refreshRules.execute("Start");
                } else {
                    Toast.makeText(getApplicationContext(), "Atleast one Rule should be present", Toast.LENGTH_LONG).show();
                }
            }
        };


        /** Setting the event listener for the delete button */
        btnDel.setOnClickListener(listenerDel);

        /** Setting the adapter to the ListView */
        setListAdapter(adapter);
    }

    private static ArrayList<String> generateDisplayRules(String[] temp) {
        ArrayList<String> rule = new ArrayList<>();
        String antecedent = null;
        String consequent = null;

        if (temp[1].equalsIgnoreCase("na")) {
            if (!temp[0].equalsIgnoreCase("na")) {
                antecedent = "If temperature IS " + temp[0] + "THEN";
                consequent = "blind is " + temp[3];
                rule.add(antecedent);
                rule.add(consequent);
                return rule;
            } else {
                antecedent = "If ambient IS " + temp[2] + "THEN";
                consequent = "blind is " + temp[3];
                rule.add(antecedent);
                rule.add(consequent);
                return rule;
            }
        } else {
            antecedent = "If temperature IS " + temp[0] + " " + temp[1].toUpperCase() + " ambient IS " + temp[2] + " THEN";
            consequent = "blind is " + temp[3];
            rule.add(antecedent);
            rule.add(consequent);
            return rule;
        }


    }


    public class LoadList extends AsyncTask<String, String, String> {

        String TAG = "LoadList";
        private ProgressDialog mProgDialog;

        @Override
        protected void onPreExecute() {
            mProgDialog = ProgressDialog.show(ListDisplay.this, "", "Loading the latest rules, Please wait....", true);
            new SendJSONRequest().execute("getRules");
        }

        @Override
        protected String doInBackground(String... params) {
            ///Add the rules to the category
            String[] tempArray;


            tempArray = CategoryDetails.receivedRules.split(" ");
            for (int i = 0; i < tempArray.length; i++) {
                Log.d(TAG, "index " + i + " " + tempArray[i]);
            }
            Log.d(TAG, (String.valueOf(tempArray.length)));
            ArrayList<String> parsedrules = new ArrayList<>();


            String temp;
            for (int i = 0; i <= tempArray.length - 4; i = i + 4) {
                temp = tempArray[i] + " " + tempArray[i + 1] + " " + tempArray[i + 2] + " " + tempArray[i + 3];
                Log.d(TAG, "tempArray" + temp);
                parsedrules.add(temp);
            }
            CategoryDetails.myRules = parsedrules;
            Log.d(TAG, "MYRULES: " + CategoryDetails.myRules);

            categories = new ArrayList<CategoryData>();
            for (int i = 0; i < parsedrules.size(); i++) {
                categories.add(new CategoryData(
                        parsedrules.get(i),
                        i,
                        CategoryDetails.id_[i % tempArray.length],
                        ""
                ));
            }

            for (int i = 0; i < categories.size(); i++) {
                String temp_text = categories.get(i).getCategoryName();
                Log.d("MyListAdapter", temp_text);
                String[] temp1 = temp_text.split(" ");
                ArrayList<String> result = generateDisplayRules(temp1);
                list.add(result.get(0) + " " + result.get(1));

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

    public class RefreshRules extends AsyncTask<String, String, String> {

        String TAG = "LoadList";
        private ProgressDialog mProgDialog;

        @Override
        protected void onPreExecute() {
            mProgDialog = ProgressDialog.show(ListDisplay.this, "", "Refreshing the rules, Please wait....", true);
        }

        @Override
        protected String doInBackground(String... params) {
            ///Add the rules to the category
            String[] tempArray;


            tempArray = CategoryDetails.receivedRules.split(" ");

            ArrayList<String> parsedrules = new ArrayList<>();


            String temp;
            for (int i = 0; i <= tempArray.length - 4; i = i + 4) {
                temp = tempArray[i] + " " + tempArray[i + 1] + " " + tempArray[i + 2] + " " + tempArray[i + 3];
                Log.d(TAG, "tempArray" + temp);
                parsedrules.add(temp);
            }

            parsedrules.remove(itemDeletePosition);
            CategoryDetails.myRules = parsedrules;

/*            String builder = null;
            for(String s: parsedrules) {
                builder = builder + " " + s;
            }*/
            StringBuilder sb = new StringBuilder();

            for (String s : parsedrules) {
                sb.append(s);
                sb.append(" ");

            }
            CategoryDetails.receivedRules = sb.toString();

            //CategoryDetails.receivedRules = builder;

            Log.d
                    (TAG, " after delete receivedRules:" + CategoryDetails.receivedRules);


            String method = "request" + "," + itemDeletePosition;
            new SendJSONRequest().execute("remove");
            return null;

        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            if (mProgDialog.isShowing()) {
                mProgDialog.dismiss();
            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.rule_add) {
            Intent i = new Intent(ListDisplay.this, AddRule.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}
