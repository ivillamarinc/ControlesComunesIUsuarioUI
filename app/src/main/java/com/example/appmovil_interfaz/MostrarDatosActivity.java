package com.example.appmovil_interfaz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MostrarDatosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        TextView lblDatos = findViewById(R.id.lblDatos);

        String datos =
                "Cédula: " + getIntent().getStringExtra("cedula") + "\n" +
                        "Nombres: " + getIntent().getStringExtra("nombres") + "\n" +
                        "Ciudad: " + getIntent().getStringExtra("ciudad") + "\n" +
                        "Correo: " + getIntent().getStringExtra("correo") + "\n" +
                        "Teléfono: " + getIntent().getStringExtra("telefono") + "\n" +
                        "Género: " + getIntent().getStringExtra("genero") + "\n" +
                        "Fecha: " + getIntent().getStringExtra("fecha");

        lblDatos.setText(datos);
    }
}
