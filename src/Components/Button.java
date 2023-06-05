package Components;

import javax.swing.*;

public class Button extends JButton {
    public Button(String title) {
        super(title);
        this.setSize(256, 64);
    }

    public Button(String title, int width, int height) {
        super(title);
        this.setSize(width, height);
    }
}
