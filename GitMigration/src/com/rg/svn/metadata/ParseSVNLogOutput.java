/**
 * 
 */
package com.rg.svn.metadata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author ramanan.gururaj
 *
 */
public class ParseSVNLogOutput {

	/**
	 * 
	 */
	public ParseSVNLogOutput() {
		// TODO Auto-generated constructor stub
	}

	public void parseFile(String filePath) {
		try {
			String outFilePath = getOutFile(filePath);
			Set<String> authorsSet = prepareUniqueAuthorList(filePath);
			FileWriter fileWriter = new FileWriter(outFilePath, Charset.forName("UTF-8"), true);
			for(String line : authorsSet) {
				fileWriter.write(line);
				fileWriter.write(System.getProperty( "line.separator" ));
			}
			fileWriter.close();
			System.out.println("SVN Author list generated.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getOutFile(String filePath) {
		String outputFilePath = "";
		Path path = Path.of(filePath);
		String outFileName = (path.getFileName().toString()).replace("Raw", "Parsed");
		outputFilePath = path.getParent().toString() + File.separator + outFileName;
System.out.println("Debug");
		return outputFilePath;
	}
	private Set<String> prepareUniqueAuthorList(String filePath) throws IOException {

	    BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(filePath));
	    }catch(FileNotFoundException ex) {
	    	ex.printStackTrace();
	    	return null;
	    }
	    String line = "";
	    Set<String> linkedHashSet = new LinkedHashSet<String>();
		while ((line = reader.readLine()) != null) {
			if (line.contains("|")) {
				String[] strArr = line.split("\\|");
			    String userName = strArr[1].trim();
			    String printName = "";
			    if(userName.contains(".")) {
			    	printName = capitalizeString(userName);
			    }else {
			    	printName = strArr[1].trim();
			    }
			    linkedHashSet.add(userName.trim() + " = " + printName + " <" + userName.trim() + "@duckcreek.com>");
			}
		}
		reader.close();
		return linkedHashSet;
	}

	private String capitalizeString(String str) {
		String[] userName = str.split("\\.");
		String returnStr = "";
		for (int i = 0; i < userName.length; i++) {
			returnStr = returnStr + " " + userName[i].substring(0, 1).toUpperCase() + userName[i].substring(1);
		}
		return returnStr.trim();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new ParseSVNLogOutput()).parseFile(args[0]);
	}

}
