package com.coolspy3.shortcommands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShortCommandCommand {
    
    public static final String addRegex = "/sc set ([a-zA-Z0-9_\\-\\/]+) (.+)";
    public static final Pattern addPattern = Pattern.compile(addRegex);
    public static final String removeRegex = "/sc remove ([a-zA-Z0-9_\\-\\/]+)";
    public static final Pattern removePattern = Pattern.compile(removeRegex);

    @SubscribeEvent
    public void register(ClientChatEvent event) {
        String msg = event.getMessage();
        if(msg.matches("/sc( .*)?")) {
            try {
                event.setCanceled(true);
                Minecraft.getInstance().gui.getChat().addRecentChat(event.getMessage());
                if(msg.matches("/sc set( .*)?")) {
                    Matcher addMatcher = addPattern.matcher(msg);
                    if(addMatcher.matches()) {
                        String trigger = addMatcher.group(1);
                        String command = addMatcher.group(2);
                        Config.getInstance().shortcuts.put(trigger, command);
                        Config.save();
                        ShortCommands.sendMessage(TextFormatting.AQUA + "Typing: \"" + trigger + "\" will now type: \"" + command + "\"");
                    } else {
                        ShortCommands.sendMessage(TextFormatting.RED + "Usage: /sc set <trigger> <command>");
                    }
                } else if(msg.matches("/sc remove( .*)?")) {
                    Matcher removeMatcher = removePattern.matcher(msg);
                    if(removeMatcher.matches()) {
                        String trigger = removeMatcher.group(1);
                        Config.getInstance().shortcuts.remove(trigger);
                        Config.save();
                        ShortCommands.sendMessage(TextFormatting.AQUA + "Removed short command for trigger: \"" + trigger + "\"");
                    } else {
                        ShortCommands.sendMessage(TextFormatting.RED + "Usage: /sc remove <trigger>");
                    }
                } else if(msg.matches("/sc list( .*)?")) {
                    ShortCommands.sendMessage(TextFormatting.AQUA + "Current Short Commands:");
                    if(Config.getInstance().shortcuts.isEmpty()) {
                        ShortCommands.sendMessage(TextFormatting.AQUA + "<None>");
                    } else {
                        for(String trigger: Config.getInstance().shortcuts.keySet()) {
                            String command = Config.getInstance().shortcuts.get(trigger);
                            ShortCommands.sendMessage(TextFormatting.AQUA + "Typing: \"" + trigger + "\" will type: \"" + command + "\"");
                        }
                    }
                } else if(msg.matches("/sc help( .*)?")) {
                    MinecraftForge.EVENT_BUS.post(new ClientChatEvent("/schelp"));
                } else {
                    ShortCommands.sendMessage(TextFormatting.RED + "Usage: /sc [set|remove|list|help]");
                }
            } catch(IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }
    
}
