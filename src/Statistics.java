import java.time.LocalDateTime;
import java.time.Duration;

public class Statistics {
    private int totalTraffic;

    private LocalDateTime minTime;

    private LocalDateTime maxTime;


    public void addEntry(int traffic, LocalDateTime entryTime){
        totalTraffic += traffic;

        if(minTime == null || entryTime.isBefore(minTime)){
            minTime = entryTime;
        }else if(maxTime == null|| entryTime.isAfter(maxTime)){
            maxTime = entryTime;
        }
    }
    public double getAverageTrafficPerHour(){

        if(minTime==null||maxTime==null) return 0;
        else
        {
            long timeDifference =
                    Duration.between(minTime , maxTime ).toMillis();

            return (double)(totalTraffic) / ((timeDifference/1000)/60/60);
        }
    }

    public double getTrafficRate() {

        return (double)totalTraffic/(getAverageTrafficPerHour());
    }
    public static void main(String[] args) {

        Statistics statistics = new Statistics();

        statistics.addEntry(10, LocalDateTime.now());
        statistics.addEntry(20, LocalDateTime.now().plusHours(1));
        statistics.addEntry(30, LocalDateTime.now().plusHours(2));

        System.out.println("Средняя скорость трафика за час: " + statistics.getAverageTrafficPerHour());
        System.out.println("Скорость изменения трафика: " + statistics.getTrafficRate());

    }
}