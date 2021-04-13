package luxus.graphics;

import org.joml.Vector2f;

public class Sprite {

    private Texture _texture;
    private Vector2f[] _textureCoordinates;

    public Sprite(Texture texture) {
        this._texture = texture;
        this._textureCoordinates = new Vector2f[] {
                    new Vector2f(1, 1),
                    new Vector2f(1, 0),
                    new Vector2f(0, 0),
                    new Vector2f(0, 1)
        };
    }

    public Sprite(Texture texture, Vector2f[] textureCoordinates) {
        this._texture = texture;
        this._textureCoordinates = textureCoordinates;
    }

    public Texture getTexture() {
        return this._texture;
    }

    public Vector2f[] getTextureCoordinates() {
        return this._textureCoordinates;
    }
}
