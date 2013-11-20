import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {


    @Test()
    public void testValidateNameUniqueness() {
    	StockTableModel stm = new StockTableModel();
    	StockItem sti = new StockItem((long)3, "Bubblegum", "Pink", 3.12);
    	StockItem sti2 = new StockItem((long)4, "Bubblegum", "Blue", 4.22);
    	stm.addItem(sti);
    	assertTrue(stm.nameExistsInStock(sti2.getName()));
    }
    
    @Test
    public void testHasEnoughInStock() {
    	StockTableModel stm = new StockTableModel();
    	StockItem sti = new StockItem((long)3, "Bubblegum", "Pink", 3.12,10);
    	int stockSize = sti.getQuantity();
    	int quantity = 10;
    	assertTrue(stm.hasEnoughInStock(stockSize, quantity));
    }

    @Test
    public void testGetItemByIdWhenItemExists() {
    	StockTableModel stm = new StockTableModel();
    	StockItem sti = new StockItem((long)3, "Bubblegum", "Pink", 3.12);
    	stm.addItem(sti);
    	assertEquals(sti, stm.getItemById(3));
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testGetItemByIdWhenThrowsException() {
    	StockTableModel stm = new StockTableModel();
    	stm.getItemById(3);
    }
}
