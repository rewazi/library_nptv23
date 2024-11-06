package ee.ivkhkdev.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Card implements Serializable {
    private UUID id;
    private User user;
    private Book book;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private boolean overdue;

    public Card() {
        this.id = UUID.randomUUID();
    }

    public Card(User user, Book book, LocalDate issueDate, LocalDate returnDate) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.book = book;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.overdue = false;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public boolean checkIfOverdue() {
        return LocalDate.now().isAfter(this.getReturnDate());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", overdue=" + overdue +
                '}';
    }
}

