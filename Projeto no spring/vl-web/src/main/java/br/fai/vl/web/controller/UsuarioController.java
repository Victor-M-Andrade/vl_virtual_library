package br.fai.vl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@GetMapping("/editar-perfil")
	public String getEditar() {
		return "usuario/editar-perfil";
	}

	@GetMapping("/perfil-usuario")
	public String getPerfil() {
		return "usuario/perfil-usuario";
	}

	@GetMapping("/notificacao")
	public String getNotificacao() {
		return "usuario/notificacao";
	}

	@GetMapping("/notificacao-adm")
	public String getNotificacaoAdm() {
		return "usuario/notificacao-adm";
	}

	@GetMapping("/carrinho")
	public String getCarrinho() {
		return "usuario/carrinho";
	}

}
