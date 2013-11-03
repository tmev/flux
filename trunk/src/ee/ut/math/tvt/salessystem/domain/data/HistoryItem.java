package ee.ut.math.tvt.salessystem.domain.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.JButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.ui.HistoryPaymentDetailedWindow;
import ee.ut.math.tvt.salessystem.ui.model.HistoryPaymentDetailedWindowTableModel;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements Cloneable, DisplayableItem, ActionListener {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "datetime")
	private String dateTime;
	
	@Column(name = "total_price")
	private double totalPrice;

	@OneToMany(mappedBy = "sale")
	private List<SoldItem> orderDetails;
	
	//Order (history) model
	@Transient
    private HistoryPaymentDetailedWindowTableModel historyPaymentDetailedWindowTableModel;
	
	@Transient
	HistoryPaymentDetailedWindow orderWindow;
	
	@Transient
	private static final Logger log = LogManager.getLogger(PurchaseInfoTableModel.class);

	public HistoryItem(String dateTime, List<SoldItem> orderDetails) {
		this.dateTime = dateTime;
		this.orderDetails = orderDetails;
		this.totalPrice = getTotalSum();
        historyPaymentDetailedWindowTableModel = new HistoryPaymentDetailedWindowTableModel();
	}
	
	public HistoryItem(String dateTime) {
		this.dateTime = dateTime;
		this.totalPrice = getTotalSum();
		this.orderDetails = new ArrayList<SoldItem>();
        historyPaymentDetailedWindowTableModel = new HistoryPaymentDetailedWindowTableModel();
	}
	
	public HistoryItem() {
		this(timeDate());
	}

	public JButton getOrderDetailsButton() {
		JButton orderDetailsButton = new  JButton("Order details");
		orderDetailsButton.addActionListener(this);
		return orderDetailsButton;
	}

	
	public List<SoldItem> getOrderDetails() {
		return orderDetails;
	}


	public String getDateTime() {
		return dateTime;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return getDateTime();
	}
	
	public static String timeDate() {

		Date now = new Date();
		SimpleDateFormat upDate = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		String dateTime = upDate.format(now);
		return dateTime;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//create new order info window
		log.debug("New order info window created");
    	orderWindow = new HistoryPaymentDetailedWindow(historyPaymentDetailedWindowTableModel, getOrderDetails());
	}
	
	public void addOrderDetail(SoldItem soldItem) {
		soldItem.setSale(this);
		orderDetails.add(soldItem);
	}
	
	private double getTotalSum() {
		
		if (getOrderDetails() == null) {
			return 0;
		}
		
		double totalSum = 0;
		
		Iterator<SoldItem> it = getOrderDetails().iterator();
		
		while (it.hasNext()) {
			totalSum += it.next().getSum();
		}
		
		return totalSum;
	}

}
