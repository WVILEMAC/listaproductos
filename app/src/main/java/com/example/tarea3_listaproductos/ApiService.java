package com.example.tarea3_listaproductos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiService {
    @GET("products")
    Call<List<Producto>> getProductos();

    @GET("products/category/{categoria}")
    Call<List<Producto>> getProductosPorCategoria(@Path("categoria") String categoria);

    @GET("products/categories")
    Call<List<String>> getCategorias();
}
