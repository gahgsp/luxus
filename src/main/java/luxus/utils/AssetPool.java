package luxus.utils;

import luxus.graphics.Spritesheet;
import luxus.graphics.Shader;
import luxus.graphics.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Asset manager that provides cached objects to avoid high memory usage.
 * This way we add and access only one object of each asset added to this manager.
 * The asset manager is accessible all across the engine.
 */
public class AssetPool {

    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();

    /**
     * Returns a Shader with the given name as parameter.
     * If the Shader does not exist, this method adds to the manager and returns it.
     * @param resourceName the name of the resource to be retrieved.
     * @return a Shader instance with the given name.
     */
    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);

        if (AssetPool.shaders.containsKey(file.getAbsolutePath())) {
            return AssetPool.shaders.get(file.getAbsolutePath());
        }

        Shader shader = new Shader(resourceName);
        shader.compile();
        AssetPool.shaders.put(file.getAbsolutePath(), shader);
        return shader;
    }

    /**
     * Returns a Texture with the given name as parameter.
     * If the Texture does not exist, this method adds to the manager and returns it.
     * @param resourceName the name of the resource to be retrieved.
     * @return a Texture instance with the given name.
     */
    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);

        if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
            return AssetPool.textures.get(file.getAbsolutePath());
        }

        Texture texture = new Texture(resourceName);
        AssetPool.textures.put(file.getAbsolutePath(), texture);
        return texture;
    }

    /**
     * Add a Spritesheet to the asset manager with the given name and value.
     * @param resourceName the name of the Spritesheet to be added to the asset manager.
     * @param spritesheet the Spritesheet instance to be added.
     */
    public static void addSpritesheet(String resourceName, Spritesheet spritesheet) {
        File file = new File(resourceName);

        if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    /**
     * Search in the asset manager and retrieve a Spritesheet instance with the given resource name.
     * @param resourceName the name of the Spritesheet to be retrieved.
     * @return a Spritesheet instance with the given resource name.
     */
    public static Spritesheet getSpritesheet(String resourceName) {
        File file = new File(resourceName);
        assert AssetPool.spritesheets.containsKey(file.getAbsolutePath()) : "ERROR: The Spritesheet '" + resourceName + "' does not exist!";
        return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }
}
