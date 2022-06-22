/**
 * 
 */
package com.rg.svn.migration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author ramanan.gururaj
 *
 */
public class SVNClone {

	/**
	 * 
	 */
	public SVNClone() {
		// TODO Auto-generated constructor stub
	}

	public void cloneFolder(String[] args) {
		StringBuilder cmd = new StringBuilder(IMigrationConstants.SVN_CMD_CLONE_FOLDER);
		cmd = cmd.append(IMigrationConstants.SPACER).append(args[1]).append(IMigrationConstants.SPACER).append(args[2]);
		Process process = ProcessExecutor.execProcess(cmd.toString());
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	    String line = "";
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("SVN folder clone completed");
	}
}
