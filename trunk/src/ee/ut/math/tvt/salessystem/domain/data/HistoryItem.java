package ee.ut.math.tvt.salessystem.domain.data;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class HistoryItem implements Cloneable, DisplayableItem {
	
	private String date;
	private String time;
	private double totalPrice;

	public HistoryItem(PurchaseInfoTableModel modelPIT, String date, String time) {
		this.totalPrice = modelPIT.getTotalSum();
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
