package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.PaymentWindow;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labeled "Point-of-sale" in the menu).
 */
public class PurchaseTab implements ActionListener {

	private static final Logger log = LogManager.getLogger(PurchaseTab.class);

	private JButton newPurchase;
	private JButton submitPurchase;
	private JButton cancelPurchase;

	private PurchaseItemPanel purchasePane;

	private SalesSystemModel salesSystemModel;
	private SalesDomainController domainController;
	
	private PaymentWindow paymentWindow;

	public PurchaseTab(SalesSystemModel model, SalesDomainController domainController)
	{
		this.salesSystemModel = model;
		this.domainController = domainController;
	}


	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog and
	 * shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(salesSystemModel);
		purchasePane.setEnabled(false);
		panel.add(purchasePane, getConstraintsForPurchasePanel());

		return panel;
	}

	// The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined above
		panel.add(newPurchase, gc);
		panel.add(submitPurchase, gc);
		panel.add(cancelPurchase, gc);

		return panel;
	}

	// Creates the button "New purchase"
	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});

		return b;
	}

	// Creates the "Confirm" button
	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}


	// Creates the "Cancel" button
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	/* === Event handlers for the menu buttons
	 *     (get executed when the buttons are clicked)
	 */


	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		try {
			domainController.startNewPurchase();
			startNewSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}


	/**  Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		try {
			domainController.cancelCurrentPurchase();
			
			// Restore warehouse state.
			PurchaseInfoTableModel modelPIT = salesSystemModel.getCurrentPurchaseTableModel();
			StockTableModel modelST = salesSystemModel.getWarehouseTableModel();
			Iterator<SoldItem> it = modelPIT.getTableRows().iterator();
			SoldItem currentSoldItem;
			StockItem currentStockItem;
			while (it.hasNext()) {
				currentSoldItem = it.next();
				currentStockItem = modelST.getItemById(currentSoldItem.getId());
				currentStockItem.setQuantity(currentStockItem.getQuantity() + currentSoldItem.getQuantity());
			}
			
			// Clear cart
			salesSystemModel.getCurrentPurchaseTableModel().clear();
			
			endSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}


	/** Event handler for the <code>submit purchase</code> event. */
	protected void submitPurchaseButtonClicked() {
		//contents empty
		if (salesSystemModel.getCurrentPurchaseTableModel().getTableRows().size() != 0) {
			log.debug("Contents of the current basket:\n" + salesSystemModel.getCurrentPurchaseTableModel());
			paymentWindow = new PaymentWindow(salesSystemModel.getCurrentPurchaseTableModel(), this);
		
			log.debug("Window open");
		}
		else {
			log.debug("Empty shopping cart!");
		}
	}


	/* === Helper methods that bring the whole purchase-tab to a certain state
	 *     when called.
	 */

	// switch UI to the state that allows to proceed with the purchase
	private void startNewSale() {
		purchasePane.reset();
		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}
	

	// switch UI to the state that allows to initiate new purchase
	public void endSale() {
		purchasePane.reset();

		cancelPurchase.setEnabled(false);
		submitPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}


	/* === Next methods just create the layout constraints objects that control the
	 *     the layout of different elements in the purchase tab. These definitions are
	 *     brought out here to separate contents from layout, and keep the methods
	 *     that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}


	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}


	// The constraints that control the layout of the buttons in the purchase menu
	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;

		return gc;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getID() == 0) {
			// Purchase accepted
			try {
				domainController.submitCurrentPurchase(salesSystemModel.getCurrentPurchaseTableModel().getTableRows());
				
				String dateTime = HistoryItem.timeDate();
				//add info to save
				List<SoldItem> shoppingCartRows = salesSystemModel.getCurrentPurchaseTableModel().getAllRows();
				salesSystemModel.getCurrentHistoryTableModel().addItem(new HistoryItem(dateTime, shoppingCartRows));
				
				
				salesSystemModel.getCurrentPurchaseTableModel().clear();
				endSale();
			} catch (VerificationFailedException e1) {
				// TODO Inform user that we cannot make a purchase
				e1.printStackTrace();
				
			}
			paymentWindow.close();
		} else if (e.getID() == 1) {
			// Purchase cancelled
			paymentWindow.close();
		}
		
	}
	
}
