package extras;

/**
 * Created by moham on 11/27/2017.
 */

public class message
{
    private String messageText;

   private boolean received;

    public message(String t,boolean r)
    {
        this.messageText = t;
        this.received = r;
    }

    public boolean isReceived()
    {
        return received;
    }
    public void setReceived(boolean received)
    {
        this.received = received;
    }

    public void setMessageText(String messageText)
    {
        this.messageText = messageText;
    }

    public String getMessageText()
    {
        return messageText;
    }
}
