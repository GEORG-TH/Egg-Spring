package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.repositorio.AutorRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio  autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre){
        
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
    }
    
    
    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList<>();
        autores = autorRepositorio.findAll();
        return autores;
    }
    
    public void modificarAutor(String id, String nombre){
        Optional<Autor> respuestaAutor = autorRepositorio.findById(id);
        
        if (respuestaAutor.isPresent()) {       
            Autor autor = respuestaAutor.get();
            
            autor.setNombre(nombre);
            
            autorRepositorio.save(autor);
            
        }
    }
    
}
