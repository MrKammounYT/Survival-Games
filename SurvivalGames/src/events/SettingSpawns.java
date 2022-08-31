package events;



import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import src.main;
import state.Gstate;
import utils.MapLocationApi;
import utils.locationAPI;

public class SettingSpawns implements Listener {
	
	
	HashMap<Player, Location> lc = new HashMap<>();
	HashMap<Player, Integer> id = new HashMap<>();
	HashMap<Player, Location> lc2 = new HashMap<>();

	
	@EventHandler
	public void onSetSpawn(PlayerInteractEvent e) {
		if(main.getState(Gstate.noSpawns)) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		if(p.hasPermission("sg.admin")) {
				if(e.getPlayer().getItemInHand().hasItemMeta()) {
					if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cSetSpawns Chest")) {
						e.setCancelled(true);				
						Inventory inv = Bukkit.createInventory(null, 9, "§2Select Map Id");
						for(int i=1;i<6;i++) {
							inv.setItem(i, mapid(i));
						}
						Location f = e.getClickedBlock().getLocation().add(0.5, 1, 0.5);
						lc.put(p, f);
						p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5.0f, 3.0f);
						p.openInventory(inv);
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cSet deathmatch spawn Chest")) {
						e.setCancelled(true);				
						Inventory inv = Bukkit.createInventory(null, 27, "§2Select Spawn DeathMatch Number");
						for(int i=1;i<5;i++) {
							inv.setItem(i-1, spawnn2(i));
						}
						Location f = e.getClickedBlock().getLocation().add(0.5, 1, 0.5);
						lc2.put(p, f);
						p.openInventory(inv);
					}
				}
			}
			
		}
		}
		
		
		}
	
	
	
	@EventHandler
	public void onclickforspawns(InventoryClickEvent e) {
		if(e.getWhoClicked().hasPermission("sg.admin")) {
			Player p = (Player) e.getWhoClicked();
			if(lc.containsKey(p)) {
			if(e.getClickedInventory() != null) {
			if(e.getClickedInventory().getTitle() != null) {
				if(e.getCurrentItem() != null) {
					if(e.getCurrentItem().hasItemMeta()) {
						if(e.getClickedInventory().getTitle().equalsIgnoreCase("§2Select Map Id")) {
							e.setCancelled(true);
							Inventory inv = Bukkit.createInventory(null, 27, "§2Select Spawn Number");
							for(int i=1;i<25;i++) {
								inv.setItem(i-1, spawnn(p,i,e.getSlot()));
							}
							id.put(p, e.getSlot());
							p.openInventory(inv);
						}

				if(e.getClickedInventory().getTitle().equalsIgnoreCase("§2Select Spawn Number")) {	
							if(lc.containsKey(e.getWhoClicked())) {
							for(int i=1;i<25;i++) {
							if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSpawn: §e"+i)) {
								e.setCancelled(true);
								MapLocationApi.setLocation(id.get(p),lc.get(p),"s"+i);
								p.sendMessage(main.Prefix+ main.color("&eSpawn &a" + i +" &ehas been set with map id &a"+id.get(p)));
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.5f, 3.0f);	
								id.remove(p);
								lc.remove(p);
								p.closeInventory();
								}
							}
							}
						}
					
				
					}
				}
				
			}
			}
		}}
	}
	@EventHandler
	public void onclickforDspawns(InventoryClickEvent e) {
		if(e.getWhoClicked().hasPermission("sg.admin")) {
			Player p = (Player) e.getWhoClicked();
			if(lc2.containsKey(p)) {
			if(e.getClickedInventory() != null) {
			if(e.getClickedInventory().getTitle() != null) {
				if(e.getClickedInventory().getTitle().equalsIgnoreCase("§2Select Spawn DeathMatch Number")) {
				if(e.getCurrentItem() != null) {
					if(e.getCurrentItem().hasItemMeta()) {	
							for(int i=1;i<5;i++) {
								if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSpawn d: §e"+i)) {
									locationAPI.setLocation(lc2.get(p), "d"+i);
									p.sendMessage(main.Prefix+ main.color("&eDeathmatch Spawn &a" + i +" &ehas been set!"));
									p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.5f, 3.0f);	
									lc2.remove(p);
									p.closeInventory();
								}
							
						}
					}}}
			}}
			}
		}
						
					}
	
	 @EventHandler
	 public void onclose(InventoryCloseEvent e) {
		 if(e.getInventory() != null) {
			 if(e.getInventory().getTitle() !=null) {
				 if(e.getInventory().getTitle().equalsIgnoreCase("§2Select Spawn DeathMatch Number")) {
					 lc2.remove(e.getPlayer());

				 }
				 if(e.getInventory().getTitle().equalsIgnoreCase("§2Select Spawn Number")) {
					 lc.remove(e.getPlayer());
						id.remove(e.getPlayer());

					 
				 }

			 }
		 }
	 }
		public ItemStack mapid(int number) {
			ItemStack item = new ItemStack(Material.GOLD_INGOT);
			if(number == 1) {
				item =  new ItemStack(Material.COAL);
			}
			else if(number == 2) {
				item =  new ItemStack(Material.IRON_INGOT);
			}
			else if(number == 3) {
				item =  new ItemStack(Material.GOLD_INGOT);
			}
			else if(number == 4) {
				item =  new ItemStack(Material.EMERALD);
			}
			else if(number == 5) {
				item =  new ItemStack(Material.DIAMOND);
			}
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§aMap id: §e"+number);
			item.setItemMeta(meta);
			return item;
		}

	public ItemStack spawnn(Player p,int number,int id) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setDisplayName("§aSpawn: §e"+number);
		meta.setOwner("ElMarcosFTW");
		ArrayList<String> lore = new ArrayList<>();
		try {
			MapLocationApi.getLocation(id,p.getLocation().getWorld().getName(),"s"+number);
			lore.add("§eSpawn Statue: §aset");

		} catch (Exception e) {
			lore.add("§eSpawn Statue: §cNot set");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack spawnn2(int number) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setDisplayName("§aSpawn d: §e"+number);
		meta.setOwner("ElMarcosFTW");
		ArrayList<String> lore = new ArrayList<>();
		try {
			locationAPI.getLocation("d"+number);
			lore.add("§eSpawn Statue: §aset");

		} catch (Exception e) {
			lore.add("§eSpawn Statue: §cNot set");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	
}
