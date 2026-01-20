package br.com.sonda.aeronave.controllers;

import br.com.sonda.aeronave.dto.AeronaveDTO;
import br.com.sonda.aeronave.dto.AeronavePatchDTO;
import br.com.sonda.aeronave.dto.AeronavePorDecadaDTO;
import br.com.sonda.aeronave.dto.AeronavePorFabricanteDTO;
import br.com.sonda.aeronave.services.AeronaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/aeronaves")
public class AeronaveController {

    @Autowired
    private AeronaveService aeronaveService;

    @GetMapping
    public ResponseEntity<Page<AeronaveDTO>> findAll(@PageableDefault(size = 12) Pageable pageable) {
        return ResponseEntity.ok().body(aeronaveService.findAll(pageable));
    }

    @GetMapping("/find")
    public ResponseEntity<List<AeronaveDTO>> find(@RequestParam String termo) {
        return ResponseEntity.ok().body(aeronaveService.find(termo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AeronaveDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(aeronaveService.findById(id));
    }

    @GetMapping("/nao-vendidas")
    public ResponseEntity<List<AeronaveDTO>> findByVendidoFalse(){
        return ResponseEntity.ok().body(aeronaveService.findByNaoVendido());
    }

    @GetMapping("/por-decada")
    public ResponseEntity<List<AeronavePorDecadaDTO>> findByAnoFabricacao(){

        return ResponseEntity.ok().body(aeronaveService.findByAnoFabricacao());
    }

    @GetMapping("/por-fabricante")
    public ResponseEntity<List<AeronavePorFabricanteDTO>> findByFabricante(){
        return ResponseEntity.ok().body(aeronaveService.findByFabricante());
    }

    @GetMapping("/ultima-semana")
    public ResponseEntity<List<AeronaveDTO>> findRecent(){
        return ResponseEntity.ok().body(aeronaveService.findRecent());
    }

    @PostMapping
    public ResponseEntity<AeronaveDTO> save(@Validated(AeronaveDTO.class) @RequestBody AeronaveDTO aeronaveDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aeronaveService.save(aeronaveDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AeronaveDTO> updateById(@PathVariable Long id, @Validated(AeronaveDTO.class) @RequestBody AeronaveDTO aeronaveDTO) {
        return ResponseEntity.ok().body(aeronaveService.updateById(id, aeronaveDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        aeronaveService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AeronavePatchDTO> patch(@PathVariable Long id, @Validated @RequestBody AeronavePatchDTO aeronavePatchDTO){
        return ResponseEntity.ok().body(aeronaveService.patch(id, aeronavePatchDTO));
    }

}
