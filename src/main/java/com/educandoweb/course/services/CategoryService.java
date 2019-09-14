package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educandoweb.course.dto.CategoryDTO;
import com.educandoweb.course.entities.Category;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.CategoryRepository;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

import services.exceptions.DatabaseException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(e -> new CategoryDTO(e)).collect(Collectors.toList());
	}
	
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new CategoryDTO(entity);
	}
	
	//retorna o usuario salvo
	public Category insert(Category obj) {
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
	public Category update(Long id, Category obj) {
		try {
			Category entity = repository.getOne(id);
			updateData(entity, obj); //atualizar o meu entity com os dados do obj
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	//atualiza os dados do entity com base no que chegou no obj
	private void updateData(Category entity, Category obj) {
		entity.setName(obj.getName());	
	}
}
