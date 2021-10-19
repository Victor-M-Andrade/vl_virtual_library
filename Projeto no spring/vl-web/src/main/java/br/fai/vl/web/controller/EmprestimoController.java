package br.fai.vl.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.dto.EntregaDTO;
import br.fai.vl.dto.RecolhimentoDTO;
import br.fai.vl.model.Entrega;
import br.fai.vl.model.Recolhimento;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.EntregaService;
import br.fai.vl.web.service.RecolhimentoService;

@Controller
@RequestMapping("/emprestimo")
public class EmprestimoController {

	@Autowired
	private EntregaService entregaService;

	@Autowired
	private RecolhimentoService recolhimentoService;

	@GetMapping("/solicitar-entrega/{idEmprestimo}")
	public String terminateLoanEntraga(@PathVariable final int idEmprestimo) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				final Entrega entrega = new Entrega();
				entrega.setEmprestimoId(idEmprestimo);
				entrega.setLeitorId(Account.getIdUser());

				entregaService.create(entrega);

				return "redirect:/account/my-previous-loans/" + idEmprestimo;

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/solicitar-recolhimento/{idEmprestimo}")
	public String terminateLoanRecolhimento(@PathVariable final int idEmprestimo) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				final Recolhimento recolhimento = new Recolhimento();
				recolhimento.setEmprestimoId(idEmprestimo);
				recolhimento.setLeitorId(Account.getIdUser());

				recolhimentoService.create(recolhimento);

				return "redirect:/account/my-previous-loans/" + idEmprestimo;

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/solicitacoes")
	public String deliveryOrderList(final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				final List<EntregaDTO> entregaDTO = entregaService.deliveryOrderList();
				final List<RecolhimentoDTO> recolhimentoDTO = recolhimentoService.pickUpOrderList();

				model.addAttribute("entragasSolicitadas", entregaDTO);
				model.addAttribute("recolhimentosSolicitados", recolhimentoDTO);

				return "/emprestimo/notificacao-adm";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/recusar-entrega/{idEntrega}")
	public String refuseDelivery(@PathVariable final int idEntrega, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				entregaService.refuseDelivery(idEntrega);

				return "redirect:/emprestimo/solicitacoes";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/aceitar-entrega/{idEntrega}")
	public String acceptDelivery(@PathVariable final int idEntrega, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				entregaService.acceptDelivery(idEntrega);

				return "redirect:/emprestimo/solicitacoes";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

}
