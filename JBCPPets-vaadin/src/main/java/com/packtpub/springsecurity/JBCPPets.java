/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.packtpub.springsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.Application;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@Component(value = "JBCPPets")
@Scope(value = "prototype")
public class JBCPPets extends Application {

	private static final long serialVersionUID = 4269441268788374372L;

	private static Logger log = LoggerFactory.getLogger(JBCPPets.class);

	@Autowired
	private HomeWindow homeWindow;

	@Autowired
	private ChangePasswordWindow changePasswordWindow;

	@Override
	public void init() {
		log.trace("Initializing Home page...");

		setMainWindow(homeWindow);

		log.trace("Initialied Home page");
	}

	public Window getHomeWindow() {
		return homeWindow;
	}

	public Window getChangePasswordWindow() {
		return changePasswordWindow;
	}

}
