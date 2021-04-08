package luxus.components;

import luxus.renderer.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {

    private Texture _texture;
    private List<Sprite> _sprites;

    public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int numberOfSprites, int spacing) {
        this._sprites = new ArrayList<>();
        this._texture = texture;
        int currentX = 0;
        int currentY = this._texture.getHeight() - spriteHeight;
        for (int index = 0; index < numberOfSprites; index++) {
            float topY = (currentY + spriteHeight) / (float) this._texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float) this._texture.getWidth();
            float leftX = currentX / (float) this._texture.getWidth();
            float bottomY = currentY / (float) this._texture.getHeight();

            Vector2f[] textureCoordinates = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY)
            };

            Sprite sprite = new Sprite(this._texture, textureCoordinates);
            this._sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if (currentX >= this._texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }

    public Sprite getSprite(int index) {
        return this._sprites.get(index);
    }
}
