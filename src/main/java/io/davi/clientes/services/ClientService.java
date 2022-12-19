package io.davi.clientes.services;

import io.davi.clientes.dto.ClientDTO;
import io.davi.clientes.entities.Client;
import io.davi.clientes.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Page<ClientDTO> findAllWithPagination (PageRequest pageRequest){
        Page<Client> list = repository.findAll(pageRequest);
        return list.map(x->new ClientDTO(x));
    }


}
