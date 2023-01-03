package com.improve10x.pomodoro;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.pomodoro.R;
import com.improve10x.pomodoro.databinding.EditFragmentDialogBinding;
import com.improve10x.pomodoro.fragment.Task;


public class EditDialogFragment extends DialogFragment {

    private EditFragmentDialogBinding binding;
    protected Task task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = EditFragmentDialogBinding.inflate(getLayoutInflater());
        View view = (binding.getRoot());
        Bundle bundle = getArguments();
        task = (Task) bundle.getSerializable(Constants.KEY_Task);
        handleEdit(task);
        deleteTask(task.id);
        return view;
    }

    private void handleEdit(Task task) {
        binding.editBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EditTaskActivity.class);
            intent.putExtra(Constants.KEY_Task, task);
            startActivity(intent);
        });
    }

    private void deleteTask(String id) {
        binding.deleteBtn.setOnClickListener(view -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("tasks")
                    .document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "successfully deleted", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
        });
    }
}