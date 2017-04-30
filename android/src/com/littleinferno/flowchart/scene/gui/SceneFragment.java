package com.littleinferno.flowchart.scene.gui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.littleinferno.flowchart.databinding.LayoutSceneBinding;
import com.littleinferno.flowchart.function.AndroidFunction;

public class SceneFragment extends Fragment {

    LayoutSceneBinding layout;

    private AndroidFunction function;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = LayoutSceneBinding.inflate(inflater, container, false);
        return layout.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getArguments();

        if (bundle != null)
            function = bundle.getParcelable(AndroidFunction.TAG);


        if (function == null)
            throw new RuntimeException("function cannot be null");

        function.getProject().setCurrentScene(function);
        function.bindScene(layout.scene);
    }
}