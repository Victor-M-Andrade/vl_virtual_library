package br.fai.vl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conta")
public class ContaController {

	@GetMapping("/login")
	public String getLogin() {
		return "conta/login";
	}

	@GetMapping("/register")
	public String getRegister() {
		return "conta/register";
	}

	@GetMapping("/recover-password")
	public String getRecoverPassword() {
		return "conta/password";
	}

}
