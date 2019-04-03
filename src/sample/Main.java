package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


        Casheer casheer = new Casheer();
        casheer.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        casheer.setSize( 600, 480 ); // set frame size
        Dimension dim = new Dimension(600, 480);
        casheer.setMinimumSize(dim);
        casheer.setVisible( true ); // display frame

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - casheer.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - casheer.getHeight()) / 2);
        casheer.setLocation(x, y);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
