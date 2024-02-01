package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
//импорт необходимых библиотек

public class CoffeGame extends ApplicationAdapter {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    //размеры экрана

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture coffeeTexture;
    private Rectangle coffeeBounds;

    private Texture milkTexture;
    private Rectangle milkBounds;

    private Texture sugarTexture;
    private Rectangle sugarBounds;

    private boolean hasCoffee;
    private boolean hasMilk;
    private boolean hasSugar;

    private boolean won;

    private Vector3 touchPos;

    @Override
    public void create() {
        touchPos = new Vector3(0,0,0);
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        coffeeTexture = new Texture(Gdx.files.internal("coffee_sheet.png"));
        coffeeBounds = new Rectangle(100, 100, 64, 64);

        milkTexture = new Texture(Gdx.files.internal("milk_sheet.png"));
        milkBounds = new Rectangle(200, 100, 64, 64);

        sugarTexture = new Texture(Gdx.files.internal("sugar_sheet.png"));
        sugarBounds = new Rectangle(300, 100, 64, 64);

        hasCoffee = false;
        hasMilk = false;
        hasSugar = false;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1,1,1,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if(won){
            batch.draw(new Texture(Gdx.files.internal("glass_sheet.png")), 200, 200, 100,100);
        }

        batch.draw(milkTexture, milkBounds.x, milkBounds.y, milkBounds.width, milkBounds.height);
        batch.draw(coffeeTexture, coffeeBounds.x, coffeeBounds.y, coffeeBounds.width, coffeeBounds.height);
        batch.draw(sugarTexture, sugarBounds.x, sugarBounds.y, sugarBounds.width, sugarBounds.height);
        batch.end();
        handleInput();
        update();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {

            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos = camera.unproject(touchPos);
            float x = touchPos.x;
            float y = touchPos.y;
            System.out.println(x + " " + y);
            System.out.println(milkBounds);
            if (coffeeBounds.contains(x, y)) {
                System.out.println("Coffee");
                hasCoffee = true;
            }
            if (milkBounds.contains(x, y)) {
                System.out.println("Milk");
                hasMilk = true;
            }
            if (sugarBounds.contains(x, y)) {
                System.out.println("Sugar");
                hasSugar = true;
            }
            if (hasCoffee && hasMilk && hasSugar) {

                won = true;
                System.out.println("Won");
                // Игра выиграна!
            }
        }
    }

    private void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}

