import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest extends PurchaseInfoTableModel{
	
	private static final long serialVersionUID = -3355723967363152090L;
	
	StockItem sti = new StockItem((long) 1, "Name", "Desctiption", 12.34, 22);
	SoldItem si = new SoldItem(sti, 3);
	
	@Test
	public void testGetColumnValue(){			
		assertEquals(si.getId(),getColumnValue(si, 0));
	    assertEquals(si.getName(),getColumnValue(si, 1));
	    assertEquals(si.getPrice(),getColumnValue(si, 2));
	    assertEquals(si.getQuantity(),getColumnValue(si, 3));
	    assertEquals(si.getSum(),getColumnValue(si, 4));	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetColumnValueWithIllegalArgument() {			
    	getColumnValue(si, 9000);	
	}
	
	@Test
	public void testAddItem() {
		PurchaseInfoTableModel pitm = new PurchaseInfoTableModel();
		pitm.addItem(si);
		pitm.addItem(si);
		SoldItem si1 = pitm.getItemById(sti.getId());
		assertEquals(si,si1);		
	}
	
	@Test
	public void testGetTotalSum() {
		PurchaseInfoTableModel pitm = new PurchaseInfoTableModel();
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
			assertEquals(list.get(i).toString(),pitm.getItemById(i+1).toString());
		}
		
	}
}
