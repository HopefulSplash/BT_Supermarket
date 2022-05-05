package net.plus.supermarket;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Basket {
    //Items List
    private final ArrayList<Item> items;

    // Create a new Locale
    private final Locale uk = new Locale("en", "GB");
    // Create a formatter given the Locale
    private final NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(uk);
    private Map<String, Integer> quantityList;


    //Constructor
    public Basket() {
        items = new ArrayList<>();
    }

    //Different Add Functions For Different Cases (Can Remove But Test Didn't Have It So Didn't Implement)
    public void addProduct(Item item) {
        items.add(item);
    }

    @Deprecated
    public void addProduct(String itemSKU, Float itemSKUPrice) {
        items.add(new Item(itemSKU, itemSKUPrice));
    }

    @Deprecated
    public void addProduct(String itemSKU, Float itemSKUPrice, List<Item.SpecialPrice> itemSpecialPriceList) {
        items.add(new Item(itemSKU, itemSKUPrice, itemSpecialPriceList));
    }

    //Default Print Basket (No Processing)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        quantityList = getQuantityList();

        for (Item item : removeDuplicateItem(items)) {
            sb.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(quantityList.get(item.getSKU())).append("\n Item Price = ").append(dollarFormat.format(item.getSKUPrice()));
            for (Item.SpecialPrice specialPrice : item.getSpecialPriceList()) {
                sb.append("\n Special Offers = ").append(specialPrice.toString());
            }
        }
        return sb.toString();
    }

    //Get The Quantities
    public Map<String, Integer> getQuantityList() {
        Map<String, Integer> mapOfItems = new HashMap<>();
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

    // put code in functions
    // try recursion approach
    // tests
    // final commit
    // fix git resp + readme
    // email off

    //Print Out The Basket & Detailed Information
    public String basketCheckOut() {
        //StringBuilder For Printing
        StringBuilder sbCheckout = new StringBuilder();
        //Isnt used until you want to use it
        @SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
        StringBuilder sbBasket = new StringBuilder();
        sbBasket.append("      ===========================" + "\n            *** BASKET ****" + "\n      ===========================");
        sbCheckout.append("\n      ===========================" + "\n           *** Checkout ****" + "\n      ===========================");


        int specialPriceQuantityNeeded = 0;
        Float totalPriceWithSpecialPrice = 0F;
        Float totalPriceWithOutSpecialPrice = 0F;
        //Getting HashMap Of Quantities
        quantityList = getQuantityList();

        //Using The Items without duplicates
        for (Item item : removeDuplicateItem(items)) {

            //Resetting item price and quantity for new item
            Float itemPrice = 0F;
            //Variables for price and count
            int itemQuantity = quantityList.get(item.getSKU());

            //(iteration) until every item has been processed
            while (itemQuantity > 0) {
                //Check if item has a special price
                if (!item.getSpecialPriceList().isEmpty()) {
                    //Sort The Special Offers List To The Highest First So The Cheapest Deal Is Reached
                    List<Item.SpecialPrice> sortedSpecialPrices = item.getSpecialPriceList().stream().sorted(Comparator.comparing(Item.SpecialPrice::getSpecialPriceQuantity).reversed()).collect(Collectors.toList());
                    //Process each special price
                    for (int i = 0; i <= sortedSpecialPrices.size() - 1; i++) {
                        Item.SpecialPrice specialPrice = sortedSpecialPrices.get(i);
                        //Check if the quantity of items is more or the same as the special price quantity
                        if (itemQuantity - specialPriceQuantityNeeded >= 0) {
                            //change the quantity needed as it meets requirements
                            specialPriceQuantityNeeded = specialPrice.getSpecialPriceQuantity();
                            //update price
                            itemPrice = itemPrice + specialPrice.getSpecialPrice();
                            //remove quantity from item count
                            itemQuantity = itemQuantity - specialPriceQuantityNeeded;
                            //print out the item and special offer prices
                            sbBasket.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(specialPriceQuantityNeeded).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n Special Offers = ").append(specialPrice);

                        }
                        //if it doesn't meet the requirements
                        else if (itemQuantity > 0) {
                            itemPrice = itemPrice + item.getSKUPrice();
                            //print out the item and special offer prices
                            if (itemQuantity > 1) {
                                sbBasket.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(itemQuantity).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n Special Offers = ").append(specialPrice.toString());
                            } else {
                                sbBasket.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(itemQuantity).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n");
                            }
                            itemQuantity = itemQuantity - 1;

                        }

                        if (itemQuantity - specialPriceQuantityNeeded >= 0) {
                            i = -1;
                        }
                    }
                }
                //no special prices
                else {
                    itemPrice = itemPrice + item.getSKUPrice();
                    //print out the item and special offer prices
                    sbBasket.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(1).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n");
                    itemQuantity = itemQuantity - 1;
                }
            }
            //add to stats for print out
            totalPriceWithOutSpecialPrice = totalPriceWithOutSpecialPrice + (item.getSKUPrice() * quantityList.get(item.getSKU()));
            totalPriceWithSpecialPrice = totalPriceWithSpecialPrice + itemPrice;
            sbCheckout.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(quantityList.get(item.getSKU())).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n");

        }

        //Printing To Console
        sbCheckout.append("      ===========================");
        sbCheckout.append("\n        Amount W/O    : ").append(dollarFormat.format(totalPriceWithOutSpecialPrice));
        if (totalPriceWithOutSpecialPrice - totalPriceWithSpecialPrice > 0) {
            sbCheckout.append("\n        Amount Saved  : ").append(dollarFormat.format(totalPriceWithOutSpecialPrice - totalPriceWithSpecialPrice));
        }
        sbCheckout.append("\n        Amount To Pay : ").append(dollarFormat.format(totalPriceWithSpecialPrice));
        sbCheckout.append("\n      ===========================");
        return sbCheckout.toString();
    }
}
