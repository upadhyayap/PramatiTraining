/**
 * 
 */
package com.pramati.imaginea.base;

import java.io.File;

/**
 * This Interface represents a web page element it can be a web text or web
 * image or web link. This is a generic representation of page elements.
 * 
 * @author anandu
 *
 */
public interface WebElement {

	/**
	 * This method is used to load web element in to web page. implementor of
	 * this method should know how that particular element is getting loaded
	 * into the web page.
	 * 
	 * @throws Exception
	 */
	public void load() throws Exception;

	/**
	 * This method is used to save a particular web element to a target suplied
	 * file. Implementation may vary from element to element because different
	 * elements may be get saved in different ways.
	 * 
	 * @param targetFile
	 * @throws Exception
	 */
	public void save(File targetFile) throws Exception;
}
