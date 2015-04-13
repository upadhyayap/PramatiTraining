/**
 * 
 */
package com.pramati.imaginea.bobj;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import com.pramati.imaginea.base.WebElement;
import com.pramati.imaginea.utilities.CrawlerUtilities;

/**
 * take data from the queue and hit the url get the mail object and submit the
 * task to executor for saving
 * 
 * @author anandu
 *
 */
public class Downloader implements Runnable {

	private BlockingQueue<WebElement> elementQueue;
	private File Directory;

	/**
	 * 
	 */
	public Downloader(BlockingQueue<WebElement> elementQueue) {
		this.elementQueue = elementQueue;
	}

	@Override
	public void run() {
		try {
			System.out.println("Downloader started");
			Directory = CrawlerUtilities.buildSaveDirectory();
			if (Directory == null) {
				System.out.println("unable to create target directory Exiting --");
				System.exit(1);
			}
			Directory.mkdir();
			WebElement lQueuedElement;
			int downloadcount = 0;
			boolean shutdown = false;
			while (!shutdown) {
				System.out.println("downloader waiting for data");
				lQueuedElement = elementQueue.take();
				System.out.println("downloader took the data");
				if (lQueuedElement instanceof WebText) {
					if(((WebText) lQueuedElement).getDataHolder() == null) {
						shutdown = true;
						System.out.println("poision element found stoping queue monitoring");
					}
					else {
						System.out.println("Downloading Web Element");
						lQueuedElement.save(Directory);
					}
				} 
				downloadcount++;
			}
			
			
			System.out.println("Downloader finshed with download count " + downloadcount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
