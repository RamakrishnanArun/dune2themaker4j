package com.fundynamic.d2tm.game.entities.sidebar;


import com.fundynamic.d2tm.game.behaviors.Updateable;
import com.fundynamic.d2tm.game.entities.EntityData;
import org.newdawn.slick.Image;

/**
 * <h1>General</h1>
 * An entity that can be built by a {@link com.fundynamic.d2tm.game.behaviors.EntityBuilder}. It contains little
 * state giving hints if it can be built, its progress and so forth.
 */
public class BuildableEntity implements Updateable {

    /**
     * The thing it is / can be building
     */
    private EntityData entityData;

    private float secondsToBuildInMs;

    public BuildableState buildableState = BuildableState.SELECTABLE;

    public BuildableEntity(EntityData entityData) {
        this.entityData = entityData;
        secondsToBuildInMs = entityData.buildTimeInSeconds;
    }

    public EntityData getEntityData() {
        return entityData;
    }

    public boolean hasBuildIcon() {
        return entityData.buildIcon != null;
    }

    public Image getBuildIcon() {
        return entityData.buildIcon;
    }

    public void startBuilding() {
        secondsToBuildInMs = entityData.buildTimeInSeconds;
        buildableState = BuildableState.BUILDING;
    }

    public void disable() {
        buildableState = BuildableState.DISABLED;
    }

    public void enable() {
        buildableState = BuildableState.SELECTABLE;
    }

    public BuildableState getBuildableState() {
        return buildableState;
    }

    @Override
    public void update(float deltaInSeconds) {
        secondsToBuildInMs -= deltaInSeconds;
        if (secondsToBuildInMs < 0) {
            buildableState = BuildableState.AWAITSPLACEMENT;
        }
    }

    public boolean awaitsPlacement() {
        return buildableState == BuildableState.AWAITSPLACEMENT;
    }

    /**
     * Returns a number between 0.0 (started) and 1.0 (completed).
     *
     * @return
     */
    public float getProgress() {
        return (entityData.buildTimeInSeconds - secondsToBuildInMs) / entityData.buildTimeInSeconds;
    }

}