package com.GaleriaVirtual.servicios;

import com.GaleriaVirtual.entidades.Foto;
import com.GaleriaVirtual.entidades.Obra;
import com.GaleriaVirtual.entidades.Usuario;
import com.GaleriaVirtual.entidades.enumeracion.Categoria;
import com.GaleriaVirtual.errores.ErrorServicio;
import com.GaleriaVirtual.repositorios.ObraRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ObraServicio {

    @Autowired
    private ObraRepositorio obraRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional(rollbackFor = {Exception.class})
    public Obra guardar(String titulo, String tamanio, String artista, String descripcion, Integer anio, Integer cantidad,

            float precio, boolean estado, Date date, Categoria categoria, MultipartFile archivo, String UsuarioId) throws ErrorServicio {


        //verificaiones
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede ser nulo/vacio");
        }
        if (tamanio == null || tamanio.isEmpty()) {
            throw new ErrorServicio("El tamaño no puede ser nulo/vacio");
        }
        if (artista == null || artista.isEmpty()) {
            throw new ErrorServicio("El artista no puede ser nulo/vacio");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede ser nulo");
        }

        Obra obra = new Obra();

        obra.setTitulo(titulo);
        obra.setTamanio(tamanio);
        obra.setArtista(artista);
        obra.setDescripcion(descripcion);
        obra.setAnio(anio);
        obra.setCantidad(cantidad);
        obra.setVendido(0);
        obra.setPrecio(precio);
        obra.setEstado(true);
        obra.setAlta(new Date());
        obra.setCategoria(categoria); //por ver
        Foto foto = fotoServicio.guardar(archivo);
        
        //horrible
        obra.setFotos(new ArrayList<>());
        
        obra.getFotos().add(foto);

//        Usuario usuario = usuarioServicio.buscarPorId(UsuarioId);
//        obra.setUsuario(usuario);

        return obraRepositorio.save(obra);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Obra editar(String id, String titulo, String tamanio, String artista, String descripcion, Integer anio,
            Integer cantidad, float precio, Categoria categoria, String usuarioId) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede ser nulo/vacio");
        }
        if (tamanio == null || tamanio.isEmpty()) {
            throw new ErrorServicio("El tamaño no puede ser nulo/vacio");
        }
        if (artista == null || artista.isEmpty()) {
            throw new ErrorServicio("El artista no puede ser nulo/vacio");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede ser nulo");
        }

        Optional<Obra> respuesta = obraRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Obra obra = respuesta.get();
            obra.setTitulo(titulo);
            obra.setTamanio(tamanio);
            obra.setArtista(artista);
            obra.setDescripcion(descripcion);
            obra.setAnio(anio);
            obra.setCantidad(cantidad);
            obra.setPrecio(precio);
            obra.setCategoria(categoria);
            Usuario usuario = usuarioServicio.buscarPorId(usuarioId);
            obra.setUsuario(usuario);

            return obraRepositorio.save(obra);
        } else {
            throw new ErrorServicio("No se encontro la Obra solicitada");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public void eliminar(String id, boolean estado) throws ErrorServicio { // para borrar el objeto pongo el estado en false
        if (id == null || id.isEmpty()) {
            throw new ErrorServicio("El id no puede ser nulo");
        }
        Optional<Obra> respuesta = obraRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Obra obra = respuesta.get();
            if (obra.getEstado() == true) {
                obra.setEstado(false);
            } else {
                obra.setEstado(true);
            }

            obraRepositorio.save(obra);
        } else {
            throw new ErrorServicio("No se encontro la Obra solicitada");
        }
    }

    @Transactional(readOnly = true)
    public Obra buscarPorID(String id) throws ErrorServicio {
        Optional<Obra> respuesta = obraRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("La obra solicitada no existe");
        }
    }
    // por ver
//    public void stockActual(Integer vendido, Integer cantidad) throws ErrorServicio{
//        
//        Obra obra = obraRepositorio();
//        
//        Integer resultado = obra.getCantidad() - vendido;
//        obra.setCantidad(resultado);
//        
//        if (cantidad == 0) {
//             throw new ErrorServicio("No hay stock");
//        }
//    }

    public List<Obra> buscarPorCategoria(Categoria categoria) throws ErrorServicio {

        return obraRepositorio.buscarPorCategoria(categoria);

    }

    public List<Obra> buscarTodas() throws ErrorServicio {

        return obraRepositorio.findAll();

    }

}
