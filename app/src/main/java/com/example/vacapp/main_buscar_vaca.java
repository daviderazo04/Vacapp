package com.example.vacapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import Clases.Vaca;
import Clases.VacaAdapter;

public class main_buscar_vaca extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VacaAdapter vacaAdapter;
    private AdminSQLiteOpenHelper admin;
    private EditText editTextCodigo;
    private Spinner spinnerRaza;
    private Spinner spinnerEstablo;
    private Spinner spinnerEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_buscar_vaca);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        admin = new AdminSQLiteOpenHelper(this);
        editTextCodigo = findViewById(R.id.editTextText);
        spinnerRaza = findViewById(R.id.raza_txt2);
        spinnerEdad = findViewById(R.id.edad_txt);
        spinnerEstablo = findViewById(R.id.establo_txt);
        try {
            List<String> nombresEstablos = admin.obtenerCodigosEstablo();
            nombresEstablos.add("Cualquiera");
            // Crear el adaptador para el spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresEstablos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Establecer el adaptador al spinner
            spinnerEstablo.setAdapter(adapter);
        } catch (NullPointerException e) {
            Toast.makeText(this, "No se han encontrado establos", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClick(View view) {
        finish();
    }
    public void buscarVacas(View view) {
        String codigo = editTextCodigo.getText().toString().trim();
        String raza = spinnerRaza.getSelectedItem().toString();
        String edad = spinnerEdad.getSelectedItem().toString();
        String establo = spinnerEstablo.getSelectedItem().toString();

        // Convertimos valores para la base de datos
        if (raza.equals("Cualquiera")) raza = null;
        if (edad.equals("Cualquiera")) edad = null;
        if (establo.equals("Cualquiera")) establo = null;
        if (codigo.isEmpty()) codigo = null;

        List<Vaca> vacaList = admin.buscarVacasPorCriterios(codigo, raza, establo, edad);

        vacaAdapter = new VacaAdapter(this, vacaList);
        recyclerView.setAdapter(vacaAdapter);

        if (vacaList.isEmpty()) {
            Toast.makeText(this, "No se encontraron vacas.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vacas encontradas: " + vacaList.size(), Toast.LENGTH_SHORT).show();
        }
    }


}