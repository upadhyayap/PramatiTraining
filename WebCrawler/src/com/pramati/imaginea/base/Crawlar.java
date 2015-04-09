/**
 * 
 */
package com.pramati.imaginea.base;

/**
 * This is an generic Interface to represent crawling, There can be different
 * types of crawlers but the behaiour will be same.
 * 
 * @author anandu
 *
 */
public interface Crawlar {

	/**
	 * Implementor of this interface should provide the definition of of crawl
	 * method. This is specific to a particular crawler that how that is
	 * crawling.
	 * 
	 * @throws Exception
	 */
	public void crawl() throws Exception;

	/**
	 * This method is provided to add pages to crawler. Crawler should be able
	 * to crawl multiple urls at particular moment.
	 * 
	 * @param targetPage
	 * @throws Exception
	 */
	public void addPage(Page targetPage) throws Exception;

	/**
	 * This method is used to send shutdown signal to crawler. As soon as
	 * crawler receives this signal it finishse all the queued tasks and
	 * finishes crawling.
	 * 
	 * @throws Exception
	 */
	public void shutDown() throws Exception;
}
