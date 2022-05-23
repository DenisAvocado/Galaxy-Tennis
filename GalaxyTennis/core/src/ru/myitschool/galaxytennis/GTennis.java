package ru.myitschool.galaxytennis;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

public class GTennis extends Game{
    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font, font_big;

    Texture imgUnlock, imgMark;
    Galaxy[] galaxies;
    GalImage[] planets, ufos;
    Texture[] galImages, plImages, plateImages, ufoImages;
    int[] gal_prices = {-1, 100, 300, 900, 1500, 3000};
    int[] pl_prices = {-1, 100, 200, 300, 500, 850, 1000, 1500};
    int[] plates_prices = {-1, 1000, 3000, 7000};

    Sound sndMoney, sndNo, sndClick;

    public static final int SCR_WIDTH = 576, SCR_HEIGHT = 1024;
    Coefficient coefficient;

    ScreenGalaxies screenGalaxies;
    ScreenGame screenGame;
    ScreenMenu screenMenu;
    ScreenOptions screenOptions;
    ScreenPlanets screenPlanets;
    ScreenPlates screenPlates;
    Preferences prefs;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();

        prefs = Gdx.app.getPreferences("preferences");

        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("crystal.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = SCR_WIDTH / 15;
        parameter.color = Color.LIME;
        font = generator.generateFont(parameter);

        parameter.size = SCR_WIDTH / 10;
        parameter.color = Color.LIME;
        font_big = generator.generateFont(parameter);

        imgUnlock = new Texture("unlock.png");
        imgMark = new Texture("mark.png");

        coefficient = new Coefficient(get_galaxy() * 0.5f,
                get_planet() * 0.25f, get_plates() * 0.5f);

        sndMoney = Gdx.audio.newSound(Gdx.files.internal("money.mp3"));
        sndNo = Gdx.audio.newSound(Gdx.files.internal("no.mp3"));
        sndClick = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));

        galaxies = new Galaxy[6];
        galImages = new Texture[6];

        for (int i = 1; i < 4; i ++)
            galaxies[i - 1] = new Galaxy(i, "space" + i + ".jpg",
                    GTennis.SCR_WIDTH / 10f * i + GTennis.SCR_WIDTH / 5f * (i - 1),
                    GTennis.SCR_HEIGHT * 0.7f, GTennis.SCR_WIDTH / 5f, GTennis.SCR_HEIGHT / 4f);

        for (int i = 4; i < 7; i ++)
            galaxies[i - 1] = new Galaxy(i, "space" + i + ".jpg",
                    GTennis.SCR_WIDTH / 10f * (i - 3) + GTennis.SCR_WIDTH / 5f * (i - 4),
                    GTennis.SCR_HEIGHT * 0.4f, GTennis.SCR_WIDTH / 5f, GTennis.SCR_HEIGHT / 4f);

        for (int i=1; i < 7; i ++) galImages[i - 1] = new Texture("space" + i + ".jpg");

        planets = new GalImage[8];
        plImages = new Texture[8];

        for (int i = 1; i < 5; i ++)
            planets[i - 1] = new GalImage(i, "planet" + i + ".png",
                    GTennis.SCR_WIDTH / 20f * i + GTennis.SCR_WIDTH / 5f * (i - 1),
                    GTennis.SCR_HEIGHT * 0.7f, GTennis.SCR_WIDTH / 5f, GTennis.SCR_HEIGHT / 9f);

        for (int i = 5; i < 9; i ++)
            planets[i - 1] = new GalImage(i, "planet" + i + ".png",
                    GTennis.SCR_WIDTH / 20f * (i - 4) + GTennis.SCR_WIDTH / 5f * (i - 5),
                    GTennis.SCR_HEIGHT * 0.4f, GTennis.SCR_WIDTH / 5f, GTennis.SCR_HEIGHT / 9f);

        for (int i=1; i < 9; i ++) plImages[i - 1] = new Texture("planet" + i + ".png");

        ufos = new GalImage[4];
        ufoImages = new Texture[4];
        plateImages = new Texture[8];

        for (int i=1; i < 9; i ++) plateImages[i - 1] = new Texture("ufo_cat_" + i + ".png");
        for (int i=1; i < 5; i ++) ufoImages[i - 1] = new Texture("ufo" + i + ".png");

        for (int i = 1; i < 3; i ++)
            ufos[i - 1] = new GalImage(i, "ufo" + i + ".png",
                    GTennis.SCR_WIDTH / 10f * i + GTennis.SCR_WIDTH / 3f * (i - 1),
                    GTennis.SCR_HEIGHT * 0.7f, GTennis.SCR_WIDTH / 2.5f, GTennis.SCR_HEIGHT / 10f);

        for (int i = 3; i < 5; i ++)
            ufos[i - 1] = new GalImage(i, "ufo" + i + ".png",
                    GTennis.SCR_WIDTH / 10f * (i - 2) + GTennis.SCR_WIDTH / 3f * (i - 3),
                    GTennis.SCR_HEIGHT * 0.4f, GTennis.SCR_WIDTH / 2.5f, GTennis.SCR_HEIGHT / 10f);


        screenMenu = new ScreenMenu(this);
        screenOptions = new ScreenOptions(this);
        screenGame = new ScreenGame(this);
        screenGalaxies = new ScreenGalaxies(this);
        screenPlanets = new ScreenPlanets(this);
        screenPlates = new ScreenPlates(this);
        setScreen(screenMenu);
    }

    int get_galaxy(){
        if (prefs.contains("galaxy")) return prefs.getInteger("galaxy", 1);
        return 1;
    }

    int get_planet(){
        if (prefs.contains("planet")) return prefs.getInteger("planet", 1);
        return 1;
    }

    int get_plates(){
        if (prefs.contains("plates")) return prefs.getInteger("plates", 1);
        return 1;
    }

    void update_coef(int ind, float value){
        if (ind == 1) coefficient.galaxy = value;
        else if (ind == 2) coefficient.planet = value;
        else if (ind == 3) coefficient.plate = value;
        }

    Texture get_ufo_cat_texture_1() {return plateImages[(get_plates() * 2 - 1) - 1];}
    Texture get_ufo_cat_texture_2() {return plateImages[(get_plates() * 2) - 1];}

    int load_money(){
        if (prefs.contains("money")) return prefs.getInteger("money", 0);
        return 0;
    }

    void change_money(int delta){
        int money = load_money();
        prefs.putInteger("money", money + delta);
        prefs.flush();
    }

    boolean is_sound_on(){
        if (prefs.contains("sound")) return prefs.getBoolean("sound", true);
        return true;
    }

    void change_sound(){
        prefs.putBoolean("sound", !prefs.getBoolean("sound", true));
        prefs.flush();
    }



    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}

