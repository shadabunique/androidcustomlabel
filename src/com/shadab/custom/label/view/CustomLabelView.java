package com.shadab.custom.label.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shadab.custom.label.R;

/**
 * A custom label view which can be used as a contact entity
 * <p/>
 * The view contains picture, label text and remove icon
 * <p/>
 * Users of this class should implement OnLabelRemoveListener and call setOnLabelRemoveListener(..)
 * to get notified on removal of label event. 
 * <p/>
 * For more information, visit the project page:
 * https://github.com/shadabunique/androidcustomlabel
 *
 * @author Mohammad Shadab Ansari
 * @version 1.0.0.0
 */
public class CustomLabelView extends LinearLayout {

	private CustomLabelView labelView;
	private OnLabelRemoveListener onLabelRemoveListener;
	private String mLabelValue;
	
	private ImageView mIcon;
	private TextView mLabel;	
	private ImageView mRemoveIcon;

	//Invoked when label is dynamically created
	public CustomLabelView(Context context) {
		super(context);
		initLabelView(context);
	}

	//Invoked when the label is defined in xml files
	public CustomLabelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs,
		R.styleable.CustomLabelView, 0, 0);

		String labelText = a.getString(R.styleable.CustomLabelView_labelText);
		
		int labelTextColor = a.getColor(R.styleable.CustomLabelView_labelTextColor,
				R.color.dark_green);
		
		int labelBgColor = a.getColor(R.styleable.CustomLabelView_labelBackgroundColor,
				R.color.transparent);
		
		int labelBgRes = a.getResourceId(R.styleable.CustomLabelView_labelBackground,
				0);
		
		int labelIconRes = a.getResourceId(R.styleable.CustomLabelView_labelIcon,
				R.drawable.pic);
		
		a.recycle();
		
		initLabelView(context);
		
		//Set the view
		setLabelIcon(labelIconRes);
		setLabelText(labelText);
		setLabelTextColor(labelTextColor);
		
		/**
		 * If label background resource is not specified, then
		 * set the label background color and vice-versa
		 */
		if(labelBgRes == 0){
			setLabelBackgroundColor(labelBgColor);
		}
		else{
			setLabelBackgroundResource(labelBgRes);
		}
	} 

	/**
	 * Initializes the label view components
	 * @param context Context
	 */
	private void initLabelView(Context context) {
		
		labelView = this;
		
		//Set orientation and gravity
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);

		//Inflate the label view layout
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.label_view_layout, this, true);

		//Provide some margin so that the dynamically added labels have some space in between
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.setMargins(5, params.topMargin, params.rightMargin, params.bottomMargin);
		setLayoutParams(params);
		
		mIcon = (ImageView) getChildAt(0);
		mLabel = (TextView) getChildAt(1);
		mRemoveIcon = (ImageView) getChildAt(2);
		
		mRemoveIcon.setBackgroundResource(R.drawable.ic_cross);
		mRemoveIcon.setOnClickListener(new  OnClickListener() {
			@Override
			public void onClick(View v) {
				onLabelRemoveListener.onLabelRemove(labelView);
			}
		});
	}

	/**
	 * Sets the icon of the label
	 * @param iconResource Icon resource id
	 */
	public void setLabelIcon(int iconResource) {
		mIcon.setImageResource(iconResource);
	}
	
	/**
	 * Sets the icon of the label 
	 * @param imageBitmap Image bitmap
	 */
	public void setLabelIconBitmap(Bitmap imageBitmap) {
		mIcon.setImageBitmap(imageBitmap);
	}
	
	/**
	 * Sets the background color of the label
	 * @param bgColor Color resource id
	 */
	public void setLabelBackgroundColor(int bgColor) {
		this.setBackgroundColor(bgColor);
	}
	
	/**
	 * Sets the label background resource id
	 * @param bgResource Drawable resource id
	 */
	public void setLabelBackgroundResource(int bgResource) {
		this.setBackgroundResource(bgResource);
	}
	
	/**
	 * Sets the label text color
	 * @param textColor Color resource id
	 */
	public void setLabelTextColor(int textColor) {
		mLabel.setTextColor(textColor);
	}
	
	/**
	 * Sets the label text
	 * @param text label text
	 */
	public void setLabelText(String text) {
		mLabel.setText(text);
	}
	
	/**
	 * Gets the text assigned to the label
	 * @return label text
	 */
	public String getLabelText() {
		return mLabel.getText().toString();
	}
	
	/**
	 * Sets the value corresponding to the label
	 * @param value Label value
	 */
	public void setLabelValue(String value) {
		mLabelValue =  value;
	}
	
	/**
	 * Gets the value corresponding to the label
	 * @return Label value
	 */
	public String getLabelValue() {
		return mLabelValue;
	}
	
	/**
	 * Sets the listener which gets invoked when 
	 * cross icon of the label is pressed
	 * @param onLabelRemoveListener OnLabelRemoveListener listener
	 */
	public void setOnLabelRemoveListener(OnLabelRemoveListener onLabelRemoveListener){
		this.onLabelRemoveListener = onLabelRemoveListener;
	}
}