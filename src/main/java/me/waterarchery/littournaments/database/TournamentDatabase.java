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
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class TournamentDatabase extends Database {

    private static TournamentDatabase instance;

    public static TournamentDatabase getInstance() {
        if (instance == null) {
            ConfigFile configFile = ConfigUtils.get(ConfigFile.class);
            instance = new TournamentDatabase(LitTournaments.getInstance(), configFile.getDatabase());
        }
        return instance;
    }

    private TournamentDatabase(JavaPlugin plugin, DatabaseConfiguration databaseConfiguration) {
        super(plugin, databaseConfiguration);
    }

    public void load(List<Tournament> tournaments) {
        tournaments.forEach(this::reloadLeaderboard);
    }

    public void addPoint(UUID uuid, Tournament tournament, long point) {
        executor.submit(() -> {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                TournamentValue existing = findByPlayerAndTournament(session, uuid, tournament.getIdentifier());
                existing.setValue(existing.getValue() + point);
                session.merge(existing);
                tx.commit();
            }
        });
    }

    public void setPoint(UUID uuid, Tournament tournament, long point) {
        executor.submit(() -> {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                TournamentValue existing = findByPlayerAndTournament(session, uuid, tournament.getIdentifier());
                existing.setValue(point);
                session.merge(existing);
                tx.commit();
            }
        });
    }

    public long getPoint(UUID uuid, Tournament tournament) {
        try (Session session = sessionFactory.openSession()) {
            TournamentValue existing = findByPlayerAndTournament(session, uuid, tournament.getIdentifier());
            return existing.getValue();
        }
    }

    public void registerToTournament(UUID uuid, Tournament tournament) {
        executor.submit(() -> {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                tx.begin();

                String tournamentId = tournament.getIdentifier();
                TournamentValue existing = findByPlayerAndTournament(session, uuid, tournamentId);

                if (existing == null) {
                    TournamentValue newValue = new TournamentValue(tournamentId, uuid, 0L);
                    session.persist(newValue);
                }

                tx.commit();
            } catch (Exception ex) {
                LitTournaments.getInstance().getLogger().log(Level.SEVERE, "Error registering to tournament", ex);
            }
        });
    }

    public void deleteFromTournament(UUID uuid, Tournament tournament) {
        executor.submit(() -> {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                tx.begin();

                session.createQuery("DELETE FROM TournamentValue t WHERE t.uuid = :uuid AND t.tournamentId = :tournamentId")
                        .setParameter("uuid", uuid)
                        .setParameter("tournamentId", tournament.getIdentifier())
                        .executeUpdate();

                tx.commit();
            } catch (Exception ex) {
                LitTournaments.getInstance().getLogger().log(Level.SEVERE, "Error deleting from tournament", ex);
            }
        });
    }

    public void reloadLeaderboard(Tournament tournament) {
        executor.submit(getReloadTournamentRunnable(tournament));
    }

    public Runnable getReloadTournamentRunnable(Tournament tournament) {
        return () -> {
            try (Session session = sessionFactory.openSession()) {
                TournamentLeaderboard leaderboard = tournament.getLeaderboard();

                List<TournamentValue> results = session.createQuery(
                                "SELECT t FROM TournamentValue t WHERE t.tournamentId = :tournamentId ORDER BY t.value DESC",
                                TournamentValue.class)
                        .setParameter("tournamentId", tournament.getIdentifier())
                        .getResultList();

                int pos = 1;
                for (TournamentValue value : results) {
                    leaderboard.setPosition(value, pos);
                    pos++;
                }
            } catch (Exception ex) {
                LitTournaments.getInstance().getLogger().log(Level.SEVERE, "Error reloading leaderboard", ex);
            }
        };
    }

    public void clearTournament(Tournament tournament) {
        executor.submit(() -> {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                tx.begin();

                session.createQuery("DELETE FROM TournamentValue t WHERE t.tournamentId = :tournamentId")
                        .setParameter("tournamentId", tournament.getIdentifier())
                        .executeUpdate();

                tx.commit();
            } catch (Exception ex) {
                LitTournaments.getInstance().getLogger().log(Level.SEVERE, "Error clearing tournament", ex);
            }
        });
    }

    private TournamentValue findByPlayerAndTournament(Session session, UUID uuid, String tournamentId) {
        List<TournamentValue> results = session.createQuery(
                        "SELECT t FROM TournamentValue t WHERE t.uuid = :uuid AND t.tournamentId = :tournamentId",
                        TournamentValue.class)
                .setParameter("uuid", uuid)
                .setParameter("tournamentId", tournamentId)
                .setMaxResults(1)
                .getResultList();

        if (results.isEmpty()) throw new RuntimeException("No tournament with id " + tournamentId + " found");
        return results.getFirst();
    }
}