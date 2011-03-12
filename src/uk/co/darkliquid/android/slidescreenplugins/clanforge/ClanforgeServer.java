package uk.co.darkliquid.android.slidescreenplugins.clanforge;

public class ClanforgeServer {
	public String name;
	public String host;
	public String game;
	public String map;
	public boolean status;
	public String numplayers;
	public String maxplayers;

	public int getID() {
		return (
			this.name + "-" + this.host + "-" +
			this.game + "-" + this.map + "-" +
			(this.status ? "UP" : "DOWN") + "-" +
			this.numplayers + "-" + this.maxplayers
		).hashCode();
	}
}
