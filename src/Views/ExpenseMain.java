package Views;

import Components.*;
import Components.Button;
import Models.Note;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExpenseMain extends View {
    public ExpenseMain() {
        this.setLayout(new BorderLayout());
        this.add(new Sidenav(), BorderLayout.WEST);
        ViewManager viewManager = ViewManager.getInstance();
        CardLayout cardLayout = (CardLayout) viewManager.getLayout();
        // Create the label and center it
        JLabel title = new JLabel("Expense Tracker");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        this.add(title, BorderLayout.NORTH);

        // Create the panel for labels and text fields
        JPanel main = new JPanel(new GridLayout(0, 3, 5, 0));
        // Create labels & buttons
        JLabel petrol = new JLabel("Petrol");
        JLabel food = new JLabel("Food");
        JLabel credit = new JLabel("Phone Credit");
        JButton add_record = new JButton("Add Record");
        JButton generate = new JButton("Generate Report");
        // Create text fields
        JTextField jtf1 = new JTextField();
        JTextField jtf2 = new JTextField();
        JTextField jtf3 = new JTextField();
        main.add(petrol);
        main.add(food);
        main.add(credit);
        main.add(add_record);
        main.add(jtf1);
        main.add(jtf2);
        main.add(jtf3);
        main.add(generate);
        this.add(main, BorderLayout.CENTER);
        add_record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(viewManager, "AddRecord");
            }
        });

    }

    public DefaultTableModel getTableModel() {
        return null;
    }
}
