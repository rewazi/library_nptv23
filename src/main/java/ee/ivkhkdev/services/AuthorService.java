package ee.ivkhkdev.services;

import ee.ivkhkdev.apphelpers.AppHelper;
import ee.ivkhkdev.model.Author;

import java.util.List;

public class AuthorService implements Service<Author>{
    private final List<Author> authors;
    private final AppHelper<Author> appHelperAuthor;

    public AuthorService(List<Author> authors, AppHelper<Author>  appHelperAuthor) {
         this.authors = authors;
         this.appHelperAuthor = appHelperAuthor;
    }

    @Override
    public boolean add() {
        try {
            Author author = appHelperAuthor.create();
            if(author == null) {return false;}
            authors.add(author);
            return true;
        }catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean edit(Author entity) {
        return false;
    }

    @Override
    public boolean remove(Author entity) {
        return false;
    }

    @Override
    public void print() {
        appHelperAuthor.printList(this.list());
    }

    @Override
    public List<Author> list() {
        return authors;
    }
}
