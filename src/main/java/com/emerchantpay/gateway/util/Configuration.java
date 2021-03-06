package com.emerchantpay.gateway.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.emerchantpay.gateway.api.constants.Endpoints;
import com.emerchantpay.gateway.api.constants.Environments;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @license http://opensource.org/licenses/MIT The MIT License
 */

public class Configuration {

	private Environments environment;
	private Endpoints endpoint;
	private String username;
	private String password;
	private String token;
	private String action;
	private Boolean tokenEnabled = true;
	private Boolean wpf = false;

	private static Logger logger;

	static {
		logger = Logger.getLogger("Genesis");
		logger.addHandler(new ConsoleHandler());
		logger.setLevel(Level.INFO);
	}

	public Configuration(Environments environment, Endpoints endpoint) {

		this.environment = environment;
		this.endpoint = endpoint;
	}

	public void setLogger(Logger log) {
		logger = log;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {

		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {

		return password;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {

		return token;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public Boolean getWpfEnabled() {
		return wpf;
	}

	public void setWpfEnabled(Boolean wpf) {
		this.wpf = wpf;
	}

	public Boolean getTokenEnabled() {
		return tokenEnabled;
	}

	public void setTokenEnabled(Boolean tokenEnabled) {
		this.tokenEnabled = tokenEnabled;
	}

	public String getBaseUrl() {

		if (getTokenEnabled() == true) {
			return "https://" + environment.getEnvironmentName() + "." + endpoint.getEndpointName() + "/" + getAction()
					+ "/" + token;
		} else {

			if (getWpfEnabled() == true) {
				return "https://" + environment.getEnvironmentName().replace("gate", "wpf") + "."
						+ endpoint.getEndpointName() + "/" + getAction();
			} else {
				return "https://" + environment.getEnvironmentName() + "." + endpoint.getEndpointName() + "/"
						+ getAction();
			}
		}
	}

	public Environments getEnvironment() {
		return environment;
	}
}
