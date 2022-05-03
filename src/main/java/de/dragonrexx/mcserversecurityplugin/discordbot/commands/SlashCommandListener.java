package de.dragonrexx.mcserversecurityplugin.discordbot.commands;

import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.mongodb.objects.PlayerAccount;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command_name = event.getName();

        if(command_name.equals("create-mc-server-account")){
            String username = event.getOption("username").getAsString();
            String password = event.getOption("password").getAsString();
            String client_uuid = event.getOption("uuid").getAsString();
            if(!McServerSecurityPlugin.getInstance().getPlayerAccountManager().PlayerAccountExists(client_uuid)) {
                PlayerAccount playerAccount = McServerSecurityPlugin.getInstance().getPlayerAccountManager().createPlayerAccount(
                        username,
                        password,
                        client_uuid,
                        false
                );
                event.reply("The Account is successful created").queue();
            } else {
                event.reply("The Account with this uuid always Exists!!!").queue();
            }
        }
    }
}
