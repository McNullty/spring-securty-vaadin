package com.packtpub.springsecurity.security;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.DigestUtils;

/**
 * This implementation adds the IP address to the user's remember me token for
 * an additional level of security and non-repudiation.
 * 
 * @author Mularien
 */
public class IPTokenBasedRememberMeServices extends
		TokenBasedRememberMeServices {

	private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

	public IPTokenBasedRememberMeServices(String key,
			UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

	public HttpServletRequest getContext() {
		return requestHolder.get();
	}

	public void setContext(HttpServletRequest context) {
		requestHolder.set(context);
	}

	/**
	 * Izvlaći IP adresu iz Request Contexta
	 * 
	 * @param request
	 * @return
	 */
	protected String getUserIPAddress(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * Postavlje ThreadLocalVarijablu prije pozivanja super() metode i briše
	 * ThreadLocal varijablu nakon povratka iz super() metode
	 */
	@Override
	public void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication successfulAuthentication) {
		try {
			setContext(request);
			super.onLoginSuccess(request, response, successfulAuthentication);
		} finally {
			setContext(null);
		}
	}

	/**
	 * Mijenja defaultnu implementaciju koja ima defaultno MD5
	 * ("username:tokenExpiryTime:password:key")
	 */
	@Override
	protected String makeTokenSignature(long tokenExpiryTime, String username,
			String password) {
		return DigestUtils
				.md5DigestAsHex((username + ":" + tokenExpiryTime + ":"
						+ password + ":" + getKey() + ":" + getUserIPAddress(getContext()))
						.getBytes());
	}

	/**
	 * Dodaje IP adresu u cookie, na zadnje mjesto
	 */
	@Override
	protected void setCookie(String[] tokens, int maxAge,
			HttpServletRequest request, HttpServletResponse response) {
		// append the IP adddress to the cookie
		String[] tokensWithIPAddress = Arrays.copyOf(tokens, tokens.length + 1);
		// dodaje IP adresu na zadnje mjestu u polju tokens
		tokensWithIPAddress[tokensWithIPAddress.length - 1] = getUserIPAddress(request);

		super.setCookie(tokensWithIPAddress, maxAge, request, response);
	}

	/**
	 * Prije pozivanja supper() metode provjeri se da li IP adrese ogovaraju. U
	 * super metodu se predaje polje tokena iz kojeg je izbačena informacija o
	 * IP adresi.
	 */
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			setContext(request);

			// take off the last token
			String ipAddressToken = cookieTokens[cookieTokens.length - 1];
			if (!getUserIPAddress(request).equals(ipAddressToken)) {
				throw new InvalidCookieException(
						"Cookie IP Address did not contain a matching IP (contained '"
								+ ipAddressToken + "')");
			}

			return super.processAutoLoginCookie(
					Arrays.copyOf(cookieTokens, cookieTokens.length - 1),
					request, response);
		} finally {
			setContext(null);
		}
	}
}
