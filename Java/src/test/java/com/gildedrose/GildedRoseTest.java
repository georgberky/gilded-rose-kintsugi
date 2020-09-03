package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GildedRoseTest {
    @ParameterizedTest
    @MethodSource("qualityRegularItems")
    void regularItems_lose1qualityPerDay_ifSellInNotPassed(int startQuality, int expectedQuality) {
        //arrange
        String name = "Regular Item";
        int sellIn = 1;
        Item[] items = new Item[] { new Item(name, sellIn, startQuality) };
        GildedRose app = new GildedRose(items);

        //act
        app.updateQuality();

        //assert
        assertThat(items[0].quality).isEqualTo(expectedQuality);
    }

    private static Stream<Arguments> qualityRegularItems() {
        return Stream.of(
            arguments(1, 0),
            arguments(2, 1),
            arguments(3, 2)
        );
    }
}
