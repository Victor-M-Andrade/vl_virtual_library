package br.fai.vl.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fai.vl.api.service.EmprestimoService;
import br.fai.vl.model.Emprestimo;

@RestController
@RequestMapping("/api/v1/emprestimo")
public class EmprestimoRestController {

	@Autowired
	private EmprestimoService service;

	@GetMapping("/read-all")
	public ResponseEntity<List<Emprestimo>> readAll() {

		return ResponseEntity.ok(service.readAll());
	}

	@GetMapping("/read-by-id/{id}")
	public ResponseEntity<Emprestimo> readById(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.readById(id));
	}

	// DTOs
	// EmprestimoDTO
	// - livroId
	// - lalaId
	@PostMapping("/create/{id}")
	public ResponseEntity<Integer> create(@PathVariable("id") final int idLivroSolicitado) {

		return ResponseEntity.ok(service.create(idLivroSolicitado));
	}

	@PutMapping("/update")
	public ResponseEntity<Boolean> update(@RequestBody final Emprestimo entity) {

		return ResponseEntity.ok(service.update(entity));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.delete(id));
	}
}
