package io.davi.clientes.resource;

import io.davi.clientes.dto.ClientDTO;
import io.davi.clientes.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientResource {

    @Autowired
    private ClientService service;


    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllWithPagination(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<ClientDTO> list = service.findAllWithPagination(pageRequest);
        return ResponseEntity.ok().body(list);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO client = service.findById(id);
        return ResponseEntity.ok().body(client);

    }

    @PostMapping
    public ResponseEntity<ClientDTO> insertOne(@RequestBody ClientDTO dto) {
        ClientDTO client = service.saveOne(dto);
        return ResponseEntity.ok().body(client);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> updateOne(
            @PathVariable Long id,
            @RequestBody ClientDTO dto) {
        ClientDTO obj = service.update(id, dto);
        return ResponseEntity.ok().body(obj);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> deleteOneById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
