import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;

import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import Components.Button;
import Components.View;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();

        try {
            File fontFile = new File("fonts/Poppins-Regular.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            Font defaultFont = new Font("Poppins", Font.PLAIN, 12);
            UIManager.put("Label.font", defaultFont);
            UIManager.put("Button.font", defaultFont);
            UIManager.put("TextField.font", defaultFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            FlatIntelliJLaf.setup(new FlatOneDarkIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        int width = Integer.parseInt(dotenv.get("WINDOW_WIDTH"));
        int height = Integer.parseInt(dotenv.get("WINDOW_HEIGHT"));
        String title = dotenv.get("APP_NAME");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setSize(width, height);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ViewManager viewManager = new ViewManager();

            View indexView = new View();
            indexView.setLayout(new BoxLayout(indexView, BoxLayout.Y_AXIS));

            JLabel titleLabel = new JLabel(title);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            Font panelFont = new Font("Poppins", Font.BOLD, 24);
            titleLabel.setFont(panelFont);

            Button button = new Button("Enter");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            indexView.add(Box.createVerticalGlue());
            indexView.add(titleLabel);
            indexView.add(Box.createVerticalStrut(5));
            indexView.add(button);
            indexView.add(Box.createVerticalGlue());

            View testView = new View();
            JLabel testLabel = new JLabel("Hello, World!");
            testView.add(testLabel);

            viewManager.addView(indexView, "Index");
            viewManager.addView(testView, "Test");

            CardLayout cardLayout = (CardLayout) viewManager.getLayout();
            cardLayout.show(viewManager, "Index");

            button.setOnAction(actionEvent -> cardLayout.show(viewManager, "Test"));

            frame.add(viewManager);


            frame.setVisible(true);
        });
    }
}
