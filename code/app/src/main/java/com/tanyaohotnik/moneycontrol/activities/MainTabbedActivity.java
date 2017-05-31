package com.tanyaohotnik.moneycontrol.activities;


//import android.app.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.dao.CategoryDAO;
import com.tanyaohotnik.moneycontrol.dao.UserDAO;
import com.tanyaohotnik.moneycontrol.entities.Category;
import com.tanyaohotnik.moneycontrol.entities.OperationType;
import com.tanyaohotnik.moneycontrol.entities.User;
import com.tanyaohotnik.moneycontrol.fragments.AccountFragment;
import com.tanyaohotnik.moneycontrol.fragments.AddCostFragment;
import com.tanyaohotnik.moneycontrol.fragments.CategoryFragment;
import com.tanyaohotnik.moneycontrol.fragments.DatePickerFragment;
import com.tanyaohotnik.moneycontrol.fragments.MainFragment;
import com.tanyaohotnik.moneycontrol.fragments.ShowTutorialFragment;
import com.tanyaohotnik.moneycontrol.fragments.StatisticFragment;
import com.tanyaohotnik.moneycontrol.fragments.TutorialFragment;
import com.tanyaohotnik.moneycontrol.helpers.DateFormat;

import java.io.File;
import java.util.Date;

public class MainTabbedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int NUM_PAGES = 2;
    private static final String DIALOG_TUTORIAL = "dialogTutorial";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Fragment loadedFragment;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("com.tanyaohotnik.moneycontrol", MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        ImageView mPhotoImageView = (ImageView) headerView.findViewById(R.id.userPhotoImageView);
//        photo.setImageResource(R.drawable.ic_menu_gallery);
        TextView userName = (TextView) headerView.findViewById(R.id.userNameTextView);
        TextView userEmail = (TextView) headerView.findViewById(R.id.userEmailTextView);
        UserDAO userDAO = new UserDAO(this);
        User user = null;
        final SharedPreferences prefs = getSharedPreferences("com.tanyaohotnik.moneycontrol", Activity.MODE_PRIVATE);
            if (prefs.getString("userEmail", "").length() > 1) {
            user = userDAO.getUserByEmail(prefs.getString("userEmail", ""));
        }
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), user.getPhotoFilename() + ".jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        mPhotoImageView.setImageBitmap(bitmap);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).apply();
            FragmentManager manager = getSupportFragmentManager();
            ShowTutorialFragment dialog = ShowTutorialFragment.newInstance();
            dialog.show(manager, DIALOG_TUTORIAL);
            Category category = new Category("Медицина", R.mipmap.medicine_category, OperationType.COST);
            category.save();
            category = new Category("Кафе", R.mipmap.cafe_category, OperationType.COST);
            category.save();
            category = new Category("Питание", R.mipmap.shop_category, OperationType.COST);
            category.save();
            category = new Category("Счета", R.mipmap.bills_categoty, OperationType.COST);
            category.save();
//            category = new Category("Связь", R.mipmap., OperationType.COST);
//            category.save();
            category = new Category("Подарки", R.mipmap.difts_category, OperationType.COST);
            category.save();
            category = new Category("Животные", R.mipmap.animals_category, OperationType.COST);
            category.save();
            category = new Category("Отдых", R.mipmap.rest_category, OperationType.COST);
            category.save();
            category = new Category("Остальное", R.mipmap.differents_category, OperationType.COST);
            category.save();
            category = new Category("Остальное", R.mipmap.differents_category, OperationType.INCOME);
            category.save();
            category = new Category("Подарки", R.mipmap.difts_category, OperationType.INCOME);
            category.save();
            category = new Category("Премия", R.mipmap.prize_categorry, OperationType.INCOME);
            category.save();
            category = new Category("Аванс", R.mipmap.avans_category, OperationType.INCOME);
            category.save();
            category = new Category("Зарплата", R.mipmap.salary_category, OperationType.INCOME);
            category.save();
            User user = new User();
            user.setName("Developer");
            user.setPassword("dev");
            user.setEmail("dev@gmail.com");
            user.save();
        }

    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainFragment();
                case 1:
                    return new StatisticFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Главная";
                case 1:
                    return "Статистика";
            }
            return "error";
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        Class fragmentClass = null;
        if (id == R.id.nav_main) {
            fragmentClass = MainFragment.class;
        } else if (id == R.id.nav_account) {
            fragmentClass = AccountFragment.class;
        } else if (id == R.id.nav_categories) {
            fragmentClass = CategoryFragment.class;
        } else if (id == R.id.nav_password) {
fragmentClass = TutorialFragment.class;
        } else if (id == R.id.nav_tutorial) {
            fragmentClass = TutorialFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentClass != MainFragment.class) {

            mPager.setVisibility(ViewPager.INVISIBLE);
            tabLayout.setVisibility(TabLayout.GONE);
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
            loadedFragment = fragment;
            item.setChecked(true);

            setTitle(item.getTitle());
        } else {
            tabLayout.setVisibility(TabLayout.VISIBLE);
            mPager.setVisibility(ViewPager.VISIBLE);
            fragmentManager.beginTransaction().remove(loadedFragment).commit();
            item.setChecked(false);
            setTitle("MoneyControl");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
