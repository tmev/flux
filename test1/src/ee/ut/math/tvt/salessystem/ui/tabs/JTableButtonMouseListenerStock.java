package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;

import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

public class JTableButtonMouseListenerStock extends MouseAdapter {

	private final JTable table;
	private StockTab tab;

	public JTableButtonMouseListenerStock(JTable table, StockTab tab) {
		this.table = table;
		this.tab = tab;
	}

	@Override public void mouseClicked(MouseEvent e) {
		int row = e.getY()/table.getRowHeight(); 
		tab.changeRowIndex(row);
	}
}