package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

    // TODO - implement!

    public HistoryTab() {
    	
    } 
    
    public Component draw() {
    	
    	String[] columnNames = {"Date",
                "Time",
                "Total price"};
    	
        JPanel panel = new JPanel();
        
        panel.setLayout(new BorderLayout());
        // TODO - Sales history tabel
        
        JTable table = new JTable(3,3);
        
        table.getColumnModel().getColumn(0).setHeaderValue("Date");
        table.getColumnModel().getColumn(1).setHeaderValue("Time");
        table.getColumnModel().getColumn(2).setHeaderValue("Total Price");
        
        JScrollPane tableContainer = new JScrollPane(table);
        
        panel.add(tableContainer);
        
        return panel;
    }
}