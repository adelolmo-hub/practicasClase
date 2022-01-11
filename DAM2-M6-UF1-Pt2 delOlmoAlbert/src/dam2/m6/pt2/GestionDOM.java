package dam2.m6.pt2;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GestionDOM {
	

	public ArrayList<Autor> recorrerDOM(Document doc) {
		ArrayList<Autor> datos = new ArrayList<>();
		Node node;
		Node raiz = doc.getFirstChild();
		NodeList nodelist = raiz.getChildNodes();
		for (int i=0; i<nodelist.getLength();i++) {
			node = nodelist.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE) {
				datos.add(procesarAutoresSecuencial(node));
			}
		}
		return datos;
	}

	public Autor procesarAutoresSecuencial(Node node) {
		Node ntemp = null;
		ArrayList<Album> albumList= new ArrayList<>();
		Autor autor = new Autor();
		Album album = null;
		
		autor.setGroupType(node.getAttributes().item(0).getNodeValue());
 		if(autor.getGroupType().equals("Grup")) {
			autor.setGroupNumber(Integer.parseInt(node.getAttributes().item(0).getNodeValue()));
		}
		
		NodeList nodelist = node.getChildNodes();
		for(int i = 0; i<nodelist.getLength(); i++) {
			ntemp = nodelist.item(i);
			album = new Album();
			if(ntemp.getNodeName() == "Nom") {
				autor.setCountry(ntemp.getAttributes().item(0).getNodeValue());
				autor.setName(ntemp.getChildNodes().item(0).getNodeValue());
			} 
			if(ntemp.getNodeName() == "Album"){
				album.setYear(Integer.parseInt(ntemp.getAttributes().item(0).getNodeValue()));
				album.setSongName(ntemp.getChildNodes().item(0).getNodeValue());
				albumList.add(album);
			}
		}
		autor.setAlbums(albumList);
		return autor;
	}

	public ArrayList<Autor> procesarAutorDirecto(Document doc) {
		Autor autor = null;
		Album album = null;
		
		ArrayList<Autor> lAutor = new ArrayList<>();
		NodeList nListAutor = doc.getElementsByTagName("Autor");
		Node ntemp;
		for(int i = 0; i < nListAutor.getLength(); i++) {
			autor = new Autor();
			ntemp = nListAutor.item(i);
			Element element = (Element) ntemp;
			autor.setGroupType(element.getAttribute("tipus"));
			if(autor.getGroupType().equals("Grup")) {
				autor.setGroupNumber(Integer.parseInt(element.getAttribute("num_components")));
			}
			autor.setCountry(element.getElementsByTagName("Nom").item(0).getAttributes().getNamedItem("pais").getTextContent());
			autor.setName(element.getElementsByTagName("Nom").item(0).getTextContent());

			NodeList nListAlbum = element.getElementsByTagName("Album");
			ArrayList<Album> lAlbum = new ArrayList<>();
			for (int j = 0; j < nListAlbum.getLength(); j++) {
				album = new Album();
				album.setYear(Integer.parseInt(nListAlbum.item(j).getAttributes().getNamedItem("data_publicacio").getTextContent()));
				album.setSongName(nListAlbum.item(j).getTextContent());
				lAlbum.add(album);
			}
			autor.setAlbums(lAlbum);
			lAutor.add(autor);
		}
		return lAutor;
	}
}
