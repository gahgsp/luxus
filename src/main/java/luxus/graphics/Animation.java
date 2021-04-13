package luxus.graphics;

public class Animation {

    private Spritesheet _sprites;
    private float _animationSpeed;
    private boolean _hasLoop;
    private float _numberOfFrames;

    public Animation(Spritesheet sprites, float animationSpeed, float numberOfFrames, boolean hasLoop) {
        this._sprites = sprites;
        this._animationSpeed = animationSpeed;
        this._numberOfFrames = numberOfFrames;
        this._hasLoop = hasLoop;
    }

    public Spritesheet getAnimationSpritesheet() {
        return this._sprites;
    }

    public float getAnimationSpeed() {
        return this._animationSpeed;
    }

    public boolean hasLoop() {
        return this._hasLoop;
    }

    public float getNumberOfFrames() {
        return this._numberOfFrames;
    }
}
