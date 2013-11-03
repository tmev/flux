package ee.ut.math.tvt.salessystem.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;

public class AddProductWindow {

	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(AddProductWindow.class);
	
	private StockTab stockTab;

	private JFrame frame;
	private JPanel panel;

	private JButton addButton;
	private JButton cancelButton;

	private JTextField barCodeField;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField descField;
	private JTextField quanityField;

	public AddProductWindow(StockTab stockTab) {
		this.stockTab = stockTab;
		createAddProductWindow();
	}

	public void createAddProductWindow() {
		frame = new JFrame("Add product");
		panel = new JPanel();

		panel.setLayout(new GridLayout(6, 2));
		
		panel.add(new JLabel("Bar code"));
		barCodeField = new JTextField();
		panel.add(barCodeField);

		panel.add(new JLabel("Name"));
		nameField = new JTextField();
		panel.add(nameField);

		panel.add(new JLabel("Description"));
		descField = new JTextField();
		panel.add(descField);

		panel.add(new JLabel("Price"));
		priceField = new JTextField();
		panel.add(priceField);

		panel.add(new JLabel("Quantity"));
		quanityField = new JTextField();
		panel.add(quanityField);

		addButton = new JButton("Add");

		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				addItemActionPerformed();
			}

		});

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		panel.add(addButton);
		panel.add(cancelButton);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.setSize(280, 180);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public void close() {
		frame.dispose();
	}

	public void addItemActionPerformed() {
		String name = nameField.getText().trim();
		if(name.length() == 0) {
			JOptionPane.showMessageDialog(null, "Name cannot be empty!");
			return;
		}
		
		if (stockTab.nameExistsInStock(name)) {
			JOptionPane.showMessageDialog(null, "Item with this name already in stock!");
			return;
		}

		if(!priceField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
			JOptionPane.showMessageDialog(null, "Price should be a number!");
			return;
		}
				
		if(!quanityField.getText().matches("[1-9]+[0-9]*")) {
			JOptionPane.showMessageDialog(null, "Quantity should be an integer!");
			return;
		}

		String description = descField.getText().trim();
		double price = Double.parseDouble(priceField.getText().trim());
		int quantity = Integer.parseInt(quanityField.getText().trim());
		long barCode;
		StockItem stockItem;
		try {
			barCode = Long.parseLong(barCodeField.getText().trim());
			stockItem = new StockItem(barCode, name, description, price, quantity);
		} catch(NumberFormatException e) {
			stockItem = new StockItem(null, name, description, price, quantity);
		}
		
		stockTab.addItemToStock(stockItem);
		
		close();
	}
}
