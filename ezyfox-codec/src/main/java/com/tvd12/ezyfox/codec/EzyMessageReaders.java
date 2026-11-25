package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyInts;

public final class EzyMessageReaders {

    private EzyMessageReaders() {}

    public static EzyMessage bytesToMessage(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        EzyMessageHeader header = EzyMessageHeaderReader.read(bytes[0]);
        int messageSizeLength = header.isBigSize() ? 4 : 2;
        int contentStart = 1 + messageSizeLength;
        if (bytes.length <= contentStart) {
            return null;
        }
        byte[] messageSizeBytes = EzyBytes.copy(bytes, 1, messageSizeLength);
        long messageSize = EzyInts.bin2uint(messageSizeBytes) & 0xFFFFFFFFL;
        if (messageSize == 0 || messageSize > bytes.length - contentStart) {
            return null;
        }
        int size = (int) messageSize;
        byte[] messageContent = EzyBytes.copy(bytes, contentStart, size);
        return new EzySimpleMessage(header, messageContent, size);
    }
}
