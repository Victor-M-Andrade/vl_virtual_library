package br.fai.vl.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.dto.EntregaDTO;
import br.fai.vl.dto.RecolhimentoDTO;
import br.fai.vl.model.Entrega;
import br.fai.vl.model.Recolhimento;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.EmprestimoService;
import br.fai.vl.web.service.EntregaService;
import br.fai.vl.web.service.RecolhimentoService;

@Controller
@RequestMapping("/emprestimo")
public class EmprestimoController {

	@Autowired
	private EntregaService entregaService;

	@Autowired
	private RecolhimentoService recolhimentoService;

	@Autowired
	private EmprestimoService emprestimoService;

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

	// ========= ENTERGAS ==========

	@GetMapping("/entregas")
	public String deliveryOrderList(final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				final List<EntregaDTO> solicitacaoEntregaDTO = entregaService.deliveryOrderList();
				final List<EntregaDTO> entregaDTO = entregaService.closedDeliveryOrderList();
				final List<RecolhimentoDTO> recolhimentoDTO = recolhimentoService.pickUpOrderList();

				model.addAttribute("entragasSolicitadas", solicitacaoEntregaDTO);
				model.addAttribute("entragas", entregaDTO);
				model.addAttribute("recolhimentosSolicitados", recolhimentoDTO);

				if (solicitacaoEntregaDTO.isEmpty()) {
					model.addAttribute("semSolicitacoes", true);
				}

				if (entregaDTO.isEmpty()) {
					model.addAttribute("entregas", true);
				}

				return "/emprestimo/entregas";

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

				return "redirect:/emprestimo/entregas";

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

				return "redirect:/emprestimo/entregas";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	// ========= Recolhimento ==========

	@GetMapping("/recolhimento")
	public String collectionOrderList(final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				final List<RecolhimentoDTO> recolhimentoDTO = recolhimentoService.closedPickUpOrderList();
				final List<RecolhimentoDTO> solicitacaoRecolhimentoDTO = recolhimentoService.pickUpOrderList();

				model.addAttribute("recolhimentos", recolhimentoDTO);
				model.addAttribute("recolhimentosSolicitados", solicitacaoRecolhimentoDTO);

				if (solicitacaoRecolhimentoDTO.isEmpty() || solicitacaoRecolhimentoDTO.size() == 0) {
					model.addAttribute("semSolicitacoes", true);
				}

				if (recolhimentoDTO.isEmpty() || recolhimentoDTO.size() == 0) {
					model.addAttribute("semRecolhimentos", true);
				}

				return "/emprestimo/recolhimentos";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/recusar-recolhimento/{idRecolhimento}")
	public String refuseCollection(@PathVariable final int idRecolhimento, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				recolhimentoService.refuseCollection(idRecolhimento);

				return "redirect:/emprestimo/recolhimento";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/aceitar-recolhimento/{idRecolhimento}")
	public String acceptCollection(@PathVariable final int idRecolhimento, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				recolhimentoService.acceptCollection(idRecolhimento);

				return "redirect:/emprestimo/recolhimento";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/request-user-loan/{idEmprestimo}/{idUser}")
	public String getNotificacao(@PathVariable final int idEmprestimo, @PathVariable final int idUser,
			final Model model) {
		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				model.addAttribute("situacaoEntrega", entregaService.checkDeliveryRequest(idEmprestimo, idUser));

				model.addAttribute("situacaoRecolhimento", recolhimentoService.requestCollection(idEmprestimo, idUser));

				final List<EmprestimoDTO> openUserloan = emprestimoService.checkLoan(idEmprestimo, idUser);

				if (!openUserloan.isEmpty()) {
					model.addAttribute("loans", openUserloan);
					model.addAttribute("idEmprestimo", openUserloan.get(0).getIdEmprestimo());
				} else {
					model.addAttribute("loans", null);
					model.addAttribute("idEmprestimo", -1);
				}

				return "emprestimo/solicitacao-user";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	// ========= EMPRESTIMOS ==========
	@GetMapping("/list")
	public String getEmprestimoList(final Model model) {
		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				final List<EmprestimoDTO> openLoanDTO = emprestimoService.openLoansList();
				final List<EmprestimoDTO> closeLoanDTO = emprestimoService.closeLoansList();

				model.addAttribute("emprestimosAbertos", openLoanDTO);
				model.addAttribute("emprestimosFechados", closeLoanDTO);

				if (openLoanDTO.isEmpty() || openLoanDTO.size() == 0) {
					model.addAttribute("semEmprestimos", true);
				}

				return "emprestimo/list";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/devolver-exemplar/{idExemplar}/{idEmprestimo}")
	public String getReturnCopy(@PathVariable final int idExemplar, @PathVariable final int idEmprestimo,
			final Model model) {
		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 2) {

				emprestimoService.returnCopy(idExemplar, idEmprestimo);

				return "redirect:/emprestimo/list";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}
}
