package com.projeto.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.projeto.easymedico.R;
import com.projeto.model.Host;

/**
 * A simple {@link DialogFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HostDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HostDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostDialogFragment extends DialogFragment implements OnEditorActionListener {

    private static final String DIALOG_TAG = "editDialog";
    private static final String HOST = "host";
    private EditText edHost;
    private Host host;

    public HostDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param host Host.
     * @return A new instance of fragment HostDialogFragment.
     */
    public static HostDialogFragment newInstance(Host host) {
        HostDialogFragment dialog = new HostDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(HOST, host);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.host = (Host) getArguments().getSerializable(HOST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dialog_host,container,false);
        edHost = (EditText) layout.findViewById(R.id.edHost);
        edHost.requestFocus();
        edHost.setOnEditorActionListener(this);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(R.string.endereco_host);
        return layout;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(EditorInfo.IME_ACTION_DONE == actionId || EditorInfo.IME_ACTION_NEXT == actionId){
            Activity activity = getActivity();
            if(activity instanceof AoSalvarHost){
                if (this.host == null)
                    this.host = new Host(edHost.getText().toString());
                AoSalvarHost listener = (AoSalvarHost) activity;
                listener.salvouHost(this.host);
                dismiss();
                return true;

            }

        }
        return false;
    }

    public void abrir(FragmentManager fm){
        if(fm.findFragmentByTag(DIALOG_TAG) == null){
            show(fm, DIALOG_TAG);
        }
    }

    public interface AoSalvarHost {
        void salvouHost(Host host);
    }
}
