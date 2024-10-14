package repository;



public interface CrudRepository<T> extends SuperDao{
    boolean save(T t);


}
