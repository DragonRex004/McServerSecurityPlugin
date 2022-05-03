package de.dragonrexx.mcserversecurityplugin.utils;

import org.bukkit.plugin.PluginManager;

public interface PluginSetup {

    //in dieser Methode werden alle Commands Registriert
    void commandRegistry();

    //in dieser Methode werden alle Listener Registriert
    void eventRegistry(PluginManager pm);

    void initDiscordConfig();
}
