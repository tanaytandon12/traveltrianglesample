package com.traveltriangle.traveltriangleblog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.traveltriangle.traveltriangleblog.model.Item;

public class BlogFragment extends Fragment implements OnClickListener {

	private TextView timeTextView, titleTextView, descTextView, linkTextView;
	private Item currentItem;

	public void setCurrentItem(Item item) {
		this.currentItem = item;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.blog_fragment, parent, false);
		timeTextView = (TextView) view.findViewById(R.id.time);
		titleTextView = (TextView) view.findViewById(R.id.title);
		descTextView = (TextView) view.findViewById(R.id.description);
		linkTextView = (TextView) view.findViewById(R.id.link);
		
		String description = currentItem.getDescription();
		
		timeTextView.setText(currentItem.getTime());
		titleTextView.setText(currentItem.getTitle());
		descTextView.setText(description.substring(0,description.indexOf("<")));
		linkTextView.setText(currentItem.getLink());
		
		linkTextView.setOnClickListener(this);
		
		Log.d("TAG", "wtf dude");
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.link:
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(linkTextView.getText().toString()));
			getActivity().startActivity(browserIntent);
			break;

		default:
			break;
		}
	}
}
