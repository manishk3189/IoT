package com.example.abgomsale.iot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class RuleActivity extends Activity {

    static View.OnClickListener myOnClickListener;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<CategoryData> categories;
    private static String TAG = "RuleActivity";
    public static ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //myOnClickListener = new MyOnClickListener(this);

        setContentView(R.layout.activity_rule);
        recyclerView = (RecyclerView) findViewById(R.id.list_recycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        layoutManager = new LinearLayoutManager(this);

        String[] tempArray;


        tempArray = CategoryDetails.receivedRules.split(" ");
        for(int i = 0; i < tempArray.length; i++) {
            Log.d(TAG, "index "+ i + " " + tempArray[i]);
        }
        Log.d(TAG, (String.valueOf( tempArray.length)));
        ArrayList<String> parsedrules = new ArrayList<>();


        String temp;
        for(int i = 0; i <= tempArray.length - 4; i = i+4) {
            temp = tempArray[i]+ " " + tempArray[i+1] + " " + tempArray[i+2] + " " + tempArray[i+3] + " ";
            Log.d(TAG,"tempArray"+temp);
            parsedrules.add(temp);
        }

        categories = new ArrayList<CategoryData>();
        for (int i = 0; i < parsedrules.size(); i++) {
            categories.add(new CategoryData(
                    parsedrules.get(i),
                    i,
                    CategoryDetails.id_[i % tempArray.length],
                    ""
            ));
        }
        adapter = new MyListAdapter(categories);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rule, menu);
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

    public void refresh() {
        Intent i = new Intent(RuleActivity.this,RuleActivity.class);
        startActivity(i);

    }
//
//    private class MyOnClickListener implements View.OnClickListener {
//
//        private final Context context;
//
//        private MyOnClickListener(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public void onClick(View v) {
//            int selectedItemPosition = recyclerView.getChildPosition(v);
//            RecyclerView.ViewHolder viewHolder
//                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
//            ImageButton deleteButton = (ImageButton) viewHolder.itemView.findViewById(R.id.deleteButton);
//            Log.d(TAG,"Clicked at "+ selectedItemPosition);
//        }
//
//        private void showItemList(View v) {
//
//        }
//    }

}
