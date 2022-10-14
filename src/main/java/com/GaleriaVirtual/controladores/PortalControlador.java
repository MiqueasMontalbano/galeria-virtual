package com.GaleriaVirtual.controladores;

import com.GaleriaVirtual.entidades.Obra;
import com.GaleriaVirtual.entidades.enumeracion.Categoria;
import com.GaleriaVirtual.errores.ErrorServicio;
import com.GaleriaVirtual.servicios.ObraServicio;
import com.GaleriaVirtual.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ObraServicio obraServicio;

    @GetMapping("/indexpage")
    public String index() {
        return "index.html";
    }

    @GetMapping()
    public String index2() {
        return "index2.html";
    }

//    @PreAuthorize("hasAnyRole('ROL_USER_REGISTRADO')")
    @GetMapping("/altadeobras")
    public String altadeobras() {
        return "altadeobras.html";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto.html";
    }

    @GetMapping("/checkoutcarrito")
    public String carrito() {
        return "checkoutcarrito.html";
    }

    @GetMapping("/index")
    public String index(@RequestParam(required = false) String categoria, ModelMap modelo) throws ErrorServicio {

        List<Obra> obras = new ArrayList<>();

        if (categoria == null) {
            obras = obraServicio.buscarTodas();
        } else {
            Categoria parsedCategoria = Categoria.valueOf(categoria);
            obras = obraServicio.buscarPorCategoria(parsedCategoria);
        }

        modelo.put("obras", obras);

        return "index.html";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nickname, @RequestParam String mail, @RequestParam String contrasenia1, @RequestParam String contrasenia2) {

        try {
            usuarioServicio.registrar(nickname, mail, contrasenia1, contrasenia2);

        } catch (ErrorServicio ex) {
            modelo.put("errorReg", ex.getMessage());
            modelo.put("nickname", nickname);
            modelo.put("mail", mail);
            modelo.put("contrasenia1", contrasenia1);
            modelo.put("contrasenia2", contrasenia2);
            ex.printStackTrace();
            return "registro.html";
        }
        modelo.put("titulo", "Bienvenido a la Galería de Arte Tamago!");
        modelo.put("descripcion", "Logeate para comenzar");
        return "/index";

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o contraseña incorrecta  .");
        }
        if (logout != null) {
            model.put("logout", "Cerraste sesión correctamente.");
        }
        return "/login";
    }

//    @PreAuthorize("hasAnyRol('ROL_USER_REGISTRADO')")
//    @GetMapping("/comprar")
//    public String comprar() {
//        return "comprar.html";
//    }
//    
//    @PreAuthorize("hasAnyRol('ROL_USER_REGISTRADO')")
//    @GetMapping("/vender")
//    public String vender() {
//        return "vender.html";
//    }
}
