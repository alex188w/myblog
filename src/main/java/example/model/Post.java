package example.model;

import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String preview;
    private String imageUrl;
    private List<String> tags;
    private int commentsCount;
    private int likes;

    public Post() {}

    public Post(Long id, String title, String preview, String imageUrl, List<String> tags, int commentsCount, int likes) {
        this.id = id;
        this.title = title;
        this.preview = preview;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.commentsCount = commentsCount;
        this.likes = likes;
    }

    // Геттеры и сеттеры ниже
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPreview() { return preview; }
    public void setPreview(String preview) { this.preview = preview; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public int getCommentsCount() { return commentsCount; }
    public void setCommentsCount(int commentsCount) { this.commentsCount = commentsCount; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }
}
