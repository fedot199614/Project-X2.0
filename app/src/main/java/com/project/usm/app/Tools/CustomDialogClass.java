package com.project.usm.app.Tools;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.project.usm.app.R;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Button yes;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private String quyery,queryName;


    public CustomDialogClass(Activity a,String quyery,String queryName) {
        super(a);
        this.c = a;
        this.quyery= quyery;
        this.queryName =queryName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_edit_dialog);
        yes = (Button) findViewById(R.id.update_btn_ok);

        textInputLayout = (TextInputLayout) findViewById(R.id.update_text_input);
        textInputLayout.setHint(queryName);
        textInputEditText = (TextInputEditText) findViewById(R.id.update_text_input_inner);
        yes.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_btn_ok:
                String text = textInputEditText.getText().toString();
                Log.e("dfsdf",text);
                if(text.length()<=0){
                    textInputLayout.setError("Заполните поле!");
                }else{


                    dismiss();
                }



                break;
            default:
                dismiss();
                break;
        }
        dismiss();
    }
}
