package com.rg.svn.migration;

import java.io.IOException;

import com.rg.svn.metadata.ParseSVNLogOutput;

public class ProcessExecutor {

	public ProcessExecutor() {
		// TODO Auto-generated constructor stub
	}

	public static Process execProcess(String cmdToExecute) {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmdToExecute);
			int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                throw new IOException("Command exited with " + exitCode);
//            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return process;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: AUTHORS <svn_url> <authors_file_folder_location>");
			System.out.println("Usage: CLONE <svn_url_for_folder_to_clone> <local_folder_path>");
			System.exit(0);
		}
		switch (args[0]) {
			case "AUTHORS":
				SVNAuthors authList = new SVNAuthors();
				authList.createAuthorsList(args);
				break;
			case "CLONE":
				SVNClone cloneSVNFolder = new SVNClone();
				cloneSVNFolder.cloneFolder(args);
				break;
			case "PARSE":
				ParseSVNLogOutput parseRawFile = new ParseSVNLogOutput();
				parseRawFile.parseFile(args[1]);
		}
	}

}
