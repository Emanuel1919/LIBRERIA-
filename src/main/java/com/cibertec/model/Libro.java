package com.cibertec.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro") // Coincide con tu SQL
    private int idLibro;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autor")
    private String autor;

    @Column(name = "precio") // Sin precision/scale para evitar el error de Hibernate 7
    private Double precio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "imagen_url") // Coincide con tu SQL: imagen_url
    private String imagenUrl;

    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER para ver la categoría fácilmente en la lista
    @JoinColumn(name = "id_categoria") // Coincide con tu FK en SQL
    private Categoria categoria;

    // Constructor vacío (Obligatorio para JPA)
    public Libro() {}

    // Getters y Setters
    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}