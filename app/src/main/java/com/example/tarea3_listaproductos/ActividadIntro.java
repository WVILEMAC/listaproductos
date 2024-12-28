package com.example.tarea3_listaproductos;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;

import java.util.Set;
import java.util.HashSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import android.content.Intent;

public class ActividadIntro extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private ChipGroup chipGroup;
    private List<Producto> todosLosProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_intro);

        recyclerView = findViewById(R.id.recyclerView);
        chipGroup = findViewById(R.id.chipGroup);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Iniciar carga de datos
        cargarCategorias();
        cargarProductos();
    }

    private void cargarCategorias() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getCategorias().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    configurarChips(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(ActividadIntro.this, "Error al cargar categorías", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarChips(List<String> categorias) {
        // Agregar chip "Todos"
        Chip chipTodos = new Chip(this);
        chipTodos.setText("Todos");
        chipTodos.setCheckable(true);
        chipTodos.setChecked(true);
        chipGroup.addView(chipTodos);

        // Agregar chips para cada categoría
        for (String categoria : categorias) {
            Chip chip = new Chip(this);
            chip.setText(categoria);
            chip.setCheckable(true);
            chipGroup.addView(chip);
        }

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = findViewById(checkedId);
            if (chip != null) {
                filtrarProductos(chip.getText().toString());
            }
        });
    }

    private void cargarProductos() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    todosLosProductos = response.body();
                    mostrarProductos(todosLosProductos);
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(ActividadIntro.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarProductos(List<Producto> productos) {
        if (adapter == null) {
            adapter = new ProductoAdapter(productos, producto -> {
                Intent intent = new Intent(ActividadIntro.this, ActividadRegistroUsuario.class);
                intent.putExtra("producto", new Gson().toJson(producto));
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.actualizarProductos(productos);
        }
    }

    private void filtrarProductos(String categoria) {
        if (categoria.equals("Todos")) {
            mostrarProductos(todosLosProductos);
        } else {
            List<Producto> productosFiltrados = todosLosProductos.stream()
                    .filter(p -> p.getCategory().equalsIgnoreCase(categoria))
                    .collect(Collectors.toList());
            mostrarProductos(productosFiltrados);
        }
    }
}