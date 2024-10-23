package repository;


import javafx.collections.ObservableList;

public interface CrudRepository<T> extends SuperDao{
    boolean save(T t);
    ObservableList<Integer> getIds();
    ObservableList<String> getStringIds();
    ObservableList<T> getAll();
    T findById(String id);



}
