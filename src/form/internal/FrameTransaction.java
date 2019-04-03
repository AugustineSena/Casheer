package form.internal;

import misc.CellSpinner;
import misc.ExcelExport;
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
    private JTable jTTransactionForm;
    private Object[][] dataTransactionForm ;
    private JTextField jTFTotalTotalPrice;
    private TableRowSorter<TableModel> rowSorterTransactionForm;
    private DefaultTableModel modelDataTransaction;
    private ArrayList <ReceiptData> Receipt = new ArrayList<>();
    public FrameTransaction(){
        super("Transaction", true, true, true, true );
    }

}

