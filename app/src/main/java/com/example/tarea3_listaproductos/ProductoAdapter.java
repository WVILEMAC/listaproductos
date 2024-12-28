package com.example.tarea3_listaproductos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    private List<Producto> productos;
    private OnProductoClickListener listener;

    public interface OnProductoClickListener {
        void onProductoClick(Producto producto);
    }

    public ProductoAdapter(List<Producto> productos, OnProductoClickListener listener) {
        this.productos = productos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.nombre.setText(producto.getTitle());
        holder.categoria.setText(producto.getCategory());
        holder.precio.setText(String.format("$%.2f", producto.getPrice()));

        Picasso.get()
                .load(producto.getImage())
                .placeholder(android.R.drawable.ic_menu_gallery)  // Imagen por defecto mientras carga
                .error(android.R.drawable.ic_menu_gallery)
                .into(holder.imagen);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductoClick(producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void actualizarProductos(List<Producto> nuevosProductos) {
        this.productos = nuevosProductos;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombre, categoria, precio;

        ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenProducto);
            nombre = itemView.findViewById(R.id.nombreProducto);
            categoria = itemView.findViewById(R.id.categoriaProducto);
            precio = itemView.findViewById(R.id.precioProducto);
        }
    }
}

