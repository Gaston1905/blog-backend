package blog.backend.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import blog.backend.exceptions.CategoryNotFound;
import blog.backend.exceptions.PostNotFoundException;
import blog.backend.exceptions.UserNotFound;
import blog.backend.payloads.PostDto;
import blog.backend.payloads.PostResponse;
import blog.backend.post.entity.Category;
import blog.backend.post.entity.Post;
import blog.backend.post.repository.CategoryRepository;
import blog.backend.post.repository.PostRepository;
import blog.backend.user.UserRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.criteria.Path;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  public ModelMapper mapper;

  @Autowired
  private Map<String, Long> likesMap;

  @Autowired
  private Map<String, Long> dislikesMap;

  @Value("${project-image}")
  String uploadDir;

  @Override
  public PostDto getPostById(int postId) {

    Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Posteo no encontrado!!"));
    if (likesMap.containsKey(String.valueOf(postId))) {
      long likeCounts = likesMap.get(String.valueOf(postId));
      post.setLikeCounts(likeCounts);
    }
    if (dislikesMap.containsKey(String.valueOf(postId))) {
      long dislikeCounts = dislikesMap.get(String.valueOf(postId));
      post.setDislikeCounts(dislikeCounts);
    }
    PostDto dto = mapper.map(post, PostDto.class);

    return dto;
  }

  @Override
  public PostResponse getPostByUser(int userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    Page<Post> page = postRepository.findByUserId(userId, pageable);
    List<Post> posts = page.getContent();
    List<PostDto> postsDto = posts.stream().map(p -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
    PostResponse postResponse = new PostResponse();
    postResponse.setContents(postsDto);
    postResponse.setPageSize(pageSize);
    postResponse.setPageNumber(pageNumber);
    postResponse.setTotalElements(page.getTotalElements());
    postResponse.setTotalPages(page.getTotalPages());
    postResponse.setLast(page.isLast());
    return postResponse;
  }

  @Override
  public PostDto uploadImage(String filename, MultipartFile file, int id) throws IOException {
    Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Posteo no encontrado!!"));

    java.nio.file.Path uploadPath = Paths.get(uploadDir + File.separator + id);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    try (InputStream inputStream = file.getInputStream()) {
      String fileCode = RandomStringUtils.randomAlphanumeric(8);
      filename = fileCode + "_" + filename;
      post.setImageName(filename);
      post = postRepository.save(post);
      Path filePath = (Path) uploadPath.resolve(filename);
      // Files.copy(inputStream, filePath);
      return mapper.map(post, PostDto.class);
    } catch (IOException ioe) {
      throw new IOException("La imagen no ha podido ser guardada");
    }
  }

  @Override
  public InputStream getImage(Integer id) throws FileNotFoundException {
    Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(" Posteo no encontrado!!"));
    String fileName = post.getImageName();
    String fullPath = uploadDir + File.separator + id + File.separator + fileName;
    try {
      InputStream is = new FileInputStream(fullPath);
      return is;
    } catch (Exception e) {
      fullPath = uploadDir + File.separator + fileName;
      InputStream is = new FileInputStream(fullPath);
      return is;
    }
  }

  @Override
  public void storeLikeCounts(Integer postId, Long likeCounts, Long dislikeCounts) {
    likesMap.put(String.valueOf(postId), likeCounts);
    dislikesMap.put(String.valueOf(postId), dislikeCounts);
  }

  @Transactional
  @Scheduled(cron = "1 * * * * * *")
  public void storeLikes() {
    if (!likesMap.isEmpty() || !dislikesMap.isEmpty()) {
      for (Map.Entry<String, Long> like : likesMap.entrySet()) {
        int postId = Integer.parseInt(like.getKey());
        long likeCounts = like.getValue();
        PostDto post = getPostById(postId);
        post.setLikeCounts(likeCounts);
        post = updatePost(post, postId);
      }
      likesMap.clear();
    }
    if (!dislikesMap.isEmpty()) {
      for (Map.Entry<String, Long> dislike : dislikesMap.entrySet()) {
        int postId = Integer.parseInt(dislike.getKey());
        long dislikeCounts = dislike.getValue();
        PostDto post = getPostById(postId);
        post.setDislikeCounts(dislikeCounts);
        post = updatePost(post, postId);
      }
      dislikesMap.clear();
    }
  }

  @Override
  public PostDto createPost(PostDto postDto, int userId, int categoryId) {
    blog.backend.user.User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFound("Usuario no encontrado!!"));
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CategoryNotFound("Categoría no encontrada!!"));
    Post post = mapper.map(postDto, Post.class);
    post.setImageName("default.jpg");
    post.setAddedDate(new Date());
    post.setUser(user);
    post.setCategory(category);
    post = postRepository.save(post);
    return mapper.map(post, PostDto.class);
  }

  @Override
  public PostDto updatePost(PostDto postDto, int id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Posteo no encontrado"));
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    post.setDescription(postDto.getDescription());
    post.setLikeCounts(post.getDislikeCounts());
    post.setDislikeCounts(post.getDislikeCounts());
    if (!postDto.getImageName().isBlank()) {
      post.setImageName(postDto.getImageName());
    }
    post.setAddedDate(new Date());
    Category category = categoryRepository.findById(postDto.getCategory().getId())
        .orElseThrow(() -> new CategoryNotFound("Categoría no encontrada"));
    post.setCategory(category);
    post = postRepository.save(post);
    return mapper.map(post, PostDto.class);
  }

  @Override
  public void deletePost(PostDto postDto) throws IOException {
    Post post = mapper.map(postDto, Post.class);
    postRepository.delete(post);
  }

  @Override
  public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    Page<Post> page = postRepository.findAll(pageable);
    List<Post> posts = page.getContent();
    List<PostDto> postsDto = posts.stream().map(p -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
    PostResponse postResponse = new PostResponse();
    postResponse.setContents(postsDto);
    postResponse.setPageSize(pageSize);
    postResponse.setPageNumber(pageNumber);
    postResponse.setTotalElements(page.getTotalElements());
    postResponse.setTotalPages(page.getTotalPages());
    postResponse.setLast(page.isLast());
    return postResponse;
  }

  @Override
  public List<PostDto> searchPost(String keyword) {
    List<Post> posts = postRepository.findBytitleOrContentContains(keyword);
    List<PostDto> postDtos = posts.stream().map(p -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
    return postDtos;
  }

  @Override
  public List<PostDto> getPostByCategory(int categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CategoryNotFound("Categoría no encontrada!!"));
    List<Post> post = postRepository.findByCategory(category);
    return post.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
  }

}
