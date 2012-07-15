package com.packtpub.springsecurity;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Component
public class HomeWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4171940260399596501L;
	private static Logger log = LoggerFactory.getLogger(HomeWindow.class);
	private HomeView homeView = new HomeView();

	public HomeWindow() {
		log.trace("HomeWindow initialisation");

		setSizeFull();
		setDebugId("Homew Window");

		/*
		 * da sam umjesto setContent stavio addComponent, u windowu bi se
		 * stvorio jedan VerticalLayout s neodređenom veličinom
		 */
		setContent(homeView);

		addLogoutButton();
		addChangePasswordButton();
		log.trace("HomeWindow initialisation finished");
	}

	/**
	 * Dodjeljuje logiku za pozivanje prozora za promjenu passworda
	 */
	private void addChangePasswordButton() {
		log.trace("Adding Change Password Button");

		// TODO: treba promjentiti ove anonimne listenere tako da Home Window
		// implementira listener sučelje i u jednoj metodi pozuiva određene
		// procedure ovisno o izvoru eventa
		
		Button changePassword = homeView.getChangePasswordButton();
		changePassword.addListener(new ClickListener() {
		
			/**
			 * 
			 */
			private static final long serialVersionUID = -2280054775037564953L;

			@Override
			public void buttonClick(ClickEvent event) {
				log.trace("Change Password Window event");
				Window changePasswordWindow = ((JBCPPets)getApplication()).getChangePasswordWindow();
				
				getApplication().setMainWindow(changePasswordWindow);
				getApplication().removeWindow(HomeWindow.this);
			}
		});
		
	}

	/**
	 * Dodjeljuje logiku Logout Buttonu
	 */
	private void addLogoutButton() {
		log.trace("Adding Logout Button");

		Button logout = homeView.getButtonLogout();

		logout.addListener(new ClickListener() {
			private static final long serialVersionUID = -2280054775037564952L;

			@Override
			public void buttonClick(ClickEvent event) {
				log.trace("Logout Click event");
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
