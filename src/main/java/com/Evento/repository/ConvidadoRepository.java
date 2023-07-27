package com.Evento.repository;

import org.springframework.data.repository.CrudRepository;

import com.Evento.models.Convidado;
import com.Evento.models.Evento;

import java.util.List;


public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
	
	Iterable<Convidado> findByEventos(Evento eventos);
	Convidado findByRg(String rg);

}
