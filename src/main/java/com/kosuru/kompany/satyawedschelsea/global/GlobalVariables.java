package com.kosuru.kompany.satyawedschelsea.global;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kosuru.kompany.satyawedschelsea.model.Content;
import com.kosuru.kompany.satyawedschelsea.model.Users;
import com.kosuru.kompany.satyawedschelsea.service.ContentService;
import com.kosuru.kompany.satyawedschelsea.service.UsersService;

@Service
public class GlobalVariables {

	private static Log _log = LogFactory.getLog(GlobalVariables.class.getName());

	@Autowired
	public UsersService usersService;

	@Autowired
	private ContentService contentService;

	public Authentication userAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public String getUserFirstName() {
		String firstName = usersService.findUserByEmail(userAuthentication().getName()).getFirstName();
		return firstName;
	}

	public String getUserLastName() {
		String lastName = usersService.findUserByEmail(userAuthentication().getName()).getLastName();
		return lastName;
	}

	public String getUserEmail() {
		String email = usersService.findUserByEmail(userAuthentication().getName()).getEmail();
		return email;
	}

	public Users getUsers() {
		Users user = usersService.findUserByEmail(userAuthentication().getName());
		return user;
	}

	public String getUserRole() {
		String role = usersService.findUserByEmail(userAuthentication().getName()).getRole();
		return role;
	}

	public String getUserId() {
		String id = usersService.findUserByEmail(userAuthentication().getName()).getId();
		return id;
	}

	public String getUserProfilePic() {
		String url = null;
		if (usersService.findUserByEmail(userAuthentication().getName()).getUserProfilePicUrl() != null) {
			url = usersService.findUserByEmail(userAuthentication().getName()).getUserProfilePicUrl();
		} else {
			url = "/images/no_user_profile_pic.png";
		}
		return url;
	}

	public void store(MultipartFile file, String path) {
		byte[] bytes = null;
		try {
			bytes = file.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Path path1 = Paths.get(path + file.getOriginalFilename());

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		try {
			Files.write(path1, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ModelAndView getNavbarItems(ModelAndView mv) {

		if (!userAuthentication().getName().equals("anonymousUser")) {

			mv.addObject("userRole", getUserRole());
			mv.addObject("userName", getUserFirstName() + " " + getUserLastName());
			mv.addObject("email", getUserEmail());

		} else {
			mv.addObject("userRole", "anonymousUser");
		}
		mv.addObject("headerColor", getHeaderColor());
		mv.addObject("bgColor", getBgColor());
		return mv;
	}

	public String getBgColor() {

		return contentService.findContent(Constants.SETTINGS.toLowerCase()).getBgColor();
	}

	private String getHeaderColor() {

		return contentService.findContent(Constants.SETTINGS.toLowerCase()).getHeaderColor();
	}

	public ModelAndView getNavbarImage(ModelAndView mv) {

		return mv.addObject("navBar", contentService.findContent(Constants.SETTINGS.toLowerCase()).getImage1());

	}

	public JavaMailSenderImpl javaMailConfig(@Valid Content content) {
		String smtpEmail = content.getSmtpEmail();
		String smtpHost = content.getSmtpHost();
		String smtpPassword = content.getSmtpPassword();
		int smtpPort = content.getSmtpPort();

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setUsername(smtpEmail);
		mailSender.setHost(smtpHost);
		mailSender.setPassword(smtpPassword);
		mailSender.setPort(smtpPort);

		try {
			Properties props = mailSender.getJavaMailProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			// props.put("mail.debug", "true");
			Session session = Session.getInstance(props, null);
			Transport transport = session.getTransport("smtp");
			transport.connect(smtpHost, smtpPort, smtpEmail, smtpPassword);
			transport.close();
			_log.info("Email Authentication Sucessful For  Email=" + smtpEmail);
		} catch (AuthenticationFailedException e) {
			mailSender = null;
			_log.info("Email Authentication Failed For  Email=" + smtpEmail);
			e.printStackTrace();
		} catch (MessagingException e) {
			mailSender = null;
			_log.info("Email Configuration Error For  Email=" + smtpEmail);
			e.printStackTrace();
		}

		return mailSender;
	}

	public void sendRsvpEmail(Content content, String appUrl, @Valid Users user) {

		MimeMessage mimeMessage = javaMailConfig(content).createMimeMessage();

		String name = !user.isFamily() ? user.getFirstName() + " " + user.getLastName()
				: user.getLastName() + " family";
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			String htmlMsg = "<html><body style='background-color:white;'><p>Hello " + name + ",</p>"
					+ "<img width='100%'  src='" + appUrl
					+ contentService.findContent(Constants.HOME.toLowerCase()).getImage1() + "'/>" + "<div>"
					+ content.getRsvpBody() + ""
					+ "<a style='display: block;height: 25px;background: #007944;padding: 10px;text-align: center;font-size: 20px;border-radius: 5px;color: white;font-weight: bold;text-decoration:none' href='"
					+ appUrl + "/rsvp?token=" + generateToken(content, user)
					+ "'>Please click here to respond to RSVP.</a><br><p style='text-align:center'>For any questions or for help please email at: "
					+ content.getSupport() + "</p></div><p>With love,<br>" + content.getName() + "</p>"
					+ "</body></html>";

			mimeMessage.setContent(htmlMsg, "text/html");
			helper.setTo(user.getEmail());
			helper.setSubject(content.getRsvpSubject());
			helper.setFrom(content.getSmtpEmail());
			javaMailConfig(content).send(mimeMessage);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	private String generateToken(Content content, @Valid Users user) {

		final String token = JWT.create().withIssuer(content.getName()).withClaim("id", user.getId())
				.withIssuedAt(new Date(System.currentTimeMillis())).withNotBefore(new Date(System.currentTimeMillis()))
				.sign(Algorithm.HMAC256(user.getSecret()));

		return token;
	}

}
