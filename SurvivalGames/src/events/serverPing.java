package events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import src.main;
import state.Gstate;

public class serverPing implements Listener
{
	
	
	@EventHandler
	public void onserverPing(ServerListPingEvent e) {
		if(main.getState(Gstate.waiting)) {
			e.setMotd("Waiting");
			e.setMaxPlayers(24);
		}
		else if(main.getState(Gstate.starting)) {
			e.setMotd("Starting");
			e.setMaxPlayers(24);

		}
		else if(main.getState(Gstate.waittogame)) {
			e.setMotd("InGame");
			e.setMaxPlayers(40);

		}
		else if(main.getState(Gstate.inGame)) {
			e.setMotd("InGame");
			e.setMaxPlayers(40);

		}
		else if(main.getState(Gstate.deathMatch)) {
			e.setMotd("DeathMatch");
			e.setMaxPlayers(40);

		}
		else if(main.getState(Gstate.finish)) {
			e.setMotd("restarting");
			e.setMaxPlayers(0);

		}
		else if(main.getState(Gstate.noSpawns)) {
			e.setMotd("restarting");
			e.setMaxPlayers(0);

		}
	}

}
