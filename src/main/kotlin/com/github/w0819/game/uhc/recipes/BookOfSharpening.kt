package com.github.w0819.game.uhc.recipes

import com.github.w0819.game.util.WeaponSmithing
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta

object BookOfSharpening : WeaponSmithing(
    NamespacedKey.minecraft("book_of_sharpening"),
    BookOfSharpening,
    3,
    true
) {
    init {
        shape(
            "1  ",
            " 22",
            " 23"
        )
        setIngredient('1', Material.FLINT)
        setIngredient('2', Material.PAPER)
        setIngredient('3', Material.IRON_SWORD)
    }
    object BookOfSharpening : Item(
        ItemStack(Material.ENCHANTED_BOOK).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text("Book of Sharpening"))
                (this as EnchantmentStorageMeta).addStoredEnchant(Enchantment.DAMAGE_ALL, 1, true)
            }
        }
    )
}