package com.example.ebookapplication.Utils;

import androidx.lifecycle.ViewModelProvider;

import com.example.ebookapplication.CategoryModel;
import com.example.ebookapplication.UserCategoryModel;
import com.example.ebookapplication.ViewModel.CategoryViewModel;
import com.example.ebookapplication.ViewModel.UserCategoryViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtilsForDuc {
    public static String listInsightToString(List<UserCategoryModel> userCategoryModels, List<CategoryModel> categoryModels) {
        Map<String, String> map = new HashMap<>();
        for (CategoryModel categoryModel : categoryModels) {
            map.put(categoryModel.cFirebaseId, categoryModel.categoryName);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (UserCategoryModel userCategoryModel : userCategoryModels) {
            stringBuilder.append(map.get(userCategoryModel.categoryId) + ": " + userCategoryModel.userCategoryPoints + "\n");
        }
        return stringBuilder.toString();
    }
}
