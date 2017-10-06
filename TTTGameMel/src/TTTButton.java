import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class TTTButton extends Button implements Observer {
    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index = 0;

    public TTTButton(int index) throws HeadlessException {
        this.index = index;
    }

    @Override
    public void update(Observable o, Object arg) {
        String symbol = (String) arg;
        setLabel(symbol);
    }
}
