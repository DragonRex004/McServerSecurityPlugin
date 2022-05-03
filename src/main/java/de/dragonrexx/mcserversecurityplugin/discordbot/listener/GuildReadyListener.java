package de.dragonrexx.mcserversecurityplugin.discordbot.listener;

import de.dragonrexx.mcserversecurityplugin.discordbot.Bot;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildReadyListener extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Bot.registrySlashCommands(event.getGuild());
    }
}
