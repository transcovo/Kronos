//Forked from wdullaer/MaterialDateTimePicker and modified by Chauffeur-Privé
package com.chauffeurprive.kronos.time;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Simple utility class that represents a time in the day up to second precision
 * The time input is expected to use 24 hour mode.
 * Fields are modulo'd into their correct ranges.
 * It does not handle timezones.
 *
 * Created by wdullaer on 13/10/15.
 */
public class TimePoint implements Parcelable, Comparable<TimePoint> {
    private int hour;
    private int minute;
    private int second;

    public enum TYPE {
        HOUR,
        MINUTE,
        SECOND
    }

    public TimePoint(TimePoint time) {
        this(time.hour, time.minute, time.second);
    }

    public TimePoint(@IntRange(from = 0, to = 23) int hour,
                     @IntRange(from = 0, to = 59) int minute,
                     @IntRange(from = 0, to = 59) int second) {
        this.hour = hour % 24;
        this.minute = minute % 60;
        this.second = second % 60;
    }

    public TimePoint(Calendar date) {
        this.hour = date.get(Calendar.HOUR_OF_DAY);
        this.minute = date.get(Calendar.MINUTE);
        this.second = date.get(Calendar.SECOND);
    }

    public TimePoint(@IntRange(from = 0, to = 23) int hour,
                     @IntRange(from = 0, to = 59) int minute) {
        this(hour, minute, 0);
    }

    public TimePoint(@IntRange(from = 0, to = 23) int hour) {
        this(hour, 0);
    }

    public TimePoint(Parcel in) {
        hour = in.readInt();
        minute = in.readInt();
        second = in.readInt();
    }

    @IntRange(from = 0, to = 23)
    public int getHour() {
        return hour;
    }

    @IntRange(from = 0, to = 59)
    public int getMinute() {
        return minute;
    }

    @IntRange(from = 0, to = 59)
    public int getSecond() {
        return second;
    }

    public boolean isAM() {
        return hour < 12;
    }

    public boolean isPM() {
        return hour >= 12 && hour < 24;
    }

    public void setAM() {
        if (hour >= 12) hour = hour % 12;
    }

    public void setPM() {
        if (hour < 12) hour = (hour + 12) % 24;
    }

    @Override
    public boolean equals(Object o) {
        try {
            TimePoint other = (TimePoint) o;

            return other.getHour() == hour &&
                    other.getMinute() == minute &&
                    other.getSecond() == second;
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int compareTo(@NonNull TimePoint t) {
        return (this.hour - t.hour) * 3600 + (this.minute - t.minute) * 60 + (this.second - t.second);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(hour);
        out.writeInt(minute);
        out.writeInt(second);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<TimePoint> CREATOR
            = new Parcelable.Creator<TimePoint>() {
        public TimePoint createFromParcel(Parcel in) {
            return new TimePoint(in);
        }

        public TimePoint[] newArray(int size) {
            return new TimePoint[size];
        }
    };

    @Override
    public String toString() {
        return "Timepoint{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }
}
