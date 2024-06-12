import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DisplayPanel extends JPanel {

    private SorterController controller;

    private JLabel countLabel;
    private JLabel stepLabel;

    public DisplayPanel(SorterController controller) {
        super(new GridBagLayout());
        setBorder(new EmptyBorder(5,5,5,5));
        setPreferredSize(new Dimension(800,800));

        this.controller = controller;
        setBackground(Color.BLACK);

        GridBagConstraints c = new GridBagConstraints();
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridy = 0;
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;

        countLabel = new JLabel();
        add(countLabel, c);

        stepLabel = new JLabel("Step");
        c.weightx = 1;
        c.gridx = 1;
        add(stepLabel, c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayOperation op = controller.nextOp();

        int size = controller.array.size;
        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < size; i++) {
            if (op == null || (op.i() != i && op.j() != i)) {
                g.setColor(Color.WHITE);
            } else {
                switch (op.operation()) {
                    case ArrayOperation.COMPARE -> g.setColor(Color.BLUE);
                    case ArrayOperation.SWAP    -> g.setColor(Color.RED);
                }
            }

            int x1 = width * i / size;
            int x2 = width * (i+1) / size;
            int y = height * (controller.curEles[i]+1) / size;
            g.fillRect(x1, height-y, x2-x1, y);
        }

        countLabel.setText("Elements: " + size);
        stepLabel.setText("Step: %d/%d".formatted(controller.logIndex, controller.array.logSize()));

        if (!controller.paused) {
           if (!controller.next()) {
               controller.paused = true;
           }
        }
    }
}
