package de.miro.simpleclanchatmanager.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import de.miro.simpleclanchatmanager.Clan;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public class CreateClan {
    public CreateClan(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("clan")
                .then(Commands.literal("create")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(this::create)
                        .then(Commands.argument("color", StringArgumentType.string())
                                .executes(this::setClanColor)))));

    }

    private int setClanColor(CommandContext<CommandSourceStack> context) {
        String color = StringArgumentType.getString(context, "color");
        String name = StringArgumentType.getString(context, "name");
        Scoreboard scoreboard = context.getSource().getScoreboard();
        ServerPlayer player = context.getSource().getPlayer();

        Clan clan = new Clan(name, scoreboard);
        if (player.getScoreboard().getPlayersTeam(player.getScoreboardName()) != null) {
            scoreboard.removePlayerFromTeam(player.getScoreboardName(), player.getScoreboard().getPlayersTeam(player.getScoreboardName()));
        }
        clan.setPrefixColor(color);
        scoreboard.addPlayerToTeam(player.getScoreboardName(), clan.getTeam());
        return 0;
    }

    private int create(CommandContext<CommandSourceStack> context) {
        String name = StringArgumentType.getString(context, "name");
        Scoreboard scoreboard = context.getSource().getScoreboard();
        ServerPlayer player = context.getSource().getPlayer();

        Clan clan = new Clan(name, context.getSource().getScoreboard());
        if (player.getScoreboard().getPlayersTeam(player.getScoreboardName()) != null) {
            scoreboard.removePlayerFromTeam(player.getScoreboardName(), player.getScoreboard().getPlayersTeam(player.getScoreboardName()));
        }
        scoreboard.addPlayerToTeam(player.getScoreboardName(), clan.getTeam());
        return 0;
    }


}
