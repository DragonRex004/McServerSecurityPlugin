package de.dragonrexx.mcserversecurityplugin.listener;

import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.discordbot.Bot;
import de.dragonrexx.mcserversecurityplugin.mongodb.objects.PlayerAccount;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

public class PlayerJoinListener implements Listener {

    private final String guildId = McServerSecurityPlugin.getDiscordBotConfig().getString("DISCORD_GUILD_ID");
    private final String channelId = McServerSecurityPlugin.getDiscordBotConfig().getString("MC_MESSAGES_CHANNEL_ID");

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerAccount playerAccount = McServerSecurityPlugin.getInstance().getPlayerAccountManager().readPlayerAccount(player.getUniqueId());
        TextChannel textChannelById = Bot.getDiscordBot().getGuildById(guildId).getTextChannelById(channelId);
        if(textChannelById == null)return;

        EmbedBuilder embedBuilder= new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle(player.getName() + " is joined the Server");
        embedBuilder.setAuthor("McServerSecurityPlugin");
        embedBuilder.setFooter("This is a Plugin");
        textChannelById.sendMessageEmbeds(embedBuilder.build()).queue();

        if(!playerAccount.isLogged()) {
            player.sendMessage("Please Login with the /login <Username> <Password>");
        } else {
            player.kickPlayer("Your Account is always Logged in on this Server");
        }
    }
}
