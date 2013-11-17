import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryItemTest {


    @Test
    public void testAddHistotyItem() {
    }
    
    @Test
    public void testGetSumWithNoItems() {
		HistoryItem hi = new HistoryItem();
    	assertEquals(hi.getTotalPrice(),0.0, 0.0001);
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
}
