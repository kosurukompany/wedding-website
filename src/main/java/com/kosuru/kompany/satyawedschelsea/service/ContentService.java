package com.kosuru.kompany.satyawedschelsea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosuru.kompany.satyawedschelsea.global.GlobalVariables;
import com.kosuru.kompany.satyawedschelsea.model.Content;
import com.kosuru.kompany.satyawedschelsea.repository.ContentRepository;

@Service("contentService")
public class ContentService {

	private ContentRepository contentRepository;

	@Autowired
	private GlobalVariables globalVariables;

	@Autowired
	public ContentService(ContentRepository contentRepository) {
		this.contentRepository = contentRepository;
	}

	public Content saveContent(Content c, String type, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", type.toUpperCase() + " Failed To Save!!");
		redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		c.setType(type);

		if (contentRepository.findByType(type) == null) {

			c.setCreatedBy(globalVariables.getUserFirstName() + " " + globalVariables.getUserLastName());
			redirectAttributes.addFlashAttribute("message", type.toUpperCase() + " Saved Successfully!!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		} else {

			c.setUpdatedBy(globalVariables.getUserFirstName() + " " + globalVariables.getUserLastName());
			redirectAttributes.addFlashAttribute("message", type.toUpperCase() + " Updated Successfully!!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		}
		return contentRepository.save(c);
	}

	public Content findContent(String type) {
		Content content = contentRepository.findByType(type);
		return content != null ? content : new Content();
	}

}
