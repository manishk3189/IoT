package com.example.abgomsale.iot;


/**
 * Created by mani8177 on 7/16/15.
 */
public class CategoryData {

    String categoryName;
    int image;
    int id_;
    String data;

    public CategoryData(String categoryName, int image, int id_, String data) {
        this.categoryName = categoryName;
        this.image = image;
        this.id_ = id_;
        this.data = data;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }

    public String getSummary() {
        return data;
    }
}
