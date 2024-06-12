import javax.swing.*;
import java.util.Random;

public class SorterController {

    SorterPanel panel;
    LoggedArray array;

    int logIndex;
    int[] curEles;

    boolean paused;

    public SorterController(int size) {
        array = new LoggedArray(size);
        panel = new SorterPanel(this);

        logIndex = 0;
        curEles = new int[size];
        for (int i = 0; i < size; i++)
            curEles[i] = i;

        paused = false;
    }

    public void displayFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Sorting Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SorterController(30).displayFrame();
    }

    public ArrayOperation nextOp() {
        return logIndex < array.logSize() ? array.getOperation(logIndex) : null;
    }

    public void resizeArray(int size) {
        array = new LoggedArray(size);
        curEles = new int[size];
        for (int i = 0; i < size; i++) {
            curEles[i] = i;
        }
        logIndex = 0;

        panel.displayPanel.repaint();
        panel.controlPanel.setSliderMax(0);
    }

    public boolean hasNext() {
        return logIndex < array.logSize();
    }
    public boolean hasPrev() {
        return logIndex > 0;
    }

    public boolean next() {
        if (!hasNext())
            return false;

        ArrayOperation op = array.getOperation(logIndex++);
        if (op.operation() == ArrayOperation.SWAP) {
            int temp = curEles[op.i()];
            curEles[op.i()] = curEles[op.j()];
            curEles[op.j()] = temp;
        }

        panel.displayPanel.repaint();
        panel.controlPanel.setSliderValue(logIndex);

        return true;
    }
    public boolean prev() {
        if (!hasPrev())
            return false;

        ArrayOperation op = array.getOperation(--logIndex);
        if (op.operation() == ArrayOperation.SWAP) {
            int temp = curEles[op.i()];
            curEles[op.i()] = curEles[op.j()];
            curEles[op.j()] = temp;
        }

        panel.displayPanel.repaint();
        panel.controlPanel.setSliderValue(logIndex);

        return true;
    }

    public void gotoStart() {
        logIndex = 0;
        for (int i = 0; i < array.size; i++)
            curEles[i] = array.getInitElement(i);

        panel.displayPanel.repaint();
        panel.controlPanel.setSliderValue(0);
    }
    public void gotoEnd() {
        logIndex = array.logSize();
        for (int i = 0; i < array.size; i++) {
            curEles[i] = array.getFinalElement(i);
        }

        panel.displayPanel.repaint();
        panel.controlPanel.setSliderValue(array.size);
    }

    public void gotoStep(int index) {
        if (index < 0 || index > array.logSize())
            throw new ArrayIndexOutOfBoundsException();

        while (logIndex < index) {
            ArrayOperation op = array.getOperation(logIndex++);
            if (op.operation() == ArrayOperation.SWAP) {
                int temp = curEles[op.i()];
                curEles[op.i()] = curEles[op.j()];
                curEles[op.j()] = temp;
            }
        }

        while (logIndex > index) {
            ArrayOperation op = array.getOperation(--logIndex);
            if (op.operation() == ArrayOperation.SWAP) {
                int temp = curEles[op.i()];
                curEles[op.i()] = curEles[op.j()];
                curEles[op.j()] = temp;
            }
        }

        panel.displayPanel.repaint();
    }

    public void doOperation(String operation) {
        array.resetLog();
        logIndex = 0;
        for (int i = 0; i < array.size; i++) {
            curEles[i] = array.getInitElement(i);
        }

        switch (operation) {
            case "Shuffle"   -> shuffle();
            case "Quicksort" -> quicksort(0, array.size);
            case "Insertion" -> insertion();
            case "Bubble"    -> bubble();
            case "Selection" -> selection();
        }

        panel.displayPanel.repaint();
        panel.controlPanel.setSliderMax(0);
    }

    private void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < array.size; i++) {
            array.swap(i, rand.nextInt(array.size-i) + i);
        }
    }
    private void quicksort(int lo, int hi) {
        if (lo >= hi) return;

        int i = lo;
        for (int j = lo+1; j < hi; j++) {
            if (array.compare(j, lo) < 0) {
                array.swap(++i, j);
            }
        }
        array.swap(lo,i);

        quicksort(lo, i);
        quicksort(i+1, hi);
    }
    private void insertion() {
        for (int i = 1; i < array.size; i++) {
            for (int j = i; j > 0; j--) {
                if (array.compare(j, j-1) < 0) array.swap(j, j-1);
                else break;
            }
        }
    }
    private void bubble() {
        for (int i = array.size; --i > 0;)
            for (int j = 0; j < i; j++)
                if (array.compare(j, j+1) > 0)
                    array.swap(j, j+1);
    }
    private void selection() {
        for (int i = array.size; --i > 0;) {
            int best = 0;
            for (int j = 1; j <= i; j++)
                if (array.compare(best, j) < 0)
                    best = j;
            array.swap(best, i);
        }
    }

}
