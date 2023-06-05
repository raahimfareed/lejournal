import Views.Index;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;

import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import Components.Button;
import Components.View;

import Views.Dashboard;

public class Main {
    private static final Dictionary<String, View> views = new Hashtable<>() {{
        put("Index", new Index());
        put("Dashboard", new Dashboard());
    }};

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();

        FontManager.addFont("fonts/Poppins-Regular.ttf", Font.TRUETYPE_FONT);

        Font defaultFont = new Font("Poppins", Font.PLAIN, 12);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("TextField.font", defaultFont);

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        FlatIntelliJLaf.setup(new FlatOneDarkIJTheme());

        int width = Integer.parseInt(dotenv.get("WINDOW_WIDTH"));
        int height = Integer.parseInt(dotenv.get("WINDOW_HEIGHT"));
        String title = dotenv.get("APP_NAME");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setSize(width, height);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ViewManager viewManager = new ViewManager();

            CardLayout cardLayout = (CardLayout) viewManager.getLayout();

            Enumeration<String> keys = Main.views.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                View view = Main.views.get(key);

                SwingUtilities.updateComponentTreeUI(view);
                viewManager.addView(view, key);
            }

            cardLayout.show(viewManager, "Index");

            frame.add(viewManager);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
