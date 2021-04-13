package luxus;

import luxus.components.SpriteRenderer;
import luxus.graphics.Spritesheet;
import luxus.utils.AssetPool;
import luxus.utils.MapUtils;
import org.joml.Vector2f;

public class Map {

    private String _resourceName;
    private int _width;
    private int _height;
    private int _tileSize;

    private GameObject[][] _tiles;
    // TODO: This data chunk will be loaded from a JSON file.
    private int[] _map = {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 1,
            1, 34, 46, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 46, 34, 1,
            1, 34, 3, 6, 4, 4, 4, 4, 4, 4, 4, 4, 6, 3, 34, 1,
            1, 34, 3, 6, 227, 226, 226, 226, 226, 226, 226, 225, 6, 3, 34, 1,
            1, 34, 3, 6, 259, 258, 258, 258, 258, 258, 258, 257, 6, 3, 34, 1,
            1, 34, 3, 6, 259, 258, 258, 258, 258, 258, 258, 257, 6, 3, 34, 1,
            1, 34, 3, 6, 259, 258, 258, 258, 258, 258, 258, 257, 6, 3, 34, 1,
            1, 34, 3, 6, 259, 258, 258, 258, 258, 258, 258, 257, 6, 3, 34, 1,
            1, 34, 3, 6, 291, 290, 290, 290, 290, 290, 290, 289, 6, 3, 34, 1,
            1, 34, 3, 6, 4, 4, 4, 4, 4, 4, 4, 4, 6, 3, 34, 1,
            1, 34, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3, 34, 1,
            1, 34, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3, 34, 1,
            1, 34, 46, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 46, 34, 1,
            1, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };

    public Map(String resourceName, int width, int height, int tileSize) {
        this._resourceName = resourceName;
        this._width = width;
        this._height = height;
        this._tileSize = tileSize;

        this._tiles = new GameObject[this._width][this._height];
        // TODO: Find a way for the Tiled2D export a JSON starting from top to the right.
        this._map = MapUtils.reverseChunkData(this._map);
    }

    public void loadMap() {
        AssetPool.addSpritesheet("kokiri-forest", new Spritesheet(AssetPool.getTexture("assets/images/tileset_kokiri_forest.png"), 16, 16, 900, 0));
        Spritesheet spritesheet = AssetPool.getSpritesheet("kokiri-forest");

        for (int y = 0; y < this._height; y++) {
            for (int x = 0; x < this._width; x++) {
                this._tiles[x][y] = new GameObject("Tile " + x + "" + y, new Transform(new Vector2f(x * this._tileSize, y * this._tileSize), new Vector2f(this._tileSize, this._tileSize)));
                this._tiles[x][y].addComponent(new SpriteRenderer(spritesheet.getSprite(getTileAt(x, y, this._width))));
                Window.getCurrentScene().addGameObjectToScene(this._tiles[x][y]);
            }
        }
    }

    private int getTileAt(int x, int y, int width) {
        return this._map[(width * y) + x] - 1;
    }
}
