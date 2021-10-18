package br.fai.vl.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Emprestimo;
import br.fai.vl.model.Leitor;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.AccountService;
import br.fai.vl.web.service.BibliotecarioService;
import br.fai.vl.web.service.EmprestimoService;
import br.fai.vl.web.service.EntregaService;
import br.fai.vl.web.service.LeitorService;
import br.fai.vl.web.service.RecolhimentoService;

@Controller
@RequestMapping("/account")
public class AccountController {

	private boolean loginInvalido = false;

	@Autowired
	private AccountService service;

	@Autowired
	private LeitorService leitorService;

	@Autowired
	private BibliotecarioService bibliotecarioService;

	@Autowired
	private EmprestimoService emprestimoService;

	@Autowired
	private EntregaService entregaService;

	@Autowired
	private RecolhimentoService recolhimentoService;

	@GetMapping("/editar-perfil")
	public String getEditar() {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				return "redirect:/leitor/edit/" + Account.getIdUser();
			} else if (Account.getPermissionLevel() == 2) {
				return "redirect:/bibliotecario/edit/" + Account.getIdUser();
			} else {
				return "redirect:/leitor/detail/" + Account.getIdUser();
			}
		}
	}

	@GetMapping("/perfil-usuario")
	public String getPerfil() {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				return "redirect:/leitor/detail/" + Account.getIdUser();
			} else if (Account.getPermissionLevel() == 2) {
				return "redirect:/bibliotecario/detail/" + Account.getIdUser();
			} else {
				return "redirect:/leitor/detail/" + Account.getIdUser();
			}
		}
	}

	@GetMapping("/notificacao")
	public String getNotificacao(final Model model) {
		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {

				final Emprestimo lastEmprestimo = emprestimoService.lastLoanRecord(Account.getIdUser());
				final List<Emprestimo> emprestimoList = emprestimoService.readAll();
				if (lastEmprestimo != null && !(emprestimoList.isEmpty())) {
					model.addAttribute("lastLoan", lastEmprestimo);

					model.addAttribute("listaDeEmprestimo", emprestimoList);

					model.addAttribute("situacaoEntrega",
							entregaService.checkDeliveryRequest(lastEmprestimo.getId(), Account.getIdUser()));

					model.addAttribute("situacaoRecolhimento",
							recolhimentoService.requestCollection(lastEmprestimo.getId(), Account.getIdUser()));

					return "usuario/notificacao";
				} else {

					final Emprestimo emprestimoProvisorio = new Emprestimo();
					emprestimoProvisorio.setCodigo(0);
					model.addAttribute("lastLoan", emprestimoProvisorio);

					final List<Emprestimo> emprestimoListProvisorio = new ArrayList<Emprestimo>();

					emprestimoListProvisorio.add(emprestimoProvisorio);
					model.addAttribute("listaDeEmprestimo", emprestimoListProvisorio);

					return "usuario/notificacao";
				}

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/notificacao-adm")
	public String getNotificacaoAdm() {
		return "usuario/notificacao-adm";
	}

	@GetMapping("/my-loans")
	public String getOpenUserLoan(final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/leitor/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {

				final List<EmprestimoDTO> openUserloan = emprestimoService.checkOpenUserLoans(Account.getIdUser());

				if (!openUserloan.isEmpty()) {
					model.addAttribute("openLoans", openUserloan);
					model.addAttribute("idEmprestimo", openUserloan.get(0).getIdEmprestimo());
				} else {
					model.addAttribute("openLoans", null);
					model.addAttribute("idEmprestimo", -1);
				}

				return "usuario/emprestimos";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/terminate-loan/{id}")
	public String terminateLoan(@PathVariable final int id) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				if (emprestimoService.terminateLoan(id)) {
					return "redirect:/account/notificacao";
				} else {
					return "redirect:/leitor/entrar";
				}

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/remove-Loan-Book/{idEmprestimo}/{idExemplar}")
	public String removeLoanBook(@PathVariable final int idEmprestimo, @PathVariable final int idExemplar) {

		final EmprestimoDTO emprestimo = new EmprestimoDTO();
		emprestimo.setIdEmprestimo(idEmprestimo);
		emprestimo.setIdExemplar(idExemplar);

		emprestimoService.removeLoanBook(emprestimo);
		return "redirect:/account/my-loans";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable final int id) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				emprestimoService.delete(id);

				return "redirect:/";

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

// ======================= tudo sobre o Login =============================

	@GetMapping("/get-out")
	public String getOut() {
		service.disconnect();
		return "redirect:/";
	}

	@GetMapping("/list")
	public String getList(final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 2) {

				final List<Leitor> leitores = leitorService.readAll();
				model.addAttribute("listaDeLeitores", leitores);

				final List<Bibliotecario> bibliotecarios = bibliotecarioService.readAll();
				model.addAttribute("listaDeBibliotecarios", bibliotecarios);

				return "usuario/list";
			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/recover-password")
	public String getRecoverPassword() {
		return "conta/password";
	}

	@GetMapping("/entrar")
	public String getLogin(final Account account, final Model model) {

		return "conta/login";
	}

	@PostMapping("/login")
	private String login(final Account account, final Model model) {

		if (account.getLevelRequest() == 1) {
			final Leitor leitor = new Leitor();
			leitor.setEmail(account.getUserEmail());
			leitor.setSenha(account.getUserPassword());

			if (leitorService.login(leitor)) {

				loginInvalido = false;
				return "redirect:/";
			} else {
				loginInvalido = true;
				model.addAttribute("login", loginInvalido);
				return "conta/login";
			}

		} else if (account.getLevelRequest() == 2) {
			final Bibliotecario bibliotecario = new Bibliotecario();
			bibliotecario.setEmail(account.getUserEmail());
			bibliotecario.setSenha(account.getUserPassword());
			if (bibliotecarioService.login(bibliotecario)) {

				loginInvalido = false;
				return "redirect:/";
			} else {
				loginInvalido = true;
				model.addAttribute("login", loginInvalido);
				return "conta/login";
			}
		} else {
			return "conta/login";
		}
	}

}
