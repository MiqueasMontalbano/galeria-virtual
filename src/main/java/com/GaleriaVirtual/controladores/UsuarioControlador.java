package com.GaleriaVirtual.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @GetMapping("/usuario")
    public String usuario() {
        return "usuario.html";
    }

      @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/xxx")
    public String xxx() {
        return "xxx.html";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
