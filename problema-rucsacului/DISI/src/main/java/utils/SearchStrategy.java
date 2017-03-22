package utils;

import model.Bag;

/**
 * Created by Alex Ichim on 11.03.2017.
 */
public interface SearchStrategy {
    Bag findBestBag();
    InfoReader getInfoReader();
}
