package events;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

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
import org.bukkit.inventory.meta.SkullMeta;

import src.main;
import utils.GameState;

public class Spectate_Items implements Listener {

	
	HashMap<String, String> f = new HashMap<>();
	
	@EventHandler
	public void onClickCompass(PlayerInteractEvent e) {
		if(GameState.spec.contains(e.getPlayer().getName())) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock() != null) {
				e.setCancelled(true);
			}
			}
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().hasItemMeta()) {
				if(e.getPlayer().getItemInHand().getType() == Material.BED) {
					teleportServer( e.getPlayer(),"lobby");
				}
				 if(e.getPlayer().getItemInHand().getType() == Material.COMPASS) {
						Player p = e.getPlayer();
						Inventory inv = Bukkit.createInventory(null, 27, "§2Spectate Menu");
						for(int i=0;i<GameState.GamePlayer.size();i++) {
							inv.setItem(i, createSkull(Bukkit.getPlayer(GameState.GamePlayer.get(i))));
						}
						inv.setItem(26, close());
						p.openInventory(inv);
						
					}
			}
			}
		}
	}
	
	@EventHandler
	public void onClickCompass(InventoryClickEvent e) {
		if(e.getClickedInventory() !=null) {
			if(e.getClickedInventory().getTitle().equalsIgnoreCase("§2Spectate Menu")) {
				e.setCancelled(true);
				if(e.getCurrentItem() !=null && e.getCurrentItem().hasItemMeta()) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main.color("&cClose"))) {
						e.getWhoClicked().closeInventory();
						return;
						
					}
					if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
						Player p  = (Player) e.getWhoClicked();
						p.teleport(Bukkit.getPlayer(f.get(e.getCurrentItem().getItemMeta().getDisplayName())));
					}
				}
			}
		}
	}
	
	public void teleportServer(Player p, String server){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
 
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException eee) {
        }
 
        p.sendPluginMessage(main.getinstance(), "BungeeCord", b.toByteArray());
    }
	
	public ItemStack close() {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(main.color("&cClose"));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack createSkull(Player p) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(p.getName());
		meta.setDisplayName(""+p.getPlayerListName());
		f.put(p.getPlayerListName(), p.getName());
		item.setItemMeta(meta);
		return item;
	}
	
}
