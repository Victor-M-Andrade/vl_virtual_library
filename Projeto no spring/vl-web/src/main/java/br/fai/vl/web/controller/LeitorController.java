package br.fai.vl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Leitor;
import br.fai.vl.web.service.LeitorService;

@Controller
@RequestMapping("/leitor")
public class LeitorController {

	@Autowired
	private LeitorService service;

	@GetMapping("/detail/{id}")
	private String getLeitorDetail(@PathVariable final int id, final Model model) {
		final Leitor leitor = service.readById(id);

		model.addAttribute("tipoUsuario", "leitor");

		model.addAttribute("usuario", leitor);
		return "usuario/detail";
	}

	@GetMapping("/edit/{id}")
	private String getLeitorEdit(@PathVariable final int id, final Model model) {

		model.addAttribute("url", "/leitor/update");

		final Leitor leitor = service.readById(id);
		model.addAttribute("user", leitor);

		return "usuario/editar-perfil";
	}

	@PostMapping("/update")
	private String update(final Leitor leitor, final Model model) {
		service.update(leitor);

		return this.getLeitorDetail(leitor.getId(), model);
	}

	@PostMapping("/create")
	private String create(final Leitor editora, final Model model) {

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
