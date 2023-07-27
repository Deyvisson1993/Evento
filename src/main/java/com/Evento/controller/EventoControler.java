package com.Evento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Evento.models.Convidado;
import com.Evento.models.Evento;
import com.Evento.repository.ConvidadoRepository;
import com.Evento.repository.EventoRepository;

import jakarta.validation.Valid;

@Controller
public class EventoControler {

	@Autowired
	private EventoRepository er;

	@Autowired
	private ConvidadoRepository cr;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET) // Metodo tipo get - vai retornar Formulario
	public String form() {
		return "evento/formEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST) // Metodo tipo get - vai cadastrar //
																				// Formulario
	public String form(@Valid Evento evento, BindingResult rs, RedirectAttributes attributes) {
		if (rs.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os Campos !");
			return "redirect:/cadastrarEvento";
		}
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento Cadastrado com Sucesso!");
		return "redirect:/cadastrarEvento";
	}

	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);// A lista de eventos esta sendo passad para a view
		return mv;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET) // apos clik, ira mostrar os detalhes
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);

		Iterable<Convidado> convidados = cr.findByEventos(evento);
		mv.addObject("convidados", convidados);
		return mv;
	}

	@RequestMapping("/deletar")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";

	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult rs,
			RedirectAttributes attributes) {
		if (rs.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os Campos !");
			return "redirect:/{codigo}";
		}
		Evento evento1 = er.findByCodigo(codigo);
		convidado.setEventos(evento1);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Salvo com Sucesso");
		return "redirect:/{codigo}";

	}

	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {

		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);

		Evento evento = convidado.getEventos();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;

		return "redirect:/" + codigo;

	}
}