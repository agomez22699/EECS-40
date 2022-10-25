package eecs40.rssmanager;

import java.util.List;
import java.util.ArrayList;

import eecs40.observer.RSSFeedObserver;
import eecs40.rssfeed.RSSFeedInterface;


public class RSSManager extends AbstractRSSManager{

    List<RSSFeedObserver> feedObserver = new ArrayList<>();
    List<RSSFeedInterface> newsDigest = new ArrayList<>();

    Long timer;

    @Override
    public void addFeed(RSSFeedInterface f) {
        newsDigest.add(f);
    }

    @Override
    public void removeFeed(RSSFeedInterface f) {
        newsDigest.remove(f);
    }

    @Override
    public List<RSSFeedInterface> getFeedList() {
        return newsDigest;
    }

    @Override
    public void addObserver(RSSFeedObserver ob) {
        feedObserver.add(ob);
    }

    @Override
    public void removeObserver(RSSFeedObserver ob) {
        feedObserver.remove(ob);
    }

    @Override
    public List<RSSFeedObserver> getObserverList() {
        return feedObserver;
    }

    @Override
    public void setInterval(long interval) {
        timer = interval;
    }

    @Override
    public long getInterval() {
        return timer;
    }

}
