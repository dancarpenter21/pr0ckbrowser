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
}
