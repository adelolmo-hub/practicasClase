package dam2.m6.pt2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class EscribirFichero {

	public void escribirArchivo(String ruta, Autor autor) {
		File file = new File(ruta);
		FileWriter escritor = null;
		try {
			escritor = new FileWriter(file,true);
			escritor.write(autor.toString());
			for(Album album : autor.getAlbums()){
				escritor.write(album.toString());
			}
			escritor.write("-------------------------------------------------\n");
		} catch (FileNotFoundException e) {
			System.out.println("El fichero " + file + " no existe");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (escritor != null) {
				try {
					escritor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void borrarDatosArchivo(String ruta) {

		try {
			File file = new File(ruta);
			if(file.exists()) {
				FileWriter escritor = new FileWriter(file);
			escritor.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

