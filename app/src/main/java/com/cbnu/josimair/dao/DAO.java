package com.cbnu.josimair.dao;

import androidx.room.*;

import com.cbnu.josimair.Model.IndoorAir;
import com.cbnu.josimair.Model.IndoorAirGroup;

import java.util.Date;
import java.util.List;

@Dao
public interface DAO {
    @Query("SELECT * FROM indoorair")
    List<IndoorAir> getAll();

    @Query("SELECT * FROM indoorair WHERE time BETWEEN :from AND :to")
    List<IndoorAir> loadAllBetweenDates(Date from, Date to);

    @Query("DELETE FROM indoorair WHERE time BETWEEN :from AND :to")
    void deleteAllBetweenDates(Date from, Date to);

    @Insert
    void insertAll(IndoorAir... indoorAir);

    @Delete
    void delete(IndoorAir indoorAir);

    @Query("SELECT * FROM " +
            "(SELECT strftime('%m.%d',time) as time, avg(value) as value " +
            "FROM indoorair " +
            "WHERE time <= :to " +
            "GROUP BY strftime('%m%d',time) " +
            "ORDER BY strftime('%m%d',time) DESC " +
            "LIMIT 7) " +
            "ORDER BY time ASC"
    )
    List<IndoorAirGroup> getGroupByDayBetweenDates(Date to);

    @Query("SELECT * FROM " +
            "( SELECT strftime('%d %H:00',time) as time, avg(value) as value " +
            "FROM indoorair " +
            "WHERE time <= :to " +
            "GROUP BY strftime('%m%d%H',time) " +
            "ORDER BY strftime('%m%d%H',time) DESC " +
            "LIMIT 24 ) " +
            "ORDER BY time ASC")
    List<IndoorAirGroup> getGroupByHourBetweenDates(Date to);

    @Query("SELECT * FROM " +
            "(SELECT strftime('%m%d',time) as time, avg(value) as value " +
            "FROM indoorair " +
            "WHERE time <= :to " +
            "GROUP BY strftime('%W',time) " +
            "ORDER BY strftime('%W',time) DESC " +
            "LIMIT 7 ) " +
            "ORDER BY time ASC ")
    List<IndoorAirGroup> getGroupByWeekBetweenDates(Date to);
}





