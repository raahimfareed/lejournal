package Components;

import com.formdev.flatlaf.icons.FlatAbstractIcon;

import javax.swing.*;
import java.awt.*;

public class BackIcon extends FlatAbstractIcon {
    private static final int DEFAULT_SIZE = 16; // Adjust the size as needed

    public BackIcon() {
        super(DEFAULT_SIZE, DEFAULT_SIZE, UIManager.getColor("gray"));
    }

    @Override
    protected void paintIcon(Component c, Graphics2D g) {
        // Set the rendering hints for smooth icon rendering
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = this.width;
        int height = this.height;

        // Calculate the positions of the arrow points
        int[] xPoints = {width, 0, width};
        int[] yPoints = {0, height / 2, height};

        // Set the color of the arrow
        g.setColor(c.getForeground());

        // Draw the arrow using the calculated points
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
