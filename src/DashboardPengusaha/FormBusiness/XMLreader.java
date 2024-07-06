package DashboardPengusaha.FormBusiness;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLreader {

    public static BusinessData readDataFromXML() {
        BusinessData data = new BusinessData();

        try {
            File xmlFile = new File("business_data.xml");
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("business");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    data.setOwner(element.getElementsByTagName("owner").item(0).getTextContent());
                    data.setBusinessName(element.getElementsByTagName("businessName").item(0).getTextContent());
                    data.setLocation(element.getElementsByTagName("location").item(0).getTextContent());
                    data.setEstDate(element.getElementsByTagName("estDate").item(0).getTextContent());
                    data.setCategory(element.getElementsByTagName("category").item(0).getTextContent());
                    data.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
