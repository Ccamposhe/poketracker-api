package com.poketracker.poke_tracker_api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Game {
    POKEMMO("Pokémmo", 0, null),

    RED("Red", 1, "red"),
    BLUE("Blue", 1, "blue"),
    YELLOW("Yellow", 1, "yellow"),

    GOLD("Gold", 2, "gold"),
    SILVER("Silver", 2, "silver"),
    CRYSTAL("Crystal", 2, "crystal"),

    RUBY("Ruby", 3, "ruby"),
    SAPPHIRE("Sapphire", 3, "sapphire"),
    EMERALD("Emerald", 3, "emerald"),
    FIRERED("FireRed", 3, "firered"),
    LEAFGREEN("LeafGreen", 3, "leafgreen"),

    DIAMOND("Diamond", 4, "diamond"),
    PEARL("Pearl", 4, "pearl"),
    PLATINUM("Platinum", 4, "platinum"),
    HEARTGOLD("HeartGold", 4, "heartgold"),
    SOULSILVER("SoulSilver", 4, "soulsilver"),

    BLACK("Black", 5, "black"),
    WHITE("White", 5, "white"),
    BLACK_2("Black 2", 5, "black-2"),
    WHITE_2("White 2", 5, "white-2"),

    X("X", 6, "x"),
    Y("Y", 6, "y"),
    OMEGA_RUBY("Omega Ruby", 6, "omega-ruby"),
    ALPHA_SAPPHIRE("Alpha Sapphire", 6, "alpha-sapphire"),

    SUN("Sun", 7, "sun"),
    MOON("Moon", 7, "moon"),
    ULTRA_SUN("Ultra Sun", 7, "ultra-sun"),
    ULTRA_MOON("Ultra Moon", 7, "ultra-moon"),
    LETS_GO_PIKACHU("Let's Go, Pikachu", 7, "lets-go-pikachu"),
    LETS_GO_EEVEE("Let's Go, Eevee", 7, "lets-go-eevee"),

    SWORD("Sword", 8, "sword"),
    SHIELD("Shield", 8, "shield"),
    BRILLIANT_DIAMOND("Brilliant Diamond", 8, "brilliant-diamond"),
    SHINING_PEARL("Shining Pearl", 8, "shining-pearl"),
    LEGENDS_ARCEUS("Legends: Arceus", 8, "legends-arceus"),

    SCARLET("Scarlet", 9, "scarlet"),
    VIOLET("Violet", 9, "violet"),
    LEGENDS_ZA("Legends: Z-A", 9, "legends-za");

    private final String name;
    private final int generation;
    private final String apiName;
}