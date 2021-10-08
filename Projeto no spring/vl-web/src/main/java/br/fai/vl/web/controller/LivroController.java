package br.fai.vl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/livro")
public class LivroController {

	@GetMapping("/list")
	public String getAcervo() {
		return "livro/acervo";
	}

	@GetMapping("/list-adm")
	public String getAcervoAdm() {
		return "livro/acervo-adm";
	}

	@GetMapping("/detail")
	public String getDescricaoLivro() {
		return "livro/descricao-livro";
	}

	@GetMapping("/finalizar-emprestimo")
	public String getFinalizarEmprestimo() {
		return "livro/finalizar-emprestimo";
	}

	@GetMapping("/create")
	public String getCriarLivro() {
		return "livro/criar-livro";
	}

	@GetMapping("/edit")
	public String getEditarLivro() {
		return "livro/editar-livro";
	}

	@GetMapping("/enviar-livro")
	public String getEnviarLivro() {
		return "livro/enviar-livro";
	}
}
