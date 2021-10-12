package br.fai.vl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conta")
public class AccountController {

	@GetMapping("/entrar")
	public String getLogin() {
		return "conta/login";
	}

	@PostMapping("/login")
	public String getVerifyLogin() {
		return "conta/register";
	}

	@GetMapping("/recover-password")
	public String getRecoverPassword() {
		return "conta/password";
	}

}
