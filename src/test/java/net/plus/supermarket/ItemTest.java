package net.plus.supermarket;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void setItemName() {
        Item item = new Item("A", 10F);
        assertSame("A", item.getSKU());
        item.setItemName("B");
        assertSame("B", item.getSKU());
    }

    @Test
    public void getSKU() {
        Item item = new Item("A", 10F);
        assertSame("A", item.getSKU());
    }

    @Test
    public void setSKUPrice() {
        Item item = new Item("A", 10F);
        assertEquals(10F, item.getSKUPrice(), 0.0);
        item.setSKUPrice(15F);
        assertEquals(15F, item.getSKUPrice(), 0.0);
    }

    @Test
    public void getSKUPrice() {
        Item item = new Item("A", 10F);
        assertEquals(10F, item.getSKUPrice(), 0.0);
    }

    @Test
    public void getSpecialPriceList() {
        Item item = new Item("A", 10F);
        assertNotNull(item.getSpecialPriceList());
        item.addSpecialPriceList(5F, 5);
        assertEquals(item.getSKU(), "A");
        assertEquals(10F, item.getSKUPrice(), 0.0);
        assertNotEquals(10F, item.getSpecialPriceList().get(0).getSpecialPrice(), 0.0);
        assertNotEquals(15, item.getSpecialPriceList().get(0).getSpecialPriceQuantity());
        assertEquals(5F, item.getSpecialPriceList().get(0).getSpecialPrice(), 0.0);
        assertEquals(5, item.getSpecialPriceList().get(0).getSpecialPriceQuantity());
    }

    @Test
    public void addSpecialPriceList() {
        Item item = new Item("A", 10F);
        item.addSpecialPriceList(5F, 5);
        assertEquals(item.getSKU(), "A");
        assertEquals(10F, item.getSKUPrice(), 0.0);
        assertEquals(5F, item.getSpecialPriceList().get(0).getSpecialPrice(), 0.0);
        assertEquals(5, item.getSpecialPriceList().get(0).getSpecialPriceQuantity());
    }

    @Test
    public void removeSpecialPriceList() {
        Item item = new Item("A", 10F);
        item.addSpecialPriceList(5F, 5);
        assertEquals(item.getSKU(), "A");
        assertEquals(10F, item.getSKUPrice(), 0.0);
        assertEquals(5F, item.getSpecialPriceList().get(0).getSpecialPrice(), 0.0);
        assertEquals(5, item.getSpecialPriceList().get(0).getSpecialPriceQuantity());
        item.removeSpecialPriceList(0);
        assertTrue(item.getSpecialPriceList().isEmpty());
        item.addSpecialPriceList(5F, 5);
        item.addSpecialPriceList(15F, 2);
        item.removeSpecialPriceList(0);
        assertEquals(15F, item.getSpecialPriceList().get(0).getSpecialPrice(), 0.0);
        assertEquals(2, item.getSpecialPriceList().get(0).getSpecialPriceQuantity());

    }
}