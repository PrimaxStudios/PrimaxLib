package net.primaxstudios.primaxcore.databases.connectors;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.primaxstudios.primaxcore.databases.ConnectorType;
import net.primaxstudios.primaxcore.databases.Credentials;
import net.primaxstudios.primaxcore.databases.DatabaseConnector;
import net.primaxstudios.primaxcore.databases.DatabaseType;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
public class MongodbConnector implements DatabaseConnector {

    private static final CodecRegistry POJO_CODEC_REGISTRY = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    private final Credentials credentials;
    private final boolean atlas;
    private MongoClient client;
    private MongoDatabase database;

    public MongodbConnector(Credentials credentials, boolean atlas) {
        this.credentials = credentials;
        this.atlas = atlas;
    }

    @Override
    public ConnectorType getConnectorType() {
        return ConnectorType.MONGODB;
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MONGODB;
    }

    @Override
    public void connect() {
        try {
            this.client = MongoClients.create(getUrl());
            this.database = this.client.getDatabase(credentials.getDatabaseName()).withCodecRegistry(POJO_CODEC_REGISTRY);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to MongoDB: " + getUrl(), e);
        }
    }

    public <T> MongoCollection<T> getCollection(String collectionName, Class<T> collectionClass) {
        return database.getCollection(collectionName, collectionClass);
    }

    private String getAtlasUrl() {
        String user = URLEncoder.encode(credentials.getUsername(), StandardCharsets.UTF_8);
        String pass = URLEncoder.encode(credentials.getPassword(), StandardCharsets.UTF_8);
        String params = credentials.getParameters() == null ? "" : credentials.getParameters();
        return "mongodb+srv://" + user + ":" + pass + "@" + credentials.getHost() + "/" + credentials.getDatabaseName() + params;
    }

    private String getClassicUrl() {
        String params = credentials.getParameters() != null ? credentials.getParameters() : "";
        if (credentials.getUsername() != null && credentials.getPassword() != null) {
            String user = URLEncoder.encode(credentials.getUsername(), StandardCharsets.UTF_8);
            String pass = URLEncoder.encode(credentials.getPassword(), StandardCharsets.UTF_8);
            return "mongodb://" + user + ":" + pass + "@" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName() + params;
        } else {
            return "mongodb://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName() + params;
        }
    }

    private String getUrl() {
        return atlas ? getAtlasUrl() : getClassicUrl();
    }

    @Override
    public void close() {
        if (client != null) {
            client.close();
        }
    }
}
