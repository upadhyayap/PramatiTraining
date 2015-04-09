package com.pramati.imaginea.startup;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import com.pramati.imaginea.Entities.mail;
import com.pramati.imaginea.utilities.CrawlerConstants;
import com.pramati.imaginea.utilities.CrawlerUtilities;

public class Main {
	public static void main(String[] args) throws ParserConfigurationException, SAXException {
		
			long startTime = System.currentTimeMillis();
			try {
				downloadData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long EndTime = System.currentTimeMillis();
			System.out.println("Total time taken" + (EndTime - startTime));
			//getWebsite();
			
		
		
	}// end main
	public static void downloadData() throws IOException, JAXBException, MessagingException {
		Document doc;
		File Directory = CrawlerUtilities.buildSaveDirectory(); 
		Directory.mkdir();
		doc = Jsoup.connect("http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/").get();
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			//System.out.println(link.attr("href"));
			if (link.attr("href").contains("@")) {
				/*JAXBContext jaxbContext = JAXBContext.newInstance(mail.class);  
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()*/;  
				URL rawUrl = new URL(CrawlerConstants.RootUrl + "/raw/" + link.attr("href"));
				
				URLConnection urlc = rawUrl.openConnection();

				BufferedInputStream buffer = new BufferedInputStream(
						urlc.getInputStream());
				
				StringBuilder builder = new StringBuilder();
				int byteRead;
				while ((byteRead = buffer.read()) != -1)
					builder.append((char) byteRead);
				
				buffer.close();
				InputStream stream = new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));
				MimeMessage message = new MimeMessage(null, stream);
				/*File lFile = new File(Directory, link.attr("href"));
				lFile.createNewFile();
				FileWriter lWriter = new FileWriter(lFile.getAbsoluteFile());
				BufferedWriter lBuffer = new BufferedWriter(lWriter);
				lBuffer.write(builder.toString());
				lBuffer.flush();
				lBuffer.close();*/
			}
			
		}
	}
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

	public static void getWebsite() {

		try {

			URL lurl = new URL(
					"http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/raw/%3Cm6c5hv$lu1$1@ger.gmane.org%3E");
			URLConnection urlc = lurl.openConnection();

			BufferedInputStream buffer = new BufferedInputStream(
					urlc.getInputStream());

			StringBuilder builder = new StringBuilder();
			int byteRead;
			while ((byteRead = buffer.read()) != -1)
				builder.append((char) byteRead);

			buffer.close();

			System.out.println(builder.toString());

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static String fetchEmailFromUrl(String url) {

		StringBuilder builder = new StringBuilder();
		try {

			URL lurl = new URL(url);
			URLConnection urlc = lurl.openConnection();

			BufferedInputStream buffer = new BufferedInputStream(
					urlc.getInputStream());

			int byteRead;
			while ((byteRead = buffer.read()) != -1)
				builder.append((char) byteRead);
			System.out.println(builder.toString());
			buffer.close();

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return builder.toString();
	}
	public static void getLinks () {
		Document doc;
		try {
			doc = Jsoup.connect("http://mail-archives.apache.org/mod_mbox/maven-users/201412.mbox/").get();
			Elements links = doc.select("a[href]");
			
			for (Element link : links) {
				if (link.attr("href").contains("@")) {
					System.out.println(link.attr("href"));
					
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static mail fetchAndparseEmail(URL pTargetUrl) throws JAXBException, MalformedURLException{
		
		 JAXBContext jaxbContext = JAXBContext.newInstance(mail.class);  
		   
	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
	        mail email= (mail) jaxbUnmarshaller.unmarshal(pTargetUrl);
	        return email;
	}
	public static void saveOnDisk() throws IOException {
		String os = System.getProperty("os.name");
		System.out.println(System.getProperty("user.name"));
		File folder = new File("/home/anandu/Desktop/MailArchive");
		folder.mkdir();
		File file = new File(folder, "test");
		file.createNewFile();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Hello ubuntu");
		bw.close();
		System.out.println("Success   " + folder.getPath());
	}
}
