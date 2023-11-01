import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int fileCounter = 0;
        while (true) {
            try {
                System.out.print("Введите путь к файлу: ");
                String path = new Scanner(System.in).nextLine();

                File file = new File(path);
                boolean fileExists = file.exists();
                boolean isFile = file.isFile();

                if (!fileExists || !isFile) {
                    System.out.println("Указанный файл не существует или указан путь к папке или неверный путь к файлу.");
                    continue;
                }

                fileCounter++;
                System.out.println("Путь указан верно");
                System.out.println("Это файл номер " + fileCounter);

                // Variables to keep track of line statistics
                int totalLines = 0;
                int maxLength = 0;
                int minLength = Integer.MAX_VALUE;

                // Now, read the file line by line
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    if (length > 1024) {
                        // Выбрасываем собственное исключение, если строка длиннее 1024 символов
                        throw new LineLengthExceededException("Строка в файле длиннее 1024 символов.");
                    }
                    totalLines++;
                    maxLength = Math.max(maxLength, length);
                    minLength = Math.min(minLength, length);
                    // You can process the 'line' variable as needed
                }
                reader.close(); // Close the reader when done

                // Print the statistics
                System.out.println("Общее количество строк в файле: " + totalLines);
                System.out.println("Длина самой длинной строки: " + maxLength);
                System.out.println("Длина самой короткой строки: " + minLength);
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
            } catch (LineLengthExceededException e) {
                System.out.println("Ошибка: " + e.getMessage());
                // Прекращаем выполнение программы в случае исключения
                break;
            }
        }
    }
}