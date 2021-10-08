package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.model.Emprestimo;
import br.fai.vl.web.service.EmprestimoService;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

	@Override
	public List<Emprestimo> readAll() {

		final String endpoint = "http://localhost:8085/api/v1/emprestimo/read-all";
		List<Emprestimo> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Emprestimo[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Emprestimo[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Emprestimo readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/emprestimo/read-by-id";
		Emprestimo response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Emprestimo> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Emprestimo.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final int idLivro) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/create/" + idLivro;
		int id = Integer.valueOf(-1);

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Integer> httpEntity = new HttpEntity<Integer>(idLivro);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.GET, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return id;
	}

	@Override
	public boolean update(final Emprestimo entity) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Emprestimo> httpEntity = new HttpEntity<Emprestimo>(entity);
			final ResponseEntity<Boolean> responseEntity = restTemplace.exchange(endpoint, HttpMethod.PUT, httpEntity,
					Boolean.class);
			response = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean delete(final int id) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/delete/" + id;
		boolean response = false;

		try {
			final RestTemplate restTemplace = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Boolean> requestResponse = restTemplace.exchange(endpoint, HttpMethod.DELETE,
					httpEntity, Boolean.class);

			response = requestResponse.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

}
