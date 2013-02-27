package com.packtpub.springsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Configurable(preConstruction = true)
@Component
public class LoginWindow extends Window {

	/**
	 * pokupi parametre iz view te proba authenticirati usera
	 * 
	 * @author mladenc
	 *
	 */
	public class LoginClickListener implements ClickListener {

		private static final long serialVersionUID = 5492188769403322044L;

		@Override
		public void buttonClick(ClickEvent event) {
			String username = loginView.getUsername();
			String oldPassword = loginView.getPassword();
			am.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));

			log.debug("User was authenticated");
			
			//TODO:Poslati User Logged in event
		}

	}

	private static final long serialVersionUID = 7469856686096570605L;

	private static Logger log = LoggerFactory
			.getLogger(ChangePasswordWindow.class);

	private final LoginView loginView = new LoginView();
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager am;

	public LoginWindow() {
		log.trace("Initialazing Login Window");

		setSizeFull();

		setName("loginWindow");
		setDebugId("Change Password Window");

		setContent(loginView);

		addLoginButton();
		log.trace("Initialazed Login Window");
	}

	private void addLoginButton() {
		loginView.getSubmitButton().addListener(new LoginClickListener());
		
	}
}
