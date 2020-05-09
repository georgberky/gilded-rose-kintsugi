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
                //here be dragons
                updateOtherItem(itemToUpdate);
            }
        }
    }

    private void updateOtherItem(Item item) {
        if (!item.name.equals("Aged Brie") &&
            !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {

            if (item.quality > 0) {
                if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                    item.quality = item.quality - 1;
                }
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }

        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
            item.sellIn = item.sellIn - 1;
        }

        if (pastSellInDate(item)) {
            if (!item.name.equals("Aged Brie")) {
                if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (item.quality > 0) {
                        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    item.quality = item.quality - item.quality;
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }
        }
    }

    void updateConjured(Item item) {
        int degeneration = 2;

        if(pastSellInDate(item)) {
            degeneration *= 2;
        }

        item.quality = Math.max(0, item.quality - degeneration);

        item.sellIn--;
    }

    private boolean pastSellInDate(Item item) {
        return item.sellIn < 0;
    }
}