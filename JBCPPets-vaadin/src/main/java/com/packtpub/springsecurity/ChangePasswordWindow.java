package com.packtpub.springsecurity;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.packtpub.springsecurity.security.IChangePassword;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

;

@Configurable(preConstruction = true)
@Component
public class ChangePasswordWindow extends Window {

	private static final long serialVersionUID = 2865301485350303457L;

	private static Logger log = LoggerFactory
			.getLogger(ChangePasswordWindow.class);
	private ChangePasswordView changePasswordView = new ChangePasswordView();

	@Autowired
	private IChangePassword userService;

	public ChangePasswordWindow() {
		log.trace("Initialazing Change Password Window");

		setSizeFull();
		setDebugId("Change Password Window");

		setContent(changePasswordView);

		addChangePasswordButton();
		log.trace("Initialazed Change Password Window");
	}

	/**
	 * Dodaje logiku za promjenu passworda kad se pritisne gumb
	 */
	private void addChangePasswordButton() {
		log.trace("Adding Change Password Button logic");
		Button changePassword = changePasswordView.getSubmitButton();
		changePassword.addListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -167918079680100693L;

			@Override
			public void buttonClick(ClickEvent event) {
				log.trace("Changeing password");
				Object principal = SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				String username = principal.toString();
				log.trace("Principal.toString: {}", username);

				if (principal instanceof UserDetails) {
					username = ((UserDetails) principal).getUsername();
				}

				String newPassword = changePasswordView.getNewPasswordValue();
				log.trace("New password: {}", newPassword);

				userService.changePassword(username, newPassword);
				SecurityContextHolder.clearContext();

				// TODO: ovo bih zamijenio s getApplication().init;
				log.trace("Logout");
				/*
				 * Ovime se zatvara aplikacija (aplikacija se uklanja iz
				 * sessiona) te se browser preusmjerava na logout URL. Logout
				 * URL koristi stantardni Spring Secirity link
				 * /j_spring_security_logout, ali pošto mora biti u kontekstu
				 * aplikacije, morali smo preko getURL metode dobiti ime
				 * aplikacije
				 */
				URL url = getApplication().getURL();
				String logoutURL = url.getPath() + "logout";

				getApplication().setLogoutURL(logoutURL);
				getApplication().close();
			}
		});

	}
}
