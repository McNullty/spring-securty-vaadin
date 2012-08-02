package com.packtpub.springsecurity;

import org.springframework.stereotype.Component;

import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

@Component
public class RegisterWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -774292565229201232L;

	public RegisterWindow() {
		setName("register");
		
		addComponent(new Label("Register"));
		//TODO: Dodati pravi View
	}
}
