package com.example.dosis;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        // Conecta la barra inferior de tu diseño
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Carga la pantalla de Inicio por defecto al abrir la app
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor_fragments, new InicioFragment())
                    .commit();
        }

        // Configura el cambio de pantallas al tocar los iconos
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragmentSeleccionado = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_inicio) {
                fragmentSeleccionado = new InicioFragment();
            } else if (itemId == R.id.nav_tratamientos) {
                fragmentSeleccionado = new TratamientosFragment();
            } else if (itemId == R.id.nav_progreso) {
                fragmentSeleccionado = new ProgresoFragment();
            } else if (itemId == R.id.nav_perfil) {
                fragmentSeleccionado = new PerfilFragment();
            }

            if (fragmentSeleccionado != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor_fragments, fragmentSeleccionado)
                        .commit();
                return true;
            }
            return false;
        });
    }
}