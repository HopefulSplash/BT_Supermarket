package net.plus.supermarket;

import static org.junit.Assert.*;
import org.junit.Test;

public class CashierTest {

    @Test
    public void testNewItemWorks(){
      Item item = new Item("A", 10F);
      assertEquals(item.getSKU(), "A");
        assertEquals(10F, item.getSKUPrice(), 0.0);

    }
    @Test
    public void testNewItemDoesntWork(){
        Item item = new Item("A", 10F);
        assertNotEquals(item.getSKU(), "B");
        assertNotEquals(100F, item.getSKUPrice(), 0.0);

    }
    @Test
    public void testNewSpecialPriceWork(){
        Item item = new Item("A", 10F);
        item.addSpecialPriceList(5F, 5);
        assertEquals(item.getSKU(), "A");
        assertEquals(10F, item.getSKUPrice(), 0.0);
        assertEquals(5F, item.getSpecialPriceList().get(0).getSpecialPrice(), 0.0);
        assertEquals(5, item.getSpecialPriceList().get(0).getSpecialPriceQuantity());

    }

    @Test
    public void testNewSpecialPriceDoesntWork(){
        Item item = new Item("A", 10F);
        item.addSpecialPriceList(5F, 5);
        assertEquals(item.getSKU(), "A");
        assertEquals(10F, item.getSKUPrice(), 0.0);
        assertNotEquals(10F, item.getSpecialPriceList().get(0).getSpecialPrice(), 0.0);
        assertNotEquals(15, item.getSpecialPriceList().get(0).getSpecialPriceQuantity());

    }

    @Test
    public void testAddItemBasket() {
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
        myBasket.addProduct(itemB);
        myBasket.addProduct(itemA);
        myBasket.addProduct(itemC);
        myBasket.addProduct(itemD);
        myBasket.addProduct(itemD);

        assertEquals(6, myBasket.getItems().size());
    }

    @Test
    public void testRemoveItemBasket() {
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
        myBasket.addProduct(itemB);
        myBasket.addProduct(itemA);
        myBasket.addProduct(itemC);
        myBasket.addProduct(itemD);
        myBasket.addProduct(itemD);

        assertEquals(6, myBasket.getItems().size());

        myBasket.removeProduct(1);

        assertEquals(5, myBasket.getItems().size());
    }

    @Test
    public void testCashierCheckout() {
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

        assertEquals(120F, myBasket.totalPriceWithSpecialPrice, 0.0);
        assertEquals(105F, myBasket.totalPriceWithOutSpecialPrice, 0.0);

    }

    @Test
    public void testCashierCheckoutWorks() {
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