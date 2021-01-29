package Flipper;

import com.badlogic.gdx.physics.box2d.BodyDef;

import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2dStage;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Timers.PermanentTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.PermanentTimerListener;

public class FlipperutoActor2 extends BaseHitboxActor {
    MyFixtureDef myFixtureDef;
    public FlipperutoActor2(Box2dStage stage, MyFixtureDef fixtureDef, BodyDef.BodyType bodyType, float width, float height) {
        super(stage, "Flipper/flipperuto2.png", "flipperuto2", new FlipperUtoFixtureDef(), BodyDef.BodyType.KinematicBody, width, height);
        setPosition(50,5);

        addTimer(new PermanentTimer(new PermanentTimerListener(){
            @Override
            public void onTick(PermanentTimer sender, float correction) {
                super.onTick(sender, correction);
                if (state) {
                    //if (Math.abs(Math.toDegrees((box2DWorldHelper.getBody().getAngle() + Math.PI) % (Math.PI * 2f))) < 200) {
                    if (box2DWorldHelper.getBody().getAngle() > -0.6f) {
                        box2DWorldHelper.getBody().setAngularVelocity(-25f);
                    }else{
                        box2DWorldHelper.getBody().setAngularVelocity(0f);
                        //setRotation((int)Math.toDegrees(-0.6f));
                    }
                } else {
                    //if (Math.abs(Math.toDegrees((box2DWorldHelper.getBody().getAngle() + Math.PI) % (Math.PI * 2f))) > 180) {
                    if (box2DWorldHelper.getBody().getAngle() < 0.4f) {
                        box2DWorldHelper.getBody().setAngularVelocity(6f);
                    }else{
                        box2DWorldHelper.getBody().setAngularVelocity(0f);
                        //setRotation((int)Math.toDegrees(0.4f));
                    }
                }
            }
        }));

    }

    private boolean state = false;

    public void hitUp(){
        state = true;
    }

    public void hitDown(){
        state = false;
    }
}
