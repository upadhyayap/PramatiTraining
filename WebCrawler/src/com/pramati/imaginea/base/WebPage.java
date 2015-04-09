/**
 * 
 */
package com.pramati.imaginea.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import com.pramati.imaginea.bObj.Downloader;
import com.pramati.imaginea.bObj.Parser;

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

	private URL URL;

	/**
	 * This blocking queue is used to hold data contained in the Web page in
	 * form of web element
	 */

	private BlockingQueue<WebElement> WORK_OUEUE;

	public boolean loaded;

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
		this.URL = url;
		this.loaded = false;
		WORK_OUEUE = new ArrayBlockingQueue<WebElement>(150);
	}

	/**
	 * Used to get URl of the Web page
	 * 
	 * @return the url
	 */
	public URL getUrl() {
		return URL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pramati.imaginea.base.Page#load (java.lang.Object)
	 */
	@Override
	public void load(URL url) throws Exception {

		new Thread(new Parser(WORK_OUEUE, URL.toString())).start();
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
			load(URL);
		}
		new Thread(new Downloader(WORK_OUEUE)).start();

	}

}
