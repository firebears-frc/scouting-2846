/*
 * Copyright  2017-2019  Douglas P Lau
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package org.firebears.scouting2846;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.SecureRandom;

/**
 * Our DB helper.
 */
public class OurDbHelper extends SQLiteOpenHelper {
	static public final int DATABASE_VERSION = 3;
	static public final String DATABASE_NAME = "Scouting.db";

	/** SQL statement to create parameter table */
	static private final String SQL_CREATE_PARAMS =
		"CREATE TABLE " + Param.TABLE_NAME + " (" +
		Param._ID + " INTEGER PRIMARY KEY autoincrement, " +
		Param.COL_NAME + " TEXT UNIQUE NOT NULL, " +
		Param.COL_VALUE + " INTEGER UNIQUE NOT NULL)";

	/** SQL statement to drop parameter table */
	static private final String SQL_DROP_PARAMS =
		"DROP TABLE IF EXISTS " + Param.TABLE_NAME;

	/** SQL statement to create team table */
	static private final String SQL_TEAM_OBS =
		"CREATE VIEW " + Team.OBS_VIEW_NAME + " AS SELECT " +
		"t." + Team._ID + ", " +
		Team.COL_KEY + ", " +
		"t." + Team.COL_TEAM_NUMBER + ", " +
		Team.COL_NICKNAME + ", " +
		Team.COL_WEBSITE + ", " +
		Team.COL_LOCALITY + ", " +
		Team.COL_REGION + ", " +
		Team.COL_COUNTRY + ", " +
		Team.COL_LOCATION + ", " +
		Team.COL_ROOKIE_YEAR + ", " +
		Team.COL_MOTTO +
		" FROM " + Team.TABLE_NAME + " t INNER JOIN " +
		ScoutingRec.REC.table_name + " o ON t." +
		Team.COL_TEAM_NUMBER + " = o." + ScoutingRec.REC.COL_TEAM_NUMBER;

	/** SQL statement to drop team table */
	static private final String SQL_DROP_TEAMS =
		"DROP TABLE IF EXISTS " + Team.TABLE_NAME;

	/** SQL statement to create team / observation view */
	static private final String SQL_CREATE_TEAMS =
		"CREATE TABLE " + Team.TABLE_NAME + " (" +
		Team._ID +		" INTEGER PRIMARY KEY autoincrement, "+
		Team.COL_KEY +		" TEXT UNIQUE NOT NULL, " +
		Team.COL_TEAM_NUMBER +	" INTEGER UNIQUE NOT NULL, " +
		Team.COL_NICKNAME +	" TEXT NOT NULL, " +
		Team.COL_WEBSITE +	" TEXT, " +
		Team.COL_LOCALITY +	" TEXT, " +
		Team.COL_REGION +	" TEXT, " +
		Team.COL_COUNTRY +	" TEXT, " +
		Team.COL_LOCATION +	" TEXT, " +
		Team.COL_ROOKIE_YEAR +	" INTEGER, " +
		Team.COL_MOTTO +	" TEXT)";

	/** Create our DB helper */
	public OurDbHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onConfigure(SQLiteDatabase db) {
		db.setForeignKeyConstraintsEnabled(true);
	}

	@Override
       	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_PARAMS);
		db.execSQL(SQL_CREATE_TEAMS);
		db.execSQL(SQL_INSERT_TEAMS_1);
		db.execSQL(SQL_INSERT_TEAMS_2);
		db.execSQL(SQL_INSERT_TEAMS_3);
		db.execSQL(SQL_INSERT_TEAMS_4);
		db.execSQL(SQL_INSERT_TEAMS_5);
		db.execSQL(SQL_INSERT_TEAMS_6);
		db.execSQL(SQL_INSERT_TEAMS_7);
		db.execSQL(SQL_INSERT_TEAMS_8);
		db.execSQL(SQL_INSERT_TEAMS_9);
		db.execSQL(SQL_INSERT_TEAMS_10);
		db.execSQL(SQL_INSERT_TEAMS_11);
		db.execSQL(SQL_INSERT_TEAMS_12);
		db.execSQL(SQL_INSERT_TEAMS_13);
		db.execSQL(SQL_INSERT_TEAMS_14);
		db.execSQL(SQL_INSERT_TEAMS_15);
		db.execSQL(ScoutingRec.REC.sqlCreate());
		db.execSQL(SQL_TEAM_OBS);
		initParams(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
		int newVersion)
	{
		db.execSQL(ScoutingRec.REC.sqlDrop());
		db.execSQL(SQL_DROP_TEAMS);
		db.execSQL(SQL_DROP_PARAMS);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion,
		int newVersion)
	{
		onUpgrade(db, oldVersion, newVersion);
	}

	private void initParams(SQLiteDatabase db) {
		int scouter = new SecureRandom().nextInt();
		db.execSQL("INSERT INTO " + Param.TABLE_NAME +" (name, value)"+
			" VALUES ('" + Param.ROW_SCOUTER + "', " + scouter +
			")");
		db.execSQL("INSERT INTO " + Param.TABLE_NAME +" (name, value)"+
			" VALUES ('" + Param.ROW_OBSERVATION + "', 0)");
	}

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS =
		"INSERT INTO " + Team.TABLE_NAME + " (" +
			Team.COL_KEY + ", " +
			Team.COL_TEAM_NUMBER + ", " +
			Team.COL_NICKNAME + ", " +
			Team.COL_WEBSITE + ", " +
			Team.COL_LOCALITY + ", " +
			Team.COL_REGION + ", " +
			Team.COL_COUNTRY + ", " +
			Team.COL_LOCATION + ", " +
			Team.COL_ROOKIE_YEAR + ", " +
			Team.COL_MOTTO + ") VALUES ";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_1 = SQL_INSERT_TEAMS +
		"('frc500',500,'Team 500/Team USA','http://www.cgateam500.org'," +
		" 'New London','CT','USA',NULL,2001,NULL)," +
		"('frc501',501,'The PowerKnights','http://powerknights.com'," +
		" 'Manchester','New Hampshire','USA',NULL,2001,'Meeting the challenge, exceeding the standard.')," +
		"('frc502',502,'Team 502',NULL," +
		" 'Rockville Centre','NY','USA',NULL,NULL,NULL)," +
		"('frc503',503,'Frog Force','http://www.frogforce503.org'," +
		" 'Novi','Michigan','USA',NULL,2001,'A Leap Ahead')," +
		"('frc504',504,'Team 504',NULL," +
		" 'Little Silver','NJ','USA',NULL,NULL,NULL)," +
		"('frc505',505,'Team 505',NULL," +
		" 'Yonkers','NY','USA',NULL,2001,NULL)," +
		"('frc506',506,'BroBots','http://www.startrobotics.vze.com'," +
		" 'South Huntington','NY','USA',NULL,2001,NULL)," +
		"('frc507',507,'Solid Orange Academy of Robotics (SOAR)','http://www.carolinaroboteers.com'," +
		" 'Greenville','SC','USA',NULL,2001,NULL)," +
		"('frc508',508,'Team 508',NULL," +
		" 'Avon','CT','USA',NULL,NULL,NULL)," +
		"('frc509',509,'Red Storm','http://www.redstorm509.com'," +
		" 'Bedford','New Hampshire','USA',NULL,2001,'Taking the World by STORM')," +
		"('frc510',510,'Hawaii Five-10',NULL," +
		" 'Highland Springs','VA','USA',NULL,2001,NULL)," +
		"('frc511',511,'The Robodox','http://therobodox.org'," +
		" 'Granada Hills','California','USA',NULL,2001,NULL)," +
		"('frc512',512,'Team 512',NULL," +
		" 'Hull','MA','USA',NULL,NULL,NULL)," +
		"('frc513',513,'Renegade Robotics','http://www.renegaderobotics.com'," +
		" 'Oklahoma City','OK','USA',NULL,2001,NULL)," +
		"('frc514',514,'Entropy','https://team514.com/'," +
		" 'Miller Place','New York','USA',NULL,2001,'Done is good')," +
		"('frc515',515,'TechnoKnights',NULL," +
		" 'Detroit','MI','USA',NULL,2001,NULL)," +
		"('frc517',517,'Team 517',NULL," +
		" 'New Providence','NJ','USA',NULL,NULL,NULL)," +
		"('frc518',518,'Blue Steel',NULL," +
		" 'Grand Rapids','MI','USA',NULL,2001,NULL)," +
		"('frc519',519,'Robo Masters','http://team519.bravehost.com'," +
		" 'Detroit','MI','USA',NULL,2001,NULL)," +
		"('frc520',520,'Team 520',NULL," +
		" 'Deer Park','TX','USA',NULL,NULL,NULL)," +
		"('frc521',521,'L33T CREW (\"leet crew\")','http://www.waterfordschools.org/whsnew/robotics_club/home.html'," +
		" 'Waterford','CT','USA',NULL,2001,NULL)," +
		"('frc522',522,'ROBO WIZARDS','http://www.robowizards.com'," +
		" 'Staten Island','New York','USA',NULL,2001,NULL)," +
		"('frc524',524,'Team 524',NULL," +
		" 'Bakersfield','CA','USA',NULL,NULL,NULL)," +
		"('frc525',525,'Swartdogs','http://www.525swartdogs.org'," +
		" 'Cedar Falls','Iowa','USA',NULL,2001,'Students Working Around Robotic Technology')," +
		"('frc527',527,'Red Dragons','http://www.team527.com/'," +
		" 'Massapequa','New York','USA',NULL,2001,'YEAH WOO WOO YEAH')," +
		"('frc528',528,'The Persuaders','http://www.northpennrobotics.org'," +
		" 'Lansdale','PA','USA',NULL,2001,NULL)," +
		"('frc529',529,'The Mansfield Hornets','http://www.mansfieldrobotics.org'," +
		" 'Mansfield','MA','USA',NULL,2001,NULL)," +
		"('frc533',533,'The PSIcotics','http://www.team533.com'," +
		" 'Lindenhurst','New York','USA',NULL,2001,'A Mind is a Terrible Thing to Lose')," +
		"('frc535',535,'G-Force',NULL," +
		" 'Markle','IN','USA',NULL,2001,NULL)," +
		"('frc536',536,'Team 536',NULL," +
		" 'San Francisco','CA','USA',NULL,NULL,NULL)," +
		"('frc537',537,'Charger Robotics','http://www.team537.org'," +
		" 'Sussex','Wisconsin','USA',NULL,2001,'Charging into the future one game at a time.')," +
		"('frc538',538,'Dragon Slayers','http://www.firstinspires.org/'," +
		" 'Arab','Alabama','USA',NULL,2001,'If at first you don''t make weight, drill, baby, drill!')," +
		"('frc539',539,'Titan Robotics','http://trinityes.org/'," +
		" 'Richmond','Virginia','USA',NULL,2001,'Go Titans')," +
		"('frc540',540,'TALON 540 ','http://www.team540.com/'," +
		" 'Henrico','Virginia','USA',NULL,2001,'\"More than a robot\"')," +
		"('frc541',541,'Surgeons of Steel','http://maxshayes.hostars.com/index.html'," +
		" 'Cleveland','OH','USA',NULL,2001,NULL)," +
		"('frc545',545,'ROBO-DAWGS','http://itrobotics.virtualave.net'," +
		" 'Levittown','New York','USA',NULL,2001,'WE''RE NOT DOING THIS BECAUSE IT''S EASY; WE''RE DOING IT BECAUSE IT''S HARD.')," +
		"('frc546',546,'Technotics','http://www.amityregion5.org/technotics'," +
		" 'Woodbridge','CT','USA',NULL,2001,NULL)," +
		"('frc547',547,'Falcon Engineering And Robotics','http://mws2609.wixsite.com/team547lchsrobotics'," +
		" 'Fayetteville','Tennessee','USA',NULL,2001,NULL)," +
		"('frc548',548,'Robostangs','http://www.robostangs.com'," +
		" 'Northville','Michigan','USA',NULL,2001,'We''re not horsing around!')," +
		"('frc549',549,'DevilDawgs','http://www.devildawgs549.com'," +
		" 'Leominster','MA','USA',NULL,2001,NULL)," +
		"('frc550',550,'NanKnights',NULL," +
		" 'Washington','NJ','USA',NULL,2001,NULL)," +
		"('frc551',551,'Team 551',NULL," +
		" 'Belvidere','NJ','USA',NULL,NULL,NULL)," +
		"('frc554',554,'The Bluegrease Crew','http://www.HHSrobots.org'," +
		" 'Ft. Thomas','Kentucky','USA',NULL,2001,NULL)," +
		"('frc555',555,'Montclair Robotics','https://www.montclairrobotics.org/'," +
		" 'Montclair','New Jersey','USA',NULL,2001,NULL)," +
		"('frc557',557,'Flaming Cardinals','http://www.teamFordFIRST.org/Team557'," +
		" 'Detroit','MI','USA',NULL,2001,NULL)," +
		"('frc558',558,'Elm City Robo Squad','http://www.elmcityrobosquad.org/'," +
		" 'New Haven','Connecticut','USA',NULL,2001,'Family, School, Robots')," +
		"('frc560',560,'Team 560',NULL," +
		" 'Detroit','MI','USA',NULL,2001,NULL)," +
		"('frc561',561,'Team 561',NULL," +
		" 'Phila','PA','USA',NULL,2001,NULL)," +
		"('frc562',562,'SPARK - Students Pursuing Applied Robotics Knowledge','http://robotics.montytech.net'," +
		" 'Fitchburg','MA','USA',NULL,2001,NULL)," +
		"('frc563',563,'Thrashers','http://www.spazbunny.com/robotic'," +
		" 'Phila','PA','USA',NULL,2001,NULL)," +
		"('frc564',564,'Longwood Robotics','http://www.longwoodrobotics.weebly.com'," +
		" 'Middle Island','New York','USA',NULL,2001,'Pride and Perserverence')," +
		"('frc565',565,'Team 565',NULL," +
		" 'Newark','DE','USA',NULL,NULL,NULL)," +
		"('frc566',566,'Hot Wired 566','http://www.geocities.com/mcsdrobotics'," +
		" 'Centereach','NY','USA',NULL,2001,NULL)," +
		"('frc568',568,'Nerds of the North','http://frc568.akfirstrobotics.org/'," +
		" 'Anchorage','Alaska','USA',NULL,2001,'adapt, migrate, or die')," +
		"('frc569',569,'Rambots','http://www.wix.com/team569/frc'," +
		" 'Westbury','New York','USA',NULL,2001,NULL)," +
		"('frc570',570,'Team Phoenix','http://www.cm4.com/catalysts'," +
		" 'Glen Cove','NY','USA',NULL,2001,NULL)," +
		"('frc571',571,'Team Paragon','http://www.team-paragon.org/'," +
		" 'Windsor','Connecticut','USA',NULL,2001,'Spirit Works')," +
		"('frc573',573,'Mech Warriors','http://frcteam573.com/'," +
		" 'Bloomfield Hills','Michigan','USA',NULL,2001,'Engineering with Attitude!')," +
		"('frc574',574,'Team 574',NULL," +
		" 'Bronx','NY','USA',NULL,NULL,NULL)," +
		"('frc575',575,'Team 575',NULL," +
		" 'North Hills','CA','USA',NULL,NULL,NULL)," +
		"('frc576',576,'Team 576',NULL," +
		" 'Los Angeles','CA','USA',NULL,2001,NULL)," +
		"('frc577',577,'Team 577',NULL," +
		" 'Tujunga','CA','USA',NULL,NULL,NULL)," +
		"('frc578',578,'R-cubed  - Red Raider Robotics','http://www.fairportrobotics.org'," +
		" 'Fairport','New York','USA',NULL,2001,'We build more than robots. We Build Careers.')," +
		"('frc579',579,'Team 579',NULL," +
		" 'Laveen','AZ','USA',NULL,NULL,NULL)," +
		"('frc580',580,'Viking Robotics','http://vikingrobotics580.com'," +
		" 'Studio City','California','USA',NULL,2001,'We''re [Au]some')," +
		"('frc581',581,'Blazing Bulldogs','http://www.team581.com'," +
		" 'San Jose','California','USA',NULL,2001,'Always have a plan B.')," +
		"('frc582',582,'The Viking Electros','http://www.educationalcentral.org/wmrh'," +
		" 'Jacksonville','FL','USA',NULL,2001,NULL)," +
		"('frc583',583,'Team 583',NULL," +
		" 'Seattle','WA','USA',NULL,NULL,NULL)," +
		"('frc585',585,'Cyber Penguins','http://firstteam585.org'," +
		" 'Tehachapi','California','USA',NULL,2001,'If you don''t itch, you''re not doing it right!')," +
		"('frc586',586,'Team 586',NULL," +
		" 'Alhambra','CA','USA',NULL,NULL,NULL)," +
		"('frc587',587,'Hedgehogs','http://www.team587.org'," +
		" 'Hillsborough','North Carolina','USA',NULL,2001,'We don''t cut corners, we file them.')," +
		"('frc588',588,'Jagtronx','http://www.aprtech.org'," +
		" 'Jacksonville','FL','USA',NULL,2001,NULL)," +
		"('frc589',589,'Falkon Robotics','http://www.cvrobots.com'," +
		" 'La Crescenta','California','USA',NULL,2001,'We''re Falkon awesome!')," +
		"('frc590',590,'Chahta Warriors','http://www.choctaw.org/robotics'," +
		" 'Choctaw','Mississippi','USA',NULL,2001,'So many dreams, so little time')," +
		"('frc591',591,'Team 591',NULL," +
		" 'Los Angeles','CA','USA',NULL,NULL,NULL)," +
		"('frc592',592,'Team 592',NULL," +
		" 'Sacramento','CA','USA',NULL,NULL,NULL)," +
		"('frc593',593,'Team 593',NULL," +
		" 'Houston','TX','USA',NULL,NULL,NULL)," +
		"('frc594',594,'Team 594',NULL," +
		" 'Flint','MI','USA',NULL,NULL,NULL)," +
		"('frc596',596,'SciClones','http://www.hopkinton.k12.ma.us/high/schoollife/robotics/index.htm'," +
		" 'Hopkinton','MA','USA',NULL,2001,NULL)," +
		"('frc597',597,'The Wolverines','http://www.team597.org'," +
		" 'Los Angeles','California','USA',NULL,2001,'\"We  Build More Than Robots, We Build Character!\"')," +
		"('frc598',598,'Team Duct Tape',NULL," +
		" 'Woodland Hills','CA','USA',NULL,2001,NULL)," +
		"('frc599',599,'Robodox','http://www.therobodox.org'," +
		" 'Granada Hills','California','USA',NULL,2001,'Engineering a Brighter Future')," +
		"('frc600',600,'RamRod Robotics','http://team600.org'," +
		" 'Lynchburg','VA','USA',NULL,2001,NULL)," +
		"('frc601',601,'BayBots','http://frcteam601.weebly.com'," +
		" 'Hampton Bays','New York','USA',NULL,2001,NULL)," +
		"('frc602',602,'MTC',NULL," +
		" 'Leesburg','VA','USA',NULL,2001,NULL)," +
		"('frc603',603,'Team 603',NULL," +
		" 'Daytona Beach','FL','USA',NULL,NULL,NULL)," +
		"('frc604',604,'Quixilver','http://604Robotics.com'," +
		" 'San Jose','California','USA',NULL,2001,'It will work - because it has to.')," +
		"('frc605',605,'Robots of Power','http://www.edenrop.org'," +
		" 'Hayward','CA','USA',NULL,2001,NULL)," +
		"('frc606',606,'Cyber Eagles','http://www.robotics-team606.com'," +
		" 'Los Angeles','California','USA',NULL,2001,NULL)," +
		"('frc607',607,'Team 607',NULL," +
		" 'Piney Woods','MS','USA',NULL,NULL,NULL)," +
		"('frc608',608,'Team 608',NULL," +
		" 'Atlanta','GA','USA',NULL,NULL,NULL)," +
		"('frc609',609,'Team 609',NULL," +
		" 'Santa Rosa','CA','USA',NULL,NULL,NULL)," +
		"('frc610',610,'Crescent Coyotes','http://www.team610.com'," +
		" 'North York','Ontario','Canada',NULL,2001,'Robots of Character from Parts of Promise')," +
		"('frc611',611,'Saxons','http://langleyrobotics.com/'," +
		" 'McLean','Virginia','USA',NULL,2001,'Competing Nationwide for Langley Pride ')," +
		"('frc612',612,'Chantilly Robotics','http://chantillyrobotics.org/'," +
		" 'Chantilly','Virginia','USA',NULL,2001,'Shaping Future Generations!')," +
		"('frc613',613,'NeoWarriors','http://www.neowarriors.net'," +
		" 'Somerset','New Jersey','USA',NULL,2001,NULL)," +
		"('frc614',614,'Night Hawks','http://Team614Robotics.org'," +
		" 'Alexandria','Virginia','USA',NULL,2001,'United We Soar')," +
		"('frc615',615,'Knights','http://www.firstinspires.org/'," +
		" 'Washington','District of Columbia','USA',NULL,2001,'In pursuit of excellence')," +
		"('frc616',616,'Southampton Indian Robotics','http://ircorp.fnsnet.net/index.htm'," +
		" 'Courtland','VA','USA',NULL,2001,NULL)," +
		"('frc617',617,'Enginerds','https://team617hshs.weebly.com/'," +
		" 'Henrico','Virginia','USA',NULL,2001,NULL)," +
		"('frc618',618,'E.A.R.T.H. Squad (Edison Achieving Real Technological Heights)',NULL," +
		" 'Philadelphia','PA','USA',NULL,2001,NULL)," +
		"('frc619',619,'Cavalier Robotics','http://www.CARobotics.org'," +
		" 'Charlottesville','Virginia','USA',NULL,2001,NULL)," +
		"('frc620',620,'Warbots','http://www.jmhsrobotics.org/'," +
		" 'Vienna','Virginia','USA',NULL,2001,'Together we soar.')," +
		"('frc621',621,'Team 621',NULL," +
		" 'Falls Church','VA','USA',NULL,NULL,NULL)," +
		"('frc622',622,'Team 622',NULL," +
		" 'Manassas','VA','USA',NULL,NULL,NULL)," +
		"('frc623',623,'Oakton Cougar Robotics','http://www.team623.org'," +
		" 'Vienna','Virginia','USA',NULL,2001,'We Believe')," +
		"('frc624',624,'CRyptonite','http://www.team624.org'," +
		" 'Katy','Texas','USA',NULL,2001,'Engineering a path to our future.')," +
		"('frc625',625,'Team 625',NULL," +
		" 'Palmer','AK','USA',NULL,NULL,NULL)," +
		"('frc627',627,'Team 627',NULL," +
		" 'Falls Church','VA','USA',NULL,NULL,NULL)," +
		"('frc628',628,'Northern RobArctic','http://www.dershin.com/first'," +
		" 'Fairbanks','AK','USA',NULL,2001,NULL)," +
		"('frc630',630,'Team 630',NULL," +
		" 'McLean','VA','USA',NULL,NULL,NULL)," +
		"('frc632',632,'Dissonance','http://www.geocities.com/jlrobotic/robotic.htm'," +
		" 'Union City','CA','USA',NULL,2001,NULL)," +
		"('frc633',633,'Team 633',NULL," +
		" 'Tacoma','WA','USA',NULL,2001,NULL)," +
		"('frc634',634,'Rage','http://www.lausd.k12.ca.us/vnhs/Tool_Academy/robotics.htm'," +
		" 'Van Nuys','CA','USA',NULL,2001,NULL)," +
		"('frc635',635,'Team 635',NULL," +
		" 'Buda','TX','USA',NULL,NULL,NULL)," +
		"('frc636',636,'Rascals','http://www.waipahuhighrobotics.com'," +
		" 'Waipahu','HI','USA',NULL,2001,NULL)," +
		"('frc637',637,'MR. T','http://www.montville.net/highschool/robotics'," +
		" 'Montville Township','NJ','USA',NULL,2001,NULL)," +
		"('frc638',638,'Operation Oxidation',NULL," +
		" 'Midlothian','VA','USA',NULL,2001,NULL)," +
		"('frc639',639,'Code Red Robotics','http://team639.org/'," +
		" 'Ithaca','New York','USA',NULL,2001,'Let''s Go Red!')," +
		"('frc640',640,'Edison Robotics','http://www.taehs.org'," +
		" 'Jamaica','New York','USA',NULL,2001,'Designed by Team 640, Engineered to Last')," +
		"('frc641',641,'Team 641',NULL," +
		" 'Sanborn','NY','USA',NULL,NULL,NULL)," +
		"('frc642',642,'BRR(Big Red Robotics)','http://phs.wood.k12.wv.us/robot3/index.html'," +
		" 'Parkersburg','WV','USA',NULL,2001,NULL)," +
		"('frc643',643,'Team 643',NULL," +
		" 'Allentown','PA','USA',NULL,NULL,NULL)," +
		"('frc644',644,'Team Mambo','http://www.angelfire.com/droid/teammambo/PRHS.htm'," +
		" 'Pearl River','LA','USA',NULL,2001,NULL)," +
		"('frc645',645,'Team 645',NULL," +
		" 'San Rafael','CA','USA',NULL,2001,NULL)," +
		"('frc646',646,'Team 646',NULL," +
		" 'West Sayville','NY','USA',NULL,NULL,NULL)," +
		"('frc647',647,'CYBERWOLVES','https://www.killeenisd.org/Schools/templates/basicWithCarousel/basicWithCarousel.cfm?navBarID=7669&cssFileName=blueMedAndWhite.css&campusCode=189'," +
		" 'Killeen','Texas','USA',NULL,2001,'Keep your eye on the prize!')," +
		"('frc648',648,'QC ELITE - Flaming Squirrels','http://www.qcelite.com/'," +
		" 'Quad Cities','Iowa','USA',NULL,2001,'Dream, Believe, Achieve')," +
		"('frc649',649,'M-SET Fish','http://saratogamset.org'," +
		" 'Saratoga','California','USA',NULL,2001,'We practice \"Gracious Profishionalism\"!')," +
		"('frc650',650,'Hella''s Angels','http://www.hella650.com'," +
		" 'Flora','IL','USA',NULL,2001,NULL)," +
		"('frc651',651,'Yo Bot','http://www.northschuylkill.net/Robotics%20folder/Robotics/index.html'," +
		" 'Mar Lin','PA','USA',NULL,2001,NULL)," +
		"('frc652',652,'Bobcats','http://wasd.iu5.org/seneca/robotics/index.html'," +
		" 'Erie','PA','USA',NULL,2001,NULL)," +
		"('frc653',653,'NOSIDE','http://www.edisonengineering.org'," +
		" 'San Antonio','Texas','USA',NULL,2001,'There is no challenge too great for our team.  The future is our hands.  ')," +
		"('frc654',654,'Mechateuthis Dux','http://www.maclayrobotics.org'," +
		" 'Tallahassee','FL','USA',NULL,2001,NULL)," +
		"('frc655',655,'Team 655',NULL," +
		" 'Brooklyn','NY','USA',NULL,NULL,NULL)," +
		"('frc658',658,'B.E.A.S.T. - Bringing Excitement and Science Together','http://www.Team658.com'," +
		" 'Wyoming','MI','USA',NULL,2001,NULL)," +
		"('frc659',659,'Team 659',NULL," +
		" 'Houston','TX','USA',NULL,NULL,NULL)," +
		"('frc660',660,'Dragons','http://www.roundrockrobotics.net/index2.htm'," +
		" 'Round Rock','TX','USA',NULL,2001,NULL)," +
		"('frc662',662,'Rocky Mountain Robotics','http://www.rockymountainrobotics.weebly.com'," +
		" 'USAF Academy','Colorado','USA',NULL,2001,'Climb As High As You Can Dream')," +
		"('frc663',663,'Robonauts','http://team663.org'," +
		" 'Whitinsville','Massachusetts','USA',NULL,2001,'Faith, Honor, Courage & Nobility')," +
		"('frc664',664,'Team 664',NULL," +
		" 'Harvard','IL','USA',NULL,NULL,NULL)," +
		"('frc665',665,'Motor Monkeys','http://www.wix.com/oakridgehs/firstteam665'," +
		" 'Orlando','FL','USA',NULL,2001,NULL)," +
		"('frc666',666,'The TorMentors',NULL," +
		" 'Hell','CT','USA',NULL,2008,NULL)," +
		"('frc667',667,'Team 667',NULL," +
		" 'Los Angeles','CA','USA',NULL,NULL,NULL)," +
		"('frc668',668,'The Apes of Wrath','http://www.apesofwrath668.org'," +
		" 'San Jose','California','USA',NULL,2001,'Inspired by Necessity, created by Genius, forged by Teamwork')," +
		"('frc670',670,'Homestead Robotics','http://homesteadrobotics.com'," +
		" 'Cupertino','California','USA',NULL,2001,'')," +
		"('frc671',671,'Team 671',NULL," +
		" 'San Francisco','CA','USA',NULL,NULL,NULL)," +
		"('frc674',674,'Team 674',NULL," +
		" 'Manchester','CA','USA',NULL,NULL,NULL)," +
		"('frc675',675,'Phantom Robitics','http://robotics.gweaver.net'," +
		" 'Rohnert Park','California','USA',NULL,2001,'Deus ex machina')," +
		"('frc676',676,'Team 676',NULL," +
		" 'El Paso','TX','USA',NULL,NULL,NULL)," +
		"('frc677',677,'Murphy''s Outlaws','http://firstteam677.wordpress.com/'," +
		" 'Columbus','Ohio','USA',NULL,2001,'Whatever can go wrong, will go wrong, Hombre.')," +
		"('frc679',679,'Team 679',NULL," +
		" 'New York','NY','USA',NULL,NULL,NULL)," +
		"('frc681',681,'Team 681',NULL," +
		" 'Chicago','IL','USA',NULL,NULL,NULL)," +
		"('frc684',684,'Team 684',NULL," +
		" 'Sylmar','CA','USA',NULL,NULL,NULL)," +
		"('frc685',685,'Team 685',NULL," +
		" 'Coconut Creek','FL','USA',NULL,2001,NULL)," +
		"('frc686',686,'Bovine Intervention','https://sites.google.com/view/firstteam686/about-the-team'," +
		" 'Walkersville','Maryland','USA',NULL,2001,NULL)," +
		"('frc687',687,'The Nerd Herd','http://www.camsrobotics.org'," +
		" 'Carson','California','USA',NULL,2001,'Be a Nerd, Join the Herd. Go 687!')," +
		"('frc688',688,'Team 688',NULL," +
		" 'Flint','MI','USA',NULL,NULL,NULL)," +
		"('frc690',690,'Team 690',NULL," +
		" 'Northampton','MA','USA',NULL,NULL,NULL)," +
		"('frc691',691,'Project 691 Robotics','http://www.team691.org'," +
		" 'Stevenson Ranch','California','USA',NULL,2001,'That''s Classified')," +
		"('frc692',692,'The Fembots','http://www.sfrobotics.com'," +
		" 'Sacramento','California','USA',NULL,2001,'We Can Do It!')," +
		"('frc694',694,'StuyPulse','http://www.stuypulse.com'," +
		" 'New York','New York','USA',NULL,2001,'Feel the Pulse!')," +
		"('frc695',695,'Bison Robotics','http://www.team695.com'," +
		" 'Beachwood','Ohio','USA',NULL,2001,NULL)," +
		"('frc696',696,'Circuit Breakers','http://www.team696.org'," +
		" 'La Crescenta','California','USA',NULL,2001,'Dream. Build. Excel.')," +
		"('frc697',697,'Panda Robotics','http://www.pandarobotics.net'," +
		" 'Los Angeles','CA','USA',NULL,2001,NULL)," +
		"('frc698',698,'Hamilton Microbots','http://hhsrobotics698.weebly.com'," +
		" 'Chandler','Arizona','USA',NULL,2001,'Keep it simple sweetie.')," +
		"('frc699',699,'Team 699',NULL," +
		" 'Chatsworth','CA','USA',NULL,NULL,NULL)," +
		"('frc700',700,'Team 700',NULL," +
		" 'Los Angeles','CA','USA',NULL,NULL,NULL)," +
		"('frc701',701,'RoboVikes','http://www.vandenrobotics.com/'," +
		" 'Fairfield','California','USA',NULL,2001,'Einstein 2016!')," +
		"('frc702',702,'Bagel Bytes','http://www.team702robotics.com'," +
		" 'Culver City','California','USA',NULL,2001,'It''s supposed to do that')," +
		"('frc703',703,'Team Phoenix','http://www.team703.com'," +
		" 'Saginaw','Michigan','USA',NULL,2001,'Ignite the Future')," +
		"('frc704',704,'Warriors','http://www.sgprobotics.org'," +
		" 'Grand Prairie','Texas','USA',NULL,2001,'Keepin'' it simple')," +
		"('frc706',706,'Cyberhawks','http://arrowheadrobotics.org/'," +
		" 'Hartland','Wisconsin','USA',NULL,2001,'Geared to Fly')," +
		"('frc708',708,'Hatters Robotics','http://www.team708.org/'," +
		" 'Horsham','Pennsylvania','USA',NULL,2001,NULL)," +
		"('frc709',709,'Femme Tech Fatale','https://agnesirwin709.wordpress.com/'," +
		" 'Bryn Mawr','Pennsylvania','USA',NULL,2001,'Girls are the future of techology')," +
		"('frc710',710,'Panther Bot','http://www.ftl.pinecrest.edu/robotics'," +
		" 'Fort Lauderdale','FL','USA',NULL,2001,NULL)," +
		"('frc711',711,'Robeson full force',NULL," +
		" 'Brooklyn','NY','USA',NULL,2001,NULL)," +
		"('frc713',713,'Steel Raiders','http://www.ricehighschool.com'," +
		" 'New York City','NY','USA',NULL,2001,NULL)," +
		"('frc714',714,'Panthera','http://www.714robotics.com'," +
		" 'Newark','New Jersey','USA',NULL,2001,'panthera invicto ')," +
		"('frc715',715,'Team 715',NULL," +
		" 'NY','USA',NULL,NULL,NULL,NULL)," +
		"('frc716',716,'Who''sCTEKS','http://www.716robotics.com'," +
		" 'Falls Village','Connecticut','USA',NULL,2001,'Be the Frog..... Don''t ever give up!')," +
		"('frc731',731,'Team 731',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc743',743,'Technobots','http://www.hscomputech.com'," +
		" 'Bronx','New York','USA',NULL,2002,NULL)," +
		"('frc744',744,'Shark Attack','http://www.sharkattack744.com'," +
		" 'Fort Lauderdale','Florida','USA',NULL,2002,'Excellence in all things and all things to God''s glory')," +
		"('frc746',746,'Gearheads','http://www.firstinspires.org/'," +
		" 'North York','Ontario','Canada',NULL,2002,NULL)," +
		"('frc747',747,'Flight Crew','http://www.frc747.com'," +
		" 'Middlesex','New Jersey','USA',NULL,2002,NULL)," +
		"('frc748',748,'Kingsville CavalGears',NULL," +
		" 'Kingsville','Ontario','Canada',NULL,2002,NULL)," +
		"('frc750',750,'Team 750',NULL," +
		" 'Picayune','MS','USA',NULL,2002,NULL)," +
		"('frc751',751,'barn2robotics','http://team751.org/'," +
		" 'Portola Valley','California','USA',NULL,2002,'')," +
		"('frc752',752,'The Chargers','http://www.shsrobotix.com/website/home.php'," +
		" 'Newark','New Jersey','USA',NULL,2002,NULL)," +
		"('frc753',753,'High Desert Droids','http://www.team753.org/'," +
		" 'Bend','Oregon','USA',NULL,2002,'Waffle Bacon Waffle Bacon Seven Fifty Three')," +
		"('frc754',754,'FROSTBYTE','http://frostbyte754.com'," +
		" 'Marinette','WI','USA',NULL,2002,NULL)," +
		"('frc755',755,'Team 755',NULL," +
		" 'Washington','KS','USA',NULL,2002,NULL)," +
		"('frc758',758,'South-Kent Youth Robotics','http://www.skyrobotics.ca'," +
		" 'Blenheim','ON','Canada',NULL,2002,NULL)," +
		"('frc759',759,'Systemetric','http://www.systemetric.org'," +
		" 'Cambridge','ENG','Kingdom',NULL,2002,NULL)," +
		"('frc760',760,'Team 760',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc762',762,'Patriots','http://www.lbhsrobotics.com'," +
		" 'Altamonte Springs','FL','USA',NULL,2002,NULL)," +
		"('frc764',764,'Team 764','http://meade.k12.sd.us/robotics/home.html'," +
		" 'Sturgis','SD','USA',NULL,2002,NULL)," +
		"('frc765',765,'Cobblers',NULL," +
		" 'Rapid City','SD','USA',NULL,2002,NULL)," +
		"('frc766',766,'M-A Bears','http://www.team766.com'," +
		" 'Atherton','California','USA',NULL,2002,'From Concept to Reality')," +
		"('frc768',768,'TechnoWarriors',NULL," +
		" 'Baltimore','MD','USA',NULL,2002,NULL)," +
		"('frc769',769,'Eaglebotics','http://www.landstownhs.vbschools.com/robotics2/index.html'," +
		" 'Virginia Beach','VA','USA',NULL,2002,NULL)," +
		"('frc770',770,'Team 770',NULL," +
		" 'Rapid City','SD','USA',NULL,2002,NULL)," +
		"('frc771',771,'SWAT','http://www.swat771.com'," +
		" 'Oakville','Ontario','Canada',NULL,2002,'St. Mildred''s Women Advancing Technology')," +
		"('frc772',772,'Sabre Bytes Robotics ','http://www.sabrerobotics.com'," +
		" 'LaSalle','Ontario','Canada',NULL,2002,'Growing our Community and Globally in STEMB and Robotics')," +
		"('frc773',773,'Kingsville CavalGears','http://www.cavalgears773.ca'," +
		" 'Kingsville','Ontario','Canada',NULL,2002,NULL)," +
		"('frc776',776,'SATech Saints','http://www.mnsi.net/~stanne'," +
		" 'Tecumseh','ON','Canada',NULL,2002,NULL)," +
		"('frc779',779,'Team 779',NULL," +
		" 'Cuyahoga Heights','OH','USA',NULL,NULL,NULL)," +
		"('frc780',780,'TigerBots',NULL," +
		" 'Bayamon','PR','USA',NULL,2002,NULL)," +
		"('frc781',781,'Kinetic Knights','http://www.team781.com/'," +
		" 'Kincardine','Ontario','Canada',NULL,2002,'Setting Your Potential In Motion!')," +
		"('frc782',782,'Killawatz','http://first.watkinson.org'," +
		" 'Hartford','CT','USA',NULL,2002,NULL)," +
		"('frc783',783,'Mobotics','http://www.mobotics.ca'," +
		" 'Toronto','ON','Canada',NULL,2002,NULL)," +
		"('frc800',800,'Team 800',NULL," +
		" 'Redmond','OR','USA',NULL,2002,NULL)," +
		"('frc801',801,'Horsepower','http://www.team801.com'," +
		" 'Merritt Island','Florida','USA',NULL,2002,' \"Here Comes the Stampede\"')," +
		"('frc802',802,'Team 802',NULL," +
		" 'Sudbury','MA','USA',NULL,NULL,NULL)," +
		"('frc803',803,'Lowcountry Castaways',NULL," +
		" 'Berkeley County','SC','USA',NULL,2002,NULL)," +
		"('frc804',804,'MetalMorphosis/Mad Scientists','http://www.rock-hill.k12.sc.us/Robotics'," +
		" 'Rock Hill','SC','USA',NULL,2002,NULL)," +
		"('frc805',805,'Igniters','http://www.angelfire.com/emo/SSHSrobotics'," +
		" 'Salem','OR','USA',NULL,2002,NULL)," +
		"('frc806',806,'The Brooklyn Blacksmiths','http://xaverian.org/robotics'," +
		" 'Brooklyn','New York','USA',NULL,2002,'The anvil fears no blows.')," +
		"('frc807',807,'Monster Mechanics','http://www.first-team807.org'," +
		" 'Huntsville','AL','USA',NULL,2002,NULL)," +
		"('frc808',808,'Mechanical Mayhem','http://www.aviators.stark.k12.oh.us/ahs/Robotics/Home.htm '," +
		" 'Alliance','OH','USA',NULL,2002,NULL)," +
		"('frc809',809,'The TechnoWizards','http://www.team809.org'," +
		" 'Manchester','CT','USA',NULL,2002,NULL)," +
		"('frc810',810,'The Mechanical Bulls','http://www.smithtownrobotics.com'," +
		" 'Smithtown','New York','USA',NULL,2002,'Reflecting on the past, working hard in the present, and building for the future')," +
		"('frc811',811,'Cardinals','http://www.team811.com'," +
		" 'Nashua','New Hampshire','USA',NULL,2002,'\"It''s NOT just a robot thing!\"')," +
		"('frc812',812,'The Midnight Mechanics','http://www.themidnightmechanics.com'," +
		" 'La Jolla','California','USA',NULL,2002,'Don''t Panic, I''m a Midnight Mechanic!')," +
		"('frc814',814,'Megaforce',NULL," +
		" 'Napa','CA','USA',NULL,2002,NULL)," +
		"('frc815',815,'Advanced Power','http://www.team815advancedpower.org'," +
		" 'Allen Park','Michigan','USA',NULL,2002,'Building More Than Robots')," +
		"('frc816',816,'Anomaly','http://www.bcit.cc/Domain/202'," +
		" 'Mount Holly','New Jersey','USA',NULL,2002,NULL)," +
		"('frc818',818,'Steel Armadillos','http://www.thesteelarmadillosteam818frc.org/'," +
		" 'Warren','Michigan','USA',NULL,2002,'Roll With The Armadillos')," +
		"('frc820',820,'Team DeltaTech','http://DeltaTech.ca'," +
		" 'Toronto','ON','Canada',NULL,2002,NULL)," +
		"('frc822',822,'Team 822',NULL," +
		" 'Detroit','MI','USA',NULL,2002,NULL)," +
		"('frc824',824,'SWAT Robotics','http://www.swatrobotics.org'," +
		" 'Seattle','WA','USA',NULL,2002,NULL)," +
		"('frc825',825,'SWiVaGus Maximus','http://www.team825.com'," +
		" 'Dublin','VA','USA',NULL,2002,NULL)," +
		"('frc827',827,'Robotics Eagle','http://www.csdf.k12.ca.us/Robotics05'," +
		" 'Fremont','CA','USA',NULL,2002,NULL)," +
		"('frc829',829,'The Digital Goats','http://www.warrenrobotics.org/'," +
		" 'Indianapolis','Indiana','USA',NULL,2002,NULL)," +
		"('frc830',830,'The RatPack','http://ratpackrobotics.com'," +
		" 'Ann Arbor','Michigan','USA',NULL,2002,NULL)," +
		"('frc831',831,'Ka Mikini Hui O Mililani','http://www.mililanirobotics.org'," +
		" 'Mililani','HI','USA',NULL,2002,NULL)," +
		"('frc832',832,'Oscar','http://www.roswellrobotics.com'," +
		" 'Roswell','Georgia','USA',NULL,2002,'Outstanding Students Creating Awesome Robots')," +
		"('frc833',833,'HPA Robotics','http://www.hpa.edu'," +
		" 'Kamuela','HI','USA',NULL,2002,NULL)," +
		"('frc834',834,'SparTechs','http://team834.org'," +
		" 'Center Valley','Pennsylvania','USA',NULL,2002,'Put a wrench in it')," +
		"('frc835',835,'The Sting','http://bluegold.dcds.edu/Upper%20School/US%20Club%20pages/Robotics.htm'," +
		" 'Beverly Hills','Michigan','USA',NULL,2002,'mens sana in fabricato sano')," +
		"('frc836',836,'The RoboBees','http://www.robobees.org/'," +
		" 'Hollywood','Maryland','USA',NULL,2002,'Conceive, Believe, Achieve; Desire to Succeed.')," +
		"('frc837',837,'Webbinators','http://users.gloryroad.net/~webbinators'," +
		" 'Oxford','NC','USA',NULL,2002,NULL)," +
		"('frc839',839,'Rosie Robotics','http://rosie.agawamrobotics.org/'," +
		" 'Agawam','Massachusetts','USA',NULL,2002,'\"No drama, just results\"')," +
		"('frc840',840,'Aragon Robotics Team','http://www.aragonrobotics.org'," +
		" 'San Mateo','California','USA',NULL,2002,'No Bleeding on Machines!')," +
		"('frc841',841,'The BioMechs','http://www.team841.com'," +
		" 'Richmond','California','USA',NULL,2002,'Changing the world one robot at a time.')," +
		"('frc842',842,'Falcon Robotics','http://coachfredi.wix.com/falconrobotics'," +
		" 'Phoenix','Arizona','USA',NULL,2002,'There Is No Spoon')," +
		"('frc843',843,'Wildcats','http://wos.hdsb.ca/robotics2004/index.html'," +
		" 'Oakville','ON','Canada',NULL,2002,NULL)," +
		"('frc845',845,'Cutting Edge','http://www.anderson4.k12.sc.us/schools/phs/robotics/index.htm'," +
		" 'Pendleton','SC','USA',NULL,2002,NULL)," +
		"('frc846',846,'The Funky Monkeys','http://www.lynbrookrobotics.com/'," +
		" 'San Jose','California','USA',NULL,2002,'Build. Learn. Inspire.')," +
		"('frc847',847,'PHRED','http://www.team847.com'," +
		" 'Philomath','Oregon','USA',NULL,2002,'Partnerships Under Construction')," +
		"('frc848',848,'HuskyBots','http://www.huskyrobotics.org'," +
		" 'San Pedro','California','USA',NULL,2002,'Teamwork makes the robot work')," +
		"('frc849',849,'Wolfpack','http://www.uhsrobotics.ca'," +
		" 'Unionville','ON','Canada',NULL,2002,NULL)," +
		"('frc850',850,'Team 850',NULL," +
		" 'La Canada','CA','USA',NULL,NULL,NULL)," +
		"('frc851',851,'Froggers','http://www.JTAcademy.org'," +
		" 'Torrance','CA','USA',NULL,2002,NULL)," +
		"('frc852',852,'The Athenian Robotics Collective','http://www.athenianrobotics.org'," +
		" 'Danville','California','USA',NULL,2002,'What can I do to help?')," +
		"('frc853',853,'Team 853',NULL," +
		" 'Phoenix','AZ','USA',NULL,NULL,NULL)," +
		"('frc854',854,'Iron Bears','http://www.team854.com'," +
		" 'Etobicoke','Ontario','Canada',NULL,2002,NULL)," +
		"('frc857',857,'Superior Roboworks','http://www.first857.org'," +
		" 'Houghton','Michigan','USA',NULL,2002,'Spreading FIRST to the Ends of the Earth')," +
		"('frc858',858,'Demons','http://www.Demonsrobotics.com'," +
		" 'Wyoming','Michigan','USA',NULL,2002,'Team work will get it done')," +
		"('frc859',859,'BOING','http://mhsrobotics.org'," +
		" 'Morgantown','WV','USA',NULL,2002,NULL)," +
		"('frc861',861,'Gondobots','http://www.venicerobotics.tk'," +
		" 'Los Angeles','CA','USA',NULL,2002,NULL)," +
		"('frc862',862,'Lightning Robotics','http://www.lightningrobotics.com/'," +
		" 'Canton','Michigan','USA',NULL,2002,'STRIKE : Student leadership Teamwork Resilience Inspiration Knowledge Engagement')," +
		"('frc863',863,'Team 863',NULL," +
		" 'Toronto','ON','Canada',NULL,NULL,NULL)," +
		"('frc865',865,'WARP7','https://warp7.ca'," +
		" 'Toronto','Ontario','Canada',NULL,2002,NULL)," +
		"('frc866',866,'The SmartTeens of the Turabo Valley','http://www.turabohurricanes.com'," +
		" 'Caguas','PR','USA',NULL,2002,NULL)," +
		"('frc867',867,'Absolute Value','http://absolutevalue867.com/'," +
		" 'Arcadia','California','USA',NULL,2002,'Taking robotics to the Absolute Limit !')," +
		"('frc868',868,'TechHOUNDS','http://www.techhounds.com'," +
		" 'Carmel','Indiana','USA',NULL,2002,'Invent the Future')," +
		"('frc869',869,'PowerCord','http://www.frc869.com'," +
		" 'Middlesex','New Jersey','USA',NULL,2002,'We got the power!')," +
		"('frc870',870,'TEAM  R. I. C. E.  ','http://www.rice870.com'," +
		" 'Southold','New York','USA',NULL,2002,NULL)," +
		"('frc871',871,'Robotechs','http://www.westisliprobotics.com/'," +
		" 'West Islip','New York','USA',NULL,2002,'Left over parts are proof you made it better')," +
		"('frc872',872,'Techno-Devils','http://www.techno-devils.50megs.com'," +
		" 'Columbus','OH','USA',NULL,2002,NULL)," +
		"('frc873',873,'Team 873',NULL," +
		" 'Dickinson','ND','USA',NULL,NULL,NULL)," +
		"('frc874',874,'Isis','http://www.alexander.k12.nd.us'," +
		" 'Alexander','ND','USA',NULL,2002,NULL)," +
		"('frc875',875,'Team FrostByte','http://www.hatton.k12.nd.us/robotpage.htm'," +
		" 'Hatton','ND','USA',NULL,2002,NULL)," +
		"('frc876',876,'Thunder Robotics','https://sites.google.com/a/northwoodk12.com/thunder-robotic/home'," +
		" 'Hatton-Northwood','North Dakota','USA',NULL,2002,'\"Opportunities don''t happen. You create them.\"')," +
		"('frc877',877,'North Star','http://blogs.edutech.nodak.edu/frcteam877/'," +
		" 'Cando','North Dakota','USA',NULL,2002,'\"We Can Do It\"')," +
		"('frc878',878,'Metal Gear','http://www.hs.rugby.k12.nd.us/team878/index.htm'," +
		" 'Rugby','ND','USA',NULL,2002,NULL)," +
		"('frc879',879,'Team 879',NULL," +
		" 'New Town','ND','USA',NULL,2002,NULL)," +
		"('frc880',880,'Border Patrol','http://www.fairmount.k12.nd.us'," +
		" 'Fairmount','ND','USA',NULL,2002,NULL)," +
		"('frc881',881,'Team 881',NULL," +
		" 'Bismarck','ND','USA',NULL,NULL,NULL)," +
		"('frc883',883,'Ironmen','http://centralcatholichs.org'," +
		" 'Cleveland','OH','USA',NULL,2002,NULL)," +
		"('frc884',884,'Mechanical Mules','http://www.malverne.k12.ny.us/180420921145138820/site/default.asp'," +
		" 'Malverne','New York','USA',NULL,2002,'Every accomplishment starts with the decision to try')," +
		"('frc885',885,'GREEN TEAM','http://frcteam885.com/'," +
		" 'Randolph Center','Vermont','USA',NULL,2002,'You can''t get there from here')," +
		"('frc886',886,'Wildcats','http://westview.tdsb.on.ca'," +
		" 'North York','Ontario','Canada',NULL,2002,'Deficio Est Non An Bene')," +
		"('frc887',887,'Team 887',NULL," +
		" 'Goffstown','NH','USA',NULL,NULL,NULL)," +
		"('frc888',888,'Robotiators','http://www.robotiators888.org'," +
		" 'Glenelg','Maryland','USA',NULL,2002,NULL)," +
		"('frc889',889,'Team 889',NULL," +
		" 'West Islip','NY','USA',NULL,NULL,NULL)," +
		"('frc891',891,'Neverending Chaos','http://www.neverendingchaos.com'," +
		" 'Syracuse','NY','USA',NULL,2002,NULL)," +
		"('frc892',892,'Team ASCII','http://www.geocities.com/team892ascii'," +
		" 'Canal Winchester','OH','USA',NULL,2002,NULL)," +
		"('frc894',894,'The POWER CHARGERS','http://'," +
		" 'Flint','Michigan','USA',NULL,2002,' ')," +
		"('frc896',896,'Blue Steel','http://centralrobotics.tk/'," +
		" 'Newark','New Jersey','USA',NULL,2002,'Pick Up, Stand Up, Own Up. Respect the Blue Steel. ')," +
		"('frc897',897,'Team 897',NULL," +
		" 'Toronto','ON','Canada',NULL,NULL,NULL)," +
		"('frc898',898,'Team Desert Storm','http://clubweb.peoriaud.k12.az.us/Ironwood_Robotics'," +
		" 'Glendale','AZ','USA',NULL,2002,NULL)," +
		"('frc899',899,'Team 899',NULL," +
		" 'Houston','TX','USA',NULL,NULL,NULL)," +
		"('frc900',900,'The Zebracorns','http://team900.org/'," +
		" 'Durham','North Carolina','USA',NULL,2002,NULL)," +
		"('frc901',901,'Team 901',NULL," +
		" 'Miami','FL','USA',NULL,NULL,NULL)," +
		"('frc902',902,'Delphi Heritage RoboHawks','http://www.stcs.org/heritage/robotics'," +
		" 'Saginaw','MI','USA',NULL,2002,NULL)," +
		"('frc903',903,'RoboTroopers','http://www.geocities.com/melissa_fayall/chadsey_index.html'," +
		" 'Detroit','MI','USA',NULL,2002,NULL)," +
		"('frc904',904,'D Cubed','http://www.firstteam904.weebly.com'," +
		" 'Grand Rapids','Michigan','USA',NULL,2002,'Desire times dedication times determination equals success')," +
		"('frc905',905,'Platt Tech Panthers',NULL," +
		" 'Milford','CT','USA',NULL,2002,NULL)," +
		"('frc906',906,'UMR','http://www.umr.edu/~robot'," +
		" 'Rolla','MO','USA',NULL,2002,NULL)," +
		"('frc907',907,'East York Cybernetics \"The Cybernauts\"','http://team907.ca/'," +
		" 'East York','Ontario','Canada',NULL,2002,'skill without knowledge is nothing')," +
		"('frc908',908,'Botz of Prey',NULL," +
		" 'North Charleston','SC','USA',NULL,2002,NULL)," +
		"('frc909',909,'Junkyard Crew','http://www.team909.com'," +
		" 'Lawrence','KS','USA',NULL,2002,NULL)," +
		"('frc910',910,'The Foley Freeze','http://www.foleyfreeze.com/'," +
		" 'Madison Heights','Michigan','USA',NULL,2002,'Spreading the Inspiration, Creativity and Enthusiasm')," +
		"('frc912',912,'Iron Lyons','http://ironlyons.com'," +
		" 'Toronto','ON','Canada',NULL,2002,NULL)," +
		"('frc913',913,'Bendale Robotics',NULL," +
		" 'Toronto','ON','Canada',NULL,2002,NULL)," +
		"('frc914',914,'Team 914',NULL," +
		" 'Toronto','ON','Canada',NULL,2002,NULL)," +
		"('frc915',915,'Team 915',NULL," +
		" 'Toronto','ON','Canada',NULL,NULL,NULL)," +
		"('frc917',917,'Team 917',NULL," +
		" 'Toronto','ON','Canada',NULL,2002,NULL)," +
		"('frc918',918,'Team 918',NULL," +
		" 'Toronto','ON','Canada',NULL,NULL,NULL)," +
		"('frc919',919,'Tiger Techs','http://www.harbordci.ca'," +
		" 'Toronto','Ontario','Canada',NULL,2002,'Virtus Et Doctrina')," +
		"('frc920',920,'Team 920',NULL," +
		" 'Philadelphia','PA','USA',NULL,NULL,NULL)," +
		"('frc922',922,'ULTIMATE: United Longhorn Team Inspiring Mental Attitude Towards Engineering','http://ultimaterobotics.org/FRC'," +
		" 'Laredo','TX','USA',NULL,2002,NULL)," +
		"('frc925',925,'Team 925',NULL," +
		" 'China Grove','NC','USA',NULL,NULL,NULL)," +
		"('frc926',926,'The Capacitors','http://www.freewebs.com/bhsfirst'," +
		" 'Raleigh','NC','USA',NULL,2002,NULL)," +
		"('frc927',927,'Team 927',NULL," +
		" 'Reston','VA','USA',NULL,NULL,NULL)," +
		"('frc928',928,'Hounds of Steel','http://www.benjaminbanneker.org'," +
		" 'Washington','DC','USA',NULL,2002,NULL)," +
		"('frc930',930,'Mukwonago BEARs','http://team930.com/'," +
		" 'Mukwonago','Wisconsin','USA',NULL,2002,'Make it work')," +
		"('frc931',931,'Perpetual Chaos','http://www.slps.org/domain/7813'," +
		" 'Saint Louis','Missouri','USA',NULL,2002,'Explore Robotics')," +
		"('frc932',932,'The Circuit Chargers','http://firsteam932.org/'," +
		" 'Tulsa','Oklahoma','USA',NULL,2002,'Inspiring Today, Engineering Tomorrow')," +
		"('frc933',933,'Trigos','http://www.trigos.org'," +
		" 'Rockford','IL','USA',NULL,2002,NULL)," +
		"('frc934',934,'Team 934',NULL," +
		" 'Lawrence','KS','USA',NULL,NULL,NULL)," +
		"('frc935',935,'RaileRobotics','http://team935.net'," +
		" 'Newton','Kansas','USA',NULL,2002,'Inspiration of today technology of tommorow')," +
		"('frc936',936,'Big Dogs','http://osborne-robotics.homeip.net'," +
		" 'Osborne','KS','USA',NULL,2002,NULL)," +
		"('frc937',937,'Robo Tribe','http://smnorthrobotics.com'," +
		" 'Mission','Kansas','USA',NULL,2002,'Dream, Build, Succeed!')," +
		"('frc938',938,'Code Gold','http://www.teambotman.org/'," +
		" 'Richmond','Kansas','USA',NULL,2002,'Saving the world one mind at a time.')," +
		"('frc939',939,'Hiphopanonymous',NULL," +
		" 'Sisseton','SD','USA',NULL,2002,NULL)," +
		"('frc940',940,'Digital Demolition','http://js046.k12.sd.us/Brookings%20High%20School.htm'," +
		" 'Brookings','SD','USA',NULL,2002,NULL)," +
		"('frc941',941,'Arrow Robotics','http://arrowrobotics.homestead.com'," +
		" 'Watertown','SD','USA',NULL,2002,NULL)," +
		"('frc942',942,'Roboteers','http://jv030.k12.sd.us'," +
		" 'Woonsocket','SD','USA',NULL,2002,NULL)," +
		"('frc943',943,'Great Balls of Fire',NULL," +
		" 'Volga','SD','USA',NULL,2002,NULL)," +
		"('frc945',945,'Team Banana','http://teacherpress.ocps.net/cynthiaandrews/?page_id=219'," +
		" 'Orlando','Florida','USA',NULL,2002,'We A Peel to All')," +
		"('frc946',946,'Team 946',NULL," +
		" 'Coral Springs','FL','USA',NULL,NULL,NULL)," +
		"('frc947',947,'Team 947',NULL," +
		" 'Daytona Beach','FL','USA',NULL,NULL,NULL)," +
		"('frc948',948,'NRG (Newport Robotics Group)','http://www.nrg948.com'," +
		" 'Bellevue','Washington','USA',NULL,2002,'Team eNeRGy: We have awesome potential')," +
		"('frc949',949,'Wolverine Robotics','http://wolverinerobotics949.com/'," +
		" 'Bellevue','Washington','USA',NULL,2002,'It if doesn''t work, hit it harder.')," +
		"('frc950',950,'Team 950',NULL," +
		" 'Reno','NV','USA',NULL,2002,NULL)," +
		"('frc953',953,'Team Illuminati','http://www.rhsrobotics.4mg.com'," +
		" 'Reno','NV','USA',NULL,2002,NULL)," +
		"('frc954',954,'Scorpion Robotics','http://www.sageridge.org/robotics/index.htm'," +
		" 'Reno','NV','USA',NULL,2002,NULL)," +
		"('frc955',955,'CV Robotics','http://www.cv955.com'," +
		" 'Corvallis','Oregon','USA',NULL,2002,'Design to compete; compete to win')," +
		"('frc956',956,'Eagle Cybertechnology','http://www.santiamchristian.org/home/Activities/Robotics'," +
		" 'Adair Village','OR','USA',NULL,2002,NULL)," +
		"('frc957',957,'SWARM','http://www.team957.com'," +
		" 'Albany','Oregon','USA',NULL,2002,'South West Albany Robotic Maniacs')," +
		"('frc959',959,'Team 959','http://phs.mat-su.k12.ak.us'," +
		" 'Palmer','AK','USA',NULL,2002,NULL)," +
		"('frc960',960,'Team 960',NULL," +
		" 'Anchorage','AK','USA',NULL,NULL,NULL)," +
		"('frc961',961,'Buckaroo Brutes','http://www.humboldt.k12.nv.us/lhs'," +
		" 'Winnemucca','NV','USA',NULL,2002,NULL)," +
		"('frc962',962,'Excidium','http://robotics.silverviewrvresort.com'," +
		" 'Bullhead City','AZ','USA',NULL,2002,NULL)," +
		"('frc963',963,'South Bulldogs Robotics','http://southbulldogsrobotics.org'," +
		" 'Columbus','OH','USA',NULL,2002,NULL)," +
		"('frc964',964,'Bearcat Robotics','http://www.bedfordfoundation.net/Robotics'," +
		" 'Bedford','OH','USA',NULL,2002,NULL)," +
		"('frc966',966,'Heights Tigers','http://tech.chhs.chuh.org/tech.html'," +
		" 'Cleveland Heights','OH','USA',NULL,2002,NULL)," +
		"('frc967',967,'Iron Lions','http://www.lmrobotics.com'," +
		" 'Marion','Iowa','USA',NULL,2002,NULL)," +
		"('frc968',968,'RAWC (Robotics Alliance Of West Covina)','http://team968.com'," +
		" 'West Covina','California','USA',NULL,2002,NULL)," +
		"('frc969',969,'Team 969',NULL," +
		" 'Lebanon','TN','USA',NULL,NULL,NULL)," +
		"('frc970',970,'The Cardinals','http://www.clevelandtechworks.org'," +
		" 'East Cleveland','OH','USA',NULL,2002,NULL)," +
		"('frc971',971,'Spartan Robotics','http://frc971.org'," +
		" 'Mountain View','California','USA',NULL,2002,'\"If you wanna build robots, you''ve got to break a few small appliances\"')," +
		"('frc972',972,'Iron Claw','http://www.ironclaw972.org'," +
		" 'Los Gatos','California','USA',NULL,2002,NULL)," +
		"('frc973',973,'Greybots','http://www.greybots.com'," +
		" 'Atascadero','California','USA',NULL,2002,'DESIGN,BUILD,COMPETE')," +
		"('frc974',974,'Nautae','http://lighthouse.mhs-la.org/cvivo/Robotics/HOMEPAGE.html'," +
		" 'Los Angeles','CA','USA',NULL,2002,'\"Can''t sink this ship!\"')," +
		"('frc975',975,'Synergy Robotics','http://couillard.wikispaces.com/SYNERGY'," +
		" 'Midlothian','VA','USA',NULL,2002,NULL)," +
		"('frc976',976,'Circuit Breakers','http://nottowaynt.k12.nottoway.state.va.us/nhs/robot/default.htm'," +
		" 'Nottoway','VA','USA',NULL,2002,NULL)," +
		"('frc977',977,'Cometbots','http://www.halifax.k12.va.us/team977'," +
		" 'South Boston','Virginia','USA',NULL,2002,'Knowledge, the final frontier.')," +
		"('frc979',979,'Team 979','http://TBA'," +
		" 'Dayton','OH','USA',NULL,2002,NULL)," +
		"('frc980',980,'ThunderBots','http://www.team980.com'," +
		" 'Sylmar','California','USA',NULL,2002,'Strive for Excellence!')," +
		"('frc981',981,'Snobotics','http://snobotics.org'," +
		" 'Lebec','California','USA',NULL,2002,'When we build a robot it snows!')," +
		"('frc982',982,'The Knights','http://www.sfcss.org/OgormanHS/Departments/robotics'," +
		" 'Sioux Falls','SD','USA',NULL,2002,NULL)," +
		"('frc984',984,'Team 984',NULL," +
		" 'Wellesley','MA','USA',NULL,NULL,NULL)," +
		"('frc987',987,'HIGHROLLERS','http://www.team987.com'," +
		" 'Las Vegas','Nevada','USA',NULL,2002,'\"It''s not enough!\"')," +
		"('frc988',988,'Steel Phoenix','http://team988steelphoenix.weebly.com/'," +
		" 'Las Vegas','Nevada','USA',NULL,2002,'Our team always rises from the ashes')," +
		"('frc989',989,'Palo','http://www.paloverde.org/paulus/robotics'," +
		" 'Las Vegas','NV','USA',NULL,2002,NULL)," +
		"('frc990',990,'The Deadly Viper Assassination Squad','http://www.atech.org/faculty/gauthier/robot'," +
		" 'Las Vegas','NV','USA',NULL,2002,NULL)," +
		"('frc991',991,'BroncoBotics','http://www.broncobotics.org/'," +
		" 'Phoenix','Arizona','USA',NULL,2002,'Together to Create')," +
		"('frc992',992,'Oakwood Robotics','http://www.oakwoodrobotics.com'," +
		" 'North Hollywood','CA','USA',NULL,2002,NULL)," +
		"('frc995',995,'Monkey Wrenchers','http://www.firstinspires.org/'," +
		" 'Alhambra','California','USA',NULL,2002,'LET''S DO THIS!')," +
		"('frc996',996,'Mecha Knights','http://www.firstinspires.org/'," +
		" 'Casa Grande','Arizona','USA',NULL,2002,'\"Pass the duct tape!\"')," +
		"('frc997',997,'Spartan Robotics','http://www.chsrobotics.org'," +
		" 'Corvallis','Oregon','USA',NULL,2002,'Incito Futuri')," +
		"('frc998',998,'The Overcookers','http://www.kpbsd.k12.ak.us/sohi/robotics'," +
		" 'Soldotna','AK','USA',NULL,2002,NULL)," +
		"('frc999',999,'MechaRAMS','http://frcteam999.com'," +
		" 'Cheshire','Connecticut','USA',NULL,2002,NULL)";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_2 = SQL_INSERT_TEAMS +
		"('frc1000',1000,'Cybearcats','http://www.cybearcats.com'," +
		" 'Valparaiso','IN','USA',NULL,2003,NULL)," +
		"('frc1001',1001,'Hacksaw','http://sites.google.com/site/homeofbrushrobotics'," +
		" 'Lyndhurst','OH','USA',NULL,2003,NULL)," +
		"('frc1002',1002,'The CircuitRunners','http://www.circuitrunners.com/'," +
		" 'Marietta','Georgia','USA',NULL,2003,'Moving from Potential to Kinetic')," +
		"('frc1005',1005,'Chaminade Robotics','http://portal.chaminade-stl.com'," +
		" 'St. Louis','MO','USA',NULL,2003,NULL)," +
		"('frc1006',1006,'Fast Eddie Robotics','http://www.team1006.ca'," +
		" 'Port Perry','ON','Canada',NULL,2003,NULL)," +
		"('frc1007',1007,'The Tyrants','http://www.shiloh.high.gwinnett.k12.ga.us/teachers/852/852.html'," +
		" 'Snellville','GA','USA',NULL,2003,NULL)," +
		"('frc1008',1008,'Team Lugnut',NULL," +
		" 'Columbus','OH','USA',NULL,2003,NULL)," +
		"('frc1009',1009,'Bolt Action','http://www.boltaction1009.com'," +
		" 'North York','ON','Canada',NULL,2003,NULL)," +
		"('frc1010',1010,'B.C. Hawks','http://www.bhswebteam.com/robotics'," +
		" 'Ortonville','MI','USA',NULL,2003,NULL)," +
		"('frc1011',1011,'CRUSH','http://www.crush1011.org'," +
		" 'Tucson','Arizona','USA',NULL,2003,'We Build More Than Robots!')," +
		"('frc1013',1013,'The Phoenix','http://team1013.org'," +
		" 'Queen Creek','AZ','USA',NULL,2003,NULL)," +
		"('frc1014',1014,'Bad Robots','http://dublinrobotics.com'," +
		" 'Dublin','Ohio','USA',NULL,2003,'Pay It Forward')," +
		"('frc1015',1015,'Pi Hi Samurai','http://PiHiSamurai.org'," +
		" 'Ann Arbor','MI','USA',NULL,2003,NULL)," +
		"('frc1016',1016,'Evolution','http://robots.darlington.k12.sc.us'," +
		" 'Darlington','SC','USA',NULL,2003,NULL)," +
		"('frc1017',1017,'Home Advantage',NULL," +
		" 'Flint','MI','USA',NULL,2003,NULL)," +
		"('frc1018',1018,'Pike RoboDevils','http://pikerobodevils.com'," +
		" 'Indianapolis','Indiana','USA',NULL,2003,NULL)," +
		"('frc1019',1019,'Sons Of The Beach',NULL," +
		" 'Westhampton Beach','NY','USA',NULL,2003,NULL)," +
		"('frc1020',1020,'The Prank Monkeys','http://www.team1020.org'," +
		" 'Muncie','IN','USA',NULL,2003,NULL)," +
		"('frc1022',1022,'Archer GEeks','http://www.archergeeks.org'," +
		" 'Fort Wayne','IN','USA',NULL,2003,NULL)," +
		"('frc1023',1023,'Bedford Express','http://www.bedfordexpress.org'," +
		" 'Temperance','Michigan','USA',NULL,2003,'Engineers on Track')," +
		"('frc1024',1024,'Kil-A-Bytes','http://www.mckenzierobotics.org'," +
		" 'Indianapolis','Indiana','USA',NULL,2003,NULL)," +
		"('frc1025',1025,'IMPI Robotics','http://impirobotics.com'," +
		" 'Ferndale','Michigan','USA',NULL,2003,'Education is the most powerful weapon which you can use to change the world ')," +
		"('frc1026',1026,'Cougars','http://www.york.k12.sc.us'," +
		" 'York','SC','USA',NULL,2003,NULL)," +
		"('frc1027',1027,'Project Jabberwocky','http://www.team1027.com'," +
		" 'West Springfield','Massachusetts','USA',NULL,2003,NULL)," +
		"('frc1028',1028,'UBERGEEKS','http://www.firstubergeeks.com'," +
		" 'Huntsville','AL','USA',NULL,2003,NULL)," +
		"('frc1029',1029,'Wolverine Robotics',NULL," +
		" 'Miami','FL','USA',NULL,2003,NULL)," +
		"('frc1030',1030,'Combined Minds','http://first.efcts.us'," +
		" 'Groveport','OH','USA',NULL,2003,NULL)," +
		"('frc1031',1031,'The Boilermakers','http://www.team1031.org'," +
		" 'San Francisco','CA','USA',NULL,2003,NULL)," +
		"('frc1033',1033,'Team CLUTCH','http://www.teamclutch1033.com'," +
		" 'Richmond','Virginia','USA',NULL,2003,'Always CLUTCH!')," +
		"('frc1034',1034,'Eclipse',NULL," +
		" 'Chicago','IL','USA',NULL,2003,NULL)," +
		"('frc1035',1035,'T12','http://www.comaupico.com'," +
		" 'Chicago','IL','USA',NULL,2003,NULL)," +
		"('frc1036',1036,'Central Tech Robotics','http://www.centraltech.ca'," +
		" 'Toronto','ON','Canada',NULL,2003,NULL)," +
		"('frc1037',1037,'The SCREAM Team','http://teamfordfirst.org/Team1037'," +
		" 'Rochester Hills','MI','USA',NULL,2003,NULL)," +
		"('frc1038',1038,'Lakota Robotics','http://www.lakotarobotics.com'," +
		" 'Liberty Township','Ohio','USA',NULL,2003,'We Are FIRST!')," +
		"('frc1039',1039,'Chief Sealth Robotics','http://www.team1039.org'," +
		" 'Seattle','WA','USA',NULL,2003,NULL)," +
		"('frc1040',1040,'Pythons',NULL," +
		" 'Atlanta','GA','USA',NULL,2003,NULL)," +
		"('frc1041',1041,'Team 1041',NULL," +
		" 'Austell','GA','USA',NULL,2003,NULL)," +
		"('frc1042',1042,'Team 1042',NULL," +
		" 'Chicago','IL','USA',NULL,2003,NULL)," +
		"('frc1043',1043,'Batteries Not Included','http://www.kehillahhigh.org/robotics'," +
		" 'San Jose','CA','USA',NULL,2003,NULL)," +
		"('frc1045',1045,'MSJ Robotics','http://www.msjrobotics.com'," +
		" 'Fremont','CA','USA',NULL,2003,NULL)," +
		"('frc1046',1046,'Aztechs','http://prismrobotics.org'," +
		" 'Palm Desert','CA','USA',NULL,2003,NULL)," +
		"('frc1047',1047,'Team Echoes','http://www.teamechoes.com/index.php?page=9'," +
		" 'Irvine','CA','USA',NULL,2003,NULL)," +
		"('frc1048',1048,'Mustang Robotics','http://www.manvilleschools.org/manville/High%20School/Robotics%20Team'," +
		" 'Manville','NJ','USA',NULL,2003,NULL)," +
		"('frc1049',1049,'Archangel Robotics','http://www.archangelrobotics.com/index.php'," +
		" 'Toronto','ON','Canada',NULL,2003,NULL)," +
		"('frc1051',1051,'Technical Terminators','http://www.act.marion.k12.sc.us'," +
		" 'Mullins','South Carolina','USA',NULL,2003,NULL)," +
		"('frc1052',1052,'Team 1052',NULL," +
		" 'Pinole','CA','USA',NULL,2003,NULL)," +
		"('frc1053',1053,'Gears of Glebe','http://www.gleberobotics.com'," +
		" 'Ottawa','ON','Canada',NULL,2003,NULL)," +
		"('frc1054',1054,'Knightmares','http://www.knightmarerobotics.com'," +
		" 'Buckingham','VA','USA',NULL,2003,NULL)," +
		"('frc1055',1055,'Frank''s Garage','http://www.fwpschool.org/FIRST'," +
		" 'Chicago','IL','USA',NULL,2003,NULL)," +
		"('frc1056',1056,'Hot Rocks','http://www.firstinspires.org/'," +
		" 'Hilo','Hawaii','USA',NULL,2003,'Don''t finish last')," +
		"('frc1057',1057,'The Blue Knights',NULL," +
		" 'Sandersville','GA','USA',NULL,2003,NULL)," +
		"('frc1058',1058,'PVC Pirates','http://team1058.com/'," +
		" 'Londonderry','New Hampshire','USA',NULL,2003,'You can''t stop Team 1058')," +
		"('frc1059',1059,'Team 1059','http://www.ethelwalker.org'," +
		" 'Simsbury','CT','USA',NULL,2003,NULL)," +
		"('frc1060',1060,'Dust Devils',NULL," +
		" 'Mohave Valley','AZ','USA',NULL,2003,NULL)," +
		"('frc1062',1062,'Storm Troopers','http://www.celhs.osceola.k12.fl.us/FIRST/index.html'," +
		" 'Celebration','FL','USA',NULL,2003,NULL)," +
		"('frc1063',1063,'Team MG',NULL," +
		" 'Toronto','ON','Canada',NULL,2003,NULL)," +
		"('frc1064',1064,'Wild Beast','http://www.firstwildbeast.com'," +
		" 'South Milwaukee','WI','USA',NULL,2003,NULL)," +
		"('frc1065',1065,'The Moose','http://www.ohs.osceola.k12.fl.us/clubs/robotics/index.html'," +
		" 'Kissimmee','Florida','USA',NULL,2003,'The Moose is on the Loose')," +
		"('frc1067',1067,'Junior Billikens','http://www.geocities.com/sluhrobotics'," +
		" 'Saint Louis','MO','USA',NULL,2003,NULL)," +
		"('frc1068',1068,'The Ninjaneers','http://www.team1068.org'," +
		" 'Orlando','FL','USA',NULL,2003,NULL)," +
		"('frc1069',1069,'Technobots','http://www.technobots.org'," +
		" 'Greer','SC','USA',NULL,2003,NULL)," +
		"('frc1070',1070,'Royal Robotrons','http://In Progress'," +
		" 'Woodland Hills','California','USA',NULL,2003,'')," +
		"('frc1071',1071,'MAX','http://www.max1071.com'," +
		" 'Wolcott','Connecticut','USA',NULL,2003,'To The MAX')," +
		"('frc1072',1072,'Harker Robotics','http://robotics.harker.org/'," +
		" 'San Jose','California','USA',NULL,2003,'Learning Through Guided Experience')," +
		"('frc1073',1073,'The Force Team','http://www.frc1073.org'," +
		" 'Hollis','New Hampshire','USA',NULL,2003,'Sanity is Optional')," +
		"('frc1074',1074,'Team 1074',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc1075',1075,'Sprockets','http://www.sinclairsprockets.ca/'," +
		" 'Whitby','Ontario','Canada',NULL,2003,'Rock it like the Sprockets!')," +
		"('frc1076',1076,'Pi Hi Samurai','http://pihisamurai.org'," +
		" 'Ann Arbor','Michigan','USA',NULL,2003,NULL)," +
		"('frc1077',1077,'Team 1077',NULL," +
		" 'Miami','FL','USA',NULL,2003,NULL)," +
		"('frc1079',1079,'Chaparral Robotic Eng And Tech Explorers Murrieta Amazing Grace Inventors Coalit',NULL," +
		" 'Temecula','CA','USA',NULL,2003,NULL)," +
		"('frc1080',1080,'Resurgence Robotics','https://www.resurgence1080.org/'," +
		" 'Henrico','Virginia','USA',NULL,2003,'Always CLUTCH!')," +
		"('frc1081',1081,'Tech Warriors',NULL," +
		" 'Atlanta','GA','USA',NULL,2003,NULL)," +
		"('frc1082',1082,'Railroaders','http://shsrobot.greatnow.com'," +
		" 'Sparks','NV','USA',NULL,2003,NULL)," +
		"('frc1083',1083,'Team Emoticons','http://www.team-emoticons.com'," +
		" 'Orlando','FL','USA',NULL,2003,NULL)," +
		"('frc1084',1084,'IronColts',NULL," +
		" 'Sarnia','ON','Canada',NULL,2003,NULL)," +
		"('frc1085',1085,'Aquatic Robotics','http://www.aquaticrobotics.org'," +
		" 'Avon','Connecticut','USA',NULL,2003,NULL)," +
		"('frc1086',1086,'Blue Cheese','http://www.bluecheese1086.org/'," +
		" 'Glen Allen','Virginia','USA',NULL,2003,'A Slice of FIRST')," +
		"('frc1087',1087,'Titronics Digerati','http://west.salkeiz.k12.or.us/contract/robotics/Robotics/index.htm'," +
		" 'Salem','OR','USA',NULL,2003,NULL)," +
		"('frc1088',1088,'Scarborough Warriors At The Top','http://www.swatt1088.com'," +
		" 'West Hill / Toronto','ON','Canada',NULL,2003,NULL)," +
		"('frc1089',1089,'Team Mercury','http://www.mercury1089.com'," +
		" 'Hightstown','New Jersey','USA',NULL,2003,'The Team is what you make it')," +
		"('frc1090',1090,'Team 1090',NULL," +
		" 'St Louis','MO','USA',NULL,2003,NULL)," +
		"('frc1091',1091,'Oriole Assault','http://www.team1091.com'," +
		" 'Hartford','Wisconsin','USA',NULL,2003,'It doesn’t have to look pretty, it just has to work.')," +
		"('frc1092',1092,'The Rabid Rabbits','http://chsrabidrabbits.tripod.com'," +
		" 'Smyrna','GA','USA',NULL,2003,NULL)," +
		"('frc1093',1093,'Cougars',NULL," +
		" 'Richmond','VA','USA',NULL,2003,NULL)," +
		"('frc1094',1094,'Channel Cats','http://channelcats.rivercityrobots.org/'," +
		" 'O Fallon','Missouri','USA',NULL,2003,'Hooked on Technology')," +
		"('frc1095',1095,'Metal Minds',NULL," +
		" 'Chatham','Virginia','USA',NULL,2003,NULL)," +
		"('frc1096',1096,'ZULU','http://www.radford.edu/~cougars'," +
		" 'Radford','VA','USA',NULL,2003,NULL)," +
		"('frc1097',1097,'Site 3 Engineering','http://robotics.jhs.net'," +
		" 'Carmichael','CA','USA',NULL,2003,NULL)," +
		"('frc1098',1098,'Rockwood Robotics \"Rock-Bots\"','http://www.rockwoodrobotics.com'," +
		" 'Wildwood','MO','USA',NULL,2003,NULL)," +
		"('frc1099',1099,'DiscoTechs','http://www.discotechs.org'," +
		" 'Brookfield','Connecticut','USA',NULL,2003,'To inspire people in our community to become excited about and engaged in STEM.')," +
		"('frc1100',1100,'The T-Hawks','http://www.team1100.org'," +
		" 'Northboro','Massachusetts','USA',NULL,2003,'Go Team1100!')," +
		"('frc1101',1101,'Mustangs','http://piczo.com/SurreyRoboticsTeam?cr=6&rfm=y'," +
		" 'Surrey','ND','USA',NULL,2003,NULL)," +
		"('frc1102',1102,'M''Aiken Magic ','https://twitter.com/maikenmagic'," +
		" 'Augusta','Georgia','USA',NULL,2003,'Together with FIRST, we are M''Aiken Magic happen')," +
		"('frc1103',1103,'Frankenbots','http://www.frc1103.org'," +
		" 'Delavan','WI','USA',NULL,2003,NULL)," +
		"('frc1104',1104,'TechnoEagles','http://team1104.ozautomation.com'," +
		" 'Axtell','KS','USA',NULL,2003,NULL)," +
		"('frc1105',1105,'The Prep',NULL," +
		" 'Los Angeles','CA','USA',NULL,2003,NULL)," +
		"('frc1106',1106,'patriots',NULL," +
		" 'Mayville','ND','USA',NULL,2003,NULL)," +
		"('frc1108',1108,'Panther Robotics','http://www.pantherrobotics.com'," +
		" 'Paola','Kansas','USA',NULL,2003,'A Panther among robots is a most fearsome thing.')," +
		"('frc1109',1109,'The RoboHawks',NULL," +
		" 'Lively','ON','Canada',NULL,2003,NULL)," +
		"('frc1110',1110,'Binary Bulldogs','http://hhsrobotics.circuitrunners.com'," +
		" 'Palmdale','CA','USA',NULL,2003,NULL)," +
		"('frc1111',1111,'Power Hawks Robotics','http://www.powerhawks.org'," +
		" 'Edgewater','Maryland','USA',NULL,2003,'\"Soaring to success\"')," +
		"('frc1112',1112,'Huskies',NULL," +
		" 'Toronto','ON','Canada',NULL,2003,NULL)," +
		"('frc1113',1113,'Urban Assault',NULL," +
		" 'Philadelphia','PA','USA',NULL,2003,NULL)," +
		"('frc1114',1114,'Simbotics','http://www.simbotics.org'," +
		" 'St Catharines','Ontario','Canada',NULL,2003,'Ball so hard')," +
		"('frc1115',1115,'Deus Ex Machina',NULL," +
		" 'Santa Monica','CA','USA',NULL,2003,NULL)," +
		"('frc1116',1116,'Team 1116',NULL," +
		" 'La Puente','CA','USA',NULL,2003,NULL)," +
		"('frc1118',1118,'NERDS INC.','http://www.s-dhs.org'," +
		" 'Knoxville','TN','USA',NULL,2003,NULL)," +
		"('frc1120',1120,'Milpitas Xtreme Robotics',NULL," +
		" 'Milpitas','CA','USA',NULL,2003,NULL)," +
		"('frc1122',1122,'Railbots','http://none'," +
		" 'Cleveland','OH','USA',NULL,2003,NULL)," +
		"('frc1123',1123,'AIM Robotics','http://1123.team'," +
		" 'Fairfax','Virginia','USA',NULL,2003,'At AIM we build in heavy metal')," +
		"('frc1124',1124,'UberBots','http://www.uberbots.org'," +
		" 'Avon','Connecticut','USA',NULL,2003,'We eat Kryptonite for breakfast!')," +
		"('frc1125',1125,'Broncos','http://powayusd.sdcoe.k12.ca.us/pusdrbhs'," +
		" 'San Diego','CA','USA',NULL,2003,NULL)," +
		"('frc1126',1126,'SPARX','http://www.gosparx.org'," +
		" 'Webster','New York','USA',NULL,2003,NULL)," +
		"('frc1127',1127,'Lotus Robotics','http://www.firstinspires.org/'," +
		" 'Milton','Georgia','USA',NULL,2003,'We love robotics!')," +
		"('frc1129',1129,'Team 1129','http://www.geocities.com/rtcrobotproject/index.html'," +
		" 'Montreal','QC','Canada',NULL,2003,NULL)," +
		"('frc1130',1130,'Phantoms',NULL," +
		" 'Albany','OR','USA',NULL,2003,NULL)," +
		"('frc1131',1131,'Robo-Techs','http://www.team1131.com'," +
		" 'Hagerstown','MD','USA',NULL,2003,NULL)," +
		"('frc1132',1132,'R.A.P.T.A.R. (Robotics Adventure Professionals of Tidewater and Richmond)','http://www.raptar.net'," +
		" 'Ashland','VA','USA',NULL,2003,NULL)," +
		"('frc1133',1133,'The Hug A Bots',NULL," +
		" 'Reno','NV','USA',NULL,2003,NULL)," +
		"('frc1135',1135,'WHS Schmoebotics','http://home.earthlink.net/~whsrobotics'," +
		" 'Cerritos','CA','USA',NULL,2003,NULL)," +
		"('frc1136',1136,'Nutty Genius''s','http://mt.miguelrobotics.8m.com'," +
		" 'Spring Valley','CA','USA',NULL,2003,NULL)," +
		"('frc1137',1137,'Rocket Sauce','http://firstteam1137.com/'," +
		" 'Mathews','Virginia','USA',NULL,2003,'prepare for the worst,expect the best')," +
		"('frc1138',1138,'Eagle Engineering','http://team1138robotics.com'," +
		" 'West Hills','California','USA',NULL,2003,'We Build For a Better Tomorrow')," +
		"('frc1139',1139,'The Chamblee Gear Grinders','http://geargrinders.net'," +
		" 'Chamblee','GA','USA',NULL,2003,NULL)," +
		"('frc1140',1140,'MAGNADON','http://hollyareaschools.com/hhs/academics/robotics/index.html'," +
		" 'Holly','MI','USA',NULL,2003,NULL)," +
		"('frc1141',1141,'TAS Megawatts','http://www.tasrobotics.com'," +
		" 'Peterborough','ON','Canada',NULL,2003,NULL)," +
		"('frc1142',1142,'Team 1142',NULL," +
		" 'Daytona Beach','FL','USA',NULL,2003,NULL)," +
		"('frc1143',1143,'Cruzin'' Comets','http://www.cruzincomets.com'," +
		" 'Clarks Summit','Pennsylvania','USA',NULL,2003,NULL)," +
		"('frc1144',1144,'COQUITRON','http://instagram.com/coquitron_robotics'," +
		" 'San Lorenzo','Puerto Rico','USA',NULL,2003,NULL)," +
		"('frc1146',1146,'Magic','http://www.menihekhigh.k12.nf.ca'," +
		" 'Labrador City','NL','Canada',NULL,2003,NULL)," +
		"('frc1147',1147,'The Herdinators','http://www.freewebs.com/eghsrobotics'," +
		" 'Elk Grove','CA','USA',NULL,2003,NULL)," +
		"('frc1148',1148,'Wafflebots','http://team1148.com/'," +
		" 'Studio City','California','USA',NULL,2003,NULL)," +
		"('frc1149',1149,'Team 1149',NULL," +
		" 'Thousand Oaks','CA','USA',NULL,2003,NULL)," +
		"('frc1150',1150,'Team 1150','http://n/a'," +
		" 'Camp Verde','AZ','USA',NULL,2003,NULL)," +
		"('frc1151',1151,'The Hitchhikers','http://www.contracosta.edu/mchs/robotics'," +
		" 'San Pablo','CA','USA',NULL,2003,NULL)," +
		"('frc1152',1152,'Blue Stars',NULL," +
		" 'Hyde Park','MA','USA',NULL,2003,NULL)," +
		"('frc1153',1153,'Walpole Robotics Revolution','http://www.walpolerobotics.org/'," +
		" 'Walpole','Massachusetts','USA',NULL,2003,'Join The Revolution!')," +
		"('frc1154',1154,'Harrisonburg High School Blue Streaks',NULL," +
		" 'Harrisonburburg','VA','USA',NULL,2003,NULL)," +
		"('frc1155',1155,'SciBorgs','http://bxsciborgs.com/'," +
		" 'Bronx','New York','USA',NULL,2003,'Practice Random Acts of Genius')," +
		"('frc1156',1156,'Under Control','http://www.undercontrol1156.com'," +
		" 'Novo Hamburgo','Rio Grande do Sul','Brazil',NULL,2003,'Our mission is to build better people ')," +
		"('frc1157',1157,'Landsharks','http://www.landsharkrobotics.org'," +
		" 'Boulder','Colorado','USA',NULL,2003,'\"Cable not Dish\"')," +
		"('frc1158',1158,'The Corps','http://www.eaglecorps.me'," +
		" 'Collbran','CO','USA',NULL,2003,NULL)," +
		"('frc1159',1159,'Ramona Rampage','http://ramonarampage.org'," +
		" 'Alhambra','California','USA',NULL,2003,'It''s not ghetto; it''s pragmatic.')," +
		"('frc1160',1160,'Titanium','http://www.titaniumrobotics.com'," +
		" 'San Marino','California','USA',NULL,2003,'Feelings are important, but it''s the Physics that matters!')," +
		"('frc1161',1161,'Team 1161',NULL," +
		" 'Brighton','MA','USA',NULL,2003,NULL)," +
		"('frc1162',1162,'Team 1162',NULL," +
		" 'Canutillo','TX','USA',NULL,2003,NULL)," +
		"('frc1163',1163,'Trojan Horses','http://nhm7792.k12.sd.us'," +
		" 'Faulkton','SD','USA',NULL,2003,NULL)," +
		"('frc1164',1164,'Project NEO','http://www.projectneo.net'," +
		" 'Las Cruces','New Mexico','USA',NULL,2003,'Project NEO Rocks')," +
		"('frc1165',1165,'Team Paradise','http://teamparadise1165.com'," +
		" 'Phoenix','Arizona','USA',NULL,2003,'If it was easy, you didn''t do it right.')," +
		"('frc1166',1166,'Highlander Robotics','http://www.savannah.chatham.k12.ga.us/groves/HighLander%20Index.htm'," +
		" 'Garden City','GA','USA',NULL,2003,NULL)," +
		"('frc1167',1167,'Knightmares','http://www.savannah.chatham.k12.ga.us/schools/wfhs/index.html'," +
		" 'Savannah','GA','USA',NULL,2003,NULL)," +
		"('frc1168',1168,'Malvern Robotics','http://team1168robotics.blogspot.com/'," +
		" 'Malvern','Pennsylvania','USA',NULL,2003,NULL)," +
		"('frc1169',1169,'Fairbanks Robarctics','http://www.geocities.com/kahoe1/indexdark.html'," +
		" 'Fairbanks','AK','USA',NULL,2003,NULL)," +
		"('frc1172',1172,'We Tek Too',NULL," +
		" 'Richmond','VA','USA',NULL,2003,NULL)," +
		"('frc1177',1177,'MECHA-JAGS','http://www.mechajagrobotics.org'," +
		" 'Stone Mountain','GA','USA',NULL,2003,NULL)," +
		"('frc1178',1178,'DURT  (De Smet Ultimate Robotics Team)','http://www.durtrobotics.com'," +
		" 'Saint Louis','Missouri','USA',NULL,2003,NULL)," +
		"('frc1180',1180,'Atomsmashers','http://www.smashers.org/PostNuke-0.750/html/index.php'," +
		" 'Thunderbolt','GA','USA',NULL,2003,NULL)," +
		"('frc1181',1181,'Team 1181',NULL," +
		" 'Gilbert','AZ','USA',NULL,2003,NULL)," +
		"('frc1182',1182,'Patriots',NULL," +
		" 'Manchester','MO','USA',NULL,NULL,NULL)," +
		"('frc1183',1183,'Team Ascari',NULL," +
		" 'Suwanee','GA','USA',NULL,2003,NULL)," +
		"('frc1184',1184,'Cobra Robotics','http://www.cobrarobotics.com'," +
		" 'Bel Air','MD','USA',NULL,2003,NULL)," +
		"('frc1185',1185,'The X-Bots','http://www.rec.ri.cmu.edu/education/xbots/index.html'," +
		" 'Pittsburgh','PA','USA',NULL,2003,NULL)," +
		"('frc1187',1187,'Brick City Flame','http://www.geocities.com/firebird_1187'," +
		" 'Newark','NJ','USA',NULL,2003,NULL)," +
		"('frc1188',1188,'RoboRavens','http://www.team1188.org'," +
		" 'Royal Oak','Michigan','USA',NULL,2003,'Grace is courage under pressure. \"Hemmingway\"')," +
		"('frc1189',1189,'The Gearheads','http://gearheads1189.weebly.com'," +
		" 'Grosse Pointe','Michigan','USA',NULL,2003,'We''re all Gearheads')," +
		"('frc1190',1190,'ANONOBOTS','http://www.anonobots-1190.com'," +
		" 'Aurora','CO','USA',NULL,2003,NULL)," +
		"('frc1191',1191,'Eagles','http://www.kosd.org/FIRST'," +
		" 'Pittsburgh','PA','USA',NULL,2003,NULL)," +
		"('frc1192',1192,'Team 1192',NULL," +
		" 'Land O Lakes','WI','USA',NULL,2003,NULL)," +
		"('frc1195',1195,'S.O.A.R. (Students of Applied Robotics)','http://www.patriots-ttc.org'," +
		" 'Springdale','Maryland','USA',NULL,2003,NULL)," +
		"('frc1197',1197,'TorBots','http://www.team1197.com'," +
		" 'Torrance','California','USA',NULL,2003,'Student run, student done')," +
		"('frc1199',1199,'Team 1199',NULL," +
		" 'Pittsburgh','PA','USA',NULL,2003,NULL)," +
		"('frc1200',1200,'Team 1200',NULL," +
		" 'New Stanton','PA','USA',NULL,2003,NULL)," +
		"('frc1201',1201,'CSI:Monroe','http://www.csimonroe.com'," +
		" 'Aberdeen','MS','USA',NULL,2003,NULL)," +
		"('frc1202',1202,'Team 1202',NULL," +
		" 'Sidman','PA','USA',NULL,2003,NULL)," +
		"('frc1203',1203,'PANDEMONIUM','http://shswbschools.sharpschool.com/staff_directory/technology_education/mr__de_Simone/robotics'," +
		" 'West Babylon','NY','USA',NULL,2003,NULL)," +
		"('frc1204',1204,'Eagles',NULL," +
		" 'Lakewood','OH','USA',NULL,2003,NULL)," +
		"('frc1205',1205,'Badgerbots','http://classroom.webb.esc1.net/webs/bhs/bhs_robotics.htm'," +
		" 'Bruni','TX','USA',NULL,2003,NULL)," +
		"('frc1206',1206,'Wrench Warriors','http://www.wrenchwarriors.cjb.net'," +
		" 'Fairview Park','OH','USA',NULL,2003,NULL)," +
		"('frc1208',1208,'Metool Brigade','http://www.othsrobotics.com/'," +
		" 'O Fallon','Illinois','USA',NULL,2003,'Fearless')," +
		"('frc1209',1209,'Robo Hornets','http://btwrobotics.com'," +
		" 'Tulsa','Oklahoma','USA',NULL,2003,'Excellence is to do a common thing in an uncommon way.  Booker T. Washington')," +
		"('frc1210',1210,'Nyack','http://www.jracademy.com/~nyack/NYACKhomepage.html'," +
		" 'St Louis','MO','USA',NULL,2003,NULL)," +
		"('frc1211',1211,'Robotnics','http://www.shof515.com/autohs'," +
		" 'Brooklyn','NY','USA',NULL,2003,NULL)," +
		"('frc1212',1212,'Sentinels','http://1212robotics.com'," +
		" 'Chandler','Arizona','USA',NULL,2003,'\"It''s only temporary unless it works\"')," +
		"('frc1213',1213,'GROVES',NULL," +
		" 'Beverly  Hills','MI','USA',NULL,2003,NULL)," +
		"('frc1214',1214,'The Grizzlies','http://www.cstem.com'," +
		" 'Houston','TX','USA',NULL,2003,NULL)," +
		"('frc1215',1215,'Machine Tool','http://''www.fourrivers@washington.k12.mo.us'," +
		" 'Washington','MO','USA',NULL,2003,NULL)," +
		"('frc1216',1216,'Knights','http://www.team1216.com'," +
		" 'Oak Park','MI','USA',NULL,2003,NULL)," +
		"('frc1218',1218,'SCH Vulcan Robotics','http://www.Team1218.org'," +
		" 'Philadelphia','Pennsylvania','USA',NULL,2003,'Building a better world, one robot at a time')," +
		"('frc1219',1219,'Iron Eagles','http://ironeagles.net'," +
		" 'Toronto','ON','Canada',NULL,2003,NULL)," +
		"('frc1220',1220,'Team 1220',NULL," +
		" 'Milwaukee','WI','USA',NULL,2003,NULL)," +
		"('frc1221',1221,'Nerdbotics',NULL," +
		" 'Mississauga','ON','Canada',NULL,2003,NULL)," +
		"('frc1222',1222,'Falcons','http://www.falconbot.com'," +
		" 'Richmond','VA','USA',NULL,2003,NULL)," +
		"('frc1223',1223,'Twisted Dimension','http://www.evitengineering.com'," +
		" 'Mesa','AZ','USA',NULL,2003,NULL)," +
		"('frc1224',1224,'Team 1224 - The Pius Princesses','http://Team%201224-%20The%20Pius%20Princesses'," +
		" 'Bronx','NY','USA',NULL,2003,NULL)," +
		"('frc1225',1225,'The Gorillas','http://www.team1225.com'," +
		" 'Hendersonville','North Carolina','USA',NULL,2003,'Zip ties and duct tape because we can.')," +
		"('frc1226',1226,'Team 1226',NULL," +
		" 'Farmingdale','NY','USA',NULL,2003,NULL)," +
		"('frc1227',1227,'Techno-Gremlins','http://www.techno-gremlins.org'," +
		" 'Grand Rapids','MI','USA',NULL,2003,NULL)," +
		"('frc1228',1228,'RoboTribe','http://www.team1228us.wix.com/robotribe  and new  http://team1228.org/'," +
		" 'Rahway','New Jersey','USA',NULL,2003,' “Fail Often to Succeed Sooner”')," +
		"('frc1229',1229,'MASA','http://www.MasseyFirst.com'," +
		" 'Windsor','ON','Canada',NULL,2003,NULL)," +
		"('frc1230',1230,'The Lehman Lionics','http://firstlion1230.weebly.com/'," +
		" 'Bronx','New York','USA',NULL,2003,NULL)," +
		"('frc1232',1232,'RAMS ROBOTICS','http://www.ramsrobotics.com'," +
		" 'Stratford','ON','Canada',NULL,2003,NULL)," +
		"('frc1235',1235,'Maximum Strength',NULL," +
		" 'Columbus','MS','USA',NULL,2003,NULL)," +
		"('frc1236',1236,'Phoenix Rising','http://web.dps.k12.va.us/galileo'," +
		" 'Danville','VA','USA',NULL,2003,NULL)," +
		"('frc1237',1237,'Cybernetic Panthers','http://www.unhsrobotics.com'," +
		" 'New York','NY','USA',NULL,2003,NULL)," +
		"('frc1238',1238,'Team 1238',NULL," +
		" 'New York','NY','USA',NULL,2003,NULL)," +
		"('frc1239',1239,'Vita Dogs',NULL," +
		" 'Olmsted Falls','OH','USA',NULL,2004,NULL)," +
		"('frc1240',1240,'Sappers','http://www.freewebs.com/lincolnrobotics'," +
		" 'Manitowoc','WI','USA',NULL,2004,NULL)," +
		"('frc1241',1241,'THEORY6','http://www.theory6.ca'," +
		" 'Mississauga','Ontario','Canada',NULL,2004,'Growing Technological Consciousness')," +
		"('frc1242',1242,'Team S.M.I.L.E.Y','http://www.dphdsrobotics.org'," +
		" 'Plantation','FL','USA',NULL,2004,NULL)," +
		"('frc1243',1243,'Dragons','http://www.firstinspires.org/'," +
		" 'Swartz Creek','Michigan','USA',NULL,2004,'\"Every job on this team is important!\"')," +
		"('frc1244',1244,'Viking Robotics','http://1244robotics.freehostia.com'," +
		" 'Goderich','ON','Canada',NULL,2004,NULL)," +
		"('frc1245',1245,'Monarch Robotics','https://www.shazbots.org'," +
		" 'Louisville','Colorado','USA',NULL,2004,'How high? Mo-Hi!')," +
		"('frc1246',1246,'Agincourt Skunkworks','http://na'," +
		" 'Scarborough','Ontario','Canada',NULL,2004,'We Get It Done in Time')," +
		"('frc1247',1247,'Blood, Sweat, and Gears','http://www.roksbot.com'," +
		" 'North Sutton','New Hampshire','USA',NULL,2004,NULL)," +
		"('frc1248',1248,'TITANium Allies','http://team1248.org'," +
		" 'Berea','Ohio','USA',NULL,2004,'We add up!')," +
		"('frc1249',1249,'Robo Rats','http://www.team1249.com'," +
		" 'Delbarton','West Virginia','USA',NULL,2004,'Today''s youth is tomorrow''s future')," +
		"('frc1250',1250,'Gator-Bots','http://www.gatorbots.org'," +
		" 'Dearborn','Michigan','USA',NULL,2004,'\"Bringing advanced technology into historic places\"')," +
		"('frc1251',1251,'Tech-Tiger Robotics, 4-H','http://www.team1251.org'," +
		" 'Tamarac','Florida','USA',NULL,2004,'Technology for the 21st Century')," +
		"('frc1254',1254,'Tech Force','http://www.firstinspires.org/'," +
		" 'Lawrence','Michigan','USA',NULL,2004,'Check the Flowchart')," +
		"('frc1255',1255,'Blarglefish','http://1255blarglefish.weebly.com/'," +
		" 'Baytown','Texas','USA',NULL,2004,'Where Imagination Runs Deep')," +
		"('frc1256',1256,'Highlanders','http://http:/www.hhsengineering.com'," +
		" 'Howell','MI','USA',NULL,2004,NULL)," +
		"('frc1257',1257,'Parallel Universe','http://www.team1257.org'," +
		" 'Scotch Plains','New Jersey','USA',NULL,2004,NULL)," +
		"('frc1258',1258,'SeaBot','http://www.seattlelutheran.org/events/robotics/robotics.html'," +
		" 'Seattle','Washington','USA',NULL,2004,NULL)," +
		"('frc1259',1259,'Paradigm Shift','http://team1259.com'," +
		" 'Pewaukee','Wisconsin','USA',NULL,2004,'Changing The Minds of Those Around Us')," +
		"('frc1260',1260,'Panthers','http://www.cprobotics.com'," +
		" 'Mississauga','ON','Canada',NULL,2004,NULL)," +
		"('frc1261',1261,'Robo  Lions','http://www.prhsrobotics.com/'," +
		" 'Suwanee','Georgia','USA',NULL,2004,'Fear the Roar')," +
		"('frc1262',1262,'the STAGS','http://Facebook:  Stags team 1262'," +
		" 'Martinsville','Virginia','USA',NULL,2004,'\"Fear the Deer\"')," +
		"('frc1263',1263,'Austin High School/Marshall Space Flight Center','http://www.ahsrobotics.org'," +
		" 'Decatur','AL','USA',NULL,2004,NULL)," +
		"('frc1266',1266,'The Devil Duckies','https://team1266.wordpress.com/'," +
		" 'San Diego','California','USA',NULL,2004,'Changing Perceptions - Changing Lives - Changing the Future')," +
		"('frc1268',1268,'Purgolder Robotics','http://www.whs.edu/team1268/index.html'," +
		" 'Milwaukee','WI','USA',NULL,2004,NULL)," +
		"('frc1270',1270,'Red Dragons','http://1270.ytaccc.org'," +
		" 'Cleveland','OH','USA',NULL,2004,NULL)," +
		"('frc1272',1272,'Tyrannikal Mechanikal','http://www.bloomingtonrobotics.com'," +
		" 'Bloomington','IN','USA',NULL,2004,NULL)," +
		"('frc1274',1274,'Wolf Bytes','http://berearobotics.webs.com'," +
		" 'Berea','OH','USA',NULL,2004,NULL)," +
		"('frc1275',1275,'Pi-Robots','http://www.havergalrobotics.on.ca'," +
		" 'Toronto','ON','Canada',NULL,2004,NULL)," +
		"('frc1276',1276,'Kaizen Blitz','http://www.mcstrobotics.org'," +
		" 'Rockland','ME','USA',NULL,2004,NULL)," +
		"('frc1277',1277,'The Robotomies','http://www.robotomies.com'," +
		" 'Groton','Massachusetts','USA',NULL,2004,NULL)," +
		"('frc1278',1278,'B.E.A.R.S','http://team1278.org'," +
		" 'North Royalton','OH','USA',NULL,2004,NULL)," +
		"('frc1279',1279,'Cold Fusion','https://www.immaculatahighschool.org/about_us/student_clubs_organizations/robotics'," +
		" 'Somerville','New Jersey','USA',NULL,2004,'Fusion of the best!')," +
		"('frc1280',1280,'Ragin'' C- Biscuits','http://www.srvhsrobotics.com'," +
		" 'Danville','California','USA',NULL,2004,'')," +
		"('frc1281',1281,'Mustang Robotics','http://www.mustangrobotics.ca'," +
		" 'Richmond Hill','ON','Canada',NULL,2004,NULL)," +
		"('frc1284',1284,'DART','http://robotics.guntersville-high.com'," +
		" 'Guntersville','AL','USA',NULL,2004,NULL)," +
		"('frc1285',1285,'The Big Bang','http://www.firstinspires.org/'," +
		" 'Mississauga','Ontario','Canada',NULL,2004,NULL)," +
		"('frc1286',1286,'Kevlar Cranes','http://oakland.k12.mi.us/robotics1286'," +
		" 'Royal Oak','MI','USA',NULL,2004,NULL)," +
		"('frc1287',1287,'Aluminum Assault','http://www.FRCTeam1287.com'," +
		" 'Myrtle Beach','South Carolina','USA',NULL,2004,'Best work, First time, On time')," +
		"('frc1288',1288,'RAVEN Robotics','http://ravenrobotics.org'," +
		" 'Saint Charles','Missouri','USA',NULL,2004,NULL)," +
		"('frc1289',1289,'Gearheadz','http://www.lhsgearheadz.org/'," +
		" 'Lawrence','Massachusetts','USA',NULL,2004,'Kids sharing technology, one robot at a time')," +
		"('frc1290',1290,'Robowolves','http://team1290.info'," +
		" 'Chandler','Arizona','USA',NULL,2004,'Si Se Puede')," +
		"('frc1291',1291,'Circuit Breakers','http://www.scs.on.ca/robotics'," +
		" 'Toronto','ON','Canada',NULL,2004,NULL)," +
		"('frc1292',1292,'RNS Joysticks',NULL," +
		" 'Rothesay','NB','Canada',NULL,2004,NULL)," +
		"('frc1293',1293,'Pandamaniacs','http://d5robotics.org/'," +
		" 'Columbia','South Carolina','USA',NULL,2004,'Be the bot.')," +
		"('frc1294',1294,'Top Gun','http://www.team1294.org'," +
		" 'Sammamish','Washington','USA',NULL,2004,'You get out of it, what you put into it. Stop Breaking It More!')," +
		"('frc1295',1295,'The Golems','http://robotics.rsgc.on.ca'," +
		" 'Toronto','ON','Canada',NULL,2004,NULL)," +
		"('frc1296',1296,'Full Metal Jackets','http://www.rockwallrobotics.com'," +
		" 'Rockwall','Texas','USA',NULL,2004,'It''s a cat(apult).')," +
		"('frc1297',1297,'Team 1297',NULL," +
		" 'Sylacauga','AL','USA',NULL,2004,NULL)," +
		"('frc1298',1298,'205 Terminators','http://www.jeffstateonline.com/first'," +
		" 'Birmingham','AL','USA',NULL,2004,NULL)," +
		"('frc1299',1299,'Dominators','http://www.phsdominators.com'," +
		" 'Jasper','GA','USA',NULL,2004,NULL)," +
		"('frc1300',1300,'EVO','http://www.loyola.haltonrc.edu.on.ca/robotics'," +
		" 'Oakville','ON','Canada',NULL,2004,NULL)," +
		"('frc1301',1301,'The Robotic Raiders','http://www.halerobotics.org'," +
		" 'Seattle','WA','USA',NULL,2004,NULL)," +
		"('frc1302',1302,'Team Lionheart','http://www.teamlionheart.webs.com'," +
		" 'Sparta','NJ','USA',NULL,2004,NULL)," +
		"('frc1303',1303,'WYOHAZARD','http://www.wyohazardrobotics.org'," +
		" 'Casper','Wyoming','USA',NULL,2004,'We don''t just build robots, we build Experiences!')," +
		"('frc1304',1304,'N.O. Botics','http://1304nobotics.webs.com'," +
		" 'New Orleans','LA','USA',NULL,2004,NULL)," +
		"('frc1305',1305,'Ice Cubed','http://www.team1305.org'," +
		" 'North Bay','Ontario','Canada',NULL,2004,'Tam insulsum est ut fortasse expediat!')," +
		"('frc1306',1306,'BadgerBOTS','https://www.team1306.com/'," +
		" 'Madison','Wisconsin','USA',NULL,2004,'Building on Talented Students')," +
		"('frc1307',1307,'Robosaints','http://www.firstinspires.org/'," +
		" 'Dover','New Hampshire','USA',NULL,2004,NULL)," +
		"('frc1308',1308,'Wildcats','http://www.firstinspires.org/'," +
		" 'Cleveland','Ohio','USA',NULL,2004,NULL)," +
		"('frc1309',1309,'Diamond Eagles',NULL," +
		" 'Toronto','ON','Canada',NULL,2004,NULL)," +
		"('frc1310',1310,'Runnymede Robotics','http://www.team1310.ca'," +
		" 'Toronto','Ontario','Canada',NULL,2004,'Never Give Up!')," +
		"('frc1311',1311,'Kell Robotics','http://www.kellrobotics.org'," +
		" 'Kennesaw','Georgia','USA',NULL,2004,'Creativity Unleashed')," +
		"('frc1312',1312,'Syntax Error','http://www.team1312.org'," +
		" 'Walkerton','ON','Canada',NULL,2004,NULL)," +
		"('frc1315',1315,'Robo-Knights','http://www.roboknights.org'," +
		" 'Pacific','MO','USA',NULL,2004,NULL)," +
		"('frc1317',1317,'Digital Fusion','http://www.dfweb.org'," +
		" 'Westerville','Ohio','USA',NULL,2004,NULL)," +
		"('frc1318',1318,'Issaquah Robotics Society','http://www.team1318.org'," +
		" 'Issaquah','Washington','USA',NULL,2004,'Robots Don''t Quit!')," +
		"('frc1319',1319,'Flash','http://www.flash1319.com'," +
		" 'Mauldin','South Carolina','USA',NULL,2004,'We build great robots by first building great students.')," +
		"('frc1320',1320,'\"Beltway: The Learning Machine\"','http://www.berkeley.k12.sc.us/high/ths/First%20Robotics/Website%204706/home.htm'," +
		" 'St. Stephen','SC','USA',NULL,2004,NULL)," +
		"('frc1322',1322,'Genesee Robotics Area Youth Team (GRAYT)','http://team1322.org/'," +
		" 'Fenton','Michigan','USA',NULL,2004,'FEAR OUR BUBBLES')," +
		"('frc1323',1323,'MadTown Robotics','http://www.team1323.com'," +
		" 'Madera','California','USA',NULL,2004,'Make A Difference!')," +
		"('frc1324',1324,'Sporks','http://www.verdevalleyrobotics.org'," +
		" 'Sedona','AZ','USA',NULL,2004,NULL)," +
		"('frc1325',1325,'Inverse Paradox','http://www.team1325.com/'," +
		" 'Mississauga','Ontario','Canada',NULL,2004,'We make the impossible, possible')," +
		"('frc1326',1326,'Cy-Ridge Robotics','http://www.cyridgerobotics.com'," +
		" 'Houston','TX','USA',NULL,2004,NULL)," +
		"('frc1327',1327,'SBOTZ','http://sbotz.org'," +
		" 'South Bend','IN','USA',NULL,2004,NULL)," +
		"('frc1329',1329,'ROBOREBELS','http://www.roborebels.com/'," +
		" 'St. Louis','Missouri','USA',NULL,2004,NULL)," +
		"('frc1330',1330,'Quiet Riot','http://team1330.com'," +
		" 'Toronto','ON','Canada',NULL,2004,NULL)," +
		"('frc1331',1331,'Broot Force','http://www.hhills.mccsc.edu/web%20stuff/website.htm'," +
		" 'Bloomington','IN','USA',NULL,2004,NULL)," +
		"('frc1332',1332,'Swift','http://www.pvrobotics1332.weebly.com'," +
		" 'Collbran','Colorado','USA',NULL,2004,NULL)," +
		"('frc1334',1334,'Red Devils','http://www.1334.ca'," +
		" 'Oakville','Ontario','Canada',NULL,2004,'Where science takes action')," +
		"('frc1335',1335,'Cognitive Hazard','http://www.team1335.org'," +
		" 'Raymond','NH','USA',NULL,2004,NULL)," +
		"('frc1336',1336,'The Untouchables','http://www.lexington1.net/wkh/Robotics/index.htm'," +
		" 'Lexington','SC','USA',NULL,2004,NULL)," +
		"('frc1337',1337,'Team 1337',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc1338',1338,'Birds of Prey',NULL," +
		" 'Aurora','CO','USA',NULL,2004,NULL)," +
		"('frc1339',1339,'AngelBotics','http://www.angelbotics.com'," +
		" 'Denver','Colorado','USA',NULL,2004,'hey lo lo hey lo lo hey')," +
		"('frc1340',1340,'Spartan Robotics','http://www.adamsrobotics.org'," +
		" 'Queens','NY','USA',NULL,NULL,NULL)," +
		"('frc1341',1341,'The Renegades',NULL," +
		" 'Sarasota','FL','USA',NULL,2004,NULL)," +
		"('frc1342',1342,'Lord of the Bots','http://www.lordofthebots.org'," +
		" 'Seattle','WA','USA',NULL,2004,NULL)," +
		"('frc1343',1343,'DV ROBOTICS',NULL," +
		" 'Phoenix','AZ','USA',NULL,2004,NULL)," +
		"('frc1345',1345,'Platinum Dragons','http://www.myschoolpages.com/schools/stranahanhs/magnet.cfm?subpage=489964'," +
		" 'Ft. Lauderdale','FL','USA',NULL,2004,NULL)," +
		"('frc1346',1346,'Trobotics','http://www.trobotics.ca'," +
		" 'Vancouver','BC','Canada',NULL,2004,NULL)," +
		"('frc1347',1347,'E-Robotics','http://www.elmwoodedge.com/Robotics/Pages/pg_flashindex.html'," +
		" 'Ottawa','ON','Canada',NULL,2004,NULL)," +
		"('frc1348',1348,'Electric Anarchy','http://www.KennedyRobotics.com'," +
		" 'Denver','CO','USA',NULL,2004,NULL)," +
		"('frc1349',1349,'Inter-tribal Robotics Group',NULL," +
		" 'Flandreau','SD','USA',NULL,2004,NULL)," +
		"('frc1350',1350,'The Rambots','http://rambots.net'," +
		" 'Providence','Rhode Island','USA',NULL,2004,'Rambots are what Rambots Do')," +
		"('frc1351',1351,'TKO','http://www.amhsrobotics.com/'," +
		" 'San Jose','California','USA',NULL,2004,'Tenacity, Knowledge, Opportunity')," +
		"('frc1352',1352,'Huskie Robotics',NULL," +
		" 'Stratford','ON','Canada',NULL,2004,NULL)," +
		"('frc1353',1353,'The Spartans','http://www.lorneparkrobotics.com'," +
		" 'Mississauga','ON','Canada',NULL,2004,NULL)," +
		"('frc1355',1355,'Stallions','http://www.amdsb.ca/Madill/robotics/iron_stallions.html'," +
		" 'Wingham','ON','Canada',NULL,2004,NULL)," +
		"('frc1356',1356,'Écoles secondaires l''Essor et Lajeunesse','http://www.www.com'," +
		" 'Tecumseh','ON','Canada',NULL,2004,NULL)," +
		"('frc1357',1357,'High Voltage','http://tvrobotics.googlepages.com'," +
		" 'Loveland','CO','USA',NULL,2004,NULL)," +
		"('frc1358',1358,'The Generals','http://www.levittownschools.com'," +
		" 'Levittown','NY','USA',NULL,2004,NULL)," +
		"('frc1359',1359,'Scalawags','http://www.scalawags.org'," +
		" 'Lebanon','Oregon','USA',NULL,2004,'Prepare to be boarded')," +
		"('frc1360',1360,'Orbit Robotics','http://www.1360.ca'," +
		" 'Oakville','Ontario','Canada',NULL,2004,NULL)," +
		"('frc1361',1361,'Nightmare Robotics','http://www.firstinspires.org/'," +
		" 'Colorado Springs','Colorado','USA',NULL,2004,'?')," +
		"('frc1364',1364,'Team 1364',NULL," +
		" 'Kennesaw','GA','USA',NULL,2004,NULL)," +
		"('frc1365',1365,'The Bot Docs','http://www.thesouthsidesurgeons.freeservers.com'," +
		" 'Chicago','IL','USA',NULL,2004,NULL)," +
		"('frc1366',1366,'Roughriders',NULL," +
		" 'Newark','NJ','USA',NULL,2004,NULL)," +
		"('frc1367',1367,'Blue Bears',NULL," +
		" 'Newark','NJ','USA',NULL,2004,NULL)," +
		"('frc1368',1368,'Perpetual Motion','http://www.firstbots.org'," +
		" 'Clearwater','FL','USA',NULL,2004,NULL)," +
		"('frc1369',1369,'Minotaur','http://middletonrobotics.com/'," +
		" 'Tampa','Florida','USA',NULL,2004,'Where Engineers are Born!')," +
		"('frc1370',1370,'Thermogenesis','http://www.team1370.org'," +
		" 'Middletown','Delaware','USA',NULL,2004,'Construimus.  Certamus.  Movemus.')," +
		"('frc1371',1371,'Cosmic Gold','http://www.douglasshighastros.com'," +
		" 'Atlanta','GA','USA',NULL,2004,NULL)," +
		"('frc1372',1372,'Lambda^3','http://lambdacubed.com'," +
		" 'San Diego','CA','USA',NULL,2004,NULL)," +
		"('frc1373',1373,'Spontaneous Combustion','http://www.eosrobotics.org'," +
		" 'Storrs','CT','USA',NULL,2004,NULL)," +
		"('frc1374',1374,'Amped Up',NULL," +
		" 'Oakville','Ontario','Canada',NULL,2004,NULL)," +
		"('frc1375',1375,'The Drifters','http://www.team1375.com'," +
		" 'Aurora','CO','USA',NULL,2004,NULL)," +
		"('frc1376',1376,'The Spaceballs','http://www.geocities.com/teamspaceballs/home.html'," +
		" 'Snellville','GA','USA',NULL,2004,NULL)," +
		"('frc1377',1377,'Adams 12 Aliens','http://www.team1377.com'," +
		" 'Thornton','CO','USA',NULL,2004,NULL)," +
		"('frc1378',1378,'Hilo Viking Robotics','http://www.hilovikingrobotics.com'," +
		" 'Hilo','Hawaii','USA',NULL,2004,'#WSF (Work/Safety/Fun)')," +
		"('frc1379',1379,'Gear Devils','http://www.geardevils.org'," +
		" 'Norcross','GA','USA',NULL,2004,NULL)," +
		"('frc1380',1380,'Team 1380','http://www.berkmarteched.com'," +
		" 'Lilburn','GA','USA',NULL,2004,NULL)," +
		"('frc1382',1382,'ETEP Team','http://team1382.com.br'," +
		" 'Sao Jose dos Campos','São Paulo','Brazil',NULL,2004,NULL)," +
		"('frc1384',1384,'Vineland High School Fighting Clan','http://www.vinelandrobotics.tk'," +
		" 'Vineland','NJ','USA',NULL,2004,NULL)," +
		"('frc1385',1385,'The Brainstormers','http://www.wcirobotics.com'," +
		" 'Waterloo','ON','Canada',NULL,2004,NULL)," +
		"('frc1386',1386,'The Trobots','http://www.trobots.com'," +
		" 'Canton','OH','USA',NULL,2004,NULL)," +
		"('frc1387',1387,'Rudy''s Rangers',NULL," +
		" 'Fair Oaks','CA','USA',NULL,2004,NULL)," +
		"('frc1388',1388,'Eagle Robotics','http://www.eaglerobotics.com'," +
		" 'Arroyo Grande','California','USA',NULL,2004,'Engage, Challenge, Inspire')," +
		"('frc1389',1389,'The Body Electric ','http://www.team1389.org'," +
		" 'Bethesda','Maryland','USA',NULL,2004,'Building Leaders and Community One Robot At A Time')," +
		"('frc1390',1390,'MEKHEADS','http://mekheads1390.webs.com'," +
		" 'St. Cloud & Harmony','FL','USA',NULL,2004,NULL)," +
		"('frc1391',1391,'The Metal Moose','http://www.metalmoose.org'," +
		" 'West Chester','Pennsylvania','USA',NULL,2004,'the moose is loose')," +
		"('frc1392',1392,'KINEMATRIX',NULL," +
		" 'Ajax','ON','Canada',NULL,2004,NULL)," +
		"('frc1393',1393,'Nordic Legions','http://www.cecrobotics.com'," +
		" 'Albuquerque','NM','USA',NULL,2004,NULL)," +
		"('frc1394',1394,'The Juggernaut','http://www.masteryrobotics.org'," +
		" 'Philadelphia','PA','USA',NULL,2004,NULL)," +
		"('frc1395',1395,'Los Picachos',NULL," +
		" 'Commerce City','CO','USA',NULL,2004,NULL)," +
		"('frc1396',1396,'Pyrobots','http://pyrobots.org/'," +
		" 'Staten Island','New York','USA',NULL,2004,'Keep it simple')," +
		"('frc1397',1397,'Oh! know','http://www.ajaxhs.com'," +
		" 'Ajax','ON','Canada',NULL,2004,NULL)," +
		"('frc1398',1398,'Robo-Raiders','http://tinyurl.com/team1398'," +
		" 'Columbia','South Carolina','USA',NULL,2004,'We build together and achieve together.  It''s just a REAL thing.')," +
		"('frc1401',1401,'Mekanicats','http://www.geocities.com/mechanikatz/Mechanikatz.html'," +
		" 'Naucalpan','MEX','Mexico',NULL,2004,NULL)," +
		"('frc1402',1402,'Freedom FORCE',NULL," +
		" 'Orlando','FL','USA',NULL,2004,NULL)," +
		"('frc1403',1403,'Cougar Robotics','http://www.cougarrobotics.com/'," +
		" 'Skillman','New Jersey','USA',NULL,2004,NULL)," +
		"('frc1404',1404,'Shocks',NULL," +
		" 'Toronto','ON','Canada',NULL,2004,NULL)," +
		"('frc1405',1405,'Finney Falcons','http://team1405robotics.com/index.html'," +
		" 'Penfield','New York','USA',NULL,2004,NULL)," +
		"('frc1406',1406,'Team Robius',NULL," +
		" 'Roswell','GA','USA',NULL,2004,NULL)," +
		"('frc1407',1407,'The ElectroCats','http://www.ahstechnology.com'," +
		" 'Winder','GA','USA',NULL,2004,NULL)," +
		"('frc1408',1408,'The Saintinators',NULL," +
		" 'Edgewater','CO','USA',NULL,2004,NULL)," +
		"('frc1409',1409,'Team 1409',NULL," +
		" 'Great Barrington','MA','USA',NULL,2004,NULL)," +
		"('frc1410',1410,'The Kraken','http://www.frc1410.org'," +
		" 'Denver','Colorado','USA',NULL,2004,'Release the Kraken!')," +
		"('frc1411',1411,'Trojens','http://www.tfcrobotics.tk'," +
		" 'Brampton','ON','Canada',NULL,2004,NULL)," +
		"('frc1412',1412,'The Comet Warriors','http://site2.hackensackschools.org'," +
		" 'Hackensack','NJ','USA',NULL,2004,NULL)," +
		"('frc1413',1413,'Skrappy 1413','http://www.skrappy1413.org'," +
		" 'Skipwith','Virginia','USA',NULL,2004,'Gracious Professionalism')," +
		"('frc1414',1414,'IHOT','http://www.ihotrobotics.com'," +
		" 'Atlanta','Georgia','USA',NULL,2004,'Who''s Hot, iHOT')," +
		"('frc1415',1415,'The Flying Pumpkins','http://www.picturemewords.com/firstrobotics'," +
		" 'Columbus','GA','USA',NULL,2004,NULL)," +
		"('frc1416',1416,'Overlake Robotics Branch','http://www.overlake.org/Robotics/index.htm'," +
		" 'Redmond','WA','USA',NULL,2004,NULL)," +
		"('frc1417',1417,'Titans','http://www.rcs.k12.va.us/hvhs/activity/First%20robotics%202004.htm'," +
		" 'Roanoke','VA','USA',NULL,2004,NULL)," +
		"('frc1418',1418,'Vae Victis','http://1418.team'," +
		" 'Falls Church','Virginia','USA',NULL,2004,'Vae Victis')," +
		"('frc1419',1419,'Rustic Roboteers','http://courseweb.interarts.ca/course/view.php?id=9'," +
		" 'Courtice','ON','Canada',NULL,2004,NULL)," +
		"('frc1420',1420,'Dept. of Exceptional Robots',NULL," +
		" 'Baltimore','MD','USA',NULL,2004,NULL)," +
		"('frc1421',1421,'Team Chaos','https://www.facebook.com/FRCTEAMCHAOS1421'," +
		" 'Picayune','Mississippi','USA',NULL,2004,'The Keys to knowledge and inspiration lie within the Chaos of ourselves.')," +
		"('frc1422',1422,'The Neon Knights','http://team1422.com'," +
		" 'Fresno','California','USA',NULL,2004,'Look to the neon lights, it''s  where you''ll find The Neon Knights!')," +
		"('frc1425',1425,'Error Code Xero','http://www.wilsonvillerobotics.com'," +
		" 'Wilsonville','Oregon','USA',NULL,2004,'Building Robots...Building People')," +
		"('frc1426',1426,'Cougartronics',NULL," +
		" 'Las Cruces','NM','USA',NULL,2004,NULL)," +
		"('frc1427',1427,'Team 1427',NULL," +
		" 'Reno','NV','USA',NULL,2004,NULL)," +
		"('frc1428',1428,'T-Birds',NULL," +
		" 'Zuni','NM','USA',NULL,2004,NULL)," +
		"('frc1429',1429,'TEAM KAOS','http://www.teamkaosrobotics.com'," +
		" 'Galena Park','TX','USA',NULL,2004,NULL)," +
		"('frc1430',1430,'WRONG Doers','http://www.wrong.tk'," +
		" 'Anchorage','AK','USA',NULL,2004,NULL)," +
		"('frc1432',1432,'Metal Beavers','http://team1432.com'," +
		" 'Portland','Oregon','USA',NULL,2004,'There''s a time for science and a time for hitting stuff with hammers')," +
		"('frc1433',1433,'Tiger Techies',NULL," +
		" 'Newberg','OR','USA',NULL,2004,NULL)," +
		"('frc1435',1435,'The pi-Rats','http://phs.psdr3.org'," +
		" 'Maryland Heights','MO','USA',NULL,2004,NULL)," +
		"('frc1436',1436,'Jackets','http://www.pltw-fmhs.org'," +
		" 'Fort Mill','SC','USA',NULL,2004,NULL)," +
		"('frc1437',1437,'Robot Rams','http://www.robotrams1437.org'," +
		" 'St. Louis','MO','USA',NULL,2004,NULL)," +
		"('frc1438',1438,'The A Team','http://anaheim.auhsd.k12.ca.us/robotics/?rn=4987'," +
		" 'Anaheim','California','USA',NULL,2004,'I pity thy fool!')," +
		"('frc1439',1439,'Mays RBOT',NULL," +
		" 'Atlanta','GA','USA',NULL,2004,NULL)," +
		"('frc1440',1440,'Warriors','http://www.lopatta.us/robotics'," +
		" 'Savannah','GA','USA',NULL,2004,NULL)," +
		"('frc1441',1441,'RAVENS',NULL," +
		" 'Bellevue','WA','USA',NULL,2004,NULL)," +
		"('frc1442',1442,'Ehecatl','http://www.usuarios.lycos.es/ehecatlrobotics'," +
		" 'Juarez','CHH','Mexico',NULL,2004,NULL)," +
		"('frc1444',1444,'Lightning 1444','http://www.first1444.com'," +
		" 'Saint Louis','Missouri','USA',NULL,2004,NULL)," +
		"('frc1446',1446,'Robo Knights','http://www.firstinspires.org/'," +
		" 'Washington','District of Columbia','USA',NULL,2004,NULL)," +
		"('frc1447',1447,'Panther Robotics','http://www.dndtech.us/clhs'," +
		" 'Center Line','MI','USA',NULL,2004,NULL)," +
		"('frc1448',1448,'Parsons Vikings','http://www.vikingnet.net/phs/robotics'," +
		" 'Parsons','Kansas','USA',NULL,2004,NULL)," +
		"('frc1449',1449,'Robocats','http://www.wcboe.k12.md.us/custom_pages/430/tabler/robotics06/index.htm'," +
		" 'Williamsport','MD','USA',NULL,2004,NULL)," +
		"('frc1450',1450,'XQ Robotix','http://www.xq-robotix.com'," +
		" 'Rochester','New York','USA',NULL,2004,'To Achieve is to Accomplish Your Dreams...')," +
		"('frc1451',1451,'RoboKnights','http://www.triad.madison.k12.il.us/robotics/index.htm'," +
		" 'Troy','IL','USA',NULL,2004,NULL)," +
		"('frc1452',1452,'Omnicats','http://team1452.com'," +
		" 'Los Angeles','California','USA',NULL,2004,'Student Designed,  Student Built.')," +
		"('frc1453',1453,'RoboCubs',NULL," +
		" 'Los Angeles','CA','USA',NULL,2004,NULL)," +
		"('frc1455',1455,'NEC','http://n/a'," +
		" 'Wyncote','PA','USA',NULL,2004,NULL)," +
		"('frc1456',1456,'GrizzlyBots',NULL," +
		" 'Chandler','AZ','USA',NULL,2004,NULL)," +
		"('frc1457',1457,'TRONN','http://team1457.cecnv.org/Welcome.html'," +
		" 'Reno','NV','USA',NULL,2004,NULL)," +
		"('frc1458',1458,'Red Tie Robotics','http://www.team1458.com'," +
		" 'Danville','California','USA',NULL,2004,'Take pride in your work')," +
		"('frc1459',1459,'BearCats','http://www.pilotpointisd.com/PPISD/Pphs/Robotics/index.htm'," +
		" 'Pilot Point','TX','USA',NULL,2004,NULL)," +
		"('frc1460',1460,'Robolution','http://www.alief.isd.tenet.edu'," +
		" 'Houston','TX','USA',NULL,2004,NULL)," +
		"('frc1461',1461,'Golden Hawks',NULL," +
		" 'Miami Springs','FL','USA',NULL,2004,NULL)," +
		"('frc1462',1462,'Tekeez','http://www.team1462.org'," +
		" 'New York','NY','USA',NULL,2004,NULL)," +
		"('frc1463',1463,'Transformers',NULL," +
		" 'Burke','VA','USA',NULL,2004,NULL)," +
		"('frc1464',1464,'Wolves','http://www.team1464.org%20'," +
		" 'Westminster','MD','USA',NULL,2004,NULL)," +
		"('frc1465',1465,'Code Breakers',NULL," +
		" 'Bronx','NY','USA',NULL,2004,NULL)," +
		"('frc1466',1466,'Webb Robotics','http://www.webbrobotics.org'," +
		" 'Knoxville','Tennessee','USA',NULL,2004,'\"principes non homines\"')," +
		"('frc1467',1467,'HSRO','http://www.hsro.us'," +
		" 'Midlothian','VA','USA',NULL,2004,NULL)," +
		"('frc1468',1468,'Hicksville J-Birds','http://www.firstinspires.org/'," +
		" 'Hicksville','New York','USA',NULL,2004,'Heart.....builds robots')," +
		"('frc1469',1469,'The Labcoats','http://www.smithfirstteam.freehomepage.com'," +
		" 'Greensboro','NC','USA',NULL,2004,NULL)," +
		"('frc1470',1470,'DA Bears','http://10.17.1.6/library/library.html'," +
		" 'Pittsburgh','PA','USA',NULL,2004,NULL)," +
		"('frc1472',1472,'GENESIS','http://x-ile.info/genesis/home.php'," +
		" 'Baton Rouge','LA','USA',NULL,2004,NULL)," +
		"('frc1474',1474,'Titans','https://frc1474.weebly.com/'," +
		" 'Tewksbury','Massachusetts','USA',NULL,2004,NULL)," +
		"('frc1475',1475,'LOOSE CONNECTIONS','http://www.looseconnections.dynu.com'," +
		" 'Leo','IN','USA',NULL,2004,NULL)," +
		"('frc1476',1476,'Der Eisenflug',NULL," +
		" 'Detroit','MI','USA',NULL,2004,NULL)," +
		"('frc1477',1477,'Texas Torque','http://www.texastorque.org'," +
		" 'Conroe','Texas','USA',NULL,2004,'Building futures, one robot at a time.')," +
		"('frc1478',1478,'The OZ Men','http://team1478.ozautomation.com'," +
		" 'Seneca','KS','USA',NULL,2004,NULL)," +
		"('frc1479',1479,'SWAT Team','http://www.nsbe-hsc.org'," +
		" 'Houston','TX','USA',NULL,2004,NULL)," +
		"('frc1480',1480,'Robatos Locos','http://hs.houstonisd.org/DavisHS/robotoslocos/home.html'," +
		" 'Houston','TX','USA',NULL,2004,NULL)," +
		"('frc1481',1481,'The Riveters','http://1481riveters.com/'," +
		" 'Farmington','Michigan','USA',NULL,2004,'We can do it!')," +
		"('frc1482',1482,'Ghosts','http://www.frc1482.ca'," +
		" 'Calgary','Alberta','Canada',NULL,2004,NULL)," +
		"('frc1484',1484,'Hoggzilla','http://www.hoggzilla.com'," +
		" 'Houston','TX','USA',NULL,2004,NULL)," +
		"('frc1485',1485,'Team 1485',NULL," +
		" 'Houston','TX','USA',NULL,2004,NULL)," +
		"('frc1486',1486,'Knight Robotics','http://www.knightsrobotics.ca'," +
		" 'Calgary','AB','Canada',NULL,2004,NULL)," +
		"('frc1489',1489,'Blood Sweat Gears',NULL," +
		" 'Birmingham','MI','USA',NULL,2004,NULL)," +
		"('frc1490',1490,'Sparta''s Rockets',NULL," +
		" 'Houston','TX','USA',NULL,2004,NULL)," +
		"('frc1492',1492,'Team CAUTION','http://www.teamcautionrobotics.org'," +
		" 'Tempe','Arizona','USA',NULL,2004,'The 10th time is the charm.')," +
		"('frc1493',1493,' Falcons','http://www.firstteam1493.com/index.html'," +
		" 'Albany','New York','USA',NULL,2004,NULL)," +
		"('frc1495',1495,'Red Devils','http://ttarkanick.wixsite.com/avongroverobotics/robotics-catagories'," +
		" 'West Grove','Pennsylvania','USA',NULL,2004,NULL)," +
		"('frc1496',1496,'IGUANA #1496',NULL," +
		" 'Quito','P','Ecuador',NULL,2004,NULL)," +
		"('frc1497',1497,'Ram Robotics','http://www.englewoodhighschool.org'," +
		" 'Jacksonville','FL','USA',NULL,2004,NULL);";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_3 = SQL_INSERT_TEAMS +
		"('frc1500',1500,'Metal-Morphose','http://www.hudsonvillerobotics.us'," +
		" 'Hudsonville','MI','USA',NULL,2005,NULL)," +
		"('frc1501',1501,'Team THRUST','http://huntingtonrobotics.org'," +
		" 'Huntington','Indiana','USA',NULL,2005,'We build robots and careers')," +
		"('frc1502',1502,'Technical Difficulties','https://www.chelsearobotics.org/'," +
		" 'Chelsea','Michigan','USA',NULL,2005,'We make easy things look difficult every day.')," +
		"('frc1503',1503,'Spartonics','http://www.spartonics.org'," +
		" 'Niagara Falls','ON','Canada',NULL,2005,NULL)," +
		"('frc1504',1504,'Desperate Penguins','http://team1504.com'," +
		" 'Okemos','Michigan','USA',NULL,2005,'Prior Planning Prevents Poor Performance')," +
		"('frc1505',1505,'Roybotics','http://www.roybotics.com'," +
		" 'Roy','UT','USA',NULL,2005,NULL)," +
		"('frc1506',1506,'Metal Muscle','http://www.metalmuscle.org'," +
		" 'Flint','Michigan','USA',NULL,2005,NULL)," +
		"('frc1507',1507,'Warlocks','http://www.warlocks1507.com'," +
		" 'Lockport','New York','USA',NULL,2005,NULL)," +
		"('frc1508',1508,'RoboWizards',NULL," +
		" 'Detroit','MI','USA',NULL,2005,NULL)," +
		"('frc1509',1509,'Screamin Eagles','http://www.screamineagles.org'," +
		" 'Ferndale','MI','USA',NULL,2005,NULL)," +
		"('frc1510',1510,'Wildcats','http://www.bpsrobotics.org/1510'," +
		" 'Beaverton','Oregon','USA',NULL,2005,'Student designed, student built, mentor advised')," +
		"('frc1511',1511,'Rolling Thunder','http://www.penfieldrobotics.com/'," +
		" 'Penfield','New York','USA',NULL,2005,'The Thunder Just Keeps Getting Louder!')," +
		"('frc1512',1512,'The Big Red','http://robotics.sps.edu'," +
		" 'Concord','New Hampshire','USA',NULL,2005,NULL)," +
		"('frc1513',1513,'Cyclones','https://web.springisd.org/FirstRobotics'," +
		" 'Spring','TX','USA',NULL,2005,NULL)," +
		"('frc1514',1514,'The Vikes','http://www.team1514.com'," +
		" 'Toronto','ON','Canada',NULL,2005,NULL)," +
		"('frc1515',1515,'MorTorq','http://team1515.org'," +
		" 'Beverly Hills','California','USA',NULL,2005,'Mor Power, Mor Teamwork, Mor Ingenuity, MorTorq')," +
		"('frc1516',1516,'Grizzlies','http://tetrabot.com'," +
		" 'San Ramon','California','USA',NULL,2005,'If there is a harder way to do it, we can find it.')," +
		"('frc1517',1517,'The Lumberjacks','https://sites.google.com/bishopbrady.edu/bbhs-frc-robotics-1517'," +
		" 'Concord','New Hampshire','USA',NULL,2005,'They''ve Gone to Plaid!')," +
		"('frc1518',1518,'Raider Robotics','http://www.palmacfirst.com'," +
		" 'Palmyra','New York','USA',NULL,2005,'\"You don''t have to be a genius, you just have to be pliable\"')," +
		"('frc1519',1519,'Mechanical Mayhem','http://www.mechanicalmayhem.org/'," +
		" 'Milford','New Hampshire','USA',NULL,2005,'Let the MAYHEM begin!')," +
		"('frc1520',1520,'Omega - 13','http://omega1520.googlepages.com'," +
		" 'New York','NY','USA',NULL,2005,NULL)," +
		"('frc1522',1522,'DOTM - Defenders of the Multiverse  : Hanover High School','http://www.1522DOTM.org'," +
		" 'Mechanicsville','Virginia','USA',NULL,2005,'There is no off season')," +
		"('frc1523',1523,'MARS (Mega Awesome Robotic Systems)','http://www.marsbot.org'," +
		" 'Palm Beach Gardens','Florida','USA',NULL,2005,'Work hard; think harder.')," +
		"('frc1524',1524,'Gladiators','http://www.cttech.org/GOODWIN/student/robotics-club/index.htm'," +
		" 'New Britain','CT','USA',NULL,2005,NULL)," +
		"('frc1525',1525,'Deerfield High School Warriors','http://www.warbots1525.com'," +
		" 'Deerfield','IL','USA',NULL,2005,NULL)," +
		"('frc1527',1527,'Bionic Battalion','http://team1527.xlx2.com'," +
		" 'El Cajon','CA','USA',NULL,2005,NULL)," +
		"('frc1528',1528,'MTR (Monroe Trojan Robotics)','http://www.monroetrojanrobotics.com/'," +
		" 'Monroe','Michigan','USA',NULL,2005,'\"Make it happen\"')," +
		"('frc1529',1529,'CyberCards','http://www.southportrobotics.org/'," +
		" 'Indianapolis','Indiana','USA',NULL,2005,'We build our own friends.  #FriendsWithRobots')," +
		"('frc1530',1530,'Vikings',NULL," +
		" 'Richmond','VA','USA',NULL,2005,NULL)," +
		"('frc1531',1531,'Team Lake Effect',NULL," +
		" 'Mexico','NY','USA',NULL,2005,NULL)," +
		"('frc1532',1532,'Tech Ops',NULL," +
		" 'Cleveland','OH','USA',NULL,2005,NULL)," +
		"('frc1533',1533,'Triple Strange','http://www.ecgrobotics.org/'," +
		" 'Greensboro','North Carolina','USA',NULL,2005,'Unusually good at the impossible.')," +
		"('frc1534',1534,'Abramson Robotic Soldiers','http://www.winkinc.com/ars'," +
		" 'New Orleans','LA','USA',NULL,2005,NULL)," +
		"('frc1535',1535,'Knights of Alloy','http://www.knightsofalloy.ca'," +
		" 'Sault Ste Marie','ON','Canada',NULL,2005,NULL)," +
		"('frc1537',1537,'Robotic Knights','http://www.Teched101.com'," +
		" 'Uniondale','New York','USA',NULL,2005,NULL)," +
		"('frc1538',1538,'The Holy Cows','http://www.team1538.com'," +
		" 'San Diego','California','USA',NULL,2005,'Leading the Herd')," +
		"('frc1539',1539,'Clover Robotics','http://team1539cloverrobo.wixsite.com/cloverrobotics'," +
		" 'Clover','South Carolina','USA',NULL,2005,'If at first you don''t succeed, call it version 1.0')," +
		"('frc1540',1540,'Flaming Chickens','http://www.team1540.org/'," +
		" 'Portland','Oregon','USA',NULL,2005,'Made from scratch!')," +
		"('frc1541',1541,'MidloCanics','https://sites.google.com/site/midlorobotics/'," +
		" 'Midlothian','Virginia','USA',NULL,2005,'Success comes in CANS!')," +
		"('frc1542',1542,'Heart of London',NULL," +
		" 'London','ENG','Kingdom',NULL,2005,NULL)," +
		"('frc1543',1543,'The Riddler Revolution','http://www.phs-robotics.com'," +
		" 'Kissimmee','FL','USA',NULL,2005,NULL)," +
		"('frc1544',1544,'Arctic Ice Bears','http://bartlettrobotics.com'," +
		" 'Anchorage','Alaska','USA',NULL,2005,'')," +
		"('frc1545',1545,'DSF Robotics','http://teachers.henrico.k12.va.us/freeman/foltz_p/first.html'," +
		" 'Richmond','VA','USA',NULL,2005,NULL)," +
		"('frc1546',1546,'Chaos, Inc.','http://www.firstinspires.org/'," +
		" 'Baldwin','New York','USA',NULL,2005,'Together Everyone Achieves More')," +
		"('frc1547',1547,'\"Where''s Waldo?\"','http://waldo.trafalgarcastle.ca'," +
		" 'Whitby','Ontario','Canada',NULL,2005,'Girls Do!')," +
		"('frc1548',1548,'Highlanders','http://robotics.hthstudentwebsites.org'," +
		" 'Anchorage','AK','USA',NULL,2005,NULL)," +
		"('frc1549',1549,'Fire TraXX','http://www.myspace.com/firetraxx1549'," +
		" 'Ann Arbor','MI','USA',NULL,2005,NULL)," +
		"('frc1550',1550,'The Botics Squad','http://csce.xula.edu/~aedwards/opw'," +
		" 'New Orleans','LA','USA',NULL,2005,NULL)," +
		"('frc1551',1551,'The Grapes of Wrath','http://www.TheGrapesofWrath1551.org'," +
		" 'Naples','New York','USA',NULL,2005,'Cogito Ergo Robots')," +
		"('frc1552',1552,'CougarBots','http://niwothighrobotics.com/ '," +
		" 'Niwot','CO','USA',NULL,2005,NULL)," +
		"('frc1553',1553,'KC Robotics Team','http://www.kcroboticsteam.org/'," +
		" 'Lugoff','South Carolina','USA',NULL,2005,'Changing the Way We Build the Future')," +
		"('frc1554',1554,'Oceanside Robotics','https://sites.google.com/oceansideschools.org/robotics/'," +
		" 'Oceanside','New York','USA',NULL,2005,'Honor Above All')," +
		"('frc1555',1555,'Cryonics','http://www.1555.team/'," +
		" 'Elkhart','Indiana','USA',NULL,2005,NULL)," +
		"('frc1556',1556,'Robo Raiders','http://www.geocities.com/robotics_academy'," +
		" 'North Hills','CA','USA',NULL,2005,NULL)," +
		"('frc1557',1557,'12 Volt Bolt','http://12voltbolt.com/'," +
		" 'Eustis','Florida','USA',NULL,2005,'We''re NUTS!')," +
		"('frc1558',1558,'ACCIdent','http://www.accirobotics.co.nr'," +
		" 'Toronto','ON','Canada',NULL,2005,NULL)," +
		"('frc1559',1559,'Devil Tech','http://www.victorrobotics.org'," +
		" 'Victor','New York','USA',NULL,2005,'Work smart, work fast')," +
		"('frc1560',1560,'RoboPanthers','http://www.pinewoodrobotics.com'," +
		" 'Los Altos Hills','California','USA',NULL,2005,'')," +
		"('frc1561',1561,'ROBODUCKS','http://www.roboducks.org'," +
		" 'Oklahoma City','Oklahoma','USA',NULL,2005,'Learn, Build, Inspire')," +
		"('frc1562',1562,'RMSEL Robotics','http://www.rmselrobotics.com'," +
		" 'Denver','CO','USA',NULL,2005,NULL)," +
		"('frc1563',1563,'ROBO-CATS',NULL," +
		" 'Newark','NJ','USA',NULL,2005,NULL)," +
		"('frc1564',1564,'J.A.G.S.','http://robotics.ayjackson.ca'," +
		" 'Toronto','ON','Canada',NULL,2005,NULL)," +
		"('frc1565',1565,'Think Tank Technologies (T3)','http://jhss.wrdsb.edu.on.ca/robotics/index.html'," +
		" 'Cambridge','ON','Canada',NULL,2005,NULL)," +
		"('frc1566',1566,'AMMOKNIGHTS','https://first1566ammoknigh.wixsite.com/ammoknights1566'," +
		" 'Idaho Falls','Idaho','USA',NULL,2005,'Believe')," +
		"('frc1567',1567,'Shock-a-Bots','http://robotics.easthighschool.net'," +
		" 'Rochester','NY','USA',NULL,2005,NULL)," +
		"('frc1568',1568,'Mechanicatz','http://www.team1568.org'," +
		" 'Pawtucket','RI','USA',NULL,2005,NULL)," +
		"('frc1569',1569,'Haywire Robotics','http://www.sd25.us/sites/haywirerobotics1569/index.html'," +
		" 'Pocatello','Idaho','USA',NULL,2005,'Inspiring the students of  today to become the innovators of tomorrow.')," +
		"('frc1570',1570,'Demons','http://www.demonrobotics.ca'," +
		" 'Vancouver','British Columbia','Canada',NULL,2005,'Engineering Ingenuity')," +
		"('frc1571',1571,'CALibrate Robotics','http://team1571.org'," +
		" 'Gresham','Oregon','USA',NULL,2005,'Outside the Box... Can''t get in!')," +
		"('frc1572',1572,'The Hammer Heads','http://kearnyhighrobotics.webs.com/'," +
		" 'San Diego','California','USA',NULL,2005,'\"Hammer Hammer Hammer\"')," +
		"('frc1573',1573,'Kfar Galim','http://members.lycos.co.uk/kgstars/'," +
		" 'Kfar Galim','HaZafon','Israel',NULL,2005,NULL)," +
		"('frc1574',1574,'MisCar','http://miscar1574.org'," +
		" 'Misgav','HaZafon','Israel',NULL,2005,'MisCar - Moving FIRST Forward')," +
		"('frc1575',1575,'Team 1575',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc1576',1576,'Voltrix','http://www.firstinspires.org/'," +
		" 'Tel Aviv','Tel-Aviv','Israel',NULL,2005,NULL)," +
		"('frc1577',1577,'Steampunk','http://www.steampunk1577.org'," +
		" 'Ra''anana','HaMerkaz','Israel',NULL,2005,'\"there is no life without robotics\"')," +
		"('frc1578',1578,'The Purple RoboSnails','http://www.wix.com/thepurplerobosnails/1578'," +
		" 'Tel Aviv','TA','Israel',NULL,2005,NULL)," +
		"('frc1579',1579,'Shevach-Mofet',NULL," +
		" 'Tel Aviv','TA','Israel',NULL,2005,NULL)," +
		"('frc1580',1580,' The Blue Monkeys','http://henri-ronson.ort.org.il/first/index.htm'," +
		" 'Ashkelon','HaDarom','Israel',NULL,2005,NULL)," +
		"('frc1581',1581,'Team 1581',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc1582',1582,'Dantziger','http://danciger.frihost.net'," +
		" 'Kiryat Shmona','Z','Israel',NULL,2005,NULL)," +
		"('frc1583',1583,'Ridge View Academy Rambotics','http://www.rambotics.com'," +
		" 'Watkins','Colorado','USA',NULL,2005,'Respect, Attitude, Motivation, Spirit')," +
		"('frc1584',1584,'Pirates','http://www.nedrobotics.org'," +
		" 'Nederland','Colorado','USA',NULL,2005,'Arrrrrrrgh!!!!!')," +
		"('frc1585',1585,'Scitobor Robotics','http://www.redjacketrobotics.org'," +
		" 'Shortsville','New York','USA',NULL,2005,NULL)," +
		"('frc1588',1588,'The Crunken Masters',NULL," +
		" 'Atlanta','GA','USA',NULL,2005,NULL)," +
		"('frc1589',1589,'DYNAMIC (Desgin Young New Advance Minds In Competition) BOTICS','http://www.neworleansrobotics.com/portals/kennedy'," +
		" 'New Orleans','LA','USA',NULL,2005,NULL)," +
		"('frc1590',1590,'Titaniums','http://www.firstinspires.org/'," +
		" 'Lorain','Ohio','USA',NULL,2005,NULL)," +
		"('frc1591',1591,'Greece Gladiators','http://www.greecerobotics.com/'," +
		" 'Rochester','New York','USA',NULL,2005,NULL)," +
		"('frc1592',1592,'Bionic Tigers','http://www.firstteam1592.com'," +
		" 'Cocoa','Florida','USA',NULL,2005,'The future isn’t just bright, it’s Bionic.')," +
		"('frc1593',1593,'2 Hot 2 Trot','http://www.2hot2trot1593.com'," +
		" 'New York','NY','USA',NULL,2005,NULL)," +
		"('frc1594',1594,'Double X','http://robotics.brearley.org'," +
		" 'New York','NY','USA',NULL,NULL,NULL)," +
		"('frc1595',1595,'The Dragons','http://www.1595dragons.org/'," +
		" 'Spokane','Washington','USA',NULL,2005,'Fire Up!')," +
		"('frc1596',1596,'The Instigators','https://www.saultschools.org/Page/6463'," +
		" 'Sault Sainte Marie','Michigan','USA',NULL,2005,'It''s great to be an INSTIGATOR!')," +
		"('frc1597',1597,'BxAero','http://www.bronxaerospace.org/ourpages/robotics'," +
		" 'Bronx','NY','USA',NULL,2005,NULL)," +
		"('frc1598',1598,'TeamTalon 1598','http://teamtalon1598.blogspot.com/'," +
		" 'Danville','Virginia','USA',NULL,2005,'Have Fun !!!')," +
		"('frc1599',1599,'CircuiTree','https://www.atleecircuitree.com/'," +
		" 'Mechanicsville','Virginia','USA',NULL,2005,'That''s Atlee Robotics ')," +
		"('frc1600',1600,'ROBO KINGS AND QUEENS','http://www.firstinspires.org/'," +
		" 'Brooklyn','New York','USA',NULL,2005,NULL)," +
		"('frc1601',1601,'Quantum Samurai','http://www.quantumsamurai.webs.com/'," +
		" 'Long Island City','New York','USA',NULL,2005,'Endless Possibilities')," +
		"('frc1602',1602,'CougarBots','http://www.teamfordfirst.org/team1602'," +
		" 'Detroit','MI','USA',NULL,2005,NULL)," +
		"('frc1603',1603,'Libas','http://www.liberato.com.br/first/index.html'," +
		" 'Novo Hamburgo','RS','Brazil',NULL,2005,NULL)," +
		"('frc1604',1604,'Mekheads','http://www.mekheads.org'," +
		" 'Harmony','FL','USA',NULL,2005,NULL)," +
		"('frc1605',1605,'RoboHawks',NULL," +
		" 'Toronto','ON','Canada',NULL,2005,NULL)," +
		"('frc1606',1606,'Division Dragons',NULL," +
		" 'Levittown','NY','USA',NULL,2005,NULL)," +
		"('frc1607',1607,'Rough Riders','http://www.wix.com/rooseveltli/rough-riders-robotics-1607'," +
		" 'Roosevelt','NY','USA',NULL,2005,NULL)," +
		"('frc1609',1609,'West Allied Robotics -W.A.R.',NULL," +
		" 'Ballwin','MO','USA',NULL,2005,NULL)," +
		"('frc1610',1610,'⛴ Blackwater Robotics ⛴','https://www.blackwaterrobotics.org/'," +
		" 'Franklin','Virginia','USA',NULL,2005,'A mind is a terrible thing to waste; waste it on robotics.')," +
		"('frc1611',1611,'NOS','http://www.effinghamschools.com/sehs/robotics'," +
		" 'Guyton','GA','USA',NULL,2005,NULL)," +
		"('frc1612',1612,'Robo-Sharks','http://therobosharks.com'," +
		" 'Brooksville','FL','USA',NULL,2005,NULL)," +
		"('frc1613',1613,'StARC','http://www.saintsrobotics.org'," +
		" 'San Diego','CA','USA',NULL,2005,NULL)," +
		"('frc1616',1616,'weequahic indians',NULL," +
		" 'Newark','NJ','USA',NULL,2005,NULL)," +
		"('frc1617',1617,'The Mighty Bulldogs',NULL," +
		" 'Newark','NJ','USA',NULL,2005,NULL)," +
		"('frc1618',1618,'Capital Robotics',NULL," +
		" 'Columbia','SC','USA',NULL,2005,NULL)," +
		"('frc1619',1619,'Up-A-Creek Robotics','http://www.team1619.org'," +
		" 'Longmont','Colorado','USA',NULL,2005,NULL)," +
		"('frc1620',1620,'Robolution','http://www.teamrobolution.com'," +
		" 'Pickering','ON','Canada',NULL,2005,NULL)," +
		"('frc1621',1621,'Eniac','http://www.eniac.com.br/first'," +
		" 'Guarulhos','SP','Brazil',NULL,2005,NULL)," +
		"('frc1622',1622,'Team Spyder','http://www.teamspyder.org'," +
		" 'Poway','California','USA',NULL,2005,'When in doubt throttle out')," +
		"('frc1623',1623,'Banner Bots','http://ssmrobotics.org'," +
		" 'Faribault','MN','USA',NULL,2005,NULL)," +
		"('frc1624',1624,'The Green Grinches','http://working%20on'," +
		" 'Vancouver','WA','USA',NULL,2005,NULL)," +
		"('frc1625',1625,'Winnovation','http://team1625.org/'," +
		" 'Winnebago','Illinois','USA',NULL,2005,NULL)," +
		"('frc1626',1626,'Falcon Robotics','http://www.falconroboticsteam1626.org'," +
		" 'Metuchen','New Jersey','USA',NULL,2005,NULL)," +
		"('frc1628',1628,'SENAI Mechatronics','http://www.team1628.cjb.net'," +
		" 'Caxias Do Sul','RS','Brazil',NULL,2005,NULL)," +
		"('frc1629',1629,'Garrett Coalition (GaCo)','http://www.garrettcountyschools.org/frc1629'," +
		" 'Accident','Maryland','USA',NULL,2005,'Success through Partnerships')," +
		"('frc1631',1631,'Rockin Robots','http://www.team1631.org'," +
		" 'Henderson','NV','USA',NULL,2005,NULL)," +
		"('frc1633',1633,'RoboBuffs','http://www.temperobotics.org'," +
		" 'Tempe','Arizona','USA',NULL,2005,'Once a Buffalo, always a Buffalo')," +
		"('frc1634',1634,'Wreckers','http://www.weatherlyrobotics.org'," +
		" 'Weatherly','PA','USA',NULL,2005,NULL)," +
		"('frc1635',1635,'TECHNOTICS','http://team1635.org'," +
		" 'Elmhurst','New York','USA',NULL,2005,'\"Tower Power\"')," +
		"('frc1636',1636,'Reds Robotics',NULL," +
		" 'Arvada','CO','USA',NULL,2005,NULL)," +
		"('frc1640',1640,'Sab-BOT-age','http://wiki.team1640.com'," +
		" 'Downingtown','Pennsylvania','USA',NULL,2005,'Think outside the ''bot!')," +
		"('frc1641',1641,'Where''s Waldo?','http://www.mojave-robotics.com'," +
		" 'Mojave','CA','USA',NULL,2005,NULL)," +
		"('frc1642',1642,'Techno-Cats','http://www.dhs-robotics.net/'," +
		" 'Ft. Worth','Texas','USA',NULL,2005,'Safety and fun for all')," +
		"('frc1643',1643,'Bob''s Builders in Black','http://www.tallmadge.k12.oh.us/ths_robotics/RoboWebTestPage.html'," +
		" 'Tallmadge','OH','USA',NULL,2005,NULL)," +
		"('frc1644',1644,'The Robo-Skunks','http://www.hawkinsmesa.org'," +
		" 'Los Angeles','California','USA',NULL,2005,'Focused on STEM … Committed to Improving Our Community')," +
		"('frc1645',1645,'Butte Bot Builders','http://www.kopperk9s.com'," +
		" 'Butte','MT','USA',NULL,2005,NULL)," +
		"('frc1646',1646,'Precision Guessworks','https://first1646.com/'," +
		" 'Lafayette','Indiana','USA',NULL,2005,'We don''t just build robots, we build our future!')," +
		"('frc1647',1647,'Iron Devils','http://team1647.com/'," +
		" 'Vincentown','New Jersey','USA',NULL,2005,' ')," +
		"('frc1648',1648,'G3 Robotics','http://www.g3robotics.com'," +
		" 'Atlanta','Georgia','USA',NULL,2005,'Building Robots, Building Minds')," +
		"('frc1649',1649,'Lakerbotics (Lakers)','https://www.lakerbotics.com'," +
		" 'Windermere','Florida','USA',NULL,2005,'\"I don''t know but I''ll figure it out.\"')," +
		"('frc1650',1650,'Prodigies of Technology',NULL," +
		" 'Columbia','SC','USA',NULL,2005,NULL)," +
		"('frc1651',1651,'TBA',NULL," +
		" 'Palm Beach County','FL','USA',NULL,NULL,NULL)," +
		"('frc1652',1652,'LakeView Legends','http://lakeviewlegends.us'," +
		" 'Pleasant Prairie','WI','USA',NULL,2005,NULL)," +
		"('frc1653',1653,'Washington High School RoboKatz',NULL," +
		" 'Kansas City','KS','USA',NULL,2005,NULL)," +
		"('frc1654',1654,'The Non Conformants','http://www.halthsfirst.com'," +
		" 'Holland','MI','USA',NULL,2005,NULL)," +
		"('frc1655',1655,'Screaming Eagles',NULL," +
		" 'Marion','VA','USA',NULL,2005,NULL)," +
		"('frc1656',1656,'Angry Scotsmen','http://www.haverfordrobotics.com'," +
		" 'Haverford','PA','USA',NULL,2005,NULL)," +
		"('frc1657',1657,'Hamosad','http://www.hamosad1657.net'," +
		" 'Kibutz E''in Shemer','HaZafon','Israel',NULL,2005,NULL)," +
		"('frc1658',1658,'Tech Heads','http://southtechhigh.org/student/shops/cis/2007/'," +
		" 'St. Louis','Missouri','USA',NULL,2005,NULL)," +
		"('frc1660',1660,'Harlem Knights','http://www.hk1660.org'," +
		" 'New York','New York','USA',NULL,2005,'Without struggle there is no progress')," +
		"('frc1661',1661,'Griffitrons','http://www.1661robotics.com'," +
		" 'Sherman Oaks','California','USA',NULL,2005,'We Not ME!!')," +
		"('frc1662',1662,'Raptor Force Engineering','http://RaptorForce.org'," +
		" 'Lodi','California','USA',NULL,2005,'Using  A Robot To Change Lives')," +
		"('frc1665',1665,'Weapons of Mass Construction','https://sites.google.com/hudsoncsd.org/frc-team-1665/home'," +
		" 'Hudson','New York','USA',NULL,2005,'\"We Came, We Built, We Conquered.\"')," +
		"('frc1666',1666,'REDSKINS','http://www.usd480.net/LHS/lhs.htm'," +
		" 'Liberal','KS','USA',NULL,2005,NULL)," +
		"('frc1667',1667,'Broncobotix','http://www.usd230.org/first'," +
		" 'Spring Hill','KS','USA',NULL,2005,NULL)," +
		"('frc1669',1669,'NASALong Beach Unified Schools Cabrillo High Jaguars Robotics','http://www.jaganators.com'," +
		" 'Long Beach','CA','USA',NULL,2005,NULL)," +
		"('frc1670',1670,'FHJCC Eagles',NULL," +
		" 'Manhattan','KS','USA',NULL,2005,NULL)," +
		"('frc1671',1671,'Buchanan Bird Brains','http://www.team1671.com'," +
		" 'Clovis','California','USA',NULL,2005,'Dream, Create, Inspire.')," +
		"('frc1672',1672,'Robo-T-Birds','http://www.team1672.org/'," +
		" 'Mahwah','New Jersey','USA',NULL,2005,'Soaring \"first\"')," +
		"('frc1674',1674,'Lake Effect','http://www.onekama.k12.mi.us/h2007/Robotics/March07/competition.htm'," +
		" 'Onekama','MI','USA',NULL,2005,NULL)," +
		"('frc1675',1675,'UPS (Ultimate Protection Squad)','http://team1675.org/'," +
		" 'Milwaukee','Wisconsin','USA',NULL,2005,'Creating new heroes through science and technology')," +
		"('frc1676',1676,'The Pascack PI-oneers','http://www.team1676.com'," +
		" 'Montvale','New Jersey','USA',NULL,2005,'To listen is to learn...to understand is to inspire')," +
		"('frc1677',1677,'Qunatum Ninjas','http://www.tronbots.com'," +
		" 'Kalamazoo','Michigan','USA',NULL,2005,'If it''s not broken take it apart and fix it.')," +
		"('frc1678',1678,'Citrus Circuits','http://www.citruscircuits.org/'," +
		" 'Davis','California','USA',NULL,2005,'If life gives you lemons, build a robot')," +
		"('frc1680',1680,'Fort Erie Secondary School','http://www.fesstronics.ca'," +
		" 'Fort Erie','ON','Canada',NULL,2005,NULL)," +
		"('frc1682',1682,'Eye Bots',NULL," +
		" 'Riverside','CA','USA',NULL,2005,NULL)," +
		"('frc1683',1683,'Techno Titans','http://www.technotitans.org'," +
		" 'Johns Creek','Georgia','USA',NULL,2005,'Success through Teamwork, Innovation, & Determination')," +
		"('frc1684',1684,'The Chimeras ','http://www.first1684.com'," +
		" 'Lapeer','Michigan','USA',NULL,2005,'Do whatever it takes to win the next match')," +
		"('frc1685',1685,'Tech-Know Commandos',NULL," +
		" 'Worcester','MA','USA',NULL,2005,NULL)," +
		"('frc1686',1686,'Team Navigator','http://www.wpsweb.com/all/WWW/Projects/Robotics/ALLRobotics.htm'," +
		" 'Worcester','MA','USA',NULL,2005,NULL)," +
		"('frc1687',1687,'Highlander Robotics','http://dhsrobotics.webs.com'," +
		" 'Worcester','MA','USA',NULL,2005,NULL)," +
		"('frc1688',1688,'Team Stick Shift','http://www.portrichmondhs.org'," +
		" 'Staten Island','NY','USA',NULL,2005,NULL)," +
		"('frc1689',1689,'Blazing Bengals','http://www.bloomfield.k12.nj.us/bhsrobotics'," +
		" 'Bloomfield','NJ','USA',NULL,2005,NULL)," +
		"('frc1690',1690,'Orbit','http://www.1690orbit.com'," +
		" 'Binyamina','HaZafon','Israel',NULL,2005,NULL)," +
		"('frc1691',1691,'EaglesBots',NULL," +
		" 'Sidney','MT','USA',NULL,2005,NULL)," +
		"('frc1692',1692,'CougarBots','http://www.crenshawhs.org'," +
		" 'Los Angeles','CA','USA',NULL,2005,NULL)," +
		"('frc1693',1693,'Robo Lobos','http://www.downtowncollegeprep.org'," +
		" 'San Jose','California','USA',NULL,2005,NULL)," +
		"('frc1694',1694,'Walt Disney Robo Warriors','http://simmond2%40ocps.k12.fl.us'," +
		" 'Orlando','FL','USA',NULL,2005,NULL)," +
		"('frc1695',1695,'Bearly Robotics- \"Your Best Alloy\"','http://helenarobotics.weebly.com/index.html'," +
		" 'Helena','Montana','USA',NULL,2005,'RAR')," +
		"('frc1696',1696,'RoboRevolution','http://www.srvscience.org'," +
		" 'Sun River','MT','USA',NULL,2005,NULL)," +
		"('frc1697',1697,'Gyros',NULL," +
		" 'Long Beach','CA','USA',NULL,2005,NULL)," +
		"('frc1698',1698,'Metal Cougars (MC^2)',NULL," +
		" 'New York','NY','USA',NULL,2005,NULL)," +
		"('frc1699',1699,'Robocats','http://robocats.wix.com/team1699robocats'," +
		" 'Colchester','Connecticut','USA',NULL,2005,NULL)," +
		"('frc1700',1700,'Gatorbotics','http://gatorbotics.org/'," +
		" 'Palo Alto','California','USA',NULL,2005,'Women Learning, Women Leading')," +
		"('frc1701',1701,'RoboCubs','http://www.robocubs.com'," +
		" 'Detroit','Michigan','USA',NULL,2005,'Men For Others!')," +
		"('frc1702',1702,'None',NULL," +
		" 'Ingelwood','CA','USA',NULL,2005,NULL)," +
		"('frc1703',1703,'RAT''s','http://rats1703.com'," +
		" 'Las Vegas','NV','USA',NULL,2005,NULL)," +
		"('frc1704',1704,'Steelers',NULL," +
		" 'Fontana','CA','USA',NULL,2005,NULL)," +
		"('frc1705',1705,'Vikings',NULL," +
		" 'Denver','CO','USA',NULL,2005,NULL)," +
		"('frc1706',1706,'Ratchet Rockers','http://www.ratchetrockers1706.org'," +
		" 'Wentzville','Missouri','USA',NULL,2005,'Have skills will travel!')," +
		"('frc1707',1707,'NH Indians',NULL," +
		" 'Pghp','PA','USA',NULL,2005,NULL)," +
		"('frc1708',1708,'AMP''D Robotics','http://www.team1708.com/'," +
		" 'McKeesport','Pennsylvania','USA',NULL,2005,'( KISS )  Keep It Simple and Safe')," +
		"('frc1710',1710,'The Ravonics Revolution','http://www.team1710.com'," +
		" 'Olathe','Kansas','USA',NULL,2006,'We don''t just build robots, we build leaders.')," +
		"('frc1711',1711,'RAPTORS','http://www.raptors1711.com/'," +
		" 'Traverse City','Michigan','USA',NULL,2006,'Robots And People Transmitting Objective Radical Synergy')," +
		"('frc1712',1712,'Dawgma','http://www.team1712.org'," +
		" 'Ardmore','Pennsylvania','USA',NULL,2006,'Designing on all fours')," +
		"('frc1713',1713,'Gears','http://www.1000islandsschools.org/Clubs/Gears/FIRST.htm'," +
		" 'Cape Vincent / Clayton','NY','USA',NULL,2006,NULL)," +
		"('frc1714',1714,'MORE Robotics','http://www.morerobotics.org'," +
		" 'Milwaukee','Wisconsin','USA',NULL,2006,'Learn MORE, Give MORE, Be MORE')," +
		"('frc1715',1715,'RDA',NULL," +
		" 'Moore','SC','USA',NULL,2006,NULL)," +
		"('frc1716',1716,'Redbird Robotics','http://www.frc1716.org/'," +
		" 'De Pere','Wisconsin','USA',NULL,2006,NULL)," +
		"('frc1717',1717,'D''Penguineers','http://www.dpengineering.org/1717/welcome'," +
		" 'Goleta','California','USA',NULL,2006,NULL)," +
		"('frc1718',1718,'The Fighting Pi','http://www.fightingpi.org'," +
		" 'Armada','Michigan','USA',NULL,2006,'You can''t spell ''Epic'' without ''Pi''')," +
		"('frc1719',1719,'The Umbrella Corporation','http://www.firstteam1719.org'," +
		" 'Pikesville','Maryland','USA',NULL,2006,'Roboto ergo sum')," +
		"('frc1720',1720,'PhyXTGears','http://phyxtgears.org'," +
		" 'Muncie','Indiana','USA',NULL,2006,'Well-oiled Gears work best together')," +
		"('frc1721',1721,'Tidal Force','http://www.frc1721.org/'," +
		" 'Concord','New Hampshire','USA',NULL,2006,'We built... We programmed... We Concord.')," +
		"('frc1722',1722,'Rebel Innovators','http://www.flintridgeprep.org'," +
		" 'La Canada','CA','USA',NULL,2006,NULL)," +
		"('frc1723',1723,'The FBI - FIRST Bots of Independence','http://www.1723fbi.net'," +
		" 'Independence','Missouri','USA',NULL,2006,'A Veteran Team Should Know Better')," +
		"('frc1724',1724,'Weber Fever','http://www.weberfever.org'," +
		" 'Pleasant View','UT','USA',NULL,2006,NULL)," +
		"('frc1725',1725,'Worcester Public School Girls All Star',NULL," +
		" 'Worcester','MA','USA',NULL,2006,NULL)," +
		"('frc1726',1726,'N.E.R.D.S. (Nifty Engineering Robotics Design Squad)','http://www.firstinspires.org/'," +
		" 'Sierra Vista','Arizona','USA',NULL,2006,'We put the \"eek\" in Geek\"')," +
		"('frc1727',1727,'REX','https://www.dulaneyrobotics.org'," +
		" 'Lutherville Timonium','Maryland','USA',NULL,2006,'Nothing works the first time')," +
		"('frc1728',1728,'B.L.I.N.G. (Bausch & Lomb Inspires & Nurtures Growth)','http://www.team1728.com'," +
		" 'Rochester','NY','USA',NULL,2006,NULL)," +
		"('frc1729',1729,'Team Inconceivable!','http://www.team1729.org'," +
		" 'Peterborough','New Hampshire','USA',NULL,2006,'I do not think that means what you think it means.')," +
		"('frc1730',1730,'Team Driven','http://www.teamdriven.us'," +
		" 'Lees Summit','Missouri','USA',NULL,2006,'Driven to Succeed')," +
		"('frc1731',1731,'Fresta Valley Robotics Club','http://team1731.org/'," +
		" 'Warrenton','Virginia','USA',NULL,2006,NULL)," +
		"('frc1732',1732,'Hilltoppers','http://www.team1732.com'," +
		" 'Milwaukee','Wisconsin','USA',NULL,2006,NULL)," +
		"('frc1733',1733,'PolarBots','http://polarbots.110mb.com'," +
		" 'Worcester','MA','USA',NULL,2006,NULL)," +
		"('frc1734',1734,'1734 Sesame Street','http://www.uwf.edu/team1734'," +
		" 'Fort Walton Beach','FL','USA',NULL,2006,NULL)," +
		"('frc1735',1735,'Green Reapers','http://www.team1735.org/'," +
		" 'Worcester','Massachusetts','USA',NULL,2006,'Porjece')," +
		"('frc1736',1736,'Robot Casserole','https://www.robotcasserole.org/'," +
		" 'Peoria','Illinois','USA',NULL,2006,'The Casserole''s Ready')," +
		"('frc1737',1737,'Project X','https://1737robotics.wordpress.com/'," +
		" 'Excelsior Springs','Missouri','USA',NULL,2006,'Graciously Professional, Hungry for Excellence!')," +
		"('frc1738',1738,'Team 1738',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc1739',1739,'Chicago Knights','http://www.ChicagoKnights1739.org'," +
		" 'Chicago','Illinois','USA',NULL,2006,'Do and Understand')," +
		"('frc1740',1740,'Ledyard Cyber Colonels','http://lcc1740.net/'," +
		" 'Ledyard','Connecticut','USA',NULL,2006,'Carpe diem')," +
		"('frc1741',1741,'Red Alert','http://www.redalert1741.org'," +
		" 'Greenwood','Indiana','USA',NULL,2006,'Challenge Accepted!')," +
		"('frc1742',1742,'Shockwave','http://www.team1742.com'," +
		" 'Norman','Oklahoma','USA',NULL,2006,'Design it, Build it, Break it, Fix it, Repeat Process')," +
		"('frc1743',1743,'Short Circuits','http://cityhighrobotics.tumblr.com/'," +
		" 'Pittsburgh','Pennsylvania','USA',NULL,2006,NULL)," +
		"('frc1744',1744,'Robo Rays','http://www.roborays.org'," +
		" 'Naples','Florida','USA',NULL,2006,'Imagine the Possibilities')," +
		"('frc1745',1745,'The P-51 Mustangs','http://www.pearcerobotics.com'," +
		" 'Richardson','Texas','USA',NULL,2006,'Today''s students, tomorrow''s technology leaders')," +
		"('frc1746',1746,'OTTO','http://www.team1746.com'," +
		" 'Cumming','Georgia','USA',NULL,2006,'What''s Next?')," +
		"('frc1747',1747,'Harrison Boiler Robotics','http://www.hbrlive.com'," +
		" 'West Lafayette','Indiana','USA',NULL,2006,'Going green before it was cool.')," +
		"('frc1748',1748,'Lab Rats','http://www.team1748.org'," +
		" 'Baltimore','MD','USA',NULL,2006,NULL)," +
		"('frc1749',1749,'Wapack Renaissance Robotics',NULL," +
		" 'Peterborough','NH','USA',NULL,2006,NULL)," +
		"('frc1750',1750,'ThunderStorm Robotics','http://www.thunderstormrobotics.org'," +
		" 'Stillwater','Oklahoma','USA',NULL,2006,'Building character while building Robots.')," +
		"('frc1751',1751,'The Warriors','http://sites.google.com/site/comsewoguerobotics1751'," +
		" 'Port Jefferson Station','New York','USA',NULL,2006,'Never Give Up')," +
		"('frc1752',1752,'Tonka',NULL," +
		" 'Kansas City','MO','USA',NULL,2006,NULL)," +
		"('frc1753',1753,'Goz-Bot','http://www.watertown.k12.wi.us/hs/robotics'," +
		" 'Watertown','WI','USA',NULL,2006,NULL)," +
		"('frc1754',1754,'Nitro Knights','http://www.nkrobotics.com'," +
		" 'Boston','MA','USA',NULL,2006,NULL)," +
		"('frc1755',1755,'Jaguarbotics',NULL," +
		" 'Chicago','IL','USA',NULL,2006,NULL)," +
		"('frc1756',1756,'Argos','http://teamargos.org'," +
		" 'Peoria','Illinois','USA',NULL,2006,NULL)," +
		"('frc1757',1757,'Wolverines','https://whsrobotics.org'," +
		" 'Westwood','Massachusetts','USA',NULL,2006,NULL)," +
		"('frc1758',1758,'Technomancers','http://www.frc1758.org'," +
		" 'Florence','South Carolina','USA',NULL,2006,'Metal and Magic')," +
		"('frc1759',1759,'Potatoes','http://www.eshspotatoes.com/'," +
		" 'El Segundo','California','USA',NULL,2006,'Nothing is impossible, just really frickin'' hard.')," +
		"('frc1760',1760,'Robo-Titans','https://www.sites.google.com/site/team1760'," +
		" 'Kokomo','IN','USA',NULL,2006,NULL)," +
		"('frc1761',1761,'STEAMpunk Tigers','http://www.Team1761.com'," +
		" 'Lynn','Massachusetts','USA',NULL,2006,'Work FIRST...Play later')," +
		"('frc1763',1763,'Paseliens','http://Paseorobotics.Wordpress.com'," +
		" 'Kansas City','Missouri','USA',NULL,2006,'Building Robots Is Our Art!')," +
		"('frc1764',1764,'Liberty Robotics','http://www.first1764.com'," +
		" 'Liberty','Missouri','USA',NULL,2006,'\"The Robot Builds Me\"')," +
		"('frc1765',1765,'E1Bots','http://www.team1765.com'," +
		" 'Buffalo','New York','USA',NULL,2006,NULL)," +
		"('frc1766',1766,'TM','http://www.c4robotics.com'," +
		" 'Columbus','IN','USA',NULL,2006,NULL)," +
		"('frc1767',1767,'Widget Warriors',NULL," +
		" 'Howell','MI','USA',NULL,2006,NULL)," +
		"('frc1768',1768,'RoboChiefs','http://www.nashobarobotics.org'," +
		" 'Bolton','Massachusetts','USA',NULL,2006,'Plan Ahead')," +
		"('frc1769',1769,'Digital Hawks','https://www.facebook.com/jcharmonrobotics/'," +
		" 'Kansas City','Kansas','USA',NULL,2006,'Success is getting up just one more time than you fall down')," +
		"('frc1770',1770,'Alpha Woverines',NULL," +
		" 'Chicago','IL','USA',NULL,2006,NULL)," +
		"('frc1771',1771,'Electric Phoenixes','http://www.nghsrobotics.weebly.com'," +
		" 'Suwanee','Georgia','USA',NULL,2006,'\"We''re back\"')," +
		"('frc1772',1772,'The Brazilian Trail Blazers','http://www.aidtec.org'," +
		" 'Gravatai','Rio Grande do Sul','Brazil',NULL,2006,'\"Building robots and Changing futures\" (Construindo robos e Mudando futuros)')," +
		"('frc1774',1774,'Athena''s Engineers',NULL," +
		" 'Pittsburg','TX','USA',NULL,2012,NULL)," +
		"('frc1775',1775,'Tigerbytes','https://www.tigerbytes1775.org'," +
		" 'Kansas City','Missouri','USA',NULL,2006,NULL)," +
		"('frc1776',1776,'Declaration','http://www.dlsdeclaration.com'," +
		" 'Kansas City','MO','USA',NULL,2006,NULL)," +
		"('frc1777',1777,'Viking Robotics','https://sites.google.com/smsd.org/smw1777/home'," +
		" 'Overland Park','Kansas','USA',NULL,2006,'\"Prepare to Be Pillaged\"')," +
		"('frc1778',1778,'Chill Out','http://www.first1778.org/'," +
		" 'Mountlake Terrace','Washington','USA',NULL,2006,'One Team, One Mission')," +
		"('frc1779',1779,'G. St. Techies',NULL," +
		" 'South Boston','MA','USA',NULL,2006,NULL)," +
		"('frc1780',1780,'Wolves Robotics',NULL," +
		" 'Henderson','NV','USA',NULL,NULL,NULL)," +
		"('frc1781',1781,'Lindblom Electric Eagles','http://www.firstinspires.org/'," +
		" 'Chicago','Illinois','USA',NULL,2006,NULL)," +
		"('frc1782',1782,'RADICAL RAYTOWN ROBOTICS','http://THERAYTOWNBLUEJAYS.COM'," +
		" 'RAYTOWN','MO','USA',NULL,2006,NULL)," +
		"('frc1783',1783,'Falcon Firebots',NULL," +
		" 'West Branch','MI','USA',NULL,2006,NULL)," +
		"('frc1784',1784,'Litchbots','http://sites.google.com/site/litchbotrobotics/Downhome'," +
		" 'Litchfield','CT','USA',NULL,2006,NULL)," +
		"('frc1785',1785,'Blue Springs Robocats','http://www.bluespringsrobotics.net'," +
		" 'Blue Springs','Missouri','USA',NULL,2006,NULL)," +
		"('frc1786',1786,'The Robotics Team','http://www.firstinspires.org/'," +
		" 'Dublin','New Hampshire','USA',NULL,2006,'Truth and Courage')," +
		"('frc1787',1787,'Flying Circuits','http://team1787.com'," +
		" 'Cleveland','Ohio','USA',NULL,2006,NULL)," +
		"('frc1788',1788,'Ravage','http://www.apskids.org/southside/mysite2/home.htm'," +
		" 'Atlanta','GA','USA',NULL,2006,NULL)," +
		"('frc1789',1789,'Cliffhangers','http://westgrand.k12.co.us/index.php?option=com_content&view=article&id=275&Itemid=53'," +
		" 'Kremmling','CO','USA',NULL,2006,NULL)," +
		"('frc1790',1790,'Team 1881','http://N/A'," +
		" 'Paterson','NJ','USA',NULL,NULL,NULL)," +
		"('frc1791',1791,'Voyagers','http://robotics1791.bappy.com/index.html'," +
		" 'Clayton','New Jersey','USA',NULL,2006,NULL)," +
		"('frc1792',1792,'Round Table Robotics','http://www.roundtablerobotics.com'," +
		" 'Oak Creek','Wisconsin','USA',NULL,2006,'\"Our Table Is Round\"')," +
		"('frc1793',1793,'The Aviators','http://www.norviewrobotics.org'," +
		" 'Norfolk','Virginia','USA',NULL,2006,NULL)," +
		"('frc1794',1794,'N.I.R.D.',NULL," +
		" 'St. Louis','MO','USA',NULL,2006,NULL)," +
		"('frc1795',1795,'Team Clutch','https://www.atlantapublicschools.us/Domain/2879'," +
		" 'Atlanta','Georgia','USA',NULL,2006,'Building for our Future')," +
		"('frc1796',1796,'RoboTigers','http://www.RoboTigers1796.com'," +
		" 'Queens','New York','USA',NULL,2006,'\"If Only Our Nuts & Bolts Were Bigger...\"')," +
		"('frc1797',1797,'Phoenix','https://www.aslrobotics.com/'," +
		" 'London','England','United Kingdom',NULL,2006,NULL)," +
		"('frc1798',1798,'RoboKnights','http://www.team1798.com'," +
		" 'Tucson','AZ','USA',NULL,2006,NULL)," +
		"('frc1799',1799,'Wired Up!','https://sites.google.com/a/jeffcoschools.us/cagray/home/frc'," +
		" 'Littleton','Colorado','USA',NULL,2006,NULL)," +
		"('frc1800',1800,'Bravebotics','http://www.usd204.k12.ks.us/bshspages/robotics'," +
		" 'Bonner Springs','KS','USA',NULL,2006,NULL)," +
		"('frc1801',1801,'The Dapper Dans',NULL," +
		" 'Kountze','TX','USA',NULL,2006,NULL)," +
		"('frc1802',1802,'Team Stealth','http://stealth.piperschools.us'," +
		" 'Kansas City','Kansas','USA',NULL,2006,'Tempus Fugit')," +
		"('frc1803',1803,'Vikings',NULL," +
		" 'Port Washington','New York','USA',NULL,2006,'KIS')," +
		"('frc1804',1804,'Northmen',NULL," +
		" 'Kansas City','MO','USA',NULL,2006,NULL)," +
		"('frc1805',1805,'HOT BOTS','http://www.centralhotbots.org'," +
		" 'Kansas City','MO','USA',NULL,2006,NULL)," +
		"('frc1806',1806,'S.W.A.T.','http://www.swat1806.com/'," +
		" 'Smithville','Missouri','USA',NULL,2006,'Use it up, Wear it out, Make it do or do without.')," +
		"('frc1807',1807,'Redbird Robotics','http://www.frc1807.org'," +
		" 'Allentown','New Jersey','USA',NULL,2006,'One goal, one ambition, multiple minds')," +
		"('frc1808',1808,'Red Devil Robotics',NULL," +
		" 'Freeport','NY','USA',NULL,2006,NULL)," +
		"('frc1810',1810,'Jaguar Robotics','http://1810jaguarrobotics.wix.com/jaguar-robotics-1810'," +
		" 'Shawnee','Kansas','USA',NULL,2006,NULL)," +
		"('frc1811',1811,'FRESH','https://sites.google.com/site/newarknjteam1811/'," +
		" 'Newark','New Jersey','USA',NULL,2006,'Never Stop!')," +
		"('frc1813',1813,'Roboegal','http://www.freewebs.com/teamnv211'," +
		" 'Newark','NJ','USA',NULL,2006,NULL)," +
		"('frc1814',1814,'Phoenix In Fight','http://www.marksdepot.ca/robotics'," +
		" 'Toronto','ON','Canada',NULL,2006,NULL)," +
		"('frc1815',1815,'Black Scots','http://www.firstinspires.org/'," +
		" 'Toronto','Ontario','Canada',NULL,2006,NULL)," +
		"('frc1816',1816,'\"The Green Machine\"','http://www.edinarobotics.com/'," +
		" 'Edina','Minnesota','USA',NULL,2006,'\"Eyes on the future\"')," +
		"('frc1817',1817,'Llano Estacado RoboRaiders','http://www.team1817.org'," +
		" 'Lubbock','Texas','USA',NULL,2006,'Raider Power!')," +
		"('frc1818',1818,'Team Talos','Coming Soon'," +
		" 'Shreveport','Louisiana','USA',NULL,2006,NULL)," +
		"('frc1820',1820,'Havoc','http://www.mcps.k12.md.us/schools/magruderhs/MHS_Main/robotics'," +
		" 'Rockville','MD','USA',NULL,2006,NULL)," +
		"('frc1823',1823,'The New Victorians','http://lincoln.pps.k12.or.us'," +
		" 'portland','OR','USA',NULL,2006,NULL)," +
		"('frc1824',1824,'1824','http://team1824.com'," +
		" 'Peterborough','NH','USA',NULL,2006,NULL)," +
		"('frc1825',1825,'The Cyborgs','http://www.metrohomeschoolrobotics.org'," +
		" 'Kansas City','Missouri','USA',NULL,2006,'\"Think Outside the Cube\"')," +
		"('frc1826',1826,'Team 1826, The Fuse','http://www.warp8.com/fca/usfirst'," +
		" 'Golden','CO','USA',NULL,2006,NULL)," +
		"('frc1827',1827,'THE HIVE','http://www.firstinspires.org/'," +
		" 'Kansas City','Missouri','USA',NULL,2006,'It''s not about the destination, it''s about the journey.')," +
		"('frc1828',1828,'BoxerBots','http://1828BoxerBots.org'," +
		" 'Vail','Arizona','USA',NULL,2006,'\"VARSITY ENGINEERING\"')," +
		"('frc1829',1829,'Carbonauts','http://www.team1829.com'," +
		" 'Accomack County','Virginia','USA',NULL,2006,'Excellence Without Arrogance')," +
		"('frc1830',1830,'Mighty Bookers','http://www.freewebs.com/team1830'," +
		" 'Norfolk','VA','USA',NULL,2006,NULL)," +
		"('frc1831',1831,'Screaming Eagles','http://sites.google.com/a/sau73/robotics'," +
		" 'Gilford','New Hampshire','USA',NULL,2006,'The Futures Here.  We are it.')," +
		"('frc1834',1834,'Evolution','http://robotics.siatech.org'," +
		" 'San Jose','California','USA',NULL,2006,'')," +
		"('frc1835',1835,'Wreckless Creation',NULL," +
		" 'Toronto','ON','Canada',NULL,2006,NULL)," +
		"('frc1836',1836,'The MilkenKnights','http://www.MilkenKnights.com'," +
		" 'Los Angeles','California','USA',NULL,2006,NULL)," +
		"('frc1837',1837,'Madi',NULL," +
		" 'Tallulah','LA','USA',NULL,2006,NULL)," +
		"('frc1838',1838,'Bobcats',NULL," +
		" 'Rexburg','ID','USA',NULL,2006,NULL)," +
		"('frc1839',1839,'Techattack','http://www.sd215.net/robotics'," +
		" 'Saint Anthony','ID','USA',NULL,2006,NULL)," +
		"('frc1840',1840,'Diggers',NULL," +
		" 'Sugar City','ID','USA',NULL,2006,NULL)," +
		"('frc1841',1841,'Crazy Bred......Yeeeeaah!!','http://www.sphsengineering.com'," +
		" 'plantation','FL','USA',NULL,2006,NULL)," +
		"('frc1842',1842,'Twisted E.G.O.','http://www.Twistedego.org'," +
		" 'Fort Pierce','FL','USA',NULL,2006,NULL)," +
		"('frc1843',1843,'Trobots','http://www.troboteers.com'," +
		" 'Rigby','ID','USA',NULL,2006,NULL)," +
		"('frc1845',1845,'Cyber Panthers','http://cyberpanthers1845.tripod.com'," +
		" 'Atlanta','GA','USA',NULL,2006,NULL)," +
		"('frc1846',1846,'Le Vortex','http://sfxprovidence.wix.com/1846levortex'," +
		" 'Sarnia','Ontario','Canada',NULL,2006,'L''innovation est une affaire de communauté!')," +
		"('frc1847',1847,'W.R.A.T.H (Wyandotte Robotics and Technology Hackers)','http://www.firstinspires.org/'," +
		" 'Kansas City','Kansas','USA',NULL,2006,'We don''t just build Robots, We build Engineers')," +
		"('frc1848',1848,'SOUP','http://www.garoboticsalliance.com'," +
		" 'Atlanta','GA','USA',NULL,2006,NULL)," +
		"('frc1849',1849,'Griffins','http://checrobotics.com'," +
		" 'Washington','DC','USA',NULL,2006,NULL)," +
		"('frc1850',1850,'Mechanicats','http://www.facebook.com/Team1850'," +
		" 'Chicago','Illinois','USA',NULL,2006,NULL)," +
		"('frc1851',1851,'Manic Mechanics',NULL," +
		" 'Sun Valley','CA','USA',NULL,2006,NULL)," +
		"('frc1852',1852,'Team Amore',NULL," +
		" 'Scottsdale','AZ','USA',NULL,2006,NULL)," +
		"('frc1853',1853,'Ruskin Eagles','http://schoolweb.missouri.edu/hickman.k12.mo.us/ruskin'," +
		" 'Kansas City','MO','USA',NULL,2006,NULL)," +
		"('frc1855',1855,'MagnoBots','http://robotics.magnoliascience.org'," +
		" 'Reseda','CA','USA',NULL,2006,NULL)," +
		"('frc1856',1856,'Gear Heads',NULL," +
		" 'Redford','MI','USA',NULL,2006,NULL)," +
		"('frc1858',1858,'Spartans','http://salmensobsidian.com'," +
		" 'Slidell','LA','USA',NULL,2006,NULL)," +
		"('frc1859',1859,'RoboJacks',NULL," +
		" 'Bogalusa','LA','USA',NULL,2006,NULL)," +
		"('frc1860',1860,'Alphabots','http://www.frc1860.com'," +
		" 'Sao Jose dos Campos','São Paulo','Brazil',NULL,2006,NULL)," +
		"('frc1861',1861,'SHARC','http://www.sharcbyte.com'," +
		" 'Aurora','CO','USA',NULL,2006,NULL)," +
		"('frc1862',1862,'Red Raiders','http://www.team1862.blogspot.com'," +
		" 'Cliffside Park','NJ','USA',NULL,2006,NULL)," +
		"('frc1863',1863,'Titan it up!','http://payneja%40tulsaschools.org'," +
		" 'Tulsa','OK','USA',NULL,2006,NULL)," +
		"('frc1864',1864,'Automata','http://messwoodrobotics.com/default.aspx'," +
		" 'Milwaukee','WI','USA',NULL,2006,NULL)," +
		"('frc1865',1865,'RoboBuffs','http://www.mhseeacademy.com/team1865'," +
		" 'Missouri City','TX','USA',NULL,2006,NULL)," +
		"('frc1866',1866,'The Cardinals',NULL," +
		" 'Swansea','MA','USA',NULL,2006,NULL)," +
		"('frc1867',1867,'The Prowlers','http://www.cstem.com'," +
		" 'Houston','TX','USA',NULL,2006,NULL)," +
		"('frc1868',1868,'Space Cookies','http://frc.spacecookies.org'," +
		" 'Mountain View','California','USA',NULL,2006,'Girls Engineer Their Tomorrows')," +
		"('frc1870',1870,'The Lightning Arx',NULL," +
		" 'Red Deer','AB','Canada',NULL,2006,NULL)," +
		"('frc1871',1871,'Panter Machine',NULL," +
		" 'CHarles City','VA','USA',NULL,2006,NULL)," +
		"('frc1872',1872,'Steel Lions','http://www.team1872steellions.wordpress.com'," +
		" 'San Juan','Puerto Rico','USA',NULL,2006,'Fortes in Fide')," +
		"('frc1873',1873,'CRT',NULL," +
		" 'Columbia','MO','USA',NULL,2006,NULL)," +
		"('frc1875',1875,'Purple Haze','http://www.team1875.com'," +
		" 'Cocoa','FL','USA',NULL,2006,NULL)," +
		"('frc1876',1876,'Beachbotics','http://www.beachbotics.com'," +
		" 'Hilton Head Island','South Carolina','USA',NULL,2006,'\"Lead, follow or get out of the way!\"')," +
		"('frc1877',1877,'Mechanical Mafia',NULL," +
		" 'Dahlonega','GA','USA',NULL,2006,NULL)," +
		"('frc1879',1879,'Minor High School',NULL," +
		" 'Adamsville','AL','USA',NULL,2006,NULL)," +
		"('frc1880',1880,'Warriors of East Harlem','http://ehtprobotics.org/'," +
		" 'New York','New York','USA',NULL,2006,'East Harlem''s Finest!')," +
		"('frc1881',1881,'Gamma Elite','http://gammaelite1881.webs.com'," +
		" 'Paterson','NJ','USA',NULL,2006,NULL)," +
		"('frc1882',1882,'Rebel Robotics','http://www.rebelrobotics.com'," +
		" 'Farmingdale','NJ','USA',NULL,2006,NULL)," +
		"('frc1883',1883,'Dragons',NULL," +
		" 'Las Vegas','NV','USA',NULL,2006,NULL)," +
		"('frc1884',1884,'Griffins','https://www.aslrobotics.com/'," +
		" 'London','England','United Kingdom',NULL,2006,NULL)," +
		"('frc1885',1885,'ILITE Robotics','http://www.ilite.us'," +
		" 'Haymarket','Virginia','USA',NULL,2006,'Inspiring Leaders In Technology and Engineering')," +
		"('frc1886',1886,'Hawks',NULL," +
		" 'Ijamsville','MD','USA',NULL,2006,NULL)," +
		"('frc1887',1887,'Russet Robotix',NULL," +
		" 'Shelley','ID','USA',NULL,2006,NULL)," +
		"('frc1888',1888,'West Robotics',NULL," +
		" 'plano','TX','USA',NULL,2006,NULL)," +
		"('frc1889',1889,'Haze',NULL," +
		" 'Wellington','FL','USA',NULL,2006,NULL)," +
		"('frc1890',1890,'EOT','http://www.SEMAAnashville.com'," +
		" 'Nashville','TN','USA',NULL,2006,NULL)," +
		"('frc1891',1891,'Bullbots','http://www.bullbots.org'," +
		" 'Boise','Idaho','USA',NULL,2006,'It''s just a prototype')," +
		"('frc1893',1893,'High Tech Parrots','http://bpi.edu'," +
		" 'Baltimore','MD','USA',NULL,2006,NULL)," +
		"('frc1894',1894,'The Elite','http://webduboismesa22.blogspot.com'," +
		" 'Baltimore','MD','USA',NULL,2006,NULL)," +
		"('frc1895',1895,'Lambda Corps','https://www.team1895.org/'," +
		" 'Manassas','Virginia','USA',NULL,2006,'Shoot for the stars. Fly with the Eagles.')," +
		"('frc1896',1896,'Concussive Engineers','http://www.firstinspires.org/'," +
		" 'Traverse City','Michigan','USA',NULL,2006,NULL)," +
		"('frc1897',1897,'Red Hot Chile Dragons','http://www.team1897.com'," +
		" 'Albuquerque','NM','USA',NULL,2006,NULL)," +
		"('frc1898',1898,'Westside Wolves',NULL," +
		" 'Houston','TX','USA',NULL,2006,NULL)," +
		"('frc1899',1899,'Saints Robotics','http://www.saintsrobotics.com/'," +
		" 'Bellevue','Washington','USA',NULL,2006,'Ingenuity. Honor. Strength.')," +
		"('frc1900',1900,'Rough Riders',NULL," +
		" 'Washington','DC','USA',NULL,2006,NULL)," +
		"('frc1901',1901,'Ramrod','http://www.team1901.com'," +
		" 'St. Marys','ON','Canada',NULL,2006,NULL)," +
		"('frc1902',1902,'Exploding Bacon','http://www.explodingbacon.com'," +
		" 'Orlando','Florida','USA',NULL,2006,'Where Pigs Fly')," +
		"('frc1904',1904,'Warbots','http://www.warbots.org'," +
		" 'Baltimore','MD','USA',NULL,2006,NULL)," +
		"('frc1905',1905,'KCbotics',NULL," +
		" 'Kansas City','MO','USA',NULL,2006,NULL)," +
		"('frc1906',1906,'WeBeRoyBotics','http://weberoybotics.org'," +
		" 'Roy','UT','USA',NULL,2006,NULL)," +
		"('frc1907',1907,'Birds of Prey','http://www.pecps.k12.va.us/PECHS/Clubs_and_groups/Robotics.htm'," +
		" 'Farmville','VA','USA',NULL,2006,NULL)," +
		"('frc1908',1908,'ShoreBots','http://shorebots.org'," +
		" 'Eastville','Virginia','USA',NULL,2006,'We are the shorebots')," +
		"('frc1909',1909,'G-Tech','http://www.awzone.com'," +
		" 'Boston','MA','USA',NULL,2006,NULL)," +
		"('frc1910',1910,'Hawlets',NULL," +
		" 'Kansas City','MO','USA',NULL,2006,NULL)," +
		"('frc1911',1911,'Techno-Horns',NULL," +
		" 'Fort Benton','MT','USA',NULL,2006,NULL)," +
		"('frc1912',1912,'Team Combustion','http://www.team1912.com'," +
		" 'Slidell','Louisiana','USA',NULL,2006,'Spreading the Wildfire')," +
		"('frc1913',1913,'Livewire','http://covingtonhigh.stpsb.org/clubs/robotics/alt/index.html'," +
		" 'Covington','LA','USA',NULL,2006,NULL)," +
		"('frc1915',1915,'MTHS Firebird Robotics','http://www.firstinspires.org/'," +
		" 'Washington','District of Columbia','USA',NULL,2006,'No Excuses, Just Solutions')," +
		"('frc1916',1916,'MP Robotics','http://www.boston.k12.ma.us/Madisonpk/student-life/robotics/index-robotics.htm'," +
		" 'Roxbury','MA','USA',NULL,2006,NULL)," +
		"('frc1917',1917,'T.O.M. (Teckies Operating Machinery)','http://www.tricityprep.org/robotics'," +
		" 'Prescott','AZ','USA',NULL,2006,NULL)," +
		"('frc1918',1918,'NC GEARS','http://first.ncresa.org/'," +
		" 'Fremont','Michigan','USA',NULL,2006,'Just Meshing Around ')," +
		"('frc1919',1919,'Knights of the Heights',NULL," +
		" 'South Boston','MA','USA',NULL,2006,NULL)," +
		"('frc1920',1920,'McMain Hurricanes','http://www.eleanormcm.com'," +
		" 'New Orleans','Louisiana','USA',NULL,2006,'\"Eliminating, One Robot At A Time')," +
		"('frc1922',1922,'Oz-Ram','http://www.ozram1922.org'," +
		" 'Contoocook','New Hampshire','USA',NULL,2006,'The Tin Men!')," +
		"('frc1923',1923,'The MidKnight Inventors','http://www.firstrobotics1923.org'," +
		" 'Plainsboro','New Jersey','USA',NULL,2006,'You never forget your FIRST robotics.')," +
		"('frc1925',1925,'Jags',NULL," +
		" 'West Hills','CA','USA',NULL,2006,NULL)," +
		"('frc1926',1926,'Team 8-Bit','http://www.nativerobotics.com'," +
		" 'Mesa','AZ','USA',NULL,2006,NULL)," +
		"('frc1927',1927,'Tempest','http://www.teamtempest1927.com/'," +
		" 'Biloxi','Mississippi','USA',NULL,2006,'Building Lives:  One Heartache at a Time')," +
		"('frc1929',1929,'FoxyBots',NULL," +
		" 'Montclair','NJ','USA',NULL,2006,NULL)," +
		"('frc1930',1930,'Comets','http://www.team1930.vze.com'," +
		" 'Henrietta','NY','USA',NULL,2006,NULL)," +
		"('frc1931',1931,'Hornets',NULL," +
		" 'Little Falls','NJ','USA',NULL,2006,NULL)," +
		"('frc1932',1932,'RoboRustlers','http://rustlertech.gfps.k12.mt.us/roborustlers/index.htm'," +
		" 'Great Falls','MT','USA',NULL,2006,NULL)," +
		"('frc1933',1933,'Doncaster Destroyers','http://www.team2120.co.uk'," +
		" 'Doncaster','ENG','Kingdom',NULL,2006,NULL)," +
		"('frc1934',1934,'AI',NULL," +
		" 'London','ENG','Kingdom',NULL,2006,NULL)," +
		"('frc1935',1935,'Team Happycat',NULL," +
		" 'Toronto','ON','Canada',NULL,2006,NULL)," +
		"('frc1937',1937,'Elysium ','http://www.frcelysium1937.wixsite.com/1937'," +
		" 'Modi''in-Maccabim Reu''t','HaMerkaz','Israel',NULL,2006,NULL)," +
		"('frc1938',1938,'Manhattan High School',NULL," +
		" 'Manhattan','MT','USA',NULL,2006,NULL)," +
		"('frc1939',1939,'THE KUHNIGITS','http://www.frcteam1939.com'," +
		" 'Kansas City','Missouri','USA',NULL,2006,'That rabbit is dynamite!')," +
		"('frc1940',1940,'The Tech Tigers','http://www.bhas.org'," +
		" 'Benton Harbor','Michigan','USA',NULL,2006,'We just dont think it, we build it')," +
		"('frc1941',1941,'Detroit Rocks Robotics','http://www.drrobo.weebly.com'," +
		" 'Detroit','MI','USA',NULL,2006,NULL)," +
		"('frc1942',1942,'Cinderella Tel-Nof','http://telnof.ort.org.il'," +
		" 'Gadera','HaMerkaz','Israel',NULL,2006,NULL)," +
		"('frc1943',1943,'Neat Team','http://neatteam.com'," +
		" 'Rosh Hayin','HaMerkaz','Israel',NULL,2006,NULL)," +
		"('frc1944',1944,'Airforce High School','http://www.technischool-first.co.nr'," +
		" 'Haifa','Haifa','Israel',NULL,2006,NULL)," +
		"('frc1945',1945,'Most Wanted Robotics','http://1945.btyclan.net'," +
		" 'Nazareth','Z','Israel',NULL,2006,NULL)," +
		"('frc1946',1946,' FG','http://tamra-afr.com/robot'," +
		" 'Tamra GLIL','HaZafon (Northern)','Israel',NULL,2006,NULL)," +
		"('frc1947',1947,'BlacKnight Robotics','http://www.FRC-1947.com'," +
		" 'Hadera','M','Israel',NULL,2006,NULL)," +
		"('frc1948',1948,'GreenGang','http://www.ggfirst.net'," +
		" 'Kfar Saba','M','Israel',NULL,2006,NULL)," +
		"('frc1949',1949,'shapira',NULL," +
		" 'Natanya','M','Israel',NULL,2006,NULL)," +
		"('frc1950',1950,'Emek Hefer',NULL," +
		" 'Emek Hefer','Z','Israel',NULL,2006,NULL)," +
		"('frc1951',1951,'Ort Singolovsky',NULL," +
		" 'Tel Aviv','TA','Israel',NULL,2006,NULL)," +
		"('frc1952',1952,'Arming High School','http://www.planetnana.co.il/first1952'," +
		" 'tzrifin','HaMerkaz (Central)','Israel',NULL,2006,NULL)," +
		"('frc1954',1954,'ElectroBunny','http://www.firstinspires.org/'," +
		" 'Beer Sheva','HaDarom','Israel',NULL,2006,'Everything is done by ourselves')," +
		"('frc1955',1955,'Beit Yerah',NULL," +
		" 'Emek Hayarden','Z','Israel',NULL,2006,NULL)," +
		"('frc1956',1956,'UA',NULL," +
		" 'Umm Alfahem','Z','Israel',NULL,2006,NULL)," +
		"('frc1957',1957,'LighTeam',NULL," +
		" 'Katzrin','Z','Israel',NULL,2006,NULL)," +
		"('frc1959',1959,'Bengal Bots','http://www.bh.richland2.org/~bgillam/Site_4/Blythewood_High_School_Robotics_Team_1959.html'," +
		" 'Blythewood','SC','USA',NULL,2006,NULL)," +
		"('frc1960',1960,'GO DARBY!',NULL," +
		" 'Darby','MT','USA',NULL,2006,NULL)," +
		"('frc1961',1961,'GreenvilleSeniorHigh',NULL," +
		" 'Greenville','SC','USA',NULL,2006,NULL)," +
		"('frc1962',1962,'Zilch',NULL," +
		" 'Boston','MA','USA',NULL,2006,NULL)," +
		"('frc1963',1963,'PSC Robotics','http://www.pscrobotics.org'," +
		" 'Winchester','ENG','Kingdom',NULL,2006,NULL)," +
		"('frc1965',1965,'Firebirds','http://1965firebirds.org/home/'," +
		" 'Allston','Massachusetts','USA',NULL,2006,NULL)," +
		"('frc1966',1966,'Knights',NULL," +
		" 'Missoula','MT','USA',NULL,2006,NULL)," +
		"('frc1967',1967,'The Janksters','http://team1967.ndsj.org/'," +
		" 'San Jose','California','USA',NULL,2006,'It may not be perfect, But that''s where the magic begins')," +
		"('frc1970',1970,'Bulldogs','http://www.epahs.org/'," +
		" 'Menlo Park','California','USA',NULL,2006,NULL)," +
		"('frc1972',1972,'Searing Engineering','http://www.team1972.org'," +
		" 'El Centro','California','USA',NULL,2006,'When''s Kick-Off?!')," +
		"('frc1973',1973,'Burning Tiger   #1973','http://brightonburningtigers.org'," +
		" 'Boston','Massachusetts','USA',NULL,2006,'Keep On Burning Brightly')," +
		"('frc1974',1974,'Weber Tech','http://www.weberinstitute.org'," +
		" 'Stockton','CA','USA',NULL,2006,NULL)," +
		"('frc1975',1975,'Team QUEEN',NULL," +
		" 'Millton','MA','USA',NULL,2006,NULL)," +
		"('frc1976',1976,'The Missing Lynx','http://www.themissinglynx.com'," +
		" 'Eden','TX','USA',NULL,2006,NULL)," +
		"('frc1977',1977,'The PowerSquids ','http://www.powersquidsrobotics.com/'," +
		" 'Loveland','Colorado','USA',NULL,2006,NULL)," +
		"('frc1978',1978,'Bulldogs',NULL," +
		" 'Phoenix','AZ','USA',NULL,2006,NULL)," +
		"('frc1980',1980,'The Brigade','http://www.team1980.org'," +
		" 'Aberdeen','MD','USA',NULL,2006,NULL)," +
		"('frc1981',1981,'The Gearheads',NULL," +
		" 'Independence','MO','USA',NULL,2007,NULL)," +
		"('frc1982',1982,'Cougar Robotics','http://www.nwcougarrobotics.com'," +
		" 'Shawnee','Kansas','USA',NULL,2007,NULL)," +
		"('frc1983',1983,'Skunk Works Robotics','http://skunkworks1983.com/'," +
		" 'Seattle','Washington','USA',NULL,2007,'Be Quick, be Quiet, and Be On Time...')," +
		"('frc1984',1984,'Jawas','http://www.1984.team'," +
		" 'Overland Park','Kansas','USA',NULL,2007,'Putting the Pieces Together')," +
		"('frc1985',1985,'Robohawks','http://www.robohawks1985.org'," +
		" 'Florissant','Missouri','USA',NULL,2007,NULL)," +
		"('frc1986',1986,'Team Titanium','http://www.teamtitanium.org/'," +
		" 'Lees Summit','Missouri','USA',NULL,2007,'Engineering Engineers')," +
		"('frc1987',1987,'Broncobots','http://www.teambroncobots.com'," +
		" 'Lees Summit','Missouri','USA',NULL,2007,NULL)," +
		"('frc1988',1988,'The Sweetie Pies','http://kmrobotics.org'," +
		" 'Wales','WI','USA',NULL,2007,NULL)," +
		"('frc1989',1989,'Viking Robotics','http://www.vernonrobotics.com'," +
		" 'Vernon','New Jersey','USA',NULL,2007,'Where Steel and Intelligence Clash')," +
		"('frc1990',1990,'Colts',NULL," +
		" 'Lodi','OH','USA',NULL,2007,NULL)," +
		"('frc1991',1991,'Dragons','https://frc1991.github.io/'," +
		" 'Hartford','Connecticut','USA',NULL,2007,'Failure is Not an Option')," +
		"('frc1992',1992,'Raytown South Robocards',NULL," +
		" 'Raytown','MO','USA',NULL,2007,NULL)," +
		"('frc1994',1994,'Quixote','http://www.firstinspires.org/'," +
		" 'Kansas City','Kansas','USA',NULL,2007,NULL)," +
		"('frc1995',1995,'Fatal Error','http://www.team1995.com'," +
		" 'Bangor','ME','USA',NULL,2007,NULL)," +
		"('frc1996',1996,'Wildcat Robotics','http://www.wildcatrobotics.com'," +
		" 'Blue Springs','MO','USA',NULL,2007,NULL)," +
		"('frc1997',1997,'Stag Robotics','http://www.stagroboticsteam1997.com'," +
		" 'Roeland Park','Kansas','USA',NULL,2007,'We Solve Problems')," +
		"('frc1998',1998,'Robodogz','https://www.team1998.yolasite.com'," +
		" 'Dearborn Heights','MI','USA',NULL,NULL,NULL)," +
		"('frc1999',1999,'O.U.T.E.R L.I.M.I.T.','http://www.team1999.99k.org'," +
		" 'Fort Walton Beach','FL','USA',NULL,NULL,NULL);";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_4 = SQL_INSERT_TEAMS +
		"('frc2000',2000,'TEAM ROCK','http://teamrockrobotics.com'," +
		" 'Dorr','MI','USA',NULL,2007,NULL)," +
		"('frc2001',2001,'HERMES','http://www.eaglesrobotics.com'," +
		" 'Kansas City','Missouri','USA',NULL,2007,'We can rebuild it ... We have the technology')," +
		"('frc2002',2002,'Tualatin Robotics','http://tualatinrobotics.com/'," +
		" 'Tualatin','Oregon','USA',NULL,2007,'Design, Build, Compete, Win!')," +
		"('frc2004',2004,'Thunderducks','http://www.firstinspires.org/'," +
		" 'Tulsa','Oklahoma','USA',NULL,2007,'No killing')," +
		"('frc2005',2005,'Aerobotechs',NULL," +
		" 'Kansas City','MO','USA',NULL,2007,NULL)," +
		"('frc2007',2007,'Robots of the Round Table','http://duluthrobotics.org'," +
		" 'Duluth','GA','USA',NULL,2007,NULL)," +
		"('frc2008',2008,'VTEC',NULL," +
		" 'Kansas City','MO','USA',NULL,2007,NULL)," +
		"('frc2009',2009,'Robo-Titans','http://newmissionrobotics.blogspot.com'," +
		" 'Roxbury','MA','USA',NULL,2007,NULL)," +
		"('frc2010',2010,'The Lightning Bots','http://www.chs-robotics.org'," +
		" 'Warren','Ohio','USA',NULL,2007,NULL)," +
		"('frc2011',2011,'SMH Robotics','http://www.smhrobotics.com'," +
		" 'Independence','MO','USA',NULL,2007,NULL)," +
		"('frc2012',2012,'@ Zenith',NULL," +
		" 'North Kansas City','MO','USA',NULL,2007,NULL)," +
		"('frc2013',2013,'Cybergnomes','http://www.Cybergnomes.ca'," +
		" 'Clearview Twp.','Ontario','Canada',NULL,2007,'You''ll never see us coming !')," +
		"('frc2014',2014,'Spartans','http://www.spartanrobotics.net'," +
		" 'St. Louis','MO','USA',NULL,2007,NULL)," +
		"('frc2015',2015,'Static shock','http://team2015robotics.com'," +
		" 'Holland','MI','USA',NULL,2007,NULL)," +
		"('frc2016',2016,'Mighty Monkey Wrenches','http://www.goteam2016.com'," +
		" 'Ewing','New Jersey','USA',NULL,2007,'Heavy Metal Monkey Business')," +
		"('frc2017',2017,'Trojans','http://www.mpsj.ca/robotics'," +
		" 'Etobicoke','ON','Canada',NULL,2007,NULL)," +
		"('frc2019',2019,'Robo Runners',NULL," +
		" 'El Cajon','CA','USA',NULL,2007,NULL)," +
		"('frc2021',2021,'F.A.R.','http://fredericksburgacademy.org'," +
		" 'Fredericksburg','VA','USA',NULL,2007,NULL)," +
		"('frc2022',2022,'Titan Robotics','http://www.firstinspires.org/'," +
		" 'Aurora','Illinois','USA',NULL,2007,'Measure twice, cut once')," +
		"('frc2023',2023,'PiraTech','http://www.piratechrobotics.com'," +
		" 'Melbourne','FL','USA',NULL,2007,NULL)," +
		"('frc2024',2024,'Warrior Pride',NULL," +
		" 'Hilo','Hawaii','USA',NULL,2007,'Don''t finish last')," +
		"('frc2025',2025,'Aztecs',NULL," +
		" 'Barstow','CA','USA',NULL,2007,NULL)," +
		"('frc2026',2026,'Senai',NULL," +
		" 'Porto Alegre','RS','Brazil',NULL,2007,NULL)," +
		"('frc2027',2027,'Robotic Dragons','http://www.firstinspires.org/'," +
		" 'Old Westbury','New York','USA',NULL,2007,NULL)," +
		"('frc2028',2028,'Phantom Mentalist','http://www.phantommentalists.com/'," +
		" 'Hampton','Virginia','USA',NULL,2007,'We know what you''re thinking')," +
		"('frc2029',2029,'Neo-Tech Robotics','http://www.neotechrobotics.org'," +
		" 'Ramona','California','USA',NULL,2007,NULL)," +
		"('frc2030',2030,'RoboTigers',NULL," +
		" 'Kansas City','MO','USA',NULL,2007,NULL)," +
		"('frc2031',2031,'Brainy-Actz','http://www.geocities.com/brainyactz2031/home.html'," +
		" 'Columbus','OH','USA',NULL,2007,NULL)," +
		"('frc2032',2032,'Funky Monkeys','http://robotics.sdhs.sandi.net'," +
		" 'San Diego','CA','USA',NULL,2007,NULL)," +
		"('frc2033',2033,'Eagles','http://www.englewood.cps.k12.il.us'," +
		" 'Chicago','IL','USA',NULL,2007,NULL)," +
		"('frc2034',2034,'IncrediBULLS','http://www.legacyhigh.net/apps/pages/index.jsp?uREC_ID=236200&type=d'," +
		" 'North Las Vegas','Nevada','USA',NULL,2007,NULL)," +
		"('frc2035',2035,'Robo Rockin'' Bots','http://2035.rocks'," +
		" 'Carmel','California','USA',NULL,2007,'Taking it to eleven since 2007')," +
		"('frc2036',2036,'The Black Knights','http://fairviewrobotics.org/'," +
		" 'Boulder','Colorado','USA',NULL,2007,'\"At least it didn''t catch on fire\"')," +
		"('frc2037',2037,'Oranje Lions','http://www.team2037.com'," +
		" 'Brunssum','LI','Netherlands',NULL,2007,NULL)," +
		"('frc2038',2038,'Sky Bandits',NULL," +
		" 'Griffin','GA','USA',NULL,2007,NULL)," +
		"('frc2039',2039,'Rockford Robotics','http://www.rockfordrobotics.com'," +
		" 'Machesney Park','Illinois','USA',NULL,2007,'Think outside the ''bots.')," +
		"('frc2040',2040,'DERT - Dunlap Eagles Robotics Team','http://www.dert2040.com/'," +
		" 'Dunlap','Illinois','USA',NULL,2007,'To grow leaders, FIRST you need a little DERT')," +
		"('frc2041',2041,'(RC)2',NULL," +
		" 'Chicago','IL','USA',NULL,2007,NULL)," +
		"('frc2042',2042,'Robo-trees',NULL," +
		" 'Santa Clarita','CA','USA',NULL,2007,NULL)," +
		"('frc2043',2043,'Frequency 2043',NULL," +
		" 'Roxbury','MA','USA',NULL,2007,NULL)," +
		"('frc2044',2044,'Big Red Robotics',NULL," +
		" 'Papillion','NE','USA',NULL,2007,NULL)," +
		"('frc2045',2045,'Robo-Techs','http://geocities.com/susanarn2000/homepage.html'," +
		" 'Oklahoma City','OK','USA',NULL,2007,NULL)," +
		"('frc2046',2046,'Bear Metal','http://tahomarobotics.org/'," +
		" 'Maple Valley','Washington','USA',NULL,2007,'It goes to 11')," +
		"('frc2047',2047,'Team Challenger',NULL," +
		" 'Chicago','IL','USA',NULL,2007,NULL)," +
		"('frc2048',2048,'The Pink Panthers','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2007,'Made with a woman''s touch')," +
		"('frc2049',2049,'Gênesis Technology',NULL," +
		" 'Gravataí','RS','Brazil',NULL,2007,NULL)," +
		"('frc2050',2050,'SEB',NULL," +
		" 'Detroit','MI','USA',NULL,2007,NULL)," +
		"('frc2051',2051,'The Beattie Bulldogs','http://tinyurl.com/beattiebulldogs'," +
		" 'Allison Park','Pennsylvania','USA',NULL,2007,'Run With the Pack')," +
		"('frc2052',2052,'KnightKrawler','http://www.team2052.com'," +
		" 'New Brighton','Minnesota','USA',NULL,2007,NULL)," +
		"('frc2053',2053,'TigerTronics','http://www.team2053.org'," +
		" 'Endicott','New York','USA',NULL,2007,'Extinction is not an option')," +
		"('frc2054',2054,'Tech Vikes','http://frc.techvikes.com'," +
		" 'Hopkins','Michigan','USA',NULL,2007,'Together Everyone Achieves More')," +
		"('frc2055',2055,'RoboForce','http://roboforce.org'," +
		" 'Cary','NC','USA',NULL,2007,NULL)," +
		"('frc2056',2056,'OP Robotics','http://www.2056.ca'," +
		" 'Stoney Creek','Ontario','Canada',NULL,2007,'Perseverance Develops Character')," +
		"('frc2057',2057,'AV CyberBulls','http://www.arborviewhs.net/robotics'," +
		" 'Las Vegas','NV','USA',NULL,2007,NULL)," +
		"('frc2059',2059,'The Hitchhikers','http://www.team2059.org/'," +
		" 'Apex','North Carolina','USA',NULL,2007,'BE THE NEXT LEADERS!')," +
		"('frc2060',2060,'ECHS Robotics','http://www.ecrobo.co.nr'," +
		" 'Tulsa','OK','USA',NULL,2007,NULL)," +
		"('frc2061',2061,'Pioneers','http://ccsd.net/schools/canyonsprings/Robotics/Robotics-index.html'," +
		" 'N Las Vegas','NV','USA',NULL,2007,NULL)," +
		"('frc2062',2062,'C.O.R.E  2062','http://www.core2062.com'," +
		" 'Waukesha','Wisconsin','USA',NULL,2007,'Core Values')," +
		"('frc2063',2063,'Green Machine','http://www.pitmanrobotics.com'," +
		" 'Turlock','CA','USA',NULL,2007,NULL)," +
		"('frc2064',2064,'The Panther Project','http://www.frc2064.com/'," +
		" 'Southbury / Middlebury','Connecticut','USA',NULL,2007,NULL)," +
		"('frc2065',2065,'The Lions','http://www.byerslions.net'," +
		" 'Holly Springs','MS','USA',NULL,2007,NULL)," +
		"('frc2066',2066,'PFTB of Doom','http://Pftbrobotics.com'," +
		" 'El Centro','CA','USA',NULL,2007,NULL)," +
		"('frc2067',2067,'Apple Pi','http://www.applepirobotics.org'," +
		" 'Guilford','Connecticut','USA',NULL,2007,'How ''bout them apples?')," +
		"('frc2068',2068,'Metal Jackets','http://www.metaljackets.org/'," +
		" 'Manassas','Virginia','USA',NULL,2007,'Defend the Hive')," +
		"('frc2069',2069,'Black Widow','http://myspace%20ehs%20Robotics'," +
		" 'Las Vegas','NV','USA',NULL,2007,NULL)," +
		"('frc2070',2070,'Royals','https://sites.google.com/view/team2070/'," +
		" 'Ridgefield','New Jersey','USA',NULL,2007,NULL)," +
		"('frc2071',2071,'Autonomous Eagles',NULL," +
		" 'Arimo','ID','USA',NULL,2007,NULL)," +
		"('frc2072',2072,'Carver RoboTechs',NULL," +
		" 'Houston','TX','USA',NULL,2007,NULL)," +
		"('frc2073',2073,'EagleForce','http://Team2073.com'," +
		" 'Elk Grove','California','USA',NULL,2007,NULL)," +
		"('frc2074',2074,'Robohoundz','http://icnorcomrobotics.co.nr'," +
		" 'Portsmouth','VA','USA',NULL,2007,NULL)," +
		"('frc2075',2075,'Enigma Robotics','http://www.enigmafirstrobotics.com'," +
		" 'Grand Rapids','Michigan','USA',NULL,2007,NULL)," +
		"('frc2076',2076,'Scots','http://tcirobotics.com'," +
		" 'Toronto','ON','Canada',NULL,2007,NULL)," +
		"('frc2077',2077,'Laser Robotics','http://laserrobotics.org'," +
		" 'Wales','Wisconsin','USA',NULL,2007,'Danger! Danger!')," +
		"('frc2078',2078,'Robotic Wolves','http://spsrobotics.org'," +
		" 'Covington','Louisiana','USA',NULL,2007,'The strength of the wolf is the pack')," +
		"('frc2079',2079,'4H ALARM Robotics','http://Alarmrobotics.com'," +
		" 'Bellingham','Massachusetts','USA',NULL,2007,'faciendo discimus')," +
		"('frc2080',2080,'Torbotics','http://www.torbotics2080.org'," +
		" 'Hammond','Louisiana','USA',NULL,2007,'Together Opening Realms Beyond Our Team Into our Community and Schools')," +
		"('frc2081',2081,'Icarus','http://Icarus2081.org'," +
		" 'Peoria','Illinois','USA',NULL,2007,'If it doesn''t work, try something else.')," +
		"('frc2083',2083,'Team BLITZ','http://www.TeamBlitz.net'," +
		" 'Conifer','Colorado','USA',NULL,2007,'Brainstorm, Build , Believe, Be the Future')," +
		"('frc2084',2084,'Robots by the C','http://www.robotsbythec.com'," +
		" 'Manchester','Massachusetts','USA',NULL,2007,'GO TEAM!')," +
		"('frc2085',2085,'RoboDogs','http://www.vhsrobotics.com'," +
		" 'Vacaville','California','USA',NULL,2007,NULL)," +
		"('frc2087',2087,'BFG (Building Future Generations)',NULL," +
		" 'Canby','OR','USA',NULL,2007,NULL)," +
		"('frc2089',2089,'IO',NULL," +
		" 'Lajeado','RS','Brazil',NULL,2007,NULL)," +
		"('frc2090',2090,'Buff ''n Blue','http://robotics.punahou.edu'," +
		" 'Honolulu','Hawaii','USA',NULL,2007,'Work hard, play hard.')," +
		"('frc2091',2091,'Olympians',NULL," +
		" 'New Orleans','LA','USA',NULL,2007,NULL)," +
		"('frc2092',2092,'Beaufort Robotics','http://web.beaufort.k12.sc.us/education/components/scrapbook/default.php?sectiondetailid=39076&sc_id=1191333003'," +
		" 'Beaufort','SC','USA',NULL,2007,NULL)," +
		"('frc2093',2093,'Bowtie Brigade','http://www.thebowtiebrigade.org'," +
		" 'Portland','OR','USA',NULL,2007,NULL)," +
		"('frc2095',2095,'Direct Current','http://www.firstinspires.org/'," +
		" 'Newtown Square','Pennsylvania','USA',NULL,2007,NULL)," +
		"('frc2096',2096,'RoboActive','http://roboactive.net/'," +
		" 'Dimona','HaDarom','Israel',NULL,2007,'Robotics is more than just robots')," +
		"('frc2097',2097,'Phoenix Force Robotics','http://PhoenixForceRobotics.org'," +
		" 'Tukwila','Washington','USA',NULL,2007,NULL)," +
		"('frc2098',2098,'Bulldogs',NULL," +
		" 'Atlanta','GA','USA',NULL,2007,NULL)," +
		"('frc2099',2099,'Blue on Blue',NULL," +
		" 'Jamaica Plain','MA','USA',NULL,2007,NULL)," +
		"('frc2100',2100,'M Squared','http://www.stlucie.k12.fl.us/slwch/Robotics%20site/index.htm'," +
		" 'Port St. Lucie','FL','USA',NULL,2007,NULL)," +
		"('frc2102',2102,'Team Paradox','https://team2102.org'," +
		" 'Encinitas','California','USA',NULL,2007,'Passion FIRST')," +
		"('frc2103',2103,'Fisherman','http://www.gloucesterschools.com'," +
		" 'Gloucester','MA','USA',NULL,2007,NULL)," +
		"('frc2104',2104,'The Colonels','http://www.colonelrobotics.com'," +
		" 'Worcester','MA','USA',NULL,2007,NULL)," +
		"('frc2105',2105,'University Park',NULL," +
		" 'Worcester','MA','USA',NULL,2007,NULL)," +
		"('frc2106',2106,'The Junkyard Dogs','http://www.goochlandrobotics.com/'," +
		" 'Goochland','Virginia','USA',NULL,2007,NULL)," +
		"('frc2107',2107,'Angry Bots','http://www.firstinspires.org/'," +
		" 'Norfolk','Virginia','USA',NULL,2007,'Just Do It')," +
		"('frc2108',2108,'Team Awkward Turtle','http://www.teamawkwardturtle.org'," +
		" 'Cary','NC','USA',NULL,2007,NULL)," +
		"('frc2109',2109,'Knight Vision',NULL," +
		" 'Kansas City','MO','USA',NULL,2007,NULL)," +
		"('frc2110',2110,'CHS',NULL," +
		" 'Charlestown','MA','USA',NULL,2007,NULL)," +
		"('frc2111',2111,'Eagles',NULL," +
		" 'Gilbert','AZ','USA',NULL,2007,NULL)," +
		"('frc2112',2112,'PHP Robo Warriors','http://www.myspace.com/roboteam2112'," +
		" 'Pembroke Twnshp','IL','USA',NULL,2007,NULL)," +
		"('frc2115',2115,'Nightmares','http://www.firstinspires.org/'," +
		" 'Mundelein','Illinois','USA',NULL,2007,'Virtuti et scientia')," +
		"('frc2116',2116,'Dane County Robotics','http://www.danecountyrobotics.org'," +
		" 'Stoughton','WI','USA',NULL,2007,NULL)," +
		"('frc2117',2117,'The West Georgia Wolvotics',NULL," +
		" 'Carrollton','GA','USA',NULL,2007,NULL)," +
		"('frc2119',2119,'SCAR (Sequoyah Computer Animation and Robotics)','http://2119robotics.weebly.com/index.html'," +
		" 'Canton','GA','USA',NULL,2007,NULL)," +
		"('frc2120',2120,'doncaster',NULL," +
		" 'doncaster','ENG','Kingdom',NULL,2007,NULL)," +
		"('frc2121',2121,'Mecha Clerks',NULL," +
		" 'Washington','DC','USA',NULL,2007,NULL)," +
		"('frc2122',2122,'Team Tators','http://www.teamtators.org'," +
		" 'Boise','Idaho','USA',NULL,2007,'\"Chance favors the prepared mind\" - Pasteur')," +
		"('frc2124',2124,'X-Factor',NULL," +
		" 'Hyde Park','MA','USA',NULL,2007,NULL)," +
		"('frc2125',2125,'Urban Science',NULL," +
		" 'West Roxbury','MA','USA',NULL,2007,NULL)," +
		"('frc2126',2126,'BCLA',NULL," +
		" 'Brighton','MA','USA',NULL,2007,NULL)," +
		"('frc2127',2127,'JQUS',NULL," +
		" 'Boston','MA','USA',NULL,2007,NULL)," +
		"('frc2128',2128,'SciTech','http://members.cox.net/chavezrobotics'," +
		" 'Laveen','AZ','USA',NULL,2007,NULL)," +
		"('frc2129',2129,'Ultraviolet','http://www.swrobotics.com'," +
		" 'Minneapolis','Minnesota','USA',NULL,2007,'We love Paul Blart')," +
		"('frc2130',2130,'Alpha+','http://www.bfhsrobotics.com'," +
		" 'Bonners Ferry','Idaho','USA',NULL,2007,'Who has the 7/16?')," +
		"('frc2132',2132,'Mustangs','http://www.geocities.com/mervoteam'," +
		" 'Baltimore','MD','USA',NULL,2007,NULL)," +
		"('frc2133',2133,'H.E.A.T.','http://heatrobotics.tripod.com'," +
		" 'Hohenwald','TN','USA',NULL,2007,NULL)," +
		"('frc2134',2134,'Aztecs',NULL," +
		" 'Tempe','AZ','USA',NULL,2007,NULL)," +
		"('frc2135',2135,'Presentation Invasion','http://frc2135.org'," +
		" 'San Jose','California','USA',NULL,2007,'')," +
		"('frc2136',2136,'Impossible Mission Force','http://www.2136.darkcloludhosting.com'," +
		" 'Chicago','Illinois','USA',NULL,2007,'Scraping the sky to serve you better')," +
		"('frc2137',2137,'T.O.R.C.','http://www.torc2137.com'," +
		" 'Oxford','Michigan','USA',NULL,2007,'\"We Build\"...more than robots!')," +
		"('frc2139',2139,'Road Runners','http://www.WeBuildFutures.net/robotics'," +
		" 'Las Vegas','NV','USA',NULL,2007,NULL)," +
		"('frc2140',2140,'Team Synergy',NULL," +
		" 'Pemberton','NJ','USA',NULL,2007,NULL)," +
		"('frc2141',2141,'Spartonics','http://www.firstinspires.org/'," +
		" 'Concord','California','USA',NULL,2007,'Science without religion is lame, religion without science is blind. ~Albert E')," +
		"('frc2142',2142,'Lakeview',NULL," +
		" 'Laleview','OR','USA',NULL,2007,NULL)," +
		"('frc2143',2143,'Team Tobor','http://www.teamtobor.org/'," +
		" 'Eau Claire','Wisconsin','USA',NULL,2007,'\"You Push a Button . . . \"')," +
		"('frc2144',2144,'Gators','http://www.shschools.org/Page/Campus-Life/Student-Activities/Robotics'," +
		" 'Atherton','California','USA',NULL,2007,'For the sake of one robot, we will build it.')," +
		"('frc2145',2145,'HAZMATs','https://sites.google.com/site/lf2145/'," +
		" 'Linden','Michigan','USA',NULL,2007,'Our Energy Is Corrosive')," +
		"('frc2147',2147,'CHUCK','http://chuck2147.com'," +
		" 'Spokane','Washington','USA',NULL,2007,NULL)," +
		"('frc2148',2148,'Mechaknights','http://www.Team2148.com'," +
		" 'Spokane','Washington','USA',NULL,2007,NULL)," +
		"('frc2149',2149,'CV Bearbots','http://cvhsrobots.com'," +
		" 'Veradale','Washington','USA',NULL,2007,'Linked together!')," +
		"('frc2150',2150,'W-Squared','http://www.wickedwobotics.org'," +
		" 'Palos Verdes Peninsula','CA','USA',NULL,2007,NULL)," +
		"('frc2151',2151,'Monty Pythons','http://www.firstinspires.org/'," +
		" 'Forest Park','Illinois','USA',NULL,2007,'It''s just a short circuit!')," +
		"('frc2152',2152,'S*M*A*S*H','http://www.facebook.com/team2152/'," +
		" 'Volusia County','Florida','USA',NULL,2007,'Insanity is just a state of mind!')," +
		"('frc2153',2153,'Chassell Robotics','http://team2153.org'," +
		" 'Chassell','MI','USA',NULL,2007,NULL)," +
		"('frc2154',2154,'AzTechs',NULL," +
		" 'Calexico','CA','USA',NULL,2007,NULL)," +
		"('frc2156',2156,'Wire Freaks','http://www.wirefreaks.com'," +
		" 'Sacramento','CA','USA',NULL,2007,NULL)," +
		"('frc2157',2157,'Apollo',NULL," +
		" 'Houston','TX','USA',NULL,2007,NULL)," +
		"('frc2158',2158,'ausTIN CANs','http://www.andersonrobotics.org/'," +
		" 'Austin','Texas','USA',NULL,2007,'Austin CAN!')," +
		"('frc2159',2159,'RoboPirates',NULL," +
		" 'San Leandro','CA','USA',NULL,2007,NULL)," +
		"('frc2161',2161,'Robocats','http://www.whitmanrobocats.com/'," +
		" 'Huntington Station','New York','USA',NULL,2007,'Relationships forged through Aluminum but  built for life')," +
		"('frc2162',2162,'MacAttacks',NULL," +
		" 'Boston','MA','USA',NULL,2007,NULL)," +
		"('frc2163',2163,'ChromePanthers','http://www.chromepanthers.com'," +
		" 'Lapeer','MI','USA',NULL,2007,NULL)," +
		"('frc2164',2164,'The Core','http://www.hhsrobotics.com/'," +
		" 'Harrisonville','Missouri','USA',NULL,2007,'An Important Part of Something Important')," +
		"('frc2165',2165,'BisonBots','http://www.team2165.weebly.com'," +
		" 'Bartlesville','Oklahoma','USA',NULL,2007,'Today''s Competitors, Tomorrow''s Champions')," +
		"('frc2166',2166,'Bluebotics','http://robotics.appleby.on.ca'," +
		" 'Oakville','ON','Canada',NULL,2007,NULL)," +
		"('frc2167',2167,'Radical Tech','http://www.firstinspires.org/'," +
		" 'Maryville','Missouri','USA',NULL,2007,NULL)," +
		"('frc2168',2168,'Aluminum Falcons','http://www.frc2168.com'," +
		" 'Groton','Connecticut','USA',NULL,2007,'Stay on Target')," +
		"('frc2169',2169,'KING TeC','https://www.kingtec2169.com'," +
		" 'Savage','Minnesota','USA',NULL,2007,'Beyond Rational Thought - V^5')," +
		"('frc2170',2170,'Titanium Tomahawks','https://sites.google.com/a/glastonburyus.org/ghs-first-robotics/'," +
		" 'Glastonbury','Connecticut','USA',NULL,2007,NULL)," +
		"('frc2171',2171,'RoboDogs','http://www.cprobodogs2171.weebly.com'," +
		" 'Crown Point','Indiana','USA',NULL,2007,'Improvise-Adapt-Overcome')," +
		"('frc2172',2172,'Street Legal','http://www.sehsrobotics.com/'," +
		" 'Lakewood','Ohio','USA',NULL,2007,NULL)," +
		"('frc2173',2173,'Robo Rocks','http://www.ststan.com/robotics'," +
		" 'Bay St. Louis','MS','USA',NULL,2007,NULL)," +
		"('frc2174',2174,'Iron Eagles',NULL," +
		" 'Watts','CA','USA',NULL,2007,NULL)," +
		"('frc2175',2175,'The Fighting Calculators','http://www.fightingcalculators.org/'," +
		" 'Saint Paul','Minnesota','USA',NULL,2007,NULL)," +
		"('frc2176',2176,'MAD SCIENTIST ASSOCIATION',NULL," +
		" 'Starkville','MS','USA',NULL,2007,NULL)," +
		"('frc2177',2177,'The Robettes','http://www.therobettes.com'," +
		" 'Mendota Heights','Minnesota','USA',NULL,2007,'Non Scholae Sed Vitae')," +
		"('frc2178',2178,'RioBotics','http://www.ouhsd.k12.ca.us/sites/rmhs/CLUBS/robotics.htm'," +
		" 'Oxnard','CA','USA',NULL,2007,NULL)," +
		"('frc2180',2180,'Zero Gravity','http://steinertrobotics.net'," +
		" 'Hamilton','New Jersey','USA',NULL,2007,'Nothing Can Hold Us Down!')," +
		"('frc2181',2181,'G.E.A.R.s (Great Engineers Awesome Robots)','https://sites.google.com/view/blainefrcteam2181gears'," +
		" 'Blaine','Minnesota','USA',NULL,2007,'Our gears are always turning.')," +
		"('frc2182',2182,'Tyborgs','http://www.slidellrobotics.org'," +
		" 'Slidell','LA','USA',NULL,2007,NULL)," +
		"('frc2183',2183,'Tigerbots','http://www.stcharles.k12.la.us/site/default.aspx?DomainID=642'," +
		" 'Boutte','Louisiana','USA',NULL,2007,NULL)," +
		"('frc2184',2184,'Team Injun Ingenuity',NULL," +
		" 'Whiteriver','AZ','USA',NULL,2007,NULL)," +
		"('frc2185',2185,'RAMAZOIDZ','http://etobicokeci.ca'," +
		" 'Toronto','Ontario','Canada',NULL,2007,'Students Preparing for Tomorrow. Today.')," +
		"('frc2186',2186,'Dogs of Steel','http://www.dogsofsteel.org'," +
		" 'Chantilly','Virginia','USA',NULL,2007,NULL)," +
		"('frc2187',2187,'Team Volt','http://ata.horrycountyschools.net/for_students/robotics_team/'," +
		" 'Conway','South Carolina','USA',NULL,2007,'We do more with less')," +
		"('frc2188',2188,'Critical Mass','http://robotics.bas-k12.org'," +
		" 'Belding','MI','USA',NULL,2007,NULL)," +
		"('frc2189',2189,'woodland Robotics',NULL," +
		" 'Woodland','CA','USA',NULL,2007,NULL)," +
		"('frc2190',2190,'Team Hero','http://josephbastine.wix.com/teamhero2190#!'," +
		" 'Petal','Mississippi','USA',NULL,2007,NULL)," +
		"('frc2191',2191,'Flux Core','http://www.firstteam2191.org'," +
		" 'Hamilton','New Jersey','USA',NULL,2007,'You will be seeing stars')," +
		"('frc2192',2192,'YAK Attack','http://%20%20%20%20%20%20%20%20team2192.org%20'," +
		" 'Newport','OR','USA',NULL,2007,NULL)," +
		"('frc2193',2193,'EPIC Robotics','http://www.hilltoprobotics.com'," +
		" 'Chula Vista','CA','USA',NULL,2007,NULL)," +
		"('frc2194',2194,'Fondy Fire','http://fondyfire.com/'," +
		" 'Fond du Lac','Wisconsin','USA',NULL,2007,'We Build More Than Just Smokin'' Robots')," +
		"('frc2196',2196,'Béésh Bá''áh Alchini',NULL," +
		" 'Shiprock','NM','USA',NULL,2007,NULL)," +
		"('frc2197',2197,'Las Pumas','http://www.laspumas2197.org'," +
		" 'New Carlisle','Indiana','USA',NULL,2007,'Sailing the FRC.')," +
		"('frc2198',2198,'Paradigm Shift','http://www.team2198.org'," +
		" 'Toronto','Ontario','Canada',NULL,2007,'Believe in Miracles')," +
		"('frc2199',2199,'Robo-Lions','http://www.robo-lions.org/'," +
		" 'Finksburg','Maryland','USA',NULL,2007,NULL)," +
		"('frc2200',2200,'MMRambotics','http://www.mmrambotics.com'," +
		" 'Burlington','Ontario','Canada',NULL,2007,NULL)," +
		"('frc2201',2201,'TGM','http://www.tgmtech.com/robotics'," +
		" 'Grandview','MO','USA',NULL,2007,NULL)," +
		"('frc2202',2202,'BEAST Robotics','http://www.beastrobotics.com'," +
		" 'Brookfield','Wisconsin','USA',NULL,2007,'FIRST Things First!')," +
		"('frc2203',2203,'Cyber-Eagles','http://www.wix.com/pandamochofun/robotics  '," +
		" 'Bronx','NY','USA',NULL,2007,NULL)," +
		"('frc2204',2204,'Rambots','http://www.2204rambots.weebly.com'," +
		" 'Hayward','California','USA',NULL,2007,NULL)," +
		"('frc2205',2205,'Juggernauts',NULL," +
		" 'Mount Vernon','NY','USA',NULL,NULL,NULL)," +
		"('frc2206',2206,'Ehret Patriots',NULL," +
		" 'Marrero','LA','USA',NULL,2007,NULL)," +
		"('frc2207',2207,'Bright Bears','http://www.whitebearlakerobotics.com'," +
		" 'White Bear Lake','Minnesota','USA',NULL,2007,'Bright Students, Bright Ideas, Bright Futures')," +
		"('frc2208',2208,'Makif Dalet','http://www.frc2208.tk'," +
		" 'Ashdod','D','Israel',NULL,2007,NULL)," +
		"('frc2209',2209,'Rabin HS','http://www.b7rabin.org.il/portal/tabid/110/Default.aspx'," +
		" 'Beer Sheva','D','Israel',NULL,2007,NULL)," +
		"('frc2210',2210,'Yarka High School',NULL," +
		" 'Yarka','Z','Israel',NULL,2007,NULL)," +
		"('frc2211',2211,'Ort IAI',NULL," +
		" 'Lod','M','Israel',NULL,2007,NULL)," +
		"('frc2212',2212,'The Spikes','http://spikes2212.com/'," +
		" 'Lod','HaMerkaz','Israel',NULL,2007,'We will graciously and professionally spike you!')," +
		"('frc2213',2213,'AXIOM','http://www.axiom2213.com/'," +
		" 'Nahariya','HaZafon (Northern)','Israel',NULL,2007,NULL)," +
		"('frc2214',2214,'ASSIT','http://space.ort.org.il/dupelt'," +
		" 'Yemin Orde','HA','Israel',NULL,2007,NULL)," +
		"('frc2215',2215,'Akkoleon',NULL," +
		" 'Akko','Z','Israel',NULL,2007,NULL)," +
		"('frc2216',2216,'robodim',NULL," +
		" 'Dimona','HaDarom (Southern)','Israel',NULL,2007,NULL)," +
		"('frc2217',2217,'Kiryat Motzkin','http://2217.biztv.co.il'," +
		" 'Kiryat Motzkin','Z','Israel',NULL,2007,NULL)," +
		"('frc2219',2219,'Megahurts','http://www.megahurts2219.com'," +
		" 'Carbondale','IL','USA',NULL,2007,NULL)," +
		"('frc2220',2220,'Blue Twilight ','http://team2220.org'," +
		" 'Eagan','Minnesota','USA',NULL,2007,'Blue Twilight - Lighting Up Robotics...Lighting up the world!')," +
		"('frc2221',2221,'FHS Robodawgs','http://2221.lafrc.org'," +
		" 'Mandeville','Louisiana','USA',NULL,2007,'\"Do What You''re Great At\"')," +
		"('frc2222',2222,'Abes',NULL," +
		" 'Tacoma','WA','USA',NULL,2007,NULL)," +
		"('frc2223',2223,'B-ROC',NULL," +
		" 'Philadelphia','PA','USA',NULL,2007,NULL)," +
		"('frc2224',2224,'RoboPhoenix','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2007,'#ornah')," +
		"('frc2225',2225,'R.U.S.T','http://www.facebook.com/pages/RUST-Robots-Uniting-Students-Together/176671705704918'," +
		" 'Brooklyn Park','Minnesota','USA',NULL,2007,'We''ll fix it in Post')," +
		"('frc2226',2226,'Bronco''s','http://sjh-www.sanjuanschools.org'," +
		" 'Blanding','UT','USA',NULL,2007,NULL)," +
		"('frc2227',2227,'Tigers','http://www.firstinspires.org/'," +
		" 'Fridley','Minnesota','USA',NULL,2007,NULL)," +
		"('frc2228',2228,'CougarTech','http://www.hflrobotics.com/'," +
		" 'Honeoye Falls','New York','USA',NULL,2007,'“Drive it like you stole it.”')," +
		"('frc2229',2229,'ROBOSHOP',NULL," +
		" 'Plymouth Meeting','PA','USA',NULL,2007,NULL)," +
		"('frc2230',2230,'General Angels','http://www.frc2230.com'," +
		" 'Herzliya','HaMerkaz','Israel',NULL,2007,'We do FIRST A2Z!')," +
		"('frc2231',2231,'OnyxTronix','https://onyxtronix2231.wixsite.com/home'," +
		" 'Shoham','HaMerkaz','Israel',NULL,2007,'Every member is a valuable member')," +
		"('frc2232',2232,'Deus ex Machina','http://frc2232.github.io/'," +
		" 'Anoka','Minnesota','USA',NULL,2007,NULL)," +
		"('frc2234',2234,'Alternating Current','http://www.firstinspires.org/'," +
		" 'Newtown Square','Pennsylvania','USA',NULL,2007,'Esse Quam Videri')," +
		"('frc2235',2235,'Abraxas Metal Heads',NULL," +
		" 'Poway','CA','USA',NULL,2007,NULL)," +
		"('frc2236',2236,'Genese Team','http://www.team2236.com'," +
		" 'Sao Paulo','SP','Brazil',NULL,2007,NULL)," +
		"('frc2237',2237,'The Junkyard Robo''s','http://www.team2237.org'," +
		" 'Beckley','WV','USA',NULL,2007,NULL)," +
		"('frc2239',2239,'Technocrats','http://www.firstinspires.org/'," +
		" 'Hopkins','Minnesota','USA',NULL,2007,NULL)," +
		"('frc2240',2240,'Brute Force','http://www.dsstrobotics.org'," +
		" 'Denver','Colorado','USA',NULL,2007,NULL)," +
		"('frc2241',2241,'SWAT team',NULL," +
		" 'Coon Rapids','MN','USA',NULL,2007,NULL)," +
		"('frc2242',2242,'Cyber Claw',NULL," +
		" 'New Orleans','Louisiana','USA',NULL,2007,NULL)," +
		"('frc2243',2243,'BRATECC',NULL," +
		" 'Campinas','SP','Brazil',NULL,2007,NULL)," +
		"('frc2244',2244,'The Chips','http://www.escolavirtual.org.br/frc/osasco'," +
		" 'Osasco','SP','Brazil',NULL,2007,NULL)," +
		"('frc2245',2245,'PANTHERS','http://frankfort.k12.mi.us'," +
		" 'Frankfort','MI','USA',NULL,2007,NULL)," +
		"('frc2246',2246,'The Army of Sum','http://firstarmyofsum.wix.com/home'," +
		" 'Johannesburg','Michigan','USA',NULL,2007,'Whatever it takes!!')," +
		"('frc2247',2247,'FB HT','http://www.escolavirtual.org.br/frc/gravatai'," +
		" 'Gravataí','RS','Brazil',NULL,2007,NULL)," +
		"('frc2249',2249,'KISS',NULL," +
		" 'Billings','MT','USA',NULL,2007,NULL)," +
		"('frc2250',2250,'Lancerbotics','http://alhsrobotics.wikispaces.com'," +
		" 'Denver','CO','USA',NULL,2007,NULL)," +
		"('frc2251',2251,'Hawks',NULL," +
		" 'Bozeman','MT','USA',NULL,2007,NULL)," +
		"('frc2252',2252,'The Mavericks','http://www.mavericks2252.net'," +
		" 'Milan','Ohio','USA',NULL,2007,'Engineering Was Never This Wild!')," +
		"('frc2254',2254,'Twisted Transistors','http://www.twistedtransistors.com'," +
		" 'Mississauga','ON','Canada',NULL,2007,NULL)," +
		"('frc2257',2257,'Afula High School',NULL," +
		" 'Afula','Z','Israel',NULL,2007,NULL)," +
		"('frc2259',2259,'1/4 Twenties','http://www.firstinspires.org/'," +
		" 'Denver','Colorado','USA',NULL,2007,NULL)," +
		"('frc2260',2260,'Transformers',NULL," +
		" 'Edwards','CO','USA',NULL,2007,NULL)," +
		"('frc2261',2261,'Casa Robotics','http://www.casarobotics.org'," +
		" 'Longmont','Colorado','USA',NULL,2007,'¡Sí se puede!  (Yes, we can!)')," +
		"('frc2262',2262,'Robo-Panthers','http://www.firstinspires.org/'," +
		" 'Holliston','Massachusetts','USA',NULL,2007,'Quidquid potest errare, errabit.')," +
		"('frc2264',2264,'Wayzata Robotics','http://www.team2264.com'," +
		" 'Plymouth','Minnesota','USA',NULL,2007,'Initiate. Innovate')," +
		"('frc2265',2265,'Fe Maidens','http://www.femaidens.org'," +
		" 'Bronx','New York','USA',NULL,2007,'Talk Nerdy To Us')," +
		"('frc2266',2266,'Colegio Sacramentinas','http://www.sa1team.com.br'," +
		" 'Salvador','BA','Brazil',NULL,2007,NULL)," +
		"('frc2269',2269,'Dragons',NULL," +
		" 'Lindale','GA','USA',NULL,2007,NULL)," +
		"('frc2272',2272,'Metalheads','http://groups.yahoo.com/group/conejorobotics'," +
		" 'Thousand Oaks','CA','USA',NULL,2007,NULL)," +
		"('frc2273',2273,'The Mechanix Coalition',NULL," +
		" 'Surrey','BC','Canada',NULL,2007,NULL)," +
		"('frc2274',2274,'Fuego',NULL," +
		" 'Bronx','NY','USA',NULL,2007,NULL)," +
		"('frc2275',2275,'Bello Bots','http://bellobots2275.org'," +
		" 'Denver','CO','USA',NULL,2007,NULL)," +
		"('frc2276',2276,'STARS of Cypress','http://www.starsofcypress.webs.com'," +
		" 'Cypress','TX','USA',NULL,2007,NULL)," +
		"('frc2278',2278,'AVENGING PLUTO','http://www.avengingpluto.com'," +
		" 'Orlando','FL','USA',NULL,2007,NULL)," +
		"('frc2279',2279,'The Butlers','http://Bcvt.tec.pa.us'," +
		" 'Butler','PA','USA',NULL,2007,NULL)," +
		"('frc2280',2280,'YWCA DELTA F.O.R.C.E.',NULL," +
		" 'Pittsburgh','PA','USA',NULL,2007,NULL)," +
		"('frc2283',2283,'Panteras','https://www.panterasup.com'," +
		" 'Mexico City','Distrito Federal','Mexico',NULL,2007,'Coming together is the beginning, working together is the success!')," +
		"('frc2285',2285,'Irvington Blue Knights 4-H',NULL," +
		" 'Irvington','NJ','USA',NULL,2007,NULL)," +
		"('frc2287',2287,'RoboTech Pirates',NULL," +
		" 'Sinton','TX','USA',NULL,2007,NULL)," +
		"('frc2330',2330,'F-Tech','http://www.jadamsmmp.com/robotics'," +
		" 'Fletcher','OK','USA',NULL,2008,NULL)," +
		"('frc2332',2332,'Team Lucky',NULL," +
		" 'Fairview','OK','USA',NULL,2008,NULL)," +
		"('frc2333',2333,'S.C.R.E.E.C.H.','http://sapulpachieftainrobotics.webs.com/'," +
		" 'Sapulpa','Oklahoma','USA',NULL,2008,NULL)," +
		"('frc2334',2334,'Blue Valley CAPS Robotics','http://www.bvrobotics.com'," +
		" 'Stilwell','KS','USA',NULL,2008,NULL)," +
		"('frc2335',2335,'Sargon','http://www.sargonrobotics.com'," +
		" 'Prairie Village','Kansas','USA',NULL,2008,'Conquering through cooperation')," +
		"('frc2336',2336,'Titan Crusaders',NULL," +
		" 'Midlothian','VA','USA',NULL,2008,NULL)," +
		"('frc2337',2337,'EngiNERDs','http://www.team2337.com'," +
		" 'Grand Blanc','Michigan','USA',NULL,2008,'Inspire. Redefine. Design. Change.')," +
		"('frc2338',2338,'Gear It Forward','http://www.oswegofirst.org'," +
		" 'Oswego','Illinois','USA',NULL,2008,'Two Schools, One Team')," +
		"('frc2339',2339,'Robolopes','http://www.wix.com/robolopes/robolopes'," +
		" 'Lancaster','California','USA',NULL,2008,'Scientia Super Omnia')," +
		"('frc2340',2340,'Xcentrics','http://www.team2340.org/'," +
		" 'Rochester','New York','USA',NULL,2008,'Inspire young women to go beyond the boundaries of today')," +
		"('frc2341',2341,'Sprockets','http://sprocketsteam2341.weebly.com'," +
		" 'Shawnee','Oklahoma','USA',NULL,2008,'M.A.D. (Making a Difference)')," +
		"('frc2342',2342,'Team Phoenix','http://team2342.org/'," +
		" 'Nashua','New Hampshire','USA',NULL,2008,'Ex cinis cineris')," +
		"('frc2343',2343,'Technohorns',NULL," +
		" 'Inola','OK','USA',NULL,2008,NULL)," +
		"('frc2344',2344,'The Saunders Droid Factory ','http://www.saundersdroidfactory.com'," +
		" 'Yonkers','New York','USA',NULL,2008,'Student Run, Student Done')," +
		"('frc2345',2345,'Animal Control','http://www.firstinspires.org/'," +
		" 'Kearney','Missouri','USA',NULL,2008,NULL)," +
		"('frc2346',2346,'Cel-Techs','https://sites.google.com/a/oharahs.org/ohara-celtechs/'," +
		" 'Kansas City','Missouri','USA',NULL,2008,NULL)," +
		"('frc2347',2347,'Metal Mercs','http://www.firstinspires.org/'," +
		" 'Copiague','New York','USA',NULL,2008,'Mechanized Madness')," +
		"('frc2348',2348,'Cool Geeks','http://mohs-robotics.k12.hi.us/'," +
		" 'Honolulu','Hawaii','USA',NULL,2008,NULL)," +
		"('frc2349',2349,'Hurriquake',NULL," +
		" 'Wayland','MA','USA',NULL,2008,NULL)," +
		"('frc2350',2350,'Region 1 Robotics',NULL," +
		" 'Columbus','OH','USA',NULL,2008,NULL)," +
		"('frc2352',2352,'Metal Mayhem','http://www.metalmayhem2352.com'," +
		" 'Ada','Oklahoma','USA',NULL,2008,'Mind Over Metal: Focus Determines Reality')," +
		"('frc2353',2353,'The Legion','http://phslegion2353.com'," +
		" 'Kansas City','Missouri','USA',NULL,2008,NULL)," +
		"('frc2354',2354,'Red River Robotics','http://www.firstinspires.org/'," +
		" 'Duncan','Oklahoma','USA',NULL,2008,'Channeling Creativity')," +
		"('frc2357',2357,'System Meltdown','https://www.systemmeltdown.com/'," +
		" 'Peculiar','Missouri','USA',NULL,2008,NULL)," +
		"('frc2358',2358,'Bearbotics','http://www.firstinspires.org/'," +
		" 'Lake Zurich','Illinois','USA',NULL,2008,'Too much Bot for you to Bear')," +
		"('frc2359',2359,'RoboLobos','http://team2359.wixsite.com/robotics'," +
		" 'Edmond','Oklahoma','USA',NULL,2008,NULL)," +
		"('frc2360',2360,'Tech Titan''s POWER-Storm','http://www.facebook.com/Powerstormrobotics/?fref=ts'," +
		" 'Indianapolis','Indiana','USA',NULL,2008,NULL)," +
		"('frc2361',2361,'PACE Invaders','http://www.pacerobotics.com'," +
		" 'Richmond Hill','ON','Canada',NULL,2008,NULL)," +
		"('frc2362',2362,'Olympic Robotics','http://www.olympicrobotics.org'," +
		" 'Charlotte','NC','USA',NULL,2008,NULL)," +
		"('frc2363',2363,'Triple Helix','http://www.team2363.org'," +
		" 'Newport News','Virginia','USA',NULL,2008,'It''s in our genes!')," +
		"('frc2364',2364,'Oregon RoboHawks','http://www.oregonrobohawks.com'," +
		" 'Oregon','IL','USA',NULL,2008,NULL)," +
		"('frc2365',2365,'Lions',NULL," +
		" 'Phoenix','AZ','USA',NULL,2008,NULL)," +
		"('frc2366',2366,'Tesla Robotics','http://www.teslarobotics.com'," +
		" 'Independence','MO','USA',NULL,2008,NULL)," +
		"('frc2367',2367,'Lancer Robotics','http://www.sfhsrobotics.com/'," +
		" 'Mountain View','California','USA',NULL,2008,NULL)," +
		"('frc2368',2368,'South Robotics',NULL," +
		" 'Terre Haute','IN','USA',NULL,2008,NULL)," +
		"('frc2369',2369,'Maximus Roboticus','http://www.team2369.com'," +
		" 'Stillwater','OK','USA',NULL,2008,NULL)," +
		"('frc2370',2370,'IBOTS','http://2370.rutlandarearobotics.org'," +
		" 'Rutland','Vermont','USA',NULL,2008,'Intelligence Beyond Ordinary Teenage Students')," +
		"('frc2371',2371,'Eagle Pride',NULL," +
		" 'Morris','OK','USA',NULL,2008,NULL)," +
		"('frc2372',2372,'Robo-Hooters',NULL," +
		" 'Elgin','OK','USA',NULL,2008,NULL)," +
		"('frc2373',2373,'The Crickets','http://www.firstinspires.org/'," +
		" 'Fort Cobb','Oklahoma','USA',NULL,2008,'Theoretically.... this should work....theoretically')," +
		"('frc2374',2374,'Crusader Bots','http://www.team2374.com'," +
		" 'Portland','Oregon','USA',NULL,2008,'A posse ad esse   \"from possibility to actuality\"')," +
		"('frc2375',2375,'Dragon Robotics','http://www.dragonrobotics2375.com'," +
		" 'Phoenix','Arizona','USA',NULL,2008,'\"What''s a Weekend without Robotics?\"')," +
		"('frc2376',2376,'RoboCOMETS',NULL," +
		" 'Tulsa','OK','USA',NULL,2008,NULL)," +
		"('frc2377',2377,'C Company','https://www.facebook.com/pages/FIRST-Robotics-Team-2377/1410647532563646?sk=info&tab=page_info'," +
		" 'Pasadena','Maryland','USA',NULL,2008,NULL)," +
		"('frc2378',2378,'Team Mako 2378',NULL," +
		" 'Taloga','OK','USA',NULL,2008,NULL)," +
		"('frc2380',2380,'Jag Robotics','http://www.jagrobotics.org'," +
		" 'Mesa','AZ','USA',NULL,2008,NULL)," +
		"('frc2381',2381,'The Ark',NULL," +
		" 'Chicago','IL','USA',NULL,2008,NULL)," +
		"('frc2382',2382,'The Leopard Legion','http://www.firstinspires.org/'," +
		" 'Colbert','Oklahoma','USA',NULL,2008,'\"Veni, vidi, vici\"')," +
		"('frc2383',2383,'Ninjineers','http://team2383.com/'," +
		" 'Fort Lauderdale','Florida','USA',NULL,2008,'Individually we engineer togetherness!')," +
		"('frc2385',2385,'Obelisk Trio',NULL," +
		" 'Tulsa','OK','USA',NULL,2008,NULL)," +
		"('frc2386',2386,'Trojans','https://www.bchrobotics.com'," +
		" 'Burlington','Ontario','Canada',NULL,2008,NULL)," +
		"('frc2387',2387,'Alternative Robotics',NULL," +
		" 'Columbus','OH','USA',NULL,2008,NULL)," +
		"('frc2388',2388,'Pirate Robotics','http://www.firstinspires.org/'," +
		" 'Sperry','Oklahoma','USA',NULL,2008,'We arrrrrrr Team 2388, home of Pirate Robotics! We do it without spilling blood!')," +
		"('frc2389',2389,'Oklahoma STORM','http://okstorm.ctinteractive.com'," +
		" 'Drumright','OK','USA',NULL,2008,NULL)," +
		"('frc2390',2390,'Wildcats',NULL," +
		" 'San Ramon','CA','USA',NULL,2008,NULL)," +
		"('frc2391',2391,'Tiger Drive',NULL," +
		" 'Broken Arrow','OK','USA',NULL,2008,NULL)," +
		"('frc2392',2392,'Pagwire',NULL," +
		" 'Columbus','OH','USA',NULL,2008,NULL)," +
		"('frc2393',2393,'Robotichauns','http://www.knoxvillecatholic.com/parents-students/robotics-team/'," +
		" 'Knoxville','Tennessee','USA',NULL,2008,'The Luck of the Irish')," +
		"('frc2394',2394,'Eagles',NULL," +
		" 'Watonga','OK','USA',NULL,2008,NULL)," +
		"('frc2395',2395,'OKC 4-H Robotics - Ninja Munkees','http://www.okcrobot.com'," +
		" 'Oklahoma City','Oklahoma','USA',NULL,2008,'It can happen, with God''s help.')," +
		"('frc2396',2396,'SeeHawks',NULL," +
		" 'Yukon','OK','USA',NULL,2008,NULL)," +
		"('frc2397',2397,'Cyborgs',NULL," +
		" 'Phoenix','AZ','USA',NULL,2008,NULL)," +
		"('frc2398',2398,'Tech Walkers','http://www.firstinspires.org/'," +
		" 'Tahlequah','Oklahoma','USA',NULL,2008,'Leading Native Americans into the future through technology')," +
		"('frc2399',2399,'The Fighting Unicorns','http://www.team2399.org'," +
		" 'Shaker Heights','Ohio','USA',NULL,2008,'Bursting Into Flames From Sheer Excellence')," +
		"('frc2400',2400,'Wyoming!?','http://www.scsd2.com/SheridanHigh.cfm?subpage=572845'," +
		" 'Sheridan','WY','USA',NULL,2008,NULL)," +
		"('frc2401',2401,'PATRIOTS',NULL," +
		" 'MAYVILLE','ND','USA',NULL,2008,NULL)," +
		"('frc2402',2402,'RoboJackets','http://www.robo-jackets.com'," +
		" 'Fredericksburg','Virginia','USA',NULL,2008,NULL)," +
		"('frc2403',2403,'Plasma Robotics','http://www.plasmarobotics.com'," +
		" 'Mesa','Arizona','USA',NULL,2008,'Uniting purpose-driven students, creating the problem-solvers of tomorrow.')," +
		"('frc2404',2404,'TNT','http://team2404.org/'," +
		" 'Altadena','California','USA',NULL,2008,'Having a Blast with STEAM')," +
		"('frc2405',2405,'Techno Trojans','https://www.fruitportrobotics.org'," +
		" 'Fruitport','Michigan','USA',NULL,2008,'Divided by Zero...anything is possible')," +
		"('frc2406',2406,'CowDogs',NULL," +
		" 'Camp Verde','AZ','USA',NULL,2008,NULL)," +
		"('frc2407',2407,'Roaring Bears','http://rcyders%40leetonia.k12.oh.us'," +
		" 'Leetonia','OH','USA',NULL,2008,NULL)," +
		"('frc2408',2408,'Shrapnel Sergeants','http://www.team2408.com'," +
		" 'Hazelwood','Missouri','USA',NULL,2008,'We came here to have fun')," +
		"('frc2409',2409,'Da Bears',NULL," +
		" 'New Bedford','MA','USA',NULL,2008,NULL)," +
		"('frc2410',2410,'Blue Valley CAPS Metal Mustang Robotics','http://www.mmr2410.com/'," +
		" 'Overland Park','Kansas','USA',NULL,2008,'Do what you love; love what you do. Work hard for others; they''ll do that for yo')," +
		"('frc2411',2411,'Rebel Alliance','http://www.team2411.org'," +
		" 'Portland','Oregon','USA',NULL,2008,NULL)," +
		"('frc2412',2412,'Robototes','http://first.robototes.com'," +
		" 'Bellevue','Washington','USA',NULL,2008,NULL)," +
		"('frc2413',2413,'Panthers',NULL," +
		" 'Phoenix','AZ','USA',NULL,2008,NULL)," +
		"('frc2414',2414,'Spartans',NULL," +
		" 'Phoenix','AZ','USA',NULL,2008,NULL)," +
		"('frc2415',2415,'WiredCats','http://www.wiredcats.org'," +
		" 'Atlanta','Georgia','USA',NULL,2008,'Engage. Support. Empower.')," +
		"('frc2417',2417,'RoboCougars',NULL," +
		" 'Crewe','VA','USA',NULL,2008,NULL)," +
		"('frc2418',2418,'tomahawks','http://s2.webstarts.com/northernlightsrobotics'," +
		" 'Minot','ND','USA',NULL,2008,NULL)," +
		"('frc2419',2419,'Team MonRobot','http://teammonrobot.com'," +
		" 'Brea','CA','USA',NULL,2008,NULL)," +
		"('frc2420',2420,'RoboTitans',NULL," +
		" 'Atlanta','GA','USA',NULL,2008,NULL)," +
		"('frc2421',2421,'RTR Team Robotics','http://www.rtr2421.org'," +
		" 'Burke','Virginia','USA',NULL,2008,NULL)," +
		"('frc2422',2422,'Bucket O'' Bolts','http://www.bucket-o-bolts.com'," +
		" 'Davenport','FL','USA',NULL,2008,NULL)," +
		"('frc2423',2423,'The KwarQs','http://www.team2423.org'," +
		" 'Watertown','Massachusetts','USA',NULL,2008,NULL)," +
		"('frc2424',2424,'Robot Mafia','http://bixbyrobotics.org'," +
		" 'Bixby','Oklahoma','USA',NULL,2008,'The team you don''t want to meet in a dark alley.')," +
		"('frc2425',2425,'Hydra ','http://hydrarobotics.com'," +
		" 'Tampa','Florida','USA',NULL,2008,'THREE heads are better than ONE!')," +
		"('frc2427',2427,'Techno Beavers',NULL," +
		" 'Westmount','QC','Canada',NULL,2008,NULL)," +
		"('frc2428',2428,'Panthers',NULL," +
		" 'Sewickley','PA','USA',NULL,2008,NULL)," +
		"('frc2429',2429,'La Canada Engineering Club','http://www.lacanadaengineeringclub.org'," +
		" 'La Canada Flintridge','California','USA',NULL,2008,'Mind Over Metal')," +
		"('frc2430',2430,'CyberStorm','http://www.gripus.org'," +
		" 'Greenwood','SC','USA',NULL,2008,NULL)," +
		"('frc2431',2431,'TechnoColts',NULL," +
		" 'Detroit','MI','USA',NULL,2008,NULL)," +
		"('frc2432',2432,'RoboSpartans',NULL," +
		" 'Chicago','IL','USA',NULL,2008,NULL)," +
		"('frc2433',2433,'Tech Tigers',NULL," +
		" 'Chicago','IL','USA',NULL,2008,NULL)," +
		"('frc2434',2434,'Blue Devils Gear Heads',NULL," +
		" 'Hopewell','VA','USA',NULL,2008,NULL)," +
		"('frc2435',2435,'Soldiers of Technology','http://www.team2435.org'," +
		" 'Lawton','OK','USA',NULL,2008,NULL)," +
		"('frc2436',2436,'Bearacudas',NULL," +
		" 'Noble','OK','USA',NULL,2008,NULL)," +
		"('frc2437',2437,'Lancer Robotics','http://www.lancerrobotics.org/'," +
		" 'Honolulu','Hawaii','USA',NULL,2008,'Never under estimate the power of girls in large groups')," +
		"('frc2438',2438,'''Iobotics','http://www.iobotics.org'," +
		" 'Honolulu','Hawaii','USA',NULL,2008,NULL)," +
		"('frc2439',2439,'Bearbotics','http://www.baldwinrobotics.com/'," +
		" 'Wailuku','Hawaii','USA',NULL,2008,'laulima  (many hands)')," +
		"('frc2440',2440,'Saints',NULL," +
		" 'Honolulu','HI','USA',NULL,2008,NULL)," +
		"('frc2441',2441,'Spartechs','http://www.maryknollrobotics.org/'," +
		" 'Honolulu','Hawaii','USA',NULL,2008,'Noblesse Oblige')," +
		"('frc2443',2443,'Blue Thunder','http://mauihs.k12.hi.us/robotics/'," +
		" 'Kahului','Hawaii','USA',NULL,2008,'Build your character. Program the mind. Document life.')," +
		"('frc2444',2444,'Kamehameha RoboWarriors',NULL," +
		" 'Honolulu','Hawaii','USA',NULL,2008,'Imua !')," +
		"('frc2445',2445,'RoboKAP','http://www.kapoleirobotics.com'," +
		" 'Kapolei','Hawaii','USA',NULL,2008,'\"The whole is greater than the sum of its parts\" -Aristotle')," +
		"('frc2446',2446,'Pleasanton Slingshots',NULL," +
		" 'Pleasanton','CA','USA',NULL,2008,NULL)," +
		"('frc2447',2447,'Vikes','http://www.ssvotech.org/Robotics..shtml'," +
		" 'Hanover','MA','USA',NULL,2008,NULL)," +
		"('frc2448',2448,'SLT',NULL," +
		" 'London','ENG','Kingdom',NULL,2008,NULL)," +
		"('frc2449',2449,'Out of Orbit Robotics','http://www.team2449.org'," +
		" 'Chandler','Arizona','USA',NULL,2008,'This is the path to success!')," +
		"('frc2450',2450,'Raiderbots','http://team2450.org'," +
		" 'St. Paul','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2451',2451,'PWNAGE','http://Pwnagerobotics.com'," +
		" 'Saint Charles','Illinois','USA',NULL,2008,'Inspiring Youth through STEM')," +
		"('frc2453',2453,'Soaring Eagles','http://www.hbaroboticsclub.com'," +
		" 'Honolulu','HI','USA',NULL,2008,NULL)," +
		"('frc2454',2454,'Rambotx','http://www2.k12.hi.us/~radfordrobotics'," +
		" 'Honolulu','HI','USA',NULL,2008,NULL)," +
		"('frc2455',2455,'HRC 2455',NULL," +
		" 'Honoka''a','HI','USA',NULL,2008,NULL)," +
		"('frc2456',2456,'Team Recycle It!','http://www.marinrobotics.com'," +
		" 'San Rafael','CA','USA',NULL,2008,NULL)," +
		"('frc2457',2457,'The Law','http://thelaw2457.org/'," +
		" 'Lawson','Missouri','USA',NULL,2008,'Problems, the pathways to perfection!!!')," +
		"('frc2458',2458,'Team Chaos','http://www.chaos2458.com'," +
		" 'Gladstone','New Jersey','USA',NULL,2008,'Roboticum mundus est')," +
		"('frc2459',2459,'Golden Hawks',NULL," +
		" 'Waianae','Hawaii','USA',NULL,2008,NULL)," +
		"('frc2460',2460,'Na Paniolo','http://www.kohalarobotics.com'," +
		" 'Kapaau','HI','USA',NULL,2008,NULL)," +
		"('frc2461',2461,'METAL-SKINS','http://metalskinschhs.weebly.com/'," +
		" 'Oklahoma City','Oklahoma','USA',NULL,2008,'If we build it, we can improve it!')," +
		"('frc2462',2462,'Digi Bots',NULL," +
		" 'CHICAGO','IL','USA',NULL,NULL,NULL)," +
		"('frc2463',2463,'The Siege','http://www.thesiege.dyndns.org'," +
		" 'Lexington','NC','USA',NULL,2008,NULL)," +
		"('frc2464',2464,'Trojans',NULL," +
		" 'Pahrump','NV','USA',NULL,2008,NULL)," +
		"('frc2465',2465,'Kauaibots','http://www.kauaibots.com'," +
		" 'Lihue','Hawaii','USA',NULL,2008,'Building geniuses island-wide.')," +
		"('frc2466',2466,'Menehune Robots','http://www.waimeahighschool.org/menehune%20robotics/index_robotics.htm'," +
		" 'Waimea','HI','USA',NULL,2008,NULL)," +
		"('frc2467',2467,'Sabertron','http://www.sabertron2467.com'," +
		" 'Ewa Beach','HI','USA',NULL,2008,NULL)," +
		"('frc2468',2468,'Team Appreciate','http://www.frc2468.org'," +
		" 'Austin','Texas','USA',NULL,2008,' 2 -4-6 - 8 -- Who Do You Appreciate?')," +
		"('frc2469',2469,'Idabots',NULL," +
		" 'Idabel','OK','USA',NULL,2008,NULL)," +
		"('frc2470',2470,'Team BJORG','http://www.team2470.org'," +
		" 'Bloomington','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2471',2471,'Team Mean Machine','http://www.team2471.org/'," +
		" 'Camas','Washington','USA',NULL,2008,'24 hours a day, 7 days a week, 1 build season')," +
		"('frc2472',2472,'Centurions','http://www.team2472.com'," +
		" 'Circle Pines','Minnesota','USA',NULL,2008,'Deus ex Machina')," +
		"('frc2473',2473,'Goldstrikers','http://www.cupertinorobotics.org'," +
		" 'Cupertino','California','USA',NULL,2008,'building people building robots')," +
		"('frc2474',2474,'Excel','http://www.firstinspires.org/'," +
		" 'Niles','Michigan','USA',NULL,2008,'For His Glory!')," +
		"('frc2475',2475,'Caution!',NULL," +
		" 'Kamuela','HI','USA',NULL,2008,NULL)," +
		"('frc2476',2476,'The Limited',NULL," +
		" 'Floral Park','NY','USA',NULL,2008,NULL)," +
		"('frc2477',2477,'Marauder Rascals','https://waipahurobotics.weebly.com/'," +
		" 'Waipahu','Hawaii','USA',NULL,2008,'If it ain''t broke, you ain''t trying.')," +
		"('frc2478',2478,'Westwood Robotics','http://www.westwoodrobotics.org'," +
		" 'Mesa','Arizona','USA',NULL,2008,NULL)," +
		"('frc2479',2479,'Phoenix','http://www.firstinspires.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2008,'Always Rising')," +
		"('frc2480',2480,'Roosevelt Robotics','http://www.firstinspires.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2481',2481,'Roboteers','http://www.team2481.com'," +
		" 'Tremont','Illinois','USA',NULL,2008,'Digging for Treasure in the Depths of the Mind.')," +
		"('frc2483',2483,'Clawbotz','http://www.clawbotz.net'," +
		" 'Fayetteville','NC','USA',NULL,2008,NULL)," +
		"('frc2484',2484,'Team Implosion','http://www.teamimplosion.com'," +
		" 'Woods Cross','Utah','USA',NULL,2008,'Its a yellow brick')," +
		"('frc2485',2485,'W.A.R. Lords','http://www.team2485.org/'," +
		" 'San Diego','California','USA',NULL,2008,'Champions of Lagacy')," +
		"('frc2486',2486,'CocoNuts','http://www.team2486.org'," +
		" 'Flagstaff','Arizona','USA',NULL,2008,'GO NUTS!')," +
		"('frc2487',2487,'Mechanical Animals','http://www.team2487.com'," +
		" 'West Sayville','New York','USA',NULL,2008,NULL)," +
		"('frc2488',2488,'Plasma Pumas',NULL," +
		" 'Rochester','MN','USA',NULL,2008,NULL)," +
		"('frc2489',2489,'The Insomniacs','http://www.team2489.org'," +
		" 'Fremont','California','USA',NULL,2008,'Do the math, save the world.')," +
		"('frc2490',2490,'eagles',NULL," +
		" 'chicago','IL','USA',NULL,2008,NULL)," +
		"('frc2491',2491,'NoMythic','http://www.2491nomythic.com'," +
		" 'Saint Paul','Minnesota','USA',NULL,2008,'The Unicorn Team')," +
		"('frc2493',2493,'Robokong','http://www.robokong2493.com'," +
		" 'Riverside','California','USA',NULL,2008,'Always find a way')," +
		"('frc2495',2495,'Hive Mind','http://team2495.com'," +
		" 'Hamilton','New Jersey','USA',NULL,2008,NULL)," +
		"('frc2496',2496,'Tru Blu Patriots','http://www.team2496.com'," +
		" 'Irvine','California','USA',NULL,2008,'Six Weeks That Will Change Your Life')," +
		"('frc2497',2497,'RoboNatick','http://robonatick.org'," +
		" 'Natick','MA','USA',NULL,2008,NULL)," +
		"('frc2498',2498,'BearBotics','http://www.bearbotics.org'," +
		" 'Minneapolis','Minnesota','USA',NULL,2008,'Building Leaders With Robotics')," +
		"('frc2499',2499,'Industrial Revolution','http://hibbingfrc2499.com'," +
		" 'Hibbing','Minnesota','USA',NULL,2008,'Don''t touch that!');";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_5 = SQL_INSERT_TEAMS +
		"('frc2500',2500,'Herobotics','http://www.herobotics.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2008,'Together, we WILL build a new heroic future')," +
		"('frc2501',2501,'Bionic Polars','http://www.team2501.com'," +
		" 'North St. Paul','Minnesota','USA',NULL,2008,'Rawr')," +
		"('frc2502',2502,'Talon Robotics','http://team2502.com/'," +
		" 'Eden Prairie','Minnesota','USA',NULL,2008,'Make work!')," +
		"('frc2503',2503,'Warrior Robotics','http://team2503.com'," +
		" 'Brainerd','Minnesota','USA',NULL,2008,'Resistance is Futile')," +
		"('frc2504',2504,'The Governors','http://www.farringtonrobotics.com/'," +
		" 'Honolulu','Hawaii','USA',NULL,2008,'Robotics is where geeks become heroes')," +
		"('frc2505',2505,'The Electric Sheep',NULL," +
		" 'Toronto','ON','Canada',NULL,2008,NULL)," +
		"('frc2506',2506,'Saber Robotics','http://saberrobotics.org'," +
		" 'Franklin','Wisconsin','USA',NULL,2008,'Syncing our teeth into technology')," +
		"('frc2508',2508,'Armada','http://www.frc2508.com/'," +
		" 'Stillwater','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2509',2509,'Tigerbots','http://None'," +
		" 'Hutchinson ','Minnesota','USA',NULL,2008,'One team one Tigger, all for one, all for the Tiger')," +
		"('frc2510',2510,'anishinaabe','http://www.freewebs.com/yayrobots'," +
		" 'Cloquet','MN','USA',NULL,2008,NULL)," +
		"('frc2511',2511,'Cougars','http://team2511.com'," +
		" 'Lakeville','Minnesota','USA',NULL,2008,'Free to Fail!')," +
		"('frc2512',2512,'Duluth East Daredevils','http://www.daredevils2512.org'," +
		" 'Duluth','Minnesota','USA',NULL,2008,'I can do that')," +
		"('frc2513',2513,'T.E.R.T.O.L.A.','http://www.tertola.com'," +
		" 'Minneapolis','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2514',2514,'3M MHS',NULL," +
		" 'Mahtomedi','MN','USA',NULL,2008,NULL)," +
		"('frc2515',2515,'Mr. E','http://www.2515tigerbots.com'," +
		" 'Marshall','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2517',2517,'Green Wrenches','http://evergreenrobotics.org'," +
		" 'Vancouver','WA','USA',NULL,2008,NULL)," +
		"('frc2518',2518,'Spartan Robotics','http://www.first2518.weebly.com'," +
		" 'Inver Grove Heights','Minnesota','USA',NULL,2008,'Robotics for Everyone')," +
		"('frc2520',2520,'Robotics 9000','http://www.robotics9000net'," +
		" 'Las Vegas','NV','USA',NULL,2008,NULL)," +
		"('frc2521',2521,'SERT','http://www.sert2521.org'," +
		" 'Eugene','Oregon','USA',NULL,2008,'Do the Thing')," +
		"('frc2522',2522,'Royal Robotics','http://www.royalrobotics.org'," +
		" 'Bothell','Washington','USA',NULL,2008,'In the past people were born royal. Nowadays royalty comes from what you do.')," +
		"('frc2523',2523,'Tech Storm','http://www.firstinspires.org/'," +
		" 'Saint Johnsbury','Vermont','USA',NULL,2008,NULL)," +
		"('frc2524',2524,'Roosevelt Rough Riders','http://rhsyamets.webs.com'," +
		" 'Yonkers','NY','USA',NULL,2008,NULL)," +
		"('frc2525',2525,'The pHalcons','http://www.firstinspires.org/'," +
		" 'Plymouth','Minnesota','USA',NULL,2008,'Carpe Diem')," +
		"('frc2526',2526,'Crimson Robotics','http://www.crimsonrobotics.com/'," +
		" 'Osseo','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2528',2528,'RoboDoves','http://www.firstinspires.org/'," +
		" 'Baltimore','Maryland','USA',NULL,2008,'Drive like a girl!')," +
		"('frc2529',2529,'The WHO','https://www.who2529weebly.com'," +
		" 'Saint Paul','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2530',2530,'Inconceivable','http://frcteam2530.org/'," +
		" 'Rochester','Minnesota','USA',NULL,2008,'Building Kids Who Build Robots')," +
		"('frc2531',2531,'RoboHawks','https://robohawks2531.weebly.com/'," +
		" 'Chaska','Minnesota','USA',NULL,2008,'Soar Hawks')," +
		"('frc2532',2532,'FLASH Power','http://fprobotics.webs.com/'," +
		" 'Forest Lake','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2533',2533,'Juarez Aztec Eagles','http://www.juarez.cps.k12.il.us'," +
		" 'Chicago','IL','USA',NULL,2008,NULL)," +
		"('frc2534',2534,'Lumberjack Robotics','https://www.facebook.com/boyslatinrobotics2534/'," +
		" 'Baltimore ','Maryland','USA',NULL,2008,'One Heartbeat')," +
		"('frc2535',2535,'TIGEARS','http://Tigears.org'," +
		" 'Minneapolis','MN','USA',NULL,2008,NULL)," +
		"('frc2536',2536,'Carver Bears',NULL," +
		" 'Baltimore','MD','USA',NULL,2008,NULL)," +
		"('frc2537',2537,'Space RAIDers','http://www.team2537.com'," +
		" 'Columbia','Maryland','USA',NULL,2008,'Raiding Space for Innovative and Technological Ideas to Develop the Future')," +
		"('frc2538',2538,'The Plaid Pillagers','http://www.plaidpillagers.com/'," +
		" 'Morris','Minnesota','USA',NULL,2008,'in Pursuit of Leadership And Innovative Design')," +
		"('frc2539',2539,'Krypton Cougars','http://www.Team2539.com'," +
		" 'Palmyra','Pennsylvania','USA',NULL,2008,NULL)," +
		"('frc2540',2540,'SPARC',NULL," +
		" 'Pontotoc','MS','USA',NULL,2008,NULL)," +
		"('frc2542',2542,'Go4bots','http://go4bots.gopherweb.org'," +
		" 'Gresham','OR','USA',NULL,2008,NULL)," +
		"('frc2543',2543,'TitanBot','http://www.titanbot.org/'," +
		" 'Chula Vista','California','USA',NULL,2008,'TitanBot Connectivity: The ability to connect people, technology . . . the world')," +
		"('frc2544',2544,'HCRC','http://www.hcrobotics.org'," +
		" 'Harborcreek','Pennsylvania','USA',NULL,2008,'What you need, is a bigger hammer')," +
		"('frc2545',2545,'Quantum Mechanics','http://web.me.com/beth.fawley'," +
		" 'Minneapolis','Minnesota','USA',NULL,2008,'''nuff said')," +
		"('frc2546',2546,'Electric Sheep',NULL," +
		" 'Baltimore','MD','USA',NULL,2008,NULL)," +
		"('frc2547',2547,'Redcat Robos',NULL," +
		" 'Milwaukee','WI','USA',NULL,2008,NULL)," +
		"('frc2549',2549,'Millerbots','http://www.washburnmillerbots.com/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2008,'Humans are so Last Year')," +
		"('frc2550',2550,'Skynet','http://team2550.blogspot.com/'," +
		" 'Oregon City','Oregon','USA',NULL,2008,'\"Design It. Build It. Live it!')," +
		"('frc2551',2551,'Penguin Empire','http://penguinempirerobotics.weebly.com'," +
		" 'Novato','California','USA',NULL,2008,'Luctor et Emergo')," +
		"('frc2553',2553,'Pirates',NULL," +
		" 'Pearl','MS','USA',NULL,2008,NULL)," +
		"('frc2554',2554,'The WarHawks','http://www.jpsrobotics2554.org'," +
		" 'Edison','New Jersey','USA',NULL,2008,'Don''t Worry About it - We Got This')," +
		"('frc2555',2555,'RoboRams','http://www.firstinspires.org/'," +
		" 'Tacoma','Washington','USA',NULL,2008,NULL)," +
		"('frc2556',2556,'RadioActive Roaches','http://www.team2556.com'," +
		" 'Niceville','Florida','USA',NULL,2008,'Better is the enemy of good enough')," +
		"('frc2557',2557,'SOTAbots ','http://www.sotabots.com'," +
		" 'Tacoma','Washington','USA',NULL,2008,'Can I get an Oh Yupp?')," +
		"('frc2558',2558,'SciBot',NULL," +
		" 'Harrisburg','PA','USA',NULL,2008,NULL)," +
		"('frc2559',2559,'Normality Zero','http://www.normalityzero.com'," +
		" 'Harrisburg','Pennsylvania','USA',NULL,2008,'Nothing ventured, Nothing gained')," +
		"('frc2560',2560,'RoboDog','http://www.grandviewrobodog.org'," +
		" 'Grandview','Missouri','USA',NULL,2008,'Go, Dog, Go!')," +
		"('frc2561',2561,'St Paul East Side RobEASTs',NULL," +
		" 'Saint Paul','MN','USA',NULL,2008,NULL)," +
		"('frc2562',2562,'Robinson',NULL," +
		" 'San Juan','PR','USA',NULL,2008,NULL)," +
		"('frc2563',2563,'Toros',NULL," +
		" 'Caguas','PR','USA',NULL,2008,NULL)," +
		"('frc2564',2564,'Red Hawks',NULL," +
		" 'San Juan','PR','USA',NULL,2008,NULL)," +
		"('frc2565',2565,'Technovations',NULL," +
		" 'Cupey','PR','USA',NULL,2008,NULL)," +
		"('frc2566',2566,'Universitiers',NULL," +
		" 'San Juan','PR','USA',NULL,2008,NULL)," +
		"('frc2567',2567,'Miguel',NULL," +
		" 'San Juan','PR','USA',NULL,2008,NULL)," +
		"('frc2568',2568,'STORM',NULL," +
		" 'Carolina','PR','USA',NULL,2008,NULL)," +
		"('frc2569',2569,'Artist Engineers',NULL," +
		" 'San Juan','PR','USA',NULL,2008,NULL)," +
		"('frc2570',2570,'SHS',NULL," +
		" 'Sahuarita','AZ','USA',NULL,2008,NULL)," +
		"('frc2571',2571,'Chief robot',NULL," +
		" 'Haileyville','OK','USA',NULL,2008,NULL)," +
		"('frc2572',2572,'governors',NULL," +
		" 'Saint Paul','MN','USA',NULL,2008,NULL)," +
		"('frc2573',2573,'Mustang',NULL," +
		" 'Brooklyn','NY','USA',NULL,2008,NULL)," +
		"('frc2574',2574,'RoboHuskie','http://www.robohuskie.com'," +
		" 'St. Anthony','Minnesota','USA',NULL,2008,'BOT <3   (BOT Love)')," +
		"('frc2575',2575,'White Hurricane','http://www.whitehurricane.com.br'," +
		" 'Santa Branca','SP','Brazil',NULL,2008,NULL)," +
		"('frc2576',2576,'Chilean Heart','http://www.corazondechileno.cl/'," +
		" 'Santiago','Región Metropolitana de Santiago','Chile',NULL,2008,'When your Heart is FIRST, amazing things happen')," +
		"('frc2577',2577,'Pingry Robotics','http://www.firstinspires.org/'," +
		" 'Basking Ridge','New Jersey','USA',NULL,2008,NULL)," +
		"('frc2579',2579,'LIC Robodogs','https://team2579.com'," +
		" 'Astoria','New York','USA',NULL,2008,'We''ve got some tough nuts')," +
		"('frc2580',2580,'Mecha-Knights',NULL," +
		" 'New Orleans','LA','USA',NULL,2008,NULL)," +
		"('frc2581',2581,'Bulldog Robotic Team','http://2581.lafrc.org'," +
		" 'Monroe','LA','USA',NULL,2008,NULL)," +
		"('frc2582',2582,'PantherBots','http://www.lufkinpantherbots.com'," +
		" 'Lufkin','Texas','USA',NULL,2008,'You have three choices in life. Give up, give in, or give it all you''ve got.')," +
		"('frc2583',2583,'RoboWarriors','http://www.team2583.org'," +
		" 'Austin','Texas','USA',NULL,2008,'Capture the Flag')," +
		"('frc2584',2584,'Flame of The West','https://www.team1437.com/'," +
		" 'Calabasas','California','USA',NULL,2008,NULL)," +
		"('frc2585',2585,'Impact','https://impact2585.wixsite.com/impact2585'," +
		" 'Bellaire','Texas','USA',NULL,2008,'\"Student built, Student driven\"')," +
		"('frc2586',2586,'Copper Bots','http://www.firstinspires.org/'," +
		" 'Calumet','Michigan','USA',NULL,2008,NULL)," +
		"('frc2587',2587,'DiscoBots','http://discobots.org'," +
		" 'Houston','Texas','USA',NULL,2008,'Groovin to the beat of technology !')," +
		"('frc2588',2588,'JAVA',NULL," +
		" 'Pomona','CA','USA',NULL,2008,NULL)," +
		"('frc2589',2589,'The Codebandits','http://www.trsdrobotics.com'," +
		" 'Byfield','MA','USA',NULL,2008,NULL)," +
		"('frc2590',2590,'Nemesis','http://frc2590.org/'," +
		" 'Robbinsville','New Jersey','USA',NULL,2008,'We run our team like a high tech business.')," +
		"('frc2591',2591,'RedTails','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2008,NULL)," +
		"('frc2592',2592,'Hawks','http://None'," +
		" 'Rio Rico','AZ','USA',NULL,2008,NULL)," +
		"('frc2593',2593,'Brobot','http://www.vitalinequality.com'," +
		" 'Peabody','MA','USA',NULL,2008,NULL)," +
		"('frc2594',2594,'Naskcorpions','http://www.naskcorpions.org/'," +
		" 'Nampa','Idaho','USA',NULL,2008,'Work now...Die Later')," +
		"('frc2595',2595,'Illuminati','http://illuminatirobotics.com'," +
		" 'Los Angeles','CA','USA',NULL,2008,NULL)," +
		"('frc2596',2596,'Ferrous Bulldog Inovations',NULL," +
		" 'Jersey City','NJ','USA',NULL,2008,NULL)," +
		"('frc2597',2597,'Raider bots',NULL," +
		" 'Houston','TX','USA',NULL,2008,NULL)," +
		"('frc2598',2598,'Neofighters','http://Robotics.teach4ever.net'," +
		" 'Hercules','CA','USA',NULL,2008,NULL)," +
		"('frc2599',2599,'FULL THROTTLE','http://teamfullthrottle.org'," +
		" 'CHULA VISTA','CA','USA',NULL,2008,NULL)," +
		"('frc2600',2600,'Team Falcon','http:////jthswebmail/jtpswebsite$/HighSchool/FIRSTRobotics'," +
		" 'Oak Ridge','New Jersey','USA',NULL,2008,'team falcon')," +
		"('frc2601',2601,'Steel Hawks','http://www.steelhawks.net'," +
		" 'Flushing','New York','USA',NULL,2008,'Respice, Aspice, Prospice ')," +
		"('frc2602',2602,'Falcons','http://www.foothillnv.org'," +
		" 'Henderson','NV','USA',NULL,2008,NULL)," +
		"('frc2603',2603,'Steel Stingers','http://www.steelstingers.com/'," +
		" 'Medina','Ohio','USA',NULL,2008,'Mind... Metal... Machine...')," +
		"('frc2604',2604,'Metal and Soul','http://www.metalandsoul.org'," +
		" 'Capac','Michigan','USA',NULL,2008,'\"Redefining SUCCESS since 2008\"')," +
		"('frc2605',2605,'Seamonsters ','http://www.seamonsters2605.org'," +
		" 'Bellingham','Washington','USA',NULL,2008,'Let''s get Kraken!')," +
		"('frc2606',2606,'Irish Robotics','https://rosemountrobotics.org/'," +
		" 'Rosemount','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2607',2607,'The Fighting RoboVikings','http://www.robovikings.com'," +
		" 'Warminster ','Pennsylvania','USA',NULL,2008,'Purveyors of Gracious Professionalism')," +
		"('frc2608',2608,'MiGHT','http://www.mightrobotics.com'," +
		" 'Farmington Hills','MI','USA',NULL,2008,NULL)," +
		"('frc2609',2609,'BeaverworX','http://Beaverworx.ca'," +
		" 'Guelph','Ontario','Canada',NULL,2008,'Success for all')," +
		"('frc2611',2611,'Jacktown Vectors','https://jacktownvectors.wixsite.com/2611'," +
		" 'Jackson','Michigan','USA',NULL,2008,'Semper Prodest')," +
		"('frc2612',2612,'The Corsairs',''," +
		" 'Waterford','Michigan','USA',NULL,2008,NULL)," +
		"('frc2613',2613,'Protobots','http://www.firstinspires.org/'," +
		" 'Van Horn','Texas','USA',NULL,2008,'That which we do not create--- we will obliterate')," +
		"('frc2614',2614,'Mountaineer Area RoboticS (MARS)','http://marsfirst.org'," +
		" 'Morgantown','West Virginia','USA',NULL,2008,'We came to be inspired, we stay because we are, we will become the inspiration')," +
		"('frc2617',2617,'Robo Reds','http://team2617.com'," +
		" 'Lansing','MI','USA',NULL,2008,NULL)," +
		"('frc2618',2618,'The Mad Hatters',NULL," +
		" 'Pittsburgh','PA','USA',NULL,2008,NULL)," +
		"('frc2619',2619,'The Charge','http://www.the-charge.com'," +
		" 'Midland','Michigan','USA',NULL,2008,'Unleash Your Potential')," +
		"('frc2620',2620,'Titans','http://www.firstinspires.org/'," +
		" 'Southgate','Michigan','USA',NULL,2008,NULL)," +
		"('frc2621',2621,'Bucs','http://www.firstbedford.org/'," +
		" 'Bedford','MA','USA',NULL,2008,NULL)," +
		"('frc2622',2622,'Team 1',NULL," +
		" 'San Diego','CA','USA',NULL,2008,NULL)," +
		"('frc2623',2623,'lovenpeace',NULL," +
		" 'Antioch','CA','USA',NULL,2008,NULL)," +
		"('frc2624',2624,'BotCatz',NULL," +
		" 'Toronto','ON','Canada',NULL,2008,NULL)," +
		"('frc2625',2625,'ARC Robotics','http://www.team2625.com'," +
		" 'Mississauga','ON','Canada',NULL,2008,NULL)," +
		"('frc2626',2626,'Evolution','http://evolution2626.org'," +
		" 'Sherbrooke','Québec','Canada',NULL,2008,'Evolution Pure & Simple')," +
		"('frc2627',2627,'Techies',NULL," +
		" 'Ann Arbor','MI','USA',NULL,2008,NULL)," +
		"('frc2628',2628,'Pirates',NULL," +
		" 'San Francisco','CA','USA',NULL,2008,NULL)," +
		"('frc2629',2629,'Halcones','http://www.halconesfrc.com'," +
		" 'QUERETARO','OAX','Mexico',NULL,2008,NULL)," +
		"('frc2630',2630,'Thunderbolts','http://www.thunderbolts2630.com'," +
		" 'Emek hefer','HaMerkaz','Israel',NULL,2008,'ThunderBolts - Our bolts are as fast as lightning!')," +
		"('frc2632',2632,'SteeleBots','http://sites.google.com/site/frcteam2632'," +
		" 'Amherst','Ohio','USA',NULL,2008,'We are not just building a Robot, We are building a TEAM')," +
		"('frc2633',2633,'Pittsburg Pirates',NULL," +
		" 'Pittsburg','CA','USA',NULL,2008,NULL)," +
		"('frc2634',2634,'The Gryphons','http://www.team2634.com'," +
		" 'North York','Ontario','Canada',NULL,2008,'\"Fortes in Fide\"')," +
		"('frc2635',2635,'Lake Monsters','http://www.frc2635.org'," +
		" 'Lake Oswego','Oregon','USA',NULL,2008,'Building Awesome')," +
		"('frc2637',2637,'Phantom Catz','https://www.frcteam2637.org/'," +
		" 'Rolling Hills Estates','California','USA',NULL,2008,NULL)," +
		"('frc2638',2638,'Rebel Robotics','http://www.gnsrobotics.com/'," +
		" 'Great Neck','New York','USA',NULL,2008,'\"Changing the culture one nut and bolt at a time\"')," +
		"('frc2640',2640,'HOTBOTZ','http://www.hotbotz.org'," +
		" 'Reidsville','North Carolina','USA',NULL,2008,'My Bot Hot!')," +
		"('frc2641',2641,'PCCR','http://www.team2641.com'," +
		" 'Pittsburgh','Pennsylvania','USA',NULL,2008,'TRADITION NEVER GRADUATES')," +
		"('frc2642',2642,'Pitt Pirates','http://www.pittpiratesrobotics.com/'," +
		" 'Greenville','North Carolina','USA',NULL,2008,'In Scientia Vires')," +
		"('frc2643',2643,'Dark Matter','http://team2643.com'," +
		" 'San Jose','California','USA',NULL,2008,'To See, To Do, To Improve, To Teach')," +
		"('frc2644',2644,'Trojans',NULL," +
		" 'Pittsburgh','PA','USA',NULL,2008,NULL)," +
		"('frc2645',2645,'PowerSurge','http://www.rprobotics.com'," +
		" 'Muskegon','MI','USA',NULL,2008,NULL)," +
		"('frc2647',2647,'Cyborg Mustangs','http://www.cyborgmustangs.com'," +
		" 'Phoenix','Arizona','USA',NULL,2008,NULL)," +
		"('frc2648',2648,'Infinite Loop','http://www.team2648.com'," +
		" 'Oakland','Maine','USA',NULL,2008,'Innoventers of the Future!')," +
		"('frc2649',2649,'Meyer Robotics',NULL," +
		" 'Porto Alegre','RS','Brazil',NULL,2008,NULL)," +
		"('frc2650',2650,'ELAHLYA',NULL," +
		" 'Om- Elfahem','HA','Israel',NULL,2008,NULL)," +
		"('frc2652',2652,'Highlander Engineering and Technology',NULL," +
		" 'El Paso','TX','USA',NULL,2008,NULL)," +
		"('frc2653',2653,'Master Robotics',NULL," +
		" 'Aracaju','SE','Brazil',NULL,2008,NULL)," +
		"('frc2654',2654,'Rams','http://www.firstinspires.org/'," +
		" 'Roseau','Minnesota','USA',NULL,2008,NULL)," +
		"('frc2655',2655,'The Flying Platypi','http://www.team2655.org/'," +
		" 'Greensboro','North Carolina','USA',NULL,2008,'Ask Me Why I Playtpi?')," +
		"('frc2656',2656,'Quasics','http://www.quasics.org'," +
		" 'Monroeville','Pennsylvania','USA',NULL,2008,'Building better humans')," +
		"('frc2657',2657,'Team Thundercats','https://sites.google.com/site/thundercats2657/'," +
		" 'Deming','New Mexico','USA',NULL,2008,'Fueled by HotPockets!')," +
		"('frc2658',2658,'Σ-Motion','http://www.team2658.org'," +
		" 'San Diego','California','USA',NULL,2008,'We Put People FIRST!')," +
		"('frc2659',2659,'RoboWarriors','http://team2659.com/'," +
		" 'Mission Hills','California','USA',NULL,2008,NULL)," +
		"('frc2660',2660,'Pengbots','http://www.pengbots2660.org/'," +
		" 'Tulalip','Washington','USA',NULL,2008,'Who are we? 26-60')," +
		"('frc2661',2661,'Trailblazers',NULL," +
		" 'Chatsworth','CA','USA',NULL,2008,NULL)," +
		"('frc2662',2662,'Robo Krew','http://robokrew.weebly.com/'," +
		" 'Tolleson','Arizona','USA',NULL,2008,'Our robot is only the beginning!')," +
		"('frc2663',2663,'The Foothillers',NULL," +
		" 'El Cajon','CA','USA',NULL,2008,NULL)," +
		"('frc2664',2664,'Lobos',NULL," +
		" 'Houston','TX','USA',NULL,2008,NULL)," +
		"('frc2665',2665,'CougarBots',NULL," +
		" 'Dayton','Ohio','USA',NULL,2008,NULL)," +
		"('frc2667',2667,'Knights of the Valley','http://www.team2667.org'," +
		" 'Apple Valley','Minnesota','USA',NULL,2008,'keep it simple stupid')," +
		"('frc2668',2668,'North Montco Vortex','http://www.nmtcc.org'," +
		" 'Lansdale','PA','USA',NULL,2008,NULL)," +
		"('frc2669',2669,'KY Bots','http://www.frc2669.com'," +
		" 'Kiryat Yam','Z','Israel',NULL,2008,NULL)," +
		"('frc2670',2670,'Absent Algorithms',NULL," +
		" 'Toronto','ON','Canada',NULL,2008,NULL)," +
		"('frc2672',2672,'Osfia',NULL," +
		" 'Osfia','Z','Israel',NULL,2008,NULL)," +
		"('frc2673',2673,'Tenacious Technicians','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2008,'We roll ''till the wheels fall off.')," +
		"('frc2674',2674,'Nilton Lins',NULL," +
		" 'Manaus','AM','Brazil',NULL,2008,NULL)," +
		"('frc2675',2675,'ABCTech','http://www.team2675.com.br'," +
		" 'Canoas','RS','Brazil',NULL,2008,NULL)," +
		"('frc2676',2676,'Ecorse Robo Raiders','http://www.RoboRaiders2676.com'," +
		" 'Ecorse','Michigan','USA',NULL,2008,'We design.  We build.  We win!')," +
		"('frc2678',2678,'Brauder',NULL," +
		" 'Jerusalem','JM','Israel',NULL,2008,NULL)," +
		"('frc2679',2679,'Tiger Team','https://www.facebook.com/TigerTeam2679/'," +
		" 'Jerusalem','Yerushalayim','Israel',NULL,2008,NULL)," +
		"('frc2680',2680,'Vex Minas',NULL," +
		" 'Minas do Leão','RS','Brazil',NULL,2008,NULL)," +
		"('frc2681',2681,'Lady G-House Pirates','https://www.ladypirates2681.com'," +
		" 'Brooklyn','NY','USA',NULL,2008,NULL)," +
		"('frc2682',2682,'Boneyard Robotics','http://boneyardrobotics.com'," +
		" 'Winterville','North Carolina','USA',NULL,2008,'Controlled Chaos')," +
		"('frc2685',2685,'Team Haven',NULL," +
		" 'Byfield','MA','USA',NULL,NULL,NULL)," +
		"('frc2686',2686,'The Memphis MechWarriors',NULL," +
		" 'Memphis','Tennessee','USA',NULL,2008,NULL)," +
		"('frc2702',2702,'Rebels','http://www.2702rebels.com/'," +
		" 'Kitchener','Ontario','Canada',NULL,2009,'The FIRST Step')," +
		"('frc2703',2703,'fire fox',NULL," +
		" 'ben shemen y.v.','M','Israel',NULL,NULL,NULL)," +
		"('frc2704',2704,'Roaring Robotics','http://www.goteam2704.org'," +
		" 'Batavia','Illinois','USA',NULL,2009,NULL)," +
		"('frc2705',2705,'WE ROBOT','http://www.ism-werobot.com/'," +
		" 'Eden Prairie','Minnesota','USA',NULL,2009,'Humans and Robots, Hand in Hand')," +
		"('frc2706',2706,'Merge Robotics','http://team2706.ca'," +
		" 'Ottawa','Ontario','Canada',NULL,2009,'Resolution Imminent!')," +
		"('frc2707',2707,' PCD','http://www.firstinspires.org/'," +
		" 'Saint Louis','Missouri','USA',NULL,2009,'Building Gracious Professionalism and Leadership through understanding.')," +
		"('frc2708',2708,'Lake Effect Robotics','http://www.lakeeffectrobotics.ca/'," +
		" 'Kingston','Ontario','Canada',NULL,2009,NULL)," +
		"('frc2709',2709,'Iron Wolves','http://www.firstinspires.org/'," +
		" 'Chicago','Illinois','USA',NULL,2009,NULL)," +
		"('frc2710',2710,'JetStream','http://muskwaclub.wixsite.com/muskwa/robotics-team'," +
		" 'Rancho Palos Verdes','California','USA',NULL,2009,NULL)," +
		"('frc2711',2711,'Centaurus Robotics',NULL," +
		" 'Lafayette','CO','USA',NULL,2009,NULL)," +
		"('frc2712',2712,'Power Surge 4-H Robotics','http://powersurge4-hrobotics.org'," +
		" 'Woodbury','Connecticut','USA',NULL,2009,NULL)," +
		"('frc2713',2713,'iRaiders','http://www.iraiders.org'," +
		" 'Melrose','Massachusetts','USA',NULL,2009,NULL)," +
		"('frc2714',2714,'♨️ BBQ ♨️','http://www.team2714.com'," +
		" 'Dallas','Texas','USA',NULL,2009,NULL)," +
		"('frc2719',2719,'Ketronix Robotix',NULL," +
		" 'Monroe','MI','USA',NULL,2009,NULL)," +
		"('frc2721',2721,'Fang','http://www.firstinspires.org/'," +
		" 'Lasara','Texas','USA',NULL,2009,NULL)," +
		"('frc2723',2723,'Team Rocket','http://www.teamrocketrobotics.com'," +
		" 'Oklahoma City','Oklahoma','USA',NULL,2009,'Tradition: plywood, zip ties and duck tape!')," +
		"('frc2725',2725,'Ice Princesses','http://www.firstinspires.org/'," +
		" 'Chicago','Illinois','USA',NULL,2009,'Never Give Up')," +
		"('frc2729',2729,'Storm Robotics Team','http://stormroboticsnj.org'," +
		" 'Marlton','New Jersey','USA',NULL,2009,NULL)," +
		"('frc2733',2733,'Pigmice','http://www.pigmice.com'," +
		" 'Portland','Oregon','USA',NULL,2009,'With great ROBOTS comes great Responsibility.')," +
		"('frc2735',2735,'RoBobcats','http://www.Fruitvaleisd.com'," +
		" 'Fruitvale','TX','USA',NULL,2009,NULL)," +
		"('frc2737',2737,'Wizards','http://www.facebook.com/pages/Houston-TX/YES-Prep-FIRST-Robotics-Team-2737/219102392109'," +
		" 'Houston','TX','USA',NULL,2009,NULL)," +
		"('frc2739',2739,'Bucket of Bolts',NULL," +
		" 'Gilberts','IL','USA',NULL,2009,NULL)," +
		"('frc2741',2741,'Method 2 Madness','http://method2madness.bataviarobotics.com'," +
		" 'Batavia','IL','USA',NULL,2009,NULL)," +
		"('frc2743',2743,'Berkner Rambots',NULL," +
		" 'Richardson','TX','USA',NULL,2009,NULL)," +
		"('frc2745',2745,'Incognito','http://www.neisd.net/stem/robotics.html'," +
		" 'San Antonio','TX','USA',NULL,2009,NULL)," +
		"('frc2747',2747,'Turner Lions',NULL," +
		" 'Carrollton','TX','USA',NULL,2009,NULL)," +
		"('frc2749',2749,'The 7th Direction','http://www.connersvillerobotics.org'," +
		" 'Connersville','IN','USA',NULL,2009,NULL)," +
		"('frc2751',2751,'SPARK Robotics','http:// www.Team2751.org'," +
		" 'Pendleton','SC','USA',NULL,2009,NULL)," +
		"('frc2753',2753,'Team Overdrive','http://team-overdrive.com'," +
		" 'Bridgewater','NJ','USA',NULL,2009,NULL)," +
		"('frc2757',2757,'B.A.D. (Built And Dangerous!)','http://www.lymanrobotics.com'," +
		" 'Longwood','FL','USA',NULL,2009,NULL)," +
		"('frc2759',2759,'Roboneers',NULL," +
		" 'Stillwater','OK','USA',NULL,2009,NULL)," +
		"('frc2761',2761,'IronHorse Robotics','http://www.team2761.com'," +
		" 'Fresno','California','USA',NULL,2009,'We Are Building The Future')," +
		"('frc2763',2763,'Tigers',NULL," +
		" 'Sterling','OK','USA',NULL,2009,NULL)," +
		"('frc2765',2765,'Radioactive Robotics','http://team2765.org'," +
		" 'Norman','Oklahoma','USA',NULL,2009,'To Make the Best Better')," +
		"('frc2767',2767,'Stryke Force','http://www.strykeforce.org'," +
		" 'Kalamazoo','Michigan','USA',NULL,2009,'For Flying Out Loud!')," +
		"('frc2769',2769,'ROBOTS IN MOTION',NULL," +
		" 'Chicago','IL','USA',NULL,2009,NULL)," +
		"('frc2771',2771,'Code Red Robotics the Stray Dogs','http://www.coderedrobotics.com'," +
		" 'Grandville','Michigan','USA',NULL,2009,'Building Robots to Build Better People')," +
		"('frc2773',2773,'IronDogz','http://www.IronDogz.org'," +
		" 'Edmond','Oklahoma','USA',NULL,2009,NULL)," +
		"('frc2775',2775,'Jackson Area Robotics','http://www.jacksonarearobotics.com'," +
		" 'Jackson','TN','USA',NULL,2009,NULL)," +
		"('frc2777',2777,'Blackwell Resistance',NULL," +
		" 'Blackwell','OK','USA',NULL,2009,NULL)," +
		"('frc2779',2779,'CH1RP','http://www.CH1RP.org'," +
		" 'Claremore','OK','USA',NULL,2009,NULL)," +
		"('frc2781',2781,'RoboPRIDE','http://www.roboPride.org'," +
		" 'Burbank','Illinois','USA',NULL,2009,'The roar of a lion, the hum of a machine')," +
		"('frc2783',2783,'Engineers of Tomorrow ','http://www.kyeot.com'," +
		" 'La Grange','Kentucky','USA',NULL,2009,'\"We will either find a way or make one!\"')," +
		"('frc2785',2785,'Prometheus','http://www.firstinspires.org/'," +
		" 'Kent','Connecticut','USA',NULL,2009,'Bringing technology to Kent')," +
		"('frc2787',2787,'Team Chaos','https://sites.google.com/site/moodyroboticsteam'," +
		" 'Corpus Christi','TX','USA',NULL,2009,NULL)," +
		"('frc2789',2789,'TEXPLOSION','http://www.mnthtitanrobotics.org'," +
		" 'Manor','Texas','USA',NULL,2009,'\"A small force with big leverage!\"')," +
		"('frc2791',2791,'Shaker Robotics','http://www.team2791.org'," +
		" 'Latham','New York','USA',NULL,2009,'K.I.S.S.')," +
		"('frc2793',2793,'Sabercats',NULL," +
		" 'Moore','OK','USA',NULL,2009,NULL)," +
		"('frc2795',2795,'Ohm''s Claw','http://www.firstinspires.org/'," +
		" 'Broken Arrow','Oklahoma','USA',NULL,2009,'Get our Technical Training and Go Change the World')," +
		"('frc2797',2797,'Knight & Nerdy','http://www.team2797.com'," +
		" 'Clermont','Florida','USA',NULL,2009,'Where''s the duct tape?')," +
		"('frc2799',2799,'The Teslas',NULL," +
		" 'Kansas City','MO','USA',NULL,2009,NULL)," +
		"('frc2803',2803,'Firebirds','http://phoenixmlitary.org'," +
		" 'Chicago','IL','USA',NULL,2009,NULL)," +
		"('frc2805',2805,'Ghost in the Machine','http://www.waxahachierobotics.org'," +
		" 'Waxahachie','Texas','USA',NULL,2009,'This year, we''re thinking bigger.')," +
		"('frc2809',2809,'K-Botics','http://kbotics.ca'," +
		" 'Kingston','Ontario','Canada',NULL,2009,'Creating a culture of caring and respect and a tradition of excellence.')," +
		"('frc2811',2811,'StormBots','http://www.stormbots.com'," +
		" 'Vancouver','Washington','USA',NULL,2009,'We don''t just build robots, we build futures.')," +
		"('frc2813',2813,'Gear Heads','https://www.prospectrobotics.org'," +
		" 'Saratoga','California','USA',NULL,2009,'It''s fun and I like engineering')," +
		"('frc2815',2815,'Blue Devil Mechanics','http://bluedevilmechanics.weebly.com/'," +
		" 'Columbia','South Carolina','USA',NULL,2009,NULL)," +
		"('frc2817',2817,'Bluff City Bots','http://memphisfirstteams.org'," +
		" 'Memphis','Tennessee','USA',NULL,2009,NULL)," +
		"('frc2819',2819,'Team Dynamite','http://www.facebook.com/#!/pages/FRC-Team-2819-Team-Dynamite/300815189942935?sk=wall'," +
		" 'Oxon Hill','Maryland','USA',NULL,2009,'Team Dynamite ... exploding with awesomeness!')," +
		"('frc2821',2821,'TASER',NULL," +
		" 'Preston','OK','USA',NULL,2009,NULL)," +
		"('frc2823',2823,'The Automatons','http://hprobotics.org'," +
		" 'Saint Paul','Minnesota','USA',NULL,2009,' ')," +
		"('frc2825',2825,'Roc Robotics','http://www.team2825.com'," +
		" 'Saint Paul','Minnesota','USA',NULL,2009,NULL)," +
		"('frc2826',2826,'Wave Robotics','http://www.waverobotics.com'," +
		" 'Oshkosh','Wisconsin','USA',NULL,2009,'Making a Splash!')," +
		"('frc2827',2827,'Crown City Robotics','http://www.crowncityrobotics09.webs.com/'," +
		" 'Coronado','California','USA',NULL,2009,NULL)," +
		"('frc2829',2829,'Knights of Sir MacHinery','http://www.SirMacHineryRobotics.com'," +
		" 'Norman','OK','USA',NULL,2009,NULL)," +
		"('frc2830',2830,'Riverside RoboTigers','http://team2830.com'," +
		" 'Milwaukee','Wisconsin','USA',NULL,2009,NULL)," +
		"('frc2831',2831,'Warriors',NULL," +
		" 'Chicago','IL','USA',NULL,2009,NULL)," +
		"('frc2832',2832,'The Livonia  Warriors','http://www.livoniawarriors.org'," +
		" 'Livonia','Michigan','USA',NULL,2009,'\"Gear UP\"')," +
		"('frc2833',2833,'Roboscorpions','http://roboscorpion.net'," +
		" 'Brownsville','Texas','USA',NULL,2009,NULL)," +
		"('frc2834',2834,'Bionic Black Hawks','http://www.team2834.com'," +
		" 'Bloomfield Hills','Michigan','USA',NULL,2009,'Learn, Serve, Grow')," +
		"('frc2835',2835,'Shazbotz','http://www.liberty.k12.oh.us/powpak/cgi-bin/homepage.pl?id=lbtylhsrobotics'," +
		" 'Youngstown','OH','USA',NULL,2009,NULL)," +
		"('frc2836',2836,'Team Beta','http://www.teambeta.org'," +
		" 'Woodbury','Connecticut','USA',NULL,2009,'\"We''re working on it\"')," +
		"('frc2837',2837,'Freebirds','http://www.polytechfusion.com'," +
		" 'Gilbert','AZ','USA',NULL,2009,NULL)," +
		"('frc2838',2838,'Eaglebots','http://www.2838eaglebots.org/'," +
		" 'Chesterfield','Missouri','USA',NULL,2009,'Eaglebots')," +
		"('frc2839',2839,'Daedalus','http://www.2839daedalus.org'," +
		" 'Escondido','California','USA',NULL,2009,'Intelligence cubed')," +
		"('frc2840',2840,'Blue Tide','http://www.pcdsrobotics.org/'," +
		" 'Paradise Valley','Arizona','USA',NULL,2009,NULL)," +
		"('frc2841',2841,'NECHS',NULL," +
		" 'El Paso','TX','USA',NULL,2009,NULL)," +
		"('frc2842',2842,'Mustangs',NULL," +
		" 'Bokchito','OK','USA',NULL,2009,NULL)," +
		"('frc2843',2843,'Metal Heads',NULL," +
		" 'El Paso','TX','USA',NULL,2009,NULL)," +
		"('frc2844',2844,'Stampede Robotics','http://www.team2844.com'," +
		" 'Laveen','Arizona','USA',NULL,2009,NULL)," +
		"('frc2845',2845,'O''Botics',NULL," +
		" 'Osseo','MN','USA',NULL,2009,NULL)," +
		"('frc2846',2846,'FireBears','http://www.firebears.org/'," +
		" 'Saint Paul','Minnesota','USA',NULL,2009,NULL)," +
		"('frc2847',2847,'The MegaHertz','http://www.fairmontrobotics.com/'," +
		" 'Fairmont','Minnesota','USA',NULL,2009,'Innovators and Inventors!')," +
		"('frc2848',2848,'Rangers','http://www.frc2848.com'," +
		" 'Dallas','Texas','USA',NULL,2009,'All It Takes is a Spark')," +
		"('frc2849',2849,'Ursa Major','http://www.hammondursamajor.org/ursamajor2849/'," +
		" 'Columbia','Maryland','USA',NULL,2009,'  ')," +
		"('frc2850',2850,'Cyberwolves','http://www.cyberwolvesrobotics.net'," +
		" 'Coeur d''Alene','ID','USA',NULL,2009,NULL)," +
		"('frc2851',2851,'Crevolution','http://crevolutionrobotics.org'," +
		" 'Sterling Heights','Michigan','USA',NULL,2009,'A Creative Revolution ')," +
		"('frc2852',2852,'DM High Voltage','http://dmhighvoltage.com/'," +
		" 'St Catharines','Ontario','Canada',NULL,2009,'Sparking Interest, Igniting Imagination ')," +
		"('frc2853',2853,'Hot Spot Robotics','http://www.mililanirobotics.org'," +
		" 'Mililani','Hawaii','USA',NULL,2009,NULL)," +
		"('frc2854',2854,'The Prototypes','http://evhsrobotics.com/'," +
		" 'San Jose','California','USA',NULL,2009,'It''s Just A Prototype')," +
		"('frc2855',2855,'BEASTBOT','http://www.beastbot2855.com'," +
		" 'Saint Paul','Minnesota','USA',NULL,2009,'Belong Explore Achieve Succeed Together')," +
		"('frc2856',2856,'Planetary Drive','http://www.planetarydrive.org/'," +
		" 'Lexington','Kentucky','USA',NULL,2009,'If Better is Possible, Good is not Enough')," +
		"('frc2857',2857,'Black Ops',NULL," +
		" 'Dallas','TX','USA',NULL,2009,NULL)," +
		"('frc2859',2859,'Blasterbots',NULL," +
		" 'Golden','CO','USA',NULL,2009,NULL)," +
		"('frc2861',2861,'Infinity''s End','https://sites.google.com/a/lake-city.k12.mn.us/2861-infinity-s-end/'," +
		" 'Lake City','Minnesota','USA',NULL,2009,'\"Sometimes the questions are complicated and the answers are simple.\"')," +
		"('frc2862',2862,'UTC Westhill Vikings',NULL," +
		" 'Stamford','CT','USA',NULL,2009,NULL)," +
		"('frc2864',2864,'Sigma Phoenix',NULL," +
		" 'El Paso','TX','USA',NULL,2009,NULL)," +
		"('frc2865',2865,'ROBOTEKNIX','http://www.roboteknix.com'," +
		" 'Post Falls','ID','USA',NULL,2009,NULL)," +
		"('frc2866',2866,'Phoenix Robotics','http://omhsrobotics.webs.com'," +
		" 'Owings Mills','MD','USA',NULL,NULL,NULL)," +
		"('frc2867',2867,'ElkLogics','http://www.elklogics.com'," +
		" 'Elkhart','Indiana','USA',NULL,2009,NULL)," +
		"('frc2869',2869,'Regal Eagles','http://www.bethpage.ws/extra/robotics/'," +
		" 'Bethpage','New York','USA',NULL,2009,'Sic itur ad astra./This Way to the Stars')," +
		"('frc2871',2871,'Beantown Botz',NULL," +
		" 'Roxbury','MA','USA',NULL,2009,NULL)," +
		"('frc2872',2872,'CyberCats','https://wheatleycybercats.weebly.com/'," +
		" 'Old Westbury','New York','USA',NULL,2009,NULL)," +
		"('frc2873',2873,'NWR',NULL," +
		" 'Flowood','MS','USA',NULL,2009,NULL)," +
		"('frc2874',2874,'Iron Eagles','http://'," +
		" 'Grain Valley','Missouri','USA',NULL,2009,'Together we can!')," +
		"('frc2875',2875,'CyberHawks','http://www.firstinspires.org/'," +
		" 'Cold Spring Harbor','New York','USA',NULL,2009,NULL)," +
		"('frc2876',2876,'Devilbotz','http://www.bhsrobotix.com/'," +
		" 'Burlington','Massachusetts','USA',NULL,2009,'Slow is smooth and smooth is fast')," +
		"('frc2877',2877,'LigerBots','http://www.ligerbots.org'," +
		" 'Newton','Massachusetts','USA',NULL,2009,'North & South together as one!')," +
		"('frc2879',2879,'Oriole Robotics','http://frc2879.com'," +
		" 'Saint Louis Park','Minnesota','USA',NULL,2009,NULL)," +
		"('frc2880',2880,'Finger Puppet Mafia','http://sites.google.com/site/thefingerpuppetmafiacom'," +
		" 'Galveston','TX','USA',NULL,2009,NULL)," +
		"('frc2881',2881,'Lady Cans','http://www.frcteam2881.com'," +
		" 'Austin','Texas','USA',NULL,2009,'Ladies can, can you?')," +
		"('frc2882',2882,'Nuts n'' Boltz','http://www.firstinspires.org/'," +
		" 'Katy','Texas','USA',NULL,2009,NULL)," +
		"('frc2883',2883,'F.R.E.D (Fighting Rednecks Engineering and Design)','http://www.team2883.com/'," +
		" 'Warroad','Minnesota','USA',NULL,2009,'Get er done')," +
		"('frc2884',2884,'EngiKnights','http://www.engiknights.com'," +
		" 'Caguas','PR','USA',NULL,2009,NULL)," +
		"('frc2885',2885,'Falcons',NULL," +
		" 'San Juan','PR','USA',NULL,2009,NULL)," +
		"('frc2887',2887,'Blue Bots',NULL," +
		" 'Atlanta','GA','USA',NULL,2009,NULL)," +
		"('frc2888',2888,'The Beaverbots','http://www.beaverbots.org'," +
		" 'Chestnut Hill','MA','USA',NULL,2009,NULL)," +
		"('frc2890',2890,'The Hawk Collective','http://www.hawkcollective2890.com/'," +
		" 'Chesapeake','Virginia','USA',NULL,2009,'You will be assimilated.')," +
		"('frc2892',2892,'Hottie Botties','http://rosemontrobotics.com'," +
		" 'Sacramento','CA','USA',NULL,2009,NULL)," +
		"('frc2893',2893,'Parkway North Robohobos','http://logomotion.pnhrobotics.org'," +
		" 'Saint Louis','MO','USA',NULL,2009,NULL)," +
		"('frc2894',2894,'ACE Robotics','https://www.teambroncobots.com/ace/index.html'," +
		" 'Kansas City','MO','USA',NULL,2009,NULL)," +
		"('frc2895',2895,'Blazenbots','http://team2895.org'," +
		" 'Far Rockaway','New York','USA',NULL,2009,'Indigenous Engineering')," +
		"('frc2896',2896,'MechaMonarchs','http://team2896damien.wordpress.com'," +
		" 'Honolulu','Hawaii','USA',NULL,2009,'Technology. Teamwork. Community.')," +
		"('frc2897',2897,'R3 - Rapoport Raven Robotics','http://www.firstinspires.org/'," +
		" 'Waco','Texas','USA',NULL,2009,NULL)," +
		"('frc2898',2898,'Flying Hedgehogs','http://www.team2898.bpsrobotics.org'," +
		" 'Beaverton','Oregon','USA',NULL,2009,NULL)," +
		"('frc2900',2900,'The Mighty Penguins','http://www.firstinspires.org/'," +
		" 'Washington','District of Columbia','USA',NULL,2009,NULL)," +
		"('frc2901',2901,'Rocket Sock''em Robots','http://www.team2901.com'," +
		" 'Cortland','OH','USA',NULL,2009,NULL)," +
		"('frc2902',2902,'NYACK','http://normandyrobotics.weebly.com'," +
		" 'Saint Louis','MO','USA',NULL,2009,NULL)," +
		"('frc2903',2903,'NeoBots','http://neobots2903.org/'," +
		" 'Arlington','Washington','USA',NULL,2009,'Robots of the world, unite!')," +
		"('frc2904',2904,'PlaZmachines',NULL," +
		" 'Monroe','WA','USA',NULL,2009,NULL)," +
		"('frc2905',2905,'Sultans of Turkiye','http://www.team2905.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2009,'To feel as one, to move as one!')," +
		"('frc2906',2906,'Sentinel Prime Robotics','http://www.firstinspires.org/'," +
		" 'Spanaway','Washington','USA',NULL,2009,NULL)," +
		"('frc2907',2907,'Lion Robotics','http://www.lionrobotics.org'," +
		" 'Auburn','Washington','USA',NULL,2009,'Reliable, Simplistic, & Creative')," +
		"('frc2908',2908,'DeceptiKangs',NULL," +
		" 'Kirkland','WA','USA',NULL,2009,NULL)," +
		"('frc2909',2909,'Zebra Bots','http://www.zebrabots.com'," +
		" 'Rochester','Indiana','USA',NULL,2009,'Do your Best')," +
		"('frc2910',2910,'Jack in the Bot','http://frcteam2910.org'," +
		" 'Mill Creek','Washington','USA',NULL,2009,'Think outside the Bot')," +
		"('frc2911',2911,'CoolBots','http:///Team2911.org'," +
		" 'Washington','District of Columbia','USA',NULL,2009,NULL)," +
		"('frc2912',2912,'Panther Robotics','http://www.firstinspires.org/'," +
		" 'Washington','District of Columbia','USA',NULL,2009,'Great Minds, Great Robot, Great Results')," +
		"('frc2913',2913,'ROBOT RAMBLER',NULL," +
		" 'Washington','DC','USA',NULL,2009,NULL)," +
		"('frc2914',2914,'TIGER PRIDE ','http://2914.io/'," +
		" 'Washington','District of Columbia','USA',NULL,2009,'You can''t hide that TIGER PRIDE')," +
		"('frc2915',2915,'Pandamonium','http://team2915.com'," +
		" 'Portland','Oregon','USA',NULL,2009,NULL)," +
		"('frc2916',2916,'Bionic Bears','http://2916.future-engineer.com'," +
		" 'Palm Bay','Florida','USA',NULL,2009,'Engineering the Future...')," +
		"('frc2917',2917,'Woodward RoboDogs',NULL," +
		" 'Cincinnati','OH','USA',NULL,2009,NULL)," +
		"('frc2918',2918,'Gearheads','http://www.wcctgearheads.com'," +
		" 'Jasper','Alabama','USA',NULL,2009,'Creating our future through technology.')," +
		"('frc2919',2919,'Night Wolves','http://team2919.net'," +
		" 'L.A.','CA','USA',NULL,NULL,NULL)," +
		"('frc2920',2920,'Robo-Hawks','http://robohawk.webs.com'," +
		" 'Holly Springs','MS','USA',NULL,2009,NULL)," +
		"('frc2921',2921,'Falcon',NULL," +
		" 'Tacoma','WA','USA',NULL,2009,NULL)," +
		"('frc2922',2922,'RoboCon',NULL," +
		" 'Concrete','WA','USA',NULL,2009,NULL)," +
		"('frc2923',2923,'Aggies','http://www.firstinspires.org/'," +
		" 'Rosalia','Washington','USA',NULL,2009,'Represent Agriculture')," +
		"('frc2924',2924,'2924 Murphy''s Lawyers',NULL," +
		" 'Chimacum','WA','USA',NULL,2009,NULL)," +
		"('frc2925',2925,'North Central Tech Center',NULL," +
		" 'Wenatchee','WA','USA',NULL,2009,NULL)," +
		"('frc2926',2926,'Robo Sparks','http://www.robosparks.com'," +
		" 'Wapato','Washington','USA',NULL,2009,'Igniting imagination one Spark at a time.')," +
		"('frc2927',2927,'Pi Rho Techs','http://bayessoftware.com/robotics/'," +
		" 'Graham','Washington','USA',NULL,2009,NULL)," +
		"('frc2928',2928,'Viking Robotics','https://www.ballardrobotics.org/'," +
		" 'Seattle','Washington','USA',NULL,2009,'Viking Robotics: Where we go for infinite possibilities')," +
		"('frc2929',2929,'JAGBOTS','http://jagbots.com'," +
		" 'Puyallup','Washington','USA',NULL,2009,'Geared for Life')," +
		"('frc2930',2930,'Sonic Squirrels','http://www.sonicsquirrels.com'," +
		" 'Snohomish','Washington','USA',NULL,2009,'Nuts for FIRST')," +
		"('frc2932',2932,'Pueo Power Engineering','http://www.mpirobotics.zxq.net'," +
		" 'Honolulu','HI','USA',NULL,2009,NULL)," +
		"('frc2933',2933,'BASH',NULL," +
		" 'Bronx','NY','USA',NULL,2009,NULL)," +
		"('frc2934',2934,'Tech Tigers',NULL," +
		" 'Houston','TX','USA',NULL,2009,NULL)," +
		"('frc2935',2935,'NACI Robotics','https://sites.google.com/tdsb.on.ca/nacirobotics-2935/home'," +
		" 'Etobicoke','Ontario','Canada',NULL,2009,NULL)," +
		"('frc2936',2936,'Gatorzillas','http://www.gatorzillas.com'," +
		" 'Dickinson','TX','USA',NULL,2009,NULL)," +
		"('frc2938',2938,'HSE Tigers','http://harmonytigers.webonsites.com'," +
		" 'Houston','TX','USA',NULL,2009,NULL)," +
		"('frc2940',2940,'The Alternative Experience',NULL," +
		" 'Chicago','IL','USA',NULL,2009,NULL)," +
		"('frc2941',2941,'BLURR-Ben Logan Ultra Raider Robotics',NULL," +
		" 'Bellefontaine','OH','USA',NULL,2009,NULL)," +
		"('frc2942',2942,'Panda Robotics','http://www.firstinspires.org/'," +
		" 'Seattle','Washington','USA',NULL,2009,'\" We have the gear and we''re not afraid to use it\"')," +
		"('frc2943',2943,'ROBO Dynamics',NULL," +
		" 'Dallas','TX','USA',NULL,2009,NULL)," +
		"('frc2944',2944,'Titanium Tigers','http://www.team2944.com/'," +
		" 'Spokane','Washington','USA',NULL,2009,'Living on the REG-gie')," +
		"('frc2945',2945,'Steel Mustangs','http://www.manitourobotics.com'," +
		" 'Manitou Springs','Colorado','USA',NULL,2009,'Continuous Improvement!')," +
		"('frc2946',2946,'Novas',NULL," +
		" 'Milwaukee','WI','USA',NULL,2009,NULL)," +
		"('frc2947',2947,'ROBO-TIGERS','http://www.robotigers.org'," +
		" 'EL PASO','TX','USA',NULL,2009,NULL)," +
		"('frc2948',2948,'Prototype','https://sites.google.com/site/frcteam2948/home'," +
		" 'Fort Worth','TX','USA',NULL,NULL,NULL)," +
		"('frc2949',2949,'pwnage','http://pwnage.foxvalleyrobotics.com'," +
		" 'Batavia','IL','USA',NULL,2009,NULL)," +
		"('frc2950',2950,'Devastators','http://hsawacorobotics.weebly.com/'," +
		" 'Waco','Texas','USA',NULL,2009,'Prepare to be Devestated')," +
		"('frc2951',2951,'Benson High Robotics Club',NULL," +
		" 'Portland','OR','USA',NULL,2009,NULL)," +
		"('frc2952',2952,'Brackenbots',NULL," +
		" 'San Antonio','TX','USA',NULL,2009,NULL)," +
		"('frc2953',2953,'South Dallas Bots',NULL," +
		" 'Dallas','TX','USA',NULL,2009,NULL)," +
		"('frc2956',2956,'E-STEM EAGLES','http://www.estemacademy.org'," +
		" 'Houston','TX','USA',NULL,2009,NULL)," +
		"('frc2957',2957,'Knights','http://www.firstinspires.org/'," +
		" 'Alden','Minnesota','USA',NULL,2009,NULL)," +
		"('frc2958',2958,'Grizzly''s',NULL," +
		" 'Canton','GA','USA',NULL,2009,NULL)," +
		"('frc2959',2959,'Robotarians','http://cwtech-robotarians.org'," +
		" 'Coloma','Michigan','USA',NULL,2009,'\"The only sport where every student can go pro\"')," +
		"('frc2960',2960,'Automation Nation','http://team2960.org'," +
		" 'Birmingham','Michigan','USA',NULL,2009,NULL)," +
		"('frc2961',2961,'Crimson Tide Engineers/ Coolidge Colts','http://www.firstinspires.org/'," +
		" 'Washington','District of Columbia','USA',NULL,2009,'free your mind and success will follow')," +
		"('frc2962',2962,'EagleBots','http://lukemoorerobotics.org'," +
		" 'Washington','DC','USA',NULL,NULL,NULL)," +
		"('frc2963',2963,'AnaDroids','http://anadroids2963.moonfruit.com/'," +
		" 'Washington','District of Columbia','USA',NULL,2009,NULL)," +
		"('frc2964',2964,'Robopanthers','http://www.firstinspires.org/'," +
		" 'Washington','District of Columbia','USA',NULL,2009,'Transforming Young Minds into Tomorrows Engineers')," +
		"('frc2965',2965,'Iron Vikings',NULL," +
		" 'Lubbock','TX','USA',NULL,2009,NULL)," +
		"('frc2966',2966,'Tiger Tech','http://wmebrahim.wix.com/vviewisd#!r.c.'," +
		" 'Pharr','Texas','USA',NULL,2009,'Technology is the key to a better future')," +
		"('frc2967',2967,'Columbus IronWorks','http://p2s.coolpage.biz'," +
		" 'Columbus','GA','USA',NULL,2009,NULL)," +
		"('frc2969',2969,'Aftermath','http://www.firstinspires.org/'," +
		" 'San Antonio','Texas','USA',NULL,2009,'Call me geek today... Call me boss tomorow.')," +
		"('frc2970',2970,'eSchool eBots',NULL," +
		" 'Kenosha','WI','USA',NULL,2009,NULL)," +
		"('frc2971',2971,'ROBO-IDAHO','http://rpta.net'," +
		" 'Coeur d''Alene','ID','USA',NULL,2009,NULL)," +
		"('frc2972',2972,'RC Dawson','http://RCDawson.org'," +
		" 'Lafayette','Colorado','USA',NULL,2009,'Boundless Creativity')," +
		"('frc2973',2973,'Mad Rockers','http://www.rockers2973.com/'," +
		" 'Madison','Alabama','USA',NULL,2009,'Keep it cheap')," +
		"('frc2974',2974,'Walton Robotics','http://www.waltonrobotics.org'," +
		" 'Marietta','Georgia','USA',NULL,2009,'Who will you inspire today?')," +
		"('frc2975',2975,'Team Viral',NULL," +
		" 'Central','LA','USA',NULL,2009,NULL)," +
		"('frc2976',2976,'Spartabots','http://spartabots.org'," +
		" 'Sammamish','Washington','USA',NULL,2009,'This. Is. SPARTA!!!')," +
		"('frc2977',2977,'Sir Lancer Bots','http://www.lacrescentrobotics.com/'," +
		" 'La Crescent','Minnesota','USA',NULL,2009,'Arming the Seven Rivers Region')," +
		"('frc2978',2978,'Cavaliers','http://robotics.bishopdubourg.org'," +
		" 'Saint Louis','Missouri','USA',NULL,2009,'Faith in Christ....Service to Others')," +
		"('frc2979',2979,'RO-BEAUMONT',NULL," +
		" 'Beaumont','TX','USA',NULL,2009,NULL)," +
		"('frc2980',2980,'The Whidbey Island Wild Cats','http://www.team2980.wixsite.com/team2980'," +
		" 'Oak Harbor','Washington','USA',NULL,2009,'We are ONE team, we have ONE mission our goal is to complete that mission as ONE')," +
		"('frc2981',2981,'Bulldogs','http://www.wacoisd.org'," +
		" 'Waco','TX','USA',NULL,2009,NULL)," +
		"('frc2982',2982,'Phoenix Metal','http://www.laredoisd.org/ECHSRobotics/Homepage.html'," +
		" 'Laredo','TX','USA',NULL,2009,NULL)," +
		"('frc2983',2983,'Robo-Noles',NULL," +
		" 'Fairburn','GA','USA',NULL,2009,NULL)," +
		"('frc2984',2984,'Vikings Robotics','http://ljrobotics.org/'," +
		" 'La Jolla','California','USA',NULL,2009,'2984 Where Metal meets mettle')," +
		"('frc2985',2985,'Tech Know Logic',NULL," +
		" 'San Antonio','TX','USA',NULL,2009,NULL)," +
		"('frc2986',2986,'ASTEC NASA Explorers','http://www.astecrobotics.org'," +
		" 'Oklahoma City','Oklahoma','USA',NULL,2009,'A better future through knowledge ')," +
		"('frc2987',2987,'Rogue Robotics','https://www.team2987.com/'," +
		" 'Farmington','Minnesota','USA',NULL,2009,NULL)," +
		"('frc2988',2988,'RoboGenesis','http://www.edline.net/pages/Clarke_County_High_School/Design_Resources/Shortcuts/Programs/Robotics'," +
		" 'Berryville','Virginia','USA',NULL,2009,'The quest is greater than what is sought')," +
		"('frc2989',2989,'Star Tech','http://team2989.weebly.com/'," +
		" 'Richfield','Minnesota','USA',NULL,2009,'Star Tech')," +
		"('frc2990',2990,'Hotwire','http://hotwirerobotics.com'," +
		" 'Turner','Oregon','USA',NULL,2009,'Whatever It Takes')," +
		"('frc2991',2991,'Boxer Bots',NULL," +
		" 'Houston','TX','USA',NULL,2009,NULL)," +
		"('frc2992',2992,'The S.S. Prometheus','http://mandevillerobotics.org'," +
		" 'Mandeville','Louisiana','USA',NULL,2009,'Light the Fire, Set the Sail, Spread the Word')," +
		"('frc2993',2993,'InTech MegaBots','http://megabots.intechchs.org'," +
		" 'Logan','Utah','USA',NULL,2009,'Ask Erik.')," +
		"('frc2994',2994,'ASTECHZ','http://team2994.ca'," +
		" 'Kanata','Ontario','Canada',NULL,2009,'Dedication, Determination and Duct Tape')," +
		"('frc2995',2995,'\"Mountain Force Robotics\"',NULL," +
		" 'Dallas','TX','USA',NULL,NULL,NULL)," +
		"('frc2996',2996,'Cougars Gone Wired','http://www.team2996.com'," +
		" 'Colorado Springs','Colorado','USA',NULL,2009,'Through teamwork comes diligence, through diligence comes conquest.')," +
		"('frc2998',2998,'VikingBots','http://www.firstinspires.org/'," +
		" 'Richmond','Virginia','USA',NULL,2009,NULL)," +
		"('frc2999',2999,'TJHS','http://tjeff.org'," +
		" 'Rochester','NY','USA',NULL,2009,NULL);";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_6 = SQL_INSERT_TEAMS +
		"('frc3000',3000,'Jeffersontown Robochargers','http://www.firstinspires.org/'," +
		" 'Louisville','Kentucky','USA',NULL,2009,'Chargin'' for a Win')," +
		"('frc3001',3001,'Chieftain Robotics',NULL," +
		" 'Santa Fe Springs','CA','USA',NULL,2009,NULL)," +
		"('frc3002',3002,'Roboblazers',NULL," +
		" 'Detroit','MI','USA',NULL,2009,NULL)," +
		"('frc3003',3003,'TAN(X)','http://www.tanxrobotics.com'," +
		" 'Canandaigua','New York','USA',NULL,2009,'Don''t tread on me')," +
		"('frc3004',3004,'Bronx Knights','http://www.firstinspires.org/'," +
		" 'Bronx','New York','USA',NULL,2009,'nurture and challenge students for thr twenty first century')," +
		"('frc3005',3005,'RoboChargers','http://www.frc3005.com'," +
		" 'Dallas','Texas','USA',NULL,2009,NULL)," +
		"('frc3006',3006,'Red Rock Robotics','http://team3006.com'," +
		" 'Salt Lake City','Utah','USA',NULL,2009,NULL)," +
		"('frc3007',3007,'Robotitans','http://www.firstinspires.org/'," +
		" 'Oakdale','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3008',3008,'Team Magma','http://www.magmarobotics.com'," +
		" 'Honolulu','Hawaii','USA',NULL,2009,'(Kaizen) Growth and Continual improvement')," +
		"('frc3009',3009,'High Scalers','http://bchsrobotics.com/'," +
		" 'Boulder City','Nevada','USA',NULL,2009,'Success is an Endless Flight ')," +
		"('frc3010',3010,'The Red Plague','https://sites.google.com/site/trojanfrcteam/'," +
		" 'Centerburg','Ohio','USA',NULL,2009,'There is no vaccine for the plague')," +
		"('frc3011',3011,'RoboWarriors','http://www.facebook.com/WiesbadenHighSchoolRoboWarriors'," +
		" 'APO','Hessen','Germany',NULL,2009,'Take this Class and Save the World')," +
		"('frc3012',3012,'Spartans',NULL," +
		" 'Houston','TX','USA',NULL,2009,NULL)," +
		"('frc3013',3013,'Zombots','http://www.frcteam3013.com'," +
		" 'Vallejo ','California','USA',NULL,2009,NULL)," +
		"('frc3014',3014,'Tech Heads','http://www.ahsrobotics.com'," +
		" 'Albuquerque','NM','USA',NULL,2009,NULL)," +
		"('frc3015',3015,'Ranger Robotics','http://www.rangerrobotics.org/'," +
		" 'Spencerport','New York','USA',NULL,2009,' ')," +
		"('frc3016',3016,'CyCreek Crusaders','http://www.firstinspires.org/'," +
		" 'Houston','Texas','USA',NULL,2009,NULL)," +
		"('frc3017',3017,'Patriots','http://www.flhsrobotics.com'," +
		" 'Fresh Meadows','New York','USA',NULL,2009,NULL)," +
		"('frc3018',3018,'Nordic Storm','http://www.nordicstorm.org/'," +
		" 'Saint Peter','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3019',3019,'Firebirds','http://www.firebirdrobotics.com'," +
		" 'Scottsdale','Arizona','USA',NULL,2009,'\"Can you handle the heat?\"')," +
		"('frc3020',3020,'Boeing/ Rocket Scientist Management (RSM)',NULL," +
		" 'Rancho Santa Margarita','CA','USA',NULL,2009,NULL)," +
		"('frc3021',3021,'The  Agency','http://agency-stem.org/'," +
		" 'Escondido','California','USA',NULL,2009,'In God we trust, all others we monitor.')," +
		"('frc3022',3022,'MKS Explosion',NULL," +
		" 'San Jose','CA','USA',NULL,2009,NULL)," +
		"('frc3023',3023,'STARK Industries','http://www.team3023.org'," +
		" 'Elk River','Minnesota','USA',NULL,2009,'Make it Work!')," +
		"('frc3024',3024,'My Favorite Team','http://team3024.com'," +
		" 'Ashland','Oregon','USA',NULL,2009,NULL)," +
		"('frc3025',3025,'DARC','http://www.darcrobotics.org'," +
		" 'Decatur','AL','USA',NULL,2009,NULL)," +
		"('frc3026',3026,'Orange Crush Robotics','http://www.ocr3026.com/'," +
		" 'Delano','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3027',3027,'Seraphim Systems','http://www.firstinspires.org/'," +
		" 'Ventura','California','USA',NULL,2009,'We build robots the Angelic way.')," +
		"('frc3028',3028,'Robot Commando Squad','http://robotcommandosquad.weebly.com'," +
		" 'Hidalgo','Texas','USA',NULL,2009,'The seemingly impossible is possible.')," +
		"('frc3029',3029,'T-STEM Titans','http://www.tstem.us/tsa'," +
		" 'Pharr','Texas','USA',NULL,2009,NULL)," +
		"('frc3032',3032,'Master Team','http://www.master.aju.com.br'," +
		" 'Aracaju','SE','Brazil',NULL,2009,NULL)," +
		"('frc3034',3034,'Galileo','http://www.emek.org.il'," +
		" 'Kefar Blum','HaZafon','Israel',NULL,2009,NULL)," +
		"('frc3035',3035,'Droid Rage','http://www.droidrage.org'," +
		" 'Corpus Christi','Texas','USA',NULL,2009,'Team 3035, Keepin it  Alive!')," +
		"('frc3036',3036,'DROBA Warriors','http://www.firstinspires.org/'," +
		" 'Deer River','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3037',3037,'LOBOTS','http://www.firstinspires.org/'," +
		" 'Longview','Texas','USA',NULL,2009,NULL)," +
		"('frc3038',3038,'ICE','http://sites.google.com/site/team3038ice'," +
		" 'North Branch','Minnesota','USA',NULL,2009,'Not Your Ordinary Minnesota Ice')," +
		"('frc3039',3039,'Wildcat Robotics','https://sites.google.com/view/wildcatrobotics/home'," +
		" 'Destrehan','Louisiana','USA',NULL,2009,NULL)," +
		"('frc3040',3040,'red knights',NULL," +
		" 'toronto','ON','Canada',NULL,2009,NULL)," +
		"('frc3041',3041,'Spartans Robotics Club','http://www.firstinspires.org/'," +
		" 'Chula Vista','California','USA',NULL,2009,'We make the dream work')," +
		"('frc3042',3042,'Cobalt Catalysts','http://www.team3042.org'," +
		" 'Apple Valley','Minnesota','USA',NULL,2009,'Accelerating the future of robotics')," +
		"('frc3043',3043,'Dragons','http://www.dvrdragons.webs.com/'," +
		" 'El Paso','Texas','USA',NULL,2009,'There are not such things as problems, just challenges!!')," +
		"('frc3044',3044,'Team 0xBE4','https://frcteam3044.team/'," +
		" 'Ballston Spa','New York','USA',NULL,2009,'The Ox is in The House!')," +
		"('frc3045',3045,'The Gear Gremlins','http://www.team3045.org'," +
		" 'San Mateo','California','USA',NULL,2009,'Make it, Break it, Fix it, Share it, Joy!')," +
		"('frc3046',3046,'GWHS RoboFanatics',NULL," +
		" 'Charmco','WV','USA',NULL,2009,NULL)," +
		"('frc3048',3048,'Cutthroat Robotics','http://cutthroatrobotics.co'," +
		" 'Mesa ','Arizona','USA',NULL,2009,'Just WELD It')," +
		"('frc3049',3049,'BremerTron','http://fluffyrobotics.info'," +
		" 'Bremerton','Washington','USA',NULL,2009,NULL)," +
		"('frc3052',3052,'Makif Yud','http://firstdorothy3052.com'," +
		" 'Rishon Lezion','M','Israel',NULL,2009,NULL)," +
		"('frc3053',3053,'VB Stingers','http://vbstingers.com/'," +
		" 'Queens Village','New York','USA',NULL,2009,NULL)," +
		"('frc3054',3054,'IceStorm','http://www.cookcountyschools.org/se3bin/clientgenie.cgi'," +
		" 'Grand Marais','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3055',3055,'Furious George','http://www.firstinspires.org/'," +
		" 'Austin','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3056',3056,'Pirates','http://www.firstinspires.org/'," +
		" 'Crookston','Minnesota','USA',NULL,2009,'\"KISS\" Method works')," +
		"('frc3057',3057,'MHS Jaquers',NULL," +
		" 'Rochester','NY','USA',NULL,2009,NULL)," +
		"('frc3058',3058,'AnnDroids','http://www.anndroids3058.com'," +
		" 'Annandale','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3059',3059,'Riverside Envirobotics','http://riverside.ypschools.org/home'," +
		" 'Yonkers','New York','USA',NULL,2009,NULL)," +
		"('frc3060',3060,'The Narcoleptic Doxins',NULL," +
		" 'Birmingham','MI','USA',NULL,2009,NULL)," +
		"('frc3061',3061,'Huskie Robotics','http://team3061.org/'," +
		" 'Naperville','Illinois','USA',NULL,2009,'Collaborating to create; innovating to inspire')," +
		"('frc3062',3062,'Spartan Robotics','http://www.spartanrobotics3062.com'," +
		" 'Natrona Heights','Pennsylvania','USA',NULL,2009,'Our Future is Building... Robots')," +
		"('frc3063',3063,'HOLTZ-STARS',NULL," +
		" 'Tel Aviv','M','Israel',NULL,2009,NULL)," +
		"('frc3064',3064,'Tichon Tel Aviv',NULL," +
		" 'Tichon Tel Aviv','HA','Israel',NULL,2009,NULL)," +
		"('frc3065',3065,'Jatt High School','http://www.jatt-highschool.net'," +
		" 'Jatt','HaMerkaz','Israel',NULL,2009,NULL)," +
		"('frc3067',3067,'Robovikes','http://genevarobovikes.com'," +
		" 'Geneva','Illinois','USA',NULL,2009,NULL)," +
		"('frc3069',3069,'Rollin Stones','http://rollinstones3069.com'," +
		" 'Detroit','MI','USA',NULL,2009,NULL)," +
		"('frc3070',3070,'Team Pronto','http://teampronto.weebly.com/'," +
		" 'Seattle','Washington','USA',NULL,2009,'Always look beyond what you can see')," +
		"('frc3071',3071,'Certified NOOBS',NULL," +
		" 'Humble','TX','USA',NULL,2009,NULL)," +
		"('frc3072',3072,'Backwoods Bots','http://www.backwoodsbots3072.com'," +
		" 'Wytheville','Virginia','USA',NULL,2009,'Backwoods Bots, sending the competetion 127.0.0.1')," +
		"('frc3073',3073,'firstinlod','http://firstinlod.ort.org.il'," +
		" 'LOD','TA','Israel',NULL,2009,NULL)," +
		"('frc3074',3074,'New School Robotics','http://team3074.org'," +
		" 'Kennebunk','ME','USA',NULL,2009,NULL)," +
		"('frc3075',3075,'Ha-Dream Team','http://hadream3075.com/'," +
		" 'Hod-Ha''Sharon','HaMerkaz','Israel',NULL,2009,'We don''t just dream - we make our dreams come true!')," +
		"('frc3076',3076,'Ort Arad',NULL," +
		" 'Arad','D','Israel',NULL,2009,NULL)," +
		"('frc3077',3077,'Robobrain','http://frc3077.robobrainlab.com'," +
		" 'Jerusalem','JM','Israel',NULL,2009,NULL)," +
		"('frc3079',3079,'The RoboWarriors','http://smokiethewarrior.com'," +
		" 'Richmond','VA','USA',NULL,2009,NULL)," +
		"('frc3080',3080,'So-Called Robotics',NULL," +
		" 'Rio Grande City','TX','USA',NULL,2009,NULL)," +
		"('frc3081',3081,'RoboEagles','http://kennedyrobotics.com'," +
		" 'Bloomington','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3082',3082,'Chicken Bot Pie','http://www.team3082.com'," +
		" 'Minnetonka','Minnesota','USA',NULL,2009,'It takes a community to build a robot')," +
		"('frc3083',3083,'Artemis','http://www.artemis3083.org'," +
		" 'Maagan Michael','HaZafon','Israel',NULL,2009,'The sky isn''t the limit anymore. artemis, we hunt our goals.')," +
		"('frc3084',3084,'PatriORT',NULL," +
		" 'Hazor','Z','Israel',NULL,2009,NULL)," +
		"('frc3086',3086,'Broncoweiss',NULL," +
		" 'Kyriat Shmona','Z','Israel',NULL,2009,NULL)," +
		"('frc3087',3087,'Ben Shemen',NULL," +
		" 'Ben Shemen','M','Israel',NULL,2009,NULL)," +
		"('frc3088',3088,'Kill-O-Bites','http://kob.ort.org.il'," +
		" 'Kiryat Bialik','HA','Israel',NULL,2009,NULL)," +
		"('frc3089',3089,'Bot-N','http://www.team3089.com'," +
		" 'Haifa','HA','Israel',NULL,2009,NULL)," +
		"('frc3090',3090,'Ramhawks','http://www.winona.k12.mn.us/wshs/RoboticsTeam3090'," +
		" 'Winona','Minnesota','USA',NULL,2009,'If we run out of duct tape there are always zip ties')," +
		"('frc3091',3091,'100 Scholars','http://www.firstinspires.org/'," +
		" 'Atlanta','Georgia','USA',NULL,2009,'It''s What comes FIRST That Makes Us Champions')," +
		"('frc3092',3092,'Nazareth Ilit',NULL," +
		" 'Nazareth Ilit','Z','Israel',NULL,2009,NULL)," +
		"('frc3093',3093,'Bearcats',NULL," +
		" 'Monroe','WA','USA',NULL,2009,NULL)," +
		"('frc3094',3094,'Blich',NULL," +
		" 'Ramat Gan','M','Israel',NULL,2009,NULL)," +
		"('frc3095',3095,'Pilots',NULL," +
		" 'Warren','MI','USA',NULL,2009,NULL)," +
		"('frc3096',3096,'The Village Bulldogs','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2009,NULL)," +
		"('frc3097',3097,'Blue Lightning',NULL," +
		" 'Detroit','MI','USA',NULL,2009,NULL)," +
		"('frc3098',3098,'The Captains','http://team3098.org'," +
		" 'Waterford','Michigan','USA',NULL,2009,NULL)," +
		"('frc3100',3100,'Lightning Turtles','http://team3100.com/'," +
		" 'Mendota Heights','Minnesota','USA',NULL,2009,NULL)," +
		"('frc3102',3102,'Tech-No-Tigers','http://www.tnt3102.org/'," +
		" 'Nevis','Minnesota','USA',NULL,2009,'To Build, To Succeed, To Overcome')," +
		"('frc3103',3103,'Iron Plaid','https://www.ironplaid.org'," +
		" 'Houston','Texas','USA',NULL,2009,'Empowered girls with power tools')," +
		"('frc3104',3104,'The Cyber Rams','http://www.cyberrobotics.org'," +
		" 'Stamford','Connecticut','USA',NULL,2009,'Thunder Rolls')," +
		"('frc3105',3105,'Lagablab','http://roboweb3105.webs.com'," +
		" 'Manila','00','Philippines',NULL,2009,NULL)," +
		"('frc3106',3106,'The Contenders',NULL," +
		" 'Farmersville','TX','USA',NULL,2009,NULL)," +
		"('frc3107',3107,'Tilden Techers','http://none%20yet'," +
		" 'Chicago','IL','USA',NULL,2009,NULL)," +
		"('frc3108',3108,'PR9',NULL," +
		" 'pttsburgh','PA','USA',NULL,2009,NULL)," +
		"('frc3109',3109,'GA Robotics',NULL," +
		" 'Memphis','TN','USA',NULL,2009,NULL)," +
		"('frc3110',3110,'SeaBots','http://www.firstinspires.org/'," +
		" 'Chicago','Illinois','USA',NULL,2009,NULL)," +
		"('frc3111',3111,'RoboCougars',NULL," +
		" 'Newark','NJ','USA',NULL,2009,NULL)," +
		"('frc3112',3112,'GSERT Falcons',NULL," +
		" 'Bronx','NY','USA',NULL,2009,NULL)," +
		"('frc3114',3114,'To be determined','http://TBD'," +
		" 'Southfield','MI','USA',NULL,2009,NULL)," +
		"('frc3115',3115,'Robo-Panthers',NULL," +
		" 'Detroit','MI','USA',NULL,2009,NULL)," +
		"('frc3116',3116,'Phoenix','http://melchanel96.wix.com/mase-team-phoenix#'," +
		" 'Memphis','TN','USA',NULL,2009,NULL)," +
		"('frc3117',3117,'Harfangs','http://www.firstinspires.org/'," +
		" 'Sherbrooke','Québec','Canada',NULL,2009,NULL)," +
		"('frc3119',3119,'Rockets','http://detroit-crockett.com'," +
		" 'Detroit','MI','USA',NULL,2009,NULL)," +
		"('frc3120',3120,'RoboKnights','http://ndroboknights.com/'," +
		" 'Sherman Oaks','California','USA',NULL,2009,NULL)," +
		"('frc3121',3121,'Alloy Fingerprints',NULL," +
		" 'Portage County','OH','USA',NULL,2009,NULL)," +
		"('frc3122',3122,'The Alluminators','https://www.newulmrobotics.com/frc'," +
		" 'New Ulm','Minnesota','USA',NULL,2009,'Built to Shine!')," +
		"('frc3123',3123,'Wildcogs','http://www.team3123.com'," +
		" 'Pottstown','PA','USA',NULL,2010,NULL)," +
		"('frc3124',3124,'Cyberian Huskies',NULL," +
		" 'Edmond','OK','USA',NULL,2010,NULL)," +
		"('frc3125',3125,'Pirates of the Pythagorean','http://www.ghafirstrobotics.org'," +
		" 'Hartford','CT','USA',NULL,2010,NULL)," +
		"('frc3126',3126,'FlashPoint','http://www.flashpointrobotics.org/FlashPoint_Robotics/Home.html'," +
		" 'Hollis','NH','USA',NULL,2010,NULL)," +
		"('frc3128',3128,'Aluminum Narwhals','http://team3128.org/'," +
		" 'San Diego','California','USA',NULL,2010,'Pointing the way to robotics.')," +
		"('frc3129',3129,'Green MacHHHHine','http://www.greenmachhhhine.com'," +
		" 'Mountain View','CA','USA',NULL,2010,NULL)," +
		"('frc3130',3130,'The ERRORs','http://www.error3130.org'," +
		" 'Woodbury','Minnesota','USA',NULL,2010,'Take Action, Create Results, Shower Later')," +
		"('frc3131',3131,'Gladiators','http://www.gladstonerobotics.com'," +
		" 'Gladstone','Oregon','USA',NULL,2010,'DE Cineribus Non Resurgent (Latin) Out of the ashes we rise')," +
		"('frc3132',3132,'Thunder Down Under','http://www.thethunderdownunder.org'," +
		" 'Sydney','New South Wales','Australia',NULL,2010,'Innovate. Improve. Inspire the world.')," +
		"('frc3133',3133,'Coronado Robotics','http://coronadorobotics.com'," +
		" 'Scottsdale','Arizona','USA',NULL,2010,'Our power level is over 9000')," +
		"('frc3134',3134,'The Accelerators ','http://clbaccelerators.weebly.com/'," +
		" 'Cass Lake','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3135',3135,'Robotic Colonels','http://fwparkerrobotics.org'," +
		" 'Chicago','Illinois','USA',NULL,2010,NULL)," +
		"('frc3136',3136,'Official Robotics Constructors of Ashland','http://hcps2.hanover.k12.va.us/phhs/index.htm'," +
		" 'Ashland','Virginia','USA',NULL,2010,NULL)," +
		"('frc3137',3137,'T-Birds ','http://connetquot-robotics.webs.com/'," +
		" 'Bohemia','New York','USA',NULL,2010,NULL)," +
		"('frc3138',3138,'Innovators Robotics','http://www.InnovatorsRobotics.com'," +
		" 'Dayton','Ohio','USA',NULL,2010,'Putting Metal and Minds into Motion')," +
		"('frc3139',3139,'Omega','https://sites.google.com/oxboe.com/ohsroboticsandengineering/home?authuser=0'," +
		" 'Oxford','Alabama','USA',NULL,2010,NULL)," +
		"('frc3140',3140,'Flagship','http://farragutrobotics.org/'," +
		" 'Knoxville','Tennessee','USA',NULL,2010,'Full STEAM Ahead')," +
		"('frc3141',3141,'Team Alchemy','http://www.team-alchemy.org'," +
		" 'Torrington','CT','USA',NULL,2010,NULL)," +
		"('frc3142',3142,'Aperture','http://newtonroboticsteam.org/'," +
		" 'Newton','New Jersey','USA',NULL,2010,'Make it happen')," +
		"('frc3143',3143,'NTC Night Hawks','http://www.ntcrobotics.com'," +
		" 'Norfolk','VA','USA',NULL,2010,NULL)," +
		"('frc3144',3144,'KRYPTO-KNIGHTS','http://www.krypto-knights.org'," +
		" '2801 N.W. 27TH','OK','USA',NULL,2010,NULL)," +
		"('frc3145',3145,'TeraViks','http://teraviksrobotics.wixsite.com/2019'," +
		" 'Coeur d'' Alene','Idaho','USA',NULL,2010,'Leading Engineering and Innovation Fearlessly')," +
		"('frc3146',3146,'GRANBY GRUNTS','http://granbygrunts.com'," +
		" 'Granby','Connecticut','USA',NULL,2010,NULL)," +
		"('frc3147',3147,'Munster Horsepower','http://robotics.munster.us'," +
		" 'Munster','Indiana','USA',NULL,2010,'They never said it can''t fly')," +
		"('frc3148',3148,'Joltz',NULL," +
		" 'Dorchester','MA','USA',NULL,2010,NULL)," +
		"('frc3149',3149,'PANDAMONIUM!',NULL," +
		" 'Lakeland','FL','USA',NULL,2010,NULL)," +
		"('frc3150',3150,'Mustangs','http://steelstangs.com'," +
		" 'Marriottsville','MD','USA',NULL,2010,NULL)," +
		"('frc3151',3151,'Cyberstorm','http://www.firstinspires.org/'," +
		" 'Sewell','New Jersey','USA',NULL,2010,NULL)," +
		"('frc3152',3152,'KTC-Monsters','http://ktcmonsters.com'," +
		" 'Idabel','Oklahoma','USA',NULL,2010,NULL)," +
		"('frc3154',3154,'Robo-Razzaitore \"THE RAZZ\"','http://www.lochravenrobotics.com'," +
		" 'Baltimore','MD','USA',NULL,2010,NULL)," +
		"('frc3155',3155,'Globots','http://swcta.net/robotics'," +
		" 'Las Vegas','NV','USA',NULL,2010,NULL)," +
		"('frc3156',3156,'Solista','http://www.team3156.com'," +
		" 'Fremont','CA','USA',NULL,2010,NULL)," +
		"('frc3157',3157,'Iron Lancers','http://eastridgerobotics.org/'," +
		" 'Rochester','New York','USA',NULL,2010,'If you can dream it, we can build it!')," +
		"('frc3158',3158,'PrepaTec - TECBOT','https://www.facebook.com/tecbot.team/'," +
		" 'Toluca','Mexico','Mexico',NULL,2010,'\"Unlimited Success\"')," +
		"('frc3160',3160,'F. R. O. G. - FIRST Robotics Organization of Grove','http://www.frog3160.org'," +
		" 'Grove','Oklahoma','USA',NULL,2010,'\"Figure It Out\"')," +
		"('frc3161',3161,'Tronic Titans','http://www.team3161.ca/'," +
		" 'Oakville','Ontario','Canada',NULL,2010,'The easiest way to predict the future is to invent it.')," +
		"('frc3162',3162,'RoBoComm-X','http://www.team3162.com'," +
		" 'Rochester','NY','USA',NULL,2010,NULL)," +
		"('frc3163',3163,'ROARBOTS','http://www.wpstigers.k12.ok.us/robotics/Entrance.html'," +
		" 'Henryetta','Oklahoma','USA',NULL,2010,'NA')," +
		"('frc3164',3164,'Stealth Tigers','http://www.firstinspires.org/'," +
		" 'Tampa','Florida','USA',NULL,2010,'B.E. T.I.G.E.R. S.M.A.R.T.')," +
		"('frc3165',3165,'NewBots','http://Coming%20Soon'," +
		" 'Newberg','OR','USA',NULL,2010,NULL)," +
		"('frc3166',3166,'First Year Ravens','http://www.firstinspires.org/'," +
		" 'Sandy','Utah','USA',NULL,2010,NULL)," +
		"('frc3167',3167,'Mechanical Misfits','http://www.firstinspires.org/'," +
		" 'Philadelphia','Pennsylvania','USA',NULL,2010,'It''s Not About the Robot')," +
		"('frc3168',3168,'Warriors','http://blogs.spsk12.net/nrhs-robotics'," +
		" 'Suffolk','Virginia','USA',NULL,2010,'Caution Rogue Robots')," +
		"('frc3169',3169,'Saint-Borgs','http://www.saintborgs.com'," +
		" 'Edmond','Oklahoma','USA',NULL,2010,NULL)," +
		"('frc3170',3170,'Bearded Dragons','http://www.verradorobotics.org'," +
		" 'Buckeye','AZ','USA',NULL,2010,NULL)," +
		"('frc3171',3171,'HURRICANES','http://www.firstinspires.org/'," +
		" 'Westhampton Beach','New York','USA',NULL,2010,'P.R.I.D.E.')," +
		"('frc3172',3172,'HorsePOWER','http://www.team3172.com'," +
		" 'Salina','Kansas','USA',NULL,2010,'\"Overcoming barriers to performance is how groups become teams\"')," +
		"('frc3173',3173,'IgKnighters','http://www.igknighters.com/'," +
		" 'Rochester','New York','USA',NULL,2010,'Ignem Mittere In Terram')," +
		"('frc3174',3174,'ROBODAWGS',NULL," +
		" 'ANNISTON','AL','USA',NULL,2010,NULL)," +
		"('frc3175',3175,'Knight Vision','https://ianshogren.wixsite.com/team3175'," +
		" 'Grosse Pointe Woods','Michigan','USA',NULL,2010,NULL)," +
		"('frc3176',3176,'Purple Precision','http://team3176.com'," +
		" 'Brownsburg','Indiana','USA',NULL,2010,'One team, one home, one goal')," +
		"('frc3177',3177,'Fearless Falcons',NULL," +
		" 'Chicago','Illinois','USA',NULL,2010,NULL)," +
		"('frc3179',3179,'The A.N.T.','http://team3179theant@webstarts.com'," +
		" 'Adair','Oklahoma','USA',NULL,2010,'Alone, we are weak. Together we are strong. We are the A.N.T.')," +
		"('frc3180',3180,'MECHAlosaurs','https://sites.google.com/site/3180mechalosaurs/'," +
		" 'Trafalgar','Indiana','USA',NULL,2010,NULL)," +
		"('frc3181',3181,'Pittsford Panthers','http://www.pittsfordrobotics.org/'," +
		" 'Pittsford','New York','USA',NULL,2010,NULL)," +
		"('frc3182',3182,'Athena''s Warriors','http://team3182.org/'," +
		" 'West Hartford','Connecticut','USA',NULL,2010,'Kids Who Kick Bot')," +
		"('frc3183',3183,'EPIC','http://www.epicrobotics.org'," +
		" 'Burlington','CT','USA',NULL,2010,NULL)," +
		"('frc3184',3184,'Blaze Robotics','http://www.blazerobotics.org'," +
		" 'Burnsville','Minnesota','USA',NULL,2010,'Setting FIRST ablaze')," +
		"('frc3185',3185,'Super Gurus',NULL," +
		" 'Cookeville','TN','USA',NULL,2010,NULL)," +
		"('frc3186',3186,'DECAbotz','http://decabotz.wordpress.com'," +
		" 'Dayton','OH','USA',NULL,NULL,NULL)," +
		"('frc3187',3187,'Arcadia Titan Robotics','http://www.arcadiarobotics.org'," +
		" 'Phoenix','Arizona','USA',NULL,2010,'Be Smart, Be Safe, Be FIRST')," +
		"('frc3188',3188,'The Robotics Factory','http://www.3188factory.com'," +
		" 'Portland','OR','USA',NULL,2010,NULL)," +
		"('frc3189',3189,'Circuit Breakers','https://3189.team/'," +
		" 'Placerville','California','USA',NULL,2010,NULL)," +
		"('frc3190',3190,'Mecha-51',NULL," +
		" 'St Lazare','QC','Canada',NULL,2010,NULL)," +
		"('frc3191',3191,'The Wingnuts',NULL," +
		" 'Salt Lake City','UT','USA',NULL,2010,NULL)," +
		"('frc3192',3192,'High Visibility','http://thstechteam.org'," +
		" 'Portland','Oregon','USA',NULL,2010,'Have Robot, Will Travel')," +
		"('frc3193',3193,'Falco Tech','http://falcotech3193.com/'," +
		" 'Austintown','Ohio','USA',NULL,2010,NULL)," +
		"('frc3194',3194,'The BRAINS',NULL," +
		" 'Tucson','AZ','USA',NULL,NULL,NULL)," +
		"('frc3195',3195,'Robo Ninjas',NULL," +
		" 'La Mesa','CA','USA',NULL,2010,NULL)," +
		"('frc3196',3196,'Team SPORK','http://www.teamspork.com'," +
		" 'Mooresville','North Carolina','USA',NULL,2010,'Effort Equals Results')," +
		"('frc3197',3197,'HexHounds','http://www.cedarburgrobotics.com'," +
		" 'Cedarburg','Wisconsin','USA',NULL,2010,NULL)," +
		"('frc3198',3198,'Robotomy','http://robotomy3198.wordpress.com'," +
		" 'Granby','CO','USA',NULL,2010,NULL)," +
		"('frc3200',3200,'Raptacon','http://www.raptacon.org/'," +
		" 'Aurora','Colorado','USA',NULL,2010,NULL)," +
		"('frc3201',3201,'Ross Rambotics','http://www.rossrambotics.com'," +
		" 'Hamilton','Ohio','USA',NULL,2010,'Spring Break !!1 Duct Tape and cardboard')," +
		"('frc3202',3202,'KnightBots','http://www.hardingrobotics.org'," +
		" 'St. Paul','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3203',3203,'The Unlikely Mix',NULL," +
		" 'Columbus','OH','USA',NULL,2010,NULL)," +
		"('frc3204',3204,'Steampunk Penguins','http://www.firstinspires.org/'," +
		" 'Jamaica Estates','New York','USA',NULL,2010,NULL)," +
		"('frc3205',3205,'Patriots','http://www.firstinspires.org/'," +
		" 'Concord','Massachusetts','USA',NULL,2010,NULL)," +
		"('frc3206',3206,'Royal T-Wrecks','http://www.royaltwrecks.org/'," +
		" 'Woodbury ','Minnesota','USA',NULL,2010,'Thou Shalt Not Play Minecraft')," +
		"('frc3207',3207,'Fighting Scots','http://www.owatcfightingscots.com'," +
		" 'Ogden','UT','USA',NULL,2010,NULL)," +
		"('frc3208',3208,'WildBots','http://www.fcps.edu/CentrevilleHS'," +
		" 'Clifton','VA','USA',NULL,2010,NULL)," +
		"('frc3209',3209,'NHS Robotics','http://team3209.com'," +
		" 'Niles','OH','USA',NULL,2010,NULL)," +
		"('frc3210',3210,'Falcons','http://libertyrobotics.weebly.com'," +
		" 'Hillsboro','OR','USA',NULL,NULL,NULL)," +
		"('frc3211',3211,'The Y Team','http://frc3211.com/'," +
		" 'Yeruham','HaDarom','Israel',NULL,2010,NULL)," +
		"('frc3212',3212,'YME Stingbots','http://isd2190.org'," +
		" 'Granite Falls','Minnesota','USA',NULL,2010,'Respect The Hive')," +
		"('frc3213',3213,'Thunder Tech','http://teamthundertech.com'," +
		" 'Tacoma','WA','USA',NULL,2010,NULL)," +
		"('frc3214',3214,'High Tech Hounds',NULL," +
		" 'Memphis','TN','USA',NULL,2010,NULL)," +
		"('frc3215',3215,'Apollo','http://www.teamprion.com'," +
		" 'Greensboro','North Carolina','USA',NULL,2010,'Indestructible - Innovative - Invincible')," +
		"('frc3216',3216,'Missoula Robotics Team (MRT)','http://www.mrt3216.org'," +
		" 'Missoula','Montana','USA',NULL,2010,'If we actually work on something then something will get done!')," +
		"('frc3217',3217,'L&M',NULL," +
		" 'Edcouch','TX','USA',NULL,2010,NULL)," +
		"('frc3218',3218,'Panther Robotics','https://www.youtube.com/channel/UCv4791VCZYu2OfRrcrtAAvg'," +
		" 'Bonney Lake','Washington','USA',NULL,2010,'Character Builds Champions')," +
		"('frc3219',3219,'TREAD','http://auburnsd.schoolwires.net/Page/1526'," +
		" 'Auburn','Washington','USA',NULL,2010,'We make an impression!')," +
		"('frc3220',3220,'Mechanics of Mayhem','http://www.firstinspires.org/'," +
		" 'Spangle','Washington','USA',NULL,2010,NULL)," +
		"('frc3221',3221,'KM Royals','http://www.firstinspires.org/'," +
		" 'Kent','Washington','USA',NULL,2010,NULL)," +
		"('frc3222',3222,'Robot Bantam',NULL," +
		" 'Clarkston','WA','USA',NULL,2010,NULL)," +
		"('frc3223',3223,'Robotics Of Central Kitsap','http://www.ckschools.org/learning/athletics_activities/robotics_of_central_kitsap/'," +
		" 'Silverdale','Washington','USA',NULL,2010,'You can''t beat the classics')," +
		"('frc3224',3224,'Lion Hit Squad','http://lionhitsquad.moonfruit.com'," +
		" 'Locust Grove','GA','USA',NULL,2010,NULL)," +
		"('frc3225',3225,'Wolverine Robotics','http://frcteam3225.weebly.com/'," +
		" 'West Valley City','Utah','USA',NULL,2010,'The only true failure is when you no longer try')," +
		"('frc3226',3226,'Eagle Tech','http://www.sites.google.com/site/ohseagletech'," +
		" 'Chula Vista','CA','USA',NULL,2010,NULL)," +
		"('frc3227',3227,'RoboWolves',NULL," +
		" 'Cordova','Tennessee','USA',NULL,2010,NULL)," +
		"('frc3228',3228,'Rocket','http://www.haynesrobotics.org/hrob'," +
		" 'Metairie','LA','USA',NULL,NULL,NULL)," +
		"('frc3229',3229,'Hawktimus Prime','http://hawktimusprime.com/'," +
		" 'Holly Springs','North Carolina','USA',NULL,2010,NULL)," +
		"('frc3230',3230,'PrototypeX','http://www.prototypex.org'," +
		" 'Salt Lake City','Utah','USA',NULL,2010,'Good enough')," +
		"('frc3231',3231,'PioTech',NULL," +
		" 'Clifton','NJ','USA',NULL,2010,NULL)," +
		"('frc3232',3232,'South Bend''s Finest',NULL," +
		" 'South Bend','IN','USA',NULL,2010,NULL)," +
		"('frc3233',3233,'Bots of the Round Table','http://www.firstinspires.org/'," +
		" 'Satellite Beach','Florida','USA',NULL,2010,'In technology we unite')," +
		"('frc3234',3234,'Red Arrows','http://www.lowellrobotics.org/'," +
		" 'Lowell','Michigan','USA',NULL,2010,NULL)," +
		"('frc3235',3235,'Mustang',NULL," +
		" 'Detroit','MI','USA',NULL,2010,NULL)," +
		"('frc3236',3236,'TRIFORCE','http://www.team3236.com'," +
		" 'Franklin','Massachusetts','USA',NULL,2010,'Let The TRIFORCE Be With You!')," +
		"('frc3237',3237,'Event Horizon','http://www.firstinspires.org/'," +
		" 'Spanaway','Washington','USA',NULL,2010,NULL)," +
		"('frc3238',3238,'Cyborg Ferrets','http://www.team3238.com'," +
		" 'Anacortes','Washington','USA',NULL,2010,'There is no √-1 in ''team.''')," +
		"('frc3239',3239,'Birds of Prey','http://www.team3239.com'," +
		" 'Layton','Utah','USA',NULL,2010,NULL)," +
		"('frc3240',3240,'Team Orion','https://sites.google.com/a/nisd.net/robotics/'," +
		" 'San Antonio','Texas','USA',NULL,2010,NULL)," +
		"('frc3241',3241,'Davinci Dragon Army','http://team3241.org'," +
		" 'Ogden','Utah','USA',NULL,2010,NULL)," +
		"('frc3242',3242,'ILLUMICATS','http://www.fhsemit.org/'," +
		" 'Ocala','Florida','USA',NULL,2010,'\"Where Ideas Become Reality\"')," +
		"('frc3243',3243,'Amperes','http://amesrobotics.weebly.com/'," +
		" 'Salt Lake City','Utah','USA',NULL,2010,NULL)," +
		"('frc3244',3244,'Granite City Gear Heads','http://www.granitecitygearheads.com/'," +
		" 'Saint Cloud','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3245',3245,'Ravens','http://www.firstinspires.org/'," +
		" 'Sandy','Utah','USA',NULL,2010,NULL)," +
		"('frc3246',3246,'Vandals','http://roboticsof2011.jimbo.com'," +
		" 'Eureka','NV','USA',NULL,2010,NULL)," +
		"('frc3247',3247,'Robopack','http://www.firstinspires.org/'," +
		" 'Shawnee','Oklahoma','USA',NULL,2010,'Run with the Pack')," +
		"('frc3250',3250,'Kennedy Robotics','http://www.first3250.com/'," +
		" 'Sacramento','California','USA',NULL,2010,'Zipties, E-Tape, and Sriracha')," +
		"('frc3251',3251,'Savage Pride','http://www.salmonschools.com/ExtraCurricular/Robotics.html'," +
		" 'Salmon','Idaho','USA',NULL,2010,'Duct tape fixes all')," +
		"('frc3253',3253,'Rustlers',NULL," +
		" 'Great Falls','MT','USA',NULL,2010,NULL)," +
		"('frc3255',3255,'Super NURDs','http://www.frcteam3255.com'," +
		" 'Escondido','California','USA',NULL,2010,'IMPACT')," +
		"('frc3256',3256,'WarriorBorgs','http://www.vcrobotics.net/'," +
		" 'San Jose','California','USA',NULL,2010,'Quest for Excellence')," +
		"('frc3257',3257,'Vortechs','http://www.vortechsrobotics.org'," +
		" 'Lincoln','California','USA',NULL,2010,'No Bleeding')," +
		"('frc3258',3258,'MADAWGS','http://martinsville.va.schoolwebpages.com/education/school/school.php?sectionid=4'," +
		" 'Martinsville','Virginia','USA',NULL,2010,NULL)," +
		"('frc3259',3259,'Thoroughbots','http://www.firstinspires.org/'," +
		" 'Somerset','Kentucky','USA',NULL,2010,NULL)," +
		"('frc3260',3260,'SHARP','http://sharprobotics.org'," +
		" 'Pittsburgh','Pennsylvania','USA',NULL,2010,'\"The Cutting Edge of Technology\"')," +
		"('frc3261',3261,'Bears Robotics',NULL," +
		" 'Clearbrook','MN','USA',NULL,2010,NULL)," +
		"('frc3262',3262,'Luggernauts','http://www.luggernauts3262.com'," +
		" 'Columbus','OH','USA',NULL,NULL,NULL)," +
		"('frc3263',3263,'The Elites G3',NULL," +
		" 'Coleraine','MN','USA',NULL,2010,NULL)," +
		"('frc3264',3264,'nerd nation','http://www.nerdnation.org'," +
		" 'Norwalk','OH','USA',NULL,2010,NULL)," +
		"('frc3265',3265,'Arrowheads','https://mhs3265arrowheads.wixsite.com/team3265'," +
		" 'Powder Springs','Georgia','USA',NULL,2010,'Get Robotic')," +
		"('frc3266',3266,'Robots-R-Us','http://www.robotsrus.org'," +
		" 'Eaton','Ohio','USA',NULL,2010,'Robots is our business & business is good!')," +
		"('frc3267',3267,'Mariner Robotics','http://www.firstinspires.org/'," +
		" 'Silver Bay','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3268',3268,'Vahallabots','http://valhallabots.weebly.com'," +
		" 'Kenmore','Washington','USA',NULL,2010,NULL)," +
		"('frc3270',3270,'CATA-BOTS','http://cata-bots.com'," +
		" 'Monoe','North Carolina','USA',NULL,2010,NULL)," +
		"('frc3271',3271,'Dragonbots','http://www.fthsrobotics.com'," +
		" 'Ventura','CA','USA',NULL,2010,NULL)," +
		"('frc3272',3272,'Ravage!','https://sites.google.com/site/cchsroboticsteam'," +
		" 'Carrollton','KY','USA',NULL,2010,NULL)," +
		"('frc3273',3273,'Cybercats',NULL," +
		" 'Springfield','MA','USA',NULL,2010,NULL)," +
		"('frc3274',3274,'Rocktown Robotics','http://rocktownrobotics.weebly.com/'," +
		" 'Harrisonburg','Virginia','USA',NULL,2010,'One Team, One Goal')," +
		"('frc3275',3275,'The Regulators','http://clbregulators.weebly.com/'," +
		" 'Cass Lake','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3276',3276,'TOOLCATS','http://nlsrobotics.org'," +
		" 'New London','Minnesota','USA',NULL,2010,'Wildcat Nation')," +
		"('frc3277',3277,'ProDigi','http://bit.ly/ProDigi3277'," +
		" 'Thief River Falls','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3278',3278,'QWERTY Robotics','http://qwertyrobotics.org/'," +
		" 'Detroit Lakes','Minnesota','USA',NULL,2010,'It''s Not Just About the Robot')," +
		"('frc3279',3279,'Spingarn Senior High School','http://spingarn.k12.dc.us'," +
		" 'Washington','DC','USA',NULL,2010,NULL)," +
		"('frc3280',3280,'RhodeRebels',NULL," +
		" 'Providence','RI','USA',NULL,2010,NULL)," +
		"('frc3281',3281,'VBots',NULL," +
		" 'Phoenix','AZ','USA',NULL,2010,NULL)," +
		"('frc3282',3282,'Dallas Robo Tigers','http://robotigers3282.weebly.com'," +
		" 'Dallas','Texas','USA',NULL,2010,'is better to try and fail than not try and just wonder on the what if...')," +
		"('frc3283',3283,'3283 Coyote Robotix','http://www.chsrobotix.com/'," +
		" 'Clarksburg','Maryland','USA',NULL,2010,'\"Learn from your mistakes to earn your successes\"')," +
		"('frc3284',3284,'Camdenton  LASER 3284','http://www.laser3284.org'," +
		" 'Camdenton','Missouri','USA',NULL,2010,'\"There''s no CRYING in robotics.....')," +
		"('frc3286',3286,'the rocket surgeons',NULL," +
		" 'Yakima','WA','USA',NULL,2010,NULL)," +
		"('frc3288',3288,'Punchers','http://www.firstinspires.org/'," +
		" 'Big Piney','Wyoming','USA',NULL,2010,NULL)," +
		"('frc3289',3289,'Soaring Eagle','http://3289.jdchs.org'," +
		" 'Draper','Utah','USA',NULL,2010,'Go Soaring Eagle')," +
		"('frc3290',3290,'Robo Bears LOWbotics','http://www.firstinspires.org/'," +
		" 'Baudette','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3291',3291,'Au Pirates (A.K.A Golden Pirates)','https://osseo.schoology.com/course/836821357/updates'," +
		" 'Brooklyn Park','Minnesota','USA',NULL,2010,'Have Fun Storming the Castle')," +
		"('frc3292',3292,'The Aluminum Falcon','http://www.firstinspires.org/'," +
		" 'Benson','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3293',3293,'OtterBots','http://www.firstinspires.org/'," +
		" 'Fergus Falls','Minnesota','USA',NULL,2010,'Otterbots!  Climb!')," +
		"('frc3294',3294,'Backwoods Engineers','http://www.prbschools.org/robotics'," +
		" 'Pine River','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3295',3295,'Poly Techs','http://3295Polytechs.com'," +
		" 'Mentone','California','USA',NULL,2010,' Safety first.')," +
		"('frc3296',3296,'Neon Knights','http://frc3296.org'," +
		" 'Fresno','CA','USA',NULL,2010,NULL)," +
		"('frc3297',3297,'Full Metal Jackets','http://www.perham.k12.mn.us'," +
		" 'Perham','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3298',3298,'ArrowBots','http://www.pas.k12.mn.us/page/3126'," +
		" 'Pipestone','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3299',3299,'Warehouse Crew','http://www.firstinspires.org/'," +
		" 'Chaska','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3300',3300,'Midwest WARRIORS','http://team3300.wix.com/midwestwarriors'," +
		" 'Ortonville','Minnesota','USA',NULL,2010,NULL)," +
		"('frc3301',3301,'Patriots','http://www.jaycorobo.com'," +
		" 'Portland','IN','USA',NULL,2010,NULL)," +
		"('frc3302',3302,'TurboTrojans','http://www.turbotrojans.com'," +
		" 'Clawson','Michigan','USA',NULL,2010,NULL)," +
		"('frc3303',3303,'Metallic Thunder','http://www.team3303.com'," +
		" 'Fresno','California','USA',NULL,2010,NULL)," +
		"('frc3305',3305,'Matbotz','http://www.firstinspires.org/'," +
		" 'El Paso','Texas','USA',NULL,2010,NULL)," +
		"('frc3306',3306,'Trojans',NULL," +
		" 'Pahala','HI','USA',NULL,2010,NULL)," +
		"('frc3307',3307,'Robo Dawgs','http://lhs-first.ning.com'," +
		" 'Luray','VA','USA',NULL,2010,NULL)," +
		"('frc3308',3308,'JHS Beavers','http://jhsrobotics.webs.com'," +
		" 'Jamaica','NY','USA',NULL,2010,NULL)," +
		"('frc3309',3309,'Friarbots','http://www.team3309.org'," +
		" 'Anaheim','California','USA',NULL,2010,'CREDO')," +
		"('frc3310',3310,'Black Hawk Robotics','http://www.team3310.com'," +
		" 'Heath','Texas','USA',NULL,2010,NULL)," +
		"('frc3311',3311,'KB-BOT',NULL," +
		" 'Klamath Falls','OR','USA',NULL,2010,NULL)," +
		"('frc3312',3312,'EagleBots','http://00g.teamklipz.com/eaglebots3312'," +
		" 'Bena','MN','USA',NULL,2010,NULL)," +
		"('frc3313',3313,'Mechatronics','http://www.team3313.com/'," +
		" 'Alexandria','Minnesota','USA',NULL,2010,'It''s just a prototype.')," +
		"('frc3314',3314,'Mechanical Mustangs','http://cliftonrobotics.org'," +
		" 'Clifton','New Jersey','USA',NULL,2010,NULL)," +
		"('frc3315',3315,'Mountaineers','http://www.lakeland272.org/tlhs/Industrial%20Arts/industrial_arts.htm'," +
		" 'Spirit Lake','ID','USA',NULL,2010,NULL)," +
		"('frc3316',3316,'D-Bug','http://www.team3316.com'," +
		" 'Tel Aviv','Tel-Aviv','Israel',NULL,2010,'Pass the Knowledge')," +
		"('frc3317',3317,'Q & E Robotics',NULL," +
		" 'Hollandale','MS','USA',NULL,2010,NULL)," +
		"('frc3318',3318,'RoboDragons','http://frc.gsmstengineering.com/'," +
		" 'Lawrenceville','Georgia','USA',NULL,2010,NULL)," +
		"('frc3319',3319,'Grissom Robotics ','http://www.grissomrobotics.org/'," +
		" 'Huntsville','Alabama','USA',NULL,2010,NULL)," +
		"('frc3320',3320,'Miracles & Machines (M&Ms)','http://www.eastsiderobotics.com'," +
		" 'Austin','Texas','USA',NULL,2010,NULL)," +
		"('frc3321',3321,'Superior Robotics','http://www.superiorrobotics3321.webs.com//'," +
		" 'Superior','Arizona','USA',NULL,2010,'Panther Pride Never Looked So Good')," +
		"('frc3322',3322,'Eagle Imperium','http://www.skylinerobotics.org/'," +
		" 'Ann Arbor','Michigan','USA',NULL,2010,'Veni, vidi, vici')," +
		"('frc3323',3323,'Potential Energy','http://www.pe3323.com'," +
		" 'Litchfield','New Hampshire','USA',NULL,2010,'Position is Power')," +
		"('frc3324',3324,'The Metrobots','http://www.3324metrobots.org/'," +
		" 'Columbus','Ohio','USA',NULL,2010,'Fail often in order to succeed sooner')," +
		"('frc3325',3325,'Valle Verde Robotics','http://www.wix.com/vvechs/robotics'," +
		" 'El Paso','TX','USA',NULL,2010,NULL)," +
		"('frc3326',3326,'DATC',NULL," +
		" 'Kaysville','UT','USA',NULL,2010,NULL)," +
		"('frc3328',3328,'NohoRobo','http://www.nohorobo.com'," +
		" 'North Hollywood','California','USA',NULL,2010,NULL)," +
		"('frc3329',3329,'Wildbots','https://www.camdenwildbots.org'," +
		" 'Kingsland','Georgia','USA',NULL,2010,NULL)," +
		"('frc3330',3330,'System of the Corn ','http://team3330.wixsite.com/systemofthecorn'," +
		" 'Saint Charles','Missouri','USA',NULL,2010,NULL)," +
		"('frc3331',3331,'Tar Heel Robots','http://tarheelrobots3331.web.unc.edu/'," +
		" 'Chapel Hill','North Carolina','USA',NULL,2010,'Undecided')," +
		"('frc3332',3332,'HustlerTech','http://www.hustlertech.com'," +
		" 'Melbourne','FL','USA',NULL,2010,NULL)," +
		"('frc3333',3333,'Cougars',NULL," +
		" 'Julesburg','CO','USA',NULL,2010,NULL)," +
		"('frc3334',3334,'Eagle Robotics',NULL," +
		" 'Salt Lake','UT','USA',NULL,2010,NULL)," +
		"('frc3335',3335,'Cy-Borgs','http://www.cyborgs3335.org'," +
		" 'Cypress','Texas','USA',NULL,2010,NULL)," +
		"('frc3336',3336,'Zimanators','http://www.firstinspires.org/'," +
		" 'Swansboro','North Carolina','USA',NULL,2010,NULL)," +
		"('frc3337',3337,'Panthrobotics','http://www.facebook.com/panthrobotics'," +
		" 'Baton Rouge','Louisiana','USA',NULL,2010,'Creating Robots, Designing Citizens, Building Professionals')," +
		"('frc3339',3339,'BumbleB','http://Kfaryona.wixsite.com/3339'," +
		" 'Kfar Yona','HaMerkaz','Israel',NULL,2010,'Dream. Believe. Achieve.')," +
		"('frc3340',3340,'Union City MagneGeeks','http://www.frc3340.com'," +
		" 'Union City','New Jersey','USA',NULL,2010,'The Eagle has landed')," +
		"('frc3341',3341,'Option 16','http://westviewrobotics.com'," +
		" 'San Diego','California','USA',NULL,2010,NULL)," +
		"('frc3343',3343,'HSA-Recon',NULL," +
		" 'LAREDO','TX','USA',NULL,2010,NULL)," +
		"('frc3344',3344,'Space Dragons','http://frc3344.com'," +
		" 'Fayetteville','Georgia','USA',NULL,2010,NULL)," +
		"('frc3345',3345,'Metal Jackets','Coming Soon'," +
		" 'San Antonio','Texas','USA',NULL,2010,NULL)," +
		"('frc3346',3346,'Kamikaze Comets',NULL," +
		" 'Asheboro','NC','USA',NULL,2010,NULL)," +
		"('frc3350',3350,'T-BOTS','http://www.firstinspires.org/'," +
		" 'Texarkana','Texas','USA',NULL,2010,NULL)," +
		"('frc3351',3351,'Ratchet','http://www.team3351.org'," +
		" 'Beer-Sheva','D','Israel',NULL,2010,NULL)," +
		"('frc3352',3352,'Flaming Monkeys 4-H Robotics Club','http://www.flamingmonkeys.org'," +
		" 'Belvidere','Illinois','USA',NULL,2010,'Work smarter, not harder.')," +
		"('frc3353',3353,'Freer Robotics Team','http://freerstemacademy.webs.com'," +
		" 'Freer','TX','USA',NULL,2010,NULL)," +
		"('frc3354',3354,'PrepaTec-TecDroid','http://www.tecdroid3354.com'," +
		" 'Queretaro','Querétaro','Mexico',NULL,2010,'One team, one dream, one legend.')," +
		"('frc3355',3355,'Purple Vipers','http://smil244.wix.com/team-3355'," +
		" 'Arlington','Texas','USA',NULL,2010,'Onward through the fog!')," +
		"('frc3356',3356,'MC2STEM Maniacs','http://www.mc2stemhighschool.org'," +
		" 'Cleveland','Ohio','USA',NULL,2010,NULL)," +
		"('frc3357',3357,'COMETS','http://www.foresthillsrobotics.com/'," +
		" 'Grand Rapids','Michigan','USA',NULL,2010,'Creating Outstanding Minds Embracing Technology and Science')," +
		"('frc3358',3358,'Y.T.P.T - J.E.T.S','http://www.ytpt.co.il/robotica/index.html'," +
		" 'Petach Tikva','M','Israel',NULL,2010,NULL)," +
		"('frc3359',3359,'Royal Robotics','http://www.firstinspires.org/'," +
		" 'Portsmouth','Virginia','USA',NULL,2010,'Royal Robotics, where Imagination, Innovation, and Integrity is King')," +
		"('frc3360',3360,'Hyperion','http://hyperion3360.com/'," +
		" 'Sherbrooke','Québec','Canada',NULL,2010,'Somebody told us that was impossible. So, we did it !')," +
		"('frc3361',3361,'Governators','https://governators3361.weebly.com/'," +
		" 'Fishersville','Virginia','USA',NULL,2010,'Defy, Clarify, Transmogrify')," +
		"('frc3362',3362,'Eagle Robotics',NULL," +
		" 'Milwaukee','WI','USA',NULL,2010,NULL)," +
		"('frc3363',3363,'Eagle',NULL," +
		" 'Houston','TX','USA',NULL,2010,NULL)," +
		"('frc3364',3364,'Panhandle Pirates','http://www.panhandlepirates.com'," +
		" 'Tallahassee','FL','USA',NULL,2010,NULL)," +
		"('frc3366',3366,'Plowbots','http://www.plowbots3366.com'," +
		" 'Roscoe','Texas','USA',NULL,2010,'In God we trust, all others bring data.')," +
		"('frc3367',3367,'Ogichidaa Robotics','http://www.col.pvt.k12.mn.us'," +
		" 'White Earth','Minnesota','USA',NULL,2010,'warrior')," +
		"('frc3368',3368,'Spirit of the United Neretva','http://www.roboticsteam3368sun.com'," +
		" 'Mostar','Hercegova?ko-neretvanski kanton','Bosnia-Herzegovina',NULL,2010,NULL)," +
		"('frc3369',3369,'TIGERS','http://www.hsana.org'," +
		" 'Pflugerville','TX','USA',NULL,2010,NULL)," +
		"('frc3370',3370,'Aftershock','http://jesaaftershock.weebly.com'," +
		" 'Irving','Texas','USA',NULL,2010,'Envision. Create. Deliver')," +
		"('frc3371',3371,'The Bonds ThunderBolts',NULL," +
		" 'Greer','SC','USA',NULL,2010,NULL)," +
		"('frc3373',3373,'Team RoboHawk','http://www.HighlandRobotics.org'," +
		" 'Warrenton','Virginia','USA',NULL,2010,'N/A')," +
		"('frc3374',3374,'Jackson Hole RoboBroncs','https://www.jhrobobroncs.com/'," +
		" 'Jackson','Wyoming','USA',NULL,2010,'Build me a ROBOT!')," +
		"('frc3375',3375,'North Stars','http://www.brookrobotics.ning.com'," +
		" 'Toronto','ON','Canada',NULL,2010,NULL)," +
		"('frc3376',3376,'RoboScorps',NULL," +
		" 'Satellite Beach','FL','USA',NULL,2010,NULL)," +
		"('frc3377',3377,'Raiders',NULL," +
		" 'Lumberton','TX','USA',NULL,2010,NULL)," +
		"('frc3378',3378,'OD-BS',NULL," +
		" 'Beit-Shean','Z','Israel',NULL,2010,NULL)," +
		"('frc3379',3379,'Mécani-Wolf','http://www.firstinspires.org/'," +
		" 'Montreal','Québec','Canada',NULL,2010,NULL)," +
		"('frc3380',3380,'Alchesay Falcon Team',NULL," +
		" 'Whiteriver','AZ','USA',NULL,2010,NULL)," +
		"('frc3381',3381,'Droid Rage','http://www.droidrage3381.org'," +
		" 'Valders','Wisconsin','USA',NULL,2010,'We don''t just build robots, we build people')," +
		"('frc3382',3382,'Les Béliers','http://www.firstinspires.org/'," +
		" 'Montréal','Québec','Canada',NULL,2010,NULL)," +
		"('frc3383',3383,'makif omer',NULL," +
		" 'omer','D','Israel',NULL,2010,NULL)," +
		"('frc3384',3384,'Sunset BisonBots',NULL," +
		" 'Dallas','TX','USA',NULL,2010,NULL)," +
		"('frc3385',3385,'RoboticST',NULL," +
		" 'gesher haziv','Z','Israel',NULL,2010,NULL)," +
		"('frc3386',3386,'Tornades','http://www.tornades3386.com'," +
		" 'Montreal','Québec','Canada',NULL,2010,NULL)," +
		"('frc3387',3387,'Les Aigles d''Or','http://www.firstinspires.org/'," +
		" 'Montréal','Québec','Canada',NULL,2010,NULL)," +
		"('frc3388',3388,'Flash','http://www.flash3388.com'," +
		" 'Gan Yavne','HaMerkaz','Israel',NULL,2010,'Flash: The robot that will dazzle you!')," +
		"('frc3389',3389,'TEC Tigers','https://wicomicocountyroboticsclub.weebly.com'," +
		" 'Salisbury','Maryland','USA',NULL,2010,'\"Championships are won at preseason.\"')," +
		"('frc3390',3390,'ANATOLIAN EAGLEBOTS','http://www.team3390.com/'," +
		" 'Ankara','Ankara','Turkey',NULL,2010,'We Are A Family')," +
		"('frc3392',3392,'T-Robots','http://www.t-robots.org'," +
		" 'Grand Prairie','TX','USA',NULL,2010,NULL)," +
		"('frc3393',3393,'Horns of Havoc','https://www.hornsofhavoc3393.com/'," +
		" 'Puyallup ','Washington','USA',NULL,2010,NULL)," +
		"('frc3394',3394,'Knight Riders','https://sites.google.com/site/knightridersofficial'," +
		" 'Hudson','NC','USA',NULL,2010,NULL)," +
		"('frc3396',3396,'Imperium Machinamentum','http://team3396.com/robotics'," +
		" 'Richmond Hill','ON','Canada',NULL,2010,NULL)," +
		"('frc3397',3397,'Robolions','https://www.thebluealliance.com/team/3397/history'," +
		" 'Saint Louis','Missouri','USA',NULL,2010,'If at first you don''t succeed, call it Version 1.0')," +
		"('frc3398',3398,'Crushing Crusaders','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2010,NULL)," +
		"('frc3399',3399,'Lions',NULL," +
		" 'Detroit','MI','USA',NULL,2010,NULL)," +
		"('frc3400',3400,'RCTC',NULL," +
		" 'Detroit','MI','USA',NULL,2010,NULL)," +
		"('frc3401',3401,'Eagletrons',NULL," +
		" 'Orchard Lake','MI','USA',NULL,2010,NULL)," +
		"('frc3402',3402,'ROBOMonkeys','http://www.firstinspires.org/'," +
		" 'Lenoir','North Carolina','USA',NULL,2010,NULL)," +
		"('frc3403',3403,'Cyber Wolf Robotics','http://www.vistaridgerobotics.org'," +
		" 'Colorado Springs','CO','USA',NULL,2010,NULL)," +
		"('frc3404',3404,'Robo Bibb',NULL," +
		" 'Macon','GA','USA',NULL,2010,NULL)," +
		"('frc3405',3405,'ALChemists','http://www.team3405.info'," +
		" 'Salem','Utah','USA',NULL,2010,'Work, Help, Play')," +
		"('frc3406',3406,'Ottoman RoboTecnics',NULL," +
		" 'Istanbul','34','Turkey',NULL,2010,NULL)," +
		"('frc3407',3407,'Wild Cards','http://Team3407.com'," +
		" 'Arden Hills','Minnesota','USA',NULL,2010,'help')," +
		"('frc3408',3408,'Killa-Byte Cubs','http://www.team3408.com'," +
		" 'Los Angeles','California','USA',NULL,2010,'Some assembly required.  Sanity not included.')," +
		"('frc3409',3409,'Boiling Point','http://www.chisd.net'," +
		" 'Cedar Hill','TX','USA',NULL,2010,NULL)," +
		"('frc3410',3410,'Miami MEngs','http://strongholdlife.weebly.com'," +
		" 'Miami','Florida','USA',NULL,2010,'When Lyfe granteth thee lemons, thou constructeth a robot!')," +
		"('frc3411',3411,'Team Intrepid','http://www.hornlakerobotics.org'," +
		" 'Horn Lake','MS','USA',NULL,2010,NULL)," +
		"('frc3412',3412,'The Flo-Falcons','http://www.wnecrobotics.com'," +
		" 'Wilbraham','MA','USA',NULL,2010,NULL)," +
		"('frc3413',3413,'Mad Cows','http://coppellfirstrobotics.weebly.com'," +
		" 'Coppell','TX','USA',NULL,2010,NULL)," +
		"('frc3414',3414,'Hackbots','http://www.FPSRobotics.com'," +
		" 'Farmington','Michigan','USA',NULL,2010,'Figure it Out, Make it Work')," +
		"('frc3415',3415,'The RAMS',NULL," +
		" 'Montrose','MI','USA',NULL,2010,NULL)," +
		"('frc3416',3416,'YCLA Eagles',NULL," +
		" 'Chicago','IL','USA',NULL,2010,NULL)," +
		"('frc3417',3417,'Akins Robotoics','http://www.akinseagles.org'," +
		" 'Austin','Texas','USA',NULL,2010,NULL)," +
		"('frc3418',3418,'RoboRiot','http://www.roboriotteam3418.org'," +
		" 'Sheboygan Falls','Wisconsin','USA',NULL,2010,'Overcoming diversity to become one')," +
		"('frc3419',3419,'RoHawks','https://sites.google.com/site/firstteam3419/'," +
		" 'New York','New York','USA',NULL,2010,NULL)," +
		"('frc3420',3420,'Team 3420',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc3421',3421,'Tachyon TECs','http://www.tachyontecs.org'," +
		" 'Marysville','MI','USA',NULL,2010,NULL)," +
		"('frc3422',3422,'Robo Champs',NULL," +
		" 'Detroit','MI','USA',NULL,2010,NULL)," +
		"('frc3423',3423,'The Raider',NULL," +
		" 'Farmington','MI','USA',NULL,2010,NULL)," +
		"('frc3450',3450,'Random Nuts',NULL," +
		" 'Flint','MI','USA',NULL,2011,NULL)," +
		"('frc3451',3451,'The ANOMALY','http://sanford.mainecte.org/program/pre-engineeringrobotics/'," +
		" 'Sanford','Maine','USA',NULL,2011,'Determine how strong you need it, then double it. ')," +
		"('frc3452',3452,'GreengineerZ','http://www.greengineerz.org'," +
		" 'Berrien Springs','Michigan','USA',NULL,2011,NULL)," +
		"('frc3453',3453,'DEM BOTS','https://www.facebook.com/DEMBOTS'," +
		" 'Winchester','California','USA',NULL,2011,'Ducere Discimus')," +
		"('frc3454',3454,'Z Bots','http://www.mahtomedirobotics.com'," +
		" 'Saint Paul','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3455',3455,'Robot Revolution','http://www.firstinspires.org/'," +
		" 'Alexandria','Virginia','USA',NULL,2011,NULL)," +
		"('frc3456',3456,'LiveWire','http://LiveWireRobotics.com'," +
		" 'Pocatello','ID','USA',NULL,2011,NULL)," +
		"('frc3457',3457,'MacBotics','http://www.firstinspires.org/'," +
		" 'Lawton','Oklahoma','USA',NULL,2011,'If you can''t do something smart, do something right. ')," +
		"('frc3458',3458,'Code Blue','http://team3458.com'," +
		" 'Holland','Michigan','USA',NULL,2011,'we are working on it')," +
		"('frc3459',3459,'Team PyroTech','http://www.teampyrotech.org/'," +
		" 'Cary','North Carolina','USA',NULL,2011,'The robot is the tool for outreach and inspiration')," +
		"('frc3460',3460,'Brentwood Indians',NULL," +
		" 'Brentwood','New York','USA',NULL,2011,NULL)," +
		"('frc3461',3461,'Operation PEACCE Robotics ','http://www.peacce.org'," +
		" 'Bristol','Connecticut','USA',NULL,2011,'Practicing Engineering And Competitive Cooperative Excellence')," +
		"('frc3462',3462,'FERVOT','http://fervot.tol.itesm.mx'," +
		" 'Toluca','MEX','Mexico',NULL,2011,NULL)," +
		"('frc3463',3463,'Red Neck Robotics','http://www.salina.k12.ok.us/index.php?pageID=9624_2&'," +
		" 'Salina','OK','USA',NULL,2011,NULL)," +
		"('frc3464',3464,'Sim-City','https://sites.google.com/simsburyschools.net/first-3464/'," +
		" 'Simsbury','Connecticut','USA',NULL,2011,'Geared for Success')," +
		"('frc3465',3465,'The Omega Factor','http://mustangrobotics.org'," +
		" 'Mustang','Oklahoma','USA',NULL,2011,'Having fun! Working Hard! Solving Problems!')," +
		"('frc3466',3466,'VIKINGS ROBOTICS','http://nashobatech.net/education/components/scrapbook/default.php?sectiondetailid=3462'," +
		" 'Westford','Massachusetts','USA',NULL,2011,'Respect Outreach Believe Organization Teamwork Success')," +
		"('frc3467',3467,'Windham Windup','http://www.team3467.org'," +
		" 'Windham','New Hampshire','USA',NULL,2011,NULL)," +
		"('frc3468',3468,'MAGNAtech','http://www.magnatech3468.com'," +
		" 'West Monroe','Louisiana','USA',NULL,2011,' Scientist est potentia.')," +
		"('frc3469',3469,'The Spanish Inquisition','http://asmsrobotics.com'," +
		" 'Mobile','AL','USA',NULL,2011,NULL)," +
		"('frc3470',3470,'Cyborg Zombies','http://www.cyborgzombies3470.org'," +
		" 'Temecula','California','USA',NULL,2011,'Make it, break it, fix it')," +
		"('frc3471',3471,'Jagbots','http://Jagbots.webs.com'," +
		" 'Gretna','LA','USA',NULL,2011,NULL)," +
		"('frc3472',3472,'PrepaTec - Buluk','http://buluk.org'," +
		" 'Atizapan de Zaragoza','Mexico','Mexico',NULL,2011,'Eleven minds, one spirit')," +
		"('frc3473',3473,'Team Sprocket','http://www.team3473.com'," +
		" 'Diamond Bar','California','USA',NULL,2011,NULL)," +
		"('frc3474',3474,'BAYMEN','http://www.hb-schools.us/education/components/layout/default.php?sectionid=1226&url_redirect=1'," +
		" 'Hampton Bays','NY','USA',NULL,2011,NULL)," +
		"('frc3475',3475,'The Crop?',NULL," +
		" 'Charleston','SC','USA',NULL,NULL,NULL)," +
		"('frc3476',3476,'Code Orange','http://www.teamcodeorange.com'," +
		" 'Irvine','California','USA',NULL,2011,'Intelligently Designed')," +
		"('frc3477',3477,'Chaos Vortex','http://www.chaosvortex.com'," +
		" 'Chula Vista','CA','USA',NULL,2011,NULL)," +
		"('frc3478',3478,'PrepaTec - LamBot','https://www.facebook.com/TeamLamBot3478'," +
		" 'San Luis Potosí','San Luis Potosí','Mexico',NULL,2011,'Victory Goes Beyond Winning')," +
		"('frc3479',3479,'crimson tide',NULL," +
		" 'everett','MA','USA',NULL,2011,NULL)," +
		"('frc3480',3480,'PrepaTec - ABTOMAT','http://www.abtomat.mx'," +
		" 'León','Guanajuato','Mexico',NULL,2011,'\"Robotic Passion All Night Long\".')," +
		"('frc3481',3481,'Bronc Botz ','http://www.broncbotz.org'," +
		" 'San Antonio','Texas','USA',NULL,2011,'IF gum rubber isn''t the answer, you''re asking the wrong question.')," +
		"('frc3482',3482,'Arrowbotics','http://www.team3482.com'," +
		" 'Campbell','California','USA',NULL,2011,'E Pluribus Machina')," +
		"('frc3483',3483,'Cold Smoke','http://www.bozemanrobotics.org'," +
		" 'Bozeman','MT','USA',NULL,2011,NULL)," +
		"('frc3484',3484,'Short Circuit','https://shortcircuit3484.wixsite.com/home'," +
		" 'Marysville','Ohio','USA',NULL,2011,'Anyone who has never made a mistake has never tried anything - Albert Einstein')," +
		"('frc3485',3485,'Cyclone Robotics','http://www.firstinspires.org/'," +
		" 'Kansas City','Kansas','USA',NULL,2011,'scientia ac labore - knowledge as a result of work')," +
		"('frc3486',3486,'STEM&m''s','http://www.firstinspires.org/'," +
		" 'San Diego','California','USA',NULL,2011,NULL)," +
		"('frc3487',3487,'Red Pride Robotics','http://www.team3487.com'," +
		" 'Plainfield','Indiana','USA',NULL,2011,NULL)," +
		"('frc3488',3488,'Eagle Army','http://www.firstinspires.org/'," +
		" 'Orland Park','Illinois','USA',NULL,2011,'Programmed to Win')," +
		"('frc3489',3489,'Category 5','http://www.team3489.org/'," +
		" 'North Charleston','South Carolina','USA',NULL,2011,'It''s too late to evacuate, it''s Category 5!')," +
		"('frc3490',3490,'Viper Drive','http://www.viperdrive.us'," +
		" 'Summerville','South Carolina','USA',NULL,2011,NULL)," +
		"('frc3491',3491,'FUTURE Dinos','http://none yet'," +
		" 'Holtville','California','USA',NULL,2011,'We are the past, present, & future')," +
		"('frc3492',3492,'Putnam Area Robotics Team (P.A.R.T.s)','http://parts3492.org'," +
		" 'Winfield','West Virginia','USA',NULL,2011,'We have all the right PARTs!')," +
		"('frc3493',3493,'RETRO ROBOT SQUAD',NULL," +
		" 'EDENTON','NC','USA',NULL,2011,NULL)," +
		"('frc3494',3494,'The Quadrangles','http://www.thequadrangles.com/'," +
		" 'Bloomington','Indiana','USA',NULL,2011,NULL)," +
		"('frc3495',3495,'MindCraft Robotics','https://www.centralvalleycf.org/friends-mindcraft-robotics/'," +
		" 'Fresno','California','USA',NULL,2011,'Crafting the minds of tomorrow')," +
		"('frc3496',3496,'The RoboTech Collection',NULL," +
		" 'Columbus','OH','USA',NULL,NULL,NULL)," +
		"('frc3497',3497,'Hot Bots','http://www.mcaknights.org/upper/activities/robotics'," +
		" 'Midland','TX','USA',NULL,2011,NULL)," +
		"('frc3498',3498,'Jankopotamus','http://www.firstinspires.org/'," +
		" 'Edmond','Oklahoma','USA',NULL,2011,'Failure is not  an option')," +
		"('frc3499',3499,'River''s Edge Robotics',NULL," +
		" 'Durham','New Hampshire','USA',NULL,2011,'Get back to work, stop arguing about pizza.');";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_7 = SQL_INSERT_TEAMS +
		"('frc3500',3500,'Roboteenz',NULL," +
		" 'Perris','CA','USA',NULL,2011,NULL)," +
		"('frc3501',3501,'Firebots','http://www.fremontrobotics.com'," +
		" 'Sunnyvale','California','USA',NULL,2011,NULL)," +
		"('frc3502',3502,'Octo(PI)Rates','http://octopirates.com/'," +
		" 'Tallahassee ','Florida','USA',NULL,2011,'We Put the ARR in Robots!')," +
		"('frc3503',3503,'WRX',NULL," +
		" 'Fair Oaks','CA','USA',NULL,2011,NULL)," +
		"('frc3504',3504,'Girls of Steel','http://girlsofsteelrobotics.com/'," +
		" 'Pittsburgh','Pennsylvania','USA',NULL,2011,'We Can Do It!')," +
		"('frc3505',3505,'Fighting Z-Bots','http://www.fightingzbots.org'," +
		" 'Lincoln','CA','USA',NULL,2011,NULL)," +
		"('frc3506',3506,'YETI Robotics','http://www.yetirobotics.org'," +
		" 'Charlotte','North Carolina','USA',NULL,2011,'YETI, Set, Go!')," +
		"('frc3507',3507,'Ubotics','http://ubotics.org'," +
		" 'Tulsa','Oklahoma','USA',NULL,2011,'Union Ubotics')," +
		"('frc3508',3508,'Archimedes',NULL," +
		" 'Pittsburg','TX','USA',NULL,2011,NULL)," +
		"('frc3509',3509,'Team Fallout','http://www.nilesroboticsteam.com'," +
		" 'Niles','MI','USA',NULL,2011,'How RAD are you?')," +
		"('frc3510',3510,'PrepaTec - Tectronic','http://www.wix.com/jkaz93/tectronicc'," +
		" 'Celaya','Guanajuato','Mexico',NULL,2011,NULL)," +
		"('frc3511',3511,'Road Dogs','http://www.cwctcfirst.com'," +
		" 'New Stanton','Pennsylvania','USA',NULL,2011,'\"Another Festivus Miracle\"')," +
		"('frc3512',3512,'Spartatroniks','http://www.spartatroniks.com'," +
		" 'Orcutt','California','USA',NULL,2011,'It''s Not About The Robot')," +
		"('frc3513',3513,'Misfit Toys','http://www.misfitrobotics.com'," +
		" 'Rathdrum','Idaho','USA',NULL,2011,NULL)," +
		"('frc3514',3514,'Wayne High Robotics',NULL," +
		" 'Wayne','WV','USA',NULL,2011,NULL)," +
		"('frc3515',3515,'Pneubotic Mustangs ','https://sites.google.com/site/frcteam3515'," +
		" 'Iselin','New Jersey','USA',NULL,2011,'Success always hugs you in private, failure slaps you in public!')," +
		"('frc3516',3516,'RoboChiefs','http://www.memphisfirstteams.org'," +
		" 'Memphis','Tennessee','USA',NULL,2011,NULL)," +
		"('frc3517',3517,'S.E.V.E.R.E.','http://severerobotics.org'," +
		" 'Queen Creek','AZ','USA',NULL,2011,NULL)," +
		"('frc3518',3518,'LHS Panthers','https://sites.google.com/site/lecantorobotics'," +
		" '3810 W Educational Path','FL','USA',NULL,2011,NULL)," +
		"('frc3519',3519,'Hannibal Robo Pirates',NULL," +
		" 'Hannibal','MO','USA',NULL,2011,NULL)," +
		"('frc3520',3520,'RoboColts','http://www.wix.com/team3520/therobocolts'," +
		" 'Coral Springs','FL','USA',NULL,NULL,NULL)," +
		"('frc3521',3521,'Noble Nuts','http://www.noblenuts.com'," +
		" 'Indio','CA','USA',NULL,2011,NULL)," +
		"('frc3522',3522,'CBoT´S4','http://www.cbt4.net/cbots4'," +
		" 'Toluca','Mexico','Mexico',NULL,2011,'imagina, colabora, entrega')," +
		"('frc3524',3524,'The Blizzard','http://goblizzard.org'," +
		" 'Minneapolis','MN','USA',NULL,2011,NULL)," +
		"('frc3525',3525,'The Nuts & Bolts of Fury','http://www.frc3525.com'," +
		" 'Waterbury','Connecticut','USA',NULL,2011,'Make Your Dreams Real')," +
		"('frc3526',3526,'PrepaTec - Blue Ignition','http://www.blueignition.com.mx'," +
		" 'Saltillo','Coahuila','Mexico',NULL,2011,'Are you ready to ignite?')," +
		"('frc3527',3527,'PrepaTec - Balam Esmeralda','http://tecbalam.mx'," +
		" 'Atizapan de Zaragoza','Mexico','Mexico',NULL,2011,'Triumph is a matter of instinct')," +
		"('frc3528',3528,'Up Next','http://teamupnext.com'," +
		" 'Kansas City','Missouri','USA',NULL,2011,'For HIS Glory!')," +
		"('frc3529',3529,'Kobayashi Maru','http://frcteam3529.org'," +
		" 'San Antonio','TX','USA',NULL,2011,NULL)," +
		"('frc3530',3530,'Patriotix','http://robotique-ljp.webs.com'," +
		" 'Montreal','Québec','Canada',NULL,2011,NULL)," +
		"('frc3531',3531,'2XR','http://acekillerrobot.tumblr.com'," +
		" 'Montreal','QC','Canada',NULL,2011,NULL)," +
		"('frc3532',3532,'Piranhas','http://www.firstinspires.org/'," +
		" 'Saint-Hubert','Québec','Canada',NULL,2011,'We live to learn')," +
		"('frc3533',3533,'Mekano','http://mekano3533.com'," +
		" 'Montreal','Québec','Canada',NULL,2011,'Work hard and all your dreams will come true')," +
		"('frc3534',3534,'House of Cards','http://www.team3534.com/'," +
		" 'Davison','Michigan','USA',NULL,2011,NULL)," +
		"('frc3535',3535,'Galaktech Invaders','https://www.3535.team'," +
		" 'Lapeer County','Michigan','USA',NULL,2011,'Nice, RON!')," +
		"('frc3536',3536,'Electro Eagles','http://hartlandrobotics.org'," +
		" 'Hartland','Michigan','USA',NULL,2011,'Building better humans...One robot at a time')," +
		"('frc3537',3537,'Delta Force','http://teamdeltaforce.wordpress.com'," +
		" 'Indian River','Michigan','USA',NULL,2011,'We Turn Robots On')," +
		"('frc3538',3538,'RoboJackets','http://www.team3538.com'," +
		" 'Auburn Hills','Michigan','USA',NULL,2011,'... Ship It')," +
		"('frc3539',3539,'Byting Bulldogs','http://bytingbulldogs.com'," +
		" 'Romeo','Michigan','USA',NULL,2011,'Feeding young minds - one byte at a time.')," +
		"('frc3540',3540,'Wildcats','http://wildcatrobotics.com'," +
		" 'Mooresville','NC','USA',NULL,NULL,NULL)," +
		"('frc3541',3541,'Brebotics','http://www.firstinspires.org/'," +
		" 'North York','Ontario','Canada',NULL,2011,'Robots for others')," +
		"('frc3542',3542,'S.P.E.E.D','http://www.firstinspires.org/'," +
		" 'Temperance','Michigan','USA',NULL,2011,'Students Promoting Engineering Excellence Daily')," +
		"('frc3543',3543,'C4 Robotics','http://team3543.ca/'," +
		" 'Arnprior','Ontario','Canada',NULL,2011,'We Meant To Do That! ')," +
		"('frc3544',3544,'Spartiates','http://spartiates3544.wordpress.com/'," +
		" 'Montreal-Nord','Québec','Canada',NULL,2011,NULL)," +
		"('frc3545',3545,'Bots in Blue','http://www.lacklandisd.net'," +
		" 'Jbsa Lackland','Texas','USA',NULL,2011,'Fail often to succeed sooner')," +
		"('frc3546',3546,'Buc''n''Gears','http://www.bucngears.com'," +
		" 'Grand Haven','Michigan','USA',NULL,2011,NULL)," +
		"('frc3547',3547,'VIRUS','https://www.teamvirus.org'," +
		" 'Monroe','Michigan','USA',NULL,2011,NULL)," +
		"('frc3548',3548,'RoboRavens2 ','http://www.team1188.org'," +
		" 'Royal Oak','Michigan','USA',NULL,2011,'Go Ravens!')," +
		"('frc3549',3549,'Straughn Robotics 3549',NULL," +
		" 'Andalusia','AL','USA',NULL,2011,NULL)," +
		"('frc3550',3550,'Robotronix','http://In process'," +
		" 'Lasalle','Québec','Canada',NULL,2011,NULL)," +
		"('frc3552',3552,'The T-Birds','http://www.kippsa.org'," +
		" 'San Antonio','TX','USA',NULL,2011,NULL)," +
		"('frc3553',3553,'South Philly Rambots','http://rambots7.zivtech.com'," +
		" 'Philadelphia','PA','USA',NULL,2011,NULL)," +
		"('frc3554',3554,'LC Botkickers',NULL," +
		" 'Elk Grove','CA','USA',NULL,2011,NULL)," +
		"('frc3555',3555,'Aluminati','http://www.firstinspires.org/'," +
		" 'Storrs Mansfield','Connecticut','USA',NULL,2011,NULL)," +
		"('frc3556',3556,'GET SMART','http://www.team3556.com/'," +
		" 'Lake City','Florida','USA',NULL,2011,NULL)," +
		"('frc3557',3557,'Robo-Sharks',NULL," +
		" 'Brandon','FL','USA',NULL,2011,NULL)," +
		"('frc3558',3558,'FBR Techno Tarheels',NULL," +
		" 'Washington','DC','USA',NULL,2011,NULL)," +
		"('frc3559',3559,'Thundercats','http://www.jasperrobotics.com/'," +
		" 'Jasper','Indiana','USA',NULL,2011,'Do It Right or Do It Again')," +
		"('frc3560',3560,'Chingbotics','http://www.firstinspires.org/'," +
		" 'Brampton','Ontario','Canada',NULL,2011,NULL)," +
		"('frc3561',3561,'RoboRaiders','http://www.roboraiders.org'," +
		" 'San Antonio','Texas','USA',NULL,2011,NULL)," +
		"('frc3562',3562,'LiveWire','http://www.LiveWireRobotics.com'," +
		" 'Pocatello','Idaho','USA',NULL,2011,'FIRST Things First: Building a Team. Building a Community. For all Ages.')," +
		"('frc3563',3563,'Nantyr Bot-aneers','http://nss.scdsb.on.ca'," +
		" 'Innisfil','ON','Canada',NULL,2011,NULL)," +
		"('frc3564',3564,'EGL Robotics',NULL," +
		" 'Wausau','WI','USA',NULL,2011,NULL)," +
		"('frc3565',3565,'Team T.R.E.A.D.S. (Tomorrows Robotic Engineering And Design Specialists)','http://www.connersvillerobotics.com'," +
		" 'Connersville','IN','USA',NULL,2011,NULL)," +
		"('frc3566',3566,'Gone Fishin''','http://www.team3566.org/'," +
		" 'Southborough','Massachusetts','USA',NULL,2011,'Gone Fishin')," +
		"('frc3567',3567,'The Aviators','http://www.unitedsd.net/robotics'," +
		" 'Armagh','PA','USA',NULL,2011,NULL)," +
		"('frc3568',3568,'RoboEagles','http://www.lindenrobotics.weebly.com'," +
		" 'Linden','Michigan','USA',NULL,2011,'Keep it Simple')," +
		"('frc3569',3569,'The Patrionator',NULL," +
		" 'Fresno','CA','USA',NULL,2011,NULL)," +
		"('frc3570',3570,'Pheonix 3570','http://teampheonix3570.weebly.com/'," +
		" 'Alma','Michigan','USA',NULL,2011,'Make it move')," +
		"('frc3571',3571,'Mustang Robotics','https://sites.google.com/hdsb.ca/3571/home?authuser=0'," +
		" 'Milton','Ontario','Canada',NULL,2011,NULL)," +
		"('frc3572',3572,'Wavelength','http://www.firstinspires.org/'," +
		" 'Norton Shores','Michigan','USA',NULL,2011,'Work hard, dream big, and make it happen.')," +
		"('frc3573',3573,'The Ohms','http://rmset.com/'," +
		" 'Conyers','Georgia','USA',NULL,2011,NULL)," +
		"('frc3574',3574,'HIGH TEKERZ','http://www.first3574.org'," +
		" 'Seattle','Washington','USA',NULL,2011,'Full S.T.E.A.M. Ahead')," +
		"('frc3575',3575,'Okanogan FFA ','http://www.firstinspires.org/'," +
		" 'Okanogan','Washington','USA',NULL,2011,'All things are difficult before they become easy')," +
		"('frc3576',3576,'Clover Park High School Warriors','http://www.firstinspires.org/'," +
		" 'Lakewood','Washington','USA',NULL,2011,NULL)," +
		"('frc3577',3577,'Saint''s Robotics','http://www.saints3577.com'," +
		" 'Scottsdale','Arizona','USA',NULL,2011,'Saints - All In')," +
		"('frc3578',3578,'NASA/JCPenney/FSA HS *STAR*','http://fsarobotics.com'," +
		" 'Alpharetta','GA','USA',NULL,2011,NULL)," +
		"('frc3580',3580,'Marinerds','http://www.marinerds.org'," +
		" 'Cape Coral','FL','USA',NULL,2011,NULL)," +
		"('frc3581',3581,'THINC Robotics Alliance','http://www.firstinspires.org/'," +
		" 'Lagrange','Georgia','USA',NULL,2011,'The sum is greater than the parts.')," +
		"('frc3582',3582,'Phobots',NULL," +
		" 'Mission','TX','USA',NULL,NULL,NULL)," +
		"('frc3583',3583,'Saints','http://www.sites.google.com/site/ideasanjuanrobotics'," +
		" 'San Juan','TX','USA',NULL,2011,NULL)," +
		"('frc3585',3585,'Rogue Robots of 4-H','http://www.roguerobotsof4h.org'," +
		" 'Charlestown','New Hampshire','USA',NULL,2011,'Yeah, that''ll work.')," +
		"('frc3586',3586,'Pride in the Tribe-Caveman Robotics','http://www.reardan.net'," +
		" 'Reardan','Washington','USA',NULL,2011,'Pride in the Tribe-Caveman Robotics')," +
		"('frc3587',3587,'Gorilla Gearheads',NULL," +
		" 'Davenport','WA','USA',NULL,2011,NULL)," +
		"('frc3588',3588,'the Talon','http://staff.rentonschools.us/lhs/first'," +
		" 'Renton','Washington','USA',NULL,2011,'You are the Eagle (ca-cah!)')," +
		"('frc3589',3589,'Cape Coral',NULL," +
		" 'Cape Coral','FL','USA',NULL,NULL,NULL)," +
		"('frc3590',3590,'Root Two',NULL," +
		" 'Toronto','ON','Canada',NULL,2011,NULL)," +
		"('frc3591',3591,'Wild WarBots \"WWB\"','http://wildwarbots.org'," +
		" 'Westerville','Ohio','USA',NULL,2011,NULL)," +
		"('frc3592',3592,'Sharks',NULL," +
		" 'Port Orange','FL','USA',NULL,2011,NULL)," +
		"('frc3593',3593,'Invictus','http://www.invictus3593.com'," +
		" 'Tulsa','Oklahoma','USA',NULL,2011,NULL)," +
		"('frc3594',3594,'Techtonics',NULL," +
		" 'Roanoke','VA','USA',NULL,2011,NULL)," +
		"('frc3595',3595,'Massive Attack','http://www.massiveattack3595.org'," +
		" 'Chicago','IL','USA',NULL,2011,NULL)," +
		"('frc3596',3596,'Rocket Robotics','http://www.rocketrobotics.org'," +
		" 'South Milwaukee','Wisconsin','USA',NULL,2011,'It''s Not Rocket Science, It''s Rocket Robotics!')," +
		"('frc3597',3597,'Robo-Rangers','https://team3597robotics.wordpress.com/'," +
		" 'Kittery','Maine','USA',NULL,2011,'Life begins at the end of your comfort zone')," +
		"('frc3598',3598,'SEStematic Eliminators','https://www.instagram.com/frc3598/   https://www.facebook.com/FRC3598/'," +
		" 'Sacramento','California','USA',NULL,2011,NULL)," +
		"('frc3599',3599,'Dolphin Robotics','http://dolphinrobotics.com'," +
		" 'Beaufort','SC','USA',NULL,2011,NULL)," +
		"('frc3600',3600,'Clockwork','https://www.facebook.com/FIRSTteam3600'," +
		" 'Madison','Georgia','USA',NULL,2011,'Like Clockwork')," +
		"('frc3601',3601,'Spartan Sprockets',NULL," +
		" 'Webberville','MI','USA',NULL,2011,NULL)," +
		"('frc3602',3602,'RoboMos','http://www.team3602.com'," +
		" 'Escanaba','Michigan','USA',NULL,2011,NULL)," +
		"('frc3603',3603,'Cyber Coyotes','http://cybercoyotes.weebly.com/'," +
		" 'Reed City ','Michigan','USA',NULL,2011,NULL)," +
		"('frc3604',3604,'Goon Squad','http://www.goonsquad3604.org'," +
		" 'Brownstown','Michigan','USA',NULL,2011,NULL)," +
		"('frc3605',3605,'black storm',NULL," +
		" 'dearborn','MI','USA',NULL,2011,NULL)," +
		"('frc3606',3606,'BearMetal','Coming Soon'," +
		" 'Baton Rouge','Louisiana','USA',NULL,2011,NULL)," +
		"('frc3607',3607,'TECHNOLOGIC','http://shrroboticsteam3607.wordpress.com'," +
		" 'Lambertville','NJ','USA',NULL,2011,NULL)," +
		"('frc3608',3608,'Jaguars','http://www.lisajaguars.com'," +
		" 'Sherwood','AR','USA',NULL,2011,NULL)," +
		"('frc3609',3609,'Duct Tape Dragons','http://bgcmaine.org'," +
		" 'Portland','Maine','USA',NULL,2011,'Should be fine')," +
		"('frc3610',3610,'Islanders','http://www.firstinspires.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3611',3611,'Team Tidy Cats',NULL," +
		" 'Lynn Haven','FL','USA',NULL,2011,NULL)," +
		"('frc3612',3612,'GearHogs','http://www.gearhogs.tech'," +
		" 'Springdale','Arkansas','USA',NULL,2011,NULL)," +
		"('frc3613',3613,'Spartans','http://wolfpack3613.webs.com'," +
		" 'Greece','NY','USA',NULL,2011,NULL)," +
		"('frc3614',3614,'The Love Machine',NULL," +
		" 'San Antonio','TX','USA',NULL,2011,NULL)," +
		"('frc3615',3615,'Reavers','http://reaversrobotics3615.weebly.com/'," +
		" 'Sacramento','California','USA',NULL,2011,NULL)," +
		"('frc3616',3616,'Team Phenomena ','http://www.team3616.wordpress.com'," +
		" 'Lafayette','Louisiana','USA',NULL,2011,'We came, we built, we conquered!')," +
		"('frc3617',3617,'Cold Logic','http://www.coldlogic3617.org'," +
		" 'Marquette','Michigan','USA',NULL,2011,NULL)," +
		"('frc3618',3618,'Petoskey Paladins','http://www.petoskeypaladins.org'," +
		" 'Petoskey','Michigan','USA',NULL,2011,'Engineering the Future ')," +
		"('frc3619',3619,'Blackhawks','http://www.detroitedisonpsa.org/education/district/district.php?sectionid=1'," +
		" 'Detroit','Michigan','USA',NULL,2011,'Overwhleming Brain Power!')," +
		"('frc3620',3620,'Average Joes','https://frc.stjoerobotics.com/'," +
		" 'Saint Joseph','Michigan','USA',NULL,2011,NULL)," +
		"('frc3621',3621,'Rocket Robotics','http://newhavenrocketrobotics.webs.com'," +
		" 'New Haven','MI','USA',NULL,2011,NULL)," +
		"('frc3622',3622,'r','http://%20'," +
		" 'Boca Raton','FL','USA',NULL,2011,NULL)," +
		"('frc3623',3623,'Terror Bots','http://www.firstinspires.org/'," +
		" 'Leominster','Massachusetts','USA',NULL,2011,NULL)," +
		"('frc3624',3624,'ThunderColts','http://www.TEAM3624.org'," +
		" 'Huntington Station','New York','USA',NULL,2011,NULL)," +
		"('frc3625',3625,'Some Assembly Required',NULL," +
		" 'Virginia Beach','VA','USA',NULL,2011,NULL)," +
		"('frc3626',3626,'Parish Robotics',NULL," +
		" 'Dallas','Texas','USA',NULL,2011,NULL)," +
		"('frc3627',3627,'The Jungle Robotics','http://www.junglerobotics.org'," +
		" 'Sarasota','Florida','USA',NULL,2011,NULL)," +
		"('frc3628',3628,'Scott''s Bros',NULL," +
		" 'Scottsboro','AL','USA',NULL,2011,NULL)," +
		"('frc3629',3629,'PACERobotics- Philly ALL CITY Engineers',NULL," +
		" 'Philadelphia area','PA','USA',NULL,2011,NULL)," +
		"('frc3630',3630,'Stampede','https://breckstampede.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2011,'Horsepower!')," +
		"('frc3631',3631,'The Vincents',NULL," +
		" 'Hot Springs','AR','USA',NULL,NULL,NULL)," +
		"('frc3632',3632,'D-Techs','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2011,'No limits')," +
		"('frc3633',3633,'Catalyst 3633','http://www.team3633.com'," +
		" 'Albert Lea','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3634',3634,'Spark City','http://www.sparkcityrobotics.com/'," +
		" 'Bridgeport','Connecticut','USA',NULL,2011,NULL)," +
		"('frc3635',3635,' Flying Legion ','http://www.flyinglegion3635.org'," +
		" 'Warner Robins','Georgia','USA',NULL,2011,NULL)," +
		"('frc3636',3636,'Generals','http://www.GHSrobo.com'," +
		" 'Portland','Oregon','USA',NULL,2011,NULL)," +
		"('frc3637',3637,'The Daleks','http://www.team3637.com/'," +
		" 'Flemington','New Jersey','USA',NULL,2011,'Cooperate!')," +
		"('frc3638',3638,'TigerTrons',NULL," +
		" 'Belleville','MI','USA',NULL,2011,NULL)," +
		"('frc3639',3639,'Cogs',NULL," +
		" 'Mesick','MI','USA',NULL,2011,NULL)," +
		"('frc3640',3640,'Panthers','http://portagepantherrobotics.weebly.com'," +
		" 'Portage','MI','USA',NULL,2011,NULL)," +
		"('frc3641',3641,'The Flying Toasters','http://www.theflyingtoasters.org'," +
		" 'South Lyon','Michigan','USA',NULL,2011,'To inspire and intrigue, unite and achieve greatness')," +
		"('frc3642',3642,'Huskies',NULL," +
		" 'Jackson','MN','USA',NULL,NULL,NULL)," +
		"('frc3643',3643,'Thunderbots',NULL," +
		" 'Henderson','CO','USA',NULL,2011,NULL)," +
		"('frc3644',3644,'Warriors',NULL," +
		" 'Taylorsville','UT','USA',NULL,2011,NULL)," +
		"('frc3645',3645,'Runtime Error',NULL," +
		" 'Forest Hills','NY','USA',NULL,2011,NULL)," +
		"('frc3646',3646,'INTEGRA BAHCESEHIR','http://integra3646.com/'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2011,'Yellow for a shiny future''s sun')," +
		"('frc3647',3647,'Millennium Falcons','http://www.team3647.com/'," +
		" 'San Diego','California','USA',NULL,2011,NULL)," +
		"('frc3648',3648,'Sparta Robotica','http://www.firstinspires.org/'," +
		" 'Denver','Colorado','USA',NULL,2011,NULL)," +
		"('frc3649',3649,'I-Robotics',NULL," +
		" 'Chandler','AZ','USA',NULL,NULL,NULL)," +
		"('frc3650',3650,'RoboRaptors','http://www.firstinspires.org/'," +
		" 'La Plata','Maryland','USA',NULL,2011,NULL)," +
		"('frc3651',3651,'TRIBE',NULL," +
		" 'Travelers Rest','South Carolina','USA',NULL,2011,'I know it''s here somewhere...')," +
		"('frc3652',3652,'xKRT',NULL," +
		" 'Kenton','OH','USA',NULL,2011,NULL)," +
		"('frc3653',3653,'BOTCATS','http://www.botcats3653.com'," +
		" 'Hollywood','Florida','USA',NULL,2011,'It''s bot time!')," +
		"('frc3654',3654,'TechTigers','http://techtigers3654.org'," +
		" 'Middletown','Connecticut','USA',NULL,2011,'Building the robots and women of tomorrow.')," +
		"('frc3655',3655,'Tractor Technicians','http://www.firstinspires.org/'," +
		" 'Mason','Michigan','USA',NULL,2011,NULL)," +
		"('frc3656',3656,'Dreadbots','http://dexterdreadbots.org/'," +
		" 'Dexter','Michigan','USA',NULL,2011,NULL)," +
		"('frc3657',3657,'Lincoln Cyborgs',NULL," +
		" 'Warren','MI','USA',NULL,2011,NULL)," +
		"('frc3658',3658,'Robo - Beavers','http://harpercreekrobotics.org/home/'," +
		" 'Battle Creek','Michigan','USA',NULL,2011,'\"One way to keep momentum going is to constantly have greater goals.\"')," +
		"('frc3659',3659,'Mighty Patriots','http://www.facebook.com/pages/JCPenney-and-NASA-sponsored-First-Robotics-team/315821258452194'," +
		" 'Miami','Florida','USA',NULL,2011,NULL)," +
		"('frc3660',3660,'LIGHTSABERS','http://lightsaberrobotics.org/'," +
		" 'Oklahoma City','Oklahoma','USA',NULL,2011,'We have not failed, we now know 10,000 things not to do. ')," +
		"('frc3661',3661,'RoboWolves','http://www.firstinspires.org/'," +
		" 'Wadesboro','North Carolina','USA',NULL,2011,NULL)," +
		"('frc3662',3662,'FrostByte',NULL," +
		" 'Everett','WA','USA',NULL,2011,NULL)," +
		"('frc3663',3663,'CPR - Cedar Park Robotics','http://www.cprobotics.org'," +
		" 'Bothell','Washington','USA',NULL,2011,'Bringing Robotics to Life!')," +
		"('frc3664',3664,'Lions',NULL," +
		" 'Burlington','ON','Canada',NULL,2011,NULL)," +
		"('frc3665',3665,'Titans',NULL," +
		" 'Salt Lake City','UT','USA',NULL,2011,NULL)," +
		"('frc3666',3666,'Bolton Bear Bots','http://frc3666.wix.com/3666'," +
		" 'Alexandria','Louisiana','USA',NULL,2011,'Win honor and win fame')," +
		"('frc3667',3667,'Mecanum Knights','http://www.team3667.com'," +
		" 'Port Huron','Michigan','USA',NULL,2011,'\"Figure it out\"')," +
		"('frc3668',3668,'TroBots','http://www.wlrobotics.org'," +
		" 'Whitmore Lake','Michigan','USA',NULL,2011,NULL)," +
		"('frc3669',3669,'RoboKnights','https://rcroboticsteam2016.shutterfly.com/'," +
		" 'Ripon','California','USA',NULL,2011,'Keep the knives in the freezer!')," +
		"('frc3670',3670,'Wolverines',NULL," +
		" 'Richmond Hill','NY','USA',NULL,2011,NULL)," +
		"('frc3672',3672,'Robo TORS',NULL," +
		" 'Sulphur','LA','USA',NULL,2011,NULL)," +
		"('frc3673',3673,'C.Y.B.O.R.G. Seagulls','http://team3673.org'," +
		" 'Seaside','Oregon','USA',NULL,2011,'creative young brains observing and redefining greatness')," +
		"('frc3674',3674,'CloverBots','https://www.facebook.com/CloverBots/'," +
		" 'Battle Ground','Washington','USA',NULL,2011,NULL)," +
		"('frc3675',3675,'Eagletrons','http://www.eagletrons.net'," +
		" 'Seymour','Tennessee','USA',NULL,2011,NULL)," +
		"('frc3676',3676,'Warrior Robotics','http://www.firstinspires.org/'," +
		" 'Arlington','Texas','USA',NULL,2011,NULL)," +
		"('frc3677',3677,'Don Bots',NULL," +
		" 'Pico Rivera','CA','USA',NULL,NULL,NULL)," +
		"('frc3679',3679,'rattlers','http://www.firstinspires.org/'," +
		" 'San Marcos','Texas','USA',NULL,2011,'\"We don''t make keys.\"')," +
		"('frc3680',3680,'Elemental Dragons','http://iss.schoolwires.com/Page/34829'," +
		" 'Statesville','North Carolina','USA',NULL,2011,'Elemental Dragons build STEAM')," +
		"('frc3681',3681,'Robo-Raiders','http://www.firstinspires.org/'," +
		" 'Seattle','Washington','USA',NULL,2011,NULL)," +
		"('frc3682',3682,'DATC','http://www.datc.edu'," +
		" 'Kaysville','UT','USA',NULL,2011,NULL)," +
		"('frc3683',3683,'Team DAVE','http://www.teamdave.ca'," +
		" 'Waterloo','Ontario','Canada',NULL,2011,NULL)," +
		"('frc3684',3684,'Electric Eagles','http://www.firstinspires.org/'," +
		" 'Seattle','Washington','USA',NULL,2011,NULL)," +
		"('frc3685',3685,'Searider Robotics',NULL," +
		" 'Waianae','HI','USA',NULL,2011,NULL)," +
		"('frc3686',3686,'Turkeybots',NULL," +
		" 'Memphis','TN','USA',NULL,2011,NULL)," +
		"('frc3687',3687,'The Patriots','http://www.schenectady.k12.ny.us/users/shannonk/robotics.html'," +
		" 'Schenectady','New York','USA',NULL,2011,NULL)," +
		"('frc3688',3688,'Norsemen','http://norsemenrobotics.weebly.com/'," +
		" 'Suttons Bay','Michigan','USA',NULL,2011,NULL)," +
		"('frc3690',3690,'GORT',NULL," +
		" 'st. louis park','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3691',3691,'RoboRaiders','http://www.northfieldrobotics.com/'," +
		" 'Northfield','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3692',3692,'Rock N'' Robotics','http://rocknrobotics.weebly.com'," +
		" 'Janesville','Wisconsin','USA',NULL,2011,'Rock N'' Robots')," +
		"('frc3693',3693,'GearHead Pirates','http://www.firstinspires.org/'," +
		" 'Spokane','Washington','USA',NULL,2011,NULL)," +
		"('frc3694',3694,'NAHS Warbotz','http://www.warbotz.org'," +
		" 'Atlanta','Georgia','USA',NULL,2011,'Challenge Accepted!')," +
		"('frc3695',3695,'Foximus Prime','http://yorkvillerobotics.org'," +
		" 'Yorkville','Illinois','USA',NULL,2011,'Go Get The Kids In The Corner!')," +
		"('frc3696',3696,'C.A.R.D.S','http://www.genesis.fwc.org/robotics'," +
		" 'North Richland Hills','TX','USA',NULL,2011,NULL)," +
		"('frc3697',3697,'Devil Bots',NULL," +
		" 'Mt. Pleasant','TX','USA',NULL,2011,NULL)," +
		"('frc3698',3698,'Panthers',NULL," +
		" 'Mississauga','ON','Canada',NULL,2011,NULL)," +
		"('frc3699',3699,'Panthinators','http://www.robot3699.org'," +
		" 'Spring Lake Park','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3700',3700,'The Metalling Kids','http://www.firstinspires.org/'," +
		" 'Rio Hondo','Texas','USA',NULL,2011,'When in doubt, right click!')," +
		"('frc3701',3701,'Royal Robotics','http://www.royalrobotics3701.com'," +
		" 'Gastonia','North Carolina','USA',NULL,2011,NULL)," +
		"('frc3703',3703,'MechSperts',NULL," +
		" 'Denver','CO','USA',NULL,2011,NULL)," +
		"('frc3704',3704,'Robosapiens','https://sites.google.com/site/frcteam3704/home'," +
		" 'National City','California','USA',NULL,2011,'Puedes? ')," +
		"('frc3705',3705,'Arrowbots','http://arrowbots.com/'," +
		" 'Brampton','Ontario','Canada',NULL,2011,NULL)," +
		"('frc3706',3706,'Railsplitters','http://www.railsplitterrobotics.org'," +
		" 'Lincoln Park','MI','USA',NULL,2011,NULL)," +
		"('frc3707',3707,'Brighton TechnoDogs','http://www.technodogs.org'," +
		" 'Brighton','Michigan','USA',NULL,2011,'Byte Deep into Technology')," +
		"('frc3708',3708,'Lumberjacks',NULL," +
		" 'Saginaw','MI','USA',NULL,2011,NULL)," +
		"('frc3709',3709,'RTC Megalodons','http://www.rtcblastro.webs.com/'," +
		" 'Richmond','Virginia','USA',NULL,2011,'Taking A Bite Out Of The Competition!')," +
		"('frc3710',3710,'FSS Cyber Falcons','http://www.cyberfalcons.com'," +
		" 'Kingston','Ontario','Canada',NULL,2011,NULL)," +
		"('frc3711',3711,'Iron Mustang','http://www.firstinspires.org/'," +
		" 'Trout Lake','Washington','USA',NULL,2011,'Do great things')," +
		"('frc3712',3712,'RoboCats','http://www.firstinspires.org/'," +
		" 'Union','Oregon','USA',NULL,2011,NULL)," +
		"('frc3713',3713,'War Eagle Robotics','http://orhsrobotics.clanteam.com/Index.swf'," +
		" 'Conroe','TX','USA',NULL,2011,NULL)," +
		"('frc3714',3714,'SOAR (Students of Applied Robotics)','http://www.chfsoar.org'," +
		" 'Springdale','Maryland','USA',NULL,2011,'Team work makes the dream work')," +
		"('frc3715',3715,'CRAVEN COUNTY ROBOTICS',NULL," +
		" 'NEW BERN','NC','USA',NULL,2011,NULL)," +
		"('frc3716',3716,'WARP (Wasatch Academy Robotics Program)',NULL," +
		" 'Mount Pleasant','UT','USA',NULL,2011,NULL)," +
		"('frc3717',3717,'Hitchhikers',NULL," +
		" 'San Pablo','CA','USA',NULL,2011,NULL)," +
		"('frc3718',3718,'Junkyard Battalion','http://roboticsteam3718.org/wp'," +
		" 'New Britain','Connecticut','USA',NULL,2011,'We who are about to compete salute you!')," +
		"('frc3719',3719,'STEM Whalers ','http://www.team3719.org'," +
		" 'New London ','Connecticut','USA',NULL,2011,NULL)," +
		"('frc3720',3720,'Cougears',NULL," +
		" 'Miami','FL','USA',NULL,NULL,NULL)," +
		"('frc3721',3721,'Charger Robotics','http://chargerrobotics.weebly.com/'," +
		" 'Pearl CIty','Hawaii','USA',NULL,2011,'Excellence With Honor')," +
		"('frc3722',3722,'Green Wave Robotics',NULL," +
		" 'East Grand Forks','MN','USA',NULL,2011,NULL)," +
		"('frc3723',3723,'TEKnights','http://www.firstinspires.org/'," +
		" 'Spring Valley','Minnesota','USA',NULL,2011,'Ignorance is Fatal')," +
		"('frc3724',3724,'Mansfield Robotics',NULL," +
		" 'Mansfield','OH','USA',NULL,NULL,NULL)," +
		"('frc3725',3725,'T.R.O.N.',NULL," +
		" 'Lyford','TX','USA',NULL,2011,NULL)," +
		"('frc3726',3726,'Thunder Cats',NULL," +
		" 'Splendora','TX','USA',NULL,2011,NULL)," +
		"('frc3727',3727,'Gadgeteers',NULL," +
		" 'Houston','TX','USA',NULL,2011,NULL)," +
		"('frc3728',3728,'Momentum','http://www.firstinspires.org/'," +
		" 'Houston','Texas','USA',NULL,2011,NULL)," +
		"('frc3729',3729,'The Raiders','http://rjrobotics.com'," +
		" 'Aurora','Colorado','USA',NULL,2011,'Giving back')," +
		"('frc3730',3730,'Nu-Bots',NULL," +
		" 'Dallas','TX','USA',NULL,2011,NULL)," +
		"('frc3731',3731,'MoHawk Warriors',NULL," +
		" 'houston','TX','USA',NULL,2011,NULL)," +
		"('frc3732',3732,'The Raging Tornadoes','http://www.btw-secme.webs.com'," +
		" 'Miami','FL','USA',NULL,2011,NULL)," +
		"('frc3733',3733,'Kings',NULL," +
		" 'Bowie','MD','USA',NULL,2011,NULL)," +
		"('frc3734',3734,'Caxys','http://www.team3734.com'," +
		" 'Lake Forest','Illinois','USA',NULL,2011,NULL)," +
		"('frc3735',3735,'VorTX','http://www.vortx3735.org'," +
		" 'Spring','Texas','USA',NULL,2011,'We Will Make it Work')," +
		"('frc3737',3737,'Roto-Raptors','http://rotoraptors.wix.com/team3737'," +
		" 'Goldsboro','North Carolina','USA',NULL,2011,'Be aware')," +
		"('frc3738',3738,'xx^3 xy^2',NULL," +
		" 'Yanceyville','NC','USA',NULL,2011,NULL)," +
		"('frc3739',3739,'Oakbotics','http://www.oakbotics.ca/'," +
		" 'London','Ontario','Canada',NULL,2011,'Uprooted and Rebooted')," +
		"('frc3740',3740,'Storm Robotics','http://stormrobotic.weebly.com/'," +
		" 'Sauk Rapids','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3741',3741,'CORE G IX','http://www.firstinspires.org/'," +
		" 'Rio Grande City','Texas','USA',NULL,2011,NULL)," +
		"('frc3742',3742,'Gold Hawks',NULL," +
		" 'Flushing','NY','USA',NULL,2011,NULL)," +
		"('frc3743',3743,'Eagle Bots','http://saladorobotics.org'," +
		" 'Salado','Texas','USA',NULL,2011,'never give up, never surrender')," +
		"('frc3744',3744,'robobulls',NULL," +
		" 'Durham','NC','USA',NULL,2011,NULL)," +
		"('frc3745',3745,'Governors','http://governors.spps.org/'," +
		" 'Saint Paul','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3746',3746,'Retribution',NULL," +
		" 'Huntersville','NC','USA',NULL,2011,NULL)," +
		"('frc3747',3747,'Chaotech','http://chaotech3747.webs.com'," +
		" 'Mankato','MN','USA',NULL,2011,NULL)," +
		"('frc3748',3748,'Ragnarok Robotics','http://www.ragnarokrobotics.org'," +
		" 'Ellicott City','Maryland','USA',NULL,2011,'It''s Hammer Time!')," +
		"('frc3749',3749,'Team Optix','https://www.team3749.org/'," +
		" 'San Diego','California','USA',NULL,2011,'Can you zip tie it?')," +
		"('frc3750',3750,'Gator Robotics','http://badgerrobotics3750.weebly.com/'," +
		" 'Badger','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3751',3751,'Tower View Robotics','http://NA'," +
		" 'Red Wing','Minnesota','USA',NULL,2011,'Tower of Power')," +
		"('frc3752',3752,'Roaring Robo-Panthers','http://www.firstinspires.org/'," +
		" 'Los Angeles','California','USA',NULL,2011,'Get It Done!')," +
		"('frc3753',3753,'BulahBots!','http://BulahBots.com'," +
		" 'Baton Rouge','Louisiana','USA',NULL,2011,'Got Bulah!')," +
		"('frc3754',3754,'TrekNorth First City Robotics','http://www.treknorth.org'," +
		" 'Bemidji','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3755',3755,'Dragon Robotics','http://www.firstinspires.org/'," +
		" 'Litchfield','Minnesota','USA',NULL,2011,'Adapt and Overcome')," +
		"('frc3756',3756,'RamFerno','http://ramferno.org'," +
		" 'London','Ontario','Canada',NULL,2011,'Feel the Heat')," +
		"('frc3757',3757,'Adom Ajans Robotics',NULL," +
		" 'Istanbul','34','Turkey',NULL,2011,NULL)," +
		"('frc3758',3758,'Panthers',NULL," +
		" 'Bryan','TX','USA',NULL,2011,NULL)," +
		"('frc3759',3759,'SMARTBOTS','http://www.firstinspires.org/'," +
		" 'INGLEWOOD','California','USA',NULL,2011,NULL)," +
		"('frc3760',3760,'AeroBots','http://www.firstinspires.org/'," +
		" 'Bronx','New York','USA',NULL,2011,NULL)," +
		"('frc3761',3761,'Sinton Robotics Team',NULL," +
		" 'Sinton','TX','USA',NULL,2011,NULL)," +
		"('frc3762',3762,'Spartans','http://www.staffordhsrobotics.com'," +
		" 'Stafford','TX','USA',NULL,2011,NULL)," +
		"('frc3763',3763,'4-H WildCards','http://facebook.com/4H.wildcards'," +
		" 'Jacksonville','North Carolina','USA',NULL,2011,'Say we can''t, and we will!')," +
		"('frc3764',3764,'Guardian of Greene','http://guariansofgreene.com'," +
		" 'Springfield','MO','USA',NULL,2011,NULL)," +
		"('frc3765',3765,'The TerraBots','http://www.firstinspires.org/'," +
		" 'St. Paul','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3766',3766,'ort',NULL," +
		" 'bet-shean','Z','Israel',NULL,2011,NULL)," +
		"('frc3767',3767,'Titan Robotics','http://frc3767.wordpress.com/'," +
		" 'Traverse City','Michigan','USA',NULL,2011,'Ever to Excel')," +
		"('frc3768',3768,'ASTRO DOGS','http://www.bahsastrodawgs.tk'," +
		" 'Southfield','MI','USA',NULL,NULL,NULL)," +
		"('frc3769',3769,'Droids',NULL," +
		" 'Detroit','MI','USA',NULL,2011,NULL)," +
		"('frc3770',3770,'BlitzCreek','http://www.creekrobotics.net'," +
		" 'Midland','Michigan','USA',NULL,2011,NULL)," +
		"('frc3771',3771,'Yooper Troopers',NULL," +
		" 'Dollar Bay','MI','USA',NULL,2011,NULL)," +
		"('frc3772',3772,'DITEC ARME',NULL," +
		" 'Kingsford','MI','USA',NULL,2011,NULL)," +
		"('frc3773',3773,'Cobalt Commanders','http://frc3773.com'," +
		" 'Tecumseh','Michigan','USA',NULL,2011,NULL)," +
		"('frc3774',3774,'RoboTeam',NULL," +
		" 'Ramat Hasharon','M','Israel',NULL,2011,NULL)," +
		"('frc3775',3775,'NOFARIM',NULL," +
		" 'tiberya','Z','Israel',NULL,2011,NULL)," +
		"('frc3776',3776,'3W (Woodland Wildcat What-cha-ma-call-its)','http://www.bartow.k12.ga.us/education/staff/staff.php?sectiondetailid=19420&'," +
		" 'Cartersville','GA','USA',NULL,2011,NULL)," +
		"('frc3777',3777,'Bean City Bots','http://www.firstinspires.org/'," +
		" 'Lima','Ohio','USA',NULL,2011,NULL)," +
		"('frc3778',3778,'Texsquad','http://www.texsquad.org'," +
		" 'Corpus Christi','TX','USA',NULL,2011,NULL)," +
		"('frc3779',3779,'The Hope Hoopsters',NULL," +
		" 'Chicago','IL','USA',NULL,2011,NULL)," +
		"('frc3780',3780,'Robot Unicorns','http://trufrc3780.org'," +
		" 'Providence','Rhode Island','USA',NULL,2011,'We''re a 37\"-38\" square')," +
		"('frc3781',3781,'Cardinal Robotics','http://fphsrobotics.blogspot.com'," +
		" 'Tacoma','Washington','USA',NULL,2011,'To Make the Best Better')," +
		"('frc3782',3782,'Bot-tanicals','http://www.rhsrobotics.com'," +
		" 'Panama City','FL','USA',NULL,NULL,NULL)," +
		"('frc3783',3783,'Robo Wildcats','http://memphisfirstteams.org'," +
		" 'Memphis','Tennessee','USA',NULL,2011,NULL)," +
		"('frc3784',3784,'Bit by Bit Robotics Team','http://frcteam3784.webs.com'," +
		" 'Monett','MO','USA',NULL,2011,NULL)," +
		"('frc3785',3785,'ROCK''EM SHOCK''EM ROBOTICS','https://sites.google.com/site/gilamonstersteam3785/home'," +
		" 'Yuma','Arizona','USA',NULL,2011,NULL)," +
		"('frc3786',3786,'Chargers','http://krrobotics.net'," +
		" 'Kent','Washington','USA',NULL,2011,NULL)," +
		"('frc3787',3787,'Wild Robotocats','http://www.frc3787.com'," +
		" 'Westport','Washington','USA',NULL,2011,NULL)," +
		"('frc3788',3788,'MekaMustangs',NULL," +
		" 'Northome','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3789',3789,'On Track Robotics','http://www.firstinspires.org/'," +
		" 'Spokane','Washington','USA',NULL,2011,NULL)," +
		"('frc3790',3790,'Toxic Sushi',NULL," +
		" 'Bradenton','FL','USA',NULL,NULL,NULL)," +
		"('frc3791',3791,'ATEMS',NULL," +
		" 'Abilene','TX','USA',NULL,2011,NULL)," +
		"('frc3792',3792,'The Army Ants','http://armyants.us'," +
		" 'Columbia','Missouri','USA',NULL,2011,'\"In Theory...\"')," +
		"('frc3793',3793,'CyberTitans','http://cybertitans3793.com'," +
		" 'Frederick','Maryland','USA',NULL,2011,NULL)," +
		"('frc3794',3794,'Tecmilenio WinT ','http://www.wint3794.org'," +
		" 'Metepec','Mexico','Mexico',NULL,2011,'Dreaming, Creating, Make It Possible')," +
		"('frc3795',3795,'Knight Owls',NULL," +
		" 'Garland','TX','USA',NULL,2011,NULL)," +
		"('frc3796',3796,'Technical Assassins','http://www.technicalassassinsroboticsteam.weebley.com'," +
		" 'Windsor','North Carolina','USA',NULL,2011,NULL)," +
		"('frc3797',3797,'Robo Dragons',NULL," +
		" 'Bowling Green','KY','USA',NULL,2011,NULL)," +
		"('frc3798',3798,'Rangers','http://www.wix.com/adamwalther/ranger-robotics-2012'," +
		" 'Salina','Kansas','USA',NULL,2011,NULL)," +
		"('frc3799',3799,'Electric Fire','http://www.notrdamehighschool.com/robotics'," +
		" 'Elmira','New York','USA',NULL,2011,NULL)," +
		"('frc3800',3800,'Mustangs','http://www.firstinspires.org/'," +
		" 'Kailua','Hawaii','USA',NULL,2011,NULL)," +
		"('frc3801',3801,'RAMbots 4-H Club','http://www.rambots4hclub.com'," +
		" 'Wenatchee','WA','USA',NULL,2011,NULL)," +
		"('frc3802',3802,'RoboPOP','http://www.poprobots.com'," +
		" 'Carrollton','Texas','USA',NULL,2011,'Prepare to Meet Your Maker')," +
		"('frc3803',3803,'Lightning Robotics',NULL," +
		" 'Miami','FL','USA',NULL,2011,NULL)," +
		"('frc3804',3804,'BroncoBots','http://www.bacschool.org'," +
		" 'League City','TX','USA',NULL,NULL,NULL)," +
		"('frc3805',3805,'North Spokane Yetis','http://Mt%20Spokane%20Robotics%20%28facebook%29'," +
		" 'Spokane','WA','USA',NULL,2011,NULL)," +
		"('frc3806',3806,'Tigers',NULL," +
		" 'Prescott','WA','USA',NULL,2011,NULL)," +
		"('frc3807',3807,'Overland BlazerBots','http://www.overlandrobotics.net'," +
		" 'Aurora','Colorado','USA',NULL,2011,'Leaving a Legacy Every Step We Take')," +
		"('frc3808',3808,'Phoenix Robos',NULL," +
		" 'Houston','TX','USA',NULL,2011,NULL)," +
		"('frc3809',3809,'AcRT',NULL," +
		" 'Columbia','KY','USA',NULL,NULL,NULL)," +
		"('frc3810',3810,'Pirates',NULL," +
		" 'Magna','UT','USA',NULL,2011,NULL)," +
		"('frc3811',3811,'Chargerbots','https://sites.google.com/site/jcp4hchargerrobotics'," +
		" 'Weldon','NC','USA',NULL,2011,NULL)," +
		"('frc3812',3812,'Bits & Bots','http://bitsbots3812.wixsite.com/team3812'," +
		" 'Longview','Washington','USA',NULL,2011,NULL)," +
		"('frc3813',3813,'The Three Ring Circuits',NULL," +
		" 'Puyallup','WA','USA',NULL,2011,NULL)," +
		"('frc3814',3814,'PiBotics','http://www.pibotics.info'," +
		" 'Florence','Kentucky','USA',NULL,2011,NULL)," +
		"('frc3815',3815,'NF Raiders','http://www.nfraiderrobotics.com'," +
		" 'Cumming','Georgia','USA',NULL,2011,'Metal work, Wood work, Electrical work, whatever we do, we make it work')," +
		"('frc3817',3817,'Project Viking','http://www.airlinerobotics.ezweb123.com'," +
		" 'Bossier City','LA','USA',NULL,2011,NULL)," +
		"('frc3818',3818,'Bloctopus Crime',NULL," +
		" 'Metairie','LA','USA',NULL,2011,NULL)," +
		"('frc3819',3819,'Roboticons','http://frcteamtcc_se%40yahoo.com'," +
		" 'Arlington','TX','USA',NULL,2011,NULL)," +
		"('frc3820',3820,'Robo-Raptors','http://teamroboraptors.webs.com'," +
		" 'Live Oak','TX','USA',NULL,2011,NULL)," +
		"('frc3821',3821,'Pirabots','http://belfryrobotics.webs.com/'," +
		" 'Belfry','Kentucky','USA',NULL,2011,NULL)," +
		"('frc3822',3822,'Neon Jets','http://www.firstinspires.org/'," +
		" 'Siler City','North Carolina','USA',NULL,2011,NULL)," +
		"('frc3823',3823,'TechTamers','http://%20'," +
		" 'Houston','TX','USA',NULL,2011,NULL)," +
		"('frc3824',3824,'HVA RoHAWKtics','http://rohawktics.org'," +
		" 'Knoxville','Tennessee','USA',NULL,2011,'Vision. Vigilance. Victory.')," +
		"('frc3825',3825,'Tri-Botics',NULL," +
		" 'Pasco','WA','USA',NULL,2011,NULL)," +
		"('frc3826',3826,'Sequim Robotics Federation \"SRF\" ','http://sequimroboticsfederation.weebly.com/'," +
		" 'Sequim','Washington','USA',NULL,2011,'3826 For Life')," +
		"('frc3827',3827,'Ort Hilmi Shafe',NULL," +
		" 'Acco','Z','Israel',NULL,2011,NULL)," +
		"('frc3828',3828,'12 Pretty Duckies',NULL," +
		" 'Eveleth','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3829',3829,'AmpAces',NULL," +
		" 'Soddy-Daisy','TN','USA',NULL,2011,NULL)," +
		"('frc3830',3830,'Hopkinsville/Christian County 4-H Robotics Team',NULL," +
		" 'Hopkinsville','KY','USA',NULL,NULL,NULL)," +
		"('frc3831',3831,'Da Bears','http://www.firstinspires.org/'," +
		" 'Brewster','Washington','USA',NULL,2011,NULL)," +
		"('frc3832',3832,'Muddrew''s Crew',NULL," +
		" 'Jackson','MS','USA',NULL,NULL,NULL)," +
		"('frc3833',3833,'The FIRST 3833','http://www.jrobotics3833.com'," +
		" 'Houston','TX','USA',NULL,2011,NULL)," +
		"('frc3834',3834,'Crab-bots','https://sites.google.com/site/calhouncrabbots/home'," +
		" 'Port Lavaca','Texas','USA',NULL,2011,NULL)," +
		"('frc3835',3835,'Vulcan','http://team3835.org'," +
		" 'Tel Aviv','Tel-Aviv','Israel',NULL,2011,NULL)," +
		"('frc3836',3836,'Titans',NULL," +
		" 'Gadsden','AL','USA',NULL,NULL,NULL)," +
		"('frc3837',3837,'808 Robotics','http://kekaulikerobotics.com'," +
		" 'Pukalani','HI','USA',NULL,2011,NULL)," +
		"('frc3838',3838,'Roc City Robotix','http://www.RocCityRobotix.org'," +
		" 'Rochester','New York','USA',NULL,2011,NULL)," +
		"('frc3839',3839,'SWOLVERINES','http://www.firstinspires.org/'," +
		" 'Wadena','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3840',3840,'Teens ''Nto Technology','http://team3840.org'," +
		" 'Isanti','Minnesota','USA',NULL,2011,'The Sky is the limit.')," +
		"('frc3841',3841,'Gresham Robo Rats',NULL," +
		" 'Gresham','OR','USA',NULL,2011,NULL)," +
		"('frc3842',3842,'Shock-a-Bots',NULL," +
		" 'Rochester','NY','USA',NULL,2011,NULL)," +
		"('frc3843',3843,'M.C.R.T. ROBO RACERS','http://www.firstinspires.org/'," +
		" 'Murray','Kentucky','USA',NULL,2011,'\"We''ll Talk About It\"')," +
		"('frc3844',3844,'Kentucky Wildbots','http://www.kentuckywildbots.wix.com/kentucky-wildbots'," +
		" 'Corbin','Kentucky','USA',NULL,2011,'Dream Big')," +
		"('frc3845',3845,'Yellow Jackets','http://www.firstinspires.org/'," +
		" 'Winston-Salem','North Carolina','USA',NULL,2011,NULL)," +
		"('frc3846',3846,'The First Robotic Cardinals',NULL," +
		" 'Staples','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3847',3847,'Spectrum   -△◅','http://www.spectrum3847.org/'," +
		" 'Houston','Texas','USA',NULL,2011,'Infinite Wavelengths, One Spectrum')," +
		"('frc3848',3848,'Bots in Shining Armour','http://BotShots.info'," +
		" 'Kenyon','Minnesota','USA',NULL,2011,'All for one, one for all')," +
		"('frc3849',3849,'Absolute Zero',NULL," +
		" 'San Diego','CA','USA',NULL,2010,NULL)," +
		"('frc3851',3851,'The Short Circuits ',NULL," +
		" 'Canoga Park','California','USA',NULL,2011,NULL)," +
		"('frc3853',3853,'Pridetronics','https://sites.google.com/site/3853pridetronics/'," +
		" 'Phoenix','Arizona','USA',NULL,2011,NULL)," +
		"('frc3854',3854,'Robo Warrios',NULL," +
		" 'Seminole','FL','USA',NULL,NULL,NULL)," +
		"('frc3855',3855,'The Mighty Trojans',NULL," +
		" 'Bellingham','WA','USA',NULL,2011,NULL)," +
		"('frc3856',3856,'Kingsbury Falcons',NULL," +
		" 'Memphis','Tennessee','USA',NULL,2011,NULL)," +
		"('frc3857',3857,'McKinney Masterminds',NULL," +
		" 'McKinney','TX','USA',NULL,2011,NULL)," +
		"('frc3858',3858,'MetalStorm','http://metalstormrobotics.org'," +
		" 'Greater Saint Louis','MO','USA',NULL,2011,NULL)," +
		"('frc3859',3859,'Wolfpack Robotics','http://www.wolfpackrobotics.org'," +
		" 'Elk Grove','California','USA',NULL,2011,'The strength of the wolf is the pack, and the strength of the pack is the wolf')," +
		"('frc3860',3860,'Random Genetics','http://team3860.org'," +
		" 'Sunnyside','WA','USA',NULL,2011,NULL)," +
		"('frc3861',3861,'Steel Talon','http://www.firstinspires.org/'," +
		" 'Signal Mountain','Tennessee','USA',NULL,2011,NULL)," +
		"('frc3862',3862,'Iron Scorpion 4-H Robotics',NULL," +
		" 'Farmington','MO','USA',NULL,NULL,NULL)," +
		"('frc3863',3863,'Pantherbotics','http://www.pantherbotics.org'," +
		" 'Newbury Park','California','USA',NULL,2011,'High Expectations Equals High Achievement')," +
		"('frc3864',3864,'Deus Volt',NULL," +
		" 'Paramus','NJ','USA',NULL,2011,NULL)," +
		"('frc3865',3865,'Riley WildBots','http://frcteam3865.weebly.com'," +
		" 'South Bend','Indiana','USA',NULL,2011,'Not Your Average Roboticists')," +
		"('frc3866',3866,'Blazing Eagles',NULL," +
		" 'McClellan','CA','USA',NULL,2011,NULL)," +
		"('frc3867',3867,'Timberwolves Robotics','http://spruceaoe.webs.com'," +
		" 'Dallas','TX','USA',NULL,2011,NULL)," +
		"('frc3868',3868,'Mechanized Mustangs','http://www.firstinspires.org/'," +
		" 'Paducah','Kentucky','USA',NULL,2011,NULL)," +
		"('frc3869',3869,'Tyler Lee',NULL," +
		" 'Tyler','TX','USA',NULL,2011,NULL)," +
		"('frc3870',3870,'Fighting Gobblers',NULL," +
		" 'Broadway','VA','USA',NULL,2011,NULL)," +
		"('frc3871',3871,'Trojan Robotics','http://www.firstinspires.org/'," +
		" 'Worthington','Minnesota','USA',NULL,2011,NULL)," +
		"('frc3872',3872,'Pan-Tech','https://goo.gl/nd080n'," +
		" 'Shenandoah','Virginia','USA',NULL,2011,'Never Give Up')," +
		"('frc3873',3873,'Broward Bulldogs',NULL," +
		" 'Pompano Beach','FL','USA',NULL,NULL,NULL)," +
		"('frc3874',3874,'Lightening Wave',NULL," +
		" 'Columbus','GA','USA',NULL,NULL,NULL)," +
		"('frc3875',3875,'Red Storm Robotics','http://redstormrobotics.com/'," +
		" 'Kentwood','Michigan','USA',NULL,2011,'We make FIRST happen for everyone!')," +
		"('frc3876',3876,'Mabton LugNutz','http://www.firstinspires.org/'," +
		" 'Mabton','Washington','USA',NULL,2011,'Carpe Diem')," +
		"('frc3877',3877,'DABOT',NULL," +
		" 'Madrid','MD','Spain',NULL,NULL,NULL)," +
		"('frc3878',3878,'Wildcats','http://www.firstinspires.org/'," +
		" 'Kealakekua ','Hawaii','USA',NULL,2011,NULL)," +
		"('frc3879',3879,'Charter Bots',NULL," +
		" 'Kihei','HI','USA',NULL,2011,NULL)," +
		"('frc3880',3880,'Tiki Techs','http://kealakeherobotics.org'," +
		" 'Kailua Kona','Hawaii','USA',NULL,2011,'Kulia I ka Nu''u- Strive for the summit')," +
		"('frc3881',3881,'WHEA Sharkbots','http://www.whearobotics.net'," +
		" 'Kailua Kona','Hawaii','USA',NULL,2011,'Keep It Simple!')," +
		"('frc3882',3882,'Lunas','http://lunas3882.wixsite.com/lahainalunarobotics'," +
		" 'Lahaina','Hawaii','USA',NULL,2011,'Pio''ole i ka makani Kauaula')," +
		"('frc3883',3883,'Data Bits','http://www.databits3883.com'," +
		" 'Cottage Grove','Minnesota','USA',NULL,2011,'Keep it Super Simple')," +
		"('frc3884',3884,'Spartans #2',NULL," +
		" 'Denver','CO','USA',NULL,2011,NULL)," +
		"('frc3885',3885,'Shockers',NULL," +
		" 'Bonne Terre','Missouri','USA',NULL,2011,NULL)," +
		"('frc3886',3886,'More Titans','http://www.firstinspires.org/'," +
		" 'Traverse City','Michigan','USA',NULL,2011,NULL)," +
		"('frc3888',3888,'3484',NULL," +
		" 'Marysville','Ohio','USA',NULL,2011,NULL)," +
		"('frc3889',3889,'Leptechons',NULL," +
		" 'Yuma','Arizona','USA',NULL,2011,NULL)," +
		"('frc3925',3925,'Circuit of Life','http://www.team3925.com'," +
		" 'Ventura','California','USA',NULL,2012,'Robota Matata')," +
		"('frc3926',3926,'MPArors','https://www.team3926.org'," +
		" 'Saint Paul','Minnesota','USA',NULL,2012,NULL)," +
		"('frc3927',3927,'RAW-Robotics','https://sites.google.com/site/robot3927'," +
		" 'Weymouth','MA','USA',NULL,2012,NULL)," +
		"('frc3928',3928,'Team Neutrino','http://www.teamneutrino.org'," +
		" 'Ames','Iowa','USA',NULL,2012,'Aim Higher')," +
		"('frc3929',3929,'Atomic Dragons','http://frc3929.org/'," +
		" 'Philadelphia','Pennsylvania','USA',NULL,2012,'Excellence. No Excuses.')," +
		"('frc3930',3930,'SMART  Spruce Mountain Area Robotics Team','https://www.facebook.com/SMHSrobotics'," +
		" 'Jay','Maine','USA',NULL,2012,'Theorize, Reflect, Innovate, Generate, Gravitate, Execute, Realize')," +
		"('frc3931',3931,'Cockadoodle Dominators','http://cockadoodledominators.com'," +
		" 'Chickasha','Oklahoma','USA',NULL,2012,'Hatching Dreams FIRST')," +
		"('frc3932',3932,'The Dirty Mechanics','http://dirtymechanics.org'," +
		" 'Boca Raton','Florida','USA',NULL,2012,'Build ALL the robots!')," +
		"('frc3933',3933,'PrepaTec - Taman Keet','http://www.team3933.com.mx'," +
		" 'Mexico','Distrito Federal','Mexico',NULL,2012,'Building together a path to grow')," +
		"('frc3934',3934,'Perfect Pirouettes',NULL," +
		" 'Corona','CA','USA',NULL,2012,NULL)," +
		"('frc3936',3936,'RoboBlitz','http://www.roboblitz3936.com/'," +
		" 'Michigan City','Indiana','USA',NULL,2012,'Striking Innovation')," +
		"('frc3937',3937,'Breakaway','http://www.harding.edu/3937'," +
		" 'Searcy','Arkansas','USA',NULL,2012,'Living Without Limits')," +
		"('frc3938',3938,'Radix Robotics','http://rookie-under%20development'," +
		" 'Miami','FL','USA',NULL,2012,NULL)," +
		"('frc3939',3939,'Botetourt 4-H Robotics','http://www.team3939.com'," +
		" 'Buchanan','Virginia','USA',NULL,2012,NULL)," +
		"('frc3940',3940,'CyberTooth','https://cybertooth3940.com/'," +
		" 'Kokomo','Indiana','USA',NULL,2012,NULL)," +
		"('frc3941',3941,'FRC 3941 Absolute Zero Electricity','http://absolutezeroelectricity.com/'," +
		" 'Aberdeen Proving Ground','Maryland','USA',NULL,2012,'Welcome to the Vortex')," +
		"('frc3942',3942,'Northark Pioneers',NULL," +
		" 'Harrison','AR','USA',NULL,2012,NULL)," +
		"('frc3943',3943,'Bulltronics','http://bulltronics.com'," +
		" 'Monterrey','NLE','Mexico',NULL,2012,NULL)," +
		"('frc3944',3944,'All Knights','http://www.firstinspires.org/'," +
		" 'Tempe','Arizona','USA',NULL,2012,'Merging the Socratic with the Robotic')," +
		"('frc3945',3945,'Cobratics, Inc',NULL," +
		" 'Whites Creek','TN','USA',NULL,NULL,NULL)," +
		"('frc3946',3946,'Tiger Robotics','http://www.slidellrobotics.com'," +
		" 'Slidell','Louisiana','USA',NULL,2012,'Infinite Possibilities')," +
		"('frc3947',3947,'The Last Crusaders','http://www.firstinspires.org/'," +
		" 'Knightstown','Indiana','USA',NULL,2012,'Don''t tell me it doesn''t work. Make it work.')," +
		"('frc3948',3948,'BAWSS','http://www.lhsfirstrobotics.tumblr.com'," +
		" 'Lebanon','VA','USA',NULL,2012,NULL)," +
		"('frc3949',3949,'d''Y Robotics','http://www.dyrobotics.com'," +
		" 'Brampton','Ontario','Canada',NULL,2012,NULL)," +
		"('frc3950',3950,'RoboGym Robotics','http://www.team3950.com'," +
		" 'Glen Head','New York','USA',NULL,2012,'\"Think Like an Engineer\"')," +
		"('frc3951',3951,'SUITS','http://frc.HoneoyeRobotics.org'," +
		" 'Honeoye','New York','USA',NULL,2012,NULL)," +
		"('frc3952',3952,'Troy Robotics','http://troyfrc.org'," +
		" 'Fullerton','California','USA',NULL,2012,NULL)," +
		"('frc3953',3953,'ROBATS','http://www.firstinspires.org/'," +
		" 'Eastvale','California','USA',NULL,2012,NULL)," +
		"('frc3954',3954,'4-H Electrotechs','http://4helectrotechs.com'," +
		" 'Emlenton','Pennsylvania','USA',NULL,2012,'We Came, We Built, We Learned')," +
		"('frc3955',3955,'4-H Gears','http://www.4hgears.com'," +
		" 'Greensburg','Pennsylvania','USA',NULL,2012,'Great Education Achieving Robotic Solution')," +
		"('frc3956',3956,'Itamitas',NULL," +
		" 'Mexico City','DIF','Mexico',NULL,NULL,NULL)," +
		"('frc3957',3957,'4-H Robo Rangers','http://www.firstinspires.org/'," +
		" 'New Bethlehem','Pennsylvania','USA',NULL,2012,NULL)," +
		"('frc3958',3958,'Schrodinger''s Cat','https://www.bchigh.edu/FRC3958'," +
		" 'Boston','Massachusetts','USA',NULL,2012,NULL)," +
		"('frc3959',3959,'Mech Tech','http://www.frc3959.com'," +
		" 'Somerville','Alabama','USA',NULL,2012,'Strive for perfection, achieve excellence')," +
		"('frc3960',3960,'BEast Robotics','http://beastrobotics.shutterfly.com'," +
		" 'Salt Lake City','UT','USA',NULL,2012,NULL)," +
		"('frc3961',3961,'MBA Execs','http://3961mbarobotics.weebly.com'," +
		" 'Memphis ','Tennessee','USA',NULL,2012,'One Team One Dream')," +
		"('frc3962',3962,'Incognito Robotics','http://incognitorobotics.org'," +
		" 'Kittanning','PA','USA',NULL,2012,NULL)," +
		"('frc3963',3963,'Urban Robots',NULL," +
		" 'Milwaukee','WI','USA',NULL,NULL,NULL)," +
		"('frc3964',3964,'Dolphins',NULL," +
		" 'Sanibel','FL','USA',NULL,NULL,NULL)," +
		"('frc3965',3965,'Sultans','http://TEAM3965.org'," +
		" 'Santee','California','USA',NULL,2012,'You Only Fail When You Stop Trying')," +
		"('frc3966',3966,'L&N STEMpunks','http://lnstempunks.org/'," +
		" 'Knoxville','Tennessee','USA',NULL,2012,'Get STEMpunked!')," +
		"('frc3967',3967,'Trojans Robotics',NULL," +
		" 'Chula Vista','California','USA',NULL,2012,NULL)," +
		"('frc3968',3968,'Higher Voltage','http://www.northwestrobotics.com'," +
		" 'Renton','WA','USA',NULL,2012,NULL)," +
		"('frc3969',3969,'Phoenix','http://www.firstinspires.org/'," +
		" 'Montreal','Québec','Canada',NULL,2012,NULL)," +
		"('frc3970',3970,'Duncan Dynamics','http://www.fresno.k12.ca.us/schools/s077/clubs/dynamics/'," +
		" 'Fresno','California','USA',NULL,2012,'It''s Kinda Fun to Do the Impossible ~ Walt Disney')," +
		"('frc3971',3971,'Kai Orbus','http://www.firstinspires.org/'," +
		" 'Weaverville','North Carolina','USA',NULL,2012,NULL)," +
		"('frc3972',3972,'Raider Robos',NULL," +
		" 'Kingsport','TN','USA',NULL,2012,NULL)," +
		"('frc3973',3973,'Pirate Robotics',NULL," +
		" 'Platte City','MO','USA',NULL,2012,NULL)," +
		"('frc3974',3974,'E=mCD','http://robotics.mcdevitths.org/'," +
		" 'Wyncote','Pennsylvania','USA',NULL,2012,'Safety First / GP Second')," +
		"('frc3975',3975,'Les Dragons','https://www.facebook.com/pages/ESJM-Robotique/264631943657457?ref_type=bookmark'," +
		" 'Montreal','Québec','Canada',NULL,2012,NULL)," +
		"('frc3976',3976,'Electric Hornets','http://diamondhornetsrobotics.weebly.com'," +
		" 'Hopkins','South Carolina','USA',NULL,2012,'Conceive – Believe – Achieve”')," +
		"('frc3977',3977,'Predateurs','http://www.predateurs3977.com'," +
		" 'Montreal','QC','Canada',NULL,2012,NULL)," +
		"('frc3978',3978,'Bouledogues',NULL," +
		" 'Montreal','QC','Canada',NULL,2012,NULL)," +
		"('frc3979',3979,'Solaris','http://www.ecolesaintluc.qc.ca/robotique'," +
		" 'Montreal','Québec','Canada',NULL,2012,NULL)," +
		"('frc3980',3980,'Scorpions','http://chomedeyfirst.net78.net'," +
		" 'Montreal','QC','Canada',NULL,2012,NULL)," +
		"('frc3981',3981,'Loups','http://loups3981.ca'," +
		" 'Montreal','Québec','Canada',NULL,2012,'Le Vitrail Ose Unir Persévérance et Savoir')," +
		"('frc3983',3983,'Bobcats',NULL," +
		" 'Verdun','QC','Canada',NULL,2012,NULL)," +
		"('frc3984',3984,'Topper Robotics','http://shhsrobotics.weebly.com/index.html'," +
		" 'Johnson City','Tennessee','USA',NULL,2012,NULL)," +
		"('frc3985',3985,'Sonic Howl','http://@lcchs.robotics'," +
		" 'Montreal','Québec','Canada',NULL,2012,NULL)," +
		"('frc3986',3986,'Express-O','http://www.express-o.ca'," +
		" 'Saint-Laurent','Québec','Canada',NULL,2012,'Pas de stress, on est l''Express!')," +
		"('frc3987',3987,'Les Audacieux',NULL," +
		" 'Montréal','QC','Canada',NULL,2012,NULL)," +
		"('frc3988',3988,'Scarabot','http://scarabot.weebly.com'," +
		" 'Montreal','Québec','Canada',NULL,2012,NULL)," +
		"('frc3989',3989,'Classics','http://www.classics-qc.com'," +
		" 'Montreal','QC','Canada',NULL,2012,NULL)," +
		"('frc3990',3990,'Tech for Kids','http://www.team3990.com'," +
		" 'Montréal','Québec','Canada',NULL,2012,'A vision, a motivation, an energy.')," +
		"('frc3991',3991,'KnightVision','http://3991KnightVision.com'," +
		" 'Baton Rouge','Louisiana','USA',NULL,2012,'Have Vision, Will Travel')," +
		"('frc3992',3992,'Eagles Robotics Xperience','http://www.eaglesrobotics.org'," +
		" 'Delray Beach','Florida','USA',NULL,2012,NULL)," +
		"('frc3993',3993,'Humanoids','http://team3993.weebly.com'," +
		" 'Newhall','California','USA',NULL,2012,'What doesn''t kill us makes us stronger!')," +
		"('frc3994',3994,'Goldenlions FIRST Robotics',NULL," +
		" 'Pine Bluff','AR','USA',NULL,NULL,NULL)," +
		"('frc3995',3995,'Automata',NULL," +
		" 'Redmond','OR','USA',NULL,NULL,NULL)," +
		"('frc3996',3996,'RIKITIK','http://www.rikitik.ca'," +
		" 'Rimouski','Québec','Canada',NULL,2012,'One team! One Region!')," +
		"('frc3997',3997,'Screaming Chickens','https://www.screamingchickens42.org'," +
		" 'San Antonio','Texas','USA',NULL,2012,'Our diversity is our strength!')," +
		"('frc3998',3998,'Redneck Robotics','https://www.facebook.com/barobotics/'," +
		" 'Statesboro','Georgia','USA',NULL,2012,'Kickin bots and sayin thanks')," +
		"('frc3999',3999,'Shadetree Robotics','http://www.shadetreerobotics.com'," +
		" 'Killeen','Texas','USA',NULL,2012,'We think thats a terrible idea.');";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_8 = SQL_INSERT_TEAMS +
		"('frc4000',4000,'Texsquad','http://www.texsquad.org'," +
		" 'Corpus Christi','TX','USA',NULL,2012,NULL)," +
		"('frc4001',4001,'RAMS ROBOTICS','http://www.team4001.com'," +
		" 'Thornhill','Ontario','Canada',NULL,2012,'Every piece has its place')," +
		"('frc4002',4002,'Rhombi Robotics',NULL," +
		" 'Royal Oak','MI','USA',NULL,2012,NULL)," +
		"('frc4003',4003,'TriSonics','http://www.allendalerobotics.com'," +
		" 'Allendale','Michigan','USA',NULL,2012,NULL)," +
		"('frc4004',4004,'M.A.R.S. Rovers','http://www.team4004.com'," +
		" 'Muskegon','Michigan','USA',NULL,2012,NULL)," +
		"('frc4005',4005,'Hostile Gato Robotics','http://www.firstinspires.org/'," +
		" 'Fort Gibson','Oklahoma','USA',NULL,2012,NULL)," +
		"('frc4006',4006,'CIBorgs','http://www.centralislip.k12.ny.us/webpages/robotics/'," +
		" 'Central Islip','New York','USA',NULL,2012,'CIAM (cee-I-ahm)')," +
		"('frc4007',4007,'Les Cactus','http://www.firstinspires.org/'," +
		" 'Shawinigan','Québec','Canada',NULL,2012,NULL)," +
		"('frc4008',4008,'Theodore Roosevelt''s Disco Haircut','http://team4008.wixsite.com/4008'," +
		" 'Petersburg','Indiana','USA',NULL,2012,NULL)," +
		"('frc4009',4009,'Denfeld DNA Robotics','http://denfeldrobotics4009.org'," +
		" 'Duluth','Minnesota','USA',NULL,2012,'Robotics is in our Blood!')," +
		"('frc4010',4010,'PrepaTec - Nautilus','http://www.nautilus4010.com'," +
		" 'Tlalpan','Distrito Federal','Mexico',NULL,2012,'Because we are a family')," +
		"('frc4011',4011,'πρbοtic   (pi robotics) ','http://7rrc.org/aquinas'," +
		" 'La Crosse','Wisconsin','USA',NULL,2012,NULL)," +
		"('frc4012',4012,'Bad News Bots','http://www.josephsea.org'," +
		" 'Staten Island','New York','USA',NULL,2012,'we can do it!')," +
		"('frc4013',4013,'Clockwork Mania','http://www.clockworkmania.com'," +
		" 'Orlando','Florida','USA',NULL,2012,NULL)," +
		"('frc4014',4014,'Top Hat Technicians','http://tophattechnicians.com/'," +
		" 'San Marcos','California','USA',NULL,2012,NULL)," +
		"('frc4015',4015,'Jag','http://www.firstinspires.org/'," +
		" 'Mississauga','Ontario','Canada',NULL,2012,NULL)," +
		"('frc4016',4016,'Ashebots','http://ashe-bots.org'," +
		" 'Asheville','NC','USA',NULL,2012,NULL)," +
		"('frc4017',4017,'Bulldogs',NULL," +
		" 'Lind','WA','USA',NULL,2012,NULL)," +
		"('frc4018',4018,'Knight Bots','http://www.firstinspires.org/'," +
		" 'Phoenix','Arizona','USA',NULL,2012,NULL)," +
		"('frc4019',4019,'Mechanical Paradise','https://www.team4019.com'," +
		" 'Studio City','California','USA',NULL,2012,'Imagine. Persevere. Achieve.')," +
		"('frc4020',4020,'Cyber Tribe','http://www.dbcybertribe.com/'," +
		" 'Kingsport','Tennessee','USA',NULL,2012,'Technology of the Tribe')," +
		"('frc4021',4021,'igKnightion','http://www.igKnightion.com'," +
		" 'Onalaska','Wisconsin','USA',NULL,2012,NULL)," +
		"('frc4022',4022,'Warriors',NULL," +
		" 'Waterdown','ON','Canada',NULL,2012,NULL)," +
		"('frc4023',4023,'Buff Sci-borgs','https://sites.google.com/bascs.org/buffsciborgs/home'," +
		" 'Buffalo','New York','USA',NULL,2012,NULL)," +
		"('frc4024',4024,'The Jokers','http://jokersrobotics.com'," +
		" 'Winter Park','Florida','USA',NULL,2012,'Strive for the Stars')," +
		"('frc4025',4025,'War Eagles','N/A'," +
		" 'Corryton','Tennessee','USA',NULL,2012,'N/A')," +
		"('frc4026',4026,'Global Dynamics','http://DecaturRobotics.org'," +
		" 'Decatur','Georgia','USA',NULL,2012,NULL)," +
		"('frc4027',4027,'Centre County 4-H Robotics','http://www.centre4h-robotics.org/'," +
		" 'State College','Pennsylvania','USA',NULL,2012,'#BecuzPrezDavis')," +
		"('frc4028',4028,'The Beak Squad','http://www.beaksquad.org'," +
		" 'Cincinnati','Ohio','USA',NULL,2012,NULL)," +
		"('frc4029',4029,'SpartanBots','http://team4029.net'," +
		" 'Huntington Park','CA','USA',NULL,2012,NULL)," +
		"('frc4030',4030,'NullPointerException','http://ingrahamrobotics.org/'," +
		" 'Seattle','Washington','USA',NULL,2012,'Next, We''re Building A Time Machine')," +
		"('frc4031',4031,'4-H Force Team','http://www.wix.com/skm30/4-hforcerobotics'," +
		" 'Brookville','Pennsylvania','USA',NULL,2012,'“4H Force – The mass that perpetually accelerates” ')," +
		"('frc4032',4032,'4-H Chrome Clovers','http://4hchromeclovers.com'," +
		" 'Cecil','PA','USA',NULL,2012,NULL)," +
		"('frc4034',4034,'Pelham πthons Team 4034','http://www.firstinspires.org/'," +
		" 'Pelham','New Hampshire','USA',NULL,2012,NULL)," +
		"('frc4035',4035,'Torbotix','http://ohsrobotics4035.webs.com/'," +
		" 'Orange','New Jersey','USA',NULL,2012,'Go Hard or Go Home ')," +
		"('frc4036',4036,'Short Circuit','http://swcta.net'," +
		" 'Las Vegas','NV','USA',NULL,2012,NULL)," +
		"('frc4037',4037,'Hot Springs Tronix',NULL," +
		" 'Hot Springs','AR','USA',NULL,2012,NULL)," +
		"('frc4038',4038,'Binary Robotics','http://www.binaryrobotics.com'," +
		" 'Bremerton','WA','USA',NULL,2012,NULL)," +
		"('frc4039',4039,'MakeShift Robotics','http://4039.ca'," +
		" 'Hamilton','Ontario','Canada',NULL,2012,'SHIFTing the perception of science and technology')," +
		"('frc4040',4040,'Grease Monkeys',NULL," +
		" 'Jonesboro','AR','USA',NULL,NULL,NULL)," +
		"('frc4041',4041,'Iron Tigers','http://www.firstteam4041.com'," +
		" 'Gardiner','Maine','USA',NULL,2012,NULL)," +
		"('frc4042',4042,'Murphy''s Lawyers','http://www.team4042.com'," +
		" 'South China','Maine','USA',NULL,2012,'If everything seems to be going well, you have obviously overlooked something.')," +
		"('frc4043',4043,'NerdHerd','http://www.nerdherd4043.org'," +
		" 'McMinnville','Oregon','USA',NULL,2012,'\"What ever the mind can conceive and believe it can achieve.\" - Napoleon Hill')," +
		"('frc4044',4044,'SCREAMIN'' EAGLES','http://www.ScreaminEagles.org'," +
		" 'Capac','MI','USA',NULL,NULL,NULL)," +
		"('frc4045',4045,'Toxic Drones',NULL," +
		" 'Fort Worth','Texas','USA',NULL,2012,NULL)," +
		"('frc4046',4046,'Schrodinger''s Dragons',NULL," +
		" 'Los Angeles','CA','USA',NULL,NULL,NULL)," +
		"('frc4047',4047,'Cyber Dogz','http://www.firstinspires.org/'," +
		" 'Okland','California','USA',NULL,2012,NULL)," +
		"('frc4048',4048,'Redshift','http://www.team4048.org'," +
		" 'Westborough','Massachusetts','USA',NULL,2012,'Aim high, Land softly')," +
		"('frc4049',4049,'Knoch Knights Robotics','http://www.southbutler.net/robotics/'," +
		" 'Saxonburg','Pennsylvania','USA',NULL,2012,NULL)," +
		"('frc4050',4050,'Biohazard','http://www.biohazard4050.org'," +
		" 'Chambersburg','Pennsylvania','USA',NULL,2012,'Moving Forward with Robotics')," +
		"('frc4051',4051,'Sabin-Sharks','http://Sabin-Sharks.com'," +
		" 'Portland','Oregon','USA',NULL,2012,NULL)," +
		"('frc4052',4052,'WolfTrack',NULL," +
		" 'Fort Myers','FL','USA',NULL,2012,NULL)," +
		"('frc4053',4053,'Team Ascension','Coming Soon'," +
		" 'Gonzales','Louisiana','USA',NULL,2012,NULL)," +
		"('frc4054',4054,'Robo Raiders','http://www.firstinspires.org/'," +
		" 'La Crosse','Wisconsin','USA',NULL,2012,NULL)," +
		"('frc4055',4055,'NRG','http://www.frc4055.org'," +
		" 'Winchester','Connecticut','USA',NULL,2012,'KISS (Keep It Super Simple)')," +
		"('frc4056',4056,'ElectroTitans','http://www.firstinspires.org/'," +
		" 'Colton','California','USA',NULL,2012,NULL)," +
		"('frc4057',4057,'Klamath Basin Robotics - STEAMPUNK ','http://klamathbasinrobotics.com'," +
		" 'Klamath Falls','Oregon','USA',NULL,2012,'Gearing up for a STEAM powered future')," +
		"('frc4058',4058,'BoomBots',NULL," +
		" 'Liberty','IN','USA',NULL,NULL,NULL)," +
		"('frc4059',4059,'SpareParts',NULL," +
		" 'Eastman','GA','USA',NULL,2012,NULL)," +
		"('frc4060',4060,'Bearcat Robotics','http://swag4060.org'," +
		" 'Chehalis','Washington','USA',NULL,2012,'Students with a goal.')," +
		"('frc4061',4061,'SciBorgs','http://sciborgs4061.com/'," +
		" 'Pullman','Washington','USA',NULL,2012,'4-0-6-1 Working Hard and Having Fun')," +
		"('frc4062',4062,'Électro Choc Gérald-Godin','http://www.electrochocgg.com'," +
		" 'Ste-Genevieve','QC','Canada',NULL,2012,NULL)," +
		"('frc4063',4063,'TriKzR4Kidz','http://www.facebook.com/team4063'," +
		" 'Del Rio','Texas','USA',NULL,2012,'Gearing our minds')," +
		"('frc4064',4064,'InZombiacs','http://www.InZombiacs.com'," +
		" 'Ocala','Florida','USA',NULL,2012,NULL)," +
		"('frc4065',4065,'Nerds of Prey','http://www.thenerdsofprey.com'," +
		" 'Minneola','Florida','USA',NULL,2012,'Prototypes and Pocket Protectors')," +
		"('frc4066',4066,'MechMate',NULL," +
		" 'Groveland','FL','USA',NULL,2012,NULL)," +
		"('frc4067',4067,'The Incredible Hawk','http://riverhillrobotics.org'," +
		" 'Clarksville','Maryland','USA',NULL,2012,'Soaring past the competition.')," +
		"('frc4068',4068,'Palmer Ridge BEARbotics','http://www.firstinspires.org/'," +
		" 'Monument','Colorado','USA',NULL,2012,NULL)," +
		"('frc4069',4069,'Lo-Ellen Robotics','http://www.loellenrobotics.ca'," +
		" 'Sudbury','Ontario','Canada',NULL,2012,NULL)," +
		"('frc4070',4070,'Silicon Synapse','http://siliconsynapse4070.wordpress.com'," +
		" 'Shelbyville','IN','USA',NULL,2012,NULL)," +
		"('frc4071',4071,'Diatonic Carbonites',NULL," +
		" 'Syracuse','NY','USA',NULL,2012,NULL)," +
		"('frc4072',4072,'Demon Robotics','http://www.daemonrobotics.com'," +
		" 'Glenwood Springs','CO','USA',NULL,2012,NULL)," +
		"('frc4073',4073,'Robo Katz','Coming Soon'," +
		" 'Myrtle Beach','South Carolina','USA',NULL,2012,'Go Panthers')," +
		"('frc4074',4074,'Shark Bytes','http://www.facebook.com/mobileprotection#!/pages/RoboSharks/275392299178474?fref=ts'," +
		" 'Murrells Inlet','South Carolina','USA',NULL,2012,'Beware of... Well.. Just Beware')," +
		"('frc4075',4075,'Robo Tigers','Coming Soon'," +
		" 'Conway','South Carolina','USA',NULL,2012,'Go Tigers')," +
		"('frc4076',4076,'Texan Robotics','http://www.nisdtx.org/Page/33580'," +
		" 'Justin','Texas','USA',NULL,2012,'\"Duct Tape Kings\"')," +
		"('frc4077',4077,'M*A*S*H','http://ewhsrobotics.org'," +
		" 'Edmonds','Washington','USA',NULL,2012,'Don''t hate, Create ')," +
		"('frc4078',4078,'Warriors','http://www.LPTech.org'," +
		" 'La Puente','California','USA',NULL,2012,NULL)," +
		"('frc4079',4079,'Quantum Leap','http://frc4079.org'," +
		" 'Cypress','California','USA',NULL,2012,'Take the leap')," +
		"('frc4080',4080,'Team Reboot','https://frcteam4080.wordpress.com/'," +
		" 'Tucker','Georgia','USA',NULL,2012,NULL)," +
		"('frc4081',4081,'Metal Masters',NULL," +
		" 'East Chicago','IN','USA',NULL,2012,NULL)," +
		"('frc4082',4082,'Ranching Robots','http://www.firstinspires.org/'," +
		" 'Dayton','Washington','USA',NULL,2012,NULL)," +
		"('frc4083',4083,'The Iron Wolverines','http://ironwolverines4083.weebly.com/'," +
		" 'Dorchester','South Carolina','USA',NULL,2012,'Eat. Sleep. Create. Repeat...')," +
		"('frc4084',4084,'Nuclear Launch Detected',NULL," +
		" 'Farmersville','TX','USA',NULL,NULL,NULL)," +
		"('frc4085',4085,'Technical Difficulties','http://techdiff4085.org'," +
		" 'Reynoldsburg','Ohio','USA',NULL,2012,NULL)," +
		"('frc4086',4086,'Team Tesla','https://www.facebook.com/pages/Robotics-Explorer-Post-411-Twin-Falls-Idaho/211552695559324'," +
		" 'Twin Falls','ID','USA',NULL,2012,NULL)," +
		"('frc4087',4087,'Falcon Robotics','Coming Soon'," +
		" 'New Orleans','Louisiana','USA',NULL,2012,NULL)," +
		"('frc4088',4088,'Insidious Aftershock','http://www.bgcrobotics.com'," +
		" 'Port Charlotte','FL','USA',NULL,NULL,NULL)," +
		"('frc4089',4089,'Stealth Robotics','http://www.firstinspires.org/'," +
		" 'Duvall','Washington','USA',NULL,2012,NULL)," +
		"('frc4090',4090,'Reb Teks','https://www.facebook.com/groups/997659140291121/'," +
		" 'Fort Smith','Arkansas','USA',NULL,2012,NULL)," +
		"('frc4091',4091,'DRIFT','http://teamdrift.org/thanks/'," +
		" 'Santo Domingo','Santo Domingo','Dominican Republic',NULL,2012,'Visualize innovation, think team DRIFT')," +
		"('frc4092',4092,'Jackson Robotics',NULL," +
		" 'Dutton','Alabama','USA',NULL,2012,NULL)," +
		"('frc4093',4093,'Hardwired   ','http://frc4093.com/'," +
		" 'Orleans County','New York','USA',NULL,2012,'Orleans County''s FIRST Robotics Team')," +
		"('frc4094',4094,'The Cyber Wolves','http://www.cyberwolves4094.com'," +
		" 'Angus','ON','Canada',NULL,2012,NULL)," +
		"('frc4095',4095,'Team RoXI',NULL," +
		" 'Milwaukee','WI','USA',NULL,2012,NULL)," +
		"('frc4096',4096,'Ctrl-Z','http://www.team4096.org'," +
		" 'Champaign','Illinois','USA',NULL,2012,'Ctrl-Z ''til it''s right')," +
		"('frc4097',4097,'Devilbots','http://www.nhsrobotics.weebly.com'," +
		" 'Northampton','Massachusetts','USA',NULL,2012,'[Robot Pun]')," +
		"('frc4098',4098,'Viking Robotics Team','http://www.thsfirst.com/'," +
		" 'Bristol','Tennessee','USA',NULL,2012,'Viking Pride')," +
		"('frc4099',4099,'The Falcons','https://www.team4099.com/'," +
		" 'Poolesville','Maryland','USA',NULL,2012,NULL)," +
		"('frc4101',4101,'St. Francis High School','http://www.sfhs.net/apps/pages/index.jsp?uREC_ID=117597&type=d'," +
		" '200 Foothill Bl.','CA','USA',NULL,2012,NULL)," +
		"('frc4102',4102,'Spartans','http://www.wix.com/sodarobotics/first'," +
		" 'Los Lunas','NM','USA',NULL,2012,NULL)," +
		"('frc4103',4103,'Roborioles','http://www.firstinspires.org/'," +
		" 'Avon','Indiana','USA',NULL,2012,NULL)," +
		"('frc4104',4104,'Blackhawks','http://www.firstinspires.org/'," +
		" 'Cheney','Washington','USA',NULL,2012,NULL)," +
		"('frc4105',4105,'ChiefBotsFTS',NULL," +
		" 'Moses Lake','WA','USA',NULL,2012,NULL)," +
		"('frc4106',4106,'Bots of Prey','http://www.botsofpreyfrc.org'," +
		" 'Nampa','ID','USA',NULL,2012,NULL)," +
		"('frc4107',4107,'Team Storm','http://www.teamstorm4107.org'," +
		" 'Gulfport','Mississippi','USA',NULL,2012,'We CARE')," +
		"('frc4108',4108,'Randolph Robotics','http://www.randolphrobotics4108.com'," +
		" 'New York','NY','USA',NULL,2012,NULL)," +
		"('frc4109',4109,'Reynolds Robo Raiders',NULL," +
		" 'Troutdale','OR','USA',NULL,NULL,NULL)," +
		"('frc4110',4110,'DEEP SPACE NINERS','http://www.bhhsrobotics4110.com/'," +
		" 'Brookings','Oregon','USA',NULL,2012,'Design, Build, Program and Conquer')," +
		"('frc4111',4111,'Some Assembly Required','http://asupreprobotics.weebly.com/'," +
		" 'Mesa','Arizona','USA',NULL,2012,'Some Assembly Required')," +
		"('frc4112',4112,'EagleBots','http://www.firstinspires.org/'," +
		" 'Cumming ','Georgia','USA',NULL,2012,'Safety, Efficiency, Attitude')," +
		"('frc4113',4113,'IHS Robotics',NULL," +
		" 'New Orleans','LA','USA',NULL,NULL,NULL)," +
		"('frc4114',4114,'Pallindrones ','http://www.pallindrones.com'," +
		" 'Hemet','California','USA',NULL,2012,'Robotic domination no matter how you look at it')," +
		"('frc4115',4115,'Team Breaking Lights','http://cmrobotics.org'," +
		" 'Clarksville','Ohio','USA',NULL,2012,NULL)," +
		"('frc4116',4116,'SCC',NULL," +
		" 'Houston','TX','USA',NULL,NULL,NULL)," +
		"('frc4117',4117,'Wired Up',NULL," +
		" 'San Diego','CA','USA',NULL,2012,NULL)," +
		"('frc4118',4118,'Roaring Riptide','http://www.roaringriptide.com'," +
		" 'Gainesville','Florida','USA',NULL,2012,NULL)," +
		"('frc4119',4119,'AERO (Avon Eagles Robotics Organization)','http://www.firstinspires.org/'," +
		" 'Avon','Ohio','USA',NULL,2012,NULL)," +
		"('frc4120',4120,'Jagwires','http://www.firstinspires.org/'," +
		" 'Spokane','Washington','USA',NULL,2012,'Getting our bot in gear!')," +
		"('frc4121',4121,'Viking  Robotics','https://sites.google.com/view/team4121'," +
		" 'North Canton','Ohio','USA',NULL,2012,NULL)," +
		"('frc4122',4122,'Ossining O-Bots','http://www.firstinspires.org/'," +
		" 'Ossining','New York','USA',NULL,2012,'Go Figure it Out!')," +
		"('frc4123',4123,'Tribe Robotics','http://frc4123.com'," +
		" 'Bellflower','California','USA',NULL,2012,'Student built. Mentor approved. Fear the gears!')," +
		"('frc4124',4124,'Integration By Parts',NULL," +
		" 'Massena','NY','USA',NULL,2012,NULL)," +
		"('frc4125',4125,'Confidential','http://www.umatillarobotics.org'," +
		" 'Umatilla','Oregon','USA',NULL,2012,'Be the Solution')," +
		"('frc4126',4126,'Decepta-Kings','http://www.youtube.com/channel/UCOmbrrxY0H4cu46tl5Qj-tQ?feature=watch'," +
		" 'Yuma','Arizona','USA',NULL,2012,NULL)," +
		"('frc4127',4127,'LoggerBots','http://www.facebook.com/loggerbotsteam4127'," +
		" 'Vernonia','Oregon','USA',NULL,2012,'Keep it simple')," +
		"('frc4128',4128,'TankBots','http://facebook.com/team4128'," +
		" 'Holmdel','NJ','USA',NULL,2012,NULL)," +
		"('frc4129',4129,'Global Citizenship Experience High School','https://sites.google.com/a/gcechicago.com/gce-frc-robotics'," +
		" 'Chicago','IL','USA',NULL,2012,NULL)," +
		"('frc4130',4130,'The Blue Devils','http://www.bluedevils4130.org'," +
		" 'Richmond','Michigan','USA',NULL,2012,'DEVILS Demonstrating Educational Values by Inspiring students to Love Stem')," +
		"('frc4131',4131,'Iron Patriots','htpps://www.frc4131.org'," +
		" 'Renton','Washington','USA',NULL,2012,NULL)," +
		"('frc4132',4132,'Scotbots','http://www.firstinspires.org/'," +
		" 'Portland','Oregon','USA',NULL,2012,'A quality product, professionally done and delivered on time')," +
		"('frc4133',4133,'S2-Boltz',NULL," +
		" '6406 E Chelsea St','FL','USA',NULL,2012,NULL)," +
		"('frc4134',4134,'RoboRams',NULL," +
		" 'Amsterdam','NY','USA',NULL,2012,NULL)," +
		"('frc4135',4135,'Iron Patriots','http://www.beyerrobotics.org'," +
		" 'Modesto','California','USA',NULL,2012,'Aspire Higher')," +
		"('frc4136',4136,'Vulcans','http://turtles.newbeginningsnola.net'," +
		" 'new orleans','LA','USA',NULL,2012,NULL)," +
		"('frc4137',4137,'Wilde Bunch','http://www.wildelakerobotics.weebly.com'," +
		" 'Columbia','Maryland','USA',NULL,2012,'Drive on the Wilde Side')," +
		"('frc4138',4138,'Arc Flash',NULL," +
		" 'Wichita Falls','TX','USA',NULL,2012,NULL)," +
		"('frc4139',4139,'Easy as Pi','http://www.firstinspires.org/'," +
		" 'San Diego','California','USA',NULL,2012,NULL)," +
		"('frc4140',4140,'WIRED',NULL," +
		" 'Chino','California','USA',NULL,2012,NULL)," +
		"('frc4141',4141,'Monarch Robotics','http://MDHSrobotics.com'," +
		" 'Santa Ana','California','USA',NULL,2012,NULL)," +
		"('frc4142',4142,'Shorebots','http://sites.google.com/site/alhsshorebots'," +
		" 'Avon Lake','OH','USA',NULL,2012,NULL)," +
		"('frc4143',4143,'MARS/ WARS','http://www.MarsWars.org'," +
		" 'Metamora','Illinois','USA',NULL,2012,'Acta non Verba')," +
		"('frc4144',4144,'Semi con-duck-tors',NULL," +
		" 'Inglewood','CA','USA',NULL,2012,NULL)," +
		"('frc4145',4145,'WorBots','http://worbots4145.org'," +
		" 'Columbus','Ohio','USA',NULL,2012,'Innovate Your Life')," +
		"('frc4146',4146,'Sabercats','http://sabercatrobotics.com'," +
		" 'Scottsdale','Arizona','USA',NULL,2012,NULL)," +
		"('frc4147',4147,'Vikings',NULL," +
		" 'Yarmouth','NS','Canada',NULL,2012,NULL)," +
		"('frc4148',4148,'The SWAGbots','http://www.wix.com/swagbots4/4148'," +
		" 'Atlanta','GA','USA',NULL,2012,'Think it, Build it, SWAG it!')," +
		"('frc4149',4149,'The GLAMbots','http://www.wix.com/ztweetybird14/glambots2012'," +
		" 'Atlanta','GA','USA',NULL,2012,'Beauty and Brains')," +
		"('frc4150',4150,'FRobotics','http://www.frobotics.org'," +
		" 'Murrysville','Pennsylvania','USA',NULL,2012,'Together we make things better')," +
		"('frc4151',4151,'SCRAP 4151','http://SCRAP4151.COM'," +
		" 'Weymouth','Massachusetts','USA',NULL,2012,NULL)," +
		"('frc4152',4152,'Hoya Robotics','https://hoyarobotics.wixsite.com/team4152-hoya'," +
		" 'Huntsville','Ontario','Canada',NULL,2012,NULL)," +
		"('frc4153',4153,'Project Y','http://www.team4153.org'," +
		" 'Los Alamos','New Mexico','USA',NULL,2012,'Why?  Why not?')," +
		"('frc4154',4154,'Perpetual Recursion','http://www.capitolcityrobotics.org'," +
		" 'Jefferson City','Missouri','USA',NULL,2012,NULL)," +
		"('frc4155',4155,'SHARC','http://www.team4155.tk/'," +
		" 'Houston','Texas','USA',NULL,2012,NULL)," +
		"('frc4156',4156,'Ninjaneers','http://frc4156.com'," +
		" 'Springfield','Illinois','USA',NULL,2012,NULL)," +
		"('frc4157',4157,'Imaginarium',NULL," +
		" 'Fort Myers','FL','USA',NULL,2012,NULL)," +
		"('frc4158',4158,'Leilehua Robotics','www.leilehuarobotics.com'," +
		" 'Wahiawa','Hawaii','USA',NULL,2012,NULL)," +
		"('frc4159',4159,'CardinalBotics','http://www.team4159.org'," +
		" 'San Francisco','California','USA',NULL,2012,NULL)," +
		"('frc4160',4160,'The RoBucs','https://www.team4160.com/'," +
		" 'San Diego','California','USA',NULL,2012,NULL)," +
		"('frc4161',4161,'T-Bird TECH','http://tbirdtech.wixsite.com/tbirdtech'," +
		" 'Yucaipa','California','USA',NULL,2012,'Let''s have fun, fall or fly\"')," +
		"('frc4162',4162,'A3 BigBots',NULL," +
		" 'New Braunfels','TX','USA',NULL,2012,NULL)," +
		"('frc4163',4163,'Westlake Robotics','http://Westlake%20High%20school.org'," +
		" 'Atlanta','GA','USA',NULL,2012,NULL)," +
		"('frc4164',4164,'Rocket Robotics',NULL," +
		" 'Castle Rock','WA','USA',NULL,2012,NULL)," +
		"('frc4165',4165,'Appalachian Automation ',NULL," +
		" 'LOGAN','Ohio','USA',NULL,2012,NULL)," +
		"('frc4166',4166,'Robostang','http://www.firstinspires.org/'," +
		" 'Mora','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4167',4167,'Aquilon',NULL," +
		" 'Quebec','Québec','Canada',NULL,2012,NULL)," +
		"('frc4169',4169,'Warrior Robotics','http://www.firstinspires.org/'," +
		" 'Sudbury','Massachusetts','USA',NULL,2012,NULL)," +
		"('frc4170',4170,'What The FRaC',NULL," +
		" 'Rialto','CA','USA',NULL,2012,NULL)," +
		"('frc4171',4171,'BayBots','http://www.shsbaybots.weebly.com'," +
		" 'Seaside','California','USA',NULL,2012,NULL)," +
		"('frc4172',4172,'Buyukcekmece Robotic',NULL," +
		" 'Istanbul','34','Turkey',NULL,NULL,NULL)," +
		"('frc4173',4173,' IMVERT (Interscholastic Mount Vernon Engineering Robotics Team)','http://www.imvert.net'," +
		" 'Mount Vernon','Washington','USA',NULL,2012,'Robots, Science?  Hey it''s what we do!')," +
		"('frc4174',4174,'Mustangs','http://www.blhsd.org/vnews/display.v/SEC/High%20School%7CActivities%3E%3ERobotics'," +
		" 'Hector','Minnesota','USA',NULL,2012,'Mustangs')," +
		"('frc4175',4175,'Coded Summit','http://roboticsteam4175teton.squarespace.com/'," +
		" 'Driggs','Idaho','USA',NULL,2012,NULL)," +
		"('frc4176',4176,'Iron Tigers','http://eastonrobotics.org'," +
		" 'North Easton','Massachusetts','USA',NULL,2012,NULL)," +
		"('frc4177',4177,'Bulldawgs',NULL," +
		" 'Monroe','GA','USA',NULL,2012,NULL)," +
		"('frc4178',4178,'MC Rocs','http://www.Mcrocs4178.wix.com/mcrocs'," +
		" 'Burley','ID','USA',NULL,2012,NULL)," +
		"('frc4179',4179,'Tahisco Techs','http://www.tahiscotechs.com'," +
		" 'Cantonment','Florida','USA',NULL,2012,NULL)," +
		"('frc4180',4180,'Iron Riders','http://IronRiders.org'," +
		" 'Seattle','Washington','USA',NULL,2012,NULL)," +
		"('frc4181',4181,'Quack Attack','http://www.firstinspires.org/'," +
		" 'Blackduck','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4182',4182,'Viking Robotics','http://www.firstinspires.org/'," +
		" 'Minneota','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4183',4183,'Bit Buckets','http://www.bitbuckets.org'," +
		" 'Tucson','Arizona','USA',NULL,2012,NULL)," +
		"('frc4184',4184,'Wreck-techs',NULL," +
		" 'Naples','FL','USA',NULL,2012,NULL)," +
		"('frc4185',4185,'Cyber Foxes','http://www.facebook.com/FhsRoboticsClub'," +
		" 'Fairfield','CA','USA',NULL,2012,NULL)," +
		"('frc4186',4186,'Alameda Aztechs','http://islandcityrobotics.com/index.html'," +
		" 'Alameda','California','USA',NULL,2012,NULL)," +
		"('frc4187',4187,'RoboRams','http://www.facebook.com/4187ROBORAMS'," +
		" 'Mount Vernon','Illinois','USA',NULL,2012,'The Sky is not the Limit')," +
		"('frc4188',4188,'Columbus Space Program','http://columbusspaceprogram.weebly.com/'," +
		" 'Columbus','Georgia','USA',NULL,2012,NULL)," +
		"('frc4189',4189,'Chargers','http://www.team4189.org'," +
		" 'Jefferson','Georgia','USA',NULL,2012,'Learning by doing')," +
		"('frc4190',4190,'Hephaestus',NULL," +
		" 'Port Richey','FL','USA',NULL,2012,NULL)," +
		"('frc4191',4191,'IMC','http://imc.tevitol.org'," +
		" 'Kocaeli','Kocaeli','Turkey',NULL,2012,'Once a member, always a member!')," +
		"('frc4192',4192,'Jaguar Robotics','http://www.fmhsrobotics.com'," +
		" 'Flower Mound','Texas','USA',NULL,2012,'To Build. To Conquer')," +
		"('frc4193',4193,'Raptors','http://www.firstinspires.org/'," +
		" 'Austell','Georgia','USA',NULL,2012,NULL)," +
		"('frc4195',4195,'Genesis','http://www.bgcnwga.org'," +
		" 'Rome','Georgia','USA',NULL,2012,'\"Focus on the promise, not the problem\"')," +
		"('frc4196',4196,'QAVTC Devils','qavtcdevils.com'," +
		" 'Quincy','IL','USA',NULL,2012,NULL)," +
		"('frc4197',4197,'Lacoochee Cowboys',NULL," +
		" 'Dade City','FL','USA',NULL,2012,NULL)," +
		"('frc4198',4198,'RoboCats','http://www.firstinspires.org/'," +
		" 'Waconia','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4199',4199,'OG',NULL," +
		" 'hattiesburg','MS','USA',NULL,2012,NULL)," +
		"('frc4200',4200,'Power Penguins',NULL," +
		" 'Sarnia','ON','Canada',NULL,2012,NULL)," +
		"('frc4201',4201,'The Vitruvian Bots','https://team4201.weebly.com/'," +
		" 'El Segundo','California','USA',NULL,2012,'More Than Robots')," +
		"('frc4202',4202,'ROBOBEAR','http://www.frcteam4202.com'," +
		" 'Scottsdale','AZ','USA',NULL,2012,NULL)," +
		"('frc4203',4203,'RoboKronos','http://www.robokronos.org'," +
		" 'Oneonta','New York','USA',NULL,2012,'Titans of Technology')," +
		"('frc4205',4205,'ROBOCUBS','http://www.firstinspires.org/'," +
		" 'Sedro Woolley','Washington','USA',NULL,2012,NULL)," +
		"('frc4206',4206,'Robo Vikes','http://www.team4206.com'," +
		" 'Fort Worth','Texas','USA',NULL,2012,'Esto Machinator')," +
		"('frc4207',4207,'PyroBotics','http://robotics.hfchs.org/robotics.php'," +
		" 'Victoria','Minnesota','USA',NULL,2012,'Do it right the first time. ')," +
		"('frc4208',4208,'AC PATRIOTS',NULL," +
		" 'Hastings','NE','USA',NULL,2012,NULL)," +
		"('frc4209',4209,'TigerBytes','http://www.team4209.org/'," +
		" 'Baton Rouge','Louisiana','USA',NULL,2012,NULL)," +
		"('frc4210',4210,'JagBots','http://sehsrobotics.wordpress.com'," +
		" 'South Gate','CA','USA',NULL,2012,NULL)," +
		"('frc4211',4211,'Psycho Tech','http://fallcreek4211.weebly.com'," +
		" 'Indianapolis','IN','USA',NULL,2012,NULL)," +
		"('frc4212',4212,'Techno Ferrets','http://www.technoferrets.com/'," +
		" 'Galesburg','Illinois','USA',NULL,2012,'Go Ferrets!')," +
		"('frc4213',4213,'MetalCow Robotics','http://metalcowrobotics.com'," +
		" 'Bloomington','Illinois','USA',NULL,2012,'Part Sport. Part Technology. 100% Awesome!')," +
		"('frc4214',4214,'Robo-Flames',NULL," +
		" 'Riverview','FL','USA',NULL,2012,NULL)," +
		"('frc4215',4215,'Tritons','http://trinitytritonsrobotics.com'," +
		" 'Saint Paul','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4216',4216,'Blue Ops Robotics','http://ejhsblueops@gmail.com'," +
		" 'Jackson','Michigan','USA',NULL,2012,'????')," +
		"('frc4217',4217,'Scitobors','http://nkrobotics.webs.com'," +
		" 'Nashwauk','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4218',4218,'Navigator Robotics','http://www.firstinspires.org/'," +
		" 'Kapolei','Hawaii','USA',NULL,2012,NULL)," +
		"('frc4219',4219,'R4 Robo Riders','http://www.neisd.net/eta'," +
		" 'San Antonio','Texas','USA',NULL,2012,NULL)," +
		"('frc4222',4222,'The Frolicking Nerds','http://croomsrobotics.com'," +
		" 'Sanford','FL','USA',NULL,NULL,NULL)," +
		"('frc4223',4223,'Robo-Flight-Transformer',NULL," +
		" 'Lakeland','FL','USA',NULL,NULL,NULL)," +
		"('frc4224',4224,'The Robo-Rays','http://seacrest.org'," +
		" 'Naples','FL','USA',NULL,2012,NULL)," +
		"('frc4225',4225,'RoboCats','http://www.firstinspires.org/'," +
		" 'Saint Paul','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4226',4226,'Huskies','http://www.firstinspires.org/'," +
		" 'Albany','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4227',4227,'quarks',NULL," +
		" 'cannon falls','MN','USA',NULL,NULL,NULL)," +
		"('frc4228',4228,'iRobotics',NULL," +
		" 'Winthrop','MN','USA',NULL,NULL,NULL)," +
		"('frc4229',4229,'Magnetech','http://www.firstinspires.org/'," +
		" 'Saint Paul','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4230',4230,'TopperBots ','http://www.topperbots4230.com'," +
		" 'Duluth','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4231',4231,'Oakville Robotics 3',NULL," +
		" 'Saint Louis','Missouri','USA',NULL,2012,NULL)," +
		"('frc4232',4232,'AHS Robotics','http://alton4hroboticsteam4232.weebly.com/'," +
		" 'Alton','Illinois','USA',NULL,2012,NULL)," +
		"('frc4234',4234,'Bark Botz',NULL," +
		" 'Indianapolis','IN','USA',NULL,2012,NULL)," +
		"('frc4235',4235,'RoboRaiders','http://www.firstinspires.org/'," +
		" 'Danielsville','Georgia','USA',NULL,2012,'Together Everyone Achieves More With Organization Respect and Kindness')," +
		"('frc4236',4236,'impulse robotics','http://www.impulserobotics.ca'," +
		" 'mississauga','ON','Canada',NULL,2012,NULL)," +
		"('frc4237',4237,'Team Lance-A-Bot','http://www.lakeshorerobotics.com'," +
		" 'Stevensville','Michigan','USA',NULL,2012,NULL)," +
		"('frc4238',4238,'BBE Resistance Robotics','http://www.firstinspires.org/'," +
		" 'Belgrade','Minnesota','USA',NULL,2012,'Can''t isn''t in our dictionary!')," +
		"('frc4239',4239,'WARPSPEED','http://warpspeed4239.wordpress.com'," +
		" 'Willmar','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4240',4240,'TroTek Warriors','http://www.firstinspires.org/'," +
		" 'Albany','Georgia','USA',NULL,2012,NULL)," +
		"('frc4241',4241,'Joliet Cyborgs ','http://www.jolietcyborgs.com'," +
		" 'Joliet','Illinois','USA',NULL,2012,'Half Man, Half Machine, All Teamwork ')," +
		"('frc4242',4242,'Fresh T.E.C.H.','http://livingclassroomsdc.org/FIRST.html'," +
		" 'Washington','District of Columbia','USA',NULL,2012,NULL)," +
		"('frc4243',4243,'Excelsior!','http://team4243.wixsite.com/4243'," +
		" 'Charleston','South Carolina','USA',NULL,2012,'Bolts are always tight')," +
		"('frc4244',4244,'The Steel Battalion','http://www.firstinspires.org/'," +
		" 'Le Sueur','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4245',4245,'Robo Dragons',NULL," +
		" '1304 Ashe Street','NC','USA',NULL,2012,NULL)," +
		"('frc4246',4246,'Resurrected Robotics','http://www.firstinspires.org/'," +
		" 'Troy','Illinois','USA',NULL,2012,'\"Zip tie or die\"')," +
		"('frc4247',4247,'CougarBots','http://www.team4247.com'," +
		" 'Milwaukee','Wisconsin','USA',NULL,2012,'Know the Code')," +
		"('frc4248',4248,'Bits & Pieces','http://team4248.ca'," +
		" 'Whitby','Ontario','Canada',NULL,2012,'Fertoofereighterae sumus')," +
		"('frc4249',4249,'Tektronix','http://tektronixroboclub.com'," +
		" 'Brampton','ON','Canada',NULL,2012,NULL)," +
		"('frc4250',4250,'Comet-tron',NULL," +
		" 'Cobourg','ON','Canada',NULL,2012,NULL)," +
		"('frc4251',4251,'The Gallup GearHeads','http://team4251.weebly.com'," +
		" 'Gallup','NM','USA',NULL,2012,NULL)," +
		"('frc4252',4252,'Cardinal Robotics','http://www.Team4252.com'," +
		" 'Markham','Ontario','Canada',NULL,2012,NULL)," +
		"('frc4253',4253,'Raid Zero','http://www.firstinspires.org/'," +
		" 'Taipei','Taipei','Chinese Taipei',NULL,2012,NULL)," +
		"('frc4254',4254,'Varsity Robotics','http://www.facebook.com/pages/Saratoga-First-Robotics-Team-4254/157195627720023'," +
		" 'Saratoga Springs','NY','USA',NULL,NULL,NULL)," +
		"('frc4255',4255,'RoboDores','http://robodores-team4255.weebly.com'," +
		" 'Monterey','California','USA',NULL,2012,'Work hard, Keep it fun, go to St. Louis!')," +
		"('frc4256',4256,'Cyborg Cats','http://www.cyborgcats.com'," +
		" 'Chesterfield','Missouri','USA',NULL,2012,'Time in the People, Talent in the Robot')," +
		"('frc4257',4257,'CyberDogs',NULL," +
		" 'Aurora','OR','USA',NULL,2012,NULL)," +
		"('frc4258',4258,'Thornhill Secondary School','http://www.tssrobotics.com'," +
		" 'Markham','ON','Canada',NULL,2012,NULL)," +
		"('frc4259',4259,'Robo Dragons','http://nacogdochesrobotics.appspot.com'," +
		" 'Nacogdoches','TX','USA',NULL,NULL,NULL)," +
		"('frc4260',4260,'BEAR Bucs','http://www.firstinspires.org/'," +
		" 'Blue Earth Area','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4261',4261,'Raptors',NULL," +
		" 'North Charleston','SC','USA',NULL,2012,NULL)," +
		"('frc4262',4262,'Tecmilenio - RoboHawk - 4262','http://www.robohawk4262.com'," +
		" 'Santiago de Querétaro','Querétaro','Mexico',NULL,2012,'Turning dreams into creations')," +
		"('frc4263',4263,'CyberDragon','http://www.lesprobots.com'," +
		" 'New York','New York','USA',NULL,2012,NULL)," +
		"('frc4264',4264,'The Fellowship of the Springs','http://www.firstinspires.org/'," +
		" 'Rogersville','Tennessee','USA',NULL,2012,NULL)," +
		"('frc4265',4265,'Secret City Wildbots','http://www.firstinspires.org/'," +
		" 'Oak Ridge','Tennessee','USA',NULL,2012,'Passionately Pursuing Perfection and Catching Excellence')," +
		"('frc4266',4266,'GhostBotics','http://www.FIRST4266.com'," +
		" 'New Oxford','Pennsylvania','USA',NULL,2012,NULL)," +
		"('frc4267',4267,'Brave Bots','Coming Soon'," +
		" 'Myrtle Beach','South Carolina','USA',NULL,2012,NULL)," +
		"('frc4268',4268,'Beehive Robotics','http://www.beehiverobotics.org'," +
		" 'Sandy','Utah','USA',NULL,2012,NULL)," +
		"('frc4269',4269,'CardinalBots','http://www.stcharlesrobotics.com'," +
		" 'Columbus','Ohio','USA',NULL,2012,'Facta Non Verba')," +
		"('frc4270',4270,'Crusaders','http://saintlouisrobotics.com'," +
		" 'Honolulu','Hawaii','USA',NULL,2012,NULL)," +
		"('frc4271',4271,'Los Bandidos','http://reaganrobotics.org'," +
		" 'Austin','TX','USA',NULL,2012,NULL)," +
		"('frc4272',4272,'Maverick Boiler Robotics','http://team4272.com'," +
		" 'Lafayette','Indiana','USA',NULL,2012,NULL)," +
		"('frc4273',4273,'n/a',NULL," +
		" 'Culpeper','VA','USA',NULL,2012,NULL)," +
		"('frc4275',4275,'Western Hills RoboStangs',NULL," +
		" 'Cincinnati','Ohio','USA',NULL,2012,NULL)," +
		"('frc4276',4276,'Surf City Vikings','http://www.vikings4276.com'," +
		" 'Huntington Beach','California','USA',NULL,2012,'Vikings Come FIRST')," +
		"('frc4277',4277,'Thingamajiggers','http://team4277.webs.com/'," +
		" 'New Hope','Minnesota','USA',NULL,2012,NULL)," +
		"('frc4278',4278,'DABOT','http://www.dabot.org'," +
		" 'Madrid','MD','Spain',NULL,NULL,NULL)," +
		"('frc4279',4279,'RoboRevolution',NULL," +
		" 'Cincinnati','OH','USA',NULL,NULL,NULL)," +
		"('frc4280',4280,'N-Gen',NULL," +
		" 'Houston','TX','USA',NULL,2012,NULL)," +
		"('frc4281',4281,' Bulldogs','http://thecenterschoolrobotic.webs.com/'," +
		" 'Somerset','New Jersey','USA',NULL,2012,'ROBO BULL DOG')," +
		"('frc4282',4282,'Cowboys','http://itechgranado.weebly.com/robotics.html'," +
		" 'Brownsvile','TX','USA',NULL,2012,NULL)," +
		"('frc4283',4283,'INSPIRE Robotics','http://www.facebook.com/GranvilleRobotics'," +
		" 'Granville','Ohio','USA',NULL,2012,NULL)," +
		"('frc4284',4284,'ScotBot4284','http://www.scotbot4284.com'," +
		" 'Cincinnati','Ohio','USA',NULL,2012,'Bad in Plaid')," +
		"('frc4285',4285,'Camo-Bots','http://waynecountyrobotics4285.org'," +
		" 'Honesdale','Pennsylvania','USA',NULL,2012,'County wide knowledge')," +
		"('frc4286',4286,'Imperial Robotics','http://cfeds4286.wordpress.com'," +
		" 'Mechanicsville','Virginia','USA',NULL,2012,NULL)," +
		"('frc4287',4287,'Trojans','http://FaceBook%3A%20Banneker%20Trojans'," +
		" 'College Park','GA','USA',NULL,2012,NULL)," +
		"('frc4288',4288,'Worcester Beach Bots','http://worcesterbeachbots.org'," +
		" 'Berlin','Maryland','USA',NULL,2012,NULL)," +
		"('frc4289',4289,'Autobots','http://www.mifflincountyrobotics.org'," +
		" 'Lewistown','PA','USA',NULL,2012,NULL)," +
		"('frc4290',4290,'Bots on Wheels','http://www.bow4290.org'," +
		" 'Charlotte','North Carolina','USA',NULL,2012,NULL)," +
		"('frc4291',4291,'AstroBots','http://www.firstinspires.org/'," +
		" 'Henderson','North Carolina','USA',NULL,2012,'Go galactic or go home')," +
		"('frc4292',4292,'PorterBots','https://porterbots4292.weebly.com/'," +
		" 'Lockport','Illinois','USA',NULL,2012,NULL)," +
		"('frc4293',4293,'Komodo','http://www.youngengineers.us'," +
		" 'Highlands Ranch','Colorado','USA',NULL,2012,NULL)," +
		"('frc4294',4294,'StarTREC','http://www.star-trec.com'," +
		" 'Lansing','MI','USA',NULL,2012,NULL)," +
		"('frc4295',4295,'Hudson Robotics','http://hhs.hudsonisd.org/55730_2'," +
		" 'Lufkin','Texas','USA',NULL,2012,NULL)," +
		"('frc4296',4296,'Trident Robotics','http://team4296.org/'," +
		" 'Gurnee','Illinois','USA',NULL,2012,NULL)," +
		"('frc4297',4297,'WENORoBoTS','http://www.wenonahrobotics.webs.com'," +
		" 'Birmingham','AL','USA',NULL,2012,NULL)," +
		"('frc4298',4298,'C-RockBotics','http://www.firstinspires.org/'," +
		" 'Amarillo','Texas','USA',NULL,2012,NULL)," +
		"('frc4299',4299,'BCS Robo Sharks','http://BCSrobosharks.org'," +
		" 'Brooklyn','New York','USA',NULL,2012,NULL)," +
		"('frc4300',4300,'The Lion Kings','http://mhsrobotics.com'," +
		" 'McKinney','Texas','USA',NULL,2012,'The road to success is always under construction.')," +
		"('frc4301',4301,'SM Energy New Tech Narcissists','Coming Soon'," +
		" 'Odessa','Texas','USA',NULL,2012,'On time, on task, and on a mission.')," +
		"('frc4302',4302,'Robophins','http://www.firstinspires.org/'," +
		" 'Chicago','Illinois','USA',NULL,2012,NULL)," +
		"('frc4304',4304,'Iron Rams','http://ironrams4304.webs.com'," +
		" 'Puyallup','WA','USA',NULL,2012,NULL)," +
		"('frc4306',4306,'Robokomodos','https://sites.google.com/view/fhs-robotics/home'," +
		" 'Franklin','Tennessee','USA',NULL,2012,NULL)," +
		"('frc4307',4307,'Trojan Thunderbolts',NULL," +
		" '180 Church St. North','ON','Canada',NULL,2012,NULL)," +
		"('frc4308',4308,'ABSOLUTE ROBOTICS','http://team4308.ca/'," +
		" 'Mississauga','Ontario','Canada',NULL,2012,NULL)," +
		"('frc4309',4309,'4-H Botsmiths','http://www.botsmiths.org'," +
		" 'Snohomish','Washington','USA',NULL,2012,NULL)," +
		"('frc4310',4310,'NUKES','http://www.firstinspires.org/'," +
		" 'American Fork','Utah','USA',NULL,2012,NULL)," +
		"('frc4311',4311,'Swampscott Currents','http://www.firstinspires.org/'," +
		" 'Swampscott','Massachusetts','USA',NULL,2012,NULL)," +
		"('frc4314',4314,'SUL Abe-Ro-Bots','http://www.firstinspires.org/'," +
		" 'Springfield ','Illinois','USA',NULL,2012,NULL)," +
		"('frc4315',4315,'Falcons',NULL," +
		" 'Dallas','TX','USA',NULL,2012,NULL)," +
		"('frc4316',4316,'Trobotics','http://N'," +
		" 'Raceland','Louisiana','USA',NULL,2012,'High in the Sky')," +
		"('frc4317',4317,'Tech Busters','http://www.dentonisd.org/pltw'," +
		" 'Denton','TX','USA',NULL,2012,NULL)," +
		"('frc4318',4318,'Mo-Bots',NULL," +
		" 'Las Vegas','NV','USA',NULL,2012,NULL)," +
		"('frc4319',4319,'Ladies FIRST','http://www.firstinspires.org/'," +
		" 'Beer Sheva','HaDarom','Israel',NULL,2012,NULL)," +
		"('frc4320',4320,'The Joker','https://frcthejoker4320.wixsite.com/team4320'," +
		" 'Petach Tikvah','HaMerkaz','Israel',NULL,2012,'Your Winning Card')," +
		"('frc4321',4321,'Vikings',NULL," +
		" 'Jacksonville','FL','USA',NULL,2012,NULL)," +
		"('frc4322',4322,'Clockwork Oranges','http://www.firstinspires.org/'," +
		" 'Orange','California','USA',NULL,2012,'Just like clockwork')," +
		"('frc4323',4323,'BGCircuit Breakers',NULL," +
		" 'Columbus','GA','USA',NULL,2012,NULL)," +
		"('frc4324',4324,'HTA','http://www.facebook.com/HTAFanPage'," +
		" 'Decatur','IL','USA',NULL,2012,NULL)," +
		"('frc4325',4325,'RoboRangers','http://roborangers4325.com'," +
		" 'Cassopolis','Michigan','USA',NULL,2012,'The revolution begins...  NOW!')," +
		"('frc4326',4326,'Team Rocket','http://www.4326.comule.com'," +
		" 'Ramat Hasharon','M','Israel',NULL,2012,NULL)," +
		"('frc4327',4327,'Q Branch','http://qbranchrobotics.com'," +
		" 'Battle Creek','Michigan','USA',NULL,2012,'Grow team, grow!')," +
		"('frc4328',4328,'Furious Falcons','http://www.furiousfalcons.com'," +
		" 'Richmond','Texas','USA',NULL,2012,NULL)," +
		"('frc4329',4329,'Lutheran Roboteers','http://www.4329roboteers.org'," +
		" 'Saint Peters','Missouri','USA',NULL,2012,'One for All and All for One')," +
		"('frc4330',4330,'Rambunction','http://www.rambunction.net'," +
		" 'Saint Louis','Missouri','USA',NULL,2012,'It has to work we made it ')," +
		"('frc4331',4331,'RoboSwag','http://www.firstinspires.org/'," +
		" 'St. Louis','Missouri','USA',NULL,2012,NULL)," +
		"('frc4332',4332,'EHS RoboCats','http://edinburg.ehs.schooldesk.net/Clubs/TechnologyRobotics/tabid/2193/Default.aspx'," +
		" 'Edinburg','Texas','USA',NULL,2012,'EHS RoboCats: “Together, we will make a')," +
		"('frc4333',4333,'Team MOSI','http://ideazone.org'," +
		" 'Tampa','FL','USA',NULL,NULL,NULL)," +
		"('frc4334',4334,'Alberta Tech Alliance (ATA)','http://4334.ca'," +
		" 'Calgary','Alberta','Canada',NULL,2012,'We Build People')," +
		"('frc4335',4335,'Metallic Clouds','http://www.FRC4335-METALLICCLOUDS.COM'," +
		" 'Waco','Texas','USA',NULL,2012,'“No I Cant’s” As a team we can do anything and everything we set our minds to.')," +
		"('frc4336',4336,'Ramageddon','http://www.ramageddon.org'," +
		" 'Lafayette','Louisiana','USA',NULL,2012,NULL)," +
		"('frc4337',4337,'R 13','http://www.firstinspires.org/'," +
		" 'Holton','Michigan','USA',NULL,2012,NULL)," +
		"('frc4338',4338,'Falcons','http://www.firstinspires.org/'," +
		" 'Even-Yehuda','HaMerkaz','Israel',NULL,2012,NULL)," +
		"('frc4339',4339,'Topsail FIRST Robotics',NULL," +
		" 'Hampstead','NC','USA',NULL,NULL,NULL)," +
		"('frc4341',4341,'Panther Robotics',NULL," +
		" 'Mulberry','FL','USA',NULL,2012,NULL)," +
		"('frc4342',4342,'Demon Robotics','http://www.demonrobotics4342.org/#home1'," +
		" 'Kennett Square','Pennsylvania','USA',NULL,2012,NULL)," +
		"('frc4343',4343,'MaxTech','http://4343.ca'," +
		" 'Aurora','Ontario','Canada',NULL,2012,'Choice not chance determines destiny')," +
		"('frc4344',4344,'SLCSE',NULL," +
		" '1400 W. Goodwin Ave.','UT','USA',NULL,NULL,NULL)," +
		"('frc4345',4345,'AutoPilots','http://autopilots4345.com'," +
		" 'Wilmington','CA','USA',NULL,NULL,NULL)," +
		"('frc4346',4346,'Crimson Dragons','http://Www.4346crimsondragons.org'," +
		" 'Houston','TX','USA',NULL,2012,NULL)," +
		"('frc4347',4347,'Mechanical End','http://holtonsworld.com/robots'," +
		" 'Paterson','NJ','USA',NULL,2012,NULL)," +
		"('frc4348',4348,'Bonneville Bots','http://frcteam4348.wixsite.com/4348'," +
		" 'Tooele','Utah','USA',NULL,2012,NULL)," +
		"('frc4349',4349,'Viking Robotics',NULL," +
		" 'Oklahoma City','OK','USA',NULL,2012,NULL)," +
		"('frc4350',4350,'Harmony',NULL," +
		" 'San Antonio','TX','USA',NULL,2012,NULL)," +
		"('frc4351',4351,'Broncobots',NULL," +
		" 'McKinney','TX','USA',NULL,NULL,NULL)," +
		"('frc4352',4352,'Generals','http://www.firstinspires.org/'," +
		" 'Jacksonville','Florida','USA',NULL,2012,NULL)," +
		"('frc4353',4353,'Krewe de Reauxbotics','http://lpss.oncoursesystems.com/websites/12548565'," +
		" 'Lafayette','Louisiana','USA',NULL,2012,'Team work makes the Dream work!')," +
		"('frc4354',4354,'PengWINs','http://www.pengwinsrobotics.org'," +
		" 'Dallas','Texas','USA',NULL,2012,NULL)," +
		"('frc4355',4355,'CP-BOTS','https://www.facebook.com/cpbots/'," +
		" 'Gomez Palacio','Durango','Mexico',NULL,2012,'Fighting spirit')," +
		"('frc4356',4356,'BIG Furious George','http://www.facebook.com/CIARobotics'," +
		" 'Chaffee','Missouri','USA',NULL,2012,'All for one, One for all')," +
		"('frc4357',4357,'Spartans','http://www2.kprdsb.ca/phhs/'," +
		" 'Port Hope','Ontario','Canada',NULL,2012,'Get Her Done')," +
		"('frc4358',4358,'Ollibotics',NULL," +
		" 'Freiburg im Breisgau','BW','Germany',NULL,NULL,NULL)," +
		"('frc4359',4359,'North Robotics',NULL," +
		" 'McKinney','TX','USA',NULL,2012,NULL)," +
		"('frc4360',4360,'Spudnik','http://moorheadrobotics.org'," +
		" 'Moorhead','Minnesota','USA',NULL,2012,'Team of Ten Thousand Schemes!')," +
		"('frc4361',4361,'Roxbotix','http://www.roxbotix.org'," +
		" 'Roxbury Township','New Jersey','USA',NULL,2012,'Building a Better Robot Since 2012')," +
		"('frc4362',4362,'Gems','http://www.gemsrobotics.com'," +
		" 'Brighton','Michigan','USA',NULL,2012,NULL)," +
		"('frc4363',4363,'Unbolted Puppeteers','http://team4363.x10.mx'," +
		" 'Hancock','MI','USA',NULL,2012,NULL)," +
		"('frc4364',4364,'Metal Jackets','http://shs.sville.us/apps/pages/Robotics'," +
		" 'Stephenville','Texas','USA',NULL,2012,'Desire, Determination, and Duct Tape!')," +
		"('frc4365',4365,'Torch Club',NULL," +
		" 'Hueytown','AL','USA',NULL,NULL,NULL)," +
		"('frc4366',4366,'Rawbotics',NULL," +
		" 'Mexico','DIF','Mexico',NULL,2012,NULL)," +
		"('frc4367',4367,'Tigers','http://www.streetbots.ca'," +
		" '72 Joymar Drive','ON','Canada',NULL,2012,NULL)," +
		"('frc4368',4368,'BullBots','http://www.firstinspires.org/'," +
		" 'Vicksburg','Michigan','USA',NULL,2012,NULL)," +
		"('frc4369',4369,'Brick City Bots',NULL," +
		" 'Sanford','NC','USA',NULL,2012,NULL)," +
		"('frc4370',4370,'PROJECT X',NULL," +
		" 'Orangeburg','SC','USA',NULL,NULL,NULL)," +
		"('frc4371',4371,'Tecmilenio-ART-4371','http://www.facebook.com/art4371'," +
		" 'Cuautilán Izcalli','Mexico','Mexico',NULL,2012,'Avanza Reta Trasciende')," +
		"('frc4372',4372,'PRDG',NULL," +
		" 'Cambridge','ON','Canada',NULL,2012,NULL)," +
		"('frc4373',4373,'RooBotics','http://www.firstinspires.org/'," +
		" 'Jenkintown','Pennsylvania','USA',NULL,2012,NULL)," +
		"('frc4374',4374,'Roosevelt Robotics',NULL," +
		" 'Honolulu','HI','USA',NULL,2012,NULL)," +
		"('frc4375',4375,'Westend Gearbusters','https://westendgearbusters.site123.me/'," +
		" 'Ishpeming','Michigan','USA',NULL,2012,NULL)," +
		"('frc4376',4376,'Raydernators','http://www.facebook.com/groups/team4376'," +
		" 'Charlevoix','Michigan','USA',NULL,2012,'Graciously kickin'' chassis since 2012')," +
		"('frc4377',4377,'Boyne City Blaze','http://www.boynecityblaze.com'," +
		" 'Boyne City','Michigan','USA',NULL,2012,NULL)," +
		"('frc4378',4378,'Dark Knights','http://www.hanksrobotics.com'," +
		" 'El Paso','Texas','USA',NULL,2012,'Dark Knights are Bright')," +
		"('frc4379',4379,'Greased Lightning','https://sites.google.com/site/cbhsrobotics'," +
		" 'Weston','FL','USA',NULL,NULL,NULL)," +
		"('frc4380',4380,'Dynomite','http://www.heroesalliance.net'," +
		" 'Detroit','Michigan','USA',NULL,2012,NULL)," +
		"('frc4381',4381,'Twisted Devils','http://www.team4381.com'," +
		" 'Richland','Michigan','USA',NULL,2012,NULL)," +
		"('frc4382',4382,'Team Trobot',NULL," +
		" 'Saginaw','Michigan','USA',NULL,2012,NULL)," +
		"('frc4383',4383,'The P-TECH Fly-Bots','http://www.firstinspires.org/'," +
		" 'Brooklyn','New York','USA',NULL,2012,NULL)," +
		"('frc4384',4384,'Benzene Bots','http://www.team4384.com'," +
		" 'Troy','Michigan','USA',NULL,2012,'Be Inspired!')," +
		"('frc4385',4385,'The Bryan Automatons',NULL," +
		" 'Bryan','OH','USA',NULL,2012,NULL)," +
		"('frc4386',4386,'Mecha-maniacs','https://team4386.jimdo.com/'," +
		" 'Brush','Colorado','USA',NULL,2012,NULL)," +
		"('frc4387',4387,'TBD',NULL," +
		" 'Sterling','CO','USA',NULL,2012,NULL)," +
		"('frc4388',4388,'Ridgebotics','http://www.Ridgebotics.com'," +
		" 'Fort Collins','Colorado','USA',NULL,2012,NULL)," +
		"('frc4389',4389,'Virtual Bot',NULL," +
		" 'Battle Creek','MI','USA',NULL,NULL,NULL)," +
		"('frc4390',4390,'ATA  Coregears','http://www.firstinspires.org/'," +
		" 'Dearborn','Michigan','USA',NULL,2012,'Robotics to the Core')," +
		"('frc4391',4391,'BraveBots','http://www.bravebots.net/'," +
		" 'Gladstone','Michigan','USA',NULL,2012,'Braves Don''t Quit')," +
		"('frc4392',4392,'The Deceivers','http://www.firstinspires.org/'," +
		" 'Brimley','Michigan','USA',NULL,2012,NULL)," +
		"('frc4394',4394,'Team Opportunity',NULL," +
		" 'Williamsburg','VA','USA',NULL,2012,NULL)," +
		"('frc4395',4395,'Dundee Vi-Borgs','http://dundeeviborgs.wix.com/vi-borgs'," +
		" 'Dundee','Michigan','USA',NULL,2012,'Ek Hefja, Ek Leggja')," +
		"('frc4396',4396,'Gear Grinders ',NULL," +
		" 'Memphis','Tennessee','USA',NULL,2012,NULL)," +
		"('frc4397',4397,'TEAM Clutch Robotics','http://www.firstinspires.org/'," +
		" 'Bagley','Minnesota','USA',NULL,2012,'We come through in the Clutch!')," +
		"('frc4398',4398,'Imprimis Deo','http://www.TCSF-robotics.weebly.com'," +
		" 'Traverse City','Michigan','USA',NULL,2012,NULL)," +
		"('frc4400',4400,'CERBOTICS','http://www.cerbotics.com.mx'," +
		" 'TORREON','Coahuila','Mexico',NULL,2012,'Imagine & Build')," +
		"('frc4401',4401,'CETMOV','http://cetmov4401.jimdo.com/'," +
		" 'Laguna del Rey Coahuila','Coahuila','Mexico',NULL,2012,'SAFETY IS OUR PRIORITY')," +
		"('frc4402',4402,'Connectors',NULL," +
		" 'West Plains','MO','USA',NULL,2012,NULL)," +
		"('frc4403',4403,'PrepaTec - ROULT','http://roult.com.mx/'," +
		" 'Torreon','Coahuila','Mexico',NULL,2012,NULL)," +
		"('frc4404',4404,'Nuts & Colts','http://nutsandcolts.com/'," +
		" 'Chesterfield','Missouri','USA',NULL,2012,NULL)," +
		"('frc4405',4405,'The Atoms Family','http://www.team4405.com'," +
		" 'Canton','Michigan','USA',NULL,2012,NULL)," +
		"('frc4406',4406,'Robotic Legends','http://www.firstinspires.org/'," +
		" 'Tel Aviv','Tel-Aviv','Israel',NULL,2012,'Science is Our Language')," +
		"('frc4407',4407,'Virtualosity',NULL," +
		" 'Jackson','MI','USA',NULL,NULL,NULL)," +
		"('frc4408',4408,'Panthera Machina','http://www.firstinspires.org/'," +
		" 'Battle Creek','Michigan','USA',NULL,2012,NULL)," +
		"('frc4409',4409,'Ground Zero','http://www.firstinspires.org/'," +
		" 'Niles','Michigan','USA',NULL,2012,NULL)," +
		"('frc4410',4410,'Cyber Senators',NULL," +
		" 'Orange','MA','USA',NULL,NULL,NULL)," +
		"('frc4411',4411,'Jiao Da Fu Zhong','http://www.bfjdfz.net'," +
		" 'Beijing','11','China',NULL,NULL,NULL)," +
		"('frc4412',4412,'Steel Stallions','http://www.firstinspires.org/'," +
		" 'San Antonio','Texas','USA',NULL,2012,NULL)," +
		"('frc4413',4413,'Viking Robotics','vikingrobotics580.com'," +
		" 'Studio City','California','USA',NULL,2012,'We''re [Au]some')," +
		"('frc4414',4414,'HighTide','https://www.team4414.com'," +
		" 'Ventura','California','USA',NULL,2012,NULL)," +
		"('frc4415',4415,'EPIC Robotz','http://www.epicrobotz.org'," +
		" 'Cerritos','California','USA',NULL,2012,'Created by God to Pursue Excellence')," +
		"('frc4416',4416,'Skynet','http://www.rocket4416.com'," +
		" 'Ramat Hasharon','Tel-Aviv','Israel',NULL,2012,NULL)," +
		"('frc4418',4418,'Team IMPULSE','http://frc4418.org/'," +
		" 'Highlands Ranch','Colorado','USA',NULL,2012,'Propelled to Success')," +
		"('frc4450',4450,'Olympia Robotics Federation','http://orf4450.org'," +
		" 'Olympia','Washington','USA',NULL,2013,NULL)," +
		"('frc4451',4451,'ROBOTZ Garage','http://frcteam4451.org'," +
		" 'Laurens','South Carolina','USA',NULL,2013,'Life is Good in the Garage!')," +
		"('frc4452',4452,'First Noble Team','http://www.firstnobleteam.com'," +
		" 'Seneca','South Carolina','USA',NULL,2013,NULL)," +
		"('frc4453',4453,'The Red Hot Chili Bots','http://redhotchilibots.com'," +
		" 'Marshall','Michigan','USA',NULL,2013,'We Bring the Heat')," +
		"('frc4454',4454,'Artisan Rockets','https://www.facebook.com/SLAengineers/'," +
		" 'Philadelphia','Pennsylvania','USA',NULL,2013,'Fail Fast, Fail Often, Fail Forward')," +
		"('frc4455',4455,'The Burger Bots','http://TheBurgerBots.com'," +
		" 'Warrensburg','Missouri','USA',NULL,2013,'Bots Made to Order')," +
		"('frc4456',4456,'Mech Cadets','https://frc4456.com/'," +
		" 'Washington','District of Columbia','USA',NULL,2013,NULL)," +
		"('frc4457',4457,'Controlled Chaotic Robotics',NULL," +
		" 'Portland','OR','USA',NULL,2013,NULL)," +
		"('frc4458',4458,'Infernobotix','http://Team4458.org'," +
		" 'Center Moriches','New York','USA',NULL,2013,NULL)," +
		"('frc4459',4459,'LeoTechs','https://www.lshsstem.com'," +
		" 'Lithia Springs','Georgia','USA',NULL,2013,NULL)," +
		"('frc4460',4460,'Green MASHine','http://www.facebook.com/FRCGreenMasHine4460'," +
		" 'West Vincent Township','Pennsylvania','USA',NULL,2013,'What we learn is more important than what we win.')," +
		"('frc4461',4461,'Ramen','https://frc4461.github.io/'," +
		" 'Des Moines','Washington','USA',NULL,2013,NULL)," +
		"('frc4462',4462,'Full Metal Jackets','http://www.fmj4462.com'," +
		" 'Kingston','Tennessee','USA',NULL,2013,'Past Integration, Immediate Inspiration, and Infinite Progression')," +
		"('frc4463',4463,'The Blaze',NULL," +
		" 'Jonesboro','AR','USA',NULL,2013,NULL)," +
		"('frc4464',4464,'Team Illusion','http://www.teamillusion4464.com/'," +
		" 'Laurel','Maryland','USA',NULL,2013,'Illusion is Our Name Engineering is our Game')," +
		"('frc4465',4465,'WC4-H',NULL," +
		" 'Youngsville','PA','USA',NULL,NULL,NULL)," +
		"('frc4466',4466,'Robo Hamsters','http://angelicrobotics.com/'," +
		" 'Culpeper','Virginia','USA',NULL,2013,NULL)," +
		"('frc4467',4467,'Titanium Titans','http://www.titaniumtitans.org'," +
		" 'Canonsburg','Pennsylvania','USA',NULL,2013,'Training tomorrow''s technology workforce')," +
		"('frc4468',4468,'Fernbank LINKS','http://www.fernbanklinks.com/'," +
		" 'Atlanta','Georgia','USA',NULL,2013,'Linking Ideas and Networking Kids with Science')," +
		"('frc4469',4469,'R.A.I.D. (Raider Artificial Intelligence DIvision)','https://raidrobotics.org'," +
		" 'Federal Way','Washington','USA',NULL,2013,NULL)," +
		"('frc4470',4470,'TiGears','https://sites.google.com/site/tigears4470/'," +
		" 'Placentia','California','USA',NULL,2013,NULL)," +
		"('frc4471',4471,'SPARTRONS','http://spartrons.com'," +
		" 'Hollywood','Florida','USA',NULL,2013,'We''re not just building a robot, we''re building our future!')," +
		"('frc4472',4472,'SuperNOVA','http://4472supernova.org/'," +
		" 'Woodbridge','Virginia','USA',NULL,2013,NULL)," +
		"('frc4473',4473,'Delta Prime','http://www.deltaprimerobotics.com'," +
		" 'Farmingdale','Maine','USA',NULL,2013,'Keep Calm and Build Robots')," +
		"('frc4474',4474,'Silver Circuit','http://www.silverlakerobotics.com'," +
		" 'Kingston','Massachusetts','USA',NULL,2013,'Inventivity!')," +
		"('frc4475',4475,'Terrier Byte Bots','http://www.firstinspires.org/'," +
		" 'Newark','New Jersey','USA',NULL,2013,NULL)," +
		"('frc4476',4476,'W.A.F.F.L.E.S.','http://wafflesrobotics.com/'," +
		" 'Kingston','Ontario','Canada',NULL,2013,'Wild About Family & Friends Learning Engineering & Science')," +
		"('frc4477',4477,'ASMSA FIRST',NULL," +
		" 'Hot Springs','AR','USA',NULL,2013,NULL)," +
		"('frc4478',4478,'Materia Oscura','http://www.materia-oscura.org'," +
		" 'Milford','MI','USA',NULL,2013,NULL)," +
		"('frc4479',4479,'Hixson Wiredcats',NULL," +
		" '5705 Middle Valley Rd','TN','USA',NULL,2013,NULL)," +
		"('frc4480',4480,'UC-Botics','https://www.facebook.com/UCBotics/'," +
		" 'Upsala','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4481',4481,'Team Rembrandts','http://teamrembrandts.com'," +
		" 'Eindhoven','Noord-Brabant','Netherlands',NULL,2013,'If things get complicated, bring in the Dutch!')," +
		"('frc4482',4482,'ID Robotics','http://idrobotics.net'," +
		" 'Kalamazoo','Michigan','USA',NULL,2013,NULL)," +
		"('frc4483',4483,'21st Century Robotics','http://www.facebook.com/21stCenturyRobotics?ref=hl'," +
		" 'Battle Creek','MI','USA',NULL,2013,NULL)," +
		"('frc4484',4484,'4-H DEADLOX','http://deadloxteam4484.wix.com/deadlox-robotics'," +
		" 'Clearfield','Pennsylvania','USA',NULL,2013,NULL)," +
		"('frc4485',4485,'Tribe Tech Robotics','http://tribetech4485.com'," +
		" 'Danville','Indiana','USA',NULL,2013,'It is David''s fault')," +
		"('frc4486',4486,'Blue Prints','http://frcteam4486.wixsite.com/frcteam4486'," +
		" 'Vista','California','USA',NULL,2013,'Dream It, Engineer It, Build It')," +
		"('frc4487',4487,'Oologah',NULL," +
		" 'Oologah','OK','USA',NULL,2013,NULL)," +
		"('frc4488',4488,'Shockwave','http://glencoerobotics.com/'," +
		" 'Hillsboro','Oregon','USA',NULL,2013,'Roll Shockwave!')," +
		"('frc4489',4489,'Cybertribe',NULL," +
		" 'Knoxville','TN','USA',NULL,2013,NULL)," +
		"('frc4490',4490,'TechRaiders','http://www.techraiders.org'," +
		" 'Searcy','Arkansas','USA',NULL,2013,'\"We don''t have failures, just different degrees of success\"')," +
		"('frc4491',4491,'Richmond Knights','http://gildanc.wixsite.com/rrhsrobotics'," +
		" 'Richmond','Québec','Canada',NULL,2013,NULL)," +
		"('frc4492',4492,'Chrono','http://chrono4492.com'," +
		" 'Coaticook','QC','Canada',NULL,2013,NULL)," +
		"('frc4493',4493,'The Red Hornet','http://www.dmesc.net/robotics'," +
		" 'Blevins','AR','USA',NULL,NULL,NULL)," +
		"('frc4494',4494,'Scrapping Hornets',NULL," +
		" 'Nashville','AR','USA',NULL,NULL,NULL)," +
		"('frc4495',4495,'Kittitas County Robotics/ Team Haywire','http://www.firstinspires.org/'," +
		" 'Thorp','Washington','USA',NULL,2013,'It is pretty straight forward')," +
		"('frc4496',4496,'Sonoran Storm Robotics','http://perryhighrobotics.wix.com/sonoranstorm-4496'," +
		" 'Gilbert','Arizona','USA',NULL,2013,'Built to Succeed')," +
		"('frc4497',4497,'SKy-Net','http://www.firstinspires.org/'," +
		" 'Swift Current','Saskatchewan','Canada',NULL,2013,NULL)," +
		"('frc4498',4498,'Team X','http://www.bgcsac.org/index.php/robotics'," +
		" 'Sacramento','California','USA',NULL,2013,NULL)," +
		"('frc4499',4499,'The Highlanders','http://www.highlandersFRC.com'," +
		" 'Fort Collins','Colorado','USA',NULL,2013,'It''s not about the game, it''s about the journey');";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_9 = SQL_INSERT_TEAMS +
		"('frc4500',4500,'RoboHounds','http://www.robohounds.com'," +
		" 'Clayton','Missouri','USA',NULL,2013,NULL)," +
		"('frc4501',4501,'Humans','http://www.firstinspires.org/'," +
		" 'Santa Monica','California','USA',NULL,2013,NULL)," +
		"('frc4502',4502,'The Octoπ','http://under%20construction'," +
		" 'New Braunfels','TX','USA',NULL,NULL,NULL)," +
		"('frc4503',4503,'Wildcats',NULL," +
		" 'El Dorado','AR','USA',NULL,NULL,NULL)," +
		"('frc4504',4504,'B. C. Robotics','https://www.bcrobotics4504.com'," +
		" 'Maryville','Tennessee','USA',NULL,2013,NULL)," +
		"('frc4505',4505,'McDonogh Robotics','http://www.mcdonogh.org/first/'," +
		" 'Owings Mills','Maryland','USA',NULL,2013,NULL)," +
		"('frc4506',4506,'PioNerds','http://www.firstinspires.org/'," +
		" 'Saint Paul','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4507',4507,'TARminators','http://Work in Process'," +
		" 'Detroit','Michigan','USA',NULL,2013,'TBD')," +
		"('frc4508',4508,'Steel Stallions','http://scsrobotics4508.wixsite.com/frcteam4508'," +
		" 'Schuylerville','New York','USA',NULL,2013,'Just Sweat and Gears')," +
		"('frc4509',4509,'Mechanical Bulls','http://first.lanierhs.org'," +
		" 'Sugar Hill','Georgia','USA',NULL,2013,'M.E. full of I.T.')," +
		"('frc4510',4510,'Bobcats',NULL," +
		" '715 North Third Street','GA','USA',NULL,2013,NULL)," +
		"('frc4511',4511,'Power Amplified','http://www.firstinspires.org/'," +
		" 'Plymouth','Minnesota','USA',NULL,2013,'Watt could be better?')," +
		"('frc4512',4512,'Otter Chaos','http://www.everett.k12.wa.us/cascade/roboticsclub/Home'," +
		" 'Everett','Washington','USA',NULL,2013,'Bruins Empowered And Ready')," +
		"('frc4513',4513,'Circuit Breakers','http://www.medicallakerobotics.com'," +
		" 'Medical Lake','Washington','USA',NULL,2013,'We''re not just building a robot, we''re building a team that builds a robot')," +
		"('frc4514',4514,'Calvert STEAM Works','http://www.csw4514.org'," +
		" 'Huntingtown','Maryland','USA',NULL,2013,'We''re putting the arts back in STEM!')," +
		"('frc4515',4515,'Stag',NULL," +
		" 'Shawnigan Lake','BC','Canada',NULL,NULL,NULL)," +
		"('frc4516',4516,'Hyperion','http://www.firstinspires.org/'," +
		" 'Roswell','Georgia','USA',NULL,2013,NULL)," +
		"('frc4517',4517,'Owl Robotics','http://www.firstinspires.org/'," +
		" 'Miami','Florida','USA',NULL,2013,NULL)," +
		"('frc4518',4518,'Optimus Pride','http://www.thehillacademy.com'," +
		" 'Vaughan','ON','Canada',NULL,NULL,NULL)," +
		"('frc4519',4519,'King''s Robotics','http://team4519.ca'," +
		" 'Oakville','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4520',4520,'Misfit Toys','http://www.frc4520.com'," +
		" 'Rathdrum','ID','USA',NULL,2013,NULL)," +
		"('frc4521',4521,'Harrison Robotics','https://www.harrisonrobotics.net/'," +
		" 'Harrison','Ohio','USA',NULL,2013,NULL)," +
		"('frc4522',4522,'Team SCREAM','http://www.firstinspires.org/'," +
		" 'Sedalia','Missouri','USA',NULL,2013,NULL)," +
		"('frc4523',4523,'MCROBO','http://www.firstinspires.org/'," +
		" 'Mayes County ','Oklahoma','USA',NULL,2013,'Captains of Industry')," +
		"('frc4524',4524,'The Memphis MechWarriors','http://memphismechwarriors.com'," +
		" 'Memphis','Tennessee','USA',NULL,2013,NULL)," +
		"('frc4525',4525,'Renaissance Robotics','http://www.rambot.ca'," +
		" 'St. Thomas','Ontario','Canada',NULL,2013,'STEAM powered STEM')," +
		"('frc4526',4526,'Léoméga','http://leomega.csaffluents.qc.ca/'," +
		" 'Terrebonne','Québec','Canada',NULL,2013,NULL)," +
		"('frc4527',4527,'Big Reds',NULL," +
		" 'Milan','MI','USA',NULL,NULL,NULL)," +
		"('frc4528',4528,'Automatons','http://www.johnbowne.org'," +
		" 'Queens','New York','USA',NULL,2013,'Hard Work Never Let Anyone Down')," +
		"('frc4529',4529,'AI Robotics','http://www.ai-robotics.com.au'," +
		" 'Armadale','Victoria','Australia',NULL,2013,'An inspired mind can change the world.')," +
		"('frc4530',4530,'D3A',NULL," +
		" 'Jacksonville','FL','USA',NULL,NULL,NULL)," +
		"('frc4531',4531,'STEMpunk','http://www.STEMpunk4531.org'," +
		" 'Two Rivers','Wisconsin','USA',NULL,2013,'We don''t just build robots, we build people\"')," +
		"('frc4532',4532,'LAN Jaguars FIRST Robotics',NULL," +
		" 'Sherwood','AR','USA',NULL,2013,NULL)," +
		"('frc4533',4533,'Wando Adv. Robotics','http://www.wandorobotics.com'," +
		" 'Mt Pleasant','South Carolina','USA',NULL,2013,'This Means W.A.R.')," +
		"('frc4534',4534,'Wired Wizards','https://www.wiredwizards.org'," +
		" 'Wilmington','North Carolina','USA',NULL,2013,'But it works!')," +
		"('frc4535',4535,'PA Bruins',NULL," +
		" 'Little Rock','AR','USA',NULL,2013,NULL)," +
		"('frc4536',4536,'MinuteBots','http://www.minutebots.org'," +
		" 'Saint Paul','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4537',4537,'RoboRoos','https://www.roboroos.org.au'," +
		" 'Adelaide','South Australia','Australia',NULL,2013,'Hopping into Engineering')," +
		"('frc4538',4538,'Enginuity','http://teamenginuity.org/'," +
		" 'Newport News','Virginia','USA',NULL,2013,NULL)," +
		"('frc4539',4539,'KAOTIC Robotics','https://www.kaoticrobotics.org'," +
		" 'Frazee','Minnesota','USA',NULL,2013,'Keeping Ahead Of Technology In Communties')," +
		"('frc4540',4540,'OrangeLab',NULL," +
		" 'El Cajon','CA','USA',NULL,NULL,NULL)," +
		"('frc4541',4541,'CAV-ineers','http://4541cavineers.org'," +
		" 'Severn','Maryland','USA',NULL,2013,'4chieving 5eyond O4r L1mits')," +
		"('frc4542',4542,'Titanium Talons',NULL," +
		" 'Woodinville','WA','USA',NULL,2013,NULL)," +
		"('frc4543',4543,'The Illuminators','http://www.team4543.com'," +
		" 'San Jose','California','USA',NULL,2013,NULL)," +
		"('frc4544',4544,'The Levitating Potatoes',NULL," +
		" 'San Rafael','CA','USA',NULL,2013,NULL)," +
		"('frc4545',4545,'GOLDEN KNIGHTS',NULL," +
		" 'Indianapolis','IN','USA',NULL,2013,NULL)," +
		"('frc4546',4546,'ShockWave','https://www.dovershockwave.org/'," +
		" 'Dover','New Hampshire','USA',NULL,2013,'In bot we trust')," +
		"('frc4547',4547,'WestyTek','http://www.westytek.com/'," +
		" 'Johnstown','Pennsylvania','USA',NULL,2013,'Better living thru ''Bots')," +
		"('frc4548',4548,'Omak FFA Wonders',NULL," +
		" 'Omak','WA','USA',NULL,2013,NULL)," +
		"('frc4549',4549,'Iron Bulls','http://www.ssprobotics.com'," +
		" 'South Saint Paul','Minnesota','USA',NULL,2013,'Charge Onward')," +
		"('frc4550',4550,'Something''s Bruin','http://www.somethingsbruin.org'," +
		" 'Greenwood Village ','Colorado','USA',NULL,2013,NULL)," +
		"('frc4551',4551,'Robotic Rangers',NULL," +
		" 'Chicago','IL','USA',NULL,2013,NULL)," +
		"('frc4552',4552,'Robotic Tigers',NULL," +
		" 'Slaton','TX','USA',NULL,2013,NULL)," +
		"('frc4553',4553,'Patriobots','http://www.heritageacademypatriobotics.weebly.com'," +
		" 'Columbus','Mississippi','USA',NULL,2013,NULL)," +
		"('frc4554',4554,'Bad News Bots',NULL," +
		" 'Knoxville','TN','USA',NULL,2013,NULL)," +
		"('frc4555',4555,'Sprocketology','http://www.firstinspires.org/'," +
		" 'Skowhegan','Maine','USA',NULL,2013,NULL)," +
		"('frc4556',4556,'TBD',NULL," +
		" 'Riverside','CA','USA',NULL,2013,NULL)," +
		"('frc4557',4557,'FullMetal Falcons','http://fullmetalfalcons.org'," +
		" 'Middletown','Connecticut','USA',NULL,2013,'FullMetal Falcons \"Laying Foundations ...\"')," +
		"('frc4558',4558,'Jagernauts',NULL," +
		" 'New Braunfels','TX','USA',NULL,NULL,NULL)," +
		"('frc4559',4559,'FIRST DRaFT','http://firstrobotics.digipen.edu'," +
		" 'Redmond','WA','USA',NULL,2013,NULL)," +
		"('frc4560',4560,'Gatordone',NULL," +
		" 'Federal Way','WA','USA',NULL,2013,NULL)," +
		"('frc4561',4561,'TerrorBytes','http://terrorbytes.org/'," +
		" 'Research Triangle Park','North Carolina','USA',NULL,2013,'Be a team player.	Respect teammates. Show pride in RTHS.')," +
		"('frc4562',4562,'RIOT','http://www.firstinspires.org/'," +
		" 'Dunnellon','Florida','USA',NULL,2013,NULL)," +
		"('frc4563',4563,'ECR Robotics',NULL," +
		" 'Woodland Hills','CA','USA',NULL,2013,NULL)," +
		"('frc4564',4564,'Orange Chaos','http://brewerfirstrobotics.com'," +
		" 'Brewer','Maine','USA',NULL,2013,NULL)," +
		"('frc4565',4565,'Skyline Robotics','http://www.mpsaz.org/skyline/clubs/robotics/'," +
		" 'Mesa','Arizona','USA',NULL,2013,NULL)," +
		"('frc4566',4566,'TERAdactyls','http://www.chhsrobotics.org'," +
		" 'Suwanee','GA','USA',NULL,2013,NULL)," +
		"('frc4567',4567,'The Mechanized Mafia','http://www.firstinspires.org/'," +
		" 'Seaford','New York','USA',NULL,2013,NULL)," +
		"('frc4568',4568,'Robot Commander','http://www.firstinspires.org/'," +
		" 'North Adams','Michigan','USA',NULL,2013,'Learn together, work together, accomplish much.')," +
		"('frc4569',4569,'Phoenix',NULL," +
		" 'N. Las Vegas','NV','USA',NULL,NULL,NULL)," +
		"('frc4570',4570,'Estacado Robodors','Coming Soon'," +
		" 'Lubbock','Texas','USA',NULL,2013,NULL)," +
		"('frc4571',4571,'Rambots','http://www.firstinspires.org/'," +
		" 'New York','New York','USA',NULL,2013,NULL)," +
		"('frc4572',4572,'BArlow RobAutics','http://www.barlowrobotics.com'," +
		" 'Redding','Connecticut','USA',NULL,2013,NULL)," +
		"('frc4573',4573,'SRNJRambotics','https://www.rambotics4573.club'," +
		" 'South River','New Jersey','USA',NULL,2013,NULL)," +
		"('frc4574',4574,'Average Joe''s','http://www.sunnyhills-engineering.net'," +
		" 'Fullerton','California','USA',NULL,2013,NULL)," +
		"('frc4575',4575,'The Tin Mints','http://www.tinmints.net'," +
		" 'Prospect Park','Pennsylvania','USA',NULL,2013,'We Can Do It !!')," +
		"('frc4576',4576,'Red Nation Robotics','https://www.rednation4576.com/'," +
		" 'Knoxville','Tennessee','USA',NULL,2013,NULL)," +
		"('frc4577',4577,'RoboFiends',NULL," +
		" 'Vilonia','AR','USA',NULL,2013,NULL)," +
		"('frc4578',4578,'CometBots','http://www.firstinspires.org/'," +
		" 'Los Angeles','California','USA',NULL,2013,NULL)," +
		"('frc4579',4579,'RoboEagles','http://www.firstinspires.org/'," +
		" 'Federal Way','Washington','USA',NULL,2013,NULL)," +
		"('frc4580',4580,'Conductors','http://www.firstinspires.org/'," +
		" 'Indianapolis','Indiana','USA',NULL,2013,NULL)," +
		"('frc4581',4581,'Rangeview Robotics',NULL," +
		" 'Aurora','CO','USA',NULL,2013,NULL)," +
		"('frc4582',4582,'Robohawks ','Coming Soon'," +
		" 'Myrtle Beach','South Carolina','USA',NULL,2013,NULL)," +
		"('frc4583',4583,'Robosaurus','http://teamrobosaurus.com'," +
		" 'San Diego','California','USA',NULL,2013,'Catch Phrase')," +
		"('frc4584',4584,'SentinelFox Engineering','https://es-la.facebook.com/sentinelfox4584/'," +
		" 'Mexicali','Baja California','Mexico',NULL,2013,NULL)," +
		"('frc4585',4585,'Husky Robotics','http://www.huskyrobotics.org'," +
		" 'Midvale','Utah','USA',NULL,2013,'Close isn''t good enough')," +
		"('frc4586',4586,'PRIMO','http://primo4586.co.il/'," +
		" 'modiin','HaMerkaz','Israel',NULL,2013,'Promoting science and technology in the community through personal example')," +
		"('frc4587',4587,'Jersey Voltage','http://www.jerseyvoltage.com'," +
		" 'Houston','Texas','USA',NULL,2013,'It''s all about the Voltage')," +
		"('frc4588',4588,'LVHS Tech Club',NULL," +
		" 'Lander','WY','USA',NULL,NULL,NULL)," +
		"('frc4589',4589,'AdamasBots',NULL," +
		" 'Houston','TX','USA',NULL,2013,'Knowledge is Power')," +
		"('frc4590',4590,'GreenBlitz','http://www.firstinspires.org/'," +
		" 'Hakfar Hayarok','Tel-Aviv','Israel',NULL,2013,'The Power To Inspire')," +
		"('frc4591',4591,'Hunchbacks',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2013,NULL)," +
		"('frc4592',4592,'M3 Mighty Mechanical Mustangs','http://www.m3robo.com'," +
		" 'Hollywood','Florida','USA',NULL,2013,'Think, Build, Believe')," +
		"('frc4593',4593,'Rapid Acceleration','http://www.rapidacceleration.org'," +
		" 'Rapid City','South Dakota','USA',NULL,2013,NULL)," +
		"('frc4594',4594,'Phéminix','http://www.firstinspires.org/'," +
		" 'Montreal','Québec','Canada',NULL,2013,NULL)," +
		"('frc4595',4595,'Infinity','http://www.firstinspires.org/'," +
		" 'Windsor','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4596',4596,'Hackers',NULL," +
		" 'Salt Lake City','UT','USA',NULL,2013,NULL)," +
		"('frc4597',4597,'SABERBOTS','http://www.firstinspires.org/'," +
		" 'Edinburg','Texas','USA',NULL,2013,NULL)," +
		"('frc4598',4598,'ICARUS INNOVATED','http://www.team4598.com'," +
		" 'Sandy','Utah','USA',NULL,2013,'We''ll never burn up again!')," +
		"('frc4599',4599,'Team Fortress',NULL," +
		" 'LAND O'' LAKES','FL','USA',NULL,NULL,NULL)," +
		"('frc4600',4600,'Tech Titans','http://www.firstinspires.org/'," +
		" 'Saint Louis','Missouri','USA',NULL,2013,NULL)," +
		"('frc4601',4601,'Circuit Birds','http://CircuitBirds.com'," +
		" 'Canfield','Ohio','USA',NULL,2013,'That''s how you do it !')," +
		"('frc4602',4602,'Calgary First Nation team',NULL," +
		" 'Calgary','AB','Canada',NULL,NULL,NULL)," +
		"('frc4603',4603,' Leones Francés ','http:///FirstLeones Francés '," +
		" 'Gomez Palacio ','Durango','Mexico',NULL,2013,NULL)," +
		"('frc4604',4604,'Intimitrons from Area 51','http://www.intimitrons.ca'," +
		" 'Calgary','Alberta','Canada',NULL,2013,NULL)," +
		"('frc4605',4605,'Calgary Ismaili Team',NULL," +
		" 'Calgary','AB','Canada',NULL,NULL,NULL)," +
		"('frc4606',4606,'Team 4606, \"Larry\"',NULL," +
		" 'Calgary','AB','Canada',NULL,2013,NULL)," +
		"('frc4607',4607,'C.I.S.','http://www.frc4607cis.com'," +
		" 'Becker','Minnesota','USA',NULL,2013,'Innovation cannot happen in isolation.')," +
		"('frc4608',4608,'Duct Tape Warriors','http://www.firstinspires.org/'," +
		" 'Tekoa','Washington','USA',NULL,2013,NULL)," +
		"('frc4609',4609,'Mechanicatz',NULL," +
		" 'Pawtucket','RI','USA',NULL,2013,NULL)," +
		"('frc4610',4610,'BearTecs','https://sites.google.com/view/beartecs4610/home'," +
		" 'Bastrop','Texas','USA',NULL,2013,'It''s the courage to continue that counts.')," +
		"('frc4611',4611,'OZone Robotics','http://www.olentangyfrc.org'," +
		" 'Lewis Center','Ohio','USA',NULL,2013,'Building Quality Robots Since 2013')," +
		"('frc4612',4612,'RoboRockets','http://www.firstinspires.org/'," +
		" 'oklahoma city','Oklahoma','USA',NULL,2013,NULL)," +
		"('frc4613',4613,'Barker Redbacks','http://team4613.org/'," +
		" 'Sydney','New South Wales','Australia',NULL,2013,'\"Overcome the challenge\"')," +
		"('frc4614',4614,'Purple Monkey Dishwasher','http://www.firstinspires.org/'," +
		" 'St Marys','New South Wales','Australia',NULL,2013,NULL)," +
		"('frc4615',4615,'Steel Trojans','http://frcteam4615.weebly.com'," +
		" 'Chambersburg','Pennsylvania','USA',NULL,2013,NULL)," +
		"('frc4616',4616,'Team Pharaohs','http://pharaohs4616.com'," +
		" 'San Diego','California','USA',NULL,2013,'Ask Nothing in Return')," +
		"('frc4617',4617,'DAUN','http://www.firstinspires.org/'," +
		" 'London','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4618',4618,'Newman Robotics','http://newmanrobotics.ca'," +
		" 'Stoney Creek','Ontario','Canada',NULL,2013,'novarum ~ innovate')," +
		"('frc4619',4619,'HawkBots','http://team4619.com'," +
		" 'Huntington Beach','California','USA',NULL,2013,'\"We totally know where our stuff is.\"')," +
		"('frc4620',4620,'Tec de Monterrey Robotics',NULL," +
		" 'Mexico City','DIF','Mexico',NULL,NULL,NULL)," +
		"('frc4621',4621,'Yotta Robota','http://yottarobota.com'," +
		" 'Roswell','GA','USA',NULL,2013,NULL)," +
		"('frc4622',4622,'Academy at the Lakes Wildcats','http://academyatthelakes.org'," +
		" 'Land O Lakes','Florida','USA',NULL,2013,'Standing on the shoulders of giants.')," +
		"('frc4623',4623,'Flyer Robotics','http://www.firstinspires.org/'," +
		" 'Little Falls','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4624',4624,'Rebel Alliance','http://owatonna4624.wixsite.com/4624'," +
		" 'Owatonna','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4625',4625,'Trappers','https://www.facebook.com/fmcpn.ab'," +
		" 'Fort McMurray','Alberta','Canada',NULL,2013,'I do and understand ')," +
		"('frc4626',4626,'Iron Centaurs','http://www.firstinspires.org/'," +
		" 'Brooklyn Center','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4627',4627,'Manning Robotics','http://4627.ca'," +
		" 'Calgary','Alberta','Canada',NULL,2013,'Lead, Learn, Innovate')," +
		"('frc4628',4628,'Sintian Robotics','http://www.firstinspires.org/'," +
		" 'Hamden','Connecticut','USA',NULL,2013,'Trust me, I got this')," +
		"('frc4629',4629,'Dorado Bots',NULL," +
		" 'Tucson','Arizona','USA',NULL,2013,NULL)," +
		"('frc4630',4630,'Robodragons','http://www.firstinspires.org/'," +
		" 'Clinton','Tennessee','USA',NULL,2013,NULL)," +
		"('frc4631',4631,'Tsuu T''ina',NULL," +
		" 'Tssuu T''ina','AB','Canada',NULL,2013,NULL)," +
		"('frc4632',4632,'Monti-Pythons','http://theburts2010.wix.com/themontipythons'," +
		" 'Monticello','Minnesota','USA',NULL,2013,'The quest for the Holy Robot')," +
		"('frc4633',4633,'Bobcats','http://www.firstinspires.org/'," +
		" 'Calgary','Alberta','Canada',NULL,2013,NULL)," +
		"('frc4634',4634,'The Wolfbotz','http://www.firstinspires.org/'," +
		" 'Calgary','Alberta','Canada',NULL,2013,NULL)," +
		"('frc4635',4635,'PrepaTec - Botbusters','http://botbusters.rocks'," +
		" 'Monterrey','Nuevo León','Mexico',NULL,2013,'Inspired and decided!')," +
		"('frc4636',4636,'PHRD Robotics Team',NULL," +
		" 'Westlock','AB','Canada',NULL,NULL,NULL)," +
		"('frc4637',4637,'BambieBotz','http://www.firstinspires.org/'," +
		" 'Philadelphia','Pennsylvania','USA',NULL,2013,'We saved our school in six weeks... We can build a robot in six weeks')," +
		"('frc4638',4638,'Jagbots','http://www.jagbotics.org'," +
		" 'Germantown','Maryland','USA',NULL,2013,NULL)," +
		"('frc4639',4639,'The RoboSpartans','http://www.team4639.org'," +
		" 'Katy','Texas','USA',NULL,2013,NULL)," +
		"('frc4640',4640,'Metallic Panthers 4640','http://www.firstinspires.org/'," +
		" 'new york','New York','USA',NULL,2013,NULL)," +
		"('frc4641',4641,'United','http://shocknawe4641.wix.com/redoakrobotics4641'," +
		" 'Red Oak','Texas','USA',NULL,2013,'It''s not that we are number one, it''s that we are one.')," +
		"('frc4642',4642,'Rebels',NULL," +
		" 'Kingsport','TN','USA',NULL,2013,NULL)," +
		"('frc4643',4643,'Butte Built Bots','http://www.buttebuiltbots.org'," +
		" 'Oroville','California','USA',NULL,2013,'To Inspire & Empower')," +
		"('frc4644',4644,'Dragonbots',NULL," +
		" 'Mountainburg','AR','USA',NULL,NULL,NULL)," +
		"('frc4645',4645,'The Chicago Style BotDogs','http://chicagostylebotdogs.com'," +
		" 'Chicago','Illinois','USA',NULL,2013,NULL)," +
		"('frc4646',4646,'Team ASAP ','http://www.team4646.org/'," +
		" 'Des Moines','Iowa','USA',NULL,2013,NULL)," +
		"('frc4647',4647,'Vista Vortex',NULL," +
		" 'Tecumseh','ON','Canada',NULL,NULL,NULL)," +
		"('frc4648',4648,'Techno-Tech','http://technotechrobotics.com/technotech/'," +
		" 'Jordan','Minnesota','USA',NULL,2013,'GO T SQUARED!')," +
		"('frc4649',4649,'HeavyMech','http://www.firstinspires.org/'," +
		" 'TEL AVIV','Tel-Aviv','Israel',NULL,2013,NULL)," +
		"('frc4650',4650,'The Vo',NULL," +
		" 'Newark','NJ','USA',NULL,2013,NULL)," +
		"('frc4651',4651,'FrostBots',NULL," +
		" 'Eyota','MN','USA',NULL,2013,NULL)," +
		"('frc4652',4652,'Ironmen 2',NULL," +
		" 'Ramsey','New Jersey','USA',NULL,2013,NULL)," +
		"('frc4653',4653,'Ironmen Robotics','http://www.firstinspires.org/'," +
		" 'Ramsey','New Jersey','USA',NULL,2013,NULL)," +
		"('frc4654',4654,'Mountaineers','http://www.firstinspires.org/'," +
		" 'Deming','Washington','USA',NULL,2013,NULL)," +
		"('frc4655',4655,'Stateline Robotics','http://statelinerobotics.wixsite.com/stateline4655'," +
		" 'Rockton','Illinois','USA',NULL,2013,NULL)," +
		"('frc4656',4656,'Rock Solid Robotics','http://twoharborsrobotics.com'," +
		" 'Two Harbors','Minnesota','USA',NULL,2013,'Is it square?')," +
		"('frc4657',4657,'Saints Robotics','https://sites.google.com/a/scfschools.com/scf-and-nasa-robotics/'," +
		" 'Saint Croix Falls','Wisconsin','USA',NULL,2013,'Big things come in small packages.')," +
		"('frc4658',4658,'Trojanbots',NULL," +
		" 'Albany','GA','USA',NULL,2013,NULL)," +
		"('frc4659',4659,'Warriors',NULL," +
		" 'Mississauga','ON','Canada',NULL,2013,NULL)," +
		"('frc4660',4660,'----',NULL," +
		" 'Salt Lake City','UT','USA',NULL,NULL,NULL)," +
		"('frc4661',4661,'The Red Pirates','http://www.firstinspires.org/'," +
		" 'bet-hasmonai','HaMerkaz','Israel',NULL,2013,NULL)," +
		"('frc4662',4662,'Byte Sized Robotics','https://sites.google.com/view/bytesized4662/home'," +
		" 'Scappoose','Oregon','USA',NULL,2013,'Keep It Simple')," +
		"('frc4663',4663,'Cyber Tigers','http://www.firstinspires.org/'," +
		" 'Belle Plaine','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4664',4664,'Butler Bots','http://www.firstinspires.org/'," +
		" 'Andover','Minnesota','USA',NULL,2013,'We do what we must because we can.')," +
		"('frc4665',4665,'Predators','http://predators4665.org'," +
		" 'Glencoe','Minnesota','USA',NULL,2013,'Powered by Bacon')," +
		"('frc4666',4666,'Robowolves','http://robowolves.com'," +
		" 'Roseville','CA','USA',NULL,2013,NULL)," +
		"('frc4667',4667,'Synergy','http://www.synergy4667.com'," +
		" 'Cuernavaca','MOR','Mexico',NULL,2013,NULL)," +
		"('frc4668',4668,'Spartans',NULL," +
		" 'Slidell','LA','USA',NULL,NULL,NULL)," +
		"('frc4669',4669,'Galileo Robotics','http://www.firstinspires.org/'," +
		" 'San Francisco','California','USA',NULL,2013,NULL)," +
		"('frc4670',4670,'The Pirates','http://www.southsideisd.org'," +
		" 'San Antoino','Texas','USA',NULL,2013,NULL)," +
		"('frc4671',4671,'Mechaneers','http://www.pierz.k12.mn.us'," +
		" 'Pierz','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4672',4672,'Youth in Black',NULL," +
		" 'San Antonio','TX','USA',NULL,2013,NULL)," +
		"('frc4673',4673,'Solar Engineering',NULL," +
		" 'Mexicali','BCN','Mexico',NULL,2013,NULL)," +
		"('frc4674',4674,'Robojacks','http://www.firstinspires.org/'," +
		" 'Bemidji','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4675',4675,'Eagles',NULL," +
		" 'North Olmsted','OH','USA',NULL,2013,NULL)," +
		"('frc4676',4676,'Battery Chargers','http://www.firstinspires.org/'," +
		" 'Palos Hills','Illinois','USA',NULL,2013,NULL)," +
		"('frc4677',4677,'RHHS Robotics Team',NULL," +
		" 'Richmond Hill','ON','Canada',NULL,NULL,NULL)," +
		"('frc4678',4678,'CyberCavs','http://www.cybercavs.com'," +
		" 'Breslau','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4679',4679,'Dragonators',NULL," +
		" 'San Benito','TX','USA',NULL,2013,NULL)," +
		"('frc4680',4680,'AzTech Eagles ','http://www.facebook.com/AzTechEagles/'," +
		" 'Detroit','Michigan','USA',NULL,2013,'Bark Bark - Woof Woof ')," +
		"('frc4681',4681,'Murphy''s law','http://www.firstinspires.org/'," +
		" 'Everett','Washington','USA',NULL,2013,NULL)," +
		"('frc4682',4682,'BraveBots','http://teambravebots.com/'," +
		" 'Seattle','Washington','USA',NULL,2013,'Brave in All We Do')," +
		"('frc4683',4683,'Full Metal Robotics','http://www.firstinspires.org/'," +
		" 'Marysville ','Washington','USA',NULL,2013,'Designing the Future')," +
		"('frc4684',4684,'eSharks',NULL," +
		" 'Yonkers','New York','USA',NULL,2013,NULL)," +
		"('frc4685',4685,'Aztlavolt','http://aztlavoltfrc.wixsite.com/aztlavolt-4685'," +
		" 'Azcapotzalco','Distrito Federal','Mexico',NULL,2013,'Working for a Technological Culture')," +
		"('frc4686',4686,'Nay Ah Shing',NULL," +
		" 'Onamia','MN','USA',NULL,NULL,NULL)," +
		"('frc4687',4687,'Spartans','http://www.firstinspires.org/'," +
		" 'Long Lake','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4688',4688,'Saints Bot','http://saintsbot.com'," +
		" 'Belle River','Ontario','Canada',NULL,2013,'Safety is First')," +
		"('frc4689',4689,'ROyal BOTS',NULL," +
		" 'Pattison','TX','USA',NULL,NULL,NULL)," +
		"('frc4690',4690,'WD-40',NULL," +
		" 'indianapolis','IN','USA',NULL,2013,NULL)," +
		"('frc4691',4691,'Falcons',NULL," +
		" 'Mississauga','ON','Canada',NULL,NULL,NULL)," +
		"('frc4692',4692,'Metal Mallards','http://www.firstinspires.org/'," +
		" 'Toutle','Washington','USA',NULL,2013,NULL)," +
		"('frc4693',4693,'B.O.B.','https://sites.google.com/rockford.k12.mn.us/waltersl/robotics'," +
		" 'Rockford','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4694',4694,'Atomic Armadillos','https://armadillos.sleetgate.com/home/'," +
		" 'Amarillo','Texas','USA',NULL,2013,'Engineers Design the World!')," +
		"('frc4695',4695,'Chowchilla Robotics',NULL," +
		" 'Chowchilla','CA','USA',NULL,2013,NULL)," +
		"('frc4696',4696,'RoboAzTechs','http://www.roboaztechs.org'," +
		" 'el paso','Texas','USA',NULL,2013,NULL)," +
		"('frc4697',4697,'Tigers',NULL," +
		" 'Globe','AZ','USA',NULL,NULL,NULL)," +
		"('frc4698',4698,'Raider Robotics','http://frc4698.weebly.com/'," +
		" 'Sacramento','California','USA',NULL,2013,NULL)," +
		"('frc4699',4699,'ESCT-Flammes',NULL," +
		" 'Timmins','ON','Canada',NULL,2013,NULL)," +
		"('frc4700',4700,'Wolves',NULL," +
		" 'Battle Creek','MI','USA',NULL,NULL,NULL)," +
		"('frc4701',4701,'Warriors (Team W.I.R.E.)','http://hvjrobotics.weebly.com'," +
		" 'Savannah','Georgia','USA',NULL,2013,'Send Help!')," +
		"('frc4702',4702,'CyberDoggz','http://www.firstinspires.org/'," +
		" 'Chicago','Illinois','USA',NULL,2013,NULL)," +
		"('frc4703',4703,'Robotic Gigabots',NULL," +
		" 'Menahga','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4704',4704,'Northern Lights Robotics','http://dev.4704.ca'," +
		" 'Timmins','Ontario','Canada',NULL,2013,'We Light Up The North')," +
		"('frc4705',4705,'Full Metal Kats',NULL," +
		" 'Fontana','CA','USA',NULL,2013,NULL)," +
		"('frc4706',4706,'Bulls',NULL," +
		" 'Miami','FL','USA',NULL,2013,NULL)," +
		"('frc4707',4707,'TEAM FORCE','http://www.firstinspires.org/'," +
		" 'Santo Domingo','Santo Domingo','Dominican Republic',NULL,2013,NULL)," +
		"('frc4708',4708,'G-Force',NULL," +
		" 'Nave Eitan','Z','Israel',NULL,2013,NULL)," +
		"('frc4709',4709,'Stem-azing',NULL," +
		" 'Riverside','CA','USA',NULL,NULL,NULL)," +
		"('frc4710',4710,'Bot-Busters','http://bot-busters.co.cc'," +
		" 'Shingle Springs','CA','USA',NULL,NULL,NULL)," +
		"('frc4711',4711,'The Flying Aces','http://flyingaces4711.weebly.com'," +
		" 'Camarillo','California','USA',NULL,2013,'Fortes Fortuna Adiuvat')," +
		"('frc4712',4712,'TracBots',NULL," +
		" 'Kibbutz Neve Eitan','HaZafon (Northern)','Israel',NULL,2013,NULL)," +
		"('frc4713',4713,'Adom Robotics',NULL," +
		" 'Istanbul','34','Turkey',NULL,2013,NULL)," +
		"('frc4714',4714,'The Millennium Falcons','http://www.firstinspires.org/'," +
		" 'Richmond','Virginia','USA',NULL,2013,NULL)," +
		"('frc4715',4715,'Big Bay de Noc Bear Bots','http://www.firstinspires.org/'," +
		" 'Cooks','Michigan','USA',NULL,2013,NULL)," +
		"('frc4716',4716,'Purple Raiders','http://www.firstinspires.org/'," +
		" 'Windsor','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4717',4717,'Westerner Robotics','https://lhsrobotics.weebly.com/'," +
		" 'Lubbock','Texas','USA',NULL,2013,NULL)," +
		"('frc4718',4718,'RoboPanthers','http://robopanthers4718.wix.com/'," +
		" 'Toronto','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4719',4719,'Bulldogs',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2013,NULL)," +
		"('frc4720',4720,'R2JAG2',NULL," +
		" 'Calgary','AB','Canada',NULL,2013,NULL)," +
		"('frc4721',4721,'Tiger Bots','http://mitchelltigerbots.weebly.com/'," +
		" 'Memphis','Tennessee','USA',NULL,2013,'We Don''t Stop Until the Job Is Done!')," +
		"('frc4722',4722,'Cyberian Soldiers',NULL," +
		" 'Rock Hill','SC','USA',NULL,2013,NULL)," +
		"('frc4723',4723,'Tecmilenio - EARTH - 4723','http://www.facebook.com/earth4723'," +
		" 'Puebla','Puebla','Mexico',NULL,2013,NULL)," +
		"('frc4724',4724,'ShakedTech',NULL," +
		" 'Kibbutz  Sde Eliyahu','Z','Israel',NULL,2013,NULL)," +
		"('frc4725',4725,'GeDarcaNator',NULL," +
		" 'Gedera','Southern','Israel',NULL,2013,NULL)," +
		"('frc4726',4726,'Robo Dynasty','http://www.aasd.wednet.edu/site/Default.aspx?PageID=617'," +
		" 'Asotin','Washington','USA',NULL,2013,'Panther Power')," +
		"('frc4727',4727,'RoboFX','http://team4727.tk'," +
		" 'Ottawa','ON','Canada',NULL,2013,NULL)," +
		"('frc4728',4728,'Rocori Rench Reckers','http://N/A'," +
		" 'Cold Spring','Minnesota','USA',NULL,2013,'N/A')," +
		"('frc4729',4729,'EMUs (Experimental Mayhem Unit)','https://www.first.unsw.edu.au'," +
		" 'Sydney','New South Wales','Australia',NULL,2013,NULL)," +
		"('frc4730',4730,'Terminators','http://www.firstinspires.org/'," +
		" 'Albany','Georgia','USA',NULL,2013,NULL)," +
		"('frc4731',4731,'PrepaTec - MONARCH-E','http://www.monarche4731.com'," +
		" 'Morelia','Michoacán','Mexico',NULL,2013,'\"Sin Imposibles\"')," +
		"('frc4732',4732,'SWC Robotics','http://4732.ca/'," +
		" 'Thunder Bay','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4733',4733,'Scarlett Robotics','http://www.scarlettrobotics.com'," +
		" 'Calgary','Alberta','Canada',NULL,2013,NULL)," +
		"('frc4734',4734,'The Iron Plainsmen','http://www.firstinspires.org/'," +
		" 'Lubbock','Texas','USA',NULL,2013,NULL)," +
		"('frc4735',4735,'Tecmilenio - DEROF - 4735','https://www.derof.mx'," +
		" 'Torreon','Coahuila','Mexico',NULL,2013,'Even in the desert we fly')," +
		"('frc4736',4736,'Temp',NULL," +
		" 'Rancho Cucamonga','CA','USA',NULL,NULL,NULL)," +
		"('frc4737',4737,'Atomigators','http://www.firstinspires.org/'," +
		" 'West Bloomfield','Michigan','USA',NULL,2013,NULL)," +
		"('frc4738',4738,'Patribots','http://frcpatribots.wixsite.com/patriot-robotics'," +
		" 'San Diego','California','USA',NULL,2013,'Robotics is for everyone!')," +
		"('frc4739',4739,'CNTRL F5','http://www.teamctrlf5.com'," +
		" 'Normanhurst','New South Wales','Australia',NULL,2013,NULL)," +
		"('frc4740',4740,'Sparta Bots','http://www.firstinspires.org/'," +
		" 'Nashville','Tennessee','USA',NULL,2013,NULL)," +
		"('frc4741',4741,'WingNuts','http://redwoodareaschools.com/page/4716'," +
		" 'Redwood Falls','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4742',4742,'JaliScience','http://jalisciencezmg.wix.com/jalisciencezmg'," +
		" 'Tlaquepaque','JAL','Mexico',NULL,2013,NULL)," +
		"('frc4743',4743,'STEAM Engine',NULL," +
		" 'Royal Oak','MI','USA',NULL,2013,NULL)," +
		"('frc4744',4744,'Ninjas','http://4744ninjas.wixsite.com/ninjas'," +
		" 'Hadera','Haifa','Israel',NULL,2013,NULL)," +
		"('frc4745',4745,'Mets Robotics','http://www.firstinspires.org/'," +
		" 'little rock','Arkansas','USA',NULL,2013,NULL)," +
		"('frc4746',4746,'Tecmilenio - Alebrijes - 4746','https://www.facebook.com/alebrijes4746frc/'," +
		" 'Monterrey','Nuevo León','Mexico',NULL,2013,'More than a number we are family')," +
		"('frc4747',4747,'Millennium Falcons',NULL," +
		" 'Los Fresnos','TX','USA',NULL,2013,NULL)," +
		"('frc4748',4748,'Bulldog Autobots','Coming Soon'," +
		" 'Charleston','South Carolina','USA',NULL,2013,NULL)," +
		"('frc4749',4749,'Pisgah Robotics','http://www.pisgahrobotics.org/'," +
		" 'Alpharetta','Georgia','USA',NULL,2013,'Building Robots Since 2013')," +
		"('frc4750',4750,'Bert','http://www.eustace.org/robotics'," +
		" 'Pennsauken','New Jersey','USA',NULL,2013,NULL)," +
		"('frc4751',4751,'Robo Warriors',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2013,NULL)," +
		"('frc4752',4752,'Ursus Robotica',NULL," +
		" 'Columbus','OH','USA',NULL,NULL,NULL)," +
		"('frc4753',4753,'Bexley Lions','http://www.firstinspires.org/'," +
		" 'Bexley','Ohio','USA',NULL,2013,NULL)," +
		"('frc4754',4754,'RoBenedicts',NULL," +
		" 'Sudbury','ON','Canada',NULL,2013,NULL)," +
		"('frc4755',4755,'M^3',NULL," +
		" 'Ann Arbor','MI','USA',NULL,2013,NULL)," +
		"('frc4757',4757,'Talos','http://www.firstinspires.org/'," +
		" 'Petach Tikva','HaMerkaz','Israel',NULL,2013,NULL)," +
		"('frc4758',4758,'RoboBlazers','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2013,NULL)," +
		"('frc4759',4759,'Devil Robotics',NULL," +
		" 'Hobart','TAS','Australia',NULL,NULL,NULL)," +
		"('frc4760',4760,'Golden Eagles',NULL," +
		" 'Indianapolis','IN','USA',NULL,NULL,NULL)," +
		"('frc4761',4761,'The Robockets','http://robockets.org'," +
		" 'Reading','Massachusetts','USA',NULL,2013,NULL)," +
		"('frc4762',4762,'Robo-Saints','http://www.firstinspires.org/'," +
		" 'St. Charles','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4763',4763,'i to the Fourth Robotics','http://i4th.org'," +
		" 'Fullerton','California','USA',NULL,2013,'We Are One')," +
		"('frc4764',4764,'The Memphis MechWarriors','https://www.memphismechwarriors.org/'," +
		" 'Memphis','Tennessee','USA',NULL,2013,NULL)," +
		"('frc4765',4765,'PWRUP','http://www.firstinspires.org/'," +
		" 'Los Altos Hills','California','USA',NULL,2013,'Where did the ____ go? It can''t have gone far. ')," +
		"('frc4766',4766,'Team 4766',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc4767',4767,'C4','http://www.firstinspires.org/'," +
		" 'Shelby','North Carolina','USA',NULL,2013,'The Bomb!')," +
		"('frc4768',4768,'Comets','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2013,NULL)," +
		"('frc4769',4769,'Nerdvana','https://www.facebook.com/Nerdvana-FRC-Team-4769-593553827373177/'," +
		" 'Saint Petersburg','Florida','USA',NULL,2013,NULL)," +
		"('frc4770',4770,'EDW Robotics',NULL," +
		" 'Thibodaux','LA','USA',NULL,2013,NULL)," +
		"('frc4771',4771,'Match',NULL," +
		" 'Morelia','MIC','Mexico',NULL,NULL,NULL)," +
		"('frc4772',4772,'Optimistic Skyz',NULL," +
		" 'Seattle','WA','USA',NULL,2013,NULL)," +
		"('frc4773',4773,'Steel Horses','http://www.firstinspires.org/'," +
		" 'Bronx','New York','USA',NULL,2013,NULL)," +
		"('frc4774',4774,'The Drop Bears','http://www.thedropbears.org.au'," +
		" 'Sydney','New South Wales','Australia',NULL,2013,'Curious minds, curious machines')," +
		"('frc4775',4775,'Aztech Robotics','http://www.aztechrobotics.com.mx'," +
		" 'Mexico City','Distrito Federal','Mexico',NULL,2013,NULL)," +
		"('frc4776',4776,'S.C.O.T.S. Bots','http://www.scotsbots.org'," +
		" 'Howell','Michigan','USA',NULL,2013,'Service, Character, Opportunity, Teamwork, Spirit')," +
		"('frc4777',4777,'Spirit Robotics',NULL," +
		" 'Mississauga','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4778',4778,'Stormbots','http://www.firstinspires.org/'," +
		" 'Chanhassen','Minnesota','USA',NULL,2013,NULL)," +
		"('frc4779',4779,'RoboSapiens','http://www.firstinspires.org/'," +
		" 'Marine City','Michigan','USA',NULL,2013,'Adapt and Overcome.')," +
		"('frc4780',4780,'Tenacious Drones','http://www.team4780.com'," +
		" 'Cleveland','Ohio','USA',NULL,2013,NULL)," +
		"('frc4781',4781,'RoboSpartans','http://www.firstinspires.org/'," +
		" 'Connersville','Indiana','USA',NULL,2013,NULL)," +
		"('frc4782',4782,'PrepaTec - Borrebots','https://www.facebook.com/Borrebots4782/'," +
		" 'Hermosillo','Sonora','Mexico',NULL,2013,'Sé un héroe. ')," +
		"('frc4783',4783,'RoboRavens','https://roboravens.ca'," +
		" 'Nepean','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4784',4784,'ATOM','http://www.nirhaemek.org.il'," +
		" 'Afula','HaZafon','Israel',NULL,2013,'to win ')," +
		"('frc4785',4785,'Cougar Robotics',NULL," +
		" 'Clovis','CA','USA',NULL,2013,NULL)," +
		"('frc4786',4786,'Nicolet FEAR','http://www.nicoletfear.com'," +
		" 'Milwaukee','Wisconsin','USA',NULL,2013,'\"Let there be nothing to fear but F.E.A.R. itself\"')," +
		"('frc4787',4787,'Axiom','http://northsiderobotics.org'," +
		" 'Chicago','Illinois','USA',NULL,2013,NULL)," +
		"('frc4788',4788,'Ace of Spanners','http://www.firstinspires.org/'," +
		" 'Bentley','Western Australia','Australia',NULL,2013,NULL)," +
		"('frc4789',4789,'VB Electras',NULL," +
		" 'Queens Village','NY','USA',NULL,2013,NULL)," +
		"('frc4790',4790,'Yigal-Alon',NULL," +
		" 'Rishon Le-Zion','TA','Israel',NULL,2013,NULL)," +
		"('frc4791',4791,'Pandroids','http://www.firstinspires.org/'," +
		" 'South Bend','Indiana','USA',NULL,2013,NULL)," +
		"('frc4792',4792,'Desert Storm','http://www.firstinspires.org/'," +
		" 'North Las Vegas','Nevada','USA',NULL,2013,NULL)," +
		"('frc4793',4793,'OOBotics',NULL," +
		" 'old Orchard Beach','ME','USA',NULL,2013,NULL)," +
		"('frc4794',4794,'infinity','http://www.firstinspires.org/'," +
		" 'shaab','Haifa','Israel',NULL,2013,NULL)," +
		"('frc4795',4795,'EastBots','http://www.eastbots.com'," +
		" 'Chapel Hill','North Carolina','USA',NULL,2013,NULL)," +
		"('frc4796',4796,'Cyber Dragons','http://www.firstinspires.org/'," +
		" 'Hyde Park','Massachusetts','USA',NULL,2013,NULL)," +
		"('frc4797',4797,'Bryant HS',NULL," +
		" 'Long Island City','NY','USA',NULL,2013,NULL)," +
		"('frc4798',4798,'Toxic Robos',NULL," +
		" 'Wheeler','Texas','USA',NULL,2013,NULL)," +
		"('frc4799',4799,'Lazbuddie Robotics','http://classroom.lazbuddieisd.org/webs/laz.robotics12/'," +
		" 'Lazbuddie','Texas','USA',NULL,2013,'Building for the future')," +
		"('frc4800',4800,'Rage Against the Machines','http://www.nwctarobotics.com'," +
		" 'Las Vegas','Nevada','USA',NULL,2013,NULL)," +
		"('frc4801',4801,'Blacktown Boys Cybernetic Phoenix','http://www.robotics4801.webs.com'," +
		" 'Blacktown','New South Wales','Australia',NULL,2013,NULL)," +
		"('frc4802',4802,'U.M.M (Unidentified Moving Machines)','https://www.bghs4802.org/'," +
		" 'Blacktown','New South Wales','Australia',NULL,2013,NULL)," +
		"('frc4803',4803,'The Superbots','http://www.wactc.net/First'," +
		" 'Canonsburg','PA','USA',NULL,2013,NULL)," +
		"('frc4804',4804,'Port PiraTech','http://portpiratech.com/'," +
		" 'Port Washington','Wisconsin','USA',NULL,2013,NULL)," +
		"('frc4805',4805,'Robotic Warriors','Coming Soon'," +
		" 'New Orleans','Louisiana','USA',NULL,2013,NULL)," +
		"('frc4806',4806,'Knights',NULL," +
		" 'Orleans','ON','Canada',NULL,2013,NULL)," +
		"('frc4807',4807,'Jag Squad',NULL," +
		" 'Richmond Hill','ON','Canada',NULL,2013,'Together we aspire, together we achieve')," +
		"('frc4808',4808,'Tigers',NULL," +
		" 'Mississauga','ON','Canada',NULL,2013,NULL)," +
		"('frc4809',4809,'Black Knight Robotics','http://www.firstinspires.org/'," +
		" 'Marceline','Missouri','USA',NULL,2013,NULL)," +
		"('frc4810',4810,'I AM Robot','http://www.iamrobot4810.org'," +
		" 'Clinton Township','Michigan','USA',NULL,2013,NULL)," +
		"('frc4811',4811,'Majestic Eagles','http://www.firstinspires.org/'," +
		" 'Madison Heights','Michigan','USA',NULL,2013,'Soaring to the top')," +
		"('frc4812',4812,'Crosby Robotics Team',NULL," +
		" 'Waterbury','CT','USA',NULL,2013,NULL)," +
		"('frc4813',4813,'NOT PAY FOR THIS TEAM',NULL," +
		" 'Shen zhen','44','China',NULL,NULL,NULL)," +
		"('frc4814',4814,'WE MARS Incubator','http://www.firstinspires.org/'," +
		" 'London','Ontario','Canada',NULL,2013,'Linking London together')," +
		"('frc4815',4815,'ELECTROPANTHERS','http://4815clep.com/'," +
		" 'Center Line','Michigan','USA',NULL,2013,NULL)," +
		"('frc4816',4816,'Gadget Girls','http://gadgetgirlsrobotics.weebly.com'," +
		" 'Raleigh','North Carolina','USA',NULL,2013,'Girl Power!')," +
		"('frc4817',4817,'One Degree North','http://frc4817.wordpress.com'," +
		" 'Singapore','Central Singapore','Singapore',NULL,2013,'There is a time for science, and there is a time for hammers!')," +
		"('frc4818',4818,'The Herd','http://wfrobotics.org'," +
		" 'West Fargo','North Dakota','USA',NULL,2013,NULL)," +
		"('frc4819',4819,'Flat Mountain Mechanics','http://www.team4819.com'," +
		" 'Mount Pleasant','Michigan','USA',NULL,2013,'COURAGE')," +
		"('frc4820',4820,'Huey Tlatoani','http://team4820.wix.com/team4820'," +
		" 'Zapopan','JAL','Mexico',NULL,2013,NULL)," +
		"('frc4821',4821,'CyberUS','http://team4821.weebly.com/'," +
		" 'Washington','District of Columbia','USA',NULL,2013,'Why so serious?')," +
		"('frc4822',4822,'Bemidji LumberBots',NULL," +
		" 'Bemidji','MN','USA',NULL,NULL,NULL)," +
		"('frc4823',4823,'Metal Manes','Coming Soon'," +
		" 'Loris','South Carolina','USA',NULL,2013,NULL)," +
		"('frc4824',4824,'BerryBotics','http://lhs.lcsc.k12.in.us/pages/Logansport_High_School/BerryBotics'," +
		" 'Logansport','Indiana','USA',NULL,2013,'What if we build a large wooden badger?')," +
		"('frc4825',4825,'The Coltenoids',NULL," +
		" 'Ottawa','Ontario','Canada',NULL,2013,NULL)," +
		"('frc4826',4826,'Deceptibots',NULL," +
		" 'Harper Woods','MI','USA',NULL,NULL,NULL)," +
		"('frc4827',4827,'Minerbotics','http://www.firstinspires.org/'," +
		" 'Negaunee','Michigan','USA',NULL,2013,NULL)," +
		"('frc4828',4828,'RoboEagles','http://www.roboeagles.org/'," +
		" 'Raleigh','North Carolina','USA',NULL,2013,'Build Strong; Soar High')," +
		"('frc4829',4829,'Titanium Tigers','http://www.firstinspires.org/'," +
		" 'Chapel Hill','North Carolina','USA',NULL,2013,NULL)," +
		"('frc4830',4830,'ROBOT  2E','www.2e.cn'," +
		" 'Shenzhen','44','China',NULL,2013,NULL)," +
		"('frc4831',4831,'FIRST PARTICIPATE',NULL," +
		" 'Shen zhen','44','China',NULL,NULL,NULL)," +
		"('frc4832',4832,'CroBots',NULL," +
		" 'Carencro','LA','USA',NULL,2013,NULL)," +
		"('frc4833',4833,'Bangarang',NULL," +
		" 'Orlando','FL','USA',NULL,2013,NULL)," +
		"('frc4834',4834,'RoboColts',NULL," +
		" 'Detroi','MI','USA',NULL,2013,NULL)," +
		"('frc4835',4835,'Junkyard Dawgs','http://www.firstinspires.org/'," +
		" 'Manistique','Michigan','USA',NULL,2013,'Reduce, Reuse, UPcycle')," +
		"('frc4836',4836,'Zai',NULL," +
		" 'Chihuahua','CHH','Mexico',NULL,2013,NULL)," +
		"('frc4838',4838,'Electro-Bots','https://www.facebook.com/FIRST-4838-The-Electro-Bots-534129123694179/'," +
		" 'River Rouge','Michigan','USA',NULL,2013,NULL)," +
		"('frc4839',4839,'Hematites',NULL," +
		" 'Ishpeming','MI','USA',NULL,2013,NULL)," +
		"('frc4840',4840,'The Panthers','http://www.firstinspires.org/'," +
		" 'Redford','Michigan','USA',NULL,2013,NULL)," +
		"('frc4841',4841,'Optimal Robotics','http://www.optimalrobotics.net'," +
		" 'Tucson','Arizona','USA',NULL,2013,'\"ORION\"')," +
		"('frc4842',4842,'Botrregos','http://www.botrregos4842.com'," +
		" 'Chihuahua','Chihuahua','Mexico',NULL,2013,'Teamwork makes the dream work')," +
		"('frc4843',4843,'Mystery Science Robotics',NULL," +
		" 'Gentry','AR','USA',NULL,NULL,NULL)," +
		"('frc4844',4844,'The Eagles',NULL," +
		" 'Muskegon','MI','USA',NULL,NULL,NULL)," +
		"('frc4845',4845,'Lion''s Pride','http://Lakeview Robotics - Lion''s Pride (Facebook)'," +
		" 'Duluth','Minnesota','USA',NULL,2013,'Do Your Best!')," +
		"('frc4846',4846,'G.I. Frost Bots','http://www.firstinspires.org/'," +
		" 'Gary','Indiana','USA',NULL,2013,'Keep Calm and Stay Frosty')," +
		"('frc4847',4847,'Techno-Logic Trojans','Coming Soon'," +
		" 'Green Sea','South Carolina','USA',NULL,2013,'No Limits')," +
		"('frc4848',4848,'Black Knights',NULL," +
		" 'Jacksonville','AR','USA',NULL,2013,NULL)," +
		"('frc4849',4849,'Rock City Robots','www.RockCityRobots.com'," +
		" 'Little Rock','AR','USA',NULL,2013,NULL)," +
		"('frc4850',4850,'Mr. Parks'' Robotics',NULL," +
		" 'Saint Paul','MN','USA',NULL,2013,NULL)," +
		"('frc4851',4851,'JungleBots','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2013,NULL)," +
		"('frc4852',4852,'The Eliminators','Coming Soon'," +
		" 'Shallowater','Texas','USA',NULL,2013,NULL)," +
		"('frc4853',4853,'RoboCats','http://Firstinidalou.org'," +
		" 'Idalou','TX','USA',NULL,2013,NULL)," +
		"('frc4854',4854,'Trobots','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2013,NULL)," +
		"('frc4855',4855,'Ramageddon','http://www.ramageddon.com'," +
		" 'South Haven','Michigan','USA',NULL,2013,NULL)," +
		"('frc4856',4856,'Double X','http://robotics.brearley.org'," +
		" 'New York','New York','USA',NULL,2013,NULL)," +
		"('frc4857',4857,'Jets',NULL," +
		" 'Powers','MI','USA',NULL,NULL,NULL)," +
		"('frc4858',4858,'Leopards',NULL," +
		" 'New Home','TX','USA',NULL,NULL,NULL)," +
		"('frc4859',4859,'The CyBears','http://byronrobotics.com/'," +
		" 'Byron','Minnesota','USA',NULL,2013,'Robotics is for Everyone ')," +
		"('frc4900',4900,'Magic Island Robotics',NULL," +
		" 'Florianopolis','SC','Brazil',NULL,NULL,NULL)," +
		"('frc4901',4901,'Garnet Squadron','http://www.garnetsquadron.com/'," +
		" 'Columbia','South Carolina','USA',NULL,2014,'Inspiration is on our radar, is it on yours?')," +
		"('frc4902',4902,'The Wildebots','http://www.firstinspires.org/'," +
		" 'Burlington','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4903',4903,'Mustangs','http://www.masseyrobotics.weebly.com'," +
		" 'Windsor','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4904',4904,'Bot-Provoking','http://www.botprovoking.org'," +
		" 'San Mateo','California','USA',NULL,2014,NULL)," +
		"('frc4905',4905,'Andromeda One','https://frc4905.com/'," +
		" 'Ayer','Massachusetts','USA',NULL,2014,'Let''s build something amazing!')," +
		"('frc4906',4906,'The Collective','http://www.team4906.com'," +
		" 'Waterville','Maine','USA',NULL,2014,'Adapt and Overcome ')," +
		"('frc4907',4907,'Thunderstamps','http://www.firstinspires.org/'," +
		" 'St. Thomas','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4908',4908,'Duxbury Dragons','http://www.firstinspires.org/'," +
		" 'Duxbury','Massachusetts','USA',NULL,2014,NULL)," +
		"('frc4909',4909,'Bionics','http://www.team4909.org/'," +
		" 'Billerica','Massachusetts','USA',NULL,2014,NULL)," +
		"('frc4910',4910,'East Cobb Robotics','http://www.eastcobbrobotics.org'," +
		" 'Marietta','Georgia','USA',NULL,2014,NULL)," +
		"('frc4911',4911,'CyberKnights','http://cyberknights4911.com/'," +
		" 'Seattle','Washington','USA',NULL,2014,NULL)," +
		"('frc4912',4912,'Panthers',NULL," +
		" 'Chattanooga','TN','USA',NULL,2014,NULL)," +
		"('frc4913',4913,'Huskyteers','http://www.huskyteers4913.com/'," +
		" 'Anaheim','California','USA',NULL,2014,'One for All and All for One')," +
		"('frc4914',4914,'Panthers','http://robotics.victoriaparkci.ca'," +
		" 'North York','Ontario','Canada',NULL,2014,'There''s Something for Everyone')," +
		"('frc4915',4915,'Spartronics','http://spartronics4915.com/'," +
		" 'Bainbridge Island','Washington','USA',NULL,2014,'STEAM Ahead !')," +
		"('frc4916',4916,'H.V.L Robotics',NULL," +
		" 'Gravatai','RS','Brazil',NULL,NULL,NULL)," +
		"('frc4917',4917,'Sir Lancerbot','http://www.4917.ca'," +
		" 'Elmira','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4918',4918,'The Roboctopi','https://frc4918.github.io/webpage/'," +
		" 'Port Townsend','Washington','USA',NULL,2014,NULL)," +
		"('frc4919',4919,'Leodroids','http://www.firstinspires.org/'," +
		" 'Lemon Grove','California','USA',NULL,2014,NULL)," +
		"('frc4920',4920,'Belle River Automatons','http://team4920.weebly.com/'," +
		" 'Belle River','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4921',4921,'HDHS HAWKTOBOTS','http://www.firstinspires.org/'," +
		" 'Harrow','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4922',4922,'Tartans',NULL," +
		" 'Windsor','ON','Canada',NULL,2014,NULL)," +
		"('frc4923',4923,'LaserBots','http://laserrobotics.wix.com/laserbots#!oursponsors/ce9v'," +
		" 'Windsor','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4924',4924,'STEM Pythons','http://www.firstinspires.org/'," +
		" 'Chattanooga','Tennessee','USA',NULL,2014,NULL)," +
		"('frc4925',4925,'The Resistance ','https://frcteam4925.weebly.com/'," +
		" 'Wolfeboro','New Hampshire','USA',NULL,2014,NULL)," +
		"('frc4926',4926,'GalacTech','http://www.team4926.org/home'," +
		" 'Columbus','Indiana','USA',NULL,2014,NULL)," +
		"('frc4927',4927,'Marauders',NULL," +
		" 'Fortville','IN','USA',NULL,2014,NULL)," +
		"('frc4928',4928,'Arabian Knights','http://www.facebook.com/FRC4928'," +
		" 'Al Ain','Abu Z¸aby (Abu Dhabi)','United Arab Emirates',NULL,2014,NULL)," +
		"('frc4929',4929,'Maroon Monsoon','http://www.firstinspires.org/'," +
		" 'Haverhill','Massachusetts','USA',NULL,2014,'Success is measured by the strength of our team')," +
		"('frc4930',4930,'Electric Mayhem','http://robotics.nicholsschool.org/'," +
		" 'Buffalo','New York','USA',NULL,2014,NULL)," +
		"('frc4931',4931,'Edwardsville Technologies','http://www.evilletech.com'," +
		" 'Edwardsville','Illinois','USA',NULL,2014,NULL)," +
		"('frc4932',4932,'Cougar Robotics','https://4932cougarrobotics.wordpress.com/'," +
		" 'Leamington','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4933',4933,'Code Green','http://jhanun.wix.com/greenmachine4933'," +
		" 'Okmulgee','Oklahoma','USA',NULL,2014,'Go Green')," +
		"('frc4934',4934,'Gear Warriors',NULL," +
		" 'Dallas','TX','USA',NULL,NULL,NULL)," +
		"('frc4935',4935,'T-Rex','http://www.trex4935.com'," +
		" 'Charlotte','North Carolina','USA',NULL,2014,'Leaving an Imprint')," +
		"('frc4936',4936,'Viral Vortex','https://vistaviralvortex.wixsite.com/49364936'," +
		" 'Tecumseh','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4937',4937,'Santa Cruz Robotics',NULL," +
		" 'Santa Cruz do Sul','RS','Brazil',NULL,NULL,NULL)," +
		"('frc4938',4938,'Falcons','http://www.firstinspires.org/'," +
		" 'Windsor','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4939',4939,'Allspark9','http://Team4939.ca'," +
		" 'Brampton','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4940',4940,'Knight Vision','http://www.knightvisionrobotics.com'," +
		" 'Windsor','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4941',4941,'RoboBibb','http://www.firstinspires.org/'," +
		" 'Macon','Georgia','USA',NULL,2014,NULL)," +
		"('frc4942',4942,'Spartech','http://www.firstinspires.org/'," +
		" 'Sherbrooke','Québec','Canada',NULL,2014,NULL)," +
		"('frc4943',4943,'\"R Cubed\"  ','http://www.firstinspires.org/'," +
		" 'Shelburne','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4944',4944,'The Hi Fives','http://thehifives.co'," +
		" 'Grand Junction','Colorado','USA',NULL,2014,NULL)," +
		"('frc4945',4945,'Titanium-Wrecks','http://www.team-t-wrecks.org'," +
		" 'Snow Hill','Maryland','USA',NULL,2014,'Beware of Wrekkies!')," +
		"('frc4946',4946,'The Alpha Dogs','http://www.4946.ca'," +
		" 'Bolton','Ontario','Canada',NULL,2014,'The journey is the prize')," +
		"('frc4947',4947,'Fenix','http://www.fenix4947.csvdc.qc.ca'," +
		" 'Cowansville','Québec','Canada',NULL,2014,NULL)," +
		"('frc4948',4948,'Buccaneers',NULL," +
		" 'Oswego','NY','USA',NULL,NULL,NULL)," +
		"('frc4949',4949,'Robo Panthers','http://www.phsrobopanthers.org'," +
		" 'Riverdale','Maryland','USA',NULL,2014,NULL)," +
		"('frc4950',4950,'Centaures','http://www.firstinspires.org/'," +
		" 'Quebec','Québec','Canada',NULL,2014,NULL)," +
		"('frc4951',4951,'CDS Cyclones','http://www.firstinspires.org/'," +
		" 'King','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4952',4952,'Les Carnicas','http://carnicas4952.csdessommets.qc.ca'," +
		" 'Magog','Québec','Canada',NULL,2014,'Busy as bee''s')," +
		"('frc4953',4953,'Laser Beam','http://www.firstinspires.org/'," +
		" 'Shenzhen','Guangdong','China',NULL,2014,NULL)," +
		"('frc4954',4954,'Palindrome Robotics','http://www.team4954.org/'," +
		" 'Wilmington','Delaware','USA',NULL,2014,NULL)," +
		"('frc4955',4955,'LA Tech','http://www.frc4955.com'," +
		" 'Lachine','Québec','Canada',NULL,2014,NULL)," +
		"('frc4956',4956,'RoboSharks','https://whitelakerobotics.weebly.com/'," +
		" 'Whitehall','Michigan','USA',NULL,2014,'Changing lives, one robot at a time.')," +
		"('frc4957',4957,'Lynx','http://frc4957lynx.yolasite.com'," +
		" 'Montréal','Québec','Canada',NULL,2014,'All together - Tous ensembles')," +
		"('frc4958',4958,'Robocats','https://sites.google.com/a/apps.sau60.org/fmrhs/robotics'," +
		" 'Langdon','New Hampshire','USA',NULL,2014,NULL)," +
		"('frc4959',4959,'Millennium Falcons','http://www.staleyrobotics.com/'," +
		" 'Kansas City','Missouri','USA',NULL,2014,NULL)," +
		"('frc4960',4960,'Polibot San Carlos',NULL," +
		" 'San Carlos','BI','Chile',NULL,2014,NULL)," +
		"('frc4961',4961,'Shock and Awe-sum','http://frc4961.com/'," +
		" 'Almont','Michigan','USA',NULL,2014,NULL)," +
		"('frc4962',4962,'Knight Tech Robotics','http://www.phoenixunion.org/Page/12649'," +
		" 'Phoenix','AZ','USA',NULL,2014,NULL)," +
		"('frc4963',4963,'RoboJets',NULL," +
		" 'Washington','DC','USA',NULL,NULL,NULL)," +
		"('frc4964',4964,'LA Streetbots','http://www.lastreetbots.com'," +
		" 'Los Angeles','California','USA',NULL,2014,NULL)," +
		"('frc4965',4965,'FIRE','http://www.firstinspires.org/'," +
		" 'Anderson','South Carolina','USA',NULL,2014,'Fired up about Robotics!')," +
		"('frc4966',4966,'Condor Force Robot','http://www.condorforcerobot.cl'," +
		" 'Santiago','RM','Chile',NULL,2014,NULL)," +
		"('frc4967',4967,'That ONE Team-OurNextEngineers','http://www.ThatONETeam.org'," +
		" 'Belmont','Michigan','USA',NULL,2014,'Playing the water game since 2013.')," +
		"('frc4968',4968,'RoboHawks','http://www.firstinspires.org/'," +
		" 'Lively','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4969',4969,'Iron FeNix','http:///www.digitalengineeringlab.com'," +
		" 'Virginia Beach','Virginia','USA',NULL,2014,NULL)," +
		"('frc4970',4970,'ICE Cubed','http://www.firstinspires.org/'," +
		" 'Harris','Michigan','USA',NULL,2014,'Get Sum')," +
		"('frc4971',4971,'River',NULL," +
		" 'Vancouver','WA','USA',NULL,NULL,NULL)," +
		"('frc4972',4972,'BORUSAN ROBOTICS','http://frcteam4972.com/'," +
		" 'Esenyurt','Istanbul','Turkey',NULL,2014,NULL)," +
		"('frc4973',4973,'Gator Gears','http://GatorGearRobotics.weebly.com'," +
		" 'San Francisco','California','USA',NULL,2014,'\"Ford over Ferrari.\"')," +
		"('frc4974',4974,'Bluestanbul',NULL," +
		" 'Buyukcekmece','34','Turkey',NULL,NULL,NULL)," +
		"('frc4975',4975,'Team RobotISTs',NULL," +
		" 'istanbul','34','Turkey',NULL,2014,NULL)," +
		"('frc4976',4976,'Revolt Robotics','https://www.team4976.com'," +
		" 'Georgetown','Ontario','Canada',NULL,2014,NULL)," +
		"('frc4977',4977,'Iron Lion','http://www.ironlion4977.com'," +
		" 'Monterrey','Nuevo León','Mexico',NULL,2014,NULL)," +
		"('frc4978',4978,'Tiger Robotics','https://sites.google.com/a/dunhamschool.org/tiger-robotics/'," +
		" 'Baton Rouge','Louisiana','USA',NULL,2014,NULL)," +
		"('frc4979',4979,'DevilStorm Robotics','http://www.firstinspires.org/'," +
		" 'Hinsdale','Illinois','USA',NULL,2014,NULL)," +
		"('frc4980',4980,'Canine Crusaders','http://www.4980robotics.com'," +
		" 'Kettle Falls','Washington','USA',NULL,2014,NULL)," +
		"('frc4981',4981,'Grifftechs',NULL," +
		" 'Alamo','TX','USA',NULL,2014,NULL)," +
		"('frc4982',4982,'Olympus Robotics','http://www.firstinspires.org/'," +
		" 'Fort Wayne','Indiana','USA',NULL,2014,'Forged by Hephaestus')," +
		"('frc4983',4983,'IronBots','https://www.facebook.com/MancelonaIronbots'," +
		" 'Mancelona','Michigan','USA',NULL,2014,NULL)," +
		"('frc4984',4984,'Bullseye','http://team4984.com'," +
		" 'Temecula','California','USA',NULL,2014,'Fire Away')," +
		"('frc4985',4985,'elkSPLOSION','http://www.firstinspires.org/'," +
		" 'Eagar','Arizona','USA',NULL,2014,NULL)," +
		"('frc4986',4986,'Iron Eagles','https://www.facebook.com/pages/VCS-Ironeagles-team4986/1390565804531680?ref=bookmarks'," +
		" 'Tower','Minnesota','USA',NULL,2014,NULL)," +
		"('frc4987',4987,'MegaRams','http://www.warobotics.org'," +
		" 'Worcester','Massachusetts','USA',NULL,2014,'\"Achieve the honorable.\"')," +
		"('frc4988',4988,'Yooper Troopers','https://www.cedarvilletrojans.org/Page/882'," +
		" 'Cedarville','Michigan','USA',NULL,2014,NULL)," +
		"('frc4989',4989,'Jaguars',NULL," +
		" 'Memphis','Tennessee','USA',NULL,2014,NULL)," +
		"('frc4990',4990,'Gryphon Robotics','https://robotics.crystal.csus.org'," +
		" 'Burlingame','California','USA',NULL,2014,NULL)," +
		"('frc4991',4991,'Horsepower','http://WWW.Horsepower4991.org'," +
		" 'Bridgeville','Pennsylvania','USA',NULL,2014,'You''ve earned a wrench')," +
		"('frc4992',4992,'SparBotics','http://www.4992.ca'," +
		" 'Milton','Ontario','Canada',NULL,2014,'This is Sparta!')," +
		"('frc4993',4993,'Molten Steel',NULL," +
		" 'Hutchinson','Minnesota','USA',NULL,2014,NULL)," +
		"('frc4994',4994,'Kingston Robo-Cards','http://team4994.weebly.com/'," +
		" 'Kingston','Michigan','USA',NULL,2014,'\"No Gears, No Glory.\"')," +
		"('frc4995',4995,'Rockets','http://www.firstinspires.org/'," +
		" 'Paradise','Michigan','USA',NULL,2014,NULL)," +
		"('frc4996',4996,'RHS FIRST Robotics Enterprise: Team SPOT','http://www.firstteamspot.com'," +
		" 'Richlands','North Carolina','USA',NULL,2014,NULL)," +
		"('frc4997',4997,'The Golden Machine','http://lbpolyrobotics.org'," +
		" 'Long Beach','CA','USA',NULL,2014,NULL)," +
		"('frc4998',4998,'TESLA','http://team4998.com'," +
		" 'Flint','Michigan','USA',NULL,2014,NULL)," +
		"('frc4999',4999,'Momentum','https://momentum4999.com/'," +
		" 'Long Beach','California','USA',NULL,2014,NULL);";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_10 = SQL_INSERT_TEAMS +
		"('frc5000',5000,'Hammerheads','http://hinghamrobotics.weebly.com'," +
		" 'Hingham','Massachusetts','USA',NULL,2014,'\"We''re gonna need a bigger bot\"')," +
		"('frc5001',5001,'Fast and Serious','http://www.firstinspires.org/'," +
		" 'Tulsa','Oklahoma','USA',NULL,2014,NULL)," +
		"('frc5002',5002,'Dragon Robotics','http://dragonrobotics.org'," +
		" 'Collierville','Tennessee','USA',NULL,2014,NULL)," +
		"('frc5003',5003,'Topperbotics','http://www.firstinspires.org/'," +
		" 'Onalaska','Wisconsin','USA',NULL,2014,NULL)," +
		"('frc5004',5004,'Gravity Loop','http://gravityloop5004.com/'," +
		" 'Roswell','Georgia','USA',NULL,2014,NULL)," +
		"('frc5005',5005,'RoboBucs','http://Www.robobucs.com'," +
		" 'Chattanooga','Tennessee','USA',NULL,2014,NULL)," +
		"('frc5006',5006,'Apophis','http://team5006.com'," +
		" 'Fayetteville','Arkansas','USA',NULL,2014,NULL)," +
		"('frc5007',5007,'Phantomechanics',NULL," +
		" 'Miami','FL','USA',NULL,2014,NULL)," +
		"('frc5008',5008,'(robo)LIONS','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2014,NULL)," +
		"('frc5009',5009,'Techno Inventors of Puerto Rico','http://technoinventors.com'," +
		" 'Guaynabo','PR','USA',NULL,2014,NULL)," +
		"('frc5010',5010,'Tiger Dynasty','http://tigerdynasty.org'," +
		" 'Fishers','Indiana','USA',NULL,2014,'Welcome to the Dynasty')," +
		"('frc5011',5011,'Desert star','https://sites.google.com/site/stemfrc/home'," +
		" 'Ksifa','D','Israel',NULL,2014,NULL)," +
		"('frc5012',5012,'Gryffingear','http://www.gryffingear.com'," +
		" 'Palmdale','California','USA',NULL,2014,'The Roboting Realm of Larry Botter')," +
		"('frc5013',5013,'Trobots','http://trobots.org'," +
		" 'Kansas City','Missouri','USA',NULL,2014,'#50Believe')," +
		"('frc5014',5014,'Wildcats','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2014,NULL)," +
		"('frc5015',5015,'SWAT Bots Robotics','http://www.firstinspires.org/'," +
		" 'Airdrie','Alberta','Canada',NULL,2014,'No Limits!')," +
		"('frc5016',5016,'Huntington Robotics','http://team5016.com'," +
		" 'Huntington','New York','USA',NULL,2014,'Invested in Leadership, Engineering and Diversity')," +
		"('frc5017',5017,'Pirates','http://www.firstinspires.org/'," +
		" 'Harbor Beach','Michigan','USA',NULL,2014,NULL)," +
		"('frc5018',5018,'The Droids You''re Looking For',NULL," +
		" 'Winter Garden','FL','USA',NULL,2014,NULL)," +
		"('frc5019',5019,'Gearbox Heroes','http://www.gearboxheroes.org'," +
		" 'West Salem','Wisconsin','USA',NULL,2014,NULL)," +
		"('frc5020',5020,'CavBOTS','Coming Soon'," +
		" 'Roebuck','South Carolina','USA',NULL,2014,NULL)," +
		"('frc5021',5021,'Bright Shiny Things',NULL," +
		" 'Grand Forks','ND','USA',NULL,NULL,NULL)," +
		"('frc5022',5022,'Rat Rod Robotics','http://www.firstinspires.org/'," +
		" 'Jonesborough','Tennessee','USA',NULL,2014,NULL)," +
		"('frc5023',5023,'Furious Disco Ninjas','http://team5023.org'," +
		" 'San Bruno','California','USA',NULL,2014,NULL)," +
		"('frc5024',5024,'Raider Robotics','http://raiderrobotics.org/index.html'," +
		" 'London','Ontario','Canada',NULL,2014,'Creating a ripple of change')," +
		"('frc5025',5025,'Pacific Steel','http://team5025.com'," +
		" 'San Diego','California','USA',NULL,2014,'Build something different than everyone else. Creativity first')," +
		"('frc5026',5026,'Iron Panthers','http://ironpanthers.com'," +
		" 'Burlingame','California','USA',NULL,2014,'student-built, student-run')," +
		"('frc5027',5027,'Event Horizon','http://www.firstinspires.org/'," +
		" 'San Jose','California','USA',NULL,2014,NULL)," +
		"('frc5028',5028,'Seacoast Robotics',NULL," +
		" 'Santa Rosa Beach','FL','USA',NULL,2014,NULL)," +
		"('frc5029',5029,'Batchelor Institute',NULL," +
		" 'Batchelor','Northern Territory','Australia',NULL,2014,NULL)," +
		"('frc5030',5030,'The Second Mouse','http://www.team5030.com'," +
		" 'Utica','New York','USA',NULL,2014,'TBD')," +
		"('frc5031',5031,' Full Metal Mustangs','http://www.downsviewrobotics.weebly.com'," +
		" 'North York','Ontario','Canada',NULL,2014,'Believe in the you that believes in yourself.')," +
		"('frc5032',5032,'Falcons','http://www.firstinspires.org/'," +
		" 'Mississauga','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5033',5033,'Beavertronics','http://www.firstinspires.org/'," +
		" 'Dorchester','Ontario','Canada',NULL,2014,'Nature''s Engineers')," +
		"('frc5034',5034,'River City Robotics','http://www.firstinspires.org/'," +
		" 'Chattanooga','Tennessee','USA',NULL,2014,NULL)," +
		"('frc5035',5035,'eNimkii',NULL," +
		" 'North Bay','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5036',5036,'The Robo Devils','http://team5036.com/'," +
		" 'Scarborough','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5037',5037,'Panther BOTS',NULL," +
		" 'Greenbrier','AR','USA',NULL,NULL,NULL)," +
		"('frc5038',5038,'MEGIDDO LIONS','http://www.megiddo-robotics.co.il'," +
		" 'Megiddo Regional Council ','HaZafon','Israel',NULL,2014,'Science technology Education')," +
		"('frc5039',5039,'Irish Iron','http://www.frc5039.com/'," +
		" 'Sarnia','Ontario','Canada',NULL,2014,'Carpe Apparatus ')," +
		"('frc5040',5040,'BoltBot',NULL," +
		" 'Arlington','Tennessee','USA',NULL,2014,NULL)," +
		"('frc5041',5041,'CyBears','https://5041cybearrobotics.wordpress.com/'," +
		" 'West Branch','Iowa','USA',NULL,2014,'Designing Robots, Building Leaders')," +
		"('frc5042',5042,'Native Bots',NULL," +
		" 'Scottsdale','AZ','USA',NULL,NULL,NULL)," +
		"('frc5043',5043,'Challenger',NULL," +
		" 'Peoria','AZ','USA',NULL,2014,NULL)," +
		"('frc5044',5044,'MBA Big Red Robots','http://mbarobots.com'," +
		" 'Nashville','TN','USA',NULL,NULL,NULL)," +
		"('frc5045',5045,'SpartaBot','http://team5045.com'," +
		" 'Memphis','Tennessee','USA',NULL,2014,NULL)," +
		"('frc5046',5046,'Jacked Up Jackets','http://team5046'," +
		" 'Memphis','Michigan','USA',NULL,2014,'always rising to the challenge')," +
		"('frc5047',5047,'DV CONQUISTABOTS','http://www.dvsystems1.weebly.com'," +
		" 'El Paso','Texas','USA',NULL,2014,'One Family, One Destiny')," +
		"('frc5048',5048,'Spartronics','http://www.Spartronics5048.com'," +
		" 'Imlay City','Michigan','USA',NULL,2014,'\"Light It Up\"')," +
		"('frc5049',5049,'Techtonics','http://smhstech.com'," +
		" 'Las Vegas','Nevada','USA',NULL,2014,NULL)," +
		"('frc5050',5050,'Cow Town Robotics','www.facebook.com/JetRobots5050'," +
		" 'Carleton','Michigan','USA',NULL,2014,'The glass is half full and the jug is a bottle of milk.')," +
		"('frc5051',5051,'Fast Eddie Community Robotics',NULL," +
		" 'Port Perry','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5052',5052,'The RoboLobos','http://team5052.com/'," +
		" 'Cedar Park','Texas','USA',NULL,2014,'These boxes were raised by wolves.')," +
		"('frc5053',5053,'Lakers','https://lakerrobotics.wordpress.com/'," +
		" 'Waterford','Michigan','USA',NULL,2014,NULL)," +
		"('frc5054',5054,'Noobum Roboticum',NULL," +
		" 'Chicago','IL','USA',NULL,NULL,NULL)," +
		"('frc5055',5055,'robodogs',NULL," +
		" 'New Orleans','LA','USA',NULL,2014,NULL)," +
		"('frc5056',5056,'MegaHurtz Robotics','http://www.MegaHurtzRobotics.org'," +
		" 'Buchanan','Michigan','USA',NULL,2014,NULL)," +
		"('frc5057',5057,'RoboBusters','https://twitter.com/Robobusters_'," +
		" 'Dallas','Texas','USA',NULL,2014,NULL)," +
		"('frc5058',5058,'CGLA Mustang Innovators',NULL," +
		" 'Chattanooga','Tennessee','USA',NULL,2014,NULL)," +
		"('frc5059',5059,'The Midnight Cicadas','http://www.midnightcicadas.com'," +
		" 'Globe','Arizona','USA',NULL,2014,NULL)," +
		"('frc5060',5060,'KnighTech','https://sites.google.com/a/farmington.k12.mo.us/pltw1/fhsfirst'," +
		" 'Farmington','Missouri','USA',NULL,2014,'Fail Fast Succeed Sooner')," +
		"('frc5061',5061,'1',NULL," +
		" 'Woodstock Academy','CT','USA',NULL,NULL,NULL)," +
		"('frc5062',5062,'Warriors',NULL," +
		" 'Tuba City','AZ','USA',NULL,2014,NULL)," +
		"('frc5063',5063,'BuzzBots','http://aynorrobotics.com'," +
		" 'Aynor','South Carolina','USA',NULL,2014,'\"Building tomorrow, one \"BOT\" at a time.\"')," +
		"('frc5064',5064,'OpCom','http://5064.ca'," +
		" 'Edmonton','Alberta','Canada',NULL,2014,'Go Fast')," +
		"('frc5065',5065,'Knights','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2014,NULL)," +
		"('frc5066',5066,'Singularity','http://salinerobotics.org'," +
		" 'Saline','Michigan','USA',NULL,2014,NULL)," +
		"('frc5067',5067,'Steiner Steel Storm','http://www.firstinspires.org/'," +
		" 'Ann Arbor','Michigan','USA',NULL,2014,NULL)," +
		"('frc5068',5068,'The Outsiders','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2014,'Ooh .. Kill ''Em!')," +
		"('frc5069',5069,'The Iron Giants','http://irongiants.org'," +
		" 'Edwardsburg','Michigan','USA',NULL,2014,NULL)," +
		"('frc5070',5070,'Gearnotics','http://www.firstinspires.org/'," +
		" 'Houston','Texas','USA',NULL,2014,NULL)," +
		"('frc5071',5071,'Chargers','http://www.cchsfirst.weebly.com'," +
		" 'Draper','Utah','USA',NULL,2014,NULL)," +
		"('frc5072',5072,'Raider Robotics','http://www.firstinspires.org/'," +
		" 'De Tour Village','Michigan','USA',NULL,2014,NULL)," +
		"('frc5073',5073,'Sparks',NULL," +
		" 'Detroit','MI','USA',NULL,2014,NULL)," +
		"('frc5074',5074,'RoboMustangs','http://www.meadowcreekfrc.com/'," +
		" 'Norcross','Georgia','USA',NULL,2014,NULL)," +
		"('frc5075',5075,'RoboJackets',NULL," +
		" 'Tombstone','Arizona','USA',NULL,2014,NULL)," +
		"('frc5076',5076,'Richardson Stormbots','http://@frc5076 (twitter)'," +
		" 'Ajax','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5077',5077,'RoboCards','http://www.firstinspires.org/'," +
		" 'Youngstown','Ohio','USA',NULL,2014,NULL)," +
		"('frc5078',5078,'ROBO-KAOS','http://robo-kaos.com'," +
		" 'Calgary','Alberta','Canada',NULL,2014,NULL)," +
		"('frc5079',5079,'LGS',NULL," +
		" 'Lahore','PB','Pakistan',NULL,NULL,NULL)," +
		"('frc5080',5080,'Robot Alchemy Manipulators',NULL," +
		" 'Wilmington','DE','USA',NULL,2014,NULL)," +
		"('frc5081',5081,'Lumbernators',NULL," +
		" 'Bay City','MI','USA',NULL,2014,NULL)," +
		"('frc5082',5082,'Northmen Robotics','http://oakparkfirstrobotics.weebly.com/'," +
		" 'Kansas City','Missouri','USA',NULL,2014,NULL)," +
		"('frc5083',5083,'PUMAS',NULL," +
		" 'Edmonton','AB','Canada',NULL,NULL,NULL)," +
		"('frc5084',5084,'Team FridgeBot','http://www.corunnarobotics.com'," +
		" 'Corunna','Michigan','USA',NULL,2014,'Keep it cool.')," +
		"('frc5085',5085,'LakerBots','http://lakerbots5085.com'," +
		" 'Blachly','Oregon','USA',NULL,2014,NULL)," +
		"('frc5086',5086,'Cadillac Connectors','http://www.firstinspires.org/'," +
		" 'Cadillac','Michigan','USA',NULL,2014,NULL)," +
		"('frc5087',5087,'Marsden High School','http://www.firstinspires.org/'," +
		" 'West Ryde','New South Wales','Australia',NULL,2014,NULL)," +
		"('frc5088',5088,'Engadine Robots',NULL," +
		" 'Engadine','MI','USA',NULL,NULL,NULL)," +
		"('frc5089',5089,'Robo-Nerds','http://www.firstinspires.org/'," +
		" 'Los Angeles','California','USA',NULL,2014,NULL)," +
		"('frc5090',5090,'Torque-Nados','http://www.frc5090.com'," +
		" 'Trenton','Michigan','USA',NULL,2014,NULL)," +
		"('frc5091',5091,'Squirrels',NULL," +
		" 'Toronto','ON','Canada',NULL,2014,NULL)," +
		"('frc5092',5092,'Titanium Tigers','http://www.firstinspires.org/'," +
		" 'North York','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5093',5093,'Tech-Sets','https://www.facebook.com/TechSets.FIRST'," +
		" 'Culiacan','Sinaloa','Mexico',NULL,2014,'Tech-dreams for success')," +
		"('frc5094',5094,'Blizzard','http://www.firstinspires.org/'," +
		" 'Brampton','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5095',5095,'RoBeauce','http://robeauce.org'," +
		" 'Saint-Georges','Québec','Canada',NULL,2014,NULL)," +
		"('frc5096',5096,'The Teutonic Force','http://www.theteutonicforce.weebly.com'," +
		" 'Germantown','Wisconsin','USA',NULL,2014,NULL)," +
		"('frc5097',5097,'Phantom','http://www.phantomrobotics.org'," +
		" 'Folsom','CA','USA',NULL,2014,NULL)," +
		"('frc5098',5098,'STING - R','http://www.northtownrobotics.com'," +
		" 'Kansas City','Missouri','USA',NULL,2014,'If you''re not FIRST, You''re Last')," +
		"('frc5099',5099,'Northport High School Robotics','http://www.team5099.com'," +
		" 'Northport','New York','USA',NULL,2014,NULL)," +
		"('frc5100',5100,'Romanoid','http://romanoid5100.blogspot.com'," +
		" 'Los Angeles','California','USA',NULL,2014,NULL)," +
		"('frc5101',5101,'Braves','http://www.firstinspires.org/'," +
		" 'San Carlos','Arizona','USA',NULL,2014,NULL)," +
		"('frc5102',5102,'The Underbots','http://team5102.com'," +
		" 'Santa Maria','California','USA',NULL,2014,'Invent Your Future')," +
		"('frc5103',5103,'Jaegernauts','http://www.firstinspires.org/'," +
		" 'New Braunfels','Texas','USA',NULL,2014,NULL)," +
		"('frc5104',5104,'BreakerBots','https://breakerbots.com'," +
		" 'Pacific Grove','California','USA',NULL,2014,NULL)," +
		"('frc5105',5105,'PioTech',NULL," +
		" 'Wayne','NJ','USA',NULL,NULL,NULL)," +
		"('frc5106',5106,'High Impact Technologies','https://www.facebook.com/HITTeam5106'," +
		" 'Sunderland','Maryland','USA',NULL,2014,NULL)," +
		"('frc5107',5107,'The Neurotoxins ',NULL," +
		" 'Upland','California','USA',NULL,2014,NULL)," +
		"('frc5108',5108,'Can-O-Bolts',NULL," +
		" 'Jacksonville','FL','USA',NULL,NULL,NULL)," +
		"('frc5109',5109,'Gladiator Robotics','https://www.team5109.org/'," +
		" 'Johns Creek','Georgia','USA',NULL,2014,NULL)," +
		"('frc5110',5110,'Robo Herd','http://www.firstinspires.org/'," +
		" 'Elk Rapids','Michigan','USA',NULL,2014,NULL)," +
		"('frc5111',5111,'SaxonBots','http://www.firstinspires.org/'," +
		" 'Spokane','Washington','USA',NULL,2014,NULL)," +
		"('frc5112',5112,'The Gongoliers','http://www.thegongoliers.com/#homepage'," +
		" 'North Scituate','Rhode Island','USA',NULL,2014,NULL)," +
		"('frc5113',5113,'Combustible Lemons','http://www.mhsfirst.org'," +
		" 'Moorestown','New Jersey','USA',NULL,2014,'When life gives you lemons, don''t just make lemonade.')," +
		"('frc5114',5114,'Titanium Tigers','http://www.fentonrobotics5114.com/'," +
		" 'Fenton','Michigan','USA',NULL,2014,NULL)," +
		"('frc5115',5115,'Knight Riders','https://wheatonrobotics.org/'," +
		" 'Silver Spring','Maryland','USA',NULL,2014,NULL)," +
		"('frc5116',5116,'Silicon Claymore',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2014,NULL)," +
		"('frc5117',5117,'Community','http://www.firstinspires.org/'," +
		" 'Calgary','Alberta','Canada',NULL,2014,NULL)," +
		"('frc5118',5118,'K-BOTS','http://www.firstinspires.org/'," +
		" 'Standoff','Alberta','Canada',NULL,2014,NULL)," +
		"('frc5119',5119,'Team STEAM','http://www.frcteam5119.com'," +
		" 'Lawrence','Kansas','USA',NULL,2014,NULL)," +
		"('frc5120',5120,'Robo-Jags',NULL," +
		" 'Little Rock','AR','USA',NULL,NULL,NULL)," +
		"('frc5121',5121,'Trobots','http://www.robopolies.weebly.com'," +
		" 'Riverside','CA','USA',NULL,2014,NULL)," +
		"('frc5122',5122,'robOTies','http://www.team5122.com/'," +
		" 'Old Town','Maine','USA',NULL,2014,'Be Brave!')," +
		"('frc5123',5123,'Mechadogs','http://www.mechadogs.org/'," +
		" 'Yonkers','New York','USA',NULL,2014,'That Looks About Right')," +
		"('frc5124',5124,'West Torrance Robotics','http://www.westtorrancerobotics.org'," +
		" 'Torrance','California','USA',NULL,2014,NULL)," +
		"('frc5125',5125,'Hawks on the Horizon','http://www.hoth5125.org'," +
		" 'Chicago','Illinois','USA',NULL,2014,NULL)," +
		"('frc5126',5126,'Electromagnetic Panthers (E.M.P.)','http://www.frc5126.com'," +
		" 'Riverside','Missouri','USA',NULL,2014,NULL)," +
		"('frc5127',5127,'Red Hurricane',NULL," +
		" 'Shenyang','Liaoning','China',NULL,2014,NULL)," +
		"('frc5128',5128,'Shenzhen Middle School Team',NULL," +
		" 'shenzhen','Guangdong','China',NULL,2014,NULL)," +
		"('frc5129',5129,'Digital Devils','http://www.firstinspires.org/'," +
		" 'Plainville','Connecticut','USA',NULL,2014,NULL)," +
		"('frc5130',5130,'Undercogs','Coming Soon'," +
		" 'LIttle River','South Carolina','USA',NULL,2014,NULL)," +
		"('frc5131',5131,'Rockets Robots2014',NULL," +
		" 'Auburn','MA','USA',NULL,NULL,NULL)," +
		"('frc5132',5132,'RoboClovers','http://barrowrobotics.webs.com/'," +
		" 'Winder','Georgia','USA',NULL,2014,'To make the best better')," +
		"('frc5133',5133,'PrepaTec - Blue Steel','http://www.teambluesteel.com'," +
		" 'Aguascalientes','Aguascalientes','Mexico',NULL,2014,NULL)," +
		"('frc5134',5134,'RoboWolves','https://www.recrobotics.weebly.com'," +
		" 'Clovis','California','USA',NULL,2014,'Never give up, never give in, never surrender')," +
		"('frc5135',5135,'Black Unicorns','http://www.firstinspires.org/'," +
		" 'Yehud','HaMerkaz','Israel',NULL,2014,NULL)," +
		"('frc5136',5136,'Mechapirates','http://www.team5136.org'," +
		" 'Santa Ynez','California','USA',NULL,2014,NULL)," +
		"('frc5137',5137,'Iron Kodiaks','http://www.team5137.com'," +
		" 'San Marcos','California','USA',NULL,2014,NULL)," +
		"('frc5138',5138,'Maine East FIRST',NULL," +
		" 'Park Ridge','IL','USA',NULL,NULL,NULL)," +
		"('frc5139',5139,'Nellis Eagles',NULL," +
		" 'Las Vegas','NV','USA',NULL,NULL,NULL)," +
		"('frc5140',5140,'Systematic Targeting Of Robotic Mayhem (S.T.O.R.M.)',NULL," +
		" 'Edmonton','Alberta','Canada',NULL,2014,NULL)," +
		"('frc5141',5141,'GRIFFINITE','http://www.priderobotics.com'," +
		" 'Kansas City','Missouri','USA',NULL,2014,'Winnetonka PRIDE')," +
		"('frc5142',5142,'Robo Dominators','http://www.firstinspires.org/'," +
		" 'West Haven','Connecticut','USA',NULL,2014,NULL)," +
		"('frc5143',5143,'GRG Raw Steel','http://www.firstinspires.org/'," +
		" 'Grand Rapids','Minnesota','USA',NULL,2014,NULL)," +
		"('frc5144',5144,'LISD TECHnicians','http://www.firstinspires.org/'," +
		" 'Adrian','Michigan','USA',NULL,2014,NULL)," +
		"('frc5145',5145,'WolfBotics','http://www.firstinspires.org/'," +
		" 'Gainesville','Florida','USA',NULL,2014,'Let''s get technical')," +
		"('frc5146',5146,'Rusty Knights','http://www.firstinspires.org/'," +
		" 'Hebron','Ohio','USA',NULL,2014,'Rust never stops!')," +
		"('frc5147',5147,'Hornets','http://www.firstinspires.org/'," +
		" 'Fulton','Missouri','USA',NULL,2014,NULL)," +
		"('frc5148',5148,'New Berlin Blitz','http://www.team5148.org'," +
		" 'New Berlin','Wisconsin','USA',NULL,2014,'Excellence Robotified')," +
		"('frc5149',5149,'TroyBotics','http://www.firstinspires.org/'," +
		" 'Troy','New York','USA',NULL,2014,NULL)," +
		"('frc5150',5150,'Hybrid Hornets','http://www.HybridHornets.weebly.com'," +
		" 'Flint','Michigan','USA',NULL,2014,'Strong Alone; Stronger United')," +
		"('frc5151',5151,'RoboTitans',NULL," +
		" 'bronx','New York','USA',NULL,2014,NULL)," +
		"('frc5152',5152,'Alotobots','http://www.firstinspires.org/'," +
		" 'Otsego','Michigan','USA',NULL,2014,NULL)," +
		"('frc5153',5153,'EAST at HHS','https://www.facebook.com/pages/EAST-at-Huntsville-High-School/208057585988673?ref=hl'," +
		" 'Huntsville','Arkansas','USA',NULL,2014,'Education is not a passive experience.')," +
		"('frc5154',5154,'Dolphins',NULL," +
		" 'Gulf Shores','AL','USA',NULL,NULL,NULL)," +
		"('frc5155',5155,'Bearcats','http://www.firstinspires.org/'," +
		" 'Ubly','Michigan','USA',NULL,2014,NULL)," +
		"('frc5156',5156,'Laker Robots','http://www.firstinspires.org/'," +
		" 'Pigeon','Michigan','USA',NULL,2014,NULL)," +
		"('frc5157',5157,'Roboprime Cardinals','http://www.firstinspires.org/'," +
		" 'Sudbury','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5158',5158,'Richmond Hill','http://www.firstinspires.org/'," +
		" 'Richmond Hill','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5159',5159,'Bengal Robotics','http://bengalrobotics.weebly.com/'," +
		" 'Salt Lake City','Utah','USA',NULL,2014,NULL)," +
		"('frc5160',5160,'Chargers','http://team5160.com'," +
		" 'Cary','North Carolina','USA',NULL,2014,'Putting the awe in chaos')," +
		"('frc5161',5161,'Trojan Technicians ','http://www.Trojantech5161.com'," +
		" 'Plainwell','Michigan','USA',NULL,2014,' Design. Build. Succeed.')," +
		"('frc5162',5162,'The Big Red Theory ','http://brobotics5162.weebly.com'," +
		" 'Big Rapids','Michigan','USA',NULL,2014,NULL)," +
		"('frc5163',5163,'The Aluminum Warriors','http://www.firstinspires.org/'," +
		" 'Hopkinton','Massachusetts','USA',NULL,2014,NULL)," +
		"('frc5164',5164,'Robusters',NULL," +
		" 'Sudbury','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5165',5165,'Team 5165',NULL," +
		" NULL,NULL,NULL,NULL,NULL,NULL)," +
		"('frc5166',5166,'Fabricators','http://www.freelandroboticsclub.com'," +
		" 'Freeland','Michigan','USA',NULL,2014,NULL)," +
		"('frc5167',5167,'Vi-Bots','http://www.team5167.weebly.com'," +
		" 'Marysville','Michigan','USA',NULL,2014,NULL)," +
		"('frc5168',5168,'Guardian Angels','http://www.facebook.com/frc5168'," +
		" 'New Orleans','Louisiana','USA',NULL,2014,'We''re soaring to new heights')," +
		"('frc5169',5169,'The Armadillos',NULL," +
		" 'Senatobia','MS','USA',NULL,2014,NULL)," +
		"('frc5170',5170,'Bobcats',NULL," +
		" 'Gainesville','FL','USA',NULL,NULL,NULL)," +
		"('frc5171',5171,'Deus Ex Machina','http://yorkrobotics.weebly.com/'," +
		" 'Monterey','California','USA',NULL,2014,NULL)," +
		"('frc5172',5172,'Gators','http://www.5172gators.org'," +
		" 'Greenbush','Minnesota','USA',NULL,2014,NULL)," +
		"('frc5173',5173,'Fennville RoboHawks','https://robohawks5173.teamapp.com/'," +
		" 'Fennville','Michigan','USA',NULL,2014,'Let''s Ride')," +
		"('frc5174',5174,'Enterprise',NULL," +
		" 'Naura','Z','Israel',NULL,2014,NULL)," +
		"('frc5175',5175,'Purple People Eaters','http://www.firstinspires.org/'," +
		" 'Pickford','Michigan','USA',NULL,2014,NULL)," +
		"('frc5176',5176,'RoboBills','http://www.firstinspires.org/'," +
		" 'Saint Louis','Missouri','USA',NULL,2014,NULL)," +
		"('frc5177',5177,'Hatchets',NULL," +
		" '200 North Barrie Rd','MI','USA',NULL,NULL,NULL)," +
		"('frc5178',5178,'The Nerdy Birds','http://www.firstinspires.org/'," +
		" 'Farmington','Arkansas','USA',NULL,2014,'The Nerdiest Bird Gets the Worm')," +
		"('frc5179',5179,'Les Sénateurs','http://www.senateurs5179.com'," +
		" 'Drummondville','Québec','Canada',NULL,2014,NULL)," +
		"('frc5180',5180,'Metal Heads','https://sites.google.com/a/richland2.org/robotics-club/'," +
		" 'Blythewood','South Carolina','USA',NULL,2014,'We Regret Nothing!')," +
		"('frc5181',5181,'Explorer','https://www.lschs.org/clubs-activities/robotics'," +
		" 'Glenside','Pennsylvania','USA',NULL,2014,'Don''t let perfect be the enemy of good.')," +
		"('frc5182',5182,'Chieftainators','http://www.firstinspires.org/'," +
		" 'Dowagiac','Michigan','USA',NULL,2014,NULL)," +
		"('frc5183',5183,'Devil Bots','https://www.facebook.com/frc5183gaylord/'," +
		" 'Gaylord','Michigan','USA',NULL,2014,NULL)," +
		"('frc5184',5184,'TITANICS',NULL," +
		" 'Edmonton','Alberta','Canada',NULL,2014,'In All things Excellence')," +
		"('frc5185',5185,'Dial-Up Grizzlies',NULL," +
		" 'Airdrie','Alberta','Canada',NULL,2014,NULL)," +
		"('frc5186',5186,'Cybears','http://www.firstinspires.org/'," +
		" 'Antioch','Tennessee','USA',NULL,2014,NULL)," +
		"('frc5187',5187,'TEAM ON IT','http://theonitfoundation.org/robotics-2014'," +
		" 'Miami','FL','USA',NULL,2014,NULL)," +
		"('frc5188',5188,'Area 5188 : Classified Robotics','http://classifiedrobotics.com/'," +
		" 'Terre Haute','Indiana','USA',NULL,2014,'It''s Classified ')," +
		"('frc5189',5189,'SABRES','https://www.facebook.com/SumnerAcademyRobotics'," +
		" 'Kansas City','Kansas','USA',NULL,2014,NULL)," +
		"('frc5190',5190,'Green Hope Falcons','http://www.ghrobotics.org'," +
		" 'Cary','North Carolina','USA',NULL,2014,'Talk Nerdy To Me')," +
		"('frc5191',5191,'LANCERobotics','http://www.lancerobotics.com'," +
		" 'Sudbury','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5192',5192,'Toros',NULL," +
		" 'Benton Harbor','MI','USA',NULL,NULL,NULL)," +
		"('frc5193',5193,'Pantheon','http://www.firstinspires.org/'," +
		" 'Saginaw','Michigan','USA',NULL,2014,NULL)," +
		"('frc5194',5194,'Gobles Voltage','http://www.firstinspires.org/'," +
		" 'Gobles','Michigan','USA',NULL,2014,NULL)," +
		"('frc5195',5195,'The Renegade',NULL," +
		" 'Wynne','Arkansas','USA',NULL,2014,NULL)," +
		"('frc5196',5196,'Breaking Bot','http://team5196.com/'," +
		" 'Pompano Beach','Florida','USA',NULL,2014,'Make the best, be the best')," +
		"('frc5197',5197,'DCP @ NW','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2014,NULL)," +
		"('frc5198',5198,'Knight Tech','http://www.firstinspires.org/'," +
		" 'Grants Pass','Oregon','USA',NULL,2014,'Minus Est Optimus')," +
		"('frc5199',5199,'Robot Dolphins From Outer Space','http://www.RobotDolphins.com'," +
		" 'San Juan Capistrano','California','USA',NULL,2014,'Palmam Qui Meruit Ferat')," +
		"('frc5200',5200,'Alchesay Falcon','http://www.firstinspires.org/'," +
		" 'Whiteriver','Arizona','USA',NULL,2014,'All for One, One for All')," +
		"('frc5201',5201,'North Branch Bad News Broncos','http://www.BroncoBots5201.com'," +
		" 'North Branch','Michigan','USA',NULL,2014,NULL)," +
		"('frc5202',5202,'NewRo Bots','http://nrhs.nred.org/group_profile_view.aspx?id=1a04f708-1195-451b-a571-e39c9f60962d'," +
		" 'New Rochelle','New York','USA',NULL,2014,NULL)," +
		"('frc5203',5203,'Volatile Chaos Inhibitors','http://www.firstinspires.org/'," +
		" 'Sanford','Michigan','USA',NULL,2014,NULL)," +
		"('frc5204',5204,'Robocats','http://www.firstinspires.org/'," +
		" 'Three Rivers','Michigan','USA',NULL,2014,NULL)," +
		"('frc5205',5205,'FullMetal-Jackets','http://www.firstinspires.org/'," +
		" 'Concord','Michigan','USA',NULL,2014,NULL)," +
		"('frc5206',5206,'A''s Gurias',NULL," +
		" 'Gravatai','RS','Brazil',NULL,NULL,NULL)," +
		"('frc5207',5207,'Bearcepticons','https://www.facebook.com/CoolidgeRobotics/'," +
		" 'Coolidge','Arizona','USA',NULL,2014,'Working together is stronger than working alone.')," +
		"('frc5208',5208,'TALON Robotics',NULL," +
		" 'Turlock','CA','USA',NULL,NULL,NULL)," +
		"('frc5209',5209,'Rectify','http://team5209.weebly.com'," +
		" 'San Diego','California','USA',NULL,2014,'Changing The Game.')," +
		"('frc5210',5210,'Milpitas Xtreme Robotics',NULL," +
		" 'Milpitas','CA','USA',NULL,2014,NULL)," +
		"('frc5211',5211,'Tuiuteam',NULL," +
		" 'Gravatai','RS','Brazil',NULL,NULL,NULL)," +
		"('frc5212',5212,'TAMSformers Robotics ','http://tamsformers.weebly.com/'," +
		" 'Denton','Texas','USA',NULL,2014,NULL)," +
		"('frc5213',5213,'St. Ignace SHIELD ','http://www.firstinspires.org/'," +
		" 'Saint Ignace','Michigan','USA',NULL,2014,NULL)," +
		"('frc5214',5214,'The Mighty CavBots','http://themightycavbots.com'," +
		" 'Saint Clair Shores','Michigan','USA',NULL,2014,'When we try … we succeed!')," +
		"('frc5215',5215,'Steagles','http://www.firstinspires.org/'," +
		" 'Frankenmuth','Michigan','USA',NULL,2014,NULL)," +
		"('frc5216',5216,'E-Ville Empire','http://www.e-hps.net'," +
		" 'Essexville','Michigan','USA',NULL,2014,NULL)," +
		"('frc5217',5217,'Robocats','http://www.Robocats5217.com'," +
		" 'Dearborn','Michigan','USA',NULL,2014,NULL)," +
		"('frc5218',5218,'System Overload',NULL," +
		" 'Yuma','Arizona','USA',NULL,2014,NULL)," +
		"('frc5219',5219,'TeknoSquad','www.futurewarbots5219.weebly.com'," +
		" 'East Point','Georgia','USA',NULL,2014,'When times get tough, keep building...')," +
		"('frc5220',5220,'Rockets',NULL," +
		" 'Westland','MI','USA',NULL,2014,NULL)," +
		"('frc5221',5221,'Project Freelance','http://www.thestag.ca'," +
		" 'Windsor','ON','Canada',NULL,2014,NULL)," +
		"('frc5222',5222,'JackBotics','http://www.firstinspires.org/'," +
		" 'Saginaw','Michigan','USA',NULL,2014,NULL)," +
		"('frc5223',5223,'Hillman Gearheads','https://www.facebook.com/hillmanhighschoolgearheadsroboticsteam'," +
		" 'Hillman','MI','USA',NULL,2014,NULL)," +
		"('frc5224',5224,'Panther Power','http://www.firstinspires.org/'," +
		" 'Standish','Michigan','USA',NULL,2014,'Learn to Learn')," +
		"('frc5225',5225,'The Shambotz','http://www.firstinspires.org/'," +
		" 'Eastpointe','Michigan','USA',NULL,2014,NULL)," +
		"('frc5226',5226,'Syn. Surgers','http://www.firstinspires.org/'," +
		" 'Flint','Michigan','USA',NULL,2014,NULL)," +
		"('frc5227',5227,'Eagle Elite','http://www.firstinspires.org/'," +
		" 'Schoolcraft','Michigan','USA',NULL,2014,NULL)," +
		"('frc5228',5228,'Astros',NULL," +
		" 'Calgary','AB','Canada',NULL,2014,NULL)," +
		"('frc5229',5229,'Heritage Hawkbots','http://www.stcs.org/HHS/Department/174-robotics-team-5229'," +
		" 'Saginaw','Michigan','USA',NULL,2014,NULL)," +
		"('frc5230',5230,'The Resistance','http://www.firstinspires.org/'," +
		" 'Alpena ','Michigan','USA',NULL,2014,NULL)," +
		"('frc5231',5231,'Radical Jays','http://5231rad.weebly.com/'," +
		" 'Shepherd','Michigan','USA',NULL,2014,'\"Duct Tape fixes most things others just call for a bigger hammer\"')," +
		"('frc5232',5232,'Talons','http://www.talons5232.com'," +
		" 'Hermantown','Minnesota','USA',NULL,2014,'Drive it until you break it!!')," +
		"('frc5233',5233,'Golden Eagles','http://www.firstinspires.org/'," +
		" 'Brooklyn','Michigan','USA',NULL,2014,NULL)," +
		"('frc5234',5234,'MarauderBots','http://www.marauderbots.com/'," +
		" 'Elsie','Michigan','USA',NULL,2014,NULL)," +
		"('frc5235',5235,'Gears of Fortune','http://team5235.com'," +
		" 'Chesaning','Michigan','USA',NULL,2014,NULL)," +
		"('frc5236',5236,'Man O'' War Robotics','http://www.firstinspires.org/'," +
		" 'Cambridge','New York','USA',NULL,2014,NULL)," +
		"('frc5237',5237,'Bears',NULL," +
		" 'Wyandotte','MI','USA',NULL,NULL,NULL)," +
		"('frc5238',5238,'Falconators','http://www.firstinspires.org/'," +
		" 'Otisville','Michigan','USA',NULL,2014,NULL)," +
		"('frc5239',5239,'Harper Woods Pioneers','http://www.firstinspires.org/'," +
		" 'Harper Woods','Michigan','USA',NULL,2014,'On the Move')," +
		"('frc5240',5240,'Udder Chaos','http://www.firstinspires.org/'," +
		" 'Potsdam','New York','USA',NULL,2014,NULL)," +
		"('frc5241',5241,'Tribe Tech FRC','http://www.tribe-borgs.com'," +
		" 'San Antonio','Texas','USA',NULL,2014,'\"Saturday or Bust!\"')," +
		"('frc5242',5242,'⚙️ RoboCats ⚙️','http://wwhsrobocats.wixsite.com/5242'," +
		" 'Dallas','Texas','USA',NULL,2014,NULL)," +
		"('frc5243',5243,'Aegis Robotics','http://www.centrevillerobotics.net/'," +
		" 'Clifton','Virginia','USA',NULL,2014,'Started from the bot-tom, now we''re in gear')," +
		"('frc5244',5244,'LNHS',NULL," +
		" 'Kalamazoo','MI','USA',NULL,NULL,NULL)," +
		"('frc5245',5245,'Warriors',NULL," +
		" 'Grass Lake','MI','USA',NULL,NULL,NULL)," +
		"('frc5246',5246,'Tech Tribe','http://www.firstinspires.org/'," +
		" 'Hartford','Michigan','USA',NULL,2014,NULL)," +
		"('frc5247',5247,'Red Devil Robotics','http://www.firstinspires.org/'," +
		" 'East Jordan','Michigan','USA',NULL,2014,'Innovation in Action ')," +
		"('frc5248',5248,'Robo-Saxons','http://www.facebook.com/Team5248'," +
		" 'Hastings','Michigan','USA',NULL,2014,NULL)," +
		"('frc5249',5249,'Revere Robotics','http://www.revererobotics.weebly.com'," +
		" 'Richfield','Ohio','USA',NULL,2014,NULL)," +
		"('frc5250',5250,'Kinetic','http://kineticrobotics.info'," +
		" 'Sacramento','California','USA',NULL,2014,NULL)," +
		"('frc5251',5251,'C3 WAYbots','http://www.firstinspires.org/'," +
		" 'Flint','Michigan','USA',NULL,2014,NULL)," +
		"('frc5252',5252,'Cyber Tigers',NULL," +
		" 'Detroit','MI','USA',NULL,2014,NULL)," +
		"('frc5253',5253,'Bigfork Backwoods Bots','http://www.firstinspires.org/'," +
		" 'Bigfork','Minnesota','USA',NULL,2014,NULL)," +
		"('frc5254',5254,'HYPE','http://frc5254.com'," +
		" 'Trumansburg','New York','USA',NULL,2014,'Helping Youth Pursue Excellence')," +
		"('frc5255',5255,'Robotic Tigers','http://www.firstinspires.org/'," +
		" 'Muskegon','Michigan','USA',NULL,2014,NULL)," +
		"('frc5256',5256,'The Atomics','http://www.lakewoodrobotics.org'," +
		" 'Lake Odessa','Michigan','USA',NULL,2014,NULL)," +
		"('frc5257',5257,'CometKaze','http://colemanschools.net/robotics'," +
		" 'Coleman','Michigan','USA',NULL,2014,NULL)," +
		"('frc5258',5258,'Flyers',NULL," +
		" 'Romeoville','IL','USA',NULL,NULL,NULL)," +
		"('frc5259',5259,'Magic Smoke',NULL," +
		" 'Jacksonville','Arkansas','USA',NULL,2014,NULL)," +
		"('frc5260',5260,'Owosso Operating System','http://www.firstinspires.org/'," +
		" 'Owosso','Michigan','USA',NULL,2014,NULL)," +
		"('frc5261',5261,'Cyber Shock','http://www.firstinspires.org/'," +
		" 'Humble','Texas','USA',NULL,2014,NULL)," +
		"('frc5262',5262,'Robotum Manus',NULL," +
		" 'Cullman','AL','USA',NULL,NULL,NULL)," +
		"('frc5263',5263,'Robo-Bucs','http://www.riverviewschools.com/education/club/club.php?sectionid=764&'," +
		" 'Riverview','Michigan','USA',NULL,2014,'I have not failed.  I''ve just found 10,000 ways that won''t work.  - Edison')," +
		"('frc5264',5264,'The Integers',NULL," +
		" 'Jamaica','NY','USA',NULL,NULL,NULL)," +
		"('frc5265',5265,'Radical Impact','http://cheverus5265.org'," +
		" 'Portland','Maine','USA',NULL,2014,NULL)," +
		"('frc5267',5267,'Cougar',NULL," +
		" 'Toronto','ON','Canada',NULL,NULL,NULL)," +
		"('frc5268',5268,'The BioMech Falcons','http://www.biomechfalcons.com/'," +
		" 'Olathe','Kansas','USA',NULL,2014,'Read the Directions - What  Directions?!?')," +
		"('frc5269',5269,'Sonic Sci-borgs','http://kennedyrobotics5269.weebly.com'," +
		" 'Silver Spring','MD','USA',NULL,2014,NULL)," +
		"('frc5270',5270,'KULTUR LISESI',NULL," +
		" 'ISTANBUL','34','Turkey',NULL,NULL,NULL)," +
		"('frc5271',5271,'Open Circuits','http://www.firstinspires.org/'," +
		" 'Saint Paul','Minnesota','USA',NULL,2014,NULL)," +
		"('frc5272',5272,'Argobots',NULL," +
		" 'Van','TX','USA',NULL,2014,NULL)," +
		"('frc5273',5273,'desert robot','http://www.firstinspires.org/'," +
		" 'Kuseyfe','HaDarom (Southern)','Israel',NULL,2014,NULL)," +
		"('frc5274',5274,'Wolverines','https://sites.google.com/site/rosemontroboticsclub5274/home'," +
		" 'Sacramento','California','USA',NULL,2014,NULL)," +
		"('frc5275',5275,'T.I.M.E.-Bots','http://www.firstinspires.org/'," +
		" 'New Prague','Minnesota','USA',NULL,2014,NULL)," +
		"('frc5276',5276,'Edgar Allan Ohms','http://edgarallanohms.weebly.com/'," +
		" 'Land O Lakes','Florida','USA',NULL,2014,NULL)," +
		"('frc5277',5277,'Rowville SC',NULL," +
		" 'Rowville','VIC','Australia',NULL,2014,NULL)," +
		"('frc5278',5278,'Los Clasicos','http://www.firstinspires.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2014,NULL)," +
		"('frc5279',5279,'Bionic Eagles','http://www.team5279.com/'," +
		" 'Altavista','Virginia','USA',NULL,2014,'Made in the USA')," +
		"('frc5280',5280,'Shockers',NULL," +
		" 'Calgary','AB','Canada',NULL,NULL,NULL)," +
		"('frc5281',5281,'PyroNinjas',NULL," +
		" 'Fort Walton Beach','FL','USA',NULL,NULL,NULL)," +
		"('frc5282',5282,'Railroaders','http://team5282.org'," +
		" 'Durand','Michigan','USA',NULL,2014,'Team 5282 to the Rescue!')," +
		"('frc5283',5283,'Torque','http://TorqueRobotics.com'," +
		" 'Tampa','Florida','USA',NULL,2014,'Torque: a FORCE to be reckoned with')," +
		"('frc5284',5284,'The Monarchs','http://N/A'," +
		" 'Wilmington','CA','USA',NULL,NULL,NULL)," +
		"('frc5285',5285,'Sea Kings Robotics','https://www.frc5285.com'," +
		" 'Palos Verdes Estates','California','USA',NULL,2014,NULL)," +
		"('frc5286',5286,'Trojonix',NULL," +
		" 'Saco','Maine','USA',NULL,2014,NULL)," +
		"('frc5287',5287,'FLARE',NULL," +
		" 'Orange','TX','USA',NULL,2014,NULL)," +
		"('frc5288',5288,'Spartan Robotics','http://spartanrobotics.ca'," +
		" 'London','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5289',5289,'Medabots',NULL," +
		" 'New York City','New York','USA',NULL,2014,NULL)," +
		"('frc5290',5290,'Mechanical Howl','http://www.firstinspires.org/'," +
		" 'Forest Lake','Minnesota','USA',NULL,2014,NULL)," +
		"('frc5291',5291,'Emperius','http://emperiuseilat5291.wix.com/5291'," +
		" 'Eilat','HaDarom','Israel',NULL,2014,NULL)," +
		"('frc5292',5292,'Cougar-Tron Squad','http://www.aspenview.org/grass/index.php?option=com_content&view=article&id=75&Itemid=101'," +
		" 'Grassland','Alberta','Canada',NULL,2014,NULL)," +
		"('frc5293',5293,'Metal Crusaders','http://www.firstinspires.org/'," +
		" 'Duluth','Georgia','USA',NULL,2014,NULL)," +
		"('frc5294',5294,'Industrial Revolution',NULL," +
		" 'Providence/Pawtucket','RI','USA',NULL,NULL,NULL)," +
		"('frc5295',5295,'Aldernating Current','http://frcteam5295aldernatingcurrent.blogspot.com/'," +
		" 'Shelton','Washington','USA',NULL,2014,'Shockingly Poplar!')," +
		"('frc5296',5296,'Falcons',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2014,NULL)," +
		"('frc5297',5297,'BOLTZAP','https://gt.gdsyzx.cn'," +
		" 'Guangzhou','Guangdong','China',NULL,2014,NULL)," +
		"('frc5298',5298,'E-TECH CHARGERS','http://www.energytechschool.org/content/team-5298-robotics'," +
		" 'Astoria','New York','USA',NULL,2014,NULL)," +
		"('frc5299',5299,'Winger Tech','https://www.gowingers.com/page/3142'," +
		" 'Red Wing','Minnesota','USA',NULL,2014,'n/a')," +
		"('frc5300',5300,'Mustangs',NULL," +
		" 'Clio','MI','USA',NULL,NULL,NULL)," +
		"('frc5301',5301,'RBT',NULL," +
		" 'CHUANGCHUN','22','China',NULL,2014,NULL)," +
		"('frc5302',5302,'Transformers',NULL," +
		" 'DALIAN','21','China',NULL,2014,NULL)," +
		"('frc5303',5303,'Power Train',NULL," +
		" 'ZhengZhou','46','China',NULL,NULL,NULL)," +
		"('frc5304',5304,'Stone',NULL," +
		" 'CHUANGCHUN','22','China',NULL,2014,NULL)," +
		"('frc5305',5305,'Screw',NULL," +
		" 'Wuhan','13','China',NULL,NULL,NULL)," +
		"('frc5306',5306,'R2','http://www.firstinspires.org/'," +
		" 'CHONGQING','Chongqing','China',NULL,2014,NULL)," +
		"('frc5307',5307,'Mechanical Dragon','http://www.sz2grobot.com'," +
		" 'SHEN ZHEN','Guangdong','China',NULL,2014,'Modern, independent, simple and special.')," +
		"('frc5308',5308,'Wuhan Yangtze ','http://www.team5308.org'," +
		" 'Wuhan','Hubei','China',NULL,2014,'Even Better')," +
		"('frc5309',5309,'Robbery',NULL," +
		" 'chuangchun','22','China',NULL,NULL,NULL)," +
		"('frc5310',5310,'Eagles Apex','http://www.firstinspires.org/'," +
		" 'East Orange','New Jersey','USA',NULL,2014,'Be Extreme!  Be Great! ')," +
		"('frc5311',5311,'Colombian Space Stars','http://Space%20Stars%20on%20Facebook'," +
		" 'Bogota','Distrito Capital de Bogotá','Colombia',NULL,2014,NULL)," +
		"('frc5312',5312,'Lobotics','http://www.lobotics5312.com'," +
		" 'Torreon','Coahuila','Mexico',NULL,2014,'We are the soul of our robot.')," +
		"('frc5313',5313,'CIBERBOTS',NULL," +
		" 'Toluca','MEX','Mexico',NULL,NULL,NULL)," +
		"('frc5314',5314,'Ice Bots','http://pellstonrobotics.org'," +
		" 'Pellston','Michigan','USA',NULL,2014,NULL)," +
		"('frc5315',5315,'Panther Robot',NULL," +
		" 'Peyton','CO','USA',NULL,NULL,NULL)," +
		"('frc5316',5316,'The Architects','http://www.robotics5316.org'," +
		" 'Saranac','Michigan','USA',NULL,2014,'Architecting the minds of future leaders')," +
		"('frc5317',5317,'@RedhawkRobotics','http://www.redhawkrobotics.com'," +
		" 'Blythewood','South Carolina','USA',NULL,2014,NULL)," +
		"('frc5318',5318,'DigiDigger','http://www.firstinspires.org/'," +
		" 'Lead','South Dakota','USA',NULL,2014,NULL)," +
		"('frc5320',5320,'Cyber Hornets',NULL," +
		" 'Lorenzo','Texas','USA',NULL,2014,NULL)," +
		"('frc5321',5321,'Medicengineers',NULL," +
		" 'Buyukcekmece','34','Turkey',NULL,NULL,NULL)," +
		"('frc5322',5322,'Southern River',NULL," +
		" 'Gosnels','WA','Australia',NULL,NULL,NULL)," +
		"('frc5323',5323,'Mechanized Mayhem',NULL," +
		" 'Chesapeake','Virginia','USA',NULL,2014,NULL)," +
		"('frc5324',5324,'Hawks','http://www.firstinspires.org/'," +
		" 'Haliburton','Ontario','Canada',NULL,2014,NULL)," +
		"('frc5325',5325,'Hurricanes','Coming Soon'," +
		" 'Marrero','Louisiana','USA',NULL,2014,NULL)," +
		"('frc5326',5326,'Optimus PRIN','http://robots@principia.edu'," +
		" 'St. Louis','Missouri','USA',NULL,2014,'Magnify the good')," +
		"('frc5327',5327,'Griffin Robotics','http://www.griffinrobotics.org'," +
		" 'Winnsboro','South Carolina','USA',NULL,2014,'\"Wing it\"')," +
		"('frc5328',5328,'APCR',NULL," +
		" 'Astana','AST','Kazakhstan',NULL,NULL,NULL)," +
		"('frc5329',5329,'Agents of S(A^3)S','https://team5329.org/'," +
		" 'Miami','Florida','USA',NULL,2014,NULL)," +
		"('frc5330',5330,'CBTIS 001 FRESNILLO',NULL," +
		" 'FRESNILLO','ZAC','Mexico',NULL,NULL,NULL)," +
		"('frc5331',5331,'Lightning Bots','http://www.firstinspires.org/'," +
		" 'Sydney','New South Wales','Australia',NULL,2014,NULL)," +
		"('frc5332',5332,'Toaster Tech','http://www.toastertech.org'," +
		" 'Decatur','Georgia','USA',NULL,2014,'Spreading Technology like butter on toast.')," +
		"('frc5333',5333,'Can''t C#','http://www.firstinspires.org/'," +
		" 'Perth','Western Australia','Australia',NULL,2014,NULL)," +
		"('frc5334',5334,'BUHOS77','http://WWW.rht8805.url.ph'," +
		" 'LEÓN','GUA','Mexico',NULL,NULL,NULL)," +
		"('frc5335',5335,'Falcons','http://flanaganhighschool.com'," +
		" 'Pembroke Pines','FL','USA',NULL,2014,NULL)," +
		"('frc5336',5336,'Longhorn ','http://www.firstinspires.org/'," +
		" 'Suwanee','Georgia','USA',NULL,2014,NULL)," +
		"('frc5337',5337,'Real Steel',NULL," +
		" 'Conway','SC','USA',NULL,2014,NULL)," +
		"('frc5338',5338,'RoboLoCo','http://www.team5338.org'," +
		" 'Leesburg','Virginia','USA',NULL,2014,'Driving you Crazy!')," +
		"('frc5339',5339,'Hurricanes','http://hurricanerobotics.com/'," +
		" 'Houston','Minnesota','USA',NULL,2014,'Team Work')," +
		"('frc5340',5340,'FAIR Robtics','http://www.firstinspires.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2014,NULL)," +
		"('frc5341',5341,'The Cardinals',NULL," +
		" 'Faribault','MN','USA',NULL,NULL,NULL)," +
		"('frc5342',5342,'The Greyhounds','http://teachersites.schoolworld.com/webpages/skymath/robotic.cfm'," +
		" 'TAFT','TX','USA',NULL,2014,NULL)," +
		"('frc5343',5343,'Environmental Charter High School',NULL," +
		" 'Lawndale','CA','USA',NULL,NULL,NULL)," +
		"('frc5344',5344,'Rhinos','http://www.idr.mx'," +
		" 'Mexico','Distrito Federal','Mexico',NULL,2014,NULL)," +
		"('frc5345',5345,'BOLTS',NULL," +
		" 'West Valley City','UT','USA',NULL,2014,NULL)," +
		"('frc5346',5346,'Atlas Engineering',NULL," +
		" 'Perry','Utah','USA',NULL,2014,NULL)," +
		"('frc5347',5347,'Gryphons','http://www.firstinspires.org/'," +
		" 'Weston','Massachusetts','USA',NULL,2014,NULL)," +
		"('frc5348',5348,'Charger Robotics','https://sites.google.com/dc.k12.mn.us/chargerrobotics'," +
		" 'Cokato','Minnesota','USA',NULL,2014,'It''s a Prototype')," +
		"('frc5349',5349,'RoboEagles','http://www.firstinspires.org/'," +
		" 'Central Square','New York','USA',NULL,2014,NULL)," +
		"('frc5350',5350,'Hope Robotics','http://www.firstinspires.org/'," +
		" 'Chicago','Illinois','USA',NULL,2014,'We can do it')," +
		"('frc5351',5351,'T-STEM Robotics',NULL," +
		" 'El Paso','TX','USA',NULL,NULL,NULL)," +
		"('frc5400',5400,'WARP','http://www.facebook.com/frcteamwarp'," +
		" 'Worcester','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5401',5401,'Fightin'' Robotic Owls','http://www.team5401.org/'," +
		" 'Bensalem','Pennsylvania','USA',NULL,2015,NULL)," +
		"('frc5402',5402,'Iron Kings','http://www.firstinspires.org/'," +
		" 'Walton','Indiana','USA',NULL,2015,NULL)," +
		"('frc5403',5403,'Aluminosity','http://www.aluminosity.org'," +
		" 'Bedford','Indiana','USA',NULL,2015,'The Future Is Bright')," +
		"('frc5404',5404,'Gearaffes','http://www.FRC5404.org'," +
		" 'Lansdale','Pennsylvania','USA',NULL,2015,'Creativity and Inspiration')," +
		"('frc5405',5405,'Red Bank High School',NULL," +
		" 'Chattanooga','Tennessee','USA',NULL,2015,NULL)," +
		"('frc5406',5406,'Celt-X','http://5406.ca'," +
		" 'Hamilton','Ontario','Canada',NULL,2015,'Robots Building Students')," +
		"('frc5407',5407,'Wolfpack Robotics','http://www.firstinspires.org/'," +
		" 'Conshohocken','Pennsylvania','USA',NULL,2015,NULL)," +
		"('frc5408',5408,'KENNEDYcache','http://kennedyrobotics.wixsite.com/mysite'," +
		" 'Windsor','Ontario','Canada',NULL,2015,'Altiora Peto')," +
		"('frc5409',5409,'Chargers','http://www.garthwebbrobotics.ca'," +
		" 'Oakville','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5410',5410,'Eaglebotics','http://www.eaglebotics.org'," +
		" 'Pompano Beach','Florida','USA',NULL,2015,'Where Engineers Soar')," +
		"('frc5411',5411,'PowerEagle','http://www.firstinspires.org/'," +
		" 'Prosper','Texas','USA',NULL,2015,NULL)," +
		"('frc5412',5412,'Impossible Robotics','http://impossible-robotics.com'," +
		" 'Groningen','Groningen','Netherlands',NULL,2015,'Challenging the Impossible')," +
		"('frc5413',5413,'Stellar Robotics','http://www.firstroboticsmansfield.com'," +
		" 'Shelby','Ohio','USA',NULL,2015,'Aim High, Achieve High')," +
		"('frc5414',5414,'Pearadox','http://www.pearadox5414.org'," +
		" 'Pearland','Texas','USA',NULL,2015,'To question, to learn, to learn to question')," +
		"('frc5415',5415,'Bears','http://bears.com.mx'," +
		" 'Mexico City','Distrito Federal','Mexico',NULL,2015,NULL)," +
		"('frc5416',5416,'TaRDIS','http://team5416.com'," +
		" 'Katy','Texas','USA',NULL,2015,'Every year, we reginerate!')," +
		"('frc5417',5417,'Eagle Robotics','http://team5417.wixsite.com/home'," +
		" 'Allen','Texas','USA',NULL,2015,'Create a platform for tomorrow’s STEM leaders to learn, grow, and soar.')," +
		"('frc5418',5418,'The Sonic Screwdrivers','http://www.firstinspires.org/'," +
		" 'Hubbard','Ohio','USA',NULL,2015,NULL)," +
		"('frc5419',5419,'Natural Disasters',NULL," +
		" 'Berkeley','California','USA',NULL,2015,NULL)," +
		"('frc5420',5420,'Velocity','http://sjrobotics.com'," +
		" 'Bridgeton','New Jersey','USA',NULL,2015,NULL)," +
		"('frc5421',5421,'Birch Bots','http://www.BirchBots.com'," +
		" 'New York','New York','USA',NULL,2015,'It SHOULD be OK!?')," +
		"('frc5422',5422,'Stormgears FRC','http://www.stormgears.org'," +
		" 'Westford','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5423',5423,'HopCo  Robo','http://www.firstinspires.org/'," +
		" 'Madisonville','Kentucky','USA',NULL,2015,'Hopin Mad and Kickin Bot')," +
		"('frc5424',5424,'Rogue Robots','http://www.firstinspires.org/'," +
		" 'Midland','Michigan','USA',NULL,2015,'Warranties were meant to be voided.')," +
		"('frc5425',5425,'RVA Coder Collective','http://www.firstinspires.org/'," +
		" 'Richmond','Virginia','USA',NULL,2015,NULL)," +
		"('frc5426',5426,'E.J. Lajeunesse','http://www.fivefourtwosix.com'," +
		" 'Windsor','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5427',5427,'Steel Talons','http://www.tompkins-robotics.weebly.com'," +
		" 'Katy','Texas','USA',NULL,2015,NULL)," +
		"('frc5428',5428,'Breaking Bots','http://www.firstinspires.org/'," +
		" 'Markham','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5429',5429,'Black Knights','https://team5429.com'," +
		" 'Las Vegas','Nevada','USA',NULL,2015,'Student Led Student Run')," +
		"('frc5430',5430,'Pirate Robolution','http://www.pittsburg.k12.ca.us/domain/1322'," +
		" 'Pittsburg','California','USA',NULL,2015,NULL)," +
		"('frc5431',5431,'Titan Robotics in memory of Jordan Grant','http://www.frc5431.com'," +
		" 'Plano','Texas','USA',NULL,2015,'Building our future one bot at a time')," +
		"('frc5432',5432,'AutoPilots','https://sites.google.com/site/autopilotsrobotics/'," +
		" 'Wilmington','California','USA',NULL,2015,NULL)," +
		"('frc5433',5433,'Blinding Light','http://www.firstinspires.org/'," +
		" 'Webster','New York','USA',NULL,2015,NULL)," +
		"('frc5434',5434,'Falcon Robotics','http://www.firstinspires.org/'," +
		" 'Faribault','Minnesota','USA',NULL,2015,NULL)," +
		"('frc5435',5435,'RoboMomoTrufflez',NULL," +
		" 'Hamilton','NJ','USA',NULL,2015,NULL)," +
		"('frc5436',5436,'Cyber Cats','https://www.cybercats5436.com'," +
		" 'Rochester','Michigan','USA',NULL,2015,'Always on the Prowl')," +
		"('frc5437',5437,'Rocky Balboabots','http://www.rocky-balboabots.org/'," +
		" 'Booneville','Arkansas','USA',NULL,2015,NULL)," +
		"('frc5438',5438,'Technological Terrors','http://robotics.spprep.org/'," +
		" 'Jersey City','New Jersey','USA',NULL,2015,NULL)," +
		"('frc5439',5439,'Huskies','http://huskies5439.ca/'," +
		" 'Québec','Québec','Canada',NULL,2015,NULL)," +
		"('frc5440',5440,'Les Chevaliers','https://leschevaliersfirst.wixsite.com/5440'," +
		" 'Quebec','Québec','Canada',NULL,2015,NULL)," +
		"('frc5441',5441,'Arsenal',NULL," +
		" 'Mascouche','Québec','Canada',NULL,2015,NULL)," +
		"('frc5442',5442,'Mechanical Monarchy','https://sites.google.com/a/wacohi.net/panther-engineering/'," +
		" 'Washington','Illinois','USA',NULL,2015,'Mind Over Metal')," +
		"('frc5443',5443,'Les PATenteux','http://www.firstinspires.org/'," +
		" 'Montréal','Québec','Canada',NULL,2015,NULL)," +
		"('frc5444',5444,'VObotik','http://vobotik.com'," +
		" 'Trois-Rivières','Québec','Canada',NULL,2015,NULL)," +
		"('frc5445',5445,'Rushing Peacock','http://www.firstinspires.org/'," +
		" 'Kunming','Yunnan','China',NULL,2015,NULL)," +
		"('frc5446',5446,'Pink Detectives','http://www.firstinspires.org/'," +
		" 'Apex','North Carolina','USA',NULL,2015,NULL)," +
		"('frc5447',5447,'Team Robotica','http://www.firstinspires.org/'," +
		" 'Sebewaing','Michigan','USA',NULL,2015,NULL)," +
		"('frc5448',5448,'CCA RoboCougars','https://frc5448.com'," +
		" 'Big Rapids','Michigan','USA',NULL,2015,NULL)," +
		"('frc5449',5449,'Prototype','http://www.firstinspires.org/'," +
		" 'Beijing','Beijing','China',NULL,2015,NULL)," +
		"('frc5450',5450,'SHREC - St. Helens Robotics and Engineering Club','http://www.firstinspires.org/'," +
		" 'Saint Helens','Oregon','USA',NULL,2015,NULL)," +
		"('frc5451',5451,'Supernova','http://www.first-china.org'," +
		" 'Beijing','Beijing','China',NULL,2015,NULL)," +
		"('frc5452',5452,'Aurora Robot','http://www.firstinspires.org/'," +
		" 'quanzhou','Fujian','China',NULL,2015,NULL)," +
		"('frc5453',5453,'RED COMET','http://www.firstinspires.org/'," +
		" 'Shenzhen','Guangdong','China',NULL,2015,NULL)," +
		"('frc5454',5454,'Wild Fire','http://5454wildfirefrc.weebly.com/'," +
		" 'Rogers','Arkansas','USA',NULL,2015,'Helping spread STEM like Wild Fire!')," +
		"('frc5455',5455,'shenzhen pingshan high school ',NULL," +
		" 'shenzhen','Guangdong','China',NULL,2015,NULL)," +
		"('frc5456',5456,'hua nan shi fan da xue fushu zhong xue',NULL," +
		" 'guangzhou','52','China',NULL,2015,NULL)," +
		"('frc5457',5457,'Falcobots','http://www.firstinspires.org/'," +
		" 'Burlington','New Jersey','USA',NULL,2015,NULL)," +
		"('frc5458',5458,'Digital Minds','http://digitalminds5458.org'," +
		" 'Woodland','California','USA',NULL,2015,'Stepping Forward With Robotics')," +
		"('frc5459',5459,'Ipswich TIGERS','http://www.ipswich5459.com'," +
		" 'Ipswich','Massachusetts','USA',NULL,2015,'One spirit, one team, one win')," +
		"('frc5460',5460,'Strike Zone','http://www.first5460.com'," +
		" 'Lapeer','Michigan','USA',NULL,2015,NULL)," +
		"('frc5461',5461,'V.E.R.N.','https://www.facebook.com/ExplorerPost4/'," +
		" 'Boise','Idaho','USA',NULL,2015,NULL)," +
		"('frc5462',5462,'2PawR (2 Paw Robotics)','https://sites.google.com/a/ppps.org/paw-paw-high-school-robotics/'," +
		" 'Paw Paw','Michigan','USA',NULL,2015,'Success Is A Choice!')," +
		"('frc5463',5463,'Iron Eagles','http://www.firstinspires.org/'," +
		" 'Las Vegas','Nevada','USA',NULL,2015,NULL)," +
		"('frc5464',5464,'Bluejacket Robotics','https://bluejacketrobotics.wixsite.com/5464'," +
		" 'Cambridge','Minnesota','USA',NULL,2015,NULL)," +
		"('frc5465',5465,'Binary Bots','http://binarybots.wix.com/binary-bots-team5465'," +
		" 'Chandler','Arizona','USA',NULL,2015,'Si Se Puede')," +
		"('frc5466',5466,'Shanghai Xiang Ming High School Of China',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2015,NULL)," +
		"('frc5467',5467,'RoboTractors','http://www.firstinspires.org/'," +
		" 'Dearborn','Michigan','USA',NULL,2015,NULL)," +
		"('frc5468',5468,'Chaos Theory','http://summitrobotics.com'," +
		" 'Bend','Oregon','USA',NULL,2015,NULL)," +
		"('frc5469',5469,'FLARE','http://www.flare5469.org'," +
		" 'Orange','Texas','USA',NULL,2015,NULL)," +
		"('frc5470',5470,'The Cowpunchers','http://www.firstinspires.org/'," +
		" 'Coopersville','Michigan','USA',NULL,2015,NULL)," +
		"('frc5471',5471,'W.Hi.R.R.','http://www.team5471.org/'," +
		" 'Winthrop','Maine','USA',NULL,2015,'Rotae in carro semper volvunt volvuntque')," +
		"('frc5472',5472,'Stallion Robotics','http://www.team5472.com'," +
		" 'Delray Beach','Florida','USA',NULL,2015,'We will figure it out')," +
		"('frc5473',5473,'Team Spitfire.','http://aviatorsrobotics.com'," +
		" 'Grand Rapids','Michigan','USA',NULL,2015,'Rising to today''s challenges to create a better tomorrow!')," +
		"('frc5474',5474,'Clairemonster','http://clairemonsterrobotics.com/'," +
		" 'San Diego','California','USA',NULL,2015,NULL)," +
		"('frc5475',5475,'Thunder','https://sites.google.com/site/thunderfrc/home'," +
		" 'Phoenix','Arizona','USA',NULL,2015,NULL)," +
		"('frc5476',5476,'Y Bridge 4H','http://Frc5476.org'," +
		" 'Galena','Missouri','USA',NULL,2015,'Building a bridge to the future')," +
		"('frc5477',5477,'NuBotX - National University Robotics Experience','http://www.nubotx.com'," +
		" 'Vista','California','USA',NULL,2015,'Keep it Simple')," +
		"('frc5478',5478,'Pershing Doughbots','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2015,NULL)," +
		"('frc5479',5479,'Cafe Bot','http://www.cafebot.org'," +
		" 'St. Louis','Missouri','USA',NULL,2015,NULL)," +
		"('frc5480',5480,'FYRE (FIRST Young Robotics Engineers)','http://www.fyrerobotics.org'," +
		" 'Reno','Nevada','USA',NULL,2015,NULL)," +
		"('frc5481',5481,'Bristol Bots',NULL," +
		" 'Bristolville','OH','USA',NULL,2015,NULL)," +
		"('frc5482',5482,'Roarbotics','http://www.firstinspires.org/'," +
		" 'Atlanta','Georgia','USA',NULL,2015,'Roar!!!!')," +
		"('frc5483',5483,'GD-Bots','http://www.firstinspires.org/'," +
		" 'London','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5484',5484,'Career Academy Robotics - Wolf Pack','http://www.team5484.com'," +
		" 'South Bend','Indiana','USA',NULL,2015,'Remember to Live')," +
		"('frc5485',5485,'Manual T-Bots','http://www.firstinspires.org/'," +
		" 'Denver','Colorado','USA',NULL,2015,NULL)," +
		"('frc5486',5486,'Robotic Turmoil','http://www.firstinspires.org/'," +
		" 'Lake Linden','Michigan','USA',NULL,2015,NULL)," +
		"('frc5487',5487,'Tiger Robotics','http://www.firstinspires.org/'," +
		" 'Caro','Michigan','USA',NULL,2015,NULL)," +
		"('frc5488',5488,'Baronbotix',NULL," +
		" 'Chula Vista','CA','USA',NULL,2015,NULL)," +
		"('frc5489',5489,'CSI Golden Eagle Robotics','https://www.facebook.com/pages/FRC-5489-CSI-Robotics/211552695559324?fref=nf'," +
		" 'Twin Falls','Idaho','USA',NULL,2015,NULL)," +
		"('frc5490',5490,'The Dark Byte','http://www.firstinspires.org/'," +
		" 'Bethlehem','Pennsylvania','USA',NULL,2015,NULL)," +
		"('frc5491',5491,'Cognitive Hazard','http://www.firstinspires.org/'," +
		" 'Winchendon','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5492',5492,'Winner''s Circle Robo Jockey''s','https://www.frc5492.org'," +
		" 'Louisville','Kentucky','USA',NULL,2015,'Building a community of STEM champions')," +
		"('frc5493',5493,'SMAbotics AG','http://smabotics.com'," +
		" 'Englewood','Colorado','USA',NULL,2015,'Silly Boys, Robots are for Girls')," +
		"('frc5494',5494,'Bizarbots Robotics','http://bizarbots.org'," +
		" 'Holbrook','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5495',5495,'Aluminati','http://www.firstinspires.org/'," +
		" 'Snohomish','Washington','USA',NULL,2015,NULL)," +
		"('frc5496',5496,'Robo Knights','https://roboknights100.wixsite.com/roboknights5496'," +
		" 'Vacaville','California','USA',NULL,2015,'Knights Always Hit Their Mark')," +
		"('frc5497',5497,'Foxes',NULL," +
		" 'Silverton','OR','USA',NULL,2015,NULL)," +
		"('frc5498',5498,'The Wired Devils','http://WiredDevils.net'," +
		" 'Grosse Ile','Michigan','USA',NULL,2015,NULL)," +
		"('frc5499',5499,'The Bay Orangutans','http://team5499.org'," +
		" 'Berkeley','California','USA',NULL,2015,'honestly');";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_11 = SQL_INSERT_TEAMS +
		"('frc5500',5500,'Jaguars Robotics & ROVs','http://www.firstinspires.org/'," +
		" 'Long Beach','California','USA',NULL,2015,'Jaguars Up!')," +
		"('frc5501',5501,'Bobcats','http://www.firstinspires.org/'," +
		" 'Burr Oak','Michigan','USA',NULL,2015,NULL)," +
		"('frc5502',5502,'Cobrots','http://www.jonesvillerobotics.com'," +
		" 'Jonesville','Michigan','USA',NULL,2015,NULL)," +
		"('frc5503',5503,'Smithville Tiger Trons','http://www.firstinspires.org/'," +
		" 'Smithville','Texas','USA',NULL,2015,NULL)," +
		"('frc5504',5504,'The Loose Connections','http://www.firstinspires.org/'," +
		" 'Pinconning ','Michigan','USA',NULL,2015,NULL)," +
		"('frc5505',5505,'V2 Robotics','http://www.firstinspires.org/'," +
		" 'Alpena','Michigan','USA',NULL,2015,'It isn''t rocket science')," +
		"('frc5506',5506,'Midcoast MAINEiacs Robotics Team','http://www.midcoastmaineiacs.org'," +
		" 'Searsport','Maine','USA',NULL,2015,'\"Raising Aspirations, Raising Opportunities, Raising Success!\"')," +
		"('frc5507',5507,'Robotic Eagles','http://www.team5507.com'," +
		" 'San Francisco','California','USA',NULL,2015,NULL)," +
		"('frc5508',5508,'Wolfbots','http://www.firstinspires.org/'," +
		" 'Knoxville','Tennessee','USA',NULL,2015,NULL)," +
		"('frc5509',5509,'Like a Boss','https://sites.google.com/midlandps.org/mhsrobotics'," +
		" 'Midland','Michigan','USA',NULL,2015,'Like a Boss.')," +
		"('frc5510',5510,'The Da Vinci Coders','http://team5510.weebly.com'," +
		" 'Los Angeles','California','USA',NULL,2015,'It''s All Good')," +
		"('frc5511',5511,'Cortechs Robotics','http://www.cortechsrobotics.com'," +
		" 'Cary','North Carolina','USA',NULL,2015,'Gearing Up to Learn')," +
		"('frc5512',5512,'Pizza Mecánica','http://www.stemacademy.cl/'," +
		" 'Recoleta','Región Metropolitana de Santiago','Chile',NULL,2015,'GO EVIL BOTS, DA EVIL BOTS!')," +
		"('frc5513',5513,'Monroe Robotics','monroeengineering.org'," +
		" 'North Hills','CA','USA',NULL,2015,'Lead by example')," +
		"('frc5514',5514,'MavBots','http://lccrobotics.org/'," +
		" 'Carlsbad','California','USA',NULL,2015,'Go Mavs!')," +
		"('frc5515',5515,'Blue Power Robotics','http://www.team5515.com'," +
		" 'Shanghai','Shanghai','China',NULL,2015,NULL)," +
		"('frc5516',5516,'Iron Maple','http://Www.FRC5516.com'," +
		" 'Shenzhen','Guangdong','China',NULL,2015,NULL)," +
		"('frc5517',5517,'The Engineers on Fire','https://sites.google.com/view/frc5517'," +
		" 'Burton','Michigan','USA',NULL,2015,'\"Catching the Fire of FIRST\"')," +
		"('frc5518',5518,'Techno Wolves','http://www.technowolves.org/'," +
		" 'Apex','North Carolina','USA',NULL,2015,'Leading The Pack')," +
		"('frc5519',5519,'LASS Robotics','http://www.lassrobotics.com'," +
		" 'Brampton','Ontario','Canada',NULL,2015,'Justice will be served')," +
		"('frc5520',5520,'Duck and Cover','www.duckandcover7037.org'," +
		" 'Henrico','Virginia','USA',NULL,2015,'Our robot teaches us')," +
		"('frc5521',5521,'WOLF TEAM ROBOTICS','NP'," +
		" 'SOPO','Cundinamarca','Colombia',NULL,2015,'Science for All')," +
		"('frc5522',5522,'Stargazer','http://frc5522.com/'," +
		" 'Shenzhen ','Guangdong','China',NULL,2015,NULL)," +
		"('frc5523',5523,'RoboWarriors','http://www.firstinspires.org/'," +
		" 'Grass Lake','Michigan','USA',NULL,2015,NULL)," +
		"('frc5524',5524,'MacroProcessors ','http://www.firstinspires.org/'," +
		" 'Birch Run','Michigan','USA',NULL,2015,NULL)," +
		"('frc5525',5525,'Alcona Tool Cats','http://toolcats5525.wix.com/alconaschools'," +
		" 'Lincoln','Michigan','USA',NULL,2015,NULL)," +
		"('frc5526',5526,'tCATs','http://www.tcats5526.com'," +
		" 'Torreon','Coahuila','Mexico',NULL,2015,'Excellence for Life Through Science and Technology')," +
		"('frc5527',5527,'Cyber Phoenix','http://iaf-sabis.net'," +
		" 'Flint','Michigan','USA',NULL,2015,'\"Rise\"')," +
		"('frc5528',5528,'Ultime','http://www.ultime5528.com'," +
		" 'Trois-Rivieres','Québec','Canada',NULL,2015,'All together !')," +
		"('frc5529',5529,'Visalia Vanquishers',NULL," +
		" 'Visalia','CA','USA',NULL,2015,'RoboMonkeys')," +
		"('frc5530',5530,'The Greenhills Lawnmowers','http://ghlawnmowers.org'," +
		" 'Ann Arbor','Michigan','USA',NULL,2015,NULL)," +
		"('frc5531',5531,'Orange Crush','http://www.firstinspires.org/'," +
		" 'Dearborn','Michigan','USA',NULL,2015,'Quality in action')," +
		"('frc5532',5532,'The G.O.A.T.S','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2015,NULL)," +
		"('frc5533',5533,'Electric Horse Power',NULL," +
		" 'Clio','Michigan','USA',NULL,2015,NULL)," +
		"('frc5534',5534,'Onaway Onabots','http://www.onawayschools.com/?DivisionID=7877&DepartmentID=27922&SubDepartmentID=12855'," +
		" 'Onaway','Michigan','USA',NULL,2015,NULL)," +
		"('frc5535',5535,'Bionic Bison','http://team5535.weebly.com'," +
		" 'New Buffalo','Michigan','USA',NULL,2015,'Go Bison')," +
		"('frc5536',5536,'Titan Alliance','http://www.titanrobotics.us'," +
		" 'Albany','Georgia','USA',NULL,2015,'Fortune Favors The Bold')," +
		"('frc5537',5537,'Dia and the Brobots feat. J-Kwelin',NULL," +
		" 'Fairfield','Iowa','USA',NULL,2015,NULL)," +
		"('frc5538',5538,'Vikingbots','http://svhsrobotics.weebly.com/'," +
		" 'Saginaw','Michigan','USA',NULL,2015,NULL)," +
		"('frc5539',5539,'DVHS Cyborgs','http://www.firstinspires.org/'," +
		" 'Tucson','Arizona','USA',NULL,2015,NULL)," +
		"('frc5540',5540,'Lincoln High School Tigers','http://www.alhsrobotics.webs.com'," +
		" 'Los Angeles','California','USA',NULL,2015,'Home of the Tigers')," +
		"('frc5541',5541,'Shakopee Robotics','http://www.shakopeerobotics.org'," +
		" 'Shakopee','Minnesota','USA',NULL,2015,'fortuna tironibus')," +
		"('frc5542',5542,'RoboHerd','http://www.firstinspires.org/'," +
		" 'Buffalo','Minnesota','USA',NULL,2015,NULL)," +
		"('frc5543',5543,'M.I.N.T.T.Z - Mechanically Insane Nerdy Tech Teenz',NULL," +
		" 'Greensboro','North Carolina','USA',NULL,2015,NULL)," +
		"('frc5544',5544,'SWIFT Robotics','http://Swiftrobotics.net'," +
		" 'Vass','North Carolina','USA',NULL,2015,'''STEAM Power'' - Students Together Engaged and Motivated')," +
		"('frc5545',5545,'PiedmontSTEM',NULL," +
		" 'concord','VA','USA',NULL,2015,NULL)," +
		"('frc5546',5546,'⚒️ A.R.T. ⚒️','https://argsrobotics.com'," +
		" 'Petersburg','Virginia','USA',NULL,2015,NULL)," +
		"('frc5547',5547,'The Talons','http://www.firstinspires.org/'," +
		" 'Hale','Michigan','USA',NULL,2015,'All students will learn')," +
		"('frc5548',5548,'Psi Factor','http://www.firstinspires.org/'," +
		" 'Yukon','Oklahoma','USA',NULL,2015,NULL)," +
		"('frc5549',5549,'Gryphon Robotics','https://www.frc5549.org/'," +
		" 'Falls Church','Virginia','USA',NULL,2015,NULL)," +
		"('frc5550',5550,'BRONChO BOTS','http://www.firstinspires.org/'," +
		" 'Bethany','Oklahoma','USA',NULL,2015,NULL)," +
		"('frc5551',5551,'Harding Revolution','http://www.firstinspires.org/'," +
		" 'Memphis','Tennessee','USA',NULL,2015,'Forge the Future')," +
		"('frc5552',5552,'SENSORED','http://www.sensored5552.com'," +
		" 'Green Bay','Wisconsin','USA',NULL,2015,'Robot Forward')," +
		"('frc5553',5553,'Robo''Lyon','http://www.robolyon.com'," +
		" 'Neuville-sur-Saône','Rhône','France',NULL,2015,NULL)," +
		"('frc5554',5554,'The Poros','http://theporos.wixsite.com/theporos'," +
		" 'Netanya','HaMerkaz','Israel',NULL,2015,NULL)," +
		"('frc5555',5555,'Spartans','http://ischool.fitz.k12.mi.us/fhsrobotics/index.html'," +
		" 'Warren','Michigan','USA',NULL,2015,'One Day Closer to the Dream')," +
		"('frc5556',5556,'Carriagetown Robotics','http://www.firstinspires.org/'," +
		" 'Amesbury','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5557',5557,'BB-R8ERS','http://team5557.org'," +
		" 'Miami','Florida','USA',NULL,2015,'May the Force BB-8 With You')," +
		"('frc5558',5558,'Ad Astra','http://www.frcadastra.com'," +
		" 'Bursa','Bursa','Turkey',NULL,2015,NULL)," +
		"('frc5559',5559,'Gear Grinders','http://www.camdenfrontierrobotics.org'," +
		" 'Camden','Michigan','USA',NULL,2015,NULL)," +
		"('frc5560',5560,'Central Lake Trobotics','http://www.firstinspires.org/'," +
		" 'Central Lake','Michigan','USA',NULL,2015,NULL)," +
		"('frc5561',5561,'Raider Robotics','https://www.flushingschools.org/site/Default.aspx?PageID=1169'," +
		" 'Flushing','Michigan','USA',NULL,2015,NULL)," +
		"('frc5562',5562,'Laker Logistics','http://www.glenlakeschools.org/news-events/extracurriculars/team-5562-laker-logistics-first-high-school-robotics.html'," +
		" 'Maple City','Michigan','USA',NULL,2015,NULL)," +
		"('frc5563',5563,'Phalanx','http://stmaryslynn.com/FIRST'," +
		" 'Lynn','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5564',5564,'GBHS Robotics','http://www.firstinspires.org/'," +
		" 'Granville','New South Wales','Australia',NULL,2015,NULL)," +
		"('frc5565',5565,'Auburn Girls','http://www.firstinspires.org/'," +
		" 'Auburn','New South Wales','Australia',NULL,2015,NULL)," +
		"('frc5566',5566,'Sugar Rush','http://www.innoventiverobotics.org'," +
		" 'Sugar Land ','Texas','USA',NULL,2015,'Change that Excites.')," +
		"('frc5567',5567,'Code Red Robotics ','http://www.firstinspires.org/'," +
		" 'Milan','Michigan','USA',NULL,2015,NULL)," +
		"('frc5568',5568,'Solid Rock ','http://www.frc5568.com'," +
		" 'Bentonville','Arkansas','USA',NULL,2015,NULL)," +
		"('frc5569',5569,'Tech Titans','http://www.firstinspires.org/'," +
		" 'Washington','District of Columbia','USA',NULL,2015,'Never say you cant, Say you CAN')," +
		"('frc5570',5570,'Ookpik','http://www.firstinspires.org/'," +
		" 'Montréal','Québec','Canada',NULL,2015,NULL)," +
		"('frc5571',5571,'RATCHET','http://www.firstinspires.org/'," +
		" 'Knoxville','Tennessee','USA',NULL,2015,NULL)," +
		"('frc5572',5572,'ROSBOTS','http://www.frc5572.org'," +
		" 'San Antonio','Texas','USA',NULL,2015,NULL)," +
		"('frc5573',5573,'Ivanhoe','http://www.firstinspires.org/'," +
		" 'Ivanhoe','New South Wales','Australia',NULL,2015,NULL)," +
		"('frc5574',5574,'P.A.N.T.H.E.R.S.','http://www.firstinspires.org/'," +
		" 'Grand Rapids','Michigan','USA',NULL,2015,'No excuses, just solutions.')," +
		"('frc5575',5575,'SHOCK','http://www,boynefallsshock.com'," +
		" 'Boyne Falls','Michigan','USA',NULL,2015,'Generation of Innovation')," +
		"('frc5576',5576,'Team Terminator','http://www.firstinspires.org/'," +
		" 'Spirit Lake','Iowa','USA',NULL,2015,NULL)," +
		"('frc5577',5577,'Kinematic Wolves','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2015,NULL)," +
		"('frc5578',5578,'Metro Mechanical Monsters','http://www.firstinspires.org/'," +
		" 'Oklahoma City','Oklahoma','USA',NULL,2015,NULL)," +
		"('frc5579',5579,'Trojanbots','https://sites.google.com/a/lee.k12.ga.us/fix/lchs-robotics'," +
		" 'Albany','GA','USA',NULL,2015,NULL)," +
		"('frc5580',5580,'BRAEZEN KNIGHTS','http://www.firstinspires.org/'," +
		" 'Toronto','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5581',5581,'Hornets',NULL," +
		" 'Brampton','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5582',5582,'Rex Machina','http://www.rexmachinatcs.com/'," +
		" 'Sharpsburg','Georgia','USA',NULL,2015,'Veni, Vidi, Vici')," +
		"('frc5583',5583,'SistamatiK','http://www.facebook.com/pages/FRC-5583-SistamatiK/826778470711878'," +
		" 'Saint Louis','Missouri','USA',NULL,2015,NULL)," +
		"('frc5584',5584,'ICRobotics','http://www.versiontree.com/icrobotics'," +
		" 'Boronia','Victoria','Australia',NULL,2015,NULL)," +
		"('frc5585',5585,'Blood, Sweat & Gears','http://www.edmestoncentralschool.net/'," +
		" 'Edmeston','New York','USA',NULL,2015,NULL)," +
		"('frc5586',5586,'Bond Brigade','http://bondbrigade.weebly.com'," +
		" 'Kiel','Wisconsin','USA',NULL,2015,NULL)," +
		"('frc5587',5587,'Titan Robotics','https://frc5587.org'," +
		" 'Alexandria','Virginia','USA',NULL,2015,NULL)," +
		"('frc5588',5588,'Reign Robotics','http://www.5588reign.com/'," +
		" 'Seattle','Washington','USA',NULL,2015,NULL)," +
		"('frc5589',5589,'Red Devils','http://tvdsb.ca/woodstock/FIRST'," +
		" 'Woodstock','Ontario','Canada',NULL,2015,'Knowledge Industry Character')," +
		"('frc5590',5590,'Alumiboti','http://www.sjcialumiboti.com'," +
		" 'Buffalo','New York','USA',NULL,2015,NULL)," +
		"('frc5591',5591,'Manta Mechanics','http://www.firstinspires.org/'," +
		" 'Miami','Florida','USA',NULL,2015,NULL)," +
		"('frc5592',5592,'Far North Robotics','http://www.firstinspires.org/'," +
		" 'Cairns','Queensland','Australia',NULL,2015,NULL)," +
		"('frc5593',5593,'Devil Robotics','http://team5593.org/'," +
		" 'Hobart','Tasmania','Australia',NULL,2015,NULL)," +
		"('frc5594',5594,'GI Joz','http://www.firstinspires.org/'," +
		" 'Toccoa','Georgia','USA',NULL,2015,'Guardians of THE Village')," +
		"('frc5595',5595,'NRHS TigerBytes','http://www.firstinspires.org/'," +
		" 'New Richmond','Wisconsin','USA',NULL,2015,NULL)," +
		"('frc5596',5596,'Wolverines','https://5596.ca'," +
		" 'Toronto','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5597',5597,'RoboMaidens','http://www.firstinspires.org/'," +
		" 'Fort Mcmurray','Alberta','Canada',NULL,2015,NULL)," +
		"('frc5598',5598,'sjmp','http://www.firstinspires.org/'," +
		" 'toronto','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5599',5599,'The Sentinels','http://www.team5599.com'," +
		" 'Bayside','New York','USA',NULL,2015,'New is better!')," +
		"('frc5600',5600,'Taiyuan Beijing Team','http://www.firstinspires.org/'," +
		" 'Beijing','Beijing','China',NULL,2015,NULL)," +
		"('frc5601',5601,'Semia Team 1','http://www.firstinspires.org/'," +
		" 'Shanghai','Shanghai','China',NULL,2015,NULL)," +
		"('frc5602',5602,'XJTLU Team','http://www.firstinspires.org/'," +
		" 'Suzhou','Jiangsu','China',NULL,2015,NULL)," +
		"('frc5603',5603,'Rise of the Warrior Bots','http://www.westernrobotics5603.weebly.com'," +
		" 'Auburn','Michigan','USA',NULL,2015,NULL)," +
		"('frc5604',5604,'Feixueqingzang','http://www.firstinspires.org/'," +
		" 'Xi''ning','Qinghai','China',NULL,2015,NULL)," +
		"('frc5605',5605,'Team DaoZu','http://www.firstinspires.org/'," +
		" 'Qingdao','Shandong','China',NULL,2015,NULL)," +
		"('frc5606',5606,'Blue Hurricane','http://www.firstinspires.org/'," +
		" 'Shenyang','Liaoning','China',NULL,2015,NULL)," +
		"('frc5607',5607,'Team Firewall','http://firewallrobotics.com'," +
		" 'Raleigh','North Carolina','USA',NULL,2015,NULL)," +
		"('frc5608',5608,'Lassiter Robotics','http://www.LassiterRobotics.org'," +
		" 'Marietta','Georgia','USA',NULL,2015,NULL)," +
		"('frc5609',5609,'COOL School',NULL," +
		" 'Gladwin','MI','USA',NULL,2015,NULL)," +
		"('frc5610',5610,'Turbulence','http://www.firstinspires.org/'," +
		" 'Saugatuck','Michigan','USA',NULL,2015,NULL)," +
		"('frc5611',5611,'Neon Tigers','http://www.firstinspires.org/'," +
		" 'Verden','Oklahoma','USA',NULL,2015,NULL)," +
		"('frc5612',5612,'RoboRaptors','http://www.firstinspires.org/'," +
		" 'Burton','Michigan','USA',NULL,2015,NULL)," +
		"('frc5613',5613,'ThunderDogs','http://www.thunderdogs5613.com'," +
		" 'Alamogordo','New Mexico','USA',NULL,2015,NULL)," +
		"('frc5614',5614,'Team Sycamore','http://teamsycamore5614.org'," +
		" 'Holon','Tel-Aviv','Israel',NULL,2015,'MORE Inspiration, MORE Technology, MORE Commitment')," +
		"('frc5615',5615,'Miners RC',NULL," +
		" 'Fort McMurray','Alberta','Canada',NULL,2015,NULL)," +
		"('frc5616',5616,'Green Machine','http://www.team5616.com'," +
		" 'Fort Walton Beach','Florida','USA',NULL,2015,'Vison')," +
		"('frc5617',5617,'Northern Lights',NULL," +
		" 'Fort McMurray','AB','Canada',NULL,2015,NULL)," +
		"('frc5618',5618,'PLS','http://www.pls5618.com'," +
		" 'Plessisville','Québec','Canada',NULL,2015,NULL)," +
		"('frc5619',5619,'RoboJags','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2015,NULL)," +
		"('frc5620',5620,'Community Team 1',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2015,NULL)," +
		"('frc5621',5621,'Cyberian Huskies','http://www.firstinspires.org/'," +
		" 'Edmond','Oklahoma','USA',NULL,2015,NULL)," +
		"('frc5622',5622,'Green Hornets','http://www.firstinspires.org/'," +
		" 'Stanton','Michigan','USA',NULL,2015,NULL)," +
		"('frc5623',5623,'Robotic Rams','http://roboticrams5623.weebly.com'," +
		" 'Galesburg','Michigan','USA',NULL,2015,NULL)," +
		"('frc5624',5624,'TIGER TECH Robotics','http://www.firstinspires.org/'," +
		" 'South Plainfield','New Jersey','USA',NULL,2015,'Syncing our teeth into technology.')," +
		"('frc5625',5625,'TrojanBots','http://www.sasmaui.org'," +
		" 'Wailuku','Hawaii','USA',NULL,2015,'No Magic, Just Physics')," +
		"('frc5626',5626,'Nu-matic Ninjas','http://raiders.central.k12.mn.us/domain/700'," +
		" 'Norwood Young America','Minnesota','USA',NULL,2015,'Frequently Awesome')," +
		"('frc5627',5627,'πrtz (Pi-rates)','http://www.sohengineering.org'," +
		" 'San Diego','California','USA',NULL,2015,'Bien Good!')," +
		"('frc5628',5628,'cyborg chakra','http://www.firstinspires.org/'," +
		" 'Jacksonville','Florida','USA',NULL,2015,NULL)," +
		"('frc5629',5629,'Community Team 2',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2015,NULL)," +
		"('frc5630',5630,'The Nomads','http://5630crt.com'," +
		" 'Calgary','Alberta','Canada',NULL,2015,NULL)," +
		"('frc5631',5631,'SpartanDroids','http://www.spartandroids.com'," +
		" 'Guelph','Ontario','Canada',NULL,2015,'Palma Per Ardua (Success through hard work)')," +
		"('frc5632',5632,'The Asimovians','http:///www.theasimovians.web.com'," +
		" 'Appling','Georgia','USA',NULL,2015,NULL)," +
		"('frc5633',5633,'Hyde Mecha Wolves','https://sites.google.com/hyde.edu/hyderobotics/meet-the-team'," +
		" 'Bath','Maine','USA',NULL,2015,NULL)," +
		"('frc5634',5634,'Jesterminators','http://www.firstinspires.org/'," +
		" 'Lakewood','California','USA',NULL,2015,NULL)," +
		"('frc5635',5635,'Demacia','http://www.firstinspires.org/'," +
		" 'nes ziona','HaMerkaz','Israel',NULL,2015,NULL)," +
		"('frc5636',5636,'Moona','http://moona.co'," +
		" 'Majd el Kurum','HaZafon','Israel',NULL,2015,'Space for Change ')," +
		"('frc5637',5637,'North Minneapolis Robotics','http://www.firstinspires.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2015,NULL)," +
		"('frc5638',5638,'LQPV Robotics','http://team5638.org'," +
		" 'Madison','Minnesota','USA',NULL,2015,NULL)," +
		"('frc5639',5639,'Loose Wires','http://www.firstinspires.org/'," +
		" 'Pharr','Texas','USA',NULL,2015,NULL)," +
		"('frc5640',5640,'Pegasus Robotics','http://hsesrobotics.com'," +
		" 'Philadelphia','PA','USA',NULL,2015,NULL)," +
		"('frc5641',5641,'Byron Robotics','http://team5641.com'," +
		" 'Byron','Michigan','USA',NULL,2015,NULL)," +
		"('frc5642',5642,'Mumford Mustangs','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2015,NULL)," +
		"('frc5643',5643,'TBD',NULL," +
		" 'Richmond','IN','USA',NULL,2015,NULL)," +
		"('frc5644',5644,'FHS ',NULL," +
		" 'Fairfield','Ohio','USA',NULL,2015,NULL)," +
		"('frc5645',5645,'Community Team 4',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2015,NULL)," +
		"('frc5646',5646,'Spider-Bots','http://www.firstinspires.org/'," +
		" 'Tampa','Florida','USA',NULL,2015,'Talk Nerdy To Me!')," +
		"('frc5647',5647,'BadgerBots','http://www.firstinspires.org/'," +
		" 'Los Angeles','California','USA',NULL,2015,NULL)," +
		"('frc5648',5648,'Melbourne RoboCats','http://www.melbournerobocats.org'," +
		" 'Hawthorn','Victoria','Australia',NULL,2015,NULL)," +
		"('frc5649',5649,'The Pithons',NULL," +
		" 'London','CMD','Kingdom',NULL,2015,NULL)," +
		"('frc5650',5650,'Carbonite Crusaders','http://www.firstinspires.org/'," +
		" 'Macomb','Michigan','USA',NULL,2015,NULL)," +
		"('frc5651',5651,'Maynard Jackson Jungle C.A.Ts','http://www.firstinspires.org/'," +
		" 'Atlanta','Georgia','USA',NULL,2015,NULL)," +
		"('frc5652',5652,'Eagles',NULL," +
		" 'Ottawa','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5653',5653,'Iron Mosquitos','http://ironmos5653.wix.com/web-studio-blog'," +
		" 'Babbitt','Minnesota','USA',NULL,2015,'Improve')," +
		"('frc5654',5654,'Phoenix','http://www.firstinspires.org/'," +
		" 'Arad','HaDarom','Israel',NULL,2015,NULL)," +
		"('frc5655',5655,'KelRot','http://www.keltechonline.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2015,'We Made, It Worked')," +
		"('frc5656',5656,'Panther-bots',NULL," +
		" 'Cabot','AR','USA',NULL,2015,NULL)," +
		"('frc5657',5657,'Rawhide Robotics','http://www.firstinspires.org/'," +
		" 'Lusk','Wyoming','USA',NULL,2015,NULL)," +
		"('frc5658',5658,'STORMBOTICS','http://www.sac.k12.mn.us'," +
		" 'Stephen','Minnesota','USA',NULL,2015,'Aut viam inveniam aut facium')," +
		"('frc5659',5659,'Team Supreme','http://www.firstinspires.org/'," +
		" 'Bridgehampton','New York','USA',NULL,2015,NULL)," +
		"('frc5660',5660,'Symmetrical Chaos','https://symmetricalchaos.wixsite.com/home'," +
		" 'Burton','Michigan','USA',NULL,2015,'The A.R.T. of Technology')," +
		"('frc5661',5661,'RoboCards','http://Whittemorbotics.com'," +
		" 'Whittemore','Michigan','USA',NULL,2015,'Hard Wired for Victory')," +
		"('frc5662',5662,'A Flock of Nerds','http://flockofnerds.weebly.com/'," +
		" 'Dryden','Michigan','USA',NULL,2015,'POR-Press on regardless. ')," +
		"('frc5663',5663,'Ground Control','http://www.firstinspires.org/'," +
		" 'Perth','Western Australia','Australia',NULL,2015,NULL)," +
		"('frc5664',5664,'Big Red Robotics','http://www.firstinspires.org/'," +
		" 'Muskegon','Michigan','USA',NULL,2015,NULL)," +
		"('frc5665',5665,'SPARC','http://www.sparc-5665.com'," +
		" 'Beyoğlu','Istanbul','Turkey',NULL,2015,'Keep It Greasy')," +
		"('frc5666',5666,'Purple Lightning','https://twitter.com/FRCteam5666'," +
		" 'Trenton','New Jersey','USA',NULL,2015,'Robots win the games, but teamwork wins the championship')," +
		"('frc5667',5667,'The Digital Eagles','http://www.nahsrobotics.org'," +
		" 'New Albany','Ohio','USA',NULL,2015,'All In Robotics!')," +
		"('frc5668',5668,'NHHS1',NULL," +
		" 'Bancroft','ON','Canada',NULL,2015,NULL)," +
		"('frc5669',5669,'Techmen','http://frctechmen-com.webs.com/'," +
		" 'Rosemead','California','USA',NULL,2015,NULL)," +
		"('frc5670',5670,'Al-Hayah',NULL," +
		" 'Jerusalem','Yerushalayim','Israel',NULL,2015,NULL)," +
		"('frc5671',5671,'ROBIN, Robótica Inemita',NULL," +
		" 'Medellín','ANT','Colombia',NULL,2015,NULL)," +
		"('frc5672',5672,'First Nations-STEM','http://team5672.weebly.com'," +
		" 'Wikwemikong','Ontario','Canada',NULL,2015,'Aanii Dash')," +
		"('frc5673',5673,'CougBorgs',NULL," +
		" 'Detroit','MI','USA',NULL,2015,NULL)," +
		"('frc5674',5674,'The Gearhounds','http://www.firstinspires.org/'," +
		" 'Eaton Rapids','Michigan','USA',NULL,2015,NULL)," +
		"('frc5675',5675,'WiredCats','http://www.mattawanwiredcats.org'," +
		" 'Mattawan','Michigan','USA',NULL,2015,NULL)," +
		"('frc5676',5676,'The H.E.R.O.E.S','http://hillsdalerobotics.wixsite.com/robotics'," +
		" 'Hillsdale','Michigan','USA',NULL,2015,'Challenge Accepted')," +
		"('frc5677',5677,'The Subatomic Smarticles','http://www.team5677.com'," +
		" 'San Jose','California','USA',NULL,2015,NULL)," +
		"('frc5678',5678,'Knightrise','http://knightrise.com'," +
		" 'Valley Glen','California','USA',NULL,2015,NULL)," +
		"('frc5679',5679,'Girls on Fire','http://www.girlsonfire5679.com'," +
		" 'Winston Salem','North Carolina','USA',NULL,2015,'Empowering Young Women in Science, Technology, Engineering, Arts and Mathematics')," +
		"('frc5680',5680,'KYS Robotics','http://www.firstinspires.org/'," +
		" 'Toronto','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5681',5681,'Dragonbots','http://www.firstinspires.org/'," +
		" 'San Antonio','Texas','USA',NULL,2015,NULL)," +
		"('frc5682',5682,'Equus Engineering','http://www.firstinspires.org/'," +
		" 'Mesquite','Texas','USA',NULL,2015,NULL)," +
		"('frc5683',5683,'Hello World','http://www.firstinspires.org/'," +
		" 'Auburn','Washington','USA',NULL,2015,NULL)," +
		"('frc5684',5684,'Iron Mechs','https://trentoncatholic.org/robotics'," +
		" 'Trenton','New Jersey','USA',NULL,2015,NULL)," +
		"('frc5685',5685,'Robo Terriers','http://www.rkangas2.wix.com/roboterriers'," +
		" 'Litchfield','Michigan','USA',NULL,2015,NULL)," +
		"('frc5686',5686,'Wirecats','http://frc5686.wix.com/wirecats#!meet-the-team/cshm'," +
		" 'Simsbury','Connecticut','USA',NULL,2015,NULL)," +
		"('frc5687',5687,'The Outliers','http://www.firstinspires.org/'," +
		" 'Portland','Maine','USA',NULL,2015,NULL)," +
		"('frc5688',5688,'Robo Cats','http://www.firstinspires.org/'," +
		" 'Pittsford','Michigan','USA',NULL,2015,NULL)," +
		"('frc5689',5689,'CK Cyber Pack','https://www.ckcyberpack.com/'," +
		" 'Chatham','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5690',5690,'SubZero Robotics','http://www.subzerorobotics.wordpress.com'," +
		" 'Esko','Minnesota','USA',NULL,2015,'Build it. Wire it. Program it.')," +
		"('frc5691',5691,'Team Tiger',NULL," +
		" 'Memphis','Tennessee','USA',NULL,2015,NULL)," +
		"('frc5692',5692,'ChiefBots','http://www.firstinspires.org/'," +
		" 'Cheboygan','Michigan','USA',NULL,2015,NULL)," +
		"('frc5693',5693,'Springport High School',NULL," +
		" 'Springport','MI','USA',NULL,2015,NULL)," +
		"('frc5694',5694,'Hamady Hawks','http:///www.hamadyhawks.net'," +
		" 'Flint','Michigan','USA',NULL,2015,'Be good.  Have fun.  ')," +
		"('frc5695',5695,'Heavy Duty Eagles','http://www.firstinspires.org/'," +
		" 'Redford','Michigan','USA',NULL,2015,NULL)," +
		"('frc5696',5696,'Tecmilenio Faraday 5696','http://www.faraday5696.com'," +
		" 'Villahermosa','Tabasco','Mexico',NULL,2015,'It was just a dream until we made it possible')," +
		"('frc5697',5697,'Bearcats','http://www.firstinspires.org/'," +
		" 'Bridgeport','Michigan','USA',NULL,2015,NULL)," +
		"('frc5698',5698,'Raider Robotics',NULL," +
		" 'Decatur','MI','USA',NULL,2015,NULL)," +
		"('frc5699',5699,'Robo Sapiens','http://www.firstinspires.org/'," +
		" 'Toronto','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5700',5700,'SOTA Cyberdragons','http://www.team5700.org/'," +
		" 'San Francisco','California','USA',NULL,2015,NULL)," +
		"('frc5701',5701,'RAIJINbotics','http://www.raijin-botics.com'," +
		" 'Machida','Tôkyô','Japan',NULL,2015,NULL)," +
		"('frc5702',5702,'Robotawatomi','http://www.hannahvilleschool.net/departments/robotics'," +
		" 'Wilson','Michigan','USA',NULL,2015,NULL)," +
		"('frc5703',5703,'Eagles Crew','http://www.firstinspires.org/'," +
		" 'Midland','Michigan','USA',NULL,2015,NULL)," +
		"('frc5704',5704,'Blended Learning Academies','http://www.firstinspires.org/'," +
		" 'Lansing','Michigan','USA',NULL,2015,NULL)," +
		"('frc5705',5705,'DESERT EAGLES','http://www.frcdeserteagles.com/index.html'," +
		" 'Torreon','Coahuila','Mexico',NULL,2015,'Inspired in life, be yourself !!!')," +
		"('frc5706',5706,'Tahquamenon Phenomenon','http://www.firstinspires.org/'," +
		" 'Newberry','Michigan','USA',NULL,2015,'Yeti ')," +
		"('frc5707',5707,'Roseville High School Robotics',NULL," +
		" 'Roseville','MI','USA',NULL,2015,NULL)," +
		"('frc5708',5708,'Zebrotics','http://www.firstinspires.org/'," +
		" 'Ann Arbor','Michigan','USA',NULL,2015,NULL)," +
		"('frc5709',5709,'Rudyard Nerf Herders','http://www.frc5709.com'," +
		" 'Rudyard','Michigan','USA',NULL,2015,NULL)," +
		"('frc5710',5710,'The O-Bots','http://charlotteobots.wix.com/robotics'," +
		" 'Charlotte','Michigan','USA',NULL,2015,'Running on all gears')," +
		"('frc5711',5711,'Battling Bathers','http://www.firstinspires.org/'," +
		" 'Mt. Clemens','Michigan','USA',NULL,2015,NULL)," +
		"('frc5712',5712,'Hemlock''s Gray Matter','http://team5712.org'," +
		" 'Hemlock','Michigan','USA',NULL,2015,'It''s OK Mr. Urbaniak')," +
		"('frc5713',5713,'Beecher Cyber Bucs','http://www.firstinspires.org/'," +
		" 'Mount Morris','Michigan','USA',NULL,2015,NULL)," +
		"('frc5714',5714,'Accidental Success ','http://www.firstinspires.org/'," +
		" 'Brimley','Michigan','USA',NULL,2015,NULL)," +
		"('frc5715',5715,'DRC','http://www.firstinspires.org/'," +
		" 'Dabburiya','HaZafon','Israel',NULL,2015,NULL)," +
		"('frc5716',5716,'PrepaTec - Keybot','http://www.keybot.mx'," +
		" 'pachuca','Hidalgo','Mexico',NULL,2015,NULL)," +
		"('frc5717',5717,'Sasquach','http://www.firstinspires.org/'," +
		" 'Soddy Daisy','Tennessee','USA',NULL,2015,NULL)," +
		"('frc5718',5718,'Campion Bears',NULL," +
		" 'Brampton','ON','Canada',NULL,2015,NULL)," +
		"('frc5719',5719,'Pink Titans','http://www.PinkTitans.ca'," +
		" 'Toronto','Ontario','Canada',NULL,2015,'Nothing worthwhile is achieved without hard work.')," +
		"('frc5720',5720,'Jagobotics','http://www.firstinspires.org/'," +
		" 'Hinckley','Minnesota','USA',NULL,2015,NULL)," +
		"('frc5721',5721,'Wolf Pack','http://www.firstinspires.org/'," +
		" 'Newark','Arkansas','USA',NULL,2015,NULL)," +
		"('frc5722',5722,'Seahawks','http://WWW.SWHSROBOTICS.COM'," +
		" 'Santa Rosa Beach','Florida','USA',NULL,2015,'Challenge, Achieve, Excel')," +
		"('frc5723',5723,'Robert Land Academy',NULL," +
		" 'Wellandport','ON','Canada',NULL,2015,NULL)," +
		"('frc5724',5724,'Spartan Robotics','http://shsrobotics.us'," +
		" 'Salem','Virginia','USA',NULL,2015,'EYKD')," +
		"('frc5725',5725,'Cerberus','http://www.frc5725.com/'," +
		" 'Lethbridge','Alberta','Canada',NULL,2015,NULL)," +
		"('frc5726',5726,'RHUMBOTZ','https://www.facebook.com/STEM-ECHS-Robotics-232793157072084/info/?tab=page_info'," +
		" 'San Antonio','Texas','USA',NULL,2015,NULL)," +
		"('frc5727',5727,'REaCH Omegabytes','http://www.firstinspires.org/'," +
		" 'Spindale','North Carolina','USA',NULL,2015,NULL)," +
		"('frc5728',5728,'MC²','http://carrillocybernetics.com'," +
		" 'Santa Rosa','California','USA',NULL,2015,NULL)," +
		"('frc5729',5729,' MAD Dragons','http://www.firstinspires.org/'," +
		" 'De Witt','Arkansas','USA',NULL,2015,NULL)," +
		"('frc5730',5730,'The Professionals ','http://www.frc5730.com'," +
		" 'Monett','Missouri','USA',NULL,2015,NULL)," +
		"('frc5731',5731,'Tiny Giants','http://www.firstinspires.org/'," +
		" 'Yanco','New South Wales','Australia',NULL,2015,NULL)," +
		"('frc5732',5732,' ROBOTIGERS','http://Pending'," +
		" 'Bloomfield','New Jersey','USA',NULL,2015,'Excellence is always an option!')," +
		"('frc5733',5733,'NGeek','http://neumanngorettihs.org'," +
		" 'Philadelphia','Pennsylvania','USA',NULL,2015,NULL)," +
		"('frc5734',5734,'RoboReign','http://www.RoboReign.com'," +
		" 'Augusta','Georgia','USA',NULL,2015,'Expect More Get More')," +
		"('frc5735',5735,'Control Freaks','http://frc5735.com/'," +
		" 'Wayland','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5736',5736,'Kingsmen','https://t.co/wI5UM40qti'," +
		" 'Kings Park','New York','USA',NULL,2015,NULL)," +
		"('frc5737',5737,'Mars Style','http://www.firstinspires.org/'," +
		" 'Beijing','Beijing','China',NULL,2015,'Bring you to Mars!')," +
		"('frc5738',5738,'warriors',NULL," +
		" 'Central Falls','Rhode Island','USA',NULL,2015,NULL)," +
		"('frc5739',5739,'Crestbots','http://Hillcrestbots.weebly.com'," +
		" 'Dallas','Texas','USA',NULL,2015,NULL)," +
		"('frc5740',5740,'Trojanators','http://trojanators.weebly.com/'," +
		" 'Cranberry Township','Pennsylvania','USA',NULL,2015,NULL)," +
		"('frc5741',5741,'Coyotes',NULL," +
		" 'Alice','TX','USA',NULL,2015,NULL)," +
		"('frc5742',5742,'Gators',NULL," +
		" 'Langley','British Columbia','Canada',NULL,2015,NULL)," +
		"('frc5743',5743,'Robo-Wolves',NULL," +
		" 'Jacksonville','Arkansas','USA',NULL,2015,NULL)," +
		"('frc5744',5744,'RoboRunners','http://www.firstinspires.org/'," +
		" 'Knoxville','Tennessee','USA',NULL,2015,NULL)," +
		"('frc5745',5745,'Gearheads','http://www.firstinspires.org/'," +
		" 'Three Oaks','Michigan','USA',NULL,2015,'Keep it simple.')," +
		"('frc5746',5746,'Human Error','http://www.firstinspires.org/'," +
		" 'Deep River','Connecticut','USA',NULL,2015,NULL)," +
		"('frc5747',5747,'Athena','https://www.facebook.com/Athena5747'," +
		" 'Beer Sheva','HaDarom','Israel',NULL,2015,'There is no substitute for quality')," +
		"('frc5748',5748,'Adna Pirate Robotics','http://Search Adna Pirate Robotics on Facebook'," +
		" 'Adna','Washington','USA',NULL,2015,'Its a Pirate''s life for me')," +
		"('frc5749',5749,'Tokyo Technical Samurai','http://tokyotechnicalsamurai.ml'," +
		" 'Aoki,Kawaguchi-shi','Saitama','Japan',NULL,2015,'Making Efforts for Our Learning')," +
		"('frc5750',5750,'Martian Mechanics',NULL," +
		" 'Naucalpan','Mexico','Mexico',NULL,2015,NULL)," +
		"('frc5751',5751,'Myerbott',NULL," +
		" 'Deep River','CT','USA',NULL,2015,NULL)," +
		"('frc5752',5752,'Bevbotics','http://www.firstinspires.org/'," +
		" 'Beverly','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5753',5753,'St. Joseph Academy Flashes','http://www.firstinspires.org/'," +
		" 'St. Augustine','Florida','USA',NULL,2015,NULL)," +
		"('frc5754',5754,'Brooks Academy Robotics','http://www.firstinspires.org/'," +
		" 'San Antonio','Texas','USA',NULL,2015,NULL)," +
		"('frc5755',5755,'Central Catholic Robotics',NULL," +
		" 'San Antonio','TX','USA',NULL,2015,NULL)," +
		"('frc5756',5756,'R.E.C''in Crew','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2015,NULL)," +
		"('frc5757',5757,'Eagletrons',NULL," +
		" 'Orchard Lake','MI','USA',NULL,2015,NULL)," +
		"('frc5758',5758,'C-Bots','http://www.urbanfuturestl.org'," +
		" 'Saint Louis','Missouri','USA',NULL,2015,'\"Building Futures\"')," +
		"('frc5759',5759,'TroBotics',NULL," +
		" 'Brampton','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5760',5760,'The Marinators','http://Marinators.Jimdo.com'," +
		" 'Marina','California','USA',NULL,2015,NULL)," +
		"('frc5761',5761,'Illawarra','http://www.illawarra-5761.org'," +
		" 'Cordeaux Heights','New South Wales','Australia',NULL,2015,NULL)," +
		"('frc5762',5762,'FranklinBots - TEAM HYDRA','https://www.facebook.com/franklinbots/'," +
		" 'Louisburg','North Carolina','USA',NULL,2015,'An investment in knowledge pays the best interest.')," +
		"('frc5763',5763,'Lightning Robotics','https://sites.google.com/adams12.org/lightningrobotics/home'," +
		" 'Broomfield','Colorado','USA',NULL,2015,NULL)," +
		"('frc5764',5764,'Grasshopper Bots',NULL," +
		" 'Loch Sheldrake','NY','USA',NULL,2015,NULL)," +
		"('frc5765',5765,'ToroBots','http://www.firstinspires.org/'," +
		" 'Los Angeles','California','USA',NULL,2015,NULL)," +
		"('frc5766',5766,'ET Robotics',NULL," +
		" 'Hawthorn','Massachusetts','USA',NULL,2015,NULL)," +
		"('frc5767',5767,'VPE and Veritas Robotics','http://www.vpe-VeritasRobotics.org'," +
		" 'Duluth','GA','USA',NULL,2015,NULL)," +
		"('frc5768',5768,'Leuzinger Resistors',NULL," +
		" 'Lawndale','California','USA',NULL,2015,NULL)," +
		"('frc5769',5769,'Radio Rangers R^2',NULL," +
		" 'Santa Fe','Texas','USA',NULL,2015,NULL)," +
		"('frc5770',5770,'ReMIT ',NULL," +
		" 'Inglewood','California','USA',NULL,2015,NULL)," +
		"('frc5771',5771,'Marfa Robohorns','https://www.facebook.com/MarfaRobotics'," +
		" 'Marfa','Texas','USA',NULL,2015,NULL)," +
		"('frc5772',5772,'The Mighty Morphin Power Bots','http://www.firstinspires.org/'," +
		" 'Los Angeles ','California','USA',NULL,2015,NULL)," +
		"('frc5773',5773,'YAFL Mechatronics','http://www.yaflmechatronics.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2015,'We are the change')," +
		"('frc5774',5774,'Maximum Overload','http://TBD'," +
		" 'Flint','Michigan','USA',NULL,2015,'TBD')," +
		"('frc5775',5775,'CRHS RoboCats','http://www.facebook.com/CrossRoadsHighSchoolRobotics'," +
		" 'Malakoff','Texas','USA',NULL,2015,'Let''s give this a shot!')," +
		"('frc5776',5776,'Phoenix','http://www.firstinspires.org/'," +
		" 'Toronto','Ontario','Canada',NULL,2015,NULL)," +
		"('frc5777',5777,'Static Storm','Coming Soon'," +
		" 'Sumter','South Carolina','USA',NULL,2015,NULL)," +
		"('frc5778',5778,'The Power-Struck Girls',NULL," +
		" 'Marrero','LA','USA',NULL,2015,NULL)," +
		"('frc5779',5779,'CB Tech',NULL," +
		" 'Moses Lake','Washington','USA',NULL,2015,NULL)," +
		"('frc5780',5780,'Harvesters',NULL," +
		" 'Pampa','Texas','USA',NULL,2015,NULL)," +
		"('frc5781',5781,'Petchey Robotics Team ','http://www.firstinspires.org/'," +
		" 'London','England','United Kingdom',NULL,2015,NULL)," +
		"('frc5782',5782,'Sullivan 4H-Newport',NULL," +
		" 'Newport','New Hampshire','USA',NULL,2015,NULL)," +
		"('frc5783',5783,'RoboDucks','Coming Soon'," +
		" 'Eunice','New Mexico','USA',NULL,2015,NULL)," +
		"('frc5784',5784,'Vassar Voltage','http://www.firstinspires.org/'," +
		" 'Vassar','Michigan','USA',NULL,2015,NULL)," +
		"('frc5785',5785,'The Shield','Coming Soon'," +
		" 'Baton Rouge','Louisiana','USA',NULL,2015,NULL)," +
		"('frc5786',5786,'Pro-Botics','http://unhprobotics.co.vu'," +
		" 'Irving','Texas','USA',NULL,2015,NULL)," +
		"('frc5787',5787,'NEXUS Robotics',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2015,NULL)," +
		"('frc5800',5800,'Magic Island Robotics','http://www.mi5800.wordpress.com'," +
		" 'FLORIANOPOLIS','Santa Catarina','Brazil',NULL,2016,NULL)," +
		"('frc5801',5801,'CTC Inspire','http://www.ctcinspire.org'," +
		" 'Independence','Missouri','USA',NULL,2016,NULL)," +
		"('frc5802',5802,'Los STEMateros','http://www.firstinspires.org/'," +
		" 'Los Angeles','California','USA',NULL,2016,'Si Se Puede')," +
		"('frc5803',5803,'Apex Robotics','http://www.apexfrc.com'," +
		" 'Seattle','Washington','USA',NULL,2016,'Pursuing the Peak of Excellence')," +
		"('frc5804',5804,'TORCH','http://www.firstinspires.org/'," +
		" 'Henrico','Virginia','USA',NULL,2016,'Parat Ditat Durat.')," +
		"('frc5805',5805,'SMbly Required','https://smblyrequired.com/'," +
		" 'Rancho Santa Margarita','California','USA',NULL,2016,'Caritas robotium')," +
		"('frc5806',5806,'Basement Lions','http://www.frc5806.org'," +
		" 'Bronx','New York','USA',NULL,2016,NULL)," +
		"('frc5807',5807,'CANbotics','http://5807canbotics.ca'," +
		" 'North Bay','Ontario','Canada',NULL,2016,'The Community Team')," +
		"('frc5808',5808,'Revive Robotics','http://www.firstinspires.org/'," +
		" 'Lodi','California','USA',NULL,2016,NULL)," +
		"('frc5809',5809,'The Jesubots','http://www.jesubots.org/'," +
		" 'Kansas City','Missouri','USA',NULL,2016,'AMDG')," +
		"('frc5810',5810,'X-Bots Robotics','http://www.xbotsrobotics.com'," +
		" 'Santa Fe Springs','California','USA',NULL,2016,NULL)," +
		"('frc5811',5811,'BONDS','http://bondsrobotics.com'," +
		" 'Dayton','Ohio','USA',NULL,2016,'License to Drill')," +
		"('frc5812',5812,'Drewbotics','http://www.firstinspires.org/'," +
		" 'Riverdale','Georgia','USA',NULL,2016,'Strive for Excellence...Attack from TITANS!')," +
		"('frc5813',5813,'Morpheus','http://5813.frc.team'," +
		" 'Concord','New Hampshire','USA',NULL,2016,'Dream Big ')," +
		"('frc5814',5814,'ROBOTA','http://www.robota5814-ec.org/'," +
		" 'Guayaquil','Guayas','Ecuador',NULL,2016,NULL)," +
		"('frc5815',5815,'Bond Science Engineer','http://www.firstinspires.org/'," +
		" 'SHENZHEN','Guangdong','China',NULL,2016,'BOND AND BE GREAT')," +
		"('frc5816',5816,'Gra-V Robotics','http://gra-v.org'," +
		" 'Orlando','Florida','USA',NULL,2016,NULL)," +
		"('frc5817',5817,'Uni-Rex','http://www.unirex5817.com'," +
		" 'Sanger','California','USA',NULL,2016,NULL)," +
		"('frc5818',5818,'Riviera Robotics','http://rivierarobotics.org/'," +
		" 'Santa Barbara','California','USA',NULL,2016,'Mostly Harmless')," +
		"('frc5819',5819,'Mecha Makos','http://www.firstinspires.org/'," +
		" 'Key Biscayne','Florida','USA',NULL,2016,'Giving our Students a World of Choices')," +
		"('frc5820',5820,'PathFinder','http://www.firstinspires.org/'," +
		" 'YuLin','Shanxi','China',NULL,2016,NULL)," +
		"('frc5821',5821,'The Saunders Robotics Club','http://www.firstinspires.org/'," +
		" 'London','Ontario','Canada',NULL,2016,NULL)," +
		"('frc5822',5822,'WolfByte','http://www.sicprobotics.com'," +
		" 'Chicago','Illinois','USA',NULL,2016,NULL)," +
		"('frc5823',5823,'ACE','http://www.frc5823.com'," +
		" 'Shenzhen','Guangdong','China',NULL,2016,NULL)," +
		"('frc5824',5824,'Orion','http://www.firstinspires.org/'," +
		" 'SHANGHAI','Shanghai','China',NULL,2016,NULL)," +
		"('frc5825',5825,'Amherst Robotics',NULL," +
		" 'Amherstburg','ON','Canada',NULL,2016,NULL)," +
		"('frc5826',5826,'Avis Automata','https://www.facebook.com/avisautomata/'," +
		" 'Chippewa Falls','Wisconsin','USA',NULL,2016,NULL)," +
		"('frc5827',5827,'Code Purple','http://codepurple5827.com'," +
		" 'Kirkland','Washington','USA',NULL,2016,NULL)," +
		"('frc5828',5828,'The Robot Masters','http://www.firstinspires.org/'," +
		" 'Albany','Georgia','USA',NULL,2016,NULL)," +
		"('frc5829',5829,'AwtyBots','http://www.awtybots.org'," +
		" 'Houston','Texas','USA',NULL,2016,'Awtybots Roll Out!')," +
		"('frc5830',5830,'LIFE Engineering','http://www.team5830.org'," +
		" 'Gambrills','Maryland','USA',NULL,2016,'\"Let''s Be Honest, We''re All a Little Irrational\"')," +
		"('frc5831',5831,'Revolution Maker','http://www.firstinspires.org/'," +
		" 'Macau','Macau','China',NULL,2016,NULL)," +
		"('frc5832',5832,'FULL OF AMBITION','http://www.firstinspires.org/'," +
		" 'zhongshan','Guangdong','China',NULL,2016,NULL)," +
		"('frc5833',5833,'Team-5833',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2016,NULL)," +
		"('frc5834',5834,'R3P2','http://riverdalerobotics.com/'," +
		" 'Toronto','Ontario','Canada',NULL,2016,NULL)," +
		"('frc5835',5835,'Sage Robotics','http://sagehillrobotics.org'," +
		" 'Newport Coast','California','USA',NULL,2016,'Intelligence, Education, Innovation')," +
		"('frc5836',5836,'Mechanic Mutts','http://www.firstinspires.org/'," +
		" 'Saint Louis','Missouri','USA',NULL,2016,NULL)," +
		"('frc5837',5837,'Unity4Tech','http://www.firstinspires.org/'," +
		" 'Waterloo','Iowa','USA',NULL,2016,'We work for TiPS')," +
		"('frc5838',5838,'Mitochondria','http://www.firstinspires.org/'," +
		" 'Kunming ','Yunnan','China',NULL,2016,NULL)," +
		"('frc5839',5839,'Blue Whales','http://www.firstinspires.org/'," +
		" 'Nanjing','Jiangsu','China',NULL,2016,NULL)," +
		"('frc5840',5840,'Shock Wave','http://www.firstinspires.org/'," +
		" 'Dawson','Georgia','USA',NULL,2016,NULL)," +
		"('frc5841',5841,'The Patriots','http://www.firstinspires.org/'," +
		" 'Frederick','Maryland','USA',NULL,2016,'Respect, Responsibility, Ready')," +
		"('frc5842',5842,'Royal Robotics','http://www.firstinspires.org/'," +
		" 'New Port Richey','Florida','USA',NULL,2016,NULL)," +
		"('frc5843',5843,'Flurb','http://flurb5843.org'," +
		" 'East China','Michigan','USA',NULL,2016,NULL)," +
		"('frc5844',5844,'Phoenix Tech','http://www.firstinspires.org/'," +
		" 'Boston','Massachusetts','USA',NULL,2016,NULL)," +
		"('frc5845',5845,'Twisted Gears','http://twistedgears5845.weebly.com/'," +
		" 'Athens','Alabama','USA',NULL,2016,'The sum of us is greater than all our parts.  Maya Angelou')," +
		"('frc5846',5846,'SouthCoast Corsairs','https://www.facebook.com/frc5846/'," +
		" 'North Dartmouth','Massachusetts','USA',NULL,2016,NULL)," +
		"('frc5847',5847,'Ironclad','http://www.ironclad5847.com'," +
		" 'Bradley','Illinois','USA',NULL,2016,NULL)," +
		"('frc5848',5848,'ROBO C3','http://www.firstinspires.org/'," +
		" 'Edison','Georgia','USA',NULL,2016,'Press towards the mark')," +
		"('frc5849',5849,'Joker','http://www.cuyra.cn'," +
		" 'Qingdao','Shandong','China',NULL,2016,NULL)," +
		"('frc5850',5850,'Guangzhou  lego xueyuan',NULL," +
		" 'Guangzhou','44','China',NULL,2016,NULL)," +
		"('frc5851',5851,'Striking Vikings','http://team5851.com'," +
		" 'Downey','California','USA',NULL,2016,NULL)," +
		"('frc5852',5852,'Illusion Robotics ','http://www.firstinspires.org/'," +
		" 'Merced','California','USA',NULL,2016,NULL)," +
		"('frc5853',5853,'FEAR The Termigators','http://www.firstinspires.org/'," +
		" 'Ypsilanti','Michigan','USA',NULL,2016,NULL)," +
		"('frc5854',5854,'GLITCH','http://team5854.com'," +
		" 'Asheville','North Carolina','USA',NULL,2016,NULL)," +
		"('frc5855',5855,'Blue Collar Bruisers','http://www.firstinspires.org/'," +
		" 'Milwaukee','Wisconsin','USA',NULL,2016,NULL)," +
		"('frc5856',5856,'Bullbots','http://www.firstinspires.org/'," +
		" 'Waterbury','Connecticut','USA',NULL,2016,NULL)," +
		"('frc5857',5857,'Walnut Valley Robotics','http://www.team5857.com'," +
		" 'Walnut','California','USA',NULL,2016,'Change the World')," +
		"('frc5858',5858,'Golden Hurricane ','http://www.columbiahsrobotics.space'," +
		" 'Huntsville','Alabama','USA',NULL,2016,'motivation, collaboration, innovation')," +
		"('frc5859',5859,'i','http://frci.weebly.com/'," +
		" 'Canton de Hatley','Québec','Canada',NULL,2016,'Be the exception')," +
		"('frc5860',5860,'Full Metal Muskrats','http://algonacrobotics.com'," +
		" 'Algonac','Michigan','USA',NULL,2016,NULL)," +
		"('frc5861',5861,'ManGeKyo','http://www.asiafrc.com/index.php/en/chinese-team/team-5861/'," +
		" 'guangzhou','44','China',NULL,2016,NULL)," +
		"('frc5862',5862,'Atomic Disco Robotic Engineers','www.piedmontstem.com'," +
		" 'concord','VA','USA',NULL,2016,NULL)," +
		"('frc5863',5863,'Hypercubed','Coming Soon'," +
		" 'Brusly','Louisiana','USA',NULL,2016,NULL)," +
		"('frc5864',5864,'Robo-Pelicans','Coming Soon'," +
		" 'Port Allen','Louisiana','USA',NULL,2016,NULL)," +
		"('frc5865',5865,'Droide','http://droide.ca'," +
		" 'Terrebonne','Québec','Canada',NULL,2016,'Autonomie. Creativite. Ingeniosite')," +
		"('frc5866',5866,'Fe [Iron] Tigers','http://jholloway59.wixsite.com/frenshipengineering'," +
		" 'Wolfforth','Texas','USA',NULL,2016,NULL)," +
		"('frc5867',5867,'fighters science','Coming Soon'," +
		" 'Los Mochis','Sinaloa','Mexico',NULL,2016,NULL)," +
		"('frc5868',5868,'UTM Veracruz',NULL," +
		" 'Veracruz','VER','Mexico',NULL,2016,NULL)," +
		"('frc5869',5869,'Radical Robotics','http://www.qhhsfrc.weebly.com'," +
		" 'Lancaster','California','USA',NULL,2016,'For the Team')," +
		"('frc5870',5870,'League of Logic','http://www.leagueoflogic.ca'," +
		" 'London','Ontario','Canada',NULL,2016,'To Try is to Triumph')," +
		"('frc5871',5871,'Chickadees','http://coen.boisestate.edu/firstrobotics'," +
		" 'Boise','Idaho','USA',NULL,2016,NULL)," +
		"('frc5872',5872,'WiredCats','http://www.thewiredcats.com/'," +
		" 'Fort Lauderdale','Florida','USA',NULL,2016,'Imagine the Impossible')," +
		"('frc5873',5873,'Farmersville Robotics','http://farmersvillerobotics.wix.com/farmersvillerobotics'," +
		" 'Farmersville','Texas','USA',NULL,2016,NULL)," +
		"('frc5874',5874,'Tecmilenio BorderBots 5874','https://www.facebook.com/BorderBots5874/'," +
		" 'Nuevo Laredo','Tamaulipas','Mexico',NULL,2016,NULL)," +
		"('frc5875',5875,'ICE Cubed','http://not yet'," +
		" 'Fernley','Nevada','USA',NULL,2016,'Let them have the present, for the future is mine (Nikola Tesla)')," +
		"('frc5876',5876,'ARTEMIS','http://www.facebook.com/artemisrobotics'," +
		" 'Wahroonga','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc5877',5877,'Mechalodons','http://team5877.com'," +
		" 'Corona','California','USA',NULL,2016,'Building Robots. Delivering Results.')," +
		"('frc5878',5878,'The Great Lakers','http://www.facebook.com/FRC5878'," +
		" 'Mackinac Island','Michigan','USA',NULL,2016,NULL)," +
		"('frc5879',5879,'Shadow of Orion','http://www.firstinspires.org/'," +
		" 'Beijing','Beijing','China',NULL,2016,NULL)," +
		"('frc5880',5880,'AF North HS','http://www.firstinspires.org/'," +
		" 'Brunssum','Limburg','Netherlands',NULL,2016,NULL)," +
		"('frc5881',5881,'TVHS Dragons','http://www.firstinspires.org/'," +
		" 'Albany','New York','USA',NULL,2016,NULL)," +
		"('frc5882',5882,'Titan Robotics','http://www.firstinspires.org/'," +
		" 'Twin Valley','Minnesota','USA',NULL,2016,NULL)," +
		"('frc5883',5883,'Spice Gears','http://spicegears.pl'," +
		" 'Krasnik','Lubelskie','Poland',NULL,2016,NULL)," +
		"('frc5884',5884,'Gray Storm','http://www.firstinspires.org/'," +
		" 'San Diego','California','USA',NULL,2016,NULL)," +
		"('frc5885',5885,'Villanova WiredCats','http://www.WiredCats5885.ca'," +
		" 'LaSalle','Ontario','Canada',NULL,2016,NULL)," +
		"('frc5886',5886,'LAAE','http://www.firstinspires.org/'," +
		" 'Hacienda Heights','California','USA',NULL,2016,NULL)," +
		"('frc5887',5887,'PrepaTec-IMPERATOR','http://www.imperator5887.com'," +
		" 'Puebla','Puebla','Mexico',NULL,2016,'to infinity and beyond')," +
		"('frc5888',5888,'M.E.T.A. Robotics','Coming Soon'," +
		" 'Lubbock','Texas','USA',NULL,2016,NULL)," +
		"('frc5889',5889,'Commandobots','http://commandorobotics.com/'," +
		" 'Tulsa','Oklahoma','USA',NULL,2016,NULL)," +
		"('frc5890',5890,'Tec Milenio Durango','Coming Soon'," +
		" 'Durango','Durango','Mexico',NULL,2016,NULL)," +
		"('frc5891',5891,'UASGC Robosquad','https://robosquad5891.wordpress.com/'," +
		" 'New York','New York','USA',NULL,2016,'Robots!')," +
		"('frc5892',5892,'Energy HEROs','http://www.facebook.com/team5892'," +
		" 'Houston','Texas','USA',NULL,2016,NULL)," +
		"('frc5893',5893,'International Grammar School','http://www.rigsteam.com'," +
		" 'Ultimo','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc5894',5894,'J.E.H.S. CyberJags','https://www.facebook.com/FRC-Team-5894-CyberJags-505679949605420/'," +
		" 'Edinburg','Texas','USA',NULL,2016,NULL)," +
		"('frc5895',5895,'Peddie School Robotics','http://frc5895.peddie.org/'," +
		" 'Hightstown','New Jersey','USA',NULL,2016,'Finimus Pariter Renovamusque Labores')," +
		"('frc5896',5896,'Fire 6','http://www.firstinspires.org/'," +
		" 'Beijing','Beijing','China',NULL,2016,NULL)," +
		"('frc5897',5897,'APEX Robotics','http://apexroboticsfrc.weebly.com/'," +
		" 'Calgary','Alberta','Canada',NULL,2016,NULL)," +
		"('frc5898',5898,'RoboFalcons','Coming Soon'," +
		" 'Darlington','South Carolina','USA',NULL,2016,NULL)," +
		"('frc5899',5899,'FROST','http://frc5899.org'," +
		" 'Littleton','Colorado','USA',NULL,2016,'Optimizing Science & Technology')," +
		"('frc5900',5900,'Career Blazer Robotics Team - The Fighting Mongooses','https://sites.google.com/wcsga.net/team5900'," +
		" 'Dalton','Georgia','USA',NULL,2016,NULL)," +
		"('frc5901',5901,'Cougar Pack','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2016,'Cougars to and through college...On a robot!')," +
		"('frc5902',5902,'The Wire Clippers','http://www.newheightsonline.org'," +
		" 'Portsmouth','New Hampshire','USA',NULL,2016,NULL)," +
		"('frc5903',5903,'ThorBots','https://sites.google.com/a/westby-norse.org/official-district-website/'," +
		" 'Westby','Wisconsin','USA',NULL,2016,'Go Big or Go Home')," +
		"('frc5904',5904,'Iron 101','http://www.firstinspires.org/'," +
		" 'Beijing','Beijing','China',NULL,2016,NULL)," +
		"('frc5905',5905,'RoboCorps','http://team5905.weebly.com'," +
		" 'San Jose','California','USA',NULL,2016,NULL)," +
		"('frc5906',5906,'Titanium Badgers','http://www.firstinspires.org/'," +
		" 'Bennington','Nebraska','USA',NULL,2016,NULL)," +
		"('frc5907',5907,'CC Shambots','http://www.ccshambots.com'," +
		" 'Novi','Michigan','USA',NULL,2016,NULL)," +
		"('frc5908',5908,'Spartans','http://www.spartans5908robotics.com'," +
		" 'Stafford','Texas','USA',NULL,2016,'\"Work hard, play right\"')," +
		"('frc5909',5909,'Cardinal Gibbons Cyber Chiefs','http://www.firstinspires.org/'," +
		" 'Fort Lauderdale','Florida','USA',NULL,2016,NULL)," +
		"('frc5910',5910,'Supertronix','https://www.facebook.com/supertronix5910/'," +
		" 'Matane','Québec','Canada',NULL,2016,'Pas de réussite sans équipe')," +
		"('frc5911',5911,'PARAGON','http://www.firstinspires.org/'," +
		" 'Scarborough','Ontario','Canada',NULL,2016,NULL)," +
		"('frc5912',5912,'Heritage Robotics','http://www.firstinspires.org/'," +
		" 'Jordan','Ontario','Canada',NULL,2016,NULL)," +
		"('frc5913',5913,'Patriotics','http://patriotics186.weebly.com'," +
		" 'Pequot Lakes','Minnesota','USA',NULL,2016,NULL)," +
		"('frc5914',5914,'Robotic Warriors','http://7RRC.org'," +
		" 'Caledonia','Minnesota','USA',NULL,2016,'Coopertition')," +
		"('frc5915',5915,'Mercy Midnight Storm ','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2016,NULL)," +
		"('frc5916',5916,'ELEKTRON','Coming Soon'," +
		" 'Reynosa','Tamaulipas','Mexico',NULL,2016,NULL)," +
		"('frc5917',5917,'Supernova',NULL," +
		" 'Mooresville','NC','USA',NULL,2016,NULL)," +
		"('frc5918',5918,'Pumatron','http://www.firstinspires.org/'," +
		" 'Kansas City','Missouri','USA',NULL,2016,NULL)," +
		"('frc5919',5919,'JoCo RoBos','http://www.firstinspires.org/'," +
		" 'Smithfield','North Carolina','USA',NULL,2016,NULL)," +
		"('frc5920',5920,'VIKotics','http://www.garpal.net'," +
		" 'Palouse','Washington','USA',NULL,2016,'The only place success comes before work is in the dictionary')," +
		"('frc5921',5921,'Binary Power','http://www.firstinspires.org/'," +
		" 'Mississauga','Ontario','Canada',NULL,2016,NULL)," +
		"('frc5922',5922,'scots',NULL," +
		" 'Toronto','ON','Canada',NULL,2016,NULL)," +
		"('frc5923',5923,'WildC.A.T.S.','http://www.firstinspires.org/'," +
		" 'Angleton','Texas','USA',NULL,2016,NULL)," +
		"('frc5924',5924,'The Cat Machine','http://team5924.org'," +
		" 'San Francisco','California','USA',NULL,2016,'Robots with and for others')," +
		"('frc5925',5925,'Holden High Robotics','http://www.firstinspires.org/'," +
		" 'Holden','Missouri','USA',NULL,2016,NULL)," +
		"('frc5926',5926,'Da MOOse','http://www.team5926.org'," +
		" 'Port Huron','Michigan','USA',NULL,2016,'Needs More Cowbell')," +
		"('frc5927',5927,'Globetrotters','https://www.team5927.com'," +
		" 'Zeeland','Michigan','USA',NULL,2016,NULL)," +
		"('frc5928',5928,'MetalBoost','http://www.firstinspires.org/'," +
		" 'Petah Tiqua','HaMerkaz','Israel',NULL,2016,NULL)," +
		"('frc5929',5929,'The Marauders','http://www.thejollyrobots.com'," +
		" 'Lake Park','Minnesota','USA',NULL,2016,NULL)," +
		"('frc5930',5930,'Lunar Kitties','http://www.firstinspires.org/'," +
		" 'Newcastle','Oklahoma','USA',NULL,2016,NULL)," +
		"('frc5931',5931,'Bots of Prey','http://www.firstinspires.org/'," +
		" 'Caldwell','Idaho','USA',NULL,2016,NULL)," +
		"('frc5932',5932,'Tecmilenio Quetzales 5932','http://www.quetzales5932.org'," +
		" 'Mérida','Yucatán','Mexico',NULL,2016,NULL)," +
		"('frc5933',5933,'JudgeMent Call','https://jmrobotics5933.wordpress.com'," +
		" 'Salt lake City','Utah','USA',NULL,2016,NULL)," +
		"('frc5934',5934,'Crowbotics','http://www.crowbotics.org'," +
		" 'DeKalb','Illinois','USA',NULL,2016,'There''s Only One Barb')," +
		"('frc5935',5935,'Tech Tigers','http://ghstechtigers.weebly.com/'," +
		" 'Grinnell','Iowa','USA',NULL,2016,NULL)," +
		"('frc5936',5936,'TigerBots','http://www.firstinspires.org/'," +
		" 'Hanover','Maryland','USA',NULL,2016,'Imagine Design Build')," +
		"('frc5937',5937,'MI-Robotics','http://www.mercerislandschools.org/domain/1603'," +
		" 'Mercer Island','Washington','USA',NULL,2016,NULL)," +
		"('frc5938',5938,'Razor Steel Robotics','http://www.razorsteelrobotics.com'," +
		" 'Dover','Delaware','USA',NULL,2016,NULL)," +
		"('frc5939',5939,'Lakes Lancers','http://www.firstinspires.org/'," +
		" 'Lakewood','Washington','USA',NULL,2016,NULL)," +
		"('frc5940',5940,'B.R.E.A.D.','http://www.team5940.org/'," +
		" 'Redwood City','California','USA',NULL,2016,'Great Teams are not born, they''re BREAD.')," +
		"('frc5941',5941,'SPQL','http://www.firstinspires.org/'," +
		" 'Everett','Washington','USA',NULL,2016,NULL)," +
		"('frc5942',5942,'Warriors','http://www.firstinspires.org/'," +
		" 'Walla Walla','Washington','USA',NULL,2016,NULL)," +
		"('frc5943',5943,'The Bad News Gears','http://team5943.org'," +
		" 'Carmel','New York','USA',NULL,2016,'A Few Screws Loose')," +
		"('frc5944',5944,'Robotic Pioneers','http://www.firstinspires.org/'," +
		" 'Mooresville','Indiana','USA',NULL,2016,'Be EPIC!')," +
		"('frc5945',5945,'|CTRL| (Absolute Control)','http://frc.thehackground.org'," +
		" 'Fulton','Maryland','USA',NULL,2016,'Absolute Knowledge, Absolute Power, Absolute Control')," +
		"('frc5946',5946,'The SPartans','http://www.firstinspires.org/'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2016,NULL)," +
		"('frc5947',5947,'Robojags','http://www.firstinspires.org/'," +
		" 'Belmont','New York','USA',NULL,2016,NULL)," +
		"('frc5948',5948,'PrepaTec-Lebotics','Coming Soon'," +
		" 'Xochitepec','Morelos','Mexico',NULL,2016,NULL)," +
		"('frc5949',5949,'TechGarage','http://www.tech-garage.org'," +
		" 'Delray Beach','Florida','USA',NULL,2016,NULL)," +
		"('frc5950',5950,'Trojans','http://www.team5950.org'," +
		" 'Dry Fork','Virginia','USA',NULL,2016,NULL)," +
		"('frc5951',5951,'Makers Assemble','http://www.firstinspires.org/'," +
		" 'Tel Aviv','Tel-Aviv','Israel',NULL,2016,NULL)," +
		"('frc5952',5952,'RoBuck','https://www.facebook.com/5259frcteam/timeline'," +
		" 'Verdun','Québec','Canada',NULL,2016,NULL)," +
		"('frc5953',5953,'NOLABOTS','Coming Soon'," +
		" 'New Orleans','Louisiana','USA',NULL,2016,NULL)," +
		"('frc5954',5954,'Portable Chargers','https://sites.google.com/k12lcps.org/portable-chargers/home'," +
		" 'Victoria','Virginia','USA',NULL,2016,'Future \"War Horse\" in training')," +
		"('frc5955',5955,'Trailblazers','http://www.firstinspires.org/'," +
		" 'Bellport','New York','USA',NULL,2016,NULL)," +
		"('frc5956',5956,'Falcons','http://www.firstinspires.org/'," +
		" 'Milwaukie','Oregon','USA',NULL,2016,NULL)," +
		"('frc5957',5957,'CAT 5 CyberCanes','http://cat5cybercanes.ukit.me'," +
		" 'Newport News','Virginia','USA',NULL,2016,'If our robot will survive this storm we are ok!')," +
		"('frc5958',5958,'Blue Wave Robotics','http://www.firstinspires.org/'," +
		" 'Providence','Rhode Island','USA',NULL,2016,NULL)," +
		"('frc5959',5959,'PrepaTec - Titanium Rams','https://www.facebook.com/titaniumrams/'," +
		" 'Tampico','Tamaulipas','Mexico',NULL,2016,NULL)," +
		"('frc5960',5960,'Mighty ROBO-RANGERS','http://roborangers5960.wixsite.com/frc5960'," +
		" 'Mission','Texas','USA',NULL,2016,'Morphin'' the Minds of the Future')," +
		"('frc5961',5961,'the pythons','http://www.firstinspires.org/'," +
		" 'rehovot','HaMerkaz (Central)','Israel',NULL,2016,NULL)," +
		"('frc5962',5962,'perSEVERE','http://www.mvrobotics.net'," +
		" 'Lowell','Massachusetts','USA',NULL,2016,'perSEVERE')," +
		"('frc5963',5963,'CA Frost High School','http://www.firstinspires.org/'," +
		" 'Grand Rapids','Michigan','USA',NULL,2016,'We can do it!')," +
		"('frc5964',5964,'The Praetorians','http://www.team5964vhs.com'," +
		" 'Voorheesville','New York','USA',NULL,2016,NULL)," +
		"('frc5965',5965,'Power Struck Girls','https://sites.google.com/site/aolpowerstruckrobotics/home'," +
		" 'Marrero','Louisiana','USA',NULL,2016,NULL)," +
		"('frc5966',5966,'HB Oiler Robotics','http://HBHSRobot.com'," +
		" 'Huntington Beach','California','USA',NULL,2016,'Knowledge is Power')," +
		"('frc5967',5967,'Freedom FORCE','http://www.firstinspires.org/'," +
		" 'Orlando','Florida','USA',NULL,2016,NULL)," +
		"('frc5968',5968,'Cyborg Indians','http://www.firstinspires.org/'," +
		" 'Manhattan','Kansas','USA',NULL,2016,NULL)," +
		"('frc5969',5969,'English Skunkworks','http://www.team5969.com'," +
		" 'Jamaica Plain','Massachusetts','USA',NULL,2016,'FIRST In the Nation')," +
		"('frc5970',5970,'BeaverTronics','https://beavertronics5970.wixsite.com/bhs-robotics'," +
		" 'Beaverton','Oregon','USA',NULL,2016,NULL)," +
		"('frc5971',5971,'Raider Robotics Team','Coming Soon'," +
		" 'Metairie','Louisiana','USA',NULL,2016,NULL)," +
		"('frc5972',5972,'Juarezbots','Coming Soon'," +
		" 'juarez','Chihuahua','Mexico',NULL,2016,NULL)," +
		"('frc5973',5973,'Moonshots','http://gilmourfirst.org'," +
		" 'Gates Mills','Ohio','USA',NULL,2016,NULL)," +
		"('frc5974',5974,'Mechanical Dragons','http://www.firstinspires.org/'," +
		" 'Salt Lake City','Utah','USA',NULL,2016,NULL)," +
		"('frc5975',5975,'Beta Blues','http://www.firstinspires.org/'," +
		" 'Portland','Oregon','USA',NULL,2016,NULL)," +
		"('frc5976',5976,'CyberSaders','http://cybersaders5976.weebly.com/'," +
		" 'Waukesha','Wisconsin','USA',NULL,2016,'\"We''ll start from humble beginnings and achieve great things\"')," +
		"('frc5977',5977,'Rosemary Rebels','http://www.portlandoic.org'," +
		" 'Portland','Oregon','USA',NULL,2016,'Yet to be determined')," +
		"('frc5978',5978,'Roboplugs','http://www.firstinspires.org/'," +
		" 'Speedway','Indiana','USA',NULL,2016,NULL)," +
		"('frc5979',5979,'Apex','http://www.firstinspires.org/'," +
		" 'Columbia','Maryland','USA',NULL,2016,NULL)," +
		"('frc5980',5980,'East Grand Rapids Robotics','http://www.egrrobotics.org/'," +
		" 'Grand Rapids','Michigan','USA',NULL,2016,NULL)," +
		"('frc5981',5981,'Thunderbotz','http://www.firstinspires.org/'," +
		" 'San Antonio','Texas','USA',NULL,2016,'Go Tbotz Go')," +
		"('frc5982',5982,'Circuits of Destruction ','https://merrillfrc5982.weebly.com/'," +
		" 'Merrill','Michigan','USA',NULL,2016,NULL)," +
		"('frc5983',5983,'Blast Furnace Bots','https://sites.google.com/a/education.nsw.gov.au/robotics-team/'," +
		" 'Lithgow','New South Wales','Australia',NULL,2016,'Have fun.')," +
		"('frc5984',5984,'Salina Robocats','http://www.firstinspires.org/'," +
		" 'Salina','Oklahoma','USA',NULL,2016,NULL)," +
		"('frc5985',5985,'Project Bucephalus','http://www.projectb.net.au'," +
		" 'Wollongong','New South Wales','Australia',NULL,2016,'Sharing. Learning. Teaching.')," +
		"('frc5986',5986,'┌ Iron Fangs ┐','http://www.firstinspires.org/'," +
		" 'San Antonio','Texas','USA',NULL,2016,'We''ll rattle your gears.')," +
		"('frc5987',5987,'Galaxia in memory of David Zohar','http://www.galaxia5987.com/'," +
		" 'Haifa','Haifa','Israel',NULL,2016,'Reali FIRST')," +
		"('frc5988',5988,'Narooma Robo Rebels','http://www.firstinspires.org/'," +
		" 'Narooma','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc5989',5989,'Panthers','http://www.firstinspires.org/'," +
		" 'Ewen','Michigan','USA',NULL,2016,NULL)," +
		"('frc5990',5990,'TRIGON','http://www.trigonfrc.info'," +
		" 'Modiin','HaMerkaz','Israel',NULL,2016,NULL)," +
		"('frc5991',5991,'Chargers','http://www.firstinspires.org/'," +
		" 'Westbrook','Minnesota','USA',NULL,2016,NULL)," +
		"('frc5992',5992,'Pirates','http://www.team5992.org'," +
		" 'West Orange','New Jersey','USA',NULL,2016,NULL)," +
		"('frc5993',5993,'Istech Robotic Society','http://www.team5993.com'," +
		" 'Florya','Istanbul','Turkey',NULL,2016,NULL)," +
		"('frc5994',5994,'Captain Retros','http://team5994.lcc.ca/'," +
		" 'Montreal','Québec','Canada',NULL,2016,'Non Robot Solum')," +
		"('frc5995',5995,'Servo Serpents',NULL," +
		" 'Rancho Mirage','CA','USA',NULL,2016,NULL)," +
		"('frc5996',5996,'R.U.R.','http://www.firstinspires.org/'," +
		" 'Prague','Praha 8','Czech Republic',NULL,2016,NULL)," +
		"('frc5997',5997,'Knightling Boltz','Coming Soon'," +
		" 'St. Gabriel','Louisiana','USA',NULL,2016,NULL)," +
		"('frc5998',5998,'The Chillbots','http://thechillbots.org'," +
		" 'International Falls','Minnesota','USA',NULL,2016,NULL)," +
		"('frc5999',5999,'Byte Force','https://sites.google.com/view/milaca-robotics-5999/home'," +
		" 'Milaca','Minnesota','USA',NULL,2016,NULL);";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_12 = SQL_INSERT_TEAMS +
		"('frc6000',6000,'Firehawk Robotics','http://www.team6000.com/'," +
		" 'Los Angeles','California','USA',NULL,2016,NULL)," +
		"('frc6001',6001,'CIP','https://www.cip6001.co'," +
		" 'Medellin','Antioquia','Colombia',NULL,2016,NULL)," +
		"('frc6002',6002,'ZooBOTix','http://kcentralrobotics.blogspot.com/'," +
		" 'Kalamazoo','Michigan','USA',NULL,2016,NULL)," +
		"('frc6003',6003,'SUM','https://wordpress.campbell.edu/frc6003/'," +
		" 'Buies Creek','North Carolina','USA',NULL,2016,'Embrace the Chaos')," +
		"('frc6004',6004,'f(x) Robotics','http://team6004.com/'," +
		" 'Smithfield','North Carolina','USA',NULL,2016,NULL)," +
		"('frc6005',6005,'Cardinaltronics','http://cardinaltronics6005.weebly.com/'," +
		" 'Custer','Michigan','USA',NULL,2016,'Small school, big dreams')," +
		"('frc6006',6006,'Albury High School','http://www.firstinspires.org/'," +
		" 'Albury','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc6007',6007,'RoboPod','http://www.firstinspires.org/'," +
		" 'laurieton','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc6008',6008,'Lions','http://www.firstinspires.org/'," +
		" 'Surrey','British Columbia','Canada',NULL,2016,NULL)," +
		"('frc6009',6009,'CYBERHEART','http://www.firstinspires.org/'," +
		" 'Etobicoke','Ontario','Canada',NULL,2016,'cursum consumavi fidem servavi')," +
		"('frc6010',6010,'Tecmilenio - Teravolts - 6010','Coming Soon'," +
		" 'MAZATLAN','Sinaloa','Mexico',NULL,2016,NULL)," +
		"('frc6011',6011,'Cyber Trojans','http://www.firstinspires.org/'," +
		" 'Sturgis','Michigan','USA',NULL,2016,NULL)," +
		"('frc6012',6012,'STEAMROLLERS','http://www.frc6012.org'," +
		" 'Bedford','Indiana','USA',NULL,2016,NULL)," +
		"('frc6013',6013,'Robowolves','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2016,NULL)," +
		"('frc6014',6014,'ARC','http://arc6014.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2016,'kids of today, engineers of tomorrow')," +
		"('frc6015',6015,'Juicy Bots','http://n/a'," +
		" 'Hackensack','New Jersey','USA',NULL,2016,'N/A')," +
		"('frc6016',6016,'Tiger Robotics','http://www.firstinspires.org/'," +
		" 'Hackettstown','New Jersey','USA',NULL,2016,NULL)," +
		"('frc6017',6017,'PrepaTec - Cyberius','http://cyberius.team'," +
		" 'Santa Catarina','Nuevo León','Mexico',NULL,2016,'Thrive beyond success.')," +
		"('frc6018',6018,'TICK','http://www.firstinspires.org/'," +
		" 'Lanzhou','Gansu','China',NULL,2016,NULL)," +
		"('frc6019',6019,'Metabots','http://www.firstinspires.org/'," +
		" 'Metamora','Michigan','USA',NULL,2016,NULL)," +
		"('frc6020',6020,'John Glenn Robotics','http://www.firstinspires.org/'," +
		" 'Bay City','Michigan','USA',NULL,2016,NULL)," +
		"('frc6021',6021,'Team T.I.G.E.R.S','http://www.firstinspires.org/'," +
		" 'Sussex','Virginia','USA',NULL,2016,NULL)," +
		"('frc6022',6022,'Wrench Warmers','http://www.wrenchwarmers6022.com/'," +
		" 'Blooming Prairie','Minnesota','USA',NULL,2016,NULL)," +
		"('frc6023',6023,'DISCBOTS','https://www.facebook.com/discbots2015/?fref=ts'," +
		" 'Atlanta','Georgia','USA',NULL,2016,'Inspire thru unity')," +
		"('frc6024',6024,'R Factor','http://www.rfactor6024.com/'," +
		" 'Mumbai','Maharashtra','India',NULL,2016,NULL)," +
		"('frc6025',6025,'Adroit Androids','http://team6025.com/'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2016,NULL)," +
		"('frc6026',6026,'Tiger Strike','http://team6026.com'," +
		" 'Broken Arrow','Oklahoma','USA',NULL,2016,NULL)," +
		"('frc6027',6027,'Brain Waves','https://www.newarkcatholic.org/robotics/'," +
		" 'Newark','Ohio','USA',NULL,2016,NULL)," +
		"('frc6028',6028,'Rs Artisan','http://www.firstinspires.org/'," +
		" 'Wuhan','Hubei','China',NULL,2016,NULL)," +
		"('frc6029',6029,'Railbots','http://www.firstinspires.org/'," +
		" 'Lincoln Park','Michigan','USA',NULL,2016,NULL)," +
		"('frc6030',6030,'Tarbiya 360',NULL," +
		" 'Sacramento','CA','USA',NULL,2016,NULL)," +
		"('frc6031',6031,'Falcons',NULL," +
		" 'Pentwater','MI','USA',NULL,2016,NULL)," +
		"('frc6032',6032,'Pirate Robotics','https://www.wcrobotics.org'," +
		" 'Dayton','Ohio','USA',NULL,2016,NULL)," +
		"('frc6033',6033,'Gadget Agents','http://www.firstinspires.org/'," +
		" 'Mount Pleasant','Michigan','USA',NULL,2016,NULL)," +
		"('frc6034',6034,'Eagle Storm','http://www.firstinspires.org/'," +
		" 'Florissant','Missouri','USA',NULL,2016,'Without Struggle There is No Success')," +
		"('frc6035',6035,'House of Ulladulla Robotics ','http://FB house of ulladulla Game of drones'," +
		" 'Ulladulla','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc6036',6036,'Peninsula Robotics','https://team6036.github.io/'," +
		" 'Palo Alto','California','USA',NULL,2016,NULL)," +
		"('frc6037',6037,'Vikings 51',NULL," +
		" 'Huntersville','NC','USA',NULL,2016,NULL)," +
		"('frc6038',6038,'ITOBOT','http://team6038.com'," +
		" 'Bayrampasa','Istanbul','Turkey',NULL,2016,'Listen To We, Be Happy.')," +
		"('frc6039',6039,'Cypress Circuits','http://cypresscircuits.com'," +
		" 'Pebble Beach','California','USA',NULL,2016,NULL)," +
		"('frc6040',6040,'Getobotics','http://www.firstinspires.org/'," +
		" 'Montreal','Québec','Canada',NULL,2016,NULL)," +
		"('frc6041',6041,'Cougar Robotics','http://www.firstinspires.org/'," +
		" 'Lethbridge','Alberta','Canada',NULL,2016,NULL)," +
		"('frc6042',6042,'Robocamp','http://www.firstinspires.org/'," +
		" 'Calera','Cundinamarca','Colombia',NULL,2016,NULL)," +
		"('frc6043',6043,'Tesla Tigers','http://www.firstinspires.org/'," +
		" 'Allegan','Michigan','USA',NULL,2016,NULL)," +
		"('frc6044',6044,'Circuit Breakers','http://www.firstinspires.org/'," +
		" 'Moose Lake','Minnesota','USA',NULL,2016,NULL)," +
		"('frc6045',6045,'Sabre Robotics','https://app.schoology.com/course/427785809/materials'," +
		" 'Sartell','Minnesota','USA',NULL,2016,'\"I didn''t come this far to miss\"')," +
		"('frc6046',6046,'Tecking','http://www.firstinspires.org/'," +
		" 'King City','Ontario','Canada',NULL,2016,'Tech is King')," +
		"('frc6047',6047,'Proctor Frostbyte','http://www.firstinspires.org/'," +
		" 'Proctor','Minnesota','USA',NULL,2016,'This isn''t combat Robotics?')," +
		"('frc6048',6048,'Ant-valanche','Coming Soon'," +
		" 'Querétaro','Querétaro','Mexico',NULL,2016,NULL)," +
		"('frc6049',6049,'Titan X','http://www.firstinspires.org/'," +
		" 'Dimona','HaDarom','Israel',NULL,2016,NULL)," +
		"('frc6050',6050,'Wee Waa Bush Bots','http://www.firstinspires.org/'," +
		" 'Wee Waa','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc6051',6051,'Rocktaπ','http://www.firstinspires.org/'," +
		" 'Converse','Texas','USA',NULL,2016,NULL)," +
		"('frc6052',6052,'Clarke Road Trojans','http://www.firstinspires.org/'," +
		" 'London','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6053',6053,'Robusters','http://www.firstinspires.org/'," +
		" 'Colon','Michigan','USA',NULL,2016,NULL)," +
		"('frc6054',6054,'The Dukes','http://www.firstinspires.org/'," +
		" 'Alliance','Ohio','USA',NULL,2016,NULL)," +
		"('frc6055',6055,'BOSS','https://www.facebook.com/BatesvilleRobotics/'," +
		" 'Batesville','Arkansas','USA',NULL,2016,NULL)," +
		"('frc6056',6056,'Brotherhood of Steel','http://www.firstinspires.org/'," +
		" 'Rothsay','Minnesota','USA',NULL,2016,NULL)," +
		"('frc6057',6057,'Thunderbots','http://www.firstinspires.org/'," +
		" 'Dearborn','Michigan','USA',NULL,2016,'Built Strong')," +
		"('frc6058',6058,'ROBOTURK','http://www.roboturk.org/'," +
		" 'Besiktas','Istanbul','Turkey',NULL,2016,NULL)," +
		"('frc6059',6059,'System Overload Robotics','https://frc6059.com'," +
		" 'Livermore','California','USA',NULL,2016,NULL)," +
		"('frc6060',6060,'Circuit Serpents','http://bit.ly/RosamondHighRobotics'," +
		" 'Rosamond','California','USA',NULL,2016,NULL)," +
		"('frc6061',6061,'Carbon Crusaders','http://www.firstinspires.org/'," +
		" 'Muswellbrook','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc6062',6062,'WHAM (Willyama High Advanced Mechatronics)','http://www.firstinspires.org/'," +
		" 'Broken Hill','New South Wales','Australia',NULL,2016,'Connect Inspire Excel Together')," +
		"('frc6063',6063,'QLA Pineapples','http://www.gracerobotics.org/'," +
		" 'Rothwell','Queensland','Australia',NULL,2016,NULL)," +
		"('frc6064',6064,'IstanBULLS','http://www.team6064.org'," +
		" 'istanbul','Istanbul','Turkey',NULL,2016,'Never give up')," +
		"('frc6065',6065,'Titanium Dragons','http://www.firstinspires.org/'," +
		" 'Grand Rapids','Michigan','USA',NULL,2016,NULL)," +
		"('frc6066',6066,'Rambler Robotics','http://www.firstinspires.org/'," +
		" 'McBain','Michigan','USA',NULL,2016,NULL)," +
		"('frc6067',6067,'Underdogs','http://www.firstinspires.org/'," +
		" 'Saint Charles','Michigan','USA',NULL,2016,NULL)," +
		"('frc6068',6068,'Delta Overload','http://www.firstinspires.org/'," +
		" 'Indianola','Mississippi','USA',NULL,2016,NULL)," +
		"('frc6069',6069,'BHS Robotics','http://www.firstinspires.org/'," +
		" 'Bloomington','California','USA',NULL,2016,'Elegant Simplicity')," +
		"('frc6070',6070,'Gryphon Machine','http://frc6070.ca'," +
		" 'Mississauga','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6071',6071,'The Aluminators','https://www.twitter.com/6071aluminators'," +
		" 'Bay City','Michigan','USA',NULL,2016,'\"You Never Know Until You Try\"')," +
		"('frc6072',6072,'Triton Tech','http://www.tritontech6072.com/'," +
		" 'Newport Beach','California','USA',NULL,2016,NULL)," +
		"('frc6073',6073,'Cav-Bots','http://www.carrolltonpublicschools.org/'," +
		" 'Saginaw','Michigan','USA',NULL,2016,'Get it Done')," +
		"('frc6074',6074,'RoboBucks','https://sites.google.com/rapsk12.net/roboreactors-team6074/home'," +
		" 'Roscommon','Michigan','USA',NULL,2016,NULL)," +
		"('frc6075',6075,'Leland Zebrotics','http://lelandzebrotics.weebly.com'," +
		" 'Leland','Michigan','USA',NULL,2016,'home of the part-time nerds')," +
		"('frc6076',6076,'Mustangs','http://www.northportschools.org'," +
		" 'Northport','Washington','USA',NULL,2016,'unknown')," +
		"('frc6077',6077,'Wiking Kujon','http://www.facebook.com/team6077'," +
		" 'Posen','Michigan','USA',NULL,2016,NULL)," +
		"('frc6078',6078,'RoboRams','http://holtrobotics.weebly.com/'," +
		" 'Holt','Michigan','USA',NULL,2016,'We Made It!')," +
		"('frc6079',6079,'JET SET','http://www.firstinspires.org/'," +
		" 'Powers','Michigan','USA',NULL,2016,NULL)," +
		"('frc6080',6080,'A.D. Johnston First',NULL," +
		" 'Bessemer','MI','USA',NULL,2016,NULL)," +
		"('frc6081',6081,'Digital Dislocators','http://www.firstinspires.org/'," +
		" 'Manchester','Michigan','USA',NULL,2016,NULL)," +
		"('frc6082',6082,'Calgary Tech Coalition (CTC)','http://www.firstinspires.org/'," +
		" 'Calgary','Alberta','Canada',NULL,2016,'Making Robots Great Again')," +
		"('frc6083',6083,'Overlooking','http://team6083.page.link/offical'," +
		" 'Chiayi','Chiayi','Chinese Taipei',NULL,2016,NULL)," +
		"('frc6084',6084,'Swoop Robotics','http://www.firstinspires.org/'," +
		" 'Cincinnati','Ohio','USA',NULL,2016,NULL)," +
		"('frc6085',6085,'Green Devil Bots','http://browncitydevilbots6085.weebly.com/'," +
		" 'Brown City','Michigan','USA',NULL,2016,'The Devil is in the Details!')," +
		"('frc6086',6086,'Ignition','http://www.mtmorrisschools.org/MECC/Portal/robotics-team-6086'," +
		" 'Mt. Morris','Michigan','USA',NULL,2016,'Changing Minds and Changing Lives')," +
		"('frc6087',6087,'Cybertronic Lancers','http://www.firstinspires.org/'," +
		" 'Ellsworth','Michigan','USA',NULL,2016,NULL)," +
		"('frc6088',6088,'Stephenson Eagles','http://www.firstinspires.org/'," +
		" 'Stephenson','Michigan','USA',NULL,2016,NULL)," +
		"('frc6089',6089,'Cyber Cougars','http://www.firstinspires.org/'," +
		" 'Dearborn Heights','Michigan','USA',NULL,2016,'All for One, One for All.')," +
		"('frc6090',6090,'Wayland Wildcats','http://www.firstinspires.org/'," +
		" 'Wayland','Michigan','USA',NULL,2016,NULL)," +
		"('frc6091',6091,'Eagle 1','http://www.firstinspires.org/'," +
		" 'Deckerville','Michigan','USA',NULL,2016,NULL)," +
		"('frc6092',6092,'Q-Bots','http://www.firstinspires.org/'," +
		" 'Quincy','Michigan','USA',NULL,2016,NULL)," +
		"('frc6093',6093,'Unplugged','https://unplugged6093.weebly.com/'," +
		" 'Sandusky','Michigan','USA',NULL,2016,NULL)," +
		"('frc6094',6094,'Manic Mechanics','https://www.facebook.com/manic.mechanics.33'," +
		" 'Montague','Michigan','USA',NULL,2016,NULL)," +
		"('frc6095',6095,'PARDUX','http://www.firstinspires.org/'," +
		" 'ISTANBUL','Istanbul','Turkey',NULL,2016,'Spread creativity')," +
		"('frc6096',6096,'UPSM Cobras','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2016,'Anything a mind can imagine it can create')," +
		"('frc6097',6097,'Botcats ','http://www.firstinspires.org/'," +
		" 'Brethren','Michigan','USA',NULL,2016,NULL)," +
		"('frc6098',6098,'bIrobot','http://birobot.org'," +
		" 'Beaver Island','Michigan','USA',NULL,2016,'Press on notwithstanding')," +
		"('frc6099',6099,'Knight Riders','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2016,NULL)," +
		"('frc6100',6100,'MSMohawks','http://www.firstinspires.org/'," +
		" 'Morley','Michigan','USA',NULL,2016,NULL)," +
		"('frc6101',6101,'Strange Quarks','http://www.firstinspires.org/'," +
		" 'Ann Arbor','Michigan','USA',NULL,2016,NULL)," +
		"('frc6102',6102,'Mustangs','http://www.firstinspires.org/'," +
		" 'Blanchard','Michigan','USA',NULL,2016,NULL)," +
		"('frc6103',6103,'Space Pirates','Coming Soon'," +
		" 'Plaquemine','Louisiana','USA',NULL,2016,NULL)," +
		"('frc6104',6104,'Desert Eagles','http://www.firstinspires.org/'," +
		" 'Ofakim','HaDarom','Israel',NULL,2016,NULL)," +
		"('frc6105',6105,'Firebirds with Skills','http://www.firstinspires.org/'," +
		" 'Nashville','Tennessee','USA',NULL,2016,NULL)," +
		"('frc6106',6106,' PrepaTec - TecGear ','https://www.facebook.com/TecGear6106/'," +
		" 'Irapuato','Guanajuato','Mexico',NULL,2016,NULL)," +
		"('frc6107',6107,'CyberJagzz','http://www.firstinspires.org/'," +
		" 'Huntsville','Alabama','USA',NULL,2016,NULL)," +
		"('frc6108',6108,'DRAGONBOTS','http://www.firstinspires.org/'," +
		" 'Clinton Township','Michigan','USA',NULL,2016,NULL)," +
		"('frc6109',6109,'Longhorn Robotics','http://www.firstinspires.org/'," +
		" 'Payson','Arizona','USA',NULL,2016,'Be yourself, unless you can be an engineer, and then be an engineer! ')," +
		"('frc6110',6110,'Doc Botics','http://www.docrobotics.com'," +
		" 'Ajax','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6111',6111,'RoboKnights','http://www.facebook.com/mlcparobotics'," +
		" 'Houston','Texas','USA',NULL,2016,NULL)," +
		"('frc6112',6112,'Cyberstangs','https://www.facebook.com/MunisingHighSchool/'," +
		" 'Munising','Michigan','USA',NULL,2016,NULL)," +
		"('frc6113',6113,'M.A.R.C. 1','http://www.firstinspires.org/'," +
		" 'Menominee','Michigan','USA',NULL,2016,NULL)," +
		"('frc6114',6114,'Hawks Do the Thing','http://www.firstinspires.org/'," +
		" 'Hamilton','Michigan','USA',NULL,2016,NULL)," +
		"('frc6115',6115,'Ellington Saints',NULL," +
		" 'Grand Rapids','MI','USA',NULL,2016,NULL)," +
		"('frc6116',6116,'Cougar Robotics','http://www.firstinspires.org/'," +
		" 'Garden City','Michigan','USA',NULL,2016,NULL)," +
		"('frc6117',6117,'Wingspan','https://wingspan6117.com/'," +
		" 'Pontiac','Michigan','USA',NULL,2016,NULL)," +
		"('frc6118',6118,'Dunedoo Central School','http://www.firstinspires.org/'," +
		" 'Dunedoo','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc6119',6119,'Prototype Pumas','http://www.firstinspires.org/'," +
		" 'Coldwater','Michigan','USA',NULL,2016,NULL)," +
		"('frc6120',6120,'Cyberstangs','http://www.firstinspires.org/'," +
		" 'New Haven','Michigan','USA',NULL,2016,'Failure is not an option.')," +
		"('frc6121',6121,'Robo Vikes','http://www.firstinspires.org/'," +
		" 'Grayling','Michigan','USA',NULL,2016,NULL)," +
		"('frc6122',6122,'Potential Energy','http://www.firstinspires.org/'," +
		" 'Bear Lake','Michigan','USA',NULL,2016,NULL)," +
		"('frc6123',6123,'Mad Scientist Club','http://www.firstinspires.org/'," +
		" 'Lake Elsinore','California','USA',NULL,2016,NULL)," +
		"('frc6124',6124,'Overdrive','http://www.firstinspires.org/'," +
		" 'Fontana','California','USA',NULL,2016,'Never Give Up!')," +
		"('frc6125',6125,'Jagbots','http://www.firstinspires.org/'," +
		" 'Toronto','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6126',6126,'Robo-Blazers','http://www.firstinspires.org/'," +
		" 'San Antonio','Texas','USA',NULL,2016,NULL)," +
		"('frc6127',6127,'BHS Pumatrons','http://www.firstinspires.org/'," +
		" 'Bisbee','Arizona','USA',NULL,2016,NULL)," +
		"('frc6128',6128,'M.O.T.O.R (More Than Our Robot)','http://www.firstinspires.org/'," +
		" 'Hart','Michigan','USA',NULL,2016,'Pirate')," +
		"('frc6129',6129,'Warp Speed, Scotty !','http://www.firstinspires.org/'," +
		" 'Spokane','Washington','USA',NULL,2016,NULL)," +
		"('frc6130',6130,'Vipers','https://stpatsrobotics6130.wixsite.com/website'," +
		" 'Toronto','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6131',6131,'NLR Robotics','http://www.firstinspires.org/'," +
		" 'North Little Rock','Arkansas','USA',NULL,2016,'Talk Nerdy To Me')," +
		"('frc6132',6132,'Iron Rangers','http://rangerrobotics.weebly.com/'," +
		" 'Crosby','Minnesota','USA',NULL,2016,NULL)," +
		"('frc6133',6133,'b a s h','https://www.birdvilleschools.net/HaltomRobotics'," +
		" 'North Richland Hills','Texas','USA',NULL,2016,NULL)," +
		"('frc6134',6134,'Robo Ram','Coming Soon'," +
		" 'Zapopan','Jalisco','Mexico',NULL,2016,NULL)," +
		"('frc6135',6135,'Arctos','http://arctos6135.com'," +
		" 'Toronto','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6136',6136,'The Robo Ravens 6136','http://www.firstinspires.org/'," +
		" 'Detroit','Michigan','USA',NULL,2016,NULL)," +
		"('frc6137',6137,'Falcon Robotics','http://www.firstinspires.org/'," +
		" 'Constantine','Michigan','USA',NULL,2016,NULL)," +
		"('frc6138',6138,'Bulldogs','http://www.firstinspires.org/'," +
		" 'Centreville','Michigan','USA',NULL,2016,NULL)," +
		"('frc6139',6139,'ThunderBotz','https://sites.google.com/view/hgccaroboticsteam6139'," +
		" 'Dublin','Georgia','USA',NULL,2016,'Making Our Future Work')," +
		"('frc6140',6140,'SMT Titans','http://www.twitter.com/smtrobotics'," +
		" 'Scarborough','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6141',6141,'Senator O''Connor College School','http://www.firstinspires.org/'," +
		" 'Toronto','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6142',6142,'Cross Keys High School','http://www.firstinspires.org/'," +
		" 'Atlanta','Georgia','USA',NULL,2016,NULL)," +
		"('frc6143',6143,'APEX Automation','http://www.firstinspires.org/'," +
		" 'Sarver','Pennsylvania','USA',NULL,2016,NULL)," +
		"('frc6144',6144,'Angelbots Gold','Coming Soon'," +
		" 'El Paso','Texas','USA',NULL,2016,NULL)," +
		"('frc6145',6145,'War Eagles','http://www.firstinspires.org/'," +
		" 'Crawfordville','Florida','USA',NULL,2016,NULL)," +
		"('frc6146',6146,'Blackjacks','http://dawsonboydrobotics.weebly.com/'," +
		" 'Dawson','Minnesota','USA',NULL,2016,NULL)," +
		"('frc6147',6147,'Tonkabots','http://westonka-activities.com/robotics'," +
		" 'Mound','Minnesota','USA',NULL,2016,'Think outside the gears!')," +
		"('frc6148',6148,'CASS Electro Knights',NULL," +
		" 'Woodstock','ON','Canada',NULL,2016,NULL)," +
		"('frc6149',6149,'hand by hand','http://www.firstinspires.org/'," +
		" 'arara negev','HaDarom','Israel',NULL,2016,'togther is better')," +
		"('frc6150',6150,'BlueBotics','http://www.firstinspires.org/'," +
		" 'Adrian','Michigan','USA',NULL,2016,NULL)," +
		"('frc6151',6151,'Red Devil Robotics',NULL," +
		" 'Ironwood','MI','USA',NULL,2016,NULL)," +
		"('frc6152',6152,'ROBO-FALCONS','http://www.firstinspires.org/'," +
		" 'DEARBORN HEIGHTS','Michigan','USA',NULL,2016,NULL)," +
		"('frc6153',6153,'Blue Crew','http://www.bluecrew6153.org/'," +
		" 'Farmington','Maine','USA',NULL,2016,'Essayons')," +
		"('frc6154',6154,'Universal Robots','http://www.firstinspires.org/'," +
		" 'Frederikssund','South Denmark','Denmark',NULL,2016,NULL)," +
		"('frc6155',6155,'ElektraBots','http://www.firstinspires.org/'," +
		" 'San Antonio','Texas','USA',NULL,2016,NULL)," +
		"('frc6156',6156,'PrepaTec - Infinity Robotics Team','Coming Soon'," +
		" 'Ciudad Obregon','Sonora','Mexico',NULL,2016,NULL)," +
		"('frc6157',6157,'Marshall Mechs','http://www.firstinspires.org/'," +
		" 'Benton','Kentucky','USA',NULL,2016,'We bring the fight')," +
		"('frc6158',6158,'PiRbot2','http://www.firstinspires.org/'," +
		" 'Birmingham','Alabama','USA',NULL,2016,NULL)," +
		"('frc6159',6159,'ALIANZA MEDELLIN','roboticaafdm.hol.es'," +
		" 'Medellin','Antioquia','Colombia',NULL,2016,'Robotics for a better country')," +
		"('frc6160',6160,'Bombatrons','http://bombatrons.weebly.com'," +
		" 'Barnum','Minnesota','USA',NULL,2016,NULL)," +
		"('frc6161',6161,'Equilibrium','https://www.team6161.com/'," +
		" 'Gray','Maine','USA',NULL,2016,NULL)," +
		"('frc6162',6162,'Cap Alpaca','http://www.liastem.ca'," +
		" 'London','Ontario','Canada',NULL,2016,'Lets Go Grab Some Food')," +
		"('frc6163',6163,'Mustangs','http://www.firstinspires.org/'," +
		" 'Birmingham','Alabama','USA',NULL,2016,'We are Destined for Greatness')," +
		"('frc6164',6164,'Moonshot Slaybots','http://6164slaybots.weebly.com/'," +
		" 'Dike','Iowa','USA',NULL,2016,NULL)," +
		"('frc6165',6165,'Aurora','http://www.firstinspires.org/'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2016,'\"make robot, not war')," +
		"('frc6166',6166,'Thorobotics','http://www.holmen.k12.wi.us/Page/8840'," +
		" 'Holmen','Wisconsin','USA',NULL,2016,NULL)," +
		"('frc6167',6167,'CB Bearbots','http://cbhbearbots.weebly.com'," +
		" 'Hemingway','South Carolina','USA',NULL,2016,'Be tough, be wild, be a Bearbot!')," +
		"('frc6168',6168,'alzahrawi','http://www.firstinspires.org/'," +
		" 'iksal','HaZafon','Israel',NULL,2016,NULL)," +
		"('frc6169',6169,'Chaos Squad','http://www.unitedroboticslacombe.weebly.com'," +
		" 'Lacombe','Alberta','Canada',NULL,2016,NULL)," +
		"('frc6170',6170,'Tecmilenio Vitronik','https://www.facebook.com/Vitronik6170'," +
		" 'San Luis Potosí','San Luis Potosí','Mexico',NULL,2016,NULL)," +
		"('frc6171',6171,'⚛ Chain Reaction ⚛','http://www.chainreaction6171.org/'," +
		" 'Plano','Texas','USA',NULL,2016,'We''re not just building a robot.')," +
		"('frc6172',6172,'The Mad Dogs','http://www.firstinspires.org/'," +
		" 'Fairfield','Maine','USA',NULL,2016,NULL)," +
		"('frc6173',6173,'Elgin Metal Mayhem','http://www.firstinspires.org/'," +
		" 'Elgin','Texas','USA',NULL,2016,NULL)," +
		"('frc6174',6174,'Kaprekar''s Constants','http://www.wintershsrobotics.com'," +
		" 'Winters','California','USA',NULL,2016,NULL)," +
		"('frc6175',6175,'Mystery Machine','http://www.firstinspires.org/'," +
		" 'Eden Valley-Watkins','Minnesota','USA',NULL,2016,NULL)," +
		"('frc6176',6176,'MTS','http://www.firstinspires.org/'," +
		" 'MInneapolis','Minnesota','USA',NULL,2016,'Sky is my limit')," +
		"('frc6177',6177,'Atomic-Robotic-Dogs','http://www.firstinspires.org/'," +
		" 'Atlanta','Georgia','USA',NULL,2016,NULL)," +
		"('frc6178',6178,'Techno Titans','http://vanier.htcsd.ca/robotics/'," +
		" 'Moose Jaw','Saskatchewan','Canada',NULL,2016,NULL)," +
		"('frc6179',6179,'PhotonBox','http://www.firstinspires.org/'," +
		" 'Shenzhen','Guangdong','China',NULL,2016,NULL)," +
		"('frc6180',6180,'Mechanical Lions ','http://www.firstinspires.org/'," +
		" 'Mission','Texas','USA',NULL,2016,NULL)," +
		"('frc6181',6181,'Cryptic Cyborgs','http://www.firstinspires.org/'," +
		" 'Archbold','Ohio','USA',NULL,2016,NULL)," +
		"('frc6182',6182,'Royals',NULL," +
		" 'Rogers','MN','USA',NULL,2016,NULL)," +
		"('frc6183',6183,'RIGS - Robotics Inspired Greenland Students','http://www.firstinspires.org/'," +
		" 'Greenland','Arkansas','USA',NULL,2016,NULL)," +
		"('frc6184',6184,'Leon Robotics  ','http://leonrobotics.com'," +
		" 'Tallahassee','Florida','USA',NULL,2016,NULL)," +
		"('frc6185',6185,'TV Patriots','http://www.firstinspires.org/'," +
		" 'New Madison','Ohio','USA',NULL,2016,NULL)," +
		"('frc6186',6186,'Hope BotCats','http://www.firstinspires.org/'," +
		" 'Hope','Arkansas','USA',NULL,2016,NULL)," +
		"('frc6187',6187,'Narrabri High School- Kaputar Klockworks','http://www.firstinspires.org/'," +
		" 'Narrabri','New South Wales','Australia',NULL,2016,NULL)," +
		"('frc6188',6188,'Sting Robotics','http://www.firstinspires.org/'," +
		" 'Fort Saskatchewan','Alberta','Canada',NULL,2016,NULL)," +
		"('frc6189',6189,'Cyber Raiders','http://www.firstinspires.org/'," +
		" 'Amelia Court House','Virginia','USA',NULL,2016,NULL)," +
		"('frc6190',6190,'Will-er Run','http://www.firstinspires.org/'," +
		" 'Canton','Michigan','USA',NULL,2016,'Share freely and borrow proudly')," +
		"('frc6191',6191,'RoboKryptonite','http://team6191.wordpress.com'," +
		" 'Taipei','Taipei','Chinese Taipei',NULL,2016,NULL)," +
		"('frc6192',6192,'IEA Robotim','http://www.firstinspires.org/'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2016,'Generate the solutions , not the problem !')," +
		"('frc6193',6193,'The Legend of Robotics','http://www.frc6193.com'," +
		" 'Bay City','Michigan','USA',NULL,2016,NULL)," +
		"('frc6194',6194,'The Aftershocks','http://cucps.k12.us.va'," +
		" 'Cumberland','Virginia','USA',NULL,2016,'Design Create Invent')," +
		"('frc6195',6195,'GOLDBOTS','http://www.firstinspires.org/'," +
		" 'Brooklyn','New York','USA',NULL,2016,'Get the Gold!')," +
		"('frc6196',6196,'Spontaneous Disassembly','http://www.firstinspires.org/'," +
		" 'College Station','Texas','USA',NULL,2016,NULL)," +
		"('frc6197',6197,'Semia United','http://www.firstinspires.org/'," +
		" 'Beijing','Beijing','China',NULL,2016,NULL)," +
		"('frc6198',6198,'Bayou Bots','http://www.firstinspires.org/'," +
		" 'Pensacola','Florida','USA',NULL,2016,'Redundancy Rocks')," +
		"('frc6199',6199,'E-Hawks','Coming Soon'," +
		" 'Torreón','Coahuila','Mexico',NULL,2016,NULL)," +
		"('frc6200',6200,'PrepaTec - XRAMS','Coming Soon'," +
		" 'Juárez','Chihuahua','Mexico',NULL,2016,'\"We are not the future, we are the PRESENT\"')," +
		"('frc6201',6201,'The Highlanders','http://team6201.com'," +
		" 'Somerville','Massachusetts','USA',NULL,2016,'Somerville Leads the Way')," +
		"('frc6202',6202,'The Centurions','http://www.firstinspires.org/'," +
		" 'Windsor','Ontario','Canada',NULL,2016,NULL)," +
		"('frc6203',6203,'Titanium Knights Robotics','http://team6203.com/'," +
		" 'Hackensack','New Jersey','USA',NULL,2016,'TBD')," +
		"('frc6204',6204,'Team Pedare','http://www.pedarecc.sa.edu.au/robotics/frc2018/'," +
		" 'Adelaide','South Australia','Australia',NULL,2016,NULL)," +
		"('frc6205',6205,'SD Tech','http://www.firstinspires.org/'," +
		" 'Shanghai','Beijing','China',NULL,2016,NULL)," +
		"('frc6206',6206,'Shandong T','http://www.firstinspires.org/'," +
		" 'Taiyuan','Shaanxi','China',NULL,2016,NULL)," +
		"('frc6207',6207,'Beichen','http://www.firstinspires.org/'," +
		" 'Changchun','Jilin','China',NULL,2016,NULL)," +
		"('frc6208',6208,'Mechstangs','http://www.firstinspires.org/'," +
		" 'Portage','Michigan','USA',NULL,2016,NULL)," +
		"('frc6209',6209,'Robo Warriors','Coming Soon'," +
		" 'Lubbock','Texas','USA',NULL,2016,NULL)," +
		"('frc6210',6210,'The Dragons Lair','http://www.firstinspires.org/'," +
		" 'Salt Lake City','Utah','USA',NULL,2016,'Curiosity leads to great ideas')," +
		"('frc6211',6211,'Hayden Robotics',NULL," +
		" 'Burlington','ON','Canada',NULL,2016,NULL)," +
		"('frc6212',6212,'Kaniva College','http://www.firstinspires.org/'," +
		" 'Kaniva','Victoria','Australia',NULL,2016,NULL)," +
		"('frc6213',6213,'Team Quantum','http://www.teamquantum#6213.org'," +
		" 'Bowie','Maryland','USA',NULL,2016,NULL)," +
		"('frc6214',6214,'PHEnix','https://sites.google.com/pender.k12.nc.us/phenix/home'," +
		" 'Burgaw','North Carolina','USA',NULL,2016,'if( Awesome >= 100){System.out.print(\"Started from the bottom now we are here\"):')," +
		"('frc6215',6215,'Armored Eagles','http://www.compassyouthcenters.org'," +
		" 'Rocky Mount','North Carolina','USA',NULL,2016,'\"Staying Alive with Armored Eagle Pride\"')," +
		"('frc6216',6216,'movers and shakers ','http://www.firstinspires.org/'," +
		" 'chester','Pennsylvania','USA',NULL,2016,NULL)," +
		"('frc6217',6217,'Bomb-Botz','http://www.firstinspires.org/'," +
		" 'Cannon Falls','Minnesota','USA',NULL,2016,NULL)," +
		"('frc6218',6218,'Tecmilenio - TAURUS - 6218 ','Coming Soon'," +
		" 'Hermosillo','Sonora','Mexico',NULL,2016,NULL)," +
		"('frc6219',6219,'Cuttin'' Edge Robotics','http://cuttinedgerobotics.weebly.com'," +
		" 'Blountville','Tennessee','USA',NULL,2016,NULL)," +
		"('frc6220',6220,'MEME Team','http://www.losalrobotics.com'," +
		" 'Los Alamitos','California','USA',NULL,2016,'Envision, Design, Inspire')," +
		"('frc6221',6221,'Vandals','http://www.firstinspires.org/'," +
		" 'Miami','Arizona','USA',NULL,2016,NULL)," +
		"('frc6222',6222,'Flying Tigers','Coming Soon'," +
		" 'Hemingway','South Carolina','USA',NULL,2016,NULL)," +
		"('frc6223',6223,'Arsenal Engineering','https://sites.google.com/menomoneefallsschools.org/arsenalengineering6223/home'," +
		" 'Menomonee Falls','Wisconsin','USA',NULL,2016,NULL)," +
		"('frc6224',6224,'Saint Dominators','http://www.stdomsmaine.org/'," +
		" 'Auburn','Maine','USA',NULL,2016,NULL)," +
		"('frc6225',6225,'SLC Roarin'' Robotics','http://www.SlcRobotics.org'," +
		" 'Fort Pierce','Florida','USA',NULL,2016,'Aim High')," +
		"('frc6226',6226,'Blue Devils','https://www.facebook.com/BlueDevilsFRC6226/'," +
		" 'Burlington','New Jersey','USA',NULL,2016,NULL)," +
		"('frc6227',6227,'Robohovers','http://www.firstinspires.org/'," +
		" 'Xi''An','Shaanxi','China',NULL,2016,NULL)," +
		"('frc6228',6228,'MAT Robotics','http://matrobotics.tk'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2016,'Just Think and do it')," +
		"('frc6229',6229,'spartans','http://spartans6229.esy.es'," +
		" 'metepec','Mexico','Mexico',NULL,2016,'THERE IS NOTHING IMPOSSIBLE ONLY LIMITED MINDS')," +
		"('frc6230',6230,'Team Koi','http://frc6230.wixsite.com/team6230'," +
		" 'Jerusalem','Yerushalayim','Israel',NULL,2016,NULL)," +
		"('frc6231',6231,'AKINCILAR','http://www.firstinspires.org/'," +
		" 'istanbul','Istanbul','Turkey',NULL,2016,'ÖNCE İNSAN SONRA İŞ GÜVENLİĞİ')," +
		"('frc6232',6232,'Florya Bisons ','http://www.floryabisons.com'," +
		" 'istanbul','Istanbul','Turkey',NULL,2016,NULL)," +
		"('frc6233',6233,'Phoe-Bots',NULL," +
		" 'Mission','TX','USA',NULL,2016,NULL)," +
		"('frc6234',6234,'Madson',NULL," +
		" 'kuna','ID','USA',NULL,2016,NULL)," +
		"('frc6235',6235,'Robo-Nerds',NULL," +
		" 'Midlothian','Texas','USA',NULL,2016,NULL)," +
		"('frc6236',6236,'Robots at The Center of the Universe','http://www.ratcotu.org'," +
		" 'Ashland','Virginia','USA',NULL,2016,NULL)," +
		"('frc6237',6237,'Martin Motion','http://www.motion.martin-eng.com/'," +
		" 'Neponset','Illinois','USA',NULL,2016,NULL)," +
		"('frc6238',6238,'POPCORN PENGUINS','https://www.popcornpenguins.com'," +
		" 'San Jose','California','USA',NULL,2016,NULL)," +
		"('frc6239',6239,'The Irrational Engineers','http://www.theirrationalengineers.com'," +
		" 'Bowie','Maryland','USA',NULL,2016,NULL)," +
		"('frc6240',6240,'Eagles of the Knight',NULL," +
		" 'Warrenton','North Carolina','USA',NULL,2016,NULL)," +
		"('frc6241',6241,'CowTech',NULL," +
		" 'San Jose','California','USA',NULL,2016,NULL)," +
		"('frc6242',6242,'Greene Machine',NULL," +
		" 'Belton','Texas','USA',NULL,2016,NULL)," +
		"('frc6243',6243,'Raiders Robotics',NULL," +
		" 'Red Deer','Alberta','Canada',NULL,2016,NULL)," +
		"('frc6244',6244,'Reese Rockets High School Robotics',NULL," +
		" 'Reese','Michigan','USA',NULL,2016,NULL)," +
		"('frc6245',6245,'Innovos Robotics','http://www.innovosrobotics.org'," +
		" 'Orange','California','USA',NULL,2016,NULL)," +
		"('frc6246',6246,'Union Knight Robotics','https://www.facebook.com/Union-Knights-Robotics-FRC-Team-286031215076524/'," +
		" 'La Porte City','Iowa','USA',NULL,2016,NULL)," +
		"('frc6248',6248,'Millbrook High School',NULL," +
		" 'Raleigh','North Carolina','USA',NULL,2016,NULL)," +
		"('frc6300',6300,'Northwood School Robotics','http://www.northwoodschoolrobotics.com'," +
		" 'Lake Placid','New York','USA',NULL,2017,NULL)," +
		"('frc6301',6301,'Nerointellect-sim Engineering & Robot Design Soiree   N.E.R.D.S.',NULL," +
		" 'Taunton','Massachusetts','USA',NULL,2017,NULL)," +
		"('frc6302',6302,'Greene Devils',NULL," +
		" 'Greeneville','Tennessee','USA',NULL,2017,NULL)," +
		"('frc6303',6303,'Citadel',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6304',6304,'EAGLE','http://www.team6304.com'," +
		" 'zhenjiang','Jiangsu','China',NULL,2017,NULL)," +
		"('frc6305',6305,'Stable Circuits','http://www.team6305.com'," +
		" 'Fresno','California','USA',NULL,2017,NULL)," +
		"('frc6306',6306,'Atonement Academy CrewStators',NULL," +
		" 'San Antonio','Texas','USA',NULL,2017,NULL)," +
		"('frc6307',6307,'Courtland High School',NULL," +
		" 'Fredericksburg','Virginia','USA',NULL,2017,NULL)," +
		"('frc6308',6308,'SPCS - Hafabots',NULL," +
		" 'Yigo','Guam','USA',NULL,2017,NULL)," +
		"('frc6309',6309,'SPCS - Koko Tek',NULL," +
		" 'Yigo','Guam','USA',NULL,2017,NULL)," +
		"('frc6312',6312,'Hippocampinae',NULL," +
		" 'Christchurch','Virginia','USA',NULL,2017,NULL)," +
		"('frc6313',6313,'Hippocampus',NULL," +
		" 'Christchurch','Virginia','USA',NULL,2017,NULL)," +
		"('frc6314',6314,'DM Robotics The WolfPack','http://wolfpack6314.com'," +
		" 'Scottsdale','Arizona','USA',NULL,2017,NULL)," +
		"('frc6315',6315,'Code Error 404',NULL," +
		" 'Laredo','Texas','USA',NULL,2017,NULL)," +
		"('frc6317',6317,'Disruptive Innovation','https://davenportwest.github.io/6317/'," +
		" 'Davenport','Iowa','USA',NULL,2017,NULL)," +
		"('frc6318',6318,'FE Freedom Engineers','https://sites.google.com/a/freedomschools.k12.wi.us/fe-6318/'," +
		" 'Freedom','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6319',6319,'Tecmilenio ENKI-NOVA 6319','https://www.facebook.com/EnkiNovaUTM/'," +
		" 'AHOME','Sinaloa','Mexico',NULL,2017,NULL)," +
		"('frc6320',6320,'Iron Hawks',NULL," +
		" 'Manchester','Iowa','USA',NULL,2017,NULL)," +
		"('frc6321',6321,'■ R-Cubed  Rouse Raider Robotics ■','http://www.rouserobotics.org'," +
		" 'Leander','Texas','USA',NULL,2017,NULL)," +
		"('frc6322',6322,'ONECLAY A.R.M.O.R.Y.',NULL," +
		" 'Orange Park','Florida','USA',NULL,2017,NULL)," +
		"('frc6323',6323,'Hayden Robotics','http://haydenrobotics.ca'," +
		" 'Burlington','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6324',6324,'The Blue Devils',NULL," +
		" 'Salem','New Hampshire','USA',NULL,2017,NULL)," +
		"('frc6325',6325,'Reset Robotics','http://www.resetrobotics.org'," +
		" 'Alpharetta','Georgia','USA',NULL,2017,NULL)," +
		"('frc6326',6326,'⚡ Baltimore Bolts ⚡','http://www.baltimorebolts.com'," +
		" 'Baltimore','Maryland','USA',NULL,2017,NULL)," +
		"('frc6327',6327,'Tin Men','http://www.EpraPrograms.com'," +
		" 'Media','Pennsylvania','USA',NULL,2017,NULL)," +
		"('frc6328',6328,'Mechanical Advantage','http://www.littletonrobotics.org'," +
		" 'Littleton','Massachusetts','USA',NULL,2017,NULL)," +
		"('frc6329',6329,'The Bucks'' Wrath','https://www.facebook.com/thebuckswrath/'," +
		" 'Bucksport','Maine','USA',NULL,2017,NULL)," +
		"('frc6330',6330,'6330 - Arc Angels',NULL," +
		" 'Mississauga','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6331',6331,'Sabotage','http://lessorrobotics.ca'," +
		" 'Windsor','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6332',6332,'Bull City Botics','http://www.bullcitybotics.co/'," +
		" 'Durham','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6333',6333,'Scavenger Robotics',NULL," +
		" 'Putnam','Connecticut','USA',NULL,2017,NULL)," +
		"('frc6334',6334,'Aluminati','http://aluminatirobotics.weebly.com/'," +
		" 'Midlothian','Virginia','USA',NULL,2017,NULL)," +
		"('frc6335',6335,'Terminal Velocity',NULL," +
		" 'Burlington','Massachusetts','USA',NULL,2017,NULL)," +
		"('frc6336',6336,'Javawockies',NULL," +
		" 'Springwater','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6337',6337,'Terror Bytes','https://www.facebook.com/MCIRobotics/'," +
		" 'Pittsfield','Maine','USA',NULL,2017,NULL)," +
		"('frc6338',6338,'DRIVING STEEL',NULL," +
		" 'Macomb','Michigan','USA',NULL,2017,NULL)," +
		"('frc6339',6339,'Pelican Falls First Nations High School',NULL," +
		" 'Sioux Lookout','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6340',6340,'The Marist Manatees','https://www.maristrobotics.com/'," +
		" 'Atlanta','Georgia','USA',NULL,2017,NULL)," +
		"('frc6341',6341,'Etowah Electric Eagles',NULL," +
		" 'Woodstock','Georgia','USA',NULL,2017,NULL)," +
		"('frc6342',6342,'BT Robotics','http://www.6342.ca'," +
		" 'Ancaster','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6343',6343,'Steel Ridge Robotics','https://steelridge6343.com'," +
		" 'Ridgefield','Washington','USA',NULL,2017,NULL)," +
		"('frc6344',6344,'Yale Jiggawattz','http://www.YaleJiggawattz6344.com'," +
		" 'Yale','Michigan','USA',NULL,2017,NULL)," +
		"('frc6345',6345,'Nimrod Nation',NULL," +
		" 'Watersmeet','Michigan','USA',NULL,2017,NULL)," +
		"('frc6346',6346,'Norwalk Cybears Robotics Team',NULL," +
		" 'Norwalk','Connecticut','USA',NULL,2017,NULL)," +
		"('frc6347',6347,'St. James Lions',NULL," +
		" 'Guelph','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6348',6348,'PrepaTec - HORUS','http://www.horuscolima.com'," +
		" 'COLIMA','Colima','Mexico',NULL,2017,NULL)," +
		"('frc6349',6349,'Albany High Indians',NULL," +
		" 'Albany','Georgia','USA',NULL,2017,NULL)," +
		"('frc6350',6350,'Clawbots','https://sites.google.com/site/ehsr6350/'," +
		" 'Enumclaw','Washington','USA',NULL,2017,NULL)," +
		"('frc6351',6351,'Rundle College Robotics',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2017,NULL)," +
		"('frc6352',6352,'LAUNCH TEAM','http://bit.ly/6352Web'," +
		" 'Surprise','Arizona','USA',NULL,2017,NULL)," +
		"('frc6353',6353,'Zodiac','http://www.team6353.com'," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6354',6354,'Jiayi Superhuman Strength',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6355',6355,'Robots Over Parma','https://frc6355robotsoverparma.wordpress.com/'," +
		" 'Cleveland','Ohio','USA',NULL,2017,NULL)," +
		"('frc6356',6356,'MetaRobotics','http://www.metarobotics.org'," +
		" 'Shenzhen','Guangdong','China',NULL,2017,NULL)," +
		"('frc6357',6357,'The Spring Konstant','http://www.springkonstant.org'," +
		" 'Dripping Springs','Texas','USA',NULL,2017,NULL)," +
		"('frc6358',6358,'The Buhlean Operators ',NULL," +
		" 'Buhl','Idaho','USA',NULL,2017,NULL)," +
		"('frc6359',6359,'TechKnow Difficulties',NULL," +
		" 'Aurora','Nebraska','USA',NULL,2017,NULL)," +
		"('frc6360',6360,'Quanzhou No.5 Middle School',NULL," +
		" 'Quanzhou','Fujian','China',NULL,2017,NULL)," +
		"('frc6361',6361,'The Blazer',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6362',6362,'Shengxue Corps',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6363',6363,'BirdBots',NULL," +
		" 'El Campo','Texas','USA',NULL,2017,NULL)," +
		"('frc6364',6364,'Ten Ton Robotics','https://www.facebook.com/TenTonRobotics/'," +
		" 'West Vancouver','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6365',6365,'Imperial star destroyer',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6366',6366,'RAM Rodz Robotics','https://uaskinshuber.wixsite.com/ramrodz'," +
		" 'Simpsonville','South Carolina','USA',NULL,2017,NULL)," +
		"('frc6367',6367,'The ElectroLights','http://team6367.org'," +
		" 'Boston','Massachusetts','USA',NULL,2017,NULL)," +
		"('frc6368',6368,'Unknown',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6369',6369,'Mercenary Robotics',NULL," +
		" 'McKinney','Texas','USA',NULL,2017,NULL)," +
		"('frc6370',6370,'Texas Titans','http://www.texastitans.org'," +
		" 'Helotes','Texas','USA',NULL,2017,NULL)," +
		"('frc6371',6371,'Spencer Robotics',NULL," +
		" 'Spencer','Iowa','USA',NULL,2017,NULL)," +
		"('frc6372',6372,'Chi-squared Robotics',NULL," +
		" 'Gilbert','Arizona','USA',NULL,2017,NULL)," +
		"('frc6373',6373,'River City Robotics',NULL," +
		" 'Chattanooga','Tennessee','USA',NULL,2017,NULL)," +
		"('frc6374',6374,'NanChong Union',NULL," +
		" 'Nan Chong','Sichuan','China',NULL,2017,NULL)," +
		"('frc6375',6375,'MC²',NULL," +
		" 'Beijing','Beijing','China',NULL,2017,NULL)," +
		"('frc6376',6376,'Further Dream',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6377',6377,'Howdy Bots','http://www.howdybots.org'," +
		" 'Austin','Texas','USA',NULL,2017,NULL)," +
		"('frc6378',6378,'LYNX','http://frc6378.weebly.com/'," +
		" 'Mississauga','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6379',6379,'Terabyte of Ram','https://sites.google.com/a/southeastpolk.org/southeast-polk-first-robotics-competition/'," +
		" 'Pleasant Hill','Iowa','USA',NULL,2017,NULL)," +
		"('frc6380',6380,'Orion Robotics','http://team6380.wixsite.com/orion'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6381',6381,'Red Raider Robotics','https://www.redraiderrobotics.org/'," +
		" 'Sheboygan','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6382',6382,'RoboHornz',NULL," +
		" 'Vista','California','USA',NULL,2017,NULL)," +
		"('frc6383',6383,'PLUG&PLAY',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2017,NULL)," +
		"('frc6384',6384,'Hisar Ravens','http://www.hisarravens.com'," +
		" 'Gokturk ISTANBUL','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6385',6385,'TrueMoe','https://truemoe.cn'," +
		" 'Jinhua','Zhejiang','China',NULL,2017,NULL)," +
		"('frc6386',6386,'Guangzhou research robot club',NULL," +
		" 'GUANGZHOU','Guangdong','China',NULL,2017,NULL)," +
		"('frc6387',6387,'Discobots Canada','http:///bramptonrobotics.org'," +
		" 'Brampton','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6388',6388,'WonderWorks Robotics',NULL," +
		" 'istanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6389',6389,'Matster Mechanist',NULL," +
		" 'Nanjing','Jiangsu','China',NULL,2017,NULL)," +
		"('frc6390',6390,'Hephaestus ','http://www.Hephaestus6390.com'," +
		" 'Surrey','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6391',6391,'Ursuline Bearbotics',NULL," +
		" 'Saint Louis','Missouri','USA',NULL,2017,NULL)," +
		"('frc6392',6392,'Dimension Creator',NULL," +
		" 'Chengdu','Sichuan','China',NULL,2017,NULL)," +
		"('frc6393',6393,'Pioneer','http://www.team6393.com'," +
		" 'Zhengzhou','Henan','China',NULL,2017,NULL)," +
		"('frc6394',6394,'MITO',NULL," +
		" 'Suzhou','Jiangsu','China',NULL,2017,NULL)," +
		"('frc6395',6395,'Xindu No.2 High School ',NULL," +
		" 'Chengdu ','Sichuan','China',NULL,2017,NULL)," +
		"('frc6396',6396,'Steel Tempest','http://www.frssteeltempest.com'," +
		" 'Pensacola','Florida','USA',NULL,2017,NULL)," +
		"('frc6397',6397,'Romero Robotics',NULL," +
		" 'York','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6398',6398,'Kilo-bots',NULL," +
		" 'Santa Paula','California','USA',NULL,2017,NULL)," +
		"('frc6399',6399,'Jinan foreign language school',NULL," +
		" 'JINAN','Shandong','China',NULL,2017,NULL)," +
		"('frc6400',6400,'The Manhattan Project','http://manhattanfirstrobotics.com'," +
		" 'Manhattan','Montana','USA',NULL,2017,NULL)," +
		"('frc6401',6401,'8-bit RAMs',NULL," +
		" 'New City','New York','USA',NULL,2017,NULL)," +
		"('frc6402',6402,'GOKTURKLER','http://www.roboatalar.com'," +
		" 'İSTANBUL','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6403',6403,'D.P. Todd Secondary',NULL," +
		" 'Prince George','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6404',6404,'Brazilian Storm','https://www.facebook.com/brazilianstorm6404/'," +
		" 'São José dos Campos','São Paulo','Brazil',NULL,2017,NULL)," +
		"('frc6405',6405,'EntreBots','http://www.EntreBots.com'," +
		" 'Palmdale','California','USA',NULL,2017,NULL)," +
		"('frc6406',6406,'Cyber/Scorpions',NULL," +
		" 'Salt Spring Island','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6407',6407,'Tecmilenio - DEVOLT - 6407','https://www.facebook.com/Devolt-FRC-567472640110257/'," +
		" 'Chihuahua','Chihuahua','Mexico',NULL,2017,NULL)," +
		"('frc6408',6408,'Syntechs Robotics',NULL," +
		" 'Port Moody','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6409',6409,'Odrinias',NULL," +
		" 'Edirne','Edirne','Turkey',NULL,2017,NULL)," +
		"('frc6410',6410,'Supreme Eagles','http://MarkMentze.weebly.com'," +
		" 'Salinas','California','USA',NULL,2017,NULL)," +
		"('frc6411',6411,'Gravediggers','https://6411gravediggers.weebly.com/'," +
		" 'Sandy','Utah','USA',NULL,2017,NULL)," +
		"('frc6412',6412,'For Sale By Owner',NULL," +
		" 'Cedar Rapids','Iowa','USA',NULL,2017,NULL)," +
		"('frc6413',6413,'Degrees of Freedom','http://www.dof6413.org'," +
		" 'Chandler','Arizona','USA',NULL,2017,NULL)," +
		"('frc6414',6414,'Voyager','http://www.sfls-frc.com'," +
		" 'Shenzhen','Guangdong','China',NULL,2017,NULL)," +
		"('frc6415',6415,'GULTEPE ROBOTICS','http://www.team6415.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6416',6416,'MEVOLUTION ROBOTIC TEAM','https://www.facebook.com/MEVMAKERS/'," +
		" 'Büyükçekmece','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6417',6417,'Fridolins',NULL," +
		" 'Glarus','Glarus','Switzerland',NULL,2017,NULL)," +
		"('frc6418',6418,'The Missfits','https://www.team6418.org'," +
		" 'San Francisco','California','USA',NULL,2017,NULL)," +
		"('frc6419',6419,'ICE','http://www.IowaICE.org'," +
		" 'West Des Moines','Iowa','USA',NULL,2017,NULL)," +
		"('frc6420',6420,'Fire Island Robotics','http://www.fireislandrobotics.com'," +
		" 'Muscatine','Iowa','USA',NULL,2017,NULL)," +
		"('frc6421',6421,'WarriorBots','http://www.warriorbots6421.com/'," +
		" 'Muskego','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6422',6422,'GearHeadz','http://www.rockypointroboticsclub.com'," +
		" 'Rocky Point','New York','USA',NULL,2017,NULL)," +
		"('frc6423',6423,'Ward Melville High School',NULL," +
		" 'East Setauket','New York','USA',NULL,2017,NULL)," +
		"('frc6424',6424,'Stealth Panther Robotics','https://stealthpantherrobotics6424.weebly.com/'," +
		" 'Knob Noster','Missouri','USA',NULL,2017,NULL)," +
		"('frc6425',6425,'The Conclave','https://sites.google.com/ccscards.org/chsrobotics'," +
		" 'Coldwater','Michigan','USA',NULL,2017,NULL)," +
		"('frc6426',6426,'Robo Gladiators','http://www.compassyouthcenters.org'," +
		" 'Durham','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6427',6427,'Glendale',NULL," +
		" 'London','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6428',6428,'STEMINEERS','http://www.coopersvillescience.org'," +
		" 'Coopersville','Michigan','USA',NULL,2017,NULL)," +
		"('frc6429',6429,'4th Dimension','http://team6429.com'," +
		" 'Bornova','Izmir','Turkey',NULL,2017,NULL)," +
		"('frc6430',6430,'Kalsedon',NULL," +
		" 'Eskişehir','Eskisehir','Turkey',NULL,2017,NULL)," +
		"('frc6431',6431,'NoktaParantez','http://frc.hisarcs.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6432',6432,'St Cathodes',NULL," +
		" 'Waverley','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6433',6433,'HZ4Z','http://www.hz4zrobotics.org'," +
		" 'Hangzhou','Zhejiang','China',NULL,2017,NULL)," +
		"('frc6434',6434,'Bossley Park High School','http://roboticsbphs.org.au/'," +
		" 'Bossley Park','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6435',6435,'Jetson''s Robotics','http://www.jetsonsrobotics6435.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6436',6436,'PARS ROBOTICS','http://www.parsrobotics.org/'," +
		" 'Basaksehir','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6437',6437,'The Pacific Quakers',NULL," +
		" 'Portland','Oregon','USA',NULL,2017,NULL)," +
		"('frc6438',6438,'MEF THUNDER FTD',NULL," +
		" 'Besiktas','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6439',6439,'tanx',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6440',6440,'Canobolas',NULL," +
		" 'Orange','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6441',6441,'Turbo Turtle',NULL," +
		" 'Nanjing','Jiangsu','China',NULL,2017,NULL)," +
		"('frc6442',6442,'Modern Americans','https://modernamericans6442.wordpress.com/'," +
		" 'Pendleton','Oregon','USA',NULL,2017,NULL)," +
		"('frc6443',6443,'AEMBOT','http://www.aembot.weebly.com'," +
		" 'Hillsboro','Oregon','USA',NULL,2017,NULL)," +
		"('frc6444',6444,'PrepaTec - Sigma Robotics by Grupo Botrregos',NULL," +
		" 'Chihuahua','Chihuahua','Mexico',NULL,2017,NULL)," +
		"('frc6445',6445,'CTEC Robotics',NULL," +
		" 'Salem','Oregon','USA',NULL,2017,NULL)," +
		"('frc6446',6446,'Redhawks',NULL," +
		" 'Blythewood','South Carolina','USA',NULL,2017,NULL)," +
		"('frc6447',6447,'Bad 2 the Bone - Bingara Central School',NULL," +
		" 'Bingara','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6448',6448,'LBJ Early College High School ',NULL," +
		" 'Austin','Texas','USA',NULL,2017,NULL)," +
		"('frc6449',6449,'DigiBots',NULL," +
		" 'Warnbro','Western Australia','Australia',NULL,2017,NULL)," +
		"('frc6450',6450,'The Reaper',NULL," +
		" 'Jinhua ','Zhejiang','China',NULL,2017,NULL)," +
		"('frc6451',6451,'Wired Warriors','https://6451wiredwarriors.com'," +
		" 'Whiteland','Indiana','USA',NULL,2017,NULL)," +
		"('frc6452',6452,'GPrep Thumpin Pup',NULL," +
		" 'Spokane','Washington','USA',NULL,2017,NULL)," +
		"('frc6453',6453,'Bog Bots!','https://www.bogbots6453.org'," +
		" 'Kelliher','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6454',6454,'Chicken Strips',NULL," +
		" 'Pasco','Washington','USA',NULL,2017,NULL)," +
		"('frc6455',6455,'The Coded Collective',NULL," +
		" 'Waterloo','Iowa','USA',NULL,2017,NULL)," +
		"('frc6456',6456,'Oregon Trail Academy Wi-Fires',NULL," +
		" 'Boring','Oregon','USA',NULL,2017,NULL)," +
		"('frc6457',6457,'Roots Charter High School',NULL," +
		" 'West Valley City','Utah','USA',NULL,2017,NULL)," +
		"('frc6458',6458,'Semiahmoo Robotics','http://semirobotix.wixsite.com/semi'," +
		" 'Surrey','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6459',6459,'AG Robotik','http://www.team6459.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6460',6460,'Air Guardians','http://airguardians.org'," +
		" 'Mansfield','Ohio','USA',NULL,2017,NULL)," +
		"('frc6461',6461,'Iron Stallions',NULL," +
		" 'Wingham','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6462',6462,'techJOYnT',NULL," +
		" 'Edmond','Oklahoma','USA',NULL,2017,NULL)," +
		"('frc6463',6463,'RAMS','http://ulsterboces.org'," +
		" 'Port Ewen','New York','USA',NULL,2017,NULL)," +
		"('frc6464',6464,'Danger Noodle Danger',NULL," +
		" 'Seminole','Oklahoma','USA',NULL,2017,NULL)," +
		"('frc6465',6465,'Mystic Biscuit','https://www.instagram.com/real_mystic_biscuit'," +
		" 'Bend','Oregon','USA',NULL,2017,NULL)," +
		"('frc6466',6466,'Tiger Tech',NULL," +
		" 'Stanfield','Oregon','USA',NULL,2017,NULL)," +
		"('frc6467',6467,'Tiger Robotics',NULL," +
		" 'Lenox','Iowa','USA',NULL,2017,NULL)," +
		"('frc6468',6468,'The Positronic Panthers',NULL," +
		" 'Ponte Vedra','Florida','USA',NULL,2017,NULL)," +
		"('frc6469',6469,'Junkyard Dogs',NULL," +
		" 'Goochland','Virginia','USA',NULL,2017,NULL)," +
		"('frc6470',6470,'Templeton STEM',NULL," +
		" 'Vancouver','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6471',6471,'Lee County High School Trobots','http://www.lee.k12.ga.us/lchs/index.php'," +
		" 'Leesburg','Georgia','USA',NULL,2017,NULL)," +
		"('frc6472',6472,'H-TECH',NULL," +
		" 'uskudar','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6473',6473,'Rock Star Robotics','http://rockstarrobotics.wixsite.com/rockstarrobot'," +
		" 'Orlando','Florida','USA',NULL,2017,NULL)," +
		"('frc6474',6474,'Indigo Dynamics',NULL," +
		" 'Fairfield','California','USA',NULL,2017,NULL)," +
		"('frc6475',6475,'Helium-3',NULL," +
		" 'Beijing','Beijing','China',NULL,2017,NULL)," +
		"('frc6476',6476,'Supernova Star Squad',NULL," +
		" 'Coonabarabran','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6477',6477,'Conqueror of Istanbul',NULL," +
		" 'Buyukcekmece','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6478',6478,'CASHS Bot Team',NULL," +
		" 'Seville Grove','Western Australia','Australia',NULL,2017,NULL)," +
		"('frc6479',6479,'AzTech','http://www.tinyurl.com/cdsrobotics'," +
		" 'Tempe','Arizona','USA',NULL,2017,NULL)," +
		"('frc6480',6480,'SAGE Robotics',NULL," +
		" 'Minneapolis','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6481',6481,'Deus Ex Machina','https://www.facebook.com/Team6481'," +
		" 'Parkhill','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6482',6482,'Wildcat Robotics',NULL," +
		" 'Joseph City','Arizona','USA',NULL,2017,NULL)," +
		"('frc6483',6483,'Tecmilenio - Charger',NULL," +
		" 'juarez','Chihuahua','Mexico',NULL,2017,NULL)," +
		"('frc6484',6484,'TechKnowLogic','http://greenwichrobotics.org'," +
		" 'Greenwich','New York','USA',NULL,2017,NULL)," +
		"('frc6485',6485,'Matheson''s Mecha Mustangs','http://lamrobotics.weebly.com/'," +
		" 'Surrey','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6486',6486,'Ten More Tons','https://www.facebook.com/TenTonRobotics/'," +
		" 'West Vancouver','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6487',6487,'Clockwork Knights',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6488',6488,'RoboRams',NULL," +
		" 'Houston','Texas','USA',NULL,2017,NULL)," +
		"('frc6489',6489,'Bronco Robotics',NULL," +
		" 'Zachary','Louisiana','USA',NULL,2017,NULL)," +
		"('frc6490',6490,'Panther Robotics',NULL," +
		" 'Geneva','New York','USA',NULL,2017,NULL)," +
		"('frc6491',6491,'Short Circuits',NULL," +
		" 'Fairhope','Alabama','USA',NULL,2017,NULL)," +
		"('frc6492',6492,'Pangburn S.W.A.T.',NULL," +
		" 'Pangburn','Arkansas','USA',NULL,2017,NULL)," +
		"('frc6493',6493,'Red Steel',NULL," +
		" 'University Center','Michigan','USA',NULL,2017,NULL)," +
		"('frc6494',6494,'Wings of Liberty',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2017,NULL)," +
		"('frc6495',6495,'Future Star',NULL," +
		" 'Beijing','Beijing','China',NULL,2017,NULL)," +
		"('frc6496',6496,'Semia United',NULL," +
		" 'Beijing','Beijing','China',NULL,2017,NULL)," +
		"('frc6497',6497,'Future Forward',NULL," +
		" 'Beijing','Beijing','China',NULL,2017,NULL)," +
		"('frc6498',6498,'Castle High School',NULL," +
		" 'Newburgh','Indiana','USA',NULL,2017,NULL)," +
		"('frc6499',6499,'Innovos Robotics','http://www.innovosrobotics.org'," +
		" 'Orange','California','USA',NULL,2017,NULL);";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_13 = SQL_INSERT_TEAMS +
		"('frc6500',6500,'GearCats','http://millbrookrobotics.com/'," +
		" 'Raleigh','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6502',6502,'DARC SIDE',NULL," +
		" 'Durham','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6503',6503,'Iron Dragon',NULL," +
		" 'Seattle','Washington','USA',NULL,2017,NULL)," +
		"('frc6504',6504,'Jagwires','http://FallsChurchRobotics.com'," +
		" 'Falls Church','Virginia','USA',NULL,2017,NULL)," +
		"('frc6505',6505,'ElectroPanthers',NULL," +
		" 'Washington','District of Columbia','USA',NULL,2017,NULL)," +
		"('frc6506',6506,'Steel Boot',NULL," +
		" 'Salinas','California','USA',NULL,2017,NULL)," +
		"('frc6507',6507,'Tecmilenio    ELEKTRON  6507','http://elektron-6507.webnode.mx/'," +
		" 'Reynosa','Tamaulipas','Mexico',NULL,2017,NULL)," +
		"('frc6508',6508,'Hastings Heroes',NULL," +
		" 'Port Macquarie','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6509',6509,'Southern River',NULL," +
		" 'Gosnells','Western Australia','Australia',NULL,2017,NULL)," +
		"('frc6510',6510,'Pymble Pride','https://www.facebook.com/PymbleLCrobotics/'," +
		" 'Pymble','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6511',6511,'Oberoi',NULL," +
		" 'Mumbai','Maharashtra','India',NULL,2017,NULL)," +
		"('frc6512',6512,'Coastal CATastrophe',NULL," +
		" 'Bolivia','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6513',6513,'Wyvern Robotics',NULL," +
		" 'Woodbridge','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6514',6514,'Sutton Robotics League',NULL," +
		" 'Sutton West','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6515',6515,'Unimatics','https://sites.google.com/calexico.k12.ca.us/chs-first-robotics'," +
		" 'Calexico','California','USA',NULL,2017,NULL)," +
		"('frc6516',6516,'Robonados',NULL," +
		" 'Lebanon','Ohio','USA',NULL,2017,NULL)," +
		"('frc6517',6517,'SoKno Robo ','http://www.soknorobo.com'," +
		" 'Knoxville','Tennessee','USA',NULL,2017,NULL)," +
		"('frc6518',6518,'JagBots','https://www.facebook.com/JagBots6518/'," +
		" 'Phoenix','Arizona','USA',NULL,2017,NULL)," +
		"('frc6519',6519,'The Vegas Vortechs',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2017,NULL)," +
		"('frc6520',6520,'Hanoi Amsterdam School',NULL," +
		" 'hanoi','Ha Noi','Vietnam',NULL,2017,NULL)," +
		"('frc6521',6521,'FPT',NULL," +
		" 'hanoi','Ha Noi','Vietnam',NULL,2017,NULL)," +
		"('frc6522',6522,'Tomaree High School',NULL," +
		" 'Salamander Bay','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6523',6523,'Condobolin',NULL," +
		" 'Condobolin','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6524',6524,'Wilder Wolves FRC','https://www.cacollegestem.org/frcteam6524'," +
		" 'Seville Grove','Western Australia','Australia',NULL,2017,NULL)," +
		"('frc6525',6525,'Greater Sydney Robotics',NULL," +
		" 'Sydney','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6526',6526,'Cyber Rangers','http:///NFHSREO.weebly.com'," +
		" 'Garland','Texas','USA',NULL,2017,NULL)," +
		"('frc6527',6527,'Short SirKit','http://shortsirkit.site11.com/'," +
		" 'Lake Mary','Florida','USA',NULL,2017,NULL)," +
		"('frc6528',6528,'Terror Bytes',NULL," +
		" 'Gibraltar','Michigan','USA',NULL,2017,NULL)," +
		"('frc6529',6529,'Beantown Blitz',NULL," +
		" 'Roxbury Crossing','Massachusetts','USA',NULL,2017,NULL)," +
		"('frc6530',6530,'Ra',NULL," +
		" 'Phoenix','Arizona','USA',NULL,2017,NULL)," +
		"('frc6531',6531,'Redhawks',NULL," +
		" 'Cedar Springs','Michigan','USA',NULL,2017,NULL)," +
		"('frc6532',6532,'Ben Carson Blue Jays',NULL," +
		" 'Detroit','Michigan','USA',NULL,2017,NULL)," +
		"('frc6533',6533,'BeaverTech',NULL," +
		" 'Beaverton','Michigan','USA',NULL,2017,NULL)," +
		"('frc6534',6534,'Saints Scholars','http://www.greaat.com'," +
		" 'Grand Rapids','Michigan','USA',NULL,2017,NULL)," +
		"('frc6535',6535,'Edison Sustainabots',NULL," +
		" 'Huntington Beach','California','USA',NULL,2017,NULL)," +
		"('frc6536',6536,'TheDarkSide',NULL," +
		" 'Vinton','Iowa','USA',NULL,2017,NULL)," +
		"('frc6537',6537,'WIHS Robotics','http://www.wihs.ca'," +
		" 'Windsor','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6538',6538,'Linc-Bots',NULL," +
		" 'Ypsilanti','Michigan','USA',NULL,2017,NULL)," +
		"('frc6539',6539,'Success Robotics Team',NULL," +
		" 'Ontonagon','Michigan','USA',NULL,2017,NULL)," +
		"('frc6540',6540,'Dynamo',NULL," +
		" 'Mont-Royal','Québec','Canada',NULL,2017,NULL)," +
		"('frc6541',6541,'T-FROGS','http://tfrogs6541.wixsite.com/tfrogs'," +
		" 'LA TALAUDIERE','Loire','France',NULL,2017,NULL)," +
		"('frc6542',6542,'Technomancers',NULL," +
		" 'Pittsburg','Kansas','USA',NULL,2017,NULL)," +
		"('frc6543',6543,'pumaTECH',NULL," +
		" 'Oakton','Virginia','USA',NULL,2017,NULL)," +
		"('frc6544',6544,'A-Team Robotics','http://www.a-teamrobotics.com'," +
		" 'Amherstburg','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6545',6545,'Tawas Braves',NULL," +
		" 'Tawas City','Michigan','USA',NULL,2017,NULL)," +
		"('frc6546',6546,'Naatsisʼáán Robotics',NULL," +
		" 'Navajo Mountain','Utah','USA',NULL,2017,NULL)," +
		"('frc6547',6547,'Flaming Metal Robotics',NULL," +
		" 'Magnolia','Texas','USA',NULL,2017,NULL)," +
		"('frc6548',6548,'Perry High School RAMBOTS',NULL," +
		" 'Perry','Michigan','USA',NULL,2017,NULL)," +
		"('frc6549',6549,'caseville eagles',NULL," +
		" 'Caseville','Michigan','USA',NULL,2017,NULL)," +
		"('frc6550',6550,'Wildcat Robotics ',NULL," +
		" 'Evart','Michigan','USA',NULL,2017,NULL)," +
		"('frc6551',6551,'MOOTeam','http://scc4hrobotics.com'," +
		" 'Port Huron','Michigan','USA',NULL,2017,NULL)," +
		"('frc6552',6552,'RoboCats',NULL," +
		" 'Houghton Lake','Michigan','USA',NULL,2017,NULL)," +
		"('frc6553',6553,'Roboticus',NULL," +
		" 'Oxnard','California','USA',NULL,2017,NULL)," +
		"('frc6554',6554,'Phoenix Robotics','https://www.phoenix6554.com'," +
		" 'La Mirada','California','USA',NULL,2017,NULL)," +
		"('frc6555',6555,'Ad Astra',NULL," +
		" 'Bursa','Bursa','Turkey',NULL,2017,NULL)," +
		"('frc6556',6556,'Waldron Bot Heads',NULL," +
		" 'Waldron','Michigan','USA',NULL,2017,NULL)," +
		"('frc6557',6557,'S.R.U - Special Robotics Unit',NULL," +
		" 'Baraga','Michigan','USA',NULL,2017,NULL)," +
		"('frc6558',6558,'Aluminum Sting',NULL," +
		" 'Lanse','Michigan','USA',NULL,2017,NULL)," +
		"('frc6559',6559,'Bay-Arenac',NULL," +
		" 'Bay City','Michigan','USA',NULL,2017,NULL)," +
		"('frc6560',6560,'Charging Champions','http://www.chargingchampions.org'," +
		" 'Irvine','California','USA',NULL,2017,NULL)," +
		"('frc6561',6561,'Omecha Wolf Pack','http://www.g2sacademy.net'," +
		" 'Scottville','Michigan','USA',NULL,2017,NULL)," +
		"('frc6562',6562,'She Heroes',NULL," +
		" 'Mercedes','Texas','USA',NULL,2017,NULL)," +
		"('frc6563',6563,'ROBO CORR',NULL," +
		" 'Quesnel','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6564',6564,'TDSS Titans',NULL," +
		" 'Woodbridge','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6565',6565,'Team Bobcat',NULL," +
		" 'Arapahoe','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6566',6566,'Lamphere Battering Rams',NULL," +
		" 'Madison Heights','Michigan','USA',NULL,2017,NULL)," +
		"('frc6567',6567,'The Broken Drill Bits ',NULL," +
		" 'Saint Clair Shores','Michigan','USA',NULL,2017,NULL)," +
		"('frc6568',6568,'Decatur Robotics Team',NULL," +
		" 'Decatur','Michigan','USA',NULL,2017,NULL)," +
		"('frc6569',6569,'Gladiators',NULL," +
		" 'Ontonagon','Michigan','USA',NULL,2017,NULL)," +
		"('frc6570',6570,'Whiteford Bobcats',NULL," +
		" 'Ottawa Lake','Michigan','USA',NULL,2017,NULL)," +
		"('frc6571',6571,'Lakeview Wildcats',NULL," +
		" 'Lakeview','Michigan','USA',NULL,2017,NULL)," +
		"('frc6572',6572,'Forest Area',NULL," +
		" 'Fife Lake','Michigan','USA',NULL,2017,NULL)," +
		"('frc6573',6573,'Alanson Viking Robotics',NULL," +
		" 'Alanson','Michigan','USA',NULL,2017,NULL)," +
		"('frc6574',6574,'Ferradermis','http://www.ferradermis.org'," +
		" 'Whitewater','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6575',6575,'Tempe T-Rex',NULL," +
		" 'Tempe','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6576',6576,'High School Science Education',NULL," +
		" 'Hanoi','Ha Noi','Vietnam',NULL,2017,NULL)," +
		"('frc6577',6577,'South City International School',NULL," +
		" 'Kolkata','West Bengal','India',NULL,2017,NULL)," +
		"('frc6578',6578,'G D Goenka',NULL," +
		" 'New Delhi','Delhi','India',NULL,2017,NULL)," +
		"('frc6579',6579,'Komplete Kaos Inc','http://kompletekaos.org/'," +
		" 'Helensburgh','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6580',6580,'Positive Charge',NULL," +
		" 'Marshall','Michigan','USA',NULL,2017,NULL)," +
		"('frc6581',6581,'Hancock Bulldogs ',NULL," +
		" 'Hancock','Michigan','USA',NULL,2017,NULL)," +
		"('frc6582',6582,'Vestabots','http://www.vestabots6582.weebly.com'," +
		" 'Vestaburg','Michigan','USA',NULL,2017,NULL)," +
		"('frc6583',6583,'Human Error','https://www.facebook.com/6583HumanError'," +
		" 'Plymouth','Michigan','USA',NULL,2017,NULL)," +
		"('frc6584',6584,'AHC Transformers','http://www.academyoftheholycross.org/'," +
		" 'Kensington','Maryland','USA',NULL,2017,NULL)," +
		"('frc6585',6585,'Hózhóogo Naasháa Doo (Walk in Beauty)',NULL," +
		" 'Many Farms','Arizona','USA',NULL,2017,NULL)," +
		"('frc6586',6586,'Tuskin'' Raiders',NULL," +
		" 'Texarkana','Arkansas','USA',NULL,2017,NULL)," +
		"('frc6587',6587,'WCA Cougars',NULL," +
		" 'Hillsdale','Michigan','USA',NULL,2017,NULL)," +
		"('frc6588',6588,'Counter-Torque Robotics','http://www.lawtoncs.org'," +
		" 'Lawton','Michigan','USA',NULL,2017,NULL)," +
		"('frc6589',6589,'Cardinal.EXE',NULL," +
		" 'Bloomingdale','Michigan','USA',NULL,2017,NULL)," +
		"('frc6590',6590,'SaviTech',NULL," +
		" 'Brooklyn','New York','USA',NULL,2017,NULL)," +
		"('frc6591',6591,'Panther Powered',NULL," +
		" 'Stockbridge','Michigan','USA',NULL,2017,NULL)," +
		"('frc6592',6592,'Sexton Redbots',NULL," +
		" 'Lansing','Michigan','USA',NULL,2017,NULL)," +
		"('frc6593',6593,'HyperDrive Robotics',NULL," +
		" 'Richmond Hill','New York','USA',NULL,2017,NULL)," +
		"('frc6594',6594,'EV ROBOTICS',NULL," +
		" 'Lansing','Michigan','USA',NULL,2017,NULL)," +
		"('frc6595',6595,'QUAKE',NULL," +
		" 'Lansing','Michigan','USA',NULL,2017,NULL)," +
		"('frc6596',6596,'Ashley BearBots',NULL," +
		" 'Ashley','Michigan','USA',NULL,2017,NULL)," +
		"('frc6597',6597,'Eagles',NULL," +
		" 'Saginaw','Michigan','USA',NULL,2017,NULL)," +
		"('frc6599',6599,'Northport Wildcats',NULL," +
		" 'Northport','Michigan','USA',NULL,2017,NULL)," +
		"('frc6600',6600,'Tech Heads',NULL," +
		" 'Baldwin','Michigan','USA',NULL,2017,NULL)," +
		"('frc6601',6601,'Easy Company',NULL," +
		" 'Staten Island','New York','USA',NULL,2017,NULL)," +
		"('frc6602',6602,'Belle Bots',NULL," +
		" 'Little Rock','Arkansas','USA',NULL,2017,NULL)," +
		"('frc6603',6603,'Cornerstone Crushers',NULL," +
		" 'Weyburn','Saskatchewan','Canada',NULL,2017,NULL)," +
		"('frc6604',6604,'Tecmilenio - Equipo ITAC - 6604',NULL," +
		" 'CULIACAN','Sinaloa','Mexico',NULL,2017,NULL)," +
		"('frc6605',6605,'Tecmilenio - Synergy - 6605',NULL," +
		" 'Cuernavaca','Morelos','Mexico',NULL,2017,NULL)," +
		"('frc6606',6606,'Tecmilenio PINK HAWKS 6606',NULL," +
		" 'Metepec','Mexico','Mexico',NULL,2017,NULL)," +
		"('frc6607',6607,'Tecmilenio - ARMY ROBOT TECHNOLOGY - 6607',NULL," +
		" 'Veracruz','Veracruz','Mexico',NULL,2017,NULL)," +
		"('frc6608',6608,'Tecmilenio ARCADIA 6608',NULL," +
		" 'Tlaquepaque','Jalisco','Mexico',NULL,2017,NULL)," +
		"('frc6609',6609,'Suwannee Bulldogs','http://Suwannee Robotics  (facebook)'," +
		" 'Live Oak','Florida','USA',NULL,2017,NULL)," +
		"('frc6610',6610,'Robot Roll Call',NULL," +
		" 'Burton','Michigan','USA',NULL,2017,NULL)," +
		"('frc6611',6611,'WolverBots',NULL," +
		" 'Au Gres','Michigan','USA',NULL,2017,NULL)," +
		"('frc6612',6612,'NexGen Robotics','https://www.dvhsrobotics.org/'," +
		" 'Antioch','California','USA',NULL,2017,NULL)," +
		"('frc6613',6613,'Gogebic Range Robotics',NULL," +
		" 'Hurley','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6614',6614,'ROBOTEDIST','http://www.robotedist.org'," +
		" 'İSTANBUL','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6615',6615,'Belle Voxel - Bots',NULL," +
		" 'Belleville','Michigan','USA',NULL,2017,NULL)," +
		"('frc6616',6616,'Mechanical Pumas','https://mechanicalpumas6616.wixsite.com/website'," +
		" 'Detroit','Michigan','USA',NULL,2017,NULL)," +
		"('frc6617',6617,'NASA BHS Black Hawks Robotics Club',NULL," +
		" 'Bellingham','Massachusetts','USA',NULL,2017,NULL)," +
		"('frc6618',6618,'WARHBOTS',NULL," +
		" 'Flat Rock','Michigan','USA',NULL,2017,NULL)," +
		"('frc6619',6619,'GravitechX','http://www.gravitechx.org/'," +
		" 'Livermore','California','USA',NULL,2017,NULL)," +
		"('frc6620',6620,'The Northmengineers','https://sites.google.com/site/roboticsinnshs/home'," +
		" 'Slatersville','Rhode Island','USA',NULL,2017,NULL)," +
		"('frc6621',6621,'Artemis Robotics ','https://sites.google.com/chatham.k12.ny.us/fischer'," +
		" 'Chatham','New York','USA',NULL,2017,NULL)," +
		"('frc6622',6622,'Stan Robotix',NULL," +
		" 'Outremont','Québec','Canada',NULL,2017,NULL)," +
		"('frc6623',6623,'Robot Maroc',NULL," +
		" 'Meknès','Meknès','Morocco',NULL,2017,NULL)," +
		"('frc6624',6624,'Marinette RoboMarines',NULL," +
		" 'Marinette','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6625',6625,'İEA Robotics',NULL," +
		" 'İstanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6626',6626,'Robohana',NULL," +
		" 'Andrews','South Carolina','USA',NULL,2017,NULL)," +
		"('frc6627',6627,'Niles Nordic Knights','http://www.nordic-knights.com'," +
		" 'Niles','Michigan','USA',NULL,2017,NULL)," +
		"('frc6628',6628,'KMS BOTKICKERS',NULL," +
		" 'Kerkhoven','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6629',6629,'Concord Boyne Kestrels',NULL," +
		" 'Boyne City','Michigan','USA',NULL,2017,NULL)," +
		"('frc6630',6630,'F.U.N.  (Fiercely Uknighted Nation)','http://6630fun.weebly.com/'," +
		" 'La Porte City','Iowa','USA',NULL,2017,NULL)," +
		"('frc6631',6631,'Rocket Robotics',NULL," +
		" 'Reese','Michigan','USA',NULL,2017,NULL)," +
		"('frc6632',6632,'Supreme Robotics','http://robotics.northviewheights.ca'," +
		" 'Toronto','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6633',6633,'Robo-Elephants',NULL," +
		" 'Detroit','Michigan','USA',NULL,2017,NULL)," +
		"('frc6634',6634,'Tecmilenio Phoenix Forces 6634','https://www.facebook.com/6634phoenixforces'," +
		" 'San Nicolás de los Garza','Nuevo León','Mexico',NULL,2017,NULL)," +
		"('frc6635',6635,'JaXon Falcon Works',NULL," +
		" 'Jackson','Michigan','USA',NULL,2017,NULL)," +
		"('frc6636',6636,'Full Metal Beavers','https://www.team6636.com'," +
		" 'Jamaica','New York','USA',NULL,2017,NULL)," +
		"('frc6637',6637,'BetaWolves',NULL," +
		" 'Carney','Michigan','USA',NULL,2017,NULL)," +
		"('frc6638',6638,'Fulton Pirates',NULL," +
		" 'Middleton','Michigan','USA',NULL,2017,NULL)," +
		"('frc6639',6639,'The Mechanical Minds ','https://www.themechanicalminds.com'," +
		" 'Hickory','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6640',6640,'Metal Eagles','https://www.facebook.com/metaleagles/'," +
		" 'Mayflower','Arkansas','USA',NULL,2017,NULL)," +
		"('frc6641',6641,'GSLRobotX',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6642',6642,'Harrison Stingers of Steel',NULL," +
		" 'Harrison','Michigan','USA',NULL,2017,NULL)," +
		"('frc6643',6643,'Walnuts and Bolts','http://www.team6643.org/'," +
		" 'Racine','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6644',6644,'Atomic Automatons',NULL," +
		" 'Rocklin','California','USA',NULL,2017,NULL)," +
		"('frc6645',6645,'Alloy Obsession',NULL," +
		" 'Crosby','Texas','USA',NULL,2017,NULL)," +
		"('frc6646',6646,'Belton Robotics Spacemakers',NULL," +
		" 'Belton','Texas','USA',NULL,2017,NULL)," +
		"('frc6647',6647,'PrepaTec - VOLTEC Robotics','http://voltec6647.com'," +
		" 'MONTERREY','Nuevo León','Mexico',NULL,2017,NULL)," +
		"('frc6648',6648,'MechaSpartans',NULL," +
		" 'Ozone Park','New York','USA',NULL,2017,NULL)," +
		"('frc6649',6649,'CHA Circuits ',NULL," +
		" 'Saint Helen','Michigan','USA',NULL,2017,NULL)," +
		"('frc6650',6650,'Tribebots',NULL," +
		" 'Fresno','California','USA',NULL,2017,NULL)," +
		"('frc6651',6651,'Golden Bots',NULL," +
		" 'Sterling','Illinois','USA',NULL,2017,NULL)," +
		"('frc6652',6652,'TigreRobotics','http://www.tigrerobotics.com'," +
		" 'Monterrey','Nuevo León','Mexico',NULL,2017,NULL)," +
		"('frc6653',6653,'West Wyalong',NULL," +
		" 'West Wyalong','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6654',6654,'Central Robobcatics',NULL," +
		" 'Knoxville','Tennessee','USA',NULL,2017,NULL)," +
		"('frc6655',6655,'Texan Robotics',NULL," +
		" 'Arlington','Texas','USA',NULL,2017,NULL)," +
		"('frc6656',6656,'Ryu Botics',NULL," +
		" 'Payson','Arizona','USA',NULL,2017,NULL)," +
		"('frc6657',6657,'Arborbotics','http://team6657.weebly.com/'," +
		" 'Visalia','California','USA',NULL,2017,NULL)," +
		"('frc6658',6658,'Rockbotics',NULL," +
		" 'Los Angeles','California','USA',NULL,2017,NULL)," +
		"('frc6659',6659,'CouBots',NULL," +
		" 'Escondido','California','USA',NULL,2017,NULL)," +
		"('frc6660',6660,'MarkerBots','http://stem.nerinxhs.org'," +
		" 'Saint Louis','Missouri','USA',NULL,2017,NULL)," +
		"('frc6661',6661,'Polaris',NULL," +
		" 'North York','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6662',6662,'FalconX','http://falconxrobotics.com'," +
		" 'Pleasant Hill','California','USA',NULL,2017,NULL)," +
		"('frc6663',6663,'Kamikaze Krew',NULL," +
		" 'Onamia','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6664',6664,'LADYBOTS',NULL," +
		" 'Pharr','Texas','USA',NULL,2017,NULL)," +
		"('frc6665',6665,'Nuns and Bolts',NULL," +
		" 'Monterey','California','USA',NULL,2017,NULL)," +
		"('frc6666',6666,'Tecmilenio- Regiobots -6666','http://www.regiobots.mx'," +
		" 'Guadalupe','Nuevo León','Mexico',NULL,2017,NULL)," +
		"('frc6667',6667,'STEM Clippers',NULL," +
		" 'Chester','Pennsylvania','USA',NULL,2017,NULL)," +
		"('frc6668',6668,'Scorpion ( The Scorpions) ',NULL," +
		" 'Hesperia','California','USA',NULL,2017,NULL)," +
		"('frc6669',6669,'ATL Eagles Robotics',NULL," +
		" 'Delray Beach','Florida','USA',NULL,2017,NULL)," +
		"('frc6670',6670,'Red Cat Robots',NULL," +
		" 'Milwaukee','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6671',6671,'Adamas Robotics',NULL," +
		" 'Fort Worth','Texas','USA',NULL,2017,NULL)," +
		"('frc6672',6672,'Fusion Corps',NULL," +
		" 'Irving','Texas','USA',NULL,2017,NULL)," +
		"('frc6673',6673,'Alexis Tech Warriors',NULL," +
		" 'Glenevis','Alberta','Canada',NULL,2017,NULL)," +
		"('frc6674',6674,'MIT Iron Eagles',NULL," +
		" 'Phoenix','Arizona','USA',NULL,2017,NULL)," +
		"('frc6675',6675,'Ingenious Innovators',NULL," +
		" 'New Britain','Connecticut','USA',NULL,2017,NULL)," +
		"('frc6676',6676,'Tecmilenio - Thunder Hawks - 6676',NULL," +
		" 'Obregón','Sonora','Mexico',NULL,2017,NULL)," +
		"('frc6677',6677,'Team Avengers',NULL," +
		" 'Pascagoula','Mississippi','USA',NULL,2017,NULL)," +
		"('frc6678',6678,'DKRobotics',NULL," +
		" 'Svendborg','South Denmark','Denmark',NULL,2017,NULL)," +
		"('frc6679',6679,'TESLA Robotics','http://www.18tesla.ca'," +
		" 'Calgary','Alberta','Canada',NULL,2017,NULL)," +
		"('frc6680',6680,'TRIObotics',NULL," +
		" 'Pocatello','Idaho','USA',NULL,2017,NULL)," +
		"('frc6681',6681,'FHC Warriors',NULL," +
		" 'Whitehorse','Yukon Territory','Canada',NULL,2017,NULL)," +
		"('frc6682',6682,' A.S.T.R.O. Vikings','https://www.facebook.com/TeamOutreach6682/'," +
		" 'Alamogordo','New Mexico','USA',NULL,2017,NULL)," +
		"('frc6683',6683,'The ROAR-Botics',NULL," +
		" 'Glendale','Arizona','USA',NULL,2017,NULL)," +
		"('frc6684',6684,'RoboCats','https://sites.google.com/a/carrizospringscisd.net/robocats/system/app/pages/sitemap/hierarchy'," +
		" 'Carrizo Springs','Texas','USA',NULL,2017,NULL)," +
		"('frc6685',6685,'Amped Up','http://robotics.rpbhs.org'," +
		" 'West Palm Beach','Florida','USA',NULL,2017,NULL)," +
		"('frc6686',6686,'Bionic Broncos',NULL," +
		" 'West Palm Beach','Florida','USA',NULL,2017,NULL)," +
		"('frc6687',6687,'Owltronics',NULL," +
		" 'Garland','Texas','USA',NULL,2017,NULL)," +
		"('frc6688',6688,'West Valley Middle College Robotics Team',NULL," +
		" 'San Jose','California','USA',NULL,2017,NULL)," +
		"('frc6689',6689,'Robit',NULL," +
		" 'Mexico City ','Distrito Federal','Mexico',NULL,2017,NULL)," +
		"('frc6690',6690,'MV roboPride',NULL," +
		" 'Concord','New Hampshire','USA',NULL,2017,NULL)," +
		"('frc6691',6691,'Torque',NULL," +
		" 'Wolfeboro','New Hampshire','USA',NULL,2017,NULL)," +
		"('frc6692',6692,'Robofalcons','http://science.aquinashs.net/robotics'," +
		" 'San Bernardino','California','USA',NULL,2017,NULL)," +
		"('frc6693',6693,'TrailBLAZERS',NULL," +
		" 'Columbia','South Carolina','USA',NULL,2017,NULL)," +
		"('frc6694',6694,'INNOBOTICS','https://www.facebook.com/Innobotics-FIRST-Robotics-1708905749429805/?fref=ts'," +
		" 'Mexicali','Baja California','Mexico',NULL,2017,NULL)," +
		"('frc6695',6695,'Alpha Knights','http://www.alphaknights.net'," +
		" 'San Marcos','California','USA',NULL,2017,NULL)," +
		"('frc6696',6696,'Cardinal Dynamics','https://corbett.k12.or.us/robotics/'," +
		" 'Corbett','Oregon','USA',NULL,2017,NULL)," +
		"('frc6697',6697,'EUROBOTICS','http://Eurobotics.weebly.com'," +
		" 'İSTANBUL','Istanbul','Turkey',NULL,2017,NULL)," +
		"('frc6699',6699,'Thunder Robotics',NULL," +
		" 'Atwater','California','USA',NULL,2017,NULL)," +
		"('frc6700',6700,'Patriot Robotics',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2017,NULL)," +
		"('frc6701',6701,'Phoenix ',NULL," +
		" 'Moose Jaw','Saskatchewan','Canada',NULL,2017,NULL)," +
		"('frc6702',6702,'PrepaTec - StingBots','http://www.stingbots.com'," +
		" 'Tlajomulco de Zuñiga','Jalisco','Mexico',NULL,2017,NULL)," +
		"('frc6703',6703,'Prince George Secondary School Robotics Club',NULL," +
		" 'Prince George','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6704',6704,'Mid Pacific Owl Robotics',NULL," +
		" 'Honolulu','Hawaii','USA',NULL,2017,NULL)," +
		"('frc6705',6705,'WildCat 5e','http://www.wildcat5e.com'," +
		" 'Atlanta','Georgia','USA',NULL,2017,NULL)," +
		"('frc6706',6706,'GOLEM','http://www.team6706.com'," +
		" 'Shenzhen','Guangdong','China',NULL,2017,NULL)," +
		"('frc6707',6707,'RCW Jaguar Robosapiens',NULL," +
		" 'Renville','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6708',6708,'Lobos Conalep','http://www.conalepmex.edu.mx'," +
		" 'Almoloya del Rio','Mexico','Mexico',NULL,2017,NULL)," +
		"('frc6709',6709,'Spudinc','https://biglakerobotics.wix.com/mysite'," +
		" 'Big Lake','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6710',6710,'Southside Robotics',NULL," +
		" 'Atlanta','Georgia','USA',NULL,2017,NULL)," +
		"('frc6711',6711,'Millennial Falcons',NULL," +
		" 'Atwater','California','USA',NULL,2017,NULL)," +
		"('frc6712',6712,'Mountaineers',NULL," +
		" 'Chatsworth','Georgia','USA',NULL,2017,NULL)," +
		"('frc6714',6714,'A. R. Johnson''s Panthers',NULL," +
		" 'Augusta','Georgia','USA',NULL,2017,NULL)," +
		"('frc6715',6715,'HZB Robotics',NULL," +
		" 'Hephzibah','Georgia','USA',NULL,2017,NULL)," +
		"('frc6716',6716,'Circuit Br8kers',NULL," +
		" 'Augusta','Georgia','USA',NULL,2017,NULL)," +
		"('frc6717',6717,'SPARTANS',NULL," +
		" 'Castle Dale','Utah','USA',NULL,2017,NULL)," +
		"('frc6718',6718,'Rocket Dogs',NULL," +
		" 'Oakland','California','USA',NULL,2017,NULL)," +
		"('frc6719',6719,'P.O.W.E.R.',NULL," +
		" 'Vancouver','British Columbia','Canada',NULL,2017,NULL)," +
		"('frc6720',6720,'Walker-Hackensack-Akeley',NULL," +
		" 'Walker','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6721',6721,'Tindley Trailblazers','http://trailblazerrobotics.org'," +
		" 'Indianapolis','Indiana','USA',NULL,2017,NULL)," +
		"('frc6722',6722,'L.E.D.s Robotics','http://www.seahawkmetal.com'," +
		" 'New Port Richey','Florida','USA',NULL,2017,NULL)," +
		"('frc6723',6723,'Mechanical Mounties','https://sites.google.com/mgrhs.org/robotics'," +
		" 'Williamstown','Massachusetts','USA',NULL,2017,NULL)," +
		"('frc6724',6724,'Raiders Robotics',NULL," +
		" 'Red Deer','Alberta','Canada',NULL,2017,NULL)," +
		"('frc6725',6725,'Westminster WildBOTS','https://wildbots.wordpress.com/'," +
		" 'London','Ontario','Canada',NULL,2017,NULL)," +
		"('frc6726',6726,'Warren County School Eagles',NULL," +
		" 'Warrenton','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6727',6727,'HOT Bots','http://www.beltonrobotics.com'," +
		" 'Belton','Texas','USA',NULL,2017,NULL)," +
		"('frc6728',6728,'The Fast and the Curious',NULL," +
		" 'Morrilton','Arkansas','USA',NULL,2017,NULL)," +
		"('frc6729',6729,'RobCoBots',NULL," +
		" 'Lumberton','North Carolina','USA',NULL,2017,NULL)," +
		"('frc6730',6730,'CCS Nuclear Knights',NULL," +
		" 'Oklahoma City','Oklahoma','USA',NULL,2017,NULL)," +
		"('frc6731',6731,'Record Robotics ','https://recordrobotics.org'," +
		" 'Belmont','Massachusetts','USA',NULL,2017,NULL)," +
		"('frc6732',6732,'BHS RoboRaiders',NULL," +
		" 'Bruce','Wisconsin','USA',NULL,2017,NULL)," +
		"('frc6733',6733,'PSJA North Raiders',NULL," +
		" 'Pharr','Texas','USA',NULL,2017,NULL)," +
		"('frc6734',6734,'Sydney TAFE',NULL," +
		" 'Sydney','New South Wales','Australia',NULL,2017,NULL)," +
		"('frc6735',6735,'Rauner College Prep',NULL," +
		" 'Chicago','Illinois','USA',NULL,2017,NULL)," +
		"('frc6736',6736,'Knights of the Sea',NULL," +
		" 'AKKO','HaZafon','Israel',NULL,2017,NULL)," +
		"('frc6737',6737,'H.R.T',NULL," +
		" 'HAIFA','Haifa','Israel',NULL,2017,NULL)," +
		"('frc6738',6738,'Excalibur',NULL," +
		" 'Modi''in','HaMerkaz','Israel',NULL,2017,NULL)," +
		"('frc6739',6739,'Tel Shevaa',NULL," +
		" 'tel sheva','HaDarom','Israel',NULL,2017,NULL)," +
		"('frc6740',6740,'G3 - Glue Gun & Glitter','https://www.g3-6740.org/'," +
		" 'Pardes Hana','Haifa','Israel',NULL,2017,NULL)," +
		"('frc6741',6741,'Space monkeys',NULL," +
		" 'rishon le tzion','Tel-Aviv','Israel',NULL,2017,NULL)," +
		"('frc6742',6742,'Blue Jays',NULL," +
		" 'Westland','Michigan','USA',NULL,2017,NULL)," +
		"('frc6743',6743,'Tiger Engineering','http://tiger-engineering.weebly.com'," +
		" 'Fort Myers','Florida','USA',NULL,2017,NULL)," +
		"('frc6744',6744,'Mehville Pi',NULL," +
		" 'Saint Louis','Missouri','USA',NULL,2017,NULL)," +
		"('frc6745',6745,'DAPCEP',NULL," +
		" 'Detroit','Michigan','USA',NULL,2017,NULL)," +
		"('frc6746',6746,'Mustangs',NULL," +
		" 'Syosset','New York','USA',NULL,2017,NULL)," +
		"('frc6747',6747,'East Jefferson High School',NULL," +
		" 'Metairie','Louisiana','USA',NULL,2017,NULL)," +
		"('frc6748',6748,'MustangRobotics',NULL," +
		" 'Mesa','Arizona','USA',NULL,2017,NULL)," +
		"('frc6749',6749,'tERAbytes','https://team6749.com/'," +
		" 'Hopkins','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6750',6750,'HenBotix',NULL," +
		" 'McDonough','Georgia','USA',NULL,2017,NULL)," +
		"('frc6751',6751,'RoboFlash','http://www.dallasisinnovates.com'," +
		" 'Dallas','Texas','USA',NULL,2017,NULL)," +
		"('frc6752',6752,'charger robotics','http://chargerroboticsclub.weebly.com/'," +
		" 'Keller','Texas','USA',NULL,2017,NULL)," +
		"('frc6753',6753,'CBA RoboKings',NULL," +
		" 'Midland','Michigan','USA',NULL,2017,NULL)," +
		"('frc6754',6754,'Trailbots ',NULL," +
		" 'Edinburg','Texas','USA',NULL,2017,NULL)," +
		"('frc6755',6755,'Overdrive',NULL," +
		" 'Fontana','California','USA',NULL,2017,NULL)," +
		"('frc6756',6756,'Cross Creek Razorbacks',NULL," +
		" 'Augusta','Georgia','USA',NULL,2017,NULL)," +
		"('frc6757',6757,'Apex Robotics FPHS',NULL," +
		" 'Forest Park','Georgia','USA',NULL,2017,NULL)," +
		"('frc6758',6758,'Otternauts ','http://kmrobotics.doodlekit.com'," +
		" 'Kasson','Minnesota','USA',NULL,2017,NULL)," +
		"('frc6759',6759,'Rebel',NULL," +
		" 'Pearl River','Louisiana','USA',NULL,2017,NULL)," +
		"('frc6762',6762,'Oscats',NULL," +
		" 'Hillsborough','New Hampshire','USA',NULL,2017,NULL)," +
		"('frc6763',6763,'FUSION','http://fusion6763.org/'," +
		" 'Manchester','New Hampshire','USA',NULL,2017,NULL)," +
		"('frc6764',6764,'Flash Drives','https://sites.google.com/view/fillmorerobotics/home'," +
		" 'Fillmore','California','USA',NULL,2017,NULL)," +
		"('frc6765',6765,'Columbus City Schools Walnut Ridge Scotts',NULL," +
		" 'Columbus','Ohio','USA',NULL,2017,NULL)," +
		"('frc6766',6766,'Pharma Atom Storm',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2017,NULL)," +
		"('frc6768',6768,'Denison Robo-Jackets','http://Denisonengineering.com'," +
		" 'Denison','Texas','USA',NULL,2017,NULL)," +
		"('frc6769',6769,'Future Star',NULL," +
		" 'Beijing','Beijing','China',NULL,2017,NULL)," +
		"('frc6770',6770,'Semia United',NULL," +
		" 'Beijing','Beijing','China',NULL,2017,NULL)," +
		"('frc6771',6771,'Raven',NULL," +
		" 'Beijing','Beijing','China',NULL,2017,NULL)," +
		"('frc6800',6800,'ViperBots Valor','http://viperbotsvalor6800.com/'," +
		" 'Austin','Texas','USA',NULL,2018,NULL)," +
		"('frc6801',6801,'Robot Crabshell ','http://www.crabshell.cc'," +
		" 'Guangzhou','Guangdong','China',NULL,2018,NULL)," +
		"('frc6802',6802,'Mean Caimans','http://www.facebook.com/frc6802'," +
		" 'Cedar Bluff','Virginia','USA',NULL,2018,NULL)," +
		"('frc6803',6803,'HAI-Panda',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc6804',6804,'Team Wolfpack',NULL," +
		" 'Livingston','California','USA',NULL,2018,NULL)," +
		"('frc6805',6805,'Cyber Jays','http://cprobotics.tk'," +
		" 'Omaha','Nebraska','USA',NULL,2018,NULL)," +
		"('frc6806',6806,'Mineola Wild Reds',NULL," +
		" 'Mineola','New York','USA',NULL,2018,NULL)," +
		"('frc6807',6807,'REAL Robotics',NULL," +
		" 'Racine','Wisconsin','USA',NULL,2018,NULL)," +
		"('frc6808',6808,'William Tennent Robotics','http://wtrobotics@centennialsd.org'," +
		" 'Warminster','Pennsylvania','USA',NULL,2018,NULL)," +
		"('frc6809',6809,'Ace Robotics ','http://Autrytech.edu'," +
		" 'Enid','Oklahoma','USA',NULL,2018,NULL)," +
		"('frc6810',6810,'Starship Robotics',NULL," +
		" 'Maringouin','Louisiana','USA',NULL,2018,NULL)," +
		"('frc6811',6811,'Gunnery Gears',NULL," +
		" 'Washington','Connecticut','USA',NULL,2018,NULL)," +
		"('frc6812',6812,'Andover Snowflakes',NULL," +
		" 'Andover','Massachusetts','USA',NULL,2018,NULL)," +
		"('frc6813',6813,'Pangaea','http://robotics.asbindia.org'," +
		" 'Mumbai','Maharashtra','India',NULL,2018,NULL)," +
		"('frc6814',6814,'Ellipse ',NULL," +
		" 'Santa Rosa','California','USA',NULL,2018,NULL)," +
		"('frc6815',6815,'The Memphis MechWarriors',NULL," +
		" 'Memphis','Tennessee','USA',NULL,2018,NULL)," +
		"('frc6816',6816,'POPCORN PENGUINS',NULL," +
		" 'San Jose','California','USA',NULL,2018,NULL)," +
		"('frc6817',6817,'10 Factorial','https://www.10factorial.org'," +
		" 'Rolla','Missouri','USA',NULL,2018,NULL)," +
		"('frc6818',6818,'Navigator','http://www.robonavigator.cn'," +
		" 'Suzhou','Jiangsu','China',NULL,2018,NULL)," +
		"('frc6819',6819,'Viking Robotics','http://www.vaxjo.se/Teknikum---startsida/'," +
		" 'Växjö','Kronobergs län','Sweden',NULL,2018,NULL)," +
		"('frc6820',6820,'Tecmilenio - Scorpion Bots - 6820','https://www.facebook.com/Scorpion-BOTS-1564563253565871/'," +
		" 'DURANGO','Durango','Mexico',NULL,2018,NULL)," +
		"('frc6821',6821,'Rogue Techs','http://Roguetechs.org'," +
		" 'Mira Loma','California','USA',NULL,2018,NULL)," +
		"('frc6822',6822,'THE SHISHKABOTS',NULL," +
		" 'San Jose','California','USA',NULL,2018,NULL)," +
		"('frc6823',6823,'Wildcats',NULL," +
		" 'Milwaukee','Wisconsin','USA',NULL,2018,NULL)," +
		"('frc6824',6824,'LVA Robot Pigeons',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2018,NULL)," +
		"('frc6825',6825,'The Stronghorns','http://www.legacyrobotics.net'," +
		" 'North Las Vegas','Nevada','USA',NULL,2018,NULL)," +
		"('frc6826',6826,'Experiment 6826',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2018,NULL)," +
		"('frc6827',6827,'hexagon',NULL," +
		" 'Beijing','Beijing','China',NULL,2018,NULL)," +
		"('frc6828',6828,'Hazel Green',NULL," +
		" 'Meridianville','Alabama','USA',NULL,2018,NULL)," +
		"('frc6829',6829,'Ignite Robotics','http://www.igniterobotics.com'," +
		" 'Suwanee','Georgia','USA',NULL,2018,NULL)," +
		"('frc6830',6830,'Hogbotics',NULL," +
		" 'Pharr','Texas','USA',NULL,2018,NULL)," +
		"('frc6831',6831,' A-05 Annex','http://a05annex.org'," +
		" 'Hood River','Oregon','USA',NULL,2018,NULL)," +
		"('frc6832',6832,'STEAMex','https://www.facebook.com/STEAMex6832/'," +
		" 'San Pedro Garza García','Nuevo León','Mexico',NULL,2018,NULL)," +
		"('frc6833',6833,'Python Robotics','http://www.pythonrobotics.org'," +
		" 'Phoenix','Arizona','USA',NULL,2018,NULL)," +
		"('frc6834',6834,'Bionic Warriors','http://robots.swl.k12.oh.us'," +
		" 'Etna','Ohio','USA',NULL,2018,NULL)," +
		"('frc6835',6835,'Steel Satyr','http://www.orionfrc.esy.es/'," +
		" 'Puebla','Puebla','Mexico',NULL,2018,NULL)," +
		"('frc6836',6836,'The Tinkerers',NULL," +
		" 'Manly','New South Wales','Australia',NULL,2018,NULL)," +
		"('frc6837',6837,'Tecmilenio - Bellum - 6837',NULL," +
		" 'Azcapotzalco','Distrito Federal','Mexico',NULL,2018,NULL)," +
		"('frc6838',6838,'X-SHARC',NULL," +
		" 'ÇEKMEKÖY','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc6839',6839,'Terminators','https://www.teamterminators.org'," +
		" 'Karditsa','Thessalia','Greece',NULL,2018,NULL)," +
		"('frc6840',6840,'Trojan Robotics',NULL," +
		" 'Kirby','Arkansas','USA',NULL,2018,NULL)," +
		"('frc6841',6841,'Cerberus 2.0',NULL," +
		" 'Lethbridge','Alberta','Canada',NULL,2018,NULL)," +
		"('frc6842',6842,'Polaris',NULL," +
		" 'Marietta','Georgia','USA',NULL,2018,NULL)," +
		"('frc6843',6843,'Guardian Gears',NULL," +
		" 'Lees Summit','Missouri','USA',NULL,2018,NULL)," +
		"('frc6844',6844,'Provotypes','http://www.provotypes.org'," +
		" 'Provo','Utah','USA',NULL,2018,NULL)," +
		"('frc6845',6845,'River Bots','http://riverbots.com'," +
		" 'Astoria','Oregon','USA',NULL,2018,NULL)," +
		"('frc6846',6846,'MAGNETUDE','http://centralmagbots.weebly.com'," +
		" 'Murfreesboro','Tennessee','USA',NULL,2018,NULL)," +
		"('frc6847',6847,'Les Lions',NULL," +
		" 'Montreal','Québec','Canada',NULL,2018,NULL)," +
		"('frc6848',6848,'Hades',NULL," +
		" 'Puebla','Puebla','Mexico',NULL,2018,NULL)," +
		"('frc6849',6849,'Juventics','http://www.guadalupejoven.gob.mx/'," +
		" 'Guadalupe','Nuevo León','Mexico',NULL,2018,NULL)," +
		"('frc6850',6850,'Cell-Ticks',NULL," +
		" 'Ocala','Florida','USA',NULL,2018,NULL)," +
		"('frc6851',6851,'Empereurs',NULL," +
		" 'Laval','Québec','Canada',NULL,2018,NULL)," +
		"('frc6852',6852,'Bedford Express','http://www.bedfordexpress.com'," +
		" 'Temperance','Michigan','USA',NULL,2018,NULL)," +
		"('frc6853',6853,'JaegersTec',NULL," +
		" 'Tuxtla Gutierrez ','Chiapas','Mexico',NULL,2018,NULL)," +
		"('frc6854',6854,'AB Lucas VIKING Robotics','https://www.facebook.com/ablucasrobotics/'," +
		" 'London','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6855',6855,'Badger Robotics',NULL," +
		" 'Arkadelphia','Arkansas','USA',NULL,2018,NULL)," +
		"('frc6856',6856,'RoboRaptors',NULL," +
		" 'London','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6857',6857,'Mechanized Garbage',NULL," +
		" 'San Fernando','California','USA',NULL,2018,NULL)," +
		"('frc6858',6858,'Fern Creek Tiger Robotics',NULL," +
		" 'Louisville','Kentucky','USA',NULL,2018,NULL)," +
		"('frc6859',6859,'BML Robotics',NULL," +
		" 'Bracebridge','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6860',6860,'Equitum Robotics','https://equitumrobotics.wixsite.com/6860'," +
		" 'Paterson','New Jersey','USA',NULL,2018,NULL)," +
		"('frc6861',6861,'The Tyros Team ','http://www.livoniawarriors.org'," +
		" 'Livonia','Michigan','USA',NULL,2018,NULL)," +
		"('frc6862',6862,'The Reavers',NULL," +
		" 'Detroit','Michigan','USA',NULL,2018,NULL)," +
		"('frc6863',6863,'Cyber Squirrels',NULL," +
		" 'Laurel','Maryland','USA',NULL,2018,NULL)," +
		"('frc6864',6864,'Gravenhurst High School',NULL," +
		" 'Gravenhurst','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6865',6865,'Manitoulin Metal',NULL," +
		" 'M''Chigeeng','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6866',6866,'The Space Invaders',NULL," +
		" 'Markham','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6867',6867,'Panthera Tech','https://jpci6867.com/'," +
		" 'North York','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6868',6868,'Cadet Robotics','http://WWW.Team6868.org'," +
		" 'Hilton','New York','USA',NULL,2018,NULL)," +
		"('frc6869',6869,'Gladiateurs','http://Gladiateurs.in'," +
		" 'Saint-Jerome','Québec','Canada',NULL,2018,NULL)," +
		"('frc6870',6870,'BEAM Robotics','http://www.beambuffalo.org'," +
		" 'Buffalo','New York','USA',NULL,2018,NULL)," +
		"('frc6871',6871,'QC DogSquad','http://www.qchsrobotics.org'," +
		" 'Queen Creek','Arizona','USA',NULL,2018,NULL)," +
		"('frc6872',6872,'PantheraFerro','https://2402261.wixsite.com/ab-pantheraferro'," +
		" 'Brossard','Québec','Canada',NULL,2018,NULL)," +
		"('frc6873',6873,'The Generals',NULL," +
		" 'New York','New York','USA',NULL,2018,NULL)," +
		"('frc6874',6874,'Imperium','http://www.teamimperium.com'," +
		" 'Döşemealtı','Antalya','Turkey',NULL,2018,NULL)," +
		"('frc6875',6875,'Amazon Warriors  -  Build A Dream Robotics','https://buildadreamrobotic.wixsite.com/mysite'," +
		" 'Windsor','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6876',6876,'UHS Wolfpack','http://www.uhsrobotics.org/'," +
		" 'Unionville','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6877',6877,'Mechanical Mustangs',NULL," +
		" 'Grand Rapids','Michigan','USA',NULL,2018,NULL)," +
		"('frc6878',6878,'SJB Odyssey ','http://www.6878.ca'," +
		" 'Hamilton','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6879',6879,'Aquatic Robotics','http://www.aquaticrobotics.org'," +
		" 'Avon','Connecticut','USA',NULL,2018,NULL)," +
		"('frc6880',6880,'Universal Serial Brawlers (USB)',NULL," +
		" 'Yorktown Heights','New York','USA',NULL,2018,NULL)," +
		"('frc6881',6881,'South Lions Roarbotics',NULL," +
		" 'London','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6882',6882,'Fahrenheit Robotics',NULL," +
		" 'Fredericksburg','Virginia','USA',NULL,2018,NULL)," +
		"('frc6883',6883,'Leviathan Robotics','http://Leviaclan.weebly.com'," +
		" 'Santa Rosa','California','USA',NULL,2018,NULL)," +
		"('frc6884',6884,'Deep-Space',NULL," +
		" 'Hollister','California','USA',NULL,2018,NULL)," +
		"('frc6885',6885,'The Pilots',NULL," +
		" 'San Diego','California','USA',NULL,2018,NULL)," +
		"('frc6886',6886,'Synthesizers',NULL," +
		" 'Warsaw','Missouri','USA',NULL,2018,NULL)," +
		"('frc6887',6887,'Dalton Catabots',NULL," +
		" 'Dalton','Georgia','USA',NULL,2018,NULL)," +
		"('frc6888',6888,'Burke County Breakouts (BC Breakouts)','https://bcbreakouts2019.wixsite.com/website'," +
		" 'Hildebran','North Carolina','USA',NULL,2018,NULL)," +
		"('frc6889',6889,'DC Current',NULL," +
		" 'Bloomfield','Iowa','USA',NULL,2018,NULL)," +
		"('frc6890',6890,'Legendary Cyber kNights','https://sites.google.com/lc-ps.net/6890lcn/home'," +
		" 'Macomb','Michigan','USA',NULL,2018,NULL)," +
		"('frc6891',6891,'Indians',NULL," +
		" 'Asher','Oklahoma','USA',NULL,2018,NULL)," +
		"('frc6892',6892,'Big Cat Robotics','https://www.facebook.com/BigCatRobotics6892/'," +
		" 'Wellsville','New York','USA',NULL,2018,NULL)," +
		"('frc6893',6893,'Bladerunners','http://www.maserdc.org'," +
		" 'Greenbelt','Maryland','USA',NULL,2018,NULL)," +
		"('frc6894',6894,'Iced Java',NULL," +
		" 'Mount Holly','North Carolina','USA',NULL,2018,NULL)," +
		"('frc6895',6895,'Mechanical Madness 4-H',NULL," +
		" 'Walpole','New Hampshire','USA',NULL,2018,NULL)," +
		"('frc6896',6896,'CABOTS','https://cabotsadmon18.wixsite.com/cabots6896'," +
		" 'Heroica Caborca','Sonora','Mexico',NULL,2018,NULL)," +
		"('frc6897',6897,'Astraea Robotics','http://www.ebfirst.org/'," +
		" 'East Brunswick','New Jersey','USA',NULL,2018,NULL)," +
		"('frc6898',6898,'The Undergraduate School of Carrots ','https://usocfrc.org'," +
		" 'Plano','Texas','USA',NULL,2018,NULL)," +
		"('frc6899',6899,'Macon Bots FRC','http://www.macon.k12.nc.us/STEM'," +
		" 'Franklin','North Carolina','USA',NULL,2018,NULL)," +
		"('frc6900',6900,'Hoodie-bots',NULL," +
		" 'Maud','Oklahoma','USA',NULL,2018,NULL)," +
		"('frc6901',6901,'Knights Robotics',NULL," +
		" 'Frisco','Texas','USA',NULL,2018,NULL)," +
		"('frc6902',6902,'STRIKE',NULL," +
		" 'FOZ DO IGUAÇU','Paraná','Brazil',NULL,2018,NULL)," +
		"('frc6903',6903,'MEXICAN HOPE',NULL," +
		" 'METEPEC','Mexico','Mexico',NULL,2018,NULL)," +
		"('frc6904',6904,'TeraWatts',NULL," +
		" 'Los Angeles','California','USA',NULL,2018,NULL)," +
		"('frc6905',6905,'TEAR-A-BYTE',NULL," +
		" 'Alpharetta','Georgia','USA',NULL,2018,NULL)," +
		"('frc6906',6906,'Cristo Rey Reybots',NULL," +
		" 'Chicago','Illinois','USA',NULL,2018,NULL)," +
		"('frc6907',6907,'The G.O.A.T',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc6908',6908,'Infuzed','http://www.inspirenc.us/frc-6908'," +
		" 'Cary','North Carolina','USA',NULL,2013,NULL)," +
		"('frc6909',6909,'SAKURA Tempesta','https://sakura-tempesta.org/'," +
		" 'Chiba','Chiba','Japan',NULL,2018,NULL)," +
		"('frc6910',6910,'Steel Talons','http://Mcfirsthawks.weebly.com'," +
		" 'Hoschton','Georgia','USA',NULL,2018,NULL)," +
		"('frc6911',6911,'SO BOTZ','http://blogs.socsd.org/botz'," +
		" 'Orangeburg','New York','USA',NULL,2018,NULL)," +
		"('frc6912',6912,'Intergalactic Transformers',NULL," +
		" 'Saint Paul','Minnesota','USA',NULL,2018,NULL)," +
		"('frc6913',6913,'North Crawford Robotics Club',NULL," +
		" 'Soldiers Grove','Wisconsin','USA',NULL,2018,NULL)," +
		"('frc6914',6914,'Retro5ive','http://www.retro5ive.com'," +
		" 'Melvindale','Michigan','USA',NULL,2018,NULL)," +
		"('frc6915',6915,'Bulldogs','http://www.oakhillsbulldogs.com/'," +
		" 'Hesperia','California','USA',NULL,2018,NULL)," +
		"('frc6916',6916,'Iron Thunder','https://vinitasingh0.wixsite.com/mysite'," +
		" 'Columbus','Ohio','USA',NULL,2018,NULL)," +
		"('frc6917',6917,'Voyageur Robotics',NULL," +
		" 'Atikokan','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6918',6918,'Cellar Rats',NULL," +
		" 'Napa','California','USA',NULL,2018,NULL)," +
		"('frc6919',6919,'The Commodores','https://www.4cacademy.org/first-robotics-competition/'," +
		" 'Albany','Georgia','USA',NULL,2018,NULL)," +
		"('frc6920',6920,'Force Fusion',NULL," +
		" 'Eindhoven','Noord-Brabant','Netherlands',NULL,2018,NULL)," +
		"('frc6921',6921,'Technados','http://www.pennsauken.ccts.org/'," +
		" 'Pennsauken','New Jersey','USA',NULL,2018,NULL)," +
		"('frc6922',6922,'Apache Genesis',NULL," +
		" 'Nogales','Arizona','USA',NULL,2018,NULL)," +
		"('frc6923',6923,'Royal Blue Robotics [RB]^2',NULL," +
		" 'Saint Francisville','Louisiana','USA',NULL,2018,NULL)," +
		"('frc6924',6924,'Hogarth Hornets',NULL," +
		" 'Markham','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6925',6925,'W.A.Robotics','https://www.team6925.com'," +
		" 'College Park','Georgia','USA',NULL,2018,NULL)," +
		"('frc6926',6926,'RobotiCats',NULL," +
		" 'Clovis','California','USA',NULL,2018,NULL)," +
		"('frc6927',6927,'The Franklinton Firebirds','http://franklintonprep.org'," +
		" 'Columbus','Ohio','USA',NULL,2018,NULL)," +
		"('frc6928',6928,'Mad Llama Robotics',NULL," +
		" 'Greeneville','Tennessee','USA',NULL,2018,NULL)," +
		"('frc6929',6929,'Cuivre & Or','http://frc.cuivreetor.ca'," +
		" 'Rouyn-Noranda','Québec','Canada',NULL,2018,NULL)," +
		"('frc6930',6930,'Hill Robotics',NULL," +
		" 'Staten Island','New York','USA',NULL,2018,NULL)," +
		"('frc6931',6931,'The Red Company ',NULL," +
		" 'Tucson','Arizona','USA',NULL,2018,NULL)," +
		"('frc6932',6932,'S.M.A.R.T. (Smoky Mountain Academic Robotics Team)','https://smokymountainrobot.weebly.com'," +
		" 'Sylva','North Carolina','USA',NULL,2018,NULL)," +
		"('frc6933',6933,'Archytas, sponsored by NASA','https://www.archytas.org'," +
		" 'Sharon','Vermont','USA',NULL,2018,NULL)," +
		"('frc6934',6934,'Scorps',NULL," +
		" 'Camarillo','California','USA',NULL,2018,NULL)," +
		"('frc6935',6935,'Ingram Tom Moore RoboWarriors',NULL," +
		" 'Ingram','Texas','USA',NULL,2018,NULL)," +
		"('frc6936',6936,'Vault 6936','http://vault6936.com'," +
		" 'Dayton','Ohio','USA',NULL,2018,NULL)," +
		"('frc6937',6937,'McCallie Robotics',NULL," +
		" 'Chattanooga','Tennessee','USA',NULL,2018,NULL)," +
		"('frc6938',6938,'STEM3 West Robotics',NULL," +
		" 'Los Angeles','California','USA',NULL,2018,NULL)," +
		"('frc6939',6939,'The Houde Academy',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2018,NULL)," +
		"('frc6940',6940,'Violet Z',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc6941',6941,'IronPulse Robotics',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc6942',6942,'SAIE',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2018,NULL)," +
		"('frc6943',6943,'Blue Bears',NULL," +
		" 'Newark','New Jersey','USA',NULL,2018,NULL)," +
		"('frc6944',6944,'Wolverines',NULL," +
		" 'Cumming','Georgia','USA',NULL,2018,NULL)," +
		"('frc6945',6945,'Children of the Corn','https://sites.google.com/northwarren.org/robotics/home'," +
		" 'Blairstown','New Jersey','USA',NULL,2018,NULL)," +
		"('frc6946',6946,'F & E',NULL," +
		" 'Kunming','Yunnan','China',NULL,2018,NULL)," +
		"('frc6947',6947,'Savage Tumaz','https://www.facebook.com/savagetumaz'," +
		" 'Taipei','Taipei','Chinese Taipei',NULL,2018,NULL)," +
		"('frc6948',6948,'EAGLES','http://www.eagles6948.com'," +
		" 'Kartal','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc6949',6949,'The Smurfs','http://WeChat Official Account : NJXGFIRST'," +
		" 'Nanjing','Jiangsu','China',NULL,2018,NULL)," +
		"('frc6950',6950,'The Breakfast Club',NULL," +
		" 'Stuart','Iowa','USA',NULL,2018,NULL)," +
		"('frc6951',6951,'Spartans',NULL," +
		" 'Porter','Texas','USA',NULL,2018,NULL)," +
		"('frc6952',6952,'Vector Machines','http://www.frc6952.com'," +
		" 'Shenzhen','Guangdong','China',NULL,2018,NULL)," +
		"('frc6953',6953,'Quetzals',NULL," +
		" 'Brossard','Québec','Canada',NULL,2018,NULL)," +
		"('frc6954',6954,'Titans of Industry',NULL," +
		" 'Dearborn','Michigan','USA',NULL,2018,NULL)," +
		"('frc6955',6955,'Los Chilis','http://www.loschilis.cl'," +
		" 'Santiago','Región Metropolitana de Santiago','Chile',NULL,2018,NULL)," +
		"('frc6956',6956,'SHAM-ROCK-BOTICS ☘',NULL," +
		" 'Westfield','Indiana','USA',NULL,2018,NULL)," +
		"('frc6957',6957,'The A.C.E.S.','http://lmca.org/community/robotics.cfm'," +
		" 'Henderson','Nevada','USA',NULL,2018,NULL)," +
		"('frc6958',6958,'CougarBots',NULL," +
		" 'Warren','Michigan','USA',NULL,2018,NULL)," +
		"('frc6959',6959,'Organized Chaos','https://www.centralia.k12.wa.us/site/Default.aspx?PageID=3675'," +
		" 'Centralia','Washington','USA',NULL,2018,NULL)," +
		"('frc6960',6960,'The Rusty Huskies','http://www.north6960.com/'," +
		" 'Riverside','California','USA',NULL,2018,NULL)," +
		"('frc6961',6961,'SIbots',NULL," +
		" 'Spartanburg','South Carolina','USA',NULL,2018,NULL)," +
		"('frc6962',6962,'RobotX','https://team6962.com'," +
		" 'Mountain View','California','USA',NULL,2018,NULL)," +
		"('frc6963',6963,'Blood Sweat and Gears',NULL," +
		" 'Remus','Michigan','USA',NULL,2018,NULL)," +
		"('frc6964',6964,'BearBots',NULL," +
		" 'Columbus','Ohio','USA',NULL,2018,NULL)," +
		"('frc6965',6965,'Tecmilenio RoboMov 6965',NULL," +
		" 'Cancun','Quintana Roo','Mexico',NULL,2018,NULL)," +
		"('frc6966',6966,'Sons Of God ',NULL," +
		" 'Toluca','Mexico','Mexico',NULL,2018,NULL)," +
		"('frc6967',6967,'A.T.Vortex',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc6968',6968,'Team PI','http://www.teampi.nl'," +
		" 'Eindhoven','Noord-Brabant','Netherlands',NULL,2018,NULL)," +
		"('frc6969',6969,'Cyborgs',NULL," +
		" 'West Nyack','New York','USA',NULL,2018,NULL)," +
		"('frc6970',6970,'Barbarian',NULL," +
		" 'Qingdao','Shandong','China',NULL,2018,NULL)," +
		"('frc6971',6971,'The Sky Savior',NULL," +
		" 'ZhengZhou','Henan','China',NULL,2018,NULL)," +
		"('frc6972',6972,'NEXUS Robotics',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2018,NULL)," +
		"('frc6973',6973,'Galaxy Computer Technology',NULL," +
		" 'Adama','Oromiya','Ethiopia',NULL,2018,NULL)," +
		"('frc6974',6974,'Zia Robotics',NULL," +
		" 'Socorro','New Mexico','USA',NULL,2018,NULL)," +
		"('frc6975',6975,'Neil McNeil Maroonotics',NULL," +
		" 'Scarborough','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6976',6976,'Electric Eagles',NULL," +
		" 'Kansas City','Missouri','USA',NULL,2018,NULL)," +
		"('frc6977',6977,'Cyber Squad',NULL," +
		" 'North York','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6978',6978,'QuickStrike Niagara','http://www.niagararobotics.com'," +
		" 'St. Catharines','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6979',6979,'The 5 Nighters','http://unitedroboticslacombe.weebly.com/'," +
		" 'Lacombe','Alberta','Canada',NULL,2018,NULL)," +
		"('frc6980',6980,'VERTIES',NULL," +
		" 'Zhengzhou','Henan','China',NULL,2018,NULL)," +
		"('frc6981',6981,'Clockwork Soldiers',NULL," +
		" 'Concord','California','USA',NULL,2018,NULL)," +
		"('frc6982',6982,'Team CEL Paraguay','https://www.facebook.com/teamparaguayfirst'," +
		" 'Asunción','Asunción','Paraguay',NULL,2018,NULL)," +
		"('frc6983',6983,'Tecmilenio - STEMHAWKS - 6983','https://www.facebook.com/stemhawks'," +
		" 'Monterrey','Nuevo León','Mexico',NULL,2018,NULL)," +
		"('frc6984',6984,'Magical Monkey',NULL," +
		" 'Nanjing','Jiangsu','China',NULL,2018,NULL)," +
		"('frc6985',6985,'ENKA TECH.','https://www.enkatech.com'," +
		" 'Dilovası','Kocaeli','Turkey',NULL,2018,NULL)," +
		"('frc6986',6986,'White Shark',NULL," +
		" 'Nanjing','Jiangsu','China',NULL,2018,NULL)," +
		"('frc6987',6987,'Falcon Automation','https://fenelonrobotics.weebly.com/'," +
		" 'Fenelon Falls','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6988',6988,'ACI 35',NULL," +
		" 'Izmir','Izmir','Turkey',NULL,2018,NULL)," +
		"('frc6989',6989,'KAISER',NULL," +
		" 'ISTANBUL','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc6990',6990,'Ambition',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc6991',6991,'Crosbotics','http://www.team6991.com'," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc6992',6992,'FrancoBots',NULL," +
		" 'Sarnia','Ontario','Canada',NULL,2018,NULL)," +
		"('frc6993',6993,'Dragons ',NULL," +
		" 'Lubbock ','Texas','USA',NULL,2018,NULL)," +
		"('frc6994',6994,'Citadel',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc6995',6995,'NOMAD','http://frc6995.org/'," +
		" 'Escondido','California','USA',NULL,2018,NULL)," +
		"('frc6996',6996,'Koalafied','http://teamkoalafied.org/'," +
		" 'Adelaide','South Australia','Australia',NULL,2018,NULL)," +
		"('frc6997',6997,'Bad wolf',NULL," +
		" 'Chongqing','Chongqing','China',NULL,2018,NULL)," +
		"('frc6998',6998,'NNKIEH','http://www.nnkieh.tn.edu.tw'," +
		" 'Tainan','Tainan','Chinese Taipei',NULL,2018,NULL)," +
		"('frc6999',6999,'B.E.S.T.',NULL," +
		" 'Sinop','Sinop','Turkey',NULL,2018,NULL);";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_14 = SQL_INSERT_TEAMS +
		"('frc7000',7000,'Newton''s 4th Law',NULL," +
		" 'Chengdu','Sichuan','China',NULL,2018,NULL)," +
		"('frc7001',7001,'Buning Power',NULL," +
		" 'Zhengzhou ','Henan','China',NULL,2018,NULL)," +
		"('frc7002',7002,'风云蹦豆',NULL," +
		" 'Taiyuan','Shanxi','China',NULL,2018,NULL)," +
		"('frc7003',7003,'Robots A-Gogo',NULL," +
		" 'Changhua','Changhua','Chinese Taipei',NULL,2018,NULL)," +
		"('frc7004',7004,'Ossining Orbiters ',NULL," +
		" 'Ossining','New York','USA',NULL,2012,NULL)," +
		"('frc7005',7005,'RAHS Phoenix',NULL," +
		" 'Seattle','Washington','USA',NULL,2018,NULL)," +
		"('frc7006',7006,'IMC',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2018,NULL)," +
		"('frc7007',7007,'RROOTT',NULL," +
		" 'Beijing','Beijing','China',NULL,2018,NULL)," +
		"('frc7008',7008,'YakRob','http://www.yeml.meb.k12.tr'," +
		" 'İstanbul','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7009',7009,'Carp & Donkey',NULL," +
		" 'ZhengZhou','Henan','China',NULL,2018,NULL)," +
		"('frc7010',7010,'SINFEN ROBOTICS',NULL," +
		" 'MERKEZ','Sinop','Turkey',NULL,2018,NULL)," +
		"('frc7011',7011,'ROBOFEN',NULL," +
		" 'ISPARTA','Isparta','Turkey',NULL,2018,NULL)," +
		"('frc7012',7012,'Rainerum Robotics',NULL," +
		" 'Bolzano','Bolzano','Italy',NULL,2018,NULL)," +
		"('frc7013',7013,'ACCN Techtronix','http://accntechtronix.com.com/contact.html'," +
		" 'Scarborough','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7014',7014,'YIWU TEAM',NULL," +
		" 'yiwu','Zhejiang','China',NULL,2018,NULL)," +
		"('frc7015',7015,'RoboCulture',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7016',7016,'PV Shock Bots',NULL," +
		" 'Putnam Valley','New York','USA',NULL,2018,NULL)," +
		"('frc7017',7017,'Fellowship of the Gear','http://fogrobotics.org'," +
		" 'Mesa','Arizona','USA',NULL,2018,NULL)," +
		"('frc7018',7018,'EOLOTICS',NULL," +
		" 'El Espinal','Oaxaca','Mexico',NULL,2018,NULL)," +
		"('frc7019',7019,'Tech NoLogic:  Powered by NASA',NULL," +
		" 'Minneapolis','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7020',7020,'IM ROBOT',NULL," +
		" 'Iron Mountain','Michigan','USA',NULL,2018,NULL)," +
		"('frc7021',7021,'TC Robotics','http://www.tcrobotics.tech'," +
		" 'Arcadia','Wisconsin','USA',NULL,2018,NULL)," +
		"('frc7022',7022,'ACE Robotics',NULL," +
		" 'Cambridge','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7023',7023,'Oxley High School Robotics Team',NULL," +
		" 'Tamworth','New South Wales','Australia',NULL,2018,NULL)," +
		"('frc7024',7024,'ENFORCERS','http://www.EHTPAL.org'," +
		" 'Egg Harbor Township','New Jersey','USA',NULL,2018,NULL)," +
		"('frc7025',7025,'RoboBarons',NULL," +
		" 'Fountain Valley','California','USA',NULL,2018,NULL)," +
		"('frc7026',7026,'Team Vinity','http://www.pinecrest.edu'," +
		" 'Fort Lauderdale','Florida','USA',NULL,2018,NULL)," +
		"('frc7027',7027,'Radioactive Rebels Robotics',NULL," +
		" 'Parish','New York','USA',NULL,2018,NULL)," +
		"('frc7028',7028,'Binary Battalion',NULL," +
		" 'Saint Michael','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7029',7029,'Scotbotics',NULL," +
		" 'Laurinburg','North Carolina','USA',NULL,2018,NULL)," +
		"('frc7030',7030,'Lake Placid Robotics - The Olympians','https://lprobotics.wixsite.com/lprobotics'," +
		" 'Lake Placid','New York','USA',NULL,2018,NULL)," +
		"('frc7031',7031,'Duanesburg Jr./Sr. High School',NULL," +
		" 'Delanson','New York','USA',NULL,2018,NULL)," +
		"('frc7032',7032,'Raptors',NULL," +
		" 'La Pine','Oregon','USA',NULL,2018,NULL)," +
		"('frc7033',7033,'Roosters',NULL," +
		" 'Medianeira','Paraná','Brazil',NULL,2018,NULL)," +
		"('frc7034',7034,'2B Determined',NULL," +
		" 'West Linn','Oregon','USA',NULL,2018,NULL)," +
		"('frc7035',7035,'MEF Thunders',NULL," +
		" 'İstanbul/Beşiktaş','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7036',7036,'Beta Gamma Robotica',NULL," +
		" 'Brooklyn','New York','USA',NULL,2018,NULL)," +
		"('frc7037',7037,'Jeunes d''argent',NULL," +
		" 'Fresnillo','Zacatecas','Mexico',NULL,2018,NULL)," +
		"('frc7038',7038,'TG Eagles (Pizza Pi''s)','https://www.totinograce.org/'," +
		" 'Minneapolis','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7039',7039,'❌⭕',NULL," +
		" 'Kfar hanoar Neurim ','HaMerkaz','Israel',NULL,2018,NULL)," +
		"('frc7040',7040,'POTROBOTS',NULL," +
		" 'TURUACHI','Chihuahua','Mexico',NULL,2018,NULL)," +
		"('frc7041',7041,'Doomsday Dogs ',NULL," +
		" 'Carlton','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7042',7042,'Poly Rabbotics','https://lbpolyrabbotics.org'," +
		" 'Long Beach','California','USA',NULL,2018,NULL)," +
		"('frc7043',7043,'RoboPirates','https://sites.google.com/rrcs.org/rocky-river-robotics'," +
		" 'Rocky River','Ohio','USA',NULL,2018,NULL)," +
		"('frc7044',7044,'FeneRobotics',NULL," +
		" 'Atasehir','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7045',7045,'MCCrusaders','http://www.morriscatholic.org'," +
		" 'Denville','New Jersey','USA',NULL,2018,NULL)," +
		"('frc7046',7046,'UNIMATE',NULL," +
		" 'San Luis Potosí','San Luis Potosí','Mexico',NULL,2018,NULL)," +
		"('frc7047',7047,'Magic Frog',NULL," +
		" 'zhengzhou ','Henan','China',NULL,2018,NULL)," +
		"('frc7048',7048,'Red River Rage',NULL," +
		" 'Fargo','North Dakota','USA',NULL,2018,NULL)," +
		"('frc7049',7049,'Marine Machines',NULL," +
		" 'Shreveport','Louisiana','USA',NULL,2018,NULL)," +
		"('frc7050',7050,'SARNIC',NULL," +
		" 'ISTANBUL','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7051',7051,'RoboDons',NULL," +
		" 'Baldwin Park','California','USA',NULL,2018,NULL)," +
		"('frc7052',7052,'Falcotronix','http://www.falcotronix.com'," +
		" 'Thunder Bay','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7053',7053,'Robo Huskies',NULL," +
		" 'St-Hubert','Québec','Canada',NULL,2018,NULL)," +
		"('frc7054',7054,'SC Sailors',NULL," +
		" 'Grand Rapids','Michigan','USA',NULL,2018,NULL)," +
		"('frc7055',7055,'Quantum Steam','https://sites.google.com/bayschoolsohio.org/bayhigh-quantum-steam/home?authuser=0'," +
		" 'Bay Village','Ohio','USA',NULL,2018,NULL)," +
		"('frc7056',7056,'The F.A.S.T. Team - Fowlerville Area Scientific Technicians','http://www.frcfastteam.com'," +
		" 'Fowlerville','Michigan','USA',NULL,2018,NULL)," +
		"('frc7057',7057,'Titanators',NULL," +
		" 'Orange Cove','California','USA',NULL,2018,NULL)," +
		"('frc7058',7058,'StrathDroids',NULL," +
		" 'Strathroy','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7059',7059,'Bot-E Builders',NULL," +
		" 'Prescott','Arizona','USA',NULL,2018,NULL)," +
		"('frc7060',7060,'Riverside Military Academy Eagle Bots ',NULL," +
		" 'Gainesville','Georgia','USA',NULL,2018,NULL)," +
		"('frc7063',7063,'Wagner TSTEM FRC 1',NULL," +
		" 'San Antonio','Texas','USA',NULL,2018,NULL)," +
		"('frc7064',7064,'Voltron Robotics',NULL," +
		" 'Leavenworth','Kansas','USA',NULL,2018,NULL)," +
		"('frc7065',7065,'Wolverine 1 ',NULL," +
		" 'Montgomery','Alabama','USA',NULL,2018,NULL)," +
		"('frc7066',7066,'Faulkner County Library Gadget Droids',NULL," +
		" 'Conway','Arkansas','USA',NULL,2018,NULL)," +
		"('frc7067',7067,'Team Streak','https://www.facebook.com/TeamStreak7067/'," +
		" 'jerusalem','Yerushalayim','Israel',NULL,2018,NULL)," +
		"('frc7068',7068,'Mechanical Masterminds','https://www.stfrancisrobotics.org/'," +
		" 'Saint Francis','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7069',7069,'Taipei 101','https://www.facebook.com/search/top/?q=frc%20team%20%3A%20taipei101%207069'," +
		" 'Taipei','Taipei','Chinese Taipei',NULL,2018,NULL)," +
		"('frc7070',7070,'SRT',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7071',7071,'ITU-Robee','http://www.iturobee.com/'," +
		" 'ISTANBUL','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7072',7072,'O.G.R.E. (Opelika''s Greatest Robotics Engineers)','http://www.ogrerobotics.com'," +
		" 'Opelika','Alabama','USA',NULL,2018,NULL)," +
		"('frc7073',7073,'Ottawa Hills High School',NULL," +
		" 'Grand Rapids','Michigan','USA',NULL,2018,NULL)," +
		"('frc7074',7074,'Reapers',NULL," +
		" 'North Sydney','New South Wales','Australia',NULL,2018,NULL)," +
		"('frc7075',7075,'Gearbreakers',NULL," +
		" 'Pleasant Plains','Arkansas','USA',NULL,2018,NULL)," +
		"('frc7076',7076,'WestTech Sentinels',NULL," +
		" 'Augusta','Georgia','USA',NULL,2018,NULL)," +
		"('frc7077',7077,'Sundevils'' Fire ','https://7077.us'," +
		" 'Las Vegas','Nevada','USA',NULL,2018,NULL)," +
		"('frc7078',7078,'BENG DOU',NULL," +
		" 'TAIYUAN','Shanxi','China',NULL,2018,NULL)," +
		"('frc7079',7079,'Co-Bot','https://www.facebook.com/cobot7079/'," +
		" 'Haifa','HaZafon','Israel',NULL,2018,NULL)," +
		"('frc7080',7080,'Vision',NULL," +
		" 'Beijing','Beijing','China',NULL,2018,NULL)," +
		"('frc7081',7081,'NEYC',NULL," +
		" 'Shenyang','Liaoning','China',NULL,2018,NULL)," +
		"('frc7082',7082,'Rogozin',NULL," +
		" 'Kiryat Gat','Yerushalayim','Israel',NULL,2018,NULL)," +
		"('frc7083',7083,'Green Hekma',NULL," +
		" 'Nazareth','HaZafon','Israel',NULL,2018,NULL)," +
		"('frc7084',7084,'Hillman Pyrobots',NULL," +
		" 'Hillman','Michigan','USA',NULL,2018,NULL)," +
		"('frc7085',7085,'Waccamaw High School',NULL," +
		" 'Pawleys Island','South Carolina','USA',NULL,2018,NULL)," +
		"('frc7086',7086,'IOROBOT ','http://team7086.blogspot.com.tr/'," +
		" 'ISTANBUL','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7087',7087,'BCS',NULL," +
		" 'Balcarres','Saskatchewan','Canada',NULL,2018,NULL)," +
		"('frc7088',7088,'Robodogs',NULL," +
		" 'Somerset','Texas','USA',NULL,2018,NULL)," +
		"('frc7091',7091,'Atlas Orbis',NULL," +
		" 'Dallas','Texas','USA',NULL,2018,NULL)," +
		"('frc7092',7092,'Bionic Bulldogs',NULL," +
		" 'Lyford','Texas','USA',NULL,2018,NULL)," +
		"('frc7093',7093,'Veritas Valiants',NULL," +
		" 'Austin','Texas','USA',NULL,2018,NULL)," +
		"('frc7094',7094,'Alpha Omega',NULL," +
		" 'Bay Saint Louis','Mississippi','USA',NULL,2018,NULL)," +
		"('frc7095',7095,'HiMach',NULL," +
		" 'Beijing','Beijing','China',NULL,2018,NULL)," +
		"('frc7096',7096,'Educated Business','https://educated.business/'," +
		" 'Los Gatos','California','USA',NULL,2018,NULL)," +
		"('frc7097',7097,'The Archers ',NULL," +
		" 'Myrtle Beach','South Carolina','USA',NULL,2018,NULL)," +
		"('frc7098',7098,'Seoul High School','http://www.dodea.edu'," +
		" 'APO','Armed Forces - Americas','USA',NULL,2018,NULL)," +
		"('frc7099',7099,'Osan Middle/High School','http://www.dodea.edu/OsanMHS/'," +
		" 'APO','Armed Forces - Pacific','USA',NULL,2018,NULL)," +
		"('frc7100',7100,'Noble Mechanical Knights',NULL," +
		" 'North Berwick','Maine','USA',NULL,2018,NULL)," +
		"('frc7101',7101,'Genesee Robotic Wolves',NULL," +
		" 'Genesee','Michigan','USA',NULL,2018,NULL)," +
		"('frc7102',7102,'PrepaTec - Daedalus',NULL," +
		" 'Zapopan','Jalisco','Mexico',NULL,2018,NULL)," +
		"('frc7103',7103,'Robo Remedy @ MLHS','http://roboremedy.org'," +
		" 'Manitowoc','Wisconsin','USA',NULL,2018,NULL)," +
		"('frc7104',7104,'Benedictine Military School Bot Brothers','http://www.thebc400.com'," +
		" 'Savannah','Georgia','USA',NULL,2018,NULL)," +
		"('frc7105',7105,'PBG AREA ROBOTICS',NULL," +
		" 'Palm Beach Gardens','Florida','USA',NULL,2018,NULL)," +
		"('frc7106',7106,'MPV StingerBots','https://stingerbots7106.wixsite.com/stingerbots7106'," +
		" 'Mulberry','Arkansas','USA',NULL,2018,NULL)," +
		"('frc7107',7107,'Blazer Robotics',NULL," +
		" 'Boca Raton','Florida','USA',NULL,2018,NULL)," +
		"('frc7108',7108,'volTRan',NULL," +
		" 'Fatih','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7109',7109,'Clare Chaos Theory','https://clarechaostheory.weebly.com'," +
		" 'Clare','Michigan','USA',NULL,2018,NULL)," +
		"('frc7110',7110,'Heights Bytes',NULL," +
		" 'Haddon Heights','New Jersey','USA',NULL,2018,NULL)," +
		"('frc7111',7111,'RAD Robotics',NULL," +
		" 'Madison','Alabama','USA',NULL,2018,NULL)," +
		"('frc7112',7112,'EverGreen',NULL," +
		" 'Kadima-Zoran','HaMerkaz','Israel',NULL,2018,NULL)," +
		"('frc7113',7113,'Control Rising',NULL," +
		" 'Perth','Western Australia','Australia',NULL,2018,NULL)," +
		"('frc7114',7114,'Robo-Raiders',NULL," +
		" 'High Point','North Carolina','USA',NULL,2018,NULL)," +
		"('frc7115',7115,'Red Lion Bots',NULL," +
		" 'Houston','Texas','USA',NULL,2018,NULL)," +
		"('frc7116',7116,'Électriks','http://www.team7116.ca'," +
		" 'Shawinigan','Québec','Canada',NULL,2018,NULL)," +
		"('frc7117',7117,'Robotic Dragons','https://sites.google.com/view/desoto-dragons-robotics/home'," +
		" 'De Soto','Missouri','USA',NULL,2018,NULL)," +
		"('frc7118',7118,'ScotBots',NULL," +
		" 'Shoreline','Washington','USA',NULL,2018,NULL)," +
		"('frc7119',7119,'Sunset RoboBison',NULL," +
		" 'Dallas','Texas','USA',NULL,2018,NULL)," +
		"('frc7120',7120,'ThunderChicas','http://rangelrobotics.wixsite.com/website'," +
		" 'Dallas','Texas','USA',NULL,2018,NULL)," +
		"('frc7121',7121,'Keller Fusion Robotics','http://www.kcalrobotics.weebly.com'," +
		" 'Keller','Texas','USA',NULL,2018,NULL)," +
		"('frc7122',7122,'WYSIWYG',NULL," +
		" 'Des Moines','Iowa','USA',NULL,2018,NULL)," +
		"('frc7123',7123,'MVE Tin Men','https://www.facebook.com/groups/1784150648328087'," +
		" 'Mount Vernon','Arkansas','USA',NULL,2018,NULL)," +
		"('frc7124',7124,'8-bit Destroyers - Cranebrook HS',NULL," +
		" 'Sydney','New South Wales','Australia',NULL,2018,NULL)," +
		"('frc7125',7125,'Tigerbotics','http://www.facebook.com/GRHSRT'," +
		" 'Glen Rose','Texas','USA',NULL,2018,NULL)," +
		"('frc7126',7126,'Cutting Edgelords ',NULL," +
		" 'Newmarket','New Hampshire','USA',NULL,2018,NULL)," +
		"('frc7127',7127,'LongMetal ','http://www.Longmetal.org'," +
		" 'Longmeadow','Massachusetts','USA',NULL,2018,NULL)," +
		"('frc7128',7128,'XLR8',NULL," +
		" 'Wantirna','Victoria','Australia',NULL,2018,NULL)," +
		"('frc7129',7129,'Pilbara Protons',NULL," +
		" 'South Hedland','Western Australia','Australia',NULL,2018,NULL)," +
		"('frc7130',7130,'FABLAB MDHS',NULL," +
		" 'Wuri Dist.','Taichung','Chinese Taipei',NULL,2018,NULL)," +
		"('frc7131',7131,'Mavericks',NULL," +
		" 'Caledon','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7132',7132,'Axeman',NULL," +
		" 'Mississauga','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7133',7133,'STEAM Makers','https://fhs-robotics.neocities.org/'," +
		" 'Farmington','New Hampshire','USA',NULL,2018,NULL)," +
		"('frc7134',7134,'rOctopus','http://roctopus7134.com'," +
		" 'Aydin','Aydin','Turkey',NULL,2018,NULL)," +
		"('frc7135',7135,'System Reset',NULL," +
		" 'Surrey','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7136',7136,'Thistletown Scot Bots',NULL," +
		" 'Etobicoke','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7137',7137,'Project 212',NULL," +
		" 'Concord','California','USA',NULL,2018,NULL)," +
		"('frc7138',7138,'Wing It',NULL," +
		" 'Fischer','Texas','USA',NULL,2018,NULL)," +
		"('frc7139',7139,'AERO','http://N/A'," +
		" 'MANIZALES','Caldas','Colombia',NULL,2018,NULL)," +
		"('frc7140',7140,'OKSEF ROBOTICS','https://oksef7140.com/'," +
		" 'IZMIR','Izmir','Turkey',NULL,2018,NULL)," +
		"('frc7141',7141,'Cyberhounds','http://mz-j.com/Mz-J.com/Robotics.html'," +
		" 'Fort Madison','Iowa','USA',NULL,2018,NULL)," +
		"('frc7142',7142,'Saydel High School Robotics Team---Vulcan Eagles',NULL," +
		" 'Des Moines','Iowa','USA',NULL,2018,NULL)," +
		"('frc7143',7143,'The Cyber-Eagles',NULL," +
		" 'Detroit','Michigan','USA',NULL,2018,NULL)," +
		"('frc7144',7144,'NexTech Hydra',NULL," +
		" 'Okemos','Michigan','USA',NULL,2018,NULL)," +
		"('frc7145',7145,'Hurricanes',NULL," +
		" 'Detroit','Michigan','USA',NULL,2018,NULL)," +
		"('frc7146',7146,'Infinity Robotics',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2018,NULL)," +
		"('frc7147',7147,'Falcons Senior Robo Team ',NULL," +
		" 'Ann Arbor','Michigan','USA',NULL,2018,NULL)," +
		"('frc7148',7148,'Faster Than Light',NULL," +
		" 'Zhengzhou','Henan','China',NULL,2018,NULL)," +
		"('frc7149',7149,'WuKong',NULL," +
		" 'Beijing','Beijing','China',NULL,2018,NULL)," +
		"('frc7150',7150,'Ogemaw Overdrive',NULL," +
		" 'West Branch','Michigan','USA',NULL,2018,NULL)," +
		"('frc7151',7151,'Real Robotics',NULL," +
		" 'Herriman','Utah','USA',NULL,2018,NULL)," +
		"('frc7152',7152,'Owltonomous',NULL," +
		" 'Boca Raton','Florida','USA',NULL,2018,NULL)," +
		"('frc7153',7153,'Aetos Dios, Eagles of Zeus','http://www.frc7153.com'," +
		" 'Manchester','Connecticut','USA',NULL,2018,NULL)," +
		"('frc7154',7154,'TheROK',NULL," +
		" 'Kingsford','Michigan','USA',NULL,2018,NULL)," +
		"('frc7155',7155,'The Robotic Rangers',NULL," +
		" 'Manton','Michigan','USA',NULL,2018,NULL)," +
		"('frc7156',7156,'NVASRoboKnights',NULL," +
		" 'Norway','Michigan','USA',NULL,2018,NULL)," +
		"('frc7157',7157,' μBotics',NULL," +
		" 'Brea','California','USA',NULL,2018,NULL)," +
		"('frc7158',7158,'The Conspirators ',NULL," +
		" 'Langley','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7159',7159,'Burnaby Central Secondary',NULL," +
		" 'Burnaby','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7160',7160,'Ludington O-Bots','http://ludingtonrobotics.org'," +
		" 'Ludington','Michigan','USA',NULL,2018,NULL)," +
		"('frc7161',7161,'LeopardBots',NULL," +
		" 'Zacazonapan','Mexico','Mexico',NULL,2018,NULL)," +
		"('frc7162',7162,'Maraudeurs',NULL," +
		" 'Laval','Québec','Canada',NULL,2018,NULL)," +
		"('frc7163',7163,'A.I. Metal Pies',NULL," +
		" 'Merrylands','New South Wales','Australia',NULL,2018,NULL)," +
		"('frc7164',7164,'RHODE HAZARD',NULL," +
		" 'Providence','Rhode Island','USA',NULL,2018,NULL)," +
		"('frc7165',7165,'Perkins Pirates ',NULL," +
		" 'Sandusky','Ohio','USA',NULL,2018,NULL)," +
		"('frc7166',7166,'Red Thunder Robotics','https://www.facebook.com/redthunderrobotics'," +
		" 'Laingsburg','Michigan','USA',NULL,2018,NULL)," +
		"('frc7167',7167,'Spectrum ThunderBots','http://thunderbots.ca'," +
		" 'Victoria','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7168',7168,'Infinity','https://sites.google.com/view/7168/home'," +
		" 'Detroit','Michigan','USA',NULL,2018,NULL)," +
		"('frc7169',7169,'Belmont Bytes ',NULL," +
		" 'Victoria','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7170',7170,'Spooky Action Robotics','http://frcteam7170.edublogs.org/'," +
		" 'Comox','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7171',7171,'Wolverine Wildcats',NULL," +
		" 'Wolverine','Michigan','USA',NULL,2018,NULL)," +
		"('frc7172',7172,'AEA Robolution',NULL," +
		" 'Oscoda','Michigan','USA',NULL,2018,NULL)," +
		"('frc7173',7173,'Iris Robotics',NULL," +
		" 'Maple Ridge','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7174',7174,'ProtoBots','https://www.team7174.org'," +
		" 'Dearborn Heights','Michigan','USA',NULL,2018,NULL)," +
		"('frc7175',7175,'Team Red Line',NULL," +
		" 'Clinton','Michigan','USA',NULL,2018,NULL)," +
		"('frc7176',7176,'ROC',NULL," +
		" 'Unionville','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7177',7177,'Amal Taybe',NULL," +
		" 'taybe','HaMerkaz','Israel',NULL,2018,NULL)," +
		"('frc7178',7178,'Yeti','http://monsters308.org'," +
		" 'Walled Lake','Michigan','USA',NULL,2018,NULL)," +
		"('frc7179',7179,'Crossfire',NULL," +
		" 'Garland','Texas','USA',NULL,2018,NULL)," +
		"('frc7180',7180,'Wellstone Lions',NULL," +
		" 'Minneapolis','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7181',7181,'Lions',NULL," +
		" 'Florence','Alabama','USA',NULL,2018,NULL)," +
		"('frc7182',7182,'Robotic Indians','http:///www.cctotie.weebly.com'," +
		" 'Leighton','Alabama','USA',NULL,2018,NULL)," +
		"('frc7183',7183,'WAR Club',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2018,NULL)," +
		"('frc7184',7184,'BIT CHALLENGE',NULL," +
		" 'Foushan','Guangdong','China',NULL,2018,NULL)," +
		"('frc7185',7185,'Glendora High School Robotics ',NULL," +
		" 'Glendora','California','USA',NULL,2018,NULL)," +
		"('frc7187',7187,'Gear Cats','https://clintwaller.wixsite.com/dkgearcats'," +
		" 'Delton','Michigan','USA',NULL,2018,NULL)," +
		"('frc7188',7188,'SaddleBots',NULL," +
		" 'Warren','Michigan','USA',NULL,2018,NULL)," +
		"('frc7189',7189,'EX NIHILO',NULL," +
		" 'Kelowna','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7190',7190,'Vancouver Rainstorms','https://templetonrobotics.ca'," +
		" 'Vancouver','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7191',7191,'ABT Gators',NULL," +
		" 'Melvindale','Michigan','USA',NULL,2018,NULL)," +
		"('frc7192',7192,'Blue Thunderneers',NULL," +
		" 'Croswell','Michigan','USA',NULL,2018,NULL)," +
		"('frc7193',7193,'Invictus','http://www.iwmf2.org/team7193'," +
		" 'Fort Myers','Florida','USA',NULL,2018,NULL)," +
		"('frc7194',7194,'iRam',NULL," +
		" 'New Port Richey','Florida','USA',NULL,2018,NULL)," +
		"('frc7195',7195,'Portland Raiders',NULL," +
		" 'Portland','Michigan','USA',NULL,2018,NULL)," +
		"('frc7196',7196,'Tech Tigers',NULL," +
		" 'Detroit','Michigan','USA',NULL,2018,NULL)," +
		"('frc7197',7197,'Mountie MegaBots','http://nwschools.org'," +
		" 'Jackson','Michigan','USA',NULL,2018,NULL)," +
		"('frc7198',7198,'TL Fearsome Gears',NULL," +
		" 'Monticello','Indiana','USA',NULL,2018,NULL)," +
		"('frc7199',7199,'Thunder',NULL," +
		" 'Maple Ridge','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7200',7200,'Banting Memorial high school',NULL," +
		" 'Alliston','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7201',7201,'Crobotics',NULL," +
		" 'Rijeka','Primorsko-goranska županija','Croatia',NULL,2018,NULL)," +
		"('frc7202',7202,'Cyber Orioles',NULL," +
		" 'Morrice','Michigan','USA',NULL,2018,NULL)," +
		"('frc7203',7203,'BCPS 21st Century Bearcats',NULL," +
		" 'Battle Creek','Michigan','USA',NULL,2018,NULL)," +
		"('frc7204',7204,'Freedom Gliders',NULL," +
		" 'Prospect','Oregon','USA',NULL,2018,NULL)," +
		"('frc7205',7205,'Lakers',NULL," +
		" 'La Loche','Saskatchewan','Canada',NULL,2018,NULL)," +
		"('frc7206',7206,'Titanium Rex',NULL," +
		" 'Center Line','Michigan','USA',NULL,2018,NULL)," +
		"('frc7207',7207,'Angry Birds',NULL," +
		" 'Delta','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7208',7208,'CMA Navigators',NULL," +
		" 'Charlevoix','Michigan','USA',NULL,2018,NULL)," +
		"('frc7209',7209,'Titanium Trojans (Homer Robotics Club)',NULL," +
		" 'Homer','Michigan','USA',NULL,2018,NULL)," +
		"('frc7210',7210,'RoboJackets',NULL," +
		" 'Greenville','Michigan','USA',NULL,2018,NULL)," +
		"('frc7211',7211,'Hollywood',NULL," +
		" 'Holly','Michigan','USA',NULL,2018,NULL)," +
		"('frc7212',7212,'LCS Warriors','http://ledyardcharterschool.org'," +
		" 'Lebanon','New Hampshire','USA',NULL,2018,NULL)," +
		"('frc7213',7213,'GladWINites',NULL," +
		" 'Gladwin','Michigan','USA',NULL,2018,NULL)," +
		"('frc7214',7214,'Tiger-Ops Robotics',NULL," +
		" 'Marana','Arizona','USA',NULL,2018,NULL)," +
		"('frc7215',7215,'Patriots',NULL," +
		" 'Hollywood','Florida','USA',NULL,2018,NULL)," +
		"('frc7216',7216,'CHARGERS ROBOTICS','http://www.chargersroboticsteam.edu'," +
		" 'North Miami Beach','Florida','USA',NULL,2018,NULL)," +
		"('frc7217',7217,'SallaBots / Universidad La Salle Laguna',NULL," +
		" 'TORREON','Coahuila','Mexico',NULL,2018,NULL)," +
		"('frc7218',7218,'Bradford Botdogs','http://bradfordrobotics7218@gmail.com'," +
		" 'Southfield','Michigan','USA',NULL,2018,NULL)," +
		"('frc7219',7219,'Olivet Eagles',NULL," +
		" 'Olivet','Michigan','USA',NULL,2018,NULL)," +
		"('frc7220',7220,'Steel Falcons',NULL," +
		" 'Brighton','Michigan','USA',NULL,2018,NULL)," +
		"('frc7221',7221,'JHS Viking Robotics',NULL," +
		" 'Jackson','Michigan','USA',NULL,2018,NULL)," +
		"('frc7222',7222,'The Fighting Anomalocari ','https://www.facebook.com/groups/142018563142130/'," +
		" 'Parma','Michigan','USA',NULL,2018,NULL)," +
		"('frc7223',7223,'MVHS Robotics',NULL," +
		" 'Vermontville','Michigan','USA',NULL,2018,NULL)," +
		"('frc7224',7224,'RoboDrakens','http://robodrakens.org'," +
		" 'Hazel Park','Michigan','USA',NULL,2018,NULL)," +
		"('frc7225',7225,'Mechatronic Mustangs',NULL," +
		" 'Milford','Michigan','USA',NULL,2018,NULL)," +
		"('frc7226',7226,'Error 404',NULL," +
		" 'Lansing','Michigan','USA',NULL,2018,NULL)," +
		"('frc7227',7227,'Tornado',NULL," +
		" 'Kayseri','Kayseri','Turkey',NULL,2018,NULL)," +
		"('frc7228',7228,'Tigers',NULL," +
		" 'İstanbul','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7229',7229,'Electronic Eagles','http://www.electroniceagles.com'," +
		" 'Sacramento','California','USA',NULL,2018,NULL)," +
		"('frc7230',7230,'Mythic Mechanix','https://www.team7230.com/'," +
		" 'Irvine','California','USA',NULL,2018,NULL)," +
		"('frc7231',7231,'Team Freedom','https://www.facebook.com/teamfreedomdr/'," +
		" 'Santo Domingo','Distrito Nacional','Dominican Republic',NULL,2018,NULL)," +
		"('frc7232',7232,'Pharaohs',NULL," +
		" 'Detroit','Michigan','USA',NULL,2018,NULL)," +
		"('frc7233',7233,'Hope Of Detroit Warriors','http:///www.hopeofdetroit.com'," +
		" 'Detroit','Michigan','USA',NULL,2018,NULL)," +
		"('frc7234',7234,'Rebel Robotics',NULL," +
		" 'Wyoming','Michigan','USA',NULL,2018,NULL)," +
		"('frc7235',7235,'Red Lake Ogichidaag',NULL," +
		" 'Redlake','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7236',7236,'ROBOGEDİK',NULL," +
		" 'Pendik','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7237',7237,'UniBots',NULL," +
		" 'Bridgeview','Illinois','USA',NULL,2018,NULL)," +
		"('frc7238',7238,'coolker',NULL," +
		" 'zhengzhou','Henan','China',NULL,2018,NULL)," +
		"('frc7239',7239,'Hell Robotics','https://www.hellrobotics.no'," +
		" 'Hell','Trøndelag','Norway',NULL,2018,NULL)," +
		"('frc7240',7240,'Nitro Knights','https://r13boston.wixsite.com/nitroknights'," +
		" 'Cambridge','Massachusetts','USA',NULL,2018,NULL)," +
		"('frc7241',7241,'NexTech Knights Robotics','http:///tbd'," +
		" 'Grand Rapids','Michigan','USA',NULL,2018,NULL)," +
		"('frc7242',7242,'Prism','https://www.7242.team'," +
		" 'Hong Kong','Hong Kong','China',NULL,2018,NULL)," +
		"('frc7243',7243,'High Tech Early College',NULL," +
		" 'Denver','Colorado','USA',NULL,2018,NULL)," +
		"('frc7244',7244,'Huskies Robotics','https://huskiesrobotics7244.weebly.com'," +
		" 'Atlanta','Michigan','USA',NULL,2018,NULL)," +
		"('frc7245',7245,'Lion Bots','https://sites.google.com/kippbayarea.org/kippking-lionbots/home?authuser=1'," +
		" 'San Lorenzo','California','USA',NULL,2018,NULL)," +
		"('frc7246',7246,'Thom Collegiate Trojans',NULL," +
		" 'Regina','Saskatchewan','Canada',NULL,2018,NULL)," +
		"('frc7247',7247,'Error 404',NULL," +
		" 'Fair Haven','Michigan','USA',NULL,2018,NULL)," +
		"('frc7248',7248,'Tactical Hams','http://pottershouseschool.org/locations/high/'," +
		" 'Wyoming','Michigan','USA',NULL,2018,NULL)," +
		"('frc7249',7249,'CVH SPARTANS',NULL," +
		" 'Chula Vista','California','USA',NULL,2018,NULL)," +
		"('frc7250',7250,'OsCODEa 0w|5',NULL," +
		" 'Oscoda','Michigan','USA',NULL,2018,NULL)," +
		"('frc7251',7251,'Warrior Robotics',NULL," +
		" 'Salamanca','New York','USA',NULL,2018,NULL)," +
		"('frc7252',7252,'Ionia Bulldogs',NULL," +
		" 'Ionia','Michigan','USA',NULL,2018,NULL)," +
		"('frc7253',7253,'Timber Wolf',NULL," +
		" 'Portland','Oregon','USA',NULL,2018,NULL)," +
		"('frc7254',7254,'Knowmads',NULL," +
		" 'Potterville','Michigan','USA',NULL,2018,NULL)," +
		"('frc7255',7255,'Mechanical Mettle',NULL," +
		" 'Charleston','Missouri','USA',NULL,2018,NULL)," +
		"('frc7256',7256,'Irish Robotics','https://irishrobotics.weebly.com/'," +
		" 'Kalamazoo','Michigan','USA',NULL,2018,NULL)," +
		"('frc7257',7257,'Sauk Centre STREET RATS',NULL," +
		" 'Sauk Centre','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7258',7258,'Hiawatha Collegiate',NULL," +
		" 'Minneapolis','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7259',7259,'Mechanical Mustangs',NULL," +
		" 'Granby','Massachusetts','USA',NULL,2018,NULL)," +
		"('frc7260',7260,'Diamondback Turtles  (Heritage High School)',NULL," +
		" 'Rogers','Arkansas','USA',NULL,2018,NULL)," +
		"('frc7261',7261,'Esquimalt Robotics',NULL," +
		" 'Victoria','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7262',7262,'QBOT',NULL," +
		" 'Quesnel','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7263',7263,'TOPPS TECH','http://www.toppsinc.org'," +
		" 'Pine Bluff','Arkansas','USA',NULL,2018,NULL)," +
		"('frc7264',7264,'Cougar Robotics',NULL," +
		" 'New Braunfels','Texas','USA',NULL,2018,NULL)," +
		"('frc7265',7265,'Skeleton Crew','https://sites.google.com/view/topsail-robirates/home'," +
		" 'Hampstead','North Carolina','USA',NULL,2018,NULL)," +
		"('frc7266',7266,'NBHS Steampunks',NULL," +
		" 'North Brookfield','Massachusetts','USA',NULL,2018,NULL)," +
		"('frc7267',7267,'Lisgar Robotics Club','http://lisgarrobotics.club'," +
		" 'Ottawa','Ontario','Canada',NULL,2018,NULL)," +
		"('frc7268',7268,'BCNY Gerry Robotic Lions (East Harlem)',NULL," +
		" 'New York','New York','USA',NULL,2018,NULL)," +
		"('frc7269',7269,'ISB VoltBots',NULL," +
		" 'Cambridge','Massachusetts','USA',NULL,2018,NULL)," +
		"('frc7270',7270,'Penco Bots','http://www.phenixrobotics.org'," +
		" 'Burgaw','North Carolina','USA',NULL,2018,NULL)," +
		"('frc7271',7271,'Hanger 84 Robotics',NULL," +
		" 'Roswell','New Mexico','USA',NULL,2018,NULL)," +
		"('frc7272',7272,'EnviroBots','https://hsesfrc.wixsite.com/website'," +
		" 'New York','New York','USA',NULL,2018,NULL)," +
		"('frc7273',7273,'ZooM Robotics',NULL," +
		" 'Zumbrota','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7274',7274,'Brashear Bulls Robotics',NULL," +
		" 'Pittsburgh','Pennsylvania','USA',NULL,2018,NULL)," +
		"('frc7275',7275,'PRIDE Robotics','http://7275.team'," +
		" 'New Orleans','Louisiana','USA',NULL,2018,NULL)," +
		"('frc7276',7276,'Success Initiative Robotics','http://projecthubspartanburg.com/success-initiative/'," +
		" 'Spartanburg','South Carolina','USA',NULL,2018,NULL)," +
		"('frc7277',7277,'Mandela United Squadron',NULL," +
		" 'Calgary','Alberta','Canada',NULL,2018,NULL)," +
		"('frc7278',7278,'Toormina High School',NULL," +
		" 'Toormina','New South Wales','Australia',NULL,2018,NULL)," +
		"('frc7279',7279,'Xishan Senior High School Team',NULL," +
		" 'Wuxi','Jiangsu','China',NULL,2018,NULL)," +
		"('frc7280',7280,'MITO Octopus',NULL," +
		" 'Suzhou','Jiangsu','China',NULL,2018,NULL)," +
		"('frc7281',7281,'F.G.',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc7282',7282,'Semia United 1',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc7283',7283,'Semia United 2',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc7284',7284,'Semia United 3',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2018,NULL)," +
		"('frc7286',7286,'Enigma',NULL," +
		" 'daliat el carmel ','Haifa','Israel',NULL,2018,NULL)," +
		"('frc7287',7287,'Atom Smashers','https://7287atomsmashers.wordpress.com/'," +
		" 'Victoria','British Columbia','Canada',NULL,2018,NULL)," +
		"('frc7288',7288,'NOMMA D3V1LB0TS',NULL," +
		" 'New Orleans','Louisiana','USA',NULL,2018,NULL)," +
		"('frc7289',7289,'Blue Eagles',NULL," +
		" 'Fowler','Michigan','USA',NULL,2018,NULL)," +
		"('frc7290',7290,'TARHUNDA',NULL," +
		" 'ADANA','Adana','Turkey',NULL,2018,NULL)," +
		"('frc7291',7291,'TYANA - NİĞDE GAZOZU',NULL," +
		" 'NİĞDE','Nigde','Turkey',NULL,2018,NULL)," +
		"('frc7292',7292,'HITIT ANGELS',NULL," +
		" 'Çorum','Çorum','Turkey',NULL,2018,NULL)," +
		"('frc7293',7293,'COTANAK ROBOTICS','http://pirazizeml.meb.k12.tr'," +
		" 'Piraziz','Giresun','Turkey',NULL,2018,NULL)," +
		"('frc7294',7294,'RoboCirak',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7295',7295,'SPECTRUM',NULL," +
		" 'SAMSUN','Samsun','Turkey',NULL,2018,NULL)," +
		"('frc7296',7296,'TeRoBoT',NULL," +
		" 'Levent','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7297',7297,'KAFKAS KARTALLARI','http://751588@meb.k12.tr'," +
		" 'KARS','Kars','Turkey',NULL,2018,NULL)," +
		"('frc7298',7298,'TOROS','https://www.instagram.com/toros7298/'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7299',7299,'Robotic Eagles',NULL," +
		" 'Santa Catarina','Nuevo León','Mexico',NULL,2018,NULL)," +
		"('frc7300',7300,'Dectro','http://dectro8002.com'," +
		" 'Edirne','Edirne','Turkey',NULL,2018,NULL)," +
		"('frc7301',7301,'Jackbots',NULL," +
		" 'Saint Maries','Idaho','USA',NULL,2018,NULL)," +
		"('frc7302',7302,'afaq school team ',NULL," +
		" 'kfar manda','HaZafon','Israel',NULL,2018,NULL)," +
		"('frc7303',7303,'Reach Robotics','http://www.eatonHSrobotics.org'," +
		" 'Haslet','Texas','USA',NULL,2018,NULL)," +
		"('frc7304',7304,'CAPA MEE6',NULL," +
		" 'ISTANBUL','Istanbul','Turkey',NULL,2018,NULL)," +
		"('frc7305',7305,'Rambo Robotics',NULL," +
		" 'Snow Hill','North Carolina','USA',NULL,2018,NULL)," +
		"('frc7306',7306,'Epping High School',NULL," +
		" 'Epping','New Hampshire','USA',NULL,2018,NULL)," +
		"('frc7307',7307,'Team Muskwa',NULL," +
		" 'Rancho Palos Verdes','California','USA',NULL,2018,NULL)," +
		"('frc7308',7308,'DeepVision','http://www.7308deepvision.com'," +
		" 'Los Altos','California','USA',NULL,2018,NULL)," +
		"('frc7309',7309,'Green Lightning',NULL," +
		" 'Storm Lake','Iowa','USA',NULL,2018,NULL)," +
		"('frc7310',7310,'Lightning Gears',NULL," +
		" 'Culiacan','Sinaloa','Mexico',NULL,2018,NULL)," +
		"('frc7311',7311,'Boring Robots',NULL," +
		" 'Virginia','Minnesota','USA',NULL,2018,NULL)," +
		"('frc7312',7312,'T3','https://tomballrobotics.wixsite.com/home'," +
		" 'Tomball','Texas','USA',NULL,2018,NULL)," +
		"('frc7313',7313,'SBA Eaglebots',NULL," +
		" 'Cordova','Tennessee','USA',NULL,2018,NULL)," +
		"('frc7314',7314,'Tornadoes',NULL," +
		" 'Franklin','New Hampshire','USA',NULL,2018,NULL)," +
		"('frc7315',7315,'Giga-Knights','http://www.deerfieldwindsor.com'," +
		" 'Albany','Georgia','USA',NULL,2018,NULL)," +
		"('frc7316',7316,'Ursuline Academy High School ',NULL," +
		" 'New Orleans','Louisiana','USA',NULL,2018,NULL)," +
		"('frc7317',7317,'Crusader Crew',NULL," +
		" 'San Francisco','California','USA',NULL,2018,NULL)," +
		"('frc7318',7318,'Nanuet Knightronz',NULL," +
		" 'Nanuet','New York','USA',NULL,2018,NULL)," +
		"('frc7319',7319,'1UP',NULL," +
		" 'Garland','Texas','USA',NULL,2018,NULL)," +
		"('frc7320',7320,'Astralis',NULL," +
		" 'Diyarbakir','Diyarbakir','Turkey',NULL,2018,NULL)," +
		"('frc7321',7321,'Aguila Robotica',NULL," +
		" 'Irving','Texas','USA',NULL,2018,NULL)," +
		"('frc7322',7322,'YVHS Robotics',NULL," +
		" 'Yucca Valley','California','USA',NULL,2018,NULL)," +
		"('frc7323',7323,'Pacifica High School',NULL," +
		" 'Oxnard','California','USA',NULL,2018,NULL)," +
		"('frc7324',7324,'The RoboRaiders',NULL," +
		" 'Oxnard','California','USA',NULL,2018,NULL)," +
		"('frc7325',7325,'ASU  Brave Guide Right ',NULL," +
		" 'Natchez ','Mississippi','USA',NULL,2018,NULL)," +
		"('frc7326',7326,'Hueneme Autonomous Robo Droidz',NULL," +
		" 'Oxnard','California','USA',NULL,2018,NULL)," +
		"('frc7327',7327,'Metal Jackets',NULL," +
		" 'Oxnard','California','USA',NULL,2018,NULL)," +
		"('frc7328',7328,'Frontier High School',NULL," +
		" 'Camarillo','California','USA',NULL,2018,NULL)," +
		"('frc7329',7329,'The Afghan Dreamers',NULL," +
		" 'Jadeh Mahbas','Herāt','Afghanistan',NULL,2018,NULL)," +
		"('frc7330',7330,'C.O.U.G.A.R.S (Cybernetic Operational Unit Generated For Artificial Robotic Syst',NULL," +
		" 'Dendron','Virginia','USA',NULL,2018,NULL)," +
		"('frc7331',7331,'McKinley High School Robotics',NULL," +
		" 'Baton Rouge','Louisiana','USA',NULL,2018,NULL)," +
		"('frc7400',7400,'ThunderMiners','http://www.team7400.org'," +
		" 'Melville','New York','USA',NULL,2019,NULL)," +
		"('frc7401',7401,'ACME Academy',NULL," +
		" 'Concord','California','USA',NULL,2019,NULL)," +
		"('frc7403',7403,'Lightning Blue Lizards',NULL," +
		" 'Envigado','Antioquia','Colombia',NULL,2019,NULL)," +
		"('frc7405',7405,'IcyNights',NULL," +
		" 'Zhangjiagang','Jiangsu','China',NULL,2019,NULL)," +
		"('frc7406',7406,'Ministry of Mechanics',NULL," +
		" 'Cleveland','Tennessee','USA',NULL,2019,NULL)," +
		"('frc7407',7407,'Wired Boars',NULL," +
		" 'Wallingford','Connecticut','USA',NULL,2019,NULL)," +
		"('frc7408',7408,'Pearl City Cats','http://www.bocahousing.org/FamilyPrograms/AfterSchoolSummerCamp'," +
		" 'Boca Raton','Florida','USA',NULL,2019,NULL)," +
		"('frc7409',7409,'GLOW',NULL," +
		" 'Boca Raton','Florida','USA',NULL,2019,NULL)," +
		"('frc7410',7410,'Texas Toast',NULL," +
		" 'Clute','Texas','USA',NULL,2019,NULL)," +
		"('frc7411',7411,'CrossThreaded',NULL," +
		" 'Cedar Falls','Iowa','USA',NULL,2019,NULL)," +
		"('frc7412',7412,'KDHS Robotic Owls',NULL," +
		" 'Metairie','Louisiana','USA',NULL,2019,NULL)," +
		"('frc7413',7413,'Plus Ultra',NULL," +
		" 'Pacific Grove','California','USA',NULL,2019,NULL)," +
		"('frc7414',7414,'PV Robotics',NULL," +
		" 'Collegeville','Pennsylvania','USA',NULL,2019,NULL)," +
		"('frc7415',7415,'Jaguar Robotics',NULL," +
		" 'West Hills','California','USA',NULL,2019,NULL)," +
		"('frc7416',7416,'Northern Horizons','http://robotics.whitemountain.org'," +
		" 'Bethlehem','New Hampshire','USA',NULL,2019,NULL)," +
		"('frc7417',7417,'Bionic Bombers',NULL," +
		" 'Williamsfield','Illinois','USA',NULL,2019,NULL)," +
		"('frc7418',7418,'Channelview TechnoFalcons',NULL," +
		" 'Channelview','Texas','USA',NULL,2019,NULL)," +
		"('frc7419',7419,'Tech Support','https://7419.tech'," +
		" 'Dublin','California','USA',NULL,2019,NULL)," +
		"('frc7420',7420,'Napa County Robotics - OtterBots',NULL," +
		" 'Napa','California','USA',NULL,2019,NULL)," +
		"('frc7421',7421,'PrepaTec - OVERTURE',NULL," +
		" 'Monterrey','Nuevo León','Mexico',NULL,2019,NULL)," +
		"('frc7422',7422,'WildBots',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2019,NULL)," +
		"('frc7423',7423,'GrizzBot',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2019,NULL)," +
		"('frc7424',7424,'Sin City Robotics',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2019,NULL)," +
		"('frc7425',7425,'Green Valley Robotics',NULL," +
		" 'Henderson','Nevada','USA',NULL,2019,NULL)," +
		"('frc7426',7426,'PAIR OF DICE ROBOTICS',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2019,NULL)," +
		"('frc7427',7427,'Protocol X Robotics',NULL," +
		" 'Alpharetta','Georgia','USA',NULL,2019,NULL)," +
		"('frc7428',7428,'Gigawatts',NULL," +
		" 'Fort Payne','Alabama','USA',NULL,2019,NULL)," +
		"('frc7429',7429,'Convergence',NULL," +
		" 'Midlothian','Virginia','USA',NULL,2019,NULL)," +
		"('frc7430',7430,'Marion High School Robotics',NULL," +
		" 'Marion','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7431',7431,'Wingspan','http://www.vierahawkrobotics.org'," +
		" 'Melbourne','Florida','USA',NULL,2019,NULL)," +
		"('frc7432',7432,'NOS','http://frc7432.com/'," +
		" 'Loretto','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7433',7433,'Iona Fusion',NULL," +
		" 'Port Macquarie','New South Wales','Australia',NULL,2019,NULL)," +
		"('frc7434',7434,'Beyond The Stars',NULL," +
		" 'Dayton','Ohio','USA',NULL,2019,NULL)," +
		"('frc7435',7435,'CYBERTRONICS',NULL," +
		" 'Saint Charles','Missouri','USA',NULL,2019,NULL)," +
		"('frc7436',7436,'8-Bit Miners',NULL," +
		" 'South Jordan','Utah','USA',NULL,2019,NULL)," +
		"('frc7437',7437,'Lionautics',NULL," +
		" 'Lancaster','California','USA',NULL,2019,NULL)," +
		"('frc7438',7438,'Brain Busters','http://www.brainbusters.us'," +
		" 'Sherborn','Massachusetts','USA',NULL,2019,NULL)," +
		"('frc7439',7439,'QUBIT','http://www.qubitrobotics.org'," +
		" 'İstanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7440',7440,'Robo Lions (FRC)',NULL," +
		" 'Livingston','Texas','USA',NULL,2019,NULL)," +
		"('frc7441',7441,'Lancer EVO ','https://cmageii.wixsite.com/levo'," +
		" 'Chula Vista','California','USA',NULL,2019,NULL)," +
		"('frc7442',7442,'Jaggernaut',NULL," +
		" 'Port Saint Lucie','Florida','USA',NULL,2019,NULL)," +
		"('frc7443',7443,'Overhills Jag-Wires','http://bit.ly/ohsjagwires7443'," +
		" 'Spring Lake','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7444',7444,'Moon Star Robotics',NULL," +
		" 'istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7445',7445,'Garage Robotics',NULL," +
		" 'Palo Alto','California','USA',NULL,2019,NULL)," +
		"('frc7446',7446,'S.E.A. (Sparkman Engineering Academy)','https://www.facebook.com/Sparkman-Engineering-Academy-471989276165564/'," +
		" 'Harvest','Alabama','USA',NULL,2019,NULL)," +
		"('frc7447',7447,'Rōnin Robotics','http://firstroboticsporto.wixsite.com/website'," +
		" 'Irvine','California','USA',NULL,2019,NULL)," +
		"('frc7448',7448,'S.C. Robotics',NULL," +
		" 'Adair Village','Oregon','USA',NULL,2019,NULL)," +
		"('frc7449',7449,'Lithium Robotics','http://www.lithiumrobotics.org/'," +
		" 'Acworth','Georgia','USA',NULL,2019,NULL)," +
		"('frc7450',7450,'oTECH Aimbots',NULL," +
		" 'Kissimmee','Florida','USA',NULL,2019,NULL)," +
		"('frc7451',7451,'Avenger Robotics','http://avengerrobotics.com'," +
		" 'Cumming','Georgia','USA',NULL,2019,NULL)," +
		"('frc7452',7452,'Dupo Tigers',NULL," +
		" 'Dupo','Illinois','USA',NULL,2019,NULL)," +
		"('frc7453',7453,'Lobo Robotics',NULL," +
		" 'Littlerock','California','USA',NULL,2019,NULL)," +
		"('frc7454',7454,'North High School','http://facebook.com/northengineering'," +
		" 'Evansville','Indiana','USA',NULL,2019,NULL)," +
		"('frc7455',7455,'Narbots',NULL," +
		" 'Harbor City','California','USA',NULL,2019,NULL)," +
		"('frc7456',7456,'Aurobots',NULL," +
		" 'Aurora','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7457',7457,'suPURDUEper Robotics',NULL," +
		" 'Indianapolis','Indiana','USA',NULL,2019,NULL)," +
		"('frc7458',7458,'Senior Robotics Team',NULL," +
		" 'Atatürk Mah.','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7459',7459,'TAUBATEXAS',NULL," +
		" 'Taubaté','São Paulo','Brazil',NULL,2019,NULL)," +
		"('frc7460',7460,'Avi and Friends','http://www.sylvaniastem.org'," +
		" 'Sylvania','Ohio','USA',NULL,2019,NULL)," +
		"('frc7461',7461,'Binary Circles','http://binarycircles.github.io'," +
		" 'Redmond','Washington','USA',NULL,2019,NULL)," +
		"('frc7462',7462,'Disruptive Technologies',NULL," +
		" 'Danbury','Connecticut','USA',NULL,2019,NULL)," +
		"('frc7463',7463,'Incandescent Mice','https://sites.google.com/ncsu.edu/incandescentmice/home'," +
		" 'Apex','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7464',7464,'ORION',NULL," +
		" 'Norman','Oklahoma','USA',NULL,2019,NULL)," +
		"('frc7465',7465,'MAGNETECH',NULL," +
		" 'Atakum','Samsun','Turkey',NULL,2019,NULL)," +
		"('frc7466',7466,'Cymurghs','http://cymurghs.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7467',7467,'Devils Burn-In',NULL," +
		" 'West Memphis','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7468',7468,'FireBolts','https://www.firebolts7468.com/'," +
		" 'San Francisco','California','USA',NULL,2019,NULL)," +
		"('frc7469',7469,'Bannerlords',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7470',7470,'iRAMBOTs with NASA',NULL," +
		" 'Sylvester','Georgia','USA',NULL,2019,NULL)," +
		"('frc7471',7471,'Robo Montagnards',NULL," +
		" 'Mont-Saint-Hilaire','Québec','Canada',NULL,2019,NULL)," +
		"('frc7472',7472,'R2 Jesu Robotics ','http://corjesu.org'," +
		" 'Saint Louis','Missouri','USA',NULL,2019,NULL)," +
		"('frc7473',7473,'Mechanic! at the Disco',NULL," +
		" 'Tecumseh','Oklahoma','USA',NULL,2019,NULL)," +
		"('frc7474',7474,'JXN UNITED','http://team7474.org'," +
		" 'Jackson','Mississippi','USA',NULL,2019,NULL)," +
		"('frc7475',7475,'WIRED',NULL," +
		" 'Woodstock','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7476',7476,'EOM Lions','http://team7476.ca'," +
		" 'Kanata','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7477',7477,'Giddy GOATS','https://sites.google.com/view/mishawakarobotics/'," +
		" 'Mishawaka','Indiana','USA',NULL,2019,NULL)," +
		"('frc7478',7478,'ISTECH','https://www.instagram.com/istech7478'," +
		" 'İstanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7479',7479,'Cardinal Robotics Team ',NULL," +
		" 'Elizabeth','Colorado','USA',NULL,2019,NULL)," +
		"('frc7480',7480,'Machine Mavericks','https://www.machinemavericks.com/'," +
		" 'Kingston','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7481',7481,'istiklal',NULL," +
		" 'Kastamonu','Kastamonu','Turkey',NULL,2019,NULL)," +
		"('frc7482',7482,'Lincoln Panther Robotics ',NULL," +
		" 'Riverside','California','USA',NULL,2019,NULL)," +
		"('frc7483',7483,'Mechanically Challenged',NULL," +
		" 'Viola','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7484',7484,'Wings of Steel','https://www.dodea.edu/FortCampbellHS/'," +
		" 'Fort Campbell','Kentucky','USA',NULL,2019,NULL)," +
		"('frc7485',7485,'Mo Co Robo',NULL," +
		" 'Craig','Colorado','USA',NULL,2019,NULL)," +
		"('frc7486',7486,'Hudson Hybrids',NULL," +
		" 'Hudson','Ohio','USA',NULL,2019,NULL)," +
		"('frc7487',7487,'Palm Beach County Sheriff PAL WestGate','https://www.pbcpal.org/copy-of-cabana-colony-pbg'," +
		" 'West Palm Beach','Florida','USA',NULL,2019,NULL)," +
		"('frc7488',7488,'Delray Achievement Center','http://www.achievementcentersfl.org/'," +
		" 'Delray Beach','Florida','USA',NULL,2019,NULL)," +
		"('frc7489',7489,'Milagro Center','http://www.milagrocenter.org/'," +
		" 'Delray Beach','Florida','USA',NULL,2019,NULL)," +
		"('frc7490',7490,'Boys & Girls Club of Riviera Beach','https://www.bgcpbc.org/MaxMFisherBoysAndGirls'," +
		" 'West Palm Beach','Florida','USA',NULL,2019,NULL)," +
		"('frc7491',7491,'Cyber Soldiers',NULL," +
		" 'Burton','Michigan','USA',NULL,2019,NULL)," +
		"('frc7492',7492,'Cavalier Robotics',NULL," +
		" 'Conroe','Texas','USA',NULL,2019,NULL)," +
		"('frc7493',7493,'Phoenix Force Robotics','http://PhoenixForceRobotics.com'," +
		" 'Tukwila','Washington','USA',NULL,2019,NULL)," +
		"('frc7494',7494,'Circuit Bizurkers',NULL," +
		" 'Missouri City','Texas','USA',NULL,2019,NULL)," +
		"('frc7495',7495,'SHAbotics',NULL," +
		" 'Mount Pleasant','Michigan','USA',NULL,2019,NULL)," +
		"('frc7496',7496,'Mascoutah High School',NULL," +
		" 'Mascoutah','Illinois','USA',NULL,2019,NULL)," +
		"('frc7497',7497,'MARAHO',NULL," +
		" 'Taipei','Taipei','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7498',7498,'Wingus & Dingus','http://www.wingusanddingus.com'," +
		" 'Auckland','Auckland','New Zealand',NULL,2019,NULL)," +
		"('frc7499',7499,'ABAC Robotics',NULL," +
		" 'Tifton','Georgia','USA',NULL,2019,NULL);";

	/** SQL statement to insert teams */
	static private final String SQL_INSERT_TEAMS_15 = SQL_INSERT_TEAMS +
		"('frc7500',7500,'MARAUDERS',NULL," +
		" 'Fort Lauderdale','Florida','USA',NULL,2019,NULL)," +
		"('frc7501',7501,'Golden Gears',NULL," +
		" 'Brooklyn','Michigan','USA',NULL,2019,NULL)," +
		"('frc7502',7502,'CyberBuggies Northridge High School',NULL," +
		" 'Middlebury','Indiana','USA',NULL,2019,NULL)," +
		"('frc7503',7503,'Radicubs',NULL," +
		" 'Frisco','Texas','USA',NULL,2019,NULL)," +
		"('frc7504',7504,'Brewster Cybear Bots',NULL," +
		" 'Brewster','New York','USA',NULL,2019,NULL)," +
		"('frc7505',7505,'PTU',NULL," +
		" 'SHANGHAI','Shanghai','China',NULL,2019,NULL)," +
		"('frc7506',7506,'CTC Fighting Ferrets',NULL," +
		" 'Arlington','Texas','USA',NULL,2019,NULL)," +
		"('frc7507',7507,'Terminators Educational Robotics Team','https://www.teamterminators.org/'," +
		" 'Karditsa','Thessalia','Greece',NULL,2019,NULL)," +
		"('frc7508',7508,'STartEM ','http://www.STartEM.org'," +
		" 'Temple','Texas','USA',NULL,2019,NULL)," +
		"('frc7509',7509,'STEAMTeam Robotics',NULL," +
		" 'Brantford','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7510',7510,'Tiger Prowl',NULL," +
		" 'Houston','Texas','USA',NULL,2019,NULL)," +
		"('frc7511',7511,'GWAMA FRC Robotics',NULL," +
		" 'Waco','Texas','USA',NULL,2019,NULL)," +
		"('frc7512',7512,'Lions Bots',NULL," +
		" 'Mobile','Alabama','USA',NULL,2019,NULL)," +
		"('frc7513',7513,'Muskrat','http://frcmuskrat.com'," +
		" 'Shenzhen','Guangdong','China',NULL,2019,NULL)," +
		"('frc7514',7514,'EVE Robotics',NULL," +
		" 'Atlanta','Georgia','USA',NULL,2019,NULL)," +
		"('frc7515',7515,'Dark Side Robotics',NULL," +
		" 'Parkersburg','West Virginia','USA',NULL,2019,NULL)," +
		"('frc7516',7516,'Louisville Centrons','http://gilbertsclass.com/'," +
		" 'Louisville','Kentucky','USA',NULL,2019,NULL)," +
		"('frc7517',7517,'Massis Robotics',NULL," +
		" 'Encino','California','USA',NULL,2019,NULL)," +
		"('frc7518',7518,'Generals FRC',NULL," +
		" 'Houston','Texas','USA',NULL,2019,NULL)," +
		"('frc7519',7519,'Silver Warriors','https://clewistonrobotics.org'," +
		" 'Clewiston','Florida','USA',NULL,2019,NULL)," +
		"('frc7520',7520,'Team MineKee','http://MineKee.com'," +
		" 'North York','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7521',7521,'Ultimate Robotics',NULL," +
		" 'Laredo','Texas','USA',NULL,2019,NULL)," +
		"('frc7522',7522,'Make 1 Robot','http://www.team7522.com'," +
		" 'Shanghai','Shanghai','China',NULL,2019,NULL)," +
		"('frc7523',7523,'SpringBots',NULL," +
		" 'Centurion','Gauteng','South Africa',NULL,2019,NULL)," +
		"('frc7524',7524,'Regen','http://www.team7524.com/'," +
		" 'zhengzhou','Henan','China',NULL,2019,NULL)," +
		"('frc7525',7525,'Pioneers^2','https://pioneers2.com/'," +
		" 'Nashville','Tennessee','USA',NULL,2019,NULL)," +
		"('frc7526',7526,'Welcome to team',NULL," +
		" 'kaohsiung','Kaohsiung','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7527',7527,'Kaohsiung power','https://m.facebook.com/groups/1873035996332907'," +
		" ' Kaohsiung ','Kaohsiung','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7528',7528,'Nuts and Bolts','http://lorobotics.wordpress.com'," +
		" 'Morgan Hill','California','USA',NULL,2019,NULL)," +
		"('frc7529',7529,'Mulan',NULL," +
		" 'Shenzhen ','Guangdong','China',NULL,2019,NULL)," +
		"('frc7530',7530,'Watertown-Mayer High School Robotics',NULL," +
		" 'Watertown','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7531',7531,'Servos Strike Back','https://frc75318.wixsite.com/servos-strike-back'," +
		" 'Dubuque','Iowa','USA',NULL,2019,NULL)," +
		"('frc7532',7532,'Gophertronics',NULL," +
		" 'Winnipeg','Manitoba','Canada',NULL,2019,NULL)," +
		"('frc7533',7533,'OSO-TECH',NULL," +
		" 'El Paso','Texas','USA',NULL,2019,NULL)," +
		"('frc7534',7534,'Dragonflies',NULL," +
		" 'Fort Worth','Texas','USA',NULL,2019,NULL)," +
		"('frc7535',7535,'Purple Poison',NULL," +
		" 'Arlington','Texas','USA',NULL,2019,NULL)," +
		"('frc7536',7536,'ANKA ROBOTICS',NULL," +
		" 'Başakşehir','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7537',7537,'Thunderbots',NULL," +
		" 'Henderson','Colorado','USA',NULL,2019,NULL)," +
		"('frc7538',7538,'Mountain View HS Robotics',NULL," +
		" 'Lawrenceville','Georgia','USA',NULL,2019,NULL)," +
		"('frc7539',7539,'Elev8',NULL," +
		" 'Mumbai','Maharashtra','India',NULL,2019,NULL)," +
		"('frc7540',7540,'The Sandia Singularities',NULL," +
		" 'Sandia Park','New Mexico','USA',NULL,2019,NULL)," +
		"('frc7541',7541,'Maple River Robotics',NULL," +
		" 'Mapleton','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7542',7542,'Entel Foreign language school',NULL," +
		" 'Hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7543',7543,'Graphene ',NULL," +
		" 'Hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7544',7544,'IZMIR ROBOTIK','http://www.izmirgirls.com'," +
		" 'Gaziemir','Izmir','Turkey',NULL,2019,NULL)," +
		"('frc7545',7545,'Leptechons',NULL," +
		" 'Yuma','Arizona','USA',NULL,2019,NULL)," +
		"('frc7546',7546,'Prepa Tec - MinerZ',NULL," +
		" 'Zacatecas','Zacatecas','Mexico',NULL,2019,NULL)," +
		"('frc7547',7547,'Hawkinators',NULL," +
		" 'Glendale','Arizona','USA',NULL,2019,NULL)," +
		"('frc7548',7548,'Le Jardin Academy',NULL," +
		" 'Kailua','Hawaii','USA',NULL,2019,NULL)," +
		"('frc7549',7549,'LYBOTICS YOUTH TEAM','https://www.youtube.com/channel/UCThR9n8rMhoIvyfbsIrmSHQ'," +
		" 'Tripoli','Tarabulus','Libya',NULL,2019,NULL)," +
		"('frc7550',7550,'DieForTonga',NULL," +
		" 'Nukualofa','Tongatapu','Tonga',NULL,2019,NULL)," +
		"('frc7551',7551,'Extreme Mechanism','https://www.facebook.com/ExtremeMechanism/'," +
		" 'Zhuqi','Chiayi','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7552',7552,'PERSEUS',NULL," +
		" 'HATAY','Hatay','Turkey',NULL,2019,NULL)," +
		"('frc7553',7553,'OSTC-Southwest','http://www.sweet-ostc.weebly.com'," +
		" 'Wixom','Michigan','USA',NULL,2019,NULL)," +
		"('frc7554',7554,'Green Rockets',NULL," +
		" 'Jaffa of Nazareth','HaZafon','Israel',NULL,2019,NULL)," +
		"('frc7555',7555,'Maestro',NULL," +
		" 'Lachine','Québec','Canada',NULL,2019,NULL)," +
		"('frc7556',7556,'PRISER','http://priser.tech'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7557',7557,'dasheng',NULL," +
		" 'Hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7558',7558,'ALT+F4',NULL," +
		" 'North York','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7559',7559,'Sweet Springs Robotics Team',NULL," +
		" 'Sweet Springs','Missouri','USA',NULL,2019,NULL)," +
		"('frc7560',7560,'Doodle Bots',NULL," +
		" 'Chicago','Illinois','USA',NULL,2019,NULL)," +
		"('frc7561',7561,'Blue Flamingos','http://team7561.wordpress.com/'," +
		" 'Wollongong','New South Wales','Australia',NULL,2019,NULL)," +
		"('frc7562',7562,'NSU Demons Robotics',NULL," +
		" 'Natchitoches','Louisiana','USA',NULL,2019,NULL)," +
		"('frc7563',7563,'SESI SENAI MEGAZORD','http://www.sesisp.org.br/'," +
		" 'Jundiaí','São Paulo','Brazil',NULL,2019,NULL)," +
		"('frc7564',7564,'Lakers',NULL," +
		" 'Oshawa','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7565',7565,'SESI SENAI São Paulo ROBONÁTICOS','http://www.sesisp.org.br/'," +
		" 'São Paulo','São Paulo','Brazil',NULL,2019,NULL)," +
		"('frc7566',7566,'SESI SENAI SÃO PAULO NIÓBIO','https://niobio7566.com.br/'," +
		" 'Campinas','São Paulo','Brazil',NULL,2019,NULL)," +
		"('frc7567',7567,'SESI SENAI SÃO PAULO OCTOPUS','http://www.sesisp.org.br/'," +
		" 'Bauru','São Paulo','Brazil',NULL,2019,NULL)," +
		"('frc7568',7568,'Tro-Bots',NULL," +
		" 'Marianna','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7569',7569,'Optimus Robotics',NULL," +
		" 'Atasehir','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7570',7570,'New Tech','http://newtech.edu.pl/'," +
		" 'Zamosc','Lubelskie','Poland',NULL,2019,NULL)," +
		"('frc7571',7571,'SESI/DN #1','http://www.portaldaindustria.com.br/sesi/institucional/departamento-nacional/'," +
		" 'Brasília','Distrito Federal','Brazil',NULL,2019,NULL)," +
		"('frc7572',7572,'Uplift Robotics',NULL," +
		" 'Lake Forest','California','USA',NULL,2019,NULL)," +
		"('frc7573',7573,'MULEBOTICS','https://classroom.google.com/u/1/c/MTY4NDkwOTg5MDJa'," +
		" 'Muleshoe','Texas','USA',NULL,2019,NULL)," +
		"('frc7574',7574,'Les Sentinelles',NULL," +
		" 'Montreal','Québec','Canada',NULL,2019,NULL)," +
		"('frc7575',7575,'Stark Robotics','http://www.starkroboticsfrc.com'," +
		" 'Büyükçekmece','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7576',7576,'FMWill',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7577',7577,'VELABOTS',NULL," +
		" 'Velardeña','Durango','Mexico',NULL,2019,NULL)," +
		"('frc7578',7578,'Jamestown Juggernauts','https://www.frc7578.org/'," +
		" 'Jamestown','North Dakota','USA',NULL,2019,NULL)," +
		"('frc7579',7579,'SESI/DN #2','http://www.portaldaindustria.com.br/sesi/institucional/departamento-nacional/'," +
		" 'Brasília','Distrito Federal','Brazil',NULL,2019,NULL)," +
		"('frc7580',7580,'SESI/DN #3','http://www.portaldaindustria.com.br/sesi/institucional/departamento-nacional/'," +
		" 'Brasília','Distrito Federal','Brazil',NULL,2019,NULL)," +
		"('frc7581',7581,'SESI/DN #4','http://www.portaldaindustria.com.br/sesi/institucional/departamento-nacional/'," +
		" 'Brasília','Distrito Federal','Brazil',NULL,2019,NULL)," +
		"('frc7582',7582,'SESI/DN #5','http://www.portaldaindustria.com.br/sesi/institucional/departamento-nacional/'," +
		" 'Brasília','Distrito Federal','Brazil',NULL,2019,NULL)," +
		"('frc7583',7583,'Elonera Embers','https://www.elonerarobotics.com.au'," +
		" 'Mount Ousley ','New South Wales','Australia',NULL,2019,NULL)," +
		"('frc7584',7584,'RoverBots',NULL," +
		" 'Easton','Pennsylvania','USA',NULL,2019,NULL)," +
		"('frc7585',7585,'The Thunder',NULL," +
		" 'Büyükçekmece','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7586',7586,'ShenHao_The Salted Fishballs','https://www.instagram.com/frc7586/'," +
		" 'Hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7587',7587,'Metuchen Momentum','http://www.metuchenmomentum.org'," +
		" 'Metuchen','New Jersey','USA',NULL,2019,NULL)," +
		"('frc7588',7588,'H.I.I Robot',NULL," +
		" 'HANGZHOU','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7589',7589,'Lishan Blue Magpie','https://www.facebook.com/FRC7589'," +
		" 'Taipei','Taipei Special Municipality','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7590',7590,'BotRaiders',NULL," +
		" 'Trois-Rivieres','Québec','Canada',NULL,2019,NULL)," +
		"('frc7591',7591,'Aurora',NULL," +
		" 'Beijing','Beijing','China',NULL,2019,NULL)," +
		"('frc7592',7592,'Sugar Nerds',NULL," +
		" 'Basseterre','Saint George Basseterre','Saint Kitts and Nevis',NULL,2019,NULL)," +
		"('frc7593',7593,'SymbiOHSis',NULL," +
		" 'Auckland','Auckland','New Zealand',NULL,2019,NULL)," +
		"('frc7594',7594,'Nautilus',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2019,NULL)," +
		"('frc7595',7595,'Crosspoint at Shanghai & Suzhou United','http://www.sz3z.cn/Default.aspx'," +
		" 'Suzhou','Jiangsu','China',NULL,2019,NULL)," +
		"('frc7596',7596,'Trojan Horses',NULL," +
		" 'Rushford','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7597',7597,'Team HorsePowered',NULL," +
		" 'Fraser','Michigan','USA',NULL,2019,NULL)," +
		"('frc7598',7598,'SCA Constellations','http://www.scaconstellations.com'," +
		" 'Wixom','Michigan','USA',NULL,2019,NULL)," +
		"('frc7599',7599,'Blue Pride',NULL," +
		" 'Philadelphia','Pennsylvania','USA',NULL,2019,NULL)," +
		"('frc7600',7600,'S.M.C.','http://www.smc7600.com'," +
		" 'Istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7601',7601,'Iron Phoenix',NULL," +
		" 'shenzhen','Guangdong','China',NULL,2019,NULL)," +
		"('frc7602',7602,'Spartronics',NULL," +
		" 'Scottville','Michigan','USA',NULL,2019,NULL)," +
		"('frc7603',7603,'Bill Hogarth Secondary School',NULL," +
		" 'Markham','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7604',7604,'Sickle and Hammer','http://frc.xshs.cn'," +
		" 'hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7605',7605,'ASTRO','https://astro7605.infast.ca'," +
		" 'Sainte-Therese','Québec','Canada',NULL,2019,NULL)," +
		"('frc7606',7606,'Liberty Robotics',NULL," +
		" 'Parma Hts.','Ohio','USA',NULL,2019,NULL)," +
		"('frc7607',7607,'Pass the Wrench',NULL," +
		" 'Palmdale','California','USA',NULL,2019,NULL)," +
		"('frc7608',7608,'Owlenators',NULL," +
		" 'Chicago','Illinois','USA',NULL,2019,NULL)," +
		"('frc7609',7609,'Half Bananas','http://www.aqsa.edu'," +
		" 'Bridgeview','Illinois','USA',NULL,2019,NULL)," +
		"('frc7610',7610,'HZYulan','http://www.alevel-hz.com'," +
		" 'hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7611',7611,'AMAL HAWKS ',NULL," +
		" 'ISTANBUL','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7612',7612,'HIS ',NULL," +
		" 'Hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7613',7613,'Revoltage',NULL," +
		" 'silivri','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7614',7614,'NHS Raiders',NULL," +
		" 'Newmarket','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7615',7615,'PatBots',NULL," +
		" 'Saint-Laurent','Québec','Canada',NULL,2019,NULL)," +
		"('frc7616',7616,'Sweeny Bulldog Robotics',NULL," +
		" 'Sweeny','Texas','USA',NULL,2019,NULL)," +
		"('frc7617',7617,'RoboBlazers','https://uhsroboblazers.weebly.com/'," +
		" 'Carmel','Indiana','USA',NULL,2019,NULL)," +
		"('frc7618',7618,'WHS Vikings',NULL," +
		" 'Williams','Arizona','USA',NULL,2019,NULL)," +
		"('frc7619',7619,'illumiBOTi','https://illumiboti.wixsite.com/illumiboti'," +
		" 'Manitowoc','Wisconsin','USA',NULL,2019,NULL)," +
		"('frc7620',7620,'Anthem Bolts',NULL," +
		" 'Anthem','Arizona','USA',NULL,2019,NULL)," +
		"('frc7621',7621,'Iron Rangers',NULL," +
		" 'Spring Branch','Texas','USA',NULL,2019,NULL)," +
		"('frc7622',7622,'Ten More Tons','https://www.facebook.com/TenTonRobotics/'," +
		" 'West Vancouver','British Columbia','Canada',NULL,2019,NULL)," +
		"('frc7623',7623,'Morebotics','https://www.frc7623.com'," +
		" 'Hamilton','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7624',7624,'YOUNG',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2019,NULL)," +
		"('frc7625',7625,'STORMING','http:///http：//www.stmrobot.cn'," +
		" 'Changsha','Hunan','China',NULL,2019,NULL)," +
		"('frc7626',7626,'NLHS','http://www.nlhs.tyc.edu.tw'," +
		" 'Taoyuan','Taoyuan','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7627',7627,'Descendants of G.O.R.T.',NULL," +
		" 'Monroe','Washington','USA',NULL,2019,NULL)," +
		"('frc7628',7628,'LISA',NULL," +
		" 'Altdorf','Uri','Switzerland',NULL,2019,NULL)," +
		"('frc7629',7629,'14 Rohan',NULL," +
		" 'Hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7630',7630,'SCIE FIRST',NULL," +
		" 'Shenzhen','Guangdong','China',NULL,2019,NULL)," +
		"('frc7631',7631,'Cool Guy',NULL," +
		" 'Toyota','Aichi','Japan',NULL,2019,NULL)," +
		"('frc7632',7632,'An Kang Robotics Maker','https://www.facebook.com/AK-MAKER-424937354561868/'," +
		" 'New Taipei City','Taipei','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7633',7633,'GRYPHUS ROBOTICS',NULL," +
		" 'NEIVA','Huila','Colombia',NULL,2019,NULL)," +
		"('frc7634',7634,'New Hawks',NULL," +
		" 'Sandy','Utah','USA',NULL,2019,NULL)," +
		"('frc7635',7635,'SWIS ROBOTICS','https://innovation.swis.cn/swis-frc.html'," +
		" 'Shenzhen','Guangdong','China',NULL,2019,NULL)," +
		"('frc7636',7636,'Robomania',NULL," +
		" 'Taichung','Taichung','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7637',7637,'Asimov Effect',NULL," +
		" 'Sinop','Sinop','Turkey',NULL,2019,NULL)," +
		"('frc7638',7638,'Northern Exposure Robotics Division (Gogebic Range Robotics)','http://www.facebook.com/gogebicrangerobotics/'," +
		" 'Bessemer','Michigan','USA',NULL,2019,NULL)," +
		"('frc7639',7639,'ENJOY VARIETY',NULL," +
		" 'SHENZHEN','Anhui','China',NULL,2019,NULL)," +
		"('frc7640',7640,'CK Steam UNION',NULL," +
		" 'hangzhou','Zhejiang','China',NULL,2019,NULL)," +
		"('frc7641',7641,'Normal Force',NULL," +
		" 'Tainan','Tainan','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7642',7642,'BozlakS','http://mucurseyhedebalimtal.meb.k12.tr/'," +
		" 'kırşehir','Kirsehir','Turkey',NULL,2019,NULL)," +
		"('frc7643',7643,'UWCDR',NULL," +
		" 'Dilijan','Tavuš','Armenia',NULL,2019,NULL)," +
		"('frc7644',7644,'CCHS','http://www.cchs.chc.edu.tw/bin/home.php'," +
		" ' Changhua City','Changhua','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7645',7645,'NK MTC',NULL," +
		" 'Taipei City','Taipei','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7646',7646,'Cadets',NULL," +
		" 'Cresco','Iowa','USA',NULL,2019,NULL)," +
		"('frc7647',7647,'Juventics',NULL," +
		" 'Guadalupe','Nuevo León','Mexico',NULL,2019,NULL)," +
		"('frc7648',7648,'McMainFrame',NULL," +
		" 'New Orleans','Louisiana','USA',NULL,2019,NULL)," +
		"('frc7649',7649,'Feles Numine',NULL," +
		" 'Santa Fe','New Mexico','USA',NULL,2019,NULL)," +
		"('frc7650',7650,'Monte Pacis Robotics',NULL," +
		" 'Gossau','Sankt Gallen','Switzerland',NULL,2019,NULL)," +
		"('frc7651',7651,'Bethlehem Eagles',NULL," +
		" 'Bethlehem','New York','USA',NULL,2019,NULL)," +
		"('frc7652',7652,'MiamiBeachBots',NULL," +
		" 'Miami Beach','Florida','USA',NULL,2019,NULL)," +
		"('frc7653',7653,'Minebots',NULL," +
		" 'Santiago Papasquiaro','Durango','Mexico',NULL,2019,NULL)," +
		"('frc7654',7654,'Robot In Box',NULL," +
		" 'Henderson','Nevada','USA',NULL,2019,NULL)," +
		"('frc7655',7655,'Hamburg Robotics Team',NULL," +
		" 'Hamburg','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7656',7656,'MC Hammers',NULL," +
		" 'Michigan Center','Michigan','USA',NULL,2019,NULL)," +
		"('frc7657',7657,'ThunderBots',NULL," +
		" 'Evansville','Indiana','USA',NULL,2019,NULL)," +
		"('frc7658',7658,'MagiTech',NULL," +
		" 'Kalamazoo','Michigan','USA',NULL,2019,NULL)," +
		"('frc7659',7659,'HNMCS Robotics',NULL," +
		" 'Mississauga','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7660',7660,'The Byting Irish',NULL," +
		" 'Ann Arbor','Michigan','USA',NULL,2019,NULL)," +
		"('frc7661',7661,'SciHigh Nautili',NULL," +
		" 'New Orleans','Louisiana','USA',NULL,2019,NULL)," +
		"('frc7662',7662,'BuzzBot',NULL," +
		" 'Lebanon','Missouri','USA',NULL,2019,NULL)," +
		"('frc7663',7663,'Sleuth Robotics',NULL," +
		" 'Fresno','California','USA',NULL,2019,NULL)," +
		"('frc7664',7664,'Big Celtic 6',NULL," +
		" 'Guelph','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7665',7665,'GL Gadgets',NULL," +
		" 'Grand Ledge','Michigan','USA',NULL,2019,NULL)," +
		"('frc7667',7667,'OtterBots','https://www.otterbots7667.com'," +
		" 'Napa','California','USA',NULL,2019,NULL)," +
		"('frc7668',7668,'GatorBots',NULL," +
		" 'New York','New York','USA',NULL,2019,NULL)," +
		"('frc7669',7669,'TECHNO R4V7NS',NULL," +
		" 'Gomez Palacio','Durango','Mexico',NULL,2019,NULL)," +
		"('frc7670',7670,'Highlanders',NULL," +
		" 'Claysville','Pennsylvania','USA',NULL,2019,NULL)," +
		"('frc7671',7671,'Fire Hazard','https://flafrcclub.wixsite.com/7671'," +
		" 'Creedmoor','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7672',7672,'SCORPIONS','http://www.scorpions7672.com'," +
		" 'ANTALYA','Antalya','Turkey',NULL,2019,NULL)," +
		"('frc7673',7673,'FORMOSAN SIKA',NULL," +
		" 'New Taipei City','Taipei','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7674',7674,'RaiderBots',NULL," +
		" 'Rochester','New Hampshire','USA',NULL,2019,NULL)," +
		"('frc7675',7675,'Spark Guardians ',NULL," +
		" 'Gatesville','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7676',7676,'THS RoboDogs',NULL," +
		" 'Thomson','Georgia','USA',NULL,2019,NULL)," +
		"('frc7677',7677,'Swanville Bulldogs Robotics Team',NULL," +
		" 'Swanville','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7678',7678,'DONKERRY',NULL," +
		" 'Ciudad de Mexico','Distrito Federal','Mexico',NULL,2019,NULL)," +
		"('frc7679',7679,'ROBOVAN',NULL," +
		" 'Ipekyolu','Van','Turkey',NULL,2019,NULL)," +
		"('frc7680',7680,'DFORCE',NULL," +
		" 'ANKARA','Ankara','Turkey',NULL,2019,NULL)," +
		"('frc7681',7681,'haxhax','http://afyon.gsb.gov.tr/'," +
		" 'Afyonkarahisar','Afyonkarahisar','Turkey',NULL,2019,NULL)," +
		"('frc7682',7682,'FUSION ROBOTICS',NULL," +
		" 'Bahçelievler','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7683',7683,'Harmonia',NULL," +
		" 'istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7684',7684,'first nilufer team','https://firstniluferteam9207.blogspot.com'," +
		" 'BURSA','Bursa','Turkey',NULL,2019,NULL)," +
		"('frc7685',7685,'SZALTech',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7686',7686,'Acalanes High School',NULL," +
		" 'Lafayette','California','USA',NULL,2019,NULL)," +
		"('frc7687',7687,'Corcebots 5.0',NULL," +
		" 'BERMEJILLO','Durango','Mexico',NULL,2019,NULL)," +
		"('frc7688',7688,'Creekside Voyagers',NULL," +
		" 'Burnaby','British Columbia','Canada',NULL,2019,NULL)," +
		"('frc7689',7689,'WMC Robotics',NULL," +
		" 'Muskegon','Michigan','USA',NULL,2019,NULL)," +
		"('frc7690',7690,'Iron Works Robotics',NULL," +
		" 'Richmond Hill','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7691',7691,'Oakley',NULL," +
		" 'Tomball','Texas','USA',NULL,2019,NULL)," +
		"('frc7692',7692,'AGBU Manoogian Robotics',NULL," +
		" 'Southfield','Michigan','USA',NULL,2019,NULL)," +
		"('frc7693',7693,'Craft Robotics',NULL," +
		" 'Morehead','Kentucky','USA',NULL,2019,NULL)," +
		"('frc7694',7694,'NORTHWEST CATHOLIC','https://www.northwestcatholic.org/index.cfm'," +
		" 'West Hartford','Connecticut','USA',NULL,2019,NULL)," +
		"('frc7695',7695,'Trojan Thunder','https://trojanthunder7695.com/'," +
		" 'Highland','Indiana','USA',NULL,2019,NULL)," +
		"('frc7696',7696,'Gear Hounds ',NULL," +
		" 'Grandview','Washington','USA',NULL,2019,NULL)," +
		"('frc7699',7699,'VeneBot Robotics','http://sites.google.com/view/venebot'," +
		" 'Maracaibo','Zulia','Venezuela',NULL,2019,NULL)," +
		"('frc7700',7700,'West Tech Paladins',NULL," +
		" 'Westmount','Québec','Canada',NULL,2019,NULL)," +
		"('frc7701',7701,'Aérobot',NULL," +
		" 'Montreal','Québec','Canada',NULL,2019,NULL)," +
		"('frc7702',7702,'Lions.exe','http://www.clarendonlions.org'," +
		" 'Clarendon','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7703',7703,'RoboBears',NULL," +
		" 'Cave Creek','Arizona','USA',NULL,2019,NULL)," +
		"('frc7704',7704,'Kimball E-TECH @ Mountain View College ','https://www.dallasisd.org/Page/2562'," +
		" 'Dallas','Texas','USA',NULL,2019,NULL)," +
		"('frc7705',7705,'Mustangs Robotics',NULL," +
		" 'Sugar Land','Texas','USA',NULL,2019,NULL)," +
		"('frc7706',7706,'Rottweilers Robotics',NULL," +
		" 'Torreón','Coahuila','Mexico',NULL,2019,NULL)," +
		"('frc7707',7707,'Cutting Edge Robotics',NULL," +
		" 'Shanghai','Shanghai','China',NULL,2019,NULL)," +
		"('frc7708',7708,'Tiger Nation ',NULL," +
		" 'Terrell','Texas','USA',NULL,2019,NULL)," +
		"('frc7709',7709,'Formosa Pangolin',NULL," +
		" 'Ban Qiao District','Taipei Special Municipality','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7710',7710,'Mowat Mustangs',NULL," +
		" 'Scarborough','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7711',7711,'Applewood Axemen ',NULL," +
		" 'Mississauga','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7712',7712,'ACCN UMOJA',NULL," +
		" 'Scarborough','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7713',7713,'The Sons & Daughters of Ivaldi, Norse-code',NULL," +
		" 'Felch','Michigan','USA',NULL,2019,NULL)," +
		"('frc7714',7714,'RedRoad',NULL," +
		" 'Washington','District of Columbia','USA',NULL,2019,NULL)," +
		"('frc7715',7715,'Robo-Banditos',NULL," +
		" 'Elizabeth City','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7716',7716,'JLW Formula 01 Robotic''s Team',NULL," +
		" 'Detroit','Michigan','USA',NULL,2019,NULL)," +
		"('frc7717',7717,'Team Steam Punks',NULL," +
		" 'Santo Domingo','Distrito Nacional','Dominican Republic',NULL,2019,NULL)," +
		"('frc7718',7718,'Space Bees',NULL," +
		" 'Ulster Park','New York','USA',NULL,2019,NULL)," +
		"('frc7719',7719,'Java Knights ','http://r2045.com'," +
		" 'Depok','Jawa Barat','Indonesia',NULL,2019,NULL)," +
		"('frc7720',7720,'⚠️ Dallas Area Robotics Experience ⚠️ ','http:///www.firstinspires.org'," +
		" 'Carrollton','Texas','USA',NULL,2019,NULL)," +
		"('frc7721',7721,'UHS Wolfpack',NULL," +
		" 'Markham','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7722',7722,'Resurrection Fire Birds',NULL," +
		" 'Kitchener','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7723',7723,'Westgate Tigers',NULL," +
		" 'Thunder Bay','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7724',7724,'Molokai Robotics','https://molokairobotics.weebly.com/'," +
		" 'Hoolehua','Hawaii','USA',NULL,2019,NULL)," +
		"('frc7725',7725,'PrepaTec - NUTS & VOLTS',NULL," +
		" 'Monterrey','Nuevo León','Mexico',NULL,2019,NULL)," +
		"('frc7726',7726,'ShanDong Experimental High School',NULL," +
		" 'Jinan','Shandong','China',NULL,2019,NULL)," +
		"('frc7727',7727,'Iteration Beaker',NULL," +
		" 'Jinan','Shandong','China',NULL,2019,NULL)," +
		"('frc7728',7728,'CAAFE',NULL," +
		" 'Windermere','Florida','USA',NULL,2019,NULL)," +
		"('frc7729',7729,'RAMs',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7730',7730,'Aperture Robotics',NULL," +
		" 'Thiells','New York','USA',NULL,2019,NULL)," +
		"('frc7731',7731,'CTB Robotics Team',NULL," +
		" 'Thai Binh','Thai Binh','Vietnam',NULL,2019,NULL)," +
		"('frc7732',7732,'Artificial Intelligence',NULL," +
		" 'Wuxi','Jiangsu','China',NULL,2019,NULL)," +
		"('frc7733',7733,'GreenHSGS Robotics Team',NULL," +
		" 'Ha Noi','Ha Noi','Vietnam',NULL,2019,NULL)," +
		"('frc7734',7734,'High School of Education Sciences','https://www.hes.vnu.edu.vn'," +
		" 'Hanoi','Ha Noi','Vietnam',NULL,2019,NULL)," +
		"('frc7735',7735,'Milliken Mills Knights',NULL," +
		" 'Markham','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7736',7736,'DRAMA KINGS',NULL," +
		" 'Qingdao','Shandong','China',NULL,2019,NULL)," +
		"('frc7737',7737,'GVR Raptors',NULL," +
		" 'Denver','Colorado','USA',NULL,2019,NULL)," +
		"('frc7738',7738,'Helion','http://www.helionrobotics.com/'," +
		" 'Beijing','Beijing','China',NULL,2019,NULL)," +
		"('frc7739',7739,'Royal Tech Warriors',NULL," +
		" 'Tarboro','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7740',7740,'Iron Donkeys','http://www.facebook.com/Iron-Donkeys-471192340022970/'," +
		" 'San Agustin Tlaxiaca','Hidalgo','Mexico',NULL,2019,NULL)," +
		"('frc7741',7741,'South Taiwan AI Robot','https://sites.google.com/site/rsshdat/frc'," +
		" 'Kaohsiung','Kaohsiung','Chinese Taipei',NULL,2019,NULL)," +
		"('frc7742',7742,'Cosmos Robot Works','http://www.cosmosrobotworks.com'," +
		" 'Üsküdar','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7743',7743,'ShanDong Experimental High School',NULL," +
		" 'Jinan','Shandong','China',NULL,2019,NULL)," +
		"('frc7744',7744,'Wild Cards',NULL," +
		" 'Collinsville','Oklahoma','USA',NULL,2019,NULL)," +
		"('frc7745',7745,'College Heights',NULL," +
		" 'Guelph','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7746',7746,'Culture','http://www.mentorsnmotion.org'," +
		" 'Saint Louis','Missouri','USA',NULL,2019,NULL)," +
		"('frc7747',7747,'Hancock High Tech ',NULL," +
		" 'Saint Louis','Missouri','USA',NULL,2019,NULL)," +
		"('frc7748',7748,'TECHTOLIA ROBOTIC',NULL," +
		" 'İstanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7749',7749,'Level UP',NULL," +
		" 'Dallas','Texas','USA',NULL,2019,NULL)," +
		"('frc7750',7750,'Alpine robo bucks',NULL," +
		" 'Alpine','Texas','USA',NULL,2019,NULL)," +
		"('frc7751',7751,'The A.E.A.-ALTimate Electric Androids ',NULL," +
		" 'West Branch','Michigan','USA',NULL,2019,NULL)," +
		"('frc7752',7752,'ROBYBAM',NULL," +
		" 'Tijuana','Baja California','Mexico',NULL,2019,NULL)," +
		"('frc7753',7753,'Tj Robotics',NULL," +
		" 'Tijuana','Baja California','Mexico',NULL,2019,NULL)," +
		"('frc7754',7754,'STAR',NULL," +
		" 'Saint Augustine','Florida','USA',NULL,2019,NULL)," +
		"('frc7755',7755,'Phantom Circuits ',NULL," +
		" 'Casa Grande','Arizona','USA',NULL,2019,NULL)," +
		"('frc7756',7756,'Bionic Blue Devils',NULL," +
		" 'Hopewell','Virginia','USA',NULL,2019,NULL)," +
		"('frc7757',7757,'Atomic Dishwashers',NULL," +
		" 'Owen Sound','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7758',7758,'MEVOLUTION',NULL," +
		" 'ISTANBUL','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7759',7759,'Irvington Robotics ',NULL," +
		" 'Irvington','New York','USA',NULL,2019,NULL)," +
		"('frc7760',7760,'PomTech','https://maxliu2020.wixsite.com/pomfretrobotics'," +
		" 'Pomfret','Connecticut','USA',NULL,2019,NULL)," +
		"('frc7761',7761,'Eagle Tech','http://www.eagletechrobotics.com'," +
		" 'Kartal','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7762',7762,'AutoPilots',NULL," +
		" 'Warren','Michigan','USA',NULL,2019,NULL)," +
		"('frc7763',7763,'CARRBOROBOTICS',NULL," +
		" 'Carrboro','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7764',7764,'Voltech',NULL," +
		" 'Santiago','Región Metropolitana de Santiago','Chile',NULL,2019,NULL)," +
		"('frc7765',7765,'STEM SOPÓ','https://www.facebook.com/roboticasopo/'," +
		" 'SOPÓ','Cundinamarca','Colombia',NULL,2019,NULL)," +
		"('frc7766',7766,'Panthers',NULL," +
		" 'Heber Springs','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7767',7767,'Rock City Robots','http://www.rockcityrobots.com'," +
		" 'Little Rock','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7768',7768,'West Iron County Wykons',NULL," +
		" 'Iron River','Michigan','USA',NULL,2019,NULL)," +
		"('frc7769',7769,'OS CREW',NULL," +
		" 'Royal Oak','Michigan','USA',NULL,2019,NULL)," +
		"('frc7770',7770,'Infinite Voltage',NULL," +
		" 'Bel Air','Maryland','USA',NULL,2019,NULL)," +
		"('frc7771',7771,'Lakewood Robotics',NULL," +
		" 'Lakewood','New Jersey','USA',NULL,2019,NULL)," +
		"('frc7772',7772,'The Lucky 7''s',NULL," +
		" 'Montrose','Michigan','USA',NULL,2019,NULL)," +
		"('frc7773',7773,'UB Ignite','http://www.mineralarea.edu/upwardbound'," +
		" 'Park Hills','Missouri','USA',NULL,2019,NULL)," +
		"('frc7774',7774,'Duval Techs','https://leaptechinc.org/'," +
		" 'Jacksonville','Florida','USA',NULL,2019,NULL)," +
		"('frc7775',7775,'Dunedin Talons',NULL," +
		" 'Dunedin','Florida','USA',NULL,2019,NULL)," +
		"('frc7776',7776,'MVR',NULL," +
		" 'Okanogan','Washington','USA',NULL,2019,NULL)," +
		"('frc7777',7777,'GHS Bruins Robotics',NULL," +
		" 'Greenfield','California','USA',NULL,2019,NULL)," +
		"('frc7778',7778,'South High Rebels',NULL," +
		" 'Bakersfield','California','USA',NULL,2019,NULL)," +
		"('frc7779',7779,'Team Murdoch',NULL," +
		" 'Perth','Western Australia','Australia',NULL,2019,NULL)," +
		"('frc7780',7780,'Colyton High School',NULL," +
		" 'Colyton','New South Wales','Australia',NULL,2019,NULL)," +
		"('frc7781',7781,'Port Moody Robotics',NULL," +
		" 'Port Moody','British Columbia','Canada',NULL,2019,NULL)," +
		"('frc7782',7782,'Wolverine Circuit Breakers',NULL," +
		" 'Rock','Michigan','USA',NULL,2019,NULL)," +
		"('frc7783',7783,'St. Mary''s Preparatory',NULL," +
		" 'Orchard Lake','Michigan','USA',NULL,2019,NULL)," +
		"('frc7784',7784,'Vassar Voltage',NULL," +
		" 'Bay City','Michigan','USA',NULL,2019,NULL)," +
		"('frc7785',7785,'Mechanical Mustangs','http://www.shencsd.com'," +
		" 'Shenandoah','Iowa','USA',NULL,2019,NULL)," +
		"('frc7786',7786,'Power Surge',NULL," +
		" 'Woodbury','Connecticut','USA',NULL,2019,NULL)," +
		"('frc7787',7787,'Reynolds Reybots',NULL," +
		" 'Victoria','British Columbia','Canada',NULL,2019,NULL)," +
		"('frc7788',7788,'Robotic Eagles',NULL," +
		" 'Inkster','Michigan','USA',NULL,2019,NULL)," +
		"('frc7789',7789,'Oakland Panthers',NULL," +
		" 'Detroit','Michigan','USA',NULL,2019,NULL)," +
		"('frc7790',7790,'Baywatch Robotics',NULL," +
		" 'Harbor Springs','Michigan','USA',NULL,2019,NULL)," +
		"('frc7791',7791,'Lincoln Abes',NULL," +
		" 'Clinton Township','Michigan','USA',NULL,2019,NULL)," +
		"('frc7792',7792,'ROBOTAYFA',NULL," +
		" 'Reyhanlı','Hatay','Turkey',NULL,2019,NULL)," +
		"('frc7793',7793,'Team Zimbabwe','http://impacthubharare.net/robotics-challenges/'," +
		" 'Harare','Harare','Zimbabwe',NULL,2019,NULL)," +
		"('frc7794',7794,'Marion Eagle Engineers',NULL," +
		" 'Marion','Michigan','USA',NULL,2019,NULL)," +
		"('frc7795',7795,'Norse Code',NULL," +
		" 'Winthrop','Massachusetts','USA',NULL,2019,NULL)," +
		"('frc7796',7796,'Burnett Robotics',NULL," +
		" 'Richmond','British Columbia','Canada',NULL,2019,NULL)," +
		"('frc7797',7797,'Cloquet''s RipSaw Robotics','https://ripsawrobotics.weebly.com/'," +
		" 'Cloquet','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7798',7798,'Cougar Coders',NULL," +
		" 'Helena','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7799',7799,'Phat Cache','https://www.phatcache.com/'," +
		" 'Calgary','Alberta','Canada',NULL,2019,NULL)," +
		"('frc7800',7800,'Electric Comets',NULL," +
		" 'Windsor','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7801',7801,'Frisch CouGears',NULL," +
		" 'Paramus','New Jersey','USA',NULL,2019,NULL)," +
		"('frc7802',7802,'Dust Devils',NULL," +
		" 'Dayton','Nevada','USA',NULL,2019,NULL)," +
		"('frc7803',7803,'Pioneer Robotics','https://www.omakpioneerrobotics.org'," +
		" 'Omak','Washington','USA',NULL,2019,NULL)," +
		"('frc7804',7804,'Team Sabotage',NULL," +
		" 'Cabot','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7805',7805,'Walnut Grove Gators',NULL," +
		" 'Langley','British Columbia','Canada',NULL,2019,NULL)," +
		"('frc7806',7806,'KDHS Kolts',NULL," +
		" 'Kapuskasing','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7807',7807,'Cambridge Panthers',NULL," +
		" 'Garden City','Michigan','USA',NULL,2019,NULL)," +
		"('frc7808',7808,'Dragoneers',NULL," +
		" 'Kalkaska','Michigan','USA',NULL,2019,NULL)," +
		"('frc7809',7809,'Valhalla Nation',NULL," +
		" 'Bangor','Michigan','USA',NULL,2019,NULL)," +
		"('frc7810',7810,'Oakridge Eagles',NULL," +
		" 'Muskegon','Michigan','USA',NULL,2019,NULL)," +
		"('frc7811',7811,'StP Tigers',NULL," +
		" 'Battle Creek','Michigan','USA',NULL,2019,NULL)," +
		"('frc7812',7812,'Robot Warriors',NULL," +
		" 'Ionia','Michigan','USA',NULL,2019,NULL)," +
		"('frc7813',7813,'New Haven Rockets Robotics',NULL," +
		" 'New Haven','Michigan','USA',NULL,2019,NULL)," +
		"('frc7814',7814,'River Valley Mustang Gearheads',NULL," +
		" 'Three Oaks','Michigan','USA',NULL,2019,NULL)," +
		"('frc7815',7815,'CavBots',NULL," +
		" 'Spencer','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7816',7816,'Hematite Robotics',NULL," +
		" 'Ishpeming','Michigan','USA',NULL,2019,NULL)," +
		"('frc7817',7817,'Lansing Catholic Cougars',NULL," +
		" 'Lansing','Michigan','USA',NULL,2019,NULL)," +
		"('frc7818',7818,'Williamston High School',NULL," +
		" 'Williamston','Michigan','USA',NULL,2019,NULL)," +
		"('frc7819',7819,'Automataardvarks',NULL," +
		" 'Portland','Oregon','USA',NULL,2019,NULL)," +
		"('frc7820',7820,'SRT UNIT (Superior Robotics Team) ',NULL," +
		" 'Detroit','Michigan','USA',NULL,2019,NULL)," +
		"('frc7821',7821,'Ravenna Bulldogs Robotics',NULL," +
		" 'Ravenna','Michigan','USA',NULL,2019,NULL)," +
		"('frc7822',7822,'Generals Robotics',NULL," +
		" 'South Hamilton','Massachusetts','USA',NULL,2019,NULL)," +
		"('frc7823',7823,'HuronBots',NULL," +
		" 'Rogers City','Michigan','USA',NULL,2019,NULL)," +
		"('frc7824',7824,'165th Airlift Wing Air National Guard Eagles',NULL," +
		" 'Savannah','Georgia','USA',NULL,2019,NULL)," +
		"('frc7825',7825,'DAMBAZE',NULL," +
		" 'İZMİT','Kocaeli','Turkey',NULL,2019,NULL)," +
		"('frc7826',7826,'King''s Logic',NULL," +
		" 'Iron Mountain','Michigan','USA',NULL,2019,NULL)," +
		"('frc7827',7827,'Yooper Robotics',NULL," +
		" 'Crystal Falls','Michigan','USA',NULL,2019,NULL)," +
		"('frc7828',7828,'KALROBOTICS','http://www.kalrobotics.tech'," +
		" 'Kadıkoy','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7829',7829,'test team turkey',NULL," +
		" 'test','Kahramanmaras','Turkey',NULL,2019,NULL)," +
		"('frc7830',7830,'Ultimate Robotics','http://www.bahcesehir.k12.tr/tr/'," +
		" 'Üsküdar','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7831',7831,'BALİSTA BİLSEM',NULL," +
		" 'İSTANBUL','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7832',7832,'Hayata Destek',NULL," +
		" 'İstanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7833',7833,'FALCONS','https://sites.google.com/palmbeachschools.org/falconsrobotics/home'," +
		" 'West Palm Beach','Florida','USA',NULL,2019,NULL)," +
		"('frc7834',7834,'Neville High School',NULL," +
		" 'Monroe','Louisiana','USA',NULL,2019,NULL)," +
		"('frc7835',7835,'APRIKOD',NULL," +
		" 'KULUNCAK/MALATYA','Malatya','Turkey',NULL,2019,NULL)," +
		"('frc7836',7836,'NEC@ Co-Lin','http://www.natchezadamsschools.org'," +
		" 'Natchez','Mississippi','USA',NULL,2019,NULL)," +
		"('frc7837',7837,'Beast of Prey',NULL," +
		" 'San Antonio','Texas','USA',NULL,2019,NULL)," +
		"('frc7838',7838,'Crobotics',NULL," +
		" 'Crows Nest','New South Wales','Australia',NULL,2019,NULL)," +
		"('frc7839',7839,'BAAL ROBOTICS',NULL," +
		" 'Beşiktaş','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7840',7840,'emoner','http://www.emonerandl.meb.k12.tr'," +
		" 'istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7841',7841,'TECHNO-ROB',NULL," +
		" 'ÇAYIROVA','Kocaeli','Turkey',NULL,2019,NULL)," +
		"('frc7842',7842,'LVGalaxy','http://www.lsschool.org'," +
		" 'Lake Village','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7843',7843,'The Galactic Wranglers',NULL," +
		" 'Bentley','Alberta','Canada',NULL,2019,NULL)," +
		"('frc7844',7844,'Yirara Colleg',NULL," +
		" 'Alice Springs','Northern Territory','Australia',NULL,2019,NULL)," +
		"('frc7845',7845,'8Bit',NULL," +
		" 'Jerusalem','Yerushalayim','Israel',NULL,2019,NULL)," +
		"('frc7846',7846,'Zombie Squad',NULL," +
		" 'Barton','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7847',7847,'Abraham Lincoln Robotics Team',NULL," +
		" 'San Francisco','California','USA',NULL,2019,NULL)," +
		"('frc7848',7848,'WI-FIghterz',NULL," +
		" 'Pontiac','Illinois','USA',NULL,2019,NULL)," +
		"('frc7849',7849,'The 7849ers',NULL," +
		" 'Saint Paul','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7850',7850,'C.A.R.D.S',NULL," +
		" 'Coon Rapids','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7851',7851,'NeuraBlink',NULL," +
		" 'Kyiv','Kyïv','Ukraine',NULL,2019,NULL)," +
		"('frc7852',7852,'DENİZYILDIZLARI_MTAL','http://denizyildizlari2.meb.k12.tr/'," +
		" 'DARICA','Kocaeli','Turkey',NULL,2019,NULL)," +
		"('frc7853',7853,'Kronecker Delta','https://www.epsnj.org/roboticsfjc'," +
		" 'Elizabeth','New Jersey','USA',NULL,2019,NULL)," +
		"('frc7854',7854,'MTCPS Bathers',NULL," +
		" 'Mount Clemens','Michigan','USA',NULL,2019,NULL)," +
		"('frc7855',7855,'Bone Yard',NULL," +
		" 'Mesick','Michigan','USA',NULL,2019,NULL)," +
		"('frc7856',7856,'Henry Ford Academy: School for Creative Studies',NULL," +
		" 'Detroit','Michigan','USA',NULL,2019,NULL)," +
		"('frc7857',7857,'OPFI Robo Knights',NULL," +
		" 'Oak Park','Michigan','USA',NULL,2019,NULL)," +
		"('frc7858',7858,'Warriors',NULL," +
		" 'Olivia','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7859',7859,'Summit Electro-Dragons',NULL," +
		" 'Romulus','Michigan','USA',NULL,2019,NULL)," +
		"('frc7860',7860,'Falcon',NULL," +
		" 'Sterling Heights','Michigan','USA',NULL,2019,NULL)," +
		"('frc7861',7861,'The Wildkatz','http://www.albaschool.org'," +
		" 'Alba','Michigan','USA',NULL,2019,NULL)," +
		"('frc7862',7862,'Mott Bears',NULL," +
		" 'Flint','Michigan','USA',NULL,2019,NULL)," +
		"('frc7863',7863,'ATA',NULL," +
		" 'DETROIT','Michigan','USA',NULL,2019,NULL)," +
		"('frc7864',7864,'North Woods Robotics ',NULL," +
		" 'Cook','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7865',7865,'Hope of Detroit Robotics',NULL," +
		" 'Detroit','Michigan','USA',NULL,2019,NULL)," +
		"('frc7866',7866,'Balista',NULL," +
		" 'İstanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7867',7867,'NEBULA',NULL," +
		" 'TEPEBASI','Eskisehir','Turkey',NULL,2019,NULL)," +
		"('frc7868',7868,'FC Mustangs',NULL," +
		" 'Forrest City','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7869',7869,'The Fun Gang',NULL," +
		" 'New Haven','Connecticut','USA',NULL,2019,NULL)," +
		"('frc7870',7870,'The RoboHawks',NULL," +
		" 'Reno','Nevada','USA',NULL,2019,NULL)," +
		"('frc7871',7871,'South Gate High School Robotics',NULL," +
		" 'South Gate','California','USA',NULL,2019,NULL)," +
		"('frc7872',7872,'Wimberley Wavelengths',NULL," +
		" 'Wimberley','Texas','USA',NULL,2019,NULL)," +
		"('frc7873',7873,'AURORA',NULL," +
		" 'Istanbul','Istanbul','Turkey',NULL,2019,NULL)," +
		"('frc7874',7874,'Wossman High School',NULL," +
		" 'Monroe','Louisiana','USA',NULL,2019,NULL)," +
		"('frc7875',7875,'MNIC Robotics',NULL," +
		" 'Saint Paul','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7876',7876,'AC JONES ROBOTICS',NULL," +
		" NULL,NULL,NULL,NULL,2019,NULL)," +
		"('frc7877',7877,'Mastery High School of Camden',NULL," +
		" 'Camden','New Jersey','USA',NULL,2019,NULL)," +
		"('frc7878',7878,'PBHS Robotics Team',NULL," +
		" 'Pine Bluff','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7879',7879,'TigerBots',NULL," +
		" 'Hanover','Maryland','USA',NULL,2019,NULL)," +
		"('frc7880',7880,'Rancho R.A.T.S.',NULL," +
		" 'Las Vegas','Nevada','USA',NULL,2019,NULL)," +
		"('frc7881',7881,'Cuyahoga Heights High School',NULL," +
		" 'Cleveland','Ohio','USA',NULL,2019,NULL)," +
		"('frc7882',7882,'SKYFIRST',NULL," +
		" 'TEL AVIV','Tel-Aviv','Israel',NULL,2019,NULL)," +
		"('frc7883',7883,'CCHS Robotics Razorbacks',NULL," +
		" 'Augusta','Georgia','USA',NULL,2019,NULL)," +
		"('frc7884',7884,'Parramatta High School',NULL," +
		" 'Parramatta','New South Wales','Australia',NULL,2019,NULL)," +
		"('frc7885',7885,'EC.exe',NULL," +
		" 'Elyria','Ohio','USA',NULL,2019,NULL)," +
		"('frc7886',7886,'Cadet Robotics',NULL," +
		" 'Frederick','Maryland','USA',NULL,2019,NULL)," +
		"('frc7887',7887,'Final FRONTIER',NULL," +
		" 'Camarillo','California','USA',NULL,2019,NULL)," +
		"('frc7889',7889,'OSAT',NULL," +
		" 'Edmonton','Alberta','Canada',NULL,2019,NULL)," +
		"('frc7890',7890,'SeQuEnCe',NULL," +
		" 'Raleigh','North Carolina','USA',NULL,2019,NULL)," +
		"('frc7891',7891,'St. Vrain Valley Innovation Center',NULL," +
		" 'Longmont','Colorado','USA',NULL,2019,NULL)," +
		"('frc7892',7892,'Medford RoboTigers',NULL," +
		" 'Medford','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7893',7893,'Maple Lake High School',NULL," +
		" 'Maple Lake','Minnesota','USA',NULL,2019,NULL)," +
		"('frc7894',7894,'The Hawk Nation',NULL," +
		" 'Flint','Michigan','USA',NULL,2019,NULL)," +
		"('frc7895',7895,'Trobots',NULL," +
		" 'Homedale','Idaho','USA',NULL,2019,NULL)," +
		"('frc7896',7896,'CyberNoles',NULL," +
		" 'Osceola','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7897',7897,'WHS Auto Barks',NULL," +
		" 'Winslow','Arizona','USA',NULL,2019,NULL)," +
		"('frc7898',7898,'Cool Geeks',NULL," +
		" 'Honolulu','Hawaii','USA',NULL,2019,NULL)," +
		"('frc7899',7899,'Gibsland-Coleman STEM Club',NULL," +
		" 'Gibsland','Louisiana','USA',NULL,2019,NULL)," +
		"('frc7900',7900,'Trial & Terror',NULL," +
		" 'Racine','Wisconsin','USA',NULL,2019,NULL)," +
		"('frc7901',7901,'Snow Hill Robotics',NULL," +
		" 'Snow Hill','Maryland','USA',NULL,2019,NULL)," +
		"('frc7902',7902,'Markham FireBirds','https://markhamfirebirds.com/'," +
		" 'Markham','Ontario','Canada',NULL,2019,NULL)," +
		"('frc7903',7903,'Rochester Prep ',NULL," +
		" 'Rochester','New York','USA',NULL,2019,NULL)," +
		"('frc7904',7904,'Mountaineers',NULL," +
		" 'Highwood','Montana','USA',NULL,2019,NULL)," +
		"('frc7905',7905,'Robo Falcons',NULL," +
		" 'Clearfield','Utah','USA',NULL,2019,NULL)," +
		"('frc7906',7906,'Underdogs',NULL," +
		" 'Salt Lake City','Utah','USA',NULL,2019,NULL)," +
		"('frc7907',7907,'Spartan Robotics',NULL," +
		" 'Whitefield','New Hampshire','USA',NULL,2019,NULL)," +
		"('frc7908',7908,'MARVELL MAKERS','http://www.marvellschools.org/first'," +
		" 'Marvell','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7909',7909,'GTI WingNuts',NULL," +
		" 'Salt Lake City','Utah','USA',NULL,2019,NULL)," +
		"('frc7910',7910,'Crossett Eagles',NULL," +
		" 'Crossett','Arkansas','USA',NULL,2019,NULL)," +
		"('frc7911',7911,'Belding RoboKnights',NULL," +
		" 'Belding','Michigan','USA',NULL,2019,NULL)," +
		"('frc7912',7912,'Flex Tech Robotics',NULL," +
		" 'Novi','Michigan','USA',NULL,2019,NULL)," +
		"('frc7913',7913,'Newfound Bears',NULL," +
		" 'Bristol','New Hampshire','USA',NULL,2019,NULL)," +
		"('frc7914',7914,'Bulldog Robotics ',NULL," +
		" 'Monroe','Louisiana','USA',NULL,2019,NULL)," +
		"('frc7915',7915,'First Alliance',NULL," +
		" 'Ripon','Wisconsin','USA',NULL,2019,NULL);";
}
