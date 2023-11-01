import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentBotCounter {
    public static void main(String[] args) {
        int googlebotCount = 0;
        int yandexbotCount = 0;
        int totalRequests = 0;

        try {
            System.out.print("Enter the path to the log file: ");
            String path = new Scanner(System.in).nextLine();

            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isFile = file.isFile();

            if (!fileExists || !isFile) {
                System.out.println("The specified file does not exist or the path is incorrect.");
                return;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            Pattern pattern = Pattern.compile("\\((.*?)\\)");

            while ((line = reader.readLine()) != null) {
                totalRequests++;

                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String botInfo = matcher.group(1);
                    String[] botInfoParts = botInfo.split(";");
                    if (botInfoParts.length >= 2) {
                        String botName = botInfoParts[1].trim();

                        if (botName.equalsIgnoreCase("Googlebot/2.1")) {
                            googlebotCount++;
                        } else if (botName.equalsIgnoreCase("YandexBot/3.0")) {
                            yandexbotCount++;
                        }
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        System.out.println("Googlebot requests: " + googlebotCount);
        System.out.println("YandexBot requests: " + yandexbotCount);
        System.out.println("Total requests: " + totalRequests);

        double googlebotRatio = (double) googlebotCount / totalRequests;
        double yandexbotRatio = (double) yandexbotCount / totalRequests;

        System.out.println("Googlebot request ratio: " + googlebotRatio);
        System.out.println("YandexBot request ratio: " + yandexbotRatio);
    }
}