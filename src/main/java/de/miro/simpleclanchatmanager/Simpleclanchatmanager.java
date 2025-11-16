package de.miro.simpleclanchatmanager;

import com.mojang.logging.LogUtils;
import de.miro.simpleclanchatmanager.commands.*;

import net.minecraft.client.Minecraft;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Simpleclanchatmanager.MODID)
public class Simpleclanchatmanager {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "simpleclanchatmanager";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Simpleclanchatmanager() {
        //IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        //modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {


    }



    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onChat(ServerChatEvent event) {
        event.setCanceled(true);
        MutableComponent fullMessage = Component.empty();

        if (event.getPlayer().getTeam() instanceof PlayerTeam pTeam) {
            //PlayerTeam pTeam = event.getPlayer().getScoreboard().getPlayersTeam(event.getPlayer().getName().toString());
            fullMessage.append(pTeam.getPlayerPrefix());
        }
        MutableComponent name = Component.literal(event.getUsername())
                .withStyle(Style.EMPTY);


        fullMessage.append(name).append(": ").append(Component.literal(event.getRawText()));



        event.getPlayer().server.execute(() -> {
            event.getPlayer().server.getPlayerList().broadcastSystemMessage(fullMessage, false);
        });
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        new CreateClan(event.getDispatcher());
        new JoinClan(event.getDispatcher());
        new LeaveClan(event.getDispatcher());
    }





    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
