package com.amarojc.customerportfolios.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amarojc.customerportfolios.dtos.ClientDTO;
import com.amarojc.customerportfolios.entities.Client;
import com.amarojc.customerportfolios.repositories.ClientRepository;
import com.amarojc.customerportfolios.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPagedClient(PageRequest pageRequest){
		Page<Client> clients = clientRepository.findAll(pageRequest);
		return clients.map(c -> new ClientDTO(c));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findByIdClient(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		Client client = obj.orElseThrow(() -> new ObjectNotFoundException("Client not found " + id));
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO createdClient(ClientDTO clientDTO) {
		Client client = new Client();
		copyDtoToEntity(clientDTO, client);
		client = clientRepository.save(client);
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
		try {
			Client client = clientRepository.getReferenceById(id);
			copyDtoToEntity(clientDTO, client);
			client = clientRepository.save(client);
			
			return new ClientDTO(client);
		}catch(EntityNotFoundException ex) {
			throw new ObjectNotFoundException("Client not found: " + id);
		}
	}
	
	private void copyDtoToEntity(ClientDTO dto, Client client) {
		client.setName(dto.getName());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
	}
}
