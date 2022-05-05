package net.plus.supermarket;

import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

public class BasketTest {

    @Test
    public void addProduct() {
        Item item = new Item("A", 10F);
        item.addSpecialPriceList(5F, 5);
        Basket basket = new Basket();
        assertTrue(basket.items.isEmpty());
        basket.addProduct(item);
        assertFalse(basket.items.isEmpty());
    }

    @Test
    public void testAddProduct() {
        Basket basket = new Basket();
        assertTrue(basket.items.isEmpty());
        basket.addProduct( new Item("A", 10F));
        assertFalse(basket.items.isEmpty());
    }

    @Test
    public void getQuantityList() {
        //Initialising Items + Special Price
        Item itemA = new Item("A", 50F);
        itemA.addSpecialPriceList(130F,  3);
        //System.out.println(itemA.toString());

        Item itemB = new Item("B", 30F);
        itemB.addSpecialPriceList(45F,  2);
        //System.out.println(itemB.toString());

        Item itemC = new Item("C", 20F);
        itemC.addSpecialPriceList(38F,  2);
        itemC.addSpecialPriceList(50F,  3);

        //System.out.println(itemC.toString());

        Item itemD = new Item("D", 5F);
        //System.out.println(itemD.toString());

        //Make A Basket
        Basket myBasket = new Basket();

        //Adding Items To Basket
        myBasket.addProduct(itemB);
        myBasket.addProduct(itemA);
        myBasket.addProduct(itemC);
        myBasket.addProduct(itemD);
        myBasket.addProduct(itemD);
        myBasket.addProduct(itemD);

        myBasket.basketCheckOut();

        assertFalse(myBasket.getQuantityList().isEmpty());
        Map<String,Integer> quantityMap = myBasket.getQuantityList();
        assertEquals(4, quantityMap.size());
        assertEquals(3, (int) quantityMap.get(itemD.getSKU()));
    }

    @Test
    public void removeDuplicateItem() {
        Item item = new Item("A", 10F);
        item.addSpecialPriceList(5F, 5);
        Basket basket = new Basket();
        assertTrue(basket.items.isEmpty());
        basket.addProduct(item);
        assertFalse(basket.items.isEmpty());
        basket.removeProduct(0);
        assertTrue(basket.items.isEmpty());

    }

    @Test
    public void basketCheckOut() {
        //Initialising Items + Special Price
        Item itemA = new Item("A", 50F);
        itemA.addSpecialPriceList(130F,  3);
        //System.out.println(itemA.toString());

        Item itemB = new Item("B", 30F);
        itemB.addSpecialPriceList(45F,  2);
        //System.out.println(itemB.toString());

        Item itemC = new Item("C", 20F);
        itemC.addSpecialPriceList(38F,  2);
        itemC.addSpecialPriceList(50F,  3);

        //System.out.println(itemC.toString());

        Item itemD = new Item("D", 5F);
        //System.out.println(itemD.toString());

        //Make A Basket
        Basket myBasket = new Basket();

        //Adding Items To Basket
        myBasket.addProduct(itemB);
        myBasket.addProduct(itemA);
        myBasket.addProduct(itemC);
        myBasket.addProduct(itemD);

        myBasket.basketCheckOut();

        assertNotEquals(105F, myBasket.totalPriceWithSpecialPrice, 0.0);
        assertNotEquals(120F, myBasket.totalPriceWithOutSpecialPrice, 0.0);

    }

}