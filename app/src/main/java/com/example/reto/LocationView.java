package com.example.reto;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class LocationView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;
    private TextView nombre;
    private TextView direccion;
    private TextView coordenadas;
    private ImageView image;

    public LocationView(ConstraintLayout root) {
        super(root);
        nombre = root.findViewById(R.id.nombre);
        direccion = root.findViewById(R.id.direccion);
        coordenadas = root.findViewById(R.id.coordenadas);
        image = root.findViewById(R.id.picture);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getNombre() {
        return nombre;
    }

    public TextView getDireccion() {
        return direccion;
    }

    public TextView getCoordenadas() {
        return coordenadas;
    }

    public ImageView getImage() {
        return image;
    }
}
