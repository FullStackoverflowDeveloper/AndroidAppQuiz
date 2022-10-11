package com.example.justquiz;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Initialize variable
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize data of questions
        QuestionsBank.setMysqlQuestionsLists(QuestionsBank.getRealtimeDatabaseData("mysqlQuestions", "-N27pVyiVkzRfTJ-m78U"));
        QuestionsBank.setMariaQuestionsLists(QuestionsBank.getRealtimeDatabaseData("mariaQuestions", "-N27pCzm_RSO_FhocUpq"));
        QuestionsBank.setAuroraQuestionsLists(QuestionsBank.getRealtimeDatabaseData("auroraQuestions", "-N27pQPgI256FcJ_XI5w"));
        QuestionsBank.setOracleQuestionsLists(QuestionsBank.getRealtimeDatabaseData("oracleQuestions", "-N27pTwKZkG6AHzsRIQ8"));

        //Assign variable
        tabLayout = findViewById((R.id.tab_layout));
        viewPager = findViewById(R.id.view_pager);

        //Initialize array list
        ArrayList<String> arrayList = new ArrayList<>();

        //Add title to array list
        arrayList.add("Tests");
        arrayList.add("Scores");

        //Prepare view pager
        prepareViewPager(viewPager, arrayList);

        //Setup with view pager
        tabLayout.setupWithViewPager(viewPager);

    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList){
        //Initialize main adapter
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());

        //Initialize main and start fragments
        StartFragment fragment1 = new StartFragment();
        MainFragment fragment2 = new MainFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", arrayList.get(0));
        fragment1.setArguments(bundle);
        adapter.addFragment(fragment1, arrayList.get(0));

        Bundle bundle2 = new Bundle();
        bundle2.putString("title", arrayList.get(1));
        fragment2.setArguments(bundle2);
        adapter.addFragment(fragment2, arrayList.get(1));

        //Set adapter
        viewPager.setAdapter(adapter);
    }

    private class MainAdapter extends FragmentPagerAdapter {
        //Initialize array list
        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        //Create constructor
        public void addFragment(Fragment fragment, String title){
            //Add title
            arrayList.add(title);
            //Add fragment
            fragmentList.add(fragment);
        }

        public MainAdapter(@NonNull FragmentManager fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            //Return fragment position
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            //Return fragment list size
            return fragmentList.size();
        }

        public CharSequence getPageTitle(int position) {
            //Return array list position
            return arrayList.get(position);
        }

    }
}
