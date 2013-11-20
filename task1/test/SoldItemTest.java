import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
	
	
	StockItem sti;
	SoldItem soi;
	
	@Before
	public void setUp() {
		sti = new StockItem(123456L, "TEST", "TEST DESC", 1234567.1234567, 10);
		soi = new SoldItem(sti, 3);
	}
	
	@Test
	public void testId() {
		assertEquals(sti.getId(), soi.getId());
		soi.setId(Long.MAX_VALUE);
		assertTrue(Long.MAX_VALUE == soi.getId());
		soi.setId(Long.MIN_VALUE);
		assertTrue(Long.MIN_VALUE == soi.getId());
		soi.setId(sti.getId());
	}
	
	@Test
	public void testSale() {
		assertTrue(soi.getSale() == null);
		HistoryItem hi = new HistoryItem();
		soi.setSale(hi);
		assertEquals(hi, soi.getSale());
		soi.setSale(null);
	}
	
	@Test
	public void testName() {
		assertEquals(sti.getName(), soi.getName());
		String name = "TEST 2 !@#$%^&*()]}|['{.,/?><`~1234567890-=qwertyuiopasdfghjkl;zxcvbnmQWERTYUIOPASDFGHJKL:ZXCVBNM\n\n";
		soi.setName(name);
		assertTrue(name.equals(soi.getName()));
		soi.setName(sti.getName());
	}
	
	@Test
	public void testPrice() {
		assertEquals(sti.getPrice(), soi.getPrice(), 0.0001);
		soi.setPrice(987654321.049999999999);
		assertEquals(987654321.049999999999, soi.getPrice(), 0.0001);
		soi.setPrice(sti.getPrice());
	}
	
	@Test
	public void testQuantity() {
		assertTrue(3 == soi.getQuantity());
		soi.setQuantity(Integer.MAX_VALUE);
		assertTrue(Integer.MAX_VALUE == soi.getQuantity());
		soi.setQuantity(3);
	}

    @Test
    public void testGetSum() {
    	assertEquals(Math.round(sti.getPrice() * 3 * 100.0) / 100.0, soi.getSum(), 0.0001);
    	soi.setQuantity(20);
    	assertEquals(Math.round(sti.getPrice() * 20 * 100.0) / 100.0, soi.getSum(), 0.0001);
    	soi.setPrice(65432.023456);
    	assertEquals(Math.round(65432.023456 * 20 * 100.0) / 100.0, soi.getSum(), 0.0001);
    	soi.setPrice(sti.getPrice());
    	soi.setQuantity(3);
    }
    
    
    @Test
    public void testGetSumWithZeroQuantity() {
     StockItem sti = new StockItem((long) 1," "," ",30);
     SoldItem si = new SoldItem(sti,0);
     double result = si.getSum();
     double expected = sti.getPrice()*(double)si.getQuantity();
     assertEquals(expected,result, 0.0001);
    }
}