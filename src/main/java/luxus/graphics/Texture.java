package luxus.graphics;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class Texture {

    private String _filePath;
    private int _textureId;
    private int _height, _width;

    public Texture(String filePath) {
        this._filePath = filePath;

        // Generates the texture on the GPU.
        this._textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, this._textureId);

        // Setting the texture parameters.

        // Enabling the transparency to appear... well, as transparent.
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Repeat image in both directions when UV are bigger than texture.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        // When stretching the image, pixelate it!
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        // When shrinking the image, pixelate it!
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Loading the image...
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load(this._filePath, width, height, channels, 0);

        // Uploading the pixels to the GPU...
        if (image != null) {
            this._width = width.get(0);
            this._height = height.get(0);

            int numberOfChannels = channels.get(0);
            if (numberOfChannels == 3) { // We loaded a RGB image.
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (numberOfChannels == 4) { // We loaded a RGBA image.
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else {
                assert false : "ERROR: Unknown number of channels for the texture '" + this._filePath + "'!";
            }
        } else {
            assert false : "ERROR: Could not load the texture '" + this._filePath + "'!";
        }

        stbi_image_free(image);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, this._textureId);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return this._width;
    }

    public int getHeight() {
        return this._height;
    }
}
