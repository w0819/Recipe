package com.github.w0819.game.uhc.recipes

import com.github.w0819.game.util.ArmorSmithing
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta

object BookOfProtection : ArmorSmithing(
    NamespacedKey.minecraft("book_of_protection"),
    BookOfPortection,
    3,
    true
) {
    init {
        shape(
            "   ",
            " 11",
            " 12"
        )
        setIngredient('1', Material.PAPER)
        setIngredient('2', Material.IRON_INGOT)
    }
    object BookOfPortection : Item(
        ItemStack(Material.ENCHANTED_BOOK).apply {
            itemMeta = itemMeta.apply {
                (this as EnchantmentStorageMeta).addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true)
            }
        }
    )
}