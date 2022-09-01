package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


public class Chest_open extends GameState implements Listener {
	
	
	public static HashMap<Location, Inventory> clc = new HashMap<>();
	private static HashMap<Integer, ArrayList<ItemStack>> chestitems=new HashMap<>();
	
	
	

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
				inv = getitems(p);
			p.openInventory(inv);
			GetGamePlayerinfo().get(e.getPlayer().getName()).addcrates(1);
			clc.put(e.getClickedBlock().getLocation(), inv);	
			}else {
				p.openInventory(clc.get(e.getClickedBlock().getLocation()));
			}
			
			}
		}
		}
		}
	}
	
	

	
	
	public Inventory getitems(Player p) {
		Random rand = new Random();
		Inventory inv = Bukkit.createInventory(null, 27, "loot chest");
		int times = rand.nextInt(6)+1;
		for(int i=0;i<times;i++) {
		ArrayList<ItemStack> repeat = new ArrayList<>();
		int chance = rand.nextInt(100) +1;
		int slot = rand.nextInt(27);
			if (chance > 20) {
				int x = rand.nextInt(chestitems.get(2).size());
				ItemStack item = chestitems.get(2).get(x);
				for(ItemStack r : repeat) {
					if(r.equals(item)) {
						x=rand.nextInt(chestitems.get(2).size());
					}
				
				}
				inv.setItem(slot,item);
				repeat.add(item);
				
				
			}
			else if (chance > 50) {
				int x = rand.nextInt(chestitems.get(3).size());
				ItemStack item = chestitems.get(3).get(x);
				for(ItemStack r : repeat) {
					if(r.equals(item)) {
						x=rand.nextInt(chestitems.get(3).size());
					}
				
				}
				inv.setItem(slot,item);
				repeat.add(item);
				
				
			}
			else if (chance > 70) {
				int x = rand.nextInt(chestitems.get(4).size());
				ItemStack item = chestitems.get(4).get(x);
				for(ItemStack r : repeat) {
					if(r.equals(item)) {
						x=rand.nextInt(chestitems.get(4).size());
					}
				
				}
				inv.setItem(slot,item);
				repeat.add(item);

				
				
			}
			else {
				int x = rand.nextInt(chestitems.get(1).size());
				ItemStack item = chestitems.get(1).get(x);
				for(ItemStack r : repeat) {
					if(r.equals(item)) {
						x=rand.nextInt(chestitems.get(1).size());
					}
				
				}
				inv.setItem(slot,item);
				repeat.add(item);
			}

		}
		
		return inv;
	}
	

	public static void addChestItems() {
		ArrayList<ItemStack> normal_item = new ArrayList<>();
		ArrayList<ItemStack> epic_item = new ArrayList<>();
		ArrayList<ItemStack> prerare_item = new ArrayList<>();
		ArrayList<ItemStack> rare_item = new ArrayList<>();
		//normal---------------------------------------------------
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
		normal_item.add(new ItemStack(Material.COOKED_BEEF,2));
		normal_item.add(new ItemStack(Material.WOOD_AXE));
		normal_item.add(new ItemStack(Material.PUMPKIN_PIE));
		chestitems.put(1, normal_item);
		//normal-----------------------
		//rare------------------------
		rare_item.add(new ItemStack(Material.COOKED_CHICKEN,2));
		prerare_item.add(new ItemStack(Material.APPLE));
		rare_item.add(new ItemStack(Material.ARROW,2));
		prerare_item.add(new ItemStack(Material.ARROW,3));
		rare_item.add(new ItemStack(Material.GOLD_AXE));
		rare_item.add(new ItemStack(Material.FISHING_ROD));
		prerare_item.add(new ItemStack(Material.GOLD_CHESTPLATE));
		rare_item.add(new ItemStack(Material.GOLD_LEGGINGS));
		prerare_item.add(new ItemStack(Material.CHAINMAIL_HELMET));
		prerare_item.add(new ItemStack(Material.CHAINMAIL_BOOTS));
		rare_item.add(new ItemStack(Material.COOKIE));
		prerare_item.add(new ItemStack(Material.FEATHER));
		rare_item.add(new ItemStack(Material.STICK));
		rare_item.add(new ItemStack(Material.GOLD_INGOT));
		prerare_item.add(new ItemStack(Material.FLINT));
		rare_item.add(new ItemStack(Material.APPLE));
		rare_item.add(new ItemStack(Material.WEB));
		rare_item.add(new ItemStack(Material.WOOD_SWORD));
		prerare_item.add(new ItemStack(Material.GOLD_SWORD));
		rare_item.add(new ItemStack(Material.STRING,2));
		chestitems.put(2, rare_item);
		chestitems.put(3, prerare_item);
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
		chestitems.put(4, epic_item);
		//epic*------------------------

	}
	
	
	
	

}
