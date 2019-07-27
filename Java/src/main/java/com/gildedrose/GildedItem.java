package com.gildedrose;

public class GildedItem {

    private final Item item;

    public GildedItem(Item item) {
        this.item = item;
    }

    Item getItem() {
        return item;
    }

    public void decreaseSellIn() {
        item.sellIn = item.sellIn - 1;
    }

    public void decreaseQuality() {
        item.quality--;
    }

    public void increaseQuality() {
        item.quality++;
    }
}
