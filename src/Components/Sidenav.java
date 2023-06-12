package Components;

import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sidenav extends JPanel {
    public Sidenav() {
        Dotenv dotenv = Dotenv.load();

        ViewManager viewManager = ViewManager.getInstance();
        CardLayout cardLayout = (CardLayout) viewManager.getLayout();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(0, 0, 0, 100));
        this.setPreferredSize(new Dimension(200, this.getPreferredSize().height));

        JLabel thisHeading = new JLabel(dotenv.get("APP_NAME"));
        Font headingFont = new Font("Poppins", Font.BOLD, 16);
        thisHeading.setFont(headingFont);

        JLabel homeBtn = new JLabel("Home");
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, homeBtn.getPreferredSize().height));
        homeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            cardLayout.show(viewManager, "Dashboard");
            }
        });

        JLabel notesBtn = new JLabel("Notes");
        notesBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, notesBtn.getPreferredSize().height));
        notesBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        notesBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            cardLayout.show(viewManager, "Notes");
            }
        });

        //added calendarApp button
        JLabel calendarBtn = new JLabel("To-Do Calendar");
        calendarBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, calendarBtn.getPreferredSize().height));
        calendarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        calendarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(viewManager, "CalendarApp");
            }
        });

        this.add(Box.createVerticalStrut(10));
        this.add(thisHeading);
        this.add(Box.createVerticalStrut(10));
        this.add(homeBtn);
        this.add(Box.createVerticalStrut(10));
        this.add(notesBtn);
        this.add(Box.createVerticalStrut(10));
        this.add(calendarBtn);
        this.add(Box.createHorizontalStrut(10));
    }
}
