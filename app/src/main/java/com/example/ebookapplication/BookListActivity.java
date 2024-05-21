package com.example.ebookapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ebookapplication.Adapter.BookAdapter;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.databinding.ActivityBookListBinding;

import java.util.List;

public class BookListActivity extends AppCompatActivity {
    ActivityBookListBinding binding;
    BookViewModel bookViewModel;
    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.rvBookList.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(this);
        binding.rvBookList.setAdapter(bookAdapter);

        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookViewModel.class);
//        BookModel bookModel = new BookModel();
//        bookModel.bookTitle = "Tên cuốn sách demo";
//        bookModel.authorName = "Tên tác giả cuốn sách demo";
//        bookModel.numberOfPages = 100;
//        bookModel.bookSummary = "Tóm tắt sách \"Effective Java\" (Ấn bản thứ hai) của Joshua Bloch là một cuốn sách hướng dẫn thực hành lập trình Java hiệu quả, tập trung vào các nguyên tắc thiết kế và kỹ thuật lập trình tốt. Cuốn sách này cung cấp các lời khuyên thiết thực và các mẫu thiết kế phổ biến để giúp các lập trình viên viết mã Java rõ ràng, hiệu quả và bảo trì dễ dàng. Dưới đây là tóm tắt của một số điểm chính trong cuốn sách:\n" +
//                "\n" +
//                "Tạo và Hủy bỏ Đối tượng (Creating and Destroying Objects):\n" +
//                "\n" +
//                "Sử dụng phương thức khởi tạo thay vì khởi tạo trực tiếp.\n" +
//                "Tránh tạo ra các đối tượng không cần thiết.\n" +
//                "Sử dụng các phương thức \"static factory\" thay vì constructors khi có thể.\n" +
//                "Áp dụng nguyên tắc Singleton và Builder cho các lớp phức tạp.\n" +
//                "Phương thức Common của Object (Methods Common to All Objects):\n" +
//                "\n" +
//                "Hiểu và triển khai đúng các phương thức equals, hashCode, toString, và clone.\n" +
//                "Triển khai giao diện Comparable cho các đối tượng cần sắp xếp.";
//        bookModel.bookCategory = "Lập trình cho Hải Đức";
//        bookViewModel.insertBook(bookModel);
        bookViewModel.getAllBooksLive().observe(BookListActivity.this, new Observer<List<BookModel>>() {
            @Override
            public void onChanged(List<BookModel> bookModelList) {
                if (bookModelList != null) {
                    bookAdapter.setBooks(bookModelList);
                }
            }
        });


    }

}