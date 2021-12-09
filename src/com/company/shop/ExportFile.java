package com.company.shop;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

// this interface for  managing write items
public interface ExportFile {
    public abstract void writeOrders(OutputStream out, ResultSet result) throws XMLStreamException, SQLException, IOException;
}
