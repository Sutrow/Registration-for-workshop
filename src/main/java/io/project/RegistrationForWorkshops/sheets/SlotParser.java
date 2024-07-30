package io.project.RegistrationForWorkshops.sheets;

import io.project.RegistrationForWorkshops.model.SlotDAO;
import io.project.RegistrationForWorkshops.model.SlotIdCounter;
import java.util.ArrayList;
import java.util.List;

public class SlotParser {
    SheetsApi sheets;

    SlotParser(SheetsApi sheets) {
        this.sheets = sheets;
    }

    List<SlotDAO> getSlotDescription(List<List<Object>> rows, int width, String sheetTitle) {
        List<SlotDAO> slots = new ArrayList<>();
        NextItem nextItem = NextItem.NAME;
        for (int col = 0; col < width; col += 11) {
            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                List<Object> row = rows.get(rowIndex);
                if (row.isEmpty() || rowIndex < 4) {
                    continue;
                }

                if (rowIndex == 4 && !row.get(col).equals("")
                        || nextItem == NextItem.NAME && row.size() >= col + 1 && !row.get(col).equals("")) {
                    slots.add(new SlotDAO(SlotIdCounter.generateID()));
                    slots.get(slots.size() - 1).setSheet(sheets.config.getSpreadsheet());
                    slots.get(slots.size() - 1).setRange(sheetTitle);
                    slots.get(slots.size() - 1).getLocation().setColumnIndex(col);
                    slots.get(slots.size() - 1).setProfessor(row.get(col).toString());
                    slots.get(slots.size() - 1).getLocation().setStartIndex(rowIndex);
                    nextItem = NextItem.SUBJECT;
                    continue;
                }

                if (nextItem == NextItem.SUBJECT) {
                    slots.get(slots.size() - 1).setSubjectsList(SheetsParser.parseSubjectList(row.get(col).toString()));
                    nextItem = NextItem.TIME;
                    continue;
                }

                if (nextItem == NextItem.TIME) {
                    slots.get(slots.size() - 1).setTime(SheetsParser.parseDate(row.get(col).toString()));
                    nextItem = NextItem.NAME;
                }
            }
        }
        return slots;
    }

    List<SlotDAO> updateSlotCapacity(List<SlotDAO> slots, List<List<Object>> rows) {
        for (SlotDAO slot: slots) {
            int slotColumn = slot.getLocation().getColumnIndex();
            if (slot.getLocation().getEndIndex() < 0) {
                for (int rowSlot = slot.getLocation().getStartIndex(); rowSlot < rows.size(); rowSlot++) {
                    List<Object> row = rows.get(rowSlot);
                    if (row.isEmpty() || rowSlot < 3) {
                        continue;
                    }

                    if (row.size() >= slotColumn + 5 && !row.get(slotColumn + 1).equals("")) {
                        slot.setInitialRecords(slot.getInitialRecords() + 1);
                    }

                    if (row.size() >= slotColumn + 9 && !row.get(slotColumn + 5).equals("")) {
                        slot.setReserveRecords(slot.getReserveRecords() + 1);
                    }
                }
                slot.setInitialCapacity(Math.max(slot.getInitialRecords(), slot.getInitialCapacity()));
                slot.setReserveCapacity(Math.max(slot.getReserveRecords(), slot.getReserveCapacity()));
                continue;
            }
            slot.setInitialCapacity(slot.getLocation().getEndIndex() - slot.getLocation().getStartIndex());
            for (int rowSlot = slot.getLocation().getStartIndex(); rowSlot < slot.getLocation().getEndIndex(); rowSlot++) {
                List<Object> row = rows.get(rowSlot);
                if (row.isEmpty() || rowSlot < 3) {
                    continue;
                }

                if (row.size() >= slotColumn + 5 && !row.get(slotColumn + 1).equals("")
                        || row.size() >= slotColumn + 9 && !row.get(slotColumn + 5).equals("")) {
                    slot.setInitialRecords(slot.getInitialRecords() + 1);
                }
            }
        }
        return slots;
    }
}
