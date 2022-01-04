package com.example.course_project_withactivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String productList[];
    int listImages[];
    String pointList[];
    LayoutInflater inflater;
    public String prName;
    public int image;
    public String points;

    public CustomBaseAdapter(Context ctx, String[] productList, int[] images, String pointList[]){
        this.context = ctx;
        this.productList = productList;
        this.listImages = images;
        this.pointList = pointList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return productList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView txtView = (TextView) convertView.findViewById(R.id.productName);
        TextView txtViewPoints = (TextView) convertView.findViewById(R.id.productPoints);
        ImageView productImage = (ImageView) convertView.findViewById(R.id.imageIcon);
        prName = productList[position];
        points = pointList[position];
        image = listImages[position];
        txtView.setText(productList[position]);
        txtViewPoints.setText(pointList[position] + " points");
        productImage.setImageResource(listImages[position]);
        return convertView;
    }
}
