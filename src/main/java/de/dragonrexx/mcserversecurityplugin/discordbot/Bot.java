package de.dragonrexx.mcserversecurityplugin.discordbot;

import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.discordbot.commands.SlashCommandListener;
import de.dragonrexx.mcserversecurityplugin.discordbot.listener.GuildReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.security.auth.login.LoginException;

public class Bot {

    private static JDA discordBot;
    private Guild discordServer;

    public void DiscordBotMain() throws LoginException {
        String token = McServerSecurityPlugin.getDiscordBotConfig().getString("DISCORD_TOKEN");
        discordBot = JDABuilder.createDefault(token).build();

        discordBot.getPresence().setStatus(OnlineStatus.ONLINE);
        discordBot.getPresence().setActivity(Activity.playing("www.McDiscordBot.net"));

        discordBot.addEventListener(new GuildReadyListener());
        discordBot.addEventListener(new SlashCommandListener());
    }

    public void shutdownBot() {
        discordBot.getPresence().setStatus(OnlineStatus.OFFLINE);
        discordBot.getPresence().setActivity(Activity.listening("The Bot is not Online"));
        discordBot.shutdown();
    }

    public static void registrySlashCommands(Guild guild) {
        guild.upsertCommand("create-mc-server-account", "This Command create a Minecraft server Account")
                .addOption(OptionType.STRING, "username", "Enter a Username for your Account", true)
                .addOption(OptionType.STRING, "password", "Test1", true)
                .addOption(OptionType.STRING, "uuid", "Test2", true)
                .queue();
    }

    public static JDA getDiscordBot() {
        return discordBot;
    }

    public Guild getDiscordServer() {
        return discordServer;
    }
}
