package Flipper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2dStage;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyContactListener;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Timers.PermanentTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.PermanentTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class FlipperInGameStage extends Box2dStage {
    MyContactListener myContactListener;

    BallActor ballActor;
    FlipperutoActor flipperutoActor;
    FlipperutoActor2 flipperutoActor2;

    private MyLabel lifeCounter;
    private int life = 3;

    public void setLife(int life) {
        this.life = life;
        lifeCounter.setText("Points:" + life);
    }
    public int getLife() {
        return life;
    }
    public void endGame() {
        if (getLife() == 0){
            setLife(0);
            lifeCounter.setText("Elfogytak a golyóid!");
            BackButton backButton = new BackButton(game);
            addActor(backButton);
//            MyGame.printStackTrace();
            ballActor.remove();
        }
    }
    public FlipperInGameStage(MyGame game) {
        super(new ExtendViewport(160,160), game);

        //setTimeMultiply(2);

        addTimer(new TickTimer(0.1f, true, new TickTimerListener(){
            @Override
            public void onTick(Timer sender, float correction) {
                super.onTick(sender, correction);
                Gdx.app.log("ACC","X=" + Gdx.input.getAccelerometerX() + " Y=" + Gdx.input.getAccelerometerY() + " Z=" + Gdx.input.getAccelerometerZ());
            }
        }));

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.CONTROL_RIGHT){
                    flipperutoActor2.hitUp();
                }
                return super.keyDown(event, keycode);
            }

            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.CONTROL_RIGHT){
                    flipperutoActor2.hitDown();
                }
                return super.keyDown(event, keycode);
            }

        });



        world.setGravity(new Vector2(0f,-100f));
        setCameraResetToCenterOfScreen();
        addBackButtonScreenBackByStackPopListener();
        setLoader("Flipper/hitboxes");
        addActor(new BgActor(this));
        addActor(new KatapultActor(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 30,30 ));
        addActor(new KatapultActor2(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 30,30));
        addActor(flipperutoActor = new FlipperutoActor(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 30, 30));
        addActor(flipperutoActor2 = new FlipperutoActor2(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 30, 30));
        addActor(new GyorsitoActor(this, new MyFixtureDef(), BodyDef.BodyType.StaticBody, 30, 30));
        SensorActor sensorActor = new SensorActor(game, world, 10,5,145,70);
        sensorActor.setRotation(65);
        addActor(sensorActor);
        SensorActor2 sensorActor2 = new SensorActor2(game, world, 10, 5, 0, 70);
        sensorActor2.setRotation(-65);
        addActor(sensorActor2);
        ballActor = new BallActor(game, world, 5,5,9,85);
        addActor(ballActor);
        BottomSensorActor bottomSensorActor = new BottomSensorActor(game, world,200,15,0,0);
        addActor(bottomSensorActor);



        lifeCounter = new MyLabel(game, "3", new LifeCounter(game));
        addActor(lifeCounter);
        lifeCounter.setFontScale(0.3f);
        lifeCounter.setPosition(80, 105);
        lifeCounter.setFontScale(0.3f);
        lifeCounter.setAlignment(2);

        getHelper(bottomSensorActor).addContactListener(new MyContactListener() {
            @Override
            public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                if (otherHelper.getActor() instanceof BallActor){
                    otherHelper.getActor().setPosition(60,50);
                    setLife(getLife() - 1);
                    lifeCounter.setText((getLife()));
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
                    otherHelper.invoke(new Runnable() {
                        @Override
                        public void run() {
                            otherHelper.getBody().setLinearVelocity(new Vector2(otherHelper.getBody().getLinearVelocity().x + 70, otherHelper.getBody().getLinearVelocity().y + 60));
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
                    otherHelper.invoke(new Runnable() {
                        @Override
                        public void run() {
                            otherHelper.getBody().setLinearVelocity(0,100);
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
    }
}
