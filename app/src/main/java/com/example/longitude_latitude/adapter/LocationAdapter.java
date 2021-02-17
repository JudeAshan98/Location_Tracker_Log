package com.example.longitude_latitude.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.longitude_latitude.R;
import com.example.longitude_latitude.WebView;
import com.example.longitude_latitude.model.LocationModel;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<LocationModel> locationModels;
    private OnItemClick onItemClick;

    public LocationAdapter(List<LocationModel> locationModelList,Context context){
        this.context = context;
        this.locationModels = locationModelList;
//        this.onItemClick = (OnItemClick) context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = inflater.inflate(R.layout.locationview, parent,false);
        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationModel locationModel = locationModels.get(position);

        holder.Longitude_text.setText("" + locationModel.getLongitude());
        holder.Latitude_text.setText("" + locationModel.getLatitude());
        holder.log_id_text.setText(""+locationModel.getLog_id());
        holder.log_id_text.setTag(locationModel);
        holder.Location_pick_item.setTag(holder);
    }

    @Override
    public int getItemCount() {
        if (locationModels.size() > 0) {
            return locationModels.size();
        } else return 0;
    }


    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Longitude_text, Latitude_text, log_id_text;
        CardView Location_pick_item;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Longitude_text = (TextView) itemView.findViewById(R.id.longitude_text);
            Latitude_text = (TextView) itemView.findViewById(R.id.Latitude_text);
            log_id_text      = (TextView) itemView.findViewById(R.id.log_text);
            Location_pick_item = (CardView) itemView.findViewById(R.id.Zones_pick);
        }

        @Override
        public void onClick(View view) {
//            onItemClick.onItemClick(getAdapterPosition());

            LocationAdapter.LocationViewHolder viewholder = (LocationAdapter.LocationViewHolder) view.getTag();
            LocationModel HeaderObj = ((LocationModel) viewholder.log_id_text.getTag());
            Intent mIntent = new Intent(context, WebView.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("Issue_Pallet", HeaderObj);
            mIntent.putExtras(mBundle);
            context.startActivity(mIntent);
        }
    }


    public interface OnItemClick {
        void onItemClick(final int pos);

    }
}
