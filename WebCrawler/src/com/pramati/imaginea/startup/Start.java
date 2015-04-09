/**
 * 
 */
package com.pramati.imaginea.startup;

import com.pramati.imaginea.bObj.WebCrawlar;
import com.pramati.imaginea.base.Crawlar;
import com.pramati.imaginea.base.Page;
import com.pramati.imaginea.base.WebPage;
import com.pramati.imaginea.utilities.CrawlerConstants;

/**
 * Starting point of the Web Crawler Package
 * 
 * @author anandu
 *
 */
public class Start {

	/**
	 * This Represents a Web page which is Http Specific implementation of
	 * crawler interface later on web page instance can also be injected through
	 * spring bean
	 */

	private static Page Webpage;

	/**
	 * This represents a web crawler which is used to crawl web page. It is web
	 * specific implementation of crawler interface. later on crawler can also
	 * be injected through Spring bean.
	 */
	private static Crawlar Webcrawlar;

	/**
	 * Main method of the Application which serves as a Entry point.
	 * 
	 * @param args
	 *            Runtime parameters
	 */
	public static void main(String[] args) {

		try {
			/******* Initialize instance variables ******/

			Webpage = new WebPage(CrawlerConstants.RootUrl);
			Webcrawlar = new WebCrawlar();

			Webcrawlar.addPage(Webpage);
			Webcrawlar.crawl();
			Webcrawlar.shutDown();

		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Sorry not able to download data");
		}
	}

}
