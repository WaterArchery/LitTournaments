package me.waterarchery.littournaments.database;

import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentLeaderboard;
import me.waterarchery.littournaments.models.TournamentValue;

import java.sql.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class Database {

    protected final LitTournaments instance;
    private final ExecutorService threadPool;

    public String createTableToken = "CREATE TABLE IF NOT EXISTS {{TOURNAMENT_NAME}} (" +
            "`player` varchar(36) NOT NULL PRIMARY KEY," +
            "`score` int(11) NOT NULL" +
            ");";

    public Database(LitTournaments instance) {
        this.instance = instance;
        ThreadFactory factory = Thread.ofVirtual()
                .name("littournaments-database-worker-", 0)
                .uncaughtExceptionHandler((thread, throwable) -> throwable.printStackTrace())
                .factory();
        threadPool = Executors.newFixedThreadPool(10, factory);
    }

    public abstract Connection getSQLConnection();

    public abstract void initialize();

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
            String query = String.format("INSERT INTO %s (player, score) VALUES(?, ?) ON CONFLICT(player)" +
                                                 " DO UPDATE SET score=score + ? WHERE player = ?;", tournament.getIdentifier());

            try (Connection connection = getSQLConnection()) {
                PreparedStatement stmt = connection.prepareStatement(query);

                stmt.setString(1, uuid.toString());
                stmt.setLong(2, point);
                stmt.setLong(3, point);
                stmt.setString(4, uuid.toString());

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
        String query = String.format("SELECT score FROM %s WHERE player = ?;", tournament.getIdentifier());

        try (Connection connection = getSQLConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.isBeforeFirst())
                return rs.getLong("score");
            else
                return -9999;
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

    public void shutdownPool() {
        try {
            LitLibs libs = LitTournaments.getLitLibs();
            libs.getLogger().log("Shutting down thread pool");
            threadPool.shutdownNow();
            boolean closed = threadPool.awaitTermination(20, TimeUnit.SECONDS);
            if (!closed)
                libs.getLogger().error("Shutting down thread pool failed");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}