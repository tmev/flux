package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.flux.PaymentWindow;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

	private static final Logger log = LogManager.getLogger(SalesDomainControllerImpl.class);
    private static double totalSum;
	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is
		// underaged and
		// cannot buy chupa-chups

		//throw new VerificationFailedException("Underaged!");

		// XXX - Save purchase
		List<SoldItem> saveList = new ArrayList<SoldItem>();

		//debug

		for(int i = 0;i<goods.size();i++){
			log.debug("I will save: " + goods.get(i).getName());
		}

		//Saving

		for(int i = 0;i<goods.size();i++){
			saveList.add(goods.get(i));
		}


		//debug  + sum
		totalSum = 0;
		for(int i = 0;i<saveList.size();i++){
			log.debug("Saved  list " + (i+1) + " component is " + saveList.get(i).getName());
			totalSum += saveList.get(i).getSum(); 

		}
		log.debug("Sum is " +  totalSum);
		setTotalSum(totalSum);
		PaymentWindow.createPaymentWindow();
		
	}
	
	public static double getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(double totalSum) {
		log.debug("Set total sum is "+ totalSum);
		SalesDomainControllerImpl.totalSum = totalSum;
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		List<StockItem> dataset = new ArrayList<StockItem>();

		StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0,
				5);
		StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0,
				8);
		StockItem frankfurters = new StockItem(3l, "Frankfurters",
				"Beer sauseges", 15.0, 12);
		StockItem beer = new StockItem(4l, "Free Beer", "Student's delight",
				0.0, 100);

		dataset.add(chips);
		dataset.add(chupaChups);
		dataset.add(frankfurters);
		dataset.add(beer);
		return dataset;
	}
}
