package interfaces;

import java.util.List;

public interface SearchInterface<T> {

    T searchById(String id);
    List<T> searchByDate(String date);
    List<T> searchByDateRange(String startDate, String endDate);
    List<T> searchAll();




}
