package br.fai.vl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Entrega;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.EntregaService;

@Controller
@RequestMapping("/entrega")
public class EntregaController {

	@Autowired
	private EntregaService entregaService;

	@GetMapping("/solicitar-entrega/{idEmprestimo}")
	public String terminateLoan(@PathVariable final int idEmprestimo) {

		if (!Account.isLogin()) {
			return "redirect:/leitor/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				final Entrega entrega = new Entrega();
				entrega.setEmprestimoId(idEmprestimo);
				entrega.setLeitorId(Account.getIdUser());

				entregaService.create(entrega);

				return "redirect:/account/notificacao";

			} else {
				return "redirect:/leitor/entrar";
			}
		}
	}

}
