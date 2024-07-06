package DashboardPengusaha.FinancialStatsData;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FinancialBulanan {
    private final SimpleStringProperty bulan;
    private final SimpleIntegerProperty pendapatan;

    public FinancialBulanan(String bulan, int pendapatan) {
        this.bulan = new SimpleStringProperty(bulan);
        this.pendapatan = new SimpleIntegerProperty(pendapatan);
    }

    public String getBulan() {
        return bulan.get();
    }

    public void setBulan(String bulan) {
        this.bulan.set(bulan);
    }

    public SimpleStringProperty bulanProperty() {
        return bulan;
    }

    public int getPendapatan() {
        return pendapatan.get();
    }

    public void setPendapatan(int pendapatan) {
        this.pendapatan.set(pendapatan);
    }

    public SimpleIntegerProperty pendapatanProperty() {
        return pendapatan;
    }
}
