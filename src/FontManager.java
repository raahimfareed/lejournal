import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {
    // Load custom fonts using font file path and their type
    public static void addFont(String path, int type) {
        try {
            File file = new File(path);
            Font customFont = Font.createFont(type, file);

            // Make the graphic driver register the font so it can be used
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
