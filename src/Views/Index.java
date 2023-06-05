package Views;

import Components.Button;
import Components.View;

import javax.swing.*;
import java.awt.*;

import Components.ViewManager;
import io.github.cdimascio.dotenv.Dotenv;

public class Index extends View {
    public Index() {
        // Load dotenv variables for use
        Dotenv dotenv = Dotenv.load();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String title = dotenv.get("APP_NAME");
        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font panelFont = new Font("Poppins", Font.BOLD, 24);
        titleLabel.setFont(panelFont);

        Button button = new Button("Enter");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue());
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(5));
        this.add(button);
        this.add(Box.createVerticalGlue());

        // Get the view manager to be used for button event
        ViewManager viewManager = ViewManager.getInstance();
        CardLayout cardLayout = (CardLayout) viewManager.getLayout();
        button.setOnAction(actionEvent -> cardLayout.show(viewManager, "Dashboard"));
    }
}
