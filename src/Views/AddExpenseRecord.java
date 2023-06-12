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
        title.setFont(new Font("Poppins", Font.BOLD, 24));

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        BackIcon icon = new BackIcon();
        Button backBtn = new Button("");
        backBtn.setIcon(icon);
        backBtn.setOnAction(e -> cardLayout.show(viewManager, "Expense"));
        main.add(Box.createVerticalStrut(10));
        main.add(title);

        JTextField jtf1= new JTextField();
        jtf1.setColumns(10);
        JTextField jtf2= new JTextField();
        JTextField jtf3= new JTextField();

        JLabel label1 = new JLabel("Food");
        main.add(label1);
        main.add(jtf1);
        main.add(Box.createVerticalStrut(10));

        JLabel label2 = new JLabel("Petrol");
        main.add(label2);
        main.add(jtf2);
        main.add(Box.createVerticalStrut(10));

        JLabel label3 = new JLabel("Credit");
        main.add(label3);
        main.add(jtf3);

        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jtf1.setAlignmentX(Component.LEFT_ALIGNMENT);

        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        jtf2.setAlignmentX(Component.LEFT_ALIGNMENT);

        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
        jtf3.setAlignmentX(Component.LEFT_ALIGNMENT);

        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        Button addExpense = new Button("Add Expense");
        addExpense.setOnAction(e -> this.createExpense(jtf1, jtf2, jtf3));

        main.add(Box.createVerticalStrut(10));
        main.add(addExpense);
        main.add(Box.createVerticalStrut(10));
        main.add(backBtn);
        main.add(Box.createHorizontalStrut(5));
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
