/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author SENA
 */
public class ReceiptData extends  ItemData{

    private int ItemQuantity = 0;
    private int ItemTotalPrice;
    public ReceiptData(){
        super();
    }
    public ReceiptData(int ID, String ItemName, int ItemPrice) {
        super.setID(ID);
        super.setItemName(ItemName);
        super.setItemPrice(ItemPrice);
    }
    
    public ReceiptData(int ID, String ItemName, int ItemPrice, int ItemQuantity) {
        super.setID(ID);
        super.setItemName(ItemName);
        super.setItemPrice(ItemPrice);
        this.ItemQuantity = ItemQuantity;
        this.ItemTotalPrice =  ItemQuantity*ItemPrice;
    }

    public void setItemQuantity(int ItemQuantity) {
        this.ItemQuantity = ItemQuantity;
        this.ItemTotalPrice =  ItemQuantity*super.getItemPrice();
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public int getItemTotalPrice() {
        return ItemTotalPrice;
    }
}
