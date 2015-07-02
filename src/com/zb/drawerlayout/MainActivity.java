package com.zb.drawerlayout;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<String> menuLists;
	private ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mTitleString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitleString = (String) getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			menuLists.add("赵斌" + 1);
		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuLists);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.left_menu, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("请选择");
				invalidateOptionsMenu();// Call onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(mTitleString);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// realize the function of APP icon on the ActionBar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// combine Drawer and the icon on the ActionBar
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		// Set Home_Button's icon of ActionBar as drawer icon of ActionBarDrawerToggle
		
		mDrawerToggle.syncState();
	}
	
	//Call when screen orientation changed
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		// insert a fragment into a frameLayout dynamically
		Fragment contentFragment = new ContentFragment();
		Bundle argsBundle = new Bundle();
		argsBundle.putString("text", menuLists.get(position));
		contentFragment.setArguments(argsBundle);

		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.content_frame, contentFragment)
				.commit();

		// close left drawer after replacing frameLayout
		mDrawerLayout.closeDrawer(mDrawerList);
	}
}
