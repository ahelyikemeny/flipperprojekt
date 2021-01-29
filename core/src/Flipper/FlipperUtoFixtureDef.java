package Flipper;

import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;

public class FlipperUtoFixtureDef extends MyFixtureDef {
    public FlipperUtoFixtureDef() {
        density = 5000f;
        restitution=0.6f;
        friction = 0.2f;
    }
}
