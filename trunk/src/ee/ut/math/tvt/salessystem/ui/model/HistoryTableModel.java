package ee.ut.math.tvt.salessystem.ui.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.HistoryPaymentDetailedWindow;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTab;

public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {
	
    private static final long serialVersionUID = 1L;
	
	public HistoryTableModel() {
	
		super(new String[] {"Date", "Time", "Total Price", "Order info"});

	}
	
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return item.getTotalPrice();
		case 3:
			return item.getOrderDetailsButton();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public void addItem(final HistoryItem item) {

		rows.add(item);
		fireTableDataChanged();
	}
	
	

}
