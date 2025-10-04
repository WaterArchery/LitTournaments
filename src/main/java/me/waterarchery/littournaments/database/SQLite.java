package me.waterarchery.littournaments.database;

import me.waterarchery.littournaments.LitTournaments;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLite extends Database {

    public SQLite(LitTournaments instance) {
        super(instance);
    }

    public Connection getSQLConnection() {
        File dataFolder = new File(instance.getDataFolder(), "database.db");

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
        } catch (SQLException ex) {
            instance.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            instance.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    @Override
    public void initialize() {
        File dataFolder = new File(instance.getDataFolder(), "database.db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                instance.getLogger().log(Level.SEVERE, "File write error: database.db");
            }
        }
    }

}
