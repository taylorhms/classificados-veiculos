package com.example.atividade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.atividade.domain.Veiculo;
import com.example.atividade.domain.VeiculosRepo;



@RestController
@CrossOrigin
@RequestMapping("/veiculos")
public class VeiculoRest {

	@Autowired
	private VeiculosRepo VeiculosRepo;

	@GetMapping
	public List<Veiculo> lerTodos() {
		return VeiculosRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Veiculo ler(@PathVariable Long id) {
		return VeiculosRepo.findById(id).orElse(null);
	}
	
	@PostMapping
	public Veiculo inserir(@RequestBody Veiculo Veiculo) {
		return VeiculosRepo.save(Veiculo);
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		VeiculosRepo.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public Veiculo atualizar(@RequestBody Veiculo Veiculo) {
		return VeiculosRepo.save(Veiculo);
	}
}