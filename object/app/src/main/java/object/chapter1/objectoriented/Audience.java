package object.chapter1.objectoriented;

public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag bag() {
        return bag;
    }
}
