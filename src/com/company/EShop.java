package com.company;

import com.company.shop.OrdersEx;
import com.company.shop.OrdersXml;

import javax.xml.stream.XMLStreamException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EShop {

    FileOutputStream out = null;
    String output_mode = "excel";
    OrdersXml ordersXml = new OrdersXml();
    OrdersEx ordersEx = new OrdersEx();
    Connection connection = null;
    Statement sta = null;
    ResultSet categories = null;
    // map to save key for select statement & value for xml file's name
    Map<Integer, String> category_map = null;
    static EShop eShop = new EShop();

    private static void getDBCon() throws ClassNotFoundException, SQLException {
        //database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            eShop.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer_service", "root", "leen20123");
            eShop.sta = eShop.connection.createStatement();
            //collect all categories
            eShop.categories = eShop.sta.executeQuery("select id,name from category");
            // map to save key for select statement & value for xml file's name
            eShop.category_map = new HashMap<>();
            while (eShop.categories.next()) {
                eShop.category_map.put(eShop.categories.getInt(1), eShop.categories.getString(2));
            }
        } catch (Exception exc) {
            System.out.println("getDBCon exception: " + exc);
        }
    }

    public static void main(String[] args) throws SQLException, XMLStreamException {
        try {
            getDBCon();
            for (Map.Entry category : eShop.category_map.entrySet()) {
                System.out.println("exporting "+category.getKey() + "/" + category.getValue() +" orders to an "+eShop.output_mode+" file.");
                ResultSet orders = eShop.sta.executeQuery("select o.*,i.name,i.price from orders o join order_item oi on o.id = oi.order_id join shopping_item i on oi.item_id = i.id and i.category_id = " + category.getKey() + ";");
                if (eShop.output_mode.equals("xml")) {
                    // make xml files
                    eShop.out = new FileOutputStream(category.getValue() + "-orders.xml");
                    eShop.ordersXml.writeOrders(eShop.out, orders);
                } else {
                    // make excel files
                    eShop.out = new FileOutputStream(category.getValue() + "-orders.xlsx");
                    eShop.ordersEx.writeOrders(eShop.out, orders);
                }

            }
            eShop.connection.close();
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }
}


