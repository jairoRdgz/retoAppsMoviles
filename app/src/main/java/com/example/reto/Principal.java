package com.example.reto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class Principal extends AppCompatActivity implements View.OnClickListener{
    protected BuscarFragment buscas;
    protected MapsFragment map;
    protected MenuFragment menu;
    protected BottomNavigationView navi;
    protected File file;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        navi = findViewById(R.id.principalnavi);
        buscas = BuscarFragment.newInstance();
        map = new MapsFragment();
        menu = MenuFragment.newInstance();
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        },11);

        mostrarFragment(menu);
        navi.setOnNavigationItemSelectedListener((menuI) -> {
            switch(menuI.getItemId()){
                case R.id.add:
                    mostrarFragment(menu);
                    break;
                case R.id.mapItem:
                     mostrarFragment(map);
                     break;
                case R.id.search:
                    mostrarFragment(buscas);
                    break;
            }
            return true;
        });
    }

    public void agregarImagen(){
        Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(getExternalFilesDir(null) + "/photo.png");
        startActivity(intento);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 11){
            boolean allGrant = true;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allGrant = false;
                    break;
                }
            }
            if(allGrant){
                Toast.makeText(this, "Todos los permisos concedidos", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "No todos los permisos fueron concedidos", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void mostrarFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, f);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {

    }
}
