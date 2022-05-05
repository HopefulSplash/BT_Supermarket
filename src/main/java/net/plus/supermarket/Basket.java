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
    //Variables for price and count
    private int itemQuantity = 0;
    private int specialPriceQuantityNeeded = 0;
    private Float itemPrice = 0F;
    private Float totalPriceWithSpecialPrice = 0F;
    private Float totalPriceWithOutSpecialPrice = 0F;
    private Map<String, Integer> quantityList = new HashMap<>();
    private List<Item.SpecialPrice> sortedUsers = new ArrayList<>();


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

        for (Item item: removeDuplicateItem(items)) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("      ===========================" + "\n            *** BASKET ****" + "\n      ===========================");

        //Getting HashMap Of Quantities
        quantityList = getQuantityList();
        //Using The Items without duplicates
        for (Item item : removeDuplicateItem(items)) {
            //Resetting item price and quantity for new item
            itemPrice = 0F;
            itemQuantity = quantityList.get(item.getSKU());
            //Sort The Special Offers List To The Highest First So The Cheapest Deal Is Reached
            sortedUsers = item.getSpecialPriceList().stream().sorted(Comparator.comparing(Item.SpecialPrice::getSpecialPriceQuantity).reversed()).collect(Collectors.toList());

            //(iteration) until every item has been processed
            while (itemQuantity > 0) {
                //Check if item has a special price
                if (!item.getSpecialPriceList().isEmpty()) {
                    //Process each special price
                    for (int i = 0; i <= sortedUsers.size() - 1; i++) {
                        Item.SpecialPrice specialPrice = sortedUsers.get(i);
                        //Check if the quantity of items is more or the same as the special price quantity
                        if (itemQuantity - specialPriceQuantityNeeded >= 0) {
                            //change the quantity needed as it meets requirements
                            specialPriceQuantityNeeded = specialPrice.getSpecialPriceQuantity();
                            //update price
                            itemPrice = itemPrice + specialPrice.getSpecialPrice();
                            //remove quantity from item count
                            itemQuantity = itemQuantity - specialPriceQuantityNeeded;
                            //print out the item and special offer prices
                            sb.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(specialPriceQuantityNeeded).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n Special Offers = ").append(specialPrice);

                        }
                        //if it doesn't meet the requirements
                        else if (itemQuantity > 0) {
                            itemPrice = itemPrice + item.getSKUPrice();
                            //print out the item and special offer prices
                            if (itemQuantity > 1) {
                                sb.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(itemQuantity).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n Special Offers = ").append(specialPrice.toString());
                            } else {
                                sb.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(itemQuantity).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n");
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
                    sb.append("\nSKU = ").append(item.getSKU()).append("\n Quantity = ").append(itemQuantity).append("\n Total Price = ").append(dollarFormat.format(itemPrice)).append("\n");
                    itemQuantity = itemQuantity - 1;
                }
            }
            //add to stats for print out
            totalPriceWithOutSpecialPrice = totalPriceWithOutSpecialPrice + (item.getSKUPrice() * quantityList.get(item.getSKU()));
            totalPriceWithSpecialPrice = totalPriceWithSpecialPrice + itemPrice;

        }

        //print out checkout details
        sb.append("\n      ===========================" + "\n           *** Checkout ****" + "\n      ===========================");
        sb.append("\n        Amount W/O    : ").append(dollarFormat.format(totalPriceWithOutSpecialPrice));

        if (totalPriceWithOutSpecialPrice - totalPriceWithSpecialPrice > 0) {
            sb.append("\n        Amount Saved  : ").append(dollarFormat.format(totalPriceWithOutSpecialPrice - totalPriceWithSpecialPrice));
        }

        sb.append("\n        Amount To Pay : ").append(dollarFormat.format(totalPriceWithSpecialPrice));
        sb.append("\n      ===========================");
        return sb.toString();
    }
}
