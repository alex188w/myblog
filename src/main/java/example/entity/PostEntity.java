package example.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String preview;
    private String imageUrl;
    private String text;
    private int commentsCount;
    private int likes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag")
    private List<String> tags;

    // Геттеры и сеттеры
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTagsAsText() {
        return tags == null ? "" : String.join(", ", tags);
    }

    public void setTagsFromText(String text) {
        if (text == null || text.isBlank()) {
            this.tags = new ArrayList<>();
        } else {
            this.tags = Arrays.stream(text.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getTextParts() {
        return text == null ? List.of() : Arrays.asList(text.split("\\r?\\n\\r?\\n"));
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public PostEntity orElseThrow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
}
