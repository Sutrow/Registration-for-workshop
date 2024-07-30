package io.project.RegistrationForWorkshops.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UserContainer {
    private static final List<Long> USERS_REGISTERED = new ArrayList<>();
    private static final Map<Long, State> USERS_STATE = new HashMap<>();
    private static final Map<Long, UserDAO> USERS_QUEUE = new HashMap<>();

    private UserContainer() { }

    public static boolean checkRegistered(Long id) {
        return USERS_REGISTERED.contains(id);
    }

    public static boolean containsUser(Long id) {
        return USERS_STATE.containsKey(id);
    }

    public static void addUser(Long id) {
        USERS_STATE.put(id, State.INITIAL);
    }

    public static void markRegistered(Long id) {
        if (!USERS_REGISTERED.contains(id)) {
            USERS_REGISTERED.add(id);
        }
    }

    public static void addToQueue(Long id, UserDAO user) {
        USERS_QUEUE.put(id, user);
    }

    public static UserDAO getUserDAO(Long id) {
        return USERS_QUEUE.get(id);
    }

    public static void setState(Long id, State state) {
        USERS_STATE.put(id, state);
    }

    public static State getState(Long id) {
        return USERS_STATE.get(id);
    }
}
