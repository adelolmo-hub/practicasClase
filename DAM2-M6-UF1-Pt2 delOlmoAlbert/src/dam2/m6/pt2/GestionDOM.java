package dam2.m6.pt2;

import java.io.File;
import java.util.ArrayList;
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

/**
 * Esta clase contiene metodos para gestionar la informacion leida de un fichero xml utilizando DOM
 * 
 * @author Albert del Olmo
 */
public class GestionDOM {

	/**
	 * Obtiene y recorre los nodos del documento y llama al metodo
	 * procesarAutoresSecuencial para procesar esa información
	 * 
	 * @param doc - Documento XML
	 * @return - ArrayList con los Autores
	 */
	public ArrayList<Autor> recorrerDOM(Document doc) {
		System.out.println("-----------------------------------------------");
		System.out.println("Leyendo XML con DOM de forma Secuencial");
		System.out.println("-----------------------------------------------");
		ArrayList<Autor> datos = new ArrayList<>();
		
		//Obtiene el primer nodo del documento
		Node raiz = doc.getFirstChild(); 
		//Obtiene una lista con los nodos hijo del primer nodo
		NodeList nodelist = raiz.getChildNodes(); 
		
		Node node;
		for (int i=0; i<nodelist.getLength();i++) {
			node = nodelist.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE) {
				datos.add(procesarAutoresSecuencial(node));
			}
		}
		return datos;
	}
	
	/**
	 * Recibe el nodo Autor del metodo anterior y añade la
	 * informacion al objeto Autor
	 * 
	 * @param node - Nodo Autor
	 * @return - Instancia del objeto Autor
	 */
	public Autor procesarAutoresSecuencial(Node node) {
		Node ntemp = null;
		ArrayList<Album> albumList= new ArrayList<>();
		Autor autor = new Autor();
		Album album = null;
		
		//Obtiene el atributo con nombre tipus
		autor.setGroupType(node.getAttributes().getNamedItem("tipus").getTextContent());
 		if(autor.getGroupType().equals("Grup")) {
 			//Obtiene el atributo con nombre num_components
			autor.setGroupNumber(Integer.parseInt(node.getAttributes().getNamedItem("num_components").getTextContent()));
		}
		//Obtiene una lista con los nodos hijo (Titulo y Autor)
		NodeList nodelist = node.getChildNodes();
		for(int i = 0; i<nodelist.getLength(); i++) {
			ntemp = nodelist.item(i);
			album = new Album();
			if(ntemp.getNodeName() == "Nom") {
				//Obtiene los atributos Pais y Nombre
				autor.setCountry(ntemp.getAttributes().item(0).getNodeValue());
				autor.setName(ntemp.getChildNodes().item(0).getNodeValue());
			} 
			if(ntemp.getNodeName() == "Album"){
				//Obtiene los atributos Relacionados con el Album
				album.setYear(Integer.parseInt(ntemp.getAttributes().item(0).getNodeValue()));
				album.setAlbumName(ntemp.getChildNodes().item(0).getNodeValue());
				//Añade el Album a la lista
				albumList.add(album);
			}
		}
		//Añade la lista de albums al Autor
		autor.setAlbums(albumList);
		return autor;
	}
	
	/**
	 * Procesa el archivo xml de forma directa
	 * 
	 * @param doc - Documento xml
	 * @return - ArrayList con los autores
	 */
	public ArrayList<Autor> procesarAutorDirecto(Document doc) {
		Autor autor = null;
		Album album = null;
		ArrayList<Autor> lAutor = new ArrayList<>();
		
		System.out.println("Leyendo XML con DOM de forma Directa");
		System.out.println("-----------------------------------------------");
		//Obtiene una lista con los nodos Autor del documento
		NodeList nListAutor = doc.getElementsByTagName("Autor");
		Node ntemp;
		for(int i = 0; i < nListAutor.getLength(); i++) {
			autor = new Autor();
			ntemp = nListAutor.item(i);
			Element element = (Element) ntemp;
			//Obtiene el atributo con nombre tipus
			autor.setGroupType(element.getAttribute("tipus"));
			if(autor.getGroupType().equals("Grup")) {
				//Obtiene el atributo con nombre num_components
				autor.setGroupNumber(Integer.parseInt(element.getAttribute("num_components")));
			}
			//Obtiene el atributo "pais" y el contenido de "nombre"
			autor.setCountry(element.getElementsByTagName("Nom").item(0).getAttributes().getNamedItem("pais").getTextContent());
			autor.setName(element.getElementsByTagName("Nom").item(0).getTextContent());
			
			//Obtiene una lista con los nodos "Album"
			NodeList nListAlbum = element.getElementsByTagName("Album");
			ArrayList<Album> lAlbum = new ArrayList<>();
			for (int j = 0; j < nListAlbum.getLength(); j++) {
				album = new Album();
				//Obtiene los atributos Relacionados con el Album
				album.setYear(Integer.parseInt(nListAlbum.item(j).getAttributes().getNamedItem("data_publicacio").getTextContent()));
				album.setAlbumName(nListAlbum.item(j).getTextContent());
				//Añade el Album a la lista
				lAlbum.add(album);
			}
			//Añade el Album a Autor
			autor.setAlbums(lAlbum);
			//Añade el Autor a la lista
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
