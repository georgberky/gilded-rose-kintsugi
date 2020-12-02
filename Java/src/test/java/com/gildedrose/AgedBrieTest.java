package com.gildedrose;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.gildedrose.GildedRoseFixtures.anySellInDate;
import static com.gildedrose.GildedRoseFixtures.maxQuality;
import static com.gildedrose.GildedRoseFixtures.notPastSellInDate;
import static com.gildedrose.GildedRoseFixtures.pastSellInDate;
import static com.gildedrose.GildedRoseFixtures.whenOneDayPasses;
import static org.assertj.core.api.Assertions.assertThat;

class AgedBrieTest {

    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Aged Brie: increases quality")
    void whenDayPasses_itemAgedBrie_shouldIncreaseQualityByOne(int initialQuality) {
        Item item = new Item("Aged Brie", notPastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality + 1);
    }

    @Test
    @DisplayName("Aged Brie: does not increase quality above maximum")
    void whenDayPasses_itemAgedBrieWithQualityFifty_shouldNotIncreaseQuality() {
        Item item = new Item("Aged Brie", anySellInDate(), maxQuality());

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(maxQuality());
    }

    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 46, 47, 48 })
    @DisplayName("Aged Brie: increases quality past sell-in date by two")
    void afterSellInDate_AgedBrie_increasesInQuality(int initialQuality) {
        Item item = new Item("Aged Brie", pastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality + 2);
    }
}
