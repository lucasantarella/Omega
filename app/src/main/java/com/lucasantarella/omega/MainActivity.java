package com.lucasantarella.omega;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
//     mDrawerToggle;
    private boolean drawerOpen;
    private boolean isDrawerActive;
    public static final String TAG = MainActivity.class.getSimpleName().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
//        if (mDrawerLayout != null) {
//            isDrawerActive = true;
//            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
//                    GravityCompat.START);
//            getActionBar().setHomeButtonEnabled(true);
//            getActionBar().setDisplayHomeAsUpEnabled(true);
//            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                    null, R.drawable.ic_drawer, R.string.open_drawer,
//                    R.string.close_drawer) {
//                @Override
//                public void onDrawerOpened(View drawerView) {
//                    super.onDrawerOpened(drawerView);
//                    Log.d(TAG, "Navigation Bar Opened");
//                }
//
//                @Override
//                public void onDrawerClosed(View drawerView) {
//                    super.onDrawerClosed(drawerView);
//                    Log.d(TAG, "Navigation Bar Closed");
//                }
//            };
//            mDrawerLayout.setDrawerListener(mDrawerToggle);
//            drawerOpen = mDrawerLayout.isDrawerOpen(R.id.navDrawer);
//            isDrawerActive = true;
//        };
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MainFragment()).commit();
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;

        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.omega_fragment_name);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.omega, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsMenuItemSelected with item id of: " + item.getItemId());
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        switch (item.getItemId()) {
            case R.id.action_refresh:
                startService(new Intent(this, GetRSSFeedData.class));
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public static class SportsFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static SportsFragment newInstance(int sectionNumber) {
            SportsFragment fragment = new SportsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public SportsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.omega_fragment, container, false);
            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
