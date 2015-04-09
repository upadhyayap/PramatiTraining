/**
 * 
 */
package com.pramati.imaginea.bObj;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import com.pramati.imaginea.base.WebElement;
import com.pramati.imaginea.base.pageContent;

/**
 * This Abstract class is an implementation of web element and used to represent
 * a Link in the web page. All the methods defined in this class are specific to
 * how web link should behave.
 * 
 * @author anandu
 *
 */
public abstract class WebLink implements WebElement {

	/**
	 * This represents an URL of the link on the page. This link is used to
	 * fetch data and that data will get saved later on
	 * 
	 */
	protected URL LINK_URL;

	/**
	 * This byte array is used to hold data after hitting the url
	 * 
	 */
	protected byte[] content = new byte[5000];

	/**
	 * 
	 */
	public WebLink(URL url) {
		this.LINK_URL = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pramati.imaginea.base.WebElement#save(java.lang.Object)
	 */
	@Override
	public void save(File targetFile) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pramati.imaginea.base.WebElement#load(java.lang.Object)
	 */
	@Override
	public void load() throws Exception {

		URLConnection urlc = LINK_URL.openConnection();
		BufferedInputStream buffer = new BufferedInputStream(
				urlc.getInputStream());
		buffer.read(content);
	}

}
