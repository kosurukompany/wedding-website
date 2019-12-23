package com.kosuru.kompany.satyawedschelsea.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosuru.kompany.satyawedschelsea.global.Constants;
import com.kosuru.kompany.satyawedschelsea.model.Content;
import com.kosuru.kompany.satyawedschelsea.model.Users;
import com.kosuru.kompany.satyawedschelsea.service.ContentService;
import com.kosuru.kompany.satyawedschelsea.service.UsersService;

@Controller
public class LoginController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		ModelAndView mv = new ModelAndView();

		Users user = usersService.findUserByEmail(email.toLowerCase());
		if (user == null) {
			redirectAttributes.addFlashAttribute("message",
					"Entered Email is not found in our system. Please verify email and try again");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} else if (user.getPassword() == null || user.getPassword().isEmpty()) {
			redirectAttributes.addFlashAttribute("message",
					"Your account setup is not completed. Please contact Satya or Chelsea to complete your account setup.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} else if (password == null) {
			redirectAttributes.addFlashAttribute("message", "Please enter the password and try again");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} else if (BCrypt.checkpw(password, user.getPassword())) {

			if (user.getActive()) {
				// Setting session time out
				Content con = contentService.findContent(Constants.SETTINGS.toLowerCase());
				int sessionTimeOut = con.getSessExpireTime() * 60 * 60;
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(sessionTimeOut);

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());
				authorities.add(new SimpleGrantedAuthority(user.getRole()));
				Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),
						authorities);
				SecurityContextHolder.getContext().setAuthentication(newAuth);

			} else {
				redirectAttributes.addFlashAttribute("message",
						"Your account is not active!. Please contact Satya or Chelsea to activate you account.");
				redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

			}

		} else {
			redirectAttributes.addFlashAttribute("message",
					"Password you enetr is wrong!! Please re-enter the password and try to login.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		}
		mv.setViewName("redirect:/content?type=" + Constants.HOME.toLowerCase());
		return mv;
	}
}
