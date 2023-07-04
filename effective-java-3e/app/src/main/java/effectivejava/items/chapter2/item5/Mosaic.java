package effectivejava.items.chapter2.item5;

import java.util.function.Supplier;

public class Mosaic {
    private final Tile tile;

    private Mosaic(Tile tile) {
        this.tile = tile;
    }

    public static Mosaic create(Supplier<? extends Tile> tileFactory) {
        return new Mosaic(tileFactory.get());
    }

    public Tile tile() {
        return tile;
    }
}
