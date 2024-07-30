package io.project.RegistrationForWorkshops.sheets;

import io.project.RegistrationForWorkshops.model.Subject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class SheetsParser {
    static LocalDate parseDate(String representation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        LocalDateTime adaptedDate;
        try {
            adaptedDate = LocalDateTime.parse(representation.split(", ")[1].split("-")[0]
                    + " " + representation.split(", ")[0] + ".2024", formatter);
        } catch (IndexOutOfBoundsException e) {
            adaptedDate = LocalDateTime.now();
        }

        return adaptedDate.toLocalDate();
    }

    static List<Subject> parseSubjectList(String representation) {
        return Arrays.stream(Subject.values())
                .filter(elem -> representation.contains(elem.toString()))
                .collect(Collectors.toList());
    }
}
