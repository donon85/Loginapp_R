package michalm.loginapp;



import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class TabMain extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("tab_index");
            if(value.equals("1")){
                mViewPager.setCurrentItem(1);
            }
            else if (value.equals("2")){
                mViewPager.setCurrentItem(2);
            }
            else if (value.equals("0")){
                mViewPager.setCurrentItem(0);
            }
            else
                mViewPager.setCurrentItem(0);
        }


    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    Tab2Zpracy tab2 = new Tab2Zpracy();
                    return tab2;
                case 1:
                    Tab3Zproblem tab3 = new Tab3Zproblem();
                    return tab3;
                case 2:
                    Tab4Skaner tab4 = new Tab4Skaner();
                    return tab4;
                case 3:
                    Tab5Tablica tab5 = new Tab5Tablica();
                    return tab5;
                case 4:
                    Tab2ZpracySzczegoly tab6 = new Tab2ZpracySzczegoly();
                    return tab6;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {

                case 0:
                    return "Zlecenia pracy";
                case 1:
                    return "Zgłoś problem";
                case 2:
                    return "Skaner barcode";
                case 3:
                    return "Tablica ogłoszeń";
            }
            return null;
        }


    }

}


