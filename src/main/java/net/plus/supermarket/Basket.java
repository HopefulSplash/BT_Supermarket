package net.plus.supermarket;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Basket {
    //Items List
    private ArrayList<Item> items;

    // Create a new Locale
    private Locale uk = new Locale("en", "GB");
    // Create a Currency instance for the Locale
    private Currency dollars = Currency.getInstance(uk);
    // Create a formatter given the Locale
    private NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(uk);

    //Constructor
    public Basket() {
        items = new ArrayList<Item>();
    }

    //Different Add Functions For Different Cases (Can Remove But Test Didn't Have It So Didn't Implement)
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
        //Variables for price and count
        Float itemPrice = 0F;
        int specialPriceQuantityNeeded = 0;
        int itemQuantity = 0;
        Float totalPriceWithSpecialPrice = 0F;
        Float totalPriceWithOutSpecialPrice = 0F;

        //StringBuilder For Printing
        StringBuilder sb = new StringBuilder();
        sb.append("      ===========================" + "\n            *** BASKET ****" + "\n      ===========================");

        //Getting HashMap Of Quantities
        Map<String, Integer> quantityList = getQuantityList();

        //Using The Items without duplicates
        for (Item item : removeDuplicateItem(items)) {
            //Resetting item price and quantity for new item
            itemPrice = 0F;
            itemQuantity = quantityList.get(item.getSKU());
            //Sort The Special Offers List To The Highest First （So The Cheapest Deal Is Reached)
            List<Item.SpecialPrice> sortedUsers = item.getSpecialPriceList().stream()
                    .sorted(Comparator.comparing(Item.SpecialPrice::getSpecialPriceQuantity).reversed())
                    .collect(Collectors.toList());

            //(iteration) until every item has been processed
            while (itemQuantity > 0) {
                //Check if item has a special price
                if (!item.getSpecialPriceList().isEmpty()) {
                    //Process each special price
                    for (Item.SpecialPrice specialPrice : sortedUsers) {
                        //Check if the quantity of items is more or the same as the special price quantity
                        if (itemQuantity - specialPriceQuantityNeeded >= 0) {
                            //change the quantity needed as it meets requirements
                            specialPriceQuantityNeeded = specialPrice.getSpecialPriceQuantity();
                            //update price
                            itemPrice = itemPrice + specialPrice.getSpecialPrice();
                            //remove quantity from item count
                            itemQuantity = itemQuantity - specialPriceQuantityNeeded;
                        }
                        //if it doesn't meet the requirements
                        else if (itemQuantity > 0) {
                                itemPrice = itemPrice + item.getSKUPrice();
                                itemQuantity = itemQuantity - 1;
                        }
                    }
                }
                //no special prices
                else {
                    itemPrice = itemPrice + item.getSKUPrice();
                    itemQuantity = itemQuantity - 1;
                }
            }
            //add to stats for print out
            totalPriceWithOutSpecialPrice = totalPriceWithOutSpecialPrice + (item.getSKUPrice() * quantityList.get(item.getSKU()));
            totalPriceWithSpecialPrice = totalPriceWithSpecialPrice + itemPrice;

            //CHANGE THIS TO SHOW WHAT WAS CHARGE

            //print out the item and special offer prices
            sb.append("\nSKU = " + item.getSKU() + "\n Quantity = " + quantityList.get(item.getSKU()) + "\n Price Per Item = " + dollarFormat.format(itemPrice) + "\n Special Offers = " + sortedUsers);
        }

        //print out checkout details
        sb.append("\n      ===========================" + "\n           *** Checkout ****" + "\n      ===========================");
        sb.append("\n        Amount W/O    : " + dollarFormat.format(totalPriceWithOutSpecialPrice));

        if (totalPriceWithOutSpecialPrice - totalPriceWithSpecialPrice > 0) {
            sb.append("\n        Amount Saved  : " + dollarFormat.format(totalPriceWithOutSpecialPrice - totalPriceWithSpecialPrice));
        }

        sb.append("\n        Amount To Pay : " + dollarFormat.format(totalPriceWithSpecialPrice));
        sb.append("\n      ===========================");
        return sb.toString();
    }
}
