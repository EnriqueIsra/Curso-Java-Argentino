package org.eitorresmendoza.java.jdbc;

import org.eitorresmendoza.java.jdbc.modelo.Categoria;
import org.eitorresmendoza.java.jdbc.modelo.Producto;
import org.eitorresmendoza.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.eitorresmendoza.java.jdbc.repositorio.Repositorio;
import org.eitorresmendoza.java.jdbc.util.ConexionDataBase;

import java.sql.*;
import java.util.Date;

public class EjemploJdbc {
    public static void main(String[] args) {


            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();

            System.out.println("============ listar ============");
            repositorio.listar().forEach(System.out::println);


            System.out.println("============ obtener por id ============");
            System.out.println(repositorio.porId(1L));

            System.out.println("============ insertar nuevo producto");
            Producto producto = new Producto();
            producto.setNombre("Notebook Omen HP");
            producto.setPrecio(2900);
            producto.setFechaRegistro(new Date());

            Categoria categoria = new Categoria();
            categoria.setId(3L);

            producto.setCategoria(categoria);

            repositorio.guardar(producto);
            System.out.println("producto guardado con éxito");
            repositorio.listar().forEach(System.out::println);

    }
}
