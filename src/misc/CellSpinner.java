/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author SENA
 */
public class CellSpinner extends DefaultCellEditor{
   
        JSpinner spinner;
        SpinnerModel spinnerModel;
        JSpinner.DefaultEditor editor;
        JTextField textField;
        boolean valueSet;
        
        // Initializes the spinner.
        public CellSpinner() {
            super(new JTextField());
            spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
            spinner = new JSpinner(spinnerModel);
            
            editor = ((JSpinner.DefaultEditor)spinner.getEditor());
            textField = editor.getTextField();
            //textField.setBounds(-1, -1, 50, 50);
            textField.addFocusListener( new FocusListener() {
                @Override
                public void focusGained( FocusEvent fe ) {
                    System.err.println("Got focus");
                    //textField.setSelectionStart(0);
                    //textField.setSelectionEnd(1);
                    SwingUtilities.invokeLater(() -> {
                        if ( valueSet ) {
                            
                            textField.setCaretPosition(1);
                            
                        }
                    });
                }
                @Override
                public void focusLost( FocusEvent fe ) {
                }
            });
            textField.addActionListener((ActionEvent ae) -> {
                stopCellEditing();
            });
        }

        // Prepares the spinner component and returns it.
        @Override
        public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column
        ) {
            if ( !valueSet ) {
                spinner.setValue(value);
            }
            SwingUtilities.invokeLater(() -> {
                textField.requestFocus();
            });
            return spinner;
        }

        @Override
        public boolean isCellEditable( EventObject eo ) {
            System.err.println("isCellEditable? true");
            if ( eo instanceof KeyEvent ) {
                KeyEvent ke = (KeyEvent)eo;
                System.err.println("key event: "+ke.getKeyChar());
                textField.setText(String.valueOf(ke.getKeyChar()));
                valueSet = true;
            } else {
                valueSet = false;
            }
            return true;
        }

        // Returns the spinners current value.
        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }

        @Override
        public boolean stopCellEditing() {
            System.err.println("Stopping edit");
            try {
                editor.commitEdit();
                spinner.commitEdit();
            } catch ( java.text.ParseException e ) {
                JOptionPane.showMessageDialog(null,
                    "Invalid value, discarding.");
            }
            return super.stopCellEditing();
        }
    
}
