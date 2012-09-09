package org.hb.bus.chat;

import java.util.List;

import org.hb.bus.api.MakeCallToPollServer;
import org.hb.bus.api.PollOption;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PollActivity extends Activity {
	
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        pollTitle = getIntent().getExtras().getString("poll");
        Log.i("PollActivity", pollTitle);
        
        mChatApplication = (ChatApplication)getApplication();
        
        mPollTitle = (TextView)findViewById(R.id.pollTitle);
        mPollTitle.setText(pollTitle);
        
        mPollOptionRadioGroup = (RadioGroup)findViewById(R.id.pollItemRadioGroup);
        getPollOptions(pollTitle);
        
        mSubmitButton = (Button)findViewById(R.id.pollButtonSubmit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				StrictMode.setThreadPolicy(policy);
		    	
		    	MakeCallToPollServer mkServer = new MakeCallToPollServer();
		    	
		    	RadioButton selectedPollOptionButton = (RadioButton) mPollOptionRadioGroup.findViewById(mPollOptionRadioGroup.getCheckedRadioButtonId());
		    	String selectedPollOption = selectedPollOptionButton.getText().toString();
		    	selectedPollOption = selectedPollOption.substring(0, selectedPollOption.indexOf("(") - 1);
		    	Log.i(".chat.PollActivity", "selected poll option: "+selectedPollOption);
		    	mkServer.voteOnPoll(mChatApplication.useGetChannelName(), pollTitle, selectedPollOption);
				
		    	getActivity().finish();
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_poll, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void getPollOptions(String title) {
    	StrictMode.setThreadPolicy(policy);
    	
    	MakeCallToPollServer mkServer = new MakeCallToPollServer();
    	List<PollOption> pollOptions = mkServer.getAllOptionsForPoll(mChatApplication.useGetChannelName(), title);
    	
    	for (PollOption option : pollOptions) {
    		RadioButton rb = new RadioButton(getApplicationContext());
            rb.setTextColor(Color.BLACK);
            rb.setText(option.getPollTitle() + " ("+option.getCount()+")");
            mPollOptionRadioGroup.addView(rb);
    	}
    	
    }
    
    private Activity getActivity() {
    	return this;
    }

    private TextView mPollTitle;
    private Button mCancelButton;
    private Button mSubmitButton;
    private RadioGroup mPollOptionRadioGroup;
    private String pollTitle;
    private ChatApplication mChatApplication;
}
