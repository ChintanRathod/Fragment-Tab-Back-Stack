/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chintanrathod.fragmenttabbackstack;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdResultListFragment extends Fragment {

	View view;

	ListView listView;

	private ListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.result_fragment, null);

		initViews();

		return view;
	}

	private void initViews() {

		((TextView) view.findViewById(R.id.txtTitle))
				.setText("Third Result Fragment");

		listView = (ListView) view.findViewById(R.id.listView1);

		listView = (ListView) view.findViewById(R.id.listView1);

		ArrayList<String> arrList = new ArrayList<>();

		for (int i = 11; i <= 20; i++) {
			arrList.add("Third Result : " + String.valueOf(i));
		}

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, arrList);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Toast.makeText(getActivity(),
						adapter.getItem(position).toString(), Toast.LENGTH_LONG)
						.show();
			}
		});
	}
}
