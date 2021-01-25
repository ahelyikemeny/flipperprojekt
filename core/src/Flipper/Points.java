package Flipper;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Points extends OneSpriteStaticActor {
    public Points(MyGame game, World world, float x, float y) {
        super(game, "box2dhelper/ball.png");
        setSize(5,5);
        setPosition(x,y);
        MyFixtureDef myFixtureDef = new MyFixtureDef();
        myFixtureDef.isSensor = true;
        setActorWorldHelper(new Box2DWorldHelper(world, this, ShapeType.Circle, myFixtureDef, BodyDef.BodyType.StaticBody ));
    }
}
