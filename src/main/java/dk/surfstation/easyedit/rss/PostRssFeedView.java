package dk.surfstation.easyedit.rss;

import com.google.common.collect.Streams;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import dk.surfstation.easyedit.domain.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Inspiration: http://www.concretepage.com/spring-4/spring-4-mvc-atom-and-rss-feed-example-with-rome-using-javaconfig
// https://kielczewski.eu/2015/10/adding-rss-to-spring-boot-application-with-rome/

@Component
public class PostRssFeedView extends AbstractRssFeedView {
	public PostRssFeedView() {
		setContentType("application/rss+xml");
	}

	@Override
	protected Channel newFeed() {
		return new Channel("rss_2.0");
	}

	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
		feed.setTitle("Surfstation Easy Edit Rss Feed");
		feed.setDescription("Some description");
		feed.setLink("http://surfstation.dk");
	}

	@Override
	protected List<Item> buildFeedItems(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		@SuppressWarnings("unchecked")
		Iterable<Post> posts = (Iterable<Post>) map.get("posts");
		return Streams
					.stream(posts)
					.map(this::createItem)
					.collect(Collectors.toList());
	}

	private Item createItem(Post post) {
		Item item = new Item();
//		item.setLink(baseUrl + post.getId());
		item.setTitle(post.getTitle());
		item.setDescription(createDescription(post));
		Date pubDate = new Date(post.getCreated());
		item.setPubDate(pubDate);
		return item;
	}

	private Description createDescription(Post post) {
		Description description = new Description();
		description.setType(Content.HTML);
		description.setValue(post.getContent());
		return description;
	}
}
