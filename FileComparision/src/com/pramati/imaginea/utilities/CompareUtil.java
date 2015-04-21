/**
 * 
 */
package com.pramati.imaginea.utilities;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

/**
 * @author anandu
 *
 */
public class CompareUtil {

	/**
	 * 
	 */
	public static boolean isDirectMatchAvalaible(Set<String> pContent,
			String ptargetWord) {
		boolean matchAvalaible = false;
		if (pContent != null && pContent.contains(ptargetWord)) {
			matchAvalaible = true;
		}
		return matchAvalaible;
	}

	public static boolean isReverseMatchAvailaible(Set<String> pContent,
			String pSourceWord, String pSplitter) {
		boolean matchAvalaible = false;
		String sourcefirstName = "";
		String sourcelastname = "";
		sourcefirstName = pSourceWord.split(pSplitter)[0];
		sourcelastname = pSourceWord.split(pSplitter)[1];
		Iterator<String> contentIterator = pContent.iterator();
		while (contentIterator.hasNext()) {
			String targetfirstName = contentIterator.next().split(pSplitter)[0];
			String targetLastName = contentIterator.next().split(pSplitter)[1];
			if (sourcefirstName.equalsIgnoreCase(targetfirstName)
					|| sourcefirstName.equalsIgnoreCase(targetLastName)) {
				matchAvalaible = true;
			}
			if (sourcelastname.equalsIgnoreCase(targetfirstName)
					|| sourcelastname.equalsIgnoreCase(targetLastName)) {
				matchAvalaible = true;
			}
		}
		return matchAvalaible;
	}
	
	public static File buildSaveDirectory() {
		File lDir = null;
		String osName = System.getProperty("os.name");
		String userName = System.getProperty("user.name");
		if (osName != null&& !osName.equals("")&& osName.contains("Linux")) {
			lDir = new File("/home/"+userName+"/Desktop/MailArchive");
		}
		else {
			lDir = new File("C:\\MailArchive");
		}
		return lDir;
	}

}
