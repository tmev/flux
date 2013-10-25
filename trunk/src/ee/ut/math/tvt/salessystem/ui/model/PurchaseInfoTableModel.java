package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(PurchaseInfoTableModel.class.getCanonicalName());
	

	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum"});
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	/**
	 * Add new StockItem to table.
	 */
	public void addItem(final SoldItem item) {
		try {
			SoldItem itemInTable = getItemById(item.getId());
			itemInTable.setQuantity(itemInTable.getQuantity() + item.getQuantity());
		} catch (NoSuchElementException e) {
			// This item is not on the list.
			rows.add(item);
		}
		fireTableDataChanged();
	}
	
	/**
	 * Get total price of current basket.
	 * 
	 * @return int, sum of sums for every SoldItem in this table.
	 */
	public double getTotalSum() {

		int totalSum = 0;
		
		Iterator<SoldItem> it = rows.iterator();
		
		while (it.hasNext()) {
			totalSum += it.next().getSum();
		}
		
		return totalSum;
	}
	
	public ArrayList<SoldItem> getAllRows() {
		
		ArrayList<SoldItem> shoppingCartRows = new ArrayList<SoldItem>();
		
		Iterator<SoldItem> it = rows.iterator();
		
		while (it.hasNext()) {
			shoppingCartRows.add(it.next());
		}
		
		return shoppingCartRows;
		
	}
	
}

