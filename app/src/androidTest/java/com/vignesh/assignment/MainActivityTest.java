package com.vignesh.assignment;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule(MainActivity.class);

    private MainActivity mainActivity = null;

    private RetrofitOneAdapter retrofitOneAdapter = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = activityRule.getActivity();
    }

    @Test
    public void testLunch(){
        View view = mainActivity.findViewById(R.id.recycler);
        View view_one = mainActivity.findViewById(R.id.swipe_view);
        assertNotNull(view);
        assertNotNull(view_one);

    }
    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}