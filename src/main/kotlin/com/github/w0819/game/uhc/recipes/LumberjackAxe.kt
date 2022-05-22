package com.github.w0819.game.uhc.recipes

import com.github.w0819.game.util.ToolSmithing
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

object LumberjackAxe : ToolSmithing(
    NamespacedKey.minecraft("lumberjack_axe"),
    LumberjackAxe,
    3,
    true
) {
    init {
        shape(
            "112",
            "13 ",
            " 3 "
        )
        setIngredient('1', Material.IRON_INGOT)
        setIngredient('2', Material.FLINT)
        setIngredient('3', Material.STICK)
    }
    object LumberjackAxe : Item(
        ItemStack(Material.IRON_AXE)
    )
}