/**
 * 
 */
package com.rg.svn.migration;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author ramanan.gururaj
 *
 */
public class SVNAuthors {

	/**
	 * 
	 */
	public SVNAuthors() {
	}
	
	public void createAuthorsList(String[] args) {
		StringBuilder cmd = new StringBuilder(IMigrationConstants.SVN_CMD_BUILD_AUTHORS);
		cmd = cmd.append(IMigrationConstants.SPACER).append(args[1]);
		Process process = ProcessExecutor.execProcess(cmd.toString());
		createFile(process, args[2]);
	}

	private void createFile(Process process, String filePath) {
		try {
			Set<String> authorsSet = prepareUniqueAuthorList(process);
			FileWriter fileWriter = new FileWriter(filePath, Charset.forName("UTF-8"), true);
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

	private Set<String> prepareUniqueAuthorList(Process process) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
}
