package Flipper;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyContactListener;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class PocokActor extends OneSpriteStaticActor {
    public PocokActor(MyGame game, World world, float x, float y) {
        super(game, "Flipper/jobbpocok.png");
        setSize(15, 15);
        setPosition(x, y);
        MyFixtureDef myFixtureDef = new MyFixtureDef();
        myFixtureDef.restitution = 40;
        setActorWorldHelper(new Box2DWorldHelper(world, this, ShapeType.Circle, myFixtureDef, BodyDef.BodyType.StaticBody));
        ((Box2DWorldHelper) getActorWorldHelper()).addContactListener(new MyContactListener() {
            @Override
            public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                if (otherHelper.actor instanceof BallActor) {
                    if (getStage() != null) {
                        ((FlipperInGameStage) getStage()).addPoint(100);
                    }
                    //setPoint(getPoints() + 1);
                    //System.out.println(getPoints());
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
