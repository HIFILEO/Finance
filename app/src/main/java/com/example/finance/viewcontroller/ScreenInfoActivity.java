package com.example.finance.viewcontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finance.R;
import com.example.finance.adapter.OnDataCompletedListener;
import com.example.finance.adapter.ScreenInfoListAdapter;
import com.example.finance.application.FinanceApplication;
import com.example.finance.gateway.ScreenInfoGateway;
import com.example.finance.gateway.ScreenInfoGatewayWebImpl;
import com.example.finance.interactor.InfoInteractor;
import com.example.finance.interactor.InfoInteractorImpl;
import com.example.finance.model.DataManager;
import com.example.finance.model.Question;
import com.example.finance.model.ScreenManager;
import com.example.finance.presenter.InfoPresenter;
import com.example.finance.presenter.InfoPresenterImpl;
import com.example.finance.presenter.InfoViewModel;
import com.example.finance.service.ScreenInfoServiceApi;
import com.example.finance.service.ScreenInfoServiceApiRawImpl;
import com.example.finance.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activity that shows all the screen data for a user ot make choices.
 */
public class ScreenInfoActivity extends AppCompatActivity implements InfoViewModel, OnDataCompletedListener {
    private InfoPresenter infoPresenter;
    private ScreenInfoListAdapter screenInfoListAdapter;
    private Menu menu;

    //Injected components
    @Inject
    ScreenInfoServiceApi screenInfoServiceApi;
    @Inject
    DataManager dataManager;

    //Bind Views
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.takeAwayTextView)
    TextView takeAwayTextView;
    @Bind(R.id.welcomeTextView)
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_info);
        ButterKnife.bind(this);

        //Inject Dagger Service
        FinanceApplication.getInstance().getComponent().inject(this);

        //For testing without server
        screenInfoServiceApi = new ScreenInfoServiceApiRawImpl(this);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        //Create Interactor
        ScreenInfoGateway screenInfoGateway = new ScreenInfoGatewayWebImpl(screenInfoServiceApi, dataManager);
        InfoInteractor infoInteractor = new InfoInteractorImpl(screenInfoGateway);

        //Create Presenter
        infoPresenter = new InfoPresenterImpl(infoInteractor, this, dataManager, new ScreenManager(dataManager), this);

        //Start Presenter so Interactor response model is setup correctly.
        infoPresenter.start();

        //Load Info
        infoPresenter.loadInfo();

        //set blank adapter
        screenInfoListAdapter = new ScreenInfoListAdapter(new ArrayList<Question>(), this, dataManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL_LIST,
                getResources().getColor(android.R.color.black)));
        recyclerView.setAdapter(screenInfoListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        Create a menu option to toggle the screen between List and Grid view.
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_layout_menu, menu);
        this.menu = menu;

        menu.findItem(R.id.nextLayout).setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextLayout:
                infoPresenter.next();
                showNextButton(false);
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showInProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_msg, Toast.LENGTH_LONG).show();
        infoPresenter.loadInfo();
    }

    @Override
    public void setAdapter(List<Question> questionList) {
        screenInfoListAdapter.replace(questionList);
    }

    @Override
    public void setNameTextField(String name) {
        welcomeTextView.setText(name);
    }

    @Override
    public void gotToFinalScreen() {
        FinalActivity.startActivity(this);
        finish();
    }

    @Override
    public void showTakeAway(String textToShow) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animation.reset();

        takeAwayTextView.setVisibility(View.VISIBLE);
        takeAwayTextView.setText(textToShow);
        takeAwayTextView.clearAnimation();
        takeAwayTextView.startAnimation(animation);

        recyclerView.setVisibility(View.INVISIBLE);
        welcomeTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideTakeAway() {
        takeAwayTextView.clearAnimation();
        takeAwayTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        welcomeTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNextButton(boolean showButton) {
        if (showButton) {
            menu.findItem(R.id.nextLayout).setEnabled(true);
        } else {
            menu.findItem(R.id.nextLayout).setEnabled(false);
        }
    }

}
