package com.example.appmovil_interfaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText txtCedula, txtNombres, txtCiudad, txtCorreo, txtTelefono, txtFecha;
    RadioGroup grpGenero;
    Button btnLimpiar, btnEnviar;

    String fechaSeleccionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarControles();
        configurarEventos();
    }

    private void iniciarControles() {
        txtCedula = findViewById(R.id.txtCedula);
        txtNombres = findViewById(R.id.txtNombres);
        txtCiudad = findViewById(R.id.txtCiudad);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtFecha = findViewById(R.id.txtFecha);
        grpGenero = findViewById(R.id.grpGenero);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnEnviar = findViewById(R.id.btnEnviar);
    }

    private void configurarEventos() {

        // Convertir nombres y ciudad a mayúsculas
        txtNombres.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) convertirMayusculas(txtNombres);
        });

        txtCiudad.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) convertirMayusculas(txtCiudad);
        });

        // DatePicker Moderno
        txtFecha.setOnClickListener(v -> mostrarDatePicker());

        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
        btnEnviar.setOnClickListener(v -> enviarDatos());
    }

    private void mostrarDatePicker() {
        MaterialDatePicker<Long> datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Selecciona fecha")
                        .build();

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            fechaSeleccionada = sdf.format(new Date(selection));
            txtFecha.setText(fechaSeleccionada);
        });
    }

    private void convertirMayusculas(EditText txt) {
        txt.setText(txt.getText().toString().trim().toUpperCase());
    }

    private void limpiarFormulario() {
        txtCedula.setText("");
        txtNombres.setText("");
        txtCiudad.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFecha.setText("");
        fechaSeleccionada = "";

        grpGenero.clearCheck();
    }

    private void enviarDatos() {

        if (txtCedula.getText().length() != 10) {
            txtCedula.setError("Cédula debe tener 10 dígitos");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(txtCorreo.getText()).matches()) {
            txtCorreo.setError("Correo no válido");
            return;
        }

        if (txtTelefono.getText().length() < 10) {
            txtTelefono.setError("Teléfono no válido");
            return;
        }

        if (fechaSeleccionada.isEmpty()) {
            Toast.makeText(this, "Seleccione fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        int generoId = grpGenero.getCheckedRadioButtonId();
        if (generoId == -1) {
            Toast.makeText(this, "Seleccione género", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rbGenero = findViewById(generoId);

        Intent i = new Intent(this, MostrarDatosActivity.class);
        i.putExtra("cedula", txtCedula.getText().toString());
        i.putExtra("nombres", txtNombres.getText().toString());
        i.putExtra("ciudad", txtCiudad.getText().toString());
        i.putExtra("correo", txtCorreo.getText().toString());
        i.putExtra("telefono", txtTelefono.getText().toString());
        i.putExtra("genero", rbGenero.getText().toString());
        i.putExtra("fecha", fechaSeleccionada);

        startActivity(i);
    }
}
