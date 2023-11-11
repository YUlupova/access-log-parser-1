import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogEntry {

    public enum HttpMethod {
        GET, POST, PUT, DELETE, HEAD, CONNECT, OPTIONS, TRACE, PATCH
    }

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
    private static final Pattern LOG_PATTERN = Pattern.compile("^([\\d.]+) - - \\[(.+?)] \"([A-Z]+) (.+?) HTTP/1\\.\\d\" (\\d+) (\\d+) \"(.+?)\" \"(.+?)\"$");

    private final String ipAddress;
    private final ZonedDateTime dateTime;
    private final HttpMethod httpMethod;
    private final String requestUrl;
    private final int responseCode;
    private final long responseSize;
    private final String referrer;
    private final String userAgent;

    public LogEntry(String logEntryString) {
        Matcher matcher = LOG_PATTERN.matcher(logEntryString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid log entry string: " + logEntryString);
        }

        this.ipAddress = matcher.group(1);
        this.dateTime = ZonedDateTime.parse(matcher.group(2), DATE_FORMAT);
        this.httpMethod = HttpMethod.valueOf(matcher.group(3));
        this.requestUrl = matcher.group(4);
        this.responseCode = Integer.parseInt(matcher.group(5));
        this.responseSize = Long.parseLong(matcher.group(6));
        this.referrer = matcher.group(7).equals("-") ? null : matcher.group(7);
        this.userAgent = matcher.group(8).equals("-") ? null : matcher.group(8);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddress='" + ipAddress + '\'' +
                ", dateTime=" + dateTime +
                ", httpMethod=" + httpMethod +
                ", requestUrl='" + requestUrl + '\'' +
                ", responseCode=" + responseCode +
                ", responseSize=" + responseSize +
                ", referrer='" + referrer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
    static String logEntryString = "127.0.0.1 - - [08/Nov/2023:15:30:42 +0000] \"GET /example HTTP/1.1\" 200 1234 \"http://referrer.com\" \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.45.67 Safari/537.36\"";
    static LogEntry logEntry = new LogEntry(logEntryString);

    public static void main(String[] args) {
        String logFilePath = "C:\\Kurs1\\access.log";  // Замените на путь к вашему файлу

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    LogEntry logEntry = new LogEntry(line);
                    System.out.println("IP Address: " + logEntry.getIpAddress());
                    System.out.println("Date & Time: " + logEntry.getDateTime());
                    System.out.println("HTTP Method: " + logEntry.getHttpMethod());
                    System.out.println("Request URL: " + logEntry.getRequestUrl());
                    System.out.println("Response Code: " + logEntry.getResponseCode());
                    System.out.println("Response Size: " + logEntry.getResponseSize());
                    System.out.println("Referrer: " + logEntry.getReferrer());
                    System.out.println("User Agent: " + logEntry.getUserAgent());
                    System.out.println();
                } catch (IllegalArgumentException e) {
                    System.err.println("Can't parse log entry: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Can't read log file: " + logFilePath);
        }
}
}