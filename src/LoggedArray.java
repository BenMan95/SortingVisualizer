import java.util.ArrayList;

public class LoggedArray {

    public final int size;
    private final int[] initEles;
    private final int[] finalEles;

    private ArrayList<ArrayOperation> log;

    public LoggedArray(int size) {
        this.size = size;
        initEles = new int[size];
        finalEles = new int[size];
        log = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            initEles[i] = i;
            finalEles[i] = i;
        }
    }

    public int getFinalElement(int i) {
        return finalEles[i];
    }
    public int getInitElement(int i) {
        return initEles[i];
    }

    public int logSize() {
        return log.size();
    }
    public ArrayOperation getOperation(int i) {
        return log.get(i);
    }

    public void resetLog() {
        log.clear();
        for (int i = 0; i < size; i++)
            initEles[i] = finalEles[i];
    }

    public int compare(int i, int j) {
        if (i < 0 || j < 0 || i >= size || j >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        log.add(new ArrayOperation(ArrayOperation.COMPARE, i, j));
        return Integer.signum(finalEles[i] - finalEles[j]);
    }

    public void swap(int i, int j) {
        if (i < 0 || j < 0 || i >= size || j >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        log.add(new ArrayOperation(ArrayOperation.SWAP, i, j));
        int temp = finalEles[i];
        finalEles[i] = finalEles[j];
        finalEles[j] = temp;
    }
}
