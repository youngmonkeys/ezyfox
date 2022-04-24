package com.tvd12.ezyfox.codec;

public interface EzyMessage {

    /**
     * Get size of message.
     *
     * @return the size of message
     */
    int getSize();

    /**
     * Get content of message in byte array.
     *
     * @return the content of message
     */
    byte[] getContent();

    /**
     * Get header of message.
     *
     * @return the message header
     */
    EzyMessageHeader getHeader();

    /**
     * Get count of bytes.
     *
     * @return the count of bytes
     */
    int getByteCount();

    /**
     * check is big size or not.
     *
     * @return true or false
     */
    boolean hasBigSize();

    /**
     * Get start index of content byte array.
     *
     * @return the start index
     */
    int getContentStartIndex();

    /**
     * Get length of message's size, 2 or 4.
     *
     * @return the length of message's size
     */
    default int getSizeLength() {
        return hasBigSize() ? 4 : 2;
    }
}
