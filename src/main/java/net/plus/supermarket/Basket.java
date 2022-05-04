package net.plus.supermarket;

import java.text.NumberFormat;
import java.util.*;

public class Basket {
    private ArrayList<Item> items;

    // Create a new Locale
    private Locale uk = new Locale("en", "GB");
    // Create a Currency instance for the Locale
    private Currency dollars = Currency.getInstance(uk);
    // Create a formatter given the Locale
    private NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(uk);

    public Basket() {
        items = new ArrayList<Item>();
    }

    //Different Constructors For Different Cases
    public void addProduct(Item item) {
        items.add(item);
    }

    public void addProduct(String itemSKU, Float itemSKUPrice) {
        items.add(new Item(itemSKU, itemSKUPrice));
    }

    public void addProduct(String itemSKU, Float itemSKUPrice, List<Item.SpecialPrice> itemSpecialPriceList) {
        items.add(new Item(itemSKU, itemSKUPrice, itemSpecialPriceList));
    }

    //Default Print Basket
    public String toString() {
        return "";
        //HERERERE
    }

    //Get The Quantities
    public Map<String, Integer> getQuantityList() {
        Map<String, Integer> mapOfItems = new HashMap<String, Integer>();
        //Getting The Quantity Of Items
        for (Item item : items) {
            if (mapOfItems.containsKey(item.getSKU())) {
                mapOfItems.replace(item.getSKU(), mapOfItems.get(item.getSKU()) + 1);
            } else {
                mapOfItems.put(item.getSKU(), 1);
            }
            //System.out.println(mapOfItems);
        }
        return mapOfItems;
    }

    //Remove Duplicates For Efficiency
    public List<Item> removeDuplicateItem(List<Item> itemsList) {
        return new ArrayList<>(new LinkedHashSet<>(itemsList));
    }

    //Print Out The Basket & Detailed Information
    public String basketCheckOut() {
        Float totalPrice = 0F;
        Float itemPrice = 0F;
        Float totalSaved = 0F;
        int maxValue = 0;
        Float totalPriceWithSpecialPrice = 0F;


        StringBuilder sb = new StringBuilder();
        // sb.append("=======================================================" + "\n==================== BASKET ===========================" + "\n=======================================================\n");

        Map<String, Integer> quantityList = getQuantityList();


        for (Item item : removeDuplicateItem(items)) {

            if (!item.getSpecialPriceList().isEmpty()) {
                for (Item.SpecialPrice specialPrice : item.getSpecialPriceList()) {


                    //max special offer
                    if (specialPrice.getSpecialPriceQuantity() > maxValue && quantityList.get(item.getSKU()) % specialPrice.getSpecialPriceQuantity() == 0) {

                        maxValue = specialPrice.getSpecialPriceQuantity();
                        itemPrice = specialPrice.getSpecialPrice();
                    }
                    else{
                        maxValue = specialPrice.getSpecialPriceQuantity();
                        itemPrice = item.getSKUPrice();
                    }

                }
                System.out.println("SKU = "+ item.getSKU() + "\n Quantity = " + quantityList.get(item.getSKU()) + "\n SKU Price = " + dollarFormat.format(itemPrice) +  "\n Special Offer =  N/A ");



            } else {
                maxValue = 0;
                System.out.println("SKU = "+ item.getSKU() + "\n Quantity = " + quantityList.get(item.getSKU()) + "\n SKU Price = " + dollarFormat.format(itemPrice) +  "\n Special Offer =  N/A ");
            }

            totalPrice = totalPrice + item.getSKUPrice();
            totalSaved = totalPrice - totalPriceWithSpecialPrice;
        }


        // sb.append("\n==================================================");
        // sb.append("\n    Amount Saved  : " + totalPriceWithSpecialPrice);
        //   sb.append("\n    Amount To Pay : " + totalSaved);
        //   sb.append("\n==================================================");

        return sb.toString();
    }
}
