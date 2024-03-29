package Views;

import Components.HibernateUtil;
import Components.Sidenav;
import Components.View;
import Components.ViewManager;
import Models.Config;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Dashboard extends View {
    public Dashboard() {
        this.setLayout(new BorderLayout());


        this.add(new Sidenav(), BorderLayout.WEST);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JLabel greeting = new JLabel("Hey there :D");
        Font greetingFont = new Font("Poppins", Font.BOLD, 24);
        greeting.setFont(greetingFont);
        final int[] seconds = {0};
        final Config[] config = {session.createQuery("FROM Config WHERE key = :timeSpent", Config.class)
                .setParameter("timeSpent", "time_spent")
                .setMaxResults(1)
                .uniqueResult()};
        if (config[0] != null) {
            seconds[0] = Integer.parseInt(config[0].getValue());
        }
        JLabel timeLabel = new JLabel("0 seconds");
        JLabel timeHeading = new JLabel("Time Spent");
        Font timeFont = new Font("Poppins", Font.BOLD, 14);
        timeHeading.setFont(timeFont);

        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds[0] += 5;
                Transaction tx = session.beginTransaction();
                if (config[0] == null) {
                    config[0] = new Config("time_spent", Integer.toString(seconds[0]));
                    session.persist(config[0]);
                } else {
                    config[0].setValue(Integer.toString(seconds[0]));
                    session.merge(config[0]);
                }
                tx.commit();
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

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                timer.stop();

                session.close();
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
