package commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import src.main;
import state.Gstate;
import utils.locationAPI;


public class SG implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(p.hasPermission("sg.admin")) {
			if(args.length == 1) {
				String tact = args[0];
				if(tact.equalsIgnoreCase("setspawns")) {
					p.getInventory().setItem(0, item());
					p.sendMessage(main.Prefix + "§aYou got the set spawns chest");
					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5.0f, 3.0f);

				}
				else if(tact.equalsIgnoreCase("setdeathmatch")) {
					p.getInventory().setItem(0, item2());
					p.sendMessage(main.Prefix + "§aYou got the set deathmatch spawns chest");
					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5.0f, 3.0f);

				}
				else if(tact.equalsIgnoreCase("setSpecDM")) {
					p.sendMessage(main.Prefix+ main.color("&eSpectate DeathMatch &ehas been set!"));
					locationAPI.setLocation(p.getLocation(), "specdm");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.5f, 3.0f);
					}
				else if(tact.equalsIgnoreCase("setlobby")) {
					p.sendMessage(main.Prefix+ main.color("&eLobby Spawn &ehas been set!"));
					locationAPI.setLocation(p.getLocation(), "lobby");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.5f, 3.0f);
				}
				else if(tact.equalsIgnoreCase("stop")) {
					main.setState(Gstate.noSpawns);
					Bukkit.broadcastMessage(main.Prefix+ main.color("&cGame Has stoped!"));
					for(Player pls : Bukkit.getOnlinePlayers()) {
						if(!pls.hasPermission("sg.admin")) {
							pls.kickPlayer("&cGame Has stoped!");
						}
					}
					p.sendMessage(main.color("&cNow you Can Edit (Spawns/Map...)"));
					main.stoped = true;
				}
				else if(tact.equalsIgnoreCase("help")) {
					p.sendMessage("§7-------------------");
					p.sendMessage("      §3Help SG      ");
					p.sendMessage("§6/sg setspawns ");
					p.sendMessage("§6/sg setdeathmatch  ");
					p.sendMessage("§6/sg setlobby       ");
					p.sendMessage("§6/sg setmap [id]    ");
					p.sendMessage("§6/sg setSpecDM      ");
					p.sendMessage("§6/sg stop           ");
					p.sendMessage("§7-------------------");
				}
				else {
					p.sendMessage("§7-------------------");
					p.sendMessage("      §3Help SG      ");
					p.sendMessage("§6/sg setspawns ");
					p.sendMessage("§6/sg setdeathmatch  ");
					p.sendMessage("§6/sg setlobby       ");
					p.sendMessage("§6/sg setmap [id]    ");
					p.sendMessage("§6/sg setSpecDM      ");
					p.sendMessage("§6/sg stop           ");
					p.sendMessage("§7-------------------");

				}
			}
			}
		
		}
		
		return false;
	}
	
	
	public ItemStack item() {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cSetSpawns Chest");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7right a click a block");
		lore.add("§7and select spawn number");
		lore.add("§cCaution: §7This Will set The location");
		lore.add("§7To the same Map you are in");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack item2() {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cSet deathmatch spawn Chest");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7right a click a block");
		lore.add("§7and select spawn number");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	

}
