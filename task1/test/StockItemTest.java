import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemTest {


    @Test
	public void testClone() {
		StockItem sti = new StockItem((long) 1, "TEST NAME", "DESCRIPTION", 30);
		StockItem stiClone = (StockItem) sti.clone();
		assertTrue(sti.getName().equals(stiClone.getName()));
		assertTrue(sti.getDescription().equals(stiClone.getDescription()));
		assertEquals(sti.getPrice(), stiClone.getPrice(), 0.0001);
		assertEquals(sti.getId(), stiClone.getId());
		assertEquals(sti.getQuantity(), stiClone.getQuantity());
		
		sti.setName("STI");
		stiClone.setName("CLONE");
		assertTrue("STI".equals(sti.getName()));
		assertTrue("CLONE".equals(stiClone.getName()));
		
		sti.setDescription("STI");
		stiClone.setDescription("CLONE");
		assertTrue("STI".equals(sti.getDescription()));
		assertTrue("CLONE".equals(stiClone.getDescription()));
		
		sti.setPrice(1234.56);
		stiClone.setPrice(12.34);
		assertEquals(1234.56, sti.getPrice(), 0.0001);
		assertEquals(12.34, stiClone.getPrice(), 0.0001);
		
		sti.setId(1234L);
		stiClone.setId(5678L);
		assertTrue(1234L == sti.getId());
		assertTrue(5678L == stiClone.getId());
		
		sti.setQuantity(1234);
		stiClone.setQuantity(5678);
		assertTrue(1234 == sti.getQuantity());
		assertTrue(5678 == stiClone.getQuantity());
	}
    
    @Test
    public void testGetColumn() {
    	StockItem sti = new StockItem((long) 1,"Waka","Something Pacman says",333.5,30);
    	assertEquals(sti.getColumn(0),sti.getId());
    	assertEquals(sti.getColumn(1),sti.getName());
    	assertEquals(sti.getColumn(2),sti.getPrice());
    	assertEquals(sti.getColumn(3),sti.getQuantity());
    }
    
    @Test
    public void testToString() {
    	StockItem sti = new StockItem(1234L,"Waka","Something Pacman says",333.5,30);
    	assertTrue("1234 Waka Something Pacman says 333.5".equals(sti.toString()));
    }

}