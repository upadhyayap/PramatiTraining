/**
 * 
 */
package com.pramati.imaginea.base;

import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This is a concrete class down the page hierarchy. It is used to represent an
 * Email Thread page which differs from normal web page in a sense that this
 * page has only email thread links which has to be down loaded.
 * 
 * @author anandu
 *
 */
public class WebPage implements Page {

	/**
	 * This represents an url for the webpage. This parameter us mandatory in
	 * order to create object of this class because there can not exist a web
	 * page without an url.
	 * 
	 */
	private URL rootUrl;
	/**
	 * This String is used to hold data contained in the Web page
	 * 
	 */
	private String content;
	
	private boolean loaded;

	/**
	 * Constructor which takes URL as a String
	 * 
	 * @throws MalformedURLException
	 * 
	 */
	public WebPage(String url) throws MalformedURLException {
		this(new URL(url));
	}

	/**
	 * Constructor which takes URL as a parameter
	 * 
	 * @throws MalformedURLException
	 * 
	 */
	public WebPage(URL url) {
		this.rootUrl = url;
		this.loaded = false;

	}

	/**
	 * Used to get URl of the Web page
	 * 
	 * @return the url
	 */
	@Override
	public URL getUrl() {
		return rootUrl;
	}

	/**
	 * Used to get Content of the Web page
	 * 
	 * @return the url
	 */
	public String getContent() {
		return content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pramati.imaginea.base.Page#load (java.lang.Object)
	 */
	@Override
	public void load() throws Exception {

		URLConnection urlc = this.rootUrl.openConnection();
		BufferedInputStream buffer = new BufferedInputStream(
				urlc.getInputStream());
		StringBuilder builder = new StringBuilder();
		int byteRead;
		while ((byteRead = buffer.read()) != -1) {
			builder.append((char) byteRead);
		}
		content = builder.toString();
		buffer.close();
		this.loaded = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pramati.imaginea.base.Page#save (java.lang.Object)
	 */
	@Override
	public void save() throws Exception {

		if (!loaded) {
			load();
			this.loaded = true;
		}
		// Logic for saving a web page to specific file left for future
		// Implementation
	}

	@Override
	public boolean equals(Object pWebPage) {
		if (pWebPage instanceof WebPage) {
			WebPage lWebpage = (WebPage) pWebPage;
			return lWebpage.getUrl().toString().equals(this.rootUrl.toString());
		}
		return false;
	}

}
