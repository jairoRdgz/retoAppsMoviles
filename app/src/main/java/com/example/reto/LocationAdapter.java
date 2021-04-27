package com.example.reto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter  extends RecyclerView.Adapter<LocationView> {

    private ArrayList<Location> locations;

    public LocationAdapter(){
        locations = new ArrayList<>();
        this.notifyDataSetChanged();
    }

    public void addLocation(Location location){
        locations.add(location);
    }

    @NonNull
    @Override
    public LocationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.locationrow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        LocationView locationView = new LocationView(rowroot);

        return locationView;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationView holder, int position) {
        holder.getNombre().setText(locations.get(position).getName());
        holder.getDireccion().setText(locations.get(position).getAddress());
        holder.getCoordenadas().setText("latitud:"+locations.get(position).getLat()+"  longitud:"+locations.get(position).getLon());


    }

    @Override
    public int getItemCount() {
        return locations.size();
    }



}
