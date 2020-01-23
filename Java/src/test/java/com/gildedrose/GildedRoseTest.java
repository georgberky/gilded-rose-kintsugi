package com.gildedrose;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GildedRoseTest {

    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Aged Brie: increases quality")
    public void whenDayPasses_itemAgedBrie_shouldIncreaseQualityByOne(int initialQuality) {
        Item item = new Item("Aged Brie", notPastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality + 1);
    }


    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Regular Item: decreases quality by one")
    public void whenDayPasses_normalItem_shouldDecreaseQualityByOne(int initialQuality) {
        Item item = new Item(anyRegularItemName(), notPastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality - 1);
    }

    @ParameterizedTest(name = "initial sell-in days: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Regular Item: decreases sell-in days by one")
    public void whenDayPasses_normalItem_shouldDecreaseSellInDateByOne(int initialSellInDate) {
        Item item = new Item(anyRegularItemName(), initialSellInDate, anyQuality());

        whenOneDayPasses(item);

        assertThat(item.sellIn).isEqualTo(initialSellInDate - 1);
    }

    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Sulfuras: does not change quality")
    public void whenDayPasses_itemSulfuras__shouldNotChangeQuality(int initialQuality) {
        Item item = new Item("Sulfuras, Hand of Ragnaros", notPastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality);
    }

    @Test
    @DisplayName("Regular item: does not decrease quality below zero")
    public void whenDayPasses_normalItemWithZeroQuality_shouldNotDecreaseQuality() {
        Item item = new Item(anyRegularItemName(), anySellInDate(), zeroQuality());

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(zeroQuality());
    }

    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 10, 11, 12 })
    @DisplayName("Regular item: past sell in date → decreases quality by two")
    public void whenDayPasses_normalItemWithNegativeSellDate_shouldDecreaseQualityeByTwo(int initialQuality) {
        Item item = new Item(anyRegularItemName(), pastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality - 2);
    }

    @Test
    @DisplayName("Aged Brie: does not increase quality above maximum")
    public void whenDayPasses_itemAgedBrieWithQualityFifty_shouldNotIncreaseQuality() {
        Item item = new Item("Aged Brie", anySellInDate(), maxQuality());

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(maxQuality());
    }

    @ParameterizedTest(name = "initial quality: {0}")
    @ValueSource(ints = { 46, 47, 48 })
    @DisplayName("Aged Brie: increases quality past sell-in date by two")
    public void afterSellInDate_AgedBrie_increasesInQuality(int initialQuality) {
        Item item = new Item("Aged Brie", pastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality + 2);
    }

    @ParameterizedTest(name = "initial sell-in days: {0}, initial quality: {1}")
    @MethodSource("backstagePassesDaysBetween10and5")
    @DisplayName("Backstage passes: sell-in days ⩽ 10 → increase quality by 2")
    public void backstagePasses_sellInLessThan11_increaseQualityBy2UpTo50(int initialSellIn, int initialQuality) {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", initialSellIn, initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality + 2);
    }

    private static Stream<Arguments> backstagePassesDaysBetween10and5() {
        List<Arguments> arguments = new LinkedList<>();

        for(int initialSellInDays = 10; initialSellInDays > 5; initialSellInDays--) {
            for(int initialQuality = 48; initialQuality >= 46; initialQuality--) {
                arguments.add(arguments(initialSellInDays, initialQuality));
            }
        }

        return arguments.stream();
    }

    @Test
    @DisplayName("Backstage passes: sell-in days ⩽ 10 → do not increase quality above maximum")
    public void backstagePasses_sellInLessThan11_doesNotIncreaseQualityBeyond50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(maxQuality());
    }

    @ParameterizedTest(name = "initial sell-in days: {0}, initial quality: {1}")
    @MethodSource("backstagePassesDaysBetween5And0")
    @DisplayName("Backstage passes: sell-in days ⩽ 5 → increase quality by 2")
    public void backstagePasses_sellInLessThan6_increasesQualityBy3UpTo50_(int initialSellIn, int initialQuality) {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", initialSellIn, initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality + 3);
    }

    private static Stream<Arguments> backstagePassesDaysBetween5And0() {
        List<Arguments> arguments = new LinkedList<>();

        for(int initialSellInDays = 5; initialSellInDays > 0; initialSellInDays--) {
            for(int initialQuality = 47; initialQuality >= 45; initialQuality--) {
                arguments.add(arguments(initialSellInDays, initialQuality));
            }
        }

        return arguments.stream();
    }

    @Test
    @DisplayName("Backstage passes: sell-in days ⩽ 5 → does not increase quality above maximum")
    public void backstagePasses_sellInLessThan6_doesNotIncreaseQualityBeyond50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(maxQuality());
    }

    @ParameterizedTest(name = "initial sell-in days: {0}")
    @ValueSource(ints = { 0, -1, -2 })
    @DisplayName("Backstage passes: quality is zero after sell-in has passed")
    public void backstagePasses_haveQualityZero_whenSellInHasPassed(int pastSellIn) {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", pastSellIn, 50);

        whenOneDayPasses(item);

        assertThat(item.quality).isZero();
    }

    private void whenOneDayPasses(Item item) {
        GildedRose app = createApp(item);

        app.updateQuality();
    }


    private int maxQuality() {
        return 50;
    }

    private int anyQuality() {
        return 10;
    }

    private int zeroQuality() {
        return 0;
    }

    private int anySellInDate() {
        return 1;
    }

    private int notPastSellInDate() {
        return 3;
    }

    private int pastSellInDate() {
        return -1;
    }

    private GildedRose createApp(Item item) {
        Item[] items = new Item[]{item};
        GildedRose app = new GildedRose(items);
        return app;
    }

    private String anyRegularItemName() {
        return "regular item";
    }
}
