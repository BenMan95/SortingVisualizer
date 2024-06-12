import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ControlPanel extends JPanel {

    static String[] ops = {
            "Quicksort",
            "Insertion",
            "Bubble",
            "Selection"
    };

    private SorterController controller;

    private JSlider stepSlider;

    public ControlPanel(SorterController controller) {
        super(new BorderLayout());
        setBorder(new EmptyBorder(0,10,0,10));
        this.controller = controller;

        addStepControls();
        addSizeControls();
        addOperationControls();
    }

    private void addStepControls() {
        JPanel stepControls = new JPanel();
        stepControls.setLayout(new BoxLayout(stepControls, BoxLayout.Y_AXIS));
        add(stepControls, BorderLayout.CENTER);

        JButton button;
        JPanel buttons = new JPanel();
        stepControls.add(buttons);

        button = new JButton("|<");
        button.addActionListener(e -> controller.gotoStart());
        buttons.add(button);

        button = new JButton("<");
        button.addActionListener(e -> controller.prev());
        buttons.add(button);

        button = new JButton("Play/Pause");
        button.addActionListener(e -> {
            controller.paused = !controller.paused;
            controller.panel.displayPanel.repaint();
        });
        buttons.add(button);

        button = new JButton(">");
        button.addActionListener(e -> controller.next());
        buttons.add(button);

        button = new JButton(">|");
        button.addActionListener(e -> controller.gotoEnd());
        buttons.add(button);

        stepSlider = new JSlider(0,0,0);
        stepSlider.setMaximumSize(buttons.getPreferredSize());
        stepSlider.addChangeListener(e -> {
            int step = stepSlider.getValue();
            controller.gotoStep(step);
        });
        stepControls.add(stepSlider);
    }

    private void addSizeControls() {
        JSlider slider = new JSlider(10, 1000, controller.array.size);
        add(slider, BorderLayout.LINE_START);

        slider.addChangeListener(e -> {
            int size = slider.getValue();
            controller.resizeArray(size);
        });
    }

    private void addOperationControls() {
        JPanel operations = new JPanel();
        JButton shuffleButton = new JButton("Shuffle");
        JButton sortButton = new JButton("Sort");
        JComboBox<String> select = new JComboBox<>(ops);

        shuffleButton.addActionListener(e -> {
            controller.doOperation("Shuffle");
            stepSlider.setValue(0);
            stepSlider.setMaximum(controller.array.logSize());
        });

        sortButton.addActionListener(e -> {
            String op = (String) select.getSelectedItem();
            controller.doOperation(op);
            stepSlider.setValue(0);
            stepSlider.setMaximum(controller.array.logSize());
        });

        operations.add(shuffleButton);
        operations.add(sortButton);
        operations.add(select);

        add(operations, BorderLayout.LINE_END);
    }

    public void setSliderValue(int value) {
        stepSlider.setValue(value);
    }

    public void setSliderMax(int max) {
        stepSlider.setMaximum(max);
    }

}
