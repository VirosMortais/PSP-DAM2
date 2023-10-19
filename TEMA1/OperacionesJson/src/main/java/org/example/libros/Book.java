package org.example.libros;

import lombok.Data;

@Data
public class Book {
    private  String isbn;
    private  String title;
    private  String author;
    private int numeroPaginas;
    private int anioPublicacion;
}
