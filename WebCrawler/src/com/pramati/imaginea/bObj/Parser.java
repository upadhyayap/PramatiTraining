/**
 * 
 */
package com.pramati.imaginea.bObj;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pramati.imaginea.base.WebElement;

/**fetch links from given url and put in the queue
 * @author anandu
 *
 */
public class Parser implements Runnable {

	private BlockingQueue<WebElement> glinkQueue = null;
	private String gUrl;
	
	public Parser(BlockingQueue<WebElement> queue,String url) {
		this.glinkQueue = queue;
		this.gUrl = url;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("parser srarted");
			Document doc = Jsoup.connect(gUrl).get();
			Elements links = doc.select("a[href]");
			int linkcount = 0;
			for (Element link : links) {
				if (link.attr("href").contains("@")) {
					glinkQueue.put(new WebMailLink(new URL(gUrl + "/raw/" + link.attr("href"))));
					linkcount++;
				}
				
			}
			shutDownQueue();
			System.out.println("parser finished with Link count " +linkcount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws InterruptedException
	 * @throws MalformedURLException 
	 */
	public void shutDownQueue() throws InterruptedException, MalformedURLException {
		glinkQueue.put(new WebMailLink(null));
	}
}
