package com.example.reto;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.reto.util.Cosntants;
import com.example.reto.util.HTTPSWebUtilDomi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment{

    private View view;
    private GoogleMap mMap;
    private LocationManager manager;
    private Marker myMarker;
    private ArrayList<Marker> myMarkers;
    private ArrayList<LatLng> latLngs;
    private FirebaseFirestore fb;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;


            LatLng posini = new LatLng(3.4,-76.5);
            myMarker = mMap.addMarker(new MarkerOptions().position(posini));

            requestLocation();

        }
    };

    public void addMarker(double lat, double lon){
        MarkerOptions options = new MarkerOptions();
        LatLng pos = new LatLng(lat,lon);
        options.position(pos);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        myMarkers.add(mMap.addMarker(options));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addMarkers() {
        latLngs = new ArrayList<LatLng>();

        HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();
        Gson gson = new Gson();

        new Thread(
                () -> {
                    String response = https.GETrequest(Cosntants.BASEURL + "location.json");
                    Type tipo = new TypeToken<HashMap<String, com.example.reto.Location>>() {}.getType();
                    HashMap<String, com.example.reto.Location> loca = gson.fromJson(response, tipo);

                    loca.forEach(
                            (key, value) -> {
                                //PasarLos values al metodo y asignarlos al row ese
                                addMarker(value.getLat(),value.getLon());
                            }
                    );
                }
        ).start();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        myMarkers = new ArrayList<Marker>();

        addMarkers();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        manager = (LocationManager) (LocationManager)getActivity().getSystemService(view.getContext().LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    public void requestLocation(){
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1,
                new LocationListener(){

                    @Override
                    public void onLocationChanged(@NonNull Location location){
                        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                        myMarker.setPosition(pos);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(@NonNull String provider) {

                    }

                    @Override
                    public void onProviderDisabled(@NonNull String provider) {

                    }
                });
    }

}