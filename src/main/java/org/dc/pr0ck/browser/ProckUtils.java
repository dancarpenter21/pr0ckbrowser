package org.dc.pr0ck.browser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.dc.pr0ck.DirectoryTraverser;
import org.dc.pr0ck.DomProckBuilder;
import org.dc.pr0ck.ProckException;

public class ProckUtils {

	public static void makeProck(String rootDirectoryString, String outFileString, String password, boolean ignoreTrash) throws IOException, ProckException {
		try {
			File sourceDirectory = new File(rootDirectoryString);
			if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
				throw new IllegalArgumentException("Bad source directory");
			}

			File outputFile = new File(outFileString);
			if (outputFile.isDirectory()) {
				throw new IllegalArgumentException("Out file cannot be a directory");
			}
			
			DirectoryTraverser directoryTraverser = new DirectoryTraverser();
			DomProckBuilder builder = new DomProckBuilder(sourceDirectory);
			directoryTraverser.traverse(sourceDirectory, builder);
			builder.setPassword(password);
			builder.build(outputFile);
		} catch (ParserConfigurationException e) {
			throw new ProckException(e);
		}
	}
	
	/**
	 * 
	 * @param pathString string representation of the file path
	 * @return <code>true</code> iff the supplied String is not a path
	 * that already exists, and if it exists it's a file and not a directory
	 */
	public static boolean isFilePath(String pathString) {
		File file = new File(pathString);
		if (file.exists() && file.isDirectory()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean exists(String pathString) {
		return new File(pathString).exists();
	}

	public static boolean isDirectory(String pathString) {
		return new File(pathString).isDirectory();
	}
}
