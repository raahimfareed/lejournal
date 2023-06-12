package Views;

import Components.*;
import Components.Button;
import Models.Note;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Notes extends View {
    private List<Note> notes = new ArrayList<>();
    private final String[] columnNames = {"ID", "Title", "Created At"};
    private DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

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

        JTable notesTable = new JTable(tableModel);
        notesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = notesTable.rowAtPoint(e.getPoint());
                if (row < 0) return;

                int id = (int) notesTable.getValueAt(row, 0);
                ViewNote viewNote = (ViewNote) viewManager.getView("ViewNote");
                viewNote.loadNote(id);
                viewNote.render();
                viewManager.changeView("ViewNote");
            }
        });
        JScrollPane scrollPane = new JScrollPane(notesTable);

        this.refreshNotes();
        this.renderNotes();


        greeting.setAlignmentX(Component.LEFT_ALIGNMENT);
        newNoteBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        main.add(Box.createVerticalStrut(10));
        main.add(greeting);
        main.add(Box.createVerticalStrut(10));
        main.add(newNoteBtn);
        main.add(Box.createVerticalStrut(10));
        main.add(scrollPane);
        main.add(Box.createHorizontalStrut(5));

        this.add(main, BorderLayout.CENTER);
    }

    public void refreshNotes() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        this.notes = session.createQuery("FROM Note", Note.class).getResultList();
    }


    public void renderNotes() {
        tableModel.setRowCount(0);
        for (Note note : notes) {
            Object[] rowData = {note.getId(), note.getTitle(), note.getCreatedAt()};
            tableModel.addRow(rowData);
        }
    }
}
