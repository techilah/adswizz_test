package com.adswizz.lib;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DownloadsStatistics {
    private Download[] downloads;

    public DownloadsStatistics(Download[] downloads) {
        this.downloads = downloads;
    }

    public Map<String, Long> GetMostWatchedShow(String city) {

        Map<String, Long> showDownloadsCounts = Arrays.stream(downloads)
                .filter(d -> d.City.equals(city))
                .collect(Collectors.groupingBy(d -> d.DownloadIdentifier.ShowId, Collectors.counting()));

        return showDownloadsCounts.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(1)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Long> GetMostUsedDevice() {

        Map<String, Long> deviceTypeCounts = Arrays.stream(downloads)
                .collect(Collectors.groupingBy(d -> d.DeviceType, Collectors.counting()));

        return deviceTypeCounts.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(1)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Integer> GetPrerollCommercialOpportunities() {

        Map<String, Integer> commercialOpportunitiesPreroll = Arrays.stream(downloads)
                .collect(Collectors.groupingBy(d -> d.DownloadIdentifier.ShowId,
                        Collectors.summingInt(Download::GetPrerollOpportunityCount)));

        return commercialOpportunitiesPreroll.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, String> GetShowsWithWeeklyAirings() {

        Map<String, List<Download>> downloadsPerShow = Arrays.stream(downloads)
                .collect(Collectors.groupingBy(Download::getShowId));

        Map<String, String> weeklyAirings = new LinkedHashMap<String, String>();

        for (Map.Entry<String, List<Download>> entry : downloadsPerShow.entrySet()) {
            Map<String, List<Download>> airingIntervals = entry.getValue().stream()
                    .collect(Collectors.groupingBy(d -> d.GetDayofWeekAndTimeofAiring()));
            if (airingIntervals.size() == 1) {
                weeklyAirings.put(entry.getKey(), airingIntervals.keySet().toArray()[0].toString());
            }
        }

        return weeklyAirings;
    }
}
