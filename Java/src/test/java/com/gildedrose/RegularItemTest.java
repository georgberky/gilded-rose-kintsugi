package com.gildedrose;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.gildedrose.GildedRoseFixtures.anyQuality;
import static com.gildedrose.GildedRoseFixtures.anySellInDate;
import static com.gildedrose.GildedRoseFixtures.notPastSellInDate;
import static com.gildedrose.GildedRoseFixtures.pastSellInDate;
import static com.gildedrose.GildedRoseFixtures.whenOneDayPasses;
import static com.gildedrose.GildedRoseFixtures.zeroQuality;
import static org.assertj.core.api.Assertions.assertThat;

class RegularItemTest {

    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Regular Item: decreases quality by one")
    void whenDayPasses_normalItem_shouldDecreaseQualityByOne(int initialQuality) {
        Item item = new Item("regular item", notPastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality - 1);
    }

    @ParameterizedTest(name = "initial sell-in days: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Regular Item: decreases sell-in days by one")
    void whenDayPasses_normalItem_shouldDecreaseSellInDateByOne(int initialSellInDate) {
        Item item = new Item("regular item", initialSellInDate, anyQuality());

        whenOneDayPasses(item);

        assertThat(item.sellIn).isEqualTo(initialSellInDate - 1);
    }

    @Test
    @DisplayName("Regular item: does not decrease quality below zero")
    void whenDayPasses_normalItemWithZeroQuality_shouldNotDecreaseQuality() {
        Item item = new Item("regular item", anySellInDate(), zeroQuality());

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(zeroQuality());
    }

    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Regular item: past sell in date → decreases quality by two")
    void whenDayPasses_normalItemWithNegativeSellDate_shouldDecreaseQualityeByTwo(int initialQuality) {
        Item item = new Item("regular item", pastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality - 2);
    }

}
