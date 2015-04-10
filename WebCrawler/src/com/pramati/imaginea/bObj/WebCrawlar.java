/**
 * 
 */
package com.pramati.imaginea.bObj;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pramati.imaginea.Exceptions.ShutDownException;
import com.pramati.imaginea.base.Crawlar;
import com.pramati.imaginea.base.Page;

/**
 * This is an Web based implementation of Crawler Interface. It is able to crawl
 * pages.This crawler is capable of crawling multiple pages parallaly in
 * multisthreaded environment. crawling capacity can be supplied from outside to
 * override default crawling capacity.
 * 
 * @author anandu
 *
 */
public class WebCrawlar implements Crawlar {

	/**
	 * Since this crawler is capable to crawling multiple pages parallely. SO
	 * this variable is used to define the crawling capacity of the web crawler
	 * by default it is one that means it can crawl at max one URL parallel.
	 */
	private static final int DEFAULT_CRAWL_CAPACITY = 1;
	/**
	 * This is a thread pool backed by executor service elements are submitted
	 * in this pool and will get executed upon submitting.
	 */
	private ExecutorService threadPool;
	/**
	 * This is a queue backed by Blocking queue. it is used to hold the web
	 * elements contained in the web page. later Download thread will start
	 * taking web elements from this queue to operate over those elements.
	 * 
	 */
	private static BlockingQueue<Page> work_Queue;
	/**
	 * This is used for shut down signaling As soon as shutdown request is
	 * received the crawler will start clearing up it's queued tasks.
	 * 
	 */
	private List<Page> crawledpages;
	
	private volatile boolean shutDown_Req = false;

	private volatile boolean started = false;

	private int threshold;

	/**
	 * Default constructor for web crawler class this will initiate crawler with
	 * default crawling capacity
	 * 
	 */
	public WebCrawlar() {
		this(DEFAULT_CRAWL_CAPACITY);
	}

	/**
	 * This constructor is used to initiate web crawler with user defined
	 * crawling capacity.
	 * 
	 */
	public WebCrawlar(int capacity) {
		this.threshold = capacity;
		crawledpages = new ArrayList<Page>(100);
		threadPool = Executors.newFixedThreadPool(threshold);
		work_Queue = new ArrayBlockingQueue<Page>(threshold);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pramati.imaginea.base.Crawlar#addPage(com.pramati.imaginea.base.Page)
	 */
	@Override
	public void addPage(Page targetPage) throws InterruptedException,
			ShutDownException {
		if (shutDown_Req) {
			throw new ShutDownException("Crawler already shutted Down");
		}
		work_Queue.put(targetPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pramati.imaginea.base.Crawlar#crawl(com.pramati.imaginea.base.Page)
	 */
	@Override
	public void crawl() throws ShutDownException {
		if (shutDown_Req) {
			throw new ShutDownException("Crawler already shutted Down");
		}
		if (!started) {
			started = true;
			new Thread(new Monitor(work_Queue)).start();
		}
	}

	private class Monitor implements Runnable {
		BlockingQueue<Page> work;

		public Monitor(BlockingQueue<Page> queue) {
			this.work = queue;
		}

		@Override
		public void run() {

			while (shutDown_Req) {
				try {
					threadPool.submit(new Traveller(work.take()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (shutDown_Req) {
				ArrayList<Page> lRemainingpages = new ArrayList<Page>();
				work.drainTo(lRemainingpages);
				ListIterator<Page> PageList = lRemainingpages.listIterator();
				while (PageList.hasNext()) {
					threadPool.submit(new Traveller(PageList.next()));
				}
			}
		}

	}

	private class Traveller implements Runnable {
		Page task;

		public Traveller(Page ptask) {
			this.task = ptask;
		}

		@Override
		public void run() {
			try {
				task.save();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pramati.imaginea.base.Crawlar#shutDown(com.pramati.imaginea.base.
	 * Page)
	 */
	@Override
	public void shutDown() throws Exception {
		shutDown_Req = true;

	}
}