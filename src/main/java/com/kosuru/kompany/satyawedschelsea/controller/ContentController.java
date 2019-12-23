package com.kosuru.kompany.satyawedschelsea.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosuru.kompany.satyawedschelsea.global.Constants;
import com.kosuru.kompany.satyawedschelsea.global.GlobalVariables;
import com.kosuru.kompany.satyawedschelsea.model.Content;
import com.kosuru.kompany.satyawedschelsea.service.ContentService;
import com.kosuru.kompany.satyawedschelsea.service.PicturesService;
import com.kosuru.kompany.satyawedschelsea.service.UsersService;

@Controller
public class ContentController {

	// private static Log _log =
	// LogFactory.getLog(ContentController.class.getName());

	@Autowired
	private UsersService usersService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private GlobalVariables globalVariables;

	@Autowired
	private PicturesService pictureService;

	@Value("${file.upload.path}")
	String fileUploadPath;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/content?type=" + Constants.HOME.toLowerCase());
		return mv;

	}

	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public ModelAndView getContent(@RequestParam(value = "type") String type) {

		ModelAndView mv = new ModelAndView();

		if (usersService.findAllUsers().isEmpty()) {

			mv.setViewName("redirect:/adminRegistration");

		} else if (type.toLowerCase().equals(Constants.SETTINGS.toLowerCase())
				&& globalVariables.userAuthentication().getName().equals("anonymousUser")) {

			mv.setViewName("redirect:/");

		} else {

			globalVariables.getNavbarItems(mv);
			globalVariables.getNavbarImage(mv);

			Content content = contentService.findContent(type);

			if (type.toLowerCase().equals(Constants.HOME.toLowerCase())) {

				mv.addObject("bgImage", content.getImage1());

			} else if (type.toLowerCase().equals(Constants.ENGAGEMENT.toLowerCase())) {

				mv.addObject("pictures", pictureService.findByType(Constants.ENGAGEMENT));
			}

			mv.addObject("content", content);
			mv.setViewName(type.toLowerCase());
		}

		return mv;
	}

	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ModelAndView saveContent(@Valid Content content, @RequestParam(value = "type") String type,
			@RequestPart(name = "picture1", required = false) MultipartFile picture1,
			RedirectAttributes redirectAttributes) {

		ModelAndView mv = new ModelAndView();

		if (globalVariables.getUserRole().toLowerCase().equals(Constants.ADMINISTRATOR.toLowerCase())) {
			if (picture1 != null && !picture1.isEmpty()) {
				if (content.getPicture1() != null) {

					globalVariables.store(content.getPicture1(), fileUploadPath);
					content.setImage1("/uploads/" + content.getPicture1().getOriginalFilename());
				}
			}

			if (StringUtils.isEmpty(content.getImage1())) {

				Content con = contentService.findContent(type);

				if (con != null && con.getImage1() != null) {

					content.setImage1(con.getImage1());
				}
			}

			contentService.saveContent(content, type, redirectAttributes);
		} else {

			redirectAttributes.addFlashAttribute("message",
					type.toUpperCase() + " Failed To Save!! You Don't have access to modify these details!!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		mv.setViewName("redirect:/content?type=" + type.toLowerCase());
		return mv;

	}
}