package luxus.components;

import luxus.Component;

public class Animation extends Component {

    private Spritesheet _sprites;
    private float _animationSpeed;
    private boolean _hasLoop;
    private boolean _isRunning;
    private float _elapsedTime;
    private float _numberOfFrames; // Can we get this accessing the Spritesheet?

    private int _currentSpriteIndex = 0;

    public Animation(Spritesheet sprites, float animationSpeed, float numberOfFrames, boolean hasLoop) {
        this._sprites = sprites;
        this._animationSpeed = animationSpeed;
        this._numberOfFrames = numberOfFrames;
        this._hasLoop = hasLoop;
    }

    @Override
    public void start() {
        this._isRunning = true;
    }

    @Override
    public void update(float deltaTime) {
        if (this._isRunning) {
            this._elapsedTime += deltaTime;
            if (this._elapsedTime >= this._animationSpeed) {
                this._elapsedTime = 0f;
                gameObject.getComponent(SpriteRenderer.class).setSprite(this._sprites.getSprite(this._currentSpriteIndex++));
                if (this._currentSpriteIndex >= this._numberOfFrames) {
                    if (this._hasLoop) {
                        this._currentSpriteIndex = 0;
                    } else {
                        this._isRunning = false;
                    }
                }
            }
        }
    }
}
