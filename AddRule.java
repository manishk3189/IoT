package com.example.abgomsale.iot;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddRule extends Activity {

    private Spinner temp_spinner,connector_spinner,ambient_spinner,blind_spinner;
    private Button save_rule;
    public static String rule;
    private static String rule_part1;
    private static String rule_part2;
    private static String rule_part3;
    private static String rule_part4;
    private static String TAG = "AddRuleActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rule);

        temp_spinner = (Spinner)findViewById(R.id.temp_spinner);
        connector_spinner =  (Spinner)findViewById(R.id.connector_spinner);
        ambient_spinner =  (Spinner)findViewById(R.id.ambient_spinner);
        blind_spinner =  (Spinner)findViewById(R.id.blind_spinner);
        save_rule = (Button)findViewById(R.id.button_add);


        addItemsOnSpinner();

        addListenerOnSpinnerItemSelection();
        save_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rule = rule_part1 + " " + rule_part2 + " " + rule_part3 + " " + rule_part4;
                new SendJSONRequest().execute("addRule");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_rule, menu);
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


    // add items into spinner dynamically
    public void addItemsOnSpinner() {


        //Add spinner for temperature
        List<String> list = new ArrayList<String>();
        list.add("Ignore");list.add("freezing");list.add("cold");list.add("comfort");list.add("warm");list.add("hot");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temp_spinner.setAdapter(dataAdapter);

        //Add spinner for Connector
        List<String> list1 = new ArrayList<String>();
        list1.add("Ignore");list1.add("AND");list1.add("OR");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        connector_spinner.setAdapter(dataAdapter1);


        //Ambient Spinner
        List<String> list2 = new ArrayList<String>();
        list2.add("Ignore");list2.add("dark");list2.add("dim");list2.add("bright");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ambient_spinner.setAdapter(dataAdapter2);

        //Blind Spinner
        List<String> list3 = new ArrayList<String>();
        list3.add("open");list3.add("half");list3.add("close");

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blind_spinner.setAdapter(dataAdapter3);


    }
    public void addListenerOnSpinnerItemSelection() {
       temp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               rule_part1 = temp_spinner.getSelectedItem().toString();
               Log.d(TAG,"1 "+rule_part1);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });


    connector_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            rule_part2 = connector_spinner.getSelectedItem().toString();
            Log.d(TAG,"2 "+parent.getAdapter().getItem(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

        ambient_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rule_part3 = ambient_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        blind_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rule_part4 = blind_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
}
}
