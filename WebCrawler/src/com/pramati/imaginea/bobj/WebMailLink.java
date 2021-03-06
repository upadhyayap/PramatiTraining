/**
 * 
 */
package com.pramati.imaginea.bobj;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import com.pramati.imaginea.base.WebElement;

/**
 * This Abstract class is an implementation of web element and used to represent
 * a Link in the web page. All the methods defined in this class are specific to
 * how web link should behave.
 * 
 * @author anandu
 *
 */
public class WebMailLink implements WebElement {

	/**
	 * This represents an URL of the link on the page. This link is used to
	 * fetch data and that data will get saved later on
	 * 
	 */
	private URL LINK_URL;
	
	
	/**
	 * This is used to hold data after hitting the URL of the web Link. Finally
	 * data contained in this container is used for later on saving.
	 * 
	 */
	private StringBuilder dataHolder = new StringBuilder();
	private static int FILE_COUNT = 0;
	private boolean loaded;

	// mail email;

	/**
	 * Constructor which takes URL as a parameter
	 * 
	 */
	public WebMailLink(URL url) {
		this.LINK_URL = url;
	}

	/**
	 * This will give the URL of this web mail Link.
	 * 
	 * @return the linkUrl
	 */
	public URL getLinkUrl() {
		return LINK_URL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pramati.imaginea.base.WebElement#save(java.lang.Object)
	 */
	@Override
	public void save(File targetFile) throws Exception {

		if (!loaded) {
			load();
		}
		InputStream stream = new ByteArrayInputStream(dataHolder.toString()
				.getBytes(StandardCharsets.UTF_8));
		MimeMessage message = new MimeMessage(null, stream);
		File lFile = new File(targetFile, message.getSubject() + "_"
				+ message.getSentDate());
		while (!lFile.createNewFile()) {
			lFile = new File(targetFile, message.getSubject() + "_"
					+ message.getSentDate() + FILE_COUNT++);
		}
		FileWriter lWriter = new FileWriter(lFile.getAbsoluteFile());
		BufferedWriter lBuffer = new BufferedWriter(lWriter);
		lBuffer.write(dataHolder.toString());
		lBuffer.flush();
		lBuffer.close();
	}

	@Override
	public void load() throws Exception {

		/*
		 * JAXBContext jaxbContext = JAXBContext.newInstance(mail.class);
		 * Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		 * email = (mail) jaxbUnmarshaller.unmarshal(linkUrl);
		 */
		
		URLConnection urlc = LINK_URL.openConnection();
		BufferedInputStream buffer = new BufferedInputStream(
				urlc.getInputStream());
		int byteRead;
		while ((byteRead = buffer.read()) != -1)
			dataHolder.append((char) byteRead);

		loaded = true;
	}

}
