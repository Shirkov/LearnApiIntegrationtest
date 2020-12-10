package DbHelper;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class GetElementValueHelper {

    public static String getElementValue(String pathName, String tagName) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(pathName));
        doc.getDocumentElement().normalize();
        NodeList tag = doc.getElementsByTagName(tagName);
        Node tagValue = tag.item(0);
        return tagValue.getTextContent();
    }

    public static String getAtributeValue(int index, String pathName, String tagName, String itemName ) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File(pathName));
        NodeList tag = document.getElementsByTagName(tagName);
        return tag.item(index).getAttributes().getNamedItem(itemName).getTextContent();
    }

    public static void main(String[] args) throws ParserConfigurationException, XPathExpressionException, IOException, SAXException {
        // public static String getXPathValue(String pathName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File("./upd/upd.xml"));

        NodeList children = document.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            // дочерний узел
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // атрибуты узла
                NamedNodeMap attributes = node.getAttributes();
                Node nameAttrib;
                nameAttrib = attributes.getNamedItem("ИнфПолФХЖ1");
                System.out.println ("" + i + ". " +
                        nameAttrib.getNodeValue());
            }
        }

        /*XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
      //  System.out.println((xpath.evaluate("/Файл/Документ/СвСчФакт/ИнфПолФХЖ1/ТекстИнф", document)));
      //  System.out.println((xpath.evaluate("/Файл/Документ/СвСчФакт/ГрузОт/ОнЖе", document)));
        System.out.println((xpath.evaluate("/Файл[@ИдФайл=\"ON_NSCHFDOPPRMARK_2BM-6671089030-667101001-201810031053489319550_2BM-6671089030-667101001-201810031053489319550_20200923_2fc12128-653b-496c-b88a-c404486e2133\"]/Документ[@КНД=\"1115131\"]/СвСчФакт[@НомерСчФ=\"2345\"]/ИнфПолФХЖ1/ТекстИнф[@Идентиф=\"Номер заказа\"]@Значен", document)));

*/
    }

}



