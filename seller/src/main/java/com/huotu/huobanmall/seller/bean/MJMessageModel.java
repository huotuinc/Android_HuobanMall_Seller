package com.huotu.huobanmall.seller.bean;

import android.os.Message;

import java.util.List;

/**
 * Created by Administrator on 2015/9/28.
 */
public class MJMessageModel extends BaseModel {
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

    public class InnerClass
    {
        private List<MessageModel> messages;

        public List<MessageModel> getMessages()
        {
            return messages;
        }

        public void setMessages(List<MessageModel> messages)
        {
            this.messages = messages;
        }


    }
}
