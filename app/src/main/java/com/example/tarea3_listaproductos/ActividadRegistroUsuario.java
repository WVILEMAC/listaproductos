package com.example.tarea3_listaproductos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ActividadRegistroUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_registro_usuario);

        // Obtener el producto de los extras
        String productoJson = getIntent().getStringExtra("producto");
        Producto producto = new Gson().fromJson(productoJson, Producto.class);

        // Inicializar vistas
        ImageView imagenDetalle = findViewById(R.id.imagenDetalle);
        TextView tituloDetalle = findViewById(R.id.tituloDetalle);
        TextView categoriaDetalle = findViewById(R.id.categoriaDetalle);
        TextView precioDetalle = findViewById(R.id.precioDetalle);
        TextView descripcionDetalle = findViewById(R.id.descripcionDetalle);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnComprar = findViewById(R.id.btnComprar);

        // Configurar datos
        Picasso.get()
                .load(producto.getImage())
                .placeholder(android.R.drawable.ic_menu_gallery)  // Imagen por defecto mientras carga
                .error(android.R.drawable.ic_menu_gallery)
                .into(imagenDetalle);

        tituloDetalle.setText(producto.getTitle());
        categoriaDetalle.setText(producto.getCategory());
        precioDetalle.setText(String.format("$%.2f", producto.getPrice()));
        descripcionDetalle.setText(producto.getDescription());

        if (producto.getRating() != null) {
            ratingBar.setRating((float) producto.getRating().getRate());
        }

        btnComprar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¡Gracias por tu compra!")
                    .setMessage("Gracias por comprar en nuestra tienda")
                    .setPositiveButton("Aceptar", (dialog, which) -> finish())
                    .show();
        });
    }
}
