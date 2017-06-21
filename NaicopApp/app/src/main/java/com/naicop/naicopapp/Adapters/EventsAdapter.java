package com.naicop.naicopapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.Helpers.DateHelper;
import com.naicop.naicopapp.Helpers.HelperFunctions;
import com.naicop.naicopapp.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

/**
 * Created by pazos on 20-Jun-17.
 */
public class EventsAdapter extends ArrayAdapter<Event>{

    protected Context context;
    protected  LayoutInflater layoutInflater;

    public EventsAdapter(Context context, List<Event> objects) {
        super(context,0, objects);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    protected class Holder{
        public ImageView eventImage;
        public TextView eventTitle;
        public TextView eventDescription;
        public TextView eventStartDate;
        public TextView eventEndDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        Holder holder = new Holder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.individual_event, null);
            holder.eventImage = (ImageView)convertView.findViewById(R.id.eventImage);
            holder.eventTitle = (TextView)convertView.findViewById(R.id.eventTitle);
            holder.eventDescription = (TextView)convertView.findViewById(R.id.eventDescription);
            holder.eventStartDate = (TextView)convertView.findViewById(R.id.eventStartDateText);
            holder.eventEndDate = (TextView)convertView.findViewById(R.id.eventEndDateText);
            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }

        Event event = getItem(position);
        Picasso.with(context).load(Constants.DOMAIN+event.imageUrl).into(holder.eventImage);
        holder.eventTitle.setText(event.title);
        holder.eventDescription.setText(event.description);

        holder.eventStartDate.setText("Empieza: "+ DateHelper.getTextDateTime(event.startDate));
        holder.eventEndDate.setText("Termina: "+DateHelper.getTextDateTime(event.endDate));

        return convertView;

    }

}

