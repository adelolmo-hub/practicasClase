package dam2.m6.pt2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Main {
	private static GestionDOM gDom = new GestionDOM();
	private static ArrayList<Autor> autores = new ArrayList<>();
	
	public static void main(String[] args) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			File discografia = new File("data/DiscografiaXML.xml");
			if(discografia.exists()) {
				Document doc = builder.parse(discografia);
				//autores = gDom.recorrerDOM(doc);
				//gDom.mostrarAutores(autores);
				gDom.procesarAutorDirecto(doc);
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

}
