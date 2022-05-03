package de.dragonrexx.mcserversecurityplugin.mongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class MongoDB {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCredential mongoCredential;

    public MongoDB(String host, int port, String username, String password, String database) {
        this.mongoCredential = MongoCredential.createScramSha256Credential(username, database, password.toCharArray());
        this.mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                        .credential(this.mongoCredential)
                        .build());
        this.mongoDatabase = mongoClient.getDatabase(database);
    }

    public void disconnect() {
        this.mongoClient.close();
    }

    public MongoCollection<Document> getMongoCollection(String collection) {
        return this.mongoDatabase.getCollection(collection);
    }
}
