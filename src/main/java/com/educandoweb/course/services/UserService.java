package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educandoweb.course.dto.UserDTO;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

import services.exceptions.DatabaseException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<UserDTO> findAll() {
		List<User> list = repository.findAll();
		return list.stream().map(e -> new UserDTO(e)).collect(Collectors.toList());
	}
	
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new UserDTO(entity);
	}
	
	//retorna o usuario salvo
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	//deletar o usuario do bd
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	//atualizar um usuario (passa o id, e um objeto usuario com os dados a serem atualizados)
	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User entity = repository.getOne(id);
			updateData(entity, dto); //atualizar o meu entity com os dados do obj
			entity = repository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	//atualiza os dados do entity com base no que chegou no obj
	private void updateData(User entity, UserDTO dto) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setPhone(dto.getPhone());		
	}
}
