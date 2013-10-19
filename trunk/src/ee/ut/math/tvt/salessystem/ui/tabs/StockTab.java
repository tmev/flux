package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StockTab {
	StockTableModel stockTableModel;
	private JButton addItem;
	private static final Logger log = LogManager.getLogger(StockTab.class);
	private SalesSystemModel model1;

	public StockTab(SalesSystemModel model) {
		this.model1 = model;
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
				log.debug("Add click.");
				new AddProduct(model1.getWarehouseTableModel());

			}
		});    

		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItem, gc);

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

}
