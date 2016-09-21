package com.example.finance.viewcontroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.finance.R;

/**
 * Screen ending activity.
 */
public class FinalActivity extends AppCompatActivity {

    /**
     * Start the activity. Destroys activity back stack.
     * @param context -
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FinalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
    }

}
