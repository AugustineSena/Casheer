package form.internal;

import model.ReceiptData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FramePrintReceipt extends JInternalFrame {
    private final JPanel jPReceiptPrint = new JPanel(new BorderLayout(1, 1));
    private JTextArea jTAAboutScript;
    private Box box;
    private String datatotext ="";
    private final String header = String.format("%3s%50s%12s%12s%12s\n","No.","Item Name","Item Price","Item Qty.","Total Price");
    private final String title = "=================\"TOKO APA AJA BOLEH\"====================\n"+
            "                \tJalan Sesat Ato Sehat no 666 Malang\n"+
            "\t             (0341)666 666 666\n"+
            "========================================================\n";
    private String text;
    private ArrayList<ReceiptData> Receipt = new ArrayList<>();
    public FramePrintReceipt(){
        super("Print Preview", true, true, false, true );
        this.add( ReceiptPrint(), BorderLayout.CENTER ); // add panel
        this.pack(); // set internal frame to size of contents
    }
    public FramePrintReceipt(ArrayList<ReceiptData> Receipt){
        super("Print Preview", true, true, false, true );
        setReceipt(Receipt);
        this.add( ReceiptPrint(), BorderLayout.CENTER ); // add panel
        this.pack(); // set internal frame to size of contents
    }
    public String LoadReceiptScript(){
        int count=1;
        for(ReceiptData me : getReceipt()){
            setDatatotext(getDatatotext() + String.format("%3d%50s%12d%12d%12d\n",count,me.getItemName(),me.getItemPrice(),me.getItemQuantity(),me.getItemTotalPrice()));
            count++;
        }
        int TotalTotalPrice = 0;
        TotalTotalPrice = Receipt.stream().map((me) -> me.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);
        setText(title+header+ getDatatotext() +
                String.format("========================================================\n\t\t\t%7s%12d\n", "Total :",TotalTotalPrice));
        return getText();
    }
    public JPanel ReceiptPrint(){
        JPanel jPReceiptView = new JPanel(new GridLayout(1, 1));
        box= Box.createVerticalBox();

        jTAAboutScript= new JTextArea(getText(),18,25);
        box.add(new JScrollPane(jTAAboutScript));
        jPReceiptView.add(box);
        JPanel jPBPrint = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton jBPrint = new JButton("Print");
        jBPrint.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,"Not Supported Yet","Not Supported Yet",JOptionPane.INFORMATION_MESSAGE);
        });
        jPBPrint.add(jBPrint);

        jPReceiptPrint.add(jPReceiptView,BorderLayout.NORTH);
        jPReceiptPrint.add(jPBPrint,BorderLayout.SOUTH);

        return jPReceiptPrint;
    }
    public ArrayList<ReceiptData> getReceipt() {
        return Receipt;
    }
    public void setReceipt(ArrayList<ReceiptData> receipt) {
        Receipt = receipt;
    }
    public void update(String text, String datatotext, String aboutScript){
                this.setText(text);
                this.setDatatotext(datatotext);
                jTAAboutScript.setText(aboutScript);
                jTAAboutScript.append(LoadReceiptScript());
    }
    public void reload(){
        if(this.isAncestorOf(ReceiptPrint())) {
            this.remove(ReceiptPrint());
        }else {
            this.add(ReceiptPrint(), BorderLayout.CENTER); // add panel
            this.pack(); // set internal frame to size of contents
        }
    }
    public String getDatatotext() {
        return datatotext;
    }
    public void setDatatotext(String datatotext) {
        this.datatotext = datatotext;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
