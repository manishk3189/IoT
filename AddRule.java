package com.example.abgomsale.iot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
    private static String rule_part1 = "na";
    private static String rule_part2 = "na";
    private static String rule_part3 = "na";
    private static String rule_part4 = "na";
    private static String TAG = "AddRuleActivity";
    private boolean flag = true;
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




                Log.d(TAG,"SIZE OF MY RULESS" + CategoryDetails.myRules.size());

                if(rule_part1.equalsIgnoreCase("na") && rule_part3.equalsIgnoreCase("na")) {
                    Toast.makeText(getApplicationContext(),"Something is not right!! Please check the rules again",Toast.LENGTH_SHORT).show();
                }
                else if((rule_part1.equalsIgnoreCase("na") || rule_part3.equalsIgnoreCase("na"))&&(!rule_part2.equalsIgnoreCase("na"))) {
                    Toast.makeText(getApplicationContext(),"Something is not right!! Please check the rules again",Toast.LENGTH_SHORT).show();
                }
                else if ((!rule_part1.equalsIgnoreCase("na") && !rule_part3.equalsIgnoreCase("na"))&&(rule_part2.equalsIgnoreCase("na"))) {
                    Toast.makeText(getApplicationContext(),"Something is not right!! Please check the rules again",Toast.LENGTH_SHORT).show();

                }
                 else {  rule = rule_part1 + " " + rule_part2 + " " + rule_part3 + " " + rule_part4;

                   Log.d(TAG, "inside Rule :" + rule);
                    if(CategoryDetails.myRules.contains(rule)) {
                        Toast.makeText(getApplicationContext(),"Rule is already in the list",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            Toast.makeText(getApplicationContext(), "Rule added Successfully!!", Toast.LENGTH_SHORT).show();
                            new SendJSONRequest().execute("addRule");
                            Thread.sleep(1000);
                            new SendJSONRequest().execute("getRules");


                            Intent i = new Intent(AddRule.this, MainActivity.class);
                            startActivity(i);
                        } catch (Exception e) { }
                    }



                }

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
        list.add("freezing");list.add("cold");list.add("comfort");list.add("warm");list.add("hot");list.add("Ignore");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temp_spinner.setAdapter(dataAdapter);

        //Add spinner for Connector
        List<String> list1 = new ArrayList<String>();
        list1.add("AND");list1.add("OR");list1.add("Ignore");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        connector_spinner.setAdapter(dataAdapter1);


        //Ambient Spinner
        List<String> list2 = new ArrayList<String>();
        list2.add("dark");list2.add("dim");list2.add("bright");list2.add("Ignore");

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
               if(rule_part1.equalsIgnoreCase("Ignore"))
                   rule_part1 = "na";
               Log.d(TAG,"1 "+rule_part1);

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

               //rule_part1 = "na";
           }
       });


    connector_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            rule_part2 = connector_spinner.getSelectedItem().toString();
            Log.d(TAG,"2 "+parent.getAdapter().getItem(position));
            if(rule_part2.equalsIgnoreCase("Ignore"))
                rule_part2 = "na";

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

            rule_part2 = "na";
        }
    });

        ambient_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rule_part3 = ambient_spinner.getSelectedItem().toString();
                if(rule_part3.equalsIgnoreCase("Ignore"))
                    rule_part3 = "na";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //rule_part3 = "na";
            }
        });


        blind_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rule_part4 = blind_spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //rule_part4 = "na";
            }
        });
}
}
