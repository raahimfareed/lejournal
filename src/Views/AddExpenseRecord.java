package Views;

import Components.*;
import Components.Button;
import Models.Expense;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class AddExpenseRecord extends View {
    // table model with columns

    public AddExpenseRecord() {
        this.setLayout(new BorderLayout());
        this.add(new Sidenav(), BorderLayout.WEST);

        ViewManager viewManager = ViewManager.getInstance();
        CardLayout cardLayout = (CardLayout) viewManager.getLayout();

        JLabel title = new JLabel("Expense Record Table ");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        this.add(title, BorderLayout.NORTH);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        BackIcon icon = new BackIcon();
        Button backBtn = new Button("");
        backBtn.setIcon(icon);
        backBtn.setOnAction(e -> cardLayout.show(viewManager, "ExpenseMain"));

        // Create labels and text fields
        JLabel label1 = new JLabel("Food");
        JTextField jtf1 = new JTextField();

        JLabel label2 = new JLabel("Petrol");
        JTextField jtf2 = new JTextField();

        JLabel label3 = new JLabel("Credit");
        JTextField jtf3 = new JTextField();

        // Align labels and text fields using BoxLayout
        main.add(label1);
        main.add(jtf1);
        main.add(Box.createVerticalStrut(10));

        main.add(label2);
        main.add(jtf2);
        main.add(Box.createVerticalStrut(10));

        main.add(label3);
        main.add(jtf3);

        Button addExpense = new Button("Add Expense");
        addExpense.setOnAction(e -> this.createExpense(jtf1, jtf2, jtf3));

        main.add(Box.createVerticalStrut(10));
        main.add(addExpense);
        main.add(backBtn);
        this.add(main, BorderLayout.CENTER);
    }

    public void createExpense(JTextField food, JTextField petrol, JTextField credit) {
        if (food.getText().isBlank() || petrol.getText().isBlank() || credit.getText().isBlank()) {
            return;
        }
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Expense expense = new Expense(food.getText(), petrol.getText(), credit.getText());
        session.persist(expense);
        tx.commit();
        clearInputs(food, petrol, credit);
        ViewManager viewManager = ViewManager.getInstance();
        ExpenseMain expenseMain = (ExpenseMain) viewManager.getView("ExpenseMain");
        viewManager.changeView("ExpenseMain");
    }

    public void clearInputs(JTextField food, JTextField petrol, JTextField credit) {
        food.setText("");
        petrol.setText("");
        credit.setText("");
    }
}
