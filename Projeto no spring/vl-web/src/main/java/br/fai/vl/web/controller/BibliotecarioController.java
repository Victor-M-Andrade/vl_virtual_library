package br.fai.vl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Bibliotecario;
import br.fai.vl.web.service.BibliotecarioService;

@Controller
@RequestMapping("/bibliotecario")
public class BibliotecarioController {

	@Autowired
	private BibliotecarioService service;

	@GetMapping("/detail/{id}")
	private String getBibliotecarioDetail(@PathVariable final int id, final Model model) {
		final Bibliotecario bibliotecario = service.readById(id);

		model.addAttribute("tipoUsuario", "bibliotecario");

		model.addAttribute("usuario", bibliotecario);
		return "usuario/detail";
	}

	@GetMapping("/edit/{id}")
	private String getBibliotecarioEdit(@PathVariable final int id, final Model model) {

		model.addAttribute("url", "/bibliotecario/update");

		final Bibliotecario bibliotecario = service.readById(id);
		model.addAttribute("user", bibliotecario);

		return "usuario/editar-perfil";
	}

	@PostMapping("/update")
	private String update(final Bibliotecario bibliotecario, final Model model) {
		service.update(bibliotecario);

		return "redirect:/bibliotecario/detail/" + bibliotecario.getId();
	}

	@PostMapping("/create")
	private String create(final Bibliotecario editora, final Model model) {

		final int id = service.create(editora);

		if (id != -1) {
			// return "redirect:/editora/list";
		} else {
			// return "/editora/create";
		}
		return "";
	}

	@GetMapping("/delete/{id}")
	private String delete(@PathVariable final int id, final Model model) {

		service.delete(id);
		return "redirect:/usuario/list";
	}
}
