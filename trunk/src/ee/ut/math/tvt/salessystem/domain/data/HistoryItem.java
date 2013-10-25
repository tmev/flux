package ee.ut.math.tvt.salessystem.domain.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.ui.AddProductWindow;
import ee.ut.math.tvt.salessystem.ui.HistoryPaymentDetailedWindow;
import ee.ut.math.tvt.salessystem.ui.model.HistoryPaymentDetailedWindowTableModel;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class HistoryItem implements Cloneable, DisplayableItem, ActionListener {
	
	private String date;
	private String time;
	private double totalPrice;
	private ArrayList<SoldItem> orderDetails;
	private JButton orderDetailsButton;
	//Order (history) model
    private HistoryPaymentDetailedWindowTableModel HistoryPaymentDetailedWindowTableModel;
	HistoryPaymentDetailedWindow orderWindow;
	private static final Logger log = LogManager.getLogger(PurchaseInfoTableModel.class);

	public HistoryItem(PurchaseInfoTableModel modelPIT, String date, String time, ArrayList<SoldItem> orderDetails) {
		this.totalPrice = modelPIT.getTotalSum();
		this.date = date;
		this.time = time;
		this.orderDetails = orderDetails;
		this.orderDetailsButton = new  JButton("Order details");
		
        HistoryPaymentDetailedWindowTableModel = new HistoryPaymentDetailedWindowTableModel();

		this.orderDetailsButton.addActionListener(this);
		
	}

	public JButton getOrderDetailsButton() {
		return orderDetailsButton;
	}

	public ArrayList<SoldItem> getOrderDetails() {
		return orderDetails;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		//create new order info window
		log.debug("New order info window created");
    	orderWindow = new HistoryPaymentDetailedWindow(HistoryPaymentDetailedWindowTableModel, orderDetails);
	}

}
