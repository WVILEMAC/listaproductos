package com.example.tarea3_listaproductos;

import java.util.List;

public class ApiResponse {
    private List<Producto> data;

    public List<Producto> getData() {
        return data;
    }

    public void setData(List<Producto> data) {
        this.data = data;
    }
}
