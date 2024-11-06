package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.repositorio.EditorialRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) {

        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }
    
    public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = new ArrayList<>();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }
    
     public void modificarEditorial(String id, String nombre){
         Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(id);
        
        if (respuestaEditorial.isPresent()) {       
            Editorial editorial = respuestaEditorial.get();
            
            editorial.setNombre(nombre);
            
            editorialRepositorio.save(editorial);
            
        }
    }

}
