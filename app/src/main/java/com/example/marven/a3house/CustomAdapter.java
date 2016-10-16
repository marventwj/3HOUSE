package com.example.marven.a3house;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Marven on 15-10-16.
 */
//this customer adapter is for the listview of the properties
public class CustomAdapter extends ArrayAdapter<Property> {

    private ArrayList<Property> propertyList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtStatus;
        ImageView imgProperty;
        TextView txtDetails;
    }

    public CustomAdapter(ArrayList<Property> propertyList, Context context) {
        super(context, R.layout.property_row_item, propertyList);
        this.propertyList = propertyList;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Property property = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.property_row_item, parent, false);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.status);
            viewHolder.imgProperty = (ImageView) convertView.findViewById(R.id.propertyMainImage);
            viewHolder.txtDetails = (TextView) convertView.findViewById(R.id.propertyDetails);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        //add animation to the scrolling
        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        //lastPosition = position;

        //set status correctly onto the view
        if (property.getRentStatus()) {
            viewHolder.txtStatus.setText("RENT");
            viewHolder.txtStatus.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
        } else {
            viewHolder.txtStatus.setText("SALE");
            viewHolder.txtStatus.setBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
        }

        viewHolder.txtDetails.setText(property.getDetails());
        //CircularImageView propertyImage = (CircularImageView) findViewById(R.id.propertyMainImage);
        //propertyImage.setImageBitmap(bitmap);
        // Return the completed view to render on screen
        return convertView;
    }
}
