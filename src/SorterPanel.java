import javax.swing.*;
import java.awt.*;

public class SorterPanel extends JPanel {

    SorterController controller;
    DisplayPanel displayPanel;
    ControlPanel controlPanel;

    public SorterPanel(SorterController controller) {
        super(new BorderLayout());

        this.controller = controller;
        displayPanel = new DisplayPanel(controller);
        controlPanel = new ControlPanel(controller);

        add(displayPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.PAGE_END);
    }

}
