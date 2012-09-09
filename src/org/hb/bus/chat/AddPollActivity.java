package org.hb.bus.chat;

import java.util.ArrayList;
import java.util.List;

import org.hb.bus.api.MakeCallToPollServer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddPollActivity extends Activity {
	private static final String TAG = "chat.AddPollActivity";
    
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
	
	List<String> pollOptions = new ArrayList<String>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poll);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
        mChatApplication = (ChatApplication)getApplication();
        mChatApplication.checkin();
        
        mRadioGroup = (RadioGroup)findViewById(R.id.addPollRadioGroup);
        
        mPollTitle = (EditText)findViewById(R.id.addPollTitle);
        
        mAddItemTextBox = (EditText)findViewById(R.id.addPollItem);
        mAddItemTextBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            	Log.i(TAG, "EVENT XXX: "+event);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                	String message = view.getText().toString();
                    Log.i(TAG, "useMessage.onEditorAction(): got message " + message + ")");
    	            //Update list adapter
                    
                    RadioButton rb = new RadioButton(getApplicationContext());
                    rb.setTextColor(Color.BLACK);
                    rb.setText(message);
                    mRadioGroup.addView(rb);
                    
                    pollOptions.add(message);
                    
    	            view.setText("");
                }
                return true;
            }
        });
        
        mSubmitButton = (Button)findViewById(R.id.addPollSubmitButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Add a call to rest api for posting the poll
				StrictMode.setThreadPolicy(policy);
				MakeCallToPollServer mkCall = new MakeCallToPollServer();
				
				mkCall.createPoll(mChatApplication.useGetChannelName(), mPollTitle.getText().toString(), pollOptions);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_poll, menu);
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

    private RadioGroup mRadioGroup;
    private EditText mPollTitle;
    private EditText mAddItemTextBox;
    private Button mSubmitButton;
    
    private ChatApplication mChatApplication;
}
