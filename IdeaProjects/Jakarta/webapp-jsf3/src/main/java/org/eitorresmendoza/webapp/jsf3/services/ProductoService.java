package org.eitorresmendoza.webapp.jsf3.services;

import jakarta.ejb.Local;
import org.eitorresmendoza.webapp.jsf3.entities.Producto;

import java.util.List;
import java.util.Optional;

@Local
public interface ProductoService {

    List<Producto> listar();

    Optional<Producto> porId(Long id);

}
