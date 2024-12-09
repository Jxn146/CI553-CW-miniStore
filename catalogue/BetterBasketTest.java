package catalogue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class BetterBasketTest {

	@Test
	void testMergeAddProduct() {
		BetterBasket br = new BetterBasket();
		Product p1 = new Product("0001", "toaster", 12.3, 1);
		Product p2 = new Product("0002", "kettle", 12.7, 1);
		Product p3 = new Product("0002", "kettle", 12.7, 1);
		Product p4 = new Product("0002", "kettle", 12.7, 1);
		br.add(p1);
		br.add(p2);
		br.add(p3);
		br.add(p4);
		assertEquals(2, br.size(), "Incorrect size after Merge");
		assertEquals(3, br.get(1).getQuantity(), "Incorrect quantity after Merge");
		
	}
	@Test
	void testSortAddProduct() {
	   BetterBasket br = new BetterBasket();
	   Product p1 = new Product("0001", "toaster", 12.3, 1);
	   Product p2 = new Product("0002", "kettler", 12.7, 1);
	   Product p3 = new Product("0002", "kettled", 12.7, 1);
	   Product p4 = new Product("0002", "kettleg", 12.7, 1);
	   br.add(p1);
	   br.add(p2);
	   br.add(p3);
	   br.add(p4);

	   assertEquals("0001", br.get(0).getProductNum(), "Incorrect quantity after Merge");      
	   
	}

}
