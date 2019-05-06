package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class tzlc_stats_display extends AppCompatActivity {
    private tzlcDataSource datasource;
    private List<Goal> goals;
    private List<Card> cards;
    private List<Highlight> highlights;
    private Stats stats;
    private List<MatchOffcial> mos;
    private List<Loan> loans;
    private long matchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_stats_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        matchID = getIntent().getLongExtra("matchID", -1);
        datasource = new tzlcDataSource(this);
        datasource.open();
        Match m = datasource.getMatch(matchID);

        //try{

        stats = datasource.getAllStatsForMatch(matchID);
        if (stats.getHome_Score() == -1) {
            Toast.makeText(tzlc_stats_display.this, "No Stats available", Toast.LENGTH_SHORT).show();
            Intent returnI = new Intent();
            returnI.putExtra("matchID", matchID);
            setResult(100, returnI);
            finish();
        } else {
            TextView homeClub = findViewById(R.id.reportHomeClub);
            homeClub.setText("" + datasource.getClub(m.getHomeClubID()).getClubName());
            TextView awayClub = findViewById(R.id.reportAwayClub);
            awayClub.setText("" + datasource.getClub(m.getAwayClubID()).getClubName());
            TextView matchtype = findViewById(R.id.reportMatchType);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(tzlc_stats_display.this, R.array.matchSubTypeShort, android.R.layout.simple_spinner_item);
            matchtype.setText("" + adapter1.getItem(m.getSubtype()));
            display_Stats(stats);

            goals = datasource.getAllGoalsForMatch(matchID);
            if (goals.size() == 0)
                Toast.makeText(tzlc_stats_display.this, "No Goals available", Toast.LENGTH_SHORT).show();
            else {
                Log.d(tzlc_stats_display.class.getSimpleName(), "Total Goals : " + goals.size());
                adaptor_goal adaptor = new adaptor_goal(tzlc_stats_display.this, R.layout.goaldisplaylist, goals);
                ListView lv = findViewById(R.id.displayGoalList);
                lv.setAdapter(adaptor);
            }

            cards = datasource.getAllCardsForMatch(matchID);
            if (cards.size() == 0)
                Toast.makeText(tzlc_stats_display.this, "No Cards available", Toast.LENGTH_SHORT).show();
            else {
                Log.d(tzlc_stats_display.class.getSimpleName(), "Total Cards : " + cards.size());
                adaptor_card adaptor = new adaptor_card(tzlc_stats_display.this, R.layout.displaylistcards, cards);
                ListView lv = findViewById(R.id.displayCardList);
                lv.setAdapter(adaptor);
            }
            highlights = datasource.getAllHighlights(matchID);
            if (highlights.size() == 0)
                Toast.makeText(tzlc_stats_display.this, "No Highlight available", Toast.LENGTH_SHORT).show();
            else {
                Log.d(tzlc_stats_display.class.getSimpleName(), "Total Highlight : " + highlights.size());
                adaptor_highlight adaptor = new adaptor_highlight(tzlc_stats_display.this, R.layout.highlightdisplaylist, highlights);
                ListView lv = findViewById(R.id.displayHighlightList);
                lv.setAdapter(adaptor);
            }

            loans = datasource.getAllLoansForMatch(matchID);
            if (loans.size() == 0)
                Toast.makeText(tzlc_stats_display.this, "No Loans  available", Toast.LENGTH_SHORT).show();
            else {
                Log.d(tzlc_stats_display.class.getSimpleName(), "Total Loans :" + loans.size());
                adaptor_loan adaptor = new adaptor_loan(tzlc_stats_display.this, R.layout.matchoffcialdisplaylist, loans);
                ListView lv = findViewById(R.id.displayLoanList);
                lv.setAdapter(adaptor);
            }

            mos = datasource.getAllMOsForMatch(matchID);
            if (mos.size() == 0)
                Toast.makeText(tzlc_stats_display.this, "No MOs available", Toast.LENGTH_SHORT).show();
            else {
                Log.d(tzlc_stats_display.class.getSimpleName(), "Total MOs :" + mos.size());
                adaptor_mo adaptor = new adaptor_mo(tzlc_stats_display.this, R.layout.matchoffcialdisplaylist, mos);
                ListView lv = findViewById(R.id.displayMatchOffcialList);
                lv.setAdapter(adaptor);
            }

        }









        /*}
        catch (Exception e){
            Log.d(tzlc_stats_display.class.getSimpleName(), "" +e );
        }*/

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void display_Stats(Stats stat) {
        //for (Stats stat : stats) {
        TextView homeClubScore = findViewById(R.id.reportHomeScore);
        homeClubScore.setText("" + stat.getHome_Score());
        double poss = 0.0;
        TextView homeClubPossession = findViewById(R.id.reportHomePossession);
        //poss = (((double) stat.getHome_TIME() / (stat.getHome_TIME() + stat.getAway_TIME())) * 100);
        homeClubPossession.setText("" + stat.getHome_TIME() );

        TextView homeClubDFK = findViewById(R.id.reportHomeDFK);
        homeClubDFK.setText("" + stat.getHome_DFK());
        TextView homeClubCOR = findViewById(R.id.reportHomeCOR);
        homeClubCOR.setText("" + stat.getHome_COR());
        TextView homeClubGK = findViewById(R.id.reportHomeGK);
        homeClubGK.setText("" + stat.getHome_GK());
        TextView homeClubSOnT = findViewById(R.id.reportHomeSOnT);
        homeClubSOnT.setText("" + stat.getHome_SOnT());
        TextView homeClubSOffT = findViewById(R.id.reportHomeSOffT);
        homeClubSOffT.setText("" + stat.getHome_SOffT());
        TextView homeClubLC = findViewById(R.id.reportHomeLC);
        homeClubLC.setText("" + stat.getHome_LC());
        TextView homeClubTCK = findViewById(R.id.reportHomeTCK);
        homeClubTCK.setText("" + stat.getHome_TCK());
        TextView homeClubTI = findViewById(R.id.reportHomeTI);
        homeClubTI.setText("" + stat.getHome_TI());
        TextView homeClubOFF = findViewById(R.id.reportHomeOFF);
        homeClubOFF.setText("" + stat.getHome_OFF());
        TextView homeClubPOP = findViewById(R.id.reportHomePOP);
        homeClubPOP.setText("" + stat.getHome_POP());


        TextView awayClubScore = findViewById(R.id.reportAwayScore);
        awayClubScore.setText("" + stat.getAway_Score());
        TextView awayClubPossession = findViewById(R.id.reportAwayPossession);
        //poss = (((double) stat.getAway_TIME() / (stat.getHome_TIME() + stat.getAway_TIME())) * 100);
        awayClubPossession.setText("" + stat.getAway_TIME());
        TextView awayClubDFK = findViewById(R.id.reportAwayDFK);
        awayClubDFK.setText("" + stat.getAway_DFK());
        TextView awayClubCOR = findViewById(R.id.reportAwayCOR);
        awayClubCOR.setText("" + stat.getAway_COR());
        TextView awayClubGK = findViewById(R.id.reportAwayGK);
        awayClubGK.setText("" + stat.getAway_GK());
        TextView awayClubSOnT = findViewById(R.id.reportAwaySOnT);
        awayClubSOnT.setText("" + stat.getAway_SOnT());
        TextView awayClubSOffT = findViewById(R.id.reportAwaySOffT);
        awayClubSOffT.setText("" + stat.getAway_SOffT());
        TextView awayClubLC = findViewById(R.id.reportAwayLC);
        awayClubLC.setText("" + stat.getAway_LC());
        TextView awayClubTCK = findViewById(R.id.reportAwayTCK);
        awayClubTCK.setText("" + stat.getAway_TCK());
        TextView awayClubTI = findViewById(R.id.reportAwayTI);
        awayClubTI.setText("" + stat.getAway_TI());
        TextView awayClubOFF = findViewById(R.id.reportAwayOFF);
        awayClubOFF.setText("" + stat.getAway_OFF());
        TextView awayClubPOP = findViewById(R.id.reportAwayPOP);
        awayClubPOP.setText("" + stat.getAway_POP());

        //}


    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent returnI = new Intent();
        returnI.putExtra("matchID", matchID);
        setResult(100, returnI);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.email) {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("*/*");

            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TZLC.txt")));//path of video
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Intent returnI = new Intent();
            returnI.putExtra("matchID", matchID);
            setResult(100, returnI);

            finish();

        } else

        {


            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TZLC.txt");

            try {
                //FileOutputStream fileOutputStream = new FileOutputStream(f, true);
                // document.writeTo(fileOutputStream);
                //fileOutputStream.close();
                FileWriter fileWriter = new FileWriter(file);
                Stats stats = datasource.getAllStatsForMatch(matchID);
                Match match = datasource.getMatch(matchID);

                fileWriter.append("\nMatch Stats for Match :\t" + datasource.getClub(match.getHomeClubID()).getClubName() + " vs " + datasource.getClub(match.getAwayClubID()).getClubName());
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(tzlc_stats_display.this, R.array.matchType, android.R.layout.simple_spinner_item);
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(tzlc_stats_display.this, R.array.matchSubTypeShort, android.R.layout.simple_spinner_item);
                fileWriter.append("\n\t" + adapter.getItem(match.getType()) + "(" + adapter1.getItem(match.getSubtype()) + ")");
                fileWriter.append("\n\n");
                fileWriter.append("\n" + datasource.getClub(match.getHomeClubID()).getClubShortName() + "\t\t\t\t\t" + datasource.getClub(match.getAwayClubID()).getClubShortName());
                //fileWriter.append("\n"++"\t\t"+"Score"+"\t\t"+);
                fileWriter.append("\n" + stats.getHome_Score() + "\t\t" + "Score" + "\t\t\t" + stats.getAway_Score());
                //(((double)stats.getHome_TIME()/(stats.getHome_TIME()+stats.getAway_TIME()))*100)
                //(((double)stats.getAway_TIME()/(stats.getHome_TIME()+stats.getAway_TIME()))*100)
                fileWriter.append("\n" + String.format("%.2f", (((double) stats.getHome_TIME() / (stats.getHome_TIME() + stats.getAway_TIME())) * 100)) + "%" + "\t\t" + "Possession" + "\t\t" + String.format("%.2f", (((double) stats.getAway_TIME() / (stats.getHome_TIME() + stats.getAway_TIME())) * 100)) + "%");
                fileWriter.append("\n\n\t\tMatch Stats");
                fileWriter.append("\n" + stats.getHome_DFK() + "\t\t" + "Free Kick" + "\t\t" + stats.getAway_DFK());
                fileWriter.append("\n" + stats.getHome_COR() + "\t\t" + "Corners" + "\t\t\t" + stats.getAway_COR());
                fileWriter.append("\n" + stats.getHome_GK() + "\t\t" + "Goal Kick" + "\t\t" + stats.getAway_GK());
                fileWriter.append("\n" + stats.getHome_SOnT() + "\t\t" + "Shot On Target" + "\t\t" + stats.getAway_SOnT());
                fileWriter.append("\n" + stats.getHome_SOffT() + "\t\t" + "Shot Off Target" + "\t\t" + stats.getAway_SOffT());
                fileWriter.append("\n" + stats.getHome_LC() + "\t\t" + "Late Challenge" + "\t\t" + stats.getAway_LC());
                fileWriter.append("\n" + stats.getHome_TCK() + "\t\t" + "Tackles" + "\t\t\t" + stats.getAway_TCK());
                fileWriter.append("\n" + stats.getHome_TI() + "\t\t" + "Throw-In" + "\t\t" + stats.getAway_TI());
                fileWriter.append("\n" + stats.getHome_OFF() + "\t\t" + "Offside" + "\t\t\t" + stats.getAway_OFF());
                fileWriter.append("\n" + stats.getHome_POP() + "\t\t" + "POP" + "\t\t\t" + stats.getAway_POP());
                fileWriter.append("\n\n----------------------------------------------------------------------------------------------------------------------------------");
                fileWriter.append("\n\n\t\tGoals");
                fileWriter.append("\n" + String.format("%15S %25s %25s %25s"
                        , "For"
                        , "Goal Scorer"
                        , "Assist By"
                        , "Time"));
                for (Goal goal : goals) {
                    fileWriter.append("\n" + String.format("%15S %25s %25s %25s"
                            , datasource.getClub(datasource.getPlayer(goal.getPlayerID()).getClubId()).getClubShortName()
                            , datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[1]
                            , datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[1]
                            , goal.getMatchTime()));


                }


                fileWriter.append("\n\n\t\tCard");

                fileWriter.append("\n" + String.format("%15S %25s %25s %25s"
                        , "|Player"
                        , "|Type"
                        , "|Time"
                        , "|Reason"));

                for (Card card : cards) {
                    String temp = "";
                    switch (card.getType()) {
                        case 0:
                            temp = "YC";
                            break;
                        case 1:
                            temp = "RC";
                            break;
                        case 2:
                            temp = "BC";
                            break;
                    }
                    fileWriter.append("\n" + String.format("%15S %25s %25s %25s"
                            , datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[1]
                            , temp
                            , String.format("%02d", (card.getTime() / 60)) + ":" + String.format("%02d", (card.getTime() % 60))
                            , card.getReason()));

                }


                fileWriter.append("\n\n\t\tMatch Offcial");
                fileWriter.append("\n" + String.format("%15S %25s %25s %25s"
                        , "Player"
                        , "Club"
                        , "Job-Profile"
                        , "On-Time"));
                ArrayAdapter<CharSequence> moJobs = ArrayAdapter.createFromResource(tzlc_stats_display.this, R.array.MOJobProfile, android.R.layout.simple_spinner_item);

                for (MatchOffcial matchOffcial : mos) {
                    fileWriter.append("\n" + String.format("%15S %25s %25s %25s"
                            , datasource.getPlayer(matchOffcial.getPlayerId()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(matchOffcial.getPlayerId()).getPlayerName().split("@")[1]
                            , datasource.getClub(datasource.getPlayer(matchOffcial.getPlayerId()).getClubId()).getClubShortName()
                            , moJobs.getItem(matchOffcial.getJob())
                            , (matchOffcial.getOnTime() == 0 ? "No" : "Yes")));

                }


                fileWriter.append("\n\n\t\tLoans");
                fileWriter.append("\n" + String.format("%15S %30s %25s"
                        , "Club"
                        , "Player"
                        , "Rule"));
                //ArrayAdapter<CharSequence> moJobs = ArrayAdapter.createFromResource(tzlc_stats_display.this,R.array.MOJobProfile,android.R.layout.simple_spinner_item);

                for (Loan loan : loans) {
                    String temp = "";
                    switch (loan.getRule()) {
                        case 0:
                            temp = "AVGKL";
                            break;
                        case 1:
                            temp = "AVPL";
                            break;
                        case 2:
                            temp = "DL";
                            break;
                        case 3:
                            temp = "GKL";
                            break;
                    }

                    fileWriter.append("\n" + String.format("%15S %30s %25s"
                            , datasource.getClub(loan.getHomeClubID()).getClubShortName()
                            , "(" + datasource.getClub(datasource.getPlayer(loan.getLoanPlayerID()).getClubId()).getClubShortName() + ")" + datasource.getPlayer(loan.getLoanPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(loan.getLoanPlayerID()).getPlayerName().split("@")[1]
                            , temp));

                }


                fileWriter.append("\n\n\t\tHighlights");
                fileWriter.append("\n" + String.format("%15S %15s %25s %25s %25s"
                        , "Match Time"
                        , "VCM Time"
                        , "Club"
                        , "Highlight-1"
                        , "Highlight-2"));


                for (Highlight highlight : highlights) {
                    fileWriter.append("\n" + String.format("%15S %15s %25s %25s %25s"
                            , String.format("%02d", (highlight.getSrTime() / 60)) + ":" + String.format("%02d", (highlight.getSrTime() % 60))
                            , String.format("%02d", (highlight.getVcmTime() / 60)) + ":" + String.format("%02d", (highlight.getVcmTime() % 60))
                            , datasource.getClub(highlight.getClubID()).getClubShortName()
                            , highlight.getHighlight()
                            , highlight.getHighlight2()));

                }


                fileWriter.flush();
                fileWriter.close();


                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            } catch (Exception e) {
                Log.d(tzlc_stats_display.class.getSimpleName(), "" + e);
            }
        }

        //Environment.getExternalStorageDirectory().getAbsolutePath() +


        /*try {
            document.writeTo(new OutputStream() {
                @Override
                public void write(int b) throws IOException {

                }
            });
        } catch (Exception e){Log.d(tzlc_stats_display.class.getSimpleName(), "" +e );}
*/
        //document.close();

        /*switch (id)
        {
            case R.id.editScreen :          break;
            case R.id.deleteScreen :        break;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
