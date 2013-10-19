package ee.ut.math.tvt.salessystem.domain.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class HistoryItem implements Cloneable, DisplayableItem {
	
	private String date;
	private String time;
	private double totalPrice;

	public HistoryItem(PurchaseInfoTableModel modelPIT, String date, String time) {
		this.totalPrice = modelPIT.getTotalSum();
		this.date = date;
		this.time = time;
		
		// TODO Auto-generated constructor stub
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public double getTotalPrice() {
		return totalPrice;
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
	
	public static String[] timeDate() {

		Date now = new Date();
		SimpleDateFormat upDate = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		String dateTime[] = upDate.format(now).split(" ");
		return dateTime;
	}

}
