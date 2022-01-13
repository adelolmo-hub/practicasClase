package dam2.m6.pt2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MiHandlerSAX extends DefaultHandler{

	String texto = "";
	
	@Override
	public void startDocument() {
		System.out.println("-----------------------------------------------");
		System.out.println("Comienzo del Documento XML");
		System.out.println("Leido con SAX");
		System.out.println("-----------------------------------------------");
	}
	
	@Override
	public void endDocument(){
		System.out.println("Final del Documento XML");
	}
	
	public void startElement(String Uri, String localName, String qName,
			Attributes atts) throws SAXException{
		if(qName.equals("Autor")) {
			System.out.println(qName);
			for (int i = 0; i < atts.getLength(); i++) {
				texto += atts.getValue(i) + " ";
			}

		}else if(qName.equals("Nom")) {
			System.out.println(qName);
			for (int i = 0; i < atts.getLength(); i++) {
				texto += atts.getValue(i) + " ";
			}	
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("Final Elemento: " + qName);
	}
	
	public void characters(char[] ch, int start, int lenght) throws SAXException{
		String car = new String(ch,start,lenght);
		//System.out.println("\tCaracteres: " +car);
	}
	
}
