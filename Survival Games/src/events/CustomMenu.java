package events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import src.main;

public class CustomMenu implements Listener {
	
	
	
	@EventHandler
	public void onMenuOpen(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getItem() ==null)return;
			if(!e.getItem().hasItemMeta())return;
			if(!e.getItem().getItemMeta().hasDisplayName())return;
			if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(main.color("&6&lCustomisation Menu"))) {
				Player p = e.getPlayer();
				Inventory inv = Bukkit.createInventory(null,27,"&6&lCustomisation Menu");
				inv.setItem(13, cages());
				p.openInventory(inv);
			}
		}
	}
	
	
	
	public ItemStack cages() {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(main.color("&b&lCages Menu"));
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Open Cages Menu and");
		lore.add("§7Select a cage");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
		}

}
