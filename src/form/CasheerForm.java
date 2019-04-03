package form;

import form.internal.FrameLogin;
import misc.CellSpinner;
import misc.ExcelExport;
import model.ItemData;
import model.ReceiptData;
import model.UserData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;

public class CasheerForm extends JFrame {

    private final JDesktopPane theDesktop;
    private final FrameLogin frameLogin = new FrameLogin(){
        public void doWhenSuccess(){
            if(frameLogin.isLogin()){
                jBItemData.setEnabled(true);
                jBTransaction.setEnabled(true);
                jMILogin.setText("Logout");
                frameLogin.dispose();
                jBItemData.doClick();
                jBTransaction.doClick();
            }
        }
    };
    private AboutForm aboutform = new AboutForm();
    private ArrayList <ReceiptData> Receipt;
    private ArrayList <UserData> User;
    private ArrayList <ItemData> Item;

    /**
     */
    public CasheerForm()
    {
        super( "CasheerForm App Free (キャッシャー) - \u00a9 2016 - 311410001 - Augustine Sena,Inc" );

        theDesktop = new JDesktopPane(); // create desktop pane
        theDesktop.setBackground(Color.BLACK);
        add( theDesktop );

        InstallUserData();
        InstallItemData();

        setJMenuBar(MenuStrip());//?

        System.out.println("========== Notice Me せんぱい！===========");
        System.out.format("%18s    %-20s\n========================================\n","Username(ユーザ名)","Password(パスワード)");
        User.stream().forEach((me) -> {
            System.out.format("%20s    %-20s\n",me.getUsername(),me.getPassword());
        });
    }



    public void InstallUserData()
    {
        User = new ArrayList<>();
        User.add(new UserData("admin","1234"));
        User.add(new UserData("AlKachir","antimo=antimaho+jomblo"));//AlKachir means 'Aliran Kathok Chingkrank'
        User.add(new UserData("Lockey","mantaps"));
    }


    public void InstallItemData()
    {
        Item = new ArrayList<>();
        Item.add(new ItemData(Item.size()+1,"Adobe Creative Pack CS12",2000000));//1
        Item.add(new ItemData(Item.size()+1,"Adobe Master Collection CC2016",2800000));//2
        Item.add(new ItemData(Item.size()+1,"NVidia GTX 980",3900000));//3
        Item.add(new ItemData(Item.size()+1,"NVidia GTX 980i",8200000));//4
        Item.add(new ItemData(Item.size()+1,"NVidia GTX 1028",8900000));//5
        Item.add(new ItemData(Item.size()+1,"Processor Intel i5",2500000));//6
        Item.add(new ItemData(Item.size()+1,"Processor Intel i7",3900000));//7
        Item.add(new ItemData(Item.size()+1,"USB Flashdisk Kingston 8GB",400000));//8
        Item.add(new ItemData(Item.size()+1,"USB Flashdisk Kingston 16GB",600000));//9
        Item.add(new ItemData(Item.size()+1,"USB Flashdisk Kingston 32GB",800000));//10

    }
    //////////////////////////////////////////////MenuStrip///////////////////////////////////////////////

    private final JInternalFrame frameTransaction = new JInternalFrame(
            "Transaction", true, true, true, true );
    private final JInternalFrame frameItemData = new JInternalFrame(
            "Item Data", true, true, true, true );
    private final JInternalFrame framePrintReceipt = new JInternalFrame(
            "Print Preview", true, true, false, true );
    private JMenuItem jMILogin;
    private JButton jBTransaction;
    private JButton jBItemData;
    Dimension dimin = new Dimension(500, 500);
    Dimension dimin1 = new Dimension(310, 380);
    Dimension dimax1 = new Dimension(400, 400);
    public JMenuBar MenuStrip()
    {
        frameLogin.setUser(User);
        frameItemData.setSize(dimin);
        frameItemData.setMinimumSize(dimin);
        frameItemData.add( EditDataForm(), BorderLayout.CENTER ); // add panel
        frameItemData.pack(); // set internal frame to size of contents

        frameTransaction.setSize(dimin);
        frameTransaction.setMinimumSize(dimin);
        frameTransaction.add( TransactionForm(), BorderLayout.CENTER ); // add panel
        frameTransaction.pack(); // set internal frame to size of contents

        framePrintReceipt.setSize(dimin1);
        framePrintReceipt.setMinimumSize(dimin1);
        framePrintReceipt.setMaximumSize(dimax1);
        framePrintReceipt.add( ReceiptPrint(), BorderLayout.CENTER ); // add panel
        framePrintReceipt.pack(); // set internal frame to size of contents


        JMenuBar jMBMenuStrip = new JMenuBar();
        JMenu jMFile = new JMenu("File");
        jMILogin = new JMenuItem("Login");
        jMILogin.addActionListener((ActionEvent event) -> {
            // create internal frame
            if(jMILogin.getText().equals("Login"))
            {

                if(!frameLogin.isVisible() )
                {
                    try{
                        theDesktop.add( frameLogin ); // attach internal frame
                        // show internal frame
                        Dimension desktopSize = theDesktop.getSize();
                        Dimension jInternalFrameSize = frameLogin.getSize();
                        frameLogin.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                                (desktopSize.height- jInternalFrameSize.height)/2);
                        frameLogin.setVisible( true );
                    }catch(IllegalArgumentException e)
                    {

                        frameLogin.setVisible(true);
                        frameLogin.toFront();
                        frameLogin.repaint();
                        theDesktop.repaint();
                    }
                }
            }
            if(jMILogin.getText().equals("Logout"))
            {
                jMILogin.setText("Login");
                jBItemData.setEnabled(false);
                jBTransaction.setEnabled(false);
                theDesktop.remove(frameLogin);
                //frameLogin.setVisible(false);
                frameTransaction.dispose();
                frameItemData.dispose();
                framePrintReceipt.dispose();
                frameLogin.clearPasswordField();

            }
        });
        JMenuItem jMIExit  = new JMenuItem("Exit");
        jMIExit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        jMFile.add(jMILogin);
        jMFile.add(jMIExit);
        jMBMenuStrip.add(jMFile);
        jBTransaction = new JButton("Transaction");
        jBTransaction.addActionListener((ActionEvent event) -> {
            try{

                theDesktop.add( frameTransaction ); // attach internal frame
                // show internal frame
                Dimension desktopSize = theDesktop.getSize();
                Dimension jInternalFrameSize = frameTransaction.getSize();
                frameTransaction.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                        (desktopSize.height- jInternalFrameSize.height+120)/2);
                frameTransaction.setVisible( true );
            }catch (IllegalArgumentException e)
            {
                frameTransaction.setVisible(true);
                frameTransaction.toFront();
                frameItemData.toBack();
                frameTransaction.repaint();
                theDesktop.repaint();

            }
        });
        jBTransaction.setEnabled(false);
        jMBMenuStrip.add(jBTransaction);
        jBItemData = new JButton("Item Data");
        jBItemData.addActionListener((ActionEvent event) -> {
            try{
                theDesktop.add( frameItemData ); // attach internal frame
                // show internal frame
                Dimension desktopSize = theDesktop.getSize();
                Dimension jInternalFrameSize = frameItemData.getSize();
                frameItemData.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                        (desktopSize.height - jInternalFrameSize.height+60)/2);
                frameItemData.setVisible( true );
            }catch (IllegalArgumentException e)
            {
                frameItemData.setVisible(true);
                frameItemData.toFront();
                frameTransaction.toBack();
                frameItemData.repaint();
                theDesktop.repaint();
            }
        });
        jBItemData.setEnabled(false);
        jMBMenuStrip.add(jBItemData);
        JButton jBAbout = new JButton("About");
        jBAbout.addActionListener((ActionEvent event) -> {
           aboutform.show();
        });
        jMBMenuStrip.add(jBAbout);
        return jMBMenuStrip;
    }

    /////////////////////////////////////////TransactionForm//////////////////////////////////////////
    private JTable jTTransactionForm;
    Object[][] dataTransactionForm ;
    private JTextField jTFTotalTotalPrice;
    private TableRowSorter<TableModel> rowSorterTransactionForm;
    private DefaultTableModel modelDataTransaction;
    public JPanel TransactionForm()
    {
        Receipt = new ArrayList<>();
        dataTransactionForm = new Object [Receipt.size()][];
        JPanel jPTransactionNorth = new JPanel(new GridLayout(2, 1,1,1));
        JPanel jPBNewTransaction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jBNewTransaction = new JButton("New Transaction");
        jBNewTransaction.addActionListener((ActionEvent e) -> {
            if(!Receipt.isEmpty())
            {
                Receipt.clear();
            }
            if(modelDataTransaction.getRowCount()!=0)
            {
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
                //return true;
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
                    Receipt.stream().filter((me) -> (getItemID==me.getID())).forEach((me) -> {
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
                    for (int i = 0; i < Receipt.size(); i++) {

                        ArrayList<Object> row1 = new ArrayList<>() ;
                        row1.add(count1);
                        row1.add(Receipt.get(i).getID());
                        row1.add(Receipt.get(i).getItemName());
                        row1.add(Receipt.get(i).getItemPrice());
                        row1.add(Receipt.get(i).getItemQuantity());
                        row1.add(Receipt.get(i).getItemTotalPrice());
                        modelDataTransaction.addRow(row1.toArray(new Object[row1.size()]));
                        count1++;
                    }

                    int TotalTotalPrice = 0;
                    TotalTotalPrice = Receipt.stream().map((me) -> me.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);
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
        TotalTotalPrice = Receipt.stream().map((me) -> me.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);

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

                theDesktop.add( framePrintReceipt ); // attach internal frame
                // show internal frame
                Dimension desktopSize = theDesktop.getSize();
                Dimension jInternalFrameSize = framePrintReceipt.getSize();
                framePrintReceipt.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                        (desktopSize.height - jInternalFrameSize.height)/2);
                framePrintReceipt.setVisible( true );

                //update jTextArea
                text="";
                datatotext="";
                jTAAboutScript.setText(null);
                jTAAboutScript.append(LoadReceiptScript());

            }catch (IllegalArgumentException ef)
            {
                framePrintReceipt.setVisible(true);
                framePrintReceipt.toFront();
                framePrintReceipt.repaint();
                theDesktop.repaint();
            }
        });
        jPButtonExport.add(jBExport);
        jPButtonExport.add(jBPrint);

        jPTransactionSouth.add(jPTotalTotalPrice);
        jPTransactionSouth.add(jPButtonExport);

        frame.add(jPTransactionNorth/*,BorderLayout.NORTH*/);
        frame.add(scroll/*, BorderLayout.CENTER*/);
        frame.add(jPTransactionSouth/*, BorderLayout.SOUTH*/);
        frame.setVisible(true);
        return frame;
    }


    ////////////////////////////////////////DataTransactionEdit////////////////////////////////////////
    private JTextField jTFTransactionID;
    private JSpinner jSTransactionQty;
    private JTextField jTFTransactionDelete;
    private JLabel jLDeletedIndicator;
    private final SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
    public JPanel DataTransaction()
    {
        JPanel jPDataTransactionEdit = new JPanel(new GridLayout(2,1,2,2));

        JPanel jPAddDataTransaction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jLItemID = new JLabel("Item ID :");
        jTFTransactionID = new JTextField(5);
        jTFTransactionID.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel jLItemQty = new JLabel("Item Quantity :");
        jSTransactionQty = new JSpinner(spinnerModel);
        JButton jBAddDataTransaction = new JButton("Add Transaction");
        jBAddDataTransaction.addActionListener((ActionEvent e) -> {
            if (jTFTransactionID.getText().equals(""))
            {
                String string = String.format("Item ID cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (jTFTransactionID.getText().contains(" "))
            {
                String string = String.format("Item ID cannot contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (!jTFTransactionID.getText().matches("[0-9]*"))
            {
                String string = String.format("Item ID must only contain number");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }

            else
            { int TID = Integer.parseInt(jTFTransactionID.getText());
                boolean isntRedundant=true;
                for(ReceiptData tar:Receipt)
                {
                    isntRedundant = TID != tar.getID();
                }
                if(TID>Item.size()||TID<1)
                {
                    String string = String.format("Item ID not found");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }
                else if(!isntRedundant)
                {
                    String string = String.format("Item already added");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    Item.stream().filter((im) -> (TID == im.getID())).forEach((im) -> {
                        Receipt.add(new ReceiptData(im.getID(),im.getItemName(),im.getItemPrice(),(int) jSTransactionQty.getValue()));
                    });

                    System.out.format("%3s%3s%30s%12s%10s%12s\n","No.","ID","Item Name","Item Price","Item Qty.","Total/item");
                    int j=1;
                    for(ReceiptData me: Receipt) {
                        System.out.format("%3d%3s%30s%12s%10s%12s\n",j, me.getID(),me.getItemName(),me.getItemPrice(),me.getItemQuantity(),me.getItemTotalPrice());j++;
                    }


                    ArrayList<Object> row = new ArrayList<>() ;



                    row.add(Receipt.size());
                    row.add(Receipt.get(Receipt.size()-1).getID());
                    row.add(Receipt.get(Receipt.size()-1).getItemName());
                    row.add(Receipt.get(Receipt.size()-1).getItemPrice());
                    row.add(Receipt.get(Receipt.size()-1).getItemQuantity());
                    row.add(Receipt.get(Receipt.size()-1).getItemTotalPrice());
                    modelDataTransaction.addRow(row.toArray(new Object[row.size()]));


                    row.clear();
                }
                int TotalTotalPrice = 0;
                TotalTotalPrice = Receipt.stream().map((me) -> me.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);
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
            if (jTFTransactionDelete.getText().equals(""))
            {
                String string = String.format("Item ID cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (jTFTransactionDelete.getText().contains(" "))
            {
                String string = String.format("Item ID cannot contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (!jTFTransactionDelete.getText().matches("[0-9]*"))
            {
                String string = String.format("Item ID must only contain number");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }

            else
            { int DID = Integer.parseInt(jTFTransactionDelete.getText());

                if(DID>Item.size()||DID<1)
                {
                    String string = String.format("Item ID invalid");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }
                else /*if(  rowSorterTransactionForm.getRowFilter() != null)*/
                {
                    Iterator<ReceiptData> iter = Receipt.iterator();
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
                    for (int i = 0; i < Receipt.size(); i++) {

                        ArrayList<Object> row = new ArrayList<>() ;
                        row.add(count1);
                        row.add(Receipt.get(i).getID());
                        row.add(Receipt.get(i).getItemName());
                        row.add(Receipt.get(i).getItemPrice());
                        row.add(Receipt.get(i).getItemQuantity());
                        row.add(Receipt.get(i).getItemTotalPrice());
                        modelDataTransaction.addRow(row.toArray(new Object[row.size()]));
                        count1++;
                    }

                    ///recount total price
                    int TotalTotalPrice = 0;
                    TotalTotalPrice = Receipt.stream().map((reme) -> reme.getItemTotalPrice()).reduce(TotalTotalPrice, Integer::sum);
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
    public void ShowDeletedIndicator()
    {
        int delay = 1500; //milliseconds
        ActionListener taskPerformer = (ActionEvent evt) -> {

            jLDeletedIndicator.setVisible(false);

        };
        new Timer(delay, taskPerformer).start();

    }

    /////////////////////////////////////////EditDataForm//////////////////////////////////////////
    private JTable jTEditDataForm;
    private JPanel jPEditDataForm;
    private JTextField jTFItemName;
    private JTextField jTFSearch;
    //private JTextField jTFItemPrice;
    private Object[][] dataEditDataForm;
    private TableRowSorter<TableModel> rowSorterEditDataForm;
    private JTextField jTFItemDelete;
    private  DefaultTableModel modelEditData ;
    private JLabel jLDeletedIndicatorItem;
    public JPanel EditDataForm()
    {
        dataEditDataForm = new Object[Item.size()][];
        for (int i = 0; i < Item.size(); i++) {

            ArrayList<Object> row = new ArrayList<>() ;
            row.add(Item.get(i).getID());
            row.add(Item.get(i).getItemName());
            row.add(Item.get(i).getItemPrice());
            // row.add(ItemSelect.get(i));
            dataEditDataForm[i] = row.toArray(new Object[row.size()]);

        }
        String[] headers = {"ID","Item Name","Item Price"/*,"Pick Up?"*/};
        jPEditDataForm = new JPanel(new GridLayout(3, 1, 2, 2));

        modelEditData = new DefaultTableModel(dataEditDataForm,headers);
        jTEditDataForm = new JTable(modelEditData){

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return Integer.class;
                   /* case 3:
                        return Boolean.class;*/
                    default:
                        return Boolean.class;
                }

            }
            @Override
            public boolean isCellEditable (int row, int column)
            {
                return false;
                //return column==2;
                /*   if (column == 0) {
                    return false;
                 }  else {
                    return true;
                 }
             */
            }
        };
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
                exp.exportTable(jTEditDataForm, new File ("D:ItemList.xls"));
            }
            catch(IOException e){
                e.getMessage();
            }
        });
        jPButtonExport.add(jBExport);
        /////////////////////////////////////Add New Data/////////////////////////////////
        JPanel jPEditDataNorth = new JPanel(new GridLayout(2,1,1,1));
        JPanel jPAddData = new JPanel(new GridLayout(2, 1,1,1));

        //JLabel jLAddData = new JLabel("Add New Data",SwingConstants.CENTER );
        JPanel jPAddDataInput = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jLItemName = new JLabel("Item Name :");
        jTFItemName = new JTextField(12);
        JLabel jLItemPrice = new JLabel("Item Price :");

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(null);//?
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        JFormattedTextField jTFItemPrice = new JFormattedTextField(formatter);//http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4832257
        //jTFItemPrice.setValue(null);//?
        //jTFItemPrice.setFocusLostBehavior(JFormattedTextField.PERSIST);
        jTFItemPrice.setColumns(12);
        JButton jBAddData = new JButton("Add");
        jBAddData.addActionListener((ActionEvent e) -> {
            if(jTFItemName.getText().equals("")&&jTFItemPrice.getText().equals(""))
            {
                String string = String.format("Item name and price cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(jTFItemName.getText().equals(""))
            {
                String string = String.format("Item name cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (jTFItemPrice.getText().equals(""))
            {
                String string = String.format("Item price cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (jTFItemPrice.getText().contains(" "))
            {
                String string = String.format("Item price cannot contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }

            else
            {
                boolean isntRedundant=true;
                for(ItemData tar:Item)
                {
                    isntRedundant = !jTFItemName.getText().equals(tar.getItemName());
                }
                if(!isntRedundant)
                {
                    String string = String.format("Item already added !");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    Item.add(new ItemData(Item.size()+1,jTFItemName.getText(), (int)jTFItemPrice.getValue()));
                    String string = String.format("Data successfully added !");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                    ArrayList<Object> row = new ArrayList<>() ;
                    row.add(Item.get(Item.size()-1).getID());
                    row.add(Item.get(Item.size()-1).getItemName());
                    row.add(Item.get(Item.size()-1).getItemPrice());

                    modelEditData.addRow(row.toArray(new Object[row.size()]));
                    row.clear();
                    jTFItemName.setText("");
                    jTFItemPrice.setValue(null);
                    System.out.format("%3s%30s%12s\n","No.","Item Name","Item Price");
                    Item.stream().forEach((me) -> {
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
            if (jTFItemDelete.getText().equals(""))
            {
                String string = String.format("Item ID cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (jTFItemDelete.getText().contains(" "))
            {
                String string = String.format("Item ID cannot contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (!jTFItemDelete.getText().matches("[0-9]*"))
            {
                String string = String.format("Item ID must only contain number");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }

            else
            { int DID = Integer.parseInt(jTFItemDelete.getText());

                if(DID>Item.size()||DID<1)
                {
                    String string = String.format("Item ID invalid");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }
                else /*if(  rowSorterTransactionForm.getRowFilter() != null)*/
                {
                    Iterator<ItemData> iter = Item.iterator();
                    while(iter.hasNext()) {
                        ItemData rDIter = iter.next();
                        if(rDIter.getID()==DID) {
                            iter.remove(); // Removes the 'current' item
                        }
                    }
                    jLDeletedIndicatorItem.setVisible(true);

                    ///recount ID of Item
                    int count=1;
                    for (ItemData me: Item)
                    {
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

                    for (int i = 0; i < Item.size(); i++) {

                        ArrayList<Object> row = new ArrayList<>() ;

                        row.add(Item.get(i).getID());
                        row.add(Item.get(i).getItemName());
                        row.add(Item.get(i).getItemPrice());

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
    public void ShowDeletedIndicatorItem()
    {
        int delay = 1500; //milliseconds
        ActionListener taskPerformer = (ActionEvent evt) -> {

            jLDeletedIndicatorItem.setVisible(false);

        };
        new Timer(delay, taskPerformer).start();

    }
    //////////////////////////////////////////ReceiptForm////////////////////////////////////////
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
    public String LoadReceiptScript()
    {
        int count=1;
        for(ReceiptData me : Receipt)
        {
            datatotext += String.format("%3d%50s%12d%12d%12d\n",count,me.getItemName(),me.getItemPrice(),me.getItemQuantity(),me.getItemTotalPrice());
            count++;
        }
        text= title+header+datatotext+
                String.format("========================================================\n\t\t\t%7s%12s\n", "Total :",jTFTotalTotalPrice.getText());
        return text;
    }
    public JPanel ReceiptPrint()
    { JPanel jPReceiptView = new JPanel(new GridLayout(1, 1));

        box= Box.createVerticalBox();
        //jTAAboutScript.append(LoadReceiptScript);




        jTAAboutScript= new JTextArea(text,18,25);

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

}
