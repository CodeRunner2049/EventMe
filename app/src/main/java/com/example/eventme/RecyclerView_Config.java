package com.example.eventme;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private EventAdapter EventsAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<EventBox> events, List<String> keys)
    {
        mContext = context;
        EventsAdapter = new EventAdapter(events, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(EventsAdapter);
    }

    class EventItemView extends RecyclerView.ViewHolder
    {
        private TextView eventID;
        private TextView Name;
        private TextView Cost;
        private TextView Date;
        private ImageView imageView;
//        private TextView DestCoord;
//        private TextView eventDescription;

        private String key;

        public EventItemView(ViewGroup parent)
        {
            super(LayoutInflater.from(mContext).inflate(R.layout.recycle_item, parent, false));

            imageView = (ImageView) itemView.findViewById(R.id.m_image);
            eventID = (TextView) itemView.findViewById(R.id.eventID);
            Name = (TextView) itemView.findViewById(R.id.Name);
//            eventDescription = (TextView) itemView.findViewById(R.id.eventDescription);
            Cost = (TextView) itemView.findViewById(R.id.Cost);
            Date = (TextView) itemView.findViewById(R.id.Date);
//            DestCoord = (TextView) itemView.findViewById(R.id.DestCoord);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra("eventId", eventID.getText().toString());
                    mContext.startActivity(intent);
                }
            });

        }
        public void bind( EventItemView holder, EventBox event, String key)
        {
            Glide.with(mContext).load(event.getImage_url()).into(holder.imageView);
            eventID.setText(event.getId());
            Name.setText(event.getName());
//            eventDescription.setText(event.getEvent_Description());
            Cost.setText(event.getCost().toString());

            Date.setText(event.getDate().substring(0,event.getDate().length()-4));
//
//            String destination_loc = "(" + event.getLatitude().toString() + ", " + event.getLongitude().toString() + ")";
//            DestCoord.setText(destination_loc);

            this.key = key;
        }
    }
    class EventAdapter extends RecyclerView.Adapter<EventItemView>
    {
        private List<EventBox> event;
        private List<String> keys;

        public EventAdapter(List<EventBox> event, List<String> keys)
        {
            this.event = event;
            this.keys = keys;
        }

        @Override
        public void onBindViewHolder(@NonNull EventItemView holder, int position) {
            holder.bind(holder, event.get(position), keys.get(position));
        }

        @NonNull
        @Override
        public EventItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View v = LayoutInflater.from(mContext).inflate(R.layout.recycle_item, parent, fa)
            return new EventItemView(parent);
        }

        @Override
        public int getItemCount() {
            return event.size();
        }

    }
}
