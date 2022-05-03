package de.dragonrexx.mcserversecurityplugin;

import com.google.gson.Gson;
import de.dragonrexx.mcserversecurityplugin.commands.LoginCommand;
import de.dragonrexx.mcserversecurityplugin.discordbot.Bot;
import de.dragonrexx.mcserversecurityplugin.listener.*;
import de.dragonrexx.mcserversecurityplugin.mongodb.MongoDB;
import de.dragonrexx.mcserversecurityplugin.mongodb.PlayerAccountManager;
import de.dragonrexx.mcserversecurityplugin.utils.FileBuilder;
import de.dragonrexx.mcserversecurityplugin.utils.PluginSetup;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class McServerSecurityPlugin extends JavaPlugin implements PluginSetup {
    private static McServerSecurityPlugin instance;
    private static FileBuilder discordBotConfig = new FileBuilder("plugins//McServerSecurityPlugin", "bot-config.yml");
    private static FileBuilder mongodbConfig = new FileBuilder("plugins//McServerSecurityPlugin", "mongodb.yml");
    private static Bot discordBot = new Bot();
    private MongoDB mongodb;
    private Gson gson;
    private PlayerAccountManager playerAccountManager;

    @Override
    public void onEnable() {
        instance = this;
        initDiscordConfig();
        initMongoDBConfig();
        commandRegistry();
        eventRegistry(getServer().getPluginManager());
        startToConnectDiscordBot();
        startToConnectMongoDB();
    }

    @Override
    public void onDisable() {
        closeConnectionDiscordBot();
        closeConnectionMongoDB();
    }

    @Override
    public void commandRegistry() {

        getCommand("login").setExecutor(new LoginCommand());
    }

    @Override
    public void eventRegistry(PluginManager pm) {
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerOpenInventoryListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerDamageEntityListener(), this);
    }

    private void closeConnectionDiscordBot() {
        if(!discordBotConfig.getBoolean("DISCORD_BOT_CAN_START")){
            Bukkit.getConsoleSender().sendMessage("§3[Discord Bot] §4The Bot can Not shutdown");
            Bukkit.getConsoleSender().sendMessage("§3[Discord Bot] §4Because the the Bool §3DISCORD_BOT_CAN_START §4is set of False");
            Bukkit.getConsoleSender().sendMessage("§3[Discord Bot] §4Please set it of true");
        } else {
            discordBot.shutdownBot();
        }
    }

    private void closeConnectionMongoDB() {
        if(!mongodbConfig.getBoolean("MONGODB_CAN_START")){
            Bukkit.getConsoleSender().sendMessage("§3[MongoDB] §4The MongoDB Database can Not connected");
            Bukkit.getConsoleSender().sendMessage("§3[MongoDB] §4Because the the Bool §3MONGODB_CAN_START §4is set of False");
            Bukkit.getConsoleSender().sendMessage("§3[MongoDB] §4Please set it of true");
        } else {
            mongodb.disconnect();
        }
    }

    @Override
    public void initDiscordConfig() {
        if(!discordBotConfig.exist()){
            discordBotConfig.setValue("DISCORD_TOKEN", "Your Token Here");
            discordBotConfig.setValue("DISCORD_GUILD_ID", "Your Guild id Here");
            discordBotConfig.setValue("MC_MESSAGES_CHANNEL_ID", "Test_Channel_Id");
            discordBotConfig.setValue("DISCORD_BOT_CAN_START", false);
            discordBotConfig.save();
        }
    }

    public void initMongoDBConfig() {
        if(!mongodbConfig.exist()) {
            mongodbConfig.setValue("MONGODB_USERNAME", "Your MongoDB Username here");
            mongodbConfig.setValue("MONGODB_PASSWORD", "Your MongoDB User Password here");
            mongodbConfig.setValue("MONGODB_HOST", "Your MongoDB Host/ip here");
            mongodbConfig.setValue("MONGODB_PORT", "Your MongoDB Port here");
            mongodbConfig.setValue("MONGODB_DATABASE", "Your MongoDB Database Name here");
            mongodbConfig.setValue("MONGODB_COLLECTION", "Your MongoDB Collection here");
            mongodbConfig.setValue("MONGODB_CAN_START", false);
            mongodbConfig.save();
        }
    }

    private void startToConnectDiscordBot() {
        if(!discordBotConfig.getBoolean("DISCORD_BOT_CAN_START")){
            Bukkit.getConsoleSender().sendMessage("§3[Discord Bot] §4The Bot can Not Start");
            Bukkit.getConsoleSender().sendMessage("§3[Discord Bot] §4Because the the Bool §3DISCORD_BOT_CAN_START §4is set of False");
            Bukkit.getConsoleSender().sendMessage("§3[Discord Bot] §4Please set it of true");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            try {
                discordBot.DiscordBotMain();
            }catch (LoginException e){
                e.printStackTrace();
            }
        }
    }

    private void startToConnectMongoDB() {
        if(!mongodbConfig.getBoolean("MONGODB_CAN_START")){
            Bukkit.getConsoleSender().sendMessage("§3[MongoDB] §4The MongoDB Database can Not connected");
            Bukkit.getConsoleSender().sendMessage("§3[MongoDB] §4Because the the Bool §3MONGODB_CAN_START §4is set of False");
            Bukkit.getConsoleSender().sendMessage("§3[MongoDB] §4Please set it of true");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            this.mongodb = new MongoDB(
                    mongodbConfig.getString("MONGODB_HOST"),
                    mongodbConfig.getInt("MONGODB_PORT"),
                    mongodbConfig.getString("MONGODB_USERNAME"),
                    mongodbConfig.getString("MONGODB_PASSWORD"),
                    mongodbConfig.getString("MONGODB_DATABASE")
            );
            this.gson = new Gson();
            this.playerAccountManager = new PlayerAccountManager();
        }
    }

    public static FileBuilder getDiscordBotConfig() {
        return discordBotConfig;
    }

    public static Bot getDiscordBot() {
        return discordBot;
    }

    public static McServerSecurityPlugin getInstance() {
        return instance;
    }

    public Gson getGson() {
        return gson;
    }

    public MongoDB getMongodb() {
        return mongodb;
    }

    public static FileBuilder getMongodbConfig() {
        return mongodbConfig;
    }

    public PlayerAccountManager getPlayerAccountManager() {
        return playerAccountManager;
    }
}
