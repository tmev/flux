import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {

	private StockTableModel stm;
	private StockItem sti;

	@Before
	public void setUp() {
		this.stm = new StockTableModel();
		this.sti = new StockItem((long) 3, "Bubblegum", "Pink", 3.12, 10);
		this.stm.addItem(this.sti);
	}

	@Test
	public void testValidateNameUniqueness() {
		assertFalse(this.stm.nameExistsInStock("ThisItemShouldNotExist"));
		assertTrue(this.stm.nameExistsInStock(this.sti.getName()));
	}

	@Test
	public void testHasEnoughInStock() {
		int stockSize = this.sti.getQuantity();
		assertTrue(StockTableModel.hasEnoughInStock(stockSize, 10));
		assertFalse(StockTableModel.hasEnoughInStock(stockSize, 11));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testHasEnoughInStockWithNegativeStockSize() {
		StockTableModel.hasEnoughInStock(-1, 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testHasEnoughInStockWithNegativeQuantity() {
		StockTableModel.hasEnoughInStock(10, -1);
	}

	@Test
	public void testGetItemByIdWhenItemExists() {
		assertEquals(this.sti, this.stm.getItemById(3));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		this.stm.getItemById(13);
	}

	@Test
	public void testGetItemByNameWhenItemExists() {
		assertEquals(this.sti, this.stm.getItemByName(this.sti.getName()));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetItemByNameWhenThrowsException() {
		this.stm.getItemByName("ThisItemShouldNotExist");
	}
	
	@Test
    public void testAddItem(){
		StockTableModel _stm = new StockTableModel();
		StockItem _sti = new StockItem(3L, "Bubblegum", "Pink", 3.12, 10);
		StockItem _sti2 = new StockItem(3L, "The Same Bubblegum", "Pink", 3.12, 12);
		_stm.addItem(_sti);
		assertEquals(_sti, _stm.getItemById(3L));
		assertEquals(_sti.getQuantity(), _stm.getItemById(3L).getQuantity());
		_stm.addItem(_sti2);
		assertEquals(_sti, _stm.getItemById(3L));
		assertEquals(22, _stm.getItemById(3L).getQuantity());
    }
	
	@Test(expected = NoSuchElementException.class)
    public void testDeleteItem(){
		StockTableModel _stm = new StockTableModel();
		StockItem _sti = new StockItem(3L, "Bubblegum", "Pink", 3.12, 10);
		_stm.addItem(_sti);
		_stm.deleteItem(_sti);
		_stm.getItemById(3L);
    }
}
