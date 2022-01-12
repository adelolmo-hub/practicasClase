package dam2.m6.pt2;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

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
				album.setAlbumName(ntemp.getChildNodes().item(0).getNodeValue());
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
				album.setAlbumName(nListAlbum.item(j).getTextContent());
				lAlbum.add(album);
			}
			autor.setAlbums(lAlbum);
			lAutor.add(autor);
		}
		return lAutor;
	}
	
	public void nuevoElementoDOM(Document doc, String year, String albumName) {
		try {
			Element nSongName = doc.createElement("Album");
			nSongName.setAttribute("data_publicacio", year);
			nSongName.setTextContent(albumName);
			NodeList nListAutores = doc.getElementsByTagName("Nom");
			Node nodeAutor = null;
			for (int i = 0; i < nListAutores.getLength(); i++) {
				Node nNode = nListAutores.item(i);
				if(nNode.getTextContent().equals("Bruce Springsteen")) {
					nodeAutor = nNode.getParentNode();
				}
			}
			nodeAutor.appendChild(nSongName);
			guardarDOMaFileTransformer("DiscografiaV2.xml", doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void generarXML(ArrayList<Autor> autores){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document doc = implementation.createDocument(null, "Musica", null);
			Node root = doc.getDocumentElement();

			for(Autor autor : autores){
				List<Album> albumList = autor.getAlbums();
				for(Album album : albumList){
					Element nAlbum = doc.createElement("Album");
					nAlbum.setAttribute("data_publicacio", album.getYear()+"");
					nAlbum.setAttribute("autor", autor.getName());
					nAlbum.setTextContent(album.getAlbumName());
					root.appendChild(nAlbum);
				}
			}
			guardarDOMaFileTransformer("DiscografiaResumida.xml", doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public int guardarDOMaFileTransformer(String nombre, Document doc)
    {
        try{
                
        //Obtenemos un transformer               
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
        
        //Indicamos la fuente a transformar
        Source source = new DOMSource(doc);
        
        //Indicamos el destino
        File destino = new File("data/" + nombre);
        StreamResult result = new StreamResult(destino);
        
              
        transformer.transform(source,result);
        System.out.println("Archivo " + destino.getName() + " creado con exito");

        return 0;
         }catch(Exception e) {
           
           return -1;
        }
    }
}
