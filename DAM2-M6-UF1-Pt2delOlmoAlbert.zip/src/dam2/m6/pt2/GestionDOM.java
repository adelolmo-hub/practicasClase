package dam2.m6.pt2;

import java.util.ArrayList;
import java.util.List;

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
		
 		if(node.getAttributes().item(0).getNodeValue().equals("Solista")) {
			autor.setGroupType(node.getAttributes().item(0).getNodeValue());
		}else {
			autor.setGroupNumber(Integer.parseInt(node.getAttributes().item(0).getNodeValue()));
			autor.setGroupType(node.getAttributes().item(1).getNodeValue());
			
		}
		
		NodeList nodelist = node.getChildNodes();
		for(int i = 0; i<nodelist.getLength(); i++) {
			ntemp = nodelist.item(i);
			if(ntemp.getNodeName() == "Nom") {
				autor.setCountry(ntemp.getAttributes().item(0).getNodeValue());
				autor.setName(ntemp.getChildNodes().item(0).getNodeValue());
			} 
			if(ntemp.getNodeName() == "Album"){
				album = new Album();
				album.setYear(Integer.parseInt(ntemp.getAttributes().item(0).getNodeValue()));
				album.setSongName(ntemp.getChildNodes().item(0).getNodeValue());
				albumList.add(album);
			}
		}
		autor.setAlbums(albumList);
		return autor;
	}
	
	
	public void mostrarAutores(List<Autor> autores) {
		for(int i = 0; i<autores.size(); i++) {
			String name = autores.get(i).getName();
			String country = autores.get(i).getCountry();
			String groupType = autores.get(i).getGroupType();
			List<Album> albums = autores.get(i).getAlbums();
			if(groupType.equals("Grup")) {
				int groupNumber = autores.get(i).getGroupNumber();
				System.out.println(String.format("Autor: %s (%s) - %s (%d components)", name, country, groupType, groupNumber));
			}else {
				System.out.println(String.format("Autor: %s (%s) - %s ", name, country, groupType));
			}
			for(Album albumName : albums) {
				System.out.println(String.format("%d: %s",albumName.getYear(), albumName.getSongName()));
			}
			System.out.println("------------------------------------------");
		}
	}

	public void procesarAutorDirecto(Document doc) {
		NodeList nodelist = doc.getElementsByTagName("Autor");
		for(int i = 0; i < nodelist.getLength(); i++) {
			System.out.println(nodelist.item(i).getAttributes().item(0).getNodeValue());
		}
		
		
	}
	
}
