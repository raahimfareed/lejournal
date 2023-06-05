import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class ViewManager extends JPanel {
    private Dictionary<String, JPanel> views = new Hashtable<>();
    public ViewManager() {
        this.setLayout(new CardLayout());
    }

    public void addView(JPanel view, String name) {
        this.add(view, name);
        views.put(name, view);
    }

    public JPanel getView(String name) throws NullPointerException {
        return views.get(name);
    }
}
