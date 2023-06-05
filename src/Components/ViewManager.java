package Components;

import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class ViewManager extends JPanel {
    // Singleton instance
    public static ViewManager instance = null;

    public static ViewManager getInstance() {
        if (instance == null) {
            ViewManager.instance = new ViewManager();
        }

        return ViewManager.instance;
    }

    // List of all registered views
    private Dictionary<String, View> views = new Hashtable<>();
    public ViewManager() {
        this.setLayout(new CardLayout());
    }

    public void addView(View view, String name) {
        this.add(view, name);
        views.put(name, view);
    }

    public JPanel getView(String name) throws NullPointerException {
        return views.get(name);
    }
}
