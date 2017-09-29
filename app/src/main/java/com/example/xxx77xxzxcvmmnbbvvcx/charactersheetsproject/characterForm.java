package com.example.xxx77xxzxcvmmnbbvvcx.charactersheetsproject;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.util.Hashtable;
import java.util.Random;
import java.util.ArrayList;
import android.widget.RelativeLayout;
import android.widget.RadioButton;
import android.view.ViewGroup;


public class characterForm extends AppCompatActivity {
    public ArrayList<String> plusOneStats = new ArrayList<>();
    public ArrayList<String> plusTwoStats = new ArrayList<>();


    public ArrayList<String> raceFeatures = new ArrayList<>();
    public ArrayList<String> classFeatures = new ArrayList<>();
    public ArrayList<String> classEquipment = new ArrayList<>();
    public ArrayList<String> raceToolProficiencies = new ArrayList<>();
    public ArrayList<String> raceSkillProficiencies = new ArrayList<>();
    public ArrayList<String> raceWeaponAndArmorProficiencies = new ArrayList<>();
    public ArrayList<String> raceProficiencies = new ArrayList<>();
    public ArrayList<String> classWeaponAndArmorProficiencies = new ArrayList<>();
    public ArrayList<String> languages = new ArrayList<>();

    public ArrayList<String> backgroundSkills = new ArrayList<>();
    public ArrayList<String> backgroundProficiencies = new ArrayList<>();
    public ArrayList<String> backgroundFeatures = new ArrayList<>();
    public ArrayList<String> backgroundEquipment = new ArrayList<>();

    public int speed;
    public int numBonuses = 0;
    public int numChecked = 0;
    int bonusSize = 0;
    int hp = 0;
    int AC = 0;
    String hitDie = "";
    String saveThrows = "";
    int cantripSlots = 0;
    int spellsKnown;
    int[] spellSlots = {0,0,0,0,0,0,0,0,0};
    int[] statModifiers = {0,0,0,0,0,0};
    boolean hillDwarfHpBonus = false;
    int platinum = 0;
    int gold = 0;
    int bronze = 0;

    public CheckBox[] statBonusCheckBox = new CheckBox[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        statBonusCheckBox[0] = (CheckBox)(findViewById(R.id.checkBoxStr));
        statBonusCheckBox[1] = (CheckBox)findViewById(R.id.checkBoxDex);
        statBonusCheckBox[2] = (CheckBox)findViewById(R.id.checkBoxCon);
        statBonusCheckBox[3] = (CheckBox)findViewById(R.id.checkBoxInt);
        statBonusCheckBox[4] = (CheckBox)findViewById(R.id.checkBoxWis);
        statBonusCheckBox[5] = (CheckBox)findViewById(R.id.checkBoxChar);


        TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
        plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

        TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
        plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());


        final Spinner racespinner = (Spinner) findViewById(R.id.racespinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.DefaultRaces, R.layout.custom_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        racespinner.setAdapter(adapter);

        final Spinner backgrounds = (Spinner) findViewById(R.id.backgroundSpinner);

        ArrayAdapter<CharSequence> backgroundAdapter = ArrayAdapter.createFromResource(this,
                R.array.backgrounds, R.layout.custom_spinner_item);

        backgrounds.setAdapter(backgroundAdapter);

        backgrounds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                backgroundSelected(backgrounds);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner subracespinner = (Spinner)findViewById(R.id.subracespinner);



        final Spinner draconianHeritage = (Spinner)findViewById(R.id.draconianHeritagespinner);
        draconianHeritage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateDraconianHeritageFeatures();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        racespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateSubrace(racespinner, subracespinner, draconianHeritage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        final Spinner classSpinner = (Spinner) findViewById(R.id.classspinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(this,
                R.array.DefaultClasses, R.layout.custom_spinner_item);
// Specify the layout to use when the list of choices appears
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        classSpinner.setAdapter(classAdapter);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateClassStuff(classSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button randGeneration = (Button)findViewById(R.id.randGenbutton);
        randGeneration.setEnabled(false);

        randGeneration.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                EditText strengthInput = (EditText)findViewById(R.id.StrengthNumber);
                strengthInput.setText(generateRandomStats());
                strengthInput.setTextColor(0xff000000);

                EditText dexInput = (EditText)findViewById(R.id.DexNumber);
                dexInput.setText(generateRandomStats());
                dexInput.setTextColor(0xff000000);

                EditText conInput = (EditText)findViewById(R.id.ConNumber);
                conInput.setText(generateRandomStats());
                conInput.setTextColor(0xff000000);

                EditText intInput = (EditText)findViewById(R.id.IntNumber);
                intInput.setText(generateRandomStats());
                intInput.setTextColor(0xff000000);

                EditText wisInput = (EditText)findViewById(R.id.WisNumber);
                wisInput.setText(generateRandomStats());
                wisInput.setTextColor(0xff000000);

                EditText chaInput = (EditText)findViewById(R.id.ChaNumber);
                chaInput.setText(generateRandomStats());
                chaInput.setTextColor(0xff000000);
            }
        });
        RadioGroup selectStats = (RadioGroup)findViewById(R.id.statsRadioGroup);
        selectStats.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Button randGeneration = (Button)findViewById(R.id.randGenbutton);
                TextView strength = (TextView)findViewById(R.id.Strength);
                EditText strengthInput = (EditText)findViewById(R.id.StrengthNumber);

                TextView dexterity = (TextView)findViewById(R.id.Dexterity);
                EditText dexInput = (EditText)findViewById(R.id.DexNumber);

                TextView constitution = (TextView)findViewById(R.id.Constitution);
                EditText conInput = (EditText)findViewById(R.id.ConNumber);

                TextView intelligence = (TextView)findViewById(R.id.Intelligence);
                EditText intInput = (EditText)findViewById(R.id.IntNumber);

                TextView wisdom = (TextView)findViewById(R.id.Wisdom);
                EditText wisInput = (EditText)findViewById(R.id.WisNumber);

                TextView charisma = (TextView)findViewById(R.id.Charisma);
                EditText chaInput = (EditText)findViewById(R.id.ChaNumber);
                if(checkedId == R.id.randomRadio){
                    randGeneration.setEnabled(true);
                    strengthInput.setEnabled(false);
                    dexInput.setEnabled(false);
                    conInput.setEnabled(false);
                    intInput.setEnabled(false);
                    wisInput.setEnabled(false);
                    chaInput.setEnabled(false);
                }else if(checkedId == R.id.selfEnterRadio){
                    randGeneration.setEnabled(false);
                    strengthInput.setEnabled(true);
                    dexInput.setEnabled(true);
                    conInput.setEnabled(true);
                    intInput.setEnabled(true);
                    wisInput.setEnabled(true);
                    chaInput.setEnabled(true);
                }
            }
        });


    }


    //Generates Random Stats using 4d6 subtracting the least

    public CharSequence generateRandomStats(){
        Random randNums = new Random();
        Integer statsTotal = 0;
        Integer least = 50;
        Integer tempNum;
        for(int i = 0; i < 4; i++) {
            tempNum = randNums.nextInt(6) + 1;
            if (tempNum < least) {
                least = tempNum;
            }
            statsTotal += tempNum;
        }
        statsTotal -= least;
        CharSequence statString= statsTotal.toString();
        return statString;
    }


    ////////////////////////////////////////
    //                                    //
    //         Generates Subrace or       //
    //     Draconian Heritage Spinners    //
    //                                    //
    ////////////////////////////////////////
    public void generateSubrace(final Spinner racespinner, final Spinner subracespinner, Spinner draconianHeritage){
        LinearLayout parent = (LinearLayout)findViewById(R.id.subraceparent);
        LinearLayout draconianParent = (LinearLayout)findViewById(R.id.draconianParent);
        RelativeLayout chooseStats = (RelativeLayout) findViewById(R.id.chooseStats);
        if(racespinner.getSelectedItem().equals("Human")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.human_subraces, R.layout.custom_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            subracespinner.setAdapter(adapter);
            parent.setVisibility(View.VISIBLE);
            draconianParent.setVisibility(View.GONE);

            //STATS
            chooseStats.setVisibility(View.GONE);
            plusTwoStats.clear();
            plusOneStats.clear();
            hillDwarfHpBonus = false;
            generateSubraceBonuses(racespinner, subracespinner);

        }else if(racespinner.getSelectedItem().equals("Dwarf")){

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.dwarf_subraces, R.layout.custom_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            subracespinner.setAdapter(adapter);
            parent.setVisibility(View.VISIBLE);
            draconianParent.setVisibility(View.GONE);

            //STATS
            chooseStats.setVisibility(View.GONE);

            generateSubraceBonuses(racespinner, subracespinner);

        }else if(racespinner.getSelectedItem().equals("Elf")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.elf_subraces, R.layout.custom_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            subracespinner.setAdapter(adapter);
            parent.setVisibility(View.VISIBLE);
            draconianParent.setVisibility(View.GONE);

            //STATS
            hillDwarfHpBonus = false;
            chooseStats.setVisibility(View.GONE);
            generateSubraceBonuses(racespinner, subracespinner);

        }else if(racespinner.getSelectedItem().equals("Halfling")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.halfling_subraces, R.layout.custom_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            subracespinner.setAdapter(adapter);
            parent.setVisibility(View.VISIBLE);

            //STATS
            hillDwarfHpBonus = false;
            chooseStats.setVisibility(View.GONE);
            draconianParent.setVisibility(View.GONE);

            generateSubraceBonuses(racespinner, subracespinner);

        }else if(racespinner.getSelectedItem().equals("Gnome")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.gnome_subraces, R.layout.custom_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            subracespinner.setAdapter(adapter);
            parent.setVisibility(View.VISIBLE);
            draconianParent.setVisibility(View.GONE);

            //STATS
            hillDwarfHpBonus = false;
            chooseStats.setVisibility(View.GONE);

            generateSubraceBonuses(racespinner, subracespinner);

        }else if(racespinner.getSelectedItem().equals("Half-Elf")){
            plusTwoStats.clear();
            plusOneStats.clear();
            plusTwoStats.add("Char");
            hillDwarfHpBonus = false;


            raceFeatures.clear();
            raceSkillProficiencies.clear();
            raceToolProficiencies.clear();
            raceWeaponAndArmorProficiencies.clear();
            raceProficiencies.clear();
            languages.clear();
            speed = 30;

            raceFeatures.add("Darkvision");
            raceFeatures.add("Fey Ancestry");
            raceFeatures.add("Skill Versatility");

            languages.add("Common");
            languages.add("Elven");


            generateCheckBoxes(2, 1, chooseStats);

            TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
            plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

            TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
            plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());


            parent.setVisibility(View.GONE);
            draconianParent.setVisibility(View.GONE);

        }else if(racespinner.getSelectedItem().equals("Half-Orc")){


            raceFeatures.clear();
            raceSkillProficiencies.clear();
            raceToolProficiencies.clear();
            raceWeaponAndArmorProficiencies.clear();
            raceProficiencies.clear();
            languages.clear();
            speed = 30;

            raceFeatures.add("Darkvision");
            raceFeatures.add("Menacing");
            raceFeatures.add("Relentless Endurance");
            raceFeatures.add("Savage Attacks");

            languages.add("Common");
            languages.add("Orc");

            raceSkillProficiencies.add("Intimidation");


            plusTwoStats.clear();
            plusOneStats.clear();
            chooseStats.setVisibility(View.GONE);
            hillDwarfHpBonus = false;
            plusTwoStats.add("Str");
            plusOneStats.add("Con");
            parent.setVisibility(View.GONE);
            draconianParent.setVisibility(View.GONE);
            TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
            plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

            TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
            plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

        }else if(racespinner.getSelectedItem().equals("Tiefling")){


            raceFeatures.clear();
            raceSkillProficiencies.clear();
            raceToolProficiencies.clear();
            raceWeaponAndArmorProficiencies.clear();
            raceProficiencies.clear();
            languages.clear();
            speed = 30;

            raceFeatures.add("Darkvision");
            raceFeatures.add("Hellish Resistance");
            raceFeatures.add("Infernal Legacy");

            languages.add("Common");
            languages.add("Infernal");


            plusTwoStats.clear();
            plusOneStats.clear();
            plusTwoStats.add("Char");
            plusOneStats.add("Int");
            hillDwarfHpBonus = false;

            TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
            plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

            TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
            plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

            chooseStats.setVisibility(View.GONE);
            parent.setVisibility(View.GONE);
            draconianParent.setVisibility(View.GONE);

        }else if(racespinner.getSelectedItem().equals("Dragonborn")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.draconian_heritage, R.layout.custom_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            draconianHeritage.setAdapter(adapter);
            chooseStats.setVisibility(View.GONE);
            parent.setVisibility(View.GONE);
            draconianParent.setVisibility(View.VISIBLE);


            raceFeatures.clear();
            raceSkillProficiencies.clear();
            raceToolProficiencies.clear();
            raceWeaponAndArmorProficiencies.clear();
            raceProficiencies.clear();
            languages.clear();
            speed = 30;


            raceFeatures.add("Breath Weapon- Damage type, shape, and save type determined by draconic ancestry");
            raceFeatures.add("Damage resistance based on draconic heritage");

            languages.add("Common");
            languages.add("Draconic");


            plusTwoStats.clear();
            plusOneStats.clear();
            plusTwoStats.add("Str");
            plusOneStats.add("Char");
            hillDwarfHpBonus = false;

            TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
            plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

            TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
            plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

        }else{
            if(subracespinner.getVisibility() == View.VISIBLE){
                chooseStats.setVisibility(View.GONE);
                parent.setVisibility(View.GONE);
                draconianParent.setVisibility(View.GONE);
                hillDwarfHpBonus = false;
            }
        }
        subracespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateSubraceBonuses(racespinner, subracespinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void generateSubraceBonuses(Spinner raceSpinner, Spinner subraceSpinner){
        if(raceSpinner.getSelectedItem().equals("Human")){
            if(subraceSpinner.getSelectedItem().equals("Human")){


                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 30;

                languages.add("Common");
                languages.add("+1 of your choice");


                plusOneStats.clear();
                plusTwoStats.clear();
                plusOneStats.add("Str");
                plusOneStats.add("Dex");
                plusOneStats.add("Con");
                plusOneStats.add("Int");
                plusOneStats.add("Wis");
                plusOneStats.add("Char");
                hillDwarfHpBonus = false;
                RelativeLayout chooseStats = (RelativeLayout)findViewById(R.id.chooseStats);
                chooseStats.setVisibility(View.GONE);


                TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
                plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

                TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
                plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

            }else if(subraceSpinner.getSelectedItem().equals("Variant Human")){


                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 30;

                languages.add("Common");
                languages.add("+1 of your choice");

                raceSkillProficiencies.add("Choose one skill of your choice");

                raceFeatures.add("You can choose one feat of your choice");


                plusOneStats.clear();
                plusTwoStats.clear();
                hillDwarfHpBonus = false;

                TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
                plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

                TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
                plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

                RelativeLayout chooseStats = (RelativeLayout)findViewById(R.id.chooseStats);
                generateCheckBoxes(2, 1, chooseStats);
            }
        }else if(raceSpinner.getSelectedItem().equals("Dwarf")){

            if(subraceSpinner.getSelectedItem().equals("Hill Dwarf")){

                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 25;

                raceFeatures.add("Darkvision");
                raceFeatures.add("Dwarven Resilience");
                raceFeatures.add("Stonecunning");

                raceWeaponAndArmorProficiencies.add("Battleaxe");
                raceWeaponAndArmorProficiencies.add("Handaxe");
                raceWeaponAndArmorProficiencies.add("Throwing hammer");
                raceWeaponAndArmorProficiencies.add("Warhammer");
                raceToolProficiencies.add("Either smith's tools, brewer's supplies, or mason's tools.");


                languages.add("Common");
                languages.add("Dwarvish");

                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Con");
                plusOneStats.add("Wis");
                hillDwarfHpBonus = true;
            }else if(subraceSpinner.getSelectedItem().equals("Mountain Dwarf")){

                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 25;

                raceFeatures.add("Darkvision");
                raceFeatures.add("Darkvision");
                raceFeatures.add("Stonecunning");

                raceWeaponAndArmorProficiencies.add("Battleaxe");
                raceWeaponAndArmorProficiencies.add("Handaxe");
                raceWeaponAndArmorProficiencies.add("Throwing hammer");
                raceWeaponAndArmorProficiencies.add("Warhammer");
                raceToolProficiencies.add("Either smith's tools, brewer's supplies, or mason's tools.");
                raceWeaponAndArmorProficiencies.add("Light armor");
                raceWeaponAndArmorProficiencies.add("Medium armor");

                languages.add("Common");
                languages.add("Dwarvish");


                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Con");
                plusTwoStats.add("Str");
                hillDwarfHpBonus = false;
            }

            TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
            plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

            TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
            plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

        }else if(raceSpinner.getSelectedItem().equals("Elf")){

            if(subraceSpinner.getSelectedItem().equals("High Elf")){

                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 30;
                cantripSlots = 1;

                raceFeatures.add("Darkvision");
                raceFeatures.add("Fey Ancestry");
                raceFeatures.add("Trance");

                raceSkillProficiencies.add("Perception");
                raceWeaponAndArmorProficiencies.add("Longsword");
                raceWeaponAndArmorProficiencies.add("Shortsword");
                raceWeaponAndArmorProficiencies.add("Shortbow");
                raceWeaponAndArmorProficiencies.add("Longbow");

                languages.add("Common");
                languages.add("Elvish");
                languages.add("+1 of your choice");

                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Dex");
                plusOneStats.add("Int");
                hillDwarfHpBonus = false;
            }else if(subraceSpinner.getSelectedItem().equals("Wood Elf")){

                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 35;

                raceFeatures.add("Darkvision");
                raceFeatures.add("Fey Ancestry");
                raceFeatures.add("Trance");
                raceFeatures.add("Mask of the Wild");

                raceSkillProficiencies.add("Perception");
                raceWeaponAndArmorProficiencies.add("Longsword");
                raceWeaponAndArmorProficiencies.add("Shortsword");
                raceWeaponAndArmorProficiencies.add("Shortbow");
                raceWeaponAndArmorProficiencies.add("Longbow");

                languages.add("Common");
                languages.add("Elvish");

                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Dex");
                plusOneStats.add("Wis");
                hillDwarfHpBonus = false;
            }else if(subraceSpinner.getSelectedItem().equals("Dark Elf")){

                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 30;

                raceFeatures.add("Superior Darkvision");
                raceFeatures.add("Fey Ancestry");
                raceFeatures.add("Trance");
                raceFeatures.add("Sunlight Sensitivity");
                raceFeatures.add("Drow Magic");

                raceSkillProficiencies.add("Perception");
                raceWeaponAndArmorProficiencies.add("Rapiers");
                raceWeaponAndArmorProficiencies.add("Shortsword");
                raceWeaponAndArmorProficiencies.add("Hand Crossbow");

                languages.add("Common");
                languages.add("Elvish");

                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Dex");
                plusOneStats.add("Char");
                hillDwarfHpBonus = false;
            }

            TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
            plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

            TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
            plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

        }else if(raceSpinner.getSelectedItem().equals("Halfling")){
            hillDwarfHpBonus = false;
            if(subraceSpinner.getSelectedItem().equals("Lightfoot")){

                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 25;


                raceFeatures.add("Lucky");
                raceFeatures.add("Brave");
                raceFeatures.add("Halfling Nimbleness");
                raceFeatures.add("Naturally Stealthy");

                languages.add("Common");
                languages.add("Halfling");


                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Dex");
                plusOneStats.add("Char");
            }else if(subraceSpinner.getSelectedItem().equals("Stout")){


                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 25;


                raceFeatures.add("Lucky");
                raceFeatures.add("Brave");
                raceFeatures.add("Halfling Nimbleness");
                raceFeatures.add("Stout Resilience");

                languages.add("Common");
                languages.add("Halfling");


                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Dex");
                plusOneStats.add("Con");
            }

            TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
            plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

            TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
            plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

        }else if(raceSpinner.getSelectedItem().equals("Gnome")){
            hillDwarfHpBonus = false;
            if(subraceSpinner.getSelectedItem().equals("Forest Gnome")){


                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 25;

                raceFeatures.add("Darkvision");
                raceFeatures.add("Gnome cunning");
                raceFeatures.add("Natural Illusionist");
                raceFeatures.add("Speak with Small Beasts");

                languages.add("Common");
                languages.add("Gnomish");

                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Int");
                plusOneStats.add("Dex");
            }else if(subraceSpinner.getSelectedItem().equals("Rock Gnome")){


                raceFeatures.clear();
                raceSkillProficiencies.clear();
                raceToolProficiencies.clear();
                raceWeaponAndArmorProficiencies.clear();
                raceProficiencies.clear();
                languages.clear();
                speed = 25;

                raceFeatures.add("Darkvision");
                raceFeatures.add("Gnome cunning");
                raceFeatures.add("Artificer's Lore");
                raceFeatures.add("Tinker");

                raceToolProficiencies.add("Artisan's Tools");

                languages.add("Common");
                languages.add("Gnomish");


                plusTwoStats.clear();
                plusOneStats.clear();
                plusTwoStats.add("Int");
                plusOneStats.add("Con");
            }

            TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
            plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

            TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
            plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

        }
    }

    public void onCheckBoxChecked(View view){
        boolean checked = ((CheckBox)view).isChecked();
        if(checked){
            numChecked += 1;


            if(bonusSize == 1){
                plusOneStats.add(((CheckBox) view).getText().toString());
            }else{
                plusTwoStats.add(((CheckBox) view).getText().toString());
            }


            if(numChecked == numBonuses){
                for(int i = 0; i < statBonusCheckBox.length; i++){
                    if(!statBonusCheckBox[i].isChecked()){
                        statBonusCheckBox[i].setEnabled(false);
                    }
                }
            }
        }else{
            numChecked -= 1;

            if(bonusSize == 1){
                plusOneStats.remove(((CheckBox) view).getText().toString());
            }else{
                plusTwoStats.remove(((CheckBox) view).getText().toString());
            }

            if(numChecked < numBonuses){
                for(int i = 0; i < statBonusCheckBox.length; i++){
                    if(!statBonusCheckBox[i].isChecked()){
                        statBonusCheckBox[i].setEnabled(true);
                    }
                }
            }
        }

        TextView plusOneStatBonusText = (TextView)findViewById(R.id.plusOneStatBonuses);
        plusOneStatBonusText.setText("Plus one race bonuses: " + plusOneStats.toString());

        TextView plusTwoStatBonusText = (TextView)findViewById(R.id.plusTwoStatBonuses);
        plusTwoStatBonusText.setText("Plus two race bonuses: " + plusTwoStats.toString());

    }

    public void generateCheckBoxes(int numChoices, int modifier, RelativeLayout view){

        numBonuses = numChoices;
        bonusSize = modifier;
            view.setVisibility(View.VISIBLE);
            TextView prompt = (TextView)findViewById(R.id.modifierPrompt);
        prompt.setText("Please choose " + numChoices + " stats to have a +" + modifier + " bonus." );
    }


    public void generateDraconianHeritageFeatures(){}

    public int getStatModifier(View view){
        int bonus = 0;
        if(Integer.parseInt(((EditText)view).getText().toString()) >= 30){
            bonus = 10;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 28){
            bonus = 9;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 26){
            bonus = 8;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 24){
            bonus = 7;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 22){
            bonus = 6;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 20){
            bonus = 5;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 18){
            bonus = 4;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 16){
            bonus = 3;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 14){
            bonus = 2;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 12){
            bonus = 1;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 10){
            bonus = 0;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 8){
            bonus = -1;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 6){
            bonus = -2;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 4){
            bonus = -3;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) >= 2){
            bonus = -4;
        }else if(Integer.parseInt(((EditText)view).getText().toString()) < 2){
            bonus = -5;
        }

        return bonus;
    }


    public void generateClassStuff(Spinner classSpinner){
        final LinearLayout equipmentChoosing = (LinearLayout)findViewById(R.id.EquipmentList);
        String className = classSpinner.getSelectedItem().toString();
        classWeaponAndArmorProficiencies.clear();
        classFeatures.clear();
        if(className.equals("Barbarian")){
            hitDie = "d12";
            hp = 12 + statModifiers[2];
            saveThrows = "Strength & Constitution";
            classWeaponAndArmorProficiencies.add("Light and Medium Armor");
            classWeaponAndArmorProficiencies.add("Shields");
            classWeaponAndArmorProficiencies.add("Simple and Martial Weapons");

            classFeatures.add("Rage: 2 Rages, +2 Damage");
            classFeatures.add("Unarmored Defense");

            cantripSlots = 0;
            spellsKnown = 0;
            spellSlots[0] = 0;

            String[][] radioGroup = {{"Great Axe", "Any Martial Melee Weapon"}, {"2 Handaxes", "Any Simple Weapon"}, {"Explorer's Pack"}, {"4 Javelins"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Bard")){

            hitDie = "d8";
            hp = 8 + statModifiers[2];
            saveThrows = "Dexterity & Charisma";
            classWeaponAndArmorProficiencies.add("Light Armor");
            classWeaponAndArmorProficiencies.add("Simple Weapons");
            classWeaponAndArmorProficiencies.add("Hand Crossbows");
            classWeaponAndArmorProficiencies.add("Longswords");
            classWeaponAndArmorProficiencies.add("Rapiers");
            classWeaponAndArmorProficiencies.add("Shortswords");


            classFeatures.add("Spellcasting");
            classFeatures.add("Bardic Inspiration (d6)");

            cantripSlots = 2;
            spellsKnown = 4;
            spellSlots[0] = 2;

            String[][] radioGroup = {{"Rapier", "Longsword", "Any simple weapon"}, {"Diplomat's Pack", "Entertainer's Pack"}, {"Lute", "Any other musical instrument"}, {"Leather armor"}, {"Dagger"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Cleric")){

            hitDie = "d8";
            hp = 8 + statModifiers[2];
            saveThrows = "Wisdom & Charisma";
            classWeaponAndArmorProficiencies.add("Light Armor and Medium Armor");
            classWeaponAndArmorProficiencies.add("Shields");
            classWeaponAndArmorProficiencies.add("Simple Weapons");


            classFeatures.add("Spellcasting");
            classFeatures.add("Divine Domain");

            cantripSlots = 3;
            spellsKnown = 0;
            spellSlots[0] = 2;


            String[][] radioGroup = {{"Mace", "Warhammer(if proficient)"}, {"Scale mail", "Leather armor",
                    "Chain mail(if proficient)"}, {"Light crossbow + 20 bolts", "Any simple weapon"}, {"Priest's Pack", "Explorer's Pack"},{"Shield"},{"Holy Symbol"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Druid")){

            hitDie = "d8";
            hp = 8 + statModifiers[2];
            saveThrows = "Intelligence and Wisdom";
            classWeaponAndArmorProficiencies.add("Light and Medium Armor(Non-metal)");
            classWeaponAndArmorProficiencies.add("Shields(Non-metal)");
            classWeaponAndArmorProficiencies.add("Clubs");
            classWeaponAndArmorProficiencies.add("Daggers");
            classWeaponAndArmorProficiencies.add("Darts");
            classWeaponAndArmorProficiencies.add("Javelins");
            classWeaponAndArmorProficiencies.add("Maces");
            classWeaponAndArmorProficiencies.add("Quarterstaffs");
            classWeaponAndArmorProficiencies.add("Scimitars");
            classWeaponAndArmorProficiencies.add("Sickles");
            classWeaponAndArmorProficiencies.add("Slings");
            classWeaponAndArmorProficiencies.add("Spears");


            classFeatures.add("Spellcasting");
            classFeatures.add("Druidic");

            cantripSlots = 2;
            spellsKnown = 0;
            spellSlots[0] = 2;


            String[][] radioGroup = {{"Wooden Shield", "Any Simple Weapon"}, {"Scimitar", "Any Simple Melee Weapon"}, {"Leather Armor"}, {"Explorer's Pack"}, {"Druidic Focus"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Fighter")){

            hitDie = "d10";
            hp = 10 + statModifiers[2];
            saveThrows = "Strength and Constitution";
            classWeaponAndArmorProficiencies.add("All Armor");
            classWeaponAndArmorProficiencies.add("Shields");
            classWeaponAndArmorProficiencies.add("Simple Weapons");
            classWeaponAndArmorProficiencies.add("Martial Weapons");


            classFeatures.add("Fighting Style");
            classFeatures.add("Second Wind");

            cantripSlots = 0;
            spellsKnown = 0;
            spellSlots[0] = 0;


            String[][] radioGroup = {{"Chainmail", "Leather Armor and longbow + 20 arrows"}, {"Any Martial Weapon and a Shield",
                    "Any Martial Weapon (2)"}, {"Light Crossbow + 20 Bolts", "2 Handaxes"}, {"Dungeoneer's Pack", "Explorer's Pack"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Monk")){

            hitDie = "d8";
            hp = 8 + statModifiers[2];
            saveThrows = "Strength and Dexterity";
            classWeaponAndArmorProficiencies.add("Simple Weapons");
            classWeaponAndArmorProficiencies.add("Shortswords");

            classFeatures.add("Unarmored Defense");
            classFeatures.add("Martial Arts (1d4)");

            cantripSlots = 0;
            spellsKnown = 0;
            spellSlots[0] = 0;


            String[][] radioGroup = {{"Shortsword", "Any Simple Weapon"}, {"Dungeoneer's Pack", "Explorer's Pack"}, {"10 Darts"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Paladin")){

            hitDie = "d10";
            hp = 10 + statModifiers[2];
            saveThrows = "Wisdom and Charisma";
            classWeaponAndArmorProficiencies.add("All Armor");
            classWeaponAndArmorProficiencies.add("Shields");
            classWeaponAndArmorProficiencies.add("Simple Weapons");
            classWeaponAndArmorProficiencies.add("Martial Weapons");


            classFeatures.add("Divine Sense");
            classFeatures.add("Lay on Hands");

            cantripSlots = 0;
            spellsKnown = 0;
            spellSlots[0] = 0;


            String[][] radioGroup = {{"Any Martial Weapon and a Shield", "Any Martial Weapon (2)"},
                    {"5 Javelins", "Any Simple Melee Weapon"}, {"Priest's Pack", "Explorer's Pack"}, {"Chain Mail"}, {"A Holy Symbol"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Ranger")){

            hitDie = "d10";
            hp = 10 + statModifiers[2];
            saveThrows = "Strength and Dexterity";
            classWeaponAndArmorProficiencies.add("Light and Medium Armor");
            classWeaponAndArmorProficiencies.add("Shields");
            classWeaponAndArmorProficiencies.add("Simple Weapons");
            classWeaponAndArmorProficiencies.add("Martial Weapons");


            classFeatures.add("Favored Enemy");
            classFeatures.add("Natural Explorer");

            cantripSlots = 0;
            spellsKnown = 0;
            spellSlots[0] = 0;


            String[][] radioGroup = {{"Scale Mail", "Leather Armor"}, {"2 Shortswords", "Any Simple Weapon (2)"}, {"Dungeoneer's Pack", "Explorer's Pack"}, {"Longbow + 20 Arrows"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Rogue")){

            hitDie = "d8";
            hp = 8 + statModifiers[2];
            saveThrows = "Dexterity & Intelligence";
            classWeaponAndArmorProficiencies.add("Light Armor");
            classWeaponAndArmorProficiencies.add("Simple Weapons");
            classWeaponAndArmorProficiencies.add("Hand Crossbows");
            classWeaponAndArmorProficiencies.add("Longswords");
            classWeaponAndArmorProficiencies.add("Rapiers");
            classWeaponAndArmorProficiencies.add("Shortswords");


            classFeatures.add("Expertise");
            classFeatures.add("Sneak Attack - 1d6");
            classFeatures.add("Thieves' Cant");

            cantripSlots = 0;
            spellsKnown = 0;
            spellSlots[0] = 0;


            String[][] radioGroup = {{"Rapier", "Shortsword"}, {"Shortbow + 20 Arrows", "Shortsword"},
                    {"Burglar's Pack", "Dungeoneer's Pack", "Explorer's Pack"}, {"Leather Armor"}, {"2 Daggers"}, {"Thieve's Tools"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Sorcerer")){

            hitDie = "d6";
            hp = 6 + statModifiers[2];
            saveThrows = "Constitution and Charisma";
            classWeaponAndArmorProficiencies.add("Daggers");
            classWeaponAndArmorProficiencies.add("Darts");
            classWeaponAndArmorProficiencies.add("Slings");
            classWeaponAndArmorProficiencies.add("Quarterstaffs");
            classWeaponAndArmorProficiencies.add("Light Crossbows");


            classFeatures.add("Spellcasting");
            classFeatures.add("Sorcerous Origin");


            cantripSlots = 4;
            spellsKnown = 2;
            spellSlots[0] = 2;

            String[][] radioGroup = {{"Light Crossbow + 20 Bolts", "Any Simple Weapon"}, {"Component Pouch", "Arcane Focus"}, {"Dungeoneer's Pack", "Explorer's Pack"}, {"2 Daggers"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Warlock")){

            hitDie = "d8";
            hp = 8 + statModifiers[2];
            saveThrows = "Wisdom and Charisma";
            classWeaponAndArmorProficiencies.add("Light Armor");
            classWeaponAndArmorProficiencies.add("Simple Weapons");


            classFeatures.add("Otherworldly Patron");
            classFeatures.add("Pact Magic");


            cantripSlots = 2;
            spellsKnown = 2;
            spellSlots[0] = 1;

            String[][] radioGroup = {{"Light Crossbow + 20 Bolts", "Any Simple Weapon"}, {"Component Pouch", "Arcane Focus"}, {"Scholar's Pack", "Dungeoneer's Pack"},
                    {"2 Daggers"}, {"Leather Armor"}, {"Any Simple Weapon"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }else if(className.equals("Wizard")){
            
            hitDie = "d6";
            hp = 6 + statModifiers[2];
            saveThrows = "Intelligence and Wisdom";
            classWeaponAndArmorProficiencies.add("Daggers");
            classWeaponAndArmorProficiencies.add("Darts");
            classWeaponAndArmorProficiencies.add("Slings");
            classWeaponAndArmorProficiencies.add("Quarterstaffs");
            classWeaponAndArmorProficiencies.add("Light Crossbows");


            classFeatures.add("Spellcasting");
            classFeatures.add("Arcane Recovery");


            cantripSlots = 3;
            spellsKnown = 0;
            spellSlots[0] = 2;

            String[][] radioGroup = {{"Quarterstaff", "Dagger"}, {"Component Pouch", "Arcane Focus"}, {"Scholar's Pack", "Explorer's Pack"}, {"Spellbook"}};

            makeEquipmentList(radioGroup, equipmentChoosing);


        }
    }


    public void generateProficiencyRadios(String[] skills){
        CheckBox[] checkBoxArray = new CheckBox[skills.length];
        LinearLayout checkBoxLayout = (LinearLayout)findViewById(R.id.ProficiencyList);
        checkBoxLayout.removeAllViews();
        checkBoxLayout.setOrientation(LinearLayout.HORIZONTAL);
        for(int i = 0; i < checkBoxArray.length; i++){
            checkBoxArray[i] = new CheckBox(this);
            checkBoxLayout.addView(checkBoxArray[i]);
            checkBoxArray[i].setText(skills[i]);
        }
    }


    public void makeEquipmentList(final String[][] listChoices, final LinearLayout equipmentLayout){
        equipmentLayout.removeAllViews();
        for(int i = 0; i <listChoices.length; i++) {
            if (listChoices[i].length == 1) {
                TextView tv = new TextView(this);
                tv.setText(listChoices[i][0]);
                tv.setTextSize(15);
                tv.setTextColor(0xff000000);
                equipmentLayout.addView(tv);
            } else {

                RadioGroup rg = new RadioGroup(this);
                equipmentLayout.addView(rg);
                rg.setOrientation(LinearLayout.HORIZONTAL);
                for (int x = 0; x < listChoices[i].length; x++) {
                    final RadioButton rb = new RadioButton(this);
                    rb.setText(listChoices[i][x]);
                    rg.addView(rb);
                    if (listChoices[i][x].contains("Any") && !listChoices[i][x].toLowerCase().contains("instrument")) {
                        final int index = i;
                        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Spinner equipmentSpinner = new Spinner(equipmentLayout.getContext());
                                if (isChecked) {
                                    final ArrayAdapter<CharSequence> adapter;
                                    if (rb.getText().toString().toLowerCase().contains("any martial melee weapon")) {
                                        adapter = ArrayAdapter.createFromResource(equipmentLayout.getContext(),
                                                R.array.martial_melee_weapons, R.layout.custom_spinner_item);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        equipmentSpinner.setAdapter(adapter);

                                    } else if (rb.getText().toString().toLowerCase().contains("any simple melee weapon")) {
                                        adapter = ArrayAdapter.createFromResource(equipmentLayout.getContext(),
                                                R.array.simple_melee_weapons, R.layout.custom_spinner_item);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        equipmentSpinner.setAdapter(adapter);

                                    } else if (rb.getText().toString().toLowerCase().contains("any martial ranged weapon")) {
                                        adapter = ArrayAdapter.createFromResource(equipmentLayout.getContext(),
                                                R.array.martial_ranged_weapons, R.layout.custom_spinner_item);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        equipmentSpinner.setAdapter(adapter);

                                    } else if (rb.getText().toString().toLowerCase().contains("any simple ranged weapon")) {
                                        adapter = ArrayAdapter.createFromResource(equipmentLayout.getContext(),
                                                R.array.simple_ranged_weapons, R.layout.custom_spinner_item);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        equipmentSpinner.setAdapter(adapter);

                                    } else if (rb.getText().toString().toLowerCase().contains("any martial weapon")) {
                                        adapter = ArrayAdapter.createFromResource(equipmentLayout.getContext(),
                                                R.array.all_martial_weapons, R.layout.custom_spinner_item);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        equipmentSpinner.setAdapter(adapter);

                                    } else if (rb.getText().toString().toLowerCase().contains("any simple weapon")) {
                                        adapter = ArrayAdapter.createFromResource(equipmentLayout.getContext(),
                                                R.array.all_simple_weapons, R.layout.custom_spinner_item);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        equipmentSpinner.setAdapter(adapter);

                                    } else {

                                    }
                                    equipmentLayout.addView(equipmentSpinner, index+1);
                                    if(rb.getText().toString().contains("2")){
                                        equipmentLayout.addView(equipmentSpinner, index+1);
                                    }

                                } else {
                                    equipmentLayout.removeViewAt(index+1);
                                    if(rb.getText().toString().contains("2")){

                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }



    public void backgroundSelected(Spinner view){

        String backgroundName = view.getSelectedItem().toString();
        if(backgroundName.equals("Acolyte")){
            backgroundSkills.add("Insight");
            backgroundSkills.add("Religion");

            languages.add("+2 of your choice");

            backgroundFeatures.add("Shelter of the Faithful");

            backgroundEquipment.add("Holy Symbol");
            backgroundEquipment.add("Prayer Book or Beads");
            backgroundEquipment.add("5 Sticks of Incense");
            backgroundEquipment.add("Vestments");
            backgroundEquipment.add("Common Clothes");

            gold = 10;
        }else if(backgroundName.equals("Charlatan")){
            backgroundSkills.add("Deception");
            backgroundSkills.add("Slight of Hand");

            backgroundProficiencies.add("Disguise");
            backgroundProficiencies.add("Forgery Kit");

            backgroundFeatures.add("False Identity");

            backgroundEquipment.add("Fine Clothes");
            backgroundEquipment.add("Disguise Kit");
            backgroundEquipment.add("Forgery Kit");
            backgroundEquipment.add("Tool for Con of Preference");

            gold = 15;
        }else if(backgroundName.equals("Criminal")){
            backgroundSkills.add("Deception");
            backgroundSkills.add("Stealth");

            backgroundProficiencies.add("Gaming Set");
            backgroundProficiencies.add("Thieves' Tools");

            backgroundFeatures.add("Criminal Contact");

            backgroundEquipment.add("Crowbar");
            backgroundEquipment.add("Dark Common Clothes");
            backgroundEquipment.add("Hood");

            gold = 15;

        }else if(backgroundName.equals("Entertainer")){
            backgroundSkills.add("Acrobatics");
            backgroundSkills.add("Performance");

            backgroundProficiencies.add("Disguise Kit");
            backgroundProficiencies.add("1 Instrument");

            backgroundFeatures.add("Popular Demand");

            backgroundEquipment.add("Instrument");
            backgroundEquipment.add("Favor of an Admirer");
            backgroundEquipment.add("Costume");

            gold = 15;

        }else if(backgroundName.equals("Folk Hero")){
            backgroundSkills.add("Animal Handling");
            backgroundSkills.add("Survival");

            backgroundProficiencies.add("Artisan Tool");
            backgroundProficiencies.add("Land Vehicle");

            backgroundFeatures.add("Rustic Hospitality");

            backgroundEquipment.add("Artisan Tool");
            backgroundEquipment.add("Shovel");
            backgroundEquipment.add("Iron Pot");
            backgroundEquipment.add("Common Clothes");

            gold = 10;

        }else if(backgroundName.equals("Guild Artisan")){
            backgroundSkills.add("Insight");
            backgroundSkills.add("Persuasion");

            backgroundProficiencies.add("Artisan Tool");
            backgroundProficiencies.add("1 Language");

            backgroundFeatures.add("Guild Membership");

            backgroundEquipment.add("Letter of Introduction from Guild");
            backgroundEquipment.add("Artisan Tool");
            backgroundEquipment.add("Traveller's Clothes");

            gold = 15;
        }else if(backgroundName.equals("Hermit")){
            backgroundSkills.add("Medicine");
            backgroundSkills.add("Religion");

            backgroundProficiencies.add("Herbalism Kit");
            backgroundProficiencies.add("1 Language");

            backgroundFeatures.add("Discovery");

            backgroundEquipment.add("Scroll of notes and prayers");
            backgroundEquipment.add("Herbalism kit");
            backgroundEquipment.add("Common clothes");

            gold = 5;
        }else if(backgroundName.equals("Noble")){
            backgroundSkills.add("History");
            backgroundSkills.add("Persuasion");

            backgroundProficiencies.add("Gaming dice or Instrument");
            backgroundProficiencies.add("1 Language");

            backgroundFeatures.add("Kept in Style");

            backgroundEquipment.add("Fine clothes");
            backgroundEquipment.add("Signet Broach");
            backgroundEquipment.add("Scroll of Pedigree");
            backgroundEquipment.add("Skin of fine wine");

            gold = 25;
        }else if(backgroundName.equals("Outlander")){
            backgroundSkills.add("Athletics");
            backgroundSkills.add("Survival");

            backgroundProficiencies.add("1 Instrument");
            backgroundProficiencies.add("1 Language");

            backgroundFeatures.add("Wanderer");

            backgroundEquipment.add("Staff");
            backgroundEquipment.add("Hunting Trap");
            backgroundEquipment.add("Trophy from an Animal");
            backgroundEquipment.add("Traveller's Clothes");

            gold = 10;
        }else if(backgroundName.equals("Sage")){
            backgroundSkills.add("Arcana");
            backgroundSkills.add("History");

            backgroundProficiencies.add("2 Languages");

            backgroundFeatures.add("Researcher");

            backgroundEquipment.add("Calligrapher Kit");
            backgroundEquipment.add("Letter from Dead Colleague with information");
            backgroundEquipment.add("Common Clothes");

            gold = 10;
        }else if(backgroundName.equals("Sailor")){
            backgroundSkills.add("Athletics");
            backgroundSkills.add("Perception");

            backgroundProficiencies.add("Navigator Tool");
            backgroundProficiencies.add("Water Vehicle");

            backgroundFeatures.add("Ship Passage");

            backgroundEquipment.add("Belay Pin (Club)");
            backgroundEquipment.add("50 ft. Silk Rope");
            backgroundEquipment.add("Lucky Charm");
            backgroundEquipment.add("Common Clothes");

            gold = 10;
        }else if(backgroundName.equals("Soldier")){
            backgroundSkills.add("Athletics");
            backgroundSkills.add("Intimidation");

            backgroundProficiencies.add("Gaming Set");
            backgroundProficiencies.add("Land Vehicle");

            backgroundFeatures.add("Rank");

            backgroundEquipment.add("Insignia of Rank");
            backgroundEquipment.add("Trophy from Dead Enemy");
            backgroundEquipment.add("Dice or Cards");
            backgroundEquipment.add("Common Clothes");

            gold = 10;
        }else if(backgroundName.equals("Urchin")){
            backgroundSkills.add("Sleight of Hand");
            backgroundSkills.add("Stealth");

            backgroundProficiencies.add("Disguise Kit");
            backgroundProficiencies.add("Thieves Tool");

            backgroundFeatures.add("City Secrets");

            backgroundEquipment.add("Small Knife");
            backgroundEquipment.add("Map of City");
            backgroundEquipment.add("Pet Mouse");
            backgroundEquipment.add("Token from Parents");
            backgroundEquipment.add("Common Clothes");

            gold = 10;
        }
    }


}
