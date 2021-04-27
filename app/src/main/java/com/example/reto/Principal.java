package com.example.reto;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Principal extends AppCompatActivity {
    protected BuscarFragment buscas;
    protected MapsFragment map;
    protected MenuFragment menu;
    protected BottomNavigationView navi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        navi = findViewById(R.id.principalnavi);
        buscas = BuscarFragment.newInstance();
        map = new MapsFragment();
        menu = MenuFragment.newInstance();

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

    public void mostrarFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, f);
        fragmentTransaction.commit();
    }


}
