package io.project.RegistrationForWorkshops.sheets;

import com.google.api.services.sheets.v4.model.ValueRange;
import io.project.RegistrationForWorkshops.model.SlotDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Component
@PropertySource("application.properties")
public class SlotRecipient {
    SheetsApi sheets;
    SlotParser parser;

    SlotRecipient(SheetsApi sheets) {
        this.sheets = sheets;
        this.parser = new SlotParser(sheets);
    }

    public List<SlotDAO> readAllSheets() throws IOException {
        List<String> sheetTitles = sheets.getListNames();
        List<SlotDAO> slots = new ArrayList<>();
        for (String sheetTitle: sheetTitles) {
            slots.addAll(readData(sheets.getTable(sheetTitle), sheets.getSheetSize(sheetTitle), sheetTitle));
        }
        return slots;
    }

    List<SlotDAO> readData(ValueRange table, int width, String sheetTitle) {
        List<List<Object>> rows = table.getValues();
        if (rows == null || rows.isEmpty()) {
            return new ArrayList<>();
        }

        List<SlotDAO> slots = parser.getSlotDescription(rows, width, sheetTitle);
        slots = parser.updateSlotCapacity(slots, rows);

        return slots;
    }
}
