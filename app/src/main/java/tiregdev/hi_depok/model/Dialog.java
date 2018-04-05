package tiregdev.hi_depok.model;

import com.stfalcon.chatkit.commons.models.IDialog;

import java.util.ArrayList;

/*
 * Created by troy379 on 04.04.17.
 */
public class Dialog implements IDialog<Messages> {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private ArrayList<UserMessage> users;
    private Messages lastMessage;

    private int unreadCount;

    public Dialog() {
    }

    public Dialog(String id, String name, String photo,
                  ArrayList<UserMessage> users, Messages lastMessage, int unreadCount) {

        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<UserMessage> getUsers() {
        return users;
    }

    @Override
    public Messages getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(Messages lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
