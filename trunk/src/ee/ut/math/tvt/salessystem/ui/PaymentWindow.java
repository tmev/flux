package ee.ut.math.tvt.salessystem.ui;

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

import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;



public class PaymentWindow {

	PurchaseTab purchaseTab;

	private static final Logger log = LogManager.getLogger(PaymentWindow.class);

	public PaymentWindow(PurchaseTab purchaseTab) {
		this.purchaseTab = purchaseTab;
		createPaymentWindow();
	}

	//Fields update function
	private void update(JTextField paymentField, JTextField changeField, JButton button) {
		try{
			System.out.println();
			if (!paymentField.getText().matches("[\\d]+(?:\\.[\\d]*)?")) {
				changeField.setText("Type error");
				button.setEnabled(false);
				return;
			}
			double payment =  Double.parseDouble(paymentField.getText());
			double sum = purchaseTab.getTotalSum();
			if((payment-sum)>=0){
				changeField.setText(String.valueOf(payment-sum));
				button.setEnabled(true);
			}
			else{
				changeField.setText("----");
				button.setEnabled(false);
			}
		}
		catch(NumberFormatException ee){
			button.setEnabled(false);
			changeField.setText("Type error");		
		}
	}

	public void createPaymentWindow() {


		final JFrame frame = new JFrame("Payment");	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));

		//Add sum label and field

		panel.add(new JLabel("Total sum:"));

		final String sumText = String.valueOf(purchaseTab.getTotalSum());
		JTextField sumField = new JTextField(sumText);
		sumField.setEditable(false);
		panel.add(sumField);

		//Add payment label and field

		panel.add(new JLabel("Payment amount:"));

		final JTextField paymentField = new JTextField();
		panel.add(paymentField);

		//Add change label and field

		panel.add(new JLabel("Change amount:"));

		final JTextField changeField = new JTextField();
		changeField.setEditable(false);
		panel.add(changeField);


		//Add accept button

		final JButton acceptButton = new JButton("Accept");	
		acceptButton.setEnabled(false);
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				log.debug("Accept click.");
				purchaseTab.accept();
			}
		});    
		panel.add(acceptButton);

		//Add cancel button

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				update(paymentField, changeField,acceptButton);
				frame.dispose();
			}
		});    
		panel.add(cancelButton);

		//This able to see live calculation of change amount
		paymentField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {	
				update(paymentField, changeField,acceptButton);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {		
				update(paymentField, changeField,acceptButton);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update(paymentField, changeField,acceptButton);
			}

		});

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.setSize(100, 200);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

}
