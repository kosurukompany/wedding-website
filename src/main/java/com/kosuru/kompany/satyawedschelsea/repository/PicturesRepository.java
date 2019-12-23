package com.kosuru.kompany.satyawedschelsea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosuru.kompany.satyawedschelsea.model.Pictures;

@Repository("picturesRepository")
public interface PicturesRepository extends JpaRepository<Pictures, Long> {

	@Override
	List<Pictures> findAll();

	List<Pictures> findByType(String type);
}
