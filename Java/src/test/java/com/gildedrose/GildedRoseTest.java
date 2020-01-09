package com.gildedrose;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GildedRoseTest {

    @Test
    public void whenDayPasses_itemAgedBrie_shouldIncreaseQualityByOne() {
        Item item = new Item(getAgedBrie(), notSellinDatePassed(), 10);

        whenOneDayPasses(item);

        assertThat(item.quality, is(11));
    }


    @Test
    public void whenDayPasses_normalItem_shouldDecreaseQualityByOne() {
        Item item = new Item(anyName(), notSellinDatePassed(), 10);

        whenOneDayPasses(item);

        assertThat(item.quality, is(9));
    }

    @Test
    public void whenDayPasses_normalItem_shouldDecreaseDaysLeftByOne() {
        Item item = new Item(anyName(), notSellinDatePassed(), 10);

        whenOneDayPasses(item);

        assertThat(item.sellIn, is(notSellinDatePassed() - 1));
    }

    @Test
    public void whenDayPasses_itemSulfuras__shouldNotChangeQuality() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", notSellinDatePassed(), anyQuality());

        whenOneDayPasses(item);

        assertThat(item.quality, is(anyQuality()));
    }

    @Test
    public void whenDayPasses_normalItemWithZeroQuality_shouldNotDecreaseQuality() {
        Item item = new Item(anyName(), anySellingDate(), zeroQuality());
        whenOneDayPasses(item);
        assertThat(item.quality, is(zeroQuality()));
    }

    @Test
    public void whenDayPasses_normalItemWithNegativeSellDate_shouldDecreaseQualityeByTwo() {
        Item item = new Item(anyName(), sellInDatePassed(), anyQuality());

        whenOneDayPasses(item);

        assertThat(item.quality, is(anyQuality() - 2));
    }

    @Test
    public void whenDayPasses_itemAgedBrieWithQualityFifty_shouldNotIncreaseQuality() {
        Item item = new Item(getAgedBrie(), anySellingDate(), getMaxQuality());

        whenOneDayPasses(item);

        assertThat(item.quality, is(getMaxQuality()));
    }

    @Test
    public void afterSellInDate_AgedBrie_increasesInQuality() {
        Item item = new Item(getAgedBrie(), anySellingDate(), 48);

        whenOneDayPasses(item);

        assertThat(item.quality, is(50));
    }

    @Test
    public void backstagePasses_sellInLessThan11_increaseQualityBy2UpTo50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 48);

        whenOneDayPasses(item);

        assertThat(item.quality, is(50));
    }

    @Test
    public void backstagePasses_sellInLessThan11_doesNotIncreaseQualityBeyond50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49);

        whenOneDayPasses(item);

        assertThat(item.quality, is(50));
    }

    @Test
    public void backstagePasses_sellInLessThan6_increasesQualityBy3UpTo50_() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 47);

        whenOneDayPasses(item);

        assertThat(item.quality, is(50));
    }

    @Test
    public void backstagePasses_sellInLessThan6_doesNotIncreaseQualityBeyond50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48);
        whenOneDayPasses(item);

        assertThat(item.quality, is(50));
    }

    @Test
    public void backstagePasses_haveQualityZero_whenSellInHasPassed() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 50);

        whenOneDayPasses(item);

        assertThat(item.quality, is(0));
    }

    @Test
    public void conjuredItem_sellInDateNotPassed_degradesQualityByTwo() {
        Item item = new Item("Conjured Regular Item", notSellinDatePassed(), 10);

        GildedRose app = createApp(item);
        app.updateConjured(item);

        assertThat(item.quality, is(8));
    }

    @Test
    public void conjuredItem_sellInDatePassed_degradesQualityByFour() {
        Item item = new Item("Conjured Regular Item", sellInDatePassed(), 10);

        GildedRose app = createApp(item);
        app.updateConjured(item);

        assertThat(item.quality, is(6));
    }

    @Test
    public void conjuredItem_qualityZeroSellInDateNotPassed_doesNotDegradeFurther() {
        Item item = new Item("Conjured Regular Item", notSellinDatePassed(), 0);

        GildedRose app = createApp(item);
        app.updateConjured(item);

        assertThat(item.quality, is(0));
    }

    @Test
    public void conjuredItem_qualityZeroAndSellInPassed_doesNotDegradeFurther() {
        Item item = new Item("Conjured Regular Item", sellInDatePassed(), 0);

        GildedRose app = createApp(item);
        app.updateConjured(item);

        assertThat(item.quality, is(0));
    }

    @Test
    public void conjuredItem_decreasesSellInDateByOne_perDay() {
        Item item = new Item("Conjured Regular Item", 10, anyQuality());

        GildedRose app = createApp(item);
        app.updateConjured(item);

        assertThat(item.sellIn, is(9));
    }

    @Test
    public void conjuredItem_decreasesSellInDateBelowZero() {
        Item item = new Item("Conjured Regular Item", 0, anyQuality());

        GildedRose app = createApp(item);
        app.updateConjured(item);

        assertThat(item.sellIn, is(-1));
    }

    private void whenOneDayPasses(Item item) {
        GildedRose app = createApp(item);
        app.updateQuality();
    }

    private int sellInDatePassed() {
        return -1;
    }


    private int anyQuality() {
        return 10;
    }


    private int getMaxQuality() {
        return 50;
    }

    private int zeroQuality() {
        return 0;
    }

    private int anySellingDate() {
        return 0;
    }

    private GildedRose createApp(Item item) {
        Item[] items = new Item[]{item};
        GildedRose app = new GildedRose(items);
        return app;
    }

    private String getAgedBrie() {
        return "Aged Brie";
    }

    private int notSellinDatePassed() {
        return 3;
    }

    private String anyName() {
        return "foo";
    }
}
