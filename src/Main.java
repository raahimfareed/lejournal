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

import Views.Index;
import Views.Dashboard;

public class Main {

    // All views list
    private static final Dictionary<String, View> views = new Hashtable<>() {{
        put("Index", new Index());
        put("Dashboard", new Dashboard());
    }};

    public static void main(String[] args) {
        // Make sure all environment variables are loaded
        // Also prepares them to be used inside java
        Dotenv dotenv = Dotenv.configure().load();

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
