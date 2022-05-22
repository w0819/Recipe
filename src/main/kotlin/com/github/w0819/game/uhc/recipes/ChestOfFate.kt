package com.github.w0819.game.uhc.recipes

import com.github.w0819.game.util.ExtraUltimates
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

object ChestOfFate :  ExtraUltimates(
    NamespacedKey.minecraft("chest_of_fate"),
    ChestOfFate,
    75000,
    "BloodCraft","Engineering"
) {
    init {
        shape(
            "111",
            "121",
            "111"
        )
        setIngredient('1', Material.OAK_PLANKS)
        setIngredient('2',Material.PLAYER_HEAD)
    }

    object ChestOfFate : Item(
        ItemStack(Material.PLAYER_HEAD)
    )
}