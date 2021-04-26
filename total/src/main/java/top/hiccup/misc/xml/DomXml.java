package top.hiccup.misc.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * xml的DOM解析
 *
 * @author wenhy
 * @date 2020/9/20
 */
public class DomXml {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("roster.xml"));
            System.out.println(document.getXmlVersion());
            System.out.println(document.getXmlEncoding());
            System.out.println(document.getDoctype());

            Element root = document.getDocumentElement();
            System.out.println(root.getTagName());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
