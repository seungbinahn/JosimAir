package com.cbnu.josimair;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cbnu.josimair.Model.service.StatisticsService;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DaoUnitTest {
    @Test
    public void 시간별_통계_테스트() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        StatisticsService ss = new StatisticsService(appContext);
        assertEquals(24, ss.getHourStatisticsData().size());
    }

    @Test
    public void 일별_통계_테스트() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        StatisticsService ss = new StatisticsService(appContext);
        assertEquals(7, ss.getDayStatisticsData().size());
    }

    @Test
    public void 주별_통계_테스트() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        StatisticsService ss = new StatisticsService(appContext);
        assertEquals(7, ss.getWeekStatisticsData().size());
    }
}
