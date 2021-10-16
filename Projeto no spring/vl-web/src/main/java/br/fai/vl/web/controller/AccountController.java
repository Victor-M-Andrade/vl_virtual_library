package br.fai.vl.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Leitor;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.AccountService;
import br.fai.vl.web.service.BibliotecarioService;
import br.fai.vl.web.service.EmprestimoService;
import br.fai.vl.web.service.LeitorService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService service;

	@Autowired
	private LeitorService leitorService;

	@Autowired
	private BibliotecarioService bibliotecarioService;

	@Autowired
	private EmprestimoService emprestimoService;

	@GetMapping("/editar-perfil")
	public String getEditar() {

		if (!Account.isLogin()) {
			return "redirect:/leitor/entrar";
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
			return "redirect:/leitor/entrar";
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
	public String getNotificacao() {
		return "usuario/notificacao";
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
				return "redirect:/leitor/entrar";
			}
		}
	}

	@GetMapping("/terminate-loan/{id}")
	public String terminateLoan(@PathVariable final int id) {

		if (!Account.isLogin()) {
			return "redirect:/leitor/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				if (emprestimoService.terminateLoan(id)) {
					return "redirect:/account/my-loans";
				} else {
					return "redirect:/";
				}

			} else {
				return "redirect:/leitor/entrar";
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
			return "redirect:/leitor/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				emprestimoService.delete(id);

				return "redirect:/";

			} else {
				return "redirect:/leitor/entrar";
			}
		}
	}

	@GetMapping("/get-out")
	public String getOut() {
		service.disconnect();
		return "redirect:/";
	}

	@GetMapping("/list")
	public String getList(final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/leitor/entrar";
		} else {
			if (Account.getPermissionLevel() >= 2) {

				final List<Leitor> leitores = leitorService.readAll();
				model.addAttribute("listaDeLeitores", leitores);

				final List<Bibliotecario> bibliotecarios = bibliotecarioService.readAll();
				model.addAttribute("listaDeBibliotecarios", bibliotecarios);

				return "usuario/list";
			} else {
				return "redirect:/bibliotecario/entrar";
			}
		}
	}

	@GetMapping("/recover-password")
	public String getRecoverPassword() {
		return "conta/password";
	}

}
