package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class AddProduct {

	private static final Logger log = LogManager.getLogger(AddProduct.class);

	private JFrame frame;
	private JPanel panel;
	private JTextField idField;
	private JButton addButton;
	private JButton cancelButton;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField descField;
	public StockItem stockItem;
	private JTextField quanityField;
	private StockTableModel stockTableModel;
	private Long id;
	private String name;
	private double price;
	private String description;
	private int quantity;

	public AddProduct(StockTableModel stockTableModel) {
		this.stockTableModel = stockTableModel;
		createAddProductWindow();
	}

	public void createAddProductWindow() {
		log.debug("Started");
		stockItem = new StockItem();

		frame = new JFrame("Add product");
		panel = new JPanel();

		panel.setLayout(new GridLayout(6, 2));

		// Add sum label and field

		panel.add(new JLabel("Id"));

		idField = new JTextField();
		panel.add(idField);
		idField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!idField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
					addButton.setEnabled(false);
					return;
				}
				id = Long.parseLong(idField.getText());
				stockItem.setId(id);
				addButton.setEnabled(true);

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!idField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
					addButton.setEnabled(false);
					return;
				}
				id = Long.parseLong(idField.getText());
				stockItem.setId(id);
				addButton.setEnabled(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		});

		panel.add(new JLabel("Name"));

		nameField = new JTextField();
		panel.add(nameField);
		nameField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (nameField.getText() != null
						&& !nameField.getText().isEmpty()) {
					name = nameField.getText();
					stockItem.setName(name);
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (nameField.getText() != null
						&& !nameField.getText().isEmpty()) {
					name = nameField.getText();
					stockItem.setName(name);
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		});

		panel.add(new JLabel("Description"));

		descField = new JTextField();
		panel.add(descField);
		descField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (descField.getText() != null
						&& !nameField.getText().isEmpty()) {
					description = descField.getText();
					stockItem.setDescription(description);
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (descField.getText() != null
						&& !descField.getText().isEmpty()) {
					description = descField.getText();
					stockItem.setDescription(description);
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		});

		panel.add(new JLabel("Price"));

		priceField = new JTextField();
		panel.add(priceField);
		priceField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!priceField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
					addButton.setEnabled(false);
					return;
				}
				price = Double.parseDouble(priceField.getText());
				stockItem.setPrice(price);
				addButton.setEnabled(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!priceField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
					addButton.setEnabled(false);
					return;
				}
				price = Double.parseDouble(priceField.getText());
				stockItem.setPrice(price);
				addButton.setEnabled(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		});

		panel.add(new JLabel("Quanity"));

		quanityField = new JTextField();
		panel.add(quanityField);
		quanityField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!quanityField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
					addButton.setEnabled(false);
					return;
				}
				quantity = Integer.parseInt(quanityField.getText());
				stockItem.setQuantity(quantity);
				addButton.setEnabled(true);

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!quanityField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
					return;
				}
				quantity = Integer.parseInt(quanityField.getText());
				stockItem.setQuantity(quantity);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		});
		addButton = new JButton("Add");
		addButton.setEnabled(false);

		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (stockItem != null) {
					stockTableModel.addItem(stockItem);
				}
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
		frame.setSize(100, 200);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public void close() {
		frame.dispose();
	}

}
