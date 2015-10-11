package com.example.abgomsale.iot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mani8177 on 7/27/15.
 */
public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> /*implements View.OnClickListener*/ {
    private ArrayList<CategoryData> categoryDataSet;
    public static int deleteIndex;
    //private static int counter = 1;
    private static String TAG = "MyListAdapter";
    public static int pos;
    public Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView txtRule;
        public TextView txtBlind;
        public TextView index;
        public ImageButton deleteButton;
        public RelativeLayout rl;

        public ViewHolder(View v) {
            super(v);
            txtRule = (TextView) v.findViewById(R.id.rule_info);
            txtBlind = (TextView) v.findViewById(R.id.rule_blind_result);
            index = (TextView) v.findViewById(R.id.icon);
            deleteButton = (ImageButton) v.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(this);



        }

        @Override
    public void onClick(View v) {
            pos = getPosition();
            Log.d(TAG,pos+"");
            categoryDataSet.remove(pos);
            notifyItemRemoved(pos);
            //new SendJSONRequest().execute("remove");

            //finish();
//            context.startActivity(i);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyListAdapter(ArrayList<CategoryData> categories) {
        this.categoryDataSet = categories;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
       // v.setOnClickListener(RuleActivity.myOnClickListener);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        pos = position;
        if(position < 2)
            holder.deleteButton.setVisibility(View.GONE);


        String temp_text = categoryDataSet.get(position).getCategoryName();
        Log.d("MyListAdapter",temp_text);
        String[] temp = temp_text.split(" ");
        ArrayList<String> result = generateDisplayRules(temp);
        holder.txtRule.setText(result.get(0));
        holder.txtBlind.setText(result.get(1));
        holder.index.setText(String.valueOf(position + 1));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categoryDataSet.size();
    }

    public static ArrayList<String> generateDisplayRules(String[] temp) {
        ArrayList<String> rule = new ArrayList<>();
        String antecedent = null;
        String consequent = null;

        if(temp[1].equalsIgnoreCase("na")) {
            if(!temp[0].equalsIgnoreCase("na")) {
                antecedent = "If temperature IS "+ temp[0] + "THEN";
                consequent = "blind is " + temp[3] ;
                rule.add(antecedent);
                rule.add(consequent);
                return rule;
            }
            else {
                antecedent = "If ambient IS "+ temp[2] + "THEN";
                consequent = "blind is " + temp[3] ;
                rule.add(antecedent);
                rule.add(consequent);
                return rule;
            }
        }
        else {
            antecedent = "If temperature IS "+ temp[0] + " " + temp[1].toUpperCase() +  " ambient IS " + temp[2] + " THEN";
            consequent = "blind is " + temp[3] ;
            rule.add(antecedent);
            rule.add(consequent);
            return rule;
        }


    }

//    @Override
//    public void onClick(View v) {
//        //Log.d("View: ", v.toString());
//        //Toast.makeText(v.getContext(), mTextViewTitle.getText() + " position = " + getPosition(), Toast.LENGTH_SHORT).show();
//        if(v.equals()){
//            removeAt(getPosition());
//        }else if (mItemClickListener != null) {
//            mItemClickListener.onItemClick(v, getPosition());
//        }
//    }
//
//
//}
//
//    public void setOnItemClickListener(final AdapterView.OnItemClickListener mItemClickListener) {
//        this.mItemClickListener = mItemClickListener;
//    }
//
//    public void removeAt(int position) {
//        categoryDataSet.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, categoryDataSet.size());
//    }

}
