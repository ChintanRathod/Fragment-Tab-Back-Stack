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

import java.util.Stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chintanrathod.fragmenttabbackstack.FirstListFragment.FirstFragmentListener;
import com.chintanrathod.fragmenttabbackstack.SecondListFragment.SecondListFragmentListener;
import com.chintanrathod.fragmenttabbackstack.ThirdListFragment.ThirdListFragmentListener;

public class MainActivity extends FragmentActivity implements
		FirstFragmentListener, SecondListFragmentListener,
		ThirdListFragmentListener {

	private Stack<Fragment> firstStack;
	private Stack<Fragment> secondStack;
	private Stack<Fragment> thirdStack;
	private FragmentManager fragmentManager;
	private FirstListFragment firstListFragment;
	private SecondListFragment secondListFragment;
	private ThirdListFragment thirdListFragment;
	private FirstResultListFragment firstResultListFragment;
	private SecondResultListFragment secondResultListFragment;
	private ThirdResultListFragment thirdResultListFragment;

	RadioButton rbFirst, rbSecond, rbThird;
	RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();

	}

	private void initViews() {

		firstStack = new Stack<Fragment>();
		secondStack = new Stack<Fragment>();
		thirdStack = new Stack<Fragment>();

		rbFirst = (RadioButton) findViewById(R.id.rbFirst);
		rbSecond = (RadioButton) findViewById(R.id.rbSecond);
		rbThird = (RadioButton) findViewById(R.id.rbThird);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

		fragmentManager = getSupportFragmentManager();

		FragmentTransaction ft = fragmentManager.beginTransaction();

		// Start default first fragment
		firstListFragment = new FirstListFragment();
		firstListFragment.registerForListener(MainActivity.this);
		setFragmentStartAnimation(ft);
		ft.add(R.id.container, firstListFragment);
		firstStack.push(firstListFragment);
		ft.commit();

		setListener();

		changeRadioButtonTextColor();
	}

	private void setListener() {
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						changeRadioButtonTextColor();

						FragmentTransaction ft = fragmentManager
								.beginTransaction();

						hideTabs(ft);
						if (checkedId == rbFirst.getId()) {
							if (firstStack.size() == 0) {
								firstListFragment = new FirstListFragment();
								firstListFragment
										.registerForListener(MainActivity.this);
								setFragmentStartAnimation(ft);
								ft.add(R.id.container, firstListFragment);
								firstStack.push(firstListFragment);
							} else {
								Fragment fragment = firstStack.lastElement();
								fragment.onResume();
								setFragmentStartAnimation(ft);
								ft.show(fragment);
							}
						} else if (checkedId == rbSecond.getId()) {
							if (secondStack.size() == 0) {
								secondListFragment = new SecondListFragment();
								secondListFragment
										.registerForListener(MainActivity.this);
								setFragmentStartAnimation(ft);
								ft.add(R.id.container, secondListFragment);
								secondStack.push(secondListFragment);
							} else {
								Fragment fragment = secondStack.lastElement();
								setFragmentStartAnimation(ft);
								fragment.onResume();
								ft.show(fragment);
							}
						} else {
							if (thirdStack.size() == 0) {
								thirdListFragment = new ThirdListFragment();
								thirdListFragment
										.registerForListener(MainActivity.this);
								setFragmentStartAnimation(ft);
								ft.add(R.id.container, thirdListFragment);
								thirdStack.push(thirdListFragment);
							} else {
								Fragment fragment = thirdStack.lastElement();
								setFragmentStartAnimation(ft);
								fragment.onResume();
								ft.show(fragment);
							}
							// log service page visit
						}
						ft.commit();
					}

					private void hideTabs(FragmentTransaction ft) {

						if (!firstStack.isEmpty()) {
							Fragment fragment = firstStack.lastElement();
							fragment.onPause();
							setFragmentStartAnimation(ft);
							ft.hide(fragment);
						}

						if (!secondStack.isEmpty()) {
							Fragment fragment = secondStack.lastElement();
							fragment.onPause();
							setFragmentStartAnimation(ft);
							ft.hide(fragment);
						}

						if (!thirdStack.isEmpty()) {
							Fragment fragment = thirdStack.lastElement();
							fragment.onPause();
							setFragmentStartAnimation(ft);
							ft.hide(fragment);
						}
					}

				});
	}

	protected void setFragmentStartAnimation(FragmentTransaction ft) {
		ft.setCustomAnimations(R.anim.fragment_side_in,
				R.anim.fragment_side_out, R.anim.fragment_side_in,
				R.anim.fragment_side_out);
	}

	private void changeRadioButtonTextColor() {
		if (rbFirst.getId() == radioGroup.getCheckedRadioButtonId()) {
			rbFirst.setTextColor(getResources().getColor(R.color.app_navy_blue));
		} else {
			rbFirst.setTextColor(getResources().getColor(
					R.color.tabs_text_color_blue));
		}

		if (rbSecond.getId() == radioGroup.getCheckedRadioButtonId()) {
			rbSecond.setTextColor(getResources()
					.getColor(R.color.app_navy_blue));
		} else {
			rbSecond.setTextColor(getResources().getColor(
					R.color.tabs_text_color_blue));
		}

		if (rbThird.getId() == radioGroup.getCheckedRadioButtonId()) {
			rbThird.setTextColor(getResources().getColor(R.color.app_navy_blue));
		} else {
			rbThird.setTextColor(getResources().getColor(
					R.color.tabs_text_color_blue));
		}

	}

	public void setFragmentFinishAnimation(FragmentTransaction ft) {
		ft.setCustomAnimations(R.anim.fragment_finish_side_in,
				R.anim.fragment_finish_side_out,
				R.anim.fragment_finish_side_in, R.anim.fragment_finish_side_out);
	}

	@Override
	public void onBackPressed() {

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		setFragmentFinishAnimation(ft);

		if (radioGroup.getCheckedRadioButtonId() == rbFirst.getId()) {
			if (firstStack.size() > 1) {

				firstStack.lastElement().onPause();
				ft.remove(firstStack.pop());
				firstStack.lastElement().onResume();
				ft.show(firstStack.lastElement());
				ft.commit();
			} else {
				super.onBackPressed();
			}
		} else if (radioGroup.getCheckedRadioButtonId() == rbSecond.getId()) {
			if (secondStack.size() > 1) {
				ft.remove(secondStack.pop());
				secondStack.lastElement().onResume();
				ft.show(secondStack.lastElement());
				ft.commit();
			} else {
				super.onBackPressed();
			}
		} else {
			if (thirdStack.size() > 1) {
				thirdStack.lastElement().onPause();
				ft.remove(thirdStack.pop());
				thirdStack.lastElement().onResume();
				ft.show(thirdStack.lastElement());
				ft.commit();
			} else {
				super.onBackPressed();
			}
		}

		System.gc();
	}

	@Override
	public void onFirstFragmentItemClicked(String valueClicked) {
		Toast.makeText(this, valueClicked, Toast.LENGTH_LONG).show();

		FragmentTransaction ft = fragmentManager.beginTransaction();

		firstResultListFragment = new FirstResultListFragment();
		ft.add(R.id.container, firstResultListFragment);
		firstStack.lastElement().onPause();
		ft.hide(firstStack.lastElement());
		firstStack.push(firstResultListFragment);
		ft.commit();
	}

	@Override
	public void onSecondFragmentItemClicked(String valueClicked) {
		Toast.makeText(this, valueClicked, Toast.LENGTH_LONG).show();

		FragmentTransaction ft = fragmentManager.beginTransaction();

		secondResultListFragment = new SecondResultListFragment();
		ft.add(R.id.container, secondResultListFragment);
		secondStack.lastElement().onPause();
		ft.hide(secondStack.lastElement());
		secondStack.push(secondResultListFragment);
		ft.commit();
	}

	@Override
	public void onThirdFragmentItemClicked(String valueClicked) {
		Toast.makeText(this, valueClicked, Toast.LENGTH_LONG).show();

		FragmentTransaction ft = fragmentManager.beginTransaction();

		thirdResultListFragment = new ThirdResultListFragment();
		ft.add(R.id.container, thirdResultListFragment);
		thirdStack.lastElement().onPause();
		ft.hide(thirdStack.lastElement());
		thirdStack.push(thirdResultListFragment);
		ft.commit();
	}

}
