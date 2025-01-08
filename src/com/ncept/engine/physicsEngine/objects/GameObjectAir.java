package com.ncept.engine.physicsEngine.objects;

import com.ncept.engine.physicsEngine.objects.etc.ObjectType;
import com.ncept.engine.renderEngine.core.GameManager;
import com.ncept.engine.renderEngine.core.Window;
import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class GameObjectAir extends GameObject {

    public GameObjectAir() {
        this.hitbox = new Rectangle();
        this.color = new Color(0, 0, 0, 0);
    }

    @Override
    public void setHitbox(Rectangle box) {
    }

    @Override
    public Enum getType() {
        return ObjectType.AIR;
    }

    @Override
    public void update(Window win, GameManager gm) {

    }

}
