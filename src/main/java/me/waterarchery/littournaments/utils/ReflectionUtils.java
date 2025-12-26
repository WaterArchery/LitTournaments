package me.waterarchery.littournaments.utils;

import me.waterarchery.littournaments.models.Tournament;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReflectionUtils {

    public static List<Class<Tournament>> getTournamentClasses() {
        List<Class<Tournament>> tournaments = new ArrayList<>();

        Reflections reflections = new Reflections();
        Set<Class<? extends Tournament>> classes = reflections.getSubTypesOf(Tournament.class);
        for (Class<? extends Tournament> tournamentClass : classes) {
            tournaments.add((Class<Tournament>) tournamentClass);
        }

        return tournaments;
    }
}
