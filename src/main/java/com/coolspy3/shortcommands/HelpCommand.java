package com.coolspy3.shortcommands;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HelpCommand {
    
    public static final String justABunchOfDashes = "-----------------------------";
    
    @SubscribeEvent
    public void register(ClientChatEvent event) {
        if(event.getMessage().matches("/schelp( .*)?")) {
            event.setCanceled(true);
            ShortCommands.sendMessage(TextFormatting.BLUE + justABunchOfDashes);
            ShortCommands.sendMessage(TextFormatting.YELLOW + "/sc set <trigger> <command>" + TextFormatting.AQUA + " - Sets the message \"<trigger>\" to instead type \"<command>\"");
            ShortCommands.sendMessage(TextFormatting.YELLOW + "/sc remove <trigger>" + TextFormatting.AQUA + " - Removes the specified trigger from the command list");
            ShortCommands.sendMessage(TextFormatting.YELLOW + "/sc list"  + TextFormatting.AQUA + " - Lists the current triggers and their respective commands");
            ShortCommands.sendMessage(TextFormatting.YELLOW + "/sc help" + TextFormatting.AQUA + " - Prints this help message");
            ShortCommands.sendMessage(TextFormatting.YELLOW + "/schelp" + TextFormatting.AQUA + " - Prints this help message");
            ShortCommands.sendMessage(TextFormatting.BLUE + justABunchOfDashes);
        }
    }
    
}
