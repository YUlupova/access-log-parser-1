import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserAgent {

    private static final Pattern OS_PATTERN = Pattern.compile("(Windows NT [^;]+|Macintosh; Intel Mac OS X[^;]+|Linux[^;]*)");
    private static final Pattern BROWSER_PATTERN = Pattern.compile("(Edg|[Ff]irefox|Chrome|OPR|Safari)");

    private final String osType;
    private final String browserType;

    public UserAgent(String userAgentString) {
        this.osType = extractOSType(userAgentString);
        this.browserType = extractBrowserType(userAgentString);
    }

    private String extractOSType(String userAgentString) {
        Matcher matcher = OS_PATTERN.matcher(userAgentString);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Other";
    }

    private String extractBrowserType(String userAgentString) {
        Matcher matcher = BROWSER_PATTERN.matcher(userAgentString);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Other";
    }

    public String getOsType() {
        return osType;
    }

    public String getBrowserType() {
        return browserType;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "osType='" + osType + '\'' +
                ", browserType='" + browserType + '\'' +
                '}';
    }
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Kurs1\\access.log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UserAgent userAgent = new UserAgent(line);
                System.out.println(userAgent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}