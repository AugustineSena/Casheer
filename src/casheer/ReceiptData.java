/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casheer;

/**
 *
 * @author SENA
 */
public class ReceiptData {

    

    
    private int ID;
    private String ItemName;
    private int ItemPrice;
    private int ItemQuantity = 0;
    private int ItemTotalPrice;
    public ReceiptData(int ID, String ItemName, int ItemPrice) {
        this.ID = ID;
        this.ItemName = ItemName;
        this.ItemPrice = ItemPrice;
        
    }
    
    public ReceiptData(int ID, String ItemName, int ItemPrice, int ItemQuantity) {
        this.ID = ID;
        this.ItemName = ItemName;
        this.ItemPrice = ItemPrice;
        this.ItemQuantity = ItemQuantity;
        this.ItemTotalPrice =  ItemQuantity*ItemPrice;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public void setItemPrice(int ItemPrice) {
        this.ItemPrice = ItemPrice;
    }

    public void setItemQuantity(int ItemQuantity) {
        this.ItemQuantity = ItemQuantity;
        this.ItemTotalPrice =  ItemQuantity*ItemPrice;
    }

    /*public void setItemTotalPrice() {
        this.ItemTotalPrice = getItemPrice()*getItemQuantity();
    }*/
    public int getID() {
        return ID;
    }

    public String getItemName() {
        return ItemName;
    }

    public int getItemPrice() {
        return ItemPrice;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public int getItemTotalPrice() {
        return ItemTotalPrice;
    }
}
