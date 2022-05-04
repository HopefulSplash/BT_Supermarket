package net.plus.supermarket;

public class Cashier {

    public static void main (String[] args)
    {
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
        myBasket.addProduct(itemB);
        myBasket.addProduct(itemB);
        myBasket.addProduct(itemB);
        myBasket.addProduct(itemB);
        myBasket.addProduct(itemB);

        myBasket.addProduct(itemA);
        myBasket.addProduct(itemA);
        myBasket.addProduct(itemA);
        myBasket.addProduct(itemA);

        myBasket.addProduct(itemC);
        myBasket.addProduct(itemC);
        myBasket.addProduct(itemC);
        myBasket.addProduct(itemC);

        myBasket.addProduct(itemD);
        myBasket.addProduct(itemD);

        System.out.println(myBasket.basketCheckOut());

    }
}

