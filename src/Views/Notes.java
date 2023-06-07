package Views;

import Components.Sidenav;
import Components.View;

import java.awt.*;

public class Notes extends View {
    public Notes() {
        this.setLayout(new BorderLayout());
        this.add(new Sidenav(), BorderLayout.WEST);
    }
}
