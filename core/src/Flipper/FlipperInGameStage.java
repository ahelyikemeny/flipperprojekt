package Flipper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2dStage;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyContactListener;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Timers.OneTickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.OneTickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.PermanentTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.PermanentTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class FlipperInGameStage extends Box2dStage {
    MyContactListener myContactListener;

    ClickListener c1;

    RestartButton restartButton;
    BallActor ballActor;
    FlipperutoActor flipperutoActor;
    FlipperutoActor2 flipperutoActor2;
    PointActorCircle pointActorCircle;
    private Vector2 lastClick = null;
    private MyLabel lifeCounter;
    private MyLabel pointCounter;
    private int life = 3;
    private int points = 0;

    private int tiltCount = 0;
    private boolean tilt = false;


    public void setLife(int life) {
        this.life = life;
        lifeCounter.setText("Points:" + life);
    }


    private void setPoint(int p){
        points = p;
        pointCounter.setText("Points: " + points);
        System.out.println(p);
    }

    public void addPoint(int p) {
        setPoint(points + p);
    }


    private boolean cheatGravity = false;
    public void cheat(Vector2 vector2){
        if (!cheatGravity) {
            cheatGravity = true;
            Vector2 grav = getWorld().getGravity();
            getWorld().setGravity(vector2);
            addTimer(new OneTickTimer(0.03f, new OneTickTimerListener() {
                @Override
                public void onTick(OneTickTimer sender, float correction) {
                    super.onTick(sender, correction);
                    getWorld().setGravity(grav);
                    cheatGravity = false;
                }
            }));
            tiltCount++;
            if (tiltCount > 2){
                System.out.println("TILT");
                tilt = true;
            }
        }
    }

    public int getLife() {
        return life;
    }
    public void endGame() {
        if (getLife() == 0){
            setLife(0);
            lifeCounter.setText("Ran out of balls!");

            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = game.getMyAssetManager().getFont("Flipper/font2.ttf");
            labelStyle.fontColor = Color.YELLOW;
            MyLabel label = new MyLabel(game, "Back", labelStyle);
            label.setFontScale(0.4f);
            label.setPosition(30,50);
            label.addListener(c1 = new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    game.getMyAssetManager().getSound("click.mp3").play();
                    game.setScreen(new FlipperMenuScreen(game));
                }
            });
            addActor(label);

            restartButton = new RestartButton(game, 40, 40, 30, 80);
            addActor(restartButton);
            ballActor.remove();
        }
    }

    private RandomXS128 random = new RandomXS128();

    public FlipperInGameStage(MyGame game) {
        super(new ExtendViewport(90,160), game);



        //setTimeMultiply(2);


        addTimer(new TickTimer(10f, true, new TickTimerListener(){
            @Override
            public void onRepeat(TickTimer sender) {
                super.onRepeat(sender);
                if (tiltCount > 0){
                    tiltCount--;
                }
            }
        }));

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (!tilt) {
                    if (keycode == Input.Keys.CONTROL_RIGHT) {
                        flipperutoActor2.hitUp();
                    }
                    if (keycode == Input.Keys.CONTROL_LEFT) {
                        flipperutoActor.hitUp1();
                    }
                }
                return super.keyDown(event, keycode);
            }

            public boolean keyUp(InputEvent event, int keycode) {
                if (!tilt) {
                    if (keycode == Input.Keys.CONTROL_LEFT) {
                        flipperutoActor.hitDown1();
                    }

                    if (keycode == Input.Keys.CONTROL_RIGHT) {
                        flipperutoActor2.hitDown();
                    }
                }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (!tilt) {
                    if (character == ' ') {
                        cheat(new Vector2(random.nextInt(10000) - 5000, random.nextInt(10000) - 5000));
                    }
                }
                return super.keyTyped(event, character);
            }

        });




        world.setGravity(new Vector2(0f,-100f));
        setCameraResetToCenterOfScreen();
        addBackButtonScreenBackByStackPopListener();
        setLoader("Flipper/hitboxes");
        addActor(new BgActor(this));
        addActor(new KatapultActor(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 30,30 ));
        addActor(new KatapultActor2(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 30,30));
        addActor(flipperutoActor = new FlipperutoActor(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 25, 25));
        addActor(flipperutoActor2 = new FlipperutoActor2(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 25, 25));
        //addActor(new GyorsitoActor(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 30, 30));
        SensorActor sensorActor = new SensorActor(game, world, 13,2,74,68);
        sensorActor.setRotation(30);
        addActor(sensorActor);
        SensorActor2 sensorActor2 = new SensorActor2(game, world, 10, 2, 3, 68);
        sensorActor2.setRotation(-30);
        addActor(sensorActor2);
        ballActor = new BallActor(game, world, 5,5,29,85);
        addActor(ballActor);
        BottomSensorActor bottomSensorActor = new BottomSensorActor(game, world,200,10,0,-10);
        addActor(bottomSensorActor);

        PointActorCircle pointActorCircle = new PointActorCircle(game,world, 15,90);
        PointActorCircle pointActorCircle2 = new PointActorCircle(game,world, 60, 90);
        addActor(pointActorCircle2);
        addActor(pointActorCircle);


        PocokActor pocokActor = new PocokActor(game, world, 30, 135);
        PocokActor pocokActor2 = new PocokActor(game, world, 50, 135);
       // PocokActor pocokActor3 = new PocokActor(game, world, 10, 135);
        addActor(pocokActor);
        addActor(pocokActor2);
        //addActor(pocokActor3);


        lifeCounter = new MyLabel(game, "Life: ", new LifeCounter(game));
        addActor(lifeCounter);
        lifeCounter.setFontScale(0.3f);
        lifeCounter.setPositionCenter(-25);
        lifeCounter.setFontScale(0.3f);
        lifeCounter.setAlignment(2);


        pointCounter = new MyLabel(game, "", new PointCounter(game));
        addActor(pointCounter);
        pointCounter.setPositionCenter(135);
        pointCounter.setFontScale(0.3f);
        pointCounter.setAlignment(2);
        setPoint(0);


        getHelper(bottomSensorActor).addContactListener(new MyContactListener() {
            @Override
            public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                if (otherHelper.getActor() instanceof BallActor){
                    otherHelper.getActor().setPosition(60,50);
                    setLife(getLife() - 1);
                    lifeCounter.setText((getLife()));
                    tilt = false;
                    tiltCount = 0;
                    otherHelper.invoke(new Runnable() {
                        @Override
                        public void run() {
                            otherHelper.getBody().setLinearVelocity(0,10);
                            otherHelper.getBody().setAngularVelocity(0);
                            endGame();
                        }
                    });
                }
            }

            @Override
            public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }

            @Override
            public void preSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }

            @Override
            public void postSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }
        });




        getHelper(sensorActor2).addContactListener(new MyContactListener() {
            @Override
            public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                if (otherHelper.getActor() instanceof BallActor){
                    if (lastClick == null) {
                        lastClick = new Vector2(myHelper.body.getPosition());
                        game.getMyAssetManager().getSound("Flipper/katapult.mp3").play();
                    }else{
                        if (lastClick.sub(myHelper.body.getPosition()).len() > 5f) {
                            game.getMyAssetManager().getSound("Flipper/katapult.mp3").play();
                        }
                        lastClick.set(myHelper.body.getPosition());
                    }
                    otherHelper.invoke(new Runnable() {
                        @Override
                        public void run() {
                            otherHelper.getBody().setLinearVelocity(new Vector2(otherHelper.getBody().getLinearVelocity().x + 30, otherHelper.getBody().getLinearVelocity().y + 80));
                            otherHelper.getBody().setAngularVelocity(0);
                        }
                    });
                }
            }

            @Override
            public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }

            @Override
            public void preSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }

            @Override
            public void postSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }
        });




        getHelper(sensorActor).addContactListener(new MyContactListener() {
            @Override
            public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                if (otherHelper.getActor() instanceof BallActor){
                    if (lastClick == null) {
                        lastClick = new Vector2(myHelper.body.getPosition());
                        game.getMyAssetManager().getSound("Flipper/katapult.mp3").play();
                    }else{
                        if (lastClick.sub(myHelper.body.getPosition()).len() > 5f) {
                            game.getMyAssetManager().getSound("Flipper/katapult.mp3").play();
                        }
                        lastClick.set(myHelper.body.getPosition());
                    }
                    otherHelper.invoke(new Runnable() {
                        @Override
                        public void run() {
                            otherHelper.getBody().setLinearVelocity(new Vector2(otherHelper.getBody().getLinearVelocity().x + 30, otherHelper.getBody().getLinearVelocity().y + 80));
                            otherHelper.getBody().setAngularVelocity(0);

                        }
                    });
                }
            }

            @Override
            public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }

            @Override
            public void preSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }

            @Override
            public void postSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

            }
        });
//Bal oldali hover actor\\
        Actor leftControlActor;
        leftControlActor = new Actor(); //new OneSpriteStaticActor(game, "badlogic.jpg");
        leftControlActor.setDebug(game.debug);
        leftControlActor.setPosition(0,- (getViewport().getWorldHeight() / 2 - ((ExtendViewport)getViewport()).getCamera().position.y));
        leftControlActor.setSize(getViewport().getWorldWidth() / 3, getViewport().getWorldHeight());
        leftControlActor.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                flipperutoActor.hitUp1();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                flipperutoActor.hitDown1();
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(leftControlActor);
        //Jobb oldali hover actor\\
        Actor rightControlActor;
        rightControlActor = new Actor();
        rightControlActor.setDebug(game.debug);
        rightControlActor.setPosition(60,- (getViewport().getWorldHeight() / 2 - ((ExtendViewport)getViewport()).getCamera().position.y));
        rightControlActor.setSize(getViewport().getWorldWidth() / 3, getViewport().getWorldHeight());
        rightControlActor.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                flipperutoActor2.hitUp();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                flipperutoActor2.hitDown();
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(rightControlActor);

        final PermanentTimer p;
        addTimer(p = new PermanentTimer(new PermanentTimerListener(){
            Vector3 oldXYZ = null;
            @Override
            public void onTick(PermanentTimer sender, float correction) {
                super.onTick(sender, correction);

                if (oldXYZ != null){
                    Vector3 newXYZ = new Vector3();
                    newXYZ.x = Gdx.input.getAccelerometerX();
                    newXYZ.y = Gdx.input.getAccelerometerY();
                    newXYZ.z = Gdx.input.getAccelerometerZ();

                    if (oldXYZ.sub(newXYZ).len() >= 0.4f * Gdx.graphics.getDeltaTime()){
                        Gdx.app.log("Flipper", "Tilt");
                        cheat(new Vector2(oldXYZ.sub(newXYZ).x * 100, oldXYZ.sub(newXYZ).z * 100));
                        sender.stop();
                        addTimer(new OneTickTimer(0.5f, new OneTickTimerListener(){
                            @Override
                            public void onTick(OneTickTimer sender2, float correction) {
                                super.onTick(sender2, correction);
                                sender.start();
                            }
                        }));
                    }
                }
                else {
                    oldXYZ = new Vector3();
                }

                oldXYZ.x = Gdx.input.getAccelerometerX();
                oldXYZ.y = Gdx.input.getAccelerometerY();
                oldXYZ.z = Gdx.input.getAccelerometerZ();

            }
        }));

    }

}
