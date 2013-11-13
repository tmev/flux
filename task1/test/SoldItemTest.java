import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {


    @Test
    public void testGetSum() {
     StockItem sti = new StockItem((long) 1," "," ",30);
     SoldItem si = new SoldItem(sti,4);
     double result = si.getSum();
     double expected = sti.getPrice()*(double)si.getQuantity();
     assertEquals(expected,result, 0.0001);
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