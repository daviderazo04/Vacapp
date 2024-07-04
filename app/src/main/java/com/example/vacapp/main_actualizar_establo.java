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

import java.util.List;

import Clases.Establo;

public class main_actualizar_establo extends AppCompatActivity {

    private EditText et1, et2;
    private AdminSQLiteOpenHelper admin;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_actualizar_establo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et1=findViewById(R.id.editText6);
        et2=findViewById(R.id.editText7);
        spinner = findViewById(R.id.spinner2);
        admin = new AdminSQLiteOpenHelper(this);
        try {

            List<String> nombresEstablos = admin.obtenerCodigosEstablo();
            if(nombresEstablos.isEmpty()){
                Toast.makeText(this, "No se encontraron establos. Ingrese uno para continuar", Toast.LENGTH_SHORT).show();
                finish();
            }
            // Crear el adaptador para el spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresEstablos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Establecer el adaptador al spinner
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected item
                    String nombre = spinner.getSelectedItem().toString();
                    // Invoke the desired method with the selected item
                    llenarDatosEstablo(nombre);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(this, "No se han encontrado establos", Toast.LENGTH_SHORT).show();
        }
    }
    public void llenarDatosEstablo(String id){
        List<Establo> establosTodos = admin.obtenerTodosEstablos();
        int i, pos = -1;
        String an, lar;
        try {
            for (i = 0; i < establosTodos.size(); i++) {
                if(establosTodos.get(i).getCodigo().equals(id))
                    pos=i;
            }
            if(pos!=-1) {
                an = String.valueOf(establosTodos.get(pos).getAncho());
                lar = String.valueOf(establosTodos.get(pos).getLargo());
                et1.setText(an);
                et2.setText(lar);
            }

        } catch (NullPointerException e) {
            Toast.makeText(this, "No se encontraron datos del establo", Toast.LENGTH_SHORT).show();
        }
    }
    public void actualizarEstablo(View v){
        long resultado = admin.ModificarEstablo(spinner.getSelectedItem().toString(), Double.parseDouble(String.valueOf(et1.getText())), Double.parseDouble(String.valueOf(et2.getText())));
        if (resultado != -1) {
            Toast.makeText(this, "Se actualizaron los datos del establo", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar los datos del establo", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        finish();
    }
}