/**
 * 
 */
package com.pramati.imaginea.bobj;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pramati.imaginea.base.Page;
import com.pramati.imaginea.base.WebElement;
import com.pramati.imaginea.base.WebPage;
import com.pramati.imaginea.utilities.CrawlerConstants;

/**fetch links from given url and put in the queue
 * @author anandu
 *
 */
public class Parser implements Runnable {

	private BlockingQueue<WebElement> elementQueue ;
	private BlockingQueue<Page> pageQueue ;
	private String url;
	
	

	public Parser(BlockingQueue<WebElement> elementQueue,BlockingQueue<Page> pageQueue,String url) {
		this.elementQueue = elementQueue;
		this.pageQueue = pageQueue;
		this.url = url;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//System.out.println("parser srarted");
			StringBuilder sb = new StringBuilder();
			//System.out.println("Connecting URl   "+ url);
			/*Document doc =  Jsoup.connect(url).userAgent("Mozilla/36.0.4").timeout(0).followRedirects(true)
						    .maxBodySize(1024*1024*3).ignoreContentType(true).get();*/
			//Elements links = doc.select("a[href]");
			URL rawUrl = new URL(url);
			URLConnection urlc = rawUrl.openConnection();
			BufferedInputStream buffer = new BufferedInputStream(
					urlc.getInputStream());
			
			StringBuilder builder = new StringBuilder();
			int byteRead;
			while ((byteRead = buffer.read()) != -1)
				builder.append((char) byteRead);
			
			buffer.close();
			
			Document doc = Jsoup.parse(builder.toString());
			Elements lDomElements = doc.getAllElements();
			int linkcount = 0;
			for (Element lElement : lDomElements) { 
				if(lElement.hasText()) {
					sb.append(lElement.text());
				}
				if(lElement.select("a[href]")!=null) {
					if (lElement.select("a[href]").attr("href").contains("@")) {
						if (lElement.select("a[href]").attr("href").contains(CrawlerConstants.appender)) {
							pageQueue.put(new WebPage(new URL(CrawlerConstants.RootUrl + lElement.select("a[href]").
									attr("href"))));
							//System.out.println("Web page inserted in the queue with URL  " +CrawlerConstants.RootUrl + 
									//lElement.select("a[href]").attr("href"));
						} else {
							pageQueue.put(new WebPage(new URL(CrawlerConstants.RootUrl+CrawlerConstants.appender + 
									lElement.select("a[href]").attr("href"))));
							//System.out.println("Web page inserted in the queue with URL  " + CrawlerConstants.RootUrl+
									//CrawlerConstants.appender + lElement.select("a[href]").attr("href"));
						}
						linkcount++;
						/*System.out.println("Web page inserted in the queue with URL  " + url +" ^^appender Appended to ^^"+ 
						lElement.select("a[href]").attr("href") );*/
					} else {
						//System.out.println("not contains @");
					}
				}	
			}
			if (sb.length()>0) {
				elementQueue.put(new WebText(sb.toString()));
				//System.out.println("Web Element inserted in the queue");
			}
			//shutDownQueue();
			//System.out.println("parser finished with Link count " +linkcount);
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
		URL poisionUrl = null;
		pageQueue.put(new WebPage(poisionUrl));
		elementQueue.put(new WebText(null));
		System.out.println("Poision instances inserted");
	}
}
