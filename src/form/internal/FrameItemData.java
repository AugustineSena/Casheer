package form.internal;

import misc.ExcelExport;
import misc.NumberRenderer;
import model.ItemData;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FrameItemData extends JInternalFrame {

    private JTable jTEditDataForm;
    private JPanel jPEditDataForm;
    private JTextField jTFItemName;
    private JTextField jTFSearch;
    private Object[][] dataEditDataForm;
    private TableRowSorter<TableModel> rowSorterEditDataForm;
    private JTextField jTFItemDelete;
    private DefaultTableModel modelEditData ;
    private JLabel jLDeletedIndicatorItem;
    private ArrayList <ItemData> Item = new ArrayList<>();
    public FrameItemData(){
        super("Item Data", true, true, true, true );
    }
    public FrameItemData(ArrayList<ItemData> Item){
        this();
        setItem(Item);
        reload();
    }
    private JPanel EditDataForm(){
        dataEditDataForm = new Object[getItem().size()][];
        for (int i = 0; i < getItem().size(); i++) {
            ArrayList<Object> row = new ArrayList<>() ;
            row.add(getItem().get(i).getID());
            row.add(getItem().get(i).getItemName());
            row.add(getItem().get(i).getItemPrice());
            dataEditDataForm[i] = row.toArray(new Object[row.size()]);

        }
        String[] headers = {"ID","Item Name","Item Price"/*,"Pick Up?"*/};
        jPEditDataForm = new JPanel(new GridLayout(3, 1, 2, 2));

        modelEditData = new DefaultTableModel(dataEditDataForm,headers);
        jTEditDataForm = new JTable(modelEditData){
            private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return Integer.class;
                    default:
                        return Boolean.class;
                }
            }
            @Override
            public boolean isCellEditable (int row, int column){
                return false;
            }
        };
        TableColumnModel m = jTEditDataForm.getColumnModel();
        m.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        jTEditDataForm.setPreferredScrollableViewportSize(new Dimension(450,123));
        jTEditDataForm.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(jTEditDataForm);
        jTEditDataForm.setRowHeight(30);

        rowSorterEditDataForm = new TableRowSorter<>(jTEditDataForm.getModel());
        jTEditDataForm.setRowSorter(rowSorterEditDataForm);

        JPanel jPButtonExport = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jBExport = new JButton("Export");
        jBExport.addActionListener((ActionEvent evt) -> {
            try{
                ExcelExport exp = new ExcelExport();
                exp.exportTable(jTEditDataForm, new File("D:ItemList.xls"));
            }catch(IOException e){
                e.getMessage();
            }
        });
        jPButtonExport.add(jBExport);
        /////////////////////////////////////Add New Data/////////////////////////////////
        JPanel jPEditDataNorth = new JPanel(new GridLayout(2,1,1,1));
        JPanel jPAddData = new JPanel(new GridLayout(2, 1,1,1));

        JPanel jPAddDataInput = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jLItemName = new JLabel("Item Name :");
        jTFItemName = new JTextField(12);
        JLabel jLItemPrice = new JLabel("Item Price :");
        JFormattedTextField jTFItemPrice = new JFormattedTextField(NumberRenderer.getCurrencyFormatter());//http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4832257
        jTFItemPrice.setColumns(12);
        JButton jBAddData = new JButton("Add");
        jBAddData.addActionListener((ActionEvent e) -> {
            if(jTFItemName.getText().equals("")&&jTFItemPrice.getText().equals("")){
                String string = String.format("Item name and price cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if(jTFItemName.getText().equals("")){
                String string = String.format("Item name cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if (jTFItemPrice.getText().equals("")){
                String string = String.format("Item price cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if (jTFItemPrice.getText().contains(" ")){
                String string = String.format("Item price cannot contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else{
                boolean isntRedundant=true;
                for(ItemData tar: getItem()){
                    isntRedundant = !jTFItemName.getText().equals(tar.getItemName());
                }if(!isntRedundant){
                    String string = String.format("Item already added !");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    getItem().add(new ItemData(getItem().size()+1,jTFItemName.getText(), (int)jTFItemPrice.getValue()));
                    String string = String.format("Data successfully added !");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                    ArrayList<Object> row = new ArrayList<>() ;
                    row.add(getItem().get(getItem().size()-1).getID());
                    row.add(getItem().get(getItem().size()-1).getItemName());
                    row.add(getItem().get(getItem().size()-1).getItemPrice());

                    modelEditData.addRow(row.toArray(new Object[row.size()]));
                    row.clear();
                    jTFItemName.setText("");
                    jTFItemPrice.setValue(null);
                    System.out.format("%3s%30s%12s\n","No.","Item Name","Item Price");
                    getItem().stream().forEach((me) -> {
                        System.out.format("%3d%30s%12d\n", me.getID(),me.getItemName(),me.getItemPrice());
                    });
                }
            }
        });
        jPAddDataInput.add(jLItemName);
        jPAddDataInput.add(jTFItemName);
        jPAddDataInput.add(jLItemPrice);
        jPAddDataInput.add(jTFItemPrice);
        jPAddDataInput.add(jBAddData);
        //////////////////////Delete Item///////////////////////////////////
        JPanel jPDeleteItem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jLDeletedIndicatorItem = new JLabel("Data Successfully Deleted ! ;)");
        jLDeletedIndicatorItem.setForeground(Color.RED);
        jLDeletedIndicatorItem.setVisible(false);
        JLabel jLItemID = new JLabel("Item ID :");
        jTFItemDelete = new JTextField(5);
        jTFItemDelete.setHorizontalAlignment(SwingConstants.RIGHT);
        jTFItemDelete.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jTFItemDelete.getText();
                if (text.trim().length() == 0) {
                    rowSorterEditDataForm.setRowFilter(null);
                } else {
                    rowSorterEditDataForm.setRowFilter(RowFilter.regexFilter("(?i)" + text,0));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jTFItemDelete.getText();
                if (text.trim().length() == 0) {
                    rowSorterEditDataForm.setRowFilter(null);
                } else {
                    rowSorterEditDataForm.setRowFilter(RowFilter.regexFilter("(?i)" + text,0));
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        JButton jBDelete = new JButton("Delete");
        jBDelete.addActionListener((ActionEvent e) -> {
            if (jTFItemDelete.getText().equals("")){
                String string = String.format("Item ID cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if (jTFItemDelete.getText().contains(" ")){
                String string = String.format("Item ID cannot contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else if (!jTFItemDelete.getText().matches("[0-9]*")){
                String string = String.format("Item ID must only contain number");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }else{
                int DID = Integer.parseInt(jTFItemDelete.getText());
                if(DID> getItem().size()||DID<1){
                    String string = String.format("Item ID invalid");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    Iterator<ItemData> iter = getItem().iterator();
                    while(iter.hasNext()) {
                        ItemData rDIter = iter.next();
                        if(rDIter.getID()==DID) {
                            iter.remove(); // Removes the 'current' item
                        }
                    }
                    jLDeletedIndicatorItem.setVisible(true);
                    ///recount ID of Item
                    int count=1;
                    for (ItemData me: getItem()){
                        me.setID(count);
                        count++;
                    }
                    /////refreshing table///////////////
                    int rowCount = modelEditData.getRowCount();
                    //Remove rows one by one from the end of the table
                    for (int i = rowCount - 1; i >= 0; i--) {
                        modelEditData.removeRow(i);
                    }
                    /////reload table
                    for (int i = 0; i < getItem().size(); i++) {
                        ArrayList<Object> row = new ArrayList<>() ;

                        row.add(getItem().get(i).getID());
                        row.add(getItem().get(i).getItemName());
                        row.add(getItem().get(i).getItemPrice());

                        modelEditData.addRow(row.toArray(new Object[row.size()]));

                    }
                    jTFItemDelete.setText("");//restore session
                    ShowDeletedIndicatorItem();
                }
            }
        });
        jPDeleteItem.add(jLDeletedIndicatorItem);
        jPDeleteItem.add(jLItemID);
        jPDeleteItem.add(jTFItemDelete);
        jPDeleteItem.add(jBDelete);

        ///////////////////Search///////////////////
        JPanel jPSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jTFSearch = new JTextField(12);
        jTFSearch.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent el) {
                        String text = jTFSearch.getText();
                        if (text.trim().length() == 0) {
                            rowSorterEditDataForm.setRowFilter(null);
                        } else {
                            String query = jTFSearch.getText();
                            rowSorterEditDataForm.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" +query) );
                        }
                    }
                    @Override
                    public void removeUpdate(DocumentEvent el) {
                        String query = jTFSearch.getText();
                        if (query.trim().length() == 0) {
                            rowSorterEditDataForm.setRowFilter(null);
                        } else {
                            rowSorterEditDataForm.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + query));
                        }
                    }
                    @Override
                    public void changedUpdate(DocumentEvent el) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                }
        );
        JLabel jLSearch = new JLabel("Search :");
        jPSearch.add(jLSearch);
        jPSearch.add(jTFSearch);

        /////////////////////////////////////////////////////////////////

        jPAddData.add(jPAddDataInput);
        jPAddData.add(jPDeleteItem);

        jPEditDataNorth.add(jPAddData);
        jPEditDataNorth.add(jPSearch);

        //////////////////////////

        jPEditDataForm.add(jPEditDataNorth, BorderLayout.NORTH);
        jPEditDataForm.add(scroll,BorderLayout.CENTER);
        jPEditDataForm.add(jPButtonExport, BorderLayout.SOUTH);
        jPEditDataForm.setVisible(true);
        return jPEditDataForm;
    }
    private void ShowDeletedIndicatorItem() {
        int delay = 1500; //milliseconds
        ActionListener taskPerformer = (ActionEvent evt) -> {
            jLDeletedIndicatorItem.setVisible(false);
        };
        new Timer(delay, taskPerformer).start();
    }
    public ArrayList<ItemData> getItem() {
        return Item;
    }
    public void setItem(ArrayList<ItemData> item) {
        Item = item;
        reload();
    }
    public void reload (){
        if(this.isAncestorOf(EditDataForm())) {
            this.remove(EditDataForm());
        }else {
            this.add(EditDataForm(), BorderLayout.CENTER); // add panel
            this.pack(); // set internal frame to size of contents
        }
    }
}
