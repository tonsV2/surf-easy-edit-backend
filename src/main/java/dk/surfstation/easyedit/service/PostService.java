package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService implements PostServiceInterface {
	private final PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Optional<Post> findOne(long id) {
		return Optional.of(postRepository.findOne(id));
	}

	@Override
	public Iterable<Post> findAllByUsername(String username) {
		return postRepository.findAllByUsername(username);
	}

	@Override
	public void delete(long id) {
		postRepository.delete(id);
	}
}
