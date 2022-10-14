package com.GaleriaVirtual.servicios;

import com.GaleriaVirtual.entidades.Usuario;
import com.GaleriaVirtual.entidades.enumeracion.Rol;
import com.GaleriaVirtual.errores.ErrorServicio;
import com.GaleriaVirtual.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public Usuario registrar(String nickname, String mail, String contrasenia1, String contrasenia2) throws ErrorServicio {

        validar(nickname, mail, contrasenia1, contrasenia2);

        Usuario usuario = new Usuario();
        usuario.setNickname(nickname);
        usuario.setMail(mail);
        
        usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia1));
        usuario.setAlta(new Date());
        usuario.setEstado(true);
        usuario.setRol(Rol.USER);

        return usuarioRepositorio.save(usuario);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Usuario modificar(String id, String nickname, String mail, String contrasenia1, String contrasenia2) throws ErrorServicio {

        validar(nickname, mail, contrasenia1, contrasenia2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNickname(nickname);
            usuario.setMail(mail);
            usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia1));

            return usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("El usuario no existe.");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public void estadoUsuario(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getEstado() == true) {
                usuario.setEstado(false);
            } else {
                usuario.setEstado(true);
            }
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("El usuario no existe.");
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("El usuario que buscas no existe.");
        }

    }

    private void validar(String nickname, String mail, String contrasenia1, String contrasenia2) throws ErrorServicio {

        if (nickname == null || nickname.isEmpty()) {
            throw new ErrorServicio("El nickname no puede ser nulo.");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El mail no puede ser nulo.");
        }
        if (contrasenia1 == null || contrasenia1.isEmpty() || contrasenia1.length() <= 7) { 
            throw new ErrorServicio("La contraseña no puede ser nula y debe tener mas de 7 caracteres.");
        }
        if ( !contrasenia1.equals(contrasenia2)) {
            throw new ErrorServicio("Las contraseñas deben ser iguales");
        }

    }
    

     
    

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = (Usuario) usuarioRepositorio.buscarPorMail(mail);
        if (usuario != null) {

            List<GrantedAuthority> permiso = new ArrayList();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROL_USER_REGISTRADO");
            permiso.add(p1);

//                GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO FOTO");
//                permiso.add(p2);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getMail(), usuario.getContrasenia(), permiso);
        } else {
            return null;
        }
    }
    
    
}
