package net.plus.supermarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket
{
    private ArrayList<Item> items;

    public Basket()
    {
        items = new ArrayList<Item>();
    }
    //Different Constructors For Different Cases
    public void addProduct(Item item)
    {
        items.add(item);
    }
    public void addProduct(String itemSKU, Float itemSKUPrice)
    {
        items.add(new Item (itemSKU, itemSKUPrice));
    }
    public void addProduct(String itemSKU, Float itemSKUPrice, List<Item.SpecialPrice> itemSpecialPriceList)
    {
        items.add(new Item (itemSKU, itemSKUPrice, itemSpecialPriceList));
    }

    //Default Print Basket
    public String toString(){
        return "";
        //HERERERE
    }

    //Print Out The Basket & Detailed Information
    public String basketCheckOut()
    {
        Float totalPrice = 0F;
        Float totalSaved = 0F;
        Float totalPriceWithSpecialPrice = 0F;

        Map<String, Integer> mapOfItems = new HashMap<String, Integer>();
        //Getting The Quantity Of Items
        for (Item item: items) {
            if (mapOfItems.containsKey(item.getSKU())){
                mapOfItems.replace(item.getSKU(), mapOfItems.get(item.getSKU()) + 1);
            }
            else {
                mapOfItems.put(item.getSKU(), 1);
            }
            //System.out.println(mapOfItems);
        }

        //workout price




        StringBuilder sb = new StringBuilder();
        sb.append("=======================================================" + "\n==================== BASKET ===========================" + "\n=======================================================\n");


        for (Item item: items) {

            sb.append(item.toString() + "\n");








            totalPrice = totalPrice + item.getSKUPrice();
            totalSaved = totalPrice - totalPriceWithSpecialPrice;
        }


        sb.append("\n==================================================");
        sb.append("\n    Amount Saved  : " + totalPriceWithSpecialPrice);
        sb.append("\n    Amount To Pay : " + totalSaved);
        sb.append("\n==================================================");

        return sb.toString();
    }
}
