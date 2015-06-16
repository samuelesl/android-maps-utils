package com.google.maps.android.clustering.algo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A cluster whose center is determined on demand.
 */
public class SimpleCluster<T extends ClusterItem> implements Cluster<T> {
    private LatLng mCenter;
    private final List<T> mItems = new ArrayList<T>();

    public SimpleCluster() {
    }

    public SimpleCluster(LatLng center) {
        mCenter = center;
    }

    /**
     * <p>Add an item to the cluster.</p>
     * <p><em>You should call {@link #recomputeCenter()} before the next {@link #getPosition()} call in
     * order to update this cluster's center position.</em></p>
     *
     * @param t The item to add.
     * @return true if the item was successfully added, false otherwise.
     */
    public boolean add(T t) {
        return mItems.add(t);
    }

    @Override
    public LatLng getPosition() {
        if (mCenter == null) {
            recomputeCenter();
        }
        return mCenter;
    }

    /**
     * <p>Force this cluster's center position calculation.</p>
     * <p>The algorithm used is a simple average, which is not valid for latitude/longitude points,
     * but it's faster/easier.</p>
     * <p><em>You should call this method after editing the cluster's item list.</em></p>
     */
    public void recomputeCenter() {
        double lat = 0;
        double lon = 0;
        for (T item : mItems) {
            LatLng position = item.getPosition();
            lat += position.latitude;
            lon += position.longitude;
        }
        double size = mItems.size();
        mCenter = new LatLng(lat / size, lon / size);
    }

    /**
     * <p>Remove an item from the cluster.</p>
     * <p><em>You should call {@link #recomputeCenter()} before the next {@link #getPosition()} call in
     * order to update this cluster's center position.</em></p>
     *
     * @param t The item to remove.
     * @return true if the item was successfully removed, false otherwise.
     */
    public boolean remove(T t) {
        return mItems.remove(t);
    }

    /**
     * <p>Retrieve the items in the cluster.</p>
     * <p><em>If you change something in the content of the collection, you should call
     * {@link #recomputeCenter()} before the next {@link #getPosition()} call in order to update
     * this cluster's center position.</em></p>
     *
     * @return A collection containing this cluster's items.
     */
    @Override
    public Collection<T> getItems() {
        return mItems;
    }

    @Override
    public int getSize() {
        return mItems.size();
    }

    @Override
    public String toString() {
        return "StaticCluster{" +
                "mCenter=" + mCenter +
                ", mItems.size=" + mItems.size() +
                '}';
    }
}