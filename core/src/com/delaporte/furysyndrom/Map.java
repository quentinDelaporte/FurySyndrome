package com.delaporte.furysyndrom;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class Map {
    private TiledMapRenderer tiledMapRenderer;
    private TiledMap tiledMap;
    private int tilePixelWidth;
    private int tilePixelHeight;
    private int mapHeight;

    public Map(String mapPath) {
        tiledMap = new TmxMapLoader().load(mapPath);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        MapProperties propMap = tiledMap.getProperties();
        tilePixelWidth = propMap.get("tilewidth", Integer.class) * propMap.get("width", Integer.class);
        tilePixelHeight = propMap.get("tileheight", Integer.class) * propMap.get("height", Integer.class);

        mapHeight = propMap.get("height", Integer.class);

    }

    public int getHeight() {
        return mapHeight;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public int getTilePixelHeight() {
        return tilePixelHeight;
    }

    public int getTilePixelWidth() {
        return tilePixelWidth;
    }

    public MapLayer getCollisionLayer(int i) {
        return tiledMap.getLayers().get(i);
    }

    public MapObjects getCollisionTile(int collisionLayer) {
        MapLayer collisionObjectLayer = tiledMap.getLayers().get(collisionLayer);
        MapObjects objects = collisionObjectLayer.getObjects();

        return objects;
    }

    public boolean detectColision(Rectangle hitbox, int collisionLayer) {
        MapObjects collisionObjects = this.getCollisionTile(collisionLayer);
		for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, hitbox))
				return true;
		}
		for (PolygonMapObject polygonObject : collisionObjects.getByType(PolygonMapObject.class)) {
			Polygon polygon = polygonObject.getPolygon();
			Polygon hitboxPolygon = new Polygon(new float[] { hitbox.x, hitbox.y, hitbox.x + hitbox.width, hitbox.y,
					hitbox.x + hitbox.width, hitbox.y + hitbox.height, hitbox.x, hitbox.y + hitbox.height });
			if (Intersector.overlapConvexPolygons(polygon, hitboxPolygon))
				return true;
		}
		return false;
	}
}