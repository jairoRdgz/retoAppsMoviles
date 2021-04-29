package com.example.reto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto.util.Cosntants;
import com.example.reto.util.HTTPSWebUtilDomi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    RecyclerView recycler;
    File file;
    List<Address> lista;
    LocationAdapter adapter;
    LinearLayoutManager llManager;
    FirebaseFirestore fb;
    FirebaseDatabase mDatabase;
    StorageReference sDatabase;
    ImageView picView;
    Uri uri;
    Intent intent;

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
        //View view2 = inflater.inflate(R.layout.fragment_buscar, container, false);

        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);

        btnAgregarImagen = view.findViewById(R.id.btnAgregarImagen);
        btnAgregarImagen.setOnClickListener(this);

        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

        place = view.findViewById(R.id.place);
        txtDirection = view.findViewById(R.id.txtDirection);

        picView = view.findViewById(R.id.picView);

        adapter = new LocationAdapter();


        fb = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        sDatabase = FirebaseStorage.getInstance().getReference();
        intent = new Intent();

        /*
        llManager = new LinearLayoutManager(view2.getContext());
        adapter = new LocationAdapter();
        recycler = (RecyclerView) view2.findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(llManager);
        recycler.setAdapter(adapter);*/



        // Inflate the layout for this fragment
        return view;
    }


    public void searchPlace() throws IOException {
        new Thread(
                () ->{
                    Geocoder geoCoder = new Geocoder(view.getContext());
                    try {
                        lista = geoCoder.getFromLocationName(place.getText().toString(),1);
                        txtDirection.setText(lista.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        ).start();

    }

    public void register() throws IOException {


        String nombre = place.getText().toString();
        String direccion = txtDirection.getText().toString();
        double lat = lista.get(0).getLatitude();
        double lon = lista.get(0).getLongitude();

        DatabaseReference mDatabaseReference = mDatabase.getReference();

        Location location = new Location(UUID.randomUUID().toString(),nombre, direccion,lat, lon, uri.getLastPathSegment());
        mDatabaseReference.child("location").push().setValue(location, 0);
        mDatabaseReference.child("imgs").push();
        adapter.addLocation(location);

        StorageReference sDatabaseReference = sDatabase.child("imgs");
        sDatabaseReference.putFile(uri);
        
    }

    public void agregarImagen(){

        Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(getActivity().getExternalFilesDir(null) + "/" +  UUID.randomUUID().toString());
        Log.e(">>>>", "" + file);
        uri = FileProvider.getUriForFile(view.getContext(), "com.example.reto", file);
        intento.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        System.out.println("llamando Activity for Result");
        startActivityForResult(intento, 12);

    }

    public void abrirGaleria(){
        Intent in = new Intent(Intent.ACTION_GET_CONTENT);
        in.setType("*/*");
        startActivityForResult(in, 13);
    }

    @Override
    public void onClick(View v){
        try {
            switch (v.getId()) {
                case R.id.btnBuscar:
                    searchPlace();
                    break;
                case R.id.btnAgregarImagen:
                    System.out.println("Click listener");
                    agregarImagen();
                    break;
                case R.id.btnRegistrar:
                    register();
                    break;
            }
        }catch (IOException e){
            System.out.println("Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == getActivity().RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(file.getPath());
            picView.setImageBitmap(imageBitmap);
            // uri = data.getData();
            System.out.println("Tomamos la joto");
        }else{
            System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFF");
        }
    }

}