package luxus.components;

import luxus.Component;
import luxus.Transform;
import luxus.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.HashMap;
import java.util.Map;

public class SpriteRenderer extends Component {

    private Vector4f _color;
    private Sprite _sprite;

    private Transform _lastTransform;
    private boolean _isSpriteDirty;

    private Map<String, Animation> _animations = new HashMap<>();
    private Animation _currentAnimation;
    private boolean _isAnimated;
    private boolean _isPlaying;
    private float _elapsedTime;
    private int _currentSpriteIndex;

    public SpriteRenderer(Sprite sprite) {
        this._sprite = sprite;
        this._color = new Vector4f(1, 1, 1, 1);
    }

    public SpriteRenderer(Map<String, Animation> animations) {
        this._animations = animations;
        this._color = new Vector4f(1, 1, 1, 1);
        this._sprite = this._animations.get("Idle").getAnimationSpritesheet().getSprite(0);
        this._isAnimated = true;
    }

    @Override
    public void start() {
        this._isSpriteDirty = true;
        this._lastTransform = gameObject.getTransform().copy();
    }

    @Override
    public void update(float deltaTime) {
        if (!this._lastTransform.equals(this.gameObject.getTransform())) {
            this.gameObject.getTransform().copy(this._lastTransform);
            this._isSpriteDirty = true;
        }
        animateSprite(deltaTime);
    }

    public void playAnimation(String animationName) {
        if (this._currentAnimation != this._animations.get(animationName)) {
            this._currentAnimation = this._animations.get(animationName);
            this._isPlaying = true;
            this._currentSpriteIndex = 0;
            this._elapsedTime = 0;
        }
    }

    private void animateSprite(float deltaTime) {
        if (this._isAnimated) {
            if (this._isPlaying && this._currentAnimation.getNumberOfFrames() == 1) {
                gameObject.getComponent(SpriteRenderer.class).setSprite(this._currentAnimation.getAnimationSpritesheet().getSprite(0));
                this._isPlaying = false;
            } else if (this._isPlaying) {
                this._elapsedTime += deltaTime;
                if (this._elapsedTime >= this._currentAnimation.getAnimationSpeed()) {
                    this._elapsedTime = 0f;
                    gameObject.getComponent(SpriteRenderer.class).setSprite(this._currentAnimation.getAnimationSpritesheet().getSprite(this._currentSpriteIndex++));
                    if (this._currentSpriteIndex >= this._currentAnimation.getNumberOfFrames()) {
                        if (this._currentAnimation.hasLoop()) {
                            this._currentSpriteIndex = 0;
                        } else {
                            this._isPlaying = false;
                        }
                    }
                }
            }
        }
    }

    public Vector4f getColor() {
        return this._color;
    }

    public void setSprite(Sprite newSprite) {
        this._sprite = newSprite;
        this._isSpriteDirty = true;
    }

    public Texture getTexture() {
        return this._sprite.getTexture();
    }

    public Vector2f[] getTextureCoordinates() {
        return this._sprite.getTextureCoordinates();
    }

    public boolean isDirty() {
        return this._isSpriteDirty;
    }

    public void cleanSpriteRenderer() {
        this._isSpriteDirty = false;
    }
}
