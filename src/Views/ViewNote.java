package Views;

import Components.*;

import javax.swing.*;
import java.awt.*;

import Components.Button;
import Models.Note;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ViewNote extends View {
    private Note note = null;

    private final JLabel title;
    private final Parser parser;
    private final HtmlRenderer renderer;

    private final JEditorPane editorPane;

    private boolean update;

    private Button updateBtn;

    public ViewNote() {
        this.title = new JLabel();
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
        this.editorPane = new JEditorPane();
        this.editorPane.setContentType("text/html");
        this.editorPane.setText("");
        this.editorPane.setEditable(false);
        this.update = false;
        this.updateBtn = new Button("Update");
        JScrollPane scrollPane = new JScrollPane(this.editorPane);
        this.setLayout(new BorderLayout());
        this.add(new Sidenav(), BorderLayout.WEST);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        BackIcon icon = new BackIcon();
        Button backBtn = new Button("");
        backBtn.setIcon(icon);
        ViewManager viewManager = ViewManager.getInstance();
        CardLayout cardLayout = (CardLayout) viewManager.getLayout();
        backBtn.setOnAction(e -> cardLayout.show(viewManager, "Notes"));

        Font titleFont = new Font("Poppins", Font.BOLD, 24);
        this.title.setFont(titleFont);
//        scrollPane.setPreferredSize(new Dimension((int) bodyInput.getPreferredSize().getWidth(), 200));

        Button deleteBtn = new Button("Delete");
        deleteBtn.setBackground(new Color(82, 39, 39, 255));
        deleteBtn.setForeground(new Color(239, 156, 156, 255));
        JPanel btnContainer = new JPanel(new FlowLayout());
        btnContainer.add(this.updateBtn);
        btnContainer.add(deleteBtn);

        deleteBtn.setOnAction(e -> this.delete());
        updateBtn.setOnAction(e -> this.update());

        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.editorPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.title.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        main.add(Box.createVerticalStrut(10));
        main.add(backBtn);
        main.add(Box.createVerticalStrut(10));
        main.add(this.title);
        main.add(Box.createVerticalStrut(10));
        main.add(scrollPane);
        main.add(Box.createVerticalStrut(10));
        main.add(btnContainer);
        main.add(Box.createVerticalStrut(10));
        main.add(Box.createHorizontalStrut(5));
        this.add(main, BorderLayout.CENTER);
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void loadNote(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        final Note[] note = {session.createQuery("FROM Note WHERE id = :id", Note.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .uniqueResult()};
        setNote(note[0]);
    }

    public void render() {
        Node document = parser.parse(note.getBody());
        String html = renderer.render(document);
        this.editorPane.setText(html);
        this.title.setText(this.note.getTitle());
    }

    public void delete() {
        if (this.note == null) return;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(this.note);
        tx.commit();
        ViewManager viewManager = ViewManager.getInstance();
        Notes notesView = (Notes) viewManager.getView("Notes");
        notesView.refreshNotes();
        notesView.renderNotes();
        viewManager.changeView("Notes");
    }

    public void update() {
        if (this.update) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            this.note.setBody(this.editorPane.getText());
            session.merge(this.note);
            tx.commit();
        }
        this.update = !this.update;
        this.editorPane.setEditable(this.update);
        if (this.update) {
            this.updateBtn.setText("Save");
            this.editorPane.setContentType("text/plain");
            this.editorPane.setText(this.note.getBody());
            return;
        }

        this.updateBtn.setText("Update");
        this.editorPane.setContentType("text/html");
        this.render();
    }
}
