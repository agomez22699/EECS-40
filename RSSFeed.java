package eecs40.rssfeed;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import de.vogella.rss.model.Feed;
import de.vogella.rss.read.RSSFeedParser;
import de.vogella.rss.model.FeedMessage;


public class RSSFeed implements RSSFeedInterface {

    List<String> newsDigestID = new ArrayList<>();
    int counter;
    Feed newsDigest;
    RSSFeedParser parser;

    public RSSFeed(String companyURL, String companyTitle) {

        parser = new RSSFeedParser(companyURL);

        newsDigest = parser.readFeed();

        newsDigest.getMessages().clear();

    }
    @Override
    //return Feed
    public Feed getFeed() {
        return newsDigest;
    }
    @Override
    //read feed and return # of updated items
    public int read() {
        counter = 0;
        Feed updatedNewsDigest = parser.readFeed(); // checks if theres is any new update
        for (FeedMessage message : updatedNewsDigest.getMessages()) {// gets into updated

            if(!newsDigestID.contains(message.getGuid())) {//

                newsDigestID.add(message.getGuid());

                newsDigest.getMessages().add(0,message); //adds the new title to the top

                counter++;
            }
        }
        return getLastNumUpdate();

    }
    @Override
    //return number of items
    public int size() {
        return newsDigest.getMessages().size();
    }

    @Override
    // this returns the number of items that were updated after the latest read
    public int getLastNumUpdate() {
        return counter;
    }

    @Override
    public Iterator<FeedMessage> iterator() {
        return newsDigest.getMessages().iterator();
    }
}