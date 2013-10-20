package ee.ut.math.tvt.salessystem.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class AddProductWindow {

	@SuppressWarnings("unused")
	private static final Logger log = LogManager
			.getLogger(AddProductWindow.class);

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
	private JTextField infoField;
	private StockTableModel stockTableModel;
	private boolean checkDone;
	private Long id;
	private String name;
	private double price;
	private String description;
	private int quantity;

	public AddProductWindow(StockTableModel stockTableModel) {
		this.stockTableModel = stockTableModel;
		createAddProductWindow();

	}

	public void createAddProductWindow() {
		stockItem = new StockItem();
		frame = new JFrame("Add product");
		panel = new JPanel();

		panel.setLayout(new GridLayout(7, 2));

		// Add sum label and field

		panel.add(new JLabel("Id"));

		idField = new JTextField();
		panel.add(idField);
		
		panel.add(new JLabel("Name"));

		nameField = new JTextField();
		panel.add(nameField);

		panel.add(new JLabel("Description"));

		descField = new JTextField();
		panel.add(descField);

		panel.add(new JLabel("Price"));

		priceField = new JTextField();
		panel.add(priceField);

		panel.add(new JLabel("Quanity"));

		quanityField = new JTextField();
		panel.add(quanityField);

		panel.add(new JLabel("Information"));

		infoField = new JTextField();
		infoField.setEditable(false);
		infoField.setText("----");
		panel.add(infoField);

		addButton = new JButton("Check");

		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				check();
				if (infoField.getText().equals("OK")) {
					stockTableModel.addItem(stockItem);

				}
				if (checkDone) {
					infoField.setText("OK");
					addButton.setText("Add");
					cancelButton.setText("Change");
				}

			}

		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkDone) {
					JTextField[] fields = { idField, nameField, descField,
							priceField, quanityField };
					for (JTextField field : fields) {
						field.setEditable(true);
					}
					infoField.setText("----");
					cancelButton.setText("Cancel");
					addButton.setText("Check");
					checkDone = false;
				} else {
					close();
				}
			}
		});
		panel.add(addButton);
		panel.add(cancelButton);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.setSize(280, 200);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public void close() {
		frame.dispose();
	}

	public void check() {
		int count = 0;
		JTextField[] fields = { idField, nameField, descField, priceField,
				quanityField };
		String[] namez = { "Id", "Name", "Description", "Price",
				"Quanity" };
		int[] numCheck = {0,3, 4 };
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].getText().isEmpty()
					&& !fields[i].getText().trim().isEmpty()) {
				count++;
			} else {
				infoField.setText((namez[i] + " is empty"));
				count = 0;
				return;
			}
			if (count == 5) {
				name = nameField.getText().trim();
				description = descField.getText().trim();
				for (int j : numCheck) {
					if (!fields[j].getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
						infoField.setText(namez[j] + " not numeric");
						count = 0;
						return;
					}
				}
				if (!idField.getText().matches("[\\d]")){
					infoField.setText("Wrong id");
					count = 0;
					return;
				}
				id = Long.parseLong(idField.getText().trim());
				price = Double.parseDouble(priceField.getText().trim());
				quantity = Integer.parseInt(quanityField.getText().trim());

				stockItem.setId(id);
				stockItem.setName(name);
				stockItem.setDescription(description);
				stockItem.setPrice(price);
				stockItem.setQuantity(quantity);

				for (JTextField field : fields) {
					field.setText(field.getText().trim());
					field.setEditable(false);
				}
				checkDone = true;
			}
		}
	}
}
