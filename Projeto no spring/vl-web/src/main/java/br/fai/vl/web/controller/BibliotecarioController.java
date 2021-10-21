package br.fai.vl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Bibliotecario;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.BibliotecarioService;

@Controller
@RequestMapping("/bibliotecario")
public class BibliotecarioController {

	@Autowired
	private BibliotecarioService service;

	private boolean camposCorretos = false;

	@GetMapping("/detail/{id}")
	private String getBibliotecarioDetail(@PathVariable final int id, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 2) {

				final Bibliotecario bibliotecario = service.readById(id);

				model.addAttribute("tipoUsuario", "bibliotecario");

				model.addAttribute("usuario", bibliotecario);
				return "usuario/detail";
			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/edit/{id}")
	private String getBibliotecarioEdit(@PathVariable final int id, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 2) {

				model.addAttribute("url", "/bibliotecario/update");

				final Bibliotecario bibliotecario = service.readById(id);
				model.addAttribute("user", bibliotecario);

				return "usuario/editar-perfil";
			} else {
				return "redirect:/account/entrar";
			}
		}

	}

	@PostMapping("/update")
	private String update(final Bibliotecario bibliotecario, final Model model) {
		service.update(bibliotecario);

		return "redirect:/bibliotecario/detail/" + bibliotecario.getId();
	}

	@GetMapping("/register")
	public String getRegister(final Model model, final Bibliotecario bibliotecario) {

		camposCorretos = false;
		model.addAttribute("correto", camposCorretos);
		return "conta/register-bibliotecario";
	}

	@PostMapping("/create")
	private String create(final Bibliotecario bibliotecario, final Model model) {

		if (bibliotecario.getEstado().equals("-1")) {
			camposCorretos = true;
			model.addAttribute("correto", camposCorretos);
			return "conta/register-bibliotecario";

		} else {
			final int id = service.create(bibliotecario);

			if (id != -1) {
				camposCorretos = false;
				return "redirect:/account/list";
			} else {
				camposCorretos = true;
				model.addAttribute("correto", camposCorretos);
				return "conta/register-bibliotecario";
			}
		}
	}

	@GetMapping("/delete/{id}")
	private String delete(@PathVariable final int id, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 2) {

				service.delete(id);
				return "redirect:/usuario/list";
			} else {
				return "redirect:/account/entrar";
			}
		}

	}
}
