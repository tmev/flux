
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
		SalesSystemTableModel sstm = stm;		
		assertEquals((StockItem) sstm.getItemById(sti.getId()),sti);
		
	}
	
	@Test
	public void testGetItemByName() {
		StockTableModel stm = new StockTableModel();
		stm.addItem(sti);
		stm.addItem(sti2);
		SalesSystemTableModel sstm = stm;				
		assertEquals((StockItem) sstm.getItemByName(sti.getName()),sti);
		
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testGetItemByNonExistingId() {	
		StockTableModel stm = new StockTableModel();		
		SalesSystemTableModel sstm = stm;
   		sstm.getItemById(2);	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testgetItemByNonExistingName() {
		StockTableModel stm = new StockTableModel();
		SalesSystemTableModel sstm = stm;
   		sstm.getItemByName("WakaWaka");
		
	}
	@Test(expected = NoSuchElementException.class)
	public void testClear() {
		StockTableModel stm = new StockTableModel();
		stm.addItem(sti);
		stm.addItem(sti2);
		
		SalesSystemTableModel sstm = stm;		
		sstm.clear();
   		sstm.getItemByName(sti.getName());				
	}
}
