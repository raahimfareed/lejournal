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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ExpenseMain extends View {
    private final String[] columnNames = {"Food", "Petrol", "Credit"};
    private DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

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
        JPanel main = new JPanel();

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        Button expenseBtn = new Button("Add Expense Record");
        expenseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(viewManager, "AddExpenseRecord");
            }
        });


        JTable expenseTable = new JTable(tableModel);
        expenseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = expenseTable.rowAtPoint(e.getPoint());
                if (row < 0) return;

                int id = (int) expenseTable.getValueAt(row, 0);
                System.out.println(id);
                viewManager.changeView("AddExpenseRecord");
            }
        });
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        main.add(Box.createVerticalStrut(10));
        main.add(expenseBtn);
        main.add(Box.createVerticalStrut(10));
        main.add(scrollPane);
        main.add(Box.createHorizontalStrut(5));
        this.add(main, BorderLayout.CENTER);

    }
}