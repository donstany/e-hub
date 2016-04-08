package bg.stanev.ehub.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bg.stanev.ehub.entity.Item;
import bg.stanev.ehub.exception.RssException;
import bg.stanev.ehub.service.RssService;

public class RssServiceTest {
	
	private RssService rssService;

	@Before
	public void setUp() throws Exception {
		rssService = new RssService();
	}

	// follow 3a pattern for testing
	@Test
	public void testGetItemsFile() throws RssException {
		List<Item> items = rssService.getItems(new File("test-rss/nakov.xml"));
		assertEquals(15, items.size());
		Item firstItem = items.get(0);
		assertEquals("Професия “Програмист” … и останалите ИТ професии – Наков @ УНСС", firstItem.getTitle());
		assertEquals("http://www.nakov.com/blog/2016/03/31/developers-qas-it-professions-nakov-at-unwe/", firstItem.getLink());
		
	}

}
