package com.amarojc.customerportfolios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amarojc.customerportfolios.dtos.ClientDTO;
import com.amarojc.customerportfolios.entities.Client;
import com.amarojc.customerportfolios.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPagedClient(PageRequest pageRequest){
		Page<Client> clients = clientRepository.findAll(pageRequest);
		return clients.map(c -> new ClientDTO(c));
	}
}
