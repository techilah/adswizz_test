package com.adswizz.lib;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Download {

    @JsonProperty("deviceType")
    public String DeviceType;

    @JsonProperty("city")
    public String City;

    public String getShowId() {
        return DownloadIdentifier.ShowId;
    }

    @JsonProperty("downloadIdentifier")
    public DownloadIdentifier DownloadIdentifier;

    public static class DownloadIdentifier {

        @JsonProperty("showId")
        public String ShowId;
    }

    @JsonProperty("opportunities")
    public Opportunity[] Opportunities;

    public static class Opportunity {

        @JsonProperty("originalEventTime")
        public long OriginalEventTime;

        @JsonProperty("positionUrlSegments")
        public PositionUrlSegments PositionUrlSegments;

        public class PositionUrlSegments {
            @JsonProperty("aw_0_ais.adBreakIndex")
            public String[] adBreakIndex;
        }
    }

    public int GetPrerollOpportunityCount() {

        int retval = 0;
        for (Opportunity opportunity : Opportunities) {
            if (Arrays.stream(opportunity.PositionUrlSegments.adBreakIndex).anyMatch("preroll"::equals)) {
                retval++;
            }
        }
        return retval;
    }

    public String GetDayofWeekAndTimeofAiring() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTime(new Date(Opportunities[0].OriginalEventTime));
        String dayOfWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        return dayOfWeek + " " + String.format("%02d", hour) + ":"
                + String.format("%02d", minutes);
    }
}
