
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class SalesSystemTableModelTest {
	
	private StockItem sti = new StockItem((long)1, "X", "Y", 5.16);
	private StockItem sti2 = new StockItem((long)2, "Z", "W", 2.44);

	
	@Test
	public void testGetItemById(){	
		
		StockTableModel stm = new StockTableModel();
		stm.addItem(sti);
		stm.addItem(sti2);
		
		SalesSystemTableModel sstm = stm;		
		StockItem st3 = (StockItem) sstm.getItemById(sti.getId());
		
		assertEquals(st3.getColumn(0),sti.getId());
    	assertEquals(st3.getColumn(1),sti.getName());
    	assertEquals(st3.getColumn(2),sti.getPrice());
    	assertEquals(st3.getColumn(3),sti.getQuantity());
		
	}
	
	@Test
	public void testGetItemByName() {
		
		StockTableModel stm = new StockTableModel();
		stm.addItem(sti);
		stm.addItem(sti2);
		
		SalesSystemTableModel sstm = stm;		
		StockItem st3 = (StockItem) sstm.getItemByName(sti.getName());
		
		assertEquals(st3.getColumn(0),sti.getId());
    	assertEquals(st3.getColumn(1),sti.getName());
    	assertEquals(st3.getColumn(2),sti.getPrice());
    	assertEquals(st3.getColumn(3),sti.getQuantity());
		
	}
	
	@Test
	public void testGetItemByNonExistingId() {
		
		StockTableModel stm = new StockTableModel();		
		SalesSystemTableModel sstm = stm;
				   	
    	Throwable caught = null;
    	try {
    		sstm.getItemById(2);
    	} catch (Throwable t) {
    	   caught = t;
    	}
    	assertNotNull(caught);
    	assertSame(NoSuchElementException.class, caught.getClass());
		
	}
	
	@Test
	public void testgetItemByNonExistingName() {
		
		StockTableModel stm = new StockTableModel();
		SalesSystemTableModel sstm = stm;
				   	
    	Throwable caught = null;
    	try {
    		sstm.getItemByName("WakaWaka");
    	} catch (Throwable t) {
    	   caught = t;
    	}
    	assertNotNull(caught);
    	assertSame(NoSuchElementException.class, caught.getClass());
		
	}
	@Test
	public void testClear() {
		
		StockTableModel stm = new StockTableModel();
		stm.addItem(sti);
		stm.addItem(sti2);
		
		SalesSystemTableModel sstm = stm;		
		sstm.clear();
		
		Throwable caught = null;
    	try {
    		sstm.getItemByName(sti.getName());
    	} catch (Throwable t) {
    	   caught = t;
    	}
    	assertNotNull(caught);
    	assertSame(NoSuchElementException.class, caught.getClass());
		
		
		
	}
}
