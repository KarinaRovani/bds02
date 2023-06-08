package com.devsuperior.bds02.controller;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService service;

    @PostMapping
    public ResponseEntity<CityDTO> insert(@RequestBody CityDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CityDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	
	@GetMapping
	public ResponseEntity<Page<CityDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<CityDTO> list = service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CityDTO> findById(@PathVariable Long id){
		CityDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CityDTO> update(@PathVariable Long id, @RequestBody CityDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}