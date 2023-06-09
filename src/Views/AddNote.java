package Views;

import Components.*;

import javax.swing.*;
import java.awt.*;

import Components.Button;
import com.formdev.flatlaf.icons.FlatAscendingSortIcon;
import com.formdev.flatlaf.icons.FlatFileViewDirectoryIcon;
import com.formdev.flatlaf.icons.FlatMenuArrowIcon;
import com.formdev.flatlaf.ui.FlatArrowButton;

public class AddNote extends View {
    public AddNote() {
        this.setLayout(new BorderLayout());
        this.add(new Sidenav(), BorderLayout.WEST);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        BackIcon icon = new BackIcon();
        Button backBtn = new Button("");
        backBtn.setIcon(icon);
        ViewManager viewManager = ViewManager.getInstance();
        CardLayout cardLayout = (CardLayout) viewManager.getLayout();
        backBtn.setOnAction(e -> {
            cardLayout.show(viewManager, "Notes");
        });

        JLabel greeting = new JLabel("Add Note");

        Font greetingFont = new Font("Poppins", Font.BOLD, 24);
        greeting.setFont(greetingFont);

        main.add(Box.createVerticalStrut(10));
        main.add(backBtn);
        main.add(Box.createVerticalStrut(10));
        main.add(greeting);
        main.add(Box.createHorizontalStrut(5));
        this.add(main, BorderLayout.CENTER);
    }
}
