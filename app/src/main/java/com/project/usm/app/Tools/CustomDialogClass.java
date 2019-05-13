package com.project.usm.app.Tools;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.usm.app.R;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Button yes;
    private TextView textUpdate;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private String title,queryName;



    public CustomDialogClass(TextView s, Activity a, String title, String queryName) {
        super(a);
        this.c = a;
        this.title= title;
        this.queryName = queryName;
        this.textUpdate = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_edit_dialog);
        yes = (Button) findViewById(R.id.update_btn_ok);

        textInputLayout = (TextInputLayout) findViewById(R.id.update_text_input);
        textInputLayout.setHint(title);
        textInputEditText = (TextInputEditText) findViewById(R.id.update_text_input_inner);
        yes.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_btn_ok:
                String text = textInputEditText.getText().toString();

                if(text.length()==0){
                    Toast.makeText(c, c.getString(R.string.empty),Toast.LENGTH_SHORT).show();
                }else{
                    BaseQuery.updateData(c,queryName,text);
                    textUpdate.setText(text);
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
