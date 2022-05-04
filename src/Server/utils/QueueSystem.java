package Server.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import Server.main.ServerMain;
import unit4.collectionsLib.Queue;

public class QueueSystem {

	private class Game {
		private Queue<UUID> players;
		private int alreadyJoined;
		private final int GROUP;

		public Game(int group) {
			this.players = new Queue<UUID>();
			this.alreadyJoined = 0;
			this.GROUP = group;
		}

		public boolean insertPlayer(UUID player) {
			if (alreadyJoined + 1 <= GROUP) {
				players.insert(player);
				alreadyJoined++;
				return true;
			}
			return false;
		}

		public UUID removePlayer() {
			if(players.isEmpty())
				return null;
			alreadyJoined--;
			return players.remove();
		}

		public UUID firstToJoin() {
			return players.head();
		}

		
		public boolean isFull() {
			boolean b = insertPlayer(null);
			removePlayer();
			return b;
		}

		public boolean isEmpty() {
			return players.isEmpty();
		}
		
		public int getGroup() {
			return GROUP;
		}
	}

	private Queue<Game>[] qs;
	private Map<UUID, Game> matchMakings;
	public static final int PLAYER_LIMIT = 16;

	public QueueSystem() {
		qs = (Queue<Game>[]) new Object[ServerMain.LOBBIES];
		for (int i = 0; i < ServerMain.LOBBIES; i++) {
			qs[i] = new Queue<QueueSystem.Game>();
		}
		matchMakings = new HashMap<UUID, QueueSystem.Game>();
	}
	/*
	 *           :שאלה 1
	 * D
	 * PUSH A
	 * B = POP A
	 * E	
	 * 
	 *           :שאלה 2
	 * E
	 * PUSH B
	 * A -= POP B
	 * E	 
	 */
	public boolean JoinAGame(UUID player, int lobby, int group) {
		if (lobby >= ServerMain.LOBBIES || lobby < 0)
			return false;
		Queue<Game> allGames = qs[lobby], backUp = new Queue<Game>();
		boolean matchMakingSucceed = false;
		Game game = null;
		while (!matchMakingSucceed && !allGames.isEmpty()) {
			game = allGames.remove();
			if (game.getGroup() == group && !(game.isFull())) {
				game.insertPlayer(player);
				matchMakings.put(player, game);
				matchMakingSucceed = true;
			}
			backUp.insert(game);
		}
		while (!backUp.isEmpty())
			allGames.insert(backUp.remove());
		if (!matchMakingSucceed) {
			Game g = new Game(group);
			g.insertPlayer(player);
			allGames.insert(g);
			matchMakings.put(player, g);
			matchMakingSucceed = true;
		}
		return matchMakingSucceed;
	}

	public boolean playerQuit(UUID player) {
		if(!(matchMakings.containsKey(player)))
			return false;
		Game game = matchMakings.get(player);
		Queue<UUID> players = new Queue<UUID>();
		boolean succeed = false;
		while (!(game.isEmpty())) {
			if(succeed || player.equals(game.firstToJoin())) {
				game.removePlayer();
				succeed = true;
				continue;
			}
			players.insert(game.removePlayer());
		}
		while(!(players.isEmpty()))
			game.insertPlayer(players.remove());
		matchMakings.remove(player);
		return succeed;
	}
}
