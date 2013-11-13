package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

public class JTableButtonMouseListenerStock implements MouseListener {

	private JTable table;
	private StockTab stockTab;
	
	public JTableButtonMouseListenerStock(JTable table, StockTab stockTab) {
		super();
		this.table = table;
		this.stockTab = stockTab;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.stockTab.changeRowIndex(this.table.rowAtPoint(e.getPoint()));

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
