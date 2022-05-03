package de.dragonrexx.mcserversecurityplugin.commands;

import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.mongodb.objects.PlayerAccount;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoginCommand implements CommandExecutor {

    // /login <Username> <Password>

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))return false;
        Player player = (Player) sender;

        PlayerAccount playerAccount = McServerSecurityPlugin.getInstance().getPlayerAccountManager().readPlayerAccount(player.getUniqueId());
        if(!playerAccount.isLogged()) {
            if(args.length == 2) {
                String Username = args[0];
                String Password = args[1];

                if(Username.equalsIgnoreCase(playerAccount.getUsername())) {
                    if(Password.equalsIgnoreCase(playerAccount.getPassword())) {
                        McServerSecurityPlugin.getInstance().getPlayerAccountManager().updateOnlineMode(player.getUniqueId().toString(), true);
                        player.sendMessage("Your are Successful Logged in");
                    } else {
                        player.sendMessage("You have the wrong Password");
                    }
                } else {
                    player.sendMessage("You have the wrong Username");
                }
            } else {
                player.sendMessage("You have the wrong");
            }
        } else {
            player.sendMessage("You are already logged in");
        }
        return false;
    }
}
