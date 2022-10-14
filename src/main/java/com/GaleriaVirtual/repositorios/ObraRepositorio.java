package com.GaleriaVirtual.repositorios;

import com.GaleriaVirtual.entidades.Obra;
import com.GaleriaVirtual.entidades.enumeracion.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraRepositorio extends JpaRepository<Obra, String> {
      @Query("SELECT o FROM Obra o WHERE o.categoria LIKE :categoria")
      public List<Obra> buscarPorCategoria(@Param("categoria") String categoria);

    @Query("SELECT o FROM Obra o WHERE o.categoria LIKE :categoria")
    public List<Obra> buscarPorCategoria(@Param("categoria") Categoria categoria);
}
