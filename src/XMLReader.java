import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import gson.JsonObject;
import com.google.gson.JsonArray;

import java.io.File;
import java.util.Scanner;

public class XMLReader {

    public static void main(String[] args) {
        try {
            
            File xmlFile = new File("data.xml");

            // Set up the DOM parser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            //scan for input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the field to extract: ");
            String fieldName = scanner.nextLine().trim();
            // Get the root element
            
            // Find and print all values for the specified field
            NodeList elements = doc.getElementsByTagName(fieldName);
            if (elements.getLength() == 0) {
                System.out.println("No elements found: " + fieldName);
            } else {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < elements.getLength(); i++) {
                    Node node = elements.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;
                        String value = elem.getTextContent().trim();

                        JSONObject obj = new JSONObject();
                        obj.put(fieldName, value);
                        jsonArray.put(obj);
                    }
                }
                System.out.println("\nJSON Output:");
                System.out.println(jsonArray.toString(2));
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
    }}

    // Recursive method to print all elements and their text content
    private static void printElements(Node node, String indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.print(indent + node.getNodeName());

            // Print text content if it exists
            String text = node.getTextContent().trim();
            if (!text.isEmpty() && node.getChildNodes().getLength() == 1) {
                System.out.print(" -> " + text);
            }
            System.out.println();
        }

        // Recurse over child nodes
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            printElements(children.item(i), indent + "  ");
        }
    }
}