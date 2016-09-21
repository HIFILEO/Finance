package com.example.finance.model;

import com.example.finance.TestResourceFileHelper;
import com.example.finance.categories.UnitTest;
import com.example.finance.gateway.ScreenInfoGatewayWebImpl;
import com.example.finance.service.ScreenInfoResponseWeb;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ibm.icu.impl.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test the screen manager.
 */
@Category(UnitTest.class)
public class ScreenManagerTest {
    private DataManager dataManager = new DataManager();

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testTakeAway_stringValue() {
        //
        //Arrange
        //
        //Note - I could mock this all out but trying to get things done fast here
        String json = null;
        try {
            json = TestResourceFileHelper.getFileContentAsString(this, "screen_test.json");
        } catch (Exception e) {
            fail(e);
        }

        ScreenInfoResponseWeb screenInfoResponseWeb = new Gson().fromJson(json, ScreenInfoResponseWeb.class);
        ScreenInfoGatewayWebImpl.LoadScreenInfoSubscriptionFunc1 loadScreenInfoSubscriptionFunc1 =
                new ScreenInfoGatewayWebImpl.LoadScreenInfoSubscriptionFunc1(dataManager);
        loadScreenInfoSubscriptionFunc1.call(screenInfoResponseWeb);

        ScreenManager screenManager = new ScreenManager(dataManager);

        dataManager.setAnswer(3, new NumericalAnswer(25));
        dataManager.setAnswer(4, new NumericalAnswer(100000));

        //
        //Act
        //
        String takeAway = screenManager.determineTakeAway(1);

        //
        //Assert
        //
        assertThat(takeAway).isNotNull();
        assertThat(takeAway).isNotEmpty();
        assertThat(takeAway).isEqualToIgnoringCase("Great job! Did you know most people in their 20's only on average have $1k in their bank account");
    }

    @Test
    public void testSkip_true() {
        //
        //Arrange
        //
        //Note - I could mock this all out but trying to get things done fast here
        String json = null;
        try {
            json = TestResourceFileHelper.getFileContentAsString(this, "screen_test.json");
        } catch (Exception e) {
            fail(e);
        }

        ScreenInfoResponseWeb screenInfoResponseWeb = new Gson().fromJson(json, ScreenInfoResponseWeb.class);
        ScreenInfoGatewayWebImpl.LoadScreenInfoSubscriptionFunc1 loadScreenInfoSubscriptionFunc1 =
                new ScreenInfoGatewayWebImpl.LoadScreenInfoSubscriptionFunc1(dataManager);
        loadScreenInfoSubscriptionFunc1.call(screenInfoResponseWeb);

        Set<Integer> answerSet = new HashSet<>();
        answerSet.add(1);
        dataManager.setAnswer(7, new MultipleChoiceAnswer(answerSet));

        ScreenManager screenManager = new ScreenManager(dataManager);

        //
        //Act
        //
        boolean answer = screenManager.determineSkip(2);

        //
        //Assert
        //
        assertThat(answer).isTrue();

    }
}
