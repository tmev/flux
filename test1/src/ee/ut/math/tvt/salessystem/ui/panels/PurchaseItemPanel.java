package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;


/**
 * Purchase pane + shopping cart table UI.
 */
public class PurchaseItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LogManager.getLogger(PurchaseItemPanel.class.getCanonicalName());

	// Text field on the dialogPane
	private JTextField barCodeField;
	private JTextField quantityField;
	private JTextField nameField;
	private JTextField priceField;
	
	
	private JButton addItemButton;
	private JComboBox<String> availableItems;
	
	private SalesSystemModel salesSystemModel;
	private List<StockItem> productsInStock;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param salesSystemModel
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel salesSystemModel) {
		this.salesSystemModel = salesSystemModel;
		productsInStock = salesSystemModel.getWarehouseTableModel().getTableRows();

		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());

		setEnabled(false);
	}

	// shopping cart pane
	private JComponent drawBasketPane() {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(salesSystemModel.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBasketScrollPaneConstraints());

		return basketPane;
	}
	
	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Create item list
		availableItems = new JComboBox<String>();
		availableItems.addItem("-- select item --");
		updateAvailableItems();

		// Initialize the textfields
		barCodeField = new JTextField();	//private static final Logger log = LogManager.getLogger(PurchaseItemPanel.class);
		quantityField = new JTextField("1");
		nameField = new JTextField();
		priceField = new JTextField();

		// Fill the dialog fields if the bar code text field loses focus
		availableItems.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				
			}
		});

		nameField.setEditable(false);
		priceField.setEditable(false);

		// == Add components to the panel

		// - bar code
		panel.add(new JLabel("Bar code:"), getProductGenericConstraints(0, 0));
		panel.add(barCodeField, getProductGenericConstraints(1, 0));

		// - amount
		panel.add(new JLabel("Amount:"), getProductGenericConstraints(0, 1));
		panel.add(quantityField, getProductGenericConstraints(1, 1));

		// - name
		panel.add(new JLabel("Name:"), getProductGenericConstraints(0, 2));

		availableItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choise = (String) availableItems.getSelectedItem();
				fillDialogFields(choise);
			}
		});
		panel.add(availableItems, getProductGenericConstraints(1, 2));

		//panel.add(nameField);

		// - price
		panel.add(new JLabel("Price:"), getProductGenericConstraints(0, 3));
		panel.add(priceField, getProductGenericConstraints(1, 3));

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});
		panel.add(addItemButton, getProductGenericConstraints(0, 4, 2, 1));

		clear();
		return panel;
	}
	
	// Update choiseField 
	private void updateAvailableItems() {
		log.debug("Updating list of available items (JComboBox).");
		StockItem currentItem;
		availableItems.removeAllItems();

		// Add default value.
		availableItems.addItem("-- select item --");

		// Add items that do not exist.
		Iterator<StockItem> it = productsInStock.iterator();
		while(it.hasNext()){
			currentItem = it.next();
			if (currentItem.getQuantity() > 0) {
				availableItems.addItem(currentItem.getName());
			}
		}
	}

	// Fill dialog with data from the "database".
	public void fillDialogFields(StockItem stockItem) {
		if (stockItem == null) {
			clear();
			return;
		}
		barCodeField.setText(String.valueOf(stockItem.getId()));
		nameField.setText(stockItem.getName());
		priceField.setText(String.valueOf(stockItem.getPrice()));
		quantityField.setText("1");
	}
	
	public void fillDialogFields(int barcode) {
		StockItem stockItem = getStockItemByBarcode(barcode);
		fillDialogFields(stockItem);
	}
	
	public void fillDialogFields(String name) {
		StockItem stockItem = getStockItemByName(name);
		fillDialogFields(stockItem);
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	
	private StockItem getStockItemByBarcode(int code) {
		try {
			return salesSystemModel.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	// Search the warehouse for a StockItem with the name entered.
	
	private StockItem getStockItemByName(String name) {
		try {
			return salesSystemModel.getWarehouseTableModel().getItemByName(name);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = getStockItemByName(nameField.getText());
		
		if (stockItem != null) {
			int quantity;
			int stockSize;		

			stockSize = stockItem.getQuantity();

			try {
				quantity = Integer.parseInt(quantityField.getText());
				if (quantity <= 0) {
					throw new NumberFormatException("Quantity must be positive.");
				}
			} catch (NumberFormatException ex) {
				log.debug("NumberFormatException: " + ex.getMessage());
				JOptionPane.showMessageDialog(this, "Quantity must be positive integer");
				quantityField.setText("1");
				return;
			}
			if(stockSize-quantity<0){
				JOptionPane.showMessageDialog(this, "Not enough " + stockItem.getName() +" in the stock");
				
			} else {
				salesSystemModel.getCurrentPurchaseTableModel().addItem(new SoldItem(stockItem, quantity));
				stockItem.setQuantity(stockItem.getQuantity()-quantity);
				if (stockSize-quantity == 0) {
					availableItems.removeItem(availableItems.getSelectedItem());
					availableItems.setSelectedIndex(0);
					reset();
				}
			}
			stockItem = null;
		}
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		addItemButton.setEnabled(enabled);
		barCodeField.setEnabled(false);
		quantityField.setEnabled(enabled);
		availableItems.setEnabled(enabled);
	}
	
	// Clear all product fields.
	private void clear() {
		barCodeField.setText("----");
		quantityField.setText("----");
		nameField.setText("----");
		priceField.setText("----");
	}

	/**
	 * Reset dialog fields and reload list of available items.
	 */
	public void reset() {
		log.debug("Reset called.");
		clear();
		updateAvailableItems();
		availableItems.setSelectedIndex(0);
	}

	/*
	 * === Ideally, UI's layout and behavior should be kept as separated as
	 * possible. If you work on the behavior of the application, you don't want
	 * the layout details to get on your way all the time, and vice versa. This
	 * separation leads to cleaner, more readable and better maintainable code.
	 * 
	 * In a Swing application, the layout is also defined as Java code and this
	 * separation is more difficult to make. One thing that can still be done is
	 * moving the layout-defining code out into separate methods, leaving the
	 * more important methods unburdened of the messy layout code. This is done
	 * in the following methods.
	 */

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBasketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}
	
	private GridBagConstraints getProductGenericConstraints(int x, int y, int colspan, int rowspan) {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = colspan;
		gc.gridheight = rowspan;
		gc.ipadx = 2;
		gc.ipady = 2;
		gc.insets = new Insets(1, 1, 1, 1);

		return gc;
	}
	
	private GridBagConstraints getProductGenericConstraints(int x, int y) {
		return getProductGenericConstraints(x, y, 1, 1);
	}

}
