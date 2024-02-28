package com.uce.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uce.edu.repository.modelo.Persona;
import com.uce.edu.service.IPersonaService;

//http://localhost:8085/personas/buscar
//http://localhost:8085/personas/actualizar
@Controller
@RequestMapping("/personas")
public class PersonaController {

	@Autowired
	private IPersonaService personaService;

	// Diferentes tipos de request
	// verbos o metodos HTTP
	// Path
	// GET
	
	@GetMapping("/buscarTodos")
	public String buscarTodos(Model modelo) {
		List<Persona>lista=this.personaService.buscarTodos();
		modelo.addAttribute("personas",lista);
		return "vistaListaPersonas";
	}
	//GET
	// Cuando viaja el modelo en el response
	// /buscarPorCedula/1727450296
	// URL EN LA WEB http://localhost:8085/personas/buscarPorCedula/1727450296
	@GetMapping("/buscarPorCedula/{cedulaPersona}")
	public String buscarPorCedula(@PathVariable("cedulaPersona") String cedula, Model modelo) {
		Persona persona = this.personaService.buscarPorCedula(cedula);
		modelo.addAttribute("persona",persona);
		return "vistaPersona";
	}
	//Cuando viaja el modelo en el request
	@PutMapping("/actualizar/{cedulaPersona}")
	public String actualizar(@PathVariable("cedulaPersona") String cedula, Persona persona) {
		persona.setCedula(cedula);
		
		Persona perAux= this.personaService.buscarPorCedula(cedula);
		perAux.setApellido(persona.getApellido());
		perAux.setNombre(persona.getNombre());
		perAux.setGenero(persona.getGenero());
		perAux.setCedula(persona.getCedula());
		
		this.personaService.actualizar(perAux);
		return "redirect:/personas/buscarTodos";
	}
	
	@PostMapping("/insertar")
	public String guardar(Persona persona) {
		this.personaService.guardar(persona);
		return "redirect:/personas/buscarTodos";
	}
	@GetMapping("/nuevaPersona")
	public String mostrarNuevaPersona(Model modelo) {
		modelo.addAttribute("persona", new Persona());
		return"vistaNuevaPersona";
	}
	@DeleteMapping("/borrar/{cedula}")
	public String borrar(@PathVariable("cedula") String cedula) {
		this.personaService.borrarPorCedula(cedula);
		return"redirect:/personas/buscarTodos";
	}
	
	
}
