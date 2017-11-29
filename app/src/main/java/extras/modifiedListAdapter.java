package extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.moham.chatbotui.R;
import com.github.library.bubbleview.BubbleTextView;

import java.net.ContentHandler;
import java.util.List;

/**
 * Created by moham on 11/27/2017.
 */

public class modifiedListAdapter extends BaseAdapter
{
    private List<message> communications;
    //private Context context;
    private LayoutInflater layoutInflater;
    public modifiedListAdapter(List<message>messages, Context context)
    {
        this.communications=messages;
        //this.context=context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        return communications.size();
    }

    @Override
    public Object getItem(int x)
    {
        return communications.get(x);
    }

    @Override
    public long getItemId(int x)
    {
        return x;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent)
    {
        View v=convertView;
        if(v ==null)
        {
            if(communications.get(index).isReceived())
                v = layoutInflater.inflate(R.layout.messages_recieved,null);
            else
                v = layoutInflater.inflate(R.layout.messages_sent,null);
        }
        BubbleTextView text_message = (BubbleTextView)v.findViewById(R.id.text_message);
        text_message.setText(communications.get(index).getMessageText());
        return v;
    }
}
