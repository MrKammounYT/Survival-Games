package utils;


import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import MySQL.Points;
import src.main;
import state.Gstate;

@SuppressWarnings("deprecation")
public class Options implements Listener {
	
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		if(main.getState(Gstate.waiting) || main.getState(Gstate.starting)) {
			e.setCancelled(true);
		}
		if(GameState.spec.contains(e.getEntity().getName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
    public void onBlockSpread(BlockSpreadEvent e) {
            e.setCancelled(true);
    }

	@EventHandler
	public void ondropfromentitys(EntityDeathEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			e.getDrops().clear();
		}
	}
	
	@EventHandler
	public void oncraft(CraftItemEvent e) {
		if(e.getCurrentItem().getType() == Material.WATER_BUCKET) {
			e.setCancelled(true);
		}
		if(e.getCurrentItem().getType() == Material.MELON_BLOCK) {
			e.setCancelled(true);
		}
		if(e.getCurrentItem().getType() == Material.IRON_PICKAXE) {
			e.setCancelled(true);
		}
		if(e.getCurrentItem().getType() == Material.WATER_BUCKET) {
			e.setCancelled(true);
		}
		if(e.getCurrentItem().getType() == Material.SHEARS) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onchat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if(main.getState(Gstate.finish)) {
			if(GameState.spec.contains(p.getName())) {
				e.setFormat(main.color("&cSpectate &7❘ &8" + Titles.getPlayerName(p) + " &8» &f" + e.getMessage()));
			}else {
				e.setFormat(main.color(Titles.getPlayerName(p) + " &8» §f" + e.getMessage()));
				
			}
			return;
		}
		if(GameState.spec.contains(p.getName())) {
			e.setCancelled(true);
			for(int i=0;i<GameState.spec.size();i++) {
				Player ps = Bukkit.getPlayer(GameState.spec.get(i));
				ps.sendMessage(main.color("&cSpectate &7❘ &8" + Titles.getPlayerName(p) + " &8» &f" + e.getMessage()));
			}
			return;
		}
		int point= Points.getpoints(p.getUniqueId());
		if(main.getState(Gstate.waiting) || main.getState(Gstate.starting)) {
			e.setFormat(main.color("&e"+point+" &8❘ " + Titles.getPlayerName(p) + " &8» &f" + e.getMessage()));
		}else {
			e.setFormat(main.color(Titles.getPlayerName(p) + " &8» &f" + e.getMessage()));
			}
		
		
	}

	
	@EventHandler
	public void ondropitemsSpectate(PlayerDropItemEvent e) {
		if(GameState.spec.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
		if(main.getState(Gstate.finish) ||main.getState(Gstate.waiting) || main.getState(Gstate.starting) || main.getState(Gstate.waittogame)) {
			e.setCancelled(true);
		}
		
	}
	/*@EventHandler
	public void onPlayermoveEvent(PlayerMoveEvent e) {
		if(main.getState(Gstate.waittogame)) {
			if(e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.STONE_PLATE) {
			 if (e.getFrom().getZ() != e.getTo().getZ() && e.getFrom().getX() != e.getTo().getX())return;
			 }else {
				 if (e.getFrom().getZ() != e.getTo().getZ() && e.getFrom().getX() != e.getTo().getX()) {
					 e.getPlayer().teleport(e.getFrom());
				 }

			 }
		}
	}*/
	
	
	@EventHandler
	public void oncloseenchant(InventoryCloseEvent e) {
		if(e.getInventory() instanceof EnchantingInventory) {
			e.getInventory().setItem(1, new ItemStack(Material.AIR));
		}
	}
	@EventHandler
	public void onclicklapis(InventoryClickEvent e) {
		if(e.getInventory() instanceof EnchantingInventory ) {
			if(e.getCurrentItem().getType() == Material.INK_SACK) {
				e.setCancelled(true);
			}
		}
	}
	
	
	
	
	@EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory() instanceof EnchantingInventory) {
            EnchantingInventory inv = (EnchantingInventory) e.getInventory();
            Dye d = new Dye();
            d.setColor(DyeColor.BLUE);
            ItemStack i = d.toItemStack();
            i.setAmount(64);
            inv.setItem(1, i);
        }
    }
	
	
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.TNT) {
			e.setCancelled(false);
			e.getBlock().setType(Material.AIR);
			TNTPrimed tnt = e.getPlayer().getWorld().spawn(e.getBlock().getLocation(), TNTPrimed.class);
			tnt.setFuseTicks(40);
			return;
			
		}
		if(e.getBlock().getType() == Material.WEB) {
			e.setCancelled(false);
			return;
		}
		if(e.getBlock().getType() == Material.FIRE) {
			e.setCancelled(false);
			return;
		}
		
		if(!main.getState(Gstate.noSpawns)) {
			e.setCancelled(true);
		}else {
			e.setCancelled(false);
		}
	}
	@EventHandler
	public void onBlockbreak(BlockBreakEvent e) {
		if(!main.getState(Gstate.noSpawns)) {
				if(e.getBlock().getType() == Material.LEAVES_2 ||e.getBlock().getType() == Material.LEAVES ||e.getBlock().getType() == Material.WEB || 
						e.getBlock().getType() == Material.MELON_BLOCK) {
					e.setCancelled(false);
					return;
				}
				e.setCancelled(true);
		}
	
	}
	
	
	@EventHandler
	public void ondamage(EntityDamageByEntityEvent e) {
		
		
		if(main.getState(Gstate.waittogame)) {
			e.setCancelled(true);
		}
		if(GameState.spec.contains(e.getEntity().getName())) {
			e.setCancelled(true);
			return;
		}
		if(main.getState(Gstate.finish) ||main.getState(Gstate.waiting) || main.getState(Gstate.starting)  || main.getState(Gstate.finish)) {
			e.setCancelled(true);
		}
	
	}
	

	@EventHandler
	public void onBlockExplode(EntityExplodeEvent e) {
	    if (!(e.getEntity() instanceof TNTPrimed)) return;
	    e.setCancelled(true);

	}
	
	@EventHandler
	public void onentitydamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
		if(main.getState(Gstate.waiting) || main.getState(Gstate.starting)) {
			if(e.getCause() == DamageCause.VOID) {
				e.setCancelled(true);
				e.getEntity().teleport(LocationAPI.getLocation("lobby"));

			}
		}
		if(e.getCause() == DamageCause.ENTITY_EXPLOSION || e.getCause() == DamageCause.BLOCK_EXPLOSION) {
			e.setDamage((e.getDamage()/2));
			
		}
		if(main.getState(Gstate.waiting) || main.getState(Gstate.starting) || main.getState(Gstate.finish)) {
			e.setCancelled(true);
		}
		if(GameState.spec.contains(e.getEntity().getName())) {
			e.setCancelled(true);
		}
		}
	}
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) event.setCancelled(true);
    }

	
}
