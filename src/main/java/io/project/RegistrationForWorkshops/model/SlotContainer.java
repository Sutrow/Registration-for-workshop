package io.project.RegistrationForWorkshops.model;

import io.project.RegistrationForWorkshops.sheets.SlotRecipient;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SlotContainer {
    private static final Map<Integer, SlotDAO> SLOTS = new HashMap<>();

    private SlotContainer() { }

    public static void updateSlots(SlotRecipient sheetApi) throws IOException {
        SLOTS.clear();
        for (SlotDAO lesson : sheetApi.readAllSheets()) {
            SLOTS.put(lesson.getId(), lesson);
        }
    }

    public static SlotDAO find(int id) {
        return SLOTS.get(id);
    }

    private static List<SlotDAO> getSlots() {
        return new ArrayList<>(SLOTS.values());
    }

    public static List<SlotDAO> getAvailableSlots() {
        return SlotContainer.getSlots().stream()
                .filter(slot -> ChronoUnit.DAYS.between(slot.getTime(), LocalDate.now()) <= 1)
                .filter(slot -> slot.getInitialRecords() + slot.getReserveRecords()
                        < slot.getInitialCapacity() + slot.getReserveCapacity())
                .collect(Collectors.toList());
    }

    public static List<SlotDAO> getAvailableFilteredBySubject(String subject) {
        return getAvailableSlots()
                .stream()
                .filter(slot -> slot.getSubjectsList().stream().anyMatch(elem -> elem.getCallback().equals(subject)))
                .toList();
    }
}
