package de.dragonrexx.mcserversecurityplugin.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.dragonrexx.mcserversecurityplugin.McServerSecurityPlugin;
import de.dragonrexx.mcserversecurityplugin.mongodb.objects.PlayerAccount;
import org.bson.Document;

import java.util.UUID;

public class PlayerAccountManager {

    private MongoDB mongodb;
    private MongoCollection<Document> mongoCollection;

    public PlayerAccountManager() {

        this.mongodb = McServerSecurityPlugin.getInstance().getMongodb();
        this.mongoCollection = mongodb.getMongoCollection(McServerSecurityPlugin.getMongodbConfig().getString("MONGODB_COLLECTION"));
    }

    public PlayerAccount createPlayerAccount(String username, String password, String uuid, boolean isLogged) {

        Document document = this.mongoCollection.find(Filters.eq("uuid", uuid)).first();
        PlayerAccount playerAccount;

        if(document == null) {

            playerAccount = new PlayerAccount(uuid, username, password, isLogged);

            document = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(playerAccount), Document.class);
            this.mongoCollection.insertOne(document);

            return playerAccount;
        }

        playerAccount = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(document), PlayerAccount.class);
        return playerAccount;
    }

    public PlayerAccount updateOnlineMode(String uuid, boolean isLogged) {

        Document document = this.mongoCollection.find(Filters.eq("uuid", uuid)).first();
        PlayerAccount playerAccount;

        if(document == null) {
            return null;
        }

        playerAccount = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(document), PlayerAccount.class);
        playerAccount.setLogged(isLogged);

        document = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(playerAccount), Document.class);
        this.mongoCollection.replaceOne(Filters.eq("uuid", uuid), document);

        return playerAccount;
    }

    public PlayerAccount updateUsername(String uuid, String username) {

        Document document = this.mongoCollection.find(Filters.eq("uuid", uuid)).first();
        PlayerAccount playerAccount;

        if(document == null) {
            return null;
        }

        playerAccount = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(document), PlayerAccount.class);
        playerAccount.setUsername(username);

        document = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(playerAccount), Document.class);
        this.mongoCollection.replaceOne(Filters.eq("uuid", uuid), document);

        return playerAccount;
    }

    public PlayerAccount updatePassword(String uuid, String password) {

        Document document = this.mongoCollection.find(Filters.eq("uuid", uuid)).first();
        PlayerAccount playerAccount;

        if(document == null) {
            return null;
        }

        playerAccount = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(document), PlayerAccount.class);
        playerAccount.setPassword(password);

        document = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(playerAccount), Document.class);
        this.mongoCollection.replaceOne(Filters.eq("uuid", uuid), document);

        return playerAccount;
    }

    public PlayerAccount readPlayerAccount(UUID uuid) {

        Document document = this.mongoCollection.find(Filters.eq("uuid", uuid.toString())).first();

        if(document == null)
            return null;

        PlayerAccount playerAccount = McServerSecurityPlugin.getInstance().getGson().fromJson(McServerSecurityPlugin.getInstance().getGson().toJson(document), PlayerAccount.class);
        return playerAccount;
    }

    public boolean PlayerAccountExists(String uuid) {
        Document document = this.mongoCollection.find(Filters.eq("uuid", uuid)).first();

        if(document == null) {
            return false;
        }
        return true;
    }

    public void deletePlayerProfile(UUID uuid){
        Document document = this.mongoCollection.find(Filters.eq("uuid", uuid.toString())).first();

        if(document == null){
            return;
        }

        mongoCollection.deleteOne(document);
        System.out.println("[PlayerAccountManager] Das Document von: " + uuid.toString() + " wurde geloescht");
    }
}
