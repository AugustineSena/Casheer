package sample;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.TableModel;

class ExcelExport {
    ExcelExport() {
    }

    public void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);
        Throwable var6 = null;

        try {
            int i;
            for(i = 0; i < model.getColumnCount(); ++i) {
                bw.write(model.getColumnName(i) + "\t");
            }

            bw.write("\n");

            for(i = 0; i < model.getRowCount(); ++i) {
                for(int j = 0; j < model.getColumnCount(); ++j) {
                    bw.write(model.getValueAt(i, j).toString() + "\t");
                }

                bw.write("\n");
            }
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            if (bw != null) {
                if (var6 != null) {
                    try {
                        bw.close();
                    } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                    }
                } else {
                    bw.close();
                }
            }

        }

        System.out.print("Write out to" + file);
    }
}
