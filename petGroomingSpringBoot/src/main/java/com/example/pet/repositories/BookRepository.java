package com.example.pet.repositories;
import java.util.List;
// import java.util.Date;
// import com.example.pet.utils.TimeDeltaAdd;

import com.example.pet.dto.MessageDetails;
// import com.example.pet.exceptions.CoundNotFoundException;
import com.example.pet.exceptions.UpdateErrorException;
import com.example.pet.exceptions.InsertErrorException;
import com.example.pet.exceptions.DeleteErrorException;
import com.example.pet.models.Book;
import com.example.pet.dto.BookInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbc;

    public BookRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<BookInfo> getBooks() {
        String sql = "SELECT book_id, b.cus_id,c.cus_name,service_kind,b.s_id,s.work_time book_time, s.emp_id,e.emp_name,b.pet_id,p.pet_name FROM book b JOIN schedule s ON b.s_id = s.s_id JOIN pet p ON b.pet_id = p.pet_id JOIN employee e ON e.emp_id = s.emp_id  JOIN customer c ON c.cus_id = b.cus_id";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        RowMapper<BookInfo> rowMapper = (r, i) -> {
            BookInfo book = new BookInfo();
            book.setBookId(r.getInt("book_id"));
            book.setCusId(r.getInt("cus_id"));
            book.setCusName(r.getString("cus_name"));
            book.setServiceKind(r.getInt("service_kind"));
            book.setsId(r.getInt("s_id"));
            book.setBookTime(r.getString("book_time"));
            book.setEmpId(r.getInt("emp_id"));
            book.setEmpName(r.getString("emp_name"));
            book.setPetId(r.getInt("pet_id"));
            book.setPetName(r.getString("pet_name"));
            return book;
        };

        return jdbc.query(sql, rowMapper);
    }
    
    public List<BookInfo> getBooksByBookId(Integer bookId) {
        String sql = "SELECT book_id, b.cus_id,c.cus_name,service_kind,b.s_id,s.work_time book_time, s.emp_id,e.emp_name,b.pet_id,p.pet_name FROM book b JOIN schedule s ON b.s_id = s.s_id JOIN pet p ON b.pet_id = p.pet_id JOIN employee e ON e.emp_id = s.emp_id  JOIN customer c ON c.cus_id = b.cus_id WHERE book_id = ?";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        RowMapper<BookInfo> rowMapper = (r, i) -> {
            BookInfo book = new BookInfo();
            book.setBookId(r.getInt("book_id"));
            book.setCusId(r.getInt("cus_id"));
            book.setCusName(r.getString("cus_name"));
            book.setServiceKind(r.getInt("service_kind"));
            book.setsId(r.getInt("s_id"));
            book.setBookTime(r.getString("book_time"));
            book.setEmpId(r.getInt("emp_id"));
            book.setEmpName(r.getString("emp_name"));
            book.setPetId(r.getInt("pet_id"));
            book.setPetName(r.getString("pet_name"));
            return book;
        };

        return jdbc.query(sql, rowMapper, bookId);
    }

    public List<BookInfo> getBooksByCusId(Integer cusId) {
        String sql = "SELECT book_id, b.cus_id,c.cus_name,service_kind,b.s_id,s.work_time book_time, s.emp_id,e.emp_name,b.pet_id,p.pet_name FROM book b JOIN schedule s ON b.s_id = s.s_id JOIN pet p ON b.pet_id = p.pet_id JOIN employee e ON e.emp_id = s.emp_id  JOIN customer c ON c.cus_id = b.cus_id  WHERE c.cus_id = ?";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        RowMapper<BookInfo> rowMapper = (r, i) -> {
            BookInfo book = new BookInfo();
            book.setBookId(r.getInt("book_id"));
            book.setCusId(r.getInt("cus_id"));
            book.setCusName(r.getString("cus_name"));
            book.setServiceKind(r.getInt("service_kind"));
            book.setsId(r.getInt("s_id"));
            book.setBookTime(r.getString("book_time"));
            book.setEmpId(r.getInt("emp_id"));
            book.setEmpName(r.getString("emp_name"));
            book.setPetId(r.getInt("pet_id"));
            book.setPetName(r.getString("pet_name"));
            return book;
        };

        return jdbc.query(sql, rowMapper, cusId);
    }
    
    public void addBook(Book book) {
        String sql = "INSERT INTO book VALUES(book_id, ?, ?, ?, ?)";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        int insertNum = jdbc.update(sql, book.getCusId(),  book.getServiceKind(),
        book.getsId(), book.getPetId());

        if (insertNum == 0){
            MessageDetails msg = new MessageDetails("Book could not be inserted. ", false);
            throw new InsertErrorException(msg);
        }
    }
    
    public void updateBook(Book book) {
        int bookId = book.getBookId();
        int cusId = book.getCusId();
        int serviceKind = book.getServiceKind();
        int sId = book.getsId();
        int petId = book.getPetId();

        // TimeDeltaAdd td = new TimeDeltaAdd();
        // bookTime = td.AddHour(bookTime);

        String sql = "UPDATE book SET cus_id = ?, service_kind = ?, s_id = ?, pet_id = ? WHERE book_id = ?";
        int updateNum = jdbc.update(sql, cusId, serviceKind, sId, petId, bookId);

        if (updateNum == 0){
            MessageDetails msg = new MessageDetails("Book could not be updated. book_id = " + bookId, false);
            throw new UpdateErrorException(msg);
        }
    }
    
    public void deleteBook(Integer bookId) {
        String sql = "DELETE FROM book WHERE book_id = ?";
        int deleteNum = jdbc.update(sql, bookId);

        if (deleteNum == 0){
            MessageDetails msg = new MessageDetails("Book could not be deleted. bookId = " + bookId, false);
            throw new DeleteErrorException(msg);
        }
    }



}
