package com.adswizz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.adswizz.lib.Download;
import com.adswizz.lib.DownloadsStatistics;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import zio.ftp.SecureFtp;

public class App {
    public static void main(String[] args) throws IOException {

        Download[] downloads = ParseDownloads();
        DownloadsStatistics statistics = new DownloadsStatistics(downloads);

        Map<String, Long> mostWatchedShow = statistics.GetMostWatchedShow("san francisco");
        System.out.println("Most popular show is: " + mostWatchedShow.keySet().toArray()[0]);
        System.out.println("Number of downloads is: " + mostWatchedShow.values().toArray()[0]);

        System.out.println();

        Map<String, Long> mostUsedDevice = statistics.GetMostUsedDevice();
        System.out.println("Most popular device is: " + mostUsedDevice.keySet().toArray()[0]);
        System.out.println("Number of downloads is: " + mostUsedDevice.values().toArray()[0]);

        System.out.println();

        Map<String, Integer> prerollOpportunities = statistics.GetPrerollCommercialOpportunities();
        for (Map.Entry<String, Integer> entry : prerollOpportunities.entrySet()) {
            System.out.println("Show Id: " + entry.getKey()
                    + ", Preroll Opportunity Number: " + entry.getValue());
        }

        System.out.println();

        Map<String, String> weeklyShows = statistics.GetShowsWithWeeklyAirings();
        System.out.println("Weekly shows are: ");
        System.out.println();
        for (Map.Entry<String, String> entry : weeklyShows.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public static Download[] ParseDownloads() {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Download> downloads = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    SecureFtp.class.getClassLoader().getResource("downloads.txt").getFile()));
            String line = reader.readLine();

            while (line != null) {
                downloads.add(mapper.readValue(line, Download.class));
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return downloads.toArray(new Download[downloads.size()]);
    }

}
