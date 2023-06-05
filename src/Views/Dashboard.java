package Views;

import Components.View;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends View {
    public Dashboard() {
        Dotenv dotenv = Dotenv.load();
        this.setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(255, 255, 255, 10));
        sidebar.setPreferredSize(new Dimension(200, sidebar.getPreferredSize().height));

        JLabel sidebarHeading = new JLabel(dotenv.get("APP_NAME"));
        Font headingFont = new Font("Poppins", Font.BOLD, 16);
        sidebarHeading.setFont(headingFont);
        JLabel homeBtn = new JLabel("Home");
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, homeBtn.getPreferredSize().height));
        JLabel notesBtn = new JLabel("Notes");
        notesBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, notesBtn.getPreferredSize().height));
        notesBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(sidebarHeading);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(homeBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(notesBtn);
        sidebar.add(Box.createHorizontalStrut(10));

        this.add(sidebar, BorderLayout.WEST);




        JPanel main = new JPanel();
        JLabel greeting = new JLabel("Hey there :D");
        Font greetingFont = new Font("Poppins", Font.BOLD, 24);
        greeting.setFont(greetingFont);
        main.add(greeting);
        this.add(main, BorderLayout.CENTER);
    }
}
