package com.coolspy3.shortcommands;

import java.io.IOException;

import com.mojang.brigadier.LiteralMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("shortcommands")
public class ShortCommands {
    
    public ShortCommands() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        try {
            Config.load();
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }
        MinecraftForge.EVENT_BUS.register(new HelpCommand());
        MinecraftForge.EVENT_BUS.register(new ShortCommandCommand());
    }

    @SubscribeEvent
    public void onSendChat(ClientChatEvent event) {
        if(Config.getInstance().shortcuts.containsKey(event.getMessage())) {
            event.setCanceled(true);
            Minecraft.getInstance().gui.getChat().addRecentChat(event.getMessage());
            String shortcut = Config.getInstance().shortcuts.get(event.getMessage());
            if(!MinecraftForge.EVENT_BUS.post(new ClientChatEvent(shortcut))) {
                Minecraft.getInstance().player.chat(shortcut);
            }
        }
    }

    public static void sendMessage(String msg) {
        Minecraft.getInstance().player.sendMessage(TextComponentUtils.fromMessage(new LiteralMessage(msg)), Util.NIL_UUID);
    }

}
