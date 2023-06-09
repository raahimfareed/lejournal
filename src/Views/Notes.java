package Views;

import Components.*;
import Components.Button;
import Models.Note;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Notes extends View {
    public Notes() {
        this.setLayout(new BorderLayout());
        this.add(new Sidenav(), BorderLayout.WEST);

        ViewManager viewManager = ViewManager.getInstance();
        CardLayout cardLayout = (CardLayout) viewManager.getLayout();

        JPanel main = new JPanel();

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JLabel greeting = new JLabel("Notes");

        Font greetingFont = new Font("Poppins", Font.BOLD, 24);
        greeting.setFont(greetingFont);

        Button newNoteBtn = new Button("Add Note");
        newNoteBtn.setOnAction(actionEvent -> cardLayout.show(viewManager, "AddNote"));

        String[] columnNames = {"ID", "Title", "Created At", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable notesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(notesTable);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        final List<Note> notes = session.createQuery("FROM Note", Note.class).getResultList();

        for (Note note : notes) {
            Button updateBtn = new Button("Update");
            Button deleteBtn = new Button("Delete");
            deleteBtn.setBackground(new Color(82, 39, 39, 255));
            deleteBtn.setForeground(new Color(239, 156, 156, 255));
            JPanel btnContainer = new JPanel(new FlowLayout());
            btnContainer.add(updateBtn);
            btnContainer.add(deleteBtn);
            Object[] rowData = {note.getId(), note.getTitle(), note.getCreatedAt(), btnContainer};
            tableModel.addRow(rowData);
        }

        main.add(Box.createVerticalStrut(10));
        main.add(greeting);
        main.add(Box.createVerticalStrut(10));
        main.add(newNoteBtn);
        main.add(Box.createVerticalStrut(10));
        main.add(scrollPane);
        main.add(Box.createHorizontalStrut(5));

        this.add(main, BorderLayout.CENTER);
    }
}
