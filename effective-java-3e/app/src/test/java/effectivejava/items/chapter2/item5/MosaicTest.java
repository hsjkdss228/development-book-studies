package effectivejava.items.chapter2.item5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MosaicTest {
    @Test
    void creationWithATile() {
        Mosaic mosaic = Mosaic.create(ATile::new);
        assertThat(mosaic.tile()).isInstanceOf(ATile.class);
    }

    @Test
    void creationWithBTile() {
        Mosaic mosaic = Mosaic.create(BTile::new);
        assertThat(mosaic.tile()).isInstanceOf(BTile.class);
    }
}
