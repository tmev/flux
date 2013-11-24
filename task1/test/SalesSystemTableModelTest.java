import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;

public class SalesSystemTableModelTest {

	private class DisplayableItemImpl implements DisplayableItem {

		private long id;
		private String name;

		public DisplayableItemImpl(long id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		@Override
		public Long getId() {
			return id;
		}

		@Override
		public String getName() {
			return name;
		}

	}

	private class SalesSystemTableModelImpl extends
			SalesSystemTableModel<DisplayableItem> {

		private static final long serialVersionUID = 1938973502677613238L;

		public SalesSystemTableModelImpl(String[] headers) {
			super(headers);
		}

		@Override
		protected Object getColumnValue(DisplayableItem item, int columnIndex) {
			return item.getId();
		}

	}

	DisplayableItem i1;
	DisplayableItem i2;
	ArrayList<DisplayableItem> l;
	private SalesSystemTableModelImpl sstm;

	@Before
	public void setUp() {
		i1 = new DisplayableItemImpl(1, "TEST1");
		i2 = new DisplayableItemImpl(2, "TEST2");
		sstm = new SalesSystemTableModelImpl(new String[] { "A", "B", "C" });
		l = new ArrayList<>();
		l.add(i1);
		l.add(i2);
		sstm.populateWithData(l);
	}

	@Test
	public void testGetColumnCount() {
		assertEquals(3, sstm.getColumnCount());
	}

	@Test
	public void testGetColumnName() {
		assertTrue("B".equals(sstm.getColumnName(1)));
	}

	@Test
	public void testGetRowCount() {
		assertEquals(2, sstm.getRowCount());
	}

	@Test
	public void testGetValueAt() {
		assertEquals(i2.getId(), sstm.getValueAt(1, 0));
	}

	@Test
	public void testGetItemById() {
		assertEquals(i2, sstm.getItemById(i2.getId()));
	}

	@Test
	public void testGetItemByName() {
		assertEquals(i2, sstm.getItemByName(i2.getName()));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetItemByNonExistingId() {
		sstm.getItemById(123456);
	}

	@Test(expected = NoSuchElementException.class)
	public void testgetItemByNonExistingName() {
		sstm.getItemByName("WakaWaka");

	}

	@Test
	public void testGetTableRows() {
		assertEquals(l, sstm.getTableRows());
	}

	@Test(expected = NoSuchElementException.class)
	public void testClear() {
		SalesSystemTableModelImpl sstm = new SalesSystemTableModelImpl(null);
		sstm.populateWithData(l);

		sstm.clear();
		sstm.getItemByName(i1.getName());
	}
}
