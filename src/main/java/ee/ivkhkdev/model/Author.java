package ee.ivkhkdev.model;

import java.util.UUID;

public class Author {
    private UUID id;
    private String authorName;
    private String authorSurname;

    public Author() {
        this.id = UUID.randomUUID();
    }

    public Author(String authorName, String authorSurname) {
        this.id = UUID.randomUUID();
        this.authorName = authorName;
        this.authorSurname = authorSurname;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;
        return id.equals(author.id) && authorName.equals(author.authorName) && authorSurname.equals(author.authorSurname);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + authorName.hashCode();
        result = 31 * result + authorSurname.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", authorSurname='" + authorSurname + '\'' +
                '}';
    }
}
