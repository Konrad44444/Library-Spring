package database.project.library.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "book_category",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<Category> categories = new ArrayList<>();
    
    private String title;

    private Boolean available;

    //adding category to book and book to category at once
    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
    }

    public void addAuthor(Author author) {
        this.author = author;
        author.getBooks().add(this);
    }

    public Book(Author author, String title, Boolean available, Category category) {
        this.addAuthor(author);
        this.title = title;
        this.available = available;
        this.categories.add(category);
    }
}
