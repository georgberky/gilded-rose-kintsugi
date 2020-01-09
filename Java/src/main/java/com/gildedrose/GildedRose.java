package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item itemToUpdate = items[i];
            if (itemToUpdate.name.startsWith("Conjured ")) {
                updateConjured(itemToUpdate);
            } else {
                if (!itemToUpdate.name.equals("Aged Brie")
                        && !itemToUpdate.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (itemToUpdate.quality > 0) {
                        if (!itemToUpdate.name.equals("Sulfuras, Hand of Ragnaros")) {
                            itemToUpdate.quality = itemToUpdate.quality - 1;
                        }
                    }
                } else {
                    if (itemToUpdate.quality < 50) {
                        itemToUpdate.quality = itemToUpdate.quality + 1;

                        if (itemToUpdate.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                            if (itemToUpdate.sellIn < 11) {
                                if (itemToUpdate.quality < 50) {
                                    itemToUpdate.quality = itemToUpdate.quality + 1;
                                }
                            }

                            if (itemToUpdate.sellIn < 6) {
                                if (itemToUpdate.quality < 50) {
                                    itemToUpdate.quality = itemToUpdate.quality + 1;
                                }
                            }
                        }
                    }
                }

                if (!itemToUpdate.name.equals("Sulfuras, Hand of Ragnaros")) {
                    itemToUpdate.sellIn = itemToUpdate.sellIn - 1;
                }

                if (itemToUpdate.sellIn < 0) {
                    if (!itemToUpdate.name.equals("Aged Brie")) {
                        if (!itemToUpdate.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                            if (itemToUpdate.quality > 0) {
                                if (!itemToUpdate.name.equals("Sulfuras, Hand of Ragnaros")) {
                                    itemToUpdate.quality = itemToUpdate.quality - 1;
                                }
                            }
                        } else {
                            itemToUpdate.quality = itemToUpdate.quality - itemToUpdate.quality;
                        }
                    } else {
                        if (itemToUpdate.quality < 50) {
                            itemToUpdate.quality = itemToUpdate.quality + 1;
                        }
                    }
                }
            }
        }
    }

    void updateConjured(Item item) {
        int degeneration = item.sellIn >= 0 ? 2 : 4;
        item.quality = Math.max(0, item.quality - degeneration);

        item.sellIn--;
    }
}