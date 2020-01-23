package com.gildedrose;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GildedRoseTest {

    @Test
    public void whenDayPasses_itemAgedBrie_shouldIncreaseQualityByOne() {
        Item item = new Item("Aged Brie", notPastSellInDate(), 10);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(11);
    }


    @Test
    public void whenDayPasses_normalItem_shouldDecreaseQualityByOne() {
        Item item = new Item(anyRegularItemName(), notPastSellInDate(), 10);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(9);
    }

    @Test
    public void whenDayPasses_normalItem_shouldDecreaseDaysLeftByOne() {
        Item item = new Item(anyRegularItemName(), notPastSellInDate(), 10);

        whenOneDayPasses(item);

        assertThat(item.sellIn).isEqualTo(notPastSellInDate() - 1);
    }

    @Test
    public void whenDayPasses_itemSulfuras__shouldNotChangeQuality() {
        int initialQuality = anyQuality();
        Item item = new Item("Sulfuras, Hand of Ragnaros", notPastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality);
    }

    @Test
    public void whenDayPasses_normalItemWithZeroQuality_shouldNotDecreaseQuality() {
        Item item = new Item(anyRegularItemName(), anySellInDate(), zeroQuality());

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(zeroQuality());
    }

    @Test
    public void whenDayPasses_normalItemWithNegativeSellDate_shouldDecreaseQualityeByTwo() {
        int initialQuality = anyQuality();
        Item item = new Item(anyRegularItemName(), pastSellInDate(), initialQuality);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(initialQuality - 2);
    }

    @Test
    public void whenDayPasses_itemAgedBrieWithQualityFifty_shouldNotIncreaseQuality() {
        Item item = new Item("Aged Brie", anySellInDate(), maxQuality());

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(maxQuality());
    }

    @Test
    public void afterSellInDate_AgedBrie_increasesInQuality() {
        Item item = new Item("Aged Brie", anySellInDate(), 48);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(50);
    }

    @Test
    public void backstagePasses_sellInLessThan11_increaseQualityBy2UpTo50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 48);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(50);
    }

    @Test
    public void backstagePasses_sellInLessThan11_doesNotIncreaseQualityBeyond50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(50);
    }

    @Test
    public void backstagePasses_sellInLessThan6_increasesQualityBy3UpTo50_() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 47);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(50);
    }

    @Test
    public void backstagePasses_sellInLessThan6_doesNotIncreaseQualityBeyond50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48);

        whenOneDayPasses(item);

        assertThat(item.quality).isEqualTo(50);
    }

    @Test
    public void backstagePasses_haveQualityZero_whenSellInHasPassed() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 50);

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
        return 0;
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
