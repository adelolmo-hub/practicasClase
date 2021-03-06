package dam2.m6.pt2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class EscribirFichero {

	
	/**
	 * Crea un archivo y a?ade la informaci?n de Autor
	 * 
	 * @param ruta - Ruta del fichero
	 * @param autor - Objeto autor con la informaci?n a a?adir	w
	 */
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
	/**
	 * Borra la informaci?n del archivo si ya existe
	 * 
	 * @param ruta - Ruta del archivo
	 */
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

