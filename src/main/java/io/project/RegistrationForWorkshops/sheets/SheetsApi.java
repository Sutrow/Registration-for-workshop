package io.project.RegistrationForWorkshops.sheets;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Component
@PropertySource("application.properties")
public class SheetsApi {
    final Sheets sheetsService;
    final SheetsConfig config;

    public SheetsApi(SheetsConfig config) throws IOException {
        this.config = config;
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(config.getJsonKeyFilePath()))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        sheetsService = new Sheets.Builder(
                credential.getTransport(),
                credential.getJsonFactory(),
                credential)
                .setApplicationName("MY APP")
                .build();
    }

    List<String> getListNames() throws IOException {
        List<String> sheetTitles = new ArrayList<>();
        Spreadsheet res2 = sheetsService.spreadsheets().get(config.getSpreadsheet()).execute();
        for (Sheet sheet: res2.getSheets()) {
            sheetTitles.add(sheet.getProperties().getTitle());
        }
        return sheetTitles;
    }

    int getSheetSize(String tableName) throws IOException {
        return sheetsService.spreadsheets().values().get(config.getSpreadsheet(),
                tableName + "!A:BN").execute().getValues().get(2).size();
    }

    ValueRange getTable(String tableName) throws IOException {
        return sheetsService.spreadsheets().values().get(config.getSpreadsheet(),
                tableName + "!A:BN").execute();
    }

    public void updateCells(String spreadsheetId, String range, List<List<Object>> values) throws IOException {
        ValueRange body = new ValueRange().setValues(values);
        sheetsService.spreadsheets().values()
                .update(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .execute();
    }
}
