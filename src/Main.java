import Models.Config;
import Views.*;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;

import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import Components.ViewManager;
import Components.View;

// These are required for the hibernate example to work


public class Main {

    // All views list
    private static final Dictionary<String, View> views = new Hashtable<>() {{
        put("Index", new Index());
        put("Dashboard", new Dashboard());
        put("Notes", new Notes());
        put("AddNote", new AddNote());
        put("ViewNote", new ViewNote());
        put("Expense", new ExpenseMain());
        put("ExpenseRecord", new AddExpenseRecord());
        put("CalendarApp", new CalendarApp());
    }};

    public static void main(String[] args) {
        // Make sure all environment variables are loaded
        // Also prepares them to be used inside java
        Dotenv dotenv = Dotenv.configure().load();

//        // Test Hibernate
//        // This is a simple hibernate example
//        // Create model in Models package
//        // Refer to Models.Config class for a basic example
//        // Use the following piece of code to add a record to the database
//        SessionFactory sessionFactory = Components.HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//
//        Config config = new Config();
//        config.setKey("Test 1");
//        config.setValue("Value 1");
//
//        // Persist adds the record to database
//        session.persist(config);
//
//        // Commit makes sure that database commits the transaction
//        tx.commit();
//
//        // Be sure to close the session and factory
//        session.close();

        // Register custom font to be used in the program
        FontManager.addFont("fonts/Poppins-Regular.ttf", Font.TRUETYPE_FONT);

        // Change the default font so that it overrides current theme
        Font defaultFont = new Font("Poppins", Font.PLAIN, 12);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("TextField.font", defaultFont);

        // Try to change the theme
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        FlatIntelliJLaf.setup(new FlatOneDarkIJTheme());

        // Window options, loaded from .env
        int width = Integer.parseInt(dotenv.get("WINDOW_WIDTH"));
        int height = Integer.parseInt(dotenv.get("WINDOW_HEIGHT"));
        String title = dotenv.get("APP_NAME");

        // Adds the function provided to the main thread
        SwingUtilities.invokeLater(() -> {

            // Config main window
            JFrame frame = new JFrame(title);
            frame.setSize(width, height);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ViewManager viewManager = ViewManager.getInstance();
            CardLayout cardLayout = (CardLayout) viewManager.getLayout();

            // Register all views into the main layout
            Enumeration<String> keys = Main.views.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                View view = Main.views.get(key);

                SwingUtilities.updateComponentTreeUI(view);
                viewManager.addView(view, key);
            }

            // Shows index view first
            cardLayout.show(viewManager, "Index");

            // More main window configuration
            frame.add(viewManager);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
