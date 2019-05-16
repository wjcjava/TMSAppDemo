package e.hanglungdemo.when_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import e.hanglungdemo.MainActivity;
import e.hanglungdemo.R;

public class PageFragment extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int index = args.getInt("index");
        int layoutId = args.getInt("layoutId");
        int count = args.getInt("count");
        rootView = inflater.inflate(layoutId, null);
        // 滑动到最后一页有点击事件
        if (index == count - 1) {
            rootView.findViewById(R.id.id_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            });
        }

        return rootView;
    }
}
