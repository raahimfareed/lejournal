package Views;

import Components.View;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JLabel greeting = new JLabel("Hey there :D");
        Font greetingFont = new Font("Poppins", Font.BOLD, 24);
        greeting.setFont(greetingFont);
        final int[] seconds = {0};
        JLabel timeLabel = new JLabel("0 seconds");
        JLabel timeHeading = new JLabel("Time Spent");
        Font timeFont = new Font("Poppins", Font.BOLD, 14);
        timeHeading.setFont(timeFont);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ++seconds[0];
                if (seconds[0] < 60) {
                    timeLabel.setText(String.format("%d seconds", seconds[0]));
                    return;
                }

                if (seconds[0] / 60 < 60) {
                    timeLabel.setText(String.format("%d minutes", seconds[0] / 60));
                    return;
                }

                timeLabel.setText(String.format("%d hours", seconds[0] / (60 * 60)));
            }
        });
        timer.start();


        main.add(Box.createVerticalStrut(10));
        main.add(greeting);
        main.add(Box.createVerticalStrut(10));
        main.add(timeHeading);
        main.add(timeLabel);
        main.add(Box.createHorizontalStrut(5));
        this.add(main, BorderLayout.CENTER);
    }
}
