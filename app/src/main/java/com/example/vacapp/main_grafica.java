package com.example.vacapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.graphics.Color;
import android.os.Bundle;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Clases.ProduccionPromedio;


public class main_grafica extends AppCompatActivity {

    private Spinner spinnerFrecuencia;
    private Button btnGraficar;
    private BarChart barChart;
    private AdminSQLiteOpenHelper dbHelper;
    private String selectedFrecuencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grafica);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spinnerFrecuencia = findViewById(R.id.raza_txt2);
        btnGraficar = findViewById(R.id.button2);
        barChart = findViewById(R.id.barChart); // Asegúrate de que tienes un BarChart en tu XML
        dbHelper = new AdminSQLiteOpenHelper(this);

        BarChart barChart = findViewById(R.id.barChart);
        barChart.getDescription().setEnabled(false);

        // Configurar el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.frecuencia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrecuencia.setAdapter(adapter);

        spinnerFrecuencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFrecuencia = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFrecuencia = null;
            }
        });

        btnGraficar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFrecuencia != null) {
                    loadChartData(selectedFrecuencia);
                } else {
                    Toast.makeText(main_grafica.this, "Seleccione una frecuencia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loadChartData(String frecuencia) {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        List<ProduccionPromedio> produccionPromedios = dbHelper.obtenerPromedioProduccion(frecuencia);

        if (produccionPromedios.isEmpty()) {
            Toast.makeText(this, "No hay datos disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < produccionPromedios.size(); i++) {
            ProduccionPromedio produccion = produccionPromedios.get(i);
            entries.add(new BarEntry(i, (float) produccion.getPromedio()));
            labels.add(produccion.getVacaCodigo());
        }

        BarDataSet dataSet = new BarDataSet(entries, "Producción de Leche");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate(); // refresh

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        barChart.getAxisRight().setEnabled(false);
    }
}