package com.example.ebookapplication.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.PageModel;

import java.util.List;

@Dao
public interface PageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPage(PageModel pageModel);

    @Update
    void updatePage(PageModel pageModel);

    @Delete
    void deletePage(PageModel pageModel);

    @Query("SELECT * FROM page")
    LiveData<List<PageModel>> getAllPagesLive();

    @Query("SELECT * FROM page")
    List<PageModel> getAllPagesFuture();

    @Query("SELECT * FROM page WHERE bookId = :id")
    List<PageModel> getAllPagesFromBookIdFuture(int id);
    @Query("SELECT * FROM page")
    List<PageModel> getAllPages();

    @Query("SELECT * FROM page WHERE pId=:id")
    PageModel getPage(int id);

}