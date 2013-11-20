import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryItemTest {
	
	private List<SoldItem> soldItemListGood;
	private double soldItemListGoodPrice;
	private String dateTime = "2000.01.01 00:00:00";
	
	@Before
	public void setUp() {
		soldItemListGood = new ArrayList<SoldItem>();
		long[] ids = {1, 100, Long.MAX_VALUE};
		String[] names = {"First Element", "", "!@#$%^&*()_+=-1234567890][\'|/.,<>?"};
		String[] descriptions = {"First Element", "", "!@#$%^&*()_+=-1234567890][\'|/.,<>?"};
		double[] prices = {0.0, 1000.123456789, 9999999999999999.9};
		int[] quantities = {10, 2, 1};
		soldItemListGoodPrice = 0;
		
		for(int i = 0; i < 3; i++) {
			soldItemListGood.add(new SoldItem(new StockItem(ids[i], names[i], descriptions[i], prices[i]), quantities[i]));
			soldItemListGoodPrice += prices[i] * quantities[i];
		}
	}
	
	@Test
	public void testHistoryItemConstructorWithDateTimeAndOrderDetails() {;
		HistoryItem hi = new HistoryItem(dateTime, soldItemListGood);
		Iterator<SoldItem> it = hi.getOrderDetails().iterator();
		Iterator<SoldItem> itExp = soldItemListGood.iterator();
		while (it.hasNext()) {
			assertEquals(itExp.next(), it.next());
		}
	}
	
    @Test
    public void testGetOrderDetailsButton() {
    	HistoryItem hi = new HistoryItem();
    	assertTrue(hi.getOrderDetailsButton() instanceof JButton);
    	assertEquals(hi, hi.getOrderDetailsButton().getActionListeners()[0]);
    }
    
    @Test
    public void testGetOrderDetails() {
    	HistoryItem hi = new HistoryItem(dateTime, soldItemListGood);
    	assertEquals(soldItemListGood, hi.getOrderDetails());
    }
    
    @Test
    public void getDateTime() {
    	HistoryItem hi = new HistoryItem(dateTime, soldItemListGood);
    	assertTrue(dateTime.equals(hi.getDateTime()));
    }
    
    @Test
    public void testGetSumWithNoItems() {
		HistoryItem hi = new HistoryItem();
    	assertEquals(0.0, hi.getTotalPrice(), 0.0001);
    }

    @Test
    public void testGetSumWithOneItem() {
    	StockItem sti = new StockItem((long) 1,"Pellets","Something Pacman eats",333.5, 40);
    	SoldItem si = new SoldItem(sti,4);
    	List<SoldItem> order = Arrays.asList(si);
		HistoryItem hi = new HistoryItem("2013.11.17 22:22:22", order);
    	assertEquals(hi.getTotalPrice(),sti.getPrice()*si.getQuantity(), 0.0001);
    }
    
    @Test
    public void testGetSumWithMultipleItems() {
    	
    	StockItem sti1 = new StockItem((long) 1,"Blinky","Someone Pacman eats",10, 5);
    	StockItem sti2 = new StockItem((long) 2,"Pinky","Someone Pacman eats",20, 6);
    	StockItem sti3 = new StockItem((long) 3,"Inky","Someone Pacman eats",30, 7);
    	StockItem sti4 = new StockItem((long) 3,"Clyde","Someone Pacman eats",30, 7);
    	
    	SoldItem si1 = new SoldItem(sti1,4);
    	SoldItem si2 = new SoldItem(sti2,3);
    	SoldItem si3 = new SoldItem(sti3,2);  
    	SoldItem si4 = new SoldItem(sti4,1); 
    	
    	List<SoldItem> order = Arrays.asList(si1, si2, si3, si4);
		HistoryItem hi = new HistoryItem("2013.11.17 22:22:22", order);
		double result = sti1.getPrice()*si1.getQuantity()+sti2.getPrice()*si2.getQuantity()+sti3.getPrice()*si3.getQuantity()+sti4.getPrice()*si4.getQuantity();
    	
		assertEquals(hi.getTotalPrice(),result, 0.0001);
    }
    
    @Test
    public void testGetId() {
		HistoryItem hi = new HistoryItem();
    	assertTrue(hi.getId() == null);
    }
    
    @Test
    public void testGetName() {
    	HistoryItem hi = new HistoryItem(dateTime, soldItemListGood);
    	assertTrue(dateTime.equals(hi.getName()));
    }
    
    @Test
    public void testAddOrderDetail() {
    	HistoryItem hi = new HistoryItem(dateTime, soldItemListGood);
    	SoldItem si = new SoldItem(new StockItem(1L, "TEST", "TEST", 333.3, 3), 2);
    	hi.addOrderDetail(si);
    	assertEquals(si, hi.getOrderDetails().get(3));
    	assertEquals(soldItemListGoodPrice + si.getPrice() * 2, hi.getTotalPrice(), 0.00001);
    }
}
