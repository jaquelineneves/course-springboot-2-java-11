package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.get();
	}
	
	//retorna o usuario salvo
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	//deletar o usuario do bd
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	//atualizar um usuario (passa o id, e um objeto usuario com os dados a serem atualizados)
	public User update(Long id, User obj) {
		User entity = repository.getOne(id);
		updateData(entity, obj); //atualizar o meu entity com os dados do obj
		return repository.save(entity);
	}

	//atualiza os dados do entity com base no que chegou no obj
	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());		
	}
}
