package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.AddProductWindow;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StockTab {
	private JButton addItem;
	private JButton deleteItem;
	private static final Logger log = LogManager.getLogger(StockTab.class);
	private SalesSystemModel model1;
	private SalesDomainController domainController;
	private int rowIndex;

	public StockTab(SalesSystemModel model, SalesDomainController domainController) {
		this.model1 = model;
		this.domainController = domainController;
	}

	/**
	 * 
	 * @param stockItem
	 */
	
	public void addItemToStock(StockItem stockItem) {
		try {
			domainController.addItemToWarehouse(stockItem);
			model1.getWarehouseTableModel().addItem(stockItem);
		} catch (VerificationFailedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
	
	public void changeRowIndex(int rowInd) {
		rowIndex = rowInd;
	}

	public void deleteItemFromStock(StockItem stockItem) {
		try {
			domainController.deleteItemFromWarehouse(stockItem);
			model1.getWarehouseTableModel().deleteItem(stockItem);
		} catch (VerificationFailedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
	
	public boolean nameExistsInStock(String name) {
		return model1.getWarehouseTableModel().nameExistsInStock(name);
	}
	
	// warehouse stock tab - consists of a menu and a table
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItem = new JButton("Add");
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{	
				addItemActionPerformed();
			}
		});
		
		deleteItem = new JButton("Delete");
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				deleteItemActionPerformed(rowIndex);
			}
		}); 

		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItem, gc);
		panel.add(deleteItem, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}


	// table of the wareshouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model1.getWarehouseTableModel());

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);
		
		table.addMouseListener(new JTableButtonMouseListenerStock(table, this));

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));

		return panel;
	}
	
	private void addItemActionPerformed() {
		log.debug("Add click.");
		new AddProductWindow(this);
	}
	
	private void deleteItemActionPerformed(int index) {
		log.debug("Delete click.");
		System.out.println(index);
		List<StockItem> items = model1.getWarehouseTableModel().getTableRows();
		try {
			domainController.deleteItemFromWarehouse(items.get(index));
		} catch (VerificationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model1.getWarehouseTableModel().deleteItem(items.get(index));
		
	}

}
