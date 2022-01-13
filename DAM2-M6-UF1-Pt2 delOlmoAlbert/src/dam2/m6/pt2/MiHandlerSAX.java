package dam2.m6.pt2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MiHandlerSAX extends DefaultHandler{

	String texto = "";
	int elementoActual = 0;
	String nGrupo = "";
	String pais = "";
	
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
			elementoActual = 0;
			if(atts.getValue(0).equals("Grup")) {
				nGrupo = atts.getValue(1);
			}
			texto+=qName + ": ";

		}else if(qName.equals("Nom")){
			elementoActual=1;
			pais="(" + atts.getValue(0) + ") ";
		}else if(qName.equals("Album")){
			elementoActual = 2;
			texto+= atts.getValue(0);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("Autor")) {
			System.out.println("----------------------------------");
		}
	}
	
	public void characters(char[] ch, int start, int lenght) throws SAXException{
		String car = new String(ch,start,lenght);
		if(elementoActual == 1) { //Nom
			texto += car + " " + pais +"- "+nGrupo;
			System.out.println(texto);
			texto = "";
		}else if(elementoActual == 2) { //Album
			texto += ": "+car;
			System.out.println(texto);
			texto = ""; 
		}
		elementoActual = 0;
	}
	
}
