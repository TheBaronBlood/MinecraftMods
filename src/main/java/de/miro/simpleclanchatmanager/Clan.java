package de.miro.simpleclanchatmanager;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;


public class Clan {
    String name;
    Scoreboard scoreboard;
    PlayerTeam team;
    int color;
    Player leader;

    public String getName() {
        return name;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public int getColor() {
        return color;
    }

    public PlayerTeam getTeam() {
        return team;
    }

    public Clan(String name, Scoreboard scoreboard) {
        this.name = name;
        this.scoreboard = scoreboard;
        this.team = scoreboard.addPlayerTeam(name);
        this.team.setPlayerPrefix(Component.literal("[" + name + "] "));


    }

    public void setPrefixColor(String colorRGB) {
        this.color = Integer.parseInt(colorRGB, 16);
        this.team.setPlayerPrefix(Component.literal("[" + name + "] ").withStyle(Style.EMPTY.withColor(this.color)));
    }


}
