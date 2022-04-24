package com.tvd12.ezyfox.message.util;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.message.annotation.EzyMessage;

public final class EzyMessageAnnotations {

    private EzyMessageAnnotations() {}

    public static String getChannelName(Class<?> messageClass) {
        String channel = getChannelName(messageClass.getAnnotation(EzyMessage.class));
        if (EzyStrings.isNoContent(channel)) {
            channel = messageClass.getSimpleName();
        }
        return channel;
    }

    public static String getChannelName(EzyMessage anno) {
        String channelName = anno.value();
        if (EzyStrings.isNoContent(channelName)) {
            channelName = anno.channel();
        }
        return channelName;
    }
}
