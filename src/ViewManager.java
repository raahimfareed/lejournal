import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

import Components.View;

public class ViewManager extends JPanel {
    public static ViewManager instance = null;

    public static ViewManager getInstance() {
        if (instance == null) {
            ViewManager.instance = new ViewManager();
        }

        return ViewManager.instance;
    }

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
