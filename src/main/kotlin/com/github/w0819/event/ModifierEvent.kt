package com.github.w0819.event

import com.github.w0819.enchant.AddDamage
import com.github.w0819.enchant.ReviveToken
import com.github.w0819.game.uhc.modifiers.*
import com.github.w0819.game.uhc.recipes.DragonArmor
import com.github.w0819.game.uhc.recipes.GoldenHead
import com.github.w0819.game.uhc.recipes.`King'sRod`
import com.github.w0819.game.util.ExtraUltimates
import com.github.w0819.game.util.GroupUtil
import com.github.w0819.game.util.Util
import com.github.w0819.plugin.UHCPlugin
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.EnderDragon
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
class ModifierEvent : Listener {

    val reviveTokenItem = ItemStack(Material.EMERALD).apply { addEnchantment(ReviveToken,ReviveToken.startLevel) }
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        when(val item = e.item) {
             reviveTokenItem -> {
                // reviveToken을 이용해 팀원 살리기
                if (item.isSimilar(reviveTokenItem) && item.amount >= 4) {
                    val team = UHCPlugin.game.teams.find { player in it.players }
                    if (team == null) player.sendMessage("you don't have team") else {
                        val deadPlayers = team.players.filter { it.isDead }
                        if (deadPlayers.isEmpty()) player.sendMessage("no one dead in your team") else {
                            deadPlayers.random().apply {
                                gameMode = GameMode.SURVIVAL
                                teleport(player.location)
                            }
                            item.amount -= 4
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun onPlayerKill(e: PlayerDeathEvent) {
        val player = e.player
        val killer = player.killer ?: return
        when (UHCPlugin.game.modifier) {
            is Gold -> e.drops.add(ItemStack(Material.GOLD_INGOT))
            is Pearl -> e.drops.add(ItemStack(Material.ENDER_PEARL))
            is DoubleHead -> e.drops.add(GoldenHead.result)
            is SwordMaster -> {
                val item = killer.inventory.itemInMainHand
                if (item.type in GroupUtil.swordList) {
                    val enchants = item.enchantments
                    if (enchants.containsKey(AddDamage)) {
                        item.editMeta { itemMeta -> itemMeta.apply { addEnchant(AddDamage, (enchants[AddDamage] ?: return@editMeta),true) } }
                    } else {
                        item.addEnchantment(AddDamage, AddDamage.startLevel)
                    }
                }
            }
            is ReviveTokens -> {
                killer.inventory.addItem(reviveTokenItem)
            }
        }
    }
    @EventHandler
    fun onEntityDamage(e: EntityDamageEvent) {
        if (e.cause in GroupUtil.naturalDamageEvent) e.damage *= 3
    }

    @EventHandler
    fun onEntityDeath(e: EntityDeathEvent) {
        val killer = e.entity.killer ?: return
        when(UHCPlugin.game.modifier) {
            is ExtraDragon -> {
                if (e.entity is EnderDragon) {
                    killer.inventory.addItem(DragonArmor.result, DragonArmor.result)
                    ExtraDragon.spawnDragon(killer)
                }
            }
            is Pearl -> killer.inventory.addItem(ItemStack(Material.ENDER_PEARL))
            is HealthOnKill -> killer.healthScale++
            is BowMaster -> e.drops.add(ItemStack(Material.ARROW))
        }
    }

    @EventHandler
    fun onProjectileLaunch(e: ProjectileLaunchEvent) {
        if (UHCPlugin.game.modifier is Projectiles) e.isCancelled = true
    }

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        val block = e.block
        val blockType = block.type
        val world = e.block.world
        val location = e.block.location
        when(UHCPlugin.game.modifier) {
            is FlowerPower -> {
                if (blockType in GroupUtil.flowers) {
                    e.isDropItems = false
                    val materialList = ((Material.values().toList() - GroupUtil.nonSurvivalItems.toSet()) + UHCPlugin.itemList().map { it.type })
                    world.dropItem(
                        location,
                        ItemStack(materialList.random())
                    )
                }
            }
            is Mining -> {
                if (blockType in GroupUtil.ores)
                    if (Util.percent(50)) world.dropItem(location, ItemStack(block.type))
            }
        }
    }
    @EventHandler
    fun onCraftItem(e: CraftItemEvent) {
        when(UHCPlugin.game.modifier) {
            is Fishing -> {
                if (e.recipe.result.type == Material.FISHING_ROD) {
                    e.result = Event.Result.DENY
                    e.inventory.result = `King'sRod`.result
                }
            }
        }
    }
    @EventHandler
    fun onPlayerFishing(e: PlayerFishEvent) {
        when(UHCPlugin.game.modifier) {
            is Fishing -> {
                val extraUltimate = UHCPlugin.recipeList().filterIsInstance<ExtraUltimates>().random().result
                e.hook.location.y = 100000000.0 // TODO ?????
                e.hook.world.dropItem(e.hook.location, extraUltimate)
            }
        }
    }
}