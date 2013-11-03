package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

public class HistoryPaymentDetailedWindowTableModel extends SalesSystemTableModel<SoldItem> {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(PurchaseInfoTableModel.class);

	public HistoryPaymentDetailedWindowTableModel() {
		super(new String[] {"Name", "Price", "Quantity", "Sum"});
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getName();
		case 1:
			return item.getPrice();
		case 2:
			return item.getQuantity();
		case 3:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

}
