/**
 * 
 */
package com.pramati.imaginea.base;

import java.net.URL;

/**
 * This Interface represents a generic page which can be loaded and saved.
 * Crawler will use this page type to crawl
 * 
 * @author anandu
 *
 */
public interface Page {

	/**
	 * This method is used to load data using the provided URL and implementor
	 * will load data into byte array.
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public void load() throws Exception;

	/**
	 * This method is used to save loaded data to file.
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public void save() throws Exception;
	
	public URL getUrl();
}
