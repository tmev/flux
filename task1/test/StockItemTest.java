import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemTest {


    @Test
    public void testClone() {
     StockItem sti = new StockItem((long) 1," "," ",30);
     StockItem stiClone = (StockItem) sti.clone();
     assertEquals(stiClone.getId(),sti.getId());
     assertEquals(stiClone.getName(),sti.getName());
     assertEquals(stiClone.getDescription(),sti.getDescription());
     assertEquals(stiClone.getPrice(),sti.getPrice(), 0.0001);
    }
    
    @Test
    public void testGetColumn() {
    }

}