import java.util.Arrays;
import java.util.stream.Stream;

public class MyArrayList {

    private Player[] elements;
    private int size;

    public MyArrayList() {
        elements = new Player[5];
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(Player player) {
        if (size == elements.length) {
            grow();
        }

        elements[size] = player;
        size++;
    }

    private void grow() {
        Player[] newElements = new Player[elements.length * 2];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    public Player get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Некорректный индекс: " + index);
        }

        return elements[index];
    }

    public Player remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Некорректный индекс: " + index);
        }

        Player removedPlayer = elements[index];

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        size--;

        return removedPlayer;
    }
    public Stream<Player> stream() {
        return Arrays.stream(elements, 0, size);
    }
}