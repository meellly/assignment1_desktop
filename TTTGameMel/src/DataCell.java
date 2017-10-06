import java.util.Observable;

public class DataCell extends Observable {

    public DataCell(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        setChanged();
        notifyObservers(value);
    }

    private String value = null;

}
