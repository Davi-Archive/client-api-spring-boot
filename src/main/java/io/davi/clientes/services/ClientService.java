package io.davi.clientes.services;

import io.davi.clientes.dto.ClientDTO;
import io.davi.clientes.entities.Client;
import io.davi.clientes.repositories.ClientRepository;
import io.davi.clientes.services.exception.DatabaseException;
import io.davi.clientes.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllWithPagination(PageRequest pageRequest) {
        Page<Client> list = repository.findAll(pageRequest);
        return list.map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) throws Exception {
        Optional<Client> client = repository.findById(id);
        return new ClientDTO(client
                .orElseThrow(() -> new ResourceNotFoundException("Entity no found with id: " + id)));
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) throws Exception {
        try {
            Client entity = repository.getReferenceById(id);
            copyDTOToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("The id was not found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional
    public ClientDTO saveOne(ClientDTO dto) {
        try {
            Client entity = new Client();
            copyDTOToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }


    private void copyDTOToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
        entity.setIncome(dto.getIncome());
        entity.setChildren(dto.getChildren());
    }


}
