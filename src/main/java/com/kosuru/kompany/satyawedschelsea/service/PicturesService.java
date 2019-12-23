package com.kosuru.kompany.satyawedschelsea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosuru.kompany.satyawedschelsea.global.GlobalVariables;
import com.kosuru.kompany.satyawedschelsea.model.Pictures;
import com.kosuru.kompany.satyawedschelsea.repository.PicturesRepository;

@Service("picturesService")
public class PicturesService {

	private PicturesRepository picturesRepository;

	@Autowired
	private GlobalVariables globalVariables;

	@Autowired
	public PicturesService(PicturesRepository picturesRepository) {
		this.picturesRepository = picturesRepository;
	}

	public Pictures savePictures(Pictures picture) {
		picture.setCreatedBy(globalVariables.getUserFirstName() + globalVariables.getUserLastName());
		return picturesRepository.save(picture);
	}

	public List<Pictures> findAllPictures() {
		return picturesRepository.findAll();
	}

	public List<Pictures> findByType(String type) {
		return picturesRepository.findByType(type);
	}

}