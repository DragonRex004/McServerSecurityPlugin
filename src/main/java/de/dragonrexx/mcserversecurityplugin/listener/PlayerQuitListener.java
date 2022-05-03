package de.dragonrexx.mcserversecurityplugin.listener;

import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.discordbot.Bot;
import de.dragonrexx.mcserversecurityplugin.mongodb.objects.PlayerAccount;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class PlayerQuitListener implements Listener {

    private final String guildId = McServerSecurityPlugin.getDiscordBotConfig().getString("DISCORD_GUILD_ID");
    private final String channelId = McServerSecurityPlugin.getDiscordBotConfig().getString("MC_MESSAGES_CHANNEL_ID");

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerAccount playerAccount = McServerSecurityPlugin.getInstance().getPlayerAccountManager().readPlayerAccount(player.getUniqueId());
        TextChannel textChannelById = Bot.getDiscordBot().getGuildById(guildId).getTextChannelById(channelId);
        if(textChannelById == null)return;

        EmbedBuilder embedBuilder= new EmbedBuilder();
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle(player.getName() + " is left the Server");
        embedBuilder.setAuthor("McServerSecurityPlugin");
        embedBuilder.setFooter("This is a Plugin");
        textChannelById.sendMessageEmbeds(embedBuilder.build()).queue();

        if(playerAccount.isLogged()) {
            McServerSecurityPlugin.getInstance().getPlayerAccountManager().updateOnlineMode(player.getUniqueId().toString(), false);
        }
    }
}
