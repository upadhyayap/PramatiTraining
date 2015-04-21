/**
 * 
 */
package com.pramati.imaginea.bobj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.pramati.imaginea.base.Equatable;
import com.pramati.imaginea.utilities.CompareUtil;

/**
 * @author anandu
 *
 */
public class TextFile implements Equatable<TextFile> {

	private File sourceFile;
	private Set<String> content = new HashSet<String>();
	private boolean read = false;
	/**
	 * 
	 */
	public TextFile(File pFile) {
		this.sourceFile = pFile;
	}
	
	public Set<String> getContent() {
		return content;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pramati.imaginea.base.Equatable#equate(java.io.File)
	 */
	@Override
	public Set<String> equate(TextFile targetFile)
			throws IllegalArgumentException, IOException {
		Set<String> matchingContent = new TreeSet<String>();
		if (targetFile instanceof TextFile) {
			if(!this.read) {
				read();
			}
			if(!targetFile.read) {
				targetFile.read();
			}
			if(!this.content.isEmpty()) {
				Iterator<String> contentiterator = content.iterator();
				StringBuilder name = new StringBuilder();
				while(contentiterator.hasNext()) {
					if(name.length()>0) {
						name.setLength(0);
					} 
					name.append(contentiterator.next());
					if (CompareUtil.isDirectMatchAvalaible(targetFile.getContent(), name.toString())) {
						matchingContent.add(name.toString());
					} else if (CompareUtil.isReverseMatchAvailaible(targetFile.getContent(), name.toString(), " ")){
						matchingContent.add(name.toString());
					}
				}
			}
			
		} else {
			throw new IllegalArgumentException(
					"Only Text file can be compared to a text file");
		}
		return matchingContent;
	}

	public void read() throws IOException {

		FileReader reader = new FileReader(sourceFile);
		BufferedReader buffer = new BufferedReader(reader);
		String PersonName = "";
		while (((PersonName = buffer.readLine()) != null)) {
			this.content.add(PersonName);
		}
		buffer.close();
		read = true;
	}

}
