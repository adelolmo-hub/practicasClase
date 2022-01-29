package m9.pt1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ResourceSaver {

	Map<String, String> resources = new HashMap<String, String>();

	/**
	 * Add de folder name and the pattern to the hashmap
	 * 
	 * @param pattern - String with the pattern to compare
	 * @param carpeta - String with the folder name
	 */
	public void addResourceType(String pattern, String carpeta) {
		resources.put(pattern, carpeta);
	}

	/**
	 * Creates the folder structure to store our files
	 * 
	 * @throws IOException
	 */
	public void createFolderTree() throws IOException {
		String folderName = "resources";
		createFolder(folderName);
		for (String nombre : resources.values()) {
			createFolder(folderName + "/" + nombre);
		}
	}

	/**
	 * Given a URL, downloads the content and stores it in a specific folder 
	 * 
	 * @param resource - String with the URL to download
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void saveResource(String resource) throws MalformedURLException, IOException {
		URL url = new URL(resource);
		URLConnection con = url.openConnection();
		for (String key : resources.keySet()) {
			if (Pattern.matches(key, con.getContentType())
					|| Pattern.matches(key, URLConnection.guessContentTypeFromName(url.getFile()))) {
				String nombre = resource.split("/")[resource.split("/").length - 1];
				File file = new File("resources/" + resources.get(key) + "/" + resources.get(key) + nombre);
				FileOutputStream fileOut = new FileOutputStream(file);
				con.getInputStream().transferTo(fileOut);
			}
		}
	}

	/**
	 * Creates a folder with the name given by parameter
	 * 
	 * @param folderName - String with the folder name
	 * @throws IOException
	 */
	private void createFolder(String folderName) throws IOException {
		File file = new File(folderName);
		if (!file.exists() && !file.mkdir()) {
			throw new IOException("Can't create the folder");
		}
	}

}
