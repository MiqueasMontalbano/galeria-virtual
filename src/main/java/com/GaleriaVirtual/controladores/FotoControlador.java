package com.GaleriaVirtual.controladores;

import com.GaleriaVirtual.entidades.Foto;
import com.GaleriaVirtual.repositorios.FotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

    @Autowired
    private FotoRepositorio fotoRepositorio;
    
  //  @PreAuthorize("hasAnyRole('ROL_USER_REGISTRADO')")
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> photo(@PathVariable String id) {
        Foto foto = fotoRepositorio.getById(id);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(foto.getContenido(), headers, HttpStatus.OK);
    }
}