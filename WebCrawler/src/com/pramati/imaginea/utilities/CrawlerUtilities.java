/**
 * 
 */
package com.pramati.imaginea.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This is a utility class designed for common utility functions
 * 
 * @author anandu
 *
 */
public class CrawlerUtilities {

	public static File buildSaveDirectory() {
		File lDir = null;
		if (CrawlerConstants.osName != null
				&& !CrawlerConstants.osName.equals("")
				&& CrawlerConstants.osName.contains("Linux")) {
			lDir = new File("/home/"+CrawlerConstants.userName+"/Desktop/MailArchive");
		}
		else {
			lDir = new File("C:\\MailArchive");
		}
		return lDir;
	}

}
