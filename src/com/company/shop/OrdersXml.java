package com.company.shop;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.io.OutputStream;
import java.sql.*;


public class OrdersXml implements  ExportFile{
    @Override
    public void writeOrders(OutputStream out, ResultSet result) throws XMLStreamException, SQLException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(out);
        writer.writeStartDocument("utf-8", "1.0");

        // <orders>
        writer.writeStartElement("orders");
        while(result.next()) {
            // <order>
            writer.writeStartElement("order");
            // <.. id="">
            writer.writeAttribute("id", result.getString(1));

            // <customer>
            writer.writeStartElement("customer");
            writer.writeCharacters(result.getString(2)); // item name
            writer.writeEndElement();

            // <total_price>
            writer.writeStartElement("total_price");
            writer.writeCharacters(result.getString(3)); // total_price
            writer.writeEndElement();

            // <item>
            writer.writeStartElement("item");
            writer.writeStartElement("name");
            writer.writeCharacters(result.getString(3)); // item -> name
            writer.writeEndElement();
            writer.writeStartElement("price");
            writer.writeCharacters(result.getString(4)); // item -> name
            writer.writeEndElement();
            writer.writeEndElement();

            // </order>
            writer.writeEndElement();
        }
        writer.writeEndDocument();
        // </orders>
        writer.flush();
        writer.close();

    }
}
