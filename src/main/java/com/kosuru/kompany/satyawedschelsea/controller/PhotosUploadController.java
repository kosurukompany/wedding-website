package com.kosuru.kompany.satyawedschelsea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosuru.kompany.satyawedschelsea.global.Constants;
import com.kosuru.kompany.satyawedschelsea.global.GlobalVariables;
import com.kosuru.kompany.satyawedschelsea.model.Pictures;
import com.kosuru.kompany.satyawedschelsea.service.PicturesService;

@Controller
public class PhotosUploadController {

	@Autowired
	private GlobalVariables globalVariables;

	@Autowired
	private PicturesService pictureService;

	@Value("${file.upload.path}")
	String fileUploadPath;

	@RequestMapping(value = { "/photosUpload" }, method = RequestMethod.POST)
	public ModelAndView singleFileUpload(@RequestParam("engagementPhoto") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		} else {
			Pictures newPic = new Pictures();
			globalVariables.store(file, fileUploadPath);
			newPic.setType(Constants.ENGAGEMENT);
			newPic.setUrl("/uploads/" + file.getOriginalFilename());
			pictureService.savePictures(newPic);

		}

		mv.setViewName("redirect:/content?type=" + Constants.ENGAGEMENT.toLowerCase());

		return mv;

	}
}
