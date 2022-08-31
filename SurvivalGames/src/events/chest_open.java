package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import src.main;
import state.Gstate;
import utils.GameState;


public class chest_open implements Listener {
	
	
	private static ArrayList<ItemStack> normal_item = new ArrayList<>();
	private static ArrayList<ItemStack> epic_item = new ArrayList<>();
	private static ArrayList<ItemStack> rare_item = new ArrayList<>();
	public static HashMap<Location, Inventory> clc = new HashMap<>();
	
	
	
	
	GameState GameState = new GameState();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChestOpen(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.DRAGON_EGG) {
			e.setCancelled(true);	
			}

		}
		if(main.getState(Gstate.inGame)) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		if(e.getClickedBlock().getType() == Material.CHEST) {
			if(utils.GameState.GamePlayer.contains(e.getPlayer().getName())) {
			Player p = e.getPlayer();
			e.setCancelled(true);
			if(!clc.containsKey(e.getClickedBlock().getLocation())) {
				Inventory inv;
				inv = getitems();
			p.openInventory(inv);
			GameState.GetGamePlayerinfo().get(e.getPlayer().getName()).addcrates(1);
			clc.put(e.getClickedBlock().getLocation(), inv);	
			}else {
				p.openInventory(clc.get(e.getClickedBlock().getLocation()));
			}
			
			}
		}
		}
		}
	}
	

	
	
	public Inventory getitems() {
		Random rand = new Random();
		Inventory inv = Bukkit.createInventory(null, 27, "loot chest");
		int times = rand.nextInt(6)+1;
		for(int i=0;i<times;i++) {
		ArrayList<ItemStack> chest_items = new ArrayList<>();
		int chance = rand.nextInt(100) +1;
		int slot = rand.nextInt(27);
			if (chance < 40) {
				int is = rand.nextInt(rare_item.size());
				if(!chest_items.contains(rare_item.get(is))) {
					inv.setItem(slot,rare_item.get(is));
					chest_items.add(rare_item.get(is));

				}
			}
			else if (chance < 70) {
				int is = rand.nextInt(epic_item.size());
				if(!chest_items.contains(epic_item.get(is))) {
					inv.setItem(slot,epic_item.get(is));
					chest_items.add(epic_item.get(is));
				}
			}
			else {
				int is = rand.nextInt(normal_item.size());
				if(!chest_items.contains(normal_item.get(is))) {
					inv.setItem(slot,normal_item.get(is));
					chest_items.add(normal_item.get(is));
				}
			}
		}
		
		return inv;
	}
	

	public static void addChestItems() {
		//normal-----------------------
		normal_item.add(new ItemStack(Material.APPLE,2));
		normal_item.add(new ItemStack(Material.APPLE));
		normal_item.add(new ItemStack(Material.LEATHER_BOOTS));
		normal_item.add(new ItemStack(Material.LEATHER_CHESTPLATE));
		normal_item.add(new ItemStack(Material.LEATHER_HELMET));
		normal_item.add(new ItemStack(Material.LEATHER_LEGGINGS));
		normal_item.add(new ItemStack(Material.BAKED_POTATO,2));
		normal_item.add(new ItemStack(Material.GOLD_HELMET));
		normal_item.add(new ItemStack(Material.GOLD_BOOTS));
		normal_item.add(new ItemStack(Material.RAW_CHICKEN));
		normal_item.add(new ItemStack(Material.WOOD_AXE));
		normal_item.add(new ItemStack(Material.PUMPKIN_PIE));


		//normal-----------------------
		//rare------------------------
		rare_item.add(new ItemStack(Material.COOKED_CHICKEN,2));
		rare_item.add(new ItemStack(Material.APPLE));
		rare_item.add(new ItemStack(Material.ARROW,2));
		rare_item.add(new ItemStack(Material.ARROW,3));
		rare_item.add(new ItemStack(Material.GOLD_AXE));
		rare_item.add(new ItemStack(Material.FISHING_ROD));
		rare_item.add(new ItemStack(Material.GOLD_CHESTPLATE));
		rare_item.add(new ItemStack(Material.GOLD_LEGGINGS));
		rare_item.add(new ItemStack(Material.CHAINMAIL_HELMET));
		rare_item.add(new ItemStack(Material.CHAINMAIL_BOOTS));
		rare_item.add(new ItemStack(Material.COOKIE));
		rare_item.add(new ItemStack(Material.FEATHER));
		rare_item.add(new ItemStack(Material.STICK));
		rare_item.add(new ItemStack(Material.GOLD_INGOT));
		rare_item.add(new ItemStack(Material.FLINT));
		rare_item.add(new ItemStack(Material.APPLE));
		rare_item.add(new ItemStack(Material.WEB));
		rare_item.add(new ItemStack(Material.WOOD_SWORD));
		rare_item.add(new ItemStack(Material.GOLD_SWORD));
		rare_item.add(new ItemStack(Material.STRING,2));
		//rare------------------------
		
		//epic*------------------------
		epic_item.add(new ItemStack(Material.STONE_AXE));
		epic_item.add(new ItemStack(Material.DIAMOND));
		epic_item.add(new ItemStack(Material.IRON_INGOT));
		epic_item.add(new ItemStack(Material.IRON_CHESTPLATE));
		epic_item.add(new ItemStack(Material.IRON_BOOTS));
		epic_item.add(new ItemStack(Material.IRON_LEGGINGS));
		epic_item.add(new ItemStack(Material.IRON_HELMET));
		epic_item.add(new ItemStack(Material.STONE_SWORD));
		epic_item.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		epic_item.add(new ItemStack(Material.TNT));
		epic_item.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		epic_item.add(new ItemStack(Material.EXP_BOTTLE));
		
		//epic*------------------------

	}
	
	
	
	

}
