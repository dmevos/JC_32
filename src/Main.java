import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static final String fPath = "C://NetologyProject//JavaCore//JC_32//Games//savegames//";

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(1, 2, 3, 4.0);
        GameProgress gameProgress2 = new GameProgress(10, 20, 30, 40.0);
        GameProgress gameProgress3 = new GameProgress(100, 200, 300, 400.0);

        saveGame(fPath + "save1.dat", gameProgress1);
        saveGame(fPath + "save2.dat", gameProgress2);
        saveGame(fPath + "save3.dat", gameProgress3);

        zipFiles(fPath + "zipsave.zip", new String[]{fPath + "save1.dat", fPath + "save2.dat", fPath + "save3.dat"});
    }

    public static void saveGame(String path, GameProgress gP) {
        try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gP);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, String[] listPathFiles) {

        try (ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String fileName : listPathFiles) {
                try (FileInputStream fis = new FileInputStream(fileName)) {

                    String subFileName = fileName.substring(2 + fileName.lastIndexOf("//"));

                    ZipEntry entry = new ZipEntry(subFileName);
                    zOut.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zOut.write(buffer);
                    zOut.closeEntry();

                } catch (Exception ex1) {
                    System.out.println(ex1.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        for (String fileName : listPathFiles) {
            File file = new File(fileName);
            if (file.exists()){
                if (!file.delete()){
                    System.out.println("Не удалось удалить файл "+ fileName);
                }
            }
        }
    }
}
