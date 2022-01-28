package m9.pt1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;



public class ResourceSaver {
	
	HashMap<String, String> resources = new HashMap<String, String>();

	public void addResourceType(String pattern, String carpeta) {
		resources.put(pattern, carpeta);
	}

	public void createFolderTree() throws IOException{
		File carpeta = null;
		for(String nombre : resources.values()) {
			carpeta = new File("resources/"+nombre);
			if(!carpeta.exists()) {
				if(!carpeta.mkdir()) {
					System.out.println("No se ha podido crear la carpeta " + carpeta.getName());
				}
			}
		}
	}

	public void saveResource(String resource) throws MalformedURLException, IOException{
		try{
			URL url = new URL(resource);
			URLConnection con = url.openConnection();
			for(String key : resources.keySet()) {
				if(Pattern.matches(key , con.getContentType()) || Pattern.matches(key, URLConnection.guessContentTypeFromName(url.getFile()))){
					String nombre = resource.split("/")[resource.split("/").length-1];
					File file = new File("resources/" +resources.get(key) +"/"+ resources.get(key)+nombre);
					getContent(url, file);
				}
			}
		}catch(IOException e) {
			Logger.getLogger(ResourceSaver.class.getName()).log(Level.SEVERE, null, e);
		}	
	}
	
	public void getContent(URL url, File file){
		BufferedInputStream in;
		byte bytesLlegits[] = new byte[1024];
		int caractersLlegits;

		try {
			in = new BufferedInputStream(url.openStream());
			FileOutputStream fileOutStr = new FileOutputStream(file);
			while((caractersLlegits=in.read(bytesLlegits, 0, 1024))!=-1) {
				fileOutStr.write(bytesLlegits, 0, caractersLlegits);
			}
		}catch (IOException ex) {
			Logger.getLogger(ResourceSaver.class.getName()).log(Level.
					SEVERE, null, ex);
		}
	}

}
