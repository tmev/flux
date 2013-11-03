package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;

public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {
	
    private static final long serialVersionUID = 1L;
	
	public HistoryTableModel() {
	
		super(new String[] {"Date and Time", "Total Price", "Order info"});

	}
	
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDateTime();
		case 1:
			return item.getTotalPrice();
		case 2:
			return item.getOrderDetailsButton();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public void addItem(final HistoryItem item) {

		rows.add(item);
		fireTableDataChanged();
	}

}
