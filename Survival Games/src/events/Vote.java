package events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import src.main;
import utils.GameState;
import utils.MapLocationApi;

public class Vote extends GameState implements Listener {
	
	public Inventory voteinv;

	@EventHandler
	public void oninteractVote(PlayerInteractEvent e) {
		if(e.getItem() ==null)return;
		if(!(e.getItem().hasItemMeta()))return;
		if(!(e.getItem().getItemMeta().hasDisplayName()))return;
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(main.color("&e&lVote Menu"))) {
			if(GameState.GamePlayer.size() >= getMinPlayers()) {
			openVoteInv(e.getPlayer());
			}else {
				e.getPlayer().sendMessage(main.Prefix + "§e" + (getMinPlayers() -GameState.GamePlayer.size()) +" §cPlayer needed for voting");
			}
			}
		}
	}
	
	
	 @EventHandler
	 public void oninventoryClickVote(InventoryClickEvent e) {
		 if(e.getCurrentItem() == null)return;
		 if(!(e.getCurrentItem().hasItemMeta()))return;
		 if(e.getClickedInventory().getTitle() == null)return;
		 if(e.getClickedInventory().getTitle().equalsIgnoreCase("§6§lVote Menu")) {
			 e.setCancelled(true);
			 Player p = (Player) e.getWhoClicked();
			 if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a"+GetGameMapInfo().get(MapLocationApi.getMapByID(e.getSlot()+1)).getMapName())) {
				 if(GetGamePlayerinfo().get(p.getName()).hasvoted()) {
					 GetGameMapInfo().get(GetGamePlayerinfo().get(p.getName()).GetVotedMap()).removeVotes(1);
					 
				 }
				 GetGamePlayerinfo().get(p.getName()).setvote(true);
				 GetGamePlayerinfo().get(p.getName()).setVotedMap(GetGameMapInfo().get(MapLocationApi.getMapByID(e.getSlot()+1)).getMapName());
				 GetGameMapInfo().get(MapLocationApi.getMapByID(e.getSlot()+1)).addVotes(1);
				 p.sendMessage(main.Prefix + main.color("&eYou Have Voted to &b" + GetGameMapInfo().get(MapLocationApi.getMapByID(e.getSlot()+1)).getMapName()));
				 p.closeInventory();
				 for(Player pls:Bukkit.getOnlinePlayers()) {
					 if(pls.getOpenInventory() == voteinv) {
						 pls.updateInventory();
					 }
				 }
			 }
			 
		 }
	 }

	 public void openVoteInv(Player p) {
		 voteinv = Bukkit.createInventory(null, 9, "§6§lVote Menu");
		 voteinv.setItem(0, CreateMap(GetGameMapInfo().get(MapLocationApi.getMapByID(1)).getMapName(), "§7Total Votes: §e"+GetGameMapInfo().get(MapLocationApi.getMapByID(1)).getVote()));
		 voteinv.setItem(1, CreateMap(GetGameMapInfo().get(MapLocationApi.getMapByID(2)).getMapName(), "§7Total Votes: §e"+GetGameMapInfo().get(MapLocationApi.getMapByID(2)).getVote()));
		 voteinv.setItem(2, CreateMap(GetGameMapInfo().get(MapLocationApi.getMapByID(3)).getMapName(), "§7Total Votes: §e"+GetGameMapInfo().get(MapLocationApi.getMapByID(3)).getVote()));
		 voteinv.setItem(3, CreateMap(GetGameMapInfo().get(MapLocationApi.getMapByID(4)).getMapName(), "§7Total Votes: §e"+GetGameMapInfo().get(MapLocationApi.getMapByID(4)).getVote()));
		 voteinv.setItem(4, CreateMap(GetGameMapInfo().get(MapLocationApi.getMapByID(5)).getMapName(), "§7Total Votes: §e"+GetGameMapInfo().get(MapLocationApi.getMapByID(5)).getVote()));

		 p.openInventory(voteinv);
	 }
	 








public ItemStack CreateMap(String name,String Lore1) {
	ItemStack item = new ItemStack(Material.PAPER);
	ItemMeta meta = item.getItemMeta();
	meta.setDisplayName("§a"+name);
	ArrayList<String> s = new ArrayList<>();
	s.add(Lore1);
	meta.setLore(s);
	item.setItemMeta(meta);
	return item;
}
	
	
	
}

