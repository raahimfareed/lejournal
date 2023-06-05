package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JButton {
    private ActionListener actionListener;
    public Button(String title) {
        super(title);
    }

    public void setOnAction(ActionListener listener) {
        this.actionListener = listener;
        addActionListener(listener);
    }

    public void removeOnAction() {
        if (this.actionListener == null) return;

        removeActionListener(this.actionListener);
        this.actionListener = null;
    }
}
