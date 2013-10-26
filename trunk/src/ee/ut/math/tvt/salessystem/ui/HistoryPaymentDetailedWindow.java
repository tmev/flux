package ee.ut.math.tvt.salessystem.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.HistoryPaymentDetailedWindowTableModel;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class HistoryPaymentDetailedWindow {
	
	private static final Logger log = LogManager.getLogger(PaymentWindow.class);
	
	private JFrame frame;
	private HistoryPaymentDetailedWindowTableModel HistoryPaymentDetailedWindowTableModel;

	public HistoryPaymentDetailedWindow(HistoryPaymentDetailedWindowTableModel HistoryPaymentDetailedWindowTableModel, ArrayList<SoldItem> orderDetails) {
    	this.HistoryPaymentDetailedWindowTableModel = HistoryPaymentDetailedWindowTableModel;
		draw();
		HistoryPaymentDetailedWindowTableModel.populateWithData(orderDetails);
		
	}

	
    public Component draw() {
		
		frame = new JFrame("Order details");
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
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.setSize(100, 200);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
        return frame;
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

        JTable table = new JTable(HistoryPaymentDetailedWindowTableModel);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        panel.setLayout(gb);
        panel.add(scrollPane, gc);

        panel.setBorder(BorderFactory.createTitledBorder("Order details"));
        return panel;
      }

}
