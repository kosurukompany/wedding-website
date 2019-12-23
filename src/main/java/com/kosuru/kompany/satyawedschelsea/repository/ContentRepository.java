package com.kosuru.kompany.satyawedschelsea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosuru.kompany.satyawedschelsea.model.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	Content findByType(String type);
}
