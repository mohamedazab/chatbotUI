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

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof message))
        {
            return false;
        }
        return ((message) obj).messageText.equals(this.messageText) && ((message) obj).received == this.received;
    }

    @Override
    public String toString() {
        return "{ "+getMessageText()+" , "+received+" }";
    }
}
