package com.roomdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.roomdatabase.databinding.ActivityNewWordBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class UpdateNotesActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;
    private ActivityNewWordBinding binding;
    private Word word;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_word);
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        binding.tvHeading.setText(getString(R.string.update_notes));
        word = (Word) getIntent().getSerializableExtra("word");
        loadData();
        clickListener();
    }
    private void loadData() {
        if (word != null) {
            binding.etTitle.setText(word.getTitle());
            binding.etDate.setText(word.getDate());
            binding.etMessage.setText(word.getMessage());
        }
    }

    private void clickListener() {
        binding.etDate.setOnClickListener(v -> datePicker());
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.btnSave.setOnClickListener(view -> updateData());
    }

    private void updateData() {
        String title = binding.etTitle.getText().toString().trim();
        String date = binding.etDate.getText().toString().trim();
        String message = binding.etMessage.getText().toString().trim();
        word.setTitle(title);
        word.setDate(date);
        word.setMessage(message);
        mWordViewModel.update(word);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void datePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {
                    String date1 = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    binding.etDate.setText(date1);
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        dpd.setStyle(DialogFragment.STYLE_NORMAL, R.style.datePicker);
    }
}

