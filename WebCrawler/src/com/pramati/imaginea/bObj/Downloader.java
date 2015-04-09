/**
 * 
 */
package com.pramati.imaginea.bObj;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.pramati.imaginea.Entities.mail;
import com.pramati.imaginea.base.WebElement;
import com.pramati.imaginea.utilities.CrawlerConstants;
import com.pramati.imaginea.utilities.CrawlerUtilities;

/**
 * take data from the queue and hit the url get the mail object and submit the
 * task to executor for saving
 * 
 * @author anandu
 *
 */
public class Downloader implements Runnable {

	private BlockingQueue<WebElement> glinkQueue = null;
	private ExecutorService gThreadPool;
	private File Directory;

	/**
	 * 
	 */
	public Downloader(BlockingQueue<WebElement> queue) {
		this.glinkQueue = queue;
	}

	@Override
	public void run() {
		try {
			System.out.println("Downloader started");
			gThreadPool = Executors.newFixedThreadPool(CrawlerConstants.ThreadPoolSize);
			Directory = CrawlerUtilities.buildSaveDirectory();
			if (Directory == null) {
				System.out.println("unable to create target directory Exiting --");
				System.exit(1);
			}
			Directory.mkdir();
			WebMailLink lQueuedElement;
			int downloadcount = 0;
			while ((lQueuedElement = (WebMailLink)glinkQueue.take()).getLinkUrl() != null) {
				lQueuedElement.save(Directory);
				downloadcount++;
			}
			System.out.println("Downloader finshed with download count " + downloadcount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
