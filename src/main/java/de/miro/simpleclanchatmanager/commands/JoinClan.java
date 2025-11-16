package de.miro.simpleclanchatmanager.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public class JoinClan {
    public JoinClan(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("clan")
                .then(Commands.literal("join")
                .then(Commands.argument("clanName", StringArgumentType.string())
                        .suggests((context, builder) -> {
                            Scoreboard scoreboard = context.getSource().getScoreboard();
                            for (PlayerTeam team : scoreboard.getPlayerTeams()) {
                                builder.suggest(team.getName());
                            }
                            return builder.buildFuture();
                        })
                        .executes(this::join))));

    }

    private int join(CommandContext<CommandSourceStack> context) {
        String clanName = StringArgumentType.getString(context, "clanName");
        Player player = context.getSource().getPlayer();
        PlayerTeam clan = context.getSource().getScoreboard().getPlayerTeam(clanName);
        Scoreboard scoreboard = context.getSource().getScoreboard();

        if (clan != null) {
            scoreboard.removePlayerFromTeam(player.getScoreboardName(), clan);
        }
        scoreboard.addPlayerToTeam(player.getScoreboardName(), clan);
        scoreboard.getPlayerTeams();
        return 0;
    }
}
