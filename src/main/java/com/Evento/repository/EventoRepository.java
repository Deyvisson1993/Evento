package com.Evento.repository;

import org.springframework.data.repository.CrudRepository;

import com.Evento.models.Evento;


public interface EventoRepository extends CrudRepository<Evento, String>{
	
	Evento findByCodigo(long codigo);

}
