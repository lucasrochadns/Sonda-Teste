package br.com.sonda.aeronave.controller;

import br.com.sonda.aeronave.dto.AeronaveDTO;
import br.com.sonda.aeronave.services.AeronaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aeronaves")
@RequiredArgsConstructor
public class AeronaveController {

   @Autowired
   private AeronaveService aeronaveService;

     @GetMapping
     public ResponseEntity<Page<AeronaveDTO>> findAll(@PageableDefault(size = 12) Pageable pageable){
         return ResponseEntity.ok().body(aeronaveService.findAll(pageable).map(AeronaveDTO::from));
     }

     @GetMapping("/find")
     public ResponseEntity<List<AeronaveDTO>> find(@RequestParam String termo){
         return ResponseEntity.ok().body(aeronaveService.find(termo).stream().map(AeronaveDTO::from).toList());
     }

     @GetMapping("/{id}")
    public ResponseEntity<AeronaveDTO> findById(@PathVariable Long id){
         return ResponseEntity.ok().body(AeronaveDTO.from(aeronaveService.findById(id)));
     }

     @PostMapping
     public ResponseEntity<AeronaveDTO> save(@Validated(AeronaveDTO.class) @RequestBody AeronaveDTO aeronaveDTO){
         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(AeronaveDTO.from(aeronaveService.save(AeronaveDTO.to(aeronaveDTO))));
     }

     @PutMapping("/{id}")
    public ResponseEntity<AeronaveDTO> updateById(@PathVariable Long id, @Validated(AeronaveDTO.class)
     @RequestBody AeronaveDTO aeronaveDTO){
         System.out.println(aeronaveDTO);
         return ResponseEntity.ok().body(AeronaveDTO.
                 from(aeronaveService.updateById(id, AeronaveDTO.to(aeronaveDTO))));
     }
}
