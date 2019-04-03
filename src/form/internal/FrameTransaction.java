package form.internal;

import misc.CellSpinner;
import misc.ExcelExport;
import model.ItemData;
import model.ReceiptData;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FrameTransaction extends JInternalFrame {
    private JDesktopPane parentDesktop;
    private FramePrintReceipt framePrintReceipt = new FramePrintReceipt();
    private JTable jTTransactionForm;
    private Object[][] dataTransactionForm ;
    private JTextField jTFTotalTotalPrice;
    private TableRowSorter<TableModel> rowSorterTransactionForm;
    private DefaultTableModel modelDataTransaction;
    ////////////////////////////////////////DataTransactionEdit////////////////////////////////////////
    private JTextField jTFTransactionID;
    private JSpinner jSTransactionQty;
    private JTextField jTFTransactionDelete;
    private JLabel jLDeletedIndicator;
    private final SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
    /////////////////////////////////////Model////////////////////////////////
    private ArrayList <ReceiptData> Receipt;
    private ArrayList <ItemData> Item = new ArrayList<>();
    public FrameTransaction(){
        super("Transaction", true, true, true, true );
        this.add( TransactionForm(), BorderLayout.CENTER ); // add panel
        this.pack(); // set internal frame to size of contents
    }
    public FrameTransaction(JDesktopPane parentDesktop){
        super("Transaction", true, true, true, true );
        this.setParentDesktop(parentDesktop);
        this.add( TransactionForm(), BorderLayout.CENTER ); // add panel
        this.pack(); // set internal frame to size of contents
    }
    public FrameTransaction(JDesktopPane parentDesktop , ArrayList<ItemData> Item){
        super("Transaction", true, true, true, true );
        this.setParentDesktop(parentDesktop);
        this.setItem(Item);
        this.add( TransactionForm(), BorderLayout.CENTER ); // add panel
        this.pack(); // set internal frame to size of contents
    }
    private JPanel TransactionForm()
    {
        setReceipt(new ArrayList<>());
        dataTransactionForm = new Object [getReceipt().size()][];
        JPanel jPTransactionNorth = new JPanel(new GridLayout(2, 1,1,1));
        JPanel jPBNewTransaction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jBNewTransaction = new JButton("New Transaction");
        jBNewTransaction.addActionListener((ActionEvent e) -> {
            if(!getReceipt().isEmpty()){
                getReceipt().clear();
            }
            if(modelDataTransaction.getRowCount()!=0){
                jTTransactionForm.removeAll();
                int rowCount = modelDataTransaction.getRowCount();
                //Remove rows one by one from the end of the table
                for (int i = rowCount - 1; i >= 0; i--) {
                    modelDataTransaction.removeRow(i);
                }
            }
            jTFTransactionID.setText("");
            jSTransactionQty.setValue((int)1);
            jTFTransactionDelete.setText("");
            jTFTotalTotalPrice.setText("0");
        });
        jPBNewTransaction.add(jBNewTransaction);

        jPTransactionNorth.add(jPBNewTransaction);
        jPTransactionNorth.add(DataTransaction());

        String[] headers = {"No.","ID","Item Name","Item Price","Item Qty.","Total/item"};
        JPanel frame = new JPanel(new GridLayout(3, 1, 1, 1));
        modelDataTransaction = new DefaultTableModel(dataTransactionForm,headers);

        jTTransactionForm = new JTable(modelDataTransaction){
            private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return String.class;
                    case 3:
                        return Integer.class;
                    case 4:
                        return Integer.class;
                    case 5:
                        return Integer.class;
                    default:
                        return Boolean.class;
                }
            }
            @Override
            public boolean isCellEditable (int row, int column)
            {
                return column ==4;
            }
        };
        TableColumnModel tcm = jTTransactionForm.getColumnModel();
        jTTransactionForm.setRowHeight(30);
        TableColumn tc = tcm.getColumn(4);
        tc.setCellEditor(new CellSpinner());
        jTTransactionForm.getModel().addTableModelListener((TableModelEvent e) -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                System.out.println("Cell " + row + ", "
                        + column + " changed. The new value: "
                        + jTTransactionForm.getModel().getValueAt(row,
                        column));

                if (column == 4) {
                    Object ItemID = jTTransactionForm.getModel().getValueAt(row, 1) ;
                    int getItemID = (int)ItemID;
                    System.err.println("Patch ItemID :"+ ItemID+" ...");
                    getReceipt().stream().filter((me) -> (getItemID==me.getID())).forEach((me) -> {
                        me.setItemQuantity((int)jTTransactionForm.getModel().getValueAt(row,column));
                    });
                    /////refreshing table///////////////
                    int rowCount = modelDataTransaction.getRowCount();
                    //Remove rows one by one from the end of the table
                    for (int i = rowCount - 1; i >= 0; i--) {
                        modelDataTransaction.removeRow(i);
                    }
                    /////reload table
                    int count1 = 1;
                    for (int i = 0; i < getReceipt().size(); i++) {
                        ArrayList<Object> row1 = new ArrayList<>() ;
                        row1.add(count1);
                        row1.add(getReceipt().get(i).getID());
                        row1.add(getReceipt().get(i).getItemName());
                        row1.add(getReceipt().get(i).getItemPrice());
                        row1.add(getReceipt().get(i).getItemQuantity());
                        row1.add(getReceipt().get(i).getItemTotalPrice());
                        modelDataTransaction.addRow(row1.toArray(new Object[row1.size()]));
                        count1++;
                    }
                    int TotalTotalPrice = 0;
                    TotalTotalPrice = getReceipt().stream().map((me) -> me.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);
                    String TotalTotalPriceToString = String.valueOf( TotalTotalPrice);
                    jTFTotalTotalPrice.setText(TotalTotalPriceToString);
                }
            }
        });
        jTTransactionForm.setPreferredScrollableViewportSize(new Dimension(450,123));
        jTTransactionForm.setFillsViewportHeight(true);
        JScrollPane scroll =  new JScrollPane( jTTransactionForm ) ;
        scroll.setVisible(true);

        rowSorterTransactionForm = new TableRowSorter<>(jTTransactionForm.getModel());
        jTTransactionForm.setRowSorter(rowSorterTransactionForm);

        JPanel jPTransactionSouth = new JPanel(new GridLayout(2,1,1,1));

        int TotalTotalPrice = 0;
        TotalTotalPrice = getReceipt().stream().map((me) -> me.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);

        JPanel jPTotalTotalPrice = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jLTotalTotalPrice = new JLabel("Total :");
        jTFTotalTotalPrice = new JTextField(12);
        String valueOfTotalTotalPrice = String.valueOf(TotalTotalPrice);
        jTFTotalTotalPrice.setText(valueOfTotalTotalPrice);
        jTFTotalTotalPrice.setEditable(false);
        jTFTotalTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
        jPTotalTotalPrice.add(jLTotalTotalPrice);
        jPTotalTotalPrice.add(jTFTotalTotalPrice);

        JPanel jPButtonExport = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jBExport = new JButton("Export");
        jBExport.addActionListener((ActionEvent evt) -> {
            try{
                ExcelExport exp = new ExcelExport();
                exp.exportTable(jTTransactionForm, new File ("D:CartList.xls"));
            }
            catch(IOException e){
                e.getMessage();
            }
        });
        JButton jBPrint = new JButton("Print Preview");
        jBPrint.addActionListener((ActionEvent e) -> {
            try{
                framePrintReceipt.setReceipt(getReceipt());
                getParentDesktop().add( framePrintReceipt ); // attach internal frame
                // show internal frame
                Dimension desktopSize = getParentDesktop().getSize();
                Dimension jInternalFrameSize = framePrintReceipt.getSize();
                framePrintReceipt.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                        (desktopSize.height - jInternalFrameSize.height)/2);
                framePrintReceipt.setVisible( true );
                //update jTextArea
                framePrintReceipt.update("","",null);

            }catch (IllegalArgumentException ef)
            {
                framePrintReceipt.setVisible(true);
                framePrintReceipt.toFront();
                framePrintReceipt.repaint();
                getParentDesktop().repaint();
            }
        });
        jPButtonExport.add(jBExport);
        jPButtonExport.add(jBPrint);

        jPTransactionSouth.add(jPTotalTotalPrice);
        jPTransactionSouth.add(jPButtonExport);

        frame.add(jPTransactionNorth);
        frame.add(scroll);
        frame.add(jPTransactionSouth);
        frame.setVisible(true);
        return frame;
    }
    ////////////////////////////////////////DataTransactionEdit////////////////////////////////////////
    private JPanel DataTransaction(){
        JPanel jPDataTransactionEdit = new JPanel(new GridLayout(2,1,2,2));
        JPanel jPAddDataTransaction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jLItemID = new JLabel("Item ID :");
        jTFTransactionID = new JTextField(5);
        jTFTransactionID.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel jLItemQty = new JLabel("Item Quantity :");
        jSTransactionQty = new JSpinner(spinnerModel);
        JButton jBAddDataTransaction = new JButton("Add Transaction");
        jBAddDataTransaction.addActionListener((ActionEvent e) -> {
            if (jTFTransactionID.getText().equals("")){
                String string = String.format("Item ID cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if (jTFTransactionID.getText().contains(" ")){
                String string = String.format("Item ID cannot contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if (!jTFTransactionID.getText().matches("[0-9]*")){
                String string = String.format("Item ID must only contain number");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else{
                int TID = Integer.parseInt(jTFTransactionID.getText());
                boolean isntRedundant=true;
                for(ReceiptData tar: getReceipt()){
                    isntRedundant = TID != tar.getID();
                }
                if(TID> getItem().size()||TID<1){
                    String string = String.format("Item ID not found");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }else if(!isntRedundant){
                    String string = String.format("Item already added");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    getItem().stream().filter((im) -> (TID == im.getID())).forEach((im) -> {
                        getReceipt().add(new ReceiptData(im.getID(),im.getItemName(),im.getItemPrice(),(int) jSTransactionQty.getValue()));
                    });
                    System.out.format("%3s%3s%30s%12s%10s%12s\n","No.","ID","Item Name","Item Price","Item Qty.","Total/item");
                    int j=1;
                    for(ReceiptData me: getReceipt()) {
                        System.out.format("%3d%3s%30s%12s%10s%12s\n",j, me.getID(),me.getItemName(),me.getItemPrice(),me.getItemQuantity(),me.getItemTotalPrice());j++;
                    }
                    ArrayList<Object> row = new ArrayList<>() ;
                    row.add(getReceipt().size());
                    row.add(getReceipt().get(getReceipt().size()-1).getID());
                    row.add(getReceipt().get(getReceipt().size()-1).getItemName());
                    row.add(getReceipt().get(getReceipt().size()-1).getItemPrice());
                    row.add(getReceipt().get(getReceipt().size()-1).getItemQuantity());
                    row.add(getReceipt().get(getReceipt().size()-1).getItemTotalPrice());
                    modelDataTransaction.addRow(row.toArray(new Object[row.size()]));
                    row.clear();
                }
                int TotalTotalPrice = 0;
                TotalTotalPrice = getReceipt().stream().map((me) -> me.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);
                String TotalTotalPriceToString = String.valueOf( TotalTotalPrice);
                jTFTotalTotalPrice.setText(TotalTotalPriceToString);
            }
        });
        jPAddDataTransaction.add(jLItemID);
        jPAddDataTransaction.add(jTFTransactionID);
        jPAddDataTransaction.add(jLItemQty);
        jPAddDataTransaction.add(jSTransactionQty);
        jPAddDataTransaction.add(jBAddDataTransaction);

        JPanel jPDeleteDataTransaction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jLDeletedIndicator = new JLabel("Data Successfully Deleted ! ;)");
        jLDeletedIndicator.setForeground(Color.RED);
        jLDeletedIndicator.setVisible(false);
        JLabel jLItemID1 = new JLabel("Item ID :");
        jTFTransactionDelete = new JTextField(5);
        jTFTransactionDelete.setHorizontalAlignment(SwingConstants.RIGHT);
        jTFTransactionDelete.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jTFTransactionDelete.getText();

                if (text.trim().length() == 0) {
                    rowSorterTransactionForm.setRowFilter(null);
                } else {
                    rowSorterTransactionForm.setRowFilter(RowFilter.regexFilter("(?i)" + text,1));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jTFTransactionDelete.getText();

                if (text.trim().length() == 0) {
                    rowSorterTransactionForm.setRowFilter(null);
                } else {
                    rowSorterTransactionForm.setRowFilter(RowFilter.regexFilter("(?i)" + text,1));
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        JButton jBDelete = new JButton("Delete");
        jBDelete.addActionListener((ActionEvent e) -> {
            if (jTFTransactionDelete.getText().equals("")){
                String string = String.format("Item ID cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if (jTFTransactionDelete.getText().contains(" ")){
                String string = String.format("Item ID cannot contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if (!jTFTransactionDelete.getText().matches("[0-9]*")){
                String string = String.format("Item ID must only contain number");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else{
                int DID = Integer.parseInt(jTFTransactionDelete.getText());
                if(DID> getItem().size()||DID<1){
                    String string = String.format("Item ID invalid");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    Iterator<ReceiptData> iter = getReceipt().iterator();
                    while(iter.hasNext()) {
                        ReceiptData rDIter = iter.next();
                        if(rDIter.getID()==DID) {
                            iter.remove(); // Removes the 'current' item
                        }
                    }
                    jLDeletedIndicator.setVisible(true);
                    /////refreshing table///////////////
                    int rowCount = modelDataTransaction.getRowCount();
                    //Remove rows one by one from the end of the table
                    for (int i = rowCount - 1; i >= 0; i--) {
                        modelDataTransaction.removeRow(i);
                    }
                    /////reload table
                    int count1 = 1;
                    for (int i = 0; i < getReceipt().size(); i++) {
                        ArrayList<Object> row = new ArrayList<>() ;
                        row.add(count1);
                        row.add(getReceipt().get(i).getID());
                        row.add(getReceipt().get(i).getItemName());
                        row.add(getReceipt().get(i).getItemPrice());
                        row.add(getReceipt().get(i).getItemQuantity());
                        row.add(getReceipt().get(i).getItemTotalPrice());
                        modelDataTransaction.addRow(row.toArray(new Object[row.size()]));
                        count1++;
                    }
                    ///recount total price
                    int TotalTotalPrice = 0;
                    TotalTotalPrice = getReceipt().stream().map((reme) -> reme.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);
                    String TotalTotalPriceToString = String.valueOf( TotalTotalPrice);
                    jTFTotalTotalPrice.setText(TotalTotalPriceToString);

                    jTFTransactionDelete.setText("");//restore session
                    jTFTransactionID.setText("");
                    ShowDeletedIndicator();
                }
            }
        });
        jPDeleteDataTransaction.add(jLDeletedIndicator);
        jPDeleteDataTransaction.add(jLItemID1);
        jPDeleteDataTransaction.add(jTFTransactionDelete);
        jPDeleteDataTransaction.add(jBDelete);

        jPDataTransactionEdit.add(jPAddDataTransaction);
        jPDataTransactionEdit.add(jPDeleteDataTransaction);
        return jPDataTransactionEdit;
    }
    public void ShowDeletedIndicator(){
        int delay = 1500; //milliseconds
        ActionListener taskPerformer = (ActionEvent evt) -> {
            jLDeletedIndicator.setVisible(false);
        };
        new Timer(delay, taskPerformer).start();
    }
    public JDesktopPane getParentDesktop() {
        return parentDesktop;
    }
    public void setParentDesktop(JDesktopPane parentDesktop) {
        this.parentDesktop = parentDesktop;
    }
    public ArrayList<ItemData> getItem() {
        return Item;
    }
    public void setItem(ArrayList<ItemData> item) {
        Item = item;
    }
    public ArrayList<ReceiptData> getReceipt() {
        return Receipt;
    }
    public void setReceipt(ArrayList<ReceiptData> receipt) {
        Receipt = receipt;
    }
}

