package com.improve10x.pomodoro.addedittask;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.pomodoro.Constants;
import com.improve10x.pomodoro.addedittask.EditTaskActivity;
import com.improve10x.pomodoro.addedittask.Task;
import com.improve10x.pomodoro.databinding.EditFragmentDialogBinding;


public class EditDialogFragment extends DialogFragment{

    private EditFragmentDialogBinding binding;
    protected Task task;
    public OnItemActionListener onItemActionListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = EditFragmentDialogBinding.inflate(getLayoutInflater());
        View view = (binding.getRoot());
        Bundle bundle = getArguments();
        task = (Task) bundle.getSerializable(Constants.KEY_TASK);
        handleEdit(task);
        //deleteTask(task.id);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        deleteTask(task.id);
    }

    private void handleEdit(Task task) {
        binding.editBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EditTaskActivity.class);
            intent.putExtra(Constants.KEY_TASK, task);
            startActivity(intent);
        });
    }

    private void deleteTask(String id) {
        binding.deleteBtn.setOnClickListener(view -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            db.collection("/users/" + user.getUid() + "/tasks")
                    .document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "successfully deleted", Toast.LENGTH_SHORT).show();
                            dismiss();
                            onItemActionListener.OnRefresh(task);

                        }
                    });
        });
    }
}