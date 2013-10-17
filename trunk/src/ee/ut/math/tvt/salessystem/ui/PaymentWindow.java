package ee.ut.math.tvt.salessystem.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PaymentWindow {

	private static final Logger log = LogManager.getLogger(PaymentWindow.class);

	private PurchaseInfoTableModel modelPIT;
	private double totalSum;
	private ActionListener parentAL;

	private double payment;

	private JFrame frame;
	private JPanel panel;
	private JTextField sumField;
	private JTextField paymentField;
	private JTextField changeField;
	private JButton acceptButton;
	private JButton cancelButton;

	// Fields update function
	private void update() {
		if (!paymentField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
			changeField.setText("Type error");
			acceptButton.setEnabled(false);
			return;
		}

		payment = Double.parseDouble(paymentField.getText());

		if ((payment - totalSum) >= 0) {
			DecimalFormat df=new DecimalFormat("0.00");
			df.format(payment);
			df.format(totalSum);
			changeField.setText(String.valueOf(df.format(payment - totalSum)));
			acceptButton.setEnabled(true);
		} else {
			changeField.setText("----");
			acceptButton.setEnabled(false);
		}
	}

	private void accept() {
		log.debug("Accept button pressed. Waiting for parent action.");
		update();
		parentAL.actionPerformed(new ActionEvent(this, 0, "Accept purchase"));
	}

	private void cancel() {
		log.debug("Cancel button pressed. Waiting for parent action.");
		update();
		parentAL.actionPerformed(new ActionEvent(this, 1, "Cancel purchase"));
	}

	public PaymentWindow(PurchaseInfoTableModel modelPIT, ActionListener parentAL) {
		this.modelPIT = modelPIT;
		this.parentAL = parentAL;
		createPaymentWindow();
	}

	public void createPaymentWindow() {

		totalSum = modelPIT.getTotalSum();

		frame = new JFrame("Payment");
		panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));

		// Add sum label and field

		panel.add(new JLabel("Total sum:"));

		sumField = new JTextField(String.valueOf(totalSum));
		sumField.setEditable(false);
		panel.add(sumField);

		// Add payment label and field

		panel.add(new JLabel("Payment amount:"));

		paymentField = new JTextField();
		panel.add(paymentField);

		// Add change label and field

		panel.add(new JLabel("Change amount:"));

		changeField = new JTextField();
		changeField.setEditable(false);
		panel.add(changeField);

		// Add accept button

		acceptButton = new JButton("Accept");
		acceptButton.setEnabled(false);
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accept();
			}
		});
		panel.add(acceptButton);

		// Add cancel button

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		panel.add(cancelButton);

		// This able to see live calculation of change amount
		paymentField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

		});

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
