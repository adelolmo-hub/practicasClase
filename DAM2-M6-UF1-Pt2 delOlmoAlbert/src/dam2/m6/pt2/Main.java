package dam2.m6.pt2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Main {

		private static GestionDOM gDom = new GestionDOM();
		private static ArrayList<Autor> autores = new ArrayList<>();
		
		public static void main(String[] args) {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			
			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			
			
			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				File discografia = new File("data/DiscografiaXML.xml");
				
				SAXParser parser = saxFactory.newSAXParser();
				MiHandlerSAX mh = new MiHandlerSAX();
				
				
				if(discografia.exists()) {
					Document doc = builder.parse(discografia);
					autores = gDom.recorrerDOM(doc);
					for (Autor autor : autores) {
						printarAutores(autor);
					}
					autores = gDom.procesarAutorDirecto(doc);
					for (Autor autor : autores) {
						printarAutores(autor);
					}
					
					gDom.nuevoElementoDOM(doc, "2014", "High Hopes");
					gDom.generarXML(autores);
					
					parser.parse(discografia, mh);
					
				}else {
					System.out.println("El fichero no existe");
				}
			}catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
		
		public static void printarAutores(Autor autor) {
			System.out.print(autor.toString());
			
			for(Album albumName : autor.getAlbums()) {
				System.out.print(albumName.toString());
			}
			System.out.println("-----------------------------------------------");
		}

}
