package DashboardPengusaha.FinancialStatsData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FinancialHandler {
    private static final String FILE_PATH = "pendapatan.json";

    public static void simpanPendapatan(List<FinancialBulanan> pendapatanList) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(FILE_PATH), pendapatanList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<FinancialBulanan> ambilPendapatan() {
        List<FinancialBulanan> pendapatanList = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            pendapatanList = mapper.readValue(new File(FILE_PATH), new TypeReference<List<FinancialBulanan>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pendapatanList;
    }
}
