package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	private Session session = HibernateUtil.currentSession();

	private static final Logger log = LogManager.getLogger(SalesDomainControllerImpl.class);
	
	public void endSession() {
	    HibernateUtil.closeSession();
	}
	
	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		//throw new VerificationFailedException("Underaged!");

		// XXX - Save purchase
		log.info("Saving purchase");
		
		Iterator<SoldItem> it = goods.iterator();
		SoldItem item;
		session.beginTransaction();
		
		try {
			HistoryItem newHistoryItem = new HistoryItem(HistoryItem.timeDate(), goods);
			session.save(newHistoryItem);
			
			while(it.hasNext()) {
				item = it.next();
				item.setSale(newHistoryItem);
				session.update(item.getStockItem());
				session.save(item);
			}
			
			session.getTransaction().commit();
			
		} catch(Throwable e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new VerificationFailedException("DB Failure!");
		}
		
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	@SuppressWarnings("unchecked")
	public List<StockItem> loadWarehouseState() {
		return (List<StockItem>)(session.createQuery("from StockItem").list());
	}
	
	@SuppressWarnings("unchecked")
	public List<HistoryItem> loadHistoryState() {
		session.createQuery("from SoldItem").list();
		return (List<HistoryItem>)(session.createQuery("from HistoryItem").list());
	}

	@Override
	public void addItemToWarehouse(StockItem item)
			throws VerificationFailedException {
		session.beginTransaction();
		try {
			session.save(item);
			session.getTransaction().commit();
		} catch(Throwable e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new VerificationFailedException("Cannot add new item to database!");
		}
	}
}