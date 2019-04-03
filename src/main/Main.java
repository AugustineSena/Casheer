package main;

import form.CasheerForm;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // TODO code application logic here
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                // not worth my time
            }
        }
        CasheerForm casheerForm = new CasheerForm();
        casheerForm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        casheerForm.setSize( 600, 480 ); // set frame size
        Dimension dim = new Dimension(600, 480);
        casheerForm.setMinimumSize(dim);
        casheerForm.setVisible( true ); // display frame
        //start window on the center of the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - casheerForm.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - casheerForm.getHeight()) / 2);
        casheerForm.setLocation(x, y);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
