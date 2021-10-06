package br.fai.vl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editora")
public class EditoraController {

	@GetMapping("/list")
	public String getEditoraList() {
		return "editora/list";
	}

	@GetMapping("/edit")
	public String getEditoraEdit() {
		return "editora/edit";
	}

	@GetMapping("/create")
	public String getEditoraCreate() {
		return "editora/create";
	}
}
