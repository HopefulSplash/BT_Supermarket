package net.plus.supermarket;

import java.text.NumberFormat;
import java.util.*;

public class Item {

    // Create a new Locale
    private final Locale uk = new Locale("en", "GB");
    // Create a formatter given the Locale
    private final NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(uk);

    //Could Use A Char (String Chosen as example as AFD)
    private String itemSKU;
    //Using Float due to £5.99 (Real Prices)
    private Float itemSKUPrice;
    //list of special prices
    private List<SpecialPrice> specialPriceList = new ArrayList<>();

    //Getters & Setters
    public void setItemName(String itemSKU) {
        this.itemSKU = itemSKU;
    }

    public String getSKU() {
        return itemSKU;
    }

    public void setSKUPrice(Float priceSKU) {
        this.itemSKUPrice = priceSKU;
    }

    public Float getSKUPrice() {
        return itemSKUPrice;
    }

    public List<SpecialPrice> getSpecialPriceList() {
        return specialPriceList;
    }

    //Add to list
    public void addSpecialPriceList(Float price, Integer quantity) {
        SpecialPrice specialPrice = new SpecialPrice(price,quantity);
        this.specialPriceList.add(specialPrice);
    }
    //Remove from list
    public void removeSpecialPriceList(int removeIndex) {
        this.specialPriceList.remove(removeIndex);
    }

    //Constructor without Special Price
    public Item(String itemSKU, Float itemSKUPrice) {
        this.itemSKU = itemSKU;
        this.itemSKUPrice = itemSKUPrice;
    }

    //Constructor with Special Price
    public Item(String itemSKU, Float itemSKUPrice, List<SpecialPrice> specialPriceList) {
        this.itemSKU = itemSKU;
        this.itemSKUPrice = itemSKUPrice;
        this.specialPriceList = specialPriceList;
    }

    //Quick Call Method For Item & Price
    @Override
    public String toString() {
        int count = 1;
        String itemOutput = "Item Name: " + itemSKU + " Item Price: " + dollarFormat.format(itemSKUPrice);

        StringBuilder sb = new StringBuilder();
        sb.append(itemOutput);

        if (!specialPriceList.isEmpty()) {
            sb.append("\n    *** Special Offers ***");

            for (SpecialPrice specialPrice : specialPriceList) {
                sb.append("\n       ").append(count).append(") ").append(specialPrice.toString());
                count++;
            }
        }

        return sb.toString();
    }


    //Sub Class For Special Prices
    public class SpecialPrice implements Comparable<SpecialPrice> {
        //Could Add for different types of special offers (Weight, Brand, Etc)

        //Using Float due to £5.99 (Real Prices)
        private final Float specialPrice;
        //Using Int due to not being able to buy half an item (Unless Items Are Sold By Weight)
        private final Integer specialPriceQuantity;

        //Getters & Setters
        public int getSpecialPriceQuantity() {
            return specialPriceQuantity;
        }

        public Float getSpecialPrice() {
            return specialPrice;
        }

        //Initializer for Special Price
        public SpecialPrice(Float specialPrice, Integer specialPriceQuantity) {
            this.specialPrice = specialPrice;
            this.specialPriceQuantity = specialPriceQuantity;
        }

        //Quick Call Method For Special Price
        @Override
        public String toString() {
            return specialPriceQuantity + " For " + dollarFormat.format(specialPrice);
        }

        @Override
        public int compareTo(SpecialPrice u) {
            if (specialPriceQuantity == 0 || u.getSpecialPriceQuantity() == 0) {
                return 0;
            }
            return specialPriceQuantity.compareTo(u.getSpecialPriceQuantity());
        }
    }
}


