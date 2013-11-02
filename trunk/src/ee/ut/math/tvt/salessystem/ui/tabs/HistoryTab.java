package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.HistoryPaymentDetailedWindow;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {
	
	private SalesSystemModel salesSystemModel;
	private static final Logger log = LogManager.getLogger(PurchaseTab.class);

    public HistoryTab(SalesSystemModel salesSystemModel) {
    	this.salesSystemModel = salesSystemModel;
    } 
    
    public Component draw() {
    		
        JPanel panel = new JPanel();
        
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        panel.setLayout(gb);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1.0d;
        gc.weighty = 0d;

        panel.add(drawStockMenuPane(), gc);

        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        panel.add(drawStockMainPane(), gc);
        return panel;
    }
    
 // history menu
    private Component drawStockMenuPane() {
      JPanel panel = new JPanel();

      GridBagConstraints gc = new GridBagConstraints();
      GridBagLayout gb = new GridBagLayout();

      panel.setLayout(gb);

      gc.anchor = GridBagConstraints.NORTHWEST;
      gc.weightx = 0;

      gc.gridwidth = GridBagConstraints.RELATIVE;
      gc.weightx = 1.0;

      panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      return panel;
    }
   
 // table of the history stock
    private Component drawStockMainPane() {
        JPanel panel = new JPanel();

        JTable table = new JTable(salesSystemModel.getCurrentHistoryTableModel());

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        
        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumnModel().getColumn(2).setCellRenderer(buttonRenderer);
        table.addMouseListener(new JTableButtonMouseListener(table));

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        panel.setLayout(gb);
        panel.add(scrollPane, gc);

        panel.setBorder(BorderFactory.createTitledBorder("History status"));
        return panel;
      }
}