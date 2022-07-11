import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;
import java.io.File;
public class XMLReader {
	public void read (int angka, JTextArea hasil) {
		try {
			//creating a constructor of file class and parsing an XML file  
			File file = new File("D:\\YARSI\\FTI\\Semester 5\\Projek Akhir\\ProjekAkhirTKI\\src\\cacm.query.xml");  
			//an instance of factory that gives a document builder  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			//an instance of builder to parse the specified xml file  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();  
//			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
			NodeList nodeList = doc.getElementsByTagName("query");
//			System.out.println("Total: " + nodeList.getLength());
			String[] text = new String[nodeList.getLength()];
			// nodeList is not iterable, so we are using for loop  
			for (int i = 0; i < nodeList.getLength(); i++) {  
				Node node = nodeList.item(i);  
//				System.out.println("\nNode Name: " + node.getNodeName());  
				if (node.getNodeType() == Node.ELEMENT_NODE) {  
					Element eElement = (Element) node;
					String txt = eElement.getElementsByTagName("text").item(0).getTextContent();
//					System.out.println("Number: "+ eElement.getElementsByTagName("number").item(0).getTextContent());  
//					System.out.println("Text:"+ txt); 
					text[i] = txt;
				}  
			}
			hasil.setText(text[angka-1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
