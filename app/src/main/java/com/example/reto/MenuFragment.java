package com.example.reto;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements View.OnClickListener{

    EditText place;
    ImageButton btnBuscar;
    ImageButton btnAgregarImagen;
    Button btnRegistrar;
    TextView txtDirection;
    View view;
    RecyclerView places;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {

        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);

        btnAgregarImagen = view.findViewById(R.id.btnAgregarImagen);
        btnAgregarImagen.setOnClickListener(this);

        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

        place = view.findViewById(R.id.place);
        txtDirection = view.findViewById(R.id.txtDirection);
        places = view.findViewById(R.id.places);

        // Inflate the layout for this fragment
        return view;

    }

    public void searchPlace() throws IOException {
        Geocoder geoCoder = new Geocoder(view.getContext());
        List<Address> lista = geoCoder.getFromLocationName(place.getText().toString(),1);
        txtDirection.setText(lista.get(0).getAddressLine(0));
    }

    public void register(){
        String nombre = place.getText().toString();
        String direccion = txtDirection.getText().toString();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnBuscar:
                try {
                    searchPlace();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case R.id.btnAgregarImagen:
                break;
            case R.id.btnRegistrar:
                register();
                break;
        }
    }
}