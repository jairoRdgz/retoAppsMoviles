package com.example.reto;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.reto.util.Cosntants;
import com.example.reto.util.HTTPSWebUtilDomi;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarFragment extends Fragment {

    private View view;
    private RecyclerView recycler;
    private ArrayAdapter<Location> locationAdapter;
    private Task<QuerySnapshot> locations;
    private LocationAdapter adapter;
    private LinearLayoutManager llManager;
    private FirebaseFirestore fb;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    public BuscarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BuscarFragment newInstance() {

        BuscarFragment fragment = new BuscarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buscar, container, false);

        llManager = new LinearLayoutManager(view.getContext());
        adapter = new LocationAdapter();
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(llManager);
        recycler.setAdapter(adapter);

        //locations = adapter.getLocations();
        recycler.setAdapter(adapter);
        fb = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();

        
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data: snapshot.getChildren()) {

                    String[] child = data.getValue().toString().split("=");
                    String childpath = child[0];
                    String childpathEstesi = childpath.replace("{","");

                    HashMap<String, String> loc = (HashMap<String, String>) data.child(childpathEstesi).getValue();
                    String nombre = loc.get("name");
                    String direccion = loc.get("address");
                    String id = loc.get("id");
                    HashMap<String, Double> loc2 = (HashMap<String, Double>) data.child(childpathEstesi).getValue();
                    double lat =  loc2.get("lat");
                    double lon =  loc2.get("lon");

                    Location location = new Location(id,nombre, direccion,lat, lon, null);
                    adapter.addLocation(location);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query = fb.collection("location");

        //getUsuarios();

        // Inflate the layout for this fragment
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getUsuarios() {
        HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();
        Gson gson = new Gson();


    }

}