package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyInts;

public final class EzyMessageReaders {

    private EzyMessageReaders() {}

    public static EzyMessage bytesToMessage(byte[] bytes) {
        EzyMessageHeader header = EzyMessageHeaderReader.read(bytes[0]);
        int messageSizeLength = header.isBigSize() ? 4 : 2;
        int minSize = 2 + messageSizeLength;
        if(bytes.length < minSize)
            return null;
        byte[] messageSizeBytes = EzyBytes.copy(bytes, 1, messageSizeLength);
        int messageSize = EzyInts.bin2int(messageSizeBytes);
        int allSize = 1 + messageSizeLength + messageSize;
        if(bytes.length < allSize)
            return null;
        int contentStart = 1 + messageSizeLength;
        byte[] messageContent = EzyBytes.copy(bytes, contentStart, messageSize);
        return new EzySimpleMessage(header, messageContent, messageSize);
    }
}
