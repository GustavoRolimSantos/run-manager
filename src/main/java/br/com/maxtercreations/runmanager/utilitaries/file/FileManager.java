package br.com.maxtercreations.runmanager.utilitaries.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import br.com.maxtercreations.runmanager.logger.Logger;

public class FileManager {

	/*
	 * The file manager is used to control the database information: defining,
	 * redefining, excluding or adding data
	 */
	
	public void write(String directory, Object object) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(directory, true)));
			out.println(object.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	public void print(String directory, Object object) {
		try (PrintWriter out = new PrintWriter(directory)) {
			out.println(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getString(String directory, Object key) {
		File configFile = new File(directory);
		String string = new String();

		if (!configFile.exists() || configFile.isDirectory())
			return null;

		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);

			string = props.getProperty(key.toString());

			reader.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return string;
	}

	public FileManager createFile(String directory) {
		if (!new File(directory).exists()) {
			try {
				File file = new File(directory);
				file.getParentFile().mkdirs();
				file.createNewFile();
				Logger.debug("Creating " + file.getName() + " file.");
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return this;
	}

}
