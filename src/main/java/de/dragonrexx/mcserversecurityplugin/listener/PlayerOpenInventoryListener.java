package de.dragonrexx.mcserversecurityplugin.listener;

import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.mongodb.objects.PlayerAccount;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class PlayerOpenInventoryListener implements Listener {

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        PlayerAccount playerAccount = McServerSecurityPlugin.getInstance().getPlayerAccountManager().readPlayerAccount(player.getUniqueId());

        if(!playerAccount.isLogged()) {
            event.setCancelled(true);
        }
    }
}
