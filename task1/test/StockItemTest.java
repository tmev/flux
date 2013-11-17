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
    	StockItem sti = new StockItem((long) 1,"Waka","Something Pacman says",333.5,30);
    	assertEquals(sti.getColumn(0),sti.getId());
    	assertEquals(sti.getColumn(1),sti.getName());
    	assertEquals(sti.getColumn(2),sti.getPrice());
    	assertEquals(sti.getColumn(3),sti.getQuantity());
    }

}