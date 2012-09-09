package org.hb.bus.chat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poll);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
        mRadioGroup = (RadioGroup)findViewById(R.id.addPollRadioGroup);
        
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
                    
    	            view.setText("");
                }
                return true;
            }
        });
        
        mSubmitButton = (Button)findViewById(R.id.addPollSubmitButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Add a call to rest api for posting the poll
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
    private EditText mAddItemTextBox;
    private Button mSubmitButton;
}
