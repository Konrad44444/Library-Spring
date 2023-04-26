package database.project.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "book_category",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;
    
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

    public String categoriesToString() {
       return categories.stream()
            .map(Category::getName)
            .collect(Collectors.joining(" "));
    }
}
