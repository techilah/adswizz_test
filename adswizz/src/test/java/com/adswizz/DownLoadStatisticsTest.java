package com.adswizz;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adswizz.lib.DownloadsStatistics;

/**
 * Unit test for simple App.
 */
public class DownLoadStatisticsTest {
    protected static DownloadsStatistics statistics;

    @BeforeClass
    public static void BeforeClass() {
        statistics = new DownloadsStatistics(App.ParseDownloads());
    }

    @Test
    public void GetMostWatchedShowTest() throws Exception {
        Map<String, Long> actualValue = statistics.GetMostWatchedShow("san francisco");

        Map<String, Long> expectedValue = new HashMap<>();
        expectedValue.put("Who Trolled Amber", (long) 24);

        assertEquals("Most watched show is calculated correctly", expectedValue, actualValue);
    }

    @Test
    public void GetMostUsedDevice() throws Exception {
        Map<String, Long> actualValue = statistics.GetMostUsedDevice();

        Map<String, Long> expectedValue = new HashMap<>();
        expectedValue.put("mobiles & tablets", (long) 60);

        assertEquals("Most used device is calculated correctly", expectedValue, actualValue);
    }

    @Test
    public void GetPrerollCommercialOpportunities() throws Exception {
        Map<String, Integer> actualValue = statistics.GetPrerollCommercialOpportunities();

        Map<String, Integer> expectedValue = new HashMap<>();
        expectedValue.put("Stuff You Should Know", 40);
        expectedValue.put("Who Trolled Amber", 40);
        expectedValue.put("Crime Junkie", 30);
        expectedValue.put("The Joe Rogan Experience", 10);

        assertEquals("Preroll opportunities are calculated correctly", expectedValue, actualValue);
    }

    @Test
    public void GetShowsWithWeeklyAirings() throws Exception {
        Map<String, String> actualValue = statistics.GetShowsWithWeeklyAirings();

        Map<String, String> expectedValue = new HashMap<>();
        expectedValue.put("Crime Junkie", "Wed 22:00");
        expectedValue.put("Who Trolled Amber", "Mon 20:00");

        assertEquals("Weekly shows are calculated correctly", expectedValue, actualValue);
    }
}
