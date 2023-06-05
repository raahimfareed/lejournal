import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {
    public static void addFont(String path, int type) {
        try {
            File file = new File(path);
            Font customFont = Font.createFont(type, file);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
