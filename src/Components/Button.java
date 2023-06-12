package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JButton {
    public Button(String title) {
        super(title);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setOnAction(ActionListener listener) {
        addActionListener(listener);
    }

   /* public void removeOnAction() {
        if (this.actionListener == null) return;

        removeActionListener(this.actionListener);
        this.actionListener = null;
    }*/
}
