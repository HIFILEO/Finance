package com.example.finance.gateway;

import com.example.finance.RxBaseTest;
import com.example.finance.TestResourceFileHelper;
import com.example.finance.categories.UnitTest;
import com.example.finance.model.DataManager;
import com.example.finance.model.Question;
import com.example.finance.model.QuestionType;
import com.example.finance.model.TextQuestion;
import com.example.finance.service.QuestionInfoWeb;
import com.example.finance.service.ScreenInfoResponseWeb;
import com.example.finance.service.ScreenInfoServiceApi;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;


import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.ibm.icu.impl.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test the InfoGatewayWebImpl
 */
@Category(UnitTest.class)
public class ScreenInfoGatewayWebImpTest extends RxBaseTest {
    ScreenInfoGateway screenInfoGateway;

    @Mock
    ScreenInfoServiceApi mockScreenInfoServiceApi;

    @Mock
    DataManager mockDataManager;

    @Before
    public void setUp() {
        super.setUp();
        initMocks(this);
        screenInfoGateway = new ScreenInfoGatewayWebImpl(mockScreenInfoServiceApi, mockDataManager);
    }

    /**
     * This is an example of how we can test the observable and what results are returned.
     */
    @Test
    public void testLoadInfo() {
        //
        //Arrange
        //
        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();

        String json = null;
        try {
            json = TestResourceFileHelper.getFileContentAsString(this, "screen_test.json");
        } catch (Exception e) {
            fail(e);
        }

        ScreenInfoResponseWeb screenInfoResponseWeb = new Gson().fromJson(json, ScreenInfoResponseWeb.class);
        when(mockScreenInfoServiceApi.getInfo()).thenReturn(Observable.just(screenInfoResponseWeb));

        //
        //Act
        //
        screenInfoGateway.loadInfo().subscribe(testSubscriber);
        testScheduler.triggerActions();

        //
        //Assert
        //
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(true);


        //Examples of how you can verify the whole thing...
        verify(mockDataManager, times(8)).addQuestion(anyInt(), any(Question.class));
    }

    /**
     * An example of how we can test each part of our translator without involving RX
     */
    @Test
    public void testQuestionTranslate() {
        //
        //Arrange
        //
        QuestionInfoWeb mockQuestionInfoWeb = mock(QuestionInfoWeb.class);
        when(mockQuestionInfoWeb.getType()).thenReturn(0);
        when(mockQuestionInfoWeb.getNumber()).thenReturn(0);
        when(mockQuestionInfoWeb.getQuestion()).thenReturn("I AM?");

        ScreenInfoGatewayWebImpl.LoadScreenInfoSubscriptionFunc1 loadScreenInfoSubscriptionFunc1 =
                new ScreenInfoGatewayWebImpl.LoadScreenInfoSubscriptionFunc1(mockDataManager);
        //
        //Act
        //
        Question questionUnderTest = loadScreenInfoSubscriptionFunc1.translateQuestion(mockQuestionInfoWeb);

        //
        //Assert
        //
        assertThat(questionUnderTest.getType()).isEqualTo(QuestionType.TEXTUAL);
        assertThat(questionUnderTest.getQuestionNumber()).isEqualTo(0);
        assertThat(questionUnderTest.getQuestion()).isEqualToIgnoringCase("I AM?");
        assertThat(questionUnderTest, instanceOf(TextQuestion.class));
    }

    @Test
    public void testAllTranslation() {
        //
        //Arrange
        //
        String json = null;
        try {
            json = TestResourceFileHelper.getFileContentAsString(this, "screen_test.json");
        } catch (Exception e) {
            fail(e);
        }

        ScreenInfoResponseWeb screenInfoResponseWeb = new Gson().fromJson(json, ScreenInfoResponseWeb.class);


        DataManager dataManager = new DataManager();


        ScreenInfoGatewayWebImpl.LoadScreenInfoSubscriptionFunc1 loadScreenInfoSubscriptionFunc1 =
                new ScreenInfoGatewayWebImpl.LoadScreenInfoSubscriptionFunc1(dataManager);

        //
        //Act
        //
        loadScreenInfoSubscriptionFunc1.call(screenInfoResponseWeb);

        //
        //Assert
        //
        assertThat(dataManager.getQuestions(0)).isNotNull();
        assertThat(dataManager.getQuestions(1)).isNotNull();
        assertThat(dataManager.getQuestions(2)).isNotNull();
        assertThat(dataManager.getQuestions(3)).isNotNull();
        assertThat(dataManager.getQuestions(0).size()).isEqualTo(3);
        assertThat(dataManager.getQuestions(1).size()).isEqualTo(4);
        assertThat(dataManager.getQuestions(2).size()).isEqualTo(1);
        assertThat(dataManager.getQuestions(3).size()).isEqualTo(1);

        assertThat(dataManager.getTakeAways(1).size()).isEqualTo(2);

        assertThat(dataManager.getSkips(2).size()).isEqualTo(1);

        List<Question> questionList = dataManager.getQuestions(0);
        assertThat(questionList).isNotNull();
        assertThat(questionList.get(0).getQuestionNumber()).isEqualTo(0);
        assertThat(questionList.get(1).getQuestionNumber()).isEqualTo(1);
        assertThat(questionList.get(2).getQuestionNumber()).isEqualTo(2);
    }

}
