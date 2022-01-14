package dam2.m6.pt2;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MiHandlerSAX extends DefaultHandler{

	int elementoActual = 0;
	String ruta = "data/DiscografiaResum.txt";
	Autor autor = new Autor();
	Album album = new Album();
	EscribirFichero escribir = new EscribirFichero();
	ArrayList<Album> albums = new ArrayList<>(); 
	
	@Override
	public void startDocument() {
		escribir.borrarDatosArchivo(ruta);
	}
	
	@Override
	public void endDocument() {
		System.out.println("Archivo " + ruta + " creado correctamente");
	}
	
	
	public void startElement(String Uri, String localName, String qName,
			Attributes atts) throws SAXException{
		if(qName.equals("Autor")) {
			elementoActual = 0;
			autor.setGroupType(atts.getValue(0));
			if(autor.getGroupType().equals("Grup")) {
				autor.setGroupNumber(Integer.parseInt(atts.getValue(1)));
			}

		}else if(qName.equals("Nom")){
			elementoActual=1;
			autor.setCountry(atts.getValue(0));
		}else if(qName.equals("Album")){
			elementoActual = 2;
			album.setYear(Integer.parseInt(atts.getValue(0)));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("Album")) {
			album = new Album();
		}else if(qName.equals("Autor")) {
			autor.setAlbums(albums);
			escribir.escribirArchivo(ruta, autor);
			autor = new Autor();
		}
	}
	
	public void characters(char[] ch, int start, int lenght) throws SAXException{
		String car = new String(ch,start,lenght);
		if(elementoActual == 1) { //Nom
			autor.setName(car);
		}else if(elementoActual == 2) { //Album
			album.setAlbumName(car);
			albums.add(album);
		}
		elementoActual = 0;
	}
	
}
