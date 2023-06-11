package Views;

import Components.*;

import javax.swing.*;
import java.awt.*;

import Components.Button;
import Models.Note;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AddNote extends View {
    public AddNote() {
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

        JLabel greeting = new JLabel("Add Note");

        Font greetingFont = new Font("Poppins", Font.BOLD, 24);
        greeting.setFont(greetingFont);

        JLabel titleLabel = new JLabel("Title");
        JTextField titleInput = new JTextField();
        titleInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleInput.getPreferredSize().height));


        JLabel bodyLabel = new JLabel("Body");
        RSyntaxTextArea bodyInput = new RSyntaxTextArea();
        bodyInput.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_MARKDOWN);
        bodyInput.setCodeFoldingEnabled(true);
        bodyInput.setLineWrap(true);
        bodyInput.setWrapStyleWord(true);
        JScrollPane scrollPane = new RTextScrollPane(bodyInput);
        scrollPane.setPreferredSize(new Dimension((int) bodyInput.getPreferredSize().getWidth(), 200));

        try {
            Theme theme = Theme.load(getClass().getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/dark.xml"));
            theme.apply(bodyInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button submitBtn = new Button("Create");
        submitBtn.setOnAction(e -> this.createNote(titleInput, bodyInput));

        greeting.setAlignmentX(Component.LEFT_ALIGNMENT);
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bodyInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        main.add(Box.createVerticalStrut(10));
        main.add(backBtn);
        main.add(Box.createVerticalStrut(10));
        main.add(greeting);
        main.add(Box.createVerticalStrut(10));
        main.add(titleLabel);
        main.add(titleInput);
        main.add(Box.createVerticalStrut(10));
        main.add(bodyLabel);
        main.add(scrollPane);
        main.add(Box.createVerticalStrut(10));
        main.add(submitBtn);
        main.add(Box.createHorizontalStrut(5));
        this.add(main, BorderLayout.CENTER);
    }

    public void createNote(JTextField title, RSyntaxTextArea body) {
        if (title.getText().isBlank() && body.getText().isBlank()) {
            return;
        }
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Note note = new Note(title.getText(), body.getText());
        session.persist(note);
        tx.commit();
        clearInputs(title, body);
        ViewManager viewManager = ViewManager.getInstance();
        Notes notesView = (Notes) viewManager.getView("Notes");
        notesView.refreshNotes();
        notesView.renderNotes();
        ViewManager.getInstance().changeView("Notes");
    }

    public void clearInputs(JTextField title, RSyntaxTextArea body) {
        title.setText("");
        body.setText("");
    }
}
