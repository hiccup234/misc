package top.hiccup.misc.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * xml的SAX解析
 * <p>
 * DOM：在内存中建立起xml对应的文档树结构，需要加载整个xml文件
 * <p>
 * SAX：基于事件处理机制，占用内存少效率高，但没办法随机到达文档某部分
 *
 * @author wenhy
 * @date 2020/9/20
 */
public class SaxXml {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startDocument() throws SAXException {
                System.out.println("开始处理xml文档");
            }

            @Override
            public void endDocument() throws SAXException {
                System.out.println("处理xml文档结束");
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                System.out.println("<" + qName + ">");
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                System.out.println("</" + qName + ">");
            }

            @Override
            public void characters(char ch[], int start, int length) throws SAXException {
                System.out.println(new String(ch, start, length));
            }
        };
        parser.parse(new File("roster.xml"), handler);
    }
}
