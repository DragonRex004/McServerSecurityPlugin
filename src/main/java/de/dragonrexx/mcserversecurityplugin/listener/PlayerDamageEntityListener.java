package de.dragonrexx.mcserversecurityplugin.listener;

import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.mongodb.objects.PlayerAccount;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageEntityListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player))return;
        Player player = (Player) event.getDamager();
        PlayerAccount playerAccount = McServerSecurityPlugin.getInstance().getPlayerAccountManager().readPlayerAccount(player.getUniqueId());

        if(!playerAccount.isLogged()) {
            event.setCancelled(true);
        }
    }
}
