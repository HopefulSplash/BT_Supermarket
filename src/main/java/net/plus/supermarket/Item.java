package net.plus.supermarket;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Item {

    // Create a new Locale
    Locale uk = new Locale("en", "GB");
    // Create a Currency instance for the Locale
    Currency dollars = Currency.getInstance(uk);
    // Create a formatter given the Locale
    NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(uk);

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

    public void setSpecialPriceList(List<SpecialPrice> specialPriceList) {
        this.specialPriceList = specialPriceList;
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
        this.specialPriceList = specialPriceList;
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
        //print speical blah blah
        String itemOutput = "Item Name: " + itemSKU + " Item Price: " + dollarFormat.format(itemSKUPrice);
        StringBuilder sb = new StringBuilder();
        sb.append(itemOutput);

        if (!specialPriceList.isEmpty()) {
            sb.append("\n    *** Special Offers ***");

            for (SpecialPrice specialPrice : specialPriceList) {
                sb.append("\n       "+ count + ") " + specialPrice.toString());
                count++;
            }
        }

        return sb.toString();
    }


    //Sub Class For Special Prices
    public class SpecialPrice {
        //Could Add for different types of special offers (Weight, Brand, Etc)

        private Item item;
        //Using Float due to £5.99 (Real Prices)
        private Float specialPrice;
        //Using Int due to not being able to buy half an item (Unless Items Are Sold By Weight)
        private Integer specialPriceQuantiy;

        //Getters & Setters
        public int getSpecialPriceQuantity() {
            return specialPriceQuantiy;
        }

        public void setSpecialPriceQuantity(Integer specialPriceQuantiy) {
            this.specialPriceQuantiy = specialPriceQuantiy;
        }

        public Float getSpecialPrice() {
            return specialPrice;
        }

        public void setSpecialPrice(Float specialPrice) {
            this.specialPrice = specialPrice;
        }

        //Initializer for Special Price
        public SpecialPrice(Float specialPrice, Integer specialPriceQuantity) {
            this.specialPrice = specialPrice;
            this.specialPriceQuantiy = specialPriceQuantity;
        }

        //Quick Call Method For Special Price
        @Override
        public String toString() {
            return specialPriceQuantiy + " For " + dollarFormat.format(specialPrice);
        }
    }
}


