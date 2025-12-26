package me.waterarchery.littournaments.database;

import com.chickennw.utils.database.sql.Database;
import com.chickennw.utils.models.config.database.DatabaseConfiguration;
import com.chickennw.utils.utils.ConfigUtils;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.configurations.ConfigFile;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentLeaderboard;
import me.waterarchery.littournaments.models.TournamentValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TournamentDatabase extends Database {

    private static TournamentDatabase instance;

    public static TournamentDatabase getInstance() {
        if (instance == null) {
            ConfigFile configFile = ConfigUtils.get(ConfigFile.class);
            instance = new TournamentDatabase(LitTournaments.getInstance(), configFile.getDatabase());
        }

        return instance;
    }

    private TournamentDatabase(JavaPlugin plugin, DatabaseConfiguration config) {
        super(plugin, config);
    }

    public void load(List<Tournament> tournaments) {
        try (Connection connection = getSQLConnection()) {
            tournaments.forEach(tournament -> {
                try {
                    String tableName = tournament.getIdentifier();
                    String query = createTableToken.replace("{{TOURNAMENT_NAME}}", tableName);
                    Statement s = connection.createStatement();
                    s.executeUpdate(query);
                    s.close();

                    reloadLeaderboard(tournament);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addPoint(UUID uuid, Tournament tournament, long point) {
        Runnable runnable = () -> {
            String query = String.format("INSERT INTO %s (player, score) VALUES(?, ?) " + "ON DUPLICATE KEY UPDATE score = score + ?;",
                    tournament.getIdentifier());

            try (Connection connection = getSQLConnection()) {
                PreparedStatement stmt = connection.prepareStatement(query);

                stmt.setString(1, uuid.toString());
                stmt.setLong(2, point);
                stmt.setLong(3, point);

                stmt.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };

        threadPool.submit(runnable);
    }

    public void setPoint(UUID uuid, Tournament tournament, long point) {
        Runnable runnable = () -> {
            String query = String.format("REPLACE INTO %s (player, score) VALUES(?, ?);", tournament.getIdentifier());

            try (Connection connection = getSQLConnection()) {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, uuid.toString());
                stmt.setLong(2, point);

                stmt.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };

        threadPool.submit(runnable);
    }

    public long getPoint(UUID uuid, Tournament tournament) {
        String query = String.format("SELECT score FROM %s WHERE player = ? LIMIT 1;", tournament.getIdentifier());

        try (Connection connection = getSQLConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getLong("score");
            else return -9999;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void registerToTournament(UUID uuid, Tournament tournament) {
        Runnable runnable = () -> {
            String QUERY = String.format("INSERT INTO %s (player, score) VALUES(?, ?);", tournament.getIdentifier());

            try (Connection connection = getSQLConnection()) {
                PreparedStatement stmt = connection.prepareStatement(QUERY);
                stmt.setString(1, uuid.toString());
                stmt.setLong(2, 0L);

                stmt.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };

        threadPool.submit(runnable);
    }

    public void deleteFromTournament(UUID uuid, Tournament tournament) {
        Runnable runnable = () -> {
            String query = String.format("DELETE FROM %s WHERE player = ?;", tournament.getIdentifier());

            try (Connection connection = getSQLConnection()) {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, uuid.toString());

                stmt.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };

        threadPool.submit(runnable);
    }

    public void reloadLeaderboard(Tournament tournament) {
        threadPool.submit(getReloadTournamentRunnable(tournament));
    }

    public Runnable getReloadTournamentRunnable(Tournament tournament) {
        return () -> {
            String query = String.format("SELECT * FROM %s ORDER BY score DESC;", tournament.getIdentifier());
            TournamentLeaderboard leaderboard = tournament.getLeaderboard();

            try (Connection connection = getSQLConnection()) {
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                int pos = 1;
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("player"));
                    long score = rs.getLong("score");

                    TournamentValue tournamentValue = new TournamentValue(uuid, score);
                    leaderboard.setPosition(tournamentValue, pos);
                    pos++;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public void clearTournament(Tournament tournament) {
        Runnable runnable = () -> {
            String query = String.format("DELETE FROM %s;", tournament.getIdentifier());

            try (Connection connection = getSQLConnection()) {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };

        threadPool.submit(runnable);
    }
}