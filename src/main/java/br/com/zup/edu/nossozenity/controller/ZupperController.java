package br.com.zup.edu.nossozenity.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.nossozenity.repository.ZupperRepository;
import br.com.zup.edu.nossozenity.zupper.Zupper;

@RestController
@RequestMapping("/zuppers")
public class ZupperController {
	
	private final ZupperRepository repository;

	public ZupperController(ZupperRepository repository) {
		this.repository = repository;
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long idZupper, @RequestBody @Valid ZupperAtualizarDTO request){
		
		Zupper zupper = repository.findById(idZupper).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"zupper com esse id não cadastrado"));
		
		zupper.setNome(request.getNome());
		zupper.setCargo(request.getCargo());
		
		repository.save(zupper);
		
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ZupperResponseDTO> detalhar(@PathVariable("id") Long idZupper){
		
		Zupper zupper = repository.findById(idZupper).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artista não encontrada"));
		
		return ResponseEntity.ok(new ZupperResponseDTO(zupper));
		
	}
	
}
