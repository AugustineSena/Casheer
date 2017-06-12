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
public class ItemData {
    private int ID = 0;
    private String ItemName;
    private int ItemPrice;
   
    public ItemData(int ID, String ItemName, int ItemPrice) {
        this.ID = ID;
        this.ItemName = ItemName;
        this.ItemPrice = ItemPrice;
    }

    public int getID() {
        return ID;
    }

    public String getItemName() {
        return ItemName;
    }

    public int getItemPrice() {
        return ItemPrice;
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
    
}
