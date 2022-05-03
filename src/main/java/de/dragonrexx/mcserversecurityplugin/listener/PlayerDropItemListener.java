package de.dragonrexx.mcserversecurityplugin.listener;

import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.mongodb.objects.PlayerAccount;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        PlayerAccount playerAccount = McServerSecurityPlugin.getInstance().getPlayerAccountManager().readPlayerAccount(event.getPlayer().getUniqueId());

        if(!playerAccount.isLogged()) {
            event.setCancelled(true);
        }
    }
}
