package com.oraro.genealogy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.ui.fragment.CommonwealListFragment;
import com.oraro.genealogy.ui.fragment.DecisionListFragment;
import com.oraro.genealogy.ui.fragment.PrefaceFragment;
import com.oraro.genealogy.ui.view.smartTab.utilsV4.FragmentPagerItem;
import com.oraro.genealogy.ui.view.smartTab.utilsV4.FragmentPagerItemAdapter;
import com.oraro.genealogy.ui.view.smartTab.utilsV4.FragmentPagerItems;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by Administrator on 2016/10/9.
 */
public class MainActivity extends BasicActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ViewGroup mTab;
    ViewPager mViewpager;
    //    SmartTabLayout viewPagerTab;
    FragmentPagerItems mPages;
    DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private SearchView searchView;
    private LinearLayout userLayout;

    @Override
    public Presenter createPresenter() {
        return null;
    }

    @Override
    public BasicActivity getUi() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = new Select().from(User.class).querySingle();

//        Log.i(this.getClass().getSimpleName(),"user.."+ user.getRole()+","+ user.getGenealogyName()+",isCurrent.."+user.isCurrentUser());

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
//        toolbar.setLogo(R.drawable.drawer);
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerToggle.syncState();
        mDrawer.setDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        searchView = (SearchView) mNavigationView.getHeaderView(0).findViewById(R.id.searchView);
        searchView.setOnClickListener(this);
        mNavigationView.getHeaderView(0).findViewById(R.id.drawer_user_icon).setOnClickListener(this);
        mNavigationView.getHeaderView(0).findViewById(R.id.drawer_user_layout).setOnClickListener(this);
//        mTab = (ViewGroup) findViewById(R.id.tab);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
//        tab.addView(LayoutInflater.from(this).inflate(R.layout.tab_top_layout, tab, false));
//        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        mPages = new FragmentPagerItems(this);
        Log.i("Main", "oncreate");
        mPages.add(FragmentPagerItem.of(getString(R.string.preface), PrefaceFragment.class));
        mPages.add(FragmentPagerItem.of(getString(R.string.family_decision), DecisionListFragment.class));
        mPages.add(FragmentPagerItem.of(getString(R.string.family_commonweal), CommonwealListFragment.class));
        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), mPages);
        mViewpager.setAdapter(adapter);
//        viewPagerTab.setViewPager(viewpager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchView:
                Log.i("main","click search");
                break;
            case R.id.drawer_user_icon:
                Log.i("main","click user_icon");
                break;
            case R.id.drawer_user_layout:
                startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                Log.i("main","click drawer_user_layout");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item) || mDrawerToggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawer.closeDrawers();
        switch (item.getItemId()) {
            case R.id.menu_preface:
                mViewpager.setCurrentItem(skipTo(mViewpager.getAdapter(), item));
                break;
            case R.id.menu_family_decision:
                mViewpager.setCurrentItem(skipTo(mViewpager.getAdapter(), item));
                break;
            case R.id.menu_family_commonweal:
                mViewpager.setCurrentItem(skipTo(mViewpager.getAdapter(), item));
                break;
            case R.id.menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            default:
                break;
        }
        return false;
    }

    private int skipTo(PagerAdapter adapter, MenuItem item) {
        int i = 0;
        if (adapter == null || item == null) return i;
        for (; i < adapter.getCount(); i++) {
            if (adapter.getPageTitle(i).equals(item.getTitle())) {
                break;
            }
        }
        return i;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
