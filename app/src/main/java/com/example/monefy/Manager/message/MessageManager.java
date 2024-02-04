package com.example.monefy.Manager.message;

import android.util.Log;

import com.example.monefy.Manager.firebase.FirebaseManager;
import com.example.monefy.R;
import com.example.monefy.basic.functionality.model.DataLoadListener;
import com.example.monefy.basic.functionality.model.message.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private static MessageManager messageManager;
    public static MessageManager getMessageManager(){
        if(messageManager == null){
            messageManager = new MessageManager();
        }
        return messageManager;
    }

    public MessageManager() {
    }

    /** Змерігаємо список повідомлень.*/
    private static List<Message> messageList = new ArrayList<>();

    /**
     * loadMessage - Здійснює загрузку повідомлень.
     */
    public void loadMessage(DataLoadListener dataLoadListener){
        FirebaseManager firebaseManager = FirebaseManager.getFirebaseManager();
        firebaseManager.getMessageData(new OnMessageCallback() {
            @Override
            public void onMessageDataReceived(List<Message> messageList) {
                updateMessageList(messageList);
                dataLoadListener.onDataLoaded();
            }

            @Override
            public void onDataNotFound() {
                Log.d("error","Відсутні дані");
            }

            @Override
            public void onDataError(Exception e) {
                Log.e("ERROR", "Помилка при отриманні даних: " + e.getMessage());
            }
        });
    }

    /** Метод обноляє список повідомлень.
     * @param list передаємо як парамер новий список повідомлень.
     */
    private void updateMessageList(List<Message> list){
        messageList.clear();
        messageList.addAll(list);
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    /** Повертає кількість повідомлень*/
    public int getCollMessage(){
        if(!messageList.isEmpty()){
            return messageList.size();
        }else {
            return 0;
        }
    }

    /** Повертає останнє повідомлення у списку.*/
    public Message getLastMessageList(){
        if(!messageList.isEmpty()){
            return getMessageList().get(getCollMessage()-1);
        }else{
            return new Message("На даний момент повідомлень не існує", false, null);
        }
    }
}
