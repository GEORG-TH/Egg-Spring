package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MyException;
import com.egg.biblioteca.repositorio.AutorRepositorio;
import com.egg.biblioteca.repositorio.EditorialRepositorio;
import com.egg.biblioteca.repositorio.LibroRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MyException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());

        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);

    }

    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();
        return libros;
    }

    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) {
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {

            autor = respuestaAutor.get();

        }

        if (respuestaEditorial.isPresent()) {

            editorial = respuestaEditorial.get();

        }

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);

            libroRepositorio.save(libro);
        }

    }

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MyException{

        if (isbn == null) {
            throw new MyException("El isbn no puede ser nulo");
        }

        if (titulo.isEmpty() || titulo == null) {
            throw new MyException("El titulo no puede ser nulo o estar vacío");
        }

        if (ejemplares == null) {
            throw new MyException("Los ejemplares no pueden ser nulos o estar vacíos");
        }

        if (idAutor.isEmpty() || idAutor == null) {
            throw new MyException("El idAutor no puede ser nulo o estar vacío");
        }

        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MyException("El idEditorial no puede ser nulo o estar vacío");
        }
    }

}
