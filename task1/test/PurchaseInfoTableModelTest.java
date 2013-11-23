import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest {
	
		
	@Test
	public void testGetColumnValue(){	
	
	}
	
	@Test
	public void testGetColumnValueWithIllegalArgument() {	
		
	}
	
	@Test
	public void testAddItem() {
		PurchaseInfoTableModel pitm = new PurchaseInfoTableModel();
		StockItem sti = new StockItem((long) 1, "Name", "Desctiption", 12.34, 22);
		SoldItem si = new SoldItem(sti, 3);
		pitm.addItem(si);
		pitm.addItem(si);
		SoldItem si1 = pitm.getItemById(sti.getId());
		
		assertEquals(si.getId(),si1.getId());
	    assertEquals(si.getName(),si1.getName());
	    assertEquals(si.getQuantity(),si1.getQuantity());
	    assertEquals(si.getPrice(),si1.getPrice(), 0.0001);
		
	}
	
	@Test
	public void testGetTotalSum() {
		PurchaseInfoTableModel pitm = new PurchaseInfoTableModel();
		StockItem sti = new StockItem((long) 1, "Name", "Desctiption", 12.34, 22);
		SoldItem si = new SoldItem(sti, 3);
		pitm.addItem(si);
		pitm.addItem(si);
		assertEquals(pitm.getTotalSum(),sti.getPrice()*si.getQuantity(),0.0001);
		
	}
	
	@Test
	public void testGetAllrows() {
		PurchaseInfoTableModel pitm = new PurchaseInfoTableModel();
		StockItem sti = new StockItem((long) 1, "Name", "Desctiption", 12.34, 22);
		StockItem sti1 = new StockItem((long) 2, "Name1", "Desctiption1", 34.21, 25);
		StockItem sti2 = new StockItem((long) 3, "Name2", "Desctiption2", 55.55, 62);
		SoldItem si = new SoldItem(sti, 3);
		SoldItem si1 = new SoldItem(sti1, 3);
		SoldItem si2 = new SoldItem(sti2, 3);
		pitm.addItem(si);
		pitm.addItem(si1);
		pitm.addItem(si2);
		
		ArrayList<SoldItem> list = pitm.getAllRows();
		
		for(int i=0;i<list.size();i++){
			assertEquals(list.get(i).getId(),pitm.getItemById(i+1).getId());
		    assertEquals(list.get(i).getName(),pitm.getItemById(i+1).getName());
		    assertEquals(list.get(i).getQuantity(),pitm.getItemById(i+1).getQuantity());
		    assertEquals(list.get(i).getPrice(),pitm.getItemById(i+1).getPrice(), 0.0001);
		}
		
	}
}
