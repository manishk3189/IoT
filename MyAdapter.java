package com.example.abgomsale.iot;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by mani8177 on 7/16/15.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<CategoryData> categoryDataSet;
    private static ArrayList<Integer> randomNum = new ArrayList<>();
    private static String TAG = "MyAdapter";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCategory;
        ImageView imageViewIcon;
        TextView textViewValue;
        CardView cv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewCategory = (TextView) itemView.findViewById(R.id.textViewCategory);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.cv = (CardView) itemView.findViewById(R.id.card_view);
            this.textViewValue = (TextView) itemView.findViewById(R.id.textViewValue);
        }
    }

    public MyAdapter(ArrayList<CategoryData> categories) {
        this.categoryDataSet = categories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);
        view.setPadding(18, 18, 18, 18);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewCategory = holder.textViewCategory;
        ImageView imageView = holder.imageViewIcon;
        imageView.setImageResource(categoryDataSet.get(listPosition).getImage());
        final TextView summary = holder.textViewValue;
        final CardView cardView = holder.cv;

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendJSONRequest().execute("getAmbient");
                new SendJSONRequest().execute("getTemp");
                int color = 0;
                if(listPosition == 0) {
                    color = setColor(0);
                    cardView.setBackgroundColor(CategoryDetails.myTempColors.get(color));
                }
                if(listPosition == 1) {
                    color = setColor(1);
                    cardView.setBackgroundColor(CategoryDetails.myAmbientColors.get(color));
                }
                if (listPosition == 0) {


                    summary.setText(MainActivity.temperature + " \u2109");
                }
                if (listPosition == 1) {

                    Double num1 = Double.parseDouble(MainActivity.ambient);
                    Math.round(num1);
                    summary.setText(num1.toString());
                }
                if (listPosition == 2) {
                    summary.setText("");
                }

            }
        });
        String temp_text = categoryDataSet.get(listPosition).getCategoryName();
        Log.d(TAG, temp_text);
        Log.d(TAG, listPosition + "");

        textViewCategory.setText(temp_text);



        summary.setTextColor(Color.WHITE);


        if(listPosition == 0) {

            summary.setText(MainActivity.temperature);
        }
        if(listPosition == 1) {

            summary.setText(MainActivity.ambient);
        }
        if(listPosition == 2) {
            summary.setText("");
        }


    }

    @Override
    public int getItemCount() {
        return categoryDataSet.size();
    }


    public int setColor( int position) {

        switch(position) {

            case 0 :
            double num = Double.parseDouble(MainActivity.temperature);
            if (num < 40) //freezing
                return 0;
            if (num >= 40 && num < 68) //cold
                return 1;
            if (num >= 68 && num <= 77) //normal
                return 2;
            if (num > 77 && num < 85) //warm
                return 3;
            if (num >= 85) //Summer Hot
                return 4;
            break;
            case 1: double num1 = Double.parseDouble(MainActivity.ambient);
                if(num1 >=0 && num1 < 33)
                    return 0;
                if(num1 >= 33 && num1 < 66)
                    return 1;
                if(num1 >=66)
                    return 2;
                break;

        }
        return 0;
    }
}
