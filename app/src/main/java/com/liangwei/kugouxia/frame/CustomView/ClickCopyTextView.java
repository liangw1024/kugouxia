package com.liangwei.kugouxia.frame.CustomView;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * 点击即可复制当前文本的textview
 * Created by weibao on 2018/5/26.
 */

public class ClickCopyTextView extends androidx.appcompat.widget.AppCompatTextView
{
    private ITextOnclickListener iTextOnclickListener;
    public ClickCopyTextView(Context context, AttributeSet attri){
        super(context,attri);
        clickCopy(context);
    }

    public void setITextOnclickListener(ITextOnclickListener iTextOnclickListener)
    {
        this.iTextOnclickListener = iTextOnclickListener;
    }

    public ITextOnclickListener getITextOnclickListener()
    {
        return iTextOnclickListener;
    }
    public void clickCopy(final Context context){
        setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                if(iTextOnclickListener==null){
                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                    String text=(String) ClickCopyTextView.this.getText();
                    clipboardManager.setText(text.substring(0,text.length()-1));

                    Toast.makeText(context,"复制成功",Toast.LENGTH_SHORT).show();

                }else{
                    iTextOnclickListener.click(p1);
                }
            }
        });
    }
    public interface ITextOnclickListener{
        public void click(View p1);
    }
}
