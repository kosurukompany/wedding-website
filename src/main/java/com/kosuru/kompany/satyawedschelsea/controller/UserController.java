package com.kosuru.kompany.satyawedschelsea.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kosuru.kompany.satyawedschelsea.global.Constants;
import com.kosuru.kompany.satyawedschelsea.global.GlobalVariables;
import com.kosuru.kompany.satyawedschelsea.model.Users;
import com.kosuru.kompany.satyawedschelsea.service.ContentService;
import com.kosuru.kompany.satyawedschelsea.service.UsersService;

@Controller
public class UserController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private GlobalVariables globalVariables;

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/adminRegistration", method = RequestMethod.GET)
	public ModelAndView registartionPage() {

		ModelAndView mv = new ModelAndView();

		if (!usersService.findAllUsers().isEmpty()) {

			mv.setViewName("redirect:/");

		} else {
			Users user = new Users();
			mv.addObject("user", user);
			mv.setViewName("adminRegistration");
		}
		return mv;

	}

	@RequestMapping(value = "/adminRegistration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Users user, BindingResult bindingResult) {

		ModelAndView mv = new ModelAndView();

		if (!usersService.findAllUsers().isEmpty()) {

			mv.setViewName("redirect:/");

		} else {
			usersService.saveAdminUser(user, "Administrator");
			mv.setViewName("redirect:/");

		}
		return mv;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView getUsers() {

		ModelAndView mv = new ModelAndView();

		if (usersService.findAllUsers().isEmpty()) {

			mv.setViewName("redirect:/adminRegistration");

		} else if (globalVariables.userAuthentication().getName().equals("anonymousUser")) {

			mv.setViewName("redirect:/");

		} else {

			globalVariables.getNavbarItems(mv);
			globalVariables.getNavbarImage(mv);

			mv.addObject("users", usersService.findAllUsers());
			mv.setViewName("/users");
		}
		return mv;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView getAddUser() {

		ModelAndView mv = new ModelAndView();

		if (usersService.findAllUsers().isEmpty()) {

			mv.setViewName("redirect:/adminRegistration");

		} else if (globalVariables.userAuthentication().getName().equals("anonymousUser")) {

			mv.setViewName("redirect:/");

		} else {

			globalVariables.getNavbarItems(mv);
			globalVariables.getNavbarImage(mv);

			mv.addObject("type", Constants.ADD + Constants.SPACE + Constants.USER);
			mv.addObject("user", new Users());
			mv.setViewName("/addEditUser");
		}
		return mv;
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public ModelAndView getAddUser(@RequestParam(value = "id") String id) {

		ModelAndView mv = new ModelAndView();

		if (usersService.findAllUsers().isEmpty()) {

			mv.setViewName("redirect:/adminRegistration");

		} else if (globalVariables.userAuthentication().getName().equals("anonymousUser")) {

			mv.setViewName("redirect:/");

		} else {

			globalVariables.getNavbarItems(mv);
			globalVariables.getNavbarImage(mv);

			mv.addObject("type", Constants.EDIT + Constants.SPACE + Constants.USER);
			mv.addObject("user", usersService.findById(id));
			mv.setViewName("/addEditUser");
		}
		return mv;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView saveContent(@Valid Users user, RedirectAttributes redirectAttributes, HttpServletRequest req) {

		ModelAndView mv = new ModelAndView();

		String view = "users";

		redirectAttributes.addFlashAttribute("message", "Action Failed!!");
		redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		if (globalVariables.getUserRole().toLowerCase().equals(Constants.ADMINISTRATOR.toLowerCase())) {

			if (user.getId() == null || user.getId().isEmpty()) {

				user.setCreatedBy(globalVariables.getUserFirstName() + " " + globalVariables.getUserLastName());
				redirectAttributes.addFlashAttribute("message", "User Saved Successfully!!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-success");

				view = "addUser";

				if (usersService.findUserByEmail(user.getEmail()) == null) {
					usersService.saveUser(user);
					if (user.isSendRsvp()) {
						user.setSecret(UUID.randomUUID().toString());

						String appUrl = req.getRequestURL().toString().replace(req.getRequestURI(), "");
						globalVariables.sendRsvpEmail(contentService.findContent(Constants.SETTINGS.toLowerCase()),
								appUrl, user);
					}
				} else {
					redirectAttributes.addFlashAttribute("message",
							"Entered email is already attached to another user. Plese enter another email address and try again!!");
					redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
				}

			} else if (user.getId() != null && !user.getId().isEmpty()) {

				user.setSendRsvp(usersService.findById(user.getId()).isSendRsvp());
				user.setUpdatedBy(globalVariables.getUserFirstName() + " " + globalVariables.getUserLastName());
				redirectAttributes.addFlashAttribute("message", "User Updated Successfully!!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-success");

				view = "editUser?id=" + user.getId();

				usersService.saveUser(user);
			}

		} else {

			redirectAttributes.addFlashAttribute("message",
					"Action Failed!! You Don't have access to modify these details!!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}

		mv.setViewName("redirect:/" + view);

		return mv;

	}

	@RequestMapping(value = "/sendRsvp", method = RequestMethod.GET)
	public ModelAndView sendRsvp(@RequestParam(name = "id") String id, RedirectAttributes redirectAttributes,
			HttpServletRequest req) {

		ModelAndView mv = new ModelAndView();

		Users user = usersService.findById(id);

		if (user != null) {

			if (user.getSecret() == null) {
				user.setSecret(UUID.randomUUID().toString());
				user.setUpdatedBy(globalVariables.getUserFirstName() + " " + globalVariables.getUserLastName());

				usersService.saveUser(user);
			}

			String appUrl = req.getRequestURL().toString().replace(req.getRequestURI(), "");

			globalVariables.sendRsvpEmail(contentService.findContent(Constants.SETTINGS.toLowerCase()), appUrl, user);
			redirectAttributes.addFlashAttribute("message", "RSVP Sent Successfully!!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} else {
			redirectAttributes.addFlashAttribute("message", "RSVP Failed to Sent!! User not found.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		mv.setViewName("redirect:editUser?id=" + id);

		return mv;
	}

	@RequestMapping(value = "/rsvp", method = RequestMethod.GET)
	public ModelAndView getRsvp(@RequestParam(name = "token", required = false) String token,
			@RequestParam(name = "id", required = false) String id,
			@RequestParam(name = "email", required = false) String email, RedirectAttributes redirectAttributes,
			HttpServletRequest req) {

		ModelAndView mv = new ModelAndView();
		globalVariables.getNavbarItems(mv);
		globalVariables.getNavbarImage(mv);

		if (req.getParameter("token") != null) {
			final DecodedJWT body = JWT.decode(token);
			final Users user = usersService.findById(body.getClaim("id").asString());

			if (user != null) {
				try {
					final Algorithm algorithm = Algorithm.HMAC256(user.getSecret());

					final JWTVerifier verifier = JWT.require(algorithm).build();

					verifier.verify(token);

				} catch (SignatureVerificationException | JWTDecodeException e) {

					redirectAttributes.addFlashAttribute("message",
							"Your authentication token is expired or invalid !! Please contact support for new link.");

					redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

				}

				mv.addObject("user", user);

				mv.setViewName("/rsvp");
			} else {

				mv.setViewName("/error");
			}
		} else if (req.getParameter("id") != null) {

			mv.addObject("user", usersService.findById(id));
			mv.setViewName("/rsvp");
		} else if (req.getParameter("email") != null) {
			Users user = usersService.findUserByEmail(email.toLowerCase());
			if (user != null) {
				mv.addObject("user", user);
			} else {
				redirectAttributes.addFlashAttribute("message",
						"No RSVP found with entered email. Please correct the email address and try again.");

				redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			}
			mv.setViewName("/rsvp");
		} else {
			mv.setViewName("/rsvp");
		}

		return mv;
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam(name = "id") String id, RedirectAttributes redirectAttributes) {

		final ModelAndView modelAndView = new ModelAndView();
		usersService.delete(usersService.findById(id));

		redirectAttributes.addFlashAttribute("message", "User has been deleted successfully!!");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		modelAndView.setViewName("redirect:/users");
		return modelAndView;
	}
}