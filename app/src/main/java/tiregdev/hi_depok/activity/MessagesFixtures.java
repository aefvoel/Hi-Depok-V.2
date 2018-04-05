package tiregdev.hi_depok.activity;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import tiregdev.hi_depok.model.Messages;
import tiregdev.hi_depok.model.UserMessage;

/*
 * Created by troy379 on 12.12.16.
 */
public final class MessagesFixtures extends FixturesData {
    private MessagesFixtures() {
        throw new AssertionError();
    }

    public static Messages getImageMessage() {
        Messages message = new Messages(getRandomId(), getUser(), null);
        message.setImage(new Messages.Image(getRandomImage()));
        return message;
    }

    public static Messages getVoiceMessage() {
        Messages message = new Messages(getRandomId(), getUser(), null);
        message.setVoice(new Messages.Voice("http://example.com", rnd.nextInt(200) + 30));
        return message;
    }

    public static Messages getTextMessage() {
        return getTextMessage(getRandomMessage());
    }

    public static Messages getTextMessage(String text) {
        return new Messages(getRandomId(), getUser(), text);
    }

    public static ArrayList<Messages> getMessages(Date startDate) {
        ArrayList<Messages> messages = new ArrayList<>();
        for (int i = 0; i < 10/*days count*/; i++) {
            int countPerDay = rnd.nextInt(5) + 1;

            for (int j = 0; j < countPerDay; j++) {
                Messages message;
                if (i % 2 == 0 && j % 3 == 0) {
                    message = getImageMessage();
                } else {
                    message = getTextMessage();
                }

                Calendar calendar = Calendar.getInstance();
                if (startDate != null) calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1));

                message.setCreatedAt(calendar.getTime());
                messages.add(message);
            }
        }
        return messages;
    }

    private static UserMessage getUser() {
        boolean even = rnd.nextBoolean();
        return new UserMessage(
                even ? "0" : "1",
                even ? names.get(0) : names.get(1),
                even ? avatars.get(0) : avatars.get(1),
                true);
    }
}
