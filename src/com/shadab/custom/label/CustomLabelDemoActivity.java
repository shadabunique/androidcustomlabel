package com.shadab.custom.label;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.shadab.custom.label.view.CustomLabelView;
import com.shadab.custom.label.view.OnLabelRemoveListener;

/**
 * A sample activity which demonstrates the use of androidcustomlabel view
 * It also shows how custom label can be used in conjunction with EditBox
 * <p/>
 * In similar way, it can be used  in conjunction with MultiAutoCompleteTextView
 * <p/>
 * For more information, visit the project page:
 * https://github.com/shadabunique/androidcustomlabel
 *
 * @author Mohammad Shadab Ansari
 * @version 1.0.0.0
 */
public class CustomLabelDemoActivity extends Activity implements OnClickListener,OnLabelRemoveListener {

	private LinearLayout baseLayout;
	private Button btnAdd;
	private EditText nameBox;
	private int layoutLatestWidth = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		baseLayout = (LinearLayout) findViewById(R.id.base_layout);
		btnAdd = (Button) findViewById(R.id.btn_add);
		btnAdd.setOnClickListener(this);

		nameBox = new EditText(this);
		nameBox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		baseLayout.addView(nameBox);
		
		nameBox.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable str) {
				/**
				 * When the user presses ',' or ';' then append the label created 
				 * from the last entered string to the container layout
				 */
				String enteredText = str.toString().trim();
				if(enteredText.indexOf(',') != -1 || enteredText.indexOf(';') != -1){
					
					enteredText = enteredText.substring(0, enteredText.length()-1).trim();
					if(enteredText.equals("")){
						nameBox.setText("");
						nameBox.requestFocus();
					}
					else{
						addLabel(enteredText,enteredText);
						nameBox.setText("");
						nameBox.requestFocus();
					}
				}
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
			case R.id.btn_add:
				String randomString = getRandomString();
				addLabel(randomString,randomString);
				break;
		}
	}

	/**
	 * Adds the label view
	 * @param labelText Label Text
	 * @param labelValue Label value
	 */
	private void addLabel(String labelText,String labelValue) {

		CustomLabelView label = new CustomLabelView(this);
		label.setLabelText(labelText);
		label.setLabelIcon(R.drawable.pic);
		label.setLabelTextColor(getResources().getColor(R.color.dark_green));
		label.setLabelValue(labelValue);
		//label.setViewColor(getResources().getColor(R.color.pink));
		label.setLabelBackgroundResource(R.drawable.border);
		label.setTag(labelValue);
		label.setOnLabelRemoveListener(this);

		appendLabelView(label);

	}

	@Override
	public void onLabelRemove(View labelView) {
		CustomLabelView customView;
		LinearLayout subLayout = null;
		boolean isLabelRemoved = false;

		//Remove the label view from the container layout
		for (int index = 0; index < baseLayout.getChildCount(); index++) {
			if(isLabelRemoved){
				break;
			}
			subLayout = (LinearLayout) baseLayout.getChildAt(index);

			for (int counter = 0; counter < subLayout.getChildCount(); counter++) {
				customView = (CustomLabelView) subLayout.getChildAt(counter);
				if(labelView.getTag().equals(customView.getLabelText())){
					subLayout.removeView(customView);
					isLabelRemoved = true;
					break;
				}
			}
		}
	}

	/**
	 * Appends the label to the container layout
	 * @param labelView Label view
	 */
	private void appendLabelView(CustomLabelView labelView) {

		Display display = getWindowManager().getDefaultDisplay();
		int screenWidth = display.getWidth() - 10;

		LinearLayout subLL = null;

		if (baseLayout.getChildCount() == 1) {
			subLL = new LinearLayout(this);
			subLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			subLL.setOrientation(LinearLayout.HORIZONTAL);
			baseLayout.addView(subLL);
		}
		else {
			subLL = (LinearLayout) baseLayout.getChildAt(baseLayout.getChildCount() - 2);
		}

		labelView.measure(0, 0);

		layoutLatestWidth += labelView.getMeasuredWidth();

		if (layoutLatestWidth >= screenWidth) {

			subLL = new LinearLayout(this);
			subLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			subLL.setOrientation(LinearLayout.HORIZONTAL);

			subLL.addView(labelView);
			baseLayout.addView(subLL);
			layoutLatestWidth = labelView.getMeasuredWidth();
		}
		else {
			subLL.addView(labelView);
		}
		baseLayout.removeView(nameBox);
		baseLayout.addView(nameBox);
	}
	
	/**
	 * Creates a random string for demonstration
	 * @return Random string
	 */
	private String getRandomString() {
		String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		int stringLength = rnd.nextInt(12);
		StringBuilder randomString = new StringBuilder(stringLength);
		
		for (int i = 0; i < stringLength; i++){
			randomString.append(alphabets.charAt(rnd.nextInt(alphabets.length())));
		}
		return randomString.toString();
	}
}