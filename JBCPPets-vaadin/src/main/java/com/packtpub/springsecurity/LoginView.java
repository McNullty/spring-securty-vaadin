package com.packtpub.springsecurity;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends CustomComponent {

	private static final long serialVersionUID = -2471661365093386742L;
	
	private VerticalLayout mainLayout;
	private Button submit;
	private TextField username;
	private PasswordField password;
	

	public LoginView() {
		buildMainLayout();
		
		setSizeFull();
		setCompositionRoot(mainLayout);		
	}

	private VerticalLayout buildMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		
		username = new TextField("Username:");
		mainLayout.addComponent(username);
		
		password = new PasswordField("Password:");
		mainLayout.addComponent(password);
		
		submit = new Button("Submit");
		mainLayout.addComponent(submit);
		
		return mainLayout;
	}
	
	public Button getSubmitButton(){
		return submit;
	}
	
	public String getUsername(){
		return (String)username.getValue();
	}
	
	public String getPassword(){
		return (String)password.getValue();
	}
}
