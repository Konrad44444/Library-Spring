# My Library Service

Projekt wykonany na przedmiot bazy danych. System zarządzania biblioteką dostępny jest dla użytkowników biblioteki jak i jej pracowników. 

Użytkownik ma możliwość przeglądania książek dostęplnych w bibliotece, wypożyczania ich i zmiany danych swojego profilu. W każdym momencie użytkownik może przejrzeć książki, które wypożyczył. 

Z poziomu konta bibliotekarza można dodawać, edytować i usuwać ksążki, autorów i kategorie książek. Administrator systemu ma podgląd do wszystkich wypożyczeń. Tylko on może zwracać wypożyczenia.

## Wykonanie

System został wykonany za pomocą frameworka Spring Boot oraz bazy danych MySQL. 

### Schemat bazy danych
![image](https://github.com/Konrad44444/Library-Spring/assets/116802313/1838916f-a32e-4782-8bfb-fdf82472c0d0)

#### Opis tabel
  - Books - informacje o książce: tytuł, informację o dostępności książki, autorze, kategorii
  - Authors - informacje o autorze: imię i nazwiko
  - Categories - informacje o kategorii: nazwa
  - Basket - informacja o tym czy książka jest w koszyku jakiegoś użytkownika
  - Loans - informacje o wypożyczeniach: książki, użytkownik, data wypożyczenia i zwrotu
  - Users - informacje o użytkowniku: login, hasło, imię, nazwisko, czy jest bibliotekarzem

#### Przykład tabeli w kodzie programu
```java
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;
    
    private String title;

    private String available;

    public void addAuthor(Author author) {
        this.author = author;
        author.getBooks().add(this);
    }

    public Book(Author author, String title, String available, Category category) {
        this.addAuthor(author);
        this.title = title;
        this.available = available;
        this.category = category;
    }
}
```

### Przykład działania programu
Ekran logowania:

![image](https://github.com/Konrad44444/Library-Spring/assets/116802313/d05d3508-0ed5-40c0-a1c8-473c2b1073c6)

Widok listy książek z poziomu użytkownika:

![image](https://github.com/Konrad44444/Library-Spring/assets/116802313/926c8756-44ea-4c0c-a42f-58415968618a)

Widok listy książek z poziomu bibliotekarza:

![image](https://github.com/Konrad44444/Library-Spring/assets/116802313/c1a573fd-bd8e-42e2-a297-350c04ff9533)

## Przeniesienie systemu do programu Microsoft Access

Tabele, kwerendy, formularze i raporty:

![image](https://github.com/Konrad44444/Library-Spring/assets/116802313/f40c3a7a-75cc-41fa-a7f5-51065d7052cc)

Raport wszystkich książek:

![image](https://github.com/Konrad44444/Library-Spring/assets/116802313/fb54be10-bb7a-4a52-81fb-6fe15e6d5c91)

Raport wszystkich wypożyczeń:

![image](https://github.com/Konrad44444/Library-Spring/assets/116802313/b4b7b025-a0dc-4377-ab33-f578df78d8bf)

Dodawanie wypożyczenia za pomocą formularza z polami kombi:

![image](https://github.com/Konrad44444/Library-Spring/assets/116802313/3361a009-2c0c-40b5-b7ad-4d34cee938c2)

