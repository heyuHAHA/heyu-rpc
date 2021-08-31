package common.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class UnsafeByteArrayOutputStream  extends OutputStream {

    private byte[] mBuffer;

    /**
     * 这里为啥用protected
     */
    protected int mCount;

    public UnsafeByteArrayOutputStream() {
        this(32);
    }

    public UnsafeByteArrayOutputStream(int size) {
        if (size < 0)
            throw new IllegalArgumentException("Negative initial size:" + size);

        mBuffer = new byte[size];
    }

    /**
     * 动态扩容的写方法
     * @param b
     * @throws IOException
     */
    public void write(int b) throws IOException {
        int newCount = mCount+1;
        if (newCount > mBuffer.length) {
            byte[] dest = new byte[Math.max(mBuffer.length << 1, newCount)];
            System.arraycopy(mBuffer,0,dest,0,Math.min(mBuffer.length,dest.length));
            mBuffer = dest;
        }
        mBuffer[mCount] = (byte) b;
        mCount = newCount;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if ((off < 0) || (off > b.length) || (len < 0) || ((off+len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0)
            return;
        int newCount = mCount + len;
        if (newCount > mBuffer.length) {
            byte[] dest = new byte[Math.max(mBuffer.length << 1, newCount)];
            System.arraycopy(mBuffer,0,dest,0,Math.min(mBuffer.length,dest.length));
            mBuffer = dest;
        }
        System.arraycopy(b,off,mBuffer,mCount,len);
        mCount = newCount;
    }

    public int size() {
        return mCount;
    }

    public void reset() {
        mCount = 0;
    }

    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(mBuffer,0,mCount);
    }

    public void writeTO(OutputStream outputStream) throws IOException {
        outputStream.write(mBuffer,0,mCount);
    }
}
